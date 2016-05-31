package mno_ruili_app.adapter;




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.ct.RoundImageView;
import mno.ruili_app.ct.RoundimgView;
import mno.ruili_app.my.mycheckcode;
import mno_ruili_app.home.home_video;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.webpost;
import mno_ruili_app.net.RequestType.Type;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.image.AbImageLoader;
import com.ab.util.AbViewHolder;
import com.android.volley.toolbox.NetworkImageView;

/**
 * © 2012 amsoft.cn
 * 名称：MyListViewAdapter
 * 描述：在Adapter中释放Bitmap
 * @author 还如一梦中
 * @date 2011-12-10
 * @version
 */
public class item_ImagezbrlListAdapter extends BaseAdapter{
	
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
    public item_ImagezbrlListAdapter(Context context, ArrayList<zb_tv> mTvList ){
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
  
    ImageView zb_sc;
    TextView  zb_zj,zb_title,zb_time,tv_vipp;
    
    webhandler handler_2,handler_3;
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
          if(convertView == null){
	           //使用自定义的list_items作为Layout
	           convertView = mInflater.inflate(R.layout.item_item_list_zbrl, parent, false);
          }
          zb_sc= AbViewHolder.get(convertView, R.id.zb_sc);
          zb_zj= AbViewHolder.get(convertView, R.id.zb_zj);
          zb_title= AbViewHolder.get(convertView, R.id.zb_title);
          zb_time= AbViewHolder.get(convertView, R.id.zb_time);          
          tv_vipp=AbViewHolder.get(convertView, R.id.tv_vipp);
          final zb_tv mtv = (zb_tv)mData.get(position);
   
          String zb_time_ = mtv.gettime();
          String zb_title_ = mtv.getName();
          String zb_zj_ = mtv.getzj();
          String isMem=mtv.getIsMembers();
         
          if(mtv.getisCollect().equals("1"))
        	  zb_sc.setBackgroundResource(R.drawable.zb_tx);
          else
        	  zb_sc.setBackgroundResource(R.drawable.zb_tx2);
          
          if(isMem.equals("1")){
        	   tv_vipp.setVisibility(View.VISIBLE);
		   }else{
			   tv_vipp.setVisibility(View.INVISIBLE);
		  }        
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
          zb_sc.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(!appuser.getInstance().IsLogin())
		   	    	 {
		     			 appuser.getInstance().Login(mContext);
		     			 return;
		   	    	 }
					 if(mtv.getisCollect().equals("0"))
	    			 {
						 Map<String, String> params = new HashMap<String, String>();
		    			 params.put("forwhat","coll");
		    		     params.put("likeId",mtv.getid());
		    		     params.put("type","course");
		    			 handler_2.SetRequest(new RequestType("2",Type.addLike),params);
		    			 Drawable drawable=mContext.getResources().getDrawable(R.drawable.zb_tx);
		 				
		    			 zb_sc.setBackgroundDrawable(drawable);
		    			 mtv.setisCollect("1");
		    			 notifyDataSetChanged();
	    			 }
					 else if(mtv.getisCollect().equals("1"))
	    			 {
	    				 Map<String, String> params2 = new HashMap<String, String>();
	    				 params2.put("method","course");
	    			     params2.put("ids",mtv.getid());
	    			     handler_3.SetRequest(new RequestType("4",Type.delMyData),params2);
	    			     Drawable drawable=mContext.getResources().getDrawable(R.drawable.zb_tx2);
		    			 zb_sc.setBackgroundDrawable(drawable);
	    			     mtv.setisCollect("0");
		    			 notifyDataSetChanged();
	    			 }
				}
        });
          
          if(mtv.getisEnd().equals("1"))
        	  zb_sc.setVisibility(View.GONE);
          else
        	  zb_sc.setVisibility(View.VISIBLE);        	         	  
        	  zb_zj.setText(zb_zj_);
        	  zb_title.setText(zb_title_);
        	  zb_time.setText(zb_time_);       	 
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
