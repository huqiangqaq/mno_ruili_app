package mno_ruili_app.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.ct.CycleIndicator;
import mno.ruili_app.ct.PageFlipper;
import mno_ruili_app.adapter.ImageListAdapter;
import mno_ruili_app.adapter.tv;
import mno_ruili_app.nei.nei_find;
import mno_ruili_app.nei.nei_wdxq;
import mno_ruili_app.nei.wdfragment;
import mno_ruili_app.net.RequestType.Type;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.getlisthandler;
import mno_ruili_app.net.webpost;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.ab.view.sliding.AbSlidingPlayView;
import com.android.volley.toolbox.NetworkImageView;
import com.umeng.analytics.MobclickAgent;

public class home_hyvideo extends Activity implements OnHeaderRefreshListener,OnFooterLoadListener{
	AbPullToRefreshView mAbPullToRefreshView;
	ListView mListView;
	 ArrayList<tv>   mTvList = new ArrayList<tv>();
	TextView select_tg_text,select_tg_bz,select_sj_text,select_sj_bz,select_yy_text,select_yy_bz;
	getlisthandler handler_;
	JSONObject type_json;
	boolean IsHandler_=false; //是否处理中
	boolean Isrefresh=false;
	ImageListAdapter mylistViewAdapter ;
	int begin=0;
	int type=1;
	private List<Map<String, String>> newList = new  ArrayList<Map<String, String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_hyvidio);
		mAbPullToRefreshView = (AbPullToRefreshView)this.findViewById(R.id.mhyPullRefreshView);
		init();
	}
	 
	private void init() {
		// TODO Auto-generated method stub
		   mListView = (ListView)this.findViewById(R.id.mListView);
	    
		   mylistViewAdapter = new ImageListAdapter(this, mTvList,
					R.layout.item_list, new String[] { "itemsIcon" },
					new int[] { R.id.itemsIcon,R.id.itemstext,R.id.itemstext2});
	       image();
	       mListView.setAdapter(mylistViewAdapter);
	       mListView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent intent = new Intent(home_hyvideo.this,home_video.class);
					Constant.itemid=mTvList.get(position).getid();
					home_hyvideo.this.startActivity(intent);
				}
	    		
	    	});
	       //设置监听器
	      
	       mAbPullToRefreshView.setOnHeaderRefreshListener(this);
	       mAbPullToRefreshView.setOnFooterLoadListener(this);
	       
	       
	       handler_ = new getlisthandler(){

				@Override
				public void OnSuccess(JSONObject response) {
					// TODO Auto-generated method stub
					try {
						if(Isrefresh)
						{
							mTvList.clear();
							//mSlidingPlayView.removeAllViews();
							mAbPullToRefreshView.onHeaderRefreshFinish();
							Isrefresh=false;
						}
						//mSlidingPlayView.removeAllViews();
						mAbPullToRefreshView.onFooterLoadFinish();
						if(begin==0)
						{
						JSONArray ad=  new JSONArray( response.getString("ad") );
						int adnum=ad.length();
						//mFrom=new String[adnum];
						//if(start==0){
						begin++;
						newList.clear();
						for(int i=0;i< adnum; i++)
						    {
							 
							 JSONObject adjson=ad.getJSONObject(i);
							 String id=adjson.getString("id");
							 String title="      "+adjson.getString("title");
							 String coverImg=adjson.getString("coverImg");
							
							 Map<String, String> map=new HashMap <String, String>();
							 map.put("id", id);
							 map.put("title", title);
							 map.put("coverImg", RequestType.getWebUrl_(coverImg));
							 newList.add(map);
							// mFrom[i]=id;
						    }
						
						 addimg();
						}
						JSONArray  data=  new JSONArray( response.getString("list") );
						int length=data.length();
					    for(int i=0;i<= length; i++)
					    {
					    type_json =   data.getJSONObject(i);
					    //listData.add(data.getJSONObject(i).getString("Name"));
					    String name =  type_json.getString("title");
					    String content =  type_json.getString("desc");
					    String id =  type_json.getString("id");
					    String imgurl=type_json.getString("coverImg");
					    
					    final tv mtv = new tv(id,name, content,imgurl);
					    
					    mTvList.add(mtv);
					    mylistViewAdapter.notifyDataSetChanged();
					    }
					  
					    IsHandler_ = false;
						
					    
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			LoadData("1");
	}

	public void onclick(View v) {
	
		 if(v.getId()==R.id.select_sj)
     	{
			 i=1;
			 type=1;
			 mTvList.clear();
			 LoadData("1");
			 back();
			 select_sj_text.setTextColor(android.graphics.Color.parseColor("#ff6200"));
			 select_sj_bz.setBackgroundColor(android.graphics.Color.parseColor("#ff6200"));
		
     	}
     	else if(v.getId()==R.id.select_tg)
     	{
     		i=1;
     		type=2;
     		 mTvList.clear();
     		LoadData("2");
     		back();
     		select_tg_text.setTextColor(android.graphics.Color.parseColor("#ff6200"));
     		 select_tg_bz.setBackgroundColor(android.graphics.Color.parseColor("#ff6200"));
     	
     	}
     	else if(v.getId()==R.id.select_yy)
     	{
     		i=1;
     		type=3;
     		 mTvList.clear();
     		LoadData("3");
     		back();
     		select_yy_text.setTextColor(android.graphics.Color.parseColor("#ff6200"));
     		 select_yy_bz.setBackgroundColor(android.graphics.Color.parseColor("#ff6200"));
     		
     	}
    	else if(v.getId()==R.id.vidio_find)
     	{
    		Intent intent0 = new Intent(home_hyvideo.this, nei_find.class); 
    		Constant.viedio_find="movie";
    		Constant.viedio_label="2";
			startActivity(intent0);
     	}
 
	 }
	private void back() {
		// TODO Auto-generated method stub
		 select_tg_text.setTextColor(android.graphics.Color.parseColor("#919191"));
		 select_sj_text.setTextColor(android.graphics.Color.parseColor("#919191"));
		 select_yy_text.setTextColor(android.graphics.Color.parseColor("#919191"));
		select_sj_bz.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
		select_tg_bz.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
		select_yy_bz.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
		
	}
	
	protected void addimg() {
		// TODO Auto-generated method stub
		final PageFlipper mypic;
		final CycleIndicator indicator0;
		top.removeAllViews();
	    LayoutInflater inflate = (LayoutInflater)this. getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 		layout1 = (LinearLayout)inflate.inflate(R.layout.mytoppic, null);
		indicator0 = (CycleIndicator)layout1. findViewById(R.id.indicator0);
		mypic=(PageFlipper)layout1.findViewById(R.id.mypic);
		top.addView(layout1);
 		indicator0.setPageCount(newList.size());
 		LayoutParams lptop;       
		lptop=mypic.getLayoutParams();
        //lp.width=100;
		lptop.height=Constant.displayWidth/16*7;        
        mypic.setLayoutParams(lptop);
        mypic.setContent(newList);
        mypic.setOnItemClickListener(new PageFlipper.OnItemClickListener() {
			
			@Override
			public void onClick(int position) {
				// TODO Auto-generated method stub
			   
				Intent intent = new Intent(home_hyvideo.this,home_video.class);
				intent.putExtra("ID",newList.get(position).get("id")); 
				Constant.itemid=newList.get(position).get("id");
				home_hyvideo.this.startActivity(intent);
			}
		});
		mypic.setOnPageChangeListener(new PageFlipper.OnPageChangeListener() {
	             
	            @Override
	            public void onPageSelected(int index) {
	                //ndicator0.onPageSelected(index);
	            	 //MessageBox.Show(HomeFragment.this.getActivity(), index+"");
	                if(index==newList.size())
	                	indicator0.onPageSelected(0);
	                else if(index==newList.size()+1)
	                	indicator0.onPageSelected(newList.size()-1);
	                else 
	                	indicator0.onPageSelected(index);
	            }

				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}
	        });
        
        
	}
	LinearLayout top,layout1;
	private void image() {
		// TODO Auto-generated method stub
		
		LinearLayout topview= (LinearLayout)this.findViewById(R.id.topview);
       
		top = new LinearLayout(this);
 		top.setLayoutParams(new LinearLayout.LayoutParams(Constant.displayWidth, Constant.displayWidth/16*7));
 	
	
		topview.addView(top);
			
			LinearLayout layout = (LinearLayout) LayoutInflater.from(home_hyvideo.this).inflate(R.layout.select_view, null);
			select_tg_text=(TextView)layout.findViewById(R.id.select_tg_text);
			select_tg_bz=(TextView)layout.findViewById(R.id.select_tg_bz);
			select_sj_text=(TextView)layout.findViewById(R.id.select_sj_text);
			select_sj_bz=(TextView)layout.findViewById(R.id.select_sj_bz);
			select_yy_text=(TextView)layout.findViewById(R.id.select_yy_text);
			select_yy_bz=(TextView)layout.findViewById(R.id.select_yy_bz);
			
			topview.addView(layout);
	}
	public void LoadData(String typeId)
	{
		 handler_.getlist(home_hyvideo.this,"2",typeId, "10", "1","2",Type.getcourse);
		 
	}
	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		// TODO Auto-generated method stub
		loadMoreTask();
	}
	
	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		// TODO Auto-generated method stub
		 refreshTask();
	}
	private void refreshTask() {
		// TODO Auto-generated method stub
		//mAbPullToRefreshView.onHeaderRefreshFinish();
		i=1;
		Isrefresh=true;
		handler_.getlist(home_hyvideo.this,"2",""+type, "10", 1+"","2",Type.getcourse);
	}
	int i=1;
	private void loadMoreTask() {
		// TODO Auto-generated method stub
		i++;
		handler_.getlist(home_hyvideo.this,"2",""+type, "10", i+"","2",Type.getcourse);
		//mAbPullToRefreshView.onFooterLoadFinish();
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("home_hyvideo");
		MobclickAgent.onResume(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("home_hyvideo");
		MobclickAgent.onPause(this);
	}

}