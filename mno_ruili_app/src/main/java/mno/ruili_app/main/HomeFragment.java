package mno.ruili_app.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.ct.CycleIndicator;
import mno.ruili_app.ct.PageFlipper;
import mno.ruili_app.ct.ScreenUtil;
import mno.mylistview.FooterLoadingLayout;
import mno.mylistview.PullToRefreshBase;
import mno.mylistview.PullToRefreshList;
import mno.mylistview.PullToRefreshBase.OnRefreshListener;
import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.myapplication;
import mno.ruili_app.ct.MessageBox;
import mno_ruili_app.adapter.pullListAdapter;
import mno_ruili_app.adapter.ImageGridAdapter;
import mno_ruili_app.adapter.ImageListAdapter;
import mno_ruili_app.adapter.tv;
import mno_ruili_app.home.home_hyvideo;
import mno_ruili_app.home.home_mfvideo;
import mno_ruili_app.home.home_video;
import mno_ruili_app.home.home_zbvideo;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.getlisthandler;
import mno_ruili_app.net.webpost;
import mno_ruili_app.net.RequestType.Type;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class HomeFragment extends  Fragment {
	GridView mGridView;
	private PullToRefreshList mRefreshLayout;
    private ListView mList;
	
	ArrayList<tv>  mTvList;
	ImageGridAdapter myGridViewAdapter;
	getlisthandler handler_;
	
	LinearLayout mf,zb,hy,head1,head2,topview,mf2,zb2,hy2;
	TextView home_bt;
	View View_;
	JSONObject type_json,adjson;
	boolean IsHandler_=false; //是否处理中
	boolean Isrefresh=false;
	pullListAdapter pullListAdapter;
    JSONArray  ad;
    int start=0;
    private boolean scrollFlag = false;// 标记是否滑动
    private int lastVisibleItemPosition = 0;// 标记上次滑动位置
    LinearLayout layout1,layout2,toplayout,top,topbut;
    RelativeLayout top_tietle;
    LinearLayout layout;
    private List<Map<String, String>> newList = new  ArrayList<Map<String, String>>();
    myapplication application;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 View_ = inflater.inflate(R.layout.fmt_home, container,false);
		 top_tietle= (RelativeLayout)View_.findViewById(R.id.top_tietle);
		 topbut= (LinearLayout)View_.findViewById(R.id.topbut);
		/* LinearLayout layout2 = (LinearLayout) LayoutInflater.from(HomeFragment.this.getActivity()).inflate(R.layout.select_view2, null);
		 topbut.addView(layout2);*/
		 mf2=(LinearLayout) View_.findViewById(R.id.home_but_mf);
		 zb2=(LinearLayout) View_.findViewById(R.id.home_but_zb);
	     hy2=(LinearLayout) View_.findViewById(R.id.home_but_hy);
		 mf2.setOnClickListener(new MyListener());  
		 zb2.setOnClickListener(new MyListener());  
		 hy2.setOnClickListener(new MyListener());
		 topbut.setVisibility(View.GONE);
		 init();		
		 return View_;
	    }
	    
		private void init() {
		// TODO Auto-generated method stub
			   mRefreshLayout = (PullToRefreshList)View_.findViewById(R.id.blog_swiperefreshlayout);
			   mList = mRefreshLayout.getRefreshView();
		       mList.setDivider(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		       mList.setOverscrollFooter(null);
		       mList.setOverscrollHeader(null);
		       mList.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
		       mList.setVerticalScrollBarEnabled(false);
		      // mList.setVerticalScrollBarEnabled(false);
		       mRefreshLayout.setPullLoadEnabled(true);
		       mRefreshLayout.setPullRefreshEnabled(true);
		       
		       ((FooterLoadingLayout) mRefreshLayout.getFooterLoadingLayout())
		                .setNoMoreDataText("更多视频内容请前往个视频专区");
		       mRefreshLayout.setHasMoreData(false);
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
			ArrayList<tv>   mTvList = new ArrayList<tv>();
			final tv mtv = new tv("4","1", "1","");
			mTvList.add(mtv);
			pullListAdapter = new pullListAdapter(View_.getContext(), mTvList,ad,
						R.layout.item_list_null, new String[] { "itemsIcon" },
						new int[] { R.id.textView8 });
		    image();
		    mList.setAdapter(pullListAdapter);
		    mList.setOnScrollListener(new OnScrollListener() {
		    	 
	            @Override
	            public void onScrollStateChanged(AbsListView view, int scrollState) {
	                // TODO Auto-generated method stub
	                switch (scrollState) {
	                // 当不滚动时
	                case OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
	                    scrollFlag = false;
	                   // Log.d("web", "posision -> " + ScreenUtil.getScreenViewBottomHeight(layout2));
	                    //if (ScreenUtil.getScreenViewTopHeight(toplayout) == 0)
	                    //mRefreshLayout.setPullRefreshEnabled(true);
	                    if (ScreenUtil.getScreenViewTopHeight(layout) <= 0) {//判断layout到顶部的高度
	  	                    //显示三种视频
	                		topbut.setVisibility(view.VISIBLE);	                		 
	  	                }else{
	                    	topbut.setVisibility(view.GONE);
	                	 }
	                    break;
	                case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时	                
	                    scrollFlag = true;
	                    break;
	                case OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
	                    scrollFlag = false;
	                    break;
	                }
                }
   
                @Override
                public void onScroll(AbsListView view, int firstVisibleItem,
                        int visibleItemCount, int totalItemCount) {
                    // 当开始滑动且ListView底部的Y轴点超出屏幕最大范围时，显示或隐藏顶部按钮
                	 if (ScreenUtil.getScreenViewTopHeight(layout) <= 0) {	  	                       
                		 topbut.setVisibility(view.VISIBLE);	                		 
  	                  }else{
                		 topbut.setVisibility(view.GONE);
                	 }
                    if (scrollFlag) {
                    	/* if (ScreenUtil.getScreenViewTopHeight(toplayout) == 0) {
  	                       
  	                    	//MessageBox.Show(View_.getContext(), "top");
  	                    	mRefreshLayout.setPullRefreshEnabled(true);
  	                    }
                    	 else
                    		 mRefreshLayout.setPullRefreshEnabled(false);*/

                    }
                }
            });			 		    										
		}
		protected void refresh() {
			mTvList.clear();			
			LoadData("");
			Isrefresh=true;
		}	
		private void image() {
	 		toplayout = new LinearLayout(View_.getContext());
	 		top = new LinearLayout(View_.getContext());
	 		top.setLayoutParams(new LinearLayout.LayoutParams(Constant.displayWidth, Constant.displayWidth/16*8));
	 		toplayout.addView(top);
	 		/*LayoutParams lptop;       
			lptop=toplayout.getLayoutParams();
			lptop.width=100;
	        lptop.height=Constant.displayWidth/16*8;  
	        toplayout.setLayoutParams(lptop);*/
			mList.addHeaderView(toplayout);
			layout = (LinearLayout) LayoutInflater.from(HomeFragment.this.getActivity()).inflate(R.layout.select_view2, null);
	    	mf=(LinearLayout) layout.findViewById(R.id.home_but_mf);
			zb=(LinearLayout) layout.findViewById(R.id.home_but_zb);
			hy=(LinearLayout) layout.findViewById(R.id.home_but_hy);
			mf.setOnClickListener(new MyListener());  
			zb.setOnClickListener(new MyListener());  
			hy.setOnClickListener(new MyListener());
			mList.addHeaderView(layout);					
			layout2 = (LinearLayout) LayoutInflater.from(HomeFragment.this.getActivity()).inflate(R.layout.select_viewlist, null);
	    	
			mGridView = (GridView)layout2.findViewById(R.id.mGridView);
			
			mGridView.setGravity(Gravity.CENTER);
			mGridView.setHorizontalSpacing(10);
			mGridView.setPadding(0, 0, 0, 0);
			mGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
			mGridView.setVerticalSpacing(10);
			mTvList = new ArrayList<tv>();							
			// 使用自定义的Adapter
			myGridViewAdapter = new ImageGridAdapter(View_.getContext(),Constant.displayWidth, mTvList,
					R.layout.item_grid, new String[] { "itemsIcon" },
					new int[] { R.id.itemsIcon ,R.id.itemsImage,R.id.itemstext,R.id.itemstext2});														
			/*int totalHeight = 0;  
	        for(int i = 0;i < 4/2; i++){  
	            View itemView = myGridViewAdapter.getView(i, null, mGridView);  
	            itemView.measure(0, 0);  
	            totalHeight += itemView.getMeasuredHeight();  
	            
	        }  */
	        ViewGroup.LayoutParams layoutParams = mGridView.getLayoutParams();  
	        layoutParams.height =Constant.displayWidth/3*6+60;//总行高+每行的间距  
	        mGridView.setLayoutParams(layoutParams); 
			mGridView.setAdapter(myGridViewAdapter);
			
			mGridView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent intent = new Intent(HomeFragment.this.getActivity(),home_video.class);
					intent.putExtra("ID",mTvList.get(position).getid()); 
					Constant.itemid=mTvList.get(position).getid();
					HomeFragment.this.getActivity().startActivity(intent);
				}	    		
	    	});
			mList.addHeaderView(layout2);
		   //设置监听器		      						      						       						       
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
						if(Isrefresh){
						   mRefreshLayout.onPullDownRefreshComplete();
						}
						JSONArray  data=  new JSONArray( response.getString("list") );
						ad=  new JSONArray( response.getString("ad") );
						int adnum=ad.length();
						newList.clear();
						for(int i=0;i< adnum; i++){							 
							 adjson=ad.getJSONObject(i);
							 String id=adjson.getString("id");
							 String title="      "+adjson.getString("title");
							 String coverImg=adjson.getString("coverImg");
							 Map<String, String> map=new HashMap <String, String>();
							 map.put("id", id);
							 map.put("title",title);
							 map.put("coverImg", RequestType.getWebUrl_(coverImg));
							 newList.add(map);						
						  }
						addimg();												
						int length=data.length();
					    for(int i=0;i<= length; i++){
						    type_json =   data.getJSONObject(i);
						    //listData.add(data.getJSONObject(i).getString("Name"));
						    String name =  type_json.getString("title");
						    String content =  type_json.getString("desc");
						    String id =  type_json.getString("id");
						    String imgurl=type_json.getString("coverImg");
						    
						    final tv mtv = new tv(id,name, content,imgurl);
						    //   if(type_json.getString("typeName").equals("视觉"))
						    mTvList.add(mtv);					    					    
						    myGridViewAdapter.notifyDataSetChanged();
						    pullListAdapter.notifyDataSetChanged();
					    }					  
					    IsHandler_ = false;											    
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			};			
			LoadData("");
		}
	
		protected void addimg() {
			// TODO Auto-generated method stub
			final PageFlipper mypic;
			final CycleIndicator indicator0;
			top.removeAllViews();
		    LayoutInflater inflate = (LayoutInflater)View_.getContext(). getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 		layout1 = (LinearLayout)inflate.inflate(R.layout.mytoppic, null);//轮播图
			indicator0 = (CycleIndicator)layout1. findViewById(R.id.indicator0);
			mypic=(PageFlipper)layout1.findViewById(R.id.mypic);
			top.addView(layout1);
	 		indicator0.setPageCount(newList.size());
	 		LayoutParams lptop;       
			lptop=mypic.getLayoutParams();
	        //lp.width=100;
			lptop.height=Constant.displayWidth/16*8;        
	        mypic.setLayoutParams(lptop);
	        mypic.setContent(newList);
	        mypic.setOnItemClickListener(new PageFlipper.OnItemClickListener() {
				
				@Override
				public void onClick(int position) {
					// TODO Auto-generated method stub				   
					Intent intent = new Intent(HomeFragment.this.getActivity(),home_video.class);
					intent.putExtra("ID",newList.get(position).get("id")); 
					Constant.itemid=newList.get(position).get("id");
					HomeFragment.this.getActivity().startActivity(intent);
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
					
				}
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					
				}
	        });	        	        
		}

		public void LoadData(String typeId){			
			 handler_.getlist(HomeFragment.this.getActivity(),"1","", "20", "1","2",Type.getcourse);			 
		}		
	    private class MyListener implements OnClickListener{  
			  
	        @Override  
	        public void onClick(View v) {  
	            // TODO Auto-generated method stub  
	        	if(v.getId()==R.id.home_but_mf){
	        		Intent intent0 = new Intent(HomeFragment.this.getActivity(), home_mfvideo.class); 				    
					startActivity(intent0);
	        	}
	        	else if(v.getId()==R.id.home_but_hy){
	        		Intent intent0 = new Intent(HomeFragment.this.getActivity(), home_hyvideo.class); 				    
					startActivity(intent0);
	        	}
	        	else if(v.getId()==R.id.home_but_zb){
//	        		myapplication application = (myapplication) HomeFragment.this.getActivity().getApplication();
//		        	if(application.getName().contains("arm64"))
//					{
//						MessageBox.ShowLong(HomeFragment.this.getActivity(), "亲爱的 ，直播功能还在努力完善中，请稍等！");
//					}
//		        	else 
//		        	{						
	        		Intent intent0 = new Intent(HomeFragment.this.getActivity(), home_zbvideo.class); 				    
					startActivity(intent0);
		        	//}
	        	}
	        }
        }
		//友盟的页面统计
		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			MobclickAgent.onPageStart("HomeFragment");			
		}
	    @Override
	    public void onPause() {
	    	// TODO Auto-generated method stub
	    	super.onPause();
	    	MobclickAgent.onPageEnd("HomeFragment");
	    }	 
}
