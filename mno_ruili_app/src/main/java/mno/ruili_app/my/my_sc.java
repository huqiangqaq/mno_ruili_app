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
import mno_ruili_app.adapter.ImageListAdapter_mysc;
import mno_ruili_app.adapter.ImageListAdapter_mywd;
import mno_ruili_app.adapter.ht;
import mno_ruili_app.adapter.mywd;
import mno_ruili_app.adapter.zx;
import mno_ruili_app.home.home_video;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class my_sc extends AbActivity{

	 List<zx>   mList = new ArrayList<zx>();
	 List<mywd>   mList2 = new ArrayList<mywd>();
	private ListView mListView = null;
	private ListView mListView2 = null;
	webhandler handler_,handler_2,handler_3; 
	  JSONObject type_json;
	 ImageListAdapter_mysc mylistViewAdapter;
	ImageListAdapter_mywd mylistViewAdapter2;
	
	  TextView nei_zx,nei_wd,bz_zx,bz_wd,my_but_bj,tv_all;
	  LinearLayout ll_bottom;
	  String deleteid="",deleteid2="";
		@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_sc);
		init();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("my_sc");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("my_sc");
		MobclickAgent.onPause(this);
	}	
	private void init() {
		// TODO Auto-generated method stub
		 ll_bottom=(LinearLayout)this.findViewById(R.id.ll_bottom);
		 my_but_bj=(TextView)this.findViewById(R.id.my_but_bj);
		 tv_all=(TextView)this.findViewById(R.id.tv_all);
		 nei_zx= (TextView)this.findViewById(R.id.my_butwen);
		 nei_wd= (TextView)this.findViewById(R.id.my_butda);
		 bz_zx= (TextView)this.findViewById(R.id.bz_nei_zx);
		 bz_wd= (TextView)this.findViewById(R.id.bz_nei_wd);
		 mListView = (ListView)this.findViewById(R.id.mListView);
		 mListView2= (ListView)this.findViewById(R.id.mListView2);
		 mylistViewAdapter = new ImageListAdapter_mysc(this, mList,
					R.layout.item_list, new String[] { "itemsIcon" },
					new int[] { R.id.itemsIcon ,R.id.itemstext,R.id.itemstext2});
		  mylistViewAdapter2 = new ImageListAdapter_mywd(this, mList2,
				  R.layout.item_wen, new String[] { "itemsIcon" },
				  new int[] {R.id.title, R.id.wdcontent ,R.id.wdname,R.id.wdtime,R.id.answerTotal});
	       
	       mListView.setAdapter(mylistViewAdapter);
	       mListView2.setAdapter(mylistViewAdapter2);
	       mListView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if(mylistViewAdapter.getCheck())
					{
						return;
					}
					Intent intent = new Intent(my_sc.this,home_video.class);
					Constant.itemid=mList.get(position).getid();
					intent.putExtra("ID",mList.get(position).getid());  
					my_sc.this.startActivity(intent);
				}
	    		
	    	});
	       mListView2.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if(mylistViewAdapter.getCheck())
					{
						return;
					}
					Intent intent = new Intent(my_sc.this,mno_ruili_app.nei.nei_zx.class);
					Constant.zxid=mList2.get(position).getid();
					Constant.zxtitle=mList2.get(position).getanswerTotal();
					intent.putExtra("ID",mList2.get(position).getid());  
					my_sc.this.startActivity(intent);
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
					    String name =  type_json.getString("title");
					    String content =  type_json.getString("content");
					    String id =  type_json.getString("colleId");
					    String imgurl=type_json.getString("coverImg");
					    
					    final zx mzx = new zx(name,content, id,imgurl);
					    
						mList.add(mzx);
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
						try {
							/*if(Isrefresh)
							{
								mAbPullToRefreshView.onHeaderRefreshFinish();
							}*/
							mList2.clear();
							JSONArray data = response.getJSONArray("data");
							int length=data.length();
						    for(int i=0;i<= length; i++)
						    {
						    type_json =   data.getJSONObject(i);
						    //listData.add(data.getJSONObject(i).getString("Name"));
						    String name = type_json.getString("title");
						    String content =  type_json.getString("content");
						    String id =  type_json.getString("colleId");
						    String create_at =  type_json.getString("publish_at");
						    String answerTotal =  type_json.getString("replyTotal");
						    
						    final mywd mwd = new mywd(name,content, id, "", create_at, answerTotal);
						    
						    
							mList2.add(mwd);
						    mylistViewAdapter2.notifyDataSetChanged();
						    }
						   
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				handler_3 = new webhandler(){

					
					@Override
					public void OnResponse(JSONObject response) {
						
					String a=response.toString();
					a=a+"";
					
					}};
					
			LoadData("course");
			LoadData("news");
	}
	public void LoadData(String method )
	{
	     Map<String, String> params = new HashMap<String, String>();
		 params.put("method",method);
		 if(method.equals("course"))
		 {
	     handler_.SetRequest(new RequestType("4",Type.getMyColl),params);
		 }
		 else
		 {
			 handler_2.SetRequest(new RequestType("4",Type.getMyColl),params);
		 }
	}
	private void digshow() {
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
				AbDialogUtil.removeDialog(my_sc.this);
			}
			
		});
		
		rightBtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(where.equals("wen")){
					 Map<String, Boolean> map = mylistViewAdapter.getCheckMap();
					 
						for (int i = 0; i < mList.size(); i++) {
							if (map.containsKey(mList.get(i).getid())
									&& map.get(mList.get(i).getid())) {
								deleteid=deleteid+mList.get(i).getid().toString()+",";
								mList.remove(i);
								/*int a=(Integer.parseInt(appuser.getInstance().getUserinfo().quesTotal)-1);
								appuser.getInstance().getUserinfo().quesTotal=String.valueOf(a);*/
								i--;
								
							}
						}
						mylistViewAdapter.configCheckMap(false);
						mylistViewAdapter.notifyDataSetChanged();
						if(deleteid.length()>0)
						{
							delData("course");
							deleteid="";
						}
					 }
					 else if(where.equals("da")){
						 Map<String, Boolean> map2 = mylistViewAdapter2.getCheckMap();
						 
							for (int i = 0; i < mList2.size(); i++) {
								if (map2.containsKey(mList2.get(i).getid())
										&& map2.get(mList2.get(i).getid())) {
									deleteid2=deleteid2+mList2.get(i).getid().toString()+",";
									mList2.remove(i);
									i--;
								}
							}
							mylistViewAdapter2.configCheckMap(false);
							mylistViewAdapter2.notifyDataSetChanged();
							
							 if(deleteid2.length()>0)
							{
								delData("news");
								deleteid2="";
							}
								
					 }
						
			    AbDialogUtil.removeDialog(my_sc.this);
				}
			
		});
	}
	public void delData(String method )
	{
		
		 if(method.equals("course"))
		 {
			 Map<String, String> params = new HashMap<String, String>();
			 params.put("method",method);
		     params.put("ids",deleteid.substring(0,deleteid.length()-1));
		 
	     handler_3.SetRequest(new RequestType("4",Type.delMyData),params);
		 }
		 else if(method.equals("news"))
		 
		 {
		 Map<String, String> params = new HashMap<String, String>();
		 params.put("method",method);
		 params.put("ids",deleteid2.substring(0,deleteid2.length()-1));
		 
	     handler_3.SetRequest(new RequestType("4",Type.delMyData),params);
		 }
	
			
	}
	String where="wen";
	 public void onclick(View v) {
		 
		Drawable tnei_zx = getResources().getDrawable(R.drawable.my_sp_);
     	Drawable tnei_zx_ = getResources().getDrawable(R.drawable.my_sp);
     	Drawable tnei_wd = getResources().getDrawable(R.drawable.my_zx);
     	Drawable tnei_wd_ = getResources().getDrawable(R.drawable.my_zx_);
		 if(v.getId()==R.id.nei_zx)
		 {
			 mylistViewAdapter.configCheckMap(false);
			 mylistViewAdapter.notifyDataSetChanged();
			 where="wen";
			 mListView.setVisibility(View.VISIBLE);
			 mListView2.setVisibility(View.GONE);
			 nei_zx.setTextColor(android.graphics.Color.parseColor("#ff6200"));//#387ebc
			 nei_wd.setTextColor(android.graphics.Color.parseColor("#919191"));
			 nei_wd.setCompoundDrawablesWithIntrinsicBounds(tnei_wd_,null, null,null);
     		 nei_zx.setCompoundDrawablesWithIntrinsicBounds(tnei_zx,null, null,null);
			
    		 bz_zx.setBackgroundColor(android.graphics.Color.parseColor("#ff6200"));
    		 bz_wd.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
		 }
		 else if(v.getId()==R.id.nei_wd)
		 {
			 mylistViewAdapter2.configCheckMap(false);
			 mylistViewAdapter2.notifyDataSetChanged();
			 where="da";
			 mListView2.setVisibility(View.VISIBLE);
			 mListView.setVisibility(View.GONE);
			 nei_wd.setTextColor(android.graphics.Color.parseColor("#ff6200"));
			 nei_zx.setTextColor(android.graphics.Color.parseColor("#919191"));
			 nei_wd.setCompoundDrawablesWithIntrinsicBounds(tnei_wd,null, null,null);
     		 nei_zx.setCompoundDrawablesWithIntrinsicBounds(tnei_zx_,null, null,null);
    		 bz_wd.setBackgroundColor(android.graphics.Color.parseColor("#ff6200"));
    		 bz_zx.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
		 }
		 else if(v.getId()==R.id.tv_all)
		 {
			 if (tv_all.getText().toString().trim().equals("全选")) {
			 mylistViewAdapter.configCheckMap(true);
			 mylistViewAdapter.notifyDataSetChanged();
			 mylistViewAdapter2.configCheckMap(true);
			 mylistViewAdapter2.notifyDataSetChanged();
			 tv_all.setText("全不选");
			 }
			 else{
				 mylistViewAdapter.configCheckMap(false);
				 mylistViewAdapter.notifyDataSetChanged();
				 mylistViewAdapter2.configCheckMap(false);
				 mylistViewAdapter2.notifyDataSetChanged();
				 tv_all.setText("全选");
			 }
				 
			// all.setText("全选");
		 }
		 else if(v.getId()==R.id.tv_delete)
		 {
			 if(where.equals("wen"))
			 {
				 boolean size = mylistViewAdapter.getCheckMap().containsValue(true);
				 if(size)
					 digshow();
			 }
			 else
			 {
				 boolean size = mylistViewAdapter2.getCheckMap().containsValue(true);
				 if(size)
					 digshow();
			 }
			 
				
			 
		 }
		 else if(v.getId()==R.id.my_but_bj)
		 {
			 if(mylistViewAdapter.getCheck())
			 {
				 my_but_bj.setText("编辑");
				 mylistViewAdapter.setBoolean(false);
				 mylistViewAdapter2.setBoolean(false);
				 mylistViewAdapter.setCheck(false);
				 ll_bottom.setVisibility(View.GONE);
			 }
			
			 else
			 {
				 my_but_bj.setText("完成");
				 mylistViewAdapter.setBoolean(true);
				 mylistViewAdapter2.setBoolean(true);
				 mylistViewAdapter.setCheck(true);
				 ll_bottom.setVisibility(View.VISIBLE);
			 }
			 tv_all.setText("全选");
			 mylistViewAdapter.configCheckMap(false);
			 mylistViewAdapter2.configCheckMap(false);
			 mylistViewAdapter.notifyDataSetChanged();
			 mylistViewAdapter2.notifyDataSetChanged();
		 }
	 }
}
