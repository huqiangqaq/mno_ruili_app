package com.hyphenate.easeui.widget.chatrow;

import org.json.JSONObject;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.exceptions.HyphenateException;

import android.content.Context;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class EaseChatRowText extends EaseChatRow{

	private TextView contentView;
	int position;//
//	TextView tv_jobName1,tv_salary1,tv_workTime1,tv_education1,tv_workPlace1;
//	View vi;

    public EaseChatRowText(Context context, EMMessage message, int position, BaseAdapter adapter) {
		super(context, message, position, adapter);
		this.position=position;//
	}

	@Override
	protected void onInflatView() {
		inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
				R.layout.ease_row_received_message : R.layout.ease_row_sent_message, this);
//		if(position==0){
//			LinearLayout v=(LinearLayout) inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
//					R.layout.ease_row_received_message : R.layout.ease_row_sent_message, this);
//			vi=inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ? 
//	                R.layout.receive_email_comp : R.layout.send_email_comp, this);
//			v.addView(vi);
//		}
	}

	@Override
	protected void onFindViewById() {
		contentView = (TextView) findViewById(R.id.tv_chatcontent);
//    	tv_jobName1=(TextView) vi.findViewById(R.id.tv_jobName1);
//        tv_salary1=(TextView) vi.findViewById(R.id.tv_salary1);
//        tv_workTime1=(TextView) vi.findViewById(R.id.tv_workTime1);
//        tv_education1=(TextView) vi.findViewById(R.id.tv_education1);
//        tv_workPlace1=(TextView) vi.findViewById(R.id.tv_workPlace1);	
	}

    @Override
    public void onSetUpView() {
    	
//    	if(position==0){
//    		JSONObject mjson = null;
//        	try {
//    				mjson = message.getJSONObjectAttribute("msgResume");
//    				ChatRowMyEntity mMessageEntity=ChatRowMyEntity.getEntityFromJSONObject(mjson);
//    				tv_jobName1.setText(mMessageEntity.getJobName());	
//    				tv_salary1.setText(mMessageEntity.getSalary());	
//    				tv_workTime1.setText(mMessageEntity.getWorkTime());
//    				tv_education1.setText(mMessageEntity.getEducation());
//    				tv_workPlace1.setText(mMessageEntity.getWorkPlace());
//    		} catch (HyphenateException e) {
//    			// TODO Auto-generated catch block
//    			e.printStackTrace();
//    		}
//    	}
    	    	
        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
        Spannable span = EaseSmileUtils.getSmiledText(context, txtBody.getMessage());
        // 设置内容
        contentView.setText(span, BufferType.SPANNABLE);

        handleTextMessage();
    }

    protected void handleTextMessage() {
        if (message.direct() == EMMessage.Direct.SEND) {
            setMessageSendCallback();
            switch (message.status()) {
            case CREATE: 
                progressBar.setVisibility(View.GONE);
                statusView.setVisibility(View.VISIBLE);
                // 发送消息
//                sendMsgInBackground(message);
                break;
            case SUCCESS: // 发送成功
                progressBar.setVisibility(View.GONE);
                statusView.setVisibility(View.GONE);
                break;
            case FAIL: // 发送失败
                progressBar.setVisibility(View.GONE);
                statusView.setVisibility(View.VISIBLE);
                break;
            case INPROGRESS: // 发送中
                progressBar.setVisibility(View.VISIBLE);
                statusView.setVisibility(View.GONE);
                break;
            default:
               break;
            }
        }else{
            if(!message.isAcked() && message.getChatType() == ChatType.Chat){
                try {
                    EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onUpdateView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onBubbleClick() {
        // TODO Auto-generated method stub
        
    }



}
