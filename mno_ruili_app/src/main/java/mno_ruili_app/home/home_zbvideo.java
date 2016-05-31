package mno_ruili_app.home;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import mno.mylistview.PullToRefreshBase;
import mno.mylistview.PullToRefreshBase.OnRefreshListener;
import mno.mylistview.FooterLoadingLayout;
import mno.mylistview.PullToRefreshList;
import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.myapplication;
import mno.ruili_app.ct.MessageBox;
import mno_ruili_app.adapter.ImagebtListAdapter;
import mno_ruili_app.adapter.tv;
import mno_ruili_app.adapter.zb_tv;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class home_zbvideo extends Activity{	  
	  private PullToRefreshList mRefreshLayout;
	  private ListView mList;
	  ImagebtListAdapter mylistViewAdapter;
	  webhandler handler_;
	  ArrayList<zb_tv>   mTvList = new ArrayList<zb_tv>();
	  myapplication application;
	  Toast toast ;
	  private Handler handler = new Handler(){
	        @Override
	        public void handleMessage(Message msg) {
	            super.handleMessage(msg);
	            switch (msg.what){
	                case 0x123:
	                    toast.cancel();
	                    Intent intent = new Intent(home_zbvideo.this,home_zb_rl_.class);
	            		home_zbvideo.this.startActivity(intent);
	                    break;
	            }
	        }
	 };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_zbvidio);
		application = (myapplication) this.getApplication();
		init();
	}
	 
	private void init() {
		// TODO Auto-generated method stub
		   mRefreshLayout = (PullToRefreshList)this.findViewById(R.id.blog_swiperefreshlayout);
		   mList = mRefreshLayout.getRefreshView();
	       mList.setDivider(new ColorDrawable(android.R.color.transparent));
	       mList.setOverscrollFooter(null);
	       mList.setOverscrollHeader(null);
	       mList.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
	       mRefreshLayout.setPullLoadEnabled(false);
	       mRefreshLayout.setPullRefreshEnabled(true);
	       ((FooterLoadingLayout) mRefreshLayout.getFooterLoadingLayout())
	                .setNoMoreDataText("更多往期内容已放入免费视频专区，请前往查看");
	       mRefreshLayout.setOnRefreshListener(new OnRefreshListener<ListView>() {
	            @Override
	            public void onPullDownToRefresh(
	                    PullToRefreshBase<ListView> refreshView) {
	                refresh();
	            }	          
				@Override
	            public void onPullUpToRefresh(
	                    PullToRefreshBase<ListView> refreshView) {					
	                mRefreshLayout.setHasMoreData(false);
	            }
	        });
	        mList.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                    final int position, long id) {
	            	String typevideo=mTvList.get(position).getType();
	            	//1.直播预告的跳转
	            	if(get(mTvList.get(position).gettime())<0){
	            		if(typevideo.equals("1")){
	            			//MessageBox.Show(getApplicationContext(), "直播开始时间为\n"+mTvList.get(position).gettime());
	            			Intent intent = new Intent(home_zbvideo.this,home_zb_rl_.class);
		            		home_zbvideo.this.startActivity(intent);
		            		return;
	            		}else{
	            			application.setforwhat(mTvList.get(position).gettime());	 
		            		//利用Handler将toast在当前界面显示，然后再跳转.
		            		toast = Toast.makeText(home_zbvideo.this,"直播开始时间为\n"+mTvList.get(position).gettime(),Toast.LENGTH_SHORT);
		            		toast.show();
		            		handler.postDelayed(new Runnable() {
		                        @Override
		                        public void run() {
		                            handler.sendEmptyMessage(0x123);
		                        }
		                    },2000);
		            		return;
	            		}	            		
	            	}
	            	//2.往期直播的跳转
	            	if(mTvList.get(position).getType().equals("3")||mTvList.get(position).getType().equals("4")){
	            		Intent intent = new Intent(home_zbvideo.this,home_video.class);
						Constant.itemid=mTvList.get(position).getid();
						home_zbvideo.this.startActivity(intent);
	            	}
	            	//3.点击了今日直播的跳转，如果不在直播时间就跳转到直播日历界面，
	            	//在直播时间则跳转到视频直播直播界面（与往期直播跳转一样）。
	            	else{
	            		 if(mTvList.get(position).getisEnd().equals("1")){
							 MessageBox.Show(getApplicationContext(), "直播已结束");
							 return;
						}
		            	Intent intent = new Intent(home_zbvideo.this,home_video.class);
						Constant.itemid=mTvList.get(position).getid();
						home_zbvideo.this.startActivity(intent);
	            	}
	            }
	       });		   
		   mylistViewAdapter=new ImagebtListAdapter(this, mTvList,
					R.layout.item_list_zb, new String[] { "itemsIcon" },
					new int[] { R.id.itemsIcon });
		   mList.setAdapter(mylistViewAdapter);	 			  			  			  
		   handler_ = new webhandler(){
				@Override
				public void OnResponse(JSONObject response) {
					// TODO Auto-generated method stub						 
					try {
						mTvList.clear();
						mRefreshLayout.onPullDownRefreshComplete();
						//1.直播预告
						try {
							JSONArray  data2=response.getJSONObject("data").getJSONArray("pretoday") ;
							int length2=data2.length();
						    for(int i=0;i< length2; i++){
						    	JSONObject type_json =   data2.getJSONObject(i);
						    	String Name=type_json.getString("title"); 
						    	String time=type_json.getString("beginTime");
						    	String begin=type_json.getString("beginTime").substring(11, 16);
						    	String end=type_json.getString("endTime").substring(11, 16);
						    	String isEnd=type_json.getString("isEnd");
						    	String isMembers=type_json.getString("isMembers");
						    	String Ms=type_json.getString("beginTime").substring(0,10)+begin;;
						    	String Img=type_json.getString("coverImg");;
						    	String Type=0+"";
						    	if(i==0){
						    		Type=1+"";
						    	}else
						    		Type=1+"";
						    	String id=type_json.getString("id");;
						    	String isCollect=type_json.getString("isCollect");;
						    	final zb_tv mtv = new zb_tv(Name, Ms,RequestType.getWebUrl_(Img),Type, id,isCollect,time,isEnd,isMembers);
						    	mTvList.add(mtv);
						    }
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
						//2.正在的直播
						try {
							JSONArray  data=response.getJSONObject("data").getJSONArray("today") ;
							int length=data.length();
						    for(int i=0;i< length; i++){
						    	JSONObject type_json =   data.getJSONObject(i);
						    	String Name=type_json.getString("title"); 
						    	String Ms=type_json.getString("beginTime"); ;
						    	String Img=type_json.getString("coverImg");;
						    	String time=type_json.getString("beginTime");
						    	String isEnd=type_json.getString("isEnd");
						    	String isMembers=type_json.getString("isMembers");
						    	String Type=0+"";
						    	if(i==0){
						    		Type=2+"";
						    	}else
						    		Type=5+"";
						    	String id=type_json.getString("id");;
						    	final zb_tv mtv = new zb_tv(Name, ""+i,RequestType.getWebUrl_(Img),Type, id,"",time,isEnd,isMembers);
						    	mTvList.add(mtv);
						    }
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
						//3.往期视频
						try {
							JSONArray  data=response.getJSONObject("data").getJSONArray("pass") ;
							int length=data.length();
						    for(int i=0;i< length; i++){
						    	JSONObject type_json =   data.getJSONObject(i);
						    	String Name=type_json.getString("title"); 
						    	String Ms=type_json.getString("beginTime"); 
						    	String Img=type_json.getString("coverImg");
						    	String time=type_json.getString("beginTime");
						    	String isEnd=type_json.getString("isEnd");
						    	String isMembers=type_json.getString("isMembers");
						    	String Type=0+"";
						    	if(i==0){
						    		Type=3+"";
						    	}
						    	else
						    	Type=4+"";
						    	String id=type_json.getString("id");;
						    	final zb_tv mtv = new zb_tv(Name, ""+i,RequestType.getWebUrl_(Img),Type, id,"",time,isEnd,isMembers);
						    	mTvList.add(mtv);
						    }
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}														
					    mylistViewAdapter.notifyDataSetChanged();							
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}							
				}					
		   };    	  
	  }
	  private void refresh() {
		  Map<String, String> params = new HashMap<String, String>();
		  handler_.SetRequest(new RequestType("2",Type.getLivePage),params);		 
          mRefreshLayout.onPullUpRefreshComplete();
	}
	public void onclick(View v) {	
		 if(v.getId()==R.id.zb_rl){
			 Intent intent = new Intent(home_zbvideo.this,home_zb_rl.class);				
			this.startActivity(intent);			
     	}
     	else if(v.getId()==R.id.select_tg){     		    	
     	}
	 }	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Map<String, String> params = new HashMap<String, String>();
		handler_.SetRequest(new RequestType("2",Type.getLivePage),params);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("home_zbvideo");
		MobclickAgent.onResume(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("home_zbvideo");
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		 Map<String, String> params = new HashMap<String, String>();
		 handler_.SetRequest(new RequestType("2",Type.getLivePage),params);
	}
	   private Long get(String time) {
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
			Long timeP=ut2-ut1;
		
			return timeP;										
	   }	
}