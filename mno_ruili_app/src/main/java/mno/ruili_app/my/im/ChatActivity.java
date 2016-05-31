package mno.ruili_app.my.im;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.util.EasyUtils;
import com.hyphenate.util.NetUtils;
import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.R;
import mno.ruili_app.my.Job_details;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
//自己的
public class ChatActivity extends BaseActivity{
	public static ChatActivity activityInstance;
	private EaseChatFragment chatFragment;
	String toChatUsername;//聊天时对方的环信账号
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_activity);		
		//注册一个监听连接状态的listener
		EMClient.getInstance().addConnectionListener(new MyConnectionListener());
		toChatUsername = getIntent().getExtras().getString("userId");//对方的环信uid
		chatFragment = new ChatFragment();      
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
        //为了获得聊天双方的头像
        AppHelper.getInstance().init(ChatActivity.this);	
	}
	@Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }
    
    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
        if (EasyUtils.isSingleActivity(this)) {
            Intent intent = new Intent(this, Job_details.class);
            startActivity(intent);
        }
    }
    
    public String getToChatUsername(){
        return toChatUsername;
    }     
    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
    	public void onConnected() {
    	}
    	@Override
    	public void onDisconnected(final int error) {
    		runOnUiThread(new Runnable() {
     
    			@Override
    			public void run() {
    				if(error == EMError.USER_REMOVED){
    					// 显示帐号已经被移除
    					Toast.makeText(ChatActivity.this, "你的帐号已经被移除",  Toast.LENGTH_SHORT).show();
    				}else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
    					// 显示帐号在其他设备登陆
    					Toast.makeText(ChatActivity.this, "你的帐号在其他设备登陆",  Toast.LENGTH_SHORT).show();
    				} else {
	    				if (NetUtils.hasNetwork(ChatActivity.this)){
	    					//连接不到聊天服务器
	    					Toast.makeText(ChatActivity.this, "连接不到聊天服务器",  Toast.LENGTH_SHORT).show();
	    				}	    					
	    				else{
	    					//当前网络不可用，请检查网络设置
	    					Toast.makeText(ChatActivity.this, "当前网络不可用，请检查网络设置",  Toast.LENGTH_SHORT).show();
	    				}
	    					
    				}
    			}
    		});
    	}
    }
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("ChatActivity");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("ChatActivity");
		MobclickAgent.onPause(this);
	}
}
