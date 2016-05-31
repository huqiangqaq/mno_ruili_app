package mno.ruili_app.my;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.CustomEditText;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//添加招聘职位（填写岗位信息）
public class AddEmpActivity extends Activity implements OnClickListener{
	CustomEditText cet_jobDuty,cet_jobAsk;
	TextView tv_jobName,tv_workExp,tv_edu,tv_position,tv_salary,tv_ok;
	LinearLayout ll_jobName,ll_workExp,ll_edu,ll_position,ll_salary;	
	String jobId,salaryId="",cityId="",workTimeId="",highEduId="",post_id,uid;
	webhandler handler1,handler2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_emp_activity);
		init();
		Intent intent=getIntent();
		String from=intent.getStringExtra("from");
		uid=appuser.getInstance().getUserinfo().uid.toString();
		if(from.equals("e")){			
		}else if(from.equals("f")){
			String jobName=intent.getStringExtra("jobName");
			String salary=intent.getStringExtra("salary");
			String workTime=intent.getStringExtra("workTime");
			String workPlace=intent.getStringExtra("workPlace");
			String eduLevel=intent.getStringExtra("eduLevel");
			String jobDuty=intent.getStringExtra("jobDuty");
			String jobAsk=intent.getStringExtra("jobAsk");
			tv_jobName.setText(jobName);
			tv_salary.setText(salary);
			tv_workExp.setText(workTime);
			tv_position.setText(workPlace);
			tv_edu.setText(eduLevel);
			cet_jobDuty.setText(jobDuty);
			cet_jobAsk.setText(jobAsk);
		}
	}
	private void init() {
		cet_jobDuty=(CustomEditText) findViewById(R.id.cet_jobDuty);
		cet_jobAsk=(CustomEditText) findViewById(R.id.cet_jobAsk);
		tv_jobName=(TextView) findViewById(R.id.tv_jobName);
		tv_workExp=(TextView) findViewById(R.id.tv_workExp);
		tv_edu=(TextView) findViewById(R.id.tv_edu);
		tv_position=(TextView) findViewById(R.id.tv_position);
		tv_salary=(TextView) findViewById(R.id.tv_salary);
		tv_ok=(TextView) findViewById(R.id.tv_ok);
		ll_jobName=(LinearLayout) findViewById(R.id.ll_jobName);
		ll_workExp=(LinearLayout) findViewById(R.id.ll_workExp);
		ll_edu=(LinearLayout) findViewById(R.id.ll_edu);
		ll_position=(LinearLayout) findViewById(R.id.ll_position);
		ll_salary=(LinearLayout) findViewById(R.id.ll_salary);	
		
		ll_jobName.setOnClickListener(this);
		ll_workExp.setOnClickListener(this);
		ll_edu.setOnClickListener(this);
		ll_position.setOnClickListener(this);
		ll_salary.setOnClickListener(this);
		tv_ok.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_jobName:
			Intent intent6=new Intent(AddEmpActivity.this,SelectJobActivity.class);
			startActivityForResult(intent6, 5);			
			break;
		case R.id.ll_workExp:
			Intent intent=new Intent(AddEmpActivity.this,SelectWorkTimeActivity.class);
			startActivityForResult(intent, 1);			
			break;
		case R.id.ll_edu:
			Intent intent2=new Intent(AddEmpActivity.this,SelectHighEduActivity.class);
			startActivityForResult(intent2, 2);
			break;
		case R.id.ll_position:
			Intent intent3=new Intent(AddEmpActivity.this,SelectPlaceActivity.class);
			startActivityForResult(intent3, 3);
			break;
		case R.id.ll_salary:
			Intent intent4=new Intent(AddEmpActivity.this,SelectSalaryActivity.class);
			startActivityForResult(intent4, 4);
			break;
		case R.id.tv_ok:
			if(tv_jobName.getText().toString().length()>0&&
					tv_workExp.getText().toString().length()>0&&
					tv_edu.getText().toString().length()>0&&
					tv_position.getText().toString().length()>0&&
					tv_salary.getText().toString().length()>0&&
					cet_jobDuty.getText().toString().length()>0&&
					cet_jobAsk.getText().toString().length()>0){
				handler1=new webhandler(){
					@Override
					public void OnResponse(JSONObject response) {
						// TODO Auto-generated method stub
						super.OnResponse(response);
						try {
							post_id=response.getString("data");
							handler2=new webhandler();
							Map<String, String> params= new HashMap<String, String>();
							params.put("post_id", post_id);
							params.put("job_name", jobId);
							params.put("job_year", workTimeId);
							params.put("education", highEduId);	
							params.put("job_addr", cityId);
							params.put("salary", salaryId);			
							params.put("post_duty", cet_jobDuty.getText().toString());		
							params.put("post_require", cet_jobAsk.getText().toString());	
							handler2.SetRequest(new RequestType("ZP",Type.savePost),params);
							Intent intent5=new Intent();
							intent5.putExtra("job_name", tv_jobName.getText().toString());
							intent5.putExtra("salary", tv_salary.getText().toString());
							intent5.putExtra("job_year", tv_workExp.getText().toString());
							intent5.putExtra("job_addr", tv_position.getText().toString());
							intent5.putExtra("education", tv_edu.getText().toString());
							intent5.putExtra("post_duty", cet_jobDuty.getText().toString());
							intent5.putExtra("post_require", cet_jobAsk.getText().toString());
							AddEmpActivity.this.setResult(RESULT_OK, intent5);
							finish();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				Map<String, String> params1 = new HashMap<String, String>();
				params1.put("uid", uid);
				handler1.SetRequest(new RequestType("ZP",Type.createPost),params1);								
			}else{
				Toast.makeText(this, "请填写完必填项", 1000).show();
			}		
			break;
		default:
			break;
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		try {
			switch (requestCode) {
			case 1:
				workTimeId= data.getExtras().getString("workTimeId");
				String workTimeResult = data.getExtras().getString("workTimeResult");
				tv_workExp.setText(workTimeResult);
				break;
			case 2:
				highEduId= data.getExtras().getString("highEduId");
				String highEduResult = data.getExtras().getString("highEduResult");
				tv_edu.setText(highEduResult);
				break;
			case 3:
				cityId= data.getExtras().getString("placeId");
				String cityResult = data.getExtras().getString("placeResult");
				tv_position.setText(cityResult);
				break;
			case 4:
				salaryId= data.getExtras().getString("salaryId");
				String salaryResult = data.getExtras().getString("salaryResult");
				tv_salary.setText(salaryResult);
				break;
			case 5:
				jobId = data.getExtras().getString("jobId");
				String jobResult = data.getExtras().getString("jobResult");
				tv_jobName.setText(jobResult);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("AddEmpActivity");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("AddEmpActivity");
		MobclickAgent.onPause(this);
	}
}
