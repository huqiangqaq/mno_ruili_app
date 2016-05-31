package mno.down.modal;

import java.io.Serializable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
@Table(name = "DownloadDetail")
public class DownloadDetail extends Model implements Serializable{

	public DownloadDetail(int videoId,String downloadUrl,String iconUrl,String fileName,String title,String summary,String extension) {
		 this.VideoId = videoId;
		 this.DownloadUrl = downloadUrl;
		 this.HomeIcon = iconUrl;
		 this.FileName = fileName;
		 this.Title = title;
		 this.Summary = summary;
		 this.Extension = extension;
	}
	
	public DownloadDetail(){}

	private static final long serialVersionUID = 1L;
	
	 @Column(name ="VideoId",unique = true)
	 public  int VideoId;
	 
	 @Column(name = "DownloadUrl")
	 public String DownloadUrl;
	 
	 @Column(name = "FileName")
	 public String FileName;
	 
	 @Column(name = "HomeIcon")
	 public String HomeIcon;
	 
	 @Column(name = "Title")
	 public String Title;
	 
	 @Column(name = "Extension")
	 public String Extension;
	 
	 @Column(name ="Summary")
	 public String Summary;
}
