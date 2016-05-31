package mno_ruili_app.nei;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

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

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbFileUtil;
import com.ab.util.AbLogUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.ioc.AbIocView;
import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.ct.BitmapCompressor;
import mno.ruili_app.ct.CropImageActivity;
import mno.ruili_app.ct.LoadingDialog;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.ct.TimeButton;
import mno.ruili_app.ct.imgcut;
import mno.ruili_app.main.Main;
import mno.ruili_app.my.my_xx;
import mno.ruili_app.my.my_xxxg;
import mno.ruili_app.my.mylogin;
import mno.ruili_app.my.my_xxxg.MyThread;
import mno_ruili_app.adapter.ImageShowAdapter;
import mno_ruili_app.adapter.tv;
import mno_ruili_app.net.RequestType;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class nei_bj  extends AbActivity{
	ImageView nei_bj_addimg;
	LinearLayout nei_bj_addview;
	EditText nei_bj_edi;
	TextView tv_score;
	 
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
		private static  File PHOTO_DIR = null;
		// 照相机拍照得到的图片
		private File mCurrentPhotoFile;
		private String mFileName;
		 Dialog  dialog_;
		 String picPath="";
		 static String allpath="";int time=0;
		 String Score="";
		 InputMethodManager imm;
		 boolean del=true;
		 public File FILE_LOCAL = null;
		 //@AbIocView(id = R.id.mBtn,click="btnClick")Button button;
		 @AbIocView(id = R.id.nei_bj_img)ImageView nei_bj_img;
		 @AbIocView(id = R.id.nei_bj_jf)ImageView nei_bj_jf;
		 @AbIocView(id = R.id.but_0)TextView but_0;
		 @AbIocView(id = R.id.but_5)TextView but_5;
		 @AbIocView(id = R.id.but_10)TextView but_10;
		 @AbIocView(id = R.id.but_20)TextView but_20;
		 @AbIocView(id = R.id.but_30)TextView but_30;
		 @AbIocView(id = R.id.but_50)TextView but_50;
		 @AbIocView(id = R.id.but_70)TextView but_70;
		 @AbIocView(id = R.id.but_100)TextView but_100;
		 @AbIocView(id = R.id.relativeLayout2)RelativeLayout relativeLayout2;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.nei_bj);
		FILE_LOCAL = new File(AbFileUtil.getImageDownloadDir(this));
		if(!FILE_LOCAL.exists()){
			FILE_LOCAL.mkdirs();
		}
		init();
		
		if(Constant.isbj.equals("0")){
		nei_bj_edi.setText(Constant.bj_nr);
		Drawable drawable=this.getResources().getDrawable(R.drawable.radius_blue);
		if(Constant.bj_jf.equals("0"))
		{
			Score="0";
			//TimeButton.this.setBackgroundDrawable(drawable);
			but_0.setBackgroundDrawable(drawable);
			but_0.setTextColor(android.graphics.Color.parseColor("#ffffff"));;
		}
		else if(Constant.bj_jf.equals("5"))
		{
			Score="5";
			//TimeButton.this.setBackgroundDrawable(drawable);
			but_5.setBackgroundDrawable(drawable);
			but_5.setTextColor(android.graphics.Color.parseColor("#ffffff"));;
		}
		else if(Constant.bj_jf.equals("10"))
		{
			Score="10";
			//TimeButton.this.setBackgroundDrawable(drawable);
			but_10.setBackgroundDrawable(drawable);
			but_10.setTextColor(android.graphics.Color.parseColor("#ffffff"));;
		}
		else if(Constant.bj_jf.equals("20"))
		{
			Score="20";
			//TimeButton.this.setBackgroundDrawable(drawable);
			but_20.setBackgroundDrawable(drawable);
			but_20.setTextColor(android.graphics.Color.parseColor("#ffffff"));;
		}
		else if(Constant.bj_jf.equals("30"))
		{
			Score="30";
			//TimeButton.this.setBackgroundDrawable(drawable);
			but_30.setBackgroundDrawable(drawable);
			but_30.setTextColor(android.graphics.Color.parseColor("#ffffff"));;
		}
		else if(Constant.bj_jf.equals("30"))
		{
			Score="30";
			//TimeButton.this.setBackgroundDrawable(drawable);
			but_30.setBackgroundDrawable(drawable);
			but_30.setTextColor(android.graphics.Color.parseColor("#ffffff"));;
		}
		else if(Constant.bj_jf.equals("50"))
		{
			Score="50";
			//TimeButton.this.setBackgroundDrawable(drawable);
			but_50.setBackgroundDrawable(drawable);
			but_50.setTextColor(android.graphics.Color.parseColor("#ffffff"));;
		}
		else if(Constant.bj_jf.equals("70"))
		{
			Score="70";
			//TimeButton.this.setBackgroundDrawable(drawable);
			but_70.setBackgroundDrawable(drawable);
			but_70.setTextColor(android.graphics.Color.parseColor("#ffffff"));;
		}
		else if(Constant.bj_jf.equals("100"))
		{
			Score="100";
			//TimeButton.this.setBackgroundDrawable(drawable);
			but_100.setBackgroundDrawable(drawable);
			but_100.setTextColor(android.graphics.Color.parseColor("#ffffff"));;
		}
		
		String[] pic = null;
		String pics=Constant.bj_tp;
		try {
			pic  = pics.split(",");
			allpath="";
			 if(pic.length>=1)
			{
				
				String a=pic[0];
				if(a.length()>0)
				{
					
					mImagePathAdapter.addItem(mImagePathAdapter.getCount()-1,RequestType.getWebUrl_(a));
					camIndex++;
					allpath+=pic[0]+",";
				}
				
			}
			 if(pic.length>=2)
			 {
				 
				 mImagePathAdapter.addItem(mImagePathAdapter.getCount()-1,RequestType.getWebUrl_(pic[1]));
				 allpath+=pic[1]+",";
				 camIndex++;
			 }
			 if(pic.length>=3)
			 {
				 String a=pic[2];
				 mImagePathAdapter.addItem(mImagePathAdapter.getCount()-1,RequestType.getWebUrl_(a));
				 allpath+=pic[2]+",";
				 camIndex++;
			 }
			 
			 if(pic.length>=4)
			 {
				 String a=pic[3];
				 allpath+=pic[3]+",";
				 mImagePathAdapter.addItem(mImagePathAdapter.getCount()-1,RequestType.getWebUrl_(a));
				 camIndex++;
			 }
		}catch (Exception e){
			if(pics.length()>3)
			{
				
			mImagePathAdapter.addItem(mImagePathAdapter.getCount()-1,RequestType.getWebUrl_(pics));
			allpath+=pics+",";
			camIndex++;
			}
		}
		
		Constant.bj_jf="";
		Constant.bj_tp="";
		Constant.bj_nr="";
		}
		
		
	}
	
private void init() {
		// TODO Auto-generated method stub
	 imm = (InputMethodManager) getSystemService(nei_bj.INPUT_METHOD_SERVICE); 
	 dialog_ = LoadingDialog.createLoadingDialog(nei_bj.this, "正在上传中...");
	 nei_bj_addimg=(ImageView)this.findViewById(R.id.nei_bj_addimg);
	 nei_bj_addview=(LinearLayout)this.findViewById(R.id.nei_bj_addview);
     nei_bj_edi=(EditText)this.findViewById(R.id.nei_bj_edi);
     tv_score=(TextView) this.findViewById(R.id.tv_score);
	 LayoutParams lp;        
     lp=nei_bj_edi.getLayoutParams();
     //lp.width=100;
     lp.height=Constant.displayHeight/2; 
     nei_bj_edi.setLayoutParams(lp);
     //正文内容的点击事件
     nei_bj_edi.setOnClickListener(new View.OnClickListener() {
         
         @Override
         public void onClick(View v) {
        	 relativeLayout2.setVisibility(View.GONE);//清空输入框内容
        	 if(relativeLayout2.getVisibility()==0)
        	 {
        		 relativeLayout2.setVisibility(View.GONE);
        	 }
         }
     });
     nei_bj_edi.setOnFocusChangeListener(new android.view.View.
    			OnFocusChangeListener() {
    		@Override
    		public void onFocusChange(View v, boolean hasFocus) {
    			if (hasFocus) {
    				// 此处为得到焦点时的处理内容
    				 relativeLayout2.setVisibility(View.GONE);
    			} else {
    				// 此处为失去焦点时的处理内容
    			}
    		}
    	});
   /*//绑定焦点事件
     inputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {   
         @Override
         public void onFocusChange(View v, boolean hasFocus) {
             // TODO Auto-generated method stub
             if(hasFocus){//如果组件获得焦点
                 showTextView.setText("组件获得了焦点");
                  
             }else{
                 showTextView.setText("组件失去了焦点");
             }
         }
     });*/
    
     mPhotoList = new ArrayList<String>();
     
 	 mPhotoList.add(String.valueOf(R.drawable.nei_bj_addimg));
	
	 mGridView = (GridView)findViewById(R.id.myGrid);
	 mGridView.setGravity(Gravity.CENTER);
	 mGridView.setHorizontalSpacing(10);
	 mGridView.setPadding(0, 0, 0, 0);
	 mGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
	 mImagePathAdapter = new ImageShowAdapter(this, mPhotoList,150,150);
	 ViewGroup.LayoutParams layoutParams = mGridView.getLayoutParams();  
     layoutParams.width =Constant.displayWidth/4*6;//总行高+每行的间距  
     mGridView.setLayoutParams(layoutParams); 
		
	 mGridView.setAdapter(mImagePathAdapter);
	//初始化图片保存路径
	    String photo_dir = AbFileUtil.getImageDownloadDir(this);
	    if(AbStrUtil.isEmpty(photo_dir)){
	    	AbToastUtil.showToast(nei_bj.this,"存储卡不存在");
	    }else{
	    	PHOTO_DIR = new File(photo_dir);
	    }
	   
	    mGridView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				
				 selectIndex = position;
				 ImageView mImageView2 = ((ImageView) view.findViewById(R.id.imageView2)) ;
				 mImageView2.setOnClickListener(new View.OnClickListener() {
			         
			         @Override
			         public void onClick(View v) {
			        	 if(allpath.length()<=0)
			        	 {
			        	 MessageBox.Show(getApplicationContext(), "0");
			        	 return;
			        	 }
			        	
			        	 mImagePathAdapter.removeItem(position); 
			        	 mImagePathAdapter. notifyDataSetChanged();
			        	 String[] pic = null;
			        	 String imgpath=allpath;
			        	 pic  = imgpath.substring(0, allpath.length()-1).split(",");
			        	 imgpath="";
			        	 allpath="";
			        	 for (int i = 0; i <pic.length; i++) { 
				              
				              if(i==position)
				              {
				            	  
				              }
				              else
				              {
				            	  imgpath=imgpath+pic[i]+",";
				              }
				              
				            
				            } 		
			        	 allpath=imgpath;
			        	 camIndex--;
			        	 
			        	 if(camIndex==0)
			        	 {
			        		mGridView.setVisibility(View.GONE);
					    	nei_bj_addimg.setVisibility(View.VISIBLE );}
			        	 
			         
			     }
				 });
				if(selectIndex == camIndex&&camIndex<4){
					dialog();
					AbDialogUtil.showDialog(mAvatarView,Gravity.BOTTOM);
				}
				else if(selectIndex == camIndex&&camIndex>3)
				{
					MessageBox.Show(getApplicationContext(), "不能再多了");
				}
				else{
					
					for(int i=0;i<mImagePathAdapter.getCount();i++){
						ImageShowAdapter.ViewHolder mViewHolder = (ImageShowAdapter.ViewHolder)mGridView.getChildAt(i).getTag();
						if(mViewHolder!=null){
							mViewHolder.mImageView2.setBackgroundDrawable(null);
						}
					}
					/*if(del)
					{
						//mPhotoList.remove(camIndex);
						mPhotoList.set(camIndex, (String.valueOf(R.drawable.nei_bj_addimg)));
						mImagePathAdapter.notifyDataSetChanged();
						del=false;
					}else
					{
						mPhotoList.set(camIndex, (String.valueOf(R.drawable.nei_bj_delimg)));
						del = true;
					    //ImageShowAdapter.ViewHolder mViewHolder = (ImageShowAdapter.ViewHolder)view.getTag();
					    //mViewHolder.mImageView2.setBackgroundResource(R.drawable.photo_select);
					    //mImagePathAdapter.notifyDataSetChanged();
					}*/
				}
			}
			
		});
	}
private void dialog() {
	// TODO Auto-generated method stub
	mAvatarView = mInflater.inflate(R.layout.choose_avatar, null); 
	Button albumButton = (Button)mAvatarView.findViewById(R.id.choose_album);
	Button camButton = (Button)mAvatarView.findViewById(R.id.choose_cam);
	Button cancelButton = (Button)mAvatarView.findViewById(R.id.choose_cancel);
	albumButton.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			AbDialogUtil.removeDialog(nei_bj.this);
			// 从相册中去获取Intent.ACTION_GET_CONTENT
			try {
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setType("image/*");
				startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
			} catch (ActivityNotFoundException e) {
				AbToastUtil.showToast(nei_bj.this,"没有找到照片");
			}
		}
		
	});
	
	camButton.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			AbDialogUtil.removeDialog(nei_bj.this);
			doPickPhotoAction();
		}
		
	});
	
	cancelButton.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			AbDialogUtil.removeDialog(nei_bj.this);
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
		AbToastUtil.showToast(nei_bj.this,"没有可用的存储卡");
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
		AbToastUtil.showToast(nei_bj.this,"未找到系统相机程序");
	}
}

/**
 * 描述：因为调用了Camera和Gally所以要判断他们各自的返回情况,
 * 他们启动时是这样的startActivityForResult
 PHOTO_DIR+"temp.jpg"Uri.parse(IMAGE_FILE_LOCATION)*/
private static String IMAGE_FILE_LOCATION = "";//temp file 

Uri uriback =null ;//The Uri to store the big bitmap 
File temp=null;
//Uri uriback=null; 
protected void onActivityResult(int requestCode, int resultCode, Intent mIntent) {
	if (resultCode != RESULT_OK){
		return;
	}
	switch (requestCode) {
		case PHOTO_PICKED_WITH_DATA:
			    temp= new File(PHOTO_DIR, System.currentTimeMillis() + ".jpg");
			    uriback= Uri.fromFile(temp);
			Uri uri = mIntent.getData();
			String currentFilePath = getPath(uri);
			if(!AbStrUtil.isEmpty(currentFilePath)){
				//uri
				
		    	
		    	//Bitmap bm = checkimg(currentFilePath);
		    	//picPath=saveToLocal(bm);
				picPath=saveToLocal(imgcut.getimage(nei_bj.this,currentFilePath));
		    	mImagePathAdapter.addItem(mImagePathAdapter.getCount()-1,picPath);
		    	mGridView.setVisibility(View.VISIBLE);
		    	nei_bj_addimg.setVisibility(View.GONE );
		    	
		    	dialog_.show(); 
		    	new Thread(new MyThread()).start();
		     	camIndex++;
	        }else{
	        	AbToastUtil.showToast(nei_bj.this,"未在存储卡中找到这个文件");
	        }
			break;
		case CAMERA_WITH_DATA:
			AbLogUtil.d(my_xxxg.class, "将要进行裁剪的图片的路径是 = " + mCurrentPhotoFile.getPath());
			String currentFilePath2 = mCurrentPhotoFile.getPath();
			//Uri.fromFile(mCurrentPhotoFile)
	
			//Bitmap bm = checkimg(currentFilePath2);
	    	picPath=saveToLocal(imgcut.getimage(nei_bj.this,currentFilePath2));
			mImagePathAdapter.addItem(mImagePathAdapter.getCount()-1,picPath);
	    	mGridView.setVisibility(View.VISIBLE);
	    	nei_bj_addimg.setVisibility(View.GONE );
	    
	    	dialog_.show(); 
	    	new Thread(new MyThread()).start();
	     	camIndex++;
			
			break;
		case 3:
			if(mIntent != null){
			
					String path =temp.getPath();

			    	mImagePathAdapter.addItem(mImagePathAdapter.getCount()-1,path);
			    	mGridView.setVisibility(View.VISIBLE);
			    	nei_bj_addimg.setVisibility(View.GONE );
			    	picPath=path;
			    	dialog_.show(); 
			    	new Thread(new MyThread()).start();
			     	camIndex++;
			}
			//String path = mIntent.getStringExtra("PATH");
	    	//AbLogUtil.d(my_xxxg.class, "裁剪后得到的图片的路径是 = " + path);
	    	
			break;
			
			case 4:
				if(mIntent != null){
				
						String path =mCurrentPhotoFile.getPath();

				    	mImagePathAdapter.addItem(mImagePathAdapter.getCount()-1,path);
				    	mGridView.setVisibility(View.VISIBLE);
				    	nei_bj_addimg.setVisibility(View.GONE );
				    	picPath=path;
				    	dialog_.show(); 
				    	new Thread(new MyThread()).start();
				     	camIndex++;
					
				//String path = mIntent.getStringExtra("PATH");
		    	//AbLogUtil.d(my_xxxg.class, "裁剪后得到的图片的路径是 = " + path);
		    	
			}
				break;
	}
}
	private Bitmap checkimg(String filePath) {
	// TODO Auto-generated method stub
		  final BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;
	        BitmapFactory.decodeFile(filePath, options);

	        // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, 480, 800);

	        // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;

	    return BitmapFactory.decodeFile(filePath, options);


}
	
	public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {
	             final int heightRatio = Math.round((float) height/ (float) reqHeight);
	             final int widthRatio = Math.round((float) width / (float) reqWidth);
	             inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }
	        return inSampleSize;
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
			int options = 100;  
			FileOutputStream fos = new FileOutputStream(path);
			bitmap.compress(CompressFormat.JPEG, options, fos);
			fos.flush();
			fos.close();
		} catch (Exception e){
			
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

public void onclick(View v)
	{
	Drawable bj_img = getResources().getDrawable(R.drawable.nei_bj_img);
	Drawable bj_img_ = getResources().getDrawable(R.drawable.nei_bj_img_);
	Drawable bj_jf = getResources().getDrawable(R.drawable.nei_bj_jf);
	Drawable bj_jf_ = getResources().getDrawable(R.drawable.nei_bj_jf_);
	//下一步
	if(v.getId()==R.id.but_wd_next)
	{
		
		if(nei_bj_edi.getText().toString().length()>0)
		{
			if(Score.equals(""))
			{
				MessageBox.Show(getApplicationContext(), "请选择悬赏分值");
				return;
			}
			Intent intent = new Intent(nei_bj.this,nei_bj_tag.class);
			intent.putExtra("text",nei_bj_edi.getText().toString());
			intent.putExtra("img",allpath);
			intent.putExtra("score",Score);
		
			nei_bj.this.startActivity(intent);
		}
		else
		{
			MessageBox.Show(getApplicationContext(), "请输入问题描述");
		}
	}
	//悬赏分的图片
	else if(v.getId()==R.id.nei_bj_jf)
		
	{
		imm.hideSoftInputFromWindow(nei_bj_edi.getWindowToken(), 0);
		nei_bj_edi.setEnabled(false);
		new Handler().postDelayed(new Runnable(){    
		    public void run() {    
		    //execute the task    
		    	Message.obtain(handler,2,"").sendToTarget();
		    }    
		 }, 300);   
		
		
	}
	//图片选择
	else if(v.getId()==R.id.nei_bj_img)
	{
		imm.hideSoftInputFromWindow(nei_bj_edi.getWindowToken(), 0);
		nei_bj_edi.setEnabled(false);
		new Handler().postDelayed(new Runnable(){    
		    public void run() {    
		    	
		    //execute the task    
		    	Message.obtain(handler,1,"").sendToTarget();
		    }    
		 }, 300);   
		
		
	}
	else if(v.getId()==R.id.nei_bj_addimg)
	{
		dialog();
		AbDialogUtil.showDialog(mAvatarView,Gravity.BOTTOM);
		
	}
	else if(v.getId()==R.id.but_0)
	{
		changecolor();
		Drawable drawable=this.getResources().getDrawable(R.drawable.radius_blue);
		//TimeButton.this.setBackgroundDrawable(drawable);
		but_0.setBackgroundDrawable(drawable);
		but_0.setTextColor(android.graphics.Color.parseColor("#ffffff"));;
		Score="0";
		tv_score.setText(Score);
		new Handler().postDelayed(new Runnable(){    
		    public void run() {    
		    //execute the task    
		    	Message.obtain(handler,3,"").sendToTarget();
		    }    
		 }, 300);  
	}
	else if(v.getId()==R.id.but_5)
	{
		changecolor();
		Drawable drawable=this.getResources().getDrawable(R.drawable.radius_blue);
		//TimeButton.this.setBackgroundDrawable(drawable);
		but_5.setBackgroundDrawable(drawable);
		but_5.setTextColor(android.graphics.Color.parseColor("#ffffff"));
		Score="5";
		tv_score.setText(Score);
		new Handler().postDelayed(new Runnable(){    
		    public void run() {    
		    //execute the task    
		    	Message.obtain(handler,3,"").sendToTarget();
		    }    
		 }, 300); 
	}
	else if(v.getId()==R.id.but_10)
	{
		changecolor();
		Drawable drawable=this.getResources().getDrawable(R.drawable.radius_blue);
		//TimeButton.this.setBackgroundDrawable(drawable);
		but_10.setBackgroundDrawable(drawable);
		but_10.setTextColor(android.graphics.Color.parseColor("#ffffff"));;
		Score="10";
		tv_score.setText(Score);
		new Handler().postDelayed(new Runnable(){    
		    public void run() {    
		    //execute the task    
		    	Message.obtain(handler,3,"").sendToTarget();
		    }    
		 }, 300); 
	}
	else if(v.getId()==R.id.but_20)
	{
		changecolor();
		Drawable drawable=this.getResources().getDrawable(R.drawable.radius_blue);
		//TimeButton.this.setBackgroundDrawable(drawable);
		but_20.setBackgroundDrawable(drawable);
		but_20.setTextColor(android.graphics.Color.parseColor("#ffffff"));;
		Score="20";
		tv_score.setText(Score);
		new Handler().postDelayed(new Runnable(){    
		    public void run() {    
		    //execute the task    
		    	Message.obtain(handler,3,"").sendToTarget();
		    }    
		 }, 300); 
	}
	else if(v.getId()==R.id.but_30)
	{
		changecolor();
		Drawable drawable=this.getResources().getDrawable(R.drawable.radius_blue);
		//TimeButton.this.setBackgroundDrawable(drawable);
		but_30.setBackgroundDrawable(drawable);
		but_30.setTextColor(android.graphics.Color.parseColor("#ffffff"));;
		Score="30";
		tv_score.setText(Score);
		new Handler().postDelayed(new Runnable(){    
		    public void run() {    
		    //execute the task    
		    	Message.obtain(handler,3,"").sendToTarget();
		    }    
		 }, 300); 
	}
	else if(v.getId()==R.id.but_50)
	{
		changecolor();
		Drawable drawable=this.getResources().getDrawable(R.drawable.radius_blue);
		//TimeButton.this.setBackgroundDrawable(drawable);
		but_50.setBackgroundDrawable(drawable);
		but_50.setTextColor(android.graphics.Color.parseColor("#ffffff"));;
		Score="50";
		tv_score.setText(Score);
		new Handler().postDelayed(new Runnable(){    
		    public void run() {    
		    //execute the task    
		    	Message.obtain(handler,3,"").sendToTarget();
		    }    
		 }, 300); 
	}
	else if(v.getId()==R.id.but_70)
	{
		changecolor();
		Drawable drawable=this.getResources().getDrawable(R.drawable.radius_blue);
		//TimeButton.this.setBackgroundDrawable(drawable);
		but_70.setBackgroundDrawable(drawable);
		but_70.setTextColor(android.graphics.Color.parseColor("#ffffff"));;
		Score="70";
		tv_score.setText(Score);
		new Handler().postDelayed(new Runnable(){    
		    public void run() {    
		    //execute the task    
		    	Message.obtain(handler,3,"").sendToTarget();
		    }    
		 }, 300); 
	}
	else if(v.getId()==R.id.but_100)
	{
		changecolor();
		Drawable drawable=this.getResources().getDrawable(R.drawable.radius_blue);
		//TimeButton.this.setBackgroundDrawable(drawable);
		but_100.setBackgroundDrawable(drawable);
		but_100.setTextColor(android.graphics.Color.parseColor("#ffffff"));;
		Score="100";
		tv_score.setText(Score);
		new Handler().postDelayed(new Runnable(){    
		    public void run() {    
		    //execute the task    
		    	Message.obtain(handler,3,"").sendToTarget();
		    }    
		 }, 300); 
	}
	 else if(v.getId()==R.id.back)
	 {
		 digshow();
	 }
	 
	
	}
private void changecolor() {
	// TODO Auto-generated method stub
	Drawable drawable =this.getResources().getDrawable(R.drawable.view_line);
	but_0.setBackgroundDrawable(drawable);
	but_5.setBackgroundDrawable(drawable);
	but_10.setBackgroundDrawable(drawable);
	but_20.setBackgroundDrawable(drawable);
	but_50.setBackgroundDrawable(drawable);
	but_70.setBackgroundDrawable(drawable);
	but_30.setBackgroundDrawable(drawable);
	but_100.setBackgroundDrawable(drawable);
	but_0.setTextColor(android.graphics.Color.parseColor("#7a7a7a"));
	but_5.setTextColor(android.graphics.Color.parseColor("#7a7a7a"));
	but_10.setTextColor(android.graphics.Color.parseColor("#7a7a7a"));
	but_20.setTextColor(android.graphics.Color.parseColor("#7a7a7a"));
	but_50.setTextColor(android.graphics.Color.parseColor("#7a7a7a"));
	but_70.setTextColor(android.graphics.Color.parseColor("#7a7a7a"));
	but_30.setTextColor(android.graphics.Color.parseColor("#7a7a7a"));
	
	but_100.setTextColor(android.graphics.Color.parseColor("#7a7a7a"));
}
/*
 * 
 */
private  Handler handler = new Handler() {
    // 在Handler中获取消息，重写handleMessage()方法
    @Override
    public void handleMessage(Message msg) {   
    	Drawable bj_img = getResources().getDrawable(R.drawable.nei_bj_img);
    	Drawable bj_img_ = getResources().getDrawable(R.drawable.nei_bj_img_);
    	Drawable bj_jf = getResources().getDrawable(R.drawable.nei_bj_jf);
    	Drawable bj_jf_ = getResources().getDrawable(R.drawable.nei_bj_jf_);
    	
        // 判断消息码是否为1
        if(msg.what==0){
       	 dialog_.cancel();
    		JSONObject imgback;
    		
			try {
				imgback = new JSONObject(msg.obj.toString()+"");
				//camIndex++;
				if(camIndex<mPhotoList.size())
					{
					
					allpath+=imgback.getJSONObject("data").getString("rootName")+",";
					Log.d("upimg", "allurl -> " + allpath);
					}
				
				
				//Toast.makeText(nei_bj.this,imgback.getJSONObject("data").getString("rootName")+"", 1).show();
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 Log.d("web", "url -> " + msg.obj.toString());
        }
        if(msg.what==1){
        	AlphaAnimation animation=new AlphaAnimation(0, 1);
			animation.setDuration(300);
			relativeLayout2.startAnimation(animation);
        	nei_bj_jf.setImageDrawable(bj_jf);
    		nei_bj_img.setImageDrawable(bj_img);
    		relativeLayout2.setVisibility(View.VISIBLE);
    		nei_bj_addview.setVisibility(View.GONE);
    		if(mPhotoList.size()>1)
    			mGridView.setVisibility(View.VISIBLE);
    		else
    			nei_bj_addimg.setVisibility(View.VISIBLE);
    		nei_bj_edi.setEnabled(true);
        
        }
        if(msg.what==2)
        {
        	AlphaAnimation animation=new AlphaAnimation(0, 1);
			animation.setDuration(300);
			relativeLayout2.startAnimation(animation);
        	nei_bj_jf.setImageDrawable(bj_jf_);
    		nei_bj_img.setImageDrawable(bj_img_);
    		relativeLayout2.setVisibility(View.VISIBLE);
    		nei_bj_addview.setVisibility(View.VISIBLE);
    		nei_bj_addimg.setVisibility(View.GONE);
    		mGridView.setVisibility(View.GONE);
    		nei_bj_edi.setEnabled(true);
        }
        if(msg.what==3){
        	AlphaAnimation animation=new AlphaAnimation(1,0);
			animation.setDuration(300);
			relativeLayout2.startAnimation(animation);
			relativeLayout2.setVisibility(View.GONE);
    		nei_bj_addview.setVisibility(View.GONE);
    		nei_bj_addimg.setVisibility(View.GONE);
    		mGridView.setVisibility(View.GONE);
    		nei_bj_edi.setEnabled(true);
        }
    }
};
/*
 * 选择的图片上传到服务器
 */
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
@Override
public void finish() {
	// TODO Auto-generated method stub
	super.finish();
	
	Constant.bj_jf="";
	Constant.bj_tp="";
	Constant.bj_nr="";
}

@Override
protected void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	MobclickAgent.onPageStart("nei_bj");
	MobclickAgent.onResume(this);
}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	MobclickAgent.onPageEnd("nei_bj");
	MobclickAgent.onPause(this);
}
public boolean onKeyDown(int keyCode, KeyEvent event) {
	   
    if (keyCode == KeyEvent.KEYCODE_BACK) {
    	
    	digshow();
    	
        return false;
    }
    return super.onKeyDown(keyCode, event);
}
private void digshow() {
	// TODO Auto-generated method stub
	View mView = null;
	mView = mInflater.inflate(R.layout.dialog_myconfig,null);
	AbDialogUtil.showDialog(mView,R.animator.fragment_top_enter,R.animator.fragment_top_exit,R.animator.fragment_pop_top_enter,R.animator.fragment_pop_top_exit);
	Button leftBtn1 = (Button)mView.findViewById(R.id.left_btn);
	Button rightBtn1 = (Button)mView.findViewById(R.id.right_btn);
	TextView title_choices = (TextView)mView.findViewById(R.id.title_choices);
	TextView choice_one_text= (TextView)mView.findViewById(R.id.choice_one_text);
	//title_choices.setText("");
	choice_one_text.setText("您是否要放弃编辑提问？");
	leftBtn1.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			AbDialogUtil.removeDialog(nei_bj.this);
		}
		
	});
	
	rightBtn1.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent intent = null;
			intent = new Intent(nei_bj.this,Main.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
			startActivity(intent);		
		}
		
	});
}



}
