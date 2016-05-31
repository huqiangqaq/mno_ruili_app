package mno.ruili_app.my.im;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.adapter.EMAConversation;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.controller.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.domain.EaseUser;

public class AppHelper {
	String userId,bigImg,nickname;
	private EaseUI easeUI;
	private UserProfileManager userProManager;
	private static AppHelper instance = null;
	//private InviteMessgeDao inviteMessgeDao;
	//private UserDao userDao;
	private Context appContext;
	private String username;
	//private DemoModel demoModel = null;
	public synchronized static AppHelper getInstance() {
		if (instance == null) {
			instance = new AppHelper();
		}
		return instance;
	}
	public void init(Context context) {
	    EMOptions options = initChatOptions();
	    //EaseUI.getInstance().init(context,options);
	    //demoModel = new DemoModel(context);
	    //options传null则使用默认的
		if (EaseUI.getInstance().init(context, options)) {
		    
			appContext = context;
		    //设为调试模式，打成正式包时，最好设为false，以免消耗额外的资源
		    EMClient.getInstance().setDebugMode(true);
		    //get easeui instance
		    easeUI = EaseUI.getInstance();
		    //调用easeui的api设置providers
		    setEaseUIProviders();
		    //initDbDao();
		}
	}
	
	public boolean isResumeTxtMessage(EMMessage message){
    	JSONObject jsonObj = null;
    	try {
			jsonObj = message.getJSONObjectAttribute("msgResume");
		} catch (Exception e) {
		}
    	if(jsonObj == null){
			return false;
		}
		if(jsonObj.has("order")){
			return true;
		}
		return false;
    }
	public boolean isPostTxtMessage(EMMessage message){
    	JSONObject jsonObj = null;
    	try {
			jsonObj = message.getJSONObjectAttribute("msgPost");
		} catch (Exception e) {
		}
    	if(jsonObj == null){
			return false;
		}
		if(jsonObj.has("track")){
			return true;
		}
		return false;
    }
	
	protected void setEaseUIProviders() {
        //需要easeui库显示用户头像和昵称设置此provider
        easeUI.setUserProfileProvider(new EaseUserProfileProvider() {
            
            @Override
            public EaseUser getUser(String username) {
            	boolean isReturn = false;
                EaseUser easeUser = null;
            	for(EaseUser user:users){
            		if(user.getUsername().equals(username)){
            			 isReturn = true;
                         easeUser = user;
            			//return user;
            		}else if(username.equals(EMClient.getInstance().getCurrentUser())){
            			 isReturn = true;
            			 //easeUser = user;
            			return getUserProfileManager().getCurrentUserInfo();
            		}
            	}
            	 if (!isReturn) {
                     getUserInfo(username);
                     easeUser = new EaseUser(username);
                 }
//            	getUserInfo(username);
//                return new EaseUser(username);    
            	 return easeUser;
            }
        });
	}
	//得到单个的用户信息
	private EaseUser getSingleUserInfo(String username){//return getSingleUserInfo(username);
		if(username.equals(EMClient.getInstance().getCurrentUser())){
			return getUserProfileManager().getCurrentUserInfo();
		}            
		EaseUser euser=new EaseUser(username);	
		userId=username.substring(7);
		//根据userId获取自己服务器的头像和昵称
		webhandler handler=new webhandler(){
			@Override
			public void OnResponse(JSONObject response) {
				// TODO Auto-generated method stub
				super.OnResponse(response);
				try {
					JSONArray data =response.getJSONArray("data");
					for (int j = 0; j < data.length(); j++) {
						JSONObject user=data.getJSONObject(j);
						bigImg=user.getString("bigImg");
						nickname=user.getString("username");	
					}					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		Map<String, String> params = new HashMap<String, String>();
		params.put("uids", userId);
		handler.SetRequest(new RequestType("", Type.getUserNameAndPhoto), params);
		String imageUrl =RequestType.getWebUrl_(bigImg);
		euser.setNick(nickname);
		euser.setAvatar(imageUrl );
		return euser;
	}
	//得到回话列表的所有用户信息
	List<EaseUser> users=new ArrayList<EaseUser>();	
	private List<EaseUser> getUserInfo(final String username){          			
		String userId=username.substring(7);
		//根据userId获取自己服务器的头像和昵称
		webhandler handler=new webhandler(){
			@Override
			public void OnResponse(JSONObject response) {
				// TODO Auto-generated method stub
				super.OnResponse(response);
				try {
					JSONArray data =response.getJSONArray("data");
					for (int j = 0; j < data.length(); j++) {
						JSONObject user=data.getJSONObject(j);
						bigImg=user.getString("bigImg");
						nickname=user.getString("username");
						EaseUser euser=new EaseUser(username);
						String imageUrl =RequestType.getWebUrl_(bigImg);
						euser.setNick(nickname);
						euser.setAvatar(imageUrl );
						users.add(euser);
						//利用广播刷新UI
						Intent intent=new Intent();
						intent.setAction("huihua");
						appContext.sendBroadcast(intent);
					}					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		Map<String, String> params = new HashMap<String, String>();
		params.put("uids", userId);
		handler.SetRequest(new RequestType("", Type.getUserNameAndPhoto), params);
		return users;        
	}
//	private void initDbDao() {
//        inviteMessgeDao = new InviteMessgeDao(appContext);
//        userDao = new UserDao(appContext);
//    }
	/**
     * 获取当前用户的环信id
     */
//    public String getCurrentUsernName(){
//    	if(username == null){
//    		username = demoModel.getCurrentUsernName();
//    	}
//    	return username;
//    }
	
	public UserProfileManager getUserProfileManager() {
		if (userProManager == null) {
			userProManager = new UserProfileManager();
		}
		return userProManager;
	}
	private EMOptions initChatOptions(){
        
        // 获取到EMChatOptions对象
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        //options.setAcceptInvitationAlways(false);
        // 设置是否需要已读回执
        //options.setRequireAck(true);
        // 设置是否需要已送达回执
        //options.setRequireDeliveryAck(false);
        // 设置从db初始化加载时, 每个conversation需要加载msg的个数
        
        //options.setNumberOfMessagesLoaded(1);
        
        //使用gcm和mipush时，把里面的参数替换成自己app申请的
        //设置google推送，需要的GCM的app可以设置此参数
        //options.setGCMNumber("324169311137");
        //在小米手机上当app被kill时使用小米推送进行消息提示，同GCM一样不是必须的
        //options.setMipushConfig("2882303761517426801", "5381742660801");
        
        return options;
//        notifier.setNotificationInfoProvider(getNotificationListener());
    }
}
