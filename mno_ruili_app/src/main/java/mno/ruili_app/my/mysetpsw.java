package mno.ruili_app.my;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.umeng.analytics.MobclickAgent;





import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.main.Main;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.loginhandler;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class mysetpsw extends AbActivity   {
	private EditText  pass_;
	private EditText  pass_2;
	String user;
	String pass;
	String pass2;
	webhandler handler_; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.my_setpsw);
	
		Intent intent0 =this.getIntent();
		user=intent0.getStringExtra("user").toString();
		pass_ = (EditText)this.findViewById(R.id.edi_my_newpwd);
		pass_2 = (EditText)this.findViewById(R.id.edi_my_loginpsw);
		InitHandler();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("mysetpsw");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("mysetpsw");
		MobclickAgent.onPause(this);
	}
	private void InitHandler()
	{
		
		handler_ = new webhandler(){

			@Override
			public void OnResponse(JSONObject response) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				try {
					String a=response.getString("message");
					 MessageBox.Show(mysetpsw.this, a);
				 	Intent intent = null;
					intent = new Intent(mysetpsw.this,mylogin.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
					startActivity(intent);		
					finish();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			};
			
			handler_.SetProgressDialog(mysetpsw.this);
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
				AbDialogUtil.removeDialog(mysetpsw.this);
			}
			
		});
		
		rightBtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = null;
				intent = new Intent(mysetpsw.this,mylogin.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
				startActivity(intent);		
			}
			
		});
	}
	public void onclick(View v) {
		 if(v.getId()==R.id.my_but_login)
		 {
			  pass=pass_.getText().toString();
			  pass2=pass_2.getText().toString();
			  Map<String, String> params = new HashMap<String, String>();
			  if(pass.trim().length()>=6)
			  {
			  if(pass.equals(pass2))
			  {
			
			  params.put("phone",user);
			  params.put("newcode", pass);
			  handler_.SetRequest(new RequestType("",Type.resetcode),params);
			  }
			  else
				  MessageBox.Show(getApplicationContext(), "密码不一致，请重新输入");
			  }
			  else
				  MessageBox.Show(getApplicationContext(), "密码不能小于六位数，请重新输入");
			 
			 
		 }
		 else if(v.getId()==R.id.back)
		 {
			 digshow();
		 }
	}

}
