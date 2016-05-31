package mno.down.modal;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mno.down.util.FileUtil;
import mno.down.util.Mission;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

@Table(name = "DownloadRecord")
public class DownloadRecord extends Model implements Serializable{
	  
	private static final long serialVersionUID = 1L;

	public class STATUS{
		public static final int DOWNLOADING = 0;
		public static final int ERROR = 1;
        public static final int SUCCESS = 2;
        public static final int CANCELED = 3;
        public static final int PREPARE = 4;
        public static final int PAUSE = 5;
    };
	  
	  @Column(name = "DownloadId")
	  public int DownloadId;
	  @Column(name = "DownloadUrl")
	  public String DownloadUrl;
	  @Column(name = "HomeIcon")
	  public String HomeIcon;
	  @Column(name="FileName")
	  public String FileName;
	  @Column(name = "Title")
	  public String Title;
	  @Column(name = "Extension")
	  public String Extension; 
	  @Column(name ="Summary")
	  public String Summary;
	  
	  @Column(name = "FileSize")
	  public long FileSize;
	  @Column(name = "DownloadedSize")
	  public long DownloadedSize;
	  @Column(name = "DownloadedPercentage")
	  public int DownloadedPercentage;
	  @Column(name = "AddedTime")
	  public String AddedTime;
	  @Column(name = "Status")
	  public int Status;
	  @Column(name = "SaveDir")
	  public String SaveDir;
	  @Column(name = "SaveFileName")
	  public String SaveFileName;
	  
	 
	  
	  public DownloadRecord(){}
	  
	  public DownloadRecord(DownloadDetail detail){
		    this.DownloadId = detail.VideoId;
		    this.HomeIcon = detail.HomeIcon;
	        this.FileName = detail.FileName;
	        this.Title = detail.Title;
	        this.Summary = detail.Summary;
	        this.Extension = detail.Extension;
	    }
	  
	  
	  public DownloadRecord(DownloadDetail detail,Mission mission){
	        this(detail);
	        AddedTime = new Date().toString();
	        FileSize = mission.getFilesize();
	        DownloadedSize = mission.getDownloaded();
	        DownloadedPercentage = mission.getPercentage();
	        SaveDir = mission.getSaveDir();
	        if(mission.isDone()){
            	Status = mission.isSuccess() ? STATUS.SUCCESS : STATUS.ERROR;
            }else if(mission.isCanceled()){ //任务取消状态，下载记录为暂停状态
            	Status = STATUS.PAUSE;
            }else if(mission.isDownloading()){
            	Status = STATUS.DOWNLOADING; //任务等待状态
            }else{
            	Status = STATUS.PREPARE;
            }
	        
	        SaveFileName = mission.getSaveName();
	        DownloadUrl = mission.getUri();
	    }
	  
	  public String getAccurateReadableSize() {
		  return FileUtil.getReadableSize(DownloadedSize);
	  }
	  
	  public String getFileSize(){
		  return FileUtil.getReadableSize(FileSize);
	  }
	  
	  public static List<DownloadRecord> getAllDownloaded(){
		  try{
	        return new Select()
	                .from(DownloadRecord.class)
	                .where("Status = ?",STATUS.SUCCESS)
	                .orderBy("AddedTime desc")
	                .execute();
		  } catch (Exception e) {
				// TODO Auto-generated catch block
			  	List<DownloadRecord> d=new ArrayList<DownloadRecord>();
				  return d;
			}
	  }
	  
	  /*
	   *  获取所有可以继续下载的线程
	   */
	  public static List<DownloadRecord> getAllDownloading(){
		  try{
		  return new Select()
          .from(DownloadRecord.class)
          .where("Status == ?  OR Status ==? OR Status ==?",STATUS.PAUSE,STATUS.DOWNLOADING,STATUS.ERROR)
          .orderBy("AddedTime desc")
          .execute();
	  } catch (Exception e) {
			// TODO Auto-generated catch block
		  	List<DownloadRecord> d=new ArrayList<DownloadRecord>();
			  return d;
		}
	  }
	  
	  /*
	   *  还未开始下载的线程
	   */
	  public static List<DownloadRecord> getPreDownloading(){
		  try{
		  return new Select()
          .from(DownloadRecord.class)
          .where("Status == ?",STATUS.PREPARE)
          .orderBy("AddedTime desc")
          .execute();
		  } catch (Exception e) {
				// TODO Auto-generated catch block
			  	List<DownloadRecord> d=new ArrayList<DownloadRecord>();
				  return d;
			}
	  }
	  
	  public static List<DownloadRecord> getAllFailures(){
		  try{
	       return new Select()
	               .from(DownloadRecord.class)
	               .where("Status = ?",STATUS.ERROR)
	               .orderBy("AddedTime desc")
	               .execute();
		  } catch (Exception e) {
			// TODO Auto-generated catch block
				List<DownloadRecord> d=new ArrayList<DownloadRecord>();
				  return d;
		}
		
	 }
	  
	  
	  public static void save(DownloadDetail detail,Mission mission){
	        DownloadRecord record = new Select()
	                .from(DownloadRecord.class)
	                .where("DownloadId = ?" , detail.VideoId)
	                .executeSingle();
	        if(record == null){
	            new DownloadRecord(detail,mission).save();
	        }else{
	            int status = STATUS.DOWNLOADING;
	            if(mission.isDone()){
	            	status = mission.isSuccess() ? STATUS.SUCCESS : STATUS.ERROR;
	            }else if(mission.isCanceled()){ //任务取消状态，下载记录为暂停状态
	            	status = STATUS.PAUSE;
	            }else if(mission.isDownloading()){
	            	status = STATUS.DOWNLOADING; //任务等待状态
	            }else{
	            	status = STATUS.PREPARE;
	            }
	            new Update(DownloadRecord.class)
	                            .set("FileSize = ?," +
	                            		"HomeIcon = ?," +
	                                    "DownloadedSize = ?," +
	                                    "DownloadedPercentage = ?," +
	                                    "Status = ?," +
	                                    "SaveDir = ?," +
	                                    "SaveFileName = ? ," +
	                                    "DownloadUrl = ? ",
	                            mission.getFilesize(),
	                            detail.HomeIcon,
	                            mission.getDownloaded(),
	                            mission.getPercentage(),
	                            status,
	                            mission.getSaveDir(),
	                            mission.getSaveName(),
	                            mission.getUri())
	                    .where("DownloadId = ?",detail.VideoId)
	                    .execute();
	        }
	    }
	  
	 public static void deleteOne(DownloadDetail detail){
	        new Delete().from(DownloadRecord.class).where("DownloadId = ?",detail.VideoId).execute();
	  }
	 public static void deleteOne(String id){
	        new Delete().from(DownloadRecord.class).where("DownloadId = ?",id).execute();
	  }

	  public static void deleteAll(){
	       List<DownloadRecord> records = new Select().from(DownloadRecord.class).execute();
	       for(int i = 0; i< records.size();i++){
	            DownloadRecord r = records.get(i);
	            String filePath = r.SaveDir + r.SaveFileName;
	            File f = new File(filePath);
	            if(f.exists() && f.isFile()){
	                f.delete();
	            }
	        }
	        new Delete().from(DownloadRecord.class).execute();
	  }

	public static DownloadDetail getDownloadDetail(DownloadRecord record) {
		return new DownloadDetail(record.DownloadId,record.DownloadUrl,record.HomeIcon,record.FileName,record.Title,record.Summary,record.Extension);
	}
}
