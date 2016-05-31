package mno.ruili_app.my;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.HandlerRequestCode;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import mno.ruili_app.Constant;
import mno.ruili_app.PassMgr;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.CustomEditText;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.main.ExampleUtil;
import mno.ruili_app.main.Main;
import mno.ruili_app.main.MyFragment;
import mno.ruili_app.my.im.UserProfileManager;
import mno_ruili_app.home.home_video;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.loginhandler;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class mylogin extends Activity{
	private  Class<? extends android.app.Activity> StartClass_;
	private CustomEditText  user_name_;
	private CustomEditText  pass_;
	private loginhandler  handler_;
	webhandler handler_2; 
	UMSocialService mController;
	CheckBox check_1;
	boolean save=false;
	ImageView imageView1,imageView2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ativity_my_login);		
		 mController = UMServiceFactory.getUMSocialService("com.umeng.login");
		 mController.getConfig().setSsoHandler(new SinaSsoHandler());//配置sina SSO(免登录)开关
		 addQQZonePlatform();
	     addWXPlatform();
		if( this.getIntent().getExtras() != null){
			StartClass_  =  (Class<? extends android.app.Activity>)getIntent().getSerializableExtra("class"); 			 			 
			if(appuser.getInstance().IsLogin() == true){
				Intent intent = new Intent(mylogin.this,StartClass_);					
				mylogin.this.startActivity(intent);
				this.finish();				 
			}
		}
		//一键清除的×
		imageView1=(ImageView)this.findViewById(R.id.imageView1);		
		imageView2=(ImageView)this.findViewById(R.id.imageView2);
		user_name_ = (CustomEditText)this.findViewById(R.id.edi_my_loginname);
		//设置账号焦点改变监听
		user_name_.setOnFocusChangeListener(new OnFocusChangeListener() {			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus)
					imageView1.setVisibility(View.VISIBLE);
			}
		});	
		pass_ = (CustomEditText)this.findViewById(R.id.edi_my_loginpsw);
		//设置密码焦点改变监听
		pass_.setOnFocusChangeListener(new OnFocusChangeListener() {			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus)
					imageView2.setVisibility(View.VISIBLE);
							
			}
		});	
		check_1 = (CheckBox)this.findViewById(R.id.my_xw_but);
		check_1.setChecked(PassMgr.IsSave());
		if(PassMgr.IsSave()){
			save=true;
			user_name_.setText(PassMgr.getName());
			pass_.setText(PassMgr.getPass());
		}
		else{
			user_name_.setText(PassMgr.getName());			
		}			
		check_1.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				save=arg1;
				//MessageBox.Show(getApplicationContext(), arg1+"");
				}});
		//初始化操作
		InitHandler();	
	  
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("mylogin");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("mylogin");
		MobclickAgent.onPause(this);
	}
	/*
	 * 注册第三方账号的方法
	 */
	String coopeNo="",sex="",username="",provice="",city="",signText="",work="",danwei="",phone="",coopeMethod="";
	private void reg() {
		// TODO Auto-generated method stub
		  Map<String, String> params = new HashMap<String, String>();
		  params.put("coopeNo",coopeNo);
		  if(sex.equals("男"))
			  sex="1";
		  else			  
			  sex="2";
		  params.put("sex", sex);
		  params.put("username", username);
		  params.put("provice", provice);
		  params.put("city", city);
		  params.put("signText", signText);
		  params.put("profession", work);
		  params.put("shop_name", danwei);
		  params.put("phone", phone);
		  params.put("coopeMethod", coopeMethod);
		  handler_2.SetRequest(new RequestType("",Type.register),params);
	}
	//
	String a="",b="";
	int i=0;
	private void InitHandler(){
		//1.登录服务器
		 handler_ = new loginhandler(){
			@Override
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
				if(code!=0){
					 imageView1.setVisibility(View.VISIBLE);
					 imageView2.setVisibility(View.VISIBLE);
					 a=user_name_.getText().toString();
					 b=pass_.getText().toString();
				}
				
			}			
			@Override
			public void OnLoginSuccess(JSONObject response) {
				// TODO Auto-generated method stub
				/*Intent intent = new Intent(mylogin.this,Main.class);
				
				mylogin.this.startActivity(intent);*/
				PassMgr.Save(user_name_.getText().toString(), pass_.getText().toString(),save);
				PassMgr.IsAuto(true);
				//登录环信的后台
				String huanxin="dsxuser"+appuser.getInstance().getUserinfo().uid.toString();
				EMClient.getInstance().login(huanxin,huanxin,new EMCallBack() {//回调
					@Override
					public void onSuccess() {
						runOnUiThread(new Runnable() {
							public void run() {
								//EMClient.getInstance().groupManager().loadAllGroups();
								//EMClient.getInstance().chatManager().loadAllConversations();
								Log.i("main", "登陆聊天服务器成功！");		
							}
						});
						Log.i("main", "登陆聊天服务器成功！");
					}
				 
					@Override
					public void onProgress(int progress, String status) {
				 
					}
				 
					@Override
					public void onError(int code, String message) {
						Log.i("main", code+message);
						Log.i("main", "登陆聊天服务器失败！");
					}
				});
				//跳转
				if(StartClass_ != null){
					Intent intent = new Intent(mylogin.this,StartClass_);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
					mylogin.this.startActivity(intent);
				}				
				finish();
			}
	    };
	    handler_.SetProgressDialog(this);
		//注册
		handler_2 = new webhandler(){
			@Override
			public void OnResponse(JSONObject response) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				try {
					if(i==0){
						i++;
						reg();
						return;
					}
					JSONObject data = response.getJSONObject("data");
				    appuser.getInstance().OnLoginSuccess(data);
				   //登录环信的后台
					String huanxin="dsxuser"+appuser.getInstance().getUserinfo().uid.toString();
					EMClient.getInstance().login(huanxin,huanxin,new EMCallBack() {//回调
						@Override
						public void onSuccess() {
							runOnUiThread(new Runnable() {
								public void run() {
									//EMClient.getInstance().groupManager().loadAllGroups();
									//EMClient.getInstance().chatManager().loadAllConversations();
									Log.i("main", "登陆聊天服务器成功！");		
								}
							});
							Log.i("main", "登陆聊天服务器成功！");
						}
					 
						@Override
						public void onProgress(int progress, String status) {
					 
						}
					 
						@Override
						public void onError(int code, String message) {
							Log.i("main", code+message);
							Log.i("main", "登陆聊天服务器失败！");
						}
					});
				    mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, response.getJSONObject("data").getString("coopeNo")));
				    //appuser.getInstance().getUserinfo().nickname=username;
				    if(StartClass_ != null)
					{
						Intent intent = new Intent(mylogin.this,StartClass_);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
						intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
						mylogin.this.startActivity(intent);
					}
				
					finish();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};			
		handler_2.SetProgressDialog(mylogin.this);
	}

	public void onclick(View v) {
		 if(v.getId()==R.id.my_but_login)
		 {
			 if(user_name_.getText().toString().length()<=0)
			 {
				 MessageBox.Show(getApplicationContext(), "请输入账号");
				 return;				 
			 }
			 if(pass_.getText().toString().length()<=0)
			 {
				 MessageBox.Show(getApplicationContext(), "请输入密码");
				 return;
			 }
			 int i=0;
			 if(a.length()>0)
			 {
				 
				 if(a.equals(user_name_.getText().toString()))
				 {
					i++;
				 }
				 if(b.equals(pass_.getText().toString()))
				 {
					 i++;
					
				 }
				
			 }
			 if(i==2)
			 {
				 MessageBox.Show(getApplicationContext(), "登陆失败，请重新检查账户信息是否正确");
				 return;
			 }			 
			 handler_.Login(mylogin.this,user_name_.getText().toString(), pass_.getText().toString());										 
		 }
		 else if(v.getId()==R.id.imageView1)
		 {
			 user_name_.setText("");
		 }
		 else if(v.getId()==R.id.imageView2)
		 {
			 pass_.setText("");
		 }
		 else if(v.getId()==R.id.my_but_registered)
		 {
			 Intent intent0 = new Intent(mylogin.this, myregistered.class); 
			    
				startActivity(intent0);
		 }
		 else if(v.getId()==R.id.login_findpwd)
		 {
			 Intent intent0 = new Intent(mylogin.this, my_findpwd.class); 			    
			 startActivity(intent0);
		 }
		 else if(v.getId()==R.id.my_login_wx)
		 {
			 //配置微信 SSO(免登录)开关
			 //mController.getConfig().getSsoHandler(HandlerRequestCode.WX_REQUEST_CODE);
			 boolean i= mController.getConfig().getSsoHandler(HandlerRequestCode.WX_REQUEST_CODE).isClientInstalled();
			 if(i)
				 loginwx();
			 else
				 MessageBox.Show(getApplicationContext(), "您没有安装微信，请先下载并安装");
		 }
		 else if(v.getId()==R.id.my_login_qq)
		 {
			 login(SHARE_MEDIA.QQ);
		 }
		 else if(v.getId()==R.id.my_login_sina)
		 {
			 login(SHARE_MEDIA.SINA);
		 }		 		 
	 }
	/*
	 * 1.微信登录
	 */
	 private void loginwx() {
		 mController.doOauthVerify(mylogin.this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
			    @Override
			    public void onStart(SHARE_MEDIA platform) {
			        Toast.makeText(mylogin.this, "授权开始", Toast.LENGTH_SHORT).show();
			    }
			    @Override
			    public void onError(SocializeException e, SHARE_MEDIA platform) {
			        Toast.makeText(mylogin.this, "授权错误", Toast.LENGTH_SHORT).show();
			    }
			    @Override
			    public void onComplete(Bundle value, SHARE_MEDIA platform) {
			        Toast.makeText(mylogin.this, "授权完成", Toast.LENGTH_SHORT).show();
			        //获取相关授权信息
			        //getUserInfo(platform);
			        //coopeNo=value.getString("openid");
			        mController.getPlatformInfo(mylogin.this, SHARE_MEDIA.WEIXIN, new UMDataListener() {
					    @Override
					    public void onStart() {
					        Toast.makeText(mylogin.this, "获取平台数据开始...", Toast.LENGTH_SHORT).show();
					    }                                              
					    @Override
				        public void onComplete(int status, Map<String, Object> info) {				    	
				            if(status == 200 && info != null){
				            	coopeNo=info.get("openid").toString();
		        			    username=info.get("nickname").toString();		            			 
		        			    sex=info.get("sex").toString();					            	
				            	 if(sex.equals("1"))
				            		 sex="男";
				            	 else
				            		 sex="女";
		        			    provice=info.get("province").toString();
				                city=info.get("city").toString();
		//		                work=info.get("work").toString();
		//		                danwei=info.get("danwei").toString();
		//		                phone=info.get("phone").toString();
				                coopeMethod="WX";
				                reg();		            		  		            		  
				            }
				        }
				   });			        	
		        }
			    @Override
			    public void onCancel(SHARE_MEDIA platform) {
			        Toast.makeText(mylogin.this, "授权取消", Toast.LENGTH_SHORT).show();
			    }
			} );
	}
	 /*
	  * 2.新浪登录
	  */
//	private void loginsina() {
//			//获取accesstoken及用户资料
//				mController.getPlatformInfo(mylogin.this, SHARE_MEDIA.SINA, new UMDataListener() {
//				    @Override
//				    public void onStart() {
//				        Toast.makeText(mylogin.this, "获取平台数据开始...", Toast.LENGTH_SHORT).show();
//				    }
//
//				    @Override
//			        public void onComplete(int status, Map<String, Object> info) {
//			            if(status == 200 && info != null){
//			            	coopeNo=info.get("uid").toString();
//			            	sex=info.get("gender").toString();			            	
//			            	if(sex.equals("1"))
//			            		sex="1";
//			            	else
//			            		sex="2";
//			            	username=info.get("screen_name").toString();
//			            	String b[] = info.get("location").toString().split(" ");  
//			            	provice=b[0];
//			            	city=b[1];
//			            	signText=info.get("description").toString();			            	
//			                reg();
//			            }else{
//			               Log.d("TestData","发生错误："+status);
//			           }
//			        }                                 
//				  
//				});
//		}
	 
	 /**
	     * 授权。如果授权成功，则获取用户信息
	     * qq和新浪登录调用此方法
	     */
	    private void login(final SHARE_MEDIA platform) {
	        mController.doOauthVerify(mylogin.this, platform, new UMAuthListener() {

	            @Override
	            public void onStart(SHARE_MEDIA platform) {
	            	//[mw_shl_code=applescript,true]// wx967daebe835fbeac
	            }

	            @Override
	            public void onError(SocializeException e, SHARE_MEDIA platform) {
	            	Toast.makeText(mylogin.this, "error", 0).show();
	            }

	            @Override
	            public void onComplete(Bundle value, SHARE_MEDIA platform) {
	                //qq平台
	                 if(platform.equals(SHARE_MEDIA.QQ )){
          			    coopeNo=value.getString("openid");
          		    }
	                String uid = value.getString("uid");
	                if (!TextUtils.isEmpty(uid)) {
	                    getUserInfo(platform);
	                } else {
	                //    Toast.makeText(mylogin.this, "授权失败...", Toast.LENGTH_SHORT).show();
	                }
	            }

	            @Override
	            public void onCancel(SHARE_MEDIA platform) {
	            	
	            }
	        });
	    }
	    //返回结果
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		    super.onActivityResult(requestCode, resultCode, data);
		    /**使用SSO授权必须添加如下代码 */
		    UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
		    if(ssoHandler != null){
		       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		    }
		}
	    /**
	     * 获取授权平台的用户信息（获取qq和新浪的账户信息）
	     */
	    private void getUserInfo(final SHARE_MEDIA platform) {
	    	//获取accesstoken及用户资料
	        mController.getPlatformInfo(this, platform, new UMDataListener() {
	            @Override
	            public void onStart() {
	            }
	            @Override
	            public void onComplete(int status, Map<String, Object> info) {
	            	  if(status == 200 && info != null){
	            		  //新浪平台
	            		  if(platform.equals(SHARE_MEDIA.SINA) ){
			            	coopeNo=info.get("uid").toString();
			            	sex=info.get("gender").toString();			            	
			            	if(sex.equals("1"))
			            		sex="男";
			            	else
			            		sex="女";
			            	username=info.get("screen_name").toString();
			            	String b[] = info.get("location").toString().split(" ");  
			            	provice=b[0];
			            	//city=b[1];			            	
			            	signText=info.get("description").toString();
			            	coopeMethod="新浪";
			                reg();
			               }
	            		  //qq平台
	            		  else if(platform.equals(SHARE_MEDIA.QQ )){
	            			  //coopeNo=info.get("openid").toString();
	            			  username=info.get("screen_name").toString();
	            			  sex=info.get("gender").toString();
	            			  provice=info.get("province").toString();
				              city=info.get("city").toString();
				              coopeMethod="QQ";
				              reg();
	              		  }	            		  	            		
			            }else{
			               //Log.d("TestData","发生错误："+status);
			           }
	                
	            }
	        });
	    }
	    /*
	     * qq和qqZone的初始化配置
	     */
		private void addQQZonePlatform() {			
			UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(mylogin.this, "100424468",
	                "c7394704798a158208a74ab60104f0ba");
	             qqSsoHandler.addToSocialSDK();
		}
		/**
		 * 添加微信初始化配置
		 */
		private void addWXPlatform() {
		    // 注意：在微信授权的时候，必须传递appSecret
		    // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		    String appId = "wx1c5493ce24256f7b";
		    String appSecret = "66fa858f80415cd8138c0e54b122c0c3";
		    // 添加微信平台
		    UMWXHandler wxHandler = new UMWXHandler(mylogin.this, appId, appSecret);
		    wxHandler.addToSocialSDK();

		  /*  // 支持微信朋友圈
		    UMWXHandler wxCircleHandler = new UMWXHandler(mylogin.this, appId, appSecret);
		    wxCircleHandler.setToCircle(true);
		    wxCircleHandler.addToSocialSDK();*/
		}
		private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

	        @Override
	        public void gotResult(int code, String alias, Set<String> tags) {
	            String logs ;
	            switch (code) {
	            case 0:
	                logs = "Set tag and alias success";
	               // Log.i(TAG, logs);
	                break;
	                
	            case 6002:
	                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
	               // Log.i(TAG, logs);
	                if (ExampleUtil.isConnected(mylogin.this)) {
	                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
	                } else {
	                	//Log.i(TAG, "No network");
	                }
	                break;
	            
	            default:
	                logs = "Failed with errorCode = " + code;
	               // Log.e(TAG, logs);
	            }
	            
	            //ExampleUtil.showToast(logs, context_);
	        }
		    
		};
		private static final int MSG_SET_ALIAS = 1001;
		private static final int MSG_SET_TAGS = 1002;
		 private final Handler mHandler = new Handler() {
		        @Override
		        public void handleMessage(android.os.Message msg) {
		            super.handleMessage(msg);
		            switch (msg.what) {
		            case MSG_SET_ALIAS:
		               // Log.d(TAG, "Set alias in handler.");
		                JPushInterface.setAliasAndTags(mylogin.this, (String) msg.obj, null, mAliasCallback);
		                break;		                		         
		            default:
		               // Log.i(TAG, "Unhandled msg - " + msg.what);
		            }
		        }
		    };
}
