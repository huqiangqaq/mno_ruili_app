package mno.ruili_app.my;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbFileUtil;
import com.ab.util.AbLogUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.cropimage.CropImage;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.umeng.analytics.MobclickAgent;

import mno.down.ui.DownloadActivity;
import mno.ruili_app.PassMgr;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.userinfo;
import mno.ruili_app.ct.CropImageActivity;
import mno.ruili_app.ct.LoadingDialog;
import mno.ruili_app.ct.RoundImageView;
import mno.ruili_app.main.ExampleUtil;
import mno_ruili_app.home.home_video;
import mno_ruili_app.nei.nei_bj;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.webpost;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class my_xx extends  AbActivity{
	
	LinearLayout but_myname,but_mysex,but_myphonenum;
	TextView myname,myplace,mysex,mysign,mywork,mydanwei,myphonenum,tv_myphonenum;
	webhandler handler_,handler_2; 
	private View mAvatarView = null;
	

	/* 用来标识请求照相功能的activity */
	private static final int CAMERA_WITH_DATA = 3023;
	/* 用来标识请求gallery的activity */
	private static final int PHOTO_PICKED_WITH_DATA = 3021;
	/* 用来标识请求裁剪图片后的activity */
	private static final int CAMERA_CROP_DATA = 3022;
	
	/* 拍照的照片存储位置 */
	private  File PHOTO_DIR = null;
	// 照相机拍照得到的图片
	private File mCurrentPhotoFile;
	private String mFileName;
	Dialog  dialog_;
	String picPath="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_xx);
		FILE_LOCAL = new File(AbFileUtil.getImageDownloadDir(this));
		if(!FILE_LOCAL.exists()){
			FILE_LOCAL.mkdirs();
		}
		init();
		setimg();	
	}
	private void setimg() {
		// TODO Auto-generated method stub
		RoundImageView my_login_image;
		String path=appuser.getInstance().getUserinfo().bigImg.toString();
		my_login_image= (RoundImageView) this.findViewById(R.id.my_login_image);
		my_login_image.setImageUrl(RequestType.getWebUrl_(path),webpost.getImageLoader());
	}
	private void init() {
		// TODO Auto-generated method stub		
		dialog_ = LoadingDialog.createLoadingDialog(my_xx.this, "正在加载中...");
		String photo_dir = AbFileUtil.getImageDownloadDir(this);
	    if(AbStrUtil.isEmpty(photo_dir)){
	    	AbToastUtil.showToast(my_xx.this,"存储卡不存在");
	    }else{
	    	PHOTO_DIR = new File(photo_dir);
	    }
		but_myname= (LinearLayout) this.findViewById(R.id.but_myname);
		but_mysex= (LinearLayout) this.findViewById(R.id.but_mysex);
		but_myphonenum=(LinearLayout) this.findViewById(R.id.but_myphonenum);
		if(PassMgr.Isplatform()==false){
		//but_myphonenum.setBackgroundColor(android.graphics.Color.parseColor("#a7a7a7"));//#a7a7a7
	}
	
	myname= (TextView) this.findViewById(R.id.myname);
	myplace= (TextView) this.findViewById(R.id.myplace);
	mysex= (TextView) this.findViewById(R.id.mysex);
	mysign= (TextView) this.findViewById(R.id.mysign);
	mywork= (TextView) this.findViewById(R.id.mywork);
	mydanwei= (TextView) this.findViewById(R.id.mydanwei);
	myphonenum= (TextView) this.findViewById(R.id.myphonenum);
	tv_myphonenum=(TextView) findViewById(R.id.tv_myphonenum);
	if(PassMgr.Isplatform()==false&&!appuser.getInstance().getUserinfo().platform.equals("2")){
		tv_myphonenum.setTextColor(android.graphics.Color.parseColor("#a7a7a7"));//#a7a7a7
	}
	
	handler_ = new webhandler(){	
		@Override
		public void OnResponse(JSONObject response) {
		/*SharedPreferences sp = my_xx.this.getSharedPreferences("userInfo", my_xx.this.MODE_WORLD_READABLE); 
		Editor editor = sp.edit();  
		editor.putBoolean("auto", false);
		editor.commit();  */
	
		//System.exit(0);
		}
	
	};
			
	handler_.SetProgressDialog(my_xx.this);
	handler_2 = new webhandler(){

			@Override
			public void OnResponse(JSONObject response) {
				
				 dialog_.cancel(); 
				
			}

	};
			
			//handler_2.SetProgressDialog(my_xx.this);
}
 public void onclick(View v) {
	 if(v.getId()==R.id.but_myname)
	 {
		 Intent intent0 = new Intent(my_xx.this, my_xgname.class); 
		 intent0.putExtra("text",myname.getText().toString());  
		 startActivity(intent0);
	 }
	 else if(v.getId()==R.id.but_myimg)
	 {
		 dialog();
		 AbDialogUtil.showDialog(mAvatarView,Gravity.BOTTOM);
	 }
	//职业
	 else if(v.getId()==R.id.but_mywork)
	 {
		 Intent intent0 = new Intent(my_xx.this, My_xgwork.class); 		 
	     startActivity(intent0);
	 }
	 //工作单位
	 else if(v.getId()==R.id.but_mydanwei)
	 {
		 Intent intent0 = new Intent(my_xx.this, My_xgdanwei.class); 
		 intent0.putExtra("text",mydanwei.getText().toString());  
	     startActivity(intent0);
	 }
	 //手机号（手机app注册的账号和睿立官网能修改手机号）
	 else if(v.getId()==R.id.but_myphonenum)
	 {
		 //if括号内去掉了||appuser.getInstance().getUserinfo().platform.equals("2")
		 if(PassMgr.Isplatform()){
			 Intent intent0 = new Intent(my_xx.this, My_xgphonenum.class); 
			// intent0.putExtra("text",myphonenum.getText());  
		     startActivity(intent0);
		 }else{
			 //but_myphonenum.setBackgroundColor(Color.GRAY);
			 //tv_myphonenum.setTextColor(Color.GRAY);
		 }
		 
	 }
	 else if(v.getId()==R.id.but_mysex)
	 {			 
		 Intent intent0 = new Intent(my_xx.this, my_xgsex.class); 
		 intent0.putExtra("text",mysex.getText().toString());  
	     startActivity(intent0);		 		 
	 }
	 else if(v.getId()==R.id.but_myplace)
	 {
		 Intent intent0 = new Intent(my_xx.this, my_xgplace.class); 
		 startActivity(intent0);
	 }
	 else if(v.getId()==R.id.but_mysign)
	 {
		 Intent intent0 = new Intent(my_xx.this, my_xgsign.class); 
		 intent0.putExtra("text",mysign.getText().toString());  
		 startActivity(intent0);
	 }
	 else if(v.getId()==R.id.loginout)
	 {
		 digshow("是否注销当前用户");
	 }
 }
 	/*
 	 * 点击退出登录的时候出现的对话框
 	 */
	private void digshow(String text) {
		// TODO Auto-generated method stub
		View mView = null;
    	mView = mInflater.inflate(R.layout.dialog_myconfig,null);
		AbDialogUtil.showDialog(mView,R.animator.fragment_top_enter,R.animator.fragment_top_exit,R.animator.fragment_pop_top_enter,R.animator.fragment_pop_top_exit);
		Button leftBtn1 = (Button)mView.findViewById(R.id.left_btn);
		Button rightBtn1 = (Button)mView.findViewById(R.id.right_btn);
		TextView title_choices = (TextView)mView.findViewById(R.id.title_choices);
		TextView choice_one_text= (TextView)mView.findViewById(R.id.choice_one_text);
		leftBtn1.setText("注销");
		rightBtn1.setText("留下");
		//title_choices.setText("");
		choice_one_text.setText(text);
		leftBtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				 Map<String, String> params = new HashMap<String, String>();
				    mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, "NULL"));
				    handler_.SetRequest(new RequestType("",Type.logout),params);
					appuser.getInstance().LogOut();
					PassMgr.IsAuto(false);
					PassMgr.Islogin(false, "", "", "");
					//此方法为异步方法(退出环信后台的登录)
					EMClient.getInstance().logout(true, new EMCallBack() {
					 
						@Override
						public void onSuccess() {
						}					 
						@Override
						public void onProgress(int progress, String status) {
						}					 
						@Override
						public void onError(int code, String message) {
						}
					});
					Intent intent = null;
					intent = new Intent(my_xx.this,mylogin.class);
					/*intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); */
					startActivity(intent);		
					finish();
					AbDialogUtil.removeDialog(my_xx.this);
			}
			
		});
		
		rightBtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(my_xx.this);
			}
			
		});
	}
 @Override
protected void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
  	update_state();
 }
 @Override
	protected void onResume() {
		// TODO Auto-generated method stub
	super.onResume();
	MobclickAgent.onPageStart("my_xx");
	MobclickAgent.onResume(this); 
}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	MobclickAgent.onPageEnd("my_xx");
	MobclickAgent.onPause(this);
}
private void update_state() {
	// TODO Auto-generated method stub
	if( appuser.getInstance().IsLogin())
	{
		//my_but_login.setVisibility(View.GONE);
		
		String name=appuser.getInstance().getUserinfo().nickname.toString();
		String signText=appuser.getInstance().getUserinfo().signText.toString();
		String sex=appuser.getInstance().getUserinfo().sex.toString();
		String city=appuser.getInstance().getUserinfo().city.toString();
		String work=appuser.getInstance().getUserinfo().work.toString();
		String danwei=appuser.getInstance().getUserinfo().danwei.toString();
		String phone=appuser.getInstance().getUserinfo().phone.toString();
		//1.地区
		if(!city.equals("null")&&city.length()>0)
		{
			myplace.setText(city);
		}
		else
			myplace.setText("");
		//2.昵称
		myname.setText(name);
		//3.职业
		if(!work.equals("null")&&work.length()>0)			
			mywork.setText(work);
		else
			mywork.setText("");
		//4.工作单位
		if(!danwei.equals("null")&&danwei.length()>0)			
			mydanwei.setText(danwei);
		else
			mydanwei.setText("");
		//5.手机号
		if(!phone.equals("null")&&phone.length()>0)
			myphonenum.setText(phone);
		else
			myphonenum.setText("");
		//6.个性签名
		if(!signText.equals("null")&&signText.length()>0)
			mysign.setText(signText);
		else
			mysign.setText("");
		//7.性别
		if(sex.equals("男"))
			mysex.setText("男");
		else
			mysex.setText("女");
	}
}
/*
 * 点击头像的时候出现的对话框
 */
private void dialog() {
	// TODO Auto-generated method stub
	mAvatarView = mInflater.inflate(R.layout.choose_avatar, null); 
	Button albumButton = (Button)mAvatarView.findViewById(R.id.choose_album);//本地相册
	Button camButton = (Button)mAvatarView.findViewById(R.id.choose_cam);//相机
	Button cancelButton = (Button)mAvatarView.findViewById(R.id.choose_cancel);//取消
	albumButton.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			AbDialogUtil.removeDialog(my_xx.this);
			// 从相册中去获取Intent.ACTION_GET_CONTENT
			try {
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setType("image/*");
				startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
			} catch (ActivityNotFoundException e) {
				AbToastUtil.showToast(my_xx.this,"没有找到照片");
			}
		}
		
	});
	
	camButton.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			AbDialogUtil.removeDialog(my_xx.this);
			//从照相机获取
			doPickPhotoAction();
		}
		
	});
	
	cancelButton.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			AbDialogUtil.removeDialog(my_xx.this);
		}
		
	});
}
/**
 * 从照相机获取
 */
private void doPickPhotoAction() {
	String status = Environment.getExternalStorageState();
	//判断是否有SD卡,如果有sd卡存入sd卡在说，没有sd卡直接转换为图片
	if (status.equals(Environment.MEDIA_MOUNTED)) {
		doTakePhoto();
	} else {
		AbToastUtil.showToast(my_xx.this,"没有可用的存储卡");
	}
}

/**
 * 拍照获取图片
 */
protected void doTakePhoto() {
	try {
		mFileName = System.currentTimeMillis() + ".jpg";
		mCurrentPhotoFile = new File(PHOTO_DIR, mFileName);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile));
		startActivityForResult(intent, CAMERA_WITH_DATA);
	} catch (Exception e) {
		AbToastUtil.showToast(my_xx.this,"未找到系统相机程序");
	}
}

/**
 * 描述：因为调用了Camera和Gally所以要判断他们各自的返回情况,
 * 他们启动时是这样的startActivityForResult
 */

public File FILE_LOCAL = null;
protected void onActivityResult(int requestCode, int resultCode, Intent mIntent) {
	if (resultCode != RESULT_OK){
		return;
	}
	switch (requestCode) {
		//1.从图库返回来的时候
		case PHOTO_PICKED_WITH_DATA:
			Uri uri = mIntent.getData();
			String currentFilePath = getPath(uri);
			if(!AbStrUtil.isEmpty(currentFilePath)){
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(uri, "image/*");
				//下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
				intent.putExtra("crop", "true");
				// aspectX aspectY 是宽高的比例
				intent.putExtra("aspectX", 1);
				intent.putExtra("aspectY", 1);
				// outputX outputY 是裁剪图片宽高
				intent.putExtra("outputX", 300);
				intent.putExtra("outputY", 300);
				intent.putExtra("return-data", true);
				startActivityForResult(intent, 3);
				
	        }else{
	        	AbToastUtil.showToast(my_xx.this,"未在存储卡中找到这个文件");
	        }
			break;
		//2.从相机返回来的时候
		case CAMERA_WITH_DATA:
			AbLogUtil.d(my_xxxg.class, "将要进行裁剪的图片的路径是 = " + mCurrentPhotoFile.getPath());
			String currentFilePath2 = mCurrentPhotoFile.getPath();
			
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(Uri.fromFile(mCurrentPhotoFile), "image/*");
			//下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
			intent.putExtra("crop", "true");
			// aspectX aspectY 是宽高的比例
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			// outputX outputY 是裁剪图片宽高
			intent.putExtra("outputX", 300);
			intent.putExtra("outputY", 300);
			intent.putExtra("return-data", true);
			startActivityForResult(intent, 3);			
			break;
		case 3:
			if(mIntent != null){
				Bundle extras = mIntent.getExtras();
				if (extras != null) {
					Bitmap photo = extras.getParcelable("data");
					String path = saveToLocal(photo);
					AbLogUtil.d(my_xxxg.class, "裁剪后得到的图片的路径是 = " + path);
			    	picPath=path;
			    	dialog_.show(); 
			    	new Thread(new MyThread()).start();
				}
			}
			break;
	}
}
/**
 * Save to local.
 *
 * @param bitmap the bitmap
 * @return the string
 */
public String saveToLocal(Bitmap bitmap){
	//需要裁剪后保存为新图片
    String mFileName = System.currentTimeMillis() + ".jpg";
	String path = FILE_LOCAL+File.separator+mFileName;
	try{
		FileOutputStream fos = new FileOutputStream(path);
		bitmap.compress(CompressFormat.JPEG, 100, fos);
		fos.flush();
		fos.close();
	} catch (Exception e){
		e.printStackTrace();
		return null;
	}
	return path;
}
/**
 * 从相册得到的url转换为SD卡中图片路径
 */
public String getPath(Uri uri) {
	if(AbStrUtil.isEmpty(uri.getAuthority())){
		return null;
	}
	String[] projection = { MediaStore.Images.Media.DATA };
	Cursor cursor = managedQuery(uri, projection, null, null, null);
	int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	cursor.moveToFirst();
	String path = cursor.getString(column_index);
	return path;
}
LinearLayout layout;
 PopupWindow popupWindow;
 private  Handler handler = new Handler() {
	    // 在Handler中获取消息，重写handleMessage()方法
	    @Override
	    public void handleMessage(Message msg) {            
	        // 判断消息码是否为1
	        if(msg.what==0){	       	
	    		JSONObject imgback;	    		
				try {
					imgback = new JSONObject(msg.obj.toString()+"");
					Map<String, String> params = new HashMap<String, String>();					  
					params.put("bigImg",imgback.getJSONObject("data").getString("rootName"));
					params.put("medImg",imgback.getJSONObject("data").getString("medName"));
					params.put("smallImg",imgback.getJSONObject("data").getString("smallName"));					  
					handler_2.SetRequest(new RequestType("4",Type.editInfo),params);					 
					appuser.getInstance().getUserinfo().bigImg=imgback.getJSONObject("data").getString("rootName");
					setimg();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				 Log.d("web", "url -> " + msg.obj.toString());
	        }
	    }
	};
	public class MyThread implements Runnable {

	    @Override
	    public void run() {
	   	 try
			{	   		    
			/*	if(time<mPhotoList.size()-1)
				picPath=mPhotoList.get(time);*/
				String requestURL=RequestType.getimgpostUrl_();
	    		// TODO 信息校验通过，提交到服务器进行注册
				String	HTTPS=requestURL;
				//HttpPost request = new HttpPost("http://39.191.81.32/azc.aspx");
				HttpPost request = new HttpPost(HTTPS);
				HttpClient httpClient = new DefaultHttpClient(); 
				File file=new File(picPath);				
		        FileEntity entity = new FileEntity(file,"binary/octet-stream"); 
		        
		        HttpResponse response; 
		        try { 
		            request.setEntity(entity); 
		            entity.setContentEncoding("binary/octet-stream"); 
		            response = httpClient.execute(request); 
	             
					//如果返回状态为200，获得返回的结果  
					if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){   
					//图片上传成功             
					} 
				} 
				catch(Exception e){  
				} 
				request.setEntity(entity);
				HttpResponse httpResponse = new DefaultHttpClient().execute(request);				
				String retSrc = EntityUtils.toString(httpResponse.getEntity());				
				Message.obtain(handler,0,retSrc).sendToTarget();			
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
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
                if (ExampleUtil.isConnected(my_xx.this)) {
                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                } else {
                	//Log.i(TAG, "No network");
                }
                break;
            
            default:
                logs = "Failed with errorCode = " + code;
               // Log.e(TAG, logs);
            }
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
	                JPushInterface.setAliasAndTags(my_xx.this, (String) msg.obj, null, mAliasCallback);
	                break;
	                
	         
	            default:
	               // Log.i(TAG, "Unhandled msg - " + msg.what);
	            }
	        }
	    };
}
