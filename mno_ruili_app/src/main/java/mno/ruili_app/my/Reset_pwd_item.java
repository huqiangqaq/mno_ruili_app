package mno.ruili_app.my;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//修改密码
public class Reset_pwd_item extends Activity implements OnClickListener{
	ImageView iv_back2;
	EditText etPwd_now,etPwd_now2,etPwd_before;
	TextView tv_ok;
	String pwd_now,pwd_now2,pwd_before;
	 webhandler handler_; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.reset_pwd_item);
		iv_back2=(ImageView) findViewById(R.id.iv_back2);
		etPwd_now=(EditText) findViewById(R.id.etPwd_now);
		etPwd_now2=(EditText) findViewById(R.id.etPwd_now2);
		etPwd_before=(EditText) findViewById(R.id.etPwd_before);
		tv_ok=(TextView) findViewById(R.id.tv_ok);
		
		iv_back2.setOnClickListener(this);
		tv_ok.setOnClickListener(this);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("Reset_pwd_item");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("Reset_pwd_item");
		MobclickAgent.onPause(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_ok:
			pwd_now=etPwd_now.getText().toString();
			pwd_now2=etPwd_now2.getText().toString();
			pwd_before=etPwd_before.getText().toString();			
			//前后一致，请求后台确认新密码，并显示修改成功						
			handler_ = new webhandler(){
				//2.后台的回应
				@Override
				public void OnResponse(JSONObject response) {
					try {
						int mess = response.getInt("code");
						if(mess==0 )
						{
							Toast.makeText(Reset_pwd_item.this, "密码修改成功！", Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(Reset_pwd_item.this,My_setup_item.class);
							startActivity(intent);							
							Reset_pwd_item.this.finish();						
						}else{
							Toast.makeText(Reset_pwd_item.this, "密码修改失败！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
			};
			//1.前台的请求
			Map<String, String> params = new HashMap<String, String>();	
			params.put("newpw", pwd_now);
			params.put("repeatpw", pwd_now2);
			params.put("password", pwd_before);
			handler_.SetRequest(new RequestType("",Type.updatecode),params);
			break;
		case R.id.iv_back2:
			Reset_pwd_item.this.finish();
			break;
		default:
			break;
		}
		
	}

}

