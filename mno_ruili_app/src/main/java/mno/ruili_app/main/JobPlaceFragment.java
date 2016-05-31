package mno.ruili_app.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hyphenate.chat.EMClient;
import com.umeng.analytics.MobclickAgent;

import mno.mylistview.FooterLoadingLayout;
import mno.mylistview.PullToRefreshBase;
import mno.mylistview.PullToRefreshList;
import mno.mylistview.PullToRefreshBase.OnRefreshListener;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.my.AddEmpMsg;
import mno.ruili_app.my.JobPlace_search;
import mno.ruili_app.my.Job_details;
import mno.ruili_app.my.Resume;
import mno.ruili_app.my.Talk_message;
import mno.ruili_app.my.Worker_details;
import mno.ruili_app.my.my_wd;
import mno.ruili_app.my.mylogin;
import mno_ruili_app.adapter.ImageListAdapter_job;
import mno_ruili_app.adapter.ImageListAdapter_worker;
import mno_ruili_app.entity.Job;
import mno_ruili_app.entity.Worker;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class JobPlaceFragment extends Fragment{
	JSONObject type_json;
	webhandler handler_,handler_2;
	TextView my_butwen,my_butda,tv_job,tv_worker,tsimg;
	ImageView iv_find,iv_resume,iv_message;
	LinearLayout ll_worker,ll_job;
	private PullToRefreshList mRefreshLayout= null;
	private PullToRefreshList mRefreshLayout2 = null;
	List<Job>   mList = new ArrayList<Job>();
	List<Worker>   mList2 = new ArrayList<Worker>();
	private ListView mListView;
	private ListView mListView2;
	ImageListAdapter_job mylistViewAdapter;
	ImageListAdapter_worker mylistViewAdapter2;
	private int currentPage1 = 1;
	private int currentPage2 = 1;
	boolean havemore=true;
	boolean Isrefresh=false;
	private LocalBroadcastManager broadcastManager;
	private BroadcastReceiver broadcastReceiver;
	String myid,state,mycompanyName,text;
	View View_;
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View_ = inflater.inflate(R.layout.my_jobplace_item, container,false);		
		if(!appuser.getInstance().IsLogin()){
			myid="";
	    }else{
	    	myid=appuser.getInstance().getUserinfo().uid.toString();	
	    }
		init();
		//发送广播
		Intent intent=new Intent();
		intent.setAction("timsg");
		JobPlaceFragment.this.getActivity().sendBroadcast(intent);
		
		loadJobMsg();
		loadWorkerMsg();	
		return View_ ;
	}
	//1.找工作列表数据的获得
		private void loadJobMsg() {
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
						
						JSONObject data=response.getJSONObject("data");
						JSONArray list = data.getJSONArray("post_list");
						int length=list.length();
						havemore=true;
						if(length==0){
							havemore=false;
							mRefreshLayout.setHasMoreData(false);
						}
					    for(int i=0;i< length; i++){
						    type_json =list.getJSONObject(i);
						    
						    String jobId=type_json.getString("id");
						    String jobName=type_json.getString("job_name");
						    String salary=type_json.getString("salary");
						    String workTime =  type_json.getString("job_year");
						    String education=  type_json.getString("education");
						    String workPlace =  type_json.getString("job_addr");		
						    //亮点名字
						    JSONArray points =  type_json.getJSONArray("points");
						    String[] jobMsg = new String[points.length()];					    
						    for (int j = 0; j < points.length(); j++) {
						    	  JSONObject item=points.getJSONObject(j);
						    	  String point_name = item.getString("point_name");
						    	  jobMsg[j] = point_name;
							}

						    String jobImagePath =  type_json.getString("photo");
						    String nickName=type_json.getString("username");
						    String workLevel=type_json.getString("profession");
						    String companyName=type_json.getString("comp_name");
						    String updateTime=type_json.getString("update_time");
						    
						    final Job job = new Job(jobId, jobName, salary, workTime, education, workPlace, jobMsg, jobImagePath, nickName, workLevel, companyName, updateTime);
						    
							mList.add(job);						    
					    }
					    mylistViewAdapter.notifyDataSetChanged();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			Map<String, String> params = new HashMap<String, String>();
			params.put("page", currentPage1+"");
			handler_.SetRequest(new RequestType("ZP",Type.getPostList),params);
		}
		//2.找牛人列表数据的获得
		private void loadWorkerMsg(){
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
						JSONObject data=response.getJSONObject("data");
						state=data.getString("state");
						JSONArray list = data.getJSONArray("resume_list");
						int length=list.length();
						havemore=true;
						if(length==0){
							havemore=false;
							mRefreshLayout2.setHasMoreData(false);
						}
					    for(int i=0;i< length; i++){
						    type_json = list.getJSONObject(i);
						    String workerId =  type_json.getString("id");
						    String jobName =  type_json.getString("job_name");
						    String workTime =  type_json.getString("job_year");						    
						    String education=  type_json.getString("education");
						    String salary=  type_json.getString("salary");
						    String workPlace=  type_json.getString("job_addr");
						    
						  //亮点名字
						    JSONArray points =  type_json.getJSONArray("points");
						    String[] workerMsg = new String[points.length()];					    
						    for (int j = 0; j < points.length(); j++) {
						    	  JSONObject item=points.getJSONObject(j);
						    	  String point_name = item.getString("point_name");
						    	  workerMsg[j] = point_name;
							}					    
						    String workImagePath=  type_json.getString("photo");
						    String nickName=  type_json.getString("username");
						    String updateTime=  type_json.getString("update_time");
						    
						    final Worker worker = new Worker(workerId, jobName, workTime, education, salary, workPlace, workerMsg, workImagePath, nickName, updateTime);
						    
							mList2.add(worker);					    
					    }
					    mylistViewAdapter2.notifyDataSetChanged();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};	
			Map<String, String> params2 = new HashMap<String, String>();
			params2.put("page", currentPage2+"");
			params2.put("uid", myid);
			handler_2.SetRequest(new RequestType("ZP",Type.getResumeList),params2);
		}
		/**
		 * 刷新未读消息数
		 */
		public void updateUnreadLabel() {
			int count = getUnreadMsgCountTotal();
			if (count > 0) {
				tsimg.setVisibility(View.VISIBLE);
			} else {
				tsimg.setVisibility(View.INVISIBLE);
			}
		}
		/**
		 * 获取未读消息数
		 * 
		 * @return
		 */
		public int getUnreadMsgCountTotal() {
			int unreadMsgCountTotal = 0;
			unreadMsgCountTotal = EMClient.getInstance().chatManager().getUnreadMsgsCount();		
			return unreadMsgCountTotal;
		}
		//注册消息红点的广播
		private void registerBroadcastReceiver() {
	        broadcastManager = LocalBroadcastManager.getInstance(JobPlaceFragment.this.getActivity());
	        IntentFilter intentFilter = new IntentFilter();
	        intentFilter.addAction("timsg");
	        broadcastReceiver = new BroadcastReceiver() {
	            
	            @Override
	            public void onReceive(Context context, Intent intent) {
	                updateUnreadLabel();                               
	            }
	        };
	        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
	    }
		//注销消息红点的广播
		private void unregisterBroadcastReceiver(){
		    broadcastManager.unregisterReceiver(broadcastReceiver);
		}
		
		private void init() {
			iv_find=(ImageView) View_.findViewById(R.id.iv_find);
			iv_message=(ImageView) View_.findViewById(R.id.iv_message);
			iv_resume=(ImageView) View_.findViewById(R.id.iv_resume);
			ll_job=(LinearLayout) View_.findViewById(R.id.ll_job);
			ll_worker=(LinearLayout) View_.findViewById(R.id.ll_worker);
			iv_find.setOnClickListener(new MyListener());
			iv_message.setOnClickListener(new MyListener());
			iv_resume.setOnClickListener(new MyListener());
			ll_job.setOnClickListener(new MyListener());
			ll_worker.setOnClickListener(new MyListener());
			my_butwen=(TextView) View_.findViewById(R.id.my_butwen);
			my_butda=(TextView) View_.findViewById(R.id.my_butda);
			tv_job=(TextView) View_.findViewById(R.id.tv_job);
			tv_worker=(TextView) View_.findViewById(R.id.tv_worker);
			//消息提示
			tsimg=(TextView) View_.findViewById(R.id.tsimg);				
			
			mRefreshLayout=(PullToRefreshList) View_.findViewById(R.id.mListView);
			mListView = mRefreshLayout.getRefreshView();
			mRefreshLayout2=(PullToRefreshList) View_.findViewById(R.id.mListView2);
			mListView2 = mRefreshLayout2.getRefreshView();
			//1.找工作上下拉刷新
			mListView.setDivider(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
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
	    			params.put("page", currentPage1+"");
	    			handler_.SetRequest(new RequestType("ZP",Type.getPostList),params);
	    			Isrefresh=true;
	            }         
				@Override
	            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
					if(havemore){
						currentPage1++;
						loadJobMsg();				     
					}
					else{
						mRefreshLayout.setHasMoreData(false);
					}
	             }
				
	         });
	        //2.找牛人上下拉刷新
	        mListView2.setDivider(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
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
//	            	currentPage2=1;
//	            	Map<String, String> params2 = new HashMap<String, String>();
//	            	params2.put("page", currentPage2+"");
//	        		handler_2.SetRequest(new RequestType("ZP",Type.getResumeList),params2);
//	    			Isrefresh=true;
	            }         
				@Override
	            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
					if(!appuser.getInstance().IsLogin()){
						AlertDialog.Builder builder = new AlertDialog.Builder(JobPlaceFragment.this.getActivity());
						 builder.setMessage("      想要查看更多的简历信息？\n      请先登录个人账号哦！");
						 builder.setTitle("提示");				
						 builder.setPositiveButton("去登录",new DialogInterface.OnClickListener() {
							 @Override
							 public void onClick(DialogInterface dialog,int which) {
								 dialog.dismiss();
//								 Intent intent =new Intent(JobPlaceFragment.this.getActivity(),mylogin.class);
//								 startActivity(intent);
								 appuser.getInstance().LoginToAct(JobPlaceFragment.this.getActivity(),  mylogin.class);
							 }
						 });
						 builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
							 @Override
							 public void onClick(DialogInterface dialog,int which) {
								 dialog.dismiss();
							 }
						 });
						 builder.create().show();
				    }else{
				    	if(state.equals("2")||state.equals("3")&&havemore){
							currentPage2++;
							loadWorkerMsg();				     					
						}else if(state.equals("1")){
							AlertDialog.Builder builder = new AlertDialog.Builder(JobPlaceFragment.this.getActivity());
							 builder.setMessage("      想要查看更多的简历信息？\n      请先完善企业信息哦！");
							 builder.setTitle("提示");				
							 builder.setPositiveButton("去完善",new DialogInterface.OnClickListener() {
								 @Override
								 public void onClick(DialogInterface dialog,int which) {
									 dialog.dismiss();
									 Intent intent =new Intent(JobPlaceFragment.this.getActivity(),AddEmpMsg.class);
									 startActivity(intent);
								 }
							 });
							 builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
								 @Override
								 public void onClick(DialogInterface dialog,int which) {
									 dialog.dismiss();
								 }
							 });
							 builder.create().show();
						}else{
							mRefreshLayout2.setHasMoreData(false);
						}	
				    }					
	             }			
	         });
	        //
			 mylistViewAdapter = new ImageListAdapter_job(JobPlaceFragment.this.getActivity(), mList,
						R.layout.list_item_job, new String[] { "itemsIcon" },
						new int[] { R.id.tv_jobName,R.id.tv_salary ,R.id.tv_workTime,R.id.tv_education,R.id.tv_workPlace,
					 R.id.fl_jobMsg,R.id.iv_jobImage,R.id.tv_nickName,R.id.tv_workLevel,R.id.tv_companyName,R.id.tv_updateTime});
			 mylistViewAdapter2 = new ImageListAdapter_worker(JobPlaceFragment.this.getActivity(), mList2,
						R.layout.list_item_worker, new String[] { "itemsIcon" },
						new int[] {R.id.tv_jobName,R.id.tv_salary ,R.id.tv_workTime,R.id.tv_education,R.id.tv_workPlace,
					 R.id.fl_workerMsg,R.id.iv_workerImage,R.id.tv_nickName,R.id.tv_updateTime});
		       
	         mListView.setAdapter(mylistViewAdapter);
	         mListView2.setAdapter(mylistViewAdapter2);
	       //1.找工作的点击事件
	       mListView.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {					
						Intent intent = new Intent(JobPlaceFragment.this.getActivity(),Job_details.class);
						//Constant.itemid=mList.get(position).getJobId();
						intent.putExtra("id", mList.get(position).getJobId());
						JobPlaceFragment.this.getActivity().startActivity(intent);										
				}   		
	    	});
	       //2.找牛人的点击事件
	       mListView2.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {								
					Intent intent=new Intent(JobPlaceFragment.this.getActivity(),Worker_details.class);
					//Constant.itemid=mList2.get(position).getWorkerId();
					intent.putExtra("id", mList2.get(position).getWorkerId());
					JobPlaceFragment.this.getActivity().startActivity(intent);			       					
				}	    		
	    	});       
		}
		//标题的点击事件
//		 String where="job";
//		 public void onclick(View v) {		 
//			 if(v.getId()==R.id.ll_job){			 
//				 mylistViewAdapter.notifyDataSetChanged();
//				 where="job";
//				 mListView.setVisibility(View.VISIBLE);
//				 mListView2.setVisibility(View.GONE);
//				 my_butwen.setTextColor(android.graphics.Color.parseColor("#ff6200"));
//				 my_butda.setTextColor(android.graphics.Color.parseColor("#919191"));			 
//	     		 tv_job.setBackgroundColor(android.graphics.Color.parseColor("#ff6200"));
//	     		 tv_worker.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
//			 }else if(v.getId()==R.id.ll_worker){			
//				 mylistViewAdapter2.notifyDataSetChanged();
//				 where="worker";
//				 mRefreshLayout2.setVisibility(View.VISIBLE);
//				 mListView2.setVisibility(View.VISIBLE);
//				 mListView.setVisibility(View.GONE);
//				 my_butda.setTextColor(android.graphics.Color.parseColor("#ff6200"));
//				 my_butwen.setTextColor(android.graphics.Color.parseColor("#919191"));			 
//	     		 tv_worker.setBackgroundColor(android.graphics.Color.parseColor("#ff6200"));
//	     		 tv_job.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
//			 }else if(v.getId()==R.id.iv_message){
//				//appuser.getInstance().LoginToAct(My_jobPlace_item.this,Talk_message.class);	
//				Intent intent = new Intent(JobPlaceFragment.this.getActivity(), Talk_message.class);   
//	    		startActivity(intent);
//			 }else if(v.getId()==R.id.iv_resume){
//				if(where.equals("job")){	
//					Intent intent = new Intent(JobPlaceFragment.this.getActivity(), Resume.class);   
//		    		startActivity(intent);
//				}else{	
//					Intent intent = new Intent(JobPlaceFragment.this.getActivity(), AddEmpMsg.class);   
//		    		startActivity(intent);
//				}
//			 }else if(v.getId()==R.id.iv_find){
//				 Intent intent = new Intent(JobPlaceFragment.this.getActivity(), JobPlace_search.class); 
//				 if(where.equals("job")){		
//					 intent.putExtra("job", 1);//1代表从工作进入搜索
//				 }else{		
//					 intent.putExtra("job", 2);//2.代表从牛人进入搜索
//				 }		    			 
//				 startActivity(intent);
//			 }
//		 }
		 @Override
		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			registerBroadcastReceiver();
		}
	    @Override
		public void onDestroy() {
			super.onDestroy();		
			unregisterBroadcastReceiver();	
		}
		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			updateUnreadLabel();
			MobclickAgent.onPageStart("JobPlaceFragment");
			if(!appuser.getInstance().IsLogin()){
				myid="";
		    }else{
		    	myid=appuser.getInstance().getUserinfo().uid.toString();	
		    }
			handler_2 = new webhandler(){
				@Override
				public void OnResponse(JSONObject response) {
					try {
						//mList2.clear();
						mRefreshLayout2.onPullUpRefreshComplete();
						JSONObject data=response.getJSONObject("data");
						state=data.getString("state");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};	
			Map<String, String> params2 = new HashMap<String, String>();
			params2.put("page", currentPage2+"");
			params2.put("uid", myid);
			handler_2.SetRequest(new RequestType("ZP",Type.getResumeList),params2);
		}
		@Override
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			MobclickAgent.onPageEnd("JobPlaceFragment");
		}
		@Override
		public void onStop() {
			// TODO Auto-generated method stub
			super.onStop();
		}
		@Override
		public void onSaveInstanceState(Bundle outState) {
			// TODO Auto-generated method stub
			super.onSaveInstanceState(outState);
		}
		String where="job";
		private class MyListener implements OnClickListener{
			@Override
			public void onClick(View v) {
				if(v.getId()==R.id.ll_job){
					 mylistViewAdapter.notifyDataSetChanged();
					 where="job";
					 mListView.setVisibility(View.VISIBLE);
					 mListView2.setVisibility(View.GONE);
					 my_butwen.setTextColor(android.graphics.Color.parseColor("#ff6200"));
					 my_butda.setTextColor(android.graphics.Color.parseColor("#919191"));			 
		     		 tv_job.setBackgroundColor(android.graphics.Color.parseColor("#ff6200"));
		     		 tv_worker.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));	        		
	        	}else if(v.getId()==R.id.ll_worker){
					 mylistViewAdapter2.notifyDataSetChanged();
					 where="worker";
					 mRefreshLayout2.setVisibility(View.VISIBLE);
					 mListView2.setVisibility(View.VISIBLE);
					 mListView.setVisibility(View.GONE);
					 my_butda.setTextColor(android.graphics.Color.parseColor("#ff6200"));
					 my_butwen.setTextColor(android.graphics.Color.parseColor("#919191"));			 
		     		 tv_worker.setBackgroundColor(android.graphics.Color.parseColor("#ff6200"));
		     		 tv_job.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));	        		
	        	}else if(v.getId()==R.id.iv_find){
					 Intent intent = new Intent(JobPlaceFragment.this.getActivity(), JobPlace_search.class); 
					 if(where.equals("job")){		
						 intent.putExtra("job", 1);//1代表从工作进入搜索
					 }else{		
						 intent.putExtra("job", 2);//2.代表从牛人进入搜索
					 }		    			 
					 startActivity(intent);        		
	        	}else if(v.getId()==R.id.iv_message){
	        		appuser.getInstance().LoginToAct(JobPlaceFragment.this.getActivity(),  Talk_message.class);	        		
	        	}else if(v.getId()==R.id.iv_resume){
					if(where.equals("job")){	
			    		appuser.getInstance().LoginToAct(JobPlaceFragment.this.getActivity(),  Resume.class);
					}else{	
			    		appuser.getInstance().LoginToAct(JobPlaceFragment.this.getActivity(),  AddEmpMsg.class);
					}	        			        		
	        	}				
			}			
		}
}
