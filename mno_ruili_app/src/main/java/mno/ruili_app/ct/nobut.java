package mno.ruili_app.ct;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class nobut extends Button{
	Activity  act_ ;
	public nobut(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		if(isInEditMode())
			return;
		
		 act_ = (Activity)context;
	}
	@Override
	public void setPressed(boolean pressed) {
	if (pressed && getParent() instanceof View
	&& ((View) getParent()).isPressed()) {
	return;
	}
	super.setPressed(pressed);
	}

}
