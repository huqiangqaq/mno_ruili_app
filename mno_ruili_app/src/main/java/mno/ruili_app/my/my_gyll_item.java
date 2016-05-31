package mno.ruili_app.my;

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class my_gyll_item extends AbActivity {
	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.my_gyll_item);
	init();
}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("my_gyll_item");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("my_gyll_item");
		MobclickAgent.onPause(this);
	}
private void init() {
	// TODO Auto-generated method stub
	
}
private void digshow() {
	// TODO Auto-generated method stub
	View mView = null;
	mView = mInflater.inflate(R.layout.dialog_myconfig,null);
	AbDialogUtil.showDialog(mView,R.animator.fragment_top_enter,R.animator.fragment_top_exit,R.animator.fragment_pop_top_enter,R.animator.fragment_pop_top_exit);
	Button leftBtn1 = (Button)mView.findViewById(R.id.left_btn);
	Button rightBtn1 = (Button)mView.findViewById(R.id.right_btn);
	TextView title_choices = (TextView)mView.findViewById(R.id.title_choices);
	TextView choice_one_text= (TextView)mView.findViewById(R.id.choice_one_text);
	//title_choices.setText("");
	choice_one_text.setText("是否联系电话客服");
	leftBtn1.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			AbDialogUtil.removeDialog(my_gyll_item.this);
		}
		
	});
	
	rightBtn1.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			 Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + "18067950361"));
			 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 startActivity(intent);
			 AbDialogUtil.removeDialog(my_gyll_item.this);
		}
		
	});
}
private void digshow2() {
	// TODO Auto-generated method stub
	View mView = null;
	mView = mInflater.inflate(R.layout.dialog_myconfig,null);
	AbDialogUtil.showDialog(mView,R.animator.fragment_top_enter,R.animator.fragment_top_exit,R.animator.fragment_pop_top_enter,R.animator.fragment_pop_top_exit);
	Button leftBtn1 = (Button)mView.findViewById(R.id.left_btn);
	Button rightBtn1 = (Button)mView.findViewById(R.id.right_btn);
	TextView title_choices = (TextView)mView.findViewById(R.id.title_choices);
	TextView choice_one_text= (TextView)mView.findViewById(R.id.choice_one_text);
	//title_choices.setText("");
	choice_one_text.setText("是否联系QQ客服");
	leftBtn1.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			AbDialogUtil.removeDialog(my_gyll_item.this);
		}
		
	});
	
	rightBtn1.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			 String url11 = "mqqwpa://im/chat?chat_type=wpa&uin=2661332792&version=1";  
			 my_gyll_item.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url11)));  
			 AbDialogUtil.removeDialog(my_gyll_item.this);
		}
		
	});}
public void onclick(View v) {
	 if(v.getId()==R.id.imageView2)
	 {
		 digshow();
		 }
	 else if(v.getId()==R.id.imageView3)
	 {
		 digshow2();
		 
	 }
}
}
