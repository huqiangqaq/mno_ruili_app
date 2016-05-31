package mno_ruili_app.nei;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ab.image.AbImageLoader;
import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.android.volley.toolbox.NetworkImageView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
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

import mno.mylistview.FooterLoadingLayout;
import mno.mylistview.PullToRefreshBase;
import mno.mylistview.PullToRefreshList;
import mno.mylistview.PullToRefreshBase.OnRefreshListener;
import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.JustifyTextView;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.ct.RoundImageView;
import mno.ruili_app.ct.imageview;
import mno.ruili_app.main.HomeFragment;
import mno.ruili_app.main.NeighborFragment;
import mno.ruili_app.my.my_grzy_item;
import mno_ruili_app.adapter.ImageListAdapter_pl;
import mno_ruili_app.adapter.ImageListAdapter_pl2;
import mno_ruili_app.adapter.hd;
import mno_ruili_app.adapter.mySimpleAdapter;
import mno_ruili_app.adapter.tv;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.webpost;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;


public class nei_wdxq extends Activity {
	private UMSocialService mController = UMServiceFactory.getUMSocialService(Constant.DESCRIPTOR);
	RoundImageView lz_image,yi_image,er_image,san_image;
	ImageView img1,img2,img3,img4;
	ImageView nei_wd_cn;
	LinearLayout nei_pl;
	String id="",uid="",tag="";
	String ID;
	webhandler handler_,handler_2,handler_3; 
	TextView wdxq_name,wdxq_time,title_name,wdxq_fs;
	JustifyTextView wdxq_con;
	JSONArray answer;
	private ListView mListView = null;
	 ImageListAdapter_pl2 mylistViewAdapter;
	ArrayList<hd>  mplList = new ArrayList<hd>();
	JSONObject data ;
	JSONArray detail;
	private PullToRefreshList mRefreshLayout;
	LinearLayout layout,nei_pl_box,nei_fb_box,imgbox;
	boolean Isrefresh=false;boolean ok=false;
	AbImageLoader mAbImageLoader;
	LinearLayout pl_box;
	View view;
	private String msg_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nei_wdxq);
		Constant.SOCIAL_CONTENT="来看看这个问题，我和他对于这方面的见解不同，你怎么想？";
		Constant.SOCIAL_CONTENT2=Constant.SOCIAL_CONTENT;
		Constant.SOCIAL_TITLE="大师兄";
		Constant.SOCIAL_IMAGE="";
		String url="http://www.yuetingfengsong.com:8087/ShowPage/Share?";
		url=url+"title="+"睿立培训"+"&desc="+"&url="+"";//String url2="http://120.55.190.27/mobile/";
		Constant.SOCIAL_LINK="http://120.55.190.27/mobile/";
		/*Intent intent0 =this.getIntent();
		id=intent0.getStringExtra("ID").toString();*/
		view =new View(nei_wdxq.this);
		id=Constant.itemid;
		msg_id=Constant.msg_id;
		webhandler handler2=new webhandler(){
			  public void OnResponse(org.json.JSONObject response) {};
	    };
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("msg_id",msg_id);
	    handler2.SetRequest(new RequestType("3",Type.msgView),params);
		//
		Intent intent0 =this.getIntent();
		if(intent0.getStringExtra("ID")!=null){
			ID=intent0.getStringExtra("ID").toString();
			webhandler handler=new webhandler(){
				  public void OnResponse(org.json.JSONObject response) {};
		    };
		    Map<String, String> params4 = new HashMap<String, String>();
		    params4.put("msg_id",ID);
		    handler.SetRequest(new RequestType("3",Type.msgView),params4);
		}
		
		
	    addview();
		init();
		InitHandler();
		

	}
	private void get() {
		// TODO Auto-generated method stub
		if(!appuser.getInstance().IsLogin())
	    	 {
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("accessCode",  Constant.accessCode);
			params.put("questionId",id);
		 	handler_3.SetRequest(new RequestType("3",Type.getResonList),params);
			handler_.SetRequest(new RequestType("3",Type.getQuesDetail),params);
	    	 }
		else
		{
			
			 Map<String, String> params = new HashMap<String, String>();
			 params.put("uid", Constant.uid);
		     params.put("accessCode",  Constant.acc);
			 params.put("questionId",id);
		 	 handler_3.SetRequest(new RequestType("3",Type.getResonList),params);
			 handler_.SetRequest(new RequestType("3",Type.getQuesDetail),params);
		}
		
	}
	EditText nei_bj_edi;
	private void init() {
		// TODO Auto-generated method stub
		 imm = (InputMethodManager) getSystemService(nei_wdxq.INPUT_METHOD_SERVICE); 
		 mAbImageLoader = AbImageLoader.newInstance(nei_wdxq.this);
	     mAbImageLoader.setMaxWidth(0);
	     mAbImageLoader.setMaxHeight(0);
	    // String  coverImg="http://b264.photo.store.qq.com/psb?/V14av8GK1voYCO/BPkJ.Gv0rDTo0MGFlxDb7ze4vPxjxPztOkaZHqVgdYI!/b/dIBJXp0lFAAA&bo=*gFmAQAAAAABB7g!&rf=viewer_4";
	     nei_bj_edi=(EditText)this.findViewById(R.id.nei_bj_edi);
	     pl_box=(LinearLayout)this.findViewById(R.id.nei_pl_box);
	    
		title_name=(TextView)this.findViewById(R.id.title_name);
		
		  mRefreshLayout = (PullToRefreshList)this.findViewById(R.id.mListView);
		  mListView = mRefreshLayout.getRefreshView();
		  mListView.setDivider(new ColorDrawable(android.R.color.transparent));
		  mListView.setOverscrollFooter(null);
		   mListView.setOverscrollHeader(null);
		   mListView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
		   mListView.setSelector(R.drawable.selector_button_color_red_noshape);
		   mListView.setVerticalScrollBarEnabled(false);
	      // mList.setVerticalScrollBarEnabled(false);
	       mRefreshLayout.setPullLoadEnabled(true);
	       mRefreshLayout.setPullRefreshEnabled(true);
	      
	       ((FooterLoadingLayout) mRefreshLayout.getFooterLoadingLayout())
	                .setNoMoreDataText("已经到底啦");
	       mRefreshLayout.setHasMoreData(false);
	       mRefreshLayout.setOnRefreshListener(new OnRefreshListener<ListView>() {
	            @Override
	            public void onPullDownToRefresh(
	                    PullToRefreshBase<ListView> refreshView) {
	            	refreshTask();
	            }

	          
				@Override
	            public void onPullUpToRefresh(
	                    PullToRefreshBase<ListView> refreshView) {
				
							mRefreshLayout.setHasMoreData(false);
	            }
	        });
		
		nei_pl_box=(LinearLayout)nei_wdxq.this.findViewById(R.id.nei_pl_box);
		nei_fb_box=(LinearLayout)nei_wdxq.this.findViewById(R.id.nei_fb_box);
		nei_pl=(LinearLayout)nei_wdxq.this.findViewById(R.id.nei_pl);
		mylistViewAdapter = new ImageListAdapter_pl2(nei_wdxq.this, mplList,
				R.layout.item_pl2, new String[] { "smallImg"},
				new int[] { R.id.pl_image,R.id.pl_name ,R.id.pl_time,R.id.pl_con},id);
		 
		   mListView.addHeaderView(layout);
		   mListView.setAdapter(mylistViewAdapter);
		   mListView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					imm.hideSoftInputFromWindow(nei_bj_edi.getWindowToken(), 0);
				}
	    		
	    	});
		
	}
	private void addview() {
		// TODO Auto-generated method stub
		layout = (LinearLayout) LayoutInflater.from(nei_wdxq.this).inflate(R.layout.nei_wd_top, null);

		wdxq_name=(TextView)layout.findViewById(R.id.wdxq_name);
		wdxq_con=(JustifyTextView)layout.findViewById(R.id.wdxq_con);
		wdxq_time=(TextView)layout.findViewById(R.id.wdxq_time);
		wdxq_fs=(TextView)layout.findViewById(R.id.wdxq_fs);
		lz_image= (RoundImageView) layout.findViewById(R.id.lz_image);
		imgbox=(LinearLayout)layout.findViewById(R.id.img_box);
		/*ViewGroup.LayoutParams layoutParams = imgbox.getLayoutParams();  
        layoutParams.height =Constant.displayWidth/9*2;//总行高+每行的间距  
        imgbox.setLayoutParams(layoutParams); */
		android.view.ViewGroup.LayoutParams lp =imgbox.getLayoutParams(); lp.height=Constant.displayWidth/9*2*8/10;
		img1= (ImageView) layout.findViewById(R.id.img1);
		
		img2= (ImageView) layout.findViewById(R.id.img2);
		
		img3= (ImageView) layout.findViewById(R.id.img3);
		img4= (ImageView) layout.findViewById(R.id.img4);
		
	}
	String[] pic;
	String pics;
	private void InitHandler()
	{
		handler_3= new webhandler(){

			@Override
			public void OnResponse(JSONObject response) {
				// TODO Auto-generated method stub
				super.OnResponse(response);
				JSONArray data;
				jblist.clear();
				try {
					data = new JSONArray( response.getString("data") );
			
				int length=data.length();
			    for(int i=0;i<= length; i++)
			    {
			    
			    JSONObject type_json =   data.getJSONObject(i);
			    String name =  type_json.getString("resonName");
			    String id =  type_json.getString("id");
			    final tv mtv = new tv(name, "",id);
				jblist.add(mtv);
			    }
			    adapter1.notifyDataSetChanged();
				}
				 catch (JSONException e) {
				// TODO Auto-generated catch block
				
			}
				}
			@Override
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
			}
		
			
		};
		
		handler_2= new webhandler(){

			@Override
			public void OnResponse(JSONObject response) {
				// TODO Auto-generated method stub
				super.OnResponse(response);
				String mess;
				try {
					mess = response.getString("message");
					if(mess.equals("删除成功"))
						finish();
					if(mess.equals("null") == false && mess.length() > 1)
					{
						refreshTask();
						Toast.makeText(nei_wdxq.this, mess, Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
				String ms=mess;
				ms=ms+"";
				MessageBox.Show(nei_wdxq.this,mess);
			}
			
		};
		handler_ = new webhandler(){
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
				 mRefreshLayout.onPullDownRefreshComplete();
				 mRefreshLayout.onPullUpRefreshComplete();
			}
			@Override
			public void OnResponse(JSONObject response) {
				 
				try {
					if(Isrefresh)
					{
						mRefreshLayout.onPullDownRefreshComplete();
					}
					 mplList.clear();
					 data = response.getJSONObject("data");
					 detail = data.getJSONArray("detail");
					 if(detail.toString().length()<5)
					 {
						 wdxq_con.setText("该提问已被删除..\n");
						
						
					 }
					  try {
						    String name=detail.getJSONObject(0).getString("username");
						    if(name.length()>7)
						    	name=name.substring(0,5)+"...";
							title_name.setText(name+"的提问");
							uid=detail.getJSONObject(0).getString("uid");
							String statusName=detail.getJSONObject(0).getString("statusName");
							Constant.itemuid=uid;
							Constant.itemzt=statusName;
							Constant.bj_bt=detail.getJSONObject(0).getString("title");
							Constant.bj_nr=detail.getJSONObject(0).getString("content");
							wdxq_name.setText(detail.getJSONObject(0).getString("username"));
							wdxq_con.setText(detail.getJSONObject(0).getString("content")+"\n");
							wdxq_time.setText(detail.getJSONObject(0).getString("create_at"));
							wdxq_fs.setText(detail.getJSONObject(0).getString("needPoint"));
							tag=detail.getJSONObject(0).getString("tag");
							if(appuser.getInstance().IsLogin())
					    	 {
							
							if(uid.equals(appuser.getInstance().getUserinfo().uid))
							{
						      /*  LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					            params.setMargins(0, 0, 0, 0);
					          
					            mAbPullToRefreshView.setLayoutParams(params);*/
								title_name.setText("我的提问");
								pl_box.setVisibility(View.GONE);
								
								nei_pl.setEnabled(false);
								
							}
					    	 }
							 /*if(detail.getJSONObject(0).getString("bigImg") == null || detail.getJSONObject(0).getString("bigImg").length() < 5)
							 {
								 //lz_image.setImageResource(R.drawable.image_empty);//没图片
							 }
							 else
							 {*/
							 String url=RequestType.getWebUrl_(detail.getJSONObject(0).getString("bigImg"));
							 lz_image.setImageUrl(url,webpost.getImageLoader());
							 lz_image.setErrorImageResId(R.drawable.my_up);
							 lz_image.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										 Intent intent = new Intent(nei_wdxq.this,my_grzy_item.class);
									        try {
												intent.putExtra("img",detail.getJSONObject(0).getString("bigImg"));
												intent.putExtra("ID",uid);  
												intent.putExtra("name",detail.getJSONObject(0).getString("username"));  
												 nei_wdxq.this.startActivity(intent);
											} catch (JSONException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}  
											
										//MessageBox.Show(mContext, mzx.getContent());
									}
					     });
							 //}
							 pics=detail.getJSONObject(0).getString("pics");
							if(pics.contains(","))
							{
								
								try {
									pic  = pics.split(",");
									if(pic.length==2)
									{
									mAbImageLoader.display(img1,RequestType.getWebUrl_(pic[0]));
									mAbImageLoader.display(img2,RequestType.getWebUrl_(pic[1]));
									Constant.SOCIAL_IMAGE=RequestType.getWebUrl_(pic[0]);
									}
									else if(pic.length==3)
									{
										mAbImageLoader.display(img1,RequestType.getWebUrl_(pic[0]));
										mAbImageLoader.display(img2,RequestType.getWebUrl_(pic[1]));
										mAbImageLoader.display(img3,RequestType.getWebUrl_(pic[2]));
										Constant.SOCIAL_IMAGE=RequestType.getWebUrl_(pic[0]);
									}
									else if(pic.length>=4)
									{
										mAbImageLoader.display(img1,RequestType.getWebUrl_(pic[0]));
										mAbImageLoader.display(img2,RequestType.getWebUrl_(pic[1]));
										mAbImageLoader.display(img3,RequestType.getWebUrl_(pic[2]));
										mAbImageLoader.display(img4,RequestType.getWebUrl_(pic[3]));
										Constant.SOCIAL_IMAGE=RequestType.getWebUrl_(pic[0]);
									}

									
								}catch (Exception e){
									
								}
							}
							else if(pics.length()<5)
							{
								android.view.ViewGroup.LayoutParams lp2 =imgbox.getLayoutParams(); 
								lp2.height=10;
							}
								
							else
								mAbImageLoader.display(img1,RequestType.getWebUrl_(pics));
						  } catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					 // handler_.SetProgressDialog(nei_wdxq.this);
					/////////////////
					mplList.clear();
					answer = data.getJSONArray("answer");
					 if(answer.length()>0){
					 //mList=webpost.getList(answer);
						int length=answer.length();
					    for(int i=0;i<= length; i++)
					    {
					    JSONObject type_json =answer.getJSONObject(i);
					    //listData.add(data.getJSONObject(i).getString("Name"));
					    String name =  type_json.getString("username");
					    
					    String content =  type_json.getString("content");
					    String id =  type_json.getString("answerId");
					    String time =  type_json.getString("create_at");
					    String imgurl=type_json.getString("smallImg");
					    String choose=type_json.getString("choose");
					    final hd mzx = new hd(name,content, id,imgurl, time,choose,type_json.getString("uid"));
					    
						mplList.add(mzx);
						mylistViewAdapter.notifyDataSetChanged();
					    }
						
					  
						
				    	
					 }
					  
				
					
			
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
			}
			
			};
		
			
	}
	 InputMethodManager imm;
	public void onclick(View v)
	{
		imm.hideSoftInputFromWindow(nei_bj_edi.getWindowToken(), 0);
		/*nei_pl_box.setVisibility(View.VISIBLE);;
		 nei_fb_box.setVisibility(View.GONE);;*/
	if(v.getId()==R.id.nei_pl)
	{
		if(!appuser.getInstance().IsLogin())
   	 {
			 appuser.getInstance().LoginToAct(nei_wdxq.this,  nei_wdxq.class);
   		 return;
   	 }
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
		  params.put("questionId",id);
		
		  params.put("desc",nei_bj_edi.getText().toString());
	      handler_2.SetRequest(new RequestType("3",Type.addAnswer),params);
	      
	      nei_bj_edi.clearFocus();
	      nei_bj_edi.setText("");
	      
	     imm.hideSoftInputFromWindow(nei_bj_edi.getWindowToken(), 0);
	     
	     nei_pl_box.setVisibility(View.VISIBLE);;
		 nei_fb_box.setVisibility(View.GONE);;
	}
	else if(v.getId()==R.id.nei_wdxq_more)
	{
		
		showDialog(nei_wdxq.this);
	}
	else if(v.getId()==R.id.img1)
	{
		
		try {
			
		if(pic.length>=2)
		{
			Intent intent = new Intent(nei_wdxq.this,imageview.class);
		    intent.putExtra("url",RequestType.getWebUrl_(pic[0])); 
		    
		    nei_wdxq.this.startActivity(intent);
			overridePendingTransition(R.anim.welcome_fade_in_scale, R.anim.welcome_fade_out); 
		}
		}catch (Exception e){
			Intent intent = new Intent(nei_wdxq.this,imageview.class);
			intent.putExtra("url",RequestType.getWebUrl_(pics));
			nei_wdxq.this.startActivity(intent);
			overridePendingTransition(R.anim.welcome_fade_in_scale, R.anim.welcome_fade_out);
		}
		

	}
	else if(v.getId()==R.id.img2)
	{
		
		try {
			String img=pic[1];
			if(img.length()>=2)
			{
			Intent intent = new Intent(nei_wdxq.this,imageview.class);
		    intent.putExtra("url",RequestType.getWebUrl_(pic[1])); 
		    
		    nei_wdxq.this.startActivity(intent);
			overridePendingTransition(R.anim.welcome_fade_in_scale, R.anim.welcome_fade_out); 
			}
		}catch (Exception e){
			
		

		}
		
	}
	else if(v.getId()==R.id.img3)
	{
		try {
			if(pic[2].length()>=2)
			{
			Intent intent = new Intent(nei_wdxq.this,imageview.class);
		    intent.putExtra("url",RequestType.getWebUrl_(pic[2])); 
		    
		    nei_wdxq.this.startActivity(intent);
			overridePendingTransition(R.anim.welcome_fade_in_scale, R.anim.welcome_fade_out); 
			}
		}catch (Exception e){
			
		

		}
	}
	else if(v.getId()==R.id.img4)
	{
		try {
			if(pic[3].length()>=2)
			{
			Intent intent = new Intent(nei_wdxq.this,imageview.class);
		    intent.putExtra("url",RequestType.getWebUrl_(pic[3])); 
		    
		    nei_wdxq.this.startActivity(intent);
			overridePendingTransition(R.anim.welcome_fade_in_scale, R.anim.welcome_fade_out); 
			}
		}catch (Exception e){
	}}
	
	
	}
	
	
	 Timer timer = new Timer( );
	  TimerTask task = new TimerTask( ) {
	  public void run ( ) {
	  Message message = new Message( );
	  message.what = 1;
	  handler.sendMessage(message);
	  }
	  };
	  
	   
	  final Handler handler = new Handler( ) {
	  public void handleMessage(Message msg) {
	  switch (msg.what) {
	  case 1:
		 
	  update( );
	  break;
	  }
	  super.handleMessage(msg);
	  }

	
	  };
	  public void onDestroy ( ) {
	  if (timer != null) {
	  timer.cancel( );
	  timer = null;
	  }
	  super.onDestroy( );
	  }
	  private void update() {
			// TODO Auto-generated method stub
		
	  }
	 

		private void refreshTask() {
			// TODO Auto-generated method stub
			
			get();
			Isrefresh=true;
		
		}
		private void loadMoreTask() {
			// TODO Auto-generated method stub
			//LoadData();
			
		}
		
		LinearLayout show_1,show_2,myself;
		TextView nei_1,nei_2,nei_3,nei_4,nei_5;
		ListView jblistView;
		final ArrayList<tv>   jblist = new ArrayList<tv>();
		mySimpleAdapter adapter1;
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
			TextView nei_bj = (TextView) window.findViewById(R.id.nei_bj);
			TextView nei_del = (TextView) window.findViewById(R.id.nei_del);
			TextView cancel = (TextView) window.findViewById(R.id.nei_qx);
			ImageView login_qq= (ImageView) window.findViewById(R.id.login_qq);
			ImageView login_wx= (ImageView) window.findViewById(R.id.login_wx);
			ImageView login_wb= (ImageView) window.findViewById(R.id.login_wb);
			ImageView login_wxpy= (ImageView) window.findViewById(R.id.login_wxpy);
			RelativeLayout kb=(RelativeLayout)window.findViewById(R.id.kongbai);
			jblistView=(ListView) window.findViewById(R.id.jblistView);
			show_1= (LinearLayout) window.findViewById(R.id.show_1);

			myself= (LinearLayout) window.findViewById(R.id.myself);
			show_2= (LinearLayout) window.findViewById(R.id.show_2);
			show_2.setVisibility(View.GONE);
			sc.setText("举报");
			if(appuser.getInstance().IsLogin())
	    	 {
			
			if(uid.equals(appuser.getInstance().getUserinfo().uid))
			{
				//MessageBox.Show(nei_wdxq.this, "it.s you");
				//nei_pl_box.setVisibility(View.INVISIBLE);
				sc.setVisibility(View.GONE);
				myself.setVisibility(View.VISIBLE);
			
			}
	    	 }
			adapter1 = new mySimpleAdapter(this ,jblist, R.layout.item_jb,
		    	    new String[] { "name" }, new int[] {R.id.name });
			jblistView.setAdapter(adapter1);
			jblistView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					String a=jblist.get(position).getImg();
					jb(a);
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
			nei_del.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 Map<String, String> params = new HashMap<String, String>();
					 params.put("method","question");
				     params.put("ids",id);
				 
			     handler_2.SetRequest(new RequestType("4",Type.delMyData),params);
			     dialog.cancel();
				}
			});
			nei_bj.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if(Constant.itemzt.equals("已解决"))
					{
						MessageBox.Show(getApplicationContext(), "您已经采纳了");
						return;
					}
					Intent intent = new Intent(nei_wdxq.this,nei_bj.class);
				
					//Constant.SOCIAL_IMAGE=RequestType.getWebUrl_(mzxList.get(position-1).getImg());
					//intent.putExtra("ID",mzxList.get(position-1).getid());  
					
					Constant.bj_jf=wdxq_fs.getText().toString();
					Constant.bj_tp=pics;
					Constant.bj_tag=tag;
					Constant.isbj="0";
					Constant.bj_id=id;
					nei_wdxq.this.startActivity(intent);
					 dialog.cancel();
			
				}
			});
			sc.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				/*	 Map<String, String> params = new HashMap<String, String>();
					  params.put("likeId",Constant.zxid);
					  params.put("forwhat","coll");
					  params.put("type","news");
					  handler_.SetRequest(new RequestType("3",Type.reportQues),params);*/
					show_1.setVisibility(View.GONE);
					show_2.setVisibility(View.VISIBLE);
			
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
		public void jb(String jb) {
			// TODO Auto-generated method stub
			  Map<String, String> params = new HashMap<String, String>();
			  params.put("questionId",id);
			  params.put("resonId",jb);
			 
			  handler_2.SetRequest(new RequestType("3",Type.reportQues),params);
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
		
	    mController.postShare(nei_wdxq.this, platform, new SnsPostListener() {

	    	
	    	 @Override
	         public void onStart() {
	            MessageBox.Show(nei_wdxq.this , "开始分享");
	         }

				@Override
				public void onComplete(SHARE_MEDIA arg0, int eCode,
						SocializeEntity arg2) {
					if (eCode == 200) {
						MessageBox.Show(nei_wdxq.this , "分享成功");
	              } else {
	                   String eMsg = "";
	                   if (eCode == -101){
	                       eMsg = "没有授权";
	                   }
	                   MessageBox.Show(nei_wdxq.this , "分享失败");
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
	/**
	 * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
	 *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
	 *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
	 *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
	 * @return
	 */
	private void addQQQZonePlatform() {
		
	    String appId = "100424468";
	    String appKey = "c7394704798a158208a74ab60104f0ba";
	    // 添加QQ支持, 并且设置QQ分享内容的target url
	    UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(nei_wdxq.this,
	            appId, appKey);
	    
	    qqSsoHandler.setTargetUrl(Constant.SOCIAL_LINK);
	    qqSsoHandler.setTitle(Constant.SOCIAL_TITLE);
	    qqSsoHandler.setShareAfterAuthorize(false);
	    
	    qqSsoHandler.addToSocialSDK();

	    // 添加QZone平台
	    QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(nei_wdxq.this, appId, appKey);
	    qZoneSsoHandler.addToSocialSDK();
	    
		mController.setShareContent(Constant.SOCIAL_CONTENT);
		// 设置分享图片, 参数2为图片的url地址
		mController.setShareMedia(new UMImage(nei_wdxq.this, Constant.SOCIAL_IMAGE));
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
	    UMWXHandler wxHandler = new UMWXHandler(nei_wdxq.this, appId, appSecret);
	    wxHandler.setTargetUrl(Constant.SOCIAL_LINK);
	    wxHandler.setTitle(Constant.SOCIAL_CONTENT);
	    wxHandler.addToSocialSDK();
	   
	    // 支持微信朋友圈
	    UMWXHandler wxCircleHandler = new UMWXHandler(nei_wdxq.this, appId, appSecret);
	    wxCircleHandler.setToCircle(true);
	    wxCircleHandler.setTargetUrl(Constant.SOCIAL_LINK);
	    wxCircleHandler.setTitle(Constant.SOCIAL_CONTENT);
	    wxCircleHandler.addToSocialSDK();
	    mController.getConfig().setSsoHandler(new SinaSsoHandler());
	   /* SinaShareContent sinaContent = new SinaShareContent();
	    sinaContent.setShareContent(Constant.SOCIAL_CONTENT+Constant.SOCIAL_LINK);*/
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
        get();
        Constant.isbj="";
		super.onStart();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("nei_wdxq");
		MobclickAgent.onResume(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("nei_wdxq");
		MobclickAgent.onPause(this);
	}
}
