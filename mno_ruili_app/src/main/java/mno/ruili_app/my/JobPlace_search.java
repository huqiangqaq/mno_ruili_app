package mno.ruili_app.my;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;


import mno.down.util.SharePreTolls;
import mno.mylistview.FooterLoadingLayout;
import mno.mylistview.PullToRefreshBase;
import mno.mylistview.PullToRefreshList;
import mno.mylistview.PullToRefreshBase.OnRefreshListener;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.CustomEditText;
import mno.ruili_app.customView.ExpandTabView;
import mno.ruili_app.customView.ViewLeft;
import mno.ruili_app.customView.ViewLeft.OnSelectListener;
import mno.ruili_app.customView.ViewLeft2;
import mno.ruili_app.customView.ViewLeft3;
import mno.ruili_app.customView.ViewMiddle;
import mno_ruili_app.adapter.ImageListAdapter_job;
import mno_ruili_app.adapter.ImageListAdapter_worker;
import mno_ruili_app.entity.Job;
import mno_ruili_app.entity.Worker;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class JobPlace_search extends Activity implements OnClickListener{
	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ViewLeft viewLeft1;
	private ViewLeft2 viewLeft2;
	private ViewLeft3 viewLeft3;
	private ViewMiddle viewMiddle;
	webhandler handler,handler2,handler3,handler4;
	CustomEditText nei_find_edi;
	TextView tv_back, tv_deleteAll;
	LayoutInflater mInflater;
	private int currentPage= 1,mpage=1;
	LinearLayout ll_searchBefore,ll_searchLater;
	String keyWord,back,uid;
	private PullToRefreshList mRefreshLayout= null;
	private ListView mListView,lv_history;
	boolean havemore=true;
	boolean Isrefresh=false;
	JSONObject type_json;
	ImageListAdapter_job mylistViewAdapter;
	ImageListAdapter_worker mylistViewAdapter2;
	List<Job>   mList = new ArrayList<Job>();//根据关键词搜索出来的岗位数据
	List<Worker>   mList2 = new ArrayList<Worker>();//根据关键词搜索出来的岗位数据
	String key1="salary",key2="job_year",key3="update_time",key4="job_addr",key5="key_word";
	ArrayAdapter<String> adapter;//历史记录的适配器
	List<String> list = new ArrayList<String>();//历史记录的list	
	int text;
	Map<String, String> params = new HashMap<String, String>();
	Map<String, String> params2 = new HashMap<String, String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobplace_search);
		if(!appuser.getInstance().IsLogin()){
			uid="";
	    }else{
	    	uid=appuser.getInstance().getUserinfo().uid.toString();	
	    }
		mInflater = LayoutInflater.from(this);
		Intent intent=getIntent();
		text=intent.getIntExtra("job", 0);
		init();
		initVaule();
		initListener();
	}
	private void initListener() {
		//点击选中了一个子项的时候，当前的整个listView消失
		viewLeft1.setOnSelectListener(new ViewLeft.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				if(text==1){
					if(distance.equals("0")){
						mList.clear();
						params.remove(key1);
						loadJobMsg(currentPage);
					}else{
						mList.clear();
						params.put(key1, distance);
						loadJobMsg( currentPage);//key1, distance,
					}					
				}else if(text==2){
					if(distance.equals("0")){
						mList2.clear();
						params2.remove(key1);
						loadWorkerMsg(currentPage);
					}else{
						mList2.clear();
						params2.put(key1, distance);
						loadWorkerMsg(currentPage);
					}
				}
				onRefresh(viewLeft1, showText);
			}
		});
		viewLeft2.setOnSelectListener(new ViewLeft2.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				if(text==1){
					if(distance.equals("0")){
						mList.clear();
						params.remove(key2);
						loadJobMsg(currentPage);
					}else{
						mList.clear();
						params.put(key2, distance);
						loadJobMsg( currentPage);//key1, distance,
					}
				}else if(text==2){
					if(distance.equals("0")){
						mList2.clear();
						params2.remove(key2);
						loadWorkerMsg(currentPage);
					}else{
						mList2.clear();
						params2.put(key2, distance);
						loadWorkerMsg(currentPage);
					}
				}				
				onRefresh(viewLeft2, showText);
			}
		});
		viewLeft3.setOnSelectListener(new ViewLeft3.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				if(text==1){
					if(distance.equals("0")){
						mList.clear();
						params.remove(key3);
						loadJobMsg(currentPage);
					}else{
						mList.clear();
						params.put(key3, distance);
						loadJobMsg( currentPage);//key1, distance,
					}
				}else if(text==2){
					if(distance.equals("0")){
						mList2.clear();
						params2.remove(key3);
						loadWorkerMsg(currentPage);
					}else{
						mList2.clear();
						params2.put(key3, distance);
						loadWorkerMsg(currentPage);
					}
				}				
				onRefresh(viewLeft3, showText);
			}
		});
		
		viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {
			@Override
			public void getValue(String id, String showText) {
				if(text==1){
					if(id.equals("0")){
						mList.clear();
						params.remove(key4);
						loadJobMsg(currentPage);
					}else{
						mList.clear();
						params.put(key4, id);
						loadJobMsg( currentPage);//key4, id,
					}					
				}else if(text==2){
					if(id.equals("0")){
						mList2.clear();
						params2.remove(key4);
						loadWorkerMsg(currentPage);
					}else{
						mList2.clear();
						params2.put(key4, id);
						loadWorkerMsg(currentPage);//key4, id, 
					}					
				}				
				onRefresh(viewMiddle,showText);
			}						
		});
		
	}
	private void onRefresh(View view, String showText) {
		
		expandTabView.onPressBack();
		int position = getPositon(view);
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position);
		}

	}
	private int getPositon(View tView) {
		for (int i = 0; i < mViewArray.size(); i++) {
			if (mViewArray.get(i) == tView) {
				return i;
			}
		}
		return -1;
	}
	@Override
	public void onBackPressed() {
		if (!expandTabView.onPressBack()) {
			finish();
		}
	}
	private void initVaule() {
		mViewArray.add(viewLeft1);//
		mViewArray.add(viewLeft2);
		mViewArray.add(viewLeft3);
		mViewArray.add(viewMiddle);//
		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("薪资");//
		mTextArray.add("经验");
		mTextArray.add("发布时间");//
		mTextArray.add("工作地点");
		expandTabView.setValue(mTextArray, mViewArray);
		expandTabView.setTitle(viewLeft1.getShowText(), 0);//
		expandTabView.setTitle(viewLeft2.getShowText(), 1);//
		expandTabView.setTitle(viewLeft3.getShowText(), 2);//
		expandTabView.setTitle(viewMiddle.getShowText(), 3);// 		
	}
	private void init() {
		nei_find_edi=(CustomEditText)this.findViewById(R.id.nei_find_edi);
		tv_back=(TextView) findViewById(R.id.back);
		tv_back.setOnClickListener(this);
		ll_searchLater=(LinearLayout) findViewById(R.id.ll_searchLater);		
		mRefreshLayout=(PullToRefreshList)findViewById(R.id.mListView);
		mListView = mRefreshLayout.getRefreshView();
		//输入框的文本监听事件		
		TextWatcher tw=new TextWatcher() {
			private CharSequence temp;
			@Override
			/*这里的s表示改变之后的内容，通常start和count组合，
			可以在s中读取本次改变字段中新的内容。
			而before表示被改变的内容的数量。*/
			public void onTextChanged(CharSequence s, int start/*开始位置*/, 
					int before/*改变前的内容数量*/, int count/*新增数*/) {
				temp = s;//新增加的一句关键代码
			}			
			@Override
			/*
			 * 这里的s表示改变之前的内容，通常start和count组合，
			 * 可以在s中读取本次改变字段中被改变的内容。
			 * 而after表示改变后新的内容的数量。
			 */
			public void beforeTextChanged(CharSequence s, int start/*开始的位置*/,
					int count/*被改变的旧内容数*/,int after/*改变后的内容数量*/) {
				temp = s;
			}			
			@Override
			public void afterTextChanged(Editable s) {//表示最终内容
					tv_back.setText("搜索");   		
	            if (temp.equals("")||temp.length()==0||temp==null) {
	            	tv_back.setText("取消");
	            	ll_searchLater.setVisibility(View.GONE);
					ll_searchBefore.setVisibility(View.VISIBLE);
					list=SharePreTolls.readHistory(JobPlace_search.this, 5);
					adapter = new ArrayAdapter<String>(JobPlace_search.this, android.R.layout.simple_list_item_1, list);
					lv_history.setAdapter(adapter);
					adapter.notifyDataSetChanged();
	            }				
			}
		};
		nei_find_edi.addTextChangedListener(tw);
		/*
		 * 1.点击搜索前控件的初始化
		 */
		tv_deleteAll=(TextView) findViewById(R.id.tv_deleteAll);	
		tv_deleteAll.setOnClickListener(this);
		lv_history=(ListView) findViewById(R.id.lv_history);		
		ll_searchBefore=(LinearLayout) findViewById(R.id.ll_searchBefore);
		list=SharePreTolls.readHistory(this, 5);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		lv_history.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		lv_history.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {				
				String curr=list.get(position);
				//String curr=(String) lv_history.getAdapter().getItem(position);
				ll_searchBefore.setVisibility(View.GONE);
				ll_searchLater.setVisibility(View.VISIBLE);
				nei_find_edi.setText(curr);
				if(text==1){
					mList.clear();
					params.put(key5,curr);
					loadJobMsg(currentPage);	//key5,curr,
				}else if(text==2){
					mList2.clear();
					params2.put(key5,curr);
					loadWorkerMsg(currentPage);	//key5,curr,
				}
				
			}
		});
		/*
		 * 2.点击搜索后控件的初始化
		 */
		expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view);
		
		viewLeft1 = new ViewLeft(this);
		viewLeft2 = new ViewLeft2(this);
		viewLeft3= new ViewLeft3(this);
		viewMiddle = new ViewMiddle(this);
		
		
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
            	if(text==1){            		
        			params.put("page", currentPage+"");    			
        			handler.SetRequest(new RequestType("ZP",Type.getPostList),params);
				}else if(text==2){
	    			params2.put("page", currentPage+"");    			
	    			handler2.SetRequest(new RequestType("ZP",Type.getResumeList),params2);
				}
    			Isrefresh=true;
            }         
			@Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				if(havemore){
					mpage=mpage+1;
					if(text==1){						
						loadMoreJobMsg( mpage);	
					}else if(text==2){
						loadMoreWorkerMsg(mpage);
					}					
				}else{
					mRefreshLayout.setHasMoreData(false);
				}
             }
			
         });
        mListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(text==1){
					Intent intent = new Intent(JobPlace_search.this,Job_details.class);
					intent.putExtra("id", mList.get(position).getJobId());
					JobPlace_search.this.startActivity(intent);	
				}else if(text==2){
					Intent intent = new Intent(JobPlace_search.this,Worker_details.class);
					intent.putExtra("id", mList2.get(position).getWorkerId());
					JobPlace_search.this.startActivity(intent);	
				}																	
			}   		
    	});
		
	}
	//加载更多岗位数据
	private void loadMoreJobMsg(int page){
		handler3= new webhandler(){	
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
		params.put("page", page+"");
		handler3.SetRequest(new RequestType("ZP",Type.getPostList),params);		
	}
	//加载更多牛人数据
	private void loadMoreWorkerMsg(int page){
		handler4 = new webhandler(){
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
						mList2.clear();							
						mRefreshLayout.onPullDownRefreshComplete();
						Isrefresh=false;
					}
					//mList2.clear();
					mRefreshLayout.onPullUpRefreshComplete();
					JSONObject data=response.getJSONObject("data");
					JSONArray list = data.getJSONArray("resume_list");
					int length=list.length();
					havemore=true;
					if(length==0){
						havemore=false;
						mRefreshLayout.setHasMoreData(false);
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
		params2.put("page", page+"");
		params2.put("uid", uid);
		handler4.SetRequest(new RequestType("ZP",Type.getResumeList),params2);
	}
	/*
	 * 1.加载搜索后的岗位数据
	 * key----->搜索时要传入的键
	 * value---->搜索时要传入的值
	 * page----->搜索时要传入的页数
	 */
	private void loadJobMsg(int page) {//String key,String value,int page
		handler= new webhandler(){	
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
		params.put("page", page+"");
		handler.SetRequest(new RequestType("ZP",Type.getPostList),params);
		mylistViewAdapter = new ImageListAdapter_job(JobPlace_search.this, mList,
				R.layout.list_item_job, new String[] { "itemsIcon" },
				new int[] { R.id.tv_jobName,R.id.tv_salary ,R.id.tv_workTime,R.id.tv_education,R.id.tv_workPlace,
			 R.id.fl_jobMsg,R.id.iv_jobImage,R.id.tv_nickName,R.id.tv_workLevel,R.id.tv_companyName,R.id.tv_updateTime});
        mListView.setAdapter(mylistViewAdapter);		
	}
	//2.找牛人列表数据的获得
	private void loadWorkerMsg(int page){//String key,String value,
		handler2 = new webhandler(){
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
						mList2.clear();							
						mRefreshLayout.onPullDownRefreshComplete();
						Isrefresh=false;
					}
					//mList2.clear();
					mRefreshLayout.onPullUpRefreshComplete();
					JSONObject data=response.getJSONObject("data");
					JSONArray list = data.getJSONArray("resume_list");
					int length=list.length();
					havemore=true;
					if(length==0){
						havemore=false;
						mRefreshLayout.setHasMoreData(false);
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
		params2.put("page", page+"");
		handler2.SetRequest(new RequestType("ZP",Type.getResumeList),params2);
		mylistViewAdapter2 = new ImageListAdapter_worker(this, mList2,
				R.layout.list_item_worker, new String[] { "itemsIcon" },
				new int[] {R.id.tv_jobName,R.id.tv_salary ,R.id.tv_workTime,R.id.tv_education,R.id.tv_workPlace,
			 R.id.fl_workerMsg,R.id.iv_workerImage,R.id.tv_nickName,R.id.tv_updateTime});
		mListView.setAdapter(mylistViewAdapter2);	
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			back=tv_back.getText().toString();
			keyWord=nei_find_edi.getText().toString();
			if(back.equals("搜索")&&keyWord.trim().length()>0){
				SharePreTolls.writeHistroy(JobPlace_search.this, keyWord, 5);
				ll_searchBefore.setVisibility(View.GONE);
				ll_searchLater.setVisibility(View.VISIBLE);	
				if(text==1){
					mList.clear();
					params.put(key5, keyWord);
					loadJobMsg(currentPage);//key5,keyWord,
				}else if(text==2){
					mList2.clear();
					params2.put(key5, keyWord);
					loadWorkerMsg(currentPage);//key5, keyWord, 
				}				
			}else if(back.equals("取消")){
				finish();
			}else if(keyWord==null){
				ll_searchLater.setVisibility(View.GONE);
				ll_searchBefore.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.tv_deleteAll:
			list.clear();	
			SharePreTolls.deleteHistory(this);
			adapter.notifyDataSetChanged();
			break;			
		default:
			break;
		}
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("JobPlace_search");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("JobPlace_search");
		MobclickAgent.onPause(this);
	}
}
