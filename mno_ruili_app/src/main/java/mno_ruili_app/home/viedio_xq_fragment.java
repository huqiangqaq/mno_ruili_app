package mno_ruili_app.home;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno_ruili_app.adapter.ImageListAdapter;
import mno_ruili_app.adapter.tv;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class viedio_xq_fragment extends Fragment{
	View View_;
	TextView xq_js;
	ListView mListView;
	ArrayList<tv>   mTvList = new ArrayList<tv>();
	ImageListAdapter mylistViewAdapter ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return View_ = inflater.inflate(R.layout.viedio_xq_fragment, container,
				false);
	
		
	/*	
		if(Constant.view_xq.length()==0||Constant.view_xqnr.length()==0)
		{
			timer.schedule(task,500,500);
		}
		else
		{
			 xq_js.setText(Constant.view_xqnr);
			 try {
				 mTvList.clear();
				 JSONObject jsonObject;
			     //jsonObject = new JSONObject(Constant.view_xq.toString());
			     //jsonObject = new JSONObject(Constant.view_xq);
				 JSONArray  data=  new JSONArray(Constant.view_xq.toString() );
					int length=data.length();
				    for(int i=0;i<= length-1; i++)
				    {
				    	JSONObject type_json;
						type_json = data.getJSONObject(i);
						String name =  type_json.getString("title");
						String content =  type_json.getString("desc");
						String id =  type_json.getString("id");
						String imgurl=type_json.getString("coverImg");
						final tv mtv = new tv(id,name, content,imgurl);
						mTvList.add(mtv);
						//mylistViewAdapter.notifyDataSetChanged();
						
				    //listData.add(data.getJSONObject(i).getString("Name"));
				   
				    }
				    setListView( );
				 } catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						if (timer != null) {
							  timer.cancel( );
							  timer = null;
							  }
					}
		}
		
		return View_;
		
	}
	private void init() {
		// TODO Auto-generated method stub
		
		 xq_js=(TextView) View_.findViewById(R.id.xq_js); 
		 mListView = (ListView)View_.findViewById(R.id.mListView);

		  
	}
	private void setListView( ) {
		// TODO Auto-generated method stub
		 mylistViewAdapter = new ImageListAdapter(View_.getContext(), mTvList,
					R.layout.item_list, new String[] { "itemsIcon" },
					new int[] { R.id.itemsIcon,R.id.itemstext,R.id.itemstext2});
		   mListView.setAdapter(mylistViewAdapter);
		if(mylistViewAdapter == null){  
           
            return;  
        }  
		int totalHeight = 0;  
        for(int i = 0;i < mylistViewAdapter.getCount(); i++){  
            View itemView = mylistViewAdapter.getView(i, null, mListView);  
            itemView.measure(0, 0);  
            totalHeight += itemView.getMeasuredHeight();  
            
        }  
        ViewGroup.LayoutParams layoutParams = mListView.getLayoutParams();  
        layoutParams.height = totalHeight + (mListView.getDividerHeight() * (mylistViewAdapter.getCount() - 1));//总行高+每行的间距  
        mListView.setLayoutParams(layoutParams); 
		mListView.setAdapter(mylistViewAdapter);
		mListView.setVisibility(View.VISIBLE);
	}
	  Timer timer = new Timer( );
	  TimerTask task = new TimerTask( ) {
	  public void run ( ) {
	  Message message = new Message( );
	  message.what = 1;
	  handler.sendMessage(message);
	  }
	  };
	  
	   
	  final Handler handler = new Handler( ) {
	  public void handleMessage(Message msg) {
	  switch (msg.what) {
	  case 1:
		 
	  update( );
	  break;
	  }
	  super.handleMessage(msg);
	  }
     int i=0;
	private void update() {
		// TODO Auto-generated method stub
		 i++;
		 Toast.makeText(View_.getContext(), i+"", Toast.LENGTH_SHORT).show(); 
		if(Constant.view_xq.length()>=0&&Constant.view_xqnr.length()>=0)
		{
		
			  if (timer != null) {  
				  timer.cancel();  
				  timer = null;  
		        }  
		  
		        if (task != null) {  
		        	task.cancel();  
		        	task = null;  
		        }     
			 xq_js.setText(Constant.view_xqnr);
			 try {
			
			 JSONObject jsonObject;
		     //jsonObject = new JSONObject(Constant.view_xq.toString());
		     //jsonObject = new JSONObject(Constant.view_xq);
			 JSONArray  data=  new JSONArray(Constant.view_xq.toString() );
				int length=data.length();
			    for(int i=0;i<= length-1; i++)
			    {
			    	JSONObject type_json;
					type_json = data.getJSONObject(i);
					String name =  type_json.getString("title");
					String content =  type_json.getString("desc");
					String id =  type_json.getString("id");
					String imgurl=type_json.getString("coverImg");
					final tv mtv = new tv(id,name, content,imgurl);
					mTvList.add(mtv);
					//mylistViewAdapter.notifyDataSetChanged();
					
			    //listData.add(data.getJSONObject(i).getString("Name"));
			   
			    }
			    setListView( );
			 } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					if (timer != null) {
						  timer.cancel( );
						  timer = null;
						  }
				}
		}
	}

	};
	
	  @Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

		  
		  if (timer != null) {  
			  timer.cancel();  
			  timer = null;  
	        }  
	  
	        if (task != null) {  
	        	task.cancel();  
	        	task = null;  
	        }     
		super.onDestroy();
	}
	  
	public void onDestroy ( ) {
		  if (timer != null) {
		  timer.cancel( );
		  timer = null;
		  }
		  timer = null;
		  
		  if (timer != null) {  
			  timer.cancel();  
			  timer = null;  
	        }  
	  
	        if (task != null) {  
	        	task.cancel();  
	        	task = null;  
	        }     
	  }*/
}}