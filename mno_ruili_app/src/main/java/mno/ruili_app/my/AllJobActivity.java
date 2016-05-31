package mno.ruili_app.my;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.R;
import mno_ruili_app.adapter.AllJobAdapter;
import mno_ruili_app.entity.Job;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class AllJobActivity extends Activity{
	JSONObject type_json;
	webhandler handler_;
	ListView mListView;
	AllJobAdapter adapter;
	List<Job>   mList = new ArrayList<Job>();
	String uid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_job_activity);
		mListView=(ListView) findViewById(R.id.mListView);
		Intent intent=getIntent();
		uid=intent.getStringExtra("uid").toString();
		loadAllJob();	
	}
	//1.找工作列表数据的获得
	private void loadAllJob() {
        handler_ = new webhandler(){		    	
			@Override
			public void OnResponse(JSONObject response) {
				try {
					mList.clear();						
					JSONObject data=response.getJSONObject("data");
					JSONArray list = data.getJSONArray("post_list");
					int length=list.length();
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
					    String updateTime=type_json.getString("create_time");
					    
					    final Job job = new Job(jobId, jobName, salary, workTime, education, workPlace, jobMsg,  updateTime);
					    mList.add(job);				    
				    }
				    adapter = new AllJobAdapter(AllJobActivity.this, mList,
							R.layout.list_item_alljob, new String[] { "itemsIcon" },
							new int[] {R.id.tv_jobName,R.id.tv_salary,R.id.tv_workTime,R.id.tv_education,
				    		R.id.tv_workPlace,R.id.tfl_jobMsg,R.id.tv_updateTime});
					mListView.setAdapter(adapter);
				    adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		Map<String, String> params = new HashMap<String, String>();
		params.put("uid", uid);
		params.put("type", "my");
		handler_.SetRequest(new RequestType("ZP",Type.getPostList),params);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("AllJobActivity");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("AllJobActivity");
		MobclickAgent.onPause(this);
	}
}
