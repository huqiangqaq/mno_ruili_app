package mno_ruili_app.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mno.ruili_app.R;
import mno.ruili_app.ct.MessageBox;
import mno_ruili_app.net.webhandler;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.image.AbImageLoader;
import com.ab.util.AbViewHolder;

/**
 * © 2012 amsoft.cn
 * 名称：MyListViewAdapter
 * 描述：在Adapter中释放Bitmap
 * @author 还如一梦中
 * @date 2011-12-10
 * @version
 */
public class ImagezbrlListAdapter extends BaseAdapter{
	
	private static String TAG = "ImageListAdapter";
	//private static final boolean D = Constant.DEBUG;
  
	private Context mContext;
	//xml转View对象
    private LayoutInflater mInflater;
    //单行的布局

    //列表展现的数据
    private List mData;
    //Map中的key
    private String[] mFrom;
    //view的id
    private int[] mTo;
    //图片下载器
    private AbImageLoader mAbImageLoader = null;
    
   /**
    * 构造方法
    * @param context
    * @param mTvList 列表展现的数据
    * @param resource 单行的布局
    * @param from Map中的key
    * @param to view的id
    */
    public ImagezbrlListAdapter(Context context, ArrayList<zb_tv> mTvList ){
    	this.mContext = context;
    	this.mData = mTvList;
   
        //用于将xml转为View
        this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //图片下载器
     
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
    LinearLayout zb_rq;
    TextView zb_day,zb_month,zb_week;
    //ImageView zb_sc;TextView  zb_zj,zb_title,zb_time
    ListView mListView;
    webhandler handler_2,handler_3;
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
          if(convertView == null){
	           //使用自定义的list_items作为Layout
	           convertView = mInflater.inflate(R.layout.item_list_zbrl, parent, false);
          }
          mListView = AbViewHolder.get(convertView,R.id.mListView);
          zb_day= AbViewHolder.get(convertView, R.id.zb_day);
          zb_rq = AbViewHolder.get(convertView, R.id.zb_rq);
          zb_month= AbViewHolder.get(convertView, R.id.zb_month);
          zb_week= AbViewHolder.get(convertView, R.id.zb_week);
          final zb_tv mtv = (zb_tv)mData.get(position);
          String zb_month_ = mtv.getmonth();
          String zb_day_ = mtv.getday();
          String zb_week_ = mtv.getweek();
          JSONArray daydata=mtv.getdaydata();
          
            int length=daydata.length();
            ArrayList<zb_tv> mTvList= new ArrayList<zb_tv>();
            for(int i=0;i<= length-1; i++)
		    {
		    	JSONObject type_json;
				try {
					type_json = daydata.getJSONObject(i);				
					String begin="",end="",datatime="",isEnd="",id="",isCollect="";
					String title=type_json.getString("title");
					try {
						datatime=type_json.getString("beginTime");
					    begin=type_json.getString("beginTime").substring(11, 16);
					} catch (Exception e) {
						 begin="00:00";
					}
					String isMem=type_json.getString("isMembers");
					isEnd=type_json.getString("isEnd");
					String zj=type_json.getString("teacher");
					id=type_json.getString("id");
					isCollect=type_json.getString("isCollect");				
					final zb_tv mdt = new zb_tv(id, title,zj,datatime, isCollect,isEnd,isMem);
					mTvList.add(mdt);				
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		  }			
          item_ImagezbrlListAdapter mykcAdapter =new item_ImagezbrlListAdapter(mContext, mTvList);
 	      mListView.setAdapter(mykcAdapter); 	      
          zb_rq.setVisibility(View.GONE);      
          handler_2 = new webhandler(){
  			@Override
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
				String ms=mess;
				ms=ms+"";
				MessageBox.Show(mContext,mess);
			}	
			@Override
  			public void OnResponse(JSONObject response) {
  				// TODO Auto-generated method stub
				try {					
					String mess = response.getString("message");					
					if(mess.equals("null") == false && mess.length() > 1)
					{
						Toast.makeText(mContext, "已预约成功", Toast.LENGTH_SHORT).show();
					}					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
  			}  			
  			};
  			handler_3 = new webhandler(){
  				@Override
  				public void OnError(int code, String mess) {
  					// TODO Auto-generated method stub
  					super.OnError(code, mess);
  					String ms=mess;
  					ms=ms+"";
  					MessageBox.Show(mContext,mess);
  				}
				@Override
				public void OnResponse(JSONObject response) {					
					Toast.makeText(mContext, "已取消预约", Toast.LENGTH_SHORT).show();				
				}
			};  	           
    	   if(mtv.getType().equals("0")){
        	 zb_rq.setVisibility(View.VISIBLE); 
          }else{
    		  zb_rq.setVisibility(View.GONE);
    	  }        
    	  zb_month.setText(zb_month_);
    	  zb_day.setText(zb_day_);
    	  zb_month.setText(zb_month_);
    	  zb_week.setText(zb_week_);        	         
          return convertView;
    }
	public static int getGapCount( String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date dt=new Date();
		Date dt1 = null;
		Date dt2 = null;
		String dts=sdf.format(dt);
        Calendar fromCalendar = Calendar.getInstance();  
        
        Calendar toCalendar = Calendar.getInstance();  
        //dt1 = sdf.parse(endDate);
        try {
			fromCalendar.setTime(sdf.parse(dts));
			
			    fromCalendar.set(Calendar.HOUR_OF_DAY, 0);  
		        fromCalendar.set(Calendar.MINUTE, 0);  
		        fromCalendar.set(Calendar.SECOND, 0);  
		        fromCalendar.set(Calendar.MILLISECOND, 0);  
		  
		        toCalendar.setTime( sdf.parse(endDate));  
		        toCalendar.set(Calendar.HOUR_OF_DAY, 0);  
		        toCalendar.set(Calendar.MINUTE, 0);  
		        toCalendar.set(Calendar.SECOND, 0);  
		        toCalendar.set(Calendar.MILLISECOND, 0);  
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
     
  
        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
}

}
