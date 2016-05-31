package mno.ruili_app.my;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno_ruili_app.adapter.ImageListAdapter;
import mno_ruili_app.adapter.ImageListAdapter_mykc;
import mno_ruili_app.adapter.ImageListAdapter_sjz;
import mno_ruili_app.adapter.sjz;
import mno_ruili_app.adapter.tv;
import mno_ruili_app.adapter.zx;
import mno_ruili_app.home.home_mfvideo;
import mno_ruili_app.home.home_video;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.getlisthandler;
import mno_ruili_app.net.RequestType.Type;
import mno_ruili_app.net.webhandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class my_kcjl_item extends Activity{
	 webhandler handler_,handler_3;
	 List<sjz> kcList= new ArrayList<sjz>();
	 ImageListAdapter_sjz  mykcAdapter;
	 ListView mListView;
	 JSONObject type_json;
	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.my_kcjl_item);
	Constant.itemismy="1";
	init();
}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("my_kcjl_item");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("my_kcjl_item");
		MobclickAgent.onPause(this);
	}
private void init() {
		// TODO Auto-generated method stub
	     mListView = (ListView)this.findViewById(R.id.mListView);
	     
	     mykcAdapter =new ImageListAdapter_sjz(this, kcList,"kc","");
	     mListView.setAdapter(mykcAdapter);
	    
	     handler_3= new webhandler(){

				
				@Override
				public void OnResponse(JSONObject response) {
					try {
					
						kcList.clear();
						String httext="";
						JSONArray htdata = null;
						JSONArray data = response.getJSONArray("data");
						String nextm="";
						String nextd="";
						int length=data.length();
					    for(int i=0;i< length; i++)
					    {
					    	
					    type_json =   data.getJSONObject(i);
					    //listData.add(data.getJSONObject(i).getString("Name"));

					    String ReplyTime =  type_json.getString("create_at");
					   
					    String month=ReplyTime.substring(5, 7);
					    String day=ReplyTime.substring(8, 10);
					    if(i==0)
					    {
					    	nextd=day;
					    	nextm=month;
					    }
					    
					  
					    if(i!=0&&day.equals(nextd))
					    {
					    	httext+=","+type_json.toString();
					    	 if(i==length-1)
					    	 {
					    		 nextd=day;
					    		 //if(Integer.parseInt(day)<=Integer.parseInt(nextd))
					    		 htdata =  new JSONArray( "["+httext+"]" );	
					    		 
					    		 if(month.equals(nextm))
					    		 {
					    			 final sjz mwd = new sjz( month,nextd, htdata, "");
					    			 kcList.add(mwd);}
					    		 else
					    		 {
					    			 final sjz mwd = new sjz( nextm,nextd, htdata, "");
					    			 kcList.add(mwd);}
								
								
								
					    	 }
					    	
					    }
					   
					    else
					    {
					    	 if(httext.length()==0)
					    		 httext+=type_json.toString();
					    	 else if(httext.length()>0)
					    	 {
					    		 //if(Integer.parseInt(day)<=Integer.parseInt(nextd))
					    		 htdata =  new JSONArray( "["+httext+"]" );	
					    		 
					    		 if(month.equals(nextm))
					    		 {
					    			 final sjz mwd = new sjz( month,nextd, htdata, "");
					    			 kcList.add(mwd);}
					    		 else
					    		 {
					    			 final sjz mwd = new sjz( nextm,nextd, htdata, "");
					    			 kcList.add(mwd);}
								
								 httext=type_json.toString();
					    	 }
					    	 if(i==length-1)
					    	 {
					    		 nextd=day;
					    		 nextm=month;
					    		 //if(Integer.parseInt(day)<=Integer.parseInt(nextd))
					    		 htdata =  new JSONArray( "["+httext+"]" );	
					    		 
					    		 if(month.equals(nextm))
					    		 {
					    			 final sjz mwd = new sjz( month,nextd, htdata, "");
					    			 kcList.add(mwd);}
					    		 else
					    		 {
					    			 final sjz mwd = new sjz( nextm,nextd, htdata, "");
					    			 kcList.add(mwd);}
								
								
					    	 }
					    	
					    	 nextd=day;
					    	 nextm=month;
					    }
					    mykcAdapter.notifyDataSetChanged();
					    }
					    //viedio_ht.setVisibility(view_.GONE);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						
					}
				}
			};
			
	}

public void onclick(View v) {
	 if(v.getId()==R.id.my_but_login)
	 {
		 
	 }
	 else if(v.getId()==R.id.my_but_registered)
	 {
		 
	 }
}

@Override
protected void onStart() {
	// TODO Auto-generated method stub
	super.onStart();

	 Map<String, String> params3 = new HashMap<String, String>();
	 params3.put("uid", Constant.uid);
		String str = "ruili"+Constant.uid;
		// 在这里使用的是encode方式，返回的是byte类型加密数据，可使用new String转为String类型
		String strBase64 = new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
		Log.i("Test", "encode >>>" + strBase64);

		// 这里 encodeToString 则直接将返回String类型的加密数据
		String enToStr = Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
		params3.put("accessCode",  enToStr);
    handler_3.SetRequest(new RequestType("2",Type.getCourseRecord),params3); 
}

@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	Constant.itemismy="";
}

}
