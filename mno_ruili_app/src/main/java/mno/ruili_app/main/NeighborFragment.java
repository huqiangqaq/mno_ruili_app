package mno.ruili_app.main;

import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.BclFragmentPagerAdapter;
import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.R.color;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.my.my_xxxg;
import mno.ruili_app.my.mylogin;
import mno_ruili_app.nei.nei_bj;
import mno_ruili_app.nei.nei_find;
import mno_ruili_app.nei.wdfragment;
import mno_ruili_app.nei.zxfragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NeighborFragment extends Fragment{
	TextView but_nei_zx,but_nei_wd,bz_nei_zx,bz_nei_wd;
	View View_;
	BclFragmentPagerAdapter PagerAdapter_;
	ViewPager ViewPager_;
	LinearLayout nei_wd,nei_zx;
	ImageView nei_bj,nei_find;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View_ = inflater.inflate(R.layout.fmt_neighbor, container,
				false);
		init();
		return View_;
		
	}
	
	private void init() {
		// TODO Auto-generated method stub
		ViewPager_ = (ViewPager) View_.findViewById(R.id.nei_pager);
		nei_bj=(ImageView) View_.findViewById(R.id.nei_bj);
		nei_find=(ImageView) View_.findViewById(R.id.nei_find);
		but_nei_zx= (TextView) View_.findViewById(R.id.but_nei_zx);
		bz_nei_zx= (TextView) View_.findViewById(R.id.bz_nei_zx);
		
		but_nei_wd= (TextView) View_.findViewById(R.id.but_nei_wd);
		bz_nei_wd= (TextView) View_.findViewById(R.id.bz_nei_wd);
	
		nei_wd=(LinearLayout)View_.findViewById(R.id.nei_wd);
		nei_zx=(LinearLayout)View_.findViewById(R.id.nei_zx);
		nei_wd.setOnClickListener(new MyListener());  
		nei_zx.setOnClickListener(new MyListener());  
		nei_bj.setOnClickListener(new MyListener());  
		nei_find.setOnClickListener(new MyListener());  
		PagerAdapter_ = new BclFragmentPagerAdapter(this.getChildFragmentManager());
		PagerAdapter_.put(new zxfragment());
		PagerAdapter_.put(new wdfragment());
		ViewPager_.setAdapter(PagerAdapter_);
		ViewPager_.setOnPageChangeListener(new OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				//MessageBox.Show(NeighborFragment.this.getActivity(), arg0+"");
			}});
		
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
	}
	 private class MyListener implements OnClickListener{  
		  
	        @Override  
	        public void onClick(View v) {  
	            // TODO Auto-generated method stub  
	        	but_nei_zx.setTextColor(getResources().getColor(color.gray_91));
	        	but_nei_wd.setTextColor(getResources().getColor(color.gray_91));
	        	Drawable nei_zx = getResources().getDrawable(R.drawable.nei_zx);
	        	Drawable nei_zx_ = getResources().getDrawable(R.drawable.nei_zx_);
	        	Drawable nei_wd = getResources().getDrawable(R.drawable.nei_wd_);
	        	Drawable nei_wd_ = getResources().getDrawable(R.drawable.nei_wd);
	        	if(v.getId()==R.id.nei_zx)
	        	{
	        		nei_bj.setVisibility(View.GONE);
	        		but_nei_zx.setTextColor(android.graphics.Color.parseColor("#ff6200"));
	        		but_nei_wd.setCompoundDrawablesWithIntrinsicBounds(nei_wd_,null, null,null);
	        		but_nei_zx.setCompoundDrawablesWithIntrinsicBounds(nei_zx,null, null,null);
	        		bz_nei_zx.setBackgroundColor(android.graphics.Color.parseColor("#ff6200"));
	        		bz_nei_wd.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
	    			 ViewPager_.setCurrentItem(0, false);
	        	}
	        	else if(v.getId()==R.id.nei_wd)
	        	{
	        		nei_bj.setVisibility(View.VISIBLE);
	        		but_nei_wd.setTextColor(android.graphics.Color.parseColor("#ff6200"));
	        		but_nei_wd.setCompoundDrawablesWithIntrinsicBounds(nei_wd,null, null,null);
	        		but_nei_zx.setCompoundDrawablesWithIntrinsicBounds(nei_zx_,null, null,null);
	        		bz_nei_zx.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
	        		bz_nei_wd.setBackgroundColor(android.graphics.Color.parseColor("#ff6200"));
	    			 ViewPager_.setCurrentItem(1, false);
	        	}
	        	else if(v.getId()==R.id.nei_find)
	        	{
	        		Intent intent0 = new Intent(NeighborFragment.this.getActivity(), nei_find.class); 
	        		Constant.viedio_find="zx";
					startActivity(intent0);
	        	}
	        	else if(v.getId()==R.id.nei_bj)
	        	{
	        		if(!appuser.getInstance().IsLogin())
	   	    	 {
	        		 MessageBox.Show(View_.getContext(), "该功能需要登录以后才可以使用\n请登录");
	     			 appuser.getInstance().Login(NeighborFragment.this.getActivity());
	     			 return;
	   	    	 }
	        		Intent intent0 = new Intent(NeighborFragment.this.getActivity(), nei_bj.class); 
				    
					startActivity(intent0);
	        	}
	        }}
	 //设置viewpager滑动的事件，实现导航点的滚动
	 
	 private class NavigationPageChangeListener implements OnPageChangeListener {
	  
	 @Override
	     public void onPageScrollStateChanged(int arg0) {
	     }
	  
	     @Override
	     public void onPageScrolled(int arg0, float arg1, int arg2) {
	     }
	  
	     @Override
	     public void onPageSelected(int position) {
	        
	         }
	     }
		public void onResume() {
		    super.onResume();
		    MobclickAgent.onPageStart("NeighborFragment"); 
		}
		public void onPause() {
		    super.onPause();
		    MobclickAgent.onPageEnd("NeighborFragment"); 
		}
}
