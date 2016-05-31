/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
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
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * A custom ScrollView that can accept a scroll listener.
 */

public class ObservableScrollView extends ScrollView {
    private Callbacks mCallbacks;
    private View view;  
    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mCallbacks != null) {
            mCallbacks.onScrollChanged(t);
        }
    }
    OnTouchListener onTouchListener=new OnTouchListener(){  
    	  
        @Override  
        public boolean onTouch(View v, MotionEvent event) {  
            // TODO Auto-generated method stub  
            switch (event.getAction()) {  
            case MotionEvent.ACTION_DOWN:  
                break;  
            case MotionEvent.ACTION_UP:  
                if(view!=null&&onScrollListener!=null){  
                    handler.sendMessageDelayed(handler.obtainMessage(1), 200);  
                }  
                break;  

            default:  
                break;  
            }  
            return false;  
        }  
          
    };  
    
    public boolean onTouchEvent(MotionEvent ev) {
        if (mCallbacks != null) {
            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    mCallbacks.onDownMotionEvent();
                    break;
                case MotionEvent.ACTION_UP:
                
                case MotionEvent.ACTION_CANCEL:
                    mCallbacks.onUpOrCancelMotionEvent();
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }
    /** 
     * 获得参考的View，主要是为了获得它的MeasuredHeight，然后和滚动条的ScrollY+getHeight作比较。 
     */  
    public void getView(){  
        this.view=getChildAt(0);  
        if(view!=null){  
            init();  
        }  
    }  
    Handler handler;
    //这个获得总的高度  
   
    public int computeVerticalScrollOffset(){  
        return super.computeVerticalScrollOffset();  
    }  
    private void init() {
		// TODO Auto-generated method stub
    	 this.setOnTouchListener(onTouchListener);  
         handler=new Handler(){  
             @Override  
             public void handleMessage(Message msg) {  
                 // process incoming messages here  
                 super.handleMessage(msg);  
                 switch(msg.what){  
                 case 1:  
                     if(view.getMeasuredHeight() <= getScrollY() + getHeight()) {  
                         if(onScrollListener!=null){  
                             onScrollListener.onBottom();  
                         }  
                           
                     }else if(getScrollY()==0){  
                         if(onScrollListener!=null){  
                             onScrollListener.onTop();  
                         }  
                     }  
                     else{  
                         if(onScrollListener!=null){  
                             onScrollListener.onScroll();  
                         }  
                     }  
                     break;  
                 default:  
                     break;  
                 }  
             }  
         };  
	}

	/** 
     * 定义接口 
     * @author admin 
     * 
     */  
    public interface OnScrollListener{  
        void onBottom();  
        void onTop();  
        void onScroll();  
    }  

    private OnScrollListener onScrollListener;  
    public void setOnScrollListener(OnScrollListener onScrollListener){  
        this.onScrollListener=onScrollListener;  
    }  
    @Override
    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    public void setCallbacks(Callbacks listener) {
        mCallbacks = listener;
    }

    public static interface Callbacks {
        public void onScrollChanged(int scrollY);
        public void onDownMotionEvent();
        public void onUpOrCancelMotionEvent();
    }
}
