package mno.ruili_app.ct;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * 自定义文件输入框，自带文件清除按钮bystart
 */
public class CustomEditText extends EditText {

	/**
	 * 右侧图片控件
	 */
	private Drawable rightDrawable;
	// 这里构造方法也很重要，不加这个很多属性不能在XML里面定义
	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CustomEditText(Context context) {
		super(context);
		init();
	}

	public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		setDrawable();
		// 增加文本监听器
		addTextChangedListener(new TextWatcher() {
		   /**
		   * 当输入框里面内容发生变化的时候回调的方法
		   */
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				setDrawable();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				setDrawable();
			}
		});
	}
	//设置清除图标的显示与隐藏，顺序是左上右下（0,1,2,3,）	
	private void setDrawable() {
		if (length() == 0) {
			setCompoundDrawables(null, null, null, null);
		} else {
			setCompoundDrawables(null, null, rightDrawable, null);
		}
	}

	@Override
	public void setCompoundDrawables(Drawable left, Drawable top,
			Drawable right, Drawable bottom) {
		if (rightDrawable == null) {
			rightDrawable = right;
		}
		super.setCompoundDrawables(left, top, right, bottom);
	}

	/**
	 * 输入事件处理
	 * 模拟点击事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {		
		if(this.isEnabled()){
			if (rightDrawable != null && event.getAction() == MotionEvent.ACTION_UP) {
				int eventX = (int) event.getX();
				int width = getWidth();
				int right1 = getTotalPaddingRight();
				int right2 = getPaddingRight();
				if (eventX > (width - right1) && eventX < width - right2) {
					setText("");
					event.setAction(MotionEvent.ACTION_CANCEL);
				}
			}
		}		
		return super.onTouchEvent(event);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		this.rightDrawable = null;
	}
}
