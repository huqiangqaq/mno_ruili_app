package mno.ruili_app.my;

import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class my_bz_item extends Activity{
	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.my_bz_item);
	init();
}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("my_bz_item");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("my_bz_item");
		MobclickAgent.onPause(this);
	}
private void init() {
	// TODO Auto-generated method stub
	/*ImageView imv = (ImageView) this.findViewById(R.id.imageView1);
	Options opts = new Options();
	opts.inSampleSize = 2;
	Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.helpme, opts);
	imv.setImageBitmap(bmp);*/
}
public void onclick(View v) {
	 if(v.getId()==R.id.my_but_login)
	 {
		 
	 }
	 else if(v.getId()==R.id.my_but_registered)
	 {
		 
	 }
}
}
