package mno_ruili_app.nei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.umeng.analytics.MobclickAgent;

import mno.mylistview.FooterLoadingLayout;
import mno.mylistview.PullToRefreshBase;
import mno.mylistview.PullToRefreshList;
import mno.mylistview.PullToRefreshBase.OnRefreshListener;
import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno_ruili_app.adapter.ImageListAdapter_pl;
import mno_ruili_app.adapter.ListAdapter;
import mno_ruili_app.adapter.hd;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class nei_zxpl extends Fragment{
	webhandler handler_; 
	View View_;
	private ListView mListView = null;
	ImageListAdapter_pl mylistViewAdapter;
	List<Map<String, Object>> mList=null;
	ArrayList<hd>  mplList = new ArrayList<hd>();
	private PullToRefreshList mRefreshLayout;
	boolean Isrefresh=false;
	boolean havemore=false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View_ = inflater.inflate(R.layout.nei_zxpl, container,
				false);
		IntentFilter filter = new IntentFilter();
		filter.addAction("mno.zxpl");
		filter.setPriority(Integer.MAX_VALUE);
		nei_zxpl.this.getActivity().registerReceiver(myReceiver, filter);
		
		init();
		InitHandler();
		//get();
		
		return View_;
		
		
	}
	private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			 String name = intent.getExtras().getString("name");  
			 refreshTask();
			
		}

	};
	public void get() {
		// TODO Auto-generated method stub
		 if(!appuser.getInstance().IsLogin())
    	 {
			 Map<String, String> params = new HashMap<String, String>();
			  params.put("newsId",Constant.zxid);
			/*  params.put("uid","4");
			  params.put("accessCode","cnVpbGk0");*/
			  params.put("accessCode",  Constant.accessCode);
			
			  handler_.SetRequest(new RequestType("3",Type.getNewsReply),params);
    	 }
		 else
		 {
			 Map<String, String> params = new HashMap<String, String>();
			  params.put("newsId",Constant.zxid);
			  params.put("uid",  Constant.uid);
			  params.put("accessCode",  Constant.acc);
			/*  params.put("uid","4");
			  params.put("accessCode","cnVpbGk0");*/
			  handler_.SetRequest(new RequestType("3",Type.getNewsReply),params);
		 }
		 
		  
	}
	private void InitHandler() {
		// TODO Auto-generated method stub
		handler_ = new webhandler(){
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
				 mRefreshLayout.onPullDownRefreshComplete();
				 mRefreshLayout.onPullUpRefreshComplete();
			}

			@Override
			public void OnResponse(JSONObject response) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				 
				try {
					if(Isrefresh)
						mRefreshLayout.onPullDownRefreshComplete();
					mplList.clear();
					
					JSONArray hotdata= response.getJSONObject("data").getJSONArray("hotreply");
					int length2=hotdata.length();
				    for(int i=0;i<= length2-1; i++)
				    {
				    JSONObject type_json =hotdata.getJSONObject(i);
				    //listData.add(data.getJSONObject(i).getString("Name"));
				    String name =  type_json.getString("replyUname");
				    String replyUid=  type_json.getString("replyUid");
				    String content =  type_json.getString("content");
				    String id =  type_json.getString("id");
				    String time=type_json.getString("create_at");
				    String imgurl=type_json.getString("replyUbimg");
				    String isLike=type_json.getString("isLike");
				    String likeTotal=type_json.getString("likeTotal");
				    String ht="-1";
				    if(i==0)
				    {
				    	ht="0";
				    }
				   
				    final hd mzx = new hd(name,content, id,time,imgurl,isLike,likeTotal,ht,replyUid);
					mplList.add(mzx);
					mylistViewAdapter.notifyDataSetChanged();
				    }
				    
					JSONArray data = response.getJSONObject("data").getJSONArray("reply");
					int length=data.length();
				    for(int i=0;i<= length-1; i++)
				    {
				    JSONObject type_json =data.getJSONObject(i);
				    //listData.add(data.getJSONObject(i).getString("Name"));
				    String name =  type_json.getString("replyUname");
				    String replyUid=  type_json.getString("replyUid");
				    String content =  type_json.getString("content");
				    String id =  type_json.getString("id");
				    String time=type_json.getString("create_at");
				    String imgurl=type_json.getString("replyUbimg");
				    String isLike=type_json.getString("isLike");
				    String likeTotal=type_json.getString("likeTotal");
				    String ht="-1";

				    if(i==0)
				    {
				    	ht="1";
				    }
				    final hd mzx = new hd(name,content, id,time,imgurl,isLike,likeTotal,ht,replyUid);
				    
					mplList.add(mzx);
					mylistViewAdapter.notifyDataSetChanged();
				    }
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
			}
			
			};
			//handler_.SetProgressDialog(nei_zxpl.this.getActivity());
	}
	private void init() {
		// TODO Auto-generated method stub
		   mRefreshLayout = (PullToRefreshList)View_.findViewById(R.id.mListView);
		   mListView = mRefreshLayout.getRefreshView();
		   mListView.setDivider(new ColorDrawable(android.R.color.transparent));
		   mListView.setOverscrollFooter(null);
		   mListView.setOverscrollHeader(null);
		   mListView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
		   mListView.setSelector(R.drawable.selector_button_color_red_noshape);
		   mListView.setVerticalScrollBarEnabled(true);
	      // mList.setVerticalScrollBarEnabled(false);
	       mRefreshLayout.setPullLoadEnabled(true);
	       mRefreshLayout.setPullRefreshEnabled(true);
	      
	       ((FooterLoadingLayout) mRefreshLayout.getFooterLoadingLayout())
	                .setNoMoreDataText("已经到底啦");
	       mRefreshLayout.setHasMoreData(false);
	       mRefreshLayout.setOnRefreshListener(new OnRefreshListener<ListView>() {
	            @Override
	            public void onPullDownToRefresh(
	                    PullToRefreshBase<ListView> refreshView) {
	            	refreshTask();
	            }

	          
				@Override
	            public void onPullUpToRefresh(
	                    PullToRefreshBase<ListView> refreshView) {
					if(havemore)
						loadMoreTask();
						else
							mRefreshLayout.setHasMoreData(false);
	            }
	        });
		
		mylistViewAdapter = new ImageListAdapter_pl(View_.getContext(), mplList,
				R.layout.item_pl, new String[] { "smallImg"},
				new int[] { R.id.pl_image,R.id.pl_name ,R.id.pl_time,R.id.pl_con},"nreply");
		
		  mListView.setAdapter(mylistViewAdapter);
	}
	
	private void refreshTask() {
		// TODO Auto-generated method stub
		
		get();
		Isrefresh=true;
	
	}
	private void loadMoreTask() {
		// TODO Auto-generated method stub
		//LoadData();
		
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		refreshTask();
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		nei_zxpl.this.getActivity().unregisterReceiver(myReceiver);
	}
	public void onResume() {
	    super.onResume();
	    MobclickAgent.onPageStart("nei_zxpl"); 
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPageEnd("nei_zxpl"); 
	}
	
}
