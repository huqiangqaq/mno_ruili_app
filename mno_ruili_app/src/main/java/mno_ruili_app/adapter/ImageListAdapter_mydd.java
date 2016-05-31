package mno_ruili_app.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mno.ruili_app.R;
import mno_ruili_app.net.RequestType;

import com.ab.image.AbImageLoader;
import com.ab.util.AbViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageListAdapter_mydd extends  BaseAdapter{
	
	private static String TAG = "ImageListAdapter";
	//private static final boolean D = Constant.DEBUG;
  
	private Context mContext;
	//xml转View对象
    private LayoutInflater mInflater;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private List<mywd> mData;
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
			isCheckMap.put(mData.get(i).getid(), bool);
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
    public ImageListAdapter_mydd(Context context, List<mywd> data,
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
	           convertView = mInflater.inflate(mResource, parent, false);
         /* }*/
          
          
          
          CheckBox cbCheckBox=AbViewHolder.get(convertView, R.id.cbCheckBox);
          
          
          if (isCheck) {
        	  cbCheckBox.setVisibility(View.VISIBLE);
  		} else {
  			  cbCheckBox.setVisibility(View.GONE);
  		}
          
          //cbCheckBox.setChecked(false);
          TextView wdtitle = AbViewHolder.get(convertView, mTo[0]);
          TextView wdcontent = AbViewHolder.get(convertView, mTo[1]);
          TextView wdname = AbViewHolder.get(convertView, mTo[2]);
          TextView wdtime= AbViewHolder.get(convertView, mTo[3]);
          TextView answerTotal = AbViewHolder.get(convertView,mTo[4]);
          ImageView  tsimg= AbViewHolder.get(convertView,R.id.tsimg);
		  //获取该行的数据
        
          final mywd mwd= (mywd)mData.get(position);
         
  		if (isCheckMap.containsKey(mwd.getid()) && isCheckMap.get(mwd.getid())) {
  			cbCheckBox.setChecked(true);
  		} else {
  			cbCheckBox.setChecked(false);
  		}
  		try {
  		if(mwd.getredPoint().equals("1"))
  		{
  			tsimg.setVisibility(View.VISIBLE);
  		}
  		else
  			tsimg.setVisibility(View.GONE);
  		}   catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		   }
     
  		cbCheckBox .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
  			@Override
  			public void onCheckedChanged(CompoundButton buttonView,
  					boolean isChecked) {
  				if (isChecked) {
  					isCheckMap.put(mwd.getid(), true);
				} else {
					isCheckMap.put(mwd.getid(), false);
				}
  			}
  		});
  		String name=mwd.gettitler();
  		String content=(mwd.getcontent());
  		if(name.length()>15)
  			name=name.substring(0, 10)+"...";
  		if(content.length()>30)
  			content=content.substring(0, 20)+"...";
  		if(name.equals("title")||name.equals("睿立培训"))
  			name="";
  		  wdtitle.setText(name);
          wdcontent.setText(content);
          String sname=mwd.getstatusName();
          if(sname.length()<=0)
          {
        	  wdname.setVisibility(View.INVISIBLE);
          }
          if(sname.equals("待解决"))
          {
        	  wdname.setVisibility(View.VISIBLE);
        	  wdname.setText(sname);
        	  wdname.setTextColor(android.graphics.Color.parseColor("#ff5a00"));
          }
          else
          {
        	  wdname.setVisibility(View.VISIBLE);
        	  wdname.setText(sname);
        	  wdname.setTextColor(android.graphics.Color.parseColor("#16c32c"));
          }
          String time=mwd.create_at();
        
         // long times = System.currentTimeMillis()-time;
          wdtime.setText( get(time));
          //#16c32c
          answerTotal.setText("回答："+mwd.getanswerTotal());

          
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
