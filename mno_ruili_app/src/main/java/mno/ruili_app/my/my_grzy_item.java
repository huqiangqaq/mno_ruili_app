package mno.ruili_app.my;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ab.activity.AbActivity;
import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.ObservableScrollView;
import mno.ruili_app.ct.imageview;
import mno.ruili_app.ct.ObservableScrollView.OnScrollListener;
import mno.ruili_app.ct.RoundImageView;
import mno_ruili_app.adapter.ImageListAdapter_sjz;
import mno_ruili_app.adapter.sjz;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.webpost;
import mno_ruili_app.net.RequestType.Type;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class my_grzy_item extends AbActivity implements ObservableScrollView.Callbacks{
	
	TextView view_down,select_tg_text,select_tg_bz,select_sj_text,select_sj_bz,select_yy_text,select_yy_bz,view_title,view_likeTotal;
	TextView select_tg_text2,select_tg_bz2,select_sj_text2,select_sj_bz2,select_yy_text2,select_yy_bz2;

	LinearLayout viedio_wd,viedio_ht,viedio_sp,mBuyLayout,mBuyLayout2;
	ObservableScrollView rootView;
	ListView mListView_wd,mListView_ht,mListView_sp;
	ImageListAdapter_sjz mywdAdapter;
	ImageListAdapter_sjz myhtAdapter,mykcAdapter;
	List<sjz>     wdList = new ArrayList<sjz>();
	List<sjz>   htList = new ArrayList<sjz>();
	List<sjz> kcList= new ArrayList<sjz>();
	webhandler handler_,handler_2,handler_3;
	JSONObject type_json;
	String path,id="",img="",name="";
	private Thread mThread;
	String enToStr;
	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.my_grzy_item);
	try
	{
	Intent intent0 =this.getIntent();
	id=intent0.getStringExtra("ID").toString();
	img=intent0.getStringExtra("img").toString();
	name=intent0.getStringExtra("name").toString();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		if(appuser.getInstance().IsLogin())
   	 {
		
		id= Constant.uid;
		img=appuser.getInstance().getUserinfo().bigImg.toString();
		name=appuser.getInstance().getUserinfo().nickname.toString();
   	 }
	}
	
	
	init();
	update_state();
	inithander();
/*	if(id.equals(appuser.getInstance().getUserinfo().uid.toString()))
	{
		select_sj_text2.setText("我的问答");
		select_tg_text2.setText("我的回帖");
		select_yy_text2.setText("我的视频");
	}*/
	

}
private void inithander() {
		// TODO Auto-generated method stub
	 
    handler_3= new webhandler(){

		
		@Override
		public void OnResponse(JSONObject response) {
			try {
			
				//kcList.clear();
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
    
    
   /* 2*/
    handler_2= new webhandler(){

		
		@Override
		public void OnResponse(JSONObject response) {
			try {
			
				//htList.clear();
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

			    String ReplyTime =  type_json.getString("ReplyTime");
			   
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
							 htList.add(mwd);}
			    		 else
			    		 {
			    			 final sjz mwd = new sjz( nextm,nextd, htdata, "");
							 htList.add(mwd);}
						
						
						
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
							 htList.add(mwd);}
			    		 else
			    		 {
			    			 final sjz mwd = new sjz( nextm,nextd, htdata, "");
							 htList.add(mwd);}
						
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
							 htList.add(mwd);}
			    		 else
			    		 {
			    			 final sjz mwd = new sjz( nextm,nextd, htdata, "");
							 htList.add(mwd);}
						
						
			    	 }
			    	
			    	 nextd=day;
			    	 nextm=month;
			    }
			    myhtAdapter.notifyDataSetChanged();

			    }
			    if(begin==0)
				   {
			    Map<String, String> params3 = new HashMap<String, String>();
				params3.put("uid", id);
				//params3.put("page","1");
				//params3.put("pagesize","10");
				params3.put("accessCode",  enToStr);
			    handler_3.SetRequest(new RequestType("2",Type.getCourseRecord),params3); 
				   }
			    //viedio_ht.setVisibility(view_.GONE);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				
			}
		}
	};
    
	 handler_ = new webhandler(){

		 @Override
			public void OnResponse(JSONObject response) {
			 
			 try {
				
					
				 	//wdList.clear();
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
				    	/* if(begin!=0)
						   {
							 nextm=wdList.get(wdList.size()-1).getmonth();
							 nextd=wdList.get(wdList.size()-1).getday();
						   }*/
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
				    			 wdList.add(mwd);}
				    		 else
				    		 {
				    			 final sjz mwd = new sjz( nextm,nextd, htdata, "");
				    			 wdList.add(mwd);}
							
							
							
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
				    			 wdList.add(mwd);}
				    		 else
				    		 {
				    			 final sjz mwd = new sjz( nextm,nextd, htdata, "");
				    			 wdList.add(mwd);}
							
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
				    			 wdList.add(mwd);}
				    		 else
				    		 {
				    			 final sjz mwd = new sjz( nextm,nextd, htdata, "");
				    			 wdList.add(mwd);}
							
							
				    	 }
				    	
				    	 nextd=day;
				    	 nextm=month;
				    }
				    mywdAdapter.notifyDataSetChanged();
				    }
				   // mywdAdapter.notifyDataSetChanged();
				   if(begin==0)
				   {
				     Map<String, String> params2 = new HashMap<String, String>();
					 params2.put("uid", id);
					// params2.put("page","1");
					// params2.put("pagesize","10");
					 params2.put("accessCode",  enToStr);
				     handler_2.SetRequest(new RequestType("4",Type.getSbReply),params2);
				   }
				    //viedio_ht.setVisibility(view_.GONE);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					
				}

			
			}
		};
				
	 Map<String, String> params = new HashMap<String, String>();
	 params.put("method","question");
	 params.put("uid", id);
	// params.put("page","1");
	 //params.put("pagesize","10");
     params.put("accessCode",  enToStr);
	 
     handler_.SetRequest(new RequestType("4",Type.getSbQA),params);
    // handler_.SetProgressDialog(my_grzy_item.this);
     
   
     
		
		
	    // handler_2.SetProgressDialog(my_grzy_item.this);
	     
	}
int sx=1;
int begin=0;
int a=1;int b=1;int c=1;
private void init() {
	// TODO Auto-generated method stub
	 rootView=(ObservableScrollView)this.findViewById(R.id.scrollView1);
	 viedio_wd=(LinearLayout)this.findViewById(R.id.viedio_wd);
	 viedio_ht=(LinearLayout)this.findViewById(R.id.viedio_ht);
	 viedio_sp=(LinearLayout)this.findViewById(R.id.viedio_sp);
	
	 rootView.setCallbacks(this);
	 rootView.getView();  
	 rootView.setOnScrollListener(new OnScrollListener(){

		@Override
		public void onBottom() {
			// TODO Auto-generated method stub
			begin++;
			//MessageBox.Show(getApplicationContext(), "正在加载");
			
			/*if(sx==1)
			{
			 a++;
			 Map<String, String> params = new HashMap<String, String>();
			 params.put("method","question");
			 params.put("uid", id);
			 //params.put("page",a+"");
			 //params.put("pagesize","20");
		     params.put("accessCode",  enToStr);
		     handler_.SetRequest(new RequestType("4",Type.getSbQA),params);
		     
			}
			if(sx==2)
			{
			 b++;
			 Map<String, String> params2 = new HashMap<String, String>();
			 params2.put("uid", id);
			// params2.put("page",b+"");
			// params2.put("pagesize","20");
			 params2.put("accessCode",  enToStr);
		     handler_2.SetRequest(new RequestType("4",Type.getSbReply),params2);
			}
			if(sx==3)
			{
			 c++;
			 Map<String, String> params3 = new HashMap<String, String>();
				params3.put("uid", id);
				//params3.put("page",c+"");
				//params3.put("pagesize","20");
				params3.put("accessCode",  enToStr);
			    handler_3.SetRequest(new RequestType("2",Type.getCourseRecord),params3); 
			}*/
		}

		@Override
		public void onTop() {
			// TODO Auto-generated method stub
			//MessageBox.Show(getApplicationContext(), "onTop");
		}

		@Override
		public void onScroll() {
			// TODO Auto-generated method stub
			
		}

		 
	});  
	 mBuyLayout = (LinearLayout) findViewById(R.id.buy);
	 mBuyLayout2 = (LinearLayout) findViewById(R.id.buy2);
	 rootView.getViewTreeObserver().addOnGlobalLayoutListener(
             new ViewTreeObserver.OnGlobalLayoutListener() {
                 @Override
                 public void onGlobalLayout() {
                     onScrollChanged(rootView.getScrollY());
                 }
             });
	 mListView_wd = (ListView)this.findViewById(R.id.mListView_wd);
	 mListView_ht = (ListView)this.findViewById(R.id.mListView_ht);
	 mListView_sp = (ListView)this.findViewById(R.id.mListView_sp);
	 mListView_wd.setFocusable(false);
	 mListView_ht.setFocusable(false);
	
	 mListView_sp.setFocusable(false);
	 //mBuyLayout.setFocusable(false);
	// viedio_ht.setVisibility(View.GONE);
	select_tg_text=(TextView)this.findViewById(R.id.select_tg_text);
	select_tg_bz=(TextView)this.findViewById(R.id.select_tg_bz);
	select_sj_text=(TextView)this.findViewById(R.id.select_sj_text);
	select_sj_bz=(TextView)this.findViewById(R.id.select_sj_bz);
	select_yy_text=(TextView)this.findViewById(R.id.select_yy_text);
	select_yy_bz=(TextView)this.findViewById(R.id.select_yy_bz);
	select_tg_text2=(TextView)this.findViewById(R.id.select_tg_text2);
	select_tg_bz2=(TextView)this.findViewById(R.id.select_tg_bz2);
	select_sj_text2=(TextView)this.findViewById(R.id.select_sj_text2);
	select_sj_bz2=(TextView)this.findViewById(R.id.select_sj_bz2);
	select_yy_text2=(TextView)this.findViewById(R.id.select_yy_text2);
	select_yy_bz2=(TextView)this.findViewById(R.id.select_yy_bz2);
	
	my_xx=(LinearLayout)this.findViewById(R.id.my_xx);
	my_login_image= (RoundImageView) this.findViewById(R.id.my_login_image);
	my_name= (TextView) this.findViewById(R.id.my_name);
	my_dz= (TextView) this.findViewById(R.id.my_dz);
	my_xq= (TextView) this.findViewById(R.id.my_xq);
	my_sex= (ImageView) this.findViewById(R.id.my_sex);
	my_vip= (ImageView) this.findViewById(R.id.my_vip);
	
	mywdAdapter = new ImageListAdapter_sjz(this, wdList,"wd",img);
	mListView_wd.setAdapter(mywdAdapter);
	
	myhtAdapter = new ImageListAdapter_sjz(this, htList,"ht",img);
	mListView_ht.setAdapter(myhtAdapter);
	
	mykcAdapter = new ImageListAdapter_sjz(this, kcList,"kc",img);
	mListView_sp.setAdapter(mykcAdapter);
	
	String str = "ruili"+id;
	// 在这里使用的是encode方式，返回的是byte类型加密数据，可使用new String转为String类型
	String strBase64 = new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
	Log.i("Test", "encode >>>" + strBase64);

	// 这里 encodeToString 则直接将返回String类型的加密数据
    enToStr = Base64.encodeToString(str.getBytes(), Base64.DEFAULT);

	  // mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
    
    
    /*1*/
   
	    
			
		     
}
private void get(String id2) {
	// TODO Auto-generated method stub
	
}
View view_;
public void onclick(View view) {
	view_=view;
	if(view.getId()==R.id.select_sj)
	{
		 sx=1;
		 
		 back();
		
		 select_sj_text.setTextColor(android.graphics.Color.parseColor("#ff6200"));
		 select_sj_bz.setBackgroundColor(android.graphics.Color.parseColor("#ff6200"));
		 select_sj_text2.setTextColor(android.graphics.Color.parseColor("#ff6200"));
		 select_sj_bz2.setBackgroundColor(android.graphics.Color.parseColor("#ff6200"));
		 viedio_wd.setVisibility(view.VISIBLE);
		 viedio_ht.setVisibility(view.GONE);
		 viedio_sp.setVisibility(view.GONE);
		 //indexpager.setCurrentItem(0, false);
	}
	else if(view.getId()==R.id.select_tg)
	{
		 sx=2;
		 back();
		 select_tg_text.setTextColor(android.graphics.Color.parseColor("#ff6200"));
		 select_tg_bz.setBackgroundColor(android.graphics.Color.parseColor("#ff6200"));
		 select_tg_text2.setTextColor(android.graphics.Color.parseColor("#ff6200"));
		 select_tg_bz2.setBackgroundColor(android.graphics.Color.parseColor("#ff6200"));
		 viedio_ht.setVisibility(view.VISIBLE);
		 viedio_wd.setVisibility(view.GONE);
		 viedio_sp.setVisibility(view.GONE);
		// indexpager.setCurrentItem(1, false);
	}
	else if(view.getId()==R.id.select_yy)
	{
		 sx=3;
		 back();
		 select_yy_text.setTextColor(android.graphics.Color.parseColor("#ff6200"));
		 select_yy_bz.setBackgroundColor(android.graphics.Color.parseColor("#ff6200"));
		 select_yy_text2.setTextColor(android.graphics.Color.parseColor("#ff6200"));
		 select_yy_bz2.setBackgroundColor(android.graphics.Color.parseColor("#ff6200"));
		 viedio_sp.setVisibility(view.VISIBLE);
		 viedio_ht.setVisibility(view.GONE);
		 viedio_wd.setVisibility(view.GONE);
		// indexpager.setCurrentItem(2, false);
	}
	else if(view.getId()==R.id.my_login_image)
	{
		Intent intent = new Intent(my_grzy_item.this,imageview.class);
	    intent.putExtra("url",RequestType.getWebUrl_(img)); 
	    
	    my_grzy_item.this.startActivity(intent);
		overridePendingTransition(R.anim.welcome_fade_in_scale, R.anim.welcome_fade_out); 
	}
}
private void back() {
	// TODO Auto-generated method stub
	 //rootView.smoothScrollTo(0, 0);
	 //mBuyLayout2.setVisibility(View.GONE);
	 select_tg_text.setTextColor(android.graphics.Color.parseColor("#919191"));
	 select_sj_text.setTextColor(android.graphics.Color.parseColor("#919191"));
	 select_yy_text.setTextColor(android.graphics.Color.parseColor("#919191"));
	 select_sj_bz.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
	 select_tg_bz.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
	 select_yy_bz.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
	 
	 select_tg_text2.setTextColor(android.graphics.Color.parseColor("#919191"));
	 select_sj_text2.setTextColor(android.graphics.Color.parseColor("#919191"));
	 select_yy_text2.setTextColor(android.graphics.Color.parseColor("#919191"));
	 select_sj_bz2.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
	 select_tg_bz2.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
	 select_yy_bz2.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
	
}


LinearLayout my_xx;
ImageView my_sex,my_vip;
TextView my_but_login,my_name,my_dz,my_xq,my_wd_num,my_sc_num,my_ht_num,my_xf_num;
RoundImageView my_login_image;
private void update_state() {
	// TODO Auto-generated method stub
	
		 webhandler handler_ = new webhandler(){

				@Override
				public void OnResponse(JSONObject response) {
					// TODO Auto-generated method stub
					// TODO Auto-generated method stub
					try {
						//appuser.getInstance().getUserinfo().pointTotal=response.getJSONObject("data").getString("pointTotal");
					
						//my_but_login.setVisibility(View.GONE);
						my_xx.setVisibility(View.VISIBLE);
						//String path=response.getJSONObject("data").getString("sex");
						
						String signText=response.getJSONObject("data").getString("signText");
						String sex=response.getJSONObject("data").getString("sex");
						String city=response.getJSONObject("data").getString("city");
						String provice=response.getJSONObject("data").getString("provice");
						String indentify=response.getJSONObject("data").getString("indentify");
						if(indentify.equals("会员"))
							
							my_vip.setImageDrawable(getResources().getDrawable(R.drawable.my_vip));
							else
								my_vip.setImageDrawable(getResources().getDrawable(R.drawable.novip));
						if(!city.equals("null")&&city.length()>0)
						{
						/*if(city.equals(provice))
							my_dz.setText(city);
						else if(city.length()>0){*/
						if(provice.trim().toLowerCase().equals("null"))
								provice="";
						my_dz.setText(provice+"   "+city);}
						
						else
							my_dz.setText("没有地址");
						
						if(name.length()>0)
						my_name.setText(name);
						else
							my_name.setText("没昵称");
						
						if(signText.length()>0)
						my_xq.setText(signText);
						else
							my_xq.setText("没有签名");
						if(sex.equals("男"))
						{
						my_sex.setImageDrawable(getResources().getDrawable(R.drawable.my_sex_man));
						select_sj_text2.setText("他的问答");
						select_tg_text2.setText("他的评论");
						select_yy_text2.setText("他的视频");
						}
						else
						{
							my_sex.setImageDrawable(getResources().getDrawable(R.drawable.my_sex_woman));
							
							select_sj_text2.setText("她的问答");
							select_tg_text2.setText("她的评论");
							select_yy_text2.setText("她的视频");
						}
						if(appuser.getInstance().IsLogin())
				    	 {
						
						if(id.equals(appuser.getInstance().getUserinfo().uid.toString()))
						{
							select_sj_text2.setText("我的问答");
							select_tg_text2.setText("我的评论");
							select_yy_text2.setText("我的视频");
						}
				    	 }
						my_login_image.setVisibility(View.VISIBLE);
						my_login_image.setImageUrl(RequestType.getWebUrl_(img),webpost.getImageLoader());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
			
					
				}
				
				};
				//handler_.SetProgressDialog(getActivity());
				Map<String, String> params = new HashMap<String, String>();
				params.put("uid", id);
				String str = "ruili"+id;
				// 在这里使用的是encode方式，返回的是byte类型加密数据，可使用new String转为String类型
				String strBase64 = new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
				Log.i("Test", "encode >>>" + strBase64);

				// 这里 encodeToString 则直接将返回String类型的加密数据
				String enToStr = Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
				params.put("accessCode",  enToStr);
				handler_.SetRequest(new RequestType("",Type.getUserBaseInt),params);
		
		
		
	
	/*else
	{
		Drawable drawable=this.getResources().getDrawable(R.drawable.m_logo);
		//TimeButton.this.setBackgroundDrawable(drawable);
		my_login_image.setVisibility(View.INVISIBLE);
		//my_but_login.setVisibility(View.VISIBLE);
		my_xx.setVisibility(View.GONE);
	}*/
}
@Override
public void onScrollChanged(int scrollY) {
	mBuyLayout2.setTranslationY(Math.max(mBuyLayout.getTop(), scrollY));
}


@Override
public void onDownMotionEvent() {
	//MessageBox.Show(getApplicationContext(), "ondown");
}

@Override
public void onUpOrCancelMotionEvent() {
	//MessageBox.Show(getApplicationContext(), "onup");
}
@Override
public void finish() {
	// TODO Auto-generated method stub
	super.finish();
	/*htList.clear();
	wdList.clear();
	mywdAdapter.notifyDataSetChanged();;
	myhtAdapter.notifyDataSetChanged();;*/
}

}

