package mno_ruili_app.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import mno.ruili_app.R;
import mno.ruili_app.ct.MessageBox;
import mno_ruili_app.adapter.ImageGridAdapter.ViewHolder;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webpost;

import com.ab.image.AbImageLoader;
import com.ab.util.AbViewHolder;
import com.android.volley.toolbox.NetworkImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImageListAdapter_myht extends  BaseAdapter{
	
	private static String TAG = "ImageListAdapter";
	//private static final boolean D = Constant.DEBUG;
  
	private Context mContext;
	//xml转View对象
    private LayoutInflater mInflater;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private List<ht> mData;
    //Map中的key
    private String[] mFrom;
    //view的id
    private int[] mTo;
    //图片下载器
  
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
	private   Map<String, Boolean> isCheckMap = new HashMap<String, Boolean>();
	
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
    public ImageListAdapter_myht(Context context, List<ht> data,
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
    static class ViewHolder {
    	TextView myReply;
    	TextView title;
		TextView replyTotal;
		TextView ReplyTime;
		CheckBox cbCheckBox;
		LinearLayout zx;
		LinearLayout kc;
		NetworkImageView itemsIcon;
   		TextView itemstext;
   		TextView itemstext2;
   		TextView Rcontent;
	}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
    	ViewHolder holder = null;
         if(convertView == null){
	           //使用自定义的list_items作为Layout
        	  
        	   convertView = mInflater.inflate(mResource, parent, false);
			   holder = new ViewHolder();
			   holder.myReply = ((TextView) convertView.findViewById(mTo[0])) ;
			   holder.title=((TextView) convertView.findViewById(mTo[1])) ;
			   holder.replyTotal =((TextView) convertView.findViewById(mTo[2])) ;
			   holder.ReplyTime =((TextView) convertView.findViewById(mTo[3])) ;
			   holder.zx=((LinearLayout) convertView.findViewById(R.id.zx)) ;
			   holder.kc=((LinearLayout) convertView.findViewById(R.id.kc)) ;
			   holder.itemsIcon = ((NetworkImageView) convertView.findViewById(R.id.itemsIcon)) ;
	           holder.itemsIcon.setDefaultImageResId(R.drawable.image_empty);
			   holder.itemsIcon.setErrorImageResId(R.drawable.image_error);
			   holder.itemstext =((TextView) convertView.findViewById((R.id.itemstext))) ;
			   holder.itemstext2 =((TextView) convertView.findViewById((R.id.itemstext2))) ;
			  
			   holder.cbCheckBox=((CheckBox)convertView.findViewById( R.id.cbCheckBox));
			   convertView.setTag(holder);
	          
          
    }else{
    	 
 	   holder = (ViewHolder) convertView.getTag();
      }

          
          
        
          
          
          if (isCheck) {
        	  holder.cbCheckBox.setVisibility(View.VISIBLE);
  		} else {
  			holder.cbCheckBox.setVisibility(View.GONE);
  		}
          
        /*  //cbCheckBox.setChecked(false); R.id.myReply ,R.id.title,R.id.replyTotal,R.id.ReplyTime
          TextView myReply = AbViewHolder.get(convertView, mTo[0]);
          TextView title = AbViewHolder.get(convertView, mTo[1]);
          TextView replyTotal= AbViewHolder.get(convertView, mTo[2]);
          TextView ReplyTime = AbViewHolder.get(convertView,mTo[3]);
		  //获取该行的数据
*/        
          final ht mht= (ht)mData.get(position);
          
  		if (isCheckMap.containsKey(mht.getid()) && isCheckMap.get(mht.getid())) {
  			holder.cbCheckBox.setChecked(true);
  		} else {
  			holder.cbCheckBox.setChecked(false);
  		}
  		
  		
  		holder.cbCheckBox .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
  			@Override
  			public void onCheckedChanged(CompoundButton buttonView,
  					boolean isChecked) {
  				if(!buttonView.isPressed())return;
  				if (isChecked) {
  					isCheckMap.put(mht.getid(), true);
  					//MessageBox.Show(mContext, "true="+mht.getid());
  					//holder.cbCheckBox.setChecked(true);
  					//notifyDataSetChanged();
				} else {
					isCheckMap.put(mht.getid(), false);
					//MessageBox.Show(mContext, "false="+mht.getid());
					//holder.cbCheckBox.setChecked(false);
  					//notifyDataSetChanged();
				}
  				//notifyDataSetChanged();
  			}
  		});
  		String titletext=mht.gettitle();
  		if(titletext.length()>22)
  		{
  			titletext=titletext.substring(0, 20)+"...";
  		}
  		if(mht.getwhere().equals("0"))
  		{
  			holder.kc.setVisibility(View.GONE);
  			holder.zx.setVisibility(View.VISIBLE);
  			holder.title.setText(titletext);
  		}else
  		{
  			String imageUrl =RequestType.getWebUrl_(mht.getcoverImg());
  			holder.itemstext2.setText(mht.getRcontent());
  			holder.itemsIcon.setImageUrl(imageUrl,webpost.getImageLoader());
  			holder.itemstext.setText(titletext);
  			holder.kc.setVisibility(View.VISIBLE);
  			holder.zx.setVisibility(View.GONE);
  		}
  		
  		String time=mht.getReplyTime();
        
         // long times = System.currentTimeMillis()-time;
  		holder.ReplyTime.setText( get(time));
          //#16c32c
  		holder.replyTotal.setText("回答："+mht.getreplyTotal());
  		holder.myReply.setText(mht.getmyReply());
          
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
		{
			if(min<3)
				age="刚刚";
			else
			age=min.toString()+"分钟前";
		}
			
		else if(hr-24<0)
			age=hr.toString()+"小时前";
		else if(day-30<0)
			age=(day+1)+"天前";
		else if(month-12<0)
			age =month.toString()+"月前";
		else 
			age =year.toString()+"年前";
		return age;
	}
    
}
