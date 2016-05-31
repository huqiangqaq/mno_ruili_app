package mno.ruili_app.main;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import mno.down.ui.DownloadActivity;
import mno.pay.alipay.PayActivity;
import mno.ruili_app.Constant;
import mno.ruili_app.PassMgr;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.ct.RoundImageView;
import mno.ruili_app.ct.TimeButton;
import mno.ruili_app.ct.mnoScrollView;
import mno.ruili_app.my.AddEmpMsg;
import mno.ruili_app.my.My_setup_item;
import mno.ruili_app.my.My_xiaoxi_item;
import mno.ruili_app.my.my_buy;
import mno.ruili_app.my.my_bz_item;
import mno.ruili_app.my.my_grzy_item;
import mno.ruili_app.my.my_gyll_item;
import mno.ruili_app.my.my_ht;
import mno.ruili_app.my.my_jcgx_item;
import mno.ruili_app.my.my_kcjl_item;
import mno.ruili_app.my.my_sc;
import mno.ruili_app.my.my_tstz_item;
import mno.ruili_app.my.my_wd;
import mno.ruili_app.my.my_wdxa_item;
import mno.ruili_app.my.my_xf;
import mno.ruili_app.my.my_xx;
import mno.ruili_app.my.my_xxxg;
import mno.ruili_app.my.my_yjfk_item;
import mno.ruili_app.my.mylogin;
import mno_ruili_app.home.home_video;
import mno_ruili_app.nei.nei_bj;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.webpost;
import mno_ruili_app.net.RequestType.Type;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView.FindListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyFragment extends Fragment {
	View View_;
	mnoScrollView rootView;
	ImageView my_sex,my_vip,tsimg;
	TextView my_but_login,my_name,my_dz,my_xq,my_wd_num,my_sc_num,my_ht_num,my_xf_num,my_qd,my_login_ts,tv_message;
	RoundImageView my_login_image;
	LinearLayout my_xx,my_wd,my_sc,my_ht,my_xf,my_kcjl_item,my_wdxa_item,my_grzy_item,my_setUp_item,my_hygm_item,my_jobPlace_item;
	RelativeLayout my_login_imagebox,my_top,my_xiaoxi/*消息提示*/;
	webhandler handler;
	Map<String, String> params ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View_ = inflater.inflate(R.layout.fmt_my, container,false);
		//1.头部的广播
		IntentFilter filter = new IntentFilter();
		filter.addAction("mno.tz");		
		filter.setPriority(Integer.MAX_VALUE);
		MyFragment.this.getActivity().registerReceiver(myReceiver, filter);
		//2.下面四个东东
		IntentFilter filter2 = new IntentFilter();
		filter2.addAction("mno.xx");		
		filter2.setPriority(Integer.MAX_VALUE);
		MyFragment.this.getActivity().registerReceiver(myReceiver2, filter2);
		//3.注册喇叭的红点广播
		IntentFilter filter3= new IntentFilter();
		filter3.addAction("mno.push");
		MyFragment.this.getActivity().registerReceiver(myPushReceiver, filter3);
		init();		
		return View_;		
	}
	//喇叭那里的消息红点的广播
	private BroadcastReceiver myPushReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			webhandler handler2=new webhandler(){
				@Override
				public void OnResponse(JSONObject response) {
					super.OnResponse(response);
					try {
						JSONObject data = response.getJSONObject("data");
						String total=data.getString("total");
						int i=Integer.parseInt(total);
						if(i==0){
							tv_message.setVisibility(View.INVISIBLE);
						}else{
							tv_message.setVisibility(View.VISIBLE);
							if(i<=99){
								tv_message.setText(i+"");
							}else{
								tv_message.setText(99+"+");
							}								
						}							
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			};
			params = new HashMap<String, String>();
			handler2.SetRequest(new RequestType("3",Type.getRedPoint),params);
		}
	};	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("MyFragment");
		params = new HashMap<String, String>();
		handler.SetRequest(new RequestType("3",Type.getRedPoint),params);
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("MyFragment");
	}
	private void init() {
		// TODO Auto-generated method stub
		tv_message=(TextView) View_.findViewById(R.id.tv_message);
		handler=new webhandler(){
			@Override
			public void OnResponse(JSONObject response) {
				// TODO Auto-generated method stub
				super.OnResponse(response);
				try {
					JSONObject data = response.getJSONObject("data");
					String total=data.getString("total");
					int i=Integer.parseInt(total);
					if(i==0){
						tv_message.setVisibility(View.INVISIBLE);
					}else{
						tv_message.setVisibility(View.VISIBLE);
						if(i<99){
							tv_message.setText(i+"");
						}else{
							tv_message.setText(99+"+");
						}						
					}					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
		params = new HashMap<String, String>();
		handler.SetRequest(new RequestType("3",Type.getRedPoint),params);
		
		my_but_login = (TextView) View_.findViewById(R.id.my_but_login);
		my_name= (TextView) View_.findViewById(R.id.my_name);
		my_dz= (TextView) View_.findViewById(R.id.my_dz);
		my_xq= (TextView) View_.findViewById(R.id.my_xq);
		my_login_ts= (TextView) View_.findViewById(R.id.my_login_ts);
		my_sex= (ImageView) View_.findViewById(R.id.my_sex);
		my_vip= (ImageView) View_.findViewById(R.id.my_vip);
		tsimg = (ImageView)View_.findViewById(R.id.tsimg);
		
		my_login_imagebox = (RelativeLayout) View_.findViewById(R.id.my_login_imagebox);
		my_top= (RelativeLayout) View_.findViewById(R.id.my_top);
		my_xiaoxi=(RelativeLayout) View_.findViewById(R.id.my_xiaoxi);
		/*LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) my_top.getLayoutParams(); // 取控件mGrid当前的布局参数
		linearParams.height = Constant.displayWidth/207*100;// 当控件的高强制设成75象素
		my_top.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件mGrid2
*/		
		my_wd_num= (TextView) View_.findViewById(R.id.my_wd_num);
		my_sc_num= (TextView) View_.findViewById(R.id.my_sc_num);
		my_ht_num= (TextView) View_.findViewById(R.id.my_ht_num);
		my_xf_num= (TextView) View_.findViewById(R.id.my_xf_num);
		
		my_xx=(LinearLayout)View_.findViewById(R.id.my_xx);
		my_wd=(LinearLayout)View_.findViewById(R.id.my_wd);
		my_sc=(LinearLayout)View_.findViewById(R.id.my_sc);
		my_ht=(LinearLayout)View_.findViewById(R.id.my_ht);
		my_xf=(LinearLayout)View_.findViewById(R.id.my_xf);
		
		
		my_kcjl_item=(LinearLayout)View_.findViewById(R.id.my_kcjl_item);
		my_wdxa_item=(LinearLayout)View_.findViewById(R.id.my_wdxa_item);
		my_grzy_item=(LinearLayout)View_.findViewById(R.id.my_grzy_item);
		my_hygm_item=(LinearLayout) View_.findViewById(R.id.my_hygm_item);
		my_setUp_item=(LinearLayout) View_.findViewById(R.id.my_setUp_item);
		//my_jobPlace_item=(LinearLayout) View_.findViewById(R.id.my_jobPlace_item);

		rootView=(mnoScrollView)View_.findViewById(R.id.mnoScrollView);
		my_login_image= (RoundImageView) View_.findViewById(R.id.my_login_image);
		my_qd= (TextView) View_.findViewById(R.id.my_qd);
		
		my_but_login.setOnClickListener(new MyListener());  
		my_xx.setOnClickListener(new MyListener());  
		my_login_imagebox.setOnClickListener(new MyListener()); 
		my_xiaoxi.setOnClickListener(new MyListener());
		
		my_qd.setOnClickListener(new MyListener());  
		my_wd.setOnClickListener(new MyListener());  
		my_sc.setOnClickListener(new MyListener());  
		my_ht.setOnClickListener(new MyListener());  
		my_xf.setOnClickListener(new MyListener());  
		my_dz.setOnClickListener(new MyListener()); 
		my_xq.setOnClickListener(new MyListener()); 
		
		my_kcjl_item.setOnClickListener(new MyListener());  
		my_wdxa_item.setOnClickListener(new MyListener());  
		my_grzy_item.setOnClickListener(new MyListener());  
		my_setUp_item.setOnClickListener(new MyListener());	
		my_hygm_item.setOnClickListener(new MyListener());
		//my_jobPlace_item.setOnClickListener(new MyListener());
	}
	 private class MyListener implements OnClickListener{  		  
	        @Override  
	        public void onClick(View v) {  
	            // TODO Auto-generated method stub  
	        	//
	        	if(v.getId()==R.id.my_but_login)
	        	{
	        		Intent intent0 = new Intent(MyFragment.this.getActivity(), mylogin.class); 
				    
					startActivity(intent0);
	        	}
	        	//1.签到
	        	else if(v.getId()==R.id.my_qd){
	        		qd();
	        	}	        	
	        	//2.点击了头像框登录
	        	else if(v.getId()==R.id.my_login_imagebox){
	        		if(!appuser.getInstance().IsLogin()){
		     			 appuser.getInstance().Login(MyFragment.this.getActivity());
		     			 return;
		   	    	 }		        
	        		appuser.getInstance().LoginToAct(MyFragment.this.getActivity(),  my_xx.class);	        		
	        	}
	        	//3.消息提示
	        	else if(v.getId()==R.id.my_xiaoxi){
	        		appuser.getInstance().LoginToAct(MyFragment.this.getActivity(),  My_xiaoxi_item.class);
	        	}
	        	//4.地址
	        	else if(v.getId()==R.id.my_dz){
	        		Intent intent = new Intent(MyFragment.this.getActivity(), my_xx.class);   
	        		startActivity(intent);
	        	}
	        	//5.兴趣
	        	else if(v.getId()==R.id.my_xq){
	        		Intent intent = new Intent(MyFragment.this.getActivity(), my_xx.class);   
	        		startActivity(intent);
	        	}
	        	//问答
	        	else if(v.getId()==R.id.my_wd){
	        		appuser.getInstance().LoginToAct(MyFragment.this.getActivity(),  my_wd.class);	        		
	        	}
	        	//收藏
	        	else if(v.getId()==R.id.my_sc){
	        		appuser.getInstance().LoginToAct(MyFragment.this.getActivity(),  my_sc.class);
	        	}
	        	//评论
	        	else if(v.getId()==R.id.my_ht){
	        		appuser.getInstance().LoginToAct(MyFragment.this.getActivity(),  my_ht.class);
	        	}
	        	//学分
	        	else if(v.getId()==R.id.my_xf){
	        		appuser.getInstance().LoginToAct(MyFragment.this.getActivity(),  my_xf.class);	    	        
	        	}
	        	//1.课程记录
	        	else if(v.getId()==R.id.my_kcjl_item){
	        		appuser.getInstance().LoginToAct(MyFragment.this.getActivity(),  my_kcjl_item.class);	    	        
	        	}
	        	//2.我的下载
	        	else if(v.getId()==R.id.my_wdxa_item){
	        		appuser.getInstance().LoginToAct(MyFragment.this.getActivity(),  DownloadActivity.class);	        		
	        	}
	        	//3.个人主页
	        	else if(v.getId()==R.id.my_grzy_item){
	        		appuser.getInstance().LoginToAct(MyFragment.this.getActivity(),  my_grzy_item.class);	    	        
	        	}
	        	//4.会员购买
	        	else if(v.getId()==R.id.my_hygm_item){	        		
	        		appuser.getInstance().LoginToAct(MyFragment.this.getActivity(),  my_buy.class);
	        	}
	        	//5.设置
	        	else if(v.getId()==R.id.my_setUp_item){	        	
	        		Intent intent = new Intent(MyFragment.this.getActivity(), My_setup_item.class);   
	        		startActivity(intent);
	        	}	
	        	//5.人才广场
//	        	else if(v.getId()==R.id.my_jobPlace_item){	  
//	        		appuser.getInstance().LoginToAct(MyFragment.this.getActivity(),My_jobPlace_item.class);
//	        	}	
	        }
	  }
	 private void qd() {		 	 
		 webhandler handler_ = new webhandler(){		 
			@Override
			public void OnResponse(JSONObject response) {
				try {
					my_qd.setText("已签到          ");
					reget();						
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}				
		};
		handler_.SetProgressDialog(getActivity());
		Map<String, String> params = new HashMap<String, String>();
		handler_.SetRequest(new RequestType("",Type.signIn),params);
	 }  
		public static boolean isDownloadManagerAvailable(Context context) {
		    try {
		        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
		            return false;
		        }
		        Intent intent = new Intent(Intent.ACTION_MAIN);
		        intent.addCategory(Intent.CATEGORY_LAUNCHER);
		        intent.setClassName("com.android.providers.downloads.ui", "com.android.providers.downloads.ui.DownloadList");
		        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
		                PackageManager.MATCH_DEFAULT_ONLY);
		        return list.size() > 0;
		    } catch (Exception e) {
		        return false;
		    }
		}
		
	  @Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			MyFragment.this.getActivity().unregisterReceiver(myPushReceiver);
			MyFragment.this.getActivity().unregisterReceiver(myReceiver2);
			MyFragment.this.getActivity().unregisterReceiver(myReceiver);
		}
	  	
	public void  onStart(){
	     // TODO Auto-generated method stub
    	super.onStart();
    	update_state();
	}		
    //
	String isSignIn;
	private void update_state() {				
		// TODO Auto-generated method stub
		if( appuser.getInstance().IsLogin()){
			try{
			reget();
			my_login_ts.setVisibility(View.GONE);
			
			//my_but_login.setVisibility(View.GONE);
			my_xx.setVisibility(View.VISIBLE);
			String path=appuser.getInstance().getUserinfo().bigImg.toString();
			
			String name=appuser.getInstance().getUserinfo().nickname.toString();
			String signText=appuser.getInstance().getUserinfo().signText.toString();
			String sex=appuser.getInstance().getUserinfo().sex.toString();
			String city=appuser.getInstance().getUserinfo().city.toString();
			String provice=appuser.getInstance().getUserinfo().provice.toString();
			if(!city.equals("null")&&city.length()>0){
			if(provice.trim().toLowerCase().equals("null"))
				provice="";
				my_dz.setText(provice +"   "+city);
			}
			else
				my_dz.setText("点击添加地址");
			
			if(name.length()>0)
				my_name.setText(name);
			else
				my_name.setText("没昵称");
			
			if(signText.length()>0)
				my_xq.setText(signText);
			else
				my_xq.setText("点击编辑签名");
			if(sex.equals("男"))				
				my_sex.setImageDrawable(getResources().getDrawable(R.drawable.my_sex_man));
			else
				my_sex.setImageDrawable(getResources().getDrawable(R.drawable.my_sex_woman));			
				my_wd_num.setText(appuser.getInstance().getUserinfo().quesTotal.toString());
				my_sc_num.setText(appuser.getInstance().getUserinfo().collTotal.toString());
				my_ht_num.setText(appuser.getInstance().getUserinfo().replyTotal.toString());
				my_xf_num.setText(appuser.getInstance().getUserinfo().pointTotal.toString());
				my_login_image.setVisibility(View.VISIBLE);
				my_login_image.setImageUrl(RequestType.getWebUrl_(path),webpost.getImageLoader());			
				my_qd.setVisibility(View.VISIBLE);
    	 }catch (Exception e) {
				// TODO Auto-generated catch block
				
			}
    	}else{
			Drawable drawable=MyFragment.this.getResources().getDrawable(R.drawable.m_logo);
			//TimeButton.this.setBackgroundDrawable(drawable);
			my_login_image.setVisibility(View.INVISIBLE);
			//my_but_login.setVisibility(View.VISIBLE);
			my_xx.setVisibility(View.GONE);
			my_login_ts.setVisibility(View.VISIBLE);
			my_qd.setVisibility(View.GONE);
			my_wd_num.setText("0");
			my_sc_num.setText("0");
			my_ht_num.setText("0");
			my_xf_num.setText("0");
    	}
	}
	/*
	 * 登录成功后获取用户的基本信息
	 */
	private void reget() {
		// TODO Auto-generated method stub
		 webhandler handler_ = new webhandler(){

				@Override
				public void OnResponse(JSONObject response) {
					// TODO Auto-generated method stub
					// TODO Auto-generated method stub
					try {
						isSignIn=response.getJSONObject("data").getString("isSignIn");
						if(isSignIn.equals("1")){
							my_qd.setText("已签到          ");
						}else{
							//if(appuser.getInstance().getUserinfo().signinPoint==null){
								my_qd.setText("签到+20学分");
//							}else{
//								my_qd.setText("签到+"+appuser.getInstance().getUserinfo().signinPoint+"学分");
//							}
						}
						appuser.getInstance().getUserinfo().pointTotal=response.getJSONObject("data").getString("pointTotal");
						appuser.getInstance().getUserinfo().collTotal=response.getJSONObject("data").getString("collTotal");
						appuser.getInstance().getUserinfo().quesTotal=response.getJSONObject("data").getString("quesTotal");
						appuser.getInstance().getUserinfo().replyTotal=response.getJSONObject("data").getString("replyTotal");
						appuser.getInstance().getUserinfo().invCode=response.getJSONObject("data").getString("invCode");
						//MessageBox.Show(getActivity(), ""+appuser.getInstance().getUserinfo().invCode);
						my_wd_num.setText(appuser.getInstance().getUserinfo().quesTotal.toString());
						my_sc_num.setText(appuser.getInstance().getUserinfo().collTotal.toString());
						my_ht_num.setText(appuser.getInstance().getUserinfo().replyTotal.toString());
						my_xf_num.setText(appuser.getInstance().getUserinfo().pointTotal.toString());
						Constant.memBeginTime=response.getJSONObject("data").getString("memBeginTime");
						Constant.memEndTime=response.getJSONObject("data").getString("memEndTime");
						Constant.leftDay=response.getJSONObject("data").getString("leftDay");
						
						Constant.indentify=response.getJSONObject("data").getString("indentify");
						if(Constant.indentify.equals("会员"))							
							my_vip.setImageDrawable(getResources().getDrawable(R.drawable.my_vip));
						else
							my_vip.setImageDrawable(getResources().getDrawable(R.drawable.novip));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
					
				}
				
				};
				//handler_.SetProgressDialog(getActivity());
				Map<String, String> params = new HashMap<String, String>();
				
				params.put("uid",   Constant.uid);
				String str = "ruili"+ Constant.uid;
				// 在这里使用的是encode方式，返回的是byte类型加密数据，可使用new String转为String类型
				String strBase64 = new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
				Log.i("Test", "encode >>>" + strBase64);

				// 这里 encodeToString 则直接将返回String类型的加密数据
				String enToStr = Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
				params.put("accessCode",  enToStr);
				handler_.SetRequest(new RequestType("",Type.getUserBaseInt),params);
	}

	//头部的信息
	private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			 //String name = intent.getExtras().getString("name");  
			 
			 rootView.smoothScrollTo(0, 0);
			 update_state();
		}

	};
	//个人头像下的四个东东
	private BroadcastReceiver myReceiver2 = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
//			 String name = intent.getExtras().getString("name");  
			 try{
			    my_wd_num.setText(appuser.getInstance().getUserinfo().quesTotal.toString());
				my_sc_num.setText(appuser.getInstance().getUserinfo().collTotal.toString());
				my_ht_num.setText(appuser.getInstance().getUserinfo().replyTotal.toString());
				my_xf_num.setText(appuser.getInstance().getUserinfo().pointTotal.toString());
			 }catch (Exception e) {
				// TODO Auto-generated catch block
				
			}
//			 if(Integer.parseInt(name)>0)
//			 {
//				 tsimg.setVisibility(View.VISIBLE);
//			 }
//			 else tsimg.setVisibility(View.GONE);
			//tsimg
		}

	};
	
}


