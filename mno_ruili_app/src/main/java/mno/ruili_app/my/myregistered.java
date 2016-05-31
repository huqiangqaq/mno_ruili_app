package mno.ruili_app.my;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.JustifyTextView;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.ct.validator;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class myregistered extends AbActivity {
	EditText phone;
	EditText pass,edi_my_yqm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ativity_my_myregistered);
		phone = (EditText)myregistered.this.findViewById(R.id.edi_my_zcname);
		pass = (EditText)myregistered.this.findViewById(R.id.edi_my_zcpsw);
		edi_my_yqm= (EditText)myregistered.this.findViewById(R.id.edi_my_yqm);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("myregistered");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("myregistered");
		MobclickAgent.onPause(this);
	}
	 public void onclick(View v) {
		
		 if(v.getId()==R.id.my_but_send)
		 {
			 if(phone.getText().toString().trim().length()>0)
				 
			 {
				 if(pass.length()>=6)
				 {
					 cheack();
			
				 }
				 else
				 {
					 MessageBox.Show(getApplicationContext(), "密码不能小于六位数，请重新输入");
				 }
			 }
			 else 
			 {
				 MessageBox.Show(getApplicationContext(), "号码不能为空");
			 }
		 }
		 else  if(v.getId()==R.id.login_findpwd)
		 {
			// digshow();
			 Intent intent0 = new Intent(myregistered.this, aboutroom.class); 

			 startActivity(intent0);
		 }
		 
		 
	 }
	 private void cheack() {
		// TODO Auto-generated method stub
		 webhandler handler_ = new webhandler(){

				@Override
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
				MessageBox.Show(myregistered.this,mess);
			}

				@Override
				public void OnResponse(JSONObject response) {
					// TODO Auto-generated method stub
					// TODO Auto-generated method stub
					try {
						
						 Intent intent0 = new Intent(myregistered.this, mycheckcode.class); 
							
						 String user=phone.getText().toString();
						 String psw=pass.getText().toString();
						 String yqm=edi_my_yqm.getText().toString();
						 intent0.putExtra("user",user);  
						 intent0.putExtra("psw",psw);  
						 intent0.putExtra("yqm",yqm);  
						 
						 startActivity(intent0);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
			
					
				}
				
				};
				//handler_.SetProgressDialog(getActivity());
				Map<String, String> params = new HashMap<String, String>();
				params.put("phone", phone.getText().toString());
				handler_.SetRequest(new RequestType("",Type.checkPhone),params);
				handler_.SetProgressDialog(myregistered.this);
	}
	private void digshow() {
			// TODO Auto-generated method stub
			View mView = null;
	    	mView = mInflater.inflate(R.layout.dialog_sc,null);
			AbDialogUtil.showDialog(mView,R.animator.fragment_top_enter,R.animator.fragment_top_exit,R.animator.fragment_pop_top_enter,R.animator.fragment_pop_top_exit);
			Button leftBtn1 = (Button)mView.findViewById(R.id.left_btn);
			Button rightBtn1 = (Button)mView.findViewById(R.id.right_btn);
			//TextView title_choices = (TextView)mView.findViewById(R.id.title_choices);
			mno.ruili_app.ct.JustifyTextView choice_one_text= (JustifyTextView)mView.findViewById(R.id.choice_one_text);
			//title_choices.setText("\n《隐私政策和服务协议》");
			String text = "";

	        BufferedReader br = null;
	        try {
	            String fileName ="ab.txt";
	            br = new BufferedReader(new InputStreamReader(getAssets().open(fileName)));
	            String line;
	            StringBuffer sb = new StringBuffer();
	            while ((line = br.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	            text = sb.toString();
	            choice_one_text.setText(text);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                br.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            
	            }}
			//title_choices.setTextColor(android.graphics.Color.parseColor("#387ebc"));
			//choice_one_text.setText("用户单独承担发布内容的责任。用户对服务的使用是根据所有适用于携程服务的地方法律、国家法律和国际法律标准的。用户必须遵循： \n(1)发布信息时必须符合中国有关法规；\n(2)使用网络会员服务不作非法用途；\n(3)不干扰或混乱网络服务；");
			leftBtn1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					AbDialogUtil.removeDialog(myregistered.this);
				}
				
			});
			
			rightBtn1.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					AbDialogUtil.removeDialog(myregistered.this);
				}
				
			});
		}
}