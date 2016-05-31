package mno.ruili_app.my;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.ct.TimeButton;
import mno.ruili_app.main.MyFragment;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class My_xgphonenum extends Activity{
	EditText et_phonenum,checkCode;
	TimeButton but_send_sms_;
	webhandler handler_3/*验证send*/,handler_4/*验证cheak*/,handler/*修改手机号码*/;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_xgphonenum);
		et_phonenum = (EditText)findViewById(R.id.et_phonenum);
		checkCode=(EditText) findViewById(R.id.et_checkCode);
		but_send_sms_ = (TimeButton)this.findViewById(R.id.check_but_sms);
		
		but_send_sms_.setTextAfter("已发送").setTextBefore("重新发送").setLenght(60 * 1000);
		but_send_sms_.setText("获取验证码");
		but_send_sms_.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 if(et_phonenum.getText().toString().trim().length()>0){
						but_send_sms_.setbegin(true);
						sendSms();				 
				 }else {
						 MessageBox.Show(getApplicationContext(), "号码不能为空");
					 }				
			}
		});
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
	super.onResume();
	MobclickAgent.onPageStart("My_xgphonenum");
	MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("My_xgphonenum");
		MobclickAgent.onPause(this);
	}
	
	public void onclick(View v) {
		if(v.getId()==R.id.back){
			 My_xgphonenum.this.finish();
		 }else if(v.getId()==R.id.tv_sure){
			 checkSms();
		 }
	}
	//发送验证码
	private void sendSms() {
		handler_3= new webhandler(){
			@Override
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
				Toast.makeText(getApplicationContext(), mess,Toast.LENGTH_SHORT).show();				
			}	
			@Override
			public void OnResponse(JSONObject response) {
				// TODO Auto-generated method stub
				but_send_sms_.setbegin(true);
				Drawable drawable=My_xgphonenum.this.getResources().getDrawable(R.drawable.radius_hui);
				but_send_sms_.setBackgroundDrawable(drawable);
				//开始发送验证码
				but_send_sms_.begin();
				Toast.makeText(getApplicationContext(), "验证码已经发送",Toast.LENGTH_SHORT).show();
			}

		};
		Map<String, String> params = new HashMap<String, String>();
	    params.put("phone",et_phonenum.getText().toString().trim());
	    params.put("type","3");
	    handler_3.SetRequest(new RequestType("",Type.sendSms),params);		
	}
	
	//检验验证码
	private void checkSms() {
		handler_4= new webhandler(){

			@Override
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
				Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_SHORT).show();
			}
			@Override
			public void OnResponse(JSONObject response) {
				try {
					String mess=response.getString("message");
					if(mess.equals("验证通过")){
						handler=new webhandler(){
							@Override
							public void OnResponse(JSONObject response) {
								// TODO Auto-generated method stub
								super.OnResponse(response);
								appuser.getInstance().getUserinfo().phone=et_phonenum.getText().toString().trim();
								finish();	
							}
						};
						Map<String, String> params = new HashMap<String, String>();
					    params.put("phone",et_phonenum.getText().toString().trim());
					    handler_3.SetRequest(new RequestType("4",Type.editInfo),params);
					    Toast.makeText(My_xgphonenum.this, "修改成功！", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

		};
		Map<String, String> params = new HashMap<String, String>();
		params.put("phone",et_phonenum.getText().toString().trim());
		params.put("type","3");
		params.put("code",checkCode.getText().toString().trim());
		handler_4.SetRequest(new RequestType("",Type.checkSms),params);
	 }
}
