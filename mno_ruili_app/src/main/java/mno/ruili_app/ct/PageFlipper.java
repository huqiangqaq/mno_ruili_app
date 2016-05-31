package mno.ruili_app.ct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.android.volley.toolbox.NetworkImageView;

import mno.ruili_app.R;
import mno_ruili_app.net.webpost;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
 
public class PageFlipper extends ViewPager {
	

	/** 点击. */
	private OnItemClickListener mOnItemClickListener;
	/** 触摸. */
	private mOnTouchListener OnTouchListener;
	
    private String TAG = PageFlipper.class.getSimpleName();
     
    private ArrayList<View> views;
    private PagerAdapter adapter = new PagerAdapter() {
         
    	
    	
    	
    	
    	
    	
    	
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
        	
            View v = views.get(position);
            if (v != null) {
                ViewGroup parent = (ViewGroup) v.getParent();
                if (parent != null) {
                	View mv = new View(getContext());
                	mv=v;
                    parent.removeView(v);
                    ((ViewPager)container).addView(mv);
                }
                else
                ((ViewPager)container).addView(v);
            }
            
           // ((ViewPager)container).addView(v);
            return v;
        }
         
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
         
        @Override
        public int getItemPosition(Object object) {
            return views.indexOf(object);
        }
         
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
         
        @Override
        public int getCount() {
            return views == null ? 0 : views.size();
        }
    };
    private OnPageChangeListener listener = new OnPageChangeListener() {
         
        /**
         * 将控件位置转化为数据集中的位置 
         */
        public int convert(int position){
        	 if(views.size()<=0)
             	return 0;
            return position == 0 ? views.size()-1 : ( position > views.size() ? 0 : position-1 );
          
        }
         
        @Override
        public void onPageSelected(int position) {
            if(listener2 != null){
                listener2.onPageSelected(convert(position));
            }
           
        }
         
        @Override
        public void onPageScrolled(int position, float percent, int offset) {
            if(listener2 != null){
                listener2.onPageScrolled(convert(position), percent, offset);
            }
            if(views.size()<=0)
            	return;
             //MessageBox.Show(getContext(), ""+percent);
            if(percent == 0){
            	
                if(position == 0) // 切换到倒数第二页
                    setCurrentItem(( views.size() - 2 ) % views.size(), false);
                else if(position == views.size() - 1) // 切换到正数第二页
                    setCurrentItem(1, false);
                
            }
        }
         
        @Override
        public void onPageScrollStateChanged(int state) {
            if(listener2 != null){
                listener2.onPageScrollStateChanged(state);
            }
             
            switch (state) {
            case SCROLL_STATE_IDLE: // 闲置
            	if(!play&&playing!=1)
            	startPlay();
               /* if(!handler.hasMessages(START_FLIPPING))
                    handler.sendEmptyMessageDelayed(START_FLIPPING, 5000);  // 延时滚动
*/                     
                break;
            case SCROLL_STATE_DRAGGING: // 拖动中
            	if(play)
            	stopPlay();
               // handler.sendEmptyMessage(STOP_FLIPPING); // 取消滚动
                 
                break;
            case SCROLL_STATE_SETTLING: // 拖动结束
            	//int i=getCurrentItem();
            	
            	//MessageBox.Show(getContext(), ""+i);
            	
                break;
            }
        }
    }, listener2;
     
    private final int START_FLIPPING = 0; 
    private final int STOP_FLIPPING = 1; 
    private  int playing=1;
    private boolean play = false;
	/** 用与轮换的 handler. */
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			if (msg.what==0) {
				 if(play)
                 {
                if(views.size() > 3) // 因为前后页是辅助页，所以此处3也就是只有1页
                {
                    setCurrentItem((getCurrentItem() + 1) % views.size());
                //handler.sendEmptyMessageDelayed(START_FLIPPING, 5000);  // 延时滚动
                handler.postDelayed(runnable, 5000);  
                }
				}
		     }
		}
		
	};  
	
	/** 用于轮播的线程. */
	private Runnable runnable = new Runnable() {  
	    public void run() {  
	    	if(views!=null){
	    		handler.sendEmptyMessage(0);
			} 
	    }  
	};  

	
	/**
	 * 描述：自动轮播.
	 */
	public void startPlay(){
		if(handler!=null){
		   play  = true;
		   playing=0;
		   handler.postDelayed(runnable, 5000);  
		}
	}
	
	/**
	 * 描述：自动轮播.
	 */
	public void stopPlay(){
		if(handler!=null){
			play  = false;
			//playing=1;
			handler.removeCallbacks(runnable);  
		}
	}
	/**
	 * 描述：无轮播.
	 */
	public void noPlay(){
		if(handler!=null){
			play  = false;
			playing=1;
			handler.removeCallbacks(runnable);  
		}
	}
    
    
    /*private Handler handler = new Handler(){
         
        public void handleMessage(Message msg) {
             
            switch (msg.what) {
            case START_FLIPPING:
                 if(play==0)
                 {
                if(views.size() > 3) // 因为前后页是辅助页，所以此处3也就是只有1页
                    setCurrentItem((getCurrentItem() + 1) % views.size());
                 
                handler.sendEmptyMessageDelayed(START_FLIPPING, 5000);  // 延时滚动
                 }
                break;
            case STOP_FLIPPING:
                 
                handler.removeMessages(START_FLIPPING);
                 
                break;
            }
        }
    };*/
     
    public PageFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
 
    public PageFlipper(Context context) {
        super(context);
        
        init();
    }
 
    private void init(){
    	if(isInEditMode())
			return;
        setOffscreenPageLimit(5); // 最大页面缓存数量
        setAdapter(adapter); // 适配器
        super.setOnPageChangeListener(listener); // 监听器
        startPlay();
        //handler.sendEmptyMessageDelayed(START_FLIPPING, 5000);  // 延时滚动
    }
     
    
	
    /**
	 * 描述：添加可播放视图列表.
	 *
	 * @param views the views
	 */
	public void maddViews(List<View> mviews){
		this.views = new ArrayList<View>();
		//views.addAll(mviews);
		
		for(int i=0;i<mviews.size()+2;i++){
			final int on= (i == 0 ? mviews.size()-1 : ( i > mviews.size() ? 0 : i-1 ));
			View mv = new View(getContext());
			mv=mviews.get(on);
			mv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(mOnItemClickListener!=null){
						mOnItemClickListener.onClick(on);
					}
				}
			});
			
			mv.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View view, MotionEvent event) {
					if(OnTouchListener!=null){
						OnTouchListener.onTouch(event);
					}
					return false;
				}
			});
			
			this.views.add(mviews.get(on)); 
		}
		 setCurrentItem(1); // 首页
	     this.adapter.notifyDataSetChanged();
	}
    
    public void setViews(int[] ids){
        this.views = new ArrayList<View>();
        
        
        for(int i=0;i<ids.length+2;i++){ // 头部新增一个尾页，尾部新增一个首页
             
            ImageView iv = new ImageView(getContext());
            iv.setImageResource(ids[i == 0 ? ids.length-1 : ( i > ids.length ? 0 : i-1 )]);
            final int on= (i == 0 ? ids.length-1 : ( i > ids.length ? 0 : i-1 ));
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(mOnItemClickListener!=null){
						mOnItemClickListener.onClick(on);
					}
				}
			});
            iv.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View view, MotionEvent event) {
					if(OnTouchListener!=null){
						OnTouchListener.onTouch(event);
					}
					return false;
				}
			});
    
            this.views.add(iv); 
        }
        setCurrentItem(1); // 首页
        this.adapter.notifyDataSetChanged();
    }
     
    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.listener2 = listener;
    }


	  /**
     * 触摸屏幕接口.
     *
     * @see AbOnTouchEvent
     */
    public interface mOnTouchListener {
    	/**
    	 * 描述：Touch事件.
    	 *
    	 * @param event 触摸手势
    	 */
        public void onTouch(MotionEvent event); 
    }
    /**
     * 描述：设置页面Touch的监听器.
     *
     * @param abOnTouchListener the new on touch listener
     */
    public void setOnTouchListener(mOnTouchListener mOnTouchListener){
    	OnTouchListener = mOnTouchListener;
    }
    
    
    
    
    /**
	 * 设置点击事件监听.
	 *
	 * @param onItemClickListener the new on item click listener
	 */
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		mOnItemClickListener = onItemClickListener;
	}
	
    /**
	 * 条目点击接口.
	 *
	 * @see AbOnItemClickEvent
	 */
    public interface OnItemClickListener {
        
        /**
         * 描述：点击事件.
         * @param position 索引
         */
        public void onClick(int position); 
    }
	public void setContent(List<Map<String, String>> newList) {
		// TODO Auto-generated method stub
		this.views = new ArrayList<View>();
		views.clear();
		if(newList.size()<=0)
			return;
			
		for(int i=0;i< newList.size()+2; i++){
			final int on= (i == 0 ? newList.size()-1 : ( i > newList.size() ? 0 : i-1 ));
			//View mv = new View(getContext());
			LayoutInflater inflate = (LayoutInflater)getContext(). getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			RelativeLayout layout = (RelativeLayout)inflate.inflate(R.layout.playpicitem, null); 
			 
			NetworkImageView mPlayImage = (NetworkImageView) layout.findViewById(R.id.mPlayImage);
			mPlayImage.setImageUrl(newList.get(on).get("coverImg"),webpost.getImageLoader());
	          //图片的下载
			mPlayImage.setDefaultImageResId(R.drawable.image_empty);
			mPlayImage.setErrorImageResId(R.drawable.image_error);	
			TextView mPlayText = (TextView) layout.findViewById(R.id.mPlayText);
			mPlayText.setText(newList.get(on).get("title"));
			
			
			layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(mOnItemClickListener!=null){
						mOnItemClickListener.onClick(on);
					}
				}
			});
			
			layout.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View view, MotionEvent event) {
					if(OnTouchListener!=null){
						OnTouchListener.onTouch(event);
					}
					return false;
				}
			});
			views.add(layout);
			stopPlay();
			startPlay();
			//this.views.add(mv); 
		}
		 setCurrentItem(1); // 首页
		 
	     this.adapter.notifyDataSetChanged();
	}

	public void removeViews() {
		// TODO Auto-generated method stub
		if(views!=null)
		views.clear();
	}
	
	/*public void setContent2(List<Map<String, String>> newList,int p) {
		// TODO Auto-generated method stub
		this.views = new ArrayList<View>();
		for(int i=0;i< newList.size()+2; i++){
			final int on= (i == 0 ? newList.size()-1 : ( i > newList.size() ? 0 : i-1 ));
			//View mv = new View(getContext());
			LayoutInflater inflate = (LayoutInflater)getContext(). getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LinearLayout layout = (LinearLayout)inflate.inflate(R.layout.playpic2, null); 
			ImageView sy_about = (ImageView) layout.findViewById(R.id.sy_about);
		    //ImageView mPlayImage = (ImageView) layout.findViewById(R.id.imageView2);
			//TextView mPlayText2 = (TextView) layout.findViewById(R.id.textView2);
			TextView mPlayText = (TextView) layout.findViewById(R.id.textView1);
			mPlayText.setText(on+"公司");
			sy_about.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getContext(),about.class);
					
					 getContext().startActivity(intent);
				}});
       
			views.add(layout);
			
		}
		 setCurrentItem(1); // 首页
	     this.adapter.notifyDataSetChanged();
	}*/


}