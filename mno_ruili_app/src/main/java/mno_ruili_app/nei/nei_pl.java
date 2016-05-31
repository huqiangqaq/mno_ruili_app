package mno_ruili_app.nei;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mno.pay.alipay.PayActivity;
import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.main.MyFragment;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.webpost;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class nei_pl extends Activity{
	webhandler handler_; 
	String id,type;
	EditText nei_pl_edi;
	@Override
	
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.nei_wd_pl);
	Intent intent0 =this.getIntent();
	id=Constant.zxid;
	type=Constant.itemzt;
	init();
	InitHandler();
}
private void init() {
	// TODO Auto-generated method stub
	//nei_pl_edi  nei_pl_but
	nei_pl_edi=(EditText)this.findViewById(R.id.nei_pl_edi);
}
private void InitHandler()
{
	
	handler_ = new webhandler(){

		@Override
		public void OnResponse(JSONObject response) {
			 	
				finish();
			
		}
		
		};
		handler_.SetProgressDialog(this);
		
}
public void onclick(View v) {
	 if(v.getId()==R.id.nei_pl_but)
	 {
		 if(!appuser.getInstance().IsLogin())
    	 {
			 appuser.getInstance().LoginToAct(nei_pl.this,  nei_pl.class);
    		 return;
    	 }
		if(type.length()==0)
		{
		 Map<String, String> params = new HashMap<String, String>();
		  params.put("questionId",id);
		
		  params.put("desc",nei_pl_edi.getText().toString());
	     handler_.SetRequest(new RequestType("3",Type.addAnswer),params);
		}
		else
		{
			 Map<String, String> params = new HashMap<String, String>();
			  params.put("focusId",id);
			  params.put("type",type);
			  /*params.put("uid","3");
			  params.put("accessCode","cnVpbGkz");*/
			  params.put("content",nei_pl_edi.getText().toString());
		      handler_.SetRequest(new RequestType("3",Type.addReply),params);
			}
		
	 }
	 else if(v.getId()==R.id.my_but_registered)
	 {
		 
	 }
}
}