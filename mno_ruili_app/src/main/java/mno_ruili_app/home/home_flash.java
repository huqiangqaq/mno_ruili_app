package mno_ruili_app.home;

import mno.ruili_app.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class home_flash extends Activity{
	  WebView mWebFlash;
	 
	  String url="<html>"+
			  "<iframe src=\"http://www.tudou.com/programs/view/html5embed.action?type=2&code=DCemcsRnmuY&lcode=StDyVAcEEkA&resourceId=0_06_05_99\" allowtransparency=\"true\" allowfullscreen=\"true\" allowfullscreenInteractive=\"true\" scrolling=\"no\" border=\"0\" frameborder=\"0\" style=\"width:480px;height:400px;\"></iframe>"
			  +
			  "</html>";
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_flash);
	    init();
	    loadFlash();
	    mWebFlash.loadUrl("http://www.tudou.com/programs/view/html5embed.action?type=2&code=DCemcsRnmuY&lcode=StDyVAcEEkA&resourceId=0_06_05_99");
	     //mWebFlash.loadDataWithBaseURL(null, url, "text/html","utf-8", null);
}
	
	public void loadFlash() {
	     mWebFlash = (WebView) findViewById(R.id.newsContent);
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
	private void init() {
		// TODO Auto-generated method stub
		
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
    @Override
    protected void onResume() {
       super.onResume();
       //恢复播放
       mWebFlash.resumeTimers();
    }
     
    @Override
    protected void onPause() {
       super.onPause();
       //暂停播放
       mWebFlash.pauseTimers();
    }
     
    @Override
    protected void onDestroy() {
       super.onDestroy();
       //一定要销毁，否则无法停止播放
       mWebFlash.destroy();
    }
}