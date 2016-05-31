package mno_ruili_app.nei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.main.Main;
import mno.ruili_app.my.mycheckcode;
import mno.ruili_app.my.myfindcheckcode;
import mno.ruili_app.my.mylogin;
import mno_ruili_app.adapter.ImageGridAdapter;
import mno_ruili_app.adapter.mySimpleAdapter;
import mno_ruili_app.adapter.tv;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class nei_bj_tag extends AbActivity  {
	ImageView nei_bj_addimg;
	LinearLayout nei_bj_addview;
	EditText nei_bj_edi;
	GridView nei_tag_view,nei_tag_addview;
	String text,img,Score;
	 private String[] mFrom,mFrom2;
	 int selectnum=0;
	 mySimpleAdapter saImageItems,saImageItems2;
	 webhandler handler_,handler_2; 
	 final ArrayList<tv>   mList = new ArrayList<tv>();
	  final ArrayList<tv>   mchoseList = new ArrayList<tv>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nei_bj_tag);
		Intent intent =this.getIntent();
		text=intent.getStringExtra("text").toString();
		img=intent.getStringExtra("img").toString();
		Score=intent.getStringExtra("score").toString();
		if(Constant.isbj.equals("0"))
		{
			String[] pic;
			
			pic  = Constant.bj_tag.split(",");
				
	          for (int i = 0; i <pic.length; i++) { 
	              
	              final tv mtv = new tv(pic[i], "0","");
	              if(pic[i].length()>0)
	              {
	              mchoseList.add(mtv);
	              selectnum++;}
	            } 		
	          }
		
		nei_tag_addview=(GridView)this.findViewById(R.id.nei_tag_addview);
		nei_tag_view=(GridView)this.findViewById(R.id.nei_tag_view);
		nei_tag_view.setGravity(Gravity.CENTER);
		nei_tag_view.setHorizontalSpacing(10);
		nei_tag_view.setPadding(5, 5, 5, 5);
		nei_tag_view.setSelector(new ColorDrawable(Color.TRANSPARENT));
		nei_tag_view.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		nei_tag_view.setVerticalSpacing(10);
	
		nei_tag_addview.setGravity(Gravity.CENTER);
		nei_tag_addview.setHorizontalSpacing(15);
		//nei_tag_addview.setPadding(5, 5, 5, 5);/
		nei_tag_addview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		nei_tag_addview.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		nei_tag_addview.setVerticalSpacing(30);
		mFrom=new String[]{"客服","直通车","店铺"};
		mFrom2=new String[]{"直通车","运营","技巧","钻展","店铺","店小二","纠纷","客服","线下","买家","卖家","PS","程序","美工","智能"};
		
	
		 
/*        //final List<Map<String, Object>> items2 = new ArrayList<Map<String,Object>>(); 
        for (int i = 0; i <mFrom2.length; i++) { 
       
          final tv mtv = new tv(mFrom2[i], "0","");
          mList.add(mtv);
        } */
		
		
        saImageItems = new mySimpleAdapter(this, //没什么解释  
        		mchoseList,//数据来源   
                  R.layout.item_tag,//night_item的XML实现  
                    
                  //动态数组与ImageItem对应的子项          
                  new String[] {"textItem"},   
                    
                  //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
                  new int[] {R.id.but_tag});  
		 saImageItems2 = new mySimpleAdapter(this, //没什么解释  
				mList,//数据来源   
                 R.layout.item_tag_big,//night_item的XML实现  
                   
                 //动态数组与ImageItem对应的子项          
                 new String[] {"textItem"},   
                   
                 //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
                 new int[] {R.id.but_tag});  
		  //添加并且显示  
		  nei_tag_view.setAdapter(saImageItems);  
		  nei_tag_addview.setAdapter(saImageItems2); 
		  
		  nei_tag_addview.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					TextView title = (TextView) view.findViewById(R.id.but_tag);
					if(mList.get(position).getMs().equals("0"))
					{
						
						if(selectnum<4){
							selectnum++;
							final tv mchose = new tv(mList.get(position).getName(), "","");
							mchoseList.add(mchose);
							saImageItems.notifyDataSetChanged();
						mList.get(position).setMs("1");
						Drawable drawable=nei_bj_tag.this.getResources().getDrawable(R.drawable.radius_blue);
						//TimeButton.this.setBackgroundDrawable(drawable);
						title.setBackgroundDrawable(drawable);
						title.setTextColor(android.graphics.Color.parseColor("#ffffff"));;}
						else
						{
							MessageBox.Show(getApplicationContext(), "不能再多了");
						}
					}
					else
					{
						  for (int i = 0; i < mchoseList.size(); i++) {
							  String a=mchoseList.get(i).getName();
							  if (a.equals(""+mList.get(position).getName()))
							  {
								  mchoseList.remove(i);
								  saImageItems.notifyDataSetChanged();
							  }
						  }
						selectnum=selectnum-1;
						mList.get(position).setMs("0");
						Drawable drawable =nei_bj_tag.this.getResources().getDrawable(R.drawable.view_line);
						//but_0.setBackgroundDrawable(drawable);
						title.setBackgroundDrawable(drawable);
						title.setTextColor(android.graphics.Color.parseColor("#7a7a7a"));;
					}
					
				
				}
				});
			InitHandler();
			
		  }
		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			MobclickAgent.onPageStart("nei_bj_tag");
			MobclickAgent.onResume(this); 
		}
		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			MobclickAgent.onPageEnd("nei_bj_tag");
			MobclickAgent.onPause(this);
		}
			private void InitHandler(){
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
						    	if(Constant.isbj.equals("0"))
						    	{
						    		if( Constant.bj_tag.contains(type_json.getString("tag")))
						    				{
						    		final tv mtv = new tv(type_json.getString("tag"), "1","");
							        mList.add(mtv);
							        saImageItems2.notifyDataSetChanged();
						    				}
						    		else
						    		{
						    			final tv mtv = new tv(type_json.getString("tag"), "0","");
								        mList.add(mtv);
								        saImageItems2.notifyDataSetChanged();
						    		}
						    	}else
						    	{
						    	final tv mtv = new tv(type_json.getString("tag"), "0","");
						        mList.add(mtv);
						        saImageItems2.notifyDataSetChanged();
						    	}
						    }
						    
							
						   /* if(Constant.isbj.equals("0"))
							{
								String[] pic;
								
								pic  = Constant.bj_tag.split(",");
									
						          for (int i = 0; i <pic.length; i++) { 
						              
						              final tv mtv = new tv(pic[i], "0","");
						              mchoseList.add(mtv);
						            } 		
						          }*/
						    
						    saImageItems2.notifyDataSetChanged();
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
						
					}
					
					};
					 Map<String, String> params = new HashMap<String, String>();
					 handler_2.SetRequest(new RequestType("4",Type.getTags),params);
				
				handler_ = new webhandler(){

					@Override
					public void OnResponse(JSONObject response) {
						// TODO Auto-generated method stub
						// TODO Auto-generated method stub
						 
						try {
							String mess = response.getString("message");
							MessageBox.Show(nei_bj_tag.this, mess);
							//response -> {"message":"创建成功","data":3,"code":"0"}
							Intent intent = new Intent(nei_bj_tag.this,Main.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
							intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
							startActivity(intent);	
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
						
					}
					
					};
					handler_.SetProgressDialog(this);
		  
		  
		
	}
public void onclick(View v)
	{
	if(v.getId()==R.id.nei_but_add)
	{
		if(mchoseList.size()<=0)
		{
			MessageBox.Show(abApplication, "请选择至少一个标签");
			return;
		}
		if(Integer.parseInt(Score)>Integer.parseInt(appuser.getInstance().getUserinfo().pointTotal))
		{
			TextView nei_but_add=(TextView)this.findViewById(R.id.nei_but_add);
			Drawable drawable=nei_bj_tag.this.getResources().getDrawable(R.drawable.radius_hui);
			nei_but_add.setBackgroundDrawable(drawable);
			MessageBox.Show(getApplicationContext(), "您的学分不足");
		}
		else
		{
			
		String a="";
		 for (int i = 0; i < mchoseList.size(); i++) {
			  a=a+mchoseList.get(i).getName()+",";
			  
		 }
		 Map<String, String> params = new HashMap<String, String>();
		  params.put("point",Score);
		  params.put("desc",text);
		  if(img.length()>0)
		  params.put("pics",img.substring(0,img.length()-1));
		  else
		  params.put("pics","");
		  if(a.length()>0)
			  params.put("tags",a.substring(0,a.length()-1));
			  else
			  params.put("tags","");
		if(Constant.isbj.equals("0"))
		{
			  params.put("questionId",Constant.bj_id);
			  handler_.SetRequest(new RequestType("3",Type.updateQues),params);
		}
		else
		{
			  handler_.SetRequest(new RequestType("3",Type.createQues),params);
		}
		 
	
		/*Intent intent = new Intent(nei_bj_tag.this,Main.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
		startActivity(intent);		*/
	}
	}
	else if(v.getId()==R.id.nei_bj_jf)
	{
		
	}
	
	 else if(v.getId()==R.id.back)
	 {
		 digshow();
	 }
	 
	}
public boolean onKeyDown(int keyCode, KeyEvent event) {
	   
    if (keyCode == KeyEvent.KEYCODE_BACK) {
    	
    	digshow();
    	
        return false;
    }
    return super.onKeyDown(keyCode, event);
}
private void digshow() {
	// TODO Auto-generated method stub
	View mView = null;
	mView = mInflater.inflate(R.layout.dialog_myconfig,null);
	AbDialogUtil.showDialog(mView,R.animator.fragment_top_enter,R.animator.fragment_top_exit,R.animator.fragment_pop_top_enter,R.animator.fragment_pop_top_exit);
	Button leftBtn1 = (Button)mView.findViewById(R.id.left_btn);
	Button rightBtn1 = (Button)mView.findViewById(R.id.right_btn);
	TextView title_choices = (TextView)mView.findViewById(R.id.title_choices);
	TextView choice_one_text= (TextView)mView.findViewById(R.id.choice_one_text);
	//title_choices.setText("");
	choice_one_text.setText("您是否要返回上一页再次编辑？");
	leftBtn1.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			AbDialogUtil.removeDialog(nei_bj_tag.this);
		}
		
	});
	
	rightBtn1.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			/*Intent intent = null;
			intent = new Intent(nei_bj_tag.this,nei_bj.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
			startActivity(intent);		*/
			finish();
		}
		
	});
}
}
