package mno.ruili_app.ct;

import mno.ruili_app.R;
import mno.ruili_app.ct.MatrixImageView.OnMovingListener;
import mno.ruili_app.ct.MatrixImageView.OnSingleTapListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.image.AbImageLoader;
import com.ab.view.ioc.AbIocView;


public class imageview extends AbActivity implements OnSingleTapListener  {
	MatrixImageView  image;
	AbImageLoader mAbImageLoader;
	String coverImg;
	
	 protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setAbContentView(R.layout.imageview);
			Intent intent0 =this.getIntent();
			coverImg=intent0.getStringExtra("url").toString();
			init();
			
		
		}
	private void init() {
		// TODO Auto-generated method stub
		 image = (MatrixImageView) this.findViewById(R.id.images);
		 //image.setOnSingleTapListener(onSingleTapListener);
		 mAbImageLoader = AbImageLoader.newInstance(imageview.this);
	     mAbImageLoader.setMaxWidth(0);
	     mAbImageLoader.setMaxHeight(0);
	    // String  coverImg="http://b264.photo.store.qq.com/psb?/V14av8GK1voYCO/BPkJ.Gv0rDTo0MGFlxDb7ze4vPxjxPztOkaZHqVgdYI!/b/dIBJXp0lFAAA&bo=*gFmAQAAAAABB7g!&rf=viewer_4";
	     mAbImageLoader.setLoadingView(this.findViewById(R.id.progressBar));
	     mAbImageLoader.display(image,coverImg);
	   
	}
	 @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK) {
	            finish();
	            overridePendingTransition(R.anim.welcome_fade_in_scale, R.anim.welcome_fade_in_scale); 
	            return false;
	        }
	        return super.onKeyDown(keyCode, event);
	    }
	@Override
	public void onSingleTap() {
		// TODO Auto-generated method stub
		 finish();
	}
	


}
