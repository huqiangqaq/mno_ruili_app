package mno_ruili_app.nei;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import mno.ruili_app.BclFragmentPagerAdapter;
import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.main.HomeFragment;
import mno.ruili_app.main.Main;
import mno.ruili_app.main.MyFragment;
import mno.ruili_app.main.NeighborFragment;
import mno.ruili_app.my.mylogin;
import mno_ruili_app.home.home_video;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class nei_zx extends FragmentActivity
{
	ViewPager ViewPager_;
	TextView zx_title,zx_pl,textView2;
	LinearLayout nei_zxxq_more;
	BclFragmentPagerAdapter PagerAdapter_;
	webhandler handler_; 
	//友盟社会化分享
	private UMSocialService mController = UMServiceFactory.getUMSocialService(Constant.DESCRIPTOR);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.nei_zx);
		IntentFilter filter = new IntentFilter();
		filter.addAction("mno.zx");
		filter.setPriority(Integer.MAX_VALUE);
		registerReceiver(myReceiver, filter);
		init();
		InitHandler();
		 // 添加新浪SSO授权
       
	}
	private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			 String name = intent.getExtras().getString("name");  
			 zx_pl.setText(name+"评论");
			
		}

	};
	private void InitHandler() {
		// TODO Auto-generated method stub
		handler_ = new webhandler(){

			@Override
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				String ms=mess;
				ms=ms+"";
				MessageBox.Show(nei_zx.this,mess);
				super.OnError(code, mess);
			}

			@Override
			public void OnResponse(JSONObject response) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				String mess;
				try {
					mess = response.getString("message");
					if(mess.equals("null") == false && mess.length() > 1)
					{
						Toast.makeText(nei_zx.this, mess, Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}};
			
	}
	LinearLayout layout,nei_pl_box,nei_fb_box;
	EditText nei_bj_edi;
	webhandler handler_2;
	private void init() {
		// TODO Auto-generated method stub
		 imm = (InputMethodManager) getSystemService(nei_zx.INPUT_METHOD_SERVICE); 
		nei_pl_box=(LinearLayout)nei_zx.this.findViewById(R.id.nei_pl_box);
		nei_fb_box=(LinearLayout)nei_zx.this.findViewById(R.id.nei_fb_box);
		nei_bj_edi=(EditText)this.findViewById(R.id.nei_bj_edi);
		
		
		
		zx_pl= (TextView) findViewById(R.id.zx_pl);
		textView2= (TextView) findViewById(R.id.textView2);
		textView2.setVisibility(View.GONE );
		zx_pl.setText(Constant.zxtitle+"评论");
		zx_title= (TextView) findViewById(R.id.zxxq_titer);
		nei_zxxq_more= (LinearLayout) findViewById(R.id.nei_zxxq_more);
		ViewPager_ = (ViewPager) findViewById(R.id.indexpager);
		PagerAdapter_ = new BclFragmentPagerAdapter(this.getSupportFragmentManager());
		PagerAdapter_.put(new nei_zxxq());
		PagerAdapter_.put(new nei_zxpl());
		//PagerAdapter_.put(new MyFragment());
		ViewPager_.setAdapter(PagerAdapter_);
		ViewPager_.setOnPageChangeListener(new OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				//MessageBox.Show(nei_zx.this, arg0+"");
				if(arg0==1)
				{
				 zx_title.setText("评论");
				 nei_zxxq_more.setVisibility(View.GONE );
				 zx_pl.setVisibility(View.GONE );
				 }
				else
				{
				zx_title.setText("");
				nei_zxxq_more.setVisibility(View.VISIBLE);
				zx_pl.setVisibility(View.VISIBLE);
				}
			}});
		nei_bj_edi.setOnClickListener(new View.OnClickListener() {
	         
	         @Override
	         public void onClick(View v) {
	        	 textView2.setVisibility(View.VISIBLE);
	         }
	     });
		nei_bj_edi.setOnFocusChangeListener(new android.view.View.
	    			OnFocusChangeListener() {
	    		@Override
	    		public void onFocusChange(View v, boolean hasFocus) {
	    			if (hasFocus) {
	    				// 此处为得到焦点时的处理内容
	    				textView2.setVisibility(View.VISIBLE);
	    			} else {
	    				// 此处为失去焦点时的处理内容
	    				textView2.setVisibility(View.GONE);
	    			}
	    		}
	    	});
		
	}
	InputMethodManager imm;
	public void onclick(View v)
	{
		
		imm.hideSoftInputFromWindow(nei_bj_edi.getWindowToken(), 0);
		textView2.setVisibility(View.GONE);
	/*if(v.getId()==R.id.nei_pl)
	{
		Intent intent = new Intent(nei_zx.this,nei_pl.class);
		Constant.itemzt="news";
		intent.putExtra("ID",Constant.zxid);  
		intent.putExtra("type","news");  
		nei_zx.this.startActivity(intent);
	}*/
	if(v.getId()==R.id.nei_pl)
	{
		if(!appuser.getInstance().IsLogin()){
			appuser.getInstance().LoginToAct(nei_zx.this,  nei_zx.class);
   		    return;
   	     }		
		 textView2.setVisibility(View.VISIBLE);
		 nei_pl_box.setVisibility(View.GONE);;
		 nei_fb_box.setVisibility(View.VISIBLE);;
		 nei_bj_edi.setFocusable(true);   
		 nei_bj_edi.setFocusableInTouchMode(true);   
				 
		 nei_bj_edi.requestFocus();  
		 imm.showSoftInput(nei_bj_edi, 0);
	}
	
	else if(v.getId()==R.id.nei_fb)
	{
		
		  Map<String, String> params = new HashMap<String, String>();
		  params.put("focusId",Constant.zxid);
		  params.put("type","news");
		  /*params.put("uid","3");
		  params.put("accessCode","cnVpbGkz");*/
		  params.put("content",nei_bj_edi.getText().toString());
	      handler_.SetRequest(new RequestType("3",Type.addReply),params);
	      
	      nei_bj_edi.clearFocus();
	      nei_bj_edi.setText("");
	      
	     imm.hideSoftInputFromWindow(nei_bj_edi.getWindowToken(), 0);
	     
	     nei_pl_box.setVisibility(View.VISIBLE);;
		 nei_fb_box.setVisibility(View.GONE);;
		 Intent intent = new Intent();  
         intent.setAction("mno.zxpl");  
         intent.putExtra("name", "");  
         nei_zx.this.sendBroadcast(intent);  
	}
	else if(v.getId()==R.id.nei_zxxq_more)
	{
		showDialog(nei_zx.this);
	}
	else if(v.getId()==R.id.zx_pl)
	{
		ViewPager_.setCurrentItem(1, false);
	}
	
}
private void showDialog(Context context) {			    
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
		ImageView login_wb= (ImageView) window.findViewById(R.id.login_wb);
		RelativeLayout kb=(RelativeLayout)window.findViewById(R.id.kongbai);
		ImageView login_wxpy= (ImageView) window.findViewById(R.id.login_wxpy);
		login_qq.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Constant.SOCIAL_CONTENT=Constant.SOCIAL_CONTENT2;
				 mController.setShareContent(Constant.SOCIAL_CONTENT);
				 performShare(SHARE_MEDIA.QQ);
				 dialog.cancel();
			}

			
		});
		login_wxpy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Constant.SOCIAL_CONTENT=Constant.SOCIAL_CONTENT2;
				 mController.setShareContent(Constant.SOCIAL_CONTENT);
				 performShare(SHARE_MEDIA.WEIXIN_CIRCLE);
				 dialog.cancel();
			}});

		login_wx.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Constant.SOCIAL_CONTENT=Constant.SOCIAL_CONTENT2;
				 mController.setShareContent(Constant.SOCIAL_CONTENT);
				 performShare(SHARE_MEDIA.WEIXIN);
				 
				 dialog.cancel();
			}

			
			
		});
		login_wb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Constant.SOCIAL_CONTENT=Constant.SOCIAL_CONTENT2+Constant.SOCIAL_LINK;
				 mController.setShareContent(Constant.SOCIAL_CONTENT);
				 performShare(SHARE_MEDIA.SINA);
				 dialog.cancel();
			}

			
			
		});
		sc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 if(!appuser.getInstance().IsLogin())
		    	 {
					 appuser.getInstance().LoginToAct(nei_zx.this,nei_zx.class);
		    		 return;
		    	 }
				 Map<String, String> params = new HashMap<String, String>();
				  params.put("likeId",Constant.zxid);
				  params.put("forwhat","coll");
				  params.put("type","news");
				  handler_.SetRequest(new RequestType("2",Type.addLike),params);
				 dialog.cancel();
		
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
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    /**使用SSO授权必须添加如下代码 */
    UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
    if(ssoHandler != null){
       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
    }
}
public void performShare(SHARE_MEDIA platform) {
	mController.getConfig().closeToast();
	
    mController.postShare(nei_zx.this, platform, new SnsPostListener() {

    	
    	 @Override
         public void onStart() {
            MessageBox.Show(nei_zx.this , "开始分享");
         }

			@Override
			public void onComplete(SHARE_MEDIA arg0, int eCode,
					SocializeEntity arg2) {
				if (eCode == 200) {
					MessageBox.Show(nei_zx.this , "分享成功");
              } else {
                   String eMsg = "";
                   if (eCode == -101){
                       eMsg = "没有授权";
                   }
                   MessageBox.Show(nei_zx.this , "分享失败");
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
           Toast.makeText(nei_zx.this, showText, Toast.LENGTH_SHORT).show();
           
        }
    });*/
}

@Override
protected void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	
	imm.hideSoftInputFromWindow(nei_bj_edi.getWindowToken(), 0);
	textView2.setVisibility(View.GONE);
}

@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	
	MobclickAgent.onResume(this);
//	 Intent intent = new Intent();  
//     intent.setAction("mno.zxpl");  
//     intent.putExtra("name", "");  
//     nei_zx.this.sendBroadcast(intent);  
}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();	
	MobclickAgent.onPause(this);
}
@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	unregisterReceiver(myReceiver);
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
    UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(nei_zx.this,
            appId, appKey);
    
    qqSsoHandler.setTargetUrl(Constant.SOCIAL_LINK);
    qqSsoHandler.setTitle(Constant.SOCIAL_TITLE);
    qqSsoHandler.setShareAfterAuthorize(false);
    
    qqSsoHandler.addToSocialSDK();

    // 添加QZone平台
    QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(nei_zx.this, appId, appKey);
    qZoneSsoHandler.addToSocialSDK();
    
	mController.setShareContent(Constant.SOCIAL_CONTENT);
	// 设置分享图片, 参数2为图片的url地址
	mController.setShareMedia(new UMImage(nei_zx.this,  Constant.SOCIAL_IMAGE));
	
}
/**
 * @功能描述 : 添加微信平台分享
 * @return
 */
private void addWXPlatform() {
    // 注意：在微信授权的时候，必须传递appSecret
    // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
    String appId = "wx1c5493ce24256f7b";
    String appSecret = "66fa858f80415cd8138c0e54b122c0c3";
    // 添加微信平台
    UMWXHandler wxHandler = new UMWXHandler(nei_zx.this, appId, appSecret);
    wxHandler.setTargetUrl(Constant.SOCIAL_LINK);
    wxHandler.setTitle(Constant.SOCIAL_CONTENT);
    
    wxHandler.addToSocialSDK();
   
    // 支持微信朋友圈
    UMWXHandler wxCircleHandler = new UMWXHandler(nei_zx.this, appId, appSecret);
    wxCircleHandler.setToCircle(true);
    wxCircleHandler.setTargetUrl(Constant.SOCIAL_LINK);
    wxCircleHandler.setTitle(Constant.SOCIAL_CONTENT);
    wxCircleHandler.addToSocialSDK();
    mController.getConfig().setSsoHandler(new SinaSsoHandler());
}
}
