package mno.ruili_app.my;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.R;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.ct.validator;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class my_findpwd extends Activity{
	EditText phone;
	EditText pass,edi_my_yqm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_findpwd);
		phone = (EditText)my_findpwd.this.findViewById(R.id.edi_my_zcname);
	
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("my_findpwd");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("my_findpwd");
		MobclickAgent.onPause(this);
	}
	 public void onclick(View v) {
		
		 if(v.getId()==R.id.my_but_send)
		 {
			 
			 if(phone.getText().toString().trim().length()>0)
				 
			 {
				 webhandler handler_ = new webhandler(){

						@Override
					public void OnError(int code, String mess) {
						// TODO Auto-generated method stub
						super.OnError(code, mess);
						Intent intent0 = new Intent(my_findpwd.this, myfindcheckcode.class); 
						
						 String user=phone.getText().toString();
						
						 intent0.putExtra("user",user);  

						 startActivity(intent0);
						
					}

						@Override
						public void OnResponse(JSONObject response) {
							// TODO Auto-generated method stub
							// TODO Auto-generated method stub
							try {
								
								MessageBox.Show(my_findpwd.this,"您的号码未注册，请重新输入号码或前往注册！");
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	
					
							
						}
						
						};
					Map<String, String> params = new HashMap<String, String>();
					params.put("phone", phone.getText().toString());
					handler_.SetRequest(new RequestType("",Type.checkPhone),params);
					handler_.SetProgressDialog(my_findpwd.this);
				 
			
				
			 }
			 else 
			 {
				 MessageBox.Show(getApplicationContext(), "号码不能为空");
			 }
		 }
		 else  if(v.getId()==R.id.login_findpwd)
		 {
			 
		 }
		 
		 
	 }
}

