package mno.ruili_app.ct;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class CloseButton extends ImageView {

	Activity act_;
	public CloseButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		if(isInEditMode())
			return;
		
		act_ =  (Activity)context;
		// TODO Auto-generated constructor stub
		this.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				act_.finish();
				
			}});
		
	}

}
