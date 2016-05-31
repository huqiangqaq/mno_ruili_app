package mno_ruili_app.net;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mno.ruili_app.appuser;
import mno.ruili_app.main.ExampleUtil;
import mno.ruili_app.my.mylogin;
import mno_ruili_app.net.RequestType.Type;

import org.json.JSONException;
import org.json.JSONObject;



import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;


public abstract class loginhandler extends webhandler{
	String pass_;
	String user_;
	public void Login(Context context,String account,String password)
	{
		//String code =accessCode(account);
		context_ = context;
		Map<String, String> params = new HashMap<String, String>();
		
		user_ = account;
		pass_ = password;
		
		params.put("loginname",user_);
		
	    params.put("password",pass_);
	
		//params.put("accessCode", code);
		
		this.SetRequest(new RequestType("",Type.login),params);
		
	}
	
	public void Login(Context context,String account)
	{
		//String code =accessCode(account);
		context_ = context;
		Map<String, String> params = new HashMap<String, String>();
		user_ = account;
		params.put("loginname",user_);
		//params.put("accessCode", code);
		
		this.SetRequest(new RequestType("",Type.login),params);
		
	}

	@Override
	public void OnResponse(JSONObject response) {
		// TODO Auto-generated method stub
		super.OnResponse(response);		
		try {
			if(response.length()>0){
				JSONObject data = response.getJSONObject("data");
				JSONObject json = null;
				//Log.e("LoginHandler", response.toString());
				//json = data.getJSONObject("userInfo");
				mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, user_));
				appuser.getInstance().OnLoginSuccess(data);//存储个人数据的json字符串到sp中
				OnLoginSuccess(data);//调用抽象方法
			}
			else{
				  Intent intent = new Intent(context_, mylogin.class); 				    
				   context_.startActivity(intent);
			}							
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			this.OnError(-1, e.getMessage());
		}			
	}
	
	public abstract void  OnLoginSuccess(JSONObject response);
	
	
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
                if (ExampleUtil.isConnected(context_)) {
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
	                JPushInterface.setAliasAndTags(context_, (String) msg.obj, null, mAliasCallback);
	                break;
	                
	         
	            default:
	               // Log.i(TAG, "Unhandled msg - " + msg.what);
	            }
	        }
	    };
}
