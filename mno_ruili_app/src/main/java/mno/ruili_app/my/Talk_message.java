package mno.ruili_app.my;


import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import mno.ruili_app.R;
import mno.ruili_app.my.im.BaseActivity;
import mno.ruili_app.my.im.ConversationListFragment;

public class Talk_message extends BaseActivity{
	private EaseConversationListFragment clFragment;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.chat_activity);	
		clFragment=new ConversationListFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.container, clFragment).commit();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("Talk_message");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("Talk_message");
		MobclickAgent.onPause(this);
	}
}
