package mno_ruili_app.nei;

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
import mno.ruili_app.ct.CycleIndicator;
import mno.ruili_app.ct.PageFlipper;
import mno_ruili_app.adapter.ImageListAdapter_wd;
import mno_ruili_app.adapter.wd;
import mno_ruili_app.net.RequestType.Type;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.getlisthandler;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
public class wdfragment extends Fragment{
	View View_;
	private List<Map<String, Object>> list = null;
	private List<Map<String, String>> newList = new  ArrayList<Map<String, String>>();
	private PullToRefreshList mRefreshLayout;
	private ListView mListView;
	private int currentPage = 1;
	private ArrayList<String> mPhotoList = new ArrayList<String>();
	
	ArrayList<wd>   mList = new ArrayList<wd>();
	boolean IsHandler_=false; //是否处理中
	boolean Isrefresh=false;
	getlisthandler handler_;
	JSONObject type_json;
	ImageListAdapter_wd mylistViewAdapter;
	 boolean havemore=true;
	//private String[] mFrom;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View_ = inflater.inflate(R.layout.fmt_wd, container,
				false);
		init();
		refreshTask();
		return View_;
		
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
	       mRefreshLayout.setHasMoreData(true);
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
	       
		   mylistViewAdapter = new ImageListAdapter_wd(View_.getContext(), mList,
					R.layout.item_list_wd, new String[] { "itemsIcon" },
					new int[] { R.id.nei_wd_image ,R.id.wdname,R.id.wd_tag,R.id.wdzt,R.id.wdtime,R.id.wdcontent,R.id.wdbestname,R.id.wdbestcon,R.id.wd_fs,R.id.wdhd,R.id.wdbeshd,R.id.wdrs},1);
	       image();
	       mListView.setAdapter(mylistViewAdapter);
	       
	       mListView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent intent = new Intent(wdfragment.this.getActivity(),nei_wdxq.class);
					Constant.itemid=mList.get(position-1).getId();
					Constant.itemuid=mList.get(position-1).getuid();
					Constant.itemzt=mList.get(position-1).getzt();
					
					//intent.putExtra("ID",mList.get(position-1).getId());  
					wdfragment.this.startActivity(intent);
				}
	    		
	    	});
	       /*
	        * 数据的加载
	        */
	       handler_ = new getlisthandler(){
	    		@Override
				public void OnError(int code, String mess) {
					// TODO Auto-generated method stub
					super.OnError(code, mess);
					 mRefreshLayout.onPullDownRefreshComplete();
					 mRefreshLayout.onPullUpRefreshComplete();
				}

				@Override
				public void OnSuccess(JSONObject response) {
					try {
						if(Isrefresh){
							mList.clear();							
							mRefreshLayout.onPullDownRefreshComplete();
							Isrefresh=false;
						}						
						mRefreshLayout.onPullUpRefreshComplete();
						//轮播图数据
						JSONArray ad=  new JSONArray( response.getString("ad") );
						int adnum=ad.length();						
						newList.clear();
						for(int i=0;i< adnum; i++){							 
							 JSONObject adjson=ad.getJSONObject(i);
							 String id=adjson.getString("id");
							 String title="      "+adjson.getString("title");
							 String coverImg=adjson.getString("pics");						
							 Map<String, String> map=new HashMap <String, String>();
							 map.put("id", id);
							 map.put("title", title);
							 map.put("coverImg", RequestType.getWebUrl_(coverImg));
							 newList.add(map);
						 }						
						addimg();	
						//listview中的数据
						JSONArray  data=  new JSONArray( response.getString("list") );
						int length=data.length();
						havemore=true;
						if(length==0){
							havemore=false;
							mRefreshLayout.setHasMoreData(false);
						}
					    for(int i=0;i< length; i++){
						    type_json =   data.getJSONObject(i);
						    //listData.add(data.getJSONObject(i).getString("Name"));
						    String name =  type_json.getString("username");
						    String content =  type_json.getString("content");
						    String id =  type_json.getString("questionId");
						    String imgurl=type_json.getString("bigImg");
						    String uid=type_json.getString("uid");
						    String tag =  type_json.getString("tag");
						    String statusName =  type_json.getString("statusName");
						    String create_at =  type_json.getString("create_at");
						    String bestAnwCon =  type_json.getString("bestAnwCon");
						    String bestAnwUname=type_json.getString("bestAnwUname");
						    String needPoint=type_json.getString("needPoint");
						    String answerTotal=type_json.getString("answerTotal");
						    final wd mwd = new wd(name,content, id,uid,imgurl, tag, statusName, create_at, bestAnwCon, bestAnwUname,needPoint,answerTotal);
						    
							mList.add(mwd);
						
					    }
					    mylistViewAdapter.notifyDataSetChanged();
					    IsHandler_ = false;
						
					    
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			//LoadData();
	}
	/*
	 * 轮播图的加载
	 */
	protected void addimg() {
		// TODO Auto-generated method stub
		final PageFlipper mypic;
		final CycleIndicator indicator0;
		top.removeAllViews();
	    LayoutInflater inflate = (LayoutInflater)View_.getContext(). getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 		layout1 = (LinearLayout)inflate.inflate(R.layout.mytoppic, null);
		indicator0 = (CycleIndicator)layout1. findViewById(R.id.indicator0);
		mypic=(PageFlipper)layout1.findViewById(R.id.mypic);
		top.addView(layout1);
 		indicator0.setPageCount(newList.size());
 		LayoutParams lptop;       
		lptop=mypic.getLayoutParams();
        //lp.width=100;
		lptop.height=Constant.displayWidth/16*7;        
        mypic.setLayoutParams(lptop);
        mypic.setContent(newList);
        mypic.setOnItemClickListener(new PageFlipper.OnItemClickListener() {
			
			@Override
			public void onClick(int position) {
				// TODO Auto-generated method stub
			   
				Intent intent = new Intent(View_.getContext(),nei_wdxq.class);
				intent.putExtra("ID",newList.get(position).get("id")); 
				Constant.itemid=newList.get(position).get("id");
				View_.getContext().startActivity(intent);
			}
		});
		mypic.setOnPageChangeListener(new PageFlipper.OnPageChangeListener() {
	             
	            @Override
	            public void onPageSelected(int index) {
	                //ndicator0.onPageSelected(index);
	            	 //MessageBox.Show(HomeFragment.this.getActivity(), index+"");
	                if(index==newList.size())
	                	indicator0.onPageSelected(0);
	                else if(index==newList.size()+1)
	                	indicator0.onPageSelected(newList.size()-1);
	                else 
	                	indicator0.onPageSelected(index);
	            }

				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}
	        });
        
        
	}
	LinearLayout top,layout1,toplayout;
	private void image() {
		// TODO Auto-generated method stub
		
		//LinearLayout topview= (LinearLayout)View_.findViewById(R.id.topview);
		toplayout = new LinearLayout(View_.getContext());
 		top = new LinearLayout(View_.getContext());
 		top.setLayoutParams(new LinearLayout.LayoutParams(Constant.displayWidth, Constant.displayWidth/16*7));
 		toplayout.addView(top);
		
 		
 		mListView.addHeaderView(toplayout);
		//topview.addView(top);
			
			
			
	}
	public void LoadData(){
		/*if(!IsHandler_)
		{
		 IsHandler_ = true;*/
		 handler_.getlist(wdfragment.this.getActivity(), "10", "1","3",Type.getQuesList);		
	}
	
	
	private void refreshTask() {
		// TODO Auto-generated method stub
		i=1;
		LoadData();
		Isrefresh=true;
	
	}
	int i=1;
	private void loadMoreTask() {
		// TODO Auto-generated method stub
		//LoadData();
		i++;
		handler_.getlist(wdfragment.this.getActivity(), "10", i+"","3",Type.getQuesList);
	
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		//refreshTask();
		super.onStart();
	}
}
