package mno.ruili_app.my;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.FlowLayout;
import mno.ruili_app.ct.RoundImageView;
import mno.ruili_app.ct.TagAdapter;
import mno.ruili_app.ct.TagFlowLayout;
import mno.ruili_app.my.im.ChatActivity;
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
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class Job_details extends Activity implements OnClickListener{
	String  jobName,salary,workTime,education,workPlace;
	String [] workerMsg;
	String id,userId="",myid,resumeid="";
	TextView tv_jobName, tv_salary, tv_workTime, tv_education, tv_workPlace,
			tv_nickName,tv_auth, tv_workLevel, tv_lookAllJob, tv_teamInfo, tv_jobDuty,
			tv_jobRequire, tv_companyName, tv_domain, tv_lookAgain,tv_rightComm;
	RoundImageView iv_jobImage;
	TagFlowLayout tfl_jobMsg;
	LayoutInflater mInflater;
	String nickName,state="",myState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.job_details);
		Intent intent = this.getIntent();
		id = intent.getStringExtra("id").toString();//岗位id
		init();
		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		webhandler handler = new webhandler() {
			public void OnResponse(org.json.JSONObject response) {
				try {
					JSONObject data = response.getJSONObject("data");
					JSONObject type_json= data.getJSONObject("post");

					String jobName = type_json.getString("job_name");
					String salary = type_json.getString("salary");
					String workTime = type_json.getString("job_year");
					String education = type_json.getString("education");
					String workPlace = type_json.getString("job_addr");
					String jobImagePath = type_json.getString("photo");
					userId=type_json.getString("uid");//岗位主人的uid
					nickName = type_json.getString("username");//岗位主人的昵称
					if(nickName==null){
						nickName="dsxuser"+userId;//如果昵称为空则把昵称设置为环信uid
					}
					String state= type_json.getString("state");
					String workLevel = type_json.getString("profession");
					if(workLevel.equals("null")){
				    	workLevel="";
				    }
					String teamInfo = type_json.getString("summary");	
					if(teamInfo.equals("null")){
						teamInfo="";
					}
					// 亮点名字
					JSONArray points = type_json.getJSONArray("points");
					String[] jobMsg = new String[points.length()];
					for (int j = 0; j < points.length(); j++) {
						JSONObject item = points.getJSONObject(j);
						String point_name = item.getString("point_name");
						jobMsg[j] = point_name;
					}
					String jobDuty = type_json.getString("post_duty");
					String jobRequire = type_json.getString("post_require");
					String companyName = type_json.getString("comp_name");
					//String domain = type_json.getString("domain");
					// 数据填充到视图
					tv_jobName.setText(jobName);
					tv_salary.setText(salary);
					tv_workTime.setText(workTime);
					tv_education.setText(education);
					tv_workPlace.setText(workPlace);
					tv_nickName.setText(nickName);
					if(state.equals("2")){
						tv_auth.setBackgroundResource(R.drawable.auth);
					}else {
						tv_auth.setBackgroundResource(R.drawable.unauth);
					}					
					tv_workLevel.setText(workLevel);
					tv_teamInfo.setText(teamInfo);
					tv_jobDuty.setText(jobDuty);
					tv_jobRequire.setText(jobRequire);
					tv_companyName.setText(companyName);
					//tv_domain.setText(domain);
					
					if(jobImagePath.equals("null")){
						iv_jobImage.setDefaultImageResId(R.drawable.my_up);
					}else{
						String imageUrl = RequestType.getWebUrl_(jobImagePath);
						iv_jobImage.setImageUrl(imageUrl, webpost.getImageLoader());
					}
					// 亮点
					tfl_jobMsg.setAdapter(new TagAdapter<String>(jobMsg) {
						@Override
						public View getView(FlowLayout parent, int position,
								String t) {
							TextView tv_jobMsg = (TextView) mInflater.inflate(
									R.layout.tv, tfl_jobMsg, false);
							tv_jobMsg.setText(t);
							return tv_jobMsg;
						}
					});

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		handler.SetRequest(new RequestType("ZP", Type.getPostDetail), params);
	}

	private void init() {
		tv_jobName = (TextView) findViewById(R.id.tv_jobName);
		tv_salary = (TextView) findViewById(R.id.tv_salary);
		tv_workTime = (TextView) findViewById(R.id.tv_workTime);
		tv_education = (TextView) findViewById(R.id.tv_education);
		tv_workPlace = (TextView) findViewById(R.id.tv_workPlace);
		tv_nickName = (TextView) findViewById(R.id.tv_nickName);
		tv_auth=(TextView) findViewById(R.id.tv_auth);
		tv_workLevel = (TextView) findViewById(R.id.tv_workLevel);
		tv_companyName = (TextView) findViewById(R.id.tv_companyName);
		tv_lookAllJob = (TextView) findViewById(R.id.tv_lookAllJob);
		tv_teamInfo = (TextView) findViewById(R.id.tv_teamInfo);
		tv_jobDuty = (TextView) findViewById(R.id.tv_jobDuty);
		tv_jobRequire = (TextView) findViewById(R.id.tv_jobRequire);		
		//tv_domain = (TextView) findViewById(R.id.tv_domain);
		tv_lookAgain = (TextView) findViewById(R.id.tv_lookAgain);
		tv_rightComm = (TextView) findViewById(R.id.tv_rightComm);		

		iv_jobImage = (RoundImageView) findViewById(R.id.iv_jobImage);
		tfl_jobMsg = (TagFlowLayout) findViewById(R.id.tfl_jobMsg);
		
		tv_lookAllJob.setOnClickListener(this);
		tv_lookAgain.setOnClickListener(this);
		tv_rightComm.setOnClickListener(this);
	}
	//获取自己简历是否填写的状态
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
		params.put("type", "1");
		handler2.SetRequest(new RequestType("ZP", Type.getCompStatus), params);		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_lookAllJob:
			Intent intent=new Intent(Job_details.this,AllJobActivity.class);
			intent.putExtra("uid", userId);
			startActivity(intent);
			break;
		case R.id.tv_lookAgain:
			finish();
			break;
		case R.id.tv_rightComm:
			if(!appuser.getInstance().IsLogin()){
				appuser.getInstance().LoginToAct(Job_details.this,  mylogin.class);
		    }else{
		    	if(!userId.equals(myid)){
					if(myState.equals("1")){
						AlertDialog.Builder builder = new AlertDialog.Builder(Job_details.this);
						 builder.setMessage("完善自己的简历后才能沟通哦！");
						 builder.setTitle("提示");				
						 builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
							 @Override
							 public void onClick(DialogInterface dialog,int which) {
								 dialog.dismiss();
								 Intent intent =new Intent(Job_details.this,Resume.class);
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
						//Toast.makeText(Job_details.this, "完善自己的简历后才能沟通哦！", 1000).show();
					}else if(myState.equals("2")){
						webhandler handler2 = new webhandler() {
							//发送邮件成功时执行这个方法（自己的简历和对方的邮箱都填写好了）
							public void OnResponse(org.json.JSONObject response) {		
								try {
									JSONObject data=response.getJSONObject("data");
									state=data.getString("state");//发送邮箱是否成功，成功为1，不成功为0。
									resumeid=data.getString("resume_id");//自己简历的id					
									Intent intent2=new Intent(Job_details.this,ChatActivity.class);
									String userName="dsxuser"+userId;
									intent2.putExtra("userId", userName);//对方的环信uid
									intent2.putExtra("nickname", nickName);//对方(岗位主人)的昵称
									intent2.putExtra("flag", "job");
									intent2.putExtra("state", state);
									intent2.putExtra("from", "details");
									intent2.putExtra("resumeOrPost_id", resumeid);
									startActivity(intent2);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}	
							@Override
							//发送邮件失败时执行这个方法（原因是对方没有填写邮箱）
							public void OnError(int code, String mess) {
								// TODO Auto-generated method stub
								super.OnError(code, mess);
								Intent intent2=new Intent(Job_details.this,ChatActivity.class);
								String userName="dsxuser"+userId;
								intent2.putExtra("userId", userName);//对方的环信uid
								intent2.putExtra("nickname", nickName);//对方(岗位主人)的昵称
								intent2.putExtra("flag", "job");
								intent2.putExtra("state", "0");
								intent2.putExtra("mess", mess);
								intent2.putExtra("from", "details");
								intent2.putExtra("resumeOrPost_id", resumeid);
								startActivity(intent2);       				
							}
						};
						Map<String, String> params2 = new HashMap<String, String>();
						params2.put("uid", myid);
						params2.put("post_id", id);
						handler2.SetRequest(new RequestType("ZP", Type.sendEmailToComp), params2);
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
		MobclickAgent.onPageStart("Job_details");
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
		MobclickAgent.onPageEnd("Job_details");
		MobclickAgent.onPause(this);
	}
}
