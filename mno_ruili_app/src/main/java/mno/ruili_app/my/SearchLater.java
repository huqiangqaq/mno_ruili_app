package mno.ruili_app.my;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mno.mylistview.FooterLoadingLayout;
import mno.mylistview.PullToRefreshBase;
import mno.mylistview.PullToRefreshList;
import mno.mylistview.PullToRefreshBase.OnRefreshListener;
import mno.ruili_app.R;
import mno_ruili_app.adapter.ImageListAdapter_job;
import mno_ruili_app.entity.Job;
import mno_ruili_app.entity.Worker;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class SearchLater extends Fragment implements OnClickListener{
	PopupWindow pw1,pw2,pw3;
	View view,popLayout;
	LayoutInflater mInflater;
	private ListView lv;
	private int state; 
	webhandler handler_,handler1,handler2,handler3,handler4;
	private PullToRefreshList mRefreshLayout= null;
	JSONObject type_json;
	private int currentPage= 1;
	boolean havemore=true;
	boolean Isrefresh=false;
	List<Job>   mList = new ArrayList<Job>();
	ImageListAdapter_job mylistViewAdapter;
	private ListView mListView;
	TextView tv_salary,tv_exp,tv_time,tv_place;
	List<Worker>   list = new ArrayList<Worker>();
	String salary,exp,time,place;
	MyAdapter adapter;
	Map<Integer,Boolean> map=new HashMap<Integer,Boolean>();
	boolean isShow = false;
	int preposition;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.search_later, null);
		popLayout= inflater.inflate(R.layout.select_salary_activity, null);
		//mInflater = LayoutInflater.from(SearchLater.this.getActivity());
		init();
		initData();
		loadJobMsg(salary,exp,time,place);
		return view;
	}
	
	private void initData() {
		// TODO Auto-generated method stub
		
	}
	private void showList(List<Worker> list) {
		if (state == 1 && pw1.isShowing()) {  
            state = 0;  
            pw1.dismiss();  
        } else {  
            /***    弹出自定义的菜单        ***/  
            lv = (ListView) popLayout.findViewById(R.id.mListView);            
            adapter=new MyAdapter(list);
    		lv.setAdapter(adapter);  
            /** 
             * popLayout                PopupWindow所显示的界面    
             * view.getWidth()        设置PopupWindow宽度 
             * lv.getHeight()           设置PopupWindow高度 
             */  
            pw1= new PopupWindow(popLayout,view.getWidth(),lv.getHeight());  
            ColorDrawable cd = new ColorDrawable(-0000);  
            pw1.setBackgroundDrawable(cd);  

            pw1.update();  
            pw1.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);  
            pw1.setTouchable(true); // 设置popupwindow可点击  
            pw1.setOutsideTouchable(true);  // 设置popupwindow外部可点击  
            pw1.setFocusable(true); //获取焦点  
            /*设置popupwindow的位置*/  
            pw1.showAtLocation(popLayout, (Gravity.BOTTOM - 40)| Gravity.LEFT, 0, 40);  
            state = 1;  
            pw1.setTouchInterceptor(new View.OnTouchListener() {  
                @Override  
                public boolean onTouch(View v, MotionEvent event) {  
                    /****   如果点击了popupwindow的外部，popupwindow也会消失 ****/  
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {  
                        pw1.dismiss();  
                        return true;   
                    }  
                    return false;  
                }  

            });
        }	
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				map.put(position,true);
                map.put(preposition, false);
                adapter.notifyDataSetChanged();
                if (position!=preposition){
                    preposition = position;
                }else {
                    map.put(preposition,true);
                }
                pw1.dismiss();
//				Intent intent=new Intent();
//				intent.putExtra("salaryId", list.get(position).getWorkerId());
//				intent.putExtra("salaryResult", list.get(position).getJobName());
//				SearchLater.this.getActivity().setResult(RESULT_OK, intent);
//				SearchLater.this.getActivity().finish();				
			}
		}); 
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_salary:
			handler1=new webhandler(){
				public void OnResponse(org.json.JSONObject response) {
					try {
						JSONArray data=response.getJSONArray("data");
						for(int i=0;i<data.length();i++){
							JSONObject salary=data.getJSONObject(i);
							String salaryId=salary.getString("id");
							String salaryResult=salary.getString("name");
							Worker worker=new Worker(salaryId, salaryResult);
							list.add(worker);
							map.put(i,isShow);
						}
						adapter.notifyDataSetChanged();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			Map<String, String> params = new HashMap<String, String>();
			handler1.SetRequest(new RequestType("ZP",Type.getSalary),params);	
			showList(list);
			break;
		case R.id.tv_exp:
			handler2=new webhandler(){
				public void OnResponse(org.json.JSONObject response) {
					try {
						JSONArray data=response.getJSONArray("data");
						for(int i=0;i<data.length();i++){
							JSONObject salary=data.getJSONObject(i);
							String salaryId=salary.getString("id");
							String salaryResult=salary.getString("name");
							Worker worker=new Worker(salaryId, salaryResult);
							list.add(worker);
							map.put(i,isShow);
						}
						adapter.notifyDataSetChanged();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			Map<String, String> params2 = new HashMap<String, String>();
			handler2.SetRequest(new RequestType("ZP",Type.getJobYear),params2);
			showList(list);
			break;
		case R.id.tv_time:
			handler3=new webhandler(){
				public void OnResponse(org.json.JSONObject response) {
					try {
						JSONArray data=response.getJSONArray("data");
						for(int i=0;i<data.length();i++){
							JSONObject salary=data.getJSONObject(i);
							String salaryId=salary.getString("id");
							String salaryResult=salary.getString("name");
							Worker worker=new Worker(salaryId, salaryResult);
							list.add(worker);
							map.put(i,isShow);
						}
						adapter.notifyDataSetChanged();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			Map<String, String> params3= new HashMap<String, String>();
			handler3.SetRequest(new RequestType("ZP",Type.getJobYear),params3);
			showList(list);
			break;
		case R.id.tv_place:
	
			break;	
		default:
			break;
		}
		
	}
	private void init() {
		tv_salary=(TextView) view.findViewById(R.id.tv_salary);
		tv_exp=(TextView) view.findViewById(R.id.tv_exp);
		tv_time=(TextView) view.findViewById(R.id.tv_time);
		tv_place=(TextView) view.findViewById(R.id.tv_place);
		tv_salary.setOnClickListener(this);
		tv_exp.setOnClickListener(this);
		tv_time.setOnClickListener(this);
		tv_place.setOnClickListener(this);
		mRefreshLayout=(PullToRefreshList)view. findViewById(R.id.mListView);
		mListView = mRefreshLayout.getRefreshView();
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
            	currentPage=1;
            	Map<String, String> params = new HashMap<String, String>();
    			params.put("page", currentPage+"");
    			handler_.SetRequest(new RequestType("ZP",Type.getPostList),params);
    			Isrefresh=true;
            }         
			@Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				if(havemore){
					currentPage++;
					loadJobMsg(salary,exp,time,place);				     
				}
				else{
					mRefreshLayout.setHasMoreData(false);
				}
             }
			
         });
        mylistViewAdapter = new ImageListAdapter_job(view.getContext(), mList,
				R.layout.list_item_job, new String[] { "itemsIcon" },
				new int[] { R.id.tv_jobName,R.id.tv_salary ,R.id.tv_workTime,R.id.tv_education,R.id.tv_workPlace,
			 R.id.fl_jobMsg,R.id.iv_jobImage,R.id.tv_nickName,R.id.tv_workLevel,R.id.tv_companyName,R.id.tv_updateTime});
        mListView.setAdapter(mylistViewAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {					
					Intent intent = new Intent(SearchLater.this.getActivity(),Job_details.class);
					//Constant.itemid=mList.get(position).getJobId();
					intent.putExtra("id", mList.get(position).getJobId());
					SearchLater.this.getActivity().startActivity(intent);										
			}   		
    	});
	}
	private void loadJobMsg(String salary,String exp,String time,String place) {
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
					mList.clear();	
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
					    String updateTime=type_json.getString("create_time");
					    
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
		params.put("page", currentPage+"");
		params.put("key_word", salary);
		params.put("salary", salary);
		params.put("job_year", exp);
		params.put("create_time", time);
		params.put("job_addr", place);
		handler_.SetRequest(new RequestType("ZP",Type.getPostList),params);		
	}	
	private class MyAdapter extends BaseAdapter {
		private List<Worker>   mlist = new ArrayList<Worker>();
		public MyAdapter(List< Worker> mlist){
			this.mlist=mlist;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mlist.size();
		}
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mlist.get(position);
		}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		class ViewHolder{
            TextView tv_salary;
            ImageView iv_salary;
        }
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView==null){
				holder=new ViewHolder();
				convertView = LayoutInflater.from(SearchLater.this.getActivity()).inflate(R.layout.list_item_selectsalary,parent,false);
				holder.tv_salary=(TextView)convertView.findViewById(R.id.tv_salary);
				holder.iv_salary=(ImageView)convertView.findViewById(R.id.iv_salary);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_salary.setText(mlist.get(position).getJobName());
			if (map.get(position)){ //显示
				holder.iv_salary.setVisibility(View.VISIBLE);
            }else {//不显示
            	holder.iv_salary.setVisibility(View.INVISIBLE);
            }
			return convertView;
		}		
	}
}
