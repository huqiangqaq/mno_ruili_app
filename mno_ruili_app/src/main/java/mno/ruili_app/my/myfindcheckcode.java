package mno.ruili_app.my;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.PassMgr;
import mno.ruili_app.R;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.ct.TimeButton;
import mno.ruili_app.main.Main;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.loginhandler;
import mno_ruili_app.net.RequestType.Type;
import mno_ruili_app.net.webhandler;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class myfindcheckcode extends AbActivity   {
	TimeButton but_send_sms_;
	EditText edi_my_yzm;
	TextView cheak_ts,my_but_ok;
	String user;
	String psw,yqm;
	webhandler handler_,handler_3/*验证send*/,handler_4; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_mycheakcode);
	
		Intent intent0 =this.getIntent();
		user=intent0.getStringExtra("user").toString();
		
		
		cheak_ts = (TextView)this.findViewById(R.id.cheak_ts);
		my_but_ok= (TextView)this.findViewById(R.id.my_but_ok);
		my_but_ok.setText("下一步");
		cheak_ts.setText("验证码已发送到手机:"+user);
		but_send_sms_ = (TimeButton)this.findViewById(R.id.check_but_sms);
		edi_my_yzm= (EditText)this.findViewById(R.id.edi_my_yzm);
		but_send_sms_.setTextAfter("已发送").setTextBefore("重新发送").setLenght(60 * 1000);
		but_send_sms_.setText("正在发送");
		
		but_send_sms_.setOnClickListener(new OnClickListener(){

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				but_send_sms_.setbegin(false);
				sendsms();
			}});

		InitHandler();
		sendsms();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("myfindcheckcode");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("myfindcheckcode");
		MobclickAgent.onPause(this);
	}
	 private void sendsms() {
			// TODO Auto-generated method stub
			  Map<String, String> params = new HashMap<String, String>();
			  params.put("phone",user);
			  params.put("type","2");
			  handler_3.SetRequest(new RequestType("",Type.sendSms),params);
		 }
		 private void cheaksms() {
				// TODO Auto-generated method stub
			  Map<String, String> params = new HashMap<String, String>();
			  params.put("phone",user);
			  params.put("type","2");
			  params.put("code",edi_my_yzm.getText().toString().trim());
			  handler_4.SetRequest(new RequestType("",Type.checkSms),params);
			}
	 boolean ok=false;
	 boolean fs=false;
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
		   
	        if (keyCode == KeyEvent.KEYCODE_BACK) {
	        	
	        	digshow();
	        	
	            return false;
	        }
	        return super.onKeyDown(keyCode, event);
	    }
	 private void digshow() {
			// TODO Auto-generated method stub
			View mView = null;
	    	mView = mInflater.inflate(R.layout.dialog_myconfig,null);
			AbDialogUtil.showDialog(mView,R.animator.fragment_top_enter,R.animator.fragment_top_exit,R.animator.fragment_pop_top_enter,R.animator.fragment_pop_top_exit);
			Button leftBtn1 = (Button)mView.findViewById(R.id.left_btn);
			Button rightBtn1 = (Button)mView.findViewById(R.id.right_btn);
			TextView title_choices = (TextView)mView.findViewById(R.id.title_choices);
			TextView choice_one_text= (TextView)mView.findViewById(R.id.choice_one_text);
			//title_choices.setText("");
			choice_one_text.setText("是否放弃当前操作?");
			leftBtn1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					AbDialogUtil.removeDialog(myfindcheckcode.this);
				}
				
			});
			
			rightBtn1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent = null;
					intent = new Intent(myfindcheckcode.this,mylogin.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
					startActivity(intent);		
				}
				
			});
		}
	 public void onclick(View v) {
		 
		 if(v.getId()==R.id.my_but_ok)
		 {
			
			/*MessageBox.Show(mycheckcode.this , user+""+edi_my_yzm  
	                    .getText().toString());*/
			 ok=true;
			 cheaksms();
			
		 }
		 else if(v.getId()==R.id.back)
		 {
			 digshow();
		 }
		 
		 
	 }
	 
	private void reg() {
		// TODO Auto-generated method stub
		  Map<String, String> params = new HashMap<String, String>();
		  params.put("phone",user);
		  params.put("password", psw);
		  if(yqm.length()>=1)
			  params.put("invtCode", yqm);
		  params.put("password", psw);
		  handler_.SetRequest(new RequestType("",Type.register),params);
	}



	private void InitHandler()
	{
		
	   handler_ = new webhandler(){

			@Override
		public void OnError(int code, String mess) {
			// TODO Auto-generated method stub
			super.OnError(code, mess);
			my_but_ok.setEnabled(false);
			my_but_ok.setText("注册失败");
			cheak_ts.setText(mess.toString());
			Drawable drawable=myfindcheckcode.this.getResources().getDrawable(R.drawable.radius_hui);
			my_but_ok.setBackgroundDrawable(drawable);
			
	
		}

			@Override
			public void OnResponse(JSONObject response) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				
				
	
			}

			};
			
			handler_.SetProgressDialog(myfindcheckcode.this);
			
			handler_3= new webhandler(){

				@Override
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
				Toast.makeText(getApplicationContext(), mess,
						Toast.LENGTH_SHORT).show();
				
			}

			@Override
			public void OnResponse(JSONObject response) {
					// TODO Auto-generated method stub
				but_send_sms_.setbegin(true);
				Drawable drawable=myfindcheckcode.this.getResources().getDrawable(R.drawable.radius_hui);
				but_send_sms_.setBackgroundDrawable(drawable);
				but_send_sms_.begin();
				Toast.makeText(getApplicationContext(), "验证码已经发送",
						Toast.LENGTH_SHORT).show();
				}

				};
			handler_4= new webhandler(){

				@Override
				public void OnError(int code, String mess) {
					// TODO Auto-generated method stub
					super.OnError(code, mess);
					Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_SHORT).show();
				}
	
				@Override
				public void OnResponse(JSONObject response) {
						// TODO Auto-generated method stub
						 Intent intent0 = new Intent(myfindcheckcode.this, mysetpsw.class); 						
						 intent0.putExtra("user",user);  	
						 startActivity(intent0);	
				}
			};
	}		
	protected void onDestroy() {

		super.onDestroy();
	}

	 }