package mno.ruili_app.main;


import cn.jpush.android.api.InstrumentedActivity;
import mno.down.util.NetworkUtils;
import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.ct.MessageBox;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
//启动页
public class LauncherActivity  extends InstrumentedActivity {
	
	private RelativeLayout mLaunchLayout;
	private Animation mFadeIn;
	private Animation mFadeInScale;
	private Animation mFadeOut;
	private static  SharedPreferences sp; 
	static String pass_;
	static String user_name_;
	static boolean check_;
	static boolean  Auto_;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Constant.displayWidth = displayMetrics.widthPixels;
        Constant.displayHeight = displayMetrics.heightPixels;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.launcher);
		mLaunchLayout = (RelativeLayout) findViewById(R.id.launch);
		//JPushInterface.init(getApplicationContext());
		//webpost.init(this);
		boolean mfirst=isFirstEnter(LauncherActivity.this, LauncherActivity.this.getClass().getName());
		if(mfirst){
			mhandler.sendEmptyMessageDelayed(SWITCH_GUIDACTIVITY,1000);
		}else{
			mhandler.sendEmptyMessageDelayed(SWITCH_MAINACTIVITY,1000);
		}
		init();
		setListener();
		
	}
	/*
	 * 判断应用是否初次进入应用，读取SharedPreferences中的number字段
	 */
	private boolean isFirstEnter(Context context,String className){
		if(context==null||className==null||"".equalsIgnoreCase(className)){
			return false;
		}
		sp=context.getSharedPreferences("ifGuide", Context.MODE_WORLD_READABLE);
		String mResultStr =sp.getString("number", "");
		if(mResultStr.equals("1")){
			return false;
		}else{
			return true;
		}
	}
	/*
	 * 利用handler跳转至不同界面@wusir
	 */
	private final static int SWITCH_MAINACTIVITY = 1000;
    private final static int SWITCH_GUIDACTIVITY = 1001;
    public Handler mhandler=new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		switch (msg.what) {
			case SWITCH_MAINACTIVITY:
				Intent mIntent = new Intent();
                mIntent.setClass(LauncherActivity.this, Main.class);                               
                LauncherActivity.this.startActivity(mIntent);
                LauncherActivity.this.finish();
				break;
			case SWITCH_GUIDACTIVITY:
                mIntent = new Intent();
                mIntent.setClass(LauncherActivity.this, LeadActivity.class);
                LauncherActivity.this.startActivity(mIntent);
                LauncherActivity.this.finish();
                break;	
			default:
				break;
			}
    		super.handleMessage(msg);
    	};
    };
    
	private void setListener() {
		
		/**
		 * 动画切换原理:开始时是用第一个渐现动画,当第一个动画结束时开始第二个放大动画,当第二个动画结束时调用第三个渐隐动画,
		 * 第三个动画结束时修改显示的内容并且重新调用第一个动画,从而达到循环效果
		 */
		mFadeIn.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				mLaunchLayout.startAnimation(mFadeInScale);
			}
		});
		mFadeInScale.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {
				
			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				mLaunchLayout.startAnimation(mFadeOut);
			       //PassMgr.init(LauncherActivity.this);
			
			/*		loginhandler h = new loginhandler(){

					
					@Override
					public void OnLoginSuccess(JSONObject response) {
						// TODO Auto-generated method stub
						///Toast.makeText(context_, "欢迎回来。。", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						intent.setClass(LauncherActivity.this, Main.class);
						LauncherActivity.this.startActivity(intent);
						finish();
					}

				};
				 if(PassMgr.IsAuto() == true&&PassMgr.IsSave())
				 {
				
					h.Login(LauncherActivity.this,PassMgr.getName(),PassMgr.getPass());
				 }else
				 {Intent intent = new Intent();
					intent.setClass(LauncherActivity.this, mylogin.class);
					startActivity(intent);
					finish();}
			}*/}
		});
		
		mFadeOut.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {
				 
				int i=NetworkUtils.getConnectivityStatus(LauncherActivity.this);
				if(i==0)
				{
					MessageBox.Show(getApplicationContext(), "网络连接失败，请检查网络连接");
					
					finish();
					
				}
				else if(i==2)
				{
					MessageBox.Show(getApplicationContext(), "您正在使用手机流量\n可能会产生流量费用");
				Intent intent = new Intent();
				intent.setClass(LauncherActivity.this, Main.class);
				LauncherActivity.this.startActivity(intent);
				finish();
				}
				else
				{
					Intent intent = new Intent();
					intent.setClass(LauncherActivity.this, Main.class);
					LauncherActivity.this.startActivity(intent);
					finish();
				}
			
			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				
			}
		});
		

	}

	private void init() {
		initAnim();
	
		mLaunchLayout.startAnimation(mFadeIn);
	}

	private void initAnim() {
		mFadeIn = AnimationUtils.loadAnimation(LauncherActivity.this,R.anim.welcome_fade_in);
		mFadeIn.setDuration(1000);
		mFadeInScale = AnimationUtils.loadAnimation(LauncherActivity.this,R.anim.welcome_fade_in_scale);
		mFadeInScale.setDuration(1000);
		mFadeOut = AnimationUtils.loadAnimation(LauncherActivity.this,R.anim.welcome_fade_out);
		mFadeOut.setDuration(1000);
	}

}
