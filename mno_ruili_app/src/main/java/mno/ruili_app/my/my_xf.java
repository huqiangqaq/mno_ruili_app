package mno.ruili_app.my;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.ct.RoundimgView;
import mno_ruili_app.adapter.ImageListAdapter;
import mno_ruili_app.adapter.ImageListAdapter_myxf;
import mno_ruili_app.adapter.tv;
import mno_ruili_app.adapter.wd;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class my_xf extends Activity{
	ListView mListView;
	ArrayList<tv>   mList = new ArrayList<tv>();
	webhandler handler_;
	JSONObject type_json;
	TextView  my_xftext ,myyqm;
	ImageListAdapter_myxf mylistViewAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_xf);
		init();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("my_xf");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("my_xf");
		MobclickAgent.onPause(this);
	}
	
	private void init() {
		// TODO Auto-generated method stub
		//RoundimgView roundimgView=(RoundimgView)this.findViewById(R.id.bj_image);
		 my_xftext= (TextView)this.findViewById(R.id.my_xftext);
		 mListView = (ListView)this.findViewById(R.id.mListView);
		 mylistViewAdapter = new ImageListAdapter_myxf(this, mList,
					R.layout.item_myxf, new String[] { "itemsIcon" },
					new int[] { R.id.itemstext,R.id.itemstext2,R.id.itemstext3});
		 mListView.setAdapter(mylistViewAdapter);
		 mListView.setFocusable(false);
		 myyqm=(TextView)this.findViewById(R.id.textView2);
		 myyqm.setText("我的邀请码："+appuser.getInstance().getUserinfo().invCode);
		 myyqm.setOnLongClickListener(new OnLongClickListener() {
			 @Override
			 public boolean onLongClick(View arg0) {
			 MessageBox.Show(getApplicationContext(), "文本已复制到粘贴板");
			 copy(appuser.getInstance().getUserinfo().invCode,my_xf.this);
			 return false;
			 }
		 });
		 handler_ = new webhandler(){
	
			 @Override
				public void OnResponse(JSONObject response) {
					try {
					
						mList.clear();
						JSONObject a=response.getJSONObject("data");
						String total=response.getJSONObject("data").getString("total").toString();
						my_xftext.setText( total);
						
						JSONArray data = response.getJSONObject("data").getJSONArray("data");
						int length=data.length();
					    for(int i=0;i<= length; i++){
						    type_json =   data.getJSONObject(i);
							String name =  type_json.getString("text");
							String content =  type_json.getString("point");
							String id =  type_json.getString("nowPoint");
							String fuhao =  type_json.getString("fuhao");
							final tv mtv = new tv(id,name, content,fuhao);						
						    mList.add(mtv);
						    mylistViewAdapter.notifyDataSetChanged();												
					    }						    
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			handler_.SetProgressDialog(this);	
		    Map<String, String> params = new HashMap<String, String>();
		
	        handler_.SetRequest(new RequestType("4",Type.getPointRule),params);
	}
	public static void copy(String content, Context context) {
		// 得到剪贴板管理器
		ClipboardManager cmb = (ClipboardManager) context
		.getSystemService(Context.CLIPBOARD_SERVICE);
		cmb.setText(content.trim());
	}

}
