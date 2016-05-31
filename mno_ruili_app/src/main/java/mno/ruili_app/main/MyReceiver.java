package mno.ruili_app.main;

import mno.ruili_app.Constant;
import mno_ruili_app.home.home_video;
import mno_ruili_app.nei.nei_wdxq;
import mno_ruili_app.nei.nei_zx;

import org.json.JSONException;
import org.json.JSONObject;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
                        
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        	processCustomMessage(context, bundle);
        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            
            Intent intent2 = new Intent();
            intent2.setAction("mno.push");
			context.sendBroadcast(intent2);
        	
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            
           /* Intent ii = new Intent(context, home_video.class);
        	ii.putExtras(bundle);
        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        	context.startActivity(ii);*/
            String a=bundle.getString((JPushInterface.EXTRA_EXTRA));
                        
            JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(a);
				if(Constant.displayWidth==0){
					Intent i = new Intent(context, LauncherActivity.class);
					context.startActivity(i);
					return;
				}
				
				 if(jsonObject.getString("type").equals("course")){
		        	//打开自定义的Activity
		        	Intent i = new Intent(context, home_video.class);
		        	Constant.itemid=jsonObject.getString("id");
		        	i.putExtras(bundle);
		        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
		        	context.startActivity(i);
		          }
		          if(jsonObject.getString("type").equals("news")){
		        	//打开自定义的Activity
		        	Intent i = new Intent(context, nei_zx.class);
		        	//Constant.zxid=mzxList.get(position).getid();
		        	Constant.zxid=jsonObject.getString("id");
		        	i.putExtras(bundle);
		        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
		        	context.startActivity(i);
		            }
		        //1.打开我的问答的推送
		          if(jsonObject.getString("type").equals("question")){
		        	//打开自定义的Activity
		        	Intent i = new Intent(context, nei_wdxq.class);
		        	Constant.itemid=jsonObject.getString("id");
		        	Constant.msg_id=jsonObject.getString("msg_id");
		        	i.putExtras(bundle);
		        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
		        	context.startActivity(i);
		          }
		          //2.打开系统消息的推送
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
            
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        	Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
        	Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} 
			else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
	
	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
		if (Main.isForeground) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Intent msgIntent = new Intent(Main.MESSAGE_RECEIVED_ACTION);
			msgIntent.putExtra(Main.KEY_MESSAGE, message);
			if (!ExampleUtil.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (null != extraJson && extraJson.length() > 0) {
						msgIntent.putExtra(Main.KEY_EXTRAS, extras);
					}
				} catch (JSONException e) {

				}

			}
			context.sendBroadcast(msgIntent);
		}
	}
}
