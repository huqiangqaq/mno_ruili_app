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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class my_xgsex extends Activity{
	EditText xgname;
	String text;
	webhandler handler_; 
	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.my_xgsex);
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
		MobclickAgent.onPageStart("my_xgsex");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("my_xgsex");
		MobclickAgent.onPause(this);
	}
private void init() {
	// TODO Auto-generated method stub
	 
	 /*  */
	 RadioGroup select = (RadioGroup)this.findViewById(R.id.select);
	
		RadioButton select1=(RadioButton)this.findViewById(R.id.select1); 
		
		RadioButton select2=(RadioButton)this.findViewById(R.id.select2); 
		if(text.equals("男"))
		{
			select1.setChecked(true);
			
			select2.setChecked(false);
		}
		else
		{
			select1.setChecked(false);
			select2.setChecked(true);
		}
		
		select.setOnCheckedChangeListener(new OnCheckedChangeListener(){

         @Override
         public void onCheckedChanged(RadioGroup group, int checkedId) {
             // TODO Auto-generated method stub
             if(checkedId==R.id.select1){
             	
            	 text="男";
             	
             	
             }
             if(checkedId==R.id.select2){
            	 text="女";
             	
             }
             
             

         }

		
     });
		
		
		
	 
	 
	 handler_ = new webhandler(){

			@Override
			public void OnResponse(JSONObject response) {
				appuser.getInstance().getUserinfo().sex=text;
				finish();
				
			}

			};
			
			handler_.SetProgressDialog(my_xgsex.this);
}
 public void onclick(View v) {
	 if(v.getId()==R.id.my_but_ok)
	 {
		 
		  Map<String, String> params = new HashMap<String, String>();
		  if(text.equals("男"))
			  params.put("sex","1");
		  else
			  params.put("sex", "2");
		 
		  handler_.SetRequest(new RequestType("4",Type.editInfo),params);
	 }
	 else if(v.getId()==R.id.my_but_registered)
	 {
		 
	 }
 }
}