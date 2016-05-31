package mno.ruili_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



public class BclFragmentPagerAdapter extends FragmentPagerAdapter {

	public BclFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		list_ = new ArrayList<Fragment>();
	}
	
	List<Fragment> list_;

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return list_.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_.size();
	}
	
	public void put(Fragment fragment)
	{
		list_.add(fragment);
	}

}
