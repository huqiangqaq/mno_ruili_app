package mno_ruili_app.adapter;




import java.util.ArrayList;
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
import mno.ruili_app.main.NeighborFragment;
import mno.ruili_app.my.mycheckcode;
import mno_ruili_app.adapter.ImageListAdapter_mykc.ViewHolder;
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
public class ImagebtListAdapter extends BaseAdapter{
	
	private static String TAG = "ImageListAdapter";
	//private static final boolean D = Constant.DEBUG;
  
	private Context mContext;
	//xml转View对象
    private LayoutInflater mInflater;
    //单行的布局
    private int mResource;
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
    public ImagebtListAdapter(Context context, ArrayList<zb_tv> mTvList,
            int resource, String[] from, int[] to){
    	this.mContext = context;
    	this.mData = mTvList;
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
   /* LinearLayout zb_bt,zb_item_yg;
    TextView zb_color,zb_bt_text,zb_bt_time,itemstext2,zb_item_yg_title,zb_item_yg_time;
    ImageView zb_sc;
    RelativeLayout itemsImage;*/
    webhandler handler_2,handler_3;
    
    static class ViewHolder {
   		NetworkImageView itemsIcon;
   		TextView itemstext,tv_vipp,tv_vipv;
   		TextView itemstext2,isend;
   		TextView itemstext3;
   		RelativeLayout zb_item_yg;
   	    LinearLayout zb_bt,last;
   	    LinearLayout itemsImage;
   		ImageView zb_sc;
   	  TextView zb_color,zb_bt_text,zb_bt_time,zb_item_yg_title,zb_item_yg_time;
   	}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
    	 final ViewHolder holder;
         if(convertView == null){
	           //使用自定义的list_items作为Layout      	 
	           convertView = mInflater.inflate(mResource, parent, false);
	           holder = new ViewHolder();
			   holder.itemsIcon = ((NetworkImageView) convertView.findViewById(mTo[0])) ;
			   holder.itemsIcon.setDefaultImageResId(R.drawable.image_empty);
			   holder.itemsIcon.setErrorImageResId(R.drawable.image_error);
			   holder.zb_item_yg =((RelativeLayout) convertView.findViewById( R.id.zb_item_yg)) ;
			   holder.zb_color =((TextView) convertView.findViewById( R.id.zb_color)) ;
			   holder.zb_bt =((LinearLayout) convertView.findViewById(R.id.zb_bt)) ;
			   holder.last =((LinearLayout) convertView.findViewById(R.id.last)) ;
			   holder.zb_bt_text =((TextView) convertView.findViewById(R.id.zb_bt_text)) ;
			   holder.zb_bt_time =((TextView) convertView.findViewById(  R.id.zb_bt_time)) ;
			   holder.itemsImage =((LinearLayout) convertView.findViewById( R.id.itemsImage)) ;
			   holder.isend=((TextView) convertView.findViewById(  R.id.isend)) ;
			   
			   holder.zb_sc =((ImageView) convertView.findViewById( R.id.zb_sc)) ;
			   holder.itemstext2 =((TextView) convertView.findViewById(  R.id.itemstext2)) ;
			   
			   holder.zb_item_yg_title =((TextView) convertView.findViewById( R.id.zb_item_yg_title)) ;
			   holder.zb_item_yg_time =((TextView) convertView.findViewById(  R.id.zb_item_yg_time)) ;
			   holder.tv_vipp =((TextView) convertView.findViewById(  R.id.tv_vipp)) ;
			   holder.tv_vipv =((TextView) convertView.findViewById(  R.id.tv_vipv)) ;
			   convertView.setTag(holder);
	      }else{
	     	   holder = (ViewHolder) convertView.getTag();
	      }      
          final zb_tv mtv = (zb_tv)mData.get(position);
          String imageUrl = mtv.getImg();
          String type = mtv.getType();
          String time = mtv.getMs();
          String isMem=mtv.getIsMembers();
          if(mtv.getisCollect().equals("1"))
        	  holder.zb_sc.setBackgroundResource(R.drawable.zb_tx);
          holder.zb_bt.setVisibility(View.GONE);
          holder.zb_item_yg.setVisibility(View.GONE);
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
			holder.zb_sc.setOnClickListener(new OnClickListener() {
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
		 				
		    			 holder.zb_sc.setBackgroundDrawable(drawable);
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
		    			 holder.zb_sc.setBackgroundDrawable(drawable);
	    			     mtv.setisCollect("0");
		    			 notifyDataSetChanged();
	    			 }
					//MessageBox.Show(mContext, mtv.getid());
				}
	        });  
			//1.直播预告
			if(type.equals("1")){ 
				  if(isMem.equals("1")){
		        	   holder.tv_vipp.setVisibility(View.VISIBLE);
				  }else{
					   holder.tv_vipp.setVisibility(View.INVISIBLE);
				  } 
	        	  holder.zb_bt_time.setVisibility(View.VISIBLE);
	        	  holder.itemsImage.setVisibility(View.GONE);
	        	  holder.zb_bt.setVisibility(View.VISIBLE);
	        	  holder.zb_bt_text.setText("直播预告");
	        	  holder.zb_color.setBackgroundColor(android.graphics.Color.parseColor("#f39801"));
	        	  holder.zb_item_yg.setVisibility(View.VISIBLE);
	        	  holder.zb_item_yg_title.setText(mtv.getName());
	        	  holder.zb_item_yg_time.setText(time.substring(10));
	        	  holder.zb_bt_time.setText(time.substring(0, 10));    	        	  
	          }else if(type.equals("2")){//今日直播
	        	  if(isMem.equals("1")){
		        	   holder.tv_vipv.setVisibility(View.VISIBLE);
				  }else{
					   holder.tv_vipv.setVisibility(View.INVISIBLE);
				  }
	        	  holder.zb_bt_time.setText("");
	        	  holder.itemsImage.setVisibility(View.VISIBLE);
	        	  holder.itemstext2.setText(mtv.getName());
	        	  holder.zb_bt.setVisibility(View.VISIBLE);
	        	  holder.zb_bt_text.setText("今日直播");
	        	  holder.zb_color.setBackgroundColor(android.graphics.Color.parseColor("#3579b8"));
	        	  holder.last.setVisibility(View.GONE);
	        	  if(mtv.getisEnd().equals("1"))
	        		  holder.isend.setVisibility(View.VISIBLE);
	        	  else
	        		  holder.isend.setVisibility(View.GONE);        		  
	          }else if(type.equals("3")){
	        	  if(isMem.equals("1")){
		        	   holder.tv_vipv.setVisibility(View.VISIBLE);
				  }else{
					   holder.tv_vipv.setVisibility(View.INVISIBLE);
				  }
	        	  holder.zb_bt_time.setText("");
	        	  holder.itemsImage.setVisibility(View.VISIBLE);
	        	  holder.itemstext2.setText(mtv.getName());
	        	  holder.zb_bt.setVisibility(View.VISIBLE);
	        	  holder.zb_bt_text.setText("往期内容");
	        	  holder.zb_color.setBackgroundColor(android.graphics.Color.parseColor("#32b16c"));
	        	  holder.last.setVisibility(View.VISIBLE);
	          }else if(type.equals("4")){
	        	  if(isMem.equals("1")){
		        	   holder.tv_vipv.setVisibility(View.VISIBLE);
				  }else{
					   holder.tv_vipv.setVisibility(View.INVISIBLE);
				  }
	        	  holder.itemsImage.setVisibility(View.VISIBLE);
	        	  holder.zb_item_yg.setVisibility(View.GONE);
	        	  holder.itemstext2.setText(mtv.getName());
	        	  holder.zb_bt.setVisibility(View.GONE);
	        	  holder.last.setVisibility(View.GONE);
	        	  holder.zb_bt_text.setText("往期内容");
	        	  holder.zb_color.setBackgroundColor(android.graphics.Color.parseColor("#32b16c"));
	          }else if(type.equals("5")){
	        	  if(isMem.equals("1")){
		        	   holder.tv_vipv.setVisibility(View.VISIBLE);
				  }else{
					   holder.tv_vipv.setVisibility(View.INVISIBLE);
				  }
	        	  holder.itemsImage.setVisibility(View.VISIBLE);
	        	  holder.zb_item_yg.setVisibility(View.GONE);
	        	  holder.itemstext2.setText(mtv.getName());
	        	  holder.zb_bt.setVisibility(View.GONE);
	        	  holder.last.setVisibility(View.GONE);
	        	  holder.zb_bt_text.setText("今日直播");
	        	  holder.zb_color.setBackgroundColor(android.graphics.Color.parseColor("#32b16c"));
	        	  if(mtv.getisEnd().equals("1"))
	        		  holder.isend.setVisibility(View.VISIBLE);
	        	  else
	        		  holder.isend.setVisibility(View.GONE);
	          }else{
	        	  holder.zb_bt.setVisibility(View.GONE);
	          }        	  
	          //图片的下载
	          if(!(imageUrl.length()<3))
	          	 holder. itemsIcon.setImageUrl(imageUrl,webpost.getImageLoader());         
	          return convertView;
	    }    
}
