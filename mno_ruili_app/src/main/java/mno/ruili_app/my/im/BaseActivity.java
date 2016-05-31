package mno.ruili_app.my.im;

import android.os.Bundle;

import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.umeng.analytics.MobclickAgent;
//自己的
public class BaseActivity extends EaseBaseActivity{
	@Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // umeng
        //MobclickAgent.onResume(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // umeng
       // MobclickAgent.onPause(this);
    }
}
