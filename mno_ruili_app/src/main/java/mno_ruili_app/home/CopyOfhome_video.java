package mno_ruili_app.home;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ab.activity.AbActivity;
import com.ab.image.AbImageLoader;
import com.ab.util.AbDialogUtil;
import com.activeandroid.query.Select;
import com.gensee.common.ServiceType;
import com.gensee.entity.InitParam;
import com.gensee.net.RtComp;
import com.gensee.room.RtSimpleImpl;
import com.gensee.routine.State;
import com.gensee.routine.UserInfo;
import com.gensee.routine.IRTEvent.IRoomEvent.JoinResult;
import com.gensee.routine.IRTEvent.IRoomEvent.LeaveReason;
import com.gensee.view.GSVideoView;
import com.gensee.view.GSVideoView.RenderMode;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import mno.down.modal.DownloadDetail;
import mno.down.modal.DownloadRecord;
import mno.down.modal.DownloadRecord.STATUS;
import mno.down.service.DownloadService;
import mno.down.ui.DownloadActivity;
import mno.down.util.DownloadHelper;
import mno.down.util.NetworkUtils;
import mno.ruili_app.BclFragmentPagerAdapter;
import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.R.color;
import mno.ruili_app.ct.HorizontalListView;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.ct.mnoScrollView;
import mno.ruili_app.ct.validator;
import mno.ruili_app.main.HomeFragment;
import mno.ruili_app.main.MyFragment;
import mno.ruili_app.main.NeighborFragment;
import mno.ruili_app.my.my_buy;
import mno.ruili_app.my.my_sc;
import mno.ruili_app.my.myfindcheckcode;
import mno.ruili_app.my.mylogin;
import mno_ruili_app.adapter.ImageListAdapter;
import mno_ruili_app.adapter.ImageListAdapter_pl;
import mno_ruili_app.adapter.hd;
import mno_ruili_app.adapter.mySimpleAdapter;
import mno_ruili_app.adapter.tv;
import mno_ruili_app.nei.nei_find;
import mno_ruili_app.nei.nei_pl;
import mno_ruili_app.nei.nei_wdxq;
import mno_ruili_app.nei.nei_zx;
import mno_ruili_app.nei.nei_zxxq;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class CopyOfhome_video extends AbActivity implements  com.gensee.net.RtComp.Callback,MediaPlayer.OnCompletionListener,
MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, OnBufferingUpdateListener,
OnClickListener {
	RelativeLayout layout_view,layout_view2;
	WebView mWebFlash;
	//ViewPager indexpager;
	BclFragmentPagerAdapter PagerAdapter_;
	public static String url="",oldurl="";
	webhandler handler_,handler_2,handler_3; 
	String id;
	//int start=0;
	ImageView coverImg,view_xz;
	private AbImageLoader mAbImageLoader = null;
	private boolean fullscreen = false;//全屏/窗口播放切换标志
	List<DownloadDetail> list;
    TextView view_down,select_tg_text,select_tg_bz,select_sj_text,select_sj_bz,select_yy_text,select_yy_bz,view_title,view_likeTotal;
    private  DownloadHelper mDownloadHelper;
	private DownloadService.DownloadServiceBinder mBinder;
	DownloadDetail detail;
	
	ImageListAdapter mylistViewAdapter ,mylistViewAdapter2;
	ImageListAdapter_pl mylistViewAdapter3;
	ArrayList<hd>  mplList = new ArrayList<hd>();
	ArrayList<tv>   mTvList = new ArrayList<tv>();
	ArrayList<tv>   mTvList2 = new ArrayList<tv>();
	ListView mListView;
	TextView xq_js;
	ListView mListView_ml,mListView_pl;
	TextView ml_text,ml_text_tz;
	ImageView view_sc,view_zk;
	mnoScrollView rootView;
	LinearLayout viedio_xq,viedio_ml,viedio_pl;
	String islike="0",likeTotal,isCollect="0";
	int lastvalue;
	private UMSocialService mController = UMServiceFactory.getUMSocialService(Constant.DESCRIPTOR);
	int right=1;
	/**
     * seekBar是否自动拖动
     */
    private boolean seekBarAutoFlag = false;
    boolean threadDisable=false;
    /**
    *
    */
   private SurfaceView surfaceView;

   /**
    * surfaceView播放控制
    */
   private SurfaceHolder surfaceHolder;

   /**
    * 播放控制条
    */
   private SeekBar seekBar;

 
   /**
    * 改变视频大小button
    */
   private ImageView videoSizeButton;

   /**
    * 加载进度显示条
    */
   private ProgressBar progressBar;

   /**
    * 播放视频
    */
   private MediaPlayer mediaPlayer;
   /**
    * 视频时间显示
    */
   private TextView vedioTiemTextView,speed,error;

   /**
    * 播放总时间
    */
   private String videoTimeString;

   private long videoTimeLong;

   /**
    * 播放路径
    */
   private String pathString;

   /**
    * 屏幕的宽度和高度
    */
   private int screenWidth, screenHeight;
   private boolean clean=false;
   private boolean finish=false;
   View view;
	@Override
public void onConfigurationChanged(Configuration newConfig) {
	// TODO Auto-generated method stub
		if(newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
		  {
		   //TODO 某些操作 
		  }
	super.onConfigurationChanged(newConfig);
}
	private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			 String name = intent.getExtras().getString("name");  
			 if(name.equals("开始下载"))
				 digshow("已经开始下载,\n可前往我的下载查看");
			 else if(name.equals("正在下载"))
				 digshow("任务已存在,\n可前往我的下载查看");
			
		}

	};
	private void digshow(String text) {
		// TODO Auto-generated method stub
		View mView = null;
    	mView = mInflater.inflate(R.layout.dialog_myconfig,null);
		AbDialogUtil.showDialog(mView,R.animator.fragment_top_enter,R.animator.fragment_top_exit,R.animator.fragment_pop_top_enter,R.animator.fragment_pop_top_exit);
		Button leftBtn1 = (Button)mView.findViewById(R.id.left_btn);
		Button rightBtn1 = (Button)mView.findViewById(R.id.right_btn);
		TextView title_choices = (TextView)mView.findViewById(R.id.title_choices);
		TextView choice_one_text= (TextView)mView.findViewById(R.id.choice_one_text);
		leftBtn1.setText("前往我的下载");
		rightBtn1.setText("留在当前页面");
		//title_choices.setText("");
		choice_one_text.setText(text);
		leftBtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = null;
				intent = new Intent(CopyOfhome_video.this,DownloadActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
				startActivity(intent);		
				//unregisterReceiver(myReceiver);
				AbDialogUtil.removeDialog(CopyOfhome_video.this);
			}
			
		});
		
		rightBtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(CopyOfhome_video.this);
			}
			
		});
	}
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_view);
		view=new View(this);
		Intent intent0 =this.getIntent();
		try
		{
		id=intent0.getStringExtra("ID").toString();
		}catch(Exception e)
		{
			
		}
		id=Constant.itemid;
		IntentFilter filter = new IntentFilter();
		filter.addAction("mno.xz");
		filter.setPriority(Integer.MAX_VALUE);
		registerReceiver(myReceiver, filter);
		
		init();
		initzb();
		initlist();
		InitHandler();
		layout_view= (RelativeLayout)this.findViewById(R.id.layout_view);
		layout_view2= (RelativeLayout)this.findViewById(R.id.layout_view2);
		LayoutParams lp;       
        lp=layout_view.getLayoutParams();
      
        lp.height=Constant.displayWidth/16*9;        
        layout_view.setLayoutParams(lp); // 使设置好的布局参数应用到控件myGrid   
        
        select_tg_text=(TextView)this.findViewById(R.id.select_tg_text);
		select_tg_bz=(TextView)this.findViewById(R.id.select_tg_bz);
		select_sj_text=(TextView)this.findViewById(R.id.select_sj_text);
		select_sj_bz=(TextView)this.findViewById(R.id.select_sj_bz);
		select_yy_text=(TextView)this.findViewById(R.id.select_yy_text);
		select_yy_bz=(TextView)this.findViewById(R.id.select_yy_bz);
		 new Thread(new Runnable() {
	            public void run() {
	                while (!threadDisable) {
	                    try {
	                        Thread.sleep(1000);
	                    } catch (InterruptedException e) {

	                    }
	                   
	                  Message message = new Message( );
	              	  message.what = 2;
	              	  handler.sendMessage(message);
	                }
	            }
	        }).start();
	}
	
	EditText nei_bj_edi;
	LinearLayout pl_box,nei_fb_box,main_ctm;
	Drawable  pic,pic2,picsc,picsc2,picxz;
	private void initlist() {
		// TODO Auto-generated method stub
		 imm = (InputMethodManager) getSystemService(CopyOfhome_video.INPUT_METHOD_SERVICE); 
		 pl_box=(LinearLayout)this.findViewById(R.id.pl_box);
		 nei_bj_edi=(EditText)this.findViewById(R.id.nei_bj_edi);
		 nei_fb_box=(LinearLayout)this.findViewById(R.id.nei_fb_box);
		 pl_box.setVisibility(view.GONE);
		 pic=CopyOfhome_video.this. getResources().getDrawable( R.drawable.view_zan); 
		 pic2=CopyOfhome_video.this. getResources().getDrawable( R.drawable.view_nozan); 
		 picsc=CopyOfhome_video.this. getResources().getDrawable( R.drawable.zb_sc_true); 
		 picsc2=CopyOfhome_video.this. getResources().getDrawable( R.drawable.zb_sc_false); 
		 //picxz=home_video.this. getResources().getDrawable( R.drawable.z); 
		 viedio_xq=(LinearLayout)this.findViewById(R.id.viedio_xq);
		 viedio_ml=(LinearLayout)this.findViewById(R.id.viedio_ml);
		 viedio_pl=(LinearLayout)this.findViewById(R.id.viedio_pl);
		 rootView=(mnoScrollView)this.findViewById(R.id.mnoScrollView);
		 view_sc=(ImageView)this.findViewById(R.id.view_sc);
		 view_zk=(ImageView)this.findViewById(R.id.view_zk);
		 xq_js=(TextView) this.findViewById(R.id.xq_js); 
		  
		 mListView = (ListView)this.findViewById(R.id.mListView);
		 ml_text=(TextView) this.findViewById(R.id.ml_text); 
		 ml_text_tz=(TextView) this.findViewById(R.id.ml_text_ts); 
		 mListView_ml = (ListView)this.findViewById(R.id.mListView_ml);
		 mListView_pl= (ListView)this.findViewById(R.id.mListView_pl);
		 
		 mylistViewAdapter = new ImageListAdapter(this, mTvList,
					R.layout.item_list, new String[] { "itemsIcon" },
					new int[] { R.id.itemsIcon,R.id.itemstext,R.id.itemstext2});
		 mListView.setAdapter(mylistViewAdapter);
		 
		 mylistViewAdapter2 = new ImageListAdapter(this, mTvList2,
					R.layout.item_list2, new String[] { "itemsIcon" },
					new int[] { R.id.itemsIcon,R.id.itemstext,R.id.itemstext2});
		 mListView_ml.setAdapter(mylistViewAdapter2);
		 
		 mylistViewAdapter3  = new ImageListAdapter_pl(this, mplList,
					R.layout.item_pl, new String[] { "smallImg"},
					new int[] { R.id.pl_image,R.id.pl_name ,R.id.pl_time,R.id.pl_con},"creply");
			
			
		 mListView_pl.setAdapter(mylistViewAdapter3);
		 
		
			
		 mListView_pl.setFocusable(false);
		 mListView.setFocusable(false);
		 mListView_ml.setFocusable(false);
		 mListView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					Intent intent = new Intent(CopyOfhome_video.this,CopyOfhome_video.class);
					Constant.itemid=mTvList.get(position).getid();
					intent.putExtra("ID",mTvList.get(position).getid());  
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					//intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
					CopyOfhome_video.this.startActivity(intent);
					finish();
					overridePendingTransition(R.anim.welcome_fade_in_scale, R.anim.welcome_fade_out); 
					/*
					 if(!appuser.getInstance().IsLogin())
			    	 {
						 Map<String, String> params = new HashMap<String, String>();
						 params.put("cid",mTvList.get(position).getid());
					     params.put("accessCode",  Constant.accessCode);
						 handler_.SetRequest(new RequestType("2",Type.getCourseDetail),params);
			    	 }
					 else
					 {
						 Map<String, String> params = new HashMap<String, String>();
						
					      params.put("cid",mTvList.get(position).getid());
						  params.put("uid",  Constant.uid);
						  params.put("accessCode",  Constant.acc);
						  handler_.SetRequest(new RequestType("2",Type.getCourseDetail),params);
					 }*/
				}
	    		
	    	});
		  mListView_ml.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					/*Constant.itemzt="";
					Constant.playPosition=-1;
					vedioTiemTextView.setText("");
					seekBar.setProgress(0);*/
					Intent intent = new Intent(CopyOfhome_video.this,CopyOfhome_video.class);
					Constant.itemid=mTvList2.get(position).getid();
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					//intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
					CopyOfhome_video.this.startActivity(intent);
					finish();
					overridePendingTransition(R.anim.welcome_fade_in_scale, R.anim.welcome_fade_out); 
					/* if(!appuser.getInstance().IsLogin())
			    	 {
						 Map<String, String> params = new HashMap<String, String>();
						 params.put("cid",mTvList2.get(position).getid());
					     params.put("accessCode",  Constant.accessCode);
						 handler_.SetRequest(new RequestType("2",Type.getCourseDetail),params);
			    	 }
					 else
					 {
						 Map<String, String> params = new HashMap<String, String>();
						
					      params.put("cid",mTvList2.get(position).getid());
						  params.put("uid",  Constant.uid);
						  params.put("accessCode",  Constant.acc);
						  handler_.SetRequest(new RequestType("2",Type.getCourseDetail),params);
					 }*/
				}
	    		
	    	});
	       
	}
	
	
	boolean can=true;
	List<DownloadRecord> goingList;
	public void onclick(View view) {
		imm.hideSoftInputFromWindow(nei_bj_edi.getWindowToken(), 0);
		 if(view.getId()==R.id.select_sj)
    	{
			 viedio_xq.setVisibility(view.VISIBLE);
			 viedio_ml.setVisibility(view.GONE);
			 viedio_pl.setVisibility(view.GONE);
			 back();
			
			 select_sj_text.setTextColor(android.graphics.Color.parseColor("#387ebc"));
			 select_sj_bz.setBackgroundColor(android.graphics.Color.parseColor("#387ebc"));
			 //indexpager.setCurrentItem(0, false);
    	}
    	else if(view.getId()==R.id.select_tg)
    	{
    		 viedio_ml.setVisibility(view.VISIBLE);
			 viedio_xq.setVisibility(view.GONE);
			 viedio_pl.setVisibility(view.GONE);
    		 back();
    		 select_tg_text.setTextColor(android.graphics.Color.parseColor("#387ebc"));
    		 select_tg_bz.setBackgroundColor(android.graphics.Color.parseColor("#387ebc"));
    		// indexpager.setCurrentItem(1, false);
    	}
    	else if(view.getId()==R.id.select_yy)
    	{
    		 viedio_pl.setVisibility(view.VISIBLE);
			 viedio_ml.setVisibility(view.GONE);
			 viedio_xq.setVisibility(view.GONE);
    		 back();
    		 pl_box.setVisibility(view.VISIBLE);
    		
    		 select_yy_text.setTextColor(android.graphics.Color.parseColor("#387ebc"));
    		 select_yy_bz.setBackgroundColor(android.graphics.Color.parseColor("#387ebc"));
    		// indexpager.setCurrentItem(2, false);
    	}
    	else if(view.getId()==R.id.main_ctm)
    	{
    		 imm.hideSoftInputFromWindow(nei_bj_edi.getWindowToken(), 0);
    	}
    	else if(view.getId()==R.id.view_zk)
    	{
    		Drawable pic_1_up,pic_1_down;
    		pic_1_up= getResources().getDrawable( R.drawable.view_zk); 
    		pic_1_down= getResources().getDrawable( R.drawable.view_sq); 
    		String what=view_xqnr;
			//view_xz.setClickable(false);
    		if(can)
    		{
    			xq_js.setText(what);
    			view_zk.setImageDrawable(pic_1_down);
        	
        		can=false;
    		}else{
    			if(view_xqnr.length()>10)
    			xq_js.setText(what.substring(0, 9)+"...");
    			view_zk.setImageDrawable(pic_1_up);
        		can=true;
    		}
    		
    		
    	}
    	else if(view.getId()==R.id.view_xz)
    	{
    		if(!appuser.getInstance().IsLogin())
	    	 {
  			 appuser.getInstance().Login(this);
  			 MessageBox.Show(this, "该功能需要登录以后才可以使用\n请登录");
  			 return;
	    	 }
    		if(label.equals("2"))
			{
			if(!Constant.indentify.equals("会员"))
	    	 {
			 digshow3();
  			 return;
	    	 }
			}
    		 goingList=DownloadRecord.getAllFailures();
    		 int size=goingList.size();
    	  		
    	  		//DownloadManager.getInstance().StatMissions();
    	  		 for(int i=0;i<size;i++)
    	  		 {
    	  			 Object mission =goingList.get(i);
    	  			 if(mission instanceof DownloadRecord){
    	  				 DownloadRecord record = (DownloadRecord)mission;
    	  				 if(detail.VideoId==record.DownloadId)
    	  				 {
    	  					 digshow("任务已存在,\n可前往我的下载查看");
    	  					 return;
    	  				 }
    	  		 }
    	  		 
    	  		 }
    		/*
   		
    		if(view_down.getText().toString().equals("已下载"))
    			digshow("任务已存在,\n可前往我的下载查看");
    		else*/
    		//view_xz.setClickable(false);
    	  	mDownloadHelper = new DownloadHelper(this);
			mDownloadHelper.startDownload(detail);
    		
    	}
		 
    	else if(view.getId()==R.id.view_likeTotal)
    	{
    		 if(!appuser.getInstance().IsLogin())
	    	 {
    			 appuser.getInstance().Login(this);
    			 MessageBox.Show(this, "该功能需要登录以后才可以使用\n请登录");
	    	 }
			 else
			 {
				 Map<String, String> params = new HashMap<String, String>();
    			 params.put("forwhat","like");
    		     params.put("likeId",id);
    		     params.put("type","course");
    			 handler_2.SetRequest(new RequestType("2",Type.addLike),params);
    			 if(islike.equals("1"))
    			 {
    				
    				    view_likeTotal.setTextColor(android.graphics.Color.parseColor("#939393"));
    				    view_likeTotal.setText(Integer.parseInt(view_likeTotal.getText().toString())-1+"");
 						view_likeTotal.setCompoundDrawablesWithIntrinsicBounds(pic2,null, null,null);
 						islike="0";
    			 }
    			 else
    			 {
    				 view_likeTotal.setText(Integer.parseInt(view_likeTotal.getText().toString())+1+"");
    				 view_likeTotal.setTextColor(android.graphics.Color.parseColor("#f39801"));
					 view_likeTotal.setCompoundDrawablesWithIntrinsicBounds(pic,null, null,null);
					/* int i=Integer.parseInt(likeTotal)+1;
					 view_likeTotal.setText(""+i);*/
					 islike="1";
    			 }
			 }
    		
    		
    		/*like*/
    		
    		
    	}
    	else if(view.getId()==R.id.view_fx)
    	{
    		showDialog(CopyOfhome_video.this);
    		
    	}
    	else if(view.getId()==R.id.view_sc)
    	{
    		if(!appuser.getInstance().IsLogin())
	    	 {
   			 appuser.getInstance().Login(this);
   			 MessageBox.Show(this, "该功能需要登录以后才可以使用\n请登录");
	    	 }
    		else
    		{
    		
    			 Map<String, String> params = new HashMap<String, String>();
				  params.put("likeId",id);
				  params.put("forwhat","coll");
				  params.put("type","course");
				  handler_2.SetRequest(new RequestType("2",Type.addLike),params);
				  if(label.equals("3"))
						view_sc.setImageDrawable(CopyOfhome_video.this. getResources().getDrawable( R.drawable.zb_tx));
					else
					view_sc.setImageDrawable(picsc);
				 
    		}
    		/*like*/
    		
    	}
		 
		 
    	else if(view.getId()==R.id.view_back)
    	{
    		layout_view2.setBackgroundColor(Color.BLACK);
			finish();
    	}
    	
    	else if(view.getId()==R.id.nei_pl)
			{
				if(!appuser.getInstance().IsLogin())
		   	 {
					 appuser.getInstance().LoginToAct(CopyOfhome_video.this,  CopyOfhome_video.class);
		   		 return;
		   	 }
				 pl_box.setVisibility(View.GONE);;
				 
				 nei_fb_box.setVisibility(View.VISIBLE);;
				 nei_bj_edi.setFocusable(true);   
				 nei_bj_edi.setFocusableInTouchMode(true);   
				
				 
				 nei_bj_edi.requestFocus();  
				 imm.showSoftInput(nei_bj_edi, 0);
			}
			else if(view.getId()==R.id.nei_fb)
			{
				  Map<String, String> params = new HashMap<String, String>();
				  params.put("focusId",id);
				  params.put("type","course");
				  params.put("content",nei_bj_edi.getText().toString());
			      handler_2.SetRequest(new RequestType("3",Type.addReply),params);
			      
			      nei_bj_edi.clearFocus();
			      nei_bj_edi.setText("");
			      
			     imm.hideSoftInputFromWindow(nei_bj_edi.getWindowToken(), 0);
			     
			     pl_box.setVisibility(View.VISIBLE);;
				 nei_fb_box.setVisibility(View.GONE);;
				
			}

	 }
	InputMethodManager imm;
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK) {
	        	
	        	/*lastvalue = seekbar.getProgress() * mediaPlayer.getDuration()  //计算进度条需要前进的位置数据大小
						/ seekbar.getMax();*/
	          if(fullscreen)
	          {
	        	  getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        	  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        	  /*validator.setScreenBrightness(this,right);*/
		        	LayoutParams lp;       
		            lp=layout_view.getLayoutParams();
		            //lp.width=100;
		            lp.height=Constant.displayWidth/16*9;        
		            layout_view.setLayoutParams(lp); 
		            backButton.setVisibility(view.VISIBLE);
		            fullscreen = false;//改变全屏/窗口的标记
	          }
	        
	          else
	          {
	        	  layout_view2.setBackgroundColor(Color.BLACK);
	        	  finish();
	          }
	        	 
	            return false;
	        }
	        return super.onKeyDown(keyCode, event);
	    }
	 
	//设置屏幕亮度的函数
	 private void setScreenBrightness(float num){
	 WindowManager.LayoutParams layoutParams=super.getWindow().getAttributes();
	 layoutParams.screenBrightness=num;//设置屏幕的亮度
	 super.getWindow().setAttributes(layoutParams);
	 }
	private void back() {
		// TODO Auto-generated method stub
		 pl_box.setVisibility(view.GONE);
		 nei_fb_box.setVisibility(View.GONE);
		 rootView.smoothScrollTo(0, 0);
		 select_tg_text.setTextColor(android.graphics.Color.parseColor("#919191"));
		 select_sj_text.setTextColor(android.graphics.Color.parseColor("#919191"));
		 select_yy_text.setTextColor(android.graphics.Color.parseColor("#919191"));
		 select_sj_bz.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
		 select_tg_bz.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
		 select_yy_bz.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
		
	}

	int vWidth,vHeight;
	LinearLayout playcontrl,top2;
	ImageView backButton,playButton;
	boolean display=true;
	
	private void init() {
		// TODO Auto-generated method stub
		mAbImageLoader = AbImageLoader.newInstance(CopyOfhome_video.this);
        mAbImageLoader.setMaxWidth(0);
        mAbImageLoader.setMaxHeight(0);
		    mWebFlash = (WebView) findViewById(R.id.newsContent);
		    coverImg= (ImageView) findViewById(R.id.coverImg); 
		    view_xz= (ImageView) findViewById(R.id.view_xz); 
	        view_down= (TextView) findViewById(R.id.view_down); 
	        view_title= (TextView) findViewById(R.id.view_title); 
			view_likeTotal= (TextView) findViewById(R.id.view_likeTotal); 
		    playcontrl=(LinearLayout)findViewById(R.id.playcontrl);
		    top2=(LinearLayout)findViewById(R.id.top2);
		    surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
		    progressBar = (ProgressBar) findViewById(R.id.progressBar);
		    backButton = (ImageView) findViewById(R.id.view_back);
		    seekBar = (SeekBar) findViewById(R.id.seekbar);
		    seekBar.setEnabled(false);
		    playButton= (ImageView) findViewById(R.id.button_play); 
		    vedioTiemTextView = (TextView) findViewById(R.id.textView_showTime);
	        speed= (TextView) findViewById(R.id.speed);
	        error= (TextView) findViewById(R.id.error);
	        videoSizeButton = (ImageView) findViewById(R.id.button_videoSize);
	        // 设置surfaceHolder
	        surfaceHolder = surfaceView.getHolder();
	        // 设置Holder类型,该类型表示surfaceView自己不管理缓存区,虽然提示过时，但最好还是要设置
	        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	        // 设置surface回调
	        surfaceHolder.addCallback(new SurfaceCallback());
	        error.setOnClickListener(CopyOfhome_video.this);
	        // 暂停和播放
	        playButton.setOnClickListener(CopyOfhome_video.this);
	        surfaceView.setOnClickListener(CopyOfhome_video.this);
	        videoSizeButton.setOnClickListener(CopyOfhome_video.this);
	        vedioTiemTextView.setText("");
	}
	String view_xqnr;String view_xq,view_ml,view_pl,view_hotpl;String label="";
	private void InitHandler()
	{
		handler_3= new webhandler(){

  			@Override
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
				String ms=mess;
				ms=ms+"";
				MessageBox.Show(CopyOfhome_video.this,mess);
			}

	

			@Override
  			public void OnResponse(JSONObject response) {
  				// TODO Auto-generated method stub
  				// TODO Auto-generated method stub
				try {
					JSONObject maindata = response.getJSONObject("data");
					try {
						 
						 view_hotpl=maindata.getString("hotreply");
						 mplList.clear();
						 JSONArray  hotpl=  new JSONArray(view_hotpl.toString() );
							int length_pl=hotpl.length();
						    for(int i=0;i< length_pl; i++)
						    {

						    	 JSONObject type_json =hotpl.getJSONObject(i);
								    //listData.add(data.getJSONObject(i).getString("Name"));
								    String name =  type_json.getString("replyUname");
								    String replyUid=  type_json.getString("replyUid");
								    String content =  type_json.getString("content");
								    String id =  type_json.getString("id");
								    String time=type_json.getString("create_at");
								    String imgurl=type_json.getString("replyUbimg");
								    String isLike=type_json.getString("isLike");
								    String likeTotal=type_json.getString("likeTotal");
								    String ht="-1";
								    if(i==0)
								    {
								    	ht="0";
								    }
								   
								    final hd mzx = new hd(name,content, id,time,imgurl,isLike,likeTotal,ht,replyUid);
									mplList.add(mzx);
						   
						    }
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							mplList.clear();
						}	
						try {
							
							 view_pl=maindata.getString("reply");
							  JSONArray  pl=  new JSONArray(view_pl.toString() );
								int length_hot=pl.length();
							    for(int i=0;i<length_hot; i++)
							    {
										   JSONObject type_json =pl.getJSONObject(i);
										    //listData.add(data.getJSONObject(i).getString("Name"));
										    String name =  type_json.getString("replyUname");
										    String content =  type_json.getString("content");
										    String id =  type_json.getString("id");
										    String time=type_json.getString("create_at");
										    String imgurl=type_json.getString("replyUbimg");
										    String isLike=type_json.getString("isLike");
										    String likeTotal=type_json.getString("likeTotal");
										    String replyUid=  type_json.getString("replyUid");
										    String ht="-1";

										    if(i==0)
										    {
										    	ht="1";
										    }
										    final hd mzx = new hd(name,content, id,time,imgurl,isLike,likeTotal,ht,replyUid);
										    
											mplList.add(mzx);
											mylistViewAdapter3.notifyDataSetChanged();
							   
							    }
							 
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								//mplList.clear();
								mylistViewAdapter3.notifyDataSetChanged();
							}	
			
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
  			}
  			
  			};
  			
		  handler_2 = new webhandler(){

  			@Override
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
				String ms=mess;
				ms=ms+"";
				MessageBox.Show(CopyOfhome_video.this,mess);
			}

	

			@Override
  			public void OnResponse(JSONObject response) {
  				// TODO Auto-generated method stub
  				// TODO Auto-generated method stub
				try {
					
					String mess = response.getString("message");
					if(mess.equals("记录成功"))
					{
						finish();
					}
					if(mess.equals("点赞成功")){
					
					}
					if(mess.equals("评论成功"))
					{
						 Map<String, String> paramss = new HashMap<String, String>();
							
					      paramss.put("cid",id);
						  paramss.put("uid",  Constant.uid);
						  paramss.put("accessCode",  Constant.acc);
						  handler_3.SetRequest(new RequestType("2",Type.getCourseDetail),paramss);
					}
					if(mess.equals("null") == false && mess.length() > 1)
					{
						Toast.makeText(CopyOfhome_video.this, mess, Toast.LENGTH_SHORT).show();
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
  			}
  			
  			};
  		boolean showdown = false;
		handler_ = new webhandler(){

			@Override
			public void OnResponse(JSONObject response) {
				// TODO Auto-generated method stub
				
				
				try {
					
					JSONObject maindata = response.getJSONObject("data");
					String  data = maindata.getString("base");
					
					JSONObject jsonObject;
					jsonObject = new JSONObject(data);
					data=""+data;
					id=jsonObject.getString("id");
					url=jsonObject.getString("url");
					if(oldurl.length()<=0)
						oldurl=url;
				    label=jsonObject.getString("label");
				    String liveManuUrl=jsonObject.getString("liveManuUrl");
				    String clock=jsonObject.getString("clock");
				    if(!liveManuUrl.equals("null"))
				    {
				    	label="1";
				    	url=liveManuUrl;
				    	view_xz.setVisibility(view.GONE);
				    }
				   
				    		
				   
				    ml_text.setText(jsonObject.getString("catalogName"));
				    String imageurl=RequestType.getWebUrl_(jsonObject.getString("coverImg"));
					mAbImageLoader.display(coverImg,imageurl);
					layout_view2.setBackgroundColor(Color.TRANSPARENT);
				    if(url.contains("iframe"))
				    {
				    	mWebFlash.setVisibility(view.VISIBLE);
				    	loadFlash();
						playcontrl.setVisibility(View.GONE);
						surfaceView.setVisibility(View.GONE);
						view_xz.setVisibility(view.GONE);
						coverImg.setVisibility(View.GONE);
				    }
				    else
				    {
				    	surfaceView.setVisibility(View.GONE);
				    	playcontrl.setVisibility(View.VISIBLE);
				    	coverImg.setVisibility(View.VISIBLE);
				    	mWebFlash.setVisibility(view.GONE);
				    }
					if(label.equals("3"))
					{
						
						String a=jsonObject.getJSONObject("url").getString("guestUrl");
						if(a.contains("live-"))
						{
						String b=a.substring(a.lastIndexOf("live-"));
						String c=b.substring(5);
						liveid=c;
						}
						JSONObject jsonObject2=jsonObject.getJSONObject("code");
						String d=jsonObject2.getString("user");
						pwd=d;
						seekBar.setVisibility(View.GONE);
						videoView.setVisibility(View.VISIBLE);
						view_xz.setVisibility(view.GONE);
						vedioTiemTextView.setVisibility(view.GONE);
						
						
					}
					else
					{
						
						view_xz.setVisibility(view.VISIBLE);
						videoView.setVisibility(View.GONE);
						view_xz.setVisibility(view.VISIBLE);
						seekBar.setVisibility(View.VISIBLE);
						
				
					}
					 if(label.equals("2"))
						{
					    	view_xz.setVisibility(view.GONE);
					    	
						}
					 if(!liveManuUrl.equals("null"))
					    {
					    	view_xz.setVisibility(view.GONE);
					    }
					 if(!clock.equals("0"))
					    {
					    	
					    }
					String what=jsonObject.getString("desc");
					 view_xqnr=what;
					if(view_xqnr.length()>10)
						xq_js.setText(view_xqnr.substring(0,9)+"...");
						
						//xq_js.setText(view_xqnr.substring(0,9)+"...");
					else
						xq_js.setText(view_xqnr);
				    view_title.setText(jsonObject.getString("title"));
				    likeTotal=jsonObject.getString("likeTotal");
					view_likeTotal.setText(jsonObject.getString("likeTotal"));
					isCollect = jsonObject.getString("isCollect");
					islike = jsonObject.getString("isLike");
					//MessageBox.Show(home_video.this , isCollect);
					if(islike.equals("1")){
						view_likeTotal.setTextColor(android.graphics.Color.parseColor("#f39801"));
						view_likeTotal.setCompoundDrawablesWithIntrinsicBounds(pic,null, null,null);
					}
					else
					{
						    view_likeTotal.setTextColor(android.graphics.Color.parseColor("#939393"));
	    				   
	 						view_likeTotal.setCompoundDrawablesWithIntrinsicBounds(pic2,null, null,null);
	 						islike="0";
					}
					if(isCollect.equals("1")){
						if(label.equals("3"))
							view_sc.setImageDrawable(CopyOfhome_video.this. getResources().getDrawable( R.drawable.zb_tx));
						else
						view_sc.setImageDrawable(picsc);
					}
					else
					{
						if(label.equals("3"))
							view_sc.setImageDrawable(CopyOfhome_video.this. getResources().getDrawable( R.drawable.zb_tx2));
						else
						view_sc.setImageDrawable(picsc2);
					}
					top2.setVisibility(view.VISIBLE);
					
					Constant.SOCIAL_CONTENT="我在睿立培训看到《"+jsonObject.getString("title")+ "》，推荐你也来看看！";
					Constant.SOCIAL_TITLE="睿立培训";
					Constant.SOCIAL_IMAGE=RequestType.getWebUrl_(jsonObject.getString("coverImg"));
					String url2="http://www.yuetingfengsong.com:8087/ShowPage/Share?";
					url2=url2+"title="+jsonObject.getString("title")+"&desc="+"&url="+RequestType.getWebUrl_(jsonObject.getString("coverImg"));
					Constant.SOCIAL_LINK=url2;
					//((HorizontalListView) convertView.findViewById
					HorizontalListView tag=((HorizontalListView) CopyOfhome_video.this.findViewById(R.id.wd_tag) );
					
					  mySimpleAdapter saImageItems;
			          final ArrayList<tv>   mchoseList = new ArrayList<tv>();
			          String[] pic;
			          pic  = jsonObject.getString("tag").split(",");
					
			          for (int i = 0; i <pic.length; i++) { 
			              
			              final tv mtv = new tv(pic[i], "0","");
			              mchoseList.add(mtv);
			            } 
			          saImageItems = new mySimpleAdapter(CopyOfhome_video.this, //没什么解释  
			          		mchoseList,//数据来源   
			                    R.layout.item_tag_wd,//night_item的XML实现  
			                      
			                    //动态数组与ImageItem对应的子项          
			                    new String[] {"textItem"},   
			                      
			                    //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
			                    new int[] {R.id.but_tag});  
			          tag.setAdapter(saImageItems);
			          //tag.setText(mzx.getTag().replaceAll(",", "   "));
			          tag.setOnItemClickListener(new OnItemClickListener(){

							@Override
							public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
								if(mchoseList.get(position).getName().toString().trim().length()<=0)
									return;
								MessageBox.Show(CopyOfhome_video.this, mchoseList.get(position).getName());
								Intent intent0 = new Intent(CopyOfhome_video.this, nei_find.class); 
				        		Constant.viedio_what=mchoseList.get(position).getName();
				        		intent0.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
								intent0.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
				        		Constant.fromwd="1";
				        		Constant.viedio_label=label;
				        		Constant.viedio_find="movie";
				        		CopyOfhome_video.this.startActivity(intent0);
				     
							}
							});
					
/*					*/
					    list = new ArrayList<DownloadDetail>();
				        list.clear();
				        
				       // url=" http://www.yuetingfengsong.com:8087/media/files/test.mp4";

						list.add(new DownloadDetail(Integer.parseInt(id), url,
								imageurl,
								jsonObject.getString("title"),jsonObject.getString("title"),view_xqnr,"mp4"));
						
						detail=new DownloadDetail();
						detail =list.get(0); 
				    /*    
				        DownloadRecord record = new Select().from(DownloadRecord.class)
				                .where("DownloadId = ?",detail.VideoId)
				                .executeSingle();
				       		 if(record != null){
				       			 if(record.Status == STATUS.SUCCESS){
				       				view_down.setText(R.string.download_downloaded);
				       			 }else if(record.Status == STATUS.CANCELED){
				       				view_down.setText(R.string.download_downloadcancel);
				       			 }else if(record.Status == STATUS.PAUSE){
				       				view_down.setText(R.string.download_resume);
				       			 }
				       		 }
				       		view_down.setVisibility(view.INVISIBLE);*/
/*				       		 */
					
					try {
						
						 view_xq=maindata.getString("hotCourse");
						 mTvList.clear();

							JSONArray  xq=  new JSONArray(view_xq.toString() );
							int length=xq.length();
						    for(int i=0;i<= length-1; i++)
						    {
						    	JSONObject type_json;
								type_json = xq.getJSONObject(i);
								String name =  type_json.getString("title");
								String content =  type_json.getString("desc");
								String id =  type_json.getString("id");
								String imgurl=type_json.getString("coverImg");
								final tv mtv = new tv(id,name, content,imgurl);
								mTvList.add(mtv);
								mylistViewAdapter.notifyDataSetChanged();
								
						    //listData.add(data.getJSONObject(i).getString("Name"));
						   
						    }
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							
						}	
						try {
							
						 view_ml=maindata.getString("sameCourse");
						 mTvList2.clear();
						 JSONArray  ml=  new JSONArray(view_ml.toString() );
							int length2=ml.length();
							if(length2<=0)
						    	ml_text_tz.setVisibility(view.VISIBLE);
						    else
						    	ml_text_tz.setVisibility(view.GONE);
						    for(int i=0;i<= length2-1; i++)
						    {
						    	JSONObject type_json;
								type_json = ml.getJSONObject(i);
								String name =  type_json.getString("title");
								String content =  type_json.getString("desc");
								String id =  type_json.getString("id");
								//String imgurl=type_json.getString("coverImg");
								final tv mtv = new tv(id,name, content,"");
								mTvList2.add(mtv);
								mylistViewAdapter2.notifyDataSetChanged();
								
						    //listData.add(data.getJSONObject(i).getString("Name"));
						   
						    }
						    
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							ml_text_tz.setVisibility(view.VISIBLE);
							mTvList2.clear();
							mylistViewAdapter2.notifyDataSetChanged();
						}	
					
						try {
							
						 view_hotpl=maindata.getString("hotreply");
						 mplList.clear();
						 JSONArray  hotpl=  new JSONArray(view_hotpl.toString() );
							int length_pl=hotpl.length();
						    for(int i=0;i< length_pl; i++)
						    {

						    	 JSONObject type_json =hotpl.getJSONObject(i);
								    //listData.add(data.getJSONObject(i).getString("Name"));
								    String name =  type_json.getString("replyUname");
								    String replyUid=  type_json.getString("replyUid");
								    String content =  type_json.getString("content");
								    String id =  type_json.getString("id");
								    String time=type_json.getString("create_at");
								    String imgurl=type_json.getString("replyUbimg");
								    String isLike=type_json.getString("isLike");
								    String likeTotal=type_json.getString("likeTotal");
								    String ht="-1";
								    if(i==0)
								    {
								    	ht="0";
								    }
								   
								    final hd mzx = new hd(name,content, id,time,imgurl,isLike,likeTotal,ht,replyUid);
									mplList.add(mzx);
						   
						    }
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							mplList.clear();
						}	
						try {
							
							 view_pl=maindata.getString("reply");
							  JSONArray  pl=  new JSONArray(view_pl.toString() );
								int length_hot=pl.length();
							    for(int i=0;i<length_hot; i++)
							    {
										   JSONObject type_json =pl.getJSONObject(i);
										    //listData.add(data.getJSONObject(i).getString("Name"));
										    String name =  type_json.getString("replyUname");
										    String content =  type_json.getString("content");
										    String id =  type_json.getString("id");
										    String time=type_json.getString("create_at");
										    String imgurl=type_json.getString("replyUbimg");
										    String isLike=type_json.getString("isLike");
										    String likeTotal=type_json.getString("likeTotal");
										    String replyUid=  type_json.getString("replyUid");
										    String ht="-1";

										    if(i==0)
										    {
										    	ht="1";
										    }
										    final hd mzx = new hd(name,content, id,time,imgurl,isLike,likeTotal,ht,replyUid);
										    
											mplList.add(mzx);
											mylistViewAdapter3.notifyDataSetChanged();
							   
							    }
							 
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								//mplList.clear();
								mylistViewAdapter3.notifyDataSetChanged();
							}	
	
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
			}
			
			};
			rootView.smoothScrollTo(0, 0);
			handler_.SetProgressDialog(CopyOfhome_video.this);
			
	}

	

	private void digshow3() {
		// TODO Auto-generated method stub
		View mView = null;
    	mView = mInflater.inflate(R.layout.dialog_myconfig,null);
		AbDialogUtil.showDialog(mView,R.animator.fragment_top_enter,R.animator.fragment_top_exit,R.animator.fragment_pop_top_enter,R.animator.fragment_pop_top_exit);
		Button leftBtn1 = (Button)mView.findViewById(R.id.left_btn);
		Button rightBtn1 = (Button)mView.findViewById(R.id.right_btn);
		TextView title_choices = (TextView)mView.findViewById(R.id.title_choices);
		TextView choice_one_text= (TextView)mView.findViewById(R.id.choice_one_text);
		leftBtn1.setText("确定");
		rightBtn1.setText("取消");
		//title_choices.setText("");
		choice_one_text.setText("您还不是会员\n是否前往购买会员");
		leftBtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent0 = new Intent(CopyOfhome_video.this, my_buy.class); 
			    
				startActivity(intent0);
				AbDialogUtil.removeDialog(CopyOfhome_video.this);
			}
			
		});
		
		rightBtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(CopyOfhome_video.this);
			}
			
		});
	}
	private void digshow2() {
		// TODO Auto-generated method stub
		View mView = null;
    	mView = mInflater.inflate(R.layout.dialog_myconfig,null);
		AbDialogUtil.showDialog(mView,R.animator.fragment_top_enter,R.animator.fragment_top_exit,R.animator.fragment_pop_top_enter,R.animator.fragment_pop_top_exit);
		Button leftBtn1 = (Button)mView.findViewById(R.id.left_btn);
		Button rightBtn1 = (Button)mView.findViewById(R.id.right_btn);
		TextView title_choices = (TextView)mView.findViewById(R.id.title_choices);
		TextView choice_one_text= (TextView)mView.findViewById(R.id.choice_one_text);
		leftBtn1.setText("确定");
		rightBtn1.setText("取消");
		//title_choices.setText("");
		choice_one_text.setText("获取视频信息失败，请检查网络连接");
		leftBtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
	            startActivity(intent);
				finish();
				AbDialogUtil.removeDialog(CopyOfhome_video.this);
			}
			
		});
		
		rightBtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(CopyOfhome_video.this);
			}
			
		});
	}

		@Override
		protected void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			 id=Constant.itemid;
			 if(!appuser.getInstance().IsLogin())
	    	 {
				 Map<String, String> params = new HashMap<String, String>();
				 params.put("cid",id);
			     params.put("accessCode",  Constant.accessCode);
				 handler_.SetRequest(new RequestType("2",Type.getCourseDetail),params);
	    	 }
			 else
			 {
				 Map<String, String> params = new HashMap<String, String>();
				
			      params.put("cid",id);
				  params.put("uid",  Constant.uid);
				  params.put("accessCode",  Constant.acc);
				  handler_.SetRequest(new RequestType("2",Type.getCourseDetail),params);
			 }
			
			
		}
		public void cleanvideo(){
			try
			{
				 if(start==1)
				  {
					  leaveCast();
					  playButton.setBackgroundResource(R.drawable.view_zt);
				  }
			if (null != mediaPlayer && mediaPlayer.isPlaying()) {
				Constant.itemzt="";
				//Constant.playPosition=-1;
				
                // 如果正在播放，则停止。
		        seekBarAutoFlag = false;
               /* if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                if(id.equals(Constant.itemid))*/
                Constant.playPosition = mediaPlayer.getCurrentPosition();
               
                // 释放mediaPlayer
                this.mediaPlayer.release();
                this.mediaPlayer = null; 
				playButton.setBackgroundResource(R.drawable.view_zt);
			}
			}
			 catch (Exception e) {
				// TODO Auto-generated catch block
			
			}
               
		}
		public void remenber()
		{
			if(Constant.playPosition>0)
			{
				Intent intent = new Intent();  
                intent.setAction("mno.cd");  
                int position = Constant.playPosition ;
                intent.putExtra("name", position+"");  
                intent.putExtra("name2",position+"");  
                intent.putExtra("id", id);  
                CopyOfhome_video.this.sendBroadcast(intent);  
			}
		}
		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			
			//  mWebFlash.pauseTimers();
			  try {
				  //seekBar.setEnabled(false);
				  remenber();
				  cleanvideo();
				  mWebFlash.getClass().getMethod("onPause").invoke(mWebFlash,(Object[])null);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			super.onPause();
		}
		  @Override
		    protected void onResume() {
		       super.onResume();
		       //恢复播放
		       //mWebFlash.resumeTimers();
		       try {
		    	   if(!id.equals(Constant.itemid))
		    	   {
		    	   Constant.playPosition=-1;
		    	  
		    	   }
		    	   mWebFlash.getClass().getMethod("onResume").invoke(mWebFlash,(Object[])null);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    }
		     
	
		@Override
		protected void onDestroy() {   //activity销毁后，释放资源
			super.onDestroy();
			unregisterReceiver(myReceiver);
			Constant.itemzt="";
			this.threadDisable = true;
				 try {
			            if (null != CopyOfhome_video.this.mediaPlayer) {
			                // 提前标志为false,防止在视频停止时，线程仍在运行。
			                seekBarAutoFlag = false;
			                // 如果正在播放，则停止。
			                if (mediaPlayer.isPlaying()) {
			                    mediaPlayer.stop();
			                }
			                // 释放mediaPlayer
			                CopyOfhome_video.this.mediaPlayer.release();
			                CopyOfhome_video.this.mediaPlayer = null;
			                Constant.playPosition = -1;
			            }
			        } catch (Exception e) {
			            // TODO: handle exception
			            e.printStackTrace();
			        }
			Constant.playPosition = -1;
			mWebFlash.destroy();
			System.gc();
		}
		
/*分享*/
		private void showDialog(Context context) {
			
		    mController.getConfig().setSsoHandler(new SinaSsoHandler());
			addQQQZonePlatform();
			addWXPlatform();
			final AlertDialog dialog = new AlertDialog.Builder(context)
					.create();
			dialog.show();
			Window window = dialog.getWindow();
			// 设置布局
			window.setContentView(R.layout.showdialog);
			// 设置宽高
			window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			// 设置弹出的动画效果
			window.setWindowAnimations(R.style.AnimBottom);
			// 设置监听
			TextView sc = (TextView) window.findViewById(R.id.nei_sc);
			TextView cancel = (TextView) window.findViewById(R.id.nei_qx);
			ImageView login_qq= (ImageView) window.findViewById(R.id.login_qq);
			ImageView login_wx= (ImageView) window.findViewById(R.id.login_wx);
			ImageView login_wxpy= (ImageView) window.findViewById(R.id.login_wxpy);
			ImageView login_wb= (ImageView) window.findViewById(R.id.login_wb);
			RelativeLayout kb=(RelativeLayout)window.findViewById(R.id.kongbai);
			sc.setVisibility(view.GONE);
			login_qq.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 performShare(SHARE_MEDIA.QQ);
					 dialog.cancel();
				}

				
			});
			login_wx.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 performShare(SHARE_MEDIA.WEIXIN);
					 dialog.cancel();
				}

				
				
			});
			login_wxpy.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 performShare(SHARE_MEDIA.WEIXIN_CIRCLE);
					 dialog.cancel();
				}

				
				
			});
			login_wb.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 performShare(SHARE_MEDIA.SINA);
					 dialog.cancel();
				}

				
				
			});
			sc.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					/*  Map<String, String> params = new HashMap<String, String>();
					  params.put("likeId",id);
					  params.put("forwhat","coll");
					  params.put("type","course");
					  handler_.SetRequest(new RequestType("2",Type.addLike),params);
					  dialog.cancel();
			*/
				}
			});
		
			kb.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			
		
			cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.cancel();
					
				}
			});
			
		}


	public void performShare(SHARE_MEDIA platform) {
		mController.getConfig().closeToast();
		
	    mController.postShare(CopyOfhome_video.this, platform, new SnsPostListener() {

	    	
	    	 @Override
	         public void onStart() {
	            MessageBox.Show(CopyOfhome_video.this , "开始分享");
	         }

				@Override
				public void onComplete(SHARE_MEDIA arg0, int eCode,
						SocializeEntity arg2) {
					if (eCode == 200) {
						// MessageBox.Show(home_video.this , "分享成功");
	              } else {
	                   String eMsg = "";
	                   if (eCode == -101){
	                       eMsg = "没有授权";
	                   }
	                   //MessageBox.Show(home_video.this , "分享失败");
	              }
					
				}
	         
		});
	      /*  @Override
	        public void onStart() {

	        }

	        @Override
	        public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
	            String showText = platform.toString();
	            if (eCode == StatusCode.ST_CODE_SUCCESSED) {
	                showText += "平台分享成功";
	            } else {
	                showText += "平台分享失败";
	            }
	           Toast.makeText(home_video.this, showText, Toast.LENGTH_SHORT).show();
	           
	        }
	    });*/
	}
	/**
	 * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
	 *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
	 *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
	 *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
	 * @return
	 */
	private void addQQQZonePlatform() {
		String path="http://b264.photo.store.qq.com/psb?/V14av8GK1voYCO/BPkJ.Gv0rDTo0MGFlxDb7ze4vPxjxPztOkaZHqVgdYI!/b/dIBJXp0lFAAA&bo=*gFmAQAAAAABB7g!&rf=viewer_4";
		
	    String appId = "100424468";
	    String appKey = "c7394704798a158208a74ab60104f0ba";
	    // 添加QQ支持, 并且设置QQ分享内容的target url
	    UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(CopyOfhome_video.this,
	            appId, appKey);
	    
	    qqSsoHandler.setTargetUrl(Constant.SOCIAL_LINK);
	    qqSsoHandler.setTitle(Constant.SOCIAL_TITLE);
	    qqSsoHandler.setShareAfterAuthorize(false);
	    
	    qqSsoHandler.addToSocialSDK();

	    // 添加QZone平台
	    QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(CopyOfhome_video.this, appId, appKey);
	    qZoneSsoHandler.addToSocialSDK();
	    
		mController.setShareContent(Constant.SOCIAL_CONTENT);
		// 设置分享图片, 参数2为图片的url地址
		mController.setShareMedia(new UMImage(CopyOfhome_video.this,  Constant.SOCIAL_IMAGE));
	}
	/**
	 * @功能描述 : 添加微信平台分享
	 * @return
	 */
	private void addWXPlatform() {
	    // 注意：在微信授权的时候，必须传递appSecret
	    // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
	    String appId = "wxbf92dfd9d2e40679";
	    String appSecret = "998eaed5ff6f8710fa047ab83e33b6ab";
	    // 添加微信平台
	    UMWXHandler wxHandler = new UMWXHandler(CopyOfhome_video.this, appId, appSecret);
	    wxHandler.addToSocialSDK();

	    // 支持微信朋友圈
	    UMWXHandler wxCircleHandler = new UMWXHandler(CopyOfhome_video.this, appId, appSecret);
	    wxCircleHandler.setToCircle(true);
	    wxCircleHandler.addToSocialSDK();
	}
	
	public void loadFlash() {
	     mWebFlash = (WebView) findViewById(R.id.newsContent);
	     mWebFlash.setWebViewClient(new HelloWebViewClient ()); 
	     mWebFlash.loadUrl("http://www.tudou.com/programs/view/html5embed.action?type=2&code=DCemcsRnmuY&lcode=StDyVAcEEkA&resourceId=0_06_05_99");
	     mWebFlash.setWebChromeClient(new WebChromeClient()); 
	     WebSettings ws = mWebFlash.getSettings();
	     ws.setJavaScriptEnabled(true);
	     ws.setAllowFileAccess(true);
	     ws.setDatabaseEnabled(true);
	     ws.setDomStorageEnabled(true);
	     ws.setSaveFormData(false);
	     ws.setAppCacheEnabled(true);
	     ws.setCacheMode(WebSettings.LOAD_DEFAULT);
	     ws.setLoadWithOverviewMode(false);//<==== 一定要设置为false，不然有声音没图像
	     ws.setUseWideViewPort(true);
	 }
	  private class HelloWebViewClient extends WebViewClient
	    {
	    	@Override 
	    	public boolean shouldOverrideUrlLoading(WebView view, String url) 
	    	{  
	    		view.loadUrl(url); 
	    		return true;  
	    	}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				/*if(dialog_ != null)
		    	{
		    		dialog_.cancel(); 
		    	}*/
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				/*if(dialog_ != null)
		    	{
		    		dialog_.show();  
		    	}*/
			}  
	    	
	    	
	    }
	  /*录播*/
	// SurfaceView的callBack
	    private class SurfaceCallback implements SurfaceHolder.Callback {
	        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	        }

	        public void surfaceCreated(SurfaceHolder holder) {
	            // surfaceView被创建
	            // 设置播放资源
	            //playVideo();
	        	clean=true;
	        	//playVideo();
	            
	        }

	        public void surfaceDestroyed(SurfaceHolder holder) {
	            // surfaceView销毁,同时销毁mediaPlayer
	        		clean=false;
	        	 if (null != mediaPlayer) {
	        		 Constant.playPosition = mediaPlayer.getCurrentPosition();
	                 mediaPlayer.release();
	                 mediaPlayer = null;
	             }

	        }

	    }

	    /**
	     * 播放视频
	     */
	    public void playVideo() {
	        // 初始化MediaPlayer
	    	surfaceView.setVisibility(view.VISIBLE);
	        mediaPlayer = new MediaPlayer();
	        // 重置mediaPaly,建议在初始滑mediaplay立即调用。
	        mediaPlayer.reset();
	        // 设置声音效果
	        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
	        // 设置播放完成监听
	        mediaPlayer.setOnCompletionListener(this);
	        // 设置媒体加载完成以后回调函数。
	        mediaPlayer.setOnPreparedListener(this);
	        // 错误监听回调函数
	        mediaPlayer.setOnErrorListener(this);
	        // 设置缓存变化监听
	        mediaPlayer.setOnBufferingUpdateListener(this);
	        Uri uri = Uri.parse(url);
	        try {
	            //mediaPlayer.reset();
	            //mediaPlayer.setDataSource(pathString);
	            // mediaPlayer.setDataSource(this, uri);
	            mediaPlayer.setDataSource(CopyOfhome_video.this, uri);
	            // 设置异步加载视频，包括两种方式 prepare()同步，prepareAsync()异步
	            mediaPlayer.prepareAsync();
	        } catch (IOException e) {
	            e.printStackTrace();
	            Toast.makeText(this, "加载视频错误！", Toast.LENGTH_LONG).show();
	        }
	        
	    }

	    /**
	     * 视频加载完毕监听
	     * 
	     * @param mp
	     */
	    @Override
	    public void onPrepared(MediaPlayer mp) {
	        // 当视频加载完毕以后，隐藏加载进度条
	    	seekBar.setEnabled(true);
	        progressBar.setVisibility(View.GONE);
	        error.setVisibility(View.GONE);
	        coverImg.setVisibility(View.GONE);
	        //surfaceView.setVisibility(view.VISIBLE);
	        // 判断是否有保存的播放位置,防止屏幕旋转时，界面被重新构建，播放位置丢失。
	        if (Constant.playPosition >= 0) {
	            mediaPlayer.seekTo(Constant.playPosition);
	            Constant.playPosition = -1;
	            // surfaceHolder.unlockCanvasAndPost(Constants.getCanvas());
	        }
	        
	        // 设置控制条,放在加载完成以后设置，防止获取getDuration()错误
	        seekBar.setMax(mediaPlayer.getDuration());
	        // 设置播放时间
	        videoTimeLong = mediaPlayer.getDuration();
	        videoTimeString = getShowTime(videoTimeLong);
	        //vedioTiemTextView.setText("00:00/" + videoTimeString);
	        // 设置拖动监听事件
	        seekBar.setOnSeekBarChangeListener(new SeekBarChangeListener());
	        // 设置按钮监听事件
	       
	       
	        // 视频大小
	        videoSizeButton.setOnClickListener(CopyOfhome_video.this);
	        
	        
	        // 播放视频
	        mediaPlayer.start();
	       
	        seekBarAutoFlag = true;
	        // 设置显示到屏幕
	        mediaPlayer.setDisplay(surfaceHolder);
	        // 开启线程 刷新进度条
	        new Thread(runnable).start();
	        // 设置surfaceView保持在屏幕上
	        mediaPlayer.setScreenOnWhilePlaying(true);
	        surfaceHolder.setKeepScreenOn(true);
	      

	    }

	    /**
	     * 滑动条变化线程
	     */
	    int a=-1;
	    final Handler handler = new Handler( ) {
	    	  public void handleMessage(Message msg) {
	    	  switch (msg.what) {
	    	  case 1:
	    		  
	    	  break;
	    	  case 2:
	    		  int i=NetworkUtils.getConnectivityStatus(CopyOfhome_video.this);
	    		  
	    		 
	    			  //MessageBox.Show(getApplicationContext(), ""+i);
                  if(i==0)
                  {
                	  if(a==-1)
                      {
                	  a=0;
                	  //seekBar.setEnabled(false);
                	  digshow2();
                      }
                  }
                  else
                	  a=-1;
                  
	    		break;
	    	  }
	    	  super.handleMessage(msg);
	    	  }

	  	
	      };
	    private Runnable runnable = new Runnable() {

	        public void run() {
	            // TODO Auto-generated method stub
	            // 增加对异常的捕获，防止在判断mediaPlayer.isPlaying的时候，报IllegalStateException异常
	            try {
	                while (seekBarAutoFlag) {
	                    /*
	                     * mediaPlayer不为空且处于正在播放状态时，使进度条滚动。
	                     * 通过指定类名的方式判断mediaPlayer防止状态发生不一致
	                     */

	                    if (null != CopyOfhome_video.this.mediaPlayer
	                            && CopyOfhome_video.this.mediaPlayer.isPlaying()) {
	                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
	                        Constant.playPosition=mediaPlayer.getCurrentPosition();
	                    }
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    };

	    /**
	     * seekBar拖动监听类
	     * 
	     * @author shenxiaolei
	     */
	    @SuppressWarnings("unused")
	    private class SeekBarChangeListener implements OnSeekBarChangeListener {

	        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	            // TODO Auto-generated method stub
	            if (progress >= 0) {
	                // 如果是用户手动拖动控件，则设置视频跳转。
	            	if(a==0)
	            	{
	            		//seekBar.setProgress(Constant.playPosition);
	            		 vedioTiemTextView.setText(getShowTime(progress) + "/" + videoTimeString);
	            		return;
	            	}
	                if (fromUser) {
	                	 if (null != CopyOfhome_video.this.mediaPlayer
		                            && CopyOfhome_video.this.mediaPlayer.isPlaying()) {
	                	Constant.playPosition = progress;
	                    mediaPlayer.seekTo(progress);
	                	 }
	                }
	                // 设置当前播放时间
	                vedioTiemTextView.setText(getShowTime(progress) + "/" + videoTimeString);
	            }
	        }

	        public void onStartTrackingTouch(SeekBar seekBar) {
	            // TODO Auto-generated method stub

	        }

	        public void onStopTrackingTouch(SeekBar seekBar) {
	            // TODO Auto-generated method stub

	        }

	    }

	    /**
	     * 按钮点击事件监听
	     */
	    int start=0;
	    public void onClick(View v) {
	        // TODO Auto-generated method stub
	      
	        // 播放、暂停按钮
	        if (v == playButton) {
	        	
	        	coverImg.setVisibility(view.GONE);
	        	if(!appuser.getInstance().IsLogin())
		    	 {
	  			 appuser.getInstance().Login(CopyOfhome_video.this);
	  			 MessageBox.Show(CopyOfhome_video.this, "该功能需要登录以后才可以使用\n请登录");
	  			 return;
		    	 }
				if(label.equals("3"))
				{
				
				if(start==0)
				{
					progressBar.setVisibility(view.VISIBLE);
					start=1;
					playButton.setBackgroundResource(R.drawable.view_ks);
					goinroom();
				}
				else  {    
					leaveCast();
					playButton.setBackgroundResource(R.drawable.view_zt);
					start=0;
				}
					return;
				}
				
				if(label.equals("2"))
				{
				if(!Constant.indentify.equals("会员"))
		    	 {
				 digshow3();
	  			 return;
		    	 }
				}
	            if (null != mediaPlayer) {
	                // 正在播放
	                if (mediaPlayer.isPlaying()) {
	                    Constant.playPosition = mediaPlayer.getCurrentPosition();
	                    mediaPlayer.pause();
	                    playButton.setBackgroundResource(R.drawable.view_zt);
	                } else {
	                	
	                    if (Constant.playPosition >= 0) {
	                        mediaPlayer.seekTo(Constant.playPosition);
	                        mediaPlayer.start();
	                        playButton.setBackgroundResource(R.drawable.view_ks);
	                        Constant.playPosition = -1;
	                    }
	                   
	                   
	                }
	              /*  if(finish)
		            {
		            	   mediaPlayer.seekTo(seekBar.getProgress());
		                   mediaPlayer.start(); 
		                   finish=false;
		                   playButton.setBackgroundResource(R.drawable.view_ks);
		                   Constant.playPosition = -1;
		            }*/
	            }
	          
	            else
                {
	            	progressBar.setVisibility(view.VISIBLE);
                	playVideo();
                	playButton.setBackgroundResource(R.drawable.view_ks);
                }
                
	        }
	        // 视频截图
	      /*  if (v == screenShotButton) {
	            if (null != mediaPlayer) {
	                // 视频正在播放，
	                if (mediaPlayer.isPlaying()) {
	                    // 获取播放位置
	                    Constants.playPosition = mediaPlayer.getCurrentPosition();
	                    // 暂停播放
	                    mediaPlayer.pause();
	                    //
	                    playButton.setText("播放");
	                }
	                // 视频截图
	                savaScreenShot(Constants.playPosition);
	            } else {
	                Toast.makeText(SurfaceViewTestActivity.this, "视频暂未播放！", Toast.LENGTH_SHORT).show();
	            }
	        }*/
	       
	        else if (v ==error ) {
	        	 if (null != CopyOfhome_video.this.mediaPlayer) {
		                // 提前标志为false,防止在视频停止时，线程仍在运行。
		                seekBarAutoFlag = false;
		                // 如果正在播放，则停止。
		                if (mediaPlayer.isPlaying()) {
		                    mediaPlayer.stop();
		                }
		                // 释放mediaPlayer
		                CopyOfhome_video.this.mediaPlayer.release();
		                CopyOfhome_video.this.mediaPlayer = null;
	        	 }
	        	 playVideo();
	        	 error.setVisibility(View.GONE);
	        	 progressBar.setVisibility(View.VISIBLE );
	        }
	        else if(v ==surfaceView )
	        {
	        	
				if (display) {
					
					playcontrl.setVisibility(View.GONE);
					display = false;
				} else {
					
					playcontrl.setVisibility(View.VISIBLE);
					surfaceView.setVisibility(View.VISIBLE);
					/**
					 * 设置播放为全屏
					 */
				/*	ViewGroup.LayoutParams lp = pView.getLayoutParams();
					lp.height = LayoutParams.FILL_PARENT;
					lp.width = LayoutParams.FILL_PARENT;
					pView.setLayoutParams(lp);*/
					display = true;
				}
	        }
	        else if(v ==videoView )
	        {
	        	
				if (display) {
					
					playcontrl.setVisibility(View.GONE);
					display = false;
				} else {
					
					playcontrl.setVisibility(View.VISIBLE);
					videoView.setVisibility(View.VISIBLE);
					/**
					 * 设置播放为全屏
					 */
				/*	ViewGroup.LayoutParams lp = pView.getLayoutParams();
					lp.height = LayoutParams.FILL_PARENT;
					lp.width = LayoutParams.FILL_PARENT;
					pView.setLayoutParams(lp);*/
					display = true;
				}
	        }
	        else if(v.getId()==R.id.button_videoSize)
	    	{
	    		  if(!fullscreen){
	    			  /*WindowManager.LayoutParams layoutParams=super.getWindow().getAttributes();
	    			  layoutParams.screenBrightness=1/2;//设置屏幕的亮度
	    			  super.getWindow().setAttributes(layoutParams);*/
	    			/*  validator.setScreenBrightness(this, 20);*/
	    			  getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    			  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	    				LayoutParams lp;       
	    		        lp=layout_view.getLayoutParams();
	    		        lp.width= LayoutParams.FILL_PARENT;;
	    		        lp.height= LayoutParams.FILL_PARENT;;        
	    		        layout_view.setLayoutParams(lp); 
	    		        backButton.setVisibility(view.GONE);
	    		            fullscreen = true;//改变全屏/窗口的标记
	    		        }else{//设置RelativeLayout的窗口模式
	    		        	/*validator.setScreenBrightness(this,right);*/
	    		        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    		        	getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    		        	LayoutParams lp;       
	    		            lp=layout_view.getLayoutParams();
	    		            //lp.width=100;
	    		            lp.height=Constant.displayWidth/16*9;        
	    		            layout_view.setLayoutParams(lp); 
	    		            backButton.setVisibility(view.VISIBLE);
	    		             fullscreen = false;//改变全屏/窗口的标记
	    		        }    
	    	}

	    }

	    /**
	     * 播放完毕监听
	     * 
	     * @param mp
	     */
	    @Override
	    public void onCompletion(MediaPlayer mp) {
	        // 设置seeKbar跳转到最后位置
	        seekBar.setProgress(Integer.parseInt(String.valueOf(videoTimeLong)));
	        finish=true;
	        Constant.playPosition = -1;
            mediaPlayer.pause();
            playButton.setBackgroundResource(R.drawable.view_zt);
	    }

	    /**
	     * 视频缓存大小监听,当视频播放以后 在started状态会调用
	     */
	    public void onBufferingUpdate(MediaPlayer mp, int percent) {
	        // TODO Auto-generated method stub
	        // percent 表示缓存加载进度，0为没开始，100表示加载完成，在加载完成以后也会一直调用该方法
	        Log.e("text", "onBufferingUpdate-->" + percent);
	        speed.setText(percent+"%");
	        // 可以根据大小的变化来
	    }

	    /**
	     * 错误监听
	     * 
	     * @param mp
	     * @param what
	     * @param extra
	     * @return
	     */
	    @Override
	    public boolean onError(MediaPlayer mp, int what, int extra) {
	    	error.setVisibility(View.VISIBLE);
	    	progressBar.setVisibility(View.GONE );
	    	playButton.setBackgroundResource(R.drawable.view_zt);
	    	 if (null != CopyOfhome_video.this.mediaPlayer) {
	                // 提前标志为false,防止在视频停止时，线程仍在运行。
	                seekBarAutoFlag = false;
	                // 如果正在播放，则停止。
	                if (mediaPlayer.isPlaying()) {
	                    mediaPlayer.stop();
	                }
	                // 释放mediaPlayer
	                CopyOfhome_video.this.mediaPlayer.release();
	                CopyOfhome_video.this.mediaPlayer = null;
     	 }
	        /*switch (what) {
	        
	            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
	                Toast.makeText(this, "MEDIA_ERROR_UNKNOWN", Toast.LENGTH_SHORT).show();
	                break;
	            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
	                Toast.makeText(this, "MEDIA_ERROR_SERVER_DIED", Toast.LENGTH_SHORT).show();
	                break;
	            default:
	                break;
	        }
	*/
	        switch (extra) {
	            case MediaPlayer.MEDIA_ERROR_IO:
	                Toast.makeText(this, "网络链接失败", Toast.LENGTH_SHORT).show();
	                break;
	            case MediaPlayer.MEDIA_ERROR_MALFORMED:
	                Toast.makeText(this, "MEDIA_ERROR_MALFORMED", Toast.LENGTH_SHORT).show();
	                break;
	            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
	                Toast.makeText(this, "MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK",
	                        Toast.LENGTH_SHORT).show();
	                break;
	            case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
	                Toast.makeText(this, "MEDIA_ERROR_TIMED_OUT", Toast.LENGTH_SHORT).show();
	                break;
	            case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
	                Toast.makeText(this, "MEDIA_ERROR_UNSUPPORTED", Toast.LENGTH_SHORT).show();
	                break;
	        }
	        return false;
	    }

	    /**
	     * 转换播放时间
	     * 
	     * @param milliseconds 传入毫秒值
	     * @return 返回 hh:mm:ss或mm:ss格式的数据
	     */
	    @SuppressLint("SimpleDateFormat")
	    public String getShowTime(long milliseconds) {
	        // 获取日历函数
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTimeInMillis(milliseconds);
	        SimpleDateFormat dateFormat = null;
	        // 判断是否大于60分钟，如果大于就显示小时。设置日期格式
	        if (milliseconds / 60000 > 60) {
	            dateFormat = new SimpleDateFormat("hh:mm:ss");
	        } else {
	            dateFormat = new SimpleDateFormat("mm:ss");
	        }
	        return dateFormat.format(calendar.getTime());
	    }
	    
	    
	 /*  直播*/
	    

		

		private GSVideoView videoView;

		private RtSimpleImpl simpleImpl;
		
		// 站点类型ServiceType.ST_CASTLINE 直播webcast，
			// ServiceType.ST_MEETING 会议meeting，
			// ServiceType.ST_TRAINING 培训 training
		private ServiceType serviceType = ServiceType.ST_CASTLINE;
		private UserInfo self;

	    private void initzb() {
			// TODO Auto-generated method stub
			/**
			 * 初始化一个RTSDK简单实现，重新定义一个类继承RtSimpleImpl也是可以的，
			 * 并实现其3个抽象函数，其中onGetContext必须要返回app context，音视频最佳选择。
			 * 注：这里是在onCreate中创建的simpleImpl，如果有屏幕变化引起simpleImpl 重新被创建需要自行处理；回调线程为非UI线程，更新ui请在UI线程中处理。
			 */
			simpleImpl = new RtSimpleImpl() {
				
				
				
				@Override
				public Context onGetContext() {
					return getBaseContext();
				}
				
				@Override
				protected void onVideoStart() {
				
					
				}
				
				@Override
				protected void onVideoEnd() {
					
					
				}
				
				/**
				 * result 0 表示加入房间（直播间、会议室、课堂）成功  其他代表加入失败  失败后最好以对话框通知用户本次操作失败了
				 */
				@Override
				public void onRoomJoin(final int result, UserInfo self) {
					super.onRoomJoin(result, self);
					CopyOfhome_video.this.self = self;
					
					runOnUiThread(new Runnable() {
						public void run() {
							
							String resultDesc = null;
							switch (result) {
							//加入成功  除了成功其他均需要正常提示给用户
							case JoinResult.JR_OK:
								AlertUtil.toast(CopyOfhome_video.this, "您已加入成功");
								progressBar.setVisibility(view.GONE);
								coverImg.setVisibility(view.GONE);
								break;
								//加入错误
							case JoinResult.JR_ERROR:
								resultDesc = "加入失败，重试或联系管理员";
								leaveCast();
								break;
								//课堂被锁定
							case JoinResult.JR_ERROR_LOCKED:
								resultDesc = "直播间已被锁定";
								
								break;
								//老师（组织者已经加入）
							case JoinResult.JR_ERROR_HOST:
								resultDesc = "老师已经加入，请以其他身份加入";
								break;
								//加入人数已满
							case JoinResult.JR_ERROR_LICENSE:
								resultDesc = "人数已满，联系管理员";
								
								break;
								//音视频编码不匹配
							case JoinResult.JR_ERROR_CODEC:
								resultDesc = "编码不匹配";
								break;
								//超时
							case JoinResult.JR_ERROR_TIMESUP:
								resultDesc = "已经超过直播结束时间";
								break;
								
							default:
								resultDesc = "其他结果码：" + result + "联系管理员";
								break;
							}
							if(resultDesc != null){
								Toast.makeText(CopyOfhome_video.this,resultDesc,Toast.LENGTH_SHORT).show();
							}
						}
					});
					
				}
				
				/**
				 * 直播状态 s.getValue()   0 默认直播未开始 1、直播中， 2、直播停止，3、直播暂停
				 */
				@Override
				public void onRoomPublish(State s) {
					super.onRoomPublish(s);
					
					//TODO 此逻辑是控制视频要在直播开始后才准许看的逻辑
					/*	byte castState = s.getValue();
					    RtSdk rtSdk = getRtSdk();
					    
						switch (castState) {
						case 1:
							setVideoView(videoView);
					        rtSdk.audioOpenSpeaker(null);
							break;
						case 0:
						case 2:
						case 3:
							setVideoView(null);
					        rtSdk.audioCloseSpeaker(null);
						default:
							break;
						}*/
					
				}

				@Override
				public void onJoin(boolean result) {
					// TODO Auto-generated method stub
					
				}
				
				//退出完成 关闭界面
				@Override
				protected void onRelease(final int reason) {
					//reason 退出原因
					runOnUiThread(new Runnable() {
						
						@SuppressLint("NewApi")
						@Override
						public void run() {
							String msg = "已退出";
							switch (reason) {
							//用户自行退出  正常退出
							case LeaveReason.LR_NORMAL:
								msg = "您已经成功退出";
								break;
						    //LR_EJECTED = LR_NORMAL + 1; //被踢出
							case LeaveReason.LR_EJECTED:
								msg = "您已被踢出"; 
								break;
							//LR_TIMESUP = LR_NORMAL + 2; //时间到
							case LeaveReason.LR_TIMESUP:
								msg = "时间已过"; 
								break;
							//LR_CLOSED = LR_NORMAL + 3; //直播（课堂）已经结束（被组织者结束）
							case LeaveReason.LR_CLOSED:
								msg = "直播间已经被关闭"; 
								break;

							default:
								break;
							}
							
							      leaveCast();
					
							
						}
					});
				}
			};
			
			videoView = (GSVideoView) findViewById(R.id.surface_casting_cus);
			videoView.setOnClickListener(CopyOfhome_video.this);
			simpleImpl.setVideoView(videoView);
	
		}
	    String liveid="",pwd="";
	    public void goinroom() {
			// TODO Auto-generated method stub
	    	InitParam p = new InitParam();
			//domain
			p.setDomain("wzect.gensee.com");
			//编号（直播间号）,如果没有编号却有直播id的情况请使用setLiveId("此处直播id或课堂id");
			//p.setNumber(number);
			p.setLiveId(liveid);
			//站点认证帐号，根据情况可以填""
			p.setLoginAccount("");
			//站点认证密码，根据情况可以填""
			p.setLoginPwd("");
			//昵称，供显示用
			p.setNickName(""+appuser.getInstance().getUserinfo().nickname);
			//加入口令，没有则填""
			p.setJoinPwd(pwd);
			//站点类型ServiceType.ST_CASTLINE 直播webcast，
			//ServiceType.ST_MEETING 会议meeting，
			//ServiceType.ST_TRAINING 培训
			p.setServiceType(serviceType);
			
			RtComp comp = new RtComp(getApplicationContext(),
					CopyOfhome_video.this);
			comp.setbAttendeeOnly(true);
			comp.initWithGensee(p);
		}
		@Override
		public void onInited(String rtParam) {
			
			simpleImpl.joinWithParam("", rtParam);
		}

		@Override
		public void onErr(final int errCode) {
		
			runOnUiThread(new Runnable() {

				@Override
				public void run() {

					Toast.makeText(CopyOfhome_video.this,"初始化错误:错误码" + errCode
							+ "，请参考文档中的错误码说明",Toast.LENGTH_SHORT).show();
				}
			});
		}

		
		/**
		 * 退出的时候请调用
		 */
		private void leaveCast(){
			//TODO 显示进度框
			simpleImpl.leave(false);
			//finish();
		}

		
		@Override
		public void onBackPressed() {
			if (self == null) {
				super.onBackPressed();
				return;
			}
			leaveCast();
		}
		
		
		
	}

