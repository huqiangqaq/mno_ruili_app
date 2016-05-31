package mno_ruili_app.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class MyViewPagerAdapter extends PagerAdapter{
	//界面列表  
    private ArrayList<View> views;  
    public MyViewPagerAdapter(ArrayList<View> views)
    {
         this.views = views; 
    }
	@Override
	public int getCount() {
		if (views != null) {  
            return views.size();  
        }else{
        	return 0;
        }
		
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return (arg0 == arg1);
	}
	
	/**
     * 销毁position位置的界面 
     */
    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));     
    }
    
    /**
     * 初始化position位置的界面 
     */
    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager) container).addView(views.get(position), 0);  
        return views.get(position);  
    }
}
