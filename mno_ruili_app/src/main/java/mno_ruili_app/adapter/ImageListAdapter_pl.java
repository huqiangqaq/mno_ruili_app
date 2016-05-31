package mno_ruili_app.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.my.my_grzy_item;
import mno.ruili_app.my.my_sc;
import mno_ruili_app.adapter.ImageGridAdapter.ViewHolder;
import mno_ruili_app.home.home_video;
import mno_ruili_app.nei.nei_wdxq;
import mno_ruili_app.nei.nei_zx;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import mno_ruili_app.net.webpost;

import com.ab.image.AbImageLoader;
import com.ab.util.AbViewHolder;
import com.android.volley.toolbox.NetworkImageView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ImageListAdapter_pl extends  BaseAdapter{
	
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
   // private AbImageLoader mAbImageLoader = null;
    webhandler handler_; 
    String mwhat;
   /**
    * 构造方法
    * @param context
    * @param data 列表展现的数据
    * @param resource 单行的布局
    * @param from Map中的key
    * @param to view的id
    */
    public ImageListAdapter_pl(Context context, List<hd> data,
            int resource, String[] from, int[] to,String what){
    	this.mContext = context;
    	this.mData = data;
    	this.mResource = resource;
    	this.mFrom = from;
    	this.mTo = to;
    	this.mwhat=what;
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
   
   // LinearLayout zb_bt;
   // TextView zb_color,zb_bt_text;

    static  Drawable pic,pic2;
    
    /**
  		 * View元素
  		 */
  		static class ViewHolder {
  			NetworkImageView itemsIcon;
  			TextView username;
  			TextView create_at;
  			TextView content;
  			LinearLayout zb_bt;
  		    TextView zb_color,zb_bt_text,zan;
  		}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
    	final ViewHolder holder;
          if(convertView == null){
	           //使用自定义的list_items作为Layout
	           convertView = mInflater.inflate(mResource, parent, false);
	           holder = new ViewHolder();
			   holder.itemsIcon = ((NetworkImageView) convertView.findViewById(mTo[0])) ;
			  /* holder.itemsIcon.setDefaultImageResId(R.drawable.image_empty);
			   holder.itemsIcon.setErrorImageResId(R.drawable.image_error);*/
			  
			   holder.username =((TextView) convertView.findViewById(mTo[1])) ;
			   holder.create_at =((TextView) convertView.findViewById(mTo[2])) ;
			   holder.content =((TextView) convertView.findViewById(mTo[3])) ;
			   
			   holder.zb_color =((TextView) convertView.findViewById(R.id.zb_color)) ;
			   holder.zb_bt =((LinearLayout) convertView.findViewById( R.id.zb_bt)) ;
			   holder.zb_bt.setVisibility(View.GONE);
			   holder.zb_bt_text =((TextView) convertView.findViewById(R.id.zb_bt_text)) ;
			   holder.zan =((TextView) convertView.findViewById(R.id.pl_zan)) ;
			   convertView.setTag(holder);
	           
	  
          }else{
        	  holder = (ViewHolder) convertView.getTag();
          }
          boolean ok=false;
          pic=mContext. getResources().getDrawable( R.drawable.view_zan); 
          pic2=mContext. getResources().getDrawable( R.drawable.view_nozan); 
        
          int floornum=position+1;
   
         
          handler_ = new webhandler(){

    			@Override
  			public void OnError(int code, String mess) {
  				// TODO Auto-generated method stub
  				super.OnError(code, mess);
  				String ms=mess;
  				ms=ms+"";
  				MessageBox.Show(mContext,mess,1);
  			}

  	

  			@Override
    			public void OnResponse(JSONObject response) {
    				// TODO Auto-generated method stub
    				// TODO Auto-generated method stub
  				try {
  					String mess = response.getString("message");
  					
  					if(mess.equals("点赞成功"))
  							{
  						 
  							}	
  						
  					if(mess.equals("null") == false && mess.length() > 1)
  					{
  						MessageBox.Show(mContext,mess,1);
  					}
  					
  				} catch (JSONException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				}
    			}
    			
    			};
          final hd mzx= (hd)mData.get(position);
          
          if(mzx.gethot().equals("0"))
          {
        	  holder.zb_bt.setVisibility(View.VISIBLE);
        	  holder.zb_bt_text.setText("热门评论");
        	  holder. zb_color.setBackgroundColor(android.graphics.Color.parseColor("#f39801"));
          }else if(mzx.gethot().equals("1"))
          {
        	  holder.zb_bt.setVisibility(View.VISIBLE );
        	  holder. zb_bt_text.setText("所有评论");
        	  holder.zb_color.setBackgroundColor(android.graphics.Color.parseColor("#00a0ea"));
          }
          else
        	  holder.zb_bt.setVisibility(View.GONE );
       
          
        if(  mzx.getislike().equals("1"))
        {
        	holder.zan.setTextColor(android.graphics.Color.parseColor("#f39801"));
        	holder.zan.setCompoundDrawablesWithIntrinsicBounds(pic,null, null,null);
			 ok=false;
        }else
        { 
        	holder.zan.setTextColor(android.graphics.Color.parseColor("#939393"));
        	holder. zan.setCompoundDrawablesWithIntrinsicBounds(pic2,null, null,null);
        	ok=true;
        }
        final boolean canbut=true;
        holder.zan.setText(mzx.getlikeTotal());
          String name=mzx.getName();
          if(name.length()>0)
        	  holder. username.setText(mzx.getName());
          else
        	  holder.username.setText("该用户无昵称");
          holder.content.setText(mzx.getContent());
          holder.create_at.setText("发表于"+get(mzx.gettime()));

          String imageUrl =RequestType.getWebUrl_(mzx.getImg());
         
          holder.itemsIcon.setImageUrl(imageUrl,webpost.getImageLoader());
          //图片的下载
          holder.itemsIcon.setDefaultImageResId(R.drawable.image_empty);
          holder.itemsIcon.setErrorImageResId(R.drawable.image_error);	
        /*  holder.itemsIcon.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					Intent intent = new Intent(mContext,my_grzy_item.class);

					intent.putExtra("ID",mzx.getuid());  
					 mContext.startActivity(intent);
					// TODO Auto-generated method stub
				}});*/
          holder. itemsIcon.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					 Intent intent = new Intent(mContext,my_grzy_item.class);
				        intent.putExtra("img",mzx.getImg());  
						intent.putExtra("ID",mzx.getuid());  
						intent.putExtra("name",mzx.getName());  
						 mContext.startActivity(intent);
					//MessageBox.Show(mContext, mzx.getContent());
				}
      });
          holder.zan.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					 if(!appuser.getInstance().IsLogin())
			    	 {
						 appuser.getInstance().Login(mContext);
						 //appuser.getInstance().LoginToAct(mContext,nei_zx.class);
			    		 return;
			    	 }
					// TODO Auto-generated method stub
					if(canbut)
					{
						 Map<String, String> params = new HashMap<String, String>();
						 params.put("forwhat","like");
					     params.put("likeId",mzx.getId());
					   
					     params.put("type",mwhat);
					     
						 handler_.SetRequest(new RequestType("2",Type.addLike),params);
						//Toast.makeText(mContext, ""+mzx.getContent(), Toast.LENGTH_SHORT).show();
						 if(mzx.getislike().equals("1"))
						 {
							 if(mzx.getlikeTotal().equals("0"))
							 return;
						     int i=Integer.parseInt(mzx.getlikeTotal())-1;
						     mzx.setlikeTotal(i+"");
						     mzx.setislike("0");
						 }
						 else
						 {
							 int i=Integer.parseInt(mzx.getlikeTotal())+1;
							 mzx.setlikeTotal(i+"");
							 mzx.setislike("1");
						 }
					}
						 
						 notifyDataSetChanged();
						/* holder.zan.setText(""+i);
						 holder.zan.setTextColor(android.graphics.Color.parseColor("#f39801"));
						 holder.zan.setCompoundDrawablesWithIntrinsicBounds(pic,null, null,null);*/
						 
					
					
	    			 
				}

			
			});
  
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
		if(min-1<0)
			age="刚刚";
		else if(min-59<0)
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
