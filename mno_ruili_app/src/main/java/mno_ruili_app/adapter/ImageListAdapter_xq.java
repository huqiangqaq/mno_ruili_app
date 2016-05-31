package mno_ruili_app.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.MessageBox;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.RequestType.Type;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.webpost;

import com.android.volley.toolbox.NetworkImageView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ImageListAdapter_xq extends BaseAdapter{
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
    private webhandler handler_2,handler_3;
    
   /**
    * 构造方法
    * @param context
    * @param data 列表展现的数据
    * @param resource 单行的布局
    * @param from Map中的key
    * @param to view的id
    */
    public ImageListAdapter_xq(Context context, List<tv2> data,
            int resource, String[] from, int[] to){
    	this.mContext = context;
    	this.mData = data;
    	this.mResource = resource;
    	this.mFrom = from;
    	this.mTo = to;
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
    static class ViewHolder {
   		NetworkImageView itemsIcon;
   		TextView itemstext;
   		TextView itemstext2;
   		ImageView zb_sc;
   		//TextView itemstext3;
   		
   	}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
    	final ViewHolder holder;  
    	if(convertView == null){
      	  
	           //使用自定义的list_items作为Layout
	           convertView = mInflater.inflate(mResource, parent, false);
	           holder = new ViewHolder();
	           try
	           {
	           holder.itemsIcon = ((NetworkImageView) convertView.findViewById(mTo[0])) ;
	           holder.itemsIcon.setDefaultImageResId(R.drawable.image_empty);
			   holder.itemsIcon.setErrorImageResId(R.drawable.image_error);
	           }
	           catch(Exception e)
	           {}
			 
			   holder.itemstext =((TextView) convertView.findViewById(mTo[1])) ;
			   holder.itemstext2 =((TextView) convertView.findViewById(mTo[2])) ;
			   holder.zb_sc=(ImageView) convertView.findViewById(R.id.zb_sc);
			   
			   //holder.zb_sc=((ImageView) convertView.findViewById(mTo[3]));
			   convertView.setTag(holder);
    	}
    	else{
    		   holder = (ViewHolder) convertView.getTag();
 		}		
			/*
			 * if(mtv.getIsCollect().equals("1"))
        	 		holder. zb_sc.setBackgroundResource(R.drawable.zb_tx);
          		else
        	 		holder. zb_sc.setBackgroundResource(R.drawable.zb_tx2);
			 */
    	
          final tv2 mtv = (tv2)mData.get(position);
          holder.zb_sc.setBackgroundResource(R.drawable.zb_tx2);
          
        //预约直播
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
  		//取消预约
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
  						
  			}};
  	          
          //为小闹钟设置点击事件
          holder.zb_sc.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(!appuser.getInstance().IsLogin())
		   	    	 {
		     			 appuser.getInstance().Login(mContext);
		     			 return;
		   	    	 }
					//预约
					 if(mtv.getIsCollect().equals("0"))
	    			 {
						 Map<String, String> params = new HashMap<String, String>();
		    			 params.put("forwhat","coll");
		    		     params.put("likeId",mtv.getId());
		    		     params.put("type","course");
		    			 handler_2.SetRequest(new RequestType("2",Type.addLike),params);
		    			 Drawable drawable=mContext.getResources().getDrawable(R.drawable.zb_tx);
		 				
		    			 holder.zb_sc.setBackgroundDrawable(drawable);
		    			 mtv.setIsCollect("1");
		    			 notifyDataSetChanged();
	    			 }
					 //取消预约
					 else if(mtv.getIsCollect().equals("1"))
	    			 {
	    				 Map<String, String> params2 = new HashMap<String, String>();
	    				 params2.put("method","course");
	    			     params2.put("ids",mtv.getId());
	    			     handler_3.SetRequest(new RequestType("4",Type.delMyData),params2);
	    			     Drawable drawable=mContext.getResources().getDrawable(R.drawable.zb_tx2);
		    			 holder.zb_sc.setBackgroundDrawable(drawable);
	    			     mtv.setIsCollect("0");
		    			 notifyDataSetChanged();
	    			 }
				}
        });
          /*
           * if(mtv.getIsEnd().equals("1"))
        	  holder.zb_sc.setVisibility(View.GONE);
          else
        	  holder.zb_sc.setVisibility(View.VISIBLE);
           */
        	 
          
          String imageUrl =RequestType.getWebUrl_(mtv.getImg());

          if(mTo.length>2)  
          {
        	  String name=mtv.getName();
              String content=mtv.getMs();
              if(name.length()>15)
              {
              name=name.substring(0, 13)+"...";
              }
              if(content.length()>20)
              {
            	 content=content.substring(0, 18)+"...";
              }
              
        	  holder.itemstext.setText(name);
        	  holder.itemstext2.setText(Html.fromHtml(content).toString());
          }
          try
          {
          //图片的下载
          if(!(imageUrl.length()<3))
          	 holder.itemsIcon.setImageUrl(imageUrl,webpost.getImageLoader());
          }catch(Exception e)
          {}
         
          
          return convertView;
    }
}
