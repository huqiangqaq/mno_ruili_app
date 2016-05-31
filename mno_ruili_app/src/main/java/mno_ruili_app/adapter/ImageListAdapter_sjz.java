package mno_ruili_app.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.my.my_grzy_item;
import mno.ruili_app.my.my_ht;
import mno_ruili_app.adapter.ImageGridAdapter.ViewHolder;
import mno_ruili_app.home.home_video;
import mno_ruili_app.nei.nei_wdxq;
import mno_ruili_app.nei.nei_zx;
import mno_ruili_app.net.RequestType;

import com.ab.image.AbImageLoader;
import com.ab.util.AbViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ImageListAdapter_sjz extends  BaseAdapter{
	
	private static String TAG = "ImageListAdapter";
	//private static final boolean D = Constant.DEBUG;
  
	private Context mContext;
	//xml转View对象
    private LayoutInflater mInflater;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private List<sjz> mData;
    //Map中的key
    private String[] mFrom;
    //view的id
    private int[] mTo;
    //图片下载器
    String mtype,mimg;
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
    public ImageListAdapter_sjz(Context context, List<sjz> data,String type,String img){
    	this.mContext = context;
    	this.mData = data;
    	this.mtype=type;
    	this.mimg=img;
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
    	TextView sjz_y;
    	TextView sjz_r;
		TextView sjz_y_x;
		ListView mListView;
	}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
    	 final ViewHolder holder;
          if(convertView == null){
	           //使用自定义的list_items作为Layout
        	  
	           convertView = mInflater.inflate(R.layout.item_sjz, parent, false);
	           
	           holder = new ViewHolder();
			   holder.sjz_y = ((TextView) convertView.findViewById(R.id.sjz_y)) ;
			   holder.sjz_r=((TextView) convertView.findViewById( R.id.sjz_r)) ;
			   holder.mListView =((ListView) convertView.findViewById(R.id.mListView)) ;
			   holder.mListView.setFocusable(false);
			   convertView.setTag(holder);
          }
          else
          {
        	  holder = (ViewHolder) convertView.getTag();
          }
  
          
          if(mtype.equals("ht"))
          {
        	  
         
          final List<ht>   htList = new ArrayList<ht>();
          
          final sjz msjz= (sjz)mData.get(position);
          
          String month=msjz.getmonth();
          String day=msjz.getday();
          holder.sjz_y.setText(month);
          holder.sjz_r.setText(day);
          
          try{
        	  if(position!=0){
        	  final sjz msjz2= (sjz)mData.get(position-1);
        	  String Yestermonth=msjz2.getmonth();
              String Yesterday=msjz2.getday();
              if(Yestermonth.equals(month))
            	  holder.sjz_y.setVisibility(View.GONE );
        	  }
        	  else
        		  holder.sjz_y.setVisibility(View.VISIBLE );
          }	 catch (Exception e) {
				// TODO Auto-generated catch block
				
			}
          try {
				/*if(Isrefresh)
				{
					mAbPullToRefreshView.onHeaderRefreshFinish();
				}*/
				htList.clear();
				JSONArray data = msjz.getdata();
				int length=data.length();
			    for(int i=0;i< length; i++)
			    {
			    JSONObject type_json =   data.getJSONObject(i);
			    //listData.add(data.getJSONObject(i).getString("Name"));
			    String courseId= "",newsId = "";
			    try {
			     courseId=type_json.getString("courseId");
			    
			    } catch (JSONException e) {
					// TODO Auto-generated catch block
			    	 newsId=type_json.getString("newsId");
				}
			    String id =  type_json.getString("id");
			    String myReply =  type_json.getString("myReply");
			  
			    String ReplyTime =  type_json.getString("ReplyTime");
			    String title =  type_json.getString("title");
			    String Rcontent =  type_json.getString("Rcontent");
			    String replyTotal =  type_json.getString("replyTotal");
			    String publish_at =  type_json.getString("publish_at");
			    
			    if(newsId.length()>0)
			    {
			    final ht mwd = new ht( "0",newsId,id, myReply, ReplyTime, title, Rcontent,  replyTotal, publish_at);
			    
			    htList.add(mwd);}
			    else
			    {
				    final ht mwd = new ht( "1",courseId,id, myReply, ReplyTime, title, Rcontent,  replyTotal, publish_at);
				    
				    htList.add(mwd);}
			    
			    }
			  //  myhtAdapter.notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				
			}
         

          ImageListAdapter_myht myhtAdapter = new ImageListAdapter_myht(mContext, htList,
        			R.layout.item_ht, new String[] { "itemsIcon" },
        			new int[] { R.id.myReply ,R.id.title,R.id.replyTotal,R.id.ReplyTime});
          holder.mListView.setAdapter(myhtAdapter);
          holder.mListView.setOnItemClickListener(new OnItemClickListener(){

  			@Override
  			public void onItemClick(AdapterView<?> parent, View view,
  					int position, long id) {
  			/*	Intent intent = new Intent(mContext,nei_zx.class);
  				Constant.zxid=htList.get(position).getmianid();
  				intent.putExtra("ID",htList.get(position).getmianid());  
  				mContext.startActivity(intent);*/
  				if(htList.get(position).getwhere().equals("0"))
				{
					Intent intent = new Intent(mContext,nei_zx.class);
					Constant.zxid=htList.get(position).getmianid();
					intent.putExtra("ID",htList.get(position).getid());  
					mContext.startActivity(intent);
				}
				else
				{
					Intent intent = new Intent(mContext,home_video.class);
					Constant.itemid=htList.get(position).getmianid();  
					//intent.putExtra("ID",mList.get(position).getid());  
					mContext.startActivity(intent);
				}
  			}
   		
   	});
          }
          
          else if(mtype.equals("wd"))
          {
        	  
         
         final ArrayList<wd>   wdList = new ArrayList<wd>();
          
          final sjz msjz= (sjz)mData.get(position);
          
          String month=msjz.getmonth();
          String day=msjz.getday();
          holder.sjz_y.setText(month);
          holder.sjz_r.setText(day);
          
          try{
        	  if(position!=0){
        	  final sjz msjz2= (sjz)mData.get(position-1);
        	  String Yestermonth=msjz2.getmonth();
              String Yesterday=msjz2.getday();
              if(Yestermonth.equals(month))
            	  holder.sjz_y.setVisibility(View.GONE );
        	  }
        	  else
        		  holder.sjz_y.setVisibility(View.VISIBLE );
          }	 catch (Exception e) {
				// TODO Auto-generated catch block
				
			}
          try {
				/*if(Isrefresh)
				{
					mAbPullToRefreshView.onHeaderRefreshFinish();
				}*/
        	    wdList.clear();
				JSONArray data = msjz.getdata();
				int length=data.length();
			    for(int i=0;i<length; i++)
			    {
			    JSONObject type_json =   data.getJSONObject(i);
			    
			    String name =  type_json.getString("username");
			    String content =  type_json.getString("content");
			    String id =  type_json.getString("questionId");
			    String imgurl=type_json.getString("userSmallImg");
			   //String uid=type_json.getString("uid");
			    String tag =  type_json.getString("tag");
			    String statusName =  type_json.getString("name");
			    String create_at =  type_json.getString("create_at");
			    String bestAnwCon =  "";
			    String bestAnwUname="";
			    String needPoint=type_json.getString("needPoint");
			    String answerTotal=type_json.getString("answerTotal");
			    final wd mwd = new wd(name,content, id,"",imgurl, tag, statusName, create_at, bestAnwCon, bestAnwUname,needPoint,answerTotal);
			    
			    wdList.add(mwd);
			    }
			  //  myhtAdapter.notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				
			}
         
          ImageListAdapter_wd mywdAdapter = new ImageListAdapter_wd(mContext , wdList,
      			R.layout.item_list_wd_small, new String[] { "itemsIcon" },
      			new int[] { R.id.nei_wd_image ,R.id.wdname,R.id.wd_tag,R.id.wdzt,R.id.wdtime,R.id.wdcontent,R.id.wdbestname,R.id.wdbestcon,R.id.wd_fs,R.id.wdhd,R.id.wdbeshd,R.id.wdrs},0);
       
          holder.mListView.setAdapter(mywdAdapter);
      
          holder.mListView.setOnItemClickListener(new OnItemClickListener(){

  			@Override
  			public void onItemClick(AdapterView<?> parent, View view,
  					int position, long id) {
  				Intent intent = new Intent(mContext,nei_wdxq.class);
  				Constant.itemid=wdList.get(position).getId();
  				
  				mContext.startActivity(intent);
  			}
   		
   	});}
          
          
          
          else if(mtype.equals("kc"))
          {
        	  
         
         final List<zx>   mList = new ArrayList<zx>();
          
          final sjz msjz= (sjz)mData.get(position);
          
          String month=msjz.getmonth();
          String day=msjz.getday();
          holder.sjz_y.setText(month);
          holder.sjz_r.setText(day);
          
          try{
        	  if(position!=0){
        	  final sjz msjz2= (sjz)mData.get(position-1);
        	  String Yestermonth=msjz2.getmonth();
              String Yesterday=msjz2.getday();
              if(Yestermonth.equals(month))
            	  holder.sjz_y.setVisibility(View.GONE );
        	  }
        	  else
        		  holder.sjz_y.setVisibility(View.VISIBLE );
          }	 catch (Exception e) {
				// TODO Auto-generated catch block
				
			}
          try {
				/*if(Isrefresh)
				{
					mAbPullToRefreshView.onHeaderRefreshFinish();
				}*/
        	    mList.clear();
				JSONArray data = msjz.getdata();
				int length=data.length();
			    for(int i=0;i<length; i++)
			    {
			    JSONObject type_json =   data.getJSONObject(i);
			    
			    String name =  type_json.getString("title");
			    String content = type_json.getString("viewAt");
			    String content2 = type_json.getString("viewTime");
			    String id =  type_json.getString("courseId");
			    String imgurl=type_json.getString("coverImg");
			    String time=type_json.getString("longer");
			    final zx mtv = new zx(name, content,id,imgurl,time,content2);
			 //   if(type_json.getString("typeName").equals("视觉"))
			    mList.add(mtv);
			    }
			  //  myhtAdapter.notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				
			}
         
          ImageListAdapter_mykc mykcAdapter  = new ImageListAdapter_mykc(mContext, mList,
					R.layout.item_listkc, new String[] { "itemsIcon" },
					new int[] { R.id.itemsIcon ,R.id.itemstext,R.id.itemstext2,R.id.itemstext3});
          holder.mListView.setAdapter(mykcAdapter);
      
          holder.mListView.setOnItemClickListener(new OnItemClickListener(){

  			@Override
  			public void onItemClick(AdapterView<?> parent, View view,
  					int position, long id) {
  				Intent intent = new Intent(mContext,home_video.class);
  				Constant.itemid=mList.get(position).getid();
  				if(Constant.itemismy.equals("1"))
  				{
  					Constant.itemzt=mList.get(position).getwho();
  					Constant.playPosition=Integer.parseInt(mList.get(position).getwho());
  				}
  				
  				intent.putExtra("ID",mList.get(position).getid());  
  				mContext.startActivity(intent);
  			}
   		
   	});}
          

          return convertView;
    }
    
   
}
