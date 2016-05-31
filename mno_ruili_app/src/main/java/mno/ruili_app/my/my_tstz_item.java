package mno.ruili_app.my;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import mno.ruili_app.PassMgr;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.MessageBox;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class my_tstz_item extends Activity{
	CheckBox check_1,check_2;
	int i=1;int h=1;
	webhandler handler_;
	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.my_tstz_item);
	init();
}
private void init() {
	// TODO Auto-generated method stub
	check_1 = (CheckBox)this.findViewById(R.id.my_xw_but);
	check_1.setChecked(PassMgr.Isxw());
	
	check_1.setOnCheckedChangeListener(new OnCheckedChangeListener(){

		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			// TODO Auto-generated method stub
			PassMgr.Isxw(arg1);
			if(arg1)
				i=1;
			else
				i=2;
			change("newsPush",i);
			}});
	check_2 = (CheckBox)this.findViewById(R.id.my_zb_but);
	check_2.setChecked(PassMgr.Iszb());
	check_2.setOnCheckedChangeListener(new OnCheckedChangeListener(){

		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			// TODO Auto-generated method stub
			//MessageBox.Show(getApplicationContext(), arg1+"");
			PassMgr.Iszb(arg1);
			if(arg1)//arg1位true是执行
				h=1;
			else
				h=2;
			change("coursePush",h);
		}});
	    handler_ = new webhandler(){

		@Override
		public void OnResponse(JSONObject response) {
			
			
		}

		};
		
		//handler_.SetProgressDialog(my_tstz_item.this);
		
	
}
//
protected void change(String string,int a) {
	// TODO Auto-generated method stub
	Map<String, String> params = new HashMap<String, String>();
	params.put(string,a+"");
	
	handler_.SetRequest(new RequestType("4",Type.editInfo),params);
}
//
public void onclick(View v) {
	 if(v.getId()==R.id.my_but_login)
	 {
		 
	 }
	 else if(v.getId()==R.id.my_but_registered)
	 {
		 
	 }
}
}
