package mno.ruili_app.my.im;

import mno.ruili_app.R;
import mno.ruili_app.ct.FlowLayout;
import mno.ruili_app.ct.TagAdapter;
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

public class EaseChatRowResume extends EaseChatRow{

	TextView tv_jobName1,tv_salary1,tv_workTime1,tv_education1,tv_workPlace1;
	TagFlowLayout fl_workerMsg;

    public EaseChatRowResume(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }
    
    @Override
    protected void onInflatView() {
    	if (AppHelper.getInstance().isResumeTxtMessage(message)){
    		 inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ? 
    	                R.layout.receive_email_comp : R.layout.send_email_comp, this);
    	}      
    }

    @Override
    protected void onFindViewById() {
    	tv_jobName1=(TextView) findViewById(R.id.tv_jobName1);
        tv_salary1=(TextView) findViewById(R.id.tv_salary1);
        tv_workTime1=(TextView) findViewById(R.id.tv_workTime1);
        tv_education1=(TextView) findViewById(R.id.tv_education1);
        tv_workPlace1=(TextView) findViewById(R.id.tv_workPlace1);
        fl_workerMsg=(TagFlowLayout) findViewById(R.id.fl_workerMsg);
    }


    @Override
    public void onSetUpView() {
    	JSONObject mjson = null;
		try {
			mjson = message.getJSONObjectAttribute("msgResume");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (AppHelper.getInstance().isResumeTxtMessage(message)){
			ChatRowMyEntity mMessageEntity=ChatRowMyEntity.getEntityFromJSONObject(mjson);
			tv_jobName1.setText(mMessageEntity.getJobName());	
			tv_salary1.setText(mMessageEntity.getSalary());	
			tv_workTime1.setText(mMessageEntity.getWorkTime());
			tv_education1.setText(mMessageEntity.getEducation());
			tv_workPlace1.setText(mMessageEntity.getWorkPlace());
//			fl_workerMsg.setAdapter(new TagAdapter<String>(mMessageEntity.getWorkerMsg()) {
//
//				@Override
//				public View getView(FlowLayout parent, int position, String t) {
//					TextView tv_workerMsg=(TextView)inflater.inflate(R.layout.tv, fl_workerMsg,false);
//					tv_workerMsg.setText(t);
//					return tv_workerMsg;
//				}
//	  		});
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
