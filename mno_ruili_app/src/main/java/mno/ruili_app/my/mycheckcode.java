package mno.ruili_app.my;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.PassMgr;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.ct.TimeButton;
import mno.ruili_app.main.Main;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.loginhandler;
import mno_ruili_app.net.RequestType.Type;
import mno_ruili_app.net.webhandler;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class mycheckcode  extends AbActivity   {
	TimeButton but_send_sms_;
	EditText edi_my_yzm;
	TextView cheak_ts,my_but_ok;
	String user;
	String psw,yqm;
	webhandler handler_/*注册*/,handler_3/*验证send*/,handler_4/*验证cheak*/; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_mycheakcode);
	
		Intent intent0 =this.getIntent();
		user=intent0.getStringExtra("user").toString();
		psw=intent0.getStringExtra("psw").toString();
		yqm=intent0.getStringExtra("yqm").toString();
		
		cheak_ts = (TextView)this.findViewById(R.id.cheak_ts);
		my_but_ok= (TextView)this.findViewById(R.id.my_but_ok);
		cheak_ts.setText("验证码已发送到手机:"+user);
		but_send_sms_ = (TimeButton)this.findViewById(R.id.check_but_sms);
		edi_my_yzm= (EditText)this.findViewById(R.id.edi_my_yzm);
		but_send_sms_.setTextAfter("已发送").setTextBefore("重新发送").setLenght(60 * 1000);
		but_send_sms_.setText("正在发送");
		/*发送短信*/
		but_send_sms_.setOnClickListener(new OnClickListener(){

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				but_send_sms_.setbegin(false);
				sendsms();
				
				
				//but_send_sms_.setBackgroundColor(R.drawable.radius_blue);
		}});		
		InitHandler();		
		sendsms();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("mycheckcode");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("mycheckcode");
		MobclickAgent.onPause(this);
	}
	 private void sendsms() {
		// TODO Auto-generated method stub
		  Map<String, String> params = new HashMap<String, String>();
		  params.put("phone",user);
		  params.put("type","1");
		  handler_3.SetRequest(new RequestType("",Type.sendSms),params);
	 }
	 private void cheaksms() {
			// TODO Auto-generated method stub
		  Map<String, String> params = new HashMap<String, String>();
		  params.put("phone",user);
		  params.put("type","1");
		  params.put("code",edi_my_yzm.getText().toString().trim());
		  handler_4.SetRequest(new RequestType("",Type.checkSms),params);
	 }
	 boolean ok=false;
	 public void onclick(View v) {
		 /*验证*/
		 if(v.getId()==R.id.my_but_ok)
		 {
			ok=true;
			cheaksms();						
		 }
		 else if(v.getId()==R.id.back)
		 {
			 digshow();
		 }		 		 
	 }	
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
					AbDialogUtil.removeDialog(mycheckcode.this);
				}
				
			});
			
			rightBtn1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent = null;
					intent = new Intent(mycheckcode.this,mylogin.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
					startActivity(intent);		
				}
				
			});
		}
	private void reg() {
		// TODO Auto-generated method stub
		  Map<String, String> params = new HashMap<String, String>();
		  params.put("phone",user);
		  params.put("password", psw);
		  if(yqm.length()>=1)
			  params.put("invtCode", yqm);
		  //params.put("password", psw);
		  handler_.SetRequest(new RequestType("",Type.register),params);
	}
	loginhandler handler_2;


	private void InitHandler()
	{
		 handler_2 = new loginhandler(){

			@Override
			public void OnLoginSuccess(JSONObject response) {
				// TODO Auto-generated method stub
				
				PassMgr.Save(user, psw);//user电话号码不是uid，psw是密码；
				PassMgr.IsAuto(true);
				String huanxin="dsxuser"+appuser.getInstance().getUserinfo().uid.toString();	
				EMClient.getInstance().login(huanxin,huanxin,new EMCallBack() {//回调
					@Override
					public void onSuccess() {
						runOnUiThread(new Runnable() {
							public void run() {
								//EMClient.getInstance().groupManager().loadAllGroups();
								//EMClient.getInstance().chatManager().loadAllConversations();
								Log.i("main", "登陆聊天服务器成功！");		
							}							
						});
						Log.i("main", "登陆聊天服务器成功！");
					}				 
					@Override
					public void onProgress(int progress, String status) {
				 
					}					 
					@Override
					public void onError(int code, String message) {
						Log.i("main", code+message);
						Log.i("main", "登陆聊天服务器失败！");
					}
				});
				Intent intent = new Intent(mycheckcode.this,Main.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
				mycheckcode.this.startActivity(intent);
				finish();
			}
	    };
		   handler_2.SetProgressDialog(this);
		   //注册
		   handler_ = new webhandler(){
	
				@Override
				public void OnError(int code, String mess) {
					// TODO Auto-generated method stub
					super.OnError(code, mess);
					my_but_ok.setEnabled(false);
					my_but_ok.setText("注册失败");
					cheak_ts.setText(mess.toString());
					Drawable drawable=mycheckcode.this.getResources().getDrawable(R.drawable.radius_hui);
					my_but_ok.setBackgroundDrawable(drawable);
				}
	
				@Override
				public void OnResponse(JSONObject response) {
					//这里response返回的是{"message": "成功注册,欢迎您","data": {"uid": 1263},"code": 0}
					//注册环信的后台(自己服务器已经做了环信注册功能，app端不需要写以下注册代码，但要写登录环信的代码)				
//					try {
//						EMClient.getInstance().createAccount(huanxin, huanxin);
//					} catch (HyphenateException e) {//注册失败会抛出HyphenateException
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}//同步方法
					handler_2.Login(mycheckcode.this,user, psw);
				}
			};			
			handler_.SetProgressDialog(mycheckcode.this);
			//
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
					Drawable drawable=mycheckcode.this.getResources().getDrawable(R.drawable.radius_hui);
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
					reg();
				}

			};
	}
}