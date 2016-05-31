package mno.ruili_app.my;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.toolbox.NetworkImageView;
import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.webpost;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

public class SystemMsgDetail extends Activity {
	ScrollView sv_msg;
	TextView tv_title,tv_content;
	NetworkImageView iv_image;
	String id,title,image,content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_msg_detail);
		Intent intent0 =this.getIntent();
		title=intent0.getStringExtra("title").toString();
		image=intent0.getStringExtra("image").toString();
		content=intent0.getStringExtra("content").toString();
		id=Constant.itemid;
		webhandler handler=new webhandler(){
			  public void OnResponse(org.json.JSONObject response) {};
	    };
	    Map<String, String> params4 = new HashMap<String, String>();
	    params4.put("msg_id",id );
	    handler.SetRequest(new RequestType("3",Type.msgView),params4);
		init();
	}

	private void init() {
		sv_msg=(ScrollView) findViewById(R.id.sv_msg);
		tv_title=(TextView) findViewById(R.id.tv_title);
		tv_content=(TextView) findViewById(R.id.tv_content);
		iv_image=(NetworkImageView) findViewById(R.id.iv_image);
		
		String imageUrl =RequestType.getWebUrl_(image);
		tv_title.setText(title);
		tv_content.setText(content);
		iv_image.setImageUrl(imageUrl, webpost.getImageLoader());
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("SystemMsgDetail");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("SystemMsgDetail");
		MobclickAgent.onPause(this);
	}
}
