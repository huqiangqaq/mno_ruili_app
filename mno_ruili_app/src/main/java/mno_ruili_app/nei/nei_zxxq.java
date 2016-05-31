package mno_ruili_app.nei;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.NetworkImageView;
import com.umeng.analytics.MobclickAgent;

import mno.down.service.DownloadService;
import mno.down.util.DownloadManager;
import mno.down.util.NetworkUtils;
import mno.ruili_app.BclFragmentPagerAdapter;
import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.ct.JustifyTextView;
import mno.ruili_app.ct.RoundImageView;
import mno.ruili_app.main.CountService;
import mno.ruili_app.main.Main;
import mno.ruili_app.my.mycheckcode;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.webpost;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class nei_zxxq extends  Fragment{
	RoundImageView lz_image,yi_image,er_image,san_image;
	NetworkImageView zxxq_img;
	LinearLayout nei_pl;
	TextView zx_time;
	JustifyTextView zx_content,zx_title;
	String id;
	webhandler handler_; 
	View View_;
	BclFragmentPagerAdapter PagerAdapter_;
	ViewPager ViewPager_;
	String imageUrl ,replyTotal;
	WebView mWebFlash;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View_ = inflater.inflate(R.layout.nei_zxxq, container,false);
//		IntentFilter filter = new IntentFilter();
//		filter.addAction("mno.zxpl");
//		filter.setPriority(Integer.MAX_VALUE);
//		nei_zxxq.this.getActivity().registerReceiver(myReceiver, filter);
//		init();
//		InitHandler();
		loadWebView();
		return View_;
		
		
	}
	private void loadWebView() {		
		mWebFlash = (WebView) View_.findViewById(R.id.newsContent);
		mWebFlash.getSettings().setJavaScriptEnabled(true);
	    mWebFlash.setWebViewClient(new HelloWebViewClient ());
	    handler_ = new webhandler(){
			@Override
			public void OnResponse(JSONObject response) {
				// TODO Auto-generated method stub
				super.OnResponse(response);
				try {
					JSONObject data = response.getJSONObject("data");
					imageUrl =data.getString("linkUrl");
					replyTotal=data.getString("replyTotal");
					mWebFlash.loadUrl(imageUrl);
					Intent intent = new Intent();  
                    intent.setAction("mno.zx");  
                    intent.putExtra("name", replyTotal);  
                    nei_zxxq.this.getActivity().sendBroadcast(intent);
				} catch (Exception e) {
					// TODO: handle exception
				}				
			}
		};
		Map<String, String> params = new HashMap<String, String>();
		params.put("newsId",Constant.zxid);		
		handler_.SetRequest(new RequestType("3",Type.getNewsDetail),params);		
	}
//	private BroadcastReceiver myReceiver = new BroadcastReceiver() {
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			 String name = intent.getExtras().getString("name");  
//			  Map<String, String> params = new HashMap<String, String>();
//			  params.put("newsId",Constant.zxid);
//			
//			  handler_.SetRequest(new RequestType("3",Type.getNewsDetail),params);
//			
//		}
//
//	};		
	private void init() {		
		 loadFlash();
	}
	public void loadFlash() {
	     mWebFlash = (WebView) View_.findViewById(R.id.newsContent);
	     mWebFlash.setWebViewClient(new HelloWebViewClient ()); 
	     //mWebFlash.loadUrl("http://www.tudou.com/programs/view/html5embed.action?type=2&code=DCemcsRnmuY&lcode=StDyVAcEEkA&resourceId=0_06_05_99");
	     
	     mWebFlash.setWebChromeClient(new WebChromeClient()); 
	     WebSettings ws = mWebFlash.getSettings();
	     
	     ws.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
	     //让缩放显示的最小值为起始
	     mWebFlash.setInitialScale(5);
	     // 设置支持缩放
	     ws.setSupportZoom(true);
	     // 设置缩放工具的显示
	     ws.setBuiltInZoomControls(true);
	     
	     ws.setBlockNetworkImage(true);
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
	/*
	 * 创建自己的WebViewClient
	 */
	 private class HelloWebViewClient extends WebViewClient{
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
			//防止被其它应用植入广告
			view.loadUrl("javascript:function setTop(){document.querySelector('.ad-footer').style.display=\"none\";}setTop();");
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
	Handler handlerpost ;
	String webdata="";
	private void InitHandler()
	{
		   handlerpost = new Handler( ) {
		  	  public void handleMessage(Message msg) {
		  	  switch (msg.what) {
		  	  case 1:
		  		 mWebFlash.getSettings().setDefaultTextEncodingName("utf-8");
		  		mWebFlash.loadUrl(imageUrl);
				 //mWebFlash.loadData(""+webdata,"text/html; charset=UTF-8", null);
		  	  break;
		  	
		  	  }
		  	  super.handleMessage(msg);
		  	  }

			
		    };
		
		handler_ = new webhandler(){

			@Override
			public void OnResponse(JSONObject response) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub 
				 
				try {
					JSONObject data = response.getJSONObject("data");
		
				    	
				    	String b=data.getString("title")+"\n";
				    	//zx_title.setText(b);
				    	String a=data.getString("content");
				    	//Spanned c=Html.fromHtml(a);
				    	String cc=Html.fromHtml(a).toString()+"\n";
						//zx_content.setText(cc);
						//zx_time.setText(data.getString("publish_at"));
						Constant.SOCIAL_CONTENT="总算找到一个特别落实的课程了，觉得启发挺大，只能帮你到这里了，赶紧下载吧";
						Constant.SOCIAL_CONTENT2="总算找到一个特别落实的课程了，觉得启发挺大，只能帮你到这里了，赶紧下载吧";
						Constant.SOCIAL_TITLE=data.getString("title");
						try {
						Constant.SOCIAL_IMAGE=data.getString("img");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							Constant.SOCIAL_IMAGE="";
						}	
						/*http://www.yuetingfengsong.com:8087/ShowPage/Share?title=这里写标题&desc=这里写描述&url=http://www.yuetingfengsong.com:8087/media/root/20150707112846252.jpg */
						String url="http://www.yuetingfengsong.com:8087/ShowPage/Share?";
						url=url+"title="+"睿立培训"+"&desc="+"&url="+"";
						Constant.SOCIAL_LINK="http://120.55.190.27/mobile/";
						//Constant.SOCIAL_IMAGE=RequestType.getWebUrl_(data.getString("coverImg"));
						Intent intent = new Intent();  
	                    intent.setAction("mno.zx");  
	                    intent.putExtra("name", data.getString("replyTotal"));  
	                    nei_zxxq.this.getActivity().sendBroadcast(intent);  
					    imageUrl =data.getString("linkUrl");
						
						 new Thread() {  
							  
			                    @Override  
			                    public void run() {  
			                        // 需要花时间计算的方法  
			                        try {  
			                                              
			                             String str = posturl(imageUrl);                            
			                             webdata=str;
			                             Message message = new Message( );
			                        	  message.what = 1;
			                        	  handlerpost.sendMessage(message);
			                        } catch (Exception e) {  
			                            // TODO: handle exception  
			                        }  
			  
			                    }  
			                }.start();  
			    
						//zxxq_img.setImageUrl(imageUrl,webpost.getImageLoader());
						//mWebFlash.getSettings().setDefaultTextEncodingName("utf-8");
						//mWebFlash.loadData(imageUrl,"text/html; charset=UTF-8", null);
						//mWebFlash.loadUrl(imageUrl);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
			}
			
			};
			handler_.SetProgressDialog(nei_zxxq.this.getActivity());
			
	}
	public void onclick(View v)
	{
	if(v.getId()==R.id.nei_pl)
	{
		//nei_pl.setVisibility(View.GONE);
	}
	else if(v.getId()==R.id.nei_zxxq_more)
	{
		
	}
	else if(v.getId()==R.id.nei_bj_jf)
	{
		
	}
	
	}

//	@Override
//	public void onStart() {
//		// TODO Auto-generated method stub
//		super.onStart();
//		  Map<String, String> params = new HashMap<String, String>();
//		  params.put("newsId",Constant.zxid);
//		
//		  handler_.SetRequest(new RequestType("3",Type.getNewsDetail),params);
//	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("nei_zxxq");
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("nei_zxxq");
	}
//	@Override
//	public void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		nei_zxxq.this.getActivity().unregisterReceiver(myReceiver);
//	}
	
	public static String ToDBC(String input) {  
	   char[] c = input.toCharArray();  
	   for (int i = 0; i< c.length; i++) {  
	       if (c[i] == 12288) {  
	         c[i] = (char) 32;  
	         continue;  
	       }if (c[i]> 65280&& c[i]< 65375)  
	          c[i] = (char) (c[i] - 65248);  
	       }  
	   return new String(c);  
	}  
	public String posturl(String url){  
	       InputStream is = null;  
	       String result = "";  
	  
	       try{  
	           HttpClient httpclient = new DefaultHttpClient();  
	           HttpPost httppost = new HttpPost(url);  
	           HttpResponse response = httpclient.execute(httppost);  
	           HttpEntity entity = response.getEntity();  
	           is = entity.getContent();  
	       }catch(Exception e){  
	           return "Fail to establish http connection!"+e.toString();  
	       }  
	  
	       try{  
	           BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));  
	           StringBuilder sb = new StringBuilder();  
	           String line = null;  
	           while ((line = reader.readLine()) != null) {  
	               sb.append(line + "\n");  
	           }  
	           is.close();  
	  
	           result=sb.toString();  
	          
	       }catch(Exception e){  
	           return "Fail to convert net stream!";  
	       }  
	  
	       return result;  
	   }  
}