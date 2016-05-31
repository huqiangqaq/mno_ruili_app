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
import com.ab.view.sliding.AbSlidingPlayView;
import com.android.volley.toolbox.NetworkImageView;
import com.umeng.analytics.MobclickAgent;

import mno.mylistview.FooterLoadingLayout;
import mno.mylistview.PullToRefreshBase;
import mno.mylistview.PullToRefreshList;
import mno.mylistview.PullToRefreshBase.OnRefreshListener;
import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.ct.CycleIndicator;
import mno.ruili_app.ct.PageFlipper;
import mno.ruili_app.main.HomeFragment;
import mno_ruili_app.adapter.ImageListAdapter;
import mno_ruili_app.adapter.ImageListAdapter_zx;
import mno_ruili_app.adapter.tv;
import mno_ruili_app.adapter.zx;
import mno_ruili_app.home.home_video;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.RequestType.Type;
import mno_ruili_app.net.getlisthandler;
import mno_ruili_app.net.loginhandler;
import mno_ruili_app.net.webpost;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class zxfragment extends Fragment {
	View View_;
	private List<Map<String, Object>> list = null;
	private List<Map<String, String>> newList = new  ArrayList<Map<String, String>>();
	private PullToRefreshList mRefreshLayout;
	private ListView mListView;
	private int currentPage = 1;
	
	private ArrayList<String> mPhotoList = new ArrayList<String>();
	
	  ArrayList<zx>  mzxList = new ArrayList<zx>();
	  ImageListAdapter_zx mylistViewAdapter;
	  boolean IsHandler_=false; //是否处理中
	  boolean Isrefresh=false;
	  boolean havemore=true;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View_ = inflater.inflate(R.layout.fmt_zx, container,false);		
		init();
		return View_;		
	}
	private void init() {
		// TODO Auto-generated method stub
		   mRefreshLayout = (PullToRefreshList)View_.findViewById(R.id.mListView);
		   mListView = mRefreshLayout.getRefreshView();
		   mListView.setDivider(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
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
	       
	       image();
	       mylistViewAdapter = new ImageListAdapter_zx(View_.getContext(), mzxList,
					R.layout.item_list, new String[] { "itemsIcon" ,"itemstext","itemstext2"},
					new int[] { R.id.itemsIcon ,R.id.itemstext,R.id.itemstext2});
	      
	       mListView.setAdapter(mylistViewAdapter);
	       mListView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					Intent intent = new Intent(zxfragment.this.getActivity(),nei_zx.class);
					Constant.zxid=mzxList.get(position-1).getid();
					
					Constant.zxtitle=mzxList.get(position-1).getreplyTotal().toString();
					intent.putExtra("ID",mzxList.get(position-1).getid());  
				
					zxfragment.this.startActivity(intent);
				}
	    		
	    	});
	      
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
					// TODO Auto-generated method stub
					try {
						if(Isrefresh)
						{
							mzxList.clear();
							havemore=true;
							mRefreshLayout.setHasMoreData(true);
							mRefreshLayout.onPullDownRefreshComplete();
							Isrefresh=false;
						}
						mRefreshLayout.onPullUpRefreshComplete();
						
						JSONArray ad=  new JSONArray( response.getString("ad") );
						int adnum=ad.length();
						
						newList.clear();
						for(int i=0;i< adnum; i++){							 
							 JSONObject adjson=ad.getJSONObject(i);
							 String id=adjson.getString("id");
							 String title="      "+adjson.getString("title");
							 String coverImg=adjson.getString("coverImg");
							 
							 Map<String, String> map=new HashMap <String, String>();
							 map.put("id", id);
							 map.put("title", title);
							 map.put("coverImg", RequestType.getWebUrl_(coverImg));
							 newList.add(map);
							// mFrom[i]=id;
						}
						addimg();
						 
						JSONArray  data=  new JSONArray( response.getString("list") );
						int length=data.length();
						havemore=true;
						if(length==0)
						{
							havemore=false;
							mRefreshLayout.setHasMoreData(false);
						}
					    for(int i=0;i<= length; i++){
						    type_json =   data.getJSONObject(i);
						    //listData.add(data.getJSONObject(i).getString("Name"));
						    String name =  type_json.getString("title");
						    String content =  type_json.getString("content");
						    String id =  type_json.getString("id");
						    String imgurl=type_json.getString("coverImg");
						    String replyTotal=type_json.getString("replyTotal");
						    final zx mzx = new zx(name,content, id,imgurl,replyTotal);
						    
							mzxList.add(mzx);
							mylistViewAdapter.notifyDataSetChanged();
					    }
					    					   
					    IsHandler_ = false;											    
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			LoadData();
	}
	getlisthandler handler_;
	JSONObject type_json;
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
			   
				Intent intent = new Intent(View_.getContext(),nei_zx.class);
				intent.putExtra("ID",newList.get(position).get("id")); 
				//Constant.itemid=newList.get(position).get("id");
				Constant.zxid=newList.get(position).get("id");
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
	
	public void LoadData()
	{

		 handler_.getlist(zxfragment.this.getActivity(), "10", "1","3",Type.getNewsList);
	}
	
	/**/
	
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
		handler_.getlist(zxfragment.this.getActivity(), "10", ""+i,"3",Type.getNewsList);
		
	}
}
