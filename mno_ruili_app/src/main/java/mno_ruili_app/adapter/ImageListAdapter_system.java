package mno_ruili_app.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mno.ruili_app.R;
import mno_ruili_app.entity.Message;
import mno_ruili_app.entity.Message2;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.webpost;
import mno_ruili_app.net.RequestType.Type;

import com.ab.image.AbImageLoader;
import com.ab.util.AbViewHolder;
import com.android.volley.toolbox.NetworkImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageListAdapter_system extends  BaseAdapter{
	
	private static String TAG = "ImageListAdapter";
	//private static final boolean D = Constant.DEBUG;
  
	private Context mContext;
	//xml转View对象
    private LayoutInflater mInflater;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private List<Message2> mData;
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
    public ImageListAdapter_system(Context context, List<Message2> data,
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
                           
          TextView tv_title = AbViewHolder.get(convertView, mTo[0]);
          NetworkImageView iv_image = AbViewHolder.get(convertView, mTo[1]); 
          iv_image.setDefaultImageResId(R.drawable.image_empty);
          iv_image.setErrorImageResId(R.drawable.image_error);
          TextView tv_content = AbViewHolder.get(convertView, mTo[2]);
          TextView tv_message = AbViewHolder.get(convertView, mTo[3]);               
		  //获取该行的数据        
          final Message2 msg_system= (Message2)mData.get(position);
          
          String title=msg_system.getTitle(); 
          if(title.length()>15){
			  title=title.substring(0, 15)+"..."; 
          }else{
        	  tv_title.setText(title);
          }
          
  		  String isView=msg_system.getIsView();
		  if(isView.equals("0")){
			  tv_message.setVisibility(View.VISIBLE);
		  }else{
			  tv_message.setVisibility(View.INVISIBLE);
		  }  		  
  		  
  		  String content=msg_system.getContent();
  		  if(content.length()>20){
  			  content=content.substring(0, 18)+"..."; 
  		  }else{
  			  tv_content.setText(content);                   
  		  }
  		  
          String image=msg_system.getImage(); 
          String imageUrl =RequestType.getWebUrl_(image);
          iv_image.setImageUrl(imageUrl, webpost.getImageLoader());
          //answerTotal.setText("回答："+mwd.getanswerTotal());          
          return convertView;
    }
    //暂时用不到
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
		if(min-60<0)
			age=min.toString()+"分钟前";
		else if(hr-24<0)
			age=hr.toString()+"小时前";
		else if(day-30<0)
			age=day.toString()+"天前";
		else if(month-12<0)
			age =month.toString()+"月前";
		else 
			age =year.toString()+"年前";
		return age;
	}
    
}

