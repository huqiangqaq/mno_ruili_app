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
import mno.ruili_app.appuser;
import mno.ruili_app.ct.CustomListview;
import mno.ruili_app.ct.FlowLayout;
import mno.ruili_app.ct.RoundImageView;
import mno.ruili_app.ct.TagAdapter;
import mno.ruili_app.ct.TagFlowLayout;
import mno.ruili_app.my.im.ChatActivity;
import mno_ruili_app.adapter.EduExpAdapter;
import mno_ruili_app.adapter.WorkExpAdapter;
import mno_ruili_app.entity.Worker;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.webpost;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Worker_details extends Activity implements OnClickListener{
	CustomListview mListView1,mListView2;
	List<Worker>   mList1= new ArrayList<Worker>();
	List<Worker>   mList2 = new ArrayList<Worker>();
	WorkExpAdapter adapter1;
	EduExpAdapter adapter2;
	String id,userId="",resumeId,myid,post_id="",state="",myState; 
	String myjobName ,mysalary ,myworkTime , myeducation ,myworkPlace,mycompanyName ;
	TextView tv_jobName, tv_salary, tv_workTime, tv_education, 
			tv_workPlace,tv_nickName, tv_sex,tv_phone, tv_joinWork,tv_leaveWork,
			tv_companyName, tv_position, tv_workDescribe,tv_joinSchool,
			tv_leaveSchool,tv_school,tv_major,tv_eduLevel,tv_schoolDescribe
			, tv_lookAgain,tv_rightComm;
	RoundImageView iv_workerImage;
	TagFlowLayout tfl_workerMsg;
	LayoutInflater mInflater;
	String[] workerMsg,jobMsg;
	String jobName,salary ,workTime ,education,workPlace,workerImagePath,
		nickName,sex,phone,companyName,position,joinWork,leaveWork,workDescribe,
		school,major,eduLevel,joinSchool,leaveSchool,schoolDescribe;
	//String userIds,mycompanyNames,nickNames;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.worker_details);	
		Intent intent = this.getIntent();
		id = intent.getStringExtra("id").toString();//此简历的id
		init();
		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		// id=Constant.itemid;
		webhandler handler = new webhandler() {
			public void OnResponse(org.json.JSONObject response) {
				try {
					JSONObject data = response.getJSONObject("data");
					JSONObject type_json= data.getJSONObject("resume");
					resumeId = type_json.getString("id");//简历的id
					jobName = type_json.getString("job_name");
					salary = type_json.getString("salary");
					workTime = type_json.getString("job_year");
					education = type_json.getString("education");
					workPlace = type_json.getString("job_addr");
					workerImagePath = type_json.getString("photo");
					nickName = type_json.getString("username");
					if(nickName==null){
						nickName="dsxuser"+userId;//如果对方的昵称为空则把昵称设置为环信uid
					}
					sex = type_json.getString("sex");
					phone=type_json.getString("phone");						
					userId=type_json.getString("uid");//简历主人的id
					// 亮点名字
					JSONArray points = type_json.getJSONArray("points");
					workerMsg = new String[points.length()];
					for (int j = 0; j < points.length(); j++) {
						JSONObject item = points.getJSONObject(j);
						String point_name = item.getString("point_name");
						workerMsg[j] = point_name;
					}					
					//工作经历
					JSONArray job_exp=type_json.getJSONArray("job_exp");
					for(int i=0;i<job_exp.length();i++){
						JSONObject item=job_exp.getJSONObject(i);
						companyName=item.getString("comp_name");
						position=item.getString("job_name");
						if(position.equals("null")){
							position="";
					    }
						joinWork=item.getString("join_time");
						leaveWork=item.getString("leave_time");
						workDescribe=item.getString("description");
						if(workDescribe.equals("null")){
							workDescribe="";
					    }
						Worker work1=new Worker(companyName, position, joinWork, leaveWork, workDescribe);
						mList1.add(work1);
					}					
					//教育经历
					JSONArray stu_exp=type_json.getJSONArray("stu_exp");
					for(int i=0;i<stu_exp.length();i++){
						JSONObject item=stu_exp.getJSONObject(i);
						school=item.getString("school");
						major=item.getString("pro");
						eduLevel=item.getString("education");
						joinSchool=item.getString("join_time");
						leaveSchool=item.getString("leave_time");
						schoolDescribe=item.getString("description");
						if(schoolDescribe.equals("null")){
							schoolDescribe="";
					    }
						Worker work2=new Worker(school, major, eduLevel, joinSchool, leaveSchool,schoolDescribe);
						mList2.add(work2);
					}		
					// 数据填充到视图
					tv_jobName.setText(jobName);
					tv_salary.setText(salary);
					tv_workTime.setText(workTime);
					tv_education.setText(education);
					tv_workPlace.setText(workPlace);					
					if(workerImagePath.equals("null")){
						iv_workerImage.setDefaultImageResId(R.drawable.my_up);
					}else{
						String imageUrl = RequestType.getWebUrl_(workerImagePath);
						iv_workerImage.setImageUrl(imageUrl, webpost.getImageLoader());
					}
					tv_nickName.setText(nickName);		
					tv_sex.setText(sex);
					if(!appuser.getInstance().IsLogin()){
						tv_phone.setText("********");		
				    }else{
				    	tv_phone.setText(phone);		
				    }								
					// 亮点
					tfl_workerMsg.setAdapter(new TagAdapter<String>(workerMsg) {
						@Override
						public View getView(FlowLayout parent, int position,String t) {
							TextView tv_workerMsg = (TextView) mInflater.inflate(
									R.layout.tv, tfl_workerMsg, false);
							tv_workerMsg.setText(t);
							return tv_workerMsg;
						}
					});
					//工作经历
					adapter1=new WorkExpAdapter(Worker_details.this, mList1, R.layout.work_exp, 
							new String[] { "itemsIcon" }, new int[] { R.id.tv_position ,R.id.tv_companyName,
							R.id.tv_joinWork,R.id.tv_leaveWork,R.id.tv_workDescribe});
					mListView1.setAdapter(adapter1);
					setListViewHeight(mListView1);
					adapter1.notifyDataSetChanged();					
					//教育经历
					adapter2 = new EduExpAdapter(Worker_details.this, mList2,
							R.layout.edu_exp, new String[] { "itemsIcon" },
							new int[] { R.id.tv_school ,R.id.tv_eduLevel,
							R.id.tv_joinSchool,R.id.tv_leaveSchool,R.id.tv_major,R.id.tv_schoolDescribe});	
					mListView2.setAdapter(adapter2);
					setListViewHeight(mListView2);
					adapter2.notifyDataSetChanged();					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		handler.SetRequest(new RequestType("ZP", Type.getResumeDetail), params);		
	}
	/**  
     * 重新计算ListView的高度，解决ScrollView和ListView两个View都有滚动的效果，
     * 在嵌套使用时起冲突的问题  
     * @param listView  
     */  
    public void setListViewHeight(ListView listView) {    
            
        // 获取ListView对应的Adapter    
        
        ListAdapter listAdapter = listView.getAdapter();    
        
        if (listAdapter == null) {    
            return;    
        }    
        int totalHeight = 0;    
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目    
            View listItem = listAdapter.getView(i, null, listView);    
            listItem.measure(0, 0); // 计算子项View 的宽高    
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度    
        }    
        
        ViewGroup.LayoutParams params = listView.getLayoutParams();    
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));    
        listView.setLayoutParams(params);    
    }
	private void init() {
		tv_jobName = (TextView) findViewById(R.id.tv_jobName);
		tv_salary = (TextView) findViewById(R.id.tv_salary);
		tv_workTime = (TextView) findViewById(R.id.tv_workTime);
		tv_education = (TextView) findViewById(R.id.tv_education);
		tv_workPlace = (TextView) findViewById(R.id.tv_workPlace);
		tv_nickName = (TextView) findViewById(R.id.tv_nickName);
		tv_sex = (TextView) findViewById(R.id.tv_sex);
		tv_phone= (TextView) findViewById(R.id.tv_phone);
		tv_companyName = (TextView) findViewById(R.id.tv_companyName);
		tv_position = (TextView) findViewById(R.id.tv_position);
		tv_joinWork = (TextView) findViewById(R.id.tv_joinWork);
		tv_leaveWork = (TextView) findViewById(R.id.tv_leaveWork);
		tv_workDescribe = (TextView) findViewById(R.id.tv_workDescribe);
		tv_school = (TextView) findViewById(R.id.tv_school);
		tv_eduLevel = (TextView) findViewById(R.id.tv_eduLevel);
		tv_joinSchool= (TextView) findViewById(R.id.tv_joinSchool);
		tv_leaveSchool= (TextView) findViewById(R.id.tv_leaveSchool);
		tv_major= (TextView) findViewById(R.id.tv_major);
		tv_schoolDescribe= (TextView) findViewById(R.id.tv_schoolDescribe);
		tv_lookAgain = (TextView) findViewById(R.id.tv_lookAgain);
		tv_lookAgain.setOnClickListener(this);
		tv_rightComm = (TextView) findViewById(R.id.tv_rightComm);
		tv_rightComm.setOnClickListener(this);

		iv_workerImage = (RoundImageView) findViewById(R.id.iv_workerImage);
		tfl_workerMsg = (TagFlowLayout) findViewById(R.id.tfl_workerMsg);		
		
		mListView1=(CustomListview) findViewById(R.id.mListView1);
		mListView2=(CustomListview) findViewById(R.id.mListView2);					
	}
	//获取自己公司信息是否填写的状态
	private void getMyState(){
		webhandler handler2 = new webhandler() {
			@Override
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
				Log.i("job_details", mess);
			}
			public void OnResponse(org.json.JSONObject response) {
				Log.i("job_details", "response");
				try {
					myState = response.getString("data");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		Map<String, String> params = new HashMap<String, String>();
		params.put("uid", myid);
		params.put("type", "0");
		handler2.SetRequest(new RequestType("ZP", Type.getCompStatus), params);		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_lookAgain:
			finish();
			break;
		case R.id.tv_rightComm:
			/*
			 * 1.根据自己的id和对方简历id来获取与对方简历情况相对应的岗位id
			 * 2.根据获取的岗位id来获得岗位的简情。
			 */	
			if(!appuser.getInstance().IsLogin()){
				appuser.getInstance().LoginToAct(Worker_details.this,  mylogin.class);
		    }else{
		    	if(!userId.equals(myid)){
					if(myState.equals("1")){
						AlertDialog.Builder builder = new AlertDialog.Builder(Worker_details.this);
						 builder.setMessage("完善自己的岗位信息后才能沟通哦！");
						 builder.setTitle("提示");				
						 builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
							 @Override
							 public void onClick(DialogInterface dialog,int which) {
								 dialog.dismiss();
								 Intent intent =new Intent(Worker_details.this,AddEmpMsg.class);
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
						//Toast.makeText(this, "完善自己的岗位信息后才能沟通哦！", 1000).show();
					}else if(myState.equals("2")||myState.equals("3")){
						webhandler handler2 = new webhandler() {
							public void OnResponse(org.json.JSONObject response) {
								try {
									JSONObject data=response.getJSONObject("data");
									state=data.getString("state");
									post_id=data.getString("post_id");//招聘职位ID	
									Intent intent2=new Intent(Worker_details.this,ChatActivity.class);
									String userName="dsxuser"+userId;//简历主人的id+dsxuser
									intent2.putExtra("userId", userName);//对方的环信uid	
									intent2.putExtra("nickname", nickName);//对方(简历主人)的昵称				
									intent2.putExtra("flag", "worker");
									intent2.putExtra("state", state);
									intent2.putExtra("from", "details");
									intent2.putExtra("resumeOrPost_id", post_id);
									startActivity(intent2);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							@Override
							public void OnError(int code, String mess) {
								// TODO Auto-generated method stub
								super.OnError(code, mess);						
								Intent intent2=new Intent(Worker_details.this,ChatActivity.class);
								String userName="dsxuser"+userId;//简历主人的id+dsxuser
								intent2.putExtra("userId", userName);//对方的环信uid	
								intent2.putExtra("nickname", nickName);//对方(简历主人)的昵称				
								intent2.putExtra("flag", "worker");
								intent2.putExtra("state", "0");
								intent2.putExtra("mess", mess);
								intent2.putExtra("from", "details");
								intent2.putExtra("resumeOrPost_id", post_id);
								startActivity(intent2);
							}
						};
						Map<String, String> params2 = new HashMap<String, String>();
						params2.put("comp_uid", myid);
						params2.put("resume_id", id);
						handler2.SetRequest(new RequestType("ZP", Type.sendEmailToPerson), params2);
					}																		
				}else{				
					Toast.makeText(this, "不能和自己聊天！", 1000).show();								
				}
		    }			
			break;
		default:
			break;
		}		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("Worker_details");
		MobclickAgent.onResume(this); 
		if(!appuser.getInstance().IsLogin()){
			myid="";
	    }else{
	    	myid=appuser.getInstance().getUserinfo().uid.toString();	
	    }
		getMyState();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("Worker_details");
		MobclickAgent.onPause(this);
	}
}
