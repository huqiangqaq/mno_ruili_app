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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
//添加教育经历
public class AddEduExp extends Activity implements OnClickListener{
	LinearLayout ll_joinSchool,ll_leaveSchool,ll_edu,ll_eduDescribe;
	TextView tv_joinSchool,tv_leaveSchool,tv_edu,tv_eduDescribe,tv_ok;
	EditText et_school,et_major;
	private int mYear;  	  
    private int mMonth;    
    private int mDay; 
    private static final int DATE_DIALOG_ID = 1;      
    private static final int SHOW_DATAPICK = 0; 
    boolean joinSchool=false,leaveSchool=false;
    String eduId="",stu_exp_id,eduDescribe="",resumeId;
    StringBuilder joinTime,leaveTime;
    webhandler handler1,handler2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_eduexp_activity);
		init();
		Intent intent=getIntent();
		String from=intent.getStringExtra("from");
		resumeId=intent.getStringExtra("resumeId");
		if(from.equals("c")){
		}else if(from.equals("d")){
			String school=intent.getStringExtra("school");
			String eduLevel=intent.getStringExtra("eduLevel");
			String joinSchool=intent.getStringExtra("joinSchool");
			String leaveSchool=intent.getStringExtra("leaveSchool");
			String major=intent.getStringExtra("major");
			String schoolDescribe=intent.getStringExtra("schoolDescribe");
			et_school.setText(school);
			tv_edu.setText(eduLevel);
			tv_joinSchool.setText(joinSchool);
			tv_leaveSchool.setText(leaveSchool);
			et_major.setText(major);
			tv_eduDescribe.setText(schoolDescribe);
		}
		
		final Calendar c = Calendar.getInstance();  		  
        mYear = c.get(Calendar.YEAR);  
        mMonth = c.get(Calendar.MONTH);   
        mDay = c.get(Calendar.DAY_OF_MONTH);
	}
	private void  updateDisplay() {
		if(joinSchool){			
			joinTime=new StringBuilder().append(mYear).append(  				  
		              (mMonth + 1) < 10 ? "-0" + (mMonth + 1) :"-"+(mMonth + 1)).append(    
		              (mDay < 10) ? "-0" + mDay : "-"+mDay);
			tv_joinSchool.setText(joinTime);	
			joinSchool=false;
		}else if(leaveSchool){
			leaveTime=new StringBuilder().append(mYear).append(  				  
		              (mMonth + 1) < 10 ? "-0" + (mMonth + 1) : "-"+(mMonth + 1)).append(    
		              (mDay < 10) ? "-0" + mDay : "-"+mDay);
			tv_leaveSchool.setText(leaveTime);	
			leaveSchool=false;
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
	private void init() {
		ll_joinSchool=(LinearLayout) findViewById(R.id.ll_joinSchool);
		ll_leaveSchool=(LinearLayout) findViewById(R.id.ll_leaveSchool);
		ll_edu=(LinearLayout) findViewById(R.id.ll_edu);
		ll_eduDescribe=(LinearLayout) findViewById(R.id.ll_eduDescribe);
		tv_joinSchool=(TextView) findViewById(R.id.tv_joinSchool);
		tv_leaveSchool=(TextView) findViewById(R.id.tv_leaveSchool);
		tv_edu=(TextView) findViewById(R.id.tv_edu);
		tv_eduDescribe=(TextView) findViewById(R.id.tv_eduDescribe);
		tv_ok=(TextView) findViewById(R.id.tv_ok);
		et_school=(EditText) findViewById(R.id.et_school);
		et_major=(EditText) findViewById(R.id.et_major);
		
		ll_joinSchool.setOnClickListener(this);
		ll_leaveSchool.setOnClickListener(this);
		ll_edu.setOnClickListener(this);
		ll_eduDescribe.setOnClickListener(this);
		tv_ok.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_joinSchool:
			joinSchool=true;
			Message msg = new Message();  			              
            msg.what = AddEduExp.SHOW_DATAPICK;                  
            AddEduExp.this.saleHandler.sendMessage(msg); 
			break;
		case R.id.ll_leaveSchool:
			leaveSchool=true;
			Message msg2 = new Message();  			              
            msg2.what = AddEduExp.SHOW_DATAPICK;                  
            AddEduExp.this.saleHandler.sendMessage(msg2); 
			break;	
		case R.id.ll_edu:
			Intent intent=new Intent(AddEduExp.this,SelectHighEduActivity.class);
			startActivityForResult(intent, 2);
			break;		
		case R.id.ll_eduDescribe:
			Intent intent2=new Intent(AddEduExp.this,EduDescribeActivity.class);
			intent2.putExtra("eduDescribe", eduDescribe);
			startActivityForResult(intent2, 3);
			break;		
		case R.id.tv_ok:
			if(tv_joinSchool.getText().toString().length()>0&&
					tv_leaveSchool.getText().toString().length()>0&&
					et_school.getText().toString().length()>0&&
					tv_edu.getText().toString().length()>0&&
					et_major.getText().toString().length()>0){
				handler1=new webhandler(){
					@Override
					public void OnResponse(JSONObject response) {
						// TODO Auto-generated method stub
						super.OnResponse(response);
						try {
							stu_exp_id=response.getString("data");
							handler2=new webhandler(){
								@Override
								public void OnResponse(JSONObject response) {
									// TODO Auto-generated method stub
									super.OnResponse(response);
									try {
										String msg=response.getString("message");
										Toast.makeText(AddEduExp.this, msg, 1000).show();
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							};
							Map<String, String> params= new HashMap<String, String>();
							params.put("stu_exp_id", stu_exp_id);
							params.put("description", eduDescribe);
							params.put("join_time", tv_joinSchool.getText().toString());
							params.put("leave_time", tv_leaveSchool.getText().toString());			
							params.put("school", et_school.getText().toString());
							params.put("education", eduId);		
							params.put("pro", et_major.getText().toString());		
							handler2.SetRequest(new RequestType("ZP",Type.saveStuExp),params);
							Intent intent3=new Intent();
							intent3.putExtra("description", eduDescribe);
							intent3.putExtra("join_time", tv_joinSchool.getText().toString());
							intent3.putExtra("leave_time", tv_leaveSchool.getText().toString());	
							intent3.putExtra("school", et_school.getText().toString());
							intent3.putExtra("pro", et_major.getText().toString());	
							intent3.putExtra("education", tv_edu.getText().toString());	
							AddEduExp.this.setResult(RESULT_OK, intent3);
							finish();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				};
				Map<String, String> params1 = new HashMap<String, String>();
				params1.put("resume_id", resumeId);
				handler1.SetRequest(new RequestType("ZP",Type.createStuExp),params1);				
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
		try{
			switch (requestCode) {
			case 2:
				eduId = data.getExtras().getString("highEduId");
				String eduResult = data.getExtras().getString("highEduResult");
				tv_edu.setText(eduResult);
				break;
			case 3:
				eduDescribe = data.getExtras().getString("eduDescribe");
				if(eduDescribe.length()>10){
					tv_eduDescribe.setText(eduDescribe.substring(0, 10)+"......");
				}else{
					tv_eduDescribe.setText(eduDescribe);
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
           case AddEduExp.SHOW_DATAPICK:  	  
              showDialog(DATE_DIALOG_ID);  	  
              break;  	  
           }  	  
        }  	  
    };
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("AddEduExp");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("AddEduExp");
		MobclickAgent.onPause(this);
	}
}
