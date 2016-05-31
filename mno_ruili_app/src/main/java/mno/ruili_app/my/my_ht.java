package mno.ruili_app.my;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno_ruili_app.adapter.ImageListAdapter_myht;
import mno_ruili_app.adapter.ImageListAdapter_mywd;
import mno_ruili_app.adapter.ht;
import mno_ruili_app.adapter.mywd;
import mno_ruili_app.home.home_video;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import mno_ruili_app.nei.nei_zx;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class my_ht extends AbActivity {
	
	 List<ht>   mList = new ArrayList<ht>();
	 private ListView mListView = null;
	 TextView nei_zx,nei_wd,bz_zx,bz_wd,my_but_bj,tv_all;
	  LinearLayout ll_bottom;
	  String deleteid="",deleteid2="";
	  ImageListAdapter_myht mylistViewAdapter;
	  webhandler handler_,handler_2,handler_3; 
	  JSONObject type_json;
	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.my_ht);
	init();
}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("my_ht");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("my_ht");
		MobclickAgent.onPause(this);
	}
private void init() {
	// TODO Auto-generated method stub
	 ll_bottom=(LinearLayout)this.findViewById(R.id.ll_bottom);
	 tv_all=(TextView)this.findViewById(R.id.tv_all);
	 
	 
	 my_but_bj=(TextView)this.findViewById(R.id.my_but_bj);
	 mListView = (ListView)this.findViewById(R.id.mListView);
	 //id, myReply, ReplyTime, title, Rcontent,  replyTotal, publish_at
	 mylistViewAdapter = new ImageListAdapter_myht(this, mList,
				R.layout.item_ht, new String[] { "itemsIcon" },
				new int[] { R.id.myReply ,R.id.title,R.id.replyTotal,R.id.ReplyTime});
	 mListView.setAdapter(mylistViewAdapter);
	 mListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(mylistViewAdapter.getCheck())
				{
					return;
				}
				if(mList.get(position).getwhere().equals("0"))
				{
					Intent intent = new Intent(my_ht.this,nei_zx.class);
					Constant.zxid=mList.get(position).getmianid();
					intent.putExtra("ID",mList.get(position).getid());  
					my_ht.this.startActivity(intent);
				}
				else
				{
					Intent intent = new Intent(my_ht.this,home_video.class);
					Constant.itemid=mList.get(position).getmianid();  
					//intent.putExtra("ID",mList.get(position).getid());  
					my_ht.this.startActivity(intent);
				}
			}
 		
 	});
	 handler_ = new webhandler(){

			
			@Override
			public void OnResponse(JSONObject response) {
				try {
					/*if(Isrefresh)
					{
						mAbPullToRefreshView.onHeaderRefreshFinish();
					}*/
					mList.clear();
					JSONArray data = response.getJSONArray("data");
					int length=data.length();
				    for(int i=0;i<= length; i++)
				    {
				    type_json =   data.getJSONObject(i);
				    //listData.add(data.getJSONObject(i).getString("Name"));
				    String courseId= "",newsId = "";
				    try {
				     courseId=type_json.getString("courseId");
				    
				    } catch (JSONException e) {
						// TODO Auto-generated catch block
				    	 newsId=type_json.getString("newsId");
					}
				    
				    String id =  type_json.getString("id");
				    String myReply =  type_json.getString("myReply");
				  
				    String ReplyTime =  type_json.getString("ReplyTime");
				    String title =  type_json.getString("title");
				    String Rcontent =  type_json.getString("Rcontent");
				    String replyTotal =  type_json.getString("replyTotal");
				    String publish_at =  type_json.getString("publish_at");
				    String coverImg="";
				    try
				    {
				     coverImg =  type_json.getString("coverImg");

				    } catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    if(newsId.length()>0)
				    {
				    final ht mwd = new ht( "0",newsId,id, myReply, ReplyTime, title, Rcontent,  replyTotal, publish_at);
				    
					mList.add(mwd);}
				    else
				    {
					    final ht mwd = new ht( "1",courseId,id, myReply, ReplyTime, title, Rcontent,  replyTotal, publish_at,coverImg);
					    
						mList.add(mwd);}
				    mylistViewAdapter.notifyDataSetChanged();
				    }
				   
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		handler_2 = new webhandler(){

			
			@Override
			public void OnResponse(JSONObject response) {
				
			/*String a=response.toString();
			a=a+"";*/
			
			}};
		LoadData();
}
public void LoadData( )
{
     Map<String, String> params = new HashMap<String, String>();
     params.put("uid",   Constant.uid);
        String str = "ruili"+ Constant.uid;
		// 在这里使用的是encode方式，返回的是byte类型加密数据，可使用new String转为String类型
		String strBase64 = new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
		Log.i("Test", "encode >>>" + strBase64);

		// 这里 encodeToString 则直接将返回String类型的加密数据
		String enToStr = Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
		params.put("accessCode",  enToStr);
     handler_.SetRequest(new RequestType("4",Type.getSbReply),params);
}
private void digshow(final String method) {
	// TODO Auto-generated method stub
	View mView = null;
	mView = mInflater.inflate(R.layout.dialog_myconfig,null);
	AbDialogUtil.showDialog(mView,R.animator.fragment_top_enter,R.animator.fragment_top_exit,R.animator.fragment_pop_top_enter,R.animator.fragment_pop_top_exit);
	Button leftBtn1 = (Button)mView.findViewById(R.id.left_btn);
	Button rightBtn1 = (Button)mView.findViewById(R.id.right_btn);
	TextView title_choices = (TextView)mView.findViewById(R.id.title_choices);
	TextView choice_one_text= (TextView)mView.findViewById(R.id.choice_one_text);
	//title_choices.setText("");
	choice_one_text.setText("是否删除所选数据?");
	leftBtn1.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			AbDialogUtil.removeDialog(my_ht.this);
		}
		
	});
	
	rightBtn1.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			 Map<String, Boolean> map = mylistViewAdapter.getCheckMap();
			 
				for (int i = 0; i < mList.size(); i++) {
					if (map.containsKey(mList.get(i).getid())
							&& map.get(mList.get(i).getid())) {
						if(mList.get(i).getwhere().equals("1"))
						{
							deleteid2=deleteid2+mList.get(i).getid().toString()+",";
						}
						else
						deleteid=deleteid+mList.get(i).getid().toString()+",";
						mList.remove(i);
						
						i--;
						
					}
				}
				mylistViewAdapter.configCheckMap(false);
				mylistViewAdapter.notifyDataSetChanged();
				if(deleteid.length()>0)
				{
			Map<String, String> params = new HashMap<String, String>();
			params.put("method",method);
			params.put("ids",deleteid.substring(0,deleteid.length()-1));
		    handler_2.SetRequest(new RequestType("4",Type.delMyData),params);
				}
				if(deleteid2.length()>0)
				{
					Map<String, String> params = new HashMap<String, String>();
					params.put("method","creply");
					params.put("ids",deleteid2.substring(0,deleteid2.length()-1));
				    handler_2.SetRequest(new RequestType("4",Type.delMyData),params);
						}
			deleteid2="";
		    deleteid="";
		    AbDialogUtil.removeDialog(my_ht.this);
			}
		
	});
}
public void delData(String method )
{
	digshow(method);
	
	 }
public void onclick(View v) {
	 if(v.getId()==R.id.my_but_login)
	 {
		 
	 }
	 else if(v.getId()==R.id.my_but_registered)
	 {
		 
	 }
	 else if(v.getId()==R.id.tv_all)
	 {
		 if (tv_all.getText().toString().trim().equals("全选")) {
		 mylistViewAdapter.configCheckMap(true);
		 mylistViewAdapter.notifyDataSetChanged();
		
		 tv_all.setText("全不选");
		 }
		 else{
			 mylistViewAdapter.configCheckMap(false);
			 mylistViewAdapter.notifyDataSetChanged();
			 
			 tv_all.setText("全选");
		 }
			 
		// all.setText("全选");
	 }
	 else if(v.getId()==R.id.tv_delete)
	 {
		 boolean size = mylistViewAdapter.getCheckMap().containsValue(true);
		 if(size)
			 delData("nreply");
		 
	 }
	 else if(v.getId()==R.id.my_but_bj)
	 {
		 if(mylistViewAdapter.getCheck())
		 {
			 my_but_bj.setText("编辑");
			 mylistViewAdapter.setBoolean(false);
			 mylistViewAdapter.setCheck(false);
			 ll_bottom.setVisibility(View.GONE);
		 }
		
		 else
		 {
			 my_but_bj.setText("完成");
			 mylistViewAdapter.setBoolean(true);
			 mylistViewAdapter.setCheck(true);
			 ll_bottom.setVisibility(View.VISIBLE);
		 }
		 tv_all.setText("全选");
		 mylistViewAdapter.configCheckMap(false);
		 mylistViewAdapter.notifyDataSetChanged();
		
	 }
}
}
