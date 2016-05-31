package mno.ruili_app.my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno_ruili_app.adapter.mySimpleAdapterfor;
import mno_ruili_app.adapter.tv;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class SelectPlaceActivity extends Activity{
	String text,city,provice;
	final ArrayList<tv>   listprovince = new ArrayList<tv>();
	final ArrayList<tv>   listcity = new ArrayList<tv>();
	private String[] mFrom,mId;
	ListView mListView2 ;
	ListView mListView;
	mySimpleAdapterfor adapter1,adapter2;//省和市的适配器
	TextView my_shen;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_xgplace);
		provice="";
		city="";
		readjson();
		init();	
	}
	
	String result;
	String name,id;
	JSONObject  jsprovince,jscity;
	private void readjson() {
			// TODO Auto-generated method stub
		InputStreamReader inputStreamReader;
		try {
			inputStreamReader = new InputStreamReader(getAssets().open("place.json"), "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		    String line;
		    StringBuilder stringBuilder = new StringBuilder();
		    while((line = bufferedReader.readLine()) != null) {
		    	stringBuilder.append(line);
		     }
		     result=stringBuilder.toString();
		     bufferedReader.close();
		     inputStreamReader.close();		
		      
		     } catch (UnsupportedEncodingException e) {
		    	  e.printStackTrace();
	    	  } catch (IOException e){
	    		  e.printStackTrace();
	    	  }			
		try {  				
			JSONArray  data =  new JSONArray(result );
			int length=data.length();
		    for(int i=0;i<length; i++){
		    	jsprovince = data.getJSONObject(i);			   
			    name =  jsprovince.getString("name");
			    id=  jsprovince.getString("id");
				final tv mtv = new tv(name, id,"");
				listprovince.add(mtv);			   
		    }
		} catch (JSONException e) {
			e.printStackTrace();
		}		    
	}
	private void getjson(String city) {
	// TODO Auto-generated method stub
		try {  				
			JSONArray  data =  new JSONArray(result );
			int length=data.length();
		    for(int i=0;i<length; i++){		    	
		    	jsprovince =data.getJSONObject(i);			   
				name =jsprovince.getString("name");
				if(name.equals(city)){			    
				    find(city);
				}
		    }
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	private void find(String city) {
		listcity.clear();
		mFrom=new String[]{"全部","北京","天津","上海","重庆","香港","澳门"};
		mId=new String[]{"0","300","2800","2600","400","3100","200"};
		for (int i = 0; i <mFrom.length; i++) { 
			 if(city.equals(mFrom[i])){
				 my_shen.setText(mFrom[i]);
				 final tv mtv = new tv(mFrom[i], mId[i],"");
		    	 listcity.add(mtv);
		    	 adapter2.notifyDataSetChanged();
		    	 return;
			 }        
	    } 	
		JSONArray code;
			try {				
				code = jsprovince.getJSONArray("child");
				int length=code.length();
				for(int i=0;i<length; i++){				    	
			    	JSONObject jscity =   code.getJSONObject(i);					   
				    String name2 =  jscity.getString("name");
				    String id2=jscity.getString("id");
				    my_shen.setText(city);
			    	final tv mtv = new tv(name2, id2,"");
			    	listcity.add(mtv);
			    	adapter2.notifyDataSetChanged();			    							  
				}				    				   				    
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				    
	}

	private void init() {
		 mListView = (ListView) this.findViewById(R.id.mlistview);
		 mListView2 = (ListView) this.findViewById(R.id.mlistview2);
		 
//		 LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.my_placetop, null);
//		 my_city=(TextView) layout.findViewById(R.id.my_city);
//		 mListView.addHeaderView(layout);
		
		 LinearLayout layout2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.my_placetop2, null);
		 my_shen=(TextView) layout2.findViewById(R.id.my_shen);
		 mListView2.addHeaderView(layout2);
		
		 adapter1 = new mySimpleAdapterfor(this ,listprovince, R.layout.item_city,
	    	    new String[] { "name" }, new int[] {R.id.name });
		 mListView.setAdapter(adapter1);
		
		 adapter2 = new mySimpleAdapterfor(this ,listcity, R.layout.item_city,
		    	    new String[] { "name" }, new int[] {R.id.name });
		 mListView2.setAdapter(adapter2);
		
		 mListView.setOnItemClickListener(new OnItemClickListener(){
	
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {				
				String a=listprovince.get(position).getName();
				getjson(a);				
				mListView.setVisibility(view.GONE);
				mListView2.setVisibility(view.VISIBLE);
			}			
		});
		 mListView2.setOnItemClickListener(new OnItemClickListener(){	
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {										
					Intent intent=new Intent();
					intent.putExtra("placeId", listcity.get(position-1).getMs());
					intent.putExtra("placeResult", listcity.get(position-1).getName());
					SelectPlaceActivity.this.setResult(RESULT_OK, intent);
					SelectPlaceActivity.this.finish();			
				}				
			});		
	 }
	 public void onclick(View v) {
		 if(v.getId()==R.id.my_but_shen){
	        mListView2.setVisibility(v.GONE);
			mListView.setVisibility(v.VISIBLE);
		 }
	 }

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("SelectPlaceActivity");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("SelectPlaceActivity");
		MobclickAgent.onPause(this);
	}
}