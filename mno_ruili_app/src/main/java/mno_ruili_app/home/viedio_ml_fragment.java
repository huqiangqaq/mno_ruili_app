package mno_ruili_app.home;

import mno.ruili_app.R;

import com.ab.view.pullview.AbPullToRefreshView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class viedio_ml_fragment extends Fragment{
	View View_;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View_ = inflater.inflate(R.layout.viedio_ml_fragment, container,
				false);
	
		init();
		return View_;
		
	}
	private void init() {
		// TODO Auto-generated method stub
		
	}
}
