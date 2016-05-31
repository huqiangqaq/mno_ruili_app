package mno.ruili_app.my;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.main.Main;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class My_xgdanwei extends Activity{
	EditText xgdanwei;
	String text;
	webhandler handler_; 
	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.my_xgdanwei);
	Intent intent0 =this.getIntent();
	if(intent0.getStringExtra("text")!=null){
		text=intent0.getStringExtra("text").toString();
	}
	//text=intent0.getStringExtra("text").toString();
	init();
	
}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
	super.onResume();
	MobclickAgent.onPageStart("My_xgdanwei");
	MobclickAgent.onResume(this); 
}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	MobclickAgent.onPageEnd("My_xgdanwei");
	MobclickAgent.onPause(this);
}
private void init() {
	// TODO Auto-generated method stub
	 xgdanwei=(EditText)this.findViewById(R.id.xgdanwei);
	 xgdanwei.setText(text);
	 
	 handler_ = new webhandler(){

		@Override
		public void OnResponse(JSONObject response) {
			appuser.getInstance().getUserinfo().danwei=xgdanwei.getText().toString();
			finish();			
		}
	 };		
	 handler_.SetProgressDialog(My_xgdanwei.this);
}
 public void onclick(View v) {
	 if(v.getId()==R.id.my_but_ok)
	 {	 
		 String a=xgdanwei.getText().toString();
		 if(a.length()<=0)
			  a="null";		 
		  Map<String, String> params = new HashMap<String, String>();		  
		  params.put("shop_name",a);		 
		  handler_.SetRequest(new RequestType("4",Type.editInfo),params);
	 }
 }
}

