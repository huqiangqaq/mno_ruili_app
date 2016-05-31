package mno.ruili_app.my;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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

import mno.ruili_app.R;
import mno.ruili_app.ct.CropImageActivity;
import mno.ruili_app.ct.LoadingDialog;
import mno.ruili_app.ct.MessageBox;
import mno_ruili_app.adapter.ImageShowAdapter;
import mno_ruili_app.net.NetUtil;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.ab.activity.AbActivity;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbFileUtil;
import com.ab.util.AbLogUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.progress.AbHorizontalProgressBar;
import com.ab.view.titlebar.AbTitleBar;

public class my_xxxg extends AbActivity {
	private GridView mGridView = null;
	private ImageShowAdapter mImagePathAdapter = null;
	private ArrayList<String> mPhotoList = null;
	private int selectIndex = 0;
	private int camIndex = 0;
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
	
	/* ProgressBar进度控制 */
	private AbHorizontalProgressBar mAbProgressBar;
	/* 最大100 */
	private int max = 100;	
	private int progress = 0;
	private TextView numberText, maxText;
	private DialogFragment  mAlertDialog  = null;
	private AbHttpUtil mAbHttpUtil = null;
	 Dialog  dialog_;
	 String picPath="";
	 String allpath="";int time=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_my_xxxg);
		dialog_ = LoadingDialog.createLoadingDialog(my_xxxg.this, "正在加载中...");
		mPhotoList = new ArrayList<String>();
		
		//获取Http工具类
        mAbHttpUtil = AbHttpUtil.getInstance(this);
		
        //默认
		mPhotoList.add(String.valueOf(R.drawable.cam_photo));
		
		mGridView = (GridView)findViewById(R.id.myGrid);
		mImagePathAdapter = new ImageShowAdapter(this, mPhotoList,116,116);
		mGridView.setAdapter(mImagePathAdapter);
		
		//初始化图片保存路径
	    String photo_dir = AbFileUtil.getImageDownloadDir(this);
	    if(AbStrUtil.isEmpty(photo_dir)){
	    	AbToastUtil.showToast(my_xxxg.this,"存储卡不存在");
	    }else{
	    	PHOTO_DIR = new File(photo_dir);
	    }
	    
	    Button addBtn = (Button)findViewById(R.id.addBtn);
	    
	    mGridView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectIndex = position;
				if(selectIndex == camIndex){
					mAvatarView = mInflater.inflate(R.layout.choose_avatar, null); 
					Button albumButton = (Button)mAvatarView.findViewById(R.id.choose_album);
					Button camButton = (Button)mAvatarView.findViewById(R.id.choose_cam);
					Button cancelButton = (Button)mAvatarView.findViewById(R.id.choose_cancel);
					albumButton.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							AbDialogUtil.removeDialog(my_xxxg.this);
							// 从相册中去获取Intent.ACTION_GET_CONTENT
							try {
								Intent intent = new Intent(Intent.ACTION_PICK, null);
								intent.setType("image/*");
								startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
							} catch (ActivityNotFoundException e) {
								AbToastUtil.showToast(my_xxxg.this,"没有找到照片");
							}
						}
						
					});
					
					camButton.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							AbDialogUtil.removeDialog(my_xxxg.this);
							doPickPhotoAction();
						}
						
					});
					
					cancelButton.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							AbDialogUtil.removeDialog(my_xxxg.this);
						}
						
					});
					AbDialogUtil.showDialog(mAvatarView,Gravity.BOTTOM);
				}else{
					for(int i=0;i<mImagePathAdapter.getCount();i++){
						ImageShowAdapter.ViewHolder mViewHolder = (ImageShowAdapter.ViewHolder)mGridView.getChildAt(i).getTag();
						if(mViewHolder!=null){
							mViewHolder.mImageView2.setBackgroundDrawable(null);
						}
					}
	
					ImageShowAdapter.ViewHolder mViewHolder = (ImageShowAdapter.ViewHolder)view.getTag();
					mViewHolder.mImageView2.setBackgroundResource(R.drawable.photo_select);
				}
			}
			
		});
		
		addBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				uploadFile(mPhotoList);
			}
		});
		
	}
	
	private void initTitleRightLayout() {
		
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
			AbToastUtil.showToast(my_xxxg.this,"没有可用的存储卡");
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
			AbToastUtil.showToast(my_xxxg.this,"未找到系统相机程序");
		}
	}
	
	/**
	 * 描述：因为调用了Camera和Gally所以要判断他们各自的返回情况,
	 * 他们启动时是这样的startActivityForResult
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent mIntent) {
		if (resultCode != RESULT_OK){
			return;
		}
		switch (requestCode) {
			case PHOTO_PICKED_WITH_DATA:
				Uri uri = mIntent.getData();
				String currentFilePath = getPath(uri);
				if(!AbStrUtil.isEmpty(currentFilePath)){
					Intent intent1 = new Intent(this, CropImageActivity.class);
					intent1.putExtra("PATH", currentFilePath);
					startActivityForResult(intent1, CAMERA_CROP_DATA);
		        }else{
		        	AbToastUtil.showToast(my_xxxg.this,"未在存储卡中找到这个文件");
		        }
				break;
			case CAMERA_WITH_DATA:
				AbLogUtil.d(my_xxxg.class, "将要进行裁剪的图片的路径是 = " + mCurrentPhotoFile.getPath());
				String currentFilePath2 = mCurrentPhotoFile.getPath();
				Intent intent2 = new Intent(this, CropImageActivity.class);
				intent2.putExtra("PATH",currentFilePath2);
				startActivityForResult(intent2,CAMERA_CROP_DATA);
				break;
			case CAMERA_CROP_DATA:
				String path = mIntent.getStringExtra("PATH");
		    	AbLogUtil.d(my_xxxg.class, "裁剪后得到的图片的路径是 = " + path);
		    	mImagePathAdapter.addItem(mImagePathAdapter.getCount()-1,path);
		    	picPath=path;
		    	new Thread(new MyThread()).start();
		     	camIndex++;
				break;
		}
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
	
	public void uploadFile(List<String> list){
		//已经在后台上传

		String url = "http://www.yuetingfengsong.com:8087/Api3/onlyfileupload";
		try {
			
/*			
			//params.put("num",list.size()-1+"");
			
			for(int i=0;i<list.size()-1;i++){
				String path = list.get(i);
				picPath=null;
				picPath=path;
				if(!picPath.equals(""))
					dialog_.show();  
				new Thread(new MyThread()).start();
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	    private  Handler handler = new Handler() {
	    	         // 在Handler中获取消息，重写handleMessage()方法
	    	         @Override
	    	         public void handleMessage(Message msg) {            
	    	             // 判断消息码是否为1
	    	             if(msg.what==0){
	    	            	 dialog_.cancel();
	    	 	    		JSONObject imgback;
	    	 	    		
	    	 				try {
	    	 					imgback = new JSONObject(msg.obj.toString()+"");
	    	 					time++;
	    	 					if(time<mPhotoList.size())
	    	 						{
	    	 						
	    	 						allpath+=imgback.getJSONObject("data").getString("rootName")+",";
	    	 						 Log.d("web", "allurl -> " + allpath);
	    	 						}
	    	 					
	    	 					
	    	 					Toast.makeText(my_xxxg.this,imgback.getJSONObject("data").getString("rootName")+"", 1).show();
	    	 					
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
	    	 				String requestURL="http://www.yuetingfengsong.com:8087/Api3/onlyfileupload/";
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
	    	 			HttpResponse httpResponse = new DefaultHttpClient()
	    	 				.execute(request);
	    	 			
	    	 			String retSrc = EntityUtils.toString(httpResponse
	    	 					.getEntity());
	    	 			
	    	 			Message.obtain(handler,0,retSrc).sendToTarget();
	    	 			
	    	 			} catch (UnsupportedEncodingException e) {
	    	 				// TODO Auto-generated catch block
	    	 				e.printStackTrace();
	    	 			}
	    	 		 catch (ClientProtocolException e) {
	    	 			// TODO Auto-generated catch block
	    	 			e.printStackTrace();
	    	 		} catch (IOException e) {
	    	 			// TODO Auto-generated catch block
	    	 			e.printStackTrace();
	    	 		}
	    	        }
	    	     }
	    }
	    

