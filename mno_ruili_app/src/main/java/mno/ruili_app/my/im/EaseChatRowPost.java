package mno.ruili_app.my.im;

import mno.ruili_app.R;
import mno.ruili_app.ct.TagFlowLayout;

import org.json.JSONObject;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.exceptions.HyphenateException;
//自定义岗位消息
public class EaseChatRowPost extends EaseChatRow{

	TextView tv_jobName2,tv_salary2,tv_workTime2,tv_education2,tv_workPlace2;
	TagFlowLayout fl_jobMsg;

    public EaseChatRowPost(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }
    
    @Override
    protected void onInflatView() {
    	if (AppHelper.getInstance().isPostTxtMessage(message)){
    		inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ? 
                    R.layout.receive_email_person : R.layout.send_email_person, this);
    	}       
    }

    @Override
    protected void onFindViewById() {
    	tv_jobName2=(TextView) findViewById(R.id.tv_jobName2);
        tv_salary2=(TextView) findViewById(R.id.tv_salary2);
        tv_workTime2=(TextView) findViewById(R.id.tv_workTime2);
        tv_education2=(TextView) findViewById(R.id.tv_education2);
        tv_workPlace2=(TextView) findViewById(R.id.tv_workPlace2);
        fl_jobMsg=(TagFlowLayout) findViewById(R.id.fl_jobMsg);
    }


    @Override
    public void onSetUpView() {
    	JSONObject mjson = null;
		try {
			mjson = message.getJSONObjectAttribute("msgPost");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (AppHelper.getInstance().isPostTxtMessage(message)){
			ChatRowPostEntity mMessageEntity=ChatRowPostEntity.getEntityFromJSONObject(mjson);
			tv_jobName2.setText(mMessageEntity.getJobName());	
			tv_salary2.setText(mMessageEntity.getSalary());	
			tv_workTime2.setText(mMessageEntity.getWorkTime());
			tv_education2.setText(mMessageEntity.getEducation());
			tv_workPlace2.setText(mMessageEntity.getWorkPlace());
        }
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

