package mno.ruili_app.my.im;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mno.ruili_app.my.Job_details;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.RequestType.Type;
import mno_ruili_app.net.webhandler;

import com.alibaba.fastjson.JSON;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseChatFragment.EaseChatFragmentListener;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.util.EasyUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;
//自己的
public class ChatFragment extends EaseChatFragment implements EaseChatFragmentListener{
	
	private static final int MESSAGE_TYPE_SENT_RESUME = 1;
    private static final int MESSAGE_TYPE_RECV_RESUME = 2;
    private static final int MESSAGE_TYPE_SENT_POST = 3; 
    private static final int MESSAGE_TYPE_RECV_POST = 4;
    
    /**
     * 是否为环信小助手
     */
    private boolean isRobot;
    ChatRowMyEntity entity;
    ChatRowPostEntity pentity;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);     
    }
    String myjobName ,mysalary ,myworkTime , myeducation ,myworkPlace,mycompanyName ;
    String[] Msg;
    String resumeOrPost_id;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState); 
    	if(fragmentArgs.getString("from")!=null){
    		String from=fragmentArgs.getString("from");
        	if(from.equals("details")){
        		resumeOrPost_id=fragmentArgs.getString("resumeOrPost_id");	
            	String state=fragmentArgs.getString("state");
            	String flag=fragmentArgs.getString("flag");
            	if(state.equals("1")&&flag.equals("worker")){//自己岗位消息的获取
            		loadJobMsg();
            	}else if(state.equals("1")&&flag.equals("job")){//自己简历消息的获取
            		loadWorkerMsg();	
            	}else if(state.equals("0")&&flag.equals("worker")){
            		String mess=fragmentArgs.getString("mess");
//            		if(mess.equals("对方还没有填写邮箱")){
//            			loadJobMsg();
//            			Toast.makeText(ChatFragment.this.getActivity(), mess, 1000).show();
//            		}else if(mess.equals("你已经发送过一次")){
//            			Toast.makeText(ChatFragment.this.getActivity(), mess, 1000).show();
//            		}else if(mess.equals("你发送职位的不是相匹配的职位")){
//            			Toast.makeText(ChatFragment.this.getActivity(), mess, 1000).show();
//            		}
            		if(mess.equals("你发送职位的不是相匹配的职位")||mess.equals("你已经发送过一次")){
            			Toast.makeText(ChatFragment.this.getActivity(), mess, 1000).show();
            		}else{
            			loadJobMsg();
            			Toast.makeText(ChatFragment.this.getActivity(), mess, 1000).show();
            		}
            	}else if(state.equals("0")&&flag.equals("job")){
            		String mess=fragmentArgs.getString("mess");
            		if(mess.equals("对方还没有填写邮箱")){
            			loadWorkerMsg();	
            			Toast.makeText(ChatFragment.this.getActivity(), mess, 1000).show();
            		}else if(mess.equals("你已经发送过一次")){
            			//loadWorkerMsg();	//
            			Toast.makeText(ChatFragment.this.getActivity(), mess, 1000).show();
            		}        		
            	}
        	} 
    	}   	       
    }
    //自己岗位消息的获取
    private void loadJobMsg() {
    	webhandler handler3 = new webhandler() {
			public void OnResponse(org.json.JSONObject response) {
				try {
					JSONObject data = response.getJSONObject("data");
					JSONObject type_json= data.getJSONObject("post");

					myjobName = type_json.getString("job_name");
					mysalary = type_json.getString("salary");
					myworkTime = type_json.getString("job_year");
					myeducation = type_json.getString("education");
					myworkPlace = type_json.getString("job_addr");
					// 亮点名字
					JSONArray points = type_json.getJSONArray("points");
					Msg = new String[points.length()];
					for (int j = 0; j < points.length(); j++) {
						JSONObject item = points.getJSONObject(j);
						String point_name = item.getString("point_name");
						Msg[j] = point_name;
					}
					pentity=new ChatRowPostEntity(myjobName, myworkTime, myeducation, mysalary, myworkPlace);
					sendPostTxtMessage();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		Map<String, String> params3 = new HashMap<String, String>();
		params3.put("id", resumeOrPost_id);
		handler3.SetRequest(new RequestType("ZP", Type.getPostDetail), params3);
    }
    //自己简历消息的获取
    private void loadWorkerMsg(){
    	webhandler handler3 = new webhandler() {
			public void OnResponse(org.json.JSONObject response) {
				try {
					JSONObject type_json = response.getJSONObject("data");

					myjobName = type_json.getString("job_name");
					mysalary = type_json.getString("salary");
					myworkTime = type_json.getString("job_year");
					myeducation = type_json.getString("education");
					myworkPlace = type_json.getString("job_addr");
					// 亮点名字
					JSONArray points = type_json.getJSONArray("points");
					Msg = new String[points.length()];
					for (int j = 0; j < points.length(); j++) {
						JSONObject item = points.getJSONObject(j);
						String point_name = item.getString("point_name");
						Msg[j] = point_name;
					}
					entity=new ChatRowMyEntity(myjobName, myworkTime, myeducation, mysalary, myworkPlace);
					//entity=new ChatRowMyEntity(myjobName, myworkTime, myeducation, mysalary, myworkPlace,Msg);
					sendResumeTxtMessage();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		Map<String, String> params3 = new HashMap<String, String>();
		params3.put("resume_id", resumeOrPost_id);
		handler3.SetRequest(new RequestType("ZP", Type.getMyResume), params3);
    }
    /*
     * 发送自定义的简历消息的方法
     */
	private void sendResumeTxtMessage(){	    	
		EMMessage message = EMMessage.createTxtSendMessage("发送简历信息", toChatUsername);
		JSONObject jsonMsgType = entity.getJSONObject();
		//使用fastjson解析实体类
//		String json = JSON.toJSONString(entity);
//		JSONObject jsonMsgType=null;
//		try {
//			jsonMsgType = new JSONObject(json);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		if(jsonMsgType != null){
			message.setAttribute("msgResume", jsonMsgType);
			sendMessage(message);
		}
    }
	/*
     * 发送自定义的岗位消息的方法
     */
	private void sendPostTxtMessage(){	    	
		EMMessage message = EMMessage.createTxtSendMessage("发送岗位信息", toChatUsername);
		JSONObject jsonMsgType = pentity.getJSONObject();
		if(jsonMsgType != null){
			message.setAttribute("msgPost", jsonMsgType);
			sendMessage(message);
		}
    }
    @Override
    protected void setUpView() {
        setChatFragmentListener(this);
//        if (chatType == Constant.CHATTYPE_SINGLE) { 
//            Map<String,RobotUser> robotMap = DemoHelper.getInstance().getRobotList();
//            if(robotMap!=null && robotMap.containsKey(toChatUsername)){
//                isRobot = true;
//            }
//        }        
        super.setUpView();
        // 设置标题栏点击事件
        titleBar.setLeftLayoutClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (EasyUtils.isSingleActivity(getActivity())) {
                    Intent intent = new Intent(getActivity(), Job_details.class);
                    startActivity(intent);
                }
                getActivity().finish();
            }
        });
        //((EaseEmojiconMenu)inputMenu.getEmojiconMenu()).addEmojiconGroup(EmojiconData.getData());
    }
    
    @Override
    public void onSetMessageAttributes(EMMessage message) {
        if(isRobot){
            //设置消息扩展属性
            message.setAttribute("em_robot_message", isRobot);
        }
        
    }
    
    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        //设置自定义listview item提供者
        return new CustomChatRowProvider();
    }
  

    @Override
    public void onEnterToChatDetails() {
//        if (chatType == Constant.CHATTYPE_GROUP) {
//            EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
//            if (group == null) {
//                Toast.makeText(getActivity(), R.string.gorup_not_found, 0).show();
//                return;
//            }
//            startActivityForResult(
//                    (new Intent(getActivity(), GroupDetailsActivity.class).putExtra("groupId", toChatUsername)),
//                    REQUEST_CODE_GROUP_DETAIL);
//        }else if(chatType == Constant.CHATTYPE_CHATROOM){
//        	startActivityForResult(new Intent(getActivity(), ChatRoomDetailsActivity.class).putExtra("roomId", toChatUsername), REQUEST_CODE_GROUP_DETAIL);
//        }
    }

    @Override
    public void onAvatarClick(String username) {
        //头像点击事件
//        Intent intent = new Intent(getActivity(), UserProfileActivity.class);
//        intent.putExtra("username", username);
//        startActivity(intent);
    }
    
    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        //消息框点击事件，demo这里不做覆盖，如需覆盖，return true
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {
        //消息框长按
//        startActivityForResult((new Intent(getActivity(), ContextMenuActivity.class)).putExtra("message",message),
//                REQUEST_CODE_CONTEXT_MENU);
    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
//        switch (itemId) {
//        case ITEM_VIDEO: //视频
//            Intent intent = new Intent(getActivity(), ImageGridActivity.class);
//            startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
//            break;
//        case ITEM_FILE: //一般文件
//            //demo这里是通过系统api选择文件，实际app中最好是做成qq那种选择发送文件
//            selectFileFromLocal();
//            break;
//        case ITEM_VOICE_CALL: //音频通话
//            startVoiceCall();
//            break;
//        case ITEM_VIDEO_CALL: //视频通话
//            startVideoCall();
//            break;
//
//        default:
//            break;
//        }
        //不覆盖已有的点击事件
        return false;
    }
    
    /**
     * 选择文件
     */
    
    /**
     * 拨打语音电话
     */
    protected void startVoiceCall() {
//        if (!EMClient.getInstance().isConnected()) {
//            Toast.makeText(getActivity(), R.string.not_connect_to_server, 0).show();
//        } else {
//            startActivity(new Intent(getActivity(), VoiceCallActivity.class).putExtra("username", toChatUsername)
//                    .putExtra("isComingCall", false));
//            // voiceCallBtn.setEnabled(false);
//            inputMenu.hideExtendMenuContainer();
//        }
    }
    
    /**
     * 拨打视频电话
     */
    protected void startVideoCall() {
//        if (!EMClient.getInstance().isConnected())
//            Toast.makeText(getActivity(), R.string.not_connect_to_server, 0).show();
//        else {
//            startActivity(new Intent(getActivity(), VideoCallActivity.class).putExtra("username", toChatUsername)
//                    .putExtra("isComingCall", false));
//            // videoCallBtn.setEnabled(false);
//            inputMenu.hideExtendMenuContainer();
//        }
    }
    
    /**
     * chat row provider 
     *
     */
    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
        @Override
        public int getCustomChatRowTypeCount() {
            //音、视频通话发送、接收共4种
            return 4;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            if(message.getType() == EMMessage.Type.TXT){
                //语音通话类型
//                if (message.getBooleanAttribute(MConstant.MESSAGE_ATTR_IS_VOICE_CALL, false)){
//                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL : MESSAGE_TYPE_SENT_VOICE_CALL;
//                }else if (message.getBooleanAttribute(MConstant.MESSAGE_ATTR_IS_VIDEO_CALL, false)){
//                    //视频通话
//                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL : MESSAGE_TYPE_SENT_VIDEO_CALL;
//                }
            	
            	if(AppHelper.getInstance().isResumeTxtMessage(message)){
        			return message.direct()==EMMessage.Direct.RECEIVE?MESSAGE_TYPE_RECV_RESUME : MESSAGE_TYPE_SENT_RESUME;
        		}else if(AppHelper.getInstance().isPostTxtMessage(message)){
        			return message.direct()==EMMessage.Direct.RECEIVE?MESSAGE_TYPE_RECV_POST: MESSAGE_TYPE_SENT_POST;
        		}
            }
            return 0;
        }

        @Override
        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
            if(message.getType() == EMMessage.Type.TXT){
//                // 语音通话,  视频通话
//                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false) ||
//                    message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)){
//                    return new ChatRowVoiceCall(getActivity(), message, position, adapter);
//                }
            	
            	if(AppHelper.getInstance().isResumeTxtMessage(message)){
        			return new EaseChatRowResume(getActivity(), message, position, adapter);
        		}else if(AppHelper.getInstance().isPostTxtMessage(message)){
        			return new EaseChatRowPost(getActivity(), message, position, adapter);
        		}
            }
            return null;
        }

    }
    
}
