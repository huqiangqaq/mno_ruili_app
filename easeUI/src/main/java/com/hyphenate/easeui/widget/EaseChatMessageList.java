package com.hyphenate.easeui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.adapter.EaseMessageAdapter;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

public class EaseChatMessageList extends RelativeLayout{
    
    protected static final String TAG = "EaseChatMessageList";
    protected ListView listView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected Context context;
    protected EMConversation conversation;
    protected int chatType;
    protected String toChatUsername;
    protected EaseMessageAdapter messageAdapter;
    protected boolean showUserNick;
    protected boolean showAvatar;
    protected Drawable myBubbleBg;
    protected Drawable otherBuddleBg;
//    LinearLayout ll_content1,ll_content2;
//    TextView tv_jobName1,tv_salary1,tv_workTime1,tv_education1,tv_workPlace1;
//    TextView tv_jobName2,tv_salary2,tv_workTime2,tv_education2,tv_workPlace2;
//    TagFlowLayout fl_workerMsg,fl_jobMsg;

	public EaseChatMessageList(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public EaseChatMessageList(Context context, AttributeSet attrs) {
    	super(context, attrs);
    	parseStyle(context, attrs);
    	init(context);
    }

    public EaseChatMessageList(Context context) {
        super(context);
        init(context);
    }
    LayoutInflater mInflater;
    private void init(Context context){
        this.context = context;
        mInflater = (LayoutInflater) LayoutInflater.from(context);
        LayoutInflater.from(context).inflate(R.layout.ease_chat_message_list, this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.chat_swipe_layout);
        listView = (ListView) findViewById(R.id.list);
        //发简历的邮件到公司邮箱
//        ll_content1=(LinearLayout) findViewById(R.id.ll_content1);
//        tv_jobName1=(TextView) findViewById(R.id.tv_jobName1);
//        tv_salary1=(TextView) findViewById(R.id.tv_salary1);
//        tv_workTime1=(TextView) findViewById(R.id.tv_workTime1);
//        tv_education1=(TextView) findViewById(R.id.tv_education1);
//        tv_workPlace1=(TextView) findViewById(R.id.tv_workPlace1);
//        fl_workerMsg=(TagFlowLayout) findViewById(R.id.fl_workerMsg);        
//        //发邀请的邮件到个人邮箱
//        ll_content2=(LinearLayout) findViewById(R.id.ll_content2);
//        tv_jobName2=(TextView) findViewById(R.id.tv_jobName2);
//        tv_salary2=(TextView) findViewById(R.id.tv_salary2);
//        tv_workTime2=(TextView) findViewById(R.id.tv_workTime2);
//        tv_education2=(TextView) findViewById(R.id.tv_education2);
//        tv_workPlace2=(TextView) findViewById(R.id.tv_workPlace2);
//        fl_jobMsg=(TagFlowLayout) findViewById(R.id.fl_jobMsg);
    }
    
    /**
     * init widget
     * @param toChatUsername
     * @param chatType
     * @param customChatRowProvider
     */
    public void init(String toChatUsername, int chatType, EaseCustomChatRowProvider customChatRowProvider) {
        this.chatType = chatType;
        this.toChatUsername = toChatUsername;
//        if(jobName.length()!=0){
//        	if(flag.equals("job")){
//           	 ll_content1.setVisibility(View.VISIBLE);
//           	 tv_jobName1.setText(jobName);
//                tv_salary1.setText(salary);
//                tv_workTime1.setText(workTime);
//                tv_education1.setText(education);
//                tv_workPlace1.setText(workPlace); 
//                fl_workerMsg.setAdapter(new TagAdapter<String>(Msg) {
//        			@Override
//        			public View getView(FlowLayout parent, int position,
//        					String t) {
//        				TextView tv_jobMsg = (TextView) mInflater.inflate(
//        						R.layout.tv, fl_workerMsg, false);
//        				tv_jobMsg.setText(t);
//        				return tv_jobMsg;
//        			}
//        		});
//           }else if(flag.equals("worker")){
//           	ll_content2.setVisibility(View.VISIBLE);
//           	 tv_jobName2.setText(jobName);
//                tv_salary2.setText(salary);
//                tv_workTime2.setText(workTime);
//                tv_education2.setText(education);
//                tv_workPlace2.setText(workPlace); 
//                fl_jobMsg.setAdapter(new TagAdapter<String>(Msg) {
//        			@Override
//        			public View getView(FlowLayout parent, int position,
//        					String t) {
//        				TextView tv_jobMsg = (TextView) mInflater.inflate(
//        						R.layout.tv, fl_jobMsg, false);
//        				tv_jobMsg.setText(t);
//        				return tv_jobMsg;
//        			}
//        		});
//           }
//        }else{
//        	if(flag.equals("msg")){
//        		ll_content1.setVisibility(View.GONE);
//           	 	ll_content2.setVisibility(View.GONE);
//        	}else{
//        		ll_content1.setVisibility(View.GONE);
//           	 	ll_content2.setVisibility(View.GONE);
//           	 	Toast.makeText(context, "没有匹配的岗位！", 1000).show();
//        	}        	 
//        }               
        
        conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername, EaseCommonUtils.getConversationType(chatType), true);
        messageAdapter = new EaseMessageAdapter(context, toChatUsername, chatType, listView);
        messageAdapter.setShowAvatar(showAvatar);
        messageAdapter.setShowUserNick(showUserNick);
        messageAdapter.setMyBubbleBg(myBubbleBg);
        messageAdapter.setOtherBuddleBg(otherBuddleBg);
        messageAdapter.setCustomChatRowProvider(customChatRowProvider);
        // 设置adapter显示消息
        listView.setAdapter(messageAdapter);
        
        refreshSelectLast();
    }
    
    protected void parseStyle(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EaseChatMessageList);
        showAvatar = ta.getBoolean(R.styleable.EaseChatMessageList_msgListShowUserAvatar, true);
        myBubbleBg = ta.getDrawable(R.styleable.EaseChatMessageList_msgListMyBubbleBackground);
        otherBuddleBg = ta.getDrawable(R.styleable.EaseChatMessageList_msgListMyBubbleBackground);
        showUserNick = ta.getBoolean(R.styleable.EaseChatMessageList_msgListShowUserNick, false);
        ta.recycle();
    }
    
    
    /**
     * 刷新列表
     */
    public void refresh(){
        if (messageAdapter != null) {
            messageAdapter.refresh();
        }
    }
    
    /**
     * 刷新列表，并且跳至最后一个item
     */
    public void refreshSelectLast(){
        if (messageAdapter != null) {
            messageAdapter.refreshSelectLast();
        }
    }
    
    /**
     * 刷新页面,并跳至给定position
     * @param position
     */
    public void refreshSeekTo(int position){
        if (messageAdapter != null) {
            messageAdapter.refreshSeekTo(position);
        }
    }
    
    

    /**
     * 获取listview
     * @return
     */
	public ListView getListView() {
		return listView;
	} 
	
	/**
	 * 获取SwipeRefreshLayout
	 * @return
	 */
	public SwipeRefreshLayout getSwipeRefreshLayout(){
	    return swipeRefreshLayout;
	}
	
	public EMMessage getItem(int position){
	    return messageAdapter.getItem(position);
	}
	
	/**
	 * 设置是否显示用户昵称
	 * @param showUserNick
	 */
	public void setShowUserNick(boolean showUserNick){
	    this.showUserNick = showUserNick;
	}
	
	public boolean isShowUserNick(){
	    return showUserNick;
	}
	
	public interface MessageListItemClickListener{
	    void onResendClick(EMMessage message);
	    /**
	     * 控件有对气泡做点击事件默认实现，如果需要自己实现，return true。
	     * 当然也可以在相应的chatrow的onBubbleClick()方法里实现点击事件
	     * @param message
	     * @return
	     */
	    boolean onBubbleClick(EMMessage message);
	    void onBubbleLongClick(EMMessage message);
	    void onUserAvatarClick(String username);
	}
	
	/**
	 * 设置list item里控件的点击事件
	 * @param listener
	 */
	public void setItemClickListener(MessageListItemClickListener listener){
        if (messageAdapter != null) {
            messageAdapter.setItemClickListener(listener);
        }
	}
	
	/**
	 * 设置自定义chatrow提供者
	 * @param rowProvider
	 */
	public void setCustomChatRowProvider(EaseCustomChatRowProvider rowProvider){
        if (messageAdapter != null) {
            messageAdapter.setCustomChatRowProvider(rowProvider);
        }
    }
}
