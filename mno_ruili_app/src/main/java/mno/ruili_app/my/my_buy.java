package mno.ruili_app.my;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mno.pay.alipay.PayActivity;
import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.main.HomeFragment;
import mno_ruili_app.adapter.ImageGridAdapter;
import mno_ruili_app.adapter.ImageGridAdapter2;
import mno_ruili_app.adapter.ImageListAdapter_myht;
import mno_ruili_app.adapter.ht;
import mno_ruili_app.adapter.tv;
import mno_ruili_app.home.home_video;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class my_buy  extends Activity{

	  webhandler handler_,handler_2; 
	  JSONObject type_json;
	  GridView mGridView;
	  ArrayList<tv>  mTvList;
	  ImageGridAdapter2 myGridViewAdapter;
	  TextView timedate,textView8,tz;
	  LinearLayout time,timeleft;
	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.my_buy);
	init();
	if(Constant.indentify.equals("会员"))
	{
	timedate.setText(Constant.memBeginTime+"    "+"至 "+"    "+Constant.memEndTime);
	textView8.setText(Constant.leftDay);
	}
	else
	{
		tz.setVisibility(View.VISIBLE);
		time.setVisibility(View.INVISIBLE);
		timeleft.setVisibility(View.INVISIBLE);
	}
	 handler_ = new webhandler(){

		 @Override
			public void OnResponse(JSONObject response) {
				try {
				
					mTvList.clear();
					
					JSONArray data = response.getJSONArray("data");
					int length=data.length();
				    for(int i=0;i<= length; i++)
				    {
				    type_json =   data.getJSONObject(i);
				    
					String img =  type_json.getString("longer");
					String name =  type_json.getString("price");
					String content =  type_json.getString("discountPrice");
					
					String id =  type_json.getString("productId");
					
					final tv mtv = new tv(id,name, content,img);
					
					mTvList.add(mtv);
					myGridViewAdapter.notifyDataSetChanged();
					
					
				    }

				    
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		handler_.SetProgressDialog(this);	
		Map<String, String> params = new HashMap<String, String>();
		handler_.SetRequest(new RequestType("4",Type.getProduct),params);
		handler_2 = new webhandler(){

 			@Override
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
				String ms=mess;
				ms=ms+"";
				MessageBox.Show(my_buy.this,mess);
				
			}

	

			@Override
 			public void OnResponse(JSONObject response) {
 				// TODO Auto-generated method stub
 				// TODO Auto-generated method stub
				try {
					JSONObject data=response.getJSONArray("data").getJSONObject(0);
					Constant.apiMemId=data.getString("apiMemId");
					Constant.apiNo=data.getString("apiNo");
					Constant.paymentId=data.getString("paymentId");
			
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 			}
 			
 			};
 			Map<String, String> params2 = new HashMap<String, String>();
 			handler_2.SetRequest(new RequestType("4",Type.getPayment),params2);
	   
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("my_buy");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("my_buy");
		MobclickAgent.onPause(this);
	}
	private void init() {
		// TODO Auto-generated method stub
		mTvList = new ArrayList<tv>();
		mGridView = (GridView)this.findViewById(R.id.gridView1);
		timedate = (TextView)this.findViewById(R.id.timedate);
		textView8 = (TextView)this.findViewById(R.id.textView8);
		tz= (TextView)this.findViewById(R.id.ts);
		time= (LinearLayout)this.findViewById(R.id.time);
		timeleft= (LinearLayout)this.findViewById(R.id.timeleft);
		mGridView.setGravity(Gravity.CENTER);
		mGridView.setHorizontalSpacing(10);
		mGridView.setPadding(0, 0, 0, 0);
		mGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		mGridView.setVerticalSpacing(10);
		mTvList = new ArrayList<tv>();
		

				// 使用自定义的Adapter
				myGridViewAdapter = new ImageGridAdapter2(this, mTvList);
				
			
				mGridView.setAdapter(myGridViewAdapter);
				
				mGridView.setOnItemClickListener(new OnItemClickListener(){

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(my_buy.this,PayActivity.class);
						//Constant.itemid=mTvList.get(position).getMs();
						Constant.itemzt=mTvList.get(position).getImg();
						Constant.grandTotal=mTvList.get(position).getName();
						Constant.totalDue=mTvList.get(position).getMs();
						Constant.productId=mTvList.get(position).getid();

						startActivity(intent);
						//AbToastUtil.showToast(HomeFragment.this.getActivity(),""+mTvList.get(position).getImg());
					}
		    		
		    	});
	}
}