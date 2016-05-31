package mno_ruili_app.nei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;
import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.R.color;
import mno.ruili_app.ct.CustomEditText;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.main.Main;
import mno.ruili_app.my.my_wd;
import mno_ruili_app.adapter.ImageListAdapter;
import mno_ruili_app.adapter.ImageListAdapter_mywd;
import mno_ruili_app.adapter.mySimpleAdapter;
import mno_ruili_app.adapter.mywd;
import mno_ruili_app.adapter.tv;

import mno_ruili_app.home.home_video;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;

public class nei_find extends Activity{
	GridView nei_tag_view;
	ListView mListView,mListView2,mListView3;
	String[] mFrom;
	CustomEditText nei_find_edi;
	LinearLayout find_tag ,nei_fl;
	ImageListAdapter_mywd mylistViewAdapter,mylistViewAdapter2;
	ImageListAdapter mylistViewAdapter3 ;
	List<mywd>   mList = new ArrayList<mywd>();
	List<mywd>   mList2 = new ArrayList<mywd>();
	ArrayList<tv>   mTvList = new ArrayList<tv>();
	 final ArrayList<tv>   mListtag = new ArrayList<tv>();
	webhandler handler_,handler_2;
	JSONObject type_json;
	TextView but_nei_zx,but_nei_wd,bz_nei_zx,bz_nei_wd;
	   mySimpleAdapter  saImageItems;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nei_find);
		init();
		
	}
	private void init() {
		// TODO Auto-generated method stub
		nei_find_edi=(CustomEditText)this.findViewById(R.id.nei_find_edi);
		//nei_find_edi.setFocusable(false);
		nei_fl=(LinearLayout)this.findViewById(R.id.nei_fl);
		but_nei_zx= (TextView) this.findViewById(R.id.but_nei_zx);
		bz_nei_zx= (TextView) this.findViewById(R.id.bz_nei_zx);
		
		but_nei_wd= (TextView) this.findViewById(R.id.but_nei_wd);
		bz_nei_wd= (TextView) this.findViewById(R.id.bz_nei_wd);
		find_tag=(LinearLayout)this.findViewById(R.id.find_tag);
		nei_tag_view=(GridView)this.findViewById(R.id.nei_tag_findview);
		mListView=(ListView)this.findViewById(R.id.mListView);
		mListView2=(ListView)this.findViewById(R.id.mListView2);
		mListView3=(ListView)this.findViewById(R.id.mListView3);
		
		mylistViewAdapter3 = new ImageListAdapter(this, mTvList,
					R.layout.item_list, new String[] { "itemsIcon" },
					new int[] { R.id.itemsIcon,R.id.itemstext,R.id.itemstext2});
	       
	    mListView3.setAdapter(mylistViewAdapter3);
	    mListView3.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(nei_find.this,home_video.class);
				
				intent.putExtra("ID",mTvList.get(position).getid()); 
				Constant.itemid=mTvList.get(position).getid();
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				nei_find.this.startActivity(intent);
			}
    		
    	});   
	       
		
		mylistViewAdapter = new ImageListAdapter_mywd(this, mList,
					R.layout.item_wen, new String[] { "itemsIcon" },
					new int[] { R.id.title,R.id.wdcontent ,R.id.wdname,R.id.wdtime,R.id.answerTotal});
		
		mListView.setAdapter(mylistViewAdapter);
		
		
		mylistViewAdapter2 = new ImageListAdapter_mywd(this, mList2,
				R.layout.item_wen, new String[] { "itemsIcon" },
				new int[] { R.id.title,R.id.wdcontent ,R.id.wdname,R.id.wdtime,R.id.answerTotal});
	
	    mListView2.setAdapter(mylistViewAdapter2);
		mListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(nei_find.this,nei_wdxq.class);
				Constant.itemid=mList.get(position).getid();
				//intent.putExtra("ID",mList.get(position).getid());  
				nei_find.this.startActivity(intent);
			}
    		
    	});
		
		mListView2.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(nei_find.this,nei_zx.class);
				Constant.zxid=mList2.get(position).getid();
				Constant.zxtitle=mList2.get(position).getanswerTotal();
				intent.putExtra("ID",mList2.get(position).getid());  
				nei_find.this.startActivity(intent);
			}
    		
    	});
		handler_ = new webhandler(){

			
			@Override
			public void OnResponse(JSONObject response) {
				try {
					/*if(Isrefresh)
					{
						mAbPullToRefreshView.onHeaderRefreshFinish();
					}*/
					//if(response.getJSONObject("data").length()<2)
					Constant.wd_find="";	
					mTvList.clear();
					mList2.clear();
					mList.clear();
					
					try {
						
						
					JSONArray data = response.getJSONArray("data");
			
					int length=data.length();
				    for(int i=0;i< length; i++){
					    type_json =   data.getJSONObject(i);
					    //listData.add(data.getJSONObject(i).getString("Name"));
					    String name =  type_json.getString("title");
					    String content =  type_json.getString("desc");
					    String id =  type_json.getString("id");
					    String imgurl=type_json.getString("coverImg");
					    
					    final tv mtv = new tv(id,name, content,imgurl);
					 //   if(type_json.getString("typeName").equals("视觉"))
					    mTvList.add(mtv);
				    
				    }
				    mylistViewAdapter3.notifyDataSetChanged();
				    } catch (JSONException e) {
						// TODO Auto-generated catch block
				    	mTvList.clear();  mylistViewAdapter3.notifyDataSetChanged();
					}
				    
					try {
					JSONArray data = response.getJSONObject("data").getJSONArray("question");
					int length=data.length();
				    for(int i=0;i< length; i++){
					    type_json =   data.getJSONObject(i);
					    //listData.add(data.getJSONObject(i).getString("Name"));
					    String name =  "";
					    String content =  type_json.getString("content");
					    String id =  type_json.getString("questionId");
					    String statusName =  type_json.getString("name");
					    String create_at =  type_json.getString("create_at");
					    String answerTotal =  type_json.getString("answerTotal");
					    
					    final mywd mwd = new mywd(name,content, id, statusName, create_at, answerTotal);
					    
						mList.add(mwd);
				   
				    }
				    mylistViewAdapter.notifyDataSetChanged();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						mList.clear();
						mylistViewAdapter.notifyDataSetChanged();
					}
					
					try {
				    JSONArray data2 = response.getJSONObject("data").getJSONArray("news");
					int length2=data2.length();
				    for(int i=0;i< length2; i++){
					    type_json =   data2.getJSONObject(i);
					    //listData.add(data.getJSONObject(i).getString("Name"));
					    String name =  type_json.getString("title");
					    String content =  type_json.getString("content");
					    String id =  type_json.getString("id");
					    //String statusName =  type_json.getString("name");
					    String publish_at =  type_json.getString("publish_at");
					    String replyTotal =  type_json.getString("replyTotal");
					    if(content.length()>30)
					    	content=content.substring(0, 28)+"...";
					    final mywd mwd = new mywd(content,name, id, "", publish_at, replyTotal);
					    
						mList2.add(mwd);
				   
				    }
				    mylistViewAdapter2.notifyDataSetChanged();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						mList2.clear();mylistViewAdapter2.notifyDataSetChanged();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block  JSON
					e.printStackTrace();
				}
			}
		};
		handler_.SetProgressDialog(nei_find.this);

		
		nei_find_edi.setOnEditorActionListener(new OnEditorActionListener(){

			@Override
			public boolean onEditorAction(TextView v, int actionId,KeyEvent event) {
				// TODO Auto-generated method stub
				
				if (actionId ==EditorInfo.IME_ACTION_SEARCH) {
	                //点击搜索要做的操作								
					if(Constant.viedio_find.equals("movie")){
						find_tag.setVisibility(View.INVISIBLE);			
						mListView3.setVisibility(View.VISIBLE);
					}else{
						find_tag.setVisibility(View.INVISIBLE);
						nei_fl.setVisibility(View.VISIBLE);
						if(mListView2.getVisibility()==0 ||mListView.getVisibility()==0){			
						}else{
							mListView2.setVisibility(View.VISIBLE);
							mListView.setVisibility(View.INVISIBLE);//				
							but_nei_wd.setTextColor(color.gray_91);				
							but_nei_zx.setTextColor(android.graphics.Color.parseColor("#387ebc"));
							Drawable nei_zx = getResources().getDrawable(R.drawable.nei_zx);
							but_nei_zx.setCompoundDrawablesWithIntrinsicBounds(nei_zx,null, null,null);
							bz_nei_zx.setBackgroundColor(android.graphics.Color.parseColor("#387ebc"));
							bz_nei_wd.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));		
						}
					}					
					if(Constant.wd_find.length()>0){						
						mListView3.setVisibility(View.INVISIBLE);
						find_tag.setVisibility(View.INVISIBLE);
						nei_fl.setVisibility(View.VISIBLE);
						LoadData(Constant.wd_find.toString().trim());
						nei_find_edi.setText(Constant.wd_find.toString().trim());
					}
					if(Constant.viedio_what.length()>0){						
						mListView3.setVisibility(View.VISIBLE);
						find_tag.setVisibility(View.INVISIBLE);
						nei_fl.setVisibility(View.INVISIBLE);
						mListView2.setVisibility(View.INVISIBLE);
						mListView.setVisibility(View.INVISIBLE);
						LoadData(Constant.viedio_what.toString().trim());
						nei_find_edi.setText(Constant.viedio_what.toString().trim());
						Constant.viedio_what="";						
					}				
					LoadData(nei_find_edi.getText().toString().trim());
	              }							
				return false;
				}
			});																		
		nei_tag_view.setGravity(Gravity.CENTER);
		nei_tag_view.setHorizontalSpacing(15);
		//nei_tag_addview.setPadding(5, 5, 5, 5);/
		nei_tag_view.setSelector(new ColorDrawable(Color.TRANSPARENT));
		nei_tag_view.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		nei_tag_view.setVerticalSpacing(30);
		
		
		nei_tag_view.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					TextView title = (TextView) view.findViewById(R.id.but_tag);
					String tag=title.getText().toString();
					//MessageBox.Show(getApplicationContext(), tag);	
	
					if(Constant.viedio_find.equals("movie"))
					{
						find_tag.setVisibility(View.INVISIBLE);
						
						mListView3.setVisibility(View.VISIBLE);
					}
					else{

						find_tag.setVisibility(View.INVISIBLE);
						nei_fl.setVisibility(View.VISIBLE);
						mListView2.setVisibility(View.VISIBLE);
						mListView.setVisibility(View.INVISIBLE);//
						
						but_nei_wd.setTextColor(color.gray_91);
						
						but_nei_zx.setTextColor(android.graphics.Color.parseColor("#387ebc"));
						Drawable nei_zx = getResources().getDrawable(R.drawable.nei_zx);
						but_nei_zx.setCompoundDrawablesWithIntrinsicBounds(nei_zx,null, null,null);
						bz_nei_zx.setBackgroundColor(android.graphics.Color.parseColor("#387ebc"));
						bz_nei_wd.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
					}
					nei_find_edi.setText(tag);
					LoadData(tag);
				}});
		mFrom=new String[]{"直通车","运营","技巧","钻展","店铺","店小二","纠纷","客服","线下","买家","卖家","PS","程序","美工","智能"};
		 List<Map<String, Object>> items = new ArrayList<Map<String,Object>>(); 
	        for (int i = 0; i <15; i++) { 
	          Map<String, Object> item = new HashMap<String, Object>(); 
	          //item.put("imageItem", R.drawable.icon);//添加图像资源的ID   
	          item.put("textItem", mFrom[i]);//按序号添加ItemText   
	          items.add(item); 
	         
	        } 
	       	        
	         saImageItems = new mySimpleAdapter(this, mListtag,  R.layout.item_tag_big, new String[] {"textItem"},   new int[] {R.id.but_tag});  
			  nei_tag_view.setAdapter(saImageItems);  		  		  
			  handler_2 = new webhandler(){

					@Override
					public void OnResponse(JSONObject response) {
						// TODO Auto-generated method stub
						// TODO Auto-generated method stub
						 
						try {
							JSONArray  data=  new JSONArray( response.getString("data") );
							int length=data.length();
						    for(int i=0;i< length; i++)
						    {
						    	JSONObject type_json =   data.getJSONObject(i);
						    	
						    	final tv mtv = new tv(type_json.getString("tag"), "0","");
						        mListtag.add(mtv);
						    }
							
						    saImageItems.notifyDataSetChanged();
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
						
					}
					
					};
					 Map<String, String> params = new HashMap<String, String>();
					 handler_2.SetRequest(new RequestType("4",Type.getTags),params);
			  
	}
	public void LoadData(String method )
	{
		
	     Map<String, String> params = new HashMap<String, String>();
	     params.put("pagesize","50");
		 if(Constant.viedio_find.equals("movie"))
		 {
			 params.put("searchText",method);
		 if(Constant.viedio_label.length()>0)
		 {
			params.put("label",Constant.viedio_label);
			
		 }
	     handler_.SetRequest(new RequestType("2",Type.searchCourse),params);
		 }
		 else
		 {
			 params.put("search",method);
			 handler_.SetRequest(new RequestType("3",Type.searchAtRegion),params);
		 }
			 
		 /*else
			 handler_2.SetRequest(new RequestType("4",Type.getMyQA),params);*/
	}
	
	public void onclick(View v)
	
	{
		
		Drawable nei_zx = getResources().getDrawable(R.drawable.nei_zx);
		Drawable nei_zx_ = getResources().getDrawable(R.drawable.nei_zx_);
		Drawable nei_wd = getResources().getDrawable(R.drawable.nei_wd_);
		Drawable nei_wd_ = getResources().getDrawable(R.drawable.nei_wd);
	if(v.getId()==R.id.back)
	{
		if(Constant.fromwd.equals("1"))
		{
		 Constant.viedio_label="";
	     Constant.fromwd="0";
	     Constant.viedio_what="";
	     finish();
	     return;
		}
		  if(find_tag.getVisibility()==4 )
          {
       	   if(Constant.viedio_find.equals("movie"))
				{
					find_tag.setVisibility(View.VISIBLE);
					
					mListView3.setVisibility(View.INVISIBLE);
				}
				else{
					
					but_nei_wd.setTextColor(color.gray_91);
					
					but_nei_zx.setTextColor(android.graphics.Color.parseColor("#387ebc"));
					but_nei_zx.setCompoundDrawablesWithIntrinsicBounds(null,null, null,null);
					bz_nei_zx.setBackgroundColor(android.graphics.Color.parseColor("#387ebc"));
					bz_nei_wd.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));

					
					find_tag.setVisibility(View.VISIBLE);
					nei_fl.setVisibility(View.INVISIBLE);
					mListView2.setVisibility(View.INVISIBLE);
					mListView.setVisibility(View.INVISIBLE);
				}
          }
          else
       	   finish();
	}
	else if(v.getId()==R.id.nei_bj_jf)
	{
		
	}
	else if(v.getId()==R.id.nei_zx)
	{
		
		
    	but_nei_wd.setTextColor(color.gray_91);
		mListView2.setVisibility(View.VISIBLE);
		mListView.setVisibility(View.INVISIBLE);//
		but_nei_zx.setTextColor(android.graphics.Color.parseColor("#387ebc"));
		but_nei_wd.setCompoundDrawablesWithIntrinsicBounds(nei_wd_,null, null,null);
		but_nei_zx.setCompoundDrawablesWithIntrinsicBounds(nei_zx,null, null,null);
		bz_nei_zx.setBackgroundColor(android.graphics.Color.parseColor("#387ebc"));
		bz_nei_wd.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
		
	}
	else if(v.getId()==R.id.nei_wd)
	{
		but_nei_zx.setTextColor(color.gray_91);
		mListView2.setVisibility(View.INVISIBLE);//
		mListView.setVisibility(View.VISIBLE);
		but_nei_wd.setTextColor(android.graphics.Color.parseColor("#387ebc"));
		but_nei_wd.setCompoundDrawablesWithIntrinsicBounds(nei_wd,null, null,null);
		but_nei_zx.setCompoundDrawablesWithIntrinsicBounds(nei_zx_,null, null,null);
		bz_nei_zx.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
		bz_nei_wd.setBackgroundColor(android.graphics.Color.parseColor("#387ebc"));
		
	}
	
	}
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK) {
	        	if(Constant.fromwd.equals("1"))
	        			{
	        		     Constant.fromwd="0";
	        		     Constant.viedio_what="";
	        		     Constant.viedio_label="";
	        		     finish();
	        		    
	        			}
	        	
	        	else if(find_tag.getVisibility()==4 )
	              {
	        	   if(Constant.viedio_find.equals("movie"))
					{
						find_tag.setVisibility(View.VISIBLE);
						
						mListView3.setVisibility(View.INVISIBLE);
					}
					else{

						find_tag.setVisibility(View.VISIBLE);
						nei_fl.setVisibility(View.INVISIBLE);
						mListView2.setVisibility(View.INVISIBLE);
						mListView.setVisibility(View.INVISIBLE);
					}
	           }
	           else
	        	   finish();
	        	
	            return false;
	        }
	        return super.onKeyDown(keyCode, event);
	    }
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		

		if(Constant.wd_find.length()>0)
		{
			
			mListView3.setVisibility(View.INVISIBLE);
			find_tag.setVisibility(View.INVISIBLE);
			nei_fl.setVisibility(View.VISIBLE);
			mListView2.setVisibility(View.INVISIBLE);
			mListView.setVisibility(View.VISIBLE);
			LoadData(Constant.wd_find.toString().trim());
			nei_find_edi.setText(Constant.wd_find.toString().trim());
			but_nei_zx.setTextColor(color.gray_91);
			
			Drawable nei_zx_ = getResources().getDrawable(R.drawable.nei_zx_);
			Drawable nei_wd = getResources().getDrawable(R.drawable.nei_wd_);
			but_nei_wd.setCompoundDrawablesWithIntrinsicBounds(nei_wd,null, null,null);
		    but_nei_zx.setCompoundDrawablesWithIntrinsicBounds(nei_zx_,null, null,null);
			but_nei_wd.setTextColor(android.graphics.Color.parseColor("#387ebc"));
			
			bz_nei_zx.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
			bz_nei_wd.setBackgroundColor(android.graphics.Color.parseColor("#387ebc"));
			
		}
		if(Constant.viedio_what.length()>0)
		{
			
			mListView3.setVisibility(View.VISIBLE);
			find_tag.setVisibility(View.INVISIBLE);
			nei_fl.setVisibility(View.INVISIBLE);
			mListView2.setVisibility(View.INVISIBLE);
			mListView.setVisibility(View.INVISIBLE);
			LoadData(Constant.viedio_what.toString().trim());
			nei_find_edi.setText(Constant.viedio_what.toString().trim());
			Constant.viedio_what="";
			//nei_fl.setVisibility(View.VISIBLE);
		/*	but_nei_zx.setTextColor(color.gray_91);
			
			
			but_nei_wd.setTextColor(android.graphics.Color.parseColor("#387ebc"));
			but_nei_wd.setCompoundDrawablesWithIntrinsicBounds(null,null, null,null);
			bz_nei_zx.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
			bz_nei_wd.setBackgroundColor(android.graphics.Color.parseColor("#387ebc"));*/
			
		}
	}
	//
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("nei_find");
		MobclickAgent.onResume(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("nei_find");
		MobclickAgent.onPause(this);
	}

}
