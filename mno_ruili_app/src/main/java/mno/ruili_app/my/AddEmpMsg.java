package mno.ruili_app.my;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ab.activity.AbActivity;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbFileUtil;
import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.JsonObject;
import com.umeng.analytics.MobclickAgent;

import mno.down.util.SharePreTolls;
import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.CustomEditText;
import mno.ruili_app.ct.CustomListview;
import mno.ruili_app.ct.LoadingDialog;
import mno_ruili_app.adapter.AddEmpAdapter;
import mno_ruili_app.adapter.AddWorkExpAdapter;
import mno_ruili_app.entity.Worker;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.loginhandler;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.webpost;
import mno_ruili_app.net.RequestType.Type;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
//填写招聘信息
public class AddEmpMsg extends AbActivity implements OnClickListener{
	private AbHttpUtil mAbHttpUtil = null;
	//String[] datas = new String[7];
	LayoutInflater mInflater;
	MyAdapter adapter= new MyAdapter();
	TextView tv_addJob,tv_ok;
	CustomListview mListView;
	CustomEditText cet_jobMsg,cet_domain,cet_address,cet_companyName,cet_phone,cet_email;
	GridView gv_jobMsg;
	AddEmpAdapter aeAdapter;
	webhandler handler,handler1,handler2;
	String companyName,companyPlace,domain="",phone,email,imagePath1,teamInfo="",//,imagePath2
		jobName,salary,workTime,workPlace,uid,post_id,eduLevel,jobDuty,jobAsk;
	//String[] companyMsg;
	String[] companyMsg=new String[2];
	List<Worker>   mList = new ArrayList<Worker>();
	NetworkImageView iv_addImage1;
	private View mAvatarView = null;
	/*用来标识请求gallery的activity */
	private static final int PHOTO_PICKED_WITH_DATA = 3021;
	/*用来标识请求照相功能的activity */
	private static final int CAMERA_WITH_DATA = 3023;
	private String mFileName;
	/*拍照的照片存储位置 */
	private  File PHOTO_DIR = null;
	/*照相机拍照得到的图片*/
	private File mCurrentPhotoFile;
	public File FILE_LOCAL = null;
	Dialog  dialog_;
	//String picPath="";
	//boolean image1=false,image2=false;
	String picPath1="",images="",str,text;//picPath2="",where="",
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_emp_msg);
		FILE_LOCAL = new File(AbFileUtil.getImageDownloadDir(this));
		if(!FILE_LOCAL.exists()){
			FILE_LOCAL.mkdirs();
		}
		String photo_dir = AbFileUtil.getImageDownloadDir(this);
	    if(AbStrUtil.isEmpty(photo_dir)){
	    	AbToastUtil.showToast(AddEmpMsg.this,"存储卡不存在");
	    }else{
	    	PHOTO_DIR = new File(photo_dir);
	    }
		init();
		/*
		 * 获取选取照片之前的数据
		 */
        if(savedInstanceState!=null){  
        	cet_companyName.setText(savedInstanceState.getString("companyName"));
			cet_address.setText(savedInstanceState.getString("address"));
			cet_domain.setText(savedInstanceState.getString("domain"));
			cet_phone.setText(savedInstanceState.getString("phone"));
			cet_email	.setText(savedInstanceState.getString("email"));				
			cet_jobMsg.setText(savedInstanceState.getString("jobMsg"));  
        } 
		gv_jobMsg.setAdapter(adapter);	//第一次进入填写简历界面时亮点空格的初始化
		uid=appuser.getInstance().getUserinfo().uid.toString();
		loadCompanyData();
		mInflater = LayoutInflater.from(this);		
	}
	public void setListViewHeight(ListView listView) {    
        
        // 获取ListView对应的Adapter    
        
        ListAdapter listAdapter = listView.getAdapter();    
        
        if (listAdapter == null) {    
            return;    
        }    
        int totalHeight = 0;    
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目    
            View listItem = listAdapter.getView(i, null, listView);    
            listItem.measure(0, 0); // 计算子项View 的宽高    
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度    
        }    
        
        ViewGroup.LayoutParams params = listView.getLayoutParams();    
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));    
        listView.setLayoutParams(params);    
    }
	private void loadCompanyData() {
		handler = new webhandler() {
			public void OnResponse(org.json.JSONObject response) {
				try {
					JSONObject type_json = response.getJSONObject("data");
					//JSONObject type_json= data.getJSONObject("resume");
					
					companyName = type_json.getString("comp_name");
					if(companyName.equals("null")){
						companyName="";
					}
					companyPlace = type_json.getString("address");
					if(companyPlace.equals("null")){
						companyPlace="";
					}
					domain = type_json.getString("domain");
					if(domain.equals("null")){
						domain="";
					}
					phone = type_json.getString("comp_phone");
					if(phone.equals("null")){
						phone="";
					}
					email = type_json.getString("comp_email");
					if(email.equals("null")){
						email="";
					}
					// 1亮点名字
					JSONArray points = type_json.getJSONArray("points");					
					if(points.length()>1){
						//companyMsg = new String[points.length()];	
						companyMsg = new String[points.length()+1];
					}else{
						//companyMsg = new String[1];
						companyMsg = new String[2];
					}
					for (int j = 0; j < points.length(); j++) {
						JSONObject item = points.getJSONObject(j);
						String point_name = item.getString("point_name");
						companyMsg[j] = point_name;
					}	
					//2.团队简介
					teamInfo=type_json.getString("summary");
					if(teamInfo.equals("null")){
						teamInfo="";
					}
					//3.图片
					imagePath1=type_json.getString("images");	
//					for(int i=0;i<images.length();i++){
//						JSONObject item=images.getJSONObject(i);
//						imagePath1=item.getString("zhiZhao");
//						imagePath2=item.getString("daiMaZheng");
//					}									
					//4.招聘职位
					JSONArray post_list=type_json.getJSONArray("post_list");
					for(int i=0;i<post_list.length();i++){
						JSONObject item=post_list.getJSONObject(i);						
						jobName=item.getString("job_name");
						salary=item.getString("salary");
						workTime=item.getString("job_year");
						workPlace=item.getString("job_addr");
						post_id=item.getString("id");
						eduLevel=item.getString("education");
						jobDuty=item.getString("post_duty");
						jobAsk=item.getString("post_require");
						Worker work=new Worker(jobName,salary, workTime, workPlace, post_id,eduLevel,jobDuty,jobAsk);
						mList.add(work);
					}										
					// 数据填充到视图
					cet_companyName.setText(companyName);
					cet_address.setText(companyPlace);
					cet_domain.setText(domain);
					cet_phone.setText(phone);
					cet_email	.setText(email);				
					// 1.亮点
					adapter = new MyAdapter();
					gv_jobMsg.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					//2.团队简介
					cet_jobMsg.setText(teamInfo);
					//3.图片
					String imageUrl1 =RequestType.getWebUrl_(imagePath1);
					iv_addImage1.setImageUrl(imageUrl1, webpost.getImageLoader());
					//String imageUrl2 =RequestType.getWebUrl_(imagePath2);
					//iv_addImage2.setImageUrl(imageUrl2, webpost.getImageLoader());
					//4.招聘职位
					aeAdapter=new AddEmpAdapter(AddEmpMsg.this, mList, R.layout.add_emp, 
							new String[] { "itemsIcon" }, new int[] { R.id.tv_jobName ,R.id.tv_salary,
							R.id.tv_workTime,R.id.tv_workPlace,R.id.tv_edit,R.id.tv_delete});
					mListView.setAdapter(aeAdapter);
					setListViewHeight(mListView);
					aeAdapter.notifyDataSetChanged();																	        
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}						
		};
		Map<String, String> params = new HashMap<String, String>();
		params.put("uid", uid);
		RequestType requestType = new RequestType("ZP", Type.getCompDetail);		
		handler.SetRequest(requestType, params);			
	}
	/*
	 * 这个是为了防止在选取照片时之前填写的数据丢失
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		String address=cet_address.getText().toString();
		String companyName=cet_companyName.getText().toString();
		String domain=cet_domain.getText().toString();
		String jobMsg=cet_jobMsg.getText().toString();
		String phone=cet_phone.getText().toString();
		String email=cet_email.getText().toString();
		outState.putString("address", address);
		outState.putString("companyName", companyName);
		outState.putString("domain", domain);
		outState.putString("jobMsg", jobMsg);
		outState.putString("phone", phone);
		outState.putString("email", email);
	}
	private void init() {
		dialog_ = LoadingDialog.createLoadingDialog(AddEmpMsg.this, "正在加载中...");
		mListView=(CustomListview) findViewById(R.id.mListView);
		tv_ok=(TextView) findViewById(R.id.tv_ok);
		tv_addJob=(TextView) findViewById(R.id.tv_addJob);
		gv_jobMsg=(GridView) findViewById(R.id.gv_jobMsg);
		iv_addImage1=(NetworkImageView) findViewById(R.id.iv_addImage1);
		//iv_addImage2=(NetworkImageView) findViewById(R.id.iv_addImage2);
		
		cet_address=(CustomEditText) findViewById(R.id.cet_address);
		cet_companyName=(CustomEditText) findViewById(R.id.cet_companyName);
		cet_domain=(CustomEditText) findViewById(R.id.cet_domain);
		cet_jobMsg=(CustomEditText) findViewById(R.id.cet_jobMsg);
		cet_phone=(CustomEditText) findViewById(R.id.cet_phone);
		cet_email=(CustomEditText) findViewById(R.id.cet_email);
		
		iv_addImage1.setOnClickListener(this);
		iv_addImage1.setBackgroundResource(R.drawable.add_image);
		
//		iv_addImage2.setOnClickListener(this);
//		iv_addImage2.setBackgroundResource(R.drawable.add_image);
		tv_addJob.setOnClickListener(this);
		tv_ok.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_addImage1:
			//image1=true;
			//where="image1";
			dialog();
			 AbDialogUtil.showDialog(mAvatarView,Gravity.BOTTOM);
			break;
//		case R.id.iv_addImage2:
//			//image2=true;
//			where="image2";
//			dialog();
//			 AbDialogUtil.showDialog(mAvatarView,Gravity.BOTTOM);
//			break;	
		case R.id.tv_addJob:
			Intent intent3=new Intent(AddEmpMsg.this,AddEmpActivity.class);
			intent3.putExtra("from", "e");
			startActivityForResult(intent3, 3);
			break;	
		case R.id.tv_ok:
			//上传亮点
			StringBuffer sb = new StringBuffer();
			sb.append("[");
		    for(int i=0;i<companyMsg.length-1;i++){
		    	EditText editText = (android.widget.EditText) gv_jobMsg.getChildAt(i);				    	
		    	if(editText.getText()!=null&&!"".equals(editText.getText().toString())){
		    		String text = editText.getText().toString();
		    		sb.append("\"");
			    	sb.append(text);
			    	sb.append("\"");			    	
		    		sb.append(",");			    	
		    	}
		    }
		    str=sb.substring(0,sb.length()-1);
		    str=str+"]";
			/*
			 * 使用AndBase框架的AbHttpUtil来上传图片
			 * 1.先上传图片得到一个字符串
			 */		
			if(cet_companyName.getText().toString().length()>0&&
					cet_address.getText().toString().length()>0&&
					cet_email.getText().toString().length()>0&&
					cet_phone.getText().toString().length()>0&&//picPath1.length()>0&&&&picPath2.length()>0
					mList.size()>0&&sb.toString().length()>2){
				File file1=new File(picPath1);
				//File file2=new File(picPath2);
				String key1="zhiZhao";	//,key2="daiMaZheng"
				AbRequestParams params = new AbRequestParams(); 
				try {
					params.put(key1,file1);
					//params.put(key2,file2);
				} catch (Exception e) {
					e.printStackTrace();
				}			
				mAbHttpUtil = AbHttpUtil.getInstance(this);
				mAbHttpUtil.post(RequestType.uploadImg, params, new AbStringHttpResponseListener() {
					@Override
					public void onSuccess(int statusCode, String content) {
						try {
							JSONObject jsonObject = new JSONObject(content);
							images = jsonObject.getString("data");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						/*
						 * 2.根据上传图片后得到的字符串来上传整个页面的数据
						 */
						handler1=new webhandler(){
							@Override
							public void OnResponse(JSONObject response) {
								// TODO Auto-generated method stub
								super.OnResponse(response);
							}
							@Override
							public void OnError(int code, String mess) {
								// TODO Auto-generated method stub
								super.OnError(code, mess);
							}
						};
						Map<String, String> params2= new HashMap<String, String>();
						params2.put("uid", uid);
						params2.put("comp_name", cet_companyName.getText().toString());
						params2.put("address", cet_address.getText().toString());
						params2.put("domain", cet_domain.getText().toString());
						params2.put("comp_phone", cet_phone.getText().toString());
						params2.put("comp_email", cet_email.getText().toString());
						params2.put("summary", cet_jobMsg.getText().toString());						
						params2.put("points", str);
						params2.put("images", images);
						handler1.SetRequest(new RequestType("ZP",Type.saveComp),params2);
//						if(text.equals("jobplace")){
//							Intent intent = new Intent(AddEmpMsg.this, My_jobPlace_item.class);  
//				    		startActivity(intent);
//							finish();	
						//}else{
							finish();	
						//}				
					}
		            public void onFinish() { 
		            }
					@Override
					public void onStart() {					
					}
					@Override
					public void onFailure(int statusCode, String content,
							Throwable error) {					
					};	            
		        });	
			}else{
				Toast.makeText(this, "请填写完必填项", 1000).show();
			}					
			break;		
		default:
			break;
		}		
	}
	/*
	 * 弹出对话框
	 */
	private void dialog() {
		mAvatarView = mInflater.inflate(R.layout.choose_avatar, null); 
		Button albumButton = (Button)mAvatarView.findViewById(R.id.choose_album);//本地相册
		Button camButton = (Button)mAvatarView.findViewById(R.id.choose_cam);//相机
		Button cancelButton = (Button)mAvatarView.findViewById(R.id.choose_cancel);//取消
		// 1.从相册中去获取Intent.ACTION_GET_CONTENT
		albumButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(AddEmpMsg.this);				
				try {
					Intent intent = new Intent(Intent.ACTION_PICK, null);
					intent.setType("image/*");
					startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
				} catch (ActivityNotFoundException e) {
					AbToastUtil.showToast(AddEmpMsg.this,"没有找到照片");
				}
			}			
		});	
		//2、从照相机获取
		camButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(AddEmpMsg.this);				
				doPickPhotoAction();
			}			
		});		
		//3.取消
		cancelButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(AddEmpMsg.this);
			}			
		});		
	}
	/**
	 * 执行进入照相机的动作
	 */
	private void doPickPhotoAction() {
		String status = Environment.getExternalStorageState();
		//判断是否有SD卡,如果有sd卡存入sd卡在说，没有sd卡直接转换为图片
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			//2.拍照获取图片
			doTakePhoto();
		} else {
			AbToastUtil.showToast(AddEmpMsg.this,"没有可用的存储卡");
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
			AbToastUtil.showToast(AddEmpMsg.this,"未找到系统相机程序");
		}
	}
	/*
	 * 返回的结果
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		try{
			switch (requestCode) {
			case 3:
				jobName=data.getExtras().getString("job_name");
				salary=data.getExtras().getString("salary");
				workTime=data.getExtras().getString("job_year");
				workPlace=data.getExtras().getString("job_addr");
				eduLevel=data.getExtras().getString("education");
				jobDuty=data.getExtras().getString("post_duty");
				jobAsk=data.getExtras().getString("post_require");
				Worker work=new Worker(jobName,salary, workTime, workPlace, post_id,eduLevel,jobDuty,jobAsk);
				mList.add(work);
				aeAdapter=new AddEmpAdapter(AddEmpMsg.this, mList, R.layout.add_emp, 
						new String[] { "itemsIcon" }, new int[] { R.id.tv_jobName ,R.id.tv_salary,
						R.id.tv_workTime,R.id.tv_workPlace,R.id.tv_edit,R.id.tv_delete});
				mListView.setAdapter(aeAdapter);
				setListViewHeight(mListView);
				aeAdapter.notifyDataSetChanged();	
				break;
				//1.从图库返回来的时候
			case PHOTO_PICKED_WITH_DATA:
				Uri uri = data.getData();
				String currentFilePath = getPath(uri);
				if(!AbStrUtil.isEmpty(currentFilePath)){
					//跳转到Android系统自带的一个图片剪裁页面
					Intent intent = new Intent("com.android.camera.action.CROP");
					intent.setDataAndType(uri, "image/*");
					//下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
					intent.putExtra("crop", "true");
					// aspectX aspectY 是宽高的比例
					intent.putExtra("aspectX", 2);
					intent.putExtra("aspectY", 1);
					// outputX outputY 是裁剪图片宽高
					intent.putExtra("outputX", 100);
					intent.putExtra("outputY", 50);
					intent.putExtra("return-data", true);
					startActivityForResult(intent, 4);
					
		        }else{
		        	AbToastUtil.showToast(AddEmpMsg.this,"未在存储卡中找到这个文件");
		        }
				break;
			//2.从相机返回来的时候
			case CAMERA_WITH_DATA:
				//AbLogUtil.d(my_xxxg.class, "将要进行裁剪的图片的路径是 = " + mCurrentPhotoFile.getPath());
				//String currentFilePath2 = mCurrentPhotoFile.getPath();				
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(Uri.fromFile(mCurrentPhotoFile), "image/*");
				//下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
				intent.putExtra("crop", "true");
				// aspectX aspectY 是宽高的比例
				intent.putExtra("aspectX", 2);
				intent.putExtra("aspectY", 1);
				// outputX outputY 是裁剪图片宽高
				intent.putExtra("outputX", 100);
				intent.putExtra("outputY", 50);
				intent.putExtra("return-data", true);
				startActivityForResult(intent, 4);			
				break;
			case 4:
				if(data != null){
					Bundle extras = data.getExtras();
					if (extras != null) {
						Bitmap photo = extras.getParcelable("data");
						String path = saveToLocal(photo);
						if(path!=null){//where.equals("image1")&&
							picPath1=path;
							Bitmap bm=BitmapFactory.decodeFile(picPath1);
							iv_addImage1.setImageBitmap(bm);
						}
//							else if(where.equals("image2")&&path!=null){
//							picPath2=path;
//							Bitmap bm=BitmapFactory.decodeFile(picPath2);
//							iv_addImage2.setImageBitmap(bm);
//						}
					}
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	/*
	 * 得到裁剪后图片的路径
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
	private class MyAdapter extends BaseAdapter {
//		private String[] mdata=new String[]{};
//		public MyAdapter(String[] mdata){
//			this.mdata=mdata;
//		}
        @Override
        public int getCount() {
            //return companyMsg.length+1;
            return companyMsg.length;
        }
        @Override
        public Object getItem(int position) {
            return companyMsg[position];
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView( final int position, View convertView,  final ViewGroup parent) {
            final viewHolder holder;
            if (convertView==null){
                holder = new viewHolder();
                convertView = mInflater.inflate(R.layout.list_item_addtext,parent,false);
                holder.etcontent = (EditText) convertView.findViewById(R.id.et_addtext);
                //输入框的文本监听事件		
        		TextWatcher tw=new TextWatcher() {
        			private CharSequence temp;
        			@Override
        			public void onTextChanged(CharSequence s, int start, int before, int count) {
        				temp = s;
        			}			
        			@Override
        			public void beforeTextChanged(CharSequence s, int start,int count,int after) {
        				temp = s;        				
        			}			
        			@Override
        			public void afterTextChanged(Editable s) {
        				if(temp.toString().length()==6){
        					Toast.makeText(AddEmpMsg.this, "最多可输入6个字", 1000).show();
        				}
        			}
        		};
        		holder.etcontent.addTextChangedListener(tw);
                convertView.setTag(holder);
            }else {
                holder = (viewHolder) convertView.getTag();
            }
            if (position==companyMsg.length-1){//最后一个带加号背景的EditText, position==companyMsg.length
            	final View finalConvertView = convertView;
            	holder.etcontent = (EditText) adapter.getView(position-1,finalConvertView,parent);
	            holder.etcontent.clearFocus();
	            holder.etcontent.setFocusable(false);
	            holder.etcontent.setFocusableInTouchMode(false);
	            holder.etcontent.setHint("");
	            holder.etcontent.setBackgroundResource(R.drawable.add_edittext);    
	            
                holder.etcontent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //if (position ==companyMsg.length){
                        	companyMsg = new String[companyMsg.length+1];
                            //holder.etcontent = (EditText) adapter.getView(position,finalConvertView,parent);

                            holder.etcontent.setFocusable(true);
                            holder.etcontent.setFocusableInTouchMode(true);
                            holder.etcontent.setHint("请输入内容");
                            holder.etcontent.setBackgroundResource(R.drawable.tv_bg);
                            //adapter.notifyDataSetChanged();
                        //}
                    }
                });
            }
            final int len=companyMsg.length;
            //for(int i=0;i<len;i++){
	            if(position<len){
	            	holder.etcontent.setText(companyMsg[position]);           	
	            } 
            //}
            return convertView;
        }
        class viewHolder{
            EditText etcontent;
        }
    }
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("AddEmpMsg");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("AddEmpMsg");
		MobclickAgent.onPause(this);
	}
}
