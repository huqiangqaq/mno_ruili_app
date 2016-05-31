package mno.ruili_app.my;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.R;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.main.Main;
import mno_ruili_app.nei.nei_bj_tag;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.RequestType.Type;
import mno_ruili_app.net.webhandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

public class my_yjfk_item extends Activity{
	
	EditText my_fk_edi;
	 webhandler handler_; 
	 TextView textView2;
	@Override
	
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.my_yjfk_item);
	init();
}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("my_yjfk_item");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("my_yjfk_item");
		MobclickAgent.onPause(this);
	}
private void init() {
	// TODO Auto-generated method stub
	 my_fk_edi=(EditText)this.findViewById(R.id.my_fk_edi);
	 textView2=(TextView)this.findViewById(R.id.textView2);
	 my_fk_edi.addTextChangedListener(new TextWatcher() {  
		    @Override  
		    public void afterTextChanged(Editable s) {  
		    }  
		  
		    @Override  
		    public void beforeTextChanged(CharSequence s, int start, int count,  
		            int after) {  
		    }  
		  
		    @Override  
		    public void onTextChanged(CharSequence s, int start, int before,  
		            int count) {  
		        String content = my_fk_edi.getText().toString();  
		        textView2.setText(300-content.length()+"");
		    }  
		  
		});  
	 handler_ = new webhandler(){

			@Override
			public void OnResponse(JSONObject response) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				 
				try {
					String mess = response.getString("message");
					MessageBox.Show(my_yjfk_item.this, mess);
					//response -> {"message":"创建成功","data":3,"code":"0"}
					Intent intent = new Intent(my_yjfk_item.this,Main.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
					startActivity(intent);	
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
			}
			
			};
			handler_.SetProgressDialog(this);

}
public void onclick(View v) {
	 if(v.getId()==R.id.my_yjtj)
	 {
		 if(my_fk_edi.getText().length()<10)
		 {
			 MessageBox.Show(getApplicationContext(), "字数不能小于10个字");
		 }
		 else{
			 Map<String, String> params = new HashMap<String, String>();
			 
			  params.put("content",my_fk_edi.getText().toString());
			  handler_.SetRequest(new RequestType("4",Type.feedback),params);
		 }
			
		 
	 }
	 else if(v.getId()==R.id.my_but_registered)
	 {
		 
	 }
}
}
