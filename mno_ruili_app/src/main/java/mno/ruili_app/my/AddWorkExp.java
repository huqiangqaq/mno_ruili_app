package mno.ruili_app.my;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.R;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
//添加工作经历
public class AddWorkExp extends Activity implements OnClickListener{
	LinearLayout ll_joinWork,ll_leaveWork,ll_company,ll_position,ll_workDescribe;
	TextView tv_joinWork,tv_leaveWork,tv_position,tv_workDescribe,tv_ok;
	EditText et_company,et_position;
	private int mYear;  	  
    private int mMonth;    
    private int mDay; 
    private static final int DATE_DIALOG_ID = 1;      
    private static final int SHOW_DATAPICK = 0; 
    boolean joinWork=false,leaveWork=false;
    String jobId="",job_exp_id,workDescribe="";
    StringBuilder joinTime,leaveTime;
    webhandler handler1,handler2;   
    String resultMsg,resumeId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_workexp_activity);
		init();
		Intent intent=getIntent();
		String from=intent.getStringExtra("from");
		resumeId=intent.getStringExtra("resumeId");
		if(from.equals("a")){			
		}else if(from.equals("b")){
			String positions=intent.getStringExtra("positions");
			String companyName=intent.getStringExtra("companyName");
			String joinWork=intent.getStringExtra("joinWork");
			String leaveWork=intent.getStringExtra("leaveWork");
			String workDescribe=intent.getStringExtra("workDescribe");
			tv_position.setText(positions);
			et_company.setText(companyName);
			tv_joinWork.setText(joinWork);
			tv_leaveWork.setText(leaveWork);
			tv_workDescribe.setText(workDescribe);
		}				
		final Calendar c = Calendar.getInstance();  		  
        mYear = c.get(Calendar.YEAR);  
        mMonth = c.get(Calendar.MONTH);   
        mDay = c.get(Calendar.DAY_OF_MONTH);
	}
	private void  updateDisplay() {
		if(joinWork){			
			joinTime=new StringBuilder().append(mYear).append(  				  
		              (mMonth + 1) < 10 ? "-0" + (mMonth + 1) :"-"+(mMonth + 1)).append(    
		              (mDay < 10) ? "-0" + mDay : "-"+mDay);
			tv_joinWork.setText(joinTime);	
			joinWork=false;
		}else if(leaveWork){
			leaveTime=new StringBuilder().append(mYear).append(  				  
		              (mMonth + 1) < 10 ? "-0" + (mMonth + 1) : "-"+(mMonth + 1)).append(    
		              (mDay < 10) ? "-0" + mDay : "-"+mDay);
			tv_leaveWork.setText(leaveTime);	
			leaveWork=false;
		}
	}
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {  		  
	      public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {  	  
	          mYear = year;  	  
	          mMonth = monthOfYear;  	  
	          mDay = dayOfMonth;  	  
	          updateDisplay();  	  
	      }  	  
	 }; 
	private void init(){
		ll_joinWork=(LinearLayout) findViewById(R.id.ll_joinWork);
		ll_leaveWork=(LinearLayout) findViewById(R.id.ll_leaveWork);
		ll_company=(LinearLayout) findViewById(R.id.ll_company);
		ll_position=(LinearLayout) findViewById(R.id.ll_position);
		ll_workDescribe=(LinearLayout) findViewById(R.id.ll_workDescribe);
		tv_joinWork=(TextView) findViewById(R.id.tv_joinWork);
		tv_leaveWork=(TextView) findViewById(R.id.tv_leaveWork);
		tv_position=(TextView) findViewById(R.id.tv_position);
		tv_workDescribe=(TextView) findViewById(R.id.tv_workDescribe);
		tv_ok=(TextView) findViewById(R.id.tv_ok);
		et_company=(EditText) findViewById(R.id.et_company);
		
		ll_joinWork.setOnClickListener(this);
		ll_leaveWork.setOnClickListener(this);
		ll_position.setOnClickListener(this);
		ll_workDescribe.setOnClickListener(this);
		tv_ok.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_joinWork:
			joinWork=true;
			Message msg = new Message();  			              
            msg.what = AddWorkExp.SHOW_DATAPICK;                  
            AddWorkExp.this.saleHandler.sendMessage(msg); 
			break;
		case R.id.ll_leaveWork:
			leaveWork=true;
			Message msg2 = new Message();  			              
            msg2.what = AddWorkExp.SHOW_DATAPICK;                  
            AddWorkExp.this.saleHandler.sendMessage(msg2); 
			break;	
		case R.id.ll_position:
			Intent intent=new Intent(AddWorkExp.this,SelectJobActivity.class);
			startActivityForResult(intent, 2);
			break;		
		case R.id.ll_workDescribe:
			Intent intent2=new Intent(AddWorkExp.this,WorkDescribeActivity.class);
			intent2.putExtra("workDescribe", workDescribe);
			startActivityForResult(intent2, 3);
			break;		
		case R.id.tv_ok:
			if(tv_joinWork.getText().toString().length()>0&&
					tv_leaveWork.getText().toString().length()>0&&
					et_company.getText().toString().length()>0&&
					tv_position.getText().toString().length()>0){	
				handler1=new webhandler(){
					@Override
					public void OnResponse(JSONObject response) {
						// TODO Auto-generated method stub
						super.OnResponse(response);
						try {
							job_exp_id=response.getString("data");
							handler2=new webhandler();
							Map<String, String> params= new HashMap<String, String>();
							params.put("job_exp_id", job_exp_id);
							params.put("description", workDescribe);
							params.put("join_time", tv_joinWork.getText().toString());
							params.put("leave_time", tv_leaveWork.getText().toString());	
							params.put("comp_name", et_company.getText().toString());
							params.put("job_name", jobId);		
							handler2.SetRequest(new RequestType("ZP",Type.saveJobExp),params);
							Intent intent3=new Intent();
							intent3.putExtra("description", workDescribe);
							intent3.putExtra("join_time", tv_joinWork.getText().toString());
							intent3.putExtra("leave_time", tv_leaveWork.getText().toString());	
							intent3.putExtra("comp_name", et_company.getText().toString());
							intent3.putExtra("job_name", tv_position.getText().toString());	
							AddWorkExp.this.setResult(RESULT_OK, intent3);
							AddWorkExp.this.finish();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				};
				Map<String, String> params1 = new HashMap<String, String>();
				params1.put("resume_id", resumeId);
				handler1.SetRequest(new RequestType("ZP",Type.createJobExp),params1);
			}else{
				Toast.makeText(this, "请填写完必填项", 1000).show();
			}			
			break;
		default:
			break;
		}		
	}
	//
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		try{
			switch (requestCode) {
			case 2:
				jobId = data.getExtras().getString("jobId");
				String jobResult = data.getExtras().getString("jobResult");
				tv_position.setText(jobResult);
				break;
			case 3:
				workDescribe = data.getExtras().getString("workDescribe");
				if(workDescribe.length()>10){
					tv_workDescribe.setText(workDescribe.substring(0, 10)+"......");
				}else{
					tv_workDescribe.setText(workDescribe);
				}
				break;
			default:
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	//
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay); 
		default:
			break;
		}
		return null;
	}
	@Override
	@Deprecated
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case DATE_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay); 
			break;
		default:
			break;
		}
	}
	Handler saleHandler = new Handler() {  		  
       @Override  	  
       public void handleMessage(Message msg) {  	  
           switch (msg.what) {  	  
           case AddWorkExp.SHOW_DATAPICK:  	  
              showDialog(DATE_DIALOG_ID);  	  
              break;  	  
           }  	  
        }  	  
    };
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("AddWorkExp");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("AddWorkExp");
		MobclickAgent.onPause(this);
	}
}
