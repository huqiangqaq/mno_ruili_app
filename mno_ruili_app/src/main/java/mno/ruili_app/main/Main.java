package mno.ruili_app.main;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;
import mno.ruili_app.BclFragmentPagerAdapter;
import mno.ruili_app.PassMgr;
import mno.ruili_app.R;
import mno.ruili_app.R.color;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.RequestType.Type;
import mno_ruili_app.net.loginhandler;
import mno_ruili_app.net.webhandler;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class Main extends FragmentActivity {
	ViewPager ViewPager_;
	TextView but_index_home, but_index_neighbor, but_index_my,but_index_place;
	static Drawable pic_1_up, pic_1_down, pic_2_up, pic_2_down, 
	pic_3_up,pic_3_down, pic_4_up,pic_4_down;
	BclFragmentPagerAdapter PagerAdapter_;
	private static boolean isExit = false;
	private loginhandler handler_;
	public static boolean isForeground = false;
	TextView tsimg;
	webhandler handler2;
	Map<String, String> params;
	String sign,url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
		JPushInterface.init(this); // 初始化 JPush
		setContentView(R.layout.activity_main);
		//setGuideResId(R.drawable.my_tishi_bj);
		MobclickAgent.updateOnlineConfig(this);
		mno.ruili_app.myapplication.ActivityContrl.add(this);
		IntentFilter filter2 = new IntentFilter();
		filter2.addAction("mno.push");
		registerReceiver(myPushReceiver, filter2);

		init();
		registerMessageReceiver();
		//获得服务器的版本号和链接
		webhandler handler_ = new webhandler() {
			public void OnResponse(JSONObject response) {
				try {
					url = response.getJSONObject("data").getString("url");
					sign = response.getJSONObject("data").getString("sign");
					//用sp进行控制提示更新的次数
					SharedPreferences sp=getSharedPreferences("promptNum", Context.MODE_PRIVATE);
					
					int number2=sp.getInt("number", 0);
					String psign = sp.getString("sign", "");
					if(number2 == 0){
						Editor editor=sp.edit();
						editor.putInt("number", 1);
						editor.putString("sign", sign);
						editor.commit();
					}else{
						Editor editor=sp.edit();
						editor.putInt("number", ++number2);
						editor.putString("sign", sign);
						editor.commit();
					}
					if(number2<3){
						promptUpdate();
					}else if(!psign.isEmpty() && !psign.equals(sign)){
						number2 = 1;
						Editor edit = sp.edit();
						edit.putInt("number", number2);
						edit.commit();
						if(psign!=sign&&number2<3){
							promptUpdate();
						}
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		handler_.SetProgressDialog(Main.this);
		Map<String, String> params = new HashMap<String, String>();
		params.put("plaform", "1");
		handler_.SetRequest(new RequestType("4", Type.getNewSign), params);

	}
	/**
     * 添加引导图片
     */
//	public  int guideResourceId=0;//引导页图片资源id
//	private static  SharedPreferences sp; 
//    public void addGuideImage() {
//        View view = getWindow().getDecorView().findViewById(R.id.my_content_view);//查找通过setContentView上的根布局
//        if(view==null)return;
//        if(activityIsGuided(this, this.getClass().getName())){
//            //引导过了
//            return;
//        }
//        ViewParent viewParent = view.getParent();
//        if(viewParent instanceof FrameLayout){
//            final FrameLayout frameLayout = (FrameLayout)viewParent;
//            if(guideResourceId!=0){//设置了引导图片
//                final ImageView guideImage = new ImageView(this);
//                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
//                guideImage.setLayoutParams(params);
//                guideImage.setScaleType(ScaleType.FIT_XY);
//                guideImage.setImageResource(guideResourceId);
//                guideImage.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        frameLayout.removeView(guideImage);
//                        setIsGuided(getApplicationContext(), this.getClass().getName());//设为已引导
//                    }
//                });
//                frameLayout.addView(guideImage);//添加引导图片
//                
//            }
//        }
//    }
//    
//    /**子类在onCreate中调用，设置引导图片的资源id
//     *并在布局xml的根元素上设置android:id="@id/my_content_view"
//     * @param resId
//     */
//    protected  void setGuideResId(int resId){ 
//        this.guideResourceId=resId;
//    }
//    /*
//     * 偏好设置
//     */
//   //偏好文件名
//    public static final String SHAREDPREFERENCES_NAME = "my_pref_guide";
//    //引导界面KEY
//    private static final String KEY_GUIDE_ACTIVITY = "guide_activity";
//    
//    /**
//     * 判断activity是否引导过
//     * 
//     * @param context
//     * @return  是否已经引导过 true引导过了 false未引导
//     */
//    public  boolean activityIsGuided(Context context,String className){
//    	sp=context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_WORLD_READABLE);
//    	int number=sp.getInt("number", 0);
//    	if(number==1){
//    		return true;
//    	}
//		return false;
//    }
//    
//    /**设置该activity被引导过了。 将类名已  |a|b|c这种形式保存为value，因为偏好中只能保存键值对
//     * @param context
//     * @param className
//     */
//    public static void setIsGuided(Context context,String className){
//    	sp=context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_WORLD_READABLE);
//    	Editor et=sp.edit();
//    	et.putInt("number", 1);
//    	et.commit();
//    }              
	// 提示更新
	private void promptUpdate() {
		try {
			if (!(sign).equals(getVersionName())) {

				AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
				builder.setMessage("有新版本更新，是否要更新？");
				builder.setTitle("提示");
				builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int which) {
						dialog.dismiss();
						String downurl = url;
						DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downurl));
						request.setDescription("正在下载最新安装包");
						request.setTitle("大师兄");
						// in order for this if to run, you
						// must use the android 3.2 to
						// compile your app
						// 当手机当前的安卓系统大于3.0（HONEYCOMB--蜂巢）时才会执行以下操作
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
							request.allowScanningByMediaScanner();
							request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
						}
						request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"大师兄.apk");
						DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
						long id = manager.enqueue(request);// 将请求放入下载请求队列，并返回标志id
						PassMgr.Save(id);
					}
				});
				builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int which) {
						dialog.dismiss();
					}
				});
				builder.create().show();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	/*
	 * 得到当前的版本号
	 */
	private String getVersionName() throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
		String version = packInfo.versionName;
		return version;
	}

	private void init() {

		ViewPager_ = (ViewPager) findViewById(R.id.indexpager);
		PagerAdapter_ = new BclFragmentPagerAdapter(this.getSupportFragmentManager());
		PagerAdapter_.put(new HomeFragment());
		PagerAdapter_.put(new JobPlaceFragment());
		PagerAdapter_.put(new NeighborFragment());
		PagerAdapter_.put(new MyFragment());		
		ViewPager_.setAdapter(PagerAdapter_);
		ViewPager_.setOffscreenPageLimit(2);
		//获取红点数
		tsimg = (TextView) this.findViewById(R.id.tsimg);
		handler2=new webhandler(){
			@Override
			public void OnResponse(JSONObject response) {
				// TODO Auto-generated method stub
				super.OnResponse(response);
				try {
					JSONObject data = response.getJSONObject("data");
					String total=data.getString("total");
					int i=Integer.parseInt(total);
					if(i==0){
						tsimg.setVisibility(View.INVISIBLE);
					}else{
						tsimg.setVisibility(View.VISIBLE);
						if(i<=99){
							tsimg.setText(i+"");
						}else{
							tsimg.setText(99+"+");
						}

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};

		params = new HashMap<String, String>();
		handler2.SetRequest(new RequestType("3",Type.getRedPoint),params);
		
		but_index_home = (TextView) this.findViewById(R.id.but_index_home);
		but_index_neighbor = (TextView) this.findViewById(R.id.but_index_neighbor);
		but_index_my = (TextView) this.findViewById(R.id.but_index_my);
		but_index_place= (TextView) this.findViewById(R.id.but_index_place);
		
		but_index_place.setOnClickListener(new MyListener());
		but_index_home.setOnClickListener(new MyListener());
		but_index_neighbor.setOnClickListener(new MyListener());
		but_index_my.setOnClickListener(new MyListener());		

		pic_1_up = getResources().getDrawable(R.drawable.home_up);
		pic_2_up = getResources().getDrawable(R.drawable.neighbor_up);
		pic_3_up = getResources().getDrawable(R.drawable.my_up1);
		pic_4_up = getResources().getDrawable(R.drawable.rcgc_up);

		pic_1_down = getResources().getDrawable(R.drawable.home_down);
		pic_2_down = getResources().getDrawable(R.drawable.neighbor_down);
		pic_3_down = getResources().getDrawable(R.drawable.my_down);
		pic_4_down = getResources().getDrawable(R.drawable.rcgc_down);
	}

	private class MyListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			/*
			 * if(!appuser.getInstance().IsLogin()) {
			 * appuser.getInstance().Login(Main.this); return; }
			 */
			but_index_home.setCompoundDrawablesWithIntrinsicBounds(null,
					pic_1_up, null, null);
			but_index_home.setTextColor(getResources().getColor(color.white_f9));
			
			but_index_place.setCompoundDrawablesWithIntrinsicBounds(null,
					pic_4_up, null, null);
			but_index_place.setTextColor(getResources().getColor(color.white_f9));
			
			but_index_neighbor.setCompoundDrawablesWithIntrinsicBounds(null,
					pic_2_up, null, null);
			but_index_neighbor.setTextColor(getResources().getColor(color.white_f9));
			
			but_index_my.setCompoundDrawablesWithIntrinsicBounds(null,
					pic_3_up, null, null);
			but_index_my.setTextColor(getResources().getColor(color.white_f9));

			if (v.getId() == R.id.but_index_home) {
				but_index_home.setTextColor(android.graphics.Color
						.parseColor("#ff6200"));//#387ebc
				but_index_home.setCompoundDrawablesWithIntrinsicBounds(null,
						pic_1_down, null, null);

				ViewPager_.setCurrentItem(0, false);
			}
			//人才广场
			else if (v.getId() == R.id.but_index_place) {
				but_index_place.setTextColor(android.graphics.Color
						.parseColor("#ff6200"));
				but_index_place.setCompoundDrawablesWithIntrinsicBounds(
						null, pic_4_down, null, null);
				ViewPager_.setCurrentItem(1, false);
			} 
			else if (v.getId() == R.id.but_index_neighbor) {
				but_index_neighbor.setTextColor(android.graphics.Color
						.parseColor("#ff6200"));
				but_index_neighbor.setCompoundDrawablesWithIntrinsicBounds(
						null, pic_2_down, null, null);
				ViewPager_.setCurrentItem(2, false);
			} else if (v.getId() == R.id.but_index_my) {
				
//				Intent intent = new Intent();
//				intent.setAction("mno.tz");
//				intent.putExtra("name", "");
//				Main.this.sendBroadcast(intent);
				
				but_index_my.setTextColor(android.graphics.Color
						.parseColor("#ff6200"));
				but_index_my.setCompoundDrawablesWithIntrinsicBounds(null,
						pic_3_down, null, null);
				ViewPager_.setCurrentItem(3, false);
				//setGuideResId(R.drawable.my_tishi_bj);
                //addGuideImage();
			} else {
			}
		}
	}
	//我的那里的消息红点
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
							tsimg.setVisibility(View.INVISIBLE);
						}else{
							tsimg.setVisibility(View.VISIBLE);
							if(i<=99){
								tsimg.setText(i+"");
							}else{
								tsimg.setText(99+"+");
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
//	private BroadcastReceiver myReceiver = new BroadcastReceiver() {
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			String name = intent.getExtras().getString("name");
//
//			if (Integer.parseInt(name) > 0) {
//				tsimg.setVisibility(View.VISIBLE);
//			} else{
//				tsimg.setVisibility(View.GONE);
//			}
//		}
//
//	};
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};

	private void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			// 利用handler延迟发送更改状态信息
			mHandler.sendEmptyMessageDelayed(0, 1500);
		} else {
			Intent intent = new Intent(Main.this, CountService.class);
			stopService(intent);
			finish();
		}
	}

	// for receive customer msg from jpush server(从极光推送服务器获得自定义消息)
	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";

	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}
	//自定义MessageReceiver
	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
				String messge = intent.getStringExtra(KEY_MESSAGE);
				String extras = intent.getStringExtra(KEY_EXTRAS);
				StringBuilder showMsg = new StringBuilder();
				showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
				if (!ExampleUtil.isEmpty(extras)) {
					showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
				}
			}
		}
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
    //
	@Override
	protected void onResume() {
		isForeground = true;
		super.onResume();
		MobclickAgent.onResume(this);
		params = new HashMap<String, String>();
		handler2.SetRequest(new RequestType("3",Type.getRedPoint),params);
	}

	@Override
	protected void onPause() {
		// isForeground = false;
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mMessageReceiver);
		//unregisterReceiver(myReceiver);
		unregisterReceiver(myPushReceiver);
		JPushInterface.clearAllNotifications(getApplicationContext());
		System.exit(0);
		super.onDestroy();
	}

}
