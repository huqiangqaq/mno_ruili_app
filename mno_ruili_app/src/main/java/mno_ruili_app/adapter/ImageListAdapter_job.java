package mno_ruili_app.adapter;

import java.util.List;

import mno.ruili_app.R;
import mno.ruili_app.ct.FlowLayout;
import mno.ruili_app.ct.RoundImageView;
import mno.ruili_app.ct.TagAdapter;
import mno.ruili_app.ct.TagFlowLayout;
import mno_ruili_app.entity.Job;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webpost;

import com.ab.util.AbViewHolder;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ImageListAdapter_job extends  BaseAdapter{
	
	private static String TAG = "ImageListAdapter";
	//private static final boolean D = Constant.DEBUG;
  
	private Context mContext;
	//xml转View对象
    private LayoutInflater mInflater;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private List<Job> mData;
    //Map中的key
    private String[] mFrom;
    //view的id
    private int[] mTo;
   /**
    * 构造方法
    * @param context
    * @param data 列表展现的数据
    * @param resource 单行的布局
    * @param from Map中的key
    * @param to view的id
    */
    public ImageListAdapter_job(Context context, List<Job> data,
            int resource, String[] from, int[] to){
    	this.mContext = context;
    	this.mData = data;
    	this.mResource = resource;
    	this.mFrom = from;
    	this.mTo = to;
        //用于将xml转为View
        this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
	      convertView = mInflater.inflate(mResource, parent, false);
                           
          TextView tv_jobName = AbViewHolder.get(convertView, mTo[0]);
          TextView tv_salary= AbViewHolder.get(convertView, mTo[1]);
          TextView tv_workTime = AbViewHolder.get(convertView, mTo[2]); 
          TextView tv_education = AbViewHolder.get(convertView, mTo[3]);  
          TextView tv_workPlace = AbViewHolder.get(convertView, mTo[4]);
          final TagFlowLayout tfl_jobMsg = AbViewHolder.get(convertView, mTo[5]);
          RoundImageView iv_jobImage = AbViewHolder.get(convertView, mTo[6]);
          //iv_jobImage.setDefaultImageResId(R.drawable.my_up);
          //iv_jobImage.setErrorImageResId(R.drawable.image_error);
          TextView tv_nickName = AbViewHolder.get(convertView, mTo[7]);
          TextView tv_workLevel = AbViewHolder.get(convertView, mTo[8]);
          TextView tv_companyName = AbViewHolder.get(convertView, mTo[9]);
          TextView tv_updateTime = AbViewHolder.get(convertView, mTo[10]);
		  //获取该行的数据        
          final Job job= (Job)mData.get(position);
          
          String jobName=job.getJobName();
          if(jobName.length()>15){
        	  jobName=jobName.substring(0, 15)+"..."; 
          }else{
        	  tv_jobName.setText(jobName);
          } 
          
  		  String salary=job.getSalary();
  		  tv_salary.setText(salary);
  		  
  		  String workTime=job.getWorkTime();
  		  tv_workTime.setText(workTime);
  		  
  		  String edcation=job.getEducation();
  		  tv_education.setText(edcation);
  		  
  		  String workPlace=job.getWorkPlace();
  		  tv_workPlace.setText(workPlace);
  		  //亮点名字
		  String[] jobMsg=job.getJobMsg();
  		  tfl_jobMsg.setAdapter(new TagAdapter<String>(jobMsg) {

			@Override
			public View getView(FlowLayout parent, int position, String t) {
				TextView tv_jobMsg=(TextView)mInflater.inflate(R.layout.tv, tfl_jobMsg,false);
				tv_jobMsg.setText(t);
				return tv_jobMsg;
			}
  		  });
  		  //图片路径
  		  String jobImagePath=job.getJobImagePath();
  		  if(jobImagePath.equals("null")){
			  iv_jobImage.setDefaultImageResId(R.drawable.my_up);
		  }else{
			  String imageUrl = RequestType.getWebUrl_(jobImagePath);
			  iv_jobImage.setImageUrl(imageUrl, webpost.getImageLoader());
		  }
  		  
  		  String nickName=job.getNickName();
  		 if(nickName.equals("null")){
		    	nickName="";
		    }
  		  tv_nickName.setText(nickName);
  		  
  		  String workLevel=job.getWorkLevel();
  		if(workLevel.equals("null")){
	    	workLevel="";
	    }
  		  tv_workLevel.setText(workLevel);
  		  
  		  String companyName=job.getCompanyName();
  		  tv_companyName.setText(companyName);
  		  
          String updateTime=job.getUpdateTime();      
          tv_updateTime.setText( updateTime);          
          return convertView;
    }
}

