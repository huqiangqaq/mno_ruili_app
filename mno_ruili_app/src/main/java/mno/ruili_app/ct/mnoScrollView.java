/*
 * Copyright (c) 2014,KJFrameForAndroid Open Source Project,张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mno.ruili_app.ct;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class mnoScrollView extends ScrollView {
	private static final int MAX_Y_OVERSCROLL_DISTANCE = 100; 	
	private int lastScrollY;  
    private Context mContext; 
    private int mMaxYOverscrollDistance;

    private  OnScrollListener onScrollListener; 
      
    public mnoScrollView(Context context){ 
        super(context); 
        mContext = context; 
        initBounceListView(); 
    } 
      
    public mnoScrollView(Context context, AttributeSet attrs){ 
        super(context, attrs); 
        mContext = context; 
        initBounceListView();
    } 
      
    public mnoScrollView(Context context, AttributeSet attrs, int defStyle){ 
        super(context, attrs, defStyle); 
        mContext = context; 
        initBounceListView(); 
    } 
      
    private void initBounceListView(){ 
        final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics(); 
            final float density = metrics.density; 
          
        mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE); 
    } 
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent){  
        //这块是关键性代码
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxYOverscrollDistance, isTouchEvent);   
    }
    
    
    
	/**
	 * 设置滚动接口
	 * @param onScrollListener
	 */
	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}


	/**
	 * 用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法中
	 */
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			int scrollY = mnoScrollView.this.getScrollY();
			
			//此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息
			if(lastScrollY != scrollY){
				lastScrollY = scrollY;
				handler.sendMessageDelayed(handler.obtainMessage(), 20);  
			}
			if(onScrollListener != null){
				onScrollListener.onScroll(scrollY);
			}
			
		};

	}; 

	/**
	 * 重写onTouchEvent， 当用户的手在MyScrollView上面的时候，
	 * 直接将MyScrollView滑动的Y方向距离回调给onScroll方法中，当用户抬起手的时候，
	 * MyScrollView可能还在滑动，所以当用户抬起手我们隔5毫秒给handler发送消息，在handler处理
	 * MyScrollView滑动的距离
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(onScrollListener != null){
			onScrollListener.onScroll(lastScrollY = this.getScrollY());
		}
		switch(ev.getAction()){
		case MotionEvent.ACTION_UP:
	         handler.sendMessageDelayed(handler.obtainMessage(), 5);  
			break;
		}
		return super.onTouchEvent(ev);
	}


	/**
	 * 
	 * 滚动的回调接口
	 * 
	 * @author xiaanming
	 *
	 */
	public interface OnScrollListener{
		/**
		 * 回调方法， 返回MyScrollView滑动的Y方向距离
		 * @param scrollY
		 * 				、
		 */
		public void onScroll(int scrollY);
	}
}
