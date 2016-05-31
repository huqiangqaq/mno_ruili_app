package mno.ruili_app.my;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import mno.mylistview.FooterLoadingLayout;
import mno.mylistview.PullToRefreshBase;
import mno.mylistview.PullToRefreshList;
import mno.mylistview.PullToRefreshBase.OnRefreshListener;
import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.main.MyFragment;
import mno_ruili_app.adapter.ImageListAdapter_my;
import mno_ruili_app.adapter.ImageListAdapter_mydd;
import mno_ruili_app.adapter.ImageListAdapter_mywd;
import mno_ruili_app.adapter.ImageListAdapter_system;
import mno_ruili_app.adapter.mywd;
import mno_ruili_app.entity.Message;
import mno_ruili_app.entity.Message2;
import mno_ruili_app.nei.nei_wdxq;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.RequestType.Type;
import mno_ruili_app.net.webhandler;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class My_xiaoxi_item extends Activity{
	TextView my_butwen,my_butda,tv_my,tv_system,tv_message,tv_message2;
	webhandler handler_,/*我的*/handler_2,/*系统消息*/handler_3/*我的消息清除红点*/;
	webhandler handler4,handler5;//获取红点
	JSONObject type_json;
	List<Message>   mList = new ArrayList<Message>();
	List<Message2>   mList2 = new ArrayList<Message2>();
	private PullToRefreshList mRefreshLayout= null;
	private PullToRefreshList mRefreshLayout2 = null;	
	private ListView mListView;
	private ListView mListView2;
	ImageListAdapter_my mylistViewAdapter;
	ImageListAdapter_system mylistViewAdapter2;
	private int currentPage1 = 1;
	private int currentPage2 = 1;
	boolean havemore=true;
	boolean Isrefresh=false;
	boolean IsHandler_=false; //是否处理中
	Map<String, String> params3,params4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_xiaoxi_item);
		loadMyMsg();
		loadSysMsg();
		//3.注册我的和系统消息的红点广播
		IntentFilter filter3= new IntentFilter();
		filter3.addAction("mno.push");
		registerReceiver(myPushReceiver, filter3);
		init();
	}
	private BroadcastReceiver myPushReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			loadMyPoint();
			loadSysPoint();
		}

	};	
	
	//返回的时候再次请求刷新数据，此时执行onResume方法
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("My_xiaoxi_item");
		MobclickAgent.onResume(this); 
		//1.我的消息列表
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", "1");
		params.put("page", currentPage1+"");
		handler_.SetRequest(new RequestType("3",Type.getMsg),params);
		//2.系统消息的列表
		Map<String, String> params2 = new HashMap<String, String>();
		params2.put("type", "2");
		params2.put("page", currentPage2+"");
		handler_2.SetRequest(new RequestType("3",Type.getMsg),params2);
		//1.我的标题上的红点
		params3 = new HashMap<String, String>();
		handler4.SetRequest(new RequestType("3",Type.getRedPoint),params3);
		//2.系统标题上的红点
		params4 = new HashMap<String, String>();
		handler5.SetRequest(new RequestType("3",Type.getRedPoint),params4);		
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("My_xiaoxi_item");
		MobclickAgent.onPause(this);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(myPushReceiver);
	}
	//1.我的消息列表数据的获得
	private void loadMyMsg() {
        handler_ = new webhandler(){	
	    	@Override
	    	public void OnError(int code, String mess) {
		    	// TODO Auto-generated method stub
		    	super.OnError(code, mess);
		    	mRefreshLayout.onPullDownRefreshComplete();
				mRefreshLayout.onPullUpRefreshComplete();
	    	}
			@Override
			public void OnResponse(JSONObject response) {
				try {
					if(Isrefresh){
						mList.clear();							
						mRefreshLayout.onPullDownRefreshComplete();
						Isrefresh=false;
					}	
					//mList.clear();	
					mRefreshLayout.onPullUpRefreshComplete();
					JSONArray data = response.getJSONArray("data");
					int length=data.length();
					havemore=true;
					if(length==0){
						havemore=false;
						mRefreshLayout.setHasMoreData(false);
					}
				    for(int i=0;i< length; i++){
					    type_json =   data.getJSONObject(i);
					    
					    String id=type_json.getString("id");
					    String type_id =  type_json.getString("type_id");
					    String title =  type_json.getString("title");
					    String content =  type_json.getString("content");						    
					    String update_time =  type_json.getString("update_time");
					    String isView =  type_json.getString("isView");
					    
					    final Message msg_my = new Message(id,title,content,type_id, update_time, isView);
					    
						mList.add(msg_my);						    
					    IsHandler_ = false;
				    }
				    mylistViewAdapter.notifyDataSetChanged();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}
	//2.系统消息列表数据的获得
	private void loadSysMsg(){
		handler_2 = new webhandler(){
			@Override
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
				mRefreshLayout2.onPullDownRefreshComplete();
				mRefreshLayout2.onPullUpRefreshComplete();
			}
			@Override
			public void OnResponse(JSONObject response) {
				try {
					if(Isrefresh){
						mList2.clear();							
						mRefreshLayout2.onPullDownRefreshComplete();
						Isrefresh=false;
					}
					//mList2.clear();
					mRefreshLayout2.onPullUpRefreshComplete();
					JSONArray data = response.getJSONArray("data");
					int length=data.length();
					havemore=true;
					if(length==0){
						havemore=false;
						mRefreshLayout2.setHasMoreData(false);
					}
				    for(int i=0;i< length; i++){
					    type_json =   data.getJSONObject(i);
					    //listData.add(data.getJSONObject(i).getString("Name"));
					    String id =  type_json.getString("id");
					    String title =  type_json.getString("title");
					    String content =  type_json.getString("content");						    
					    String image =  type_json.getString("image");
					    String isView =  type_json.getString("isView");
					    
					    final Message2 msg_system = new Message2(id,title,content, image, isView);
					    
						mList2.add(msg_system);					    
					    IsHandler_ = false;
				    }
				    mylistViewAdapter2.notifyDataSetChanged();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};		
	}
	//1.我的消息标题的红点数获取
	private void loadMyPoint(){
		handler4=new webhandler(){
			@Override
			public void OnResponse(JSONObject response) {
				// TODO Auto-generated method stub
				super.OnResponse(response);
				try {
					JSONObject data = response.getJSONObject("data");
					String my=data.getString("my");
					int i=Integer.parseInt(my);
					if(i==0){
						tv_message.setVisibility(View.INVISIBLE);
					}else{
						tv_message.setVisibility(View.VISIBLE);
						if(i<99){
							tv_message.setText(i+"");
						}else{
							tv_message.setText(99+"+");
						}
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		params3 = new HashMap<String, String>();
		handler4.SetRequest(new RequestType("3",Type.getRedPoint),params3);
	}
	//2.系统消息标题的红点数获取
	private void loadSysPoint(){
		handler5=new webhandler(){
			@Override
			public void OnResponse(JSONObject response) {
				// TODO Auto-generated method stub
				super.OnResponse(response);
				try {
					JSONObject data = response.getJSONObject("data");
					String sys=data.getString("sys");
					int i=Integer.parseInt(sys);
					if(i==0){
						tv_message2.setVisibility(View.INVISIBLE);
					}else{
						tv_message2.setVisibility(View.VISIBLE);
						if(i<99){
							tv_message2.setText(i+"");
						}else{
							tv_message2.setText(99+"+");
						}
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		params4 = new HashMap<String, String>();
		handler5.SetRequest(new RequestType("3",Type.getRedPoint),params4);
	}
	private void init() {
		//1.我的消息标题的红点数获取
		tv_message=(TextView) findViewById(R.id.tv_message);
		loadMyPoint();
		//2.系统消息标题的红点数获取
		tv_message2=(TextView) findViewById(R.id.tv_message2);
		loadSysPoint();
		
		my_butwen=(TextView) findViewById(R.id.my_butwen);
		my_butda=(TextView) findViewById(R.id.my_butda);
		tv_my=(TextView) findViewById(R.id.tv_my);
		tv_system=(TextView) findViewById(R.id.tv_system);
		
		mRefreshLayout=(PullToRefreshList) findViewById(R.id.mListView);
		mListView = mRefreshLayout.getRefreshView();
		mRefreshLayout2=(PullToRefreshList) findViewById(R.id.mListView2);
		mListView2 = mRefreshLayout2.getRefreshView();
		//1.我的上下拉刷新
		mListView.setDivider(new ColorDrawable(android.R.color.transparent));
	    mListView.setOverscrollFooter(null);
	    mListView.setOverscrollHeader(null);
	    mListView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
	    mListView.setSelector(R.drawable.selector_button_color_red_noshape);
	    mListView.setVerticalScrollBarEnabled(true);
        mRefreshLayout.setPullLoadEnabled(true);
        mRefreshLayout.setPullRefreshEnabled(true);      
        ((FooterLoadingLayout) mRefreshLayout.getFooterLoadingLayout())
                .setNoMoreDataText("已经到底啦");
        mRefreshLayout.setHasMoreData(true);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            	currentPage1=1;
            	Map<String, String> params = new HashMap<String, String>();
    			params.put("type", "1");
    			params.put("page", currentPage1+"");
    			handler_.SetRequest(new RequestType("3",Type.getMsg),params);
    			Isrefresh=true;
            }         
			@Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				if(havemore){
					currentPage1++;
					loadMyMsg();				     
				}
				else{
					mRefreshLayout.setHasMoreData(false);
				}
             }
			
         });
        //2.系统上下拉刷新
        mListView2.setDivider(new ColorDrawable(android.R.color.transparent));
	    mListView2.setOverscrollFooter(null);
	    mListView2.setOverscrollHeader(null);
	    mListView2.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
	    mListView2.setSelector(R.drawable.selector_button_color_red_noshape);
	    mListView2.setVerticalScrollBarEnabled(true);
        mRefreshLayout2.setPullLoadEnabled(true);
        mRefreshLayout2.setPullRefreshEnabled(true);      
        ((FooterLoadingLayout) mRefreshLayout2.getFooterLoadingLayout())
                .setNoMoreDataText("已经到底啦");
        mRefreshLayout2.setHasMoreData(true);
        mRefreshLayout2.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            	currentPage2=1;
            	Map<String, String> params2 = new HashMap<String, String>();
            	params2.put("page", currentPage2+"");
        		params2.put("type", "2");
        		handler_2.SetRequest(new RequestType("3",Type.getMsg),params2);
    			Isrefresh=true;
            }         
			@Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				if(havemore){
					currentPage2++;
					loadSysMsg();				     
				}
				else{
					mRefreshLayout2.setHasMoreData(false);
				}
             }
			
         });
	     //
		 mylistViewAdapter = new ImageListAdapter_my(this, mList,
					R.layout.list_item_my, new String[] { "itemsIcon" },
					new int[] { R.id.tv_title,R.id.tv_content ,R.id.tv_time,R.id.tv_message});
		 mylistViewAdapter2 = new ImageListAdapter_system(this, mList2,
					R.layout.list_item_system, new String[] { "itemsIcon" },
					new int[] {R.id.tv_title,R.id.iv_image, R.id.tv_content,R.id.tv_message});
	       
	       mListView.setAdapter(mylistViewAdapter);
	       mListView2.setAdapter(mylistViewAdapter2);
	       //1.我的消息的点击事件
	       mListView.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {					
						Intent intent = new Intent(My_xiaoxi_item.this,nei_wdxq.class);
						Constant.itemid=mList.get(position).getType_id();
						intent.putExtra("ID", mList.get(position).getId());
						My_xiaoxi_item.this.startActivity(intent);										
				}
	    		
	    	});
	       //2.系统消息的点击事件
	       mListView2.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					//String mId=mList2.get(position).getId();					
					Intent intent=new Intent(My_xiaoxi_item.this,SystemMsgDetail.class);
					Constant.itemid=mList2.get(position).getId();
					intent.putExtra("title", mList2.get(position).getTitle());
					intent.putExtra("image", mList2.get(position).getImage());
					intent.putExtra("content", mList2.get(position).getContent());
					//intent.putExtra("id", mId);
					My_xiaoxi_item.this.startActivity(intent);			       					
				}	    		
	    	});
	}
	//标题的点击事件
	 String where="my";
	 public void onclick(View v) {		 
		 if(v.getId()==R.id.ll_my)
		 {			 
			 mylistViewAdapter.notifyDataSetChanged();
			 where="my";
			 mListView.setVisibility(View.VISIBLE);
			 mListView2.setVisibility(View.GONE);
			 my_butwen.setTextColor(android.graphics.Color.parseColor("#387ebc"));
			 my_butda.setTextColor(android.graphics.Color.parseColor("#919191"));			 
     		 tv_my.setBackgroundColor(android.graphics.Color.parseColor("#387ebc"));
     		 tv_system.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
		 }
		 else if(v.getId()==R.id.ll_system)
		 {
			 mylistViewAdapter2.notifyDataSetChanged();
			 where="system";
			 mRefreshLayout2.setVisibility(View.VISIBLE);
			 mListView2.setVisibility(View.VISIBLE);
			 mListView.setVisibility(View.GONE);
			 my_butda.setTextColor(android.graphics.Color.parseColor("#387ebc"));
			 my_butwen.setTextColor(android.graphics.Color.parseColor("#919191"));			 
     		 tv_system.setBackgroundColor(android.graphics.Color.parseColor("#387ebc"));
     		 tv_my.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
		 }
	 }
	 
}
