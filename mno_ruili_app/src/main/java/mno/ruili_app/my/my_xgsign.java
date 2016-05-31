package mno.ruili_app.my;

import java.util.HashMap;
import java.util.Map;

import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;

import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class my_xgsign extends Activity{
	EditText xgsign;
	String text;
	webhandler handler_; 
	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.my_xgsign);
	Intent intent0 =this.getIntent();
	if(intent0.getStringExtra("text")!=null){
		text=intent0.getStringExtra("text").toString();
	}	
	init();
	
}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("my_xgsign");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("my_xgsign");
		MobclickAgent.onPause(this);
	}
private void init() {
	// TODO Auto-generated method stub
	xgsign=(EditText)this.findViewById(R.id.xgsign);
	xgsign.setText(text);
	 
	 handler_ = new webhandler(){

			@Override
			public void OnResponse(JSONObject response) {
				appuser.getInstance().getUserinfo().signText=xgsign.getText().toString();
				finish();
				
			}

			};
			
			handler_.SetProgressDialog(my_xgsign.this);
}
 public void onclick(View v) {
	 if(v.getId()==R.id.my_but_ok)
	 {
		 String a=xgsign.getText().toString();
		 if(a.length()<=0)
			 a="null";
		  Map<String, String> params = new HashMap<String, String>();
		  
		  params.put("signText", a);
		 
		  handler_.SetRequest(new RequestType("4",Type.editInfo),params);
	 }
	 else if(v.getId()==R.id.my_but_registered)
	 {
		 
	 }
 }
}
