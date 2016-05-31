package mno.ruili_app.my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno_ruili_app.adapter.mySimpleAdapterfor;
import mno_ruili_app.adapter.tv;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class my_xgplace extends Activity  {//implements AMapLocationListener, OnClickListener
	EditText xgname;
	String city,provice;
	webhandler handler_; 
	final ArrayList<tv>   listprovince = new ArrayList<tv>();
	final ArrayList<tv>   listcity = new ArrayList<tv>();
	private String[] mFrom;
	ListView mListView2 ;
	ListView mListView;
	mySimpleAdapterfor adapter1,adapter2;
	TextView my_shen;//my_city,
	//private LocationManagerProxy mLocationManagerProxy;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_xgplace);
		provice="";
		city="";
		handler_ = new webhandler(){
			@Override
			public void OnResponse(JSONObject response) {
				appuser.getInstance().getUserinfo().provice=provice;
				if(provice.equals(city)){
					appuser.getInstance().getUserinfo().provice="";
				}				
				appuser.getInstance().getUserinfo().city=city;				
				finish();				
			}
		};			
		handler_.SetProgressDialog(my_xgplace.this);
		resdjson();
		init();	
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("my_xgplace");
		MobclickAgent.onResume(this); 
	}
	String result;
	String name,type;
	JSONObject  jsprovince,jscity;
	private void resdjson() {
			// TODO Auto-generated method stub
		InputStreamReader inputStreamReader;
		try {
			inputStreamReader = new InputStreamReader(getAssets().open("province.json"), "UTF-8");
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
		    for(int i=0;i<= length; i++){
		    	jsprovince =   data.getJSONObject(i);			   
			    name =  jsprovince.getString("name");
			    type=  jsprovince.getString("type");
				final tv mtv = new tv(name, type,"");
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
		    for(int i=0;i<= length; i++){		    	
		    	jsprovince =   data.getJSONObject(i);			   
				name =  jsprovince.getString("name");
				if(name.equals(city))			    
				    find(city);		    				   
		    }
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	private void find(String city) {
		listcity.clear();
		mFrom=new String[]{"北京","天津","上海","重庆","香港","澳门"};
		for (int i = 0; i <mFrom.length; i++) { 
			 if(city.equals(mFrom[i])){
				 my_shen.setText(mFrom[i]);
				 final tv mtv = new tv(mFrom[i], "","");
				/* final tv mtv2 = new tv("请选择", "","");
				 listcity.add(mtv2);*/
		    	listcity.add(mtv);
		    	adapter2.notifyDataSetChanged();
		    	return;
			 }        
	     } 	
		JSONArray code;
			try {				
				code = jsprovince.getJSONArray("sub");
				int length=code.length();
				for(int i=0;i<= length; i++){				    	
			    	JSONObject jscity =   code.getJSONObject(i);
					   
				    String name2 =  jscity.getString("name");
				    if(name2.equals("请选择")){
				    	my_shen.setText(city);
				    	
				    }else{
				    	final tv mtv = new tv(name2, "","");
				    	listcity.add(mtv);
				    	adapter2.notifyDataSetChanged();
			    	}						  
				}				    				   				    
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				    
	}

	private void init() {
		// TODO Auto-generated method stub
		//mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		//mLocationManagerProxy.setGpsEnable(false);
		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
		// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
		// 在定位结束后，在合适的生命周期调用destroy()方法
		// 其中如果间隔时间为-1，则定位只定一次,
		// 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
		//mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, 60 * 1000, 15, this);
		 mListView = (ListView) this.findViewById(R.id.mlistview);
		 mListView2 = (ListView) this.findViewById(R.id.mlistview2);
		 //LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.my_placetop, null);
		 //my_city=(TextView) layout.findViewById(R.id.my_city);
		 //mListView.addHeaderView(layout);
		
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
				if(!a.equals("请选择"))	{				
					getjson(a);
					
					mListView.setVisibility(view.GONE);
					mListView2.setVisibility(view.VISIBLE);
				}
			}			
		});
		mListView2.setOnItemClickListener(new OnItemClickListener(){	
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {										
				String a=listcity.get(position-1).getName();
				if(!a.equals("请选择")){
					  Map<String, String> params = new HashMap<String, String>();						  						  
					  provice=my_shen.getText().toString();
					  city=a;
					  if(provice.equals(city)){
						  params.put("provice","null");
						  params.put("city", city);
						  handler_.SetRequest(new RequestType("4",Type.editInfo),params);
					  }else{
						  params.put("provice",provice);
						  params.put("city", city);
						  handler_.SetRequest(new RequestType("4",Type.editInfo),params);
					  }						
				}
				else{						
				}					
			}				
		});		
	 }
	 public void onclick(View v) {
		 if(v.getId()==R.id.my_but_shen)
		 {
		        mListView2.setVisibility(v.GONE);
				mListView.setVisibility(v.VISIBLE);
		 }
		 else if(v.getId()==R.id.my_city_but){   
			 if(provice.length()>0){
				 Map<String, String> params = new HashMap<String, String>();			  
				 params.put("provice", provice);
				 params.put("city", city);				 
				 handler_.SetRequest(new RequestType("4",Type.editInfo),params);
		 }
			 else{
				 //mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, 60 * 1000, 15, this);
			 }
		 }
	 }
//	@Override
//	public void onLocationChanged(Location arg0) {
//	}
//	@Override
//	public void onProviderDisabled(String arg0) {
//	}
//	@Override
//	public void onProviderEnabled(String arg0) {
//	}
//	@Override
//	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
//	}
//	@Override
//	public void onClick(View arg0) {
//		
//	}
//	@Override
//	public void onLocationChanged(AMapLocation amapLocation) {
//		
//		if (amapLocation != null
//				&& amapLocation.getAMapException().getErrorCode() == 0) {
//			// 定位成功回调信息，设置相关消息
//			provice=amapLocation.getProvince();
//			city=amapLocation.getCity();
//			provice =provice.substring(0, provice.length()-1);
//			city=city.substring(0, city.length()-1);
//			//my_city.setText(amapLocation.getCity());//
//			
//			
//		/*	mLocationLatlngTextView.setText(amapLocation.getLatitude() + "  "
//					+ amapLocation.getLongitude());
//			mLocationAccurancyTextView.setText(String.valueOf(amapLocation
//					.getAccuracy()));
//			mLocationMethodTextView.setText(amapLocation.getProvider());
//	
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Date date = new Date(amapLocation.getTime());*/
//	
//			/*mLocationTimeTextView.setText(df.format(date));
//			mLocationDesTextView.setText(amapLocation.getAddress());
//			mLocationCountryTextView.setText(amapLocation.getCountry());
//			if (amapLocation.getProvince() == null) {
//				mLocationProvinceTextView.setText("null");
//			} else {
//				mLocationProvinceTextView.setText(amapLocation.getProvince());
//			}*/
//			/*mLocationCityTextView.setText(amapLocation.getCity());
//			mLocationCountyTextView.setText(amapLocation.getDistrict());
//			mLocationRoadTextView.setText(amapLocation.getRoad());
//			mLocationPOITextView.setText(amapLocation.getPoiName());
//			mLocationCityCodeTextView.setText(amapLocation.getCityCode());
//			mLocationAreaCodeTextView.setText(amapLocation.getAdCode());*/
//		} else {
//			//Log.e("AmapErr","Location ERR:" + amapLocation.getAMapException().getErrorCode());//
//		}
//		
//	}
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("my_xgplace");
		MobclickAgent.onPause(this);
		// 移除定位请求
		//mLocationManagerProxy.removeUpdates(this);
		// 销毁定位
		//mLocationManagerProxy.destroy();
	}
	
	protected void onDestroy() {
		super.onDestroy();
	
	}
}