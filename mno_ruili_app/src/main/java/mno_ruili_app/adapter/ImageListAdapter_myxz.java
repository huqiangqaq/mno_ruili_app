package mno_ruili_app.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno_ruili_app.net.RequestType;

import com.ab.download.AbDownloadProgressListener;
import com.ab.download.AbDownloadThread;
import com.ab.download.AbFileDownloader;
import com.ab.download.DownFile;
import com.ab.image.AbImageLoader;
import com.ab.task.AbTask;
import com.ab.task.AbTaskItem;
import com.ab.task.AbTaskListener;
import com.ab.util.AbFileUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.ab.util.AbViewHolder;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImageListAdapter_myxz extends  BaseAdapter{
	public HashMap<String,AbFileDownloader> mFileDownloaders = null;
	private static String TAG = "ImageListAdapter";
	//private static final boolean D = Constant.DEBUG;
  
	private Context mContext;
	//xml转View对象
    private LayoutInflater mInflater;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private List<DownFile> mData;
    //Map中的key
    private String[] mFrom;
    //view的id
    private int[] mTo;
    //图片下载器
    private AbImageLoader mAbImageLoader = null;
    private boolean isCheck, check;
    public boolean getCheck() {
		return check;
	}
	
	public void setCheck(boolean check) {
		this.check = check;
	}
	public void setBoolean(boolean isCheck) {
		this.isCheck = isCheck;
	}
	/**
	 * CheckBox 是否选择的存储集合,key 是 id , value 是该position是否选中
	 */
	private Map<String, Boolean> isCheckMap = new HashMap<String, Boolean>();
	
	/**
	 * 首先,默认情况下,所有项目都是没有选中的.这里进行初始化
	 */
	public void configCheckMap(boolean bool) {
		isCheckMap.clear();
		for (int i = 0; i < mData.size(); i++) {
			isCheckMap.put(mData.get(i).get_ID()+"", bool);
		}

	}
	public Map<String, Boolean> getCheckMap() {
		return this.isCheckMap;
	}

	// 移除一个项目的时候
	public void remove(int position) {
		this.mData.remove(position);
	}
	
   /**
    * 构造方法
    * @param context
    * @param data 列表展现的数据
    * @param resource 单行的布局
    * @param from Map中的key
    * @param to view的id
    */
    public ImageListAdapter_myxz(Context context, List<DownFile> data,
            int resource, String[] from, int[] to){
    	this.mContext = context;
    	this.mData = data;
    	this.mResource = resource;
    	this.mFrom = from;
    	this.mTo = to;
        //用于将xml转为View
        this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //图片下载器
        mAbImageLoader = AbImageLoader.newInstance(mContext);
        mAbImageLoader.setMaxWidth(0);
        mAbImageLoader.setMaxHeight(0);
        mAbImageLoader.setLoadingImage(R.drawable.image_loading);
        mAbImageLoader.setErrorImage(R.drawable.image_error);
        mAbImageLoader.setEmptyImage(R.drawable.image_empty);
        mFileDownloaders = new HashMap<String,AbFileDownloader>();
    }   
    
    @Override
    public int getCount() {
        return mData.size();
    }
    
    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
         /* if(convertView == null){*/
	           //使用自定义的list_items作为Layout
	           convertView = mInflater.inflate(R.layout.down_items, parent, false);
         // }
          
          
          
       /*   CheckBox cbCheckBox=AbViewHolder.get(convertView, R.id.cbCheckBox);
          
          
          if (isCheck) {
        	  cbCheckBox.setVisibility(View.VISIBLE);
  		} else {
  			  cbCheckBox.setVisibility(View.GONE);
  		}*/
          
          final ViewHolder holder = new ViewHolder();
    	holder.itemsIcon = (ImageView) convertView.findViewById(R.id.itemsIcon);
  	    holder.itemsTitle = (TextView) convertView.findViewById(R.id.itemsTitle);
  	    holder.itemsDesc = (TextView) convertView.findViewById(R.id.itemsDesc);
  	    holder.operateBtn = (Button) convertView.findViewById(R.id.operateBtn);
  	    holder.progress = (ProgressBar) convertView.findViewById(R.id.received_progress) ;
  	    holder.received_progress_percent = (TextView) convertView.findViewById(R.id.received_progress_percent);
  	    holder.received_progress_number = (TextView) convertView.findViewById(R.id.received_progress_number);
  	    holder.received_progressBar = (RelativeLayout) convertView.findViewById(R.id.received_progressBar) ;
  	    
  	    holder.itemsIcon.setFocusable(false);
  	    holder.operateBtn.setFocusable(false);
  	    holder.progress.setFocusable(false);
		  //获取该行的数据
        
          final DownFile mDownFile= (DownFile)mData.get(position);
          
          if (mDownFile != null) {
	          //holder.itemsIcon.setImageResource(mDownFile.getIcon());
	          holder.itemsTitle.setText(mDownFile.getName());
	          holder.itemsDesc.setText(mDownFile.getDescription());
	          if(mDownFile.getState() == Constant.undownLoad){
	        	  holder.operateBtn.setVisibility(View.VISIBLE);
	        	  holder.operateBtn.setBackgroundResource(R.drawable.view_zt);
	        	  holder.received_progressBar.setVisibility(View.GONE);
	        	  holder.itemsDesc.setVisibility(View.VISIBLE);
	        	  holder.progress.setProgress(0);
	        	  holder.received_progress_percent.setText(0+"%");
	        	  holder.received_progress_number.setText("0KB/"+AbStrUtil.getSizeDesc(mDownFile.getTotalLength()));
	          }else if(mDownFile.getState() == Constant.downInProgress){
	        	  holder.operateBtn.setVisibility(View.VISIBLE);
	        	  holder.operateBtn.setBackgroundResource(R.drawable.view_ks);
	        	  if(mDownFile.getDownLength()!=0 && mDownFile.getTotalLength()!=0){
		        	  int c = (int)(mDownFile.getDownLength()*100/mDownFile.getTotalLength());
		        	  holder.itemsDesc.setVisibility(View.GONE);
		        	  holder.received_progressBar.setVisibility(View.VISIBLE);
		        	  holder.progress.setProgress(c);
		        	  holder.received_progress_percent.setText(c+"%");
		        	  holder.received_progress_number.setText(AbStrUtil.getSizeDesc(mDownFile.getDownLength())+"/"+AbStrUtil.getSizeDesc(mDownFile.getTotalLength()));
		          }
	          }else if(mDownFile.getState() == Constant.downLoadPause){
	        	  holder.operateBtn.setVisibility(View.VISIBLE);
	        	  holder.operateBtn.setBackgroundResource(R.drawable.view_zt);
	        	  //下载了多少
	        	  if(mDownFile.getDownLength()!=0 && mDownFile.getTotalLength()!=0){
		        	  int c = (int)(mDownFile.getDownLength()*100/mDownFile.getTotalLength());
		        	  holder.itemsDesc.setVisibility(View.GONE);
		        	  holder.received_progressBar.setVisibility(View.VISIBLE);
		        	  holder.progress.setProgress(c);
		        	  holder.received_progress_percent.setText(c+"%");
		        	  holder.received_progress_number.setText(AbStrUtil.getSizeDesc(mDownFile.getDownLength())+"/"+AbStrUtil.getSizeDesc(mDownFile.getTotalLength()));
		          }else{
		        	  holder.itemsDesc.setVisibility(View.VISIBLE);
		        	  holder.received_progressBar.setVisibility(View.GONE);
		        	  holder.progress.setProgress(0);
		        	  holder.received_progress_percent.setText(0+"%");
		        	  holder.received_progress_number.setText("0KB/"+AbStrUtil.getSizeDesc(mDownFile.getTotalLength()));
		          }
	          }else if(mDownFile.getState() == Constant.downloadComplete){
	        	  holder.operateBtn.setVisibility(View.GONE);
	        	  holder.received_progressBar.setVisibility(View.GONE);
	        	  holder.itemsDesc.setVisibility(View.VISIBLE);
	          }
         
	          
	          
	          final AbDownloadProgressListener mDownloadProgressListener = new AbDownloadProgressListener() {
					//实时获知文件已经下载的数据长度
					@Override
					public void onDownloadSize(final long size) {
						if(mDownFile.getTotalLength()==0){
							return;
						}
						final int c = (int)(size*100/mDownFile.getTotalLength());
		        		if(c!=holder.progress.getProgress()){
		        			holder.progress.post(new Runnable(){
								@Override
								public void run() {
									holder.progress.setProgress(c);
				    				holder.received_progress_percent.setText(c+"%");
				    				holder.received_progress_number.setText(AbStrUtil.getSizeDesc(size)+"/"+AbStrUtil.getSizeDesc(mDownFile.getTotalLength()));
								}
		        			});
		        		}
						if(mDownFile.getTotalLength() == size){
							if(true)Log.d(TAG, "下载完成:"+size);
							mDownFile.setState(Constant.downloadComplete);
			        		//下载完成		
							holder.progress.post(new Runnable(){
								@Override
								public void run() {
									notifyDataSetChanged();
								}
		        			});
							
		        		}
					}
			  };
	          
	          //处理按钮事件
	          holder.operateBtn.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
							//无sd卡
							AbToastUtil.showToast(mContext,"没找到存储卡");
							return;
						}
						
						if(mDownFile.getState() == Constant.undownLoad || mDownFile.getState() == Constant.downLoadPause){
				            //下载
							
							holder.itemsDesc.setVisibility(View.GONE);
				        	holder.received_progressBar.setVisibility(View.VISIBLE);
				        	holder.operateBtn.setBackgroundResource(R.drawable.view_ks);
				        	mDownFile.setState(Constant.downInProgress);
				        	AbTask mAbTask = new AbTask();
							final AbTaskItem item = new AbTaskItem();
							item.setListener(new AbTaskListener() {

								@Override
								public void update() {
								}

								@Override
								public void get() {
									try {
										//检查文件总长度
										int totalLength = AbFileUtil.getContentLengthFromUrl(mDownFile.getDownUrl());
										mDownFile.setTotalLength(totalLength);
										//开始下载文件
										AbFileDownloader loader = new AbFileDownloader(mContext,mDownFile,1);
										mFileDownloaders.put(mDownFile.getDownUrl(), loader);
										loader.download(mDownloadProgressListener);
									} catch (Exception e) {
										e.printStackTrace();
									}
							  };
							});
							mAbTask.execute(item);
							
				        	
						}else if(mDownFile.getState()==Constant.downInProgress){
							//暂停
							holder.operateBtn.setBackgroundResource(R.drawable.view_zt);
							mDownFile.setState(Constant.undownLoad);
							AbFileDownloader mFileDownloader = mFileDownloaders.get(mDownFile.getDownUrl());
							//释放原来的线程
							if(mFileDownloader!=null){
								mFileDownloader.setFlag(false);
								AbDownloadThread mDownloadThread = mFileDownloader.getThreads();
								if(mDownloadThread!=null){
									mDownloadThread.setFlag(false);
									mFileDownloaders.remove(mDownFile.getDownUrl());
									mDownloadThread = null;
								}
								mFileDownloader = null;
							}
						}else if(mDownFile.getState()==Constant.downloadComplete){
							//删除
						
							mDownFile.setState(Constant.undownLoad);
							
							notifyDataSetChanged();
						}
						
					}
				});
  	/*	if (isCheckMap.containsKey(mht.get_ID()) && isCheckMap.get(mht.get_ID())) {
  			cbCheckBox.setChecked(true);
  		} else {
  			cbCheckBox.setChecked(false);
  		}
  		
  		
  		cbCheckBox .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
  			@Override
  			public void onCheckedChanged(CompoundButton buttonView,
  					boolean isChecked) {
  				if (isChecked) {
  					isCheckMap.put(mht.get_ID()+"", true);
				} else {
					isCheckMap.put(mht.get_ID()+"", false);
				}
  			}
  		});
  		*/
  		
  	
          }
          
          return convertView;
    }

	private String get(String time) {
		// TODO Auto-generated method stub
		//定义时间格式
		 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
		//取的两个时间
		Date dt=new Date();
		 
		//透过SimpleDateFormat的format方法将Date转为字符串
		 
		String dts=sdf.format(dt);
		Date dt1 = null;
		Date dt2 = null;
		try {
			dt1 = sdf.parse(time);
			dt2 =sdf.parse(dts);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//取得两个时间的Unix时间
		 
		Long ut1=dt1.getTime();
		Long ut2=dt2.getTime();
		//相减获得两个时间差距的毫秒 
		Long timeP=ut2-ut1;//毫秒差 
		Long sec=timeP/1000;//秒差
		Long min=timeP/1000/60;//分差
		Long hr=timeP/1000/60/60;//时差
		Long day=timeP/1000/60/60/24;//日差
		Long month =day/30;
		Long year =day/30/12;
		String age = null;
		if(day-30<0)
			age=day.toString()+"天前";
		else if(month-12<0)
			age =month.toString()+"月前";
		else 
			age =year.toString()+"年前";
		return age;
	}
	/**
	 * 描述：释放线程
	 */
	public void releaseThread() {
		 Iterator it = mFileDownloaders.entrySet().iterator();   
		 AbFileDownloader mFileDownloader = null;
		 while (it.hasNext()) {
		    Map.Entry e = (Map.Entry) it.next(); 
		    mFileDownloader = (AbFileDownloader)e.getValue();
		    //System.out.println("Key: " + e.getKey() + "; Value: " + e.getValue());   
		    if(mFileDownloader!=null){
		    	mFileDownloader.setFlag(false);
		    	AbDownloadThread mDownloadThread = mFileDownloader.getThreads();
				if(mDownloadThread!=null){
					mDownloadThread.setFlag(false);
					mDownloadThread = null;
				}
				mFileDownloader = null;
			}
		 }   
	}
	public class ViewHolder {
		public ImageView itemsIcon;
		public TextView itemsTitle;
		public TextView itemsDesc;
		public Button operateBtn;
		public ProgressBar progress;
		public TextView received_progress_percent;
		public TextView received_progress_number;
		public RelativeLayout received_progressBar;
	}
}
