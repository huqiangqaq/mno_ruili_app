package mno.ruili_app.my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.R;
import mno.ruili_app.ct.JustifyTextView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class aboutroom extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutroom);
		
		initView();
		initDate();
		initEvent();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("aboutroom");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("aboutroom");
		MobclickAgent.onPause(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		mno.ruili_app.ct.JustifyTextView choice_one_text= (JustifyTextView)this.findViewById(R.id.choice_one_text);
		//title_choices.setText("\n《隐私政策和服务协议》");
		String text = "";

        BufferedReader br = null;
        try {
            String fileName ="ab.txt";
            br = new BufferedReader(new InputStreamReader(getAssets().open(fileName)));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            text = sb.toString();
            choice_one_text.setText(text);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            
            }}
	}

	private void initDate() {
		// TODO Auto-generated method stub
		
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		
	}
	public void onclick(View v) {
		 if(v.getId()==R.id.login_findpwd)
		 {

			
			
			 
		 }
	}
}
