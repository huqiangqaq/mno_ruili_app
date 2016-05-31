package mno.ruili_app.my;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mno.ruili_app.R;
import mno.ruili_app.appuser;

import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class My_xgwork extends Activity implements OnClickListener {
	webhandler handler_,handler_2; 
	ExAdapter adapter;  
    ExpandableListView exList;//可扩展的ListView  
	private List<String> groupData=new ArrayList<String>(); 
	private List<List<String>> childData = new ArrayList<List<String>>();//小组成员	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_xgwork);			
		exList = (ExpandableListView) this.findViewById(R.id.mlistview);	
		adapter=new ExAdapter(this);
		exList.setAdapter(adapter);
		initData();
		//为ExpandableListView的子view设置相应点击事件
		exList.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
					int childPosition, long id) {
				//1.修改到后台	 
				handler_2=new webhandler(){
					
				};
	        	Map<String, String> params = new HashMap<String, String>();	
	        	String a=childData.get(groupPosition).get(childPosition).toString();
	        	Log.i("ccc", a+"");	
				params.put("profession", a);	 
				handler_2.SetRequest(new RequestType("4",Type.editInfo),params); 
				appuser.getInstance().getUserinfo().work=a;
				My_xgwork.this.finish();
				return true;
			}
		});
	}
	 @Override
		protected void onResume() {
			// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("My_xgwork");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("My_xgwork");
		MobclickAgent.onPause(this);
	}
	
	JSONObject temp,temp2;
	JSONArray child;
	String name1,name2;
	String id1,id2;
	private void initData() {    	
		handler_=new webhandler(){
			public void OnResponse(JSONObject response) {
				try {
					JSONArray data=response.getJSONArray("data");					
					int length=data.length();
					for(int i=0;i<length;i++){
						temp=data.getJSONObject(i);
						//1.获得工作大纲
						id1=temp.getString("id");
						name1=temp.getString("name");
						Log.i("aaa", name1+"");
						groupData.add(name1);
						adapter.notifyDataSetChanged();
						//2.获得工作小纲
						child=temp.getJSONArray("child");						
						int length2=child.length();
						List<String> children = new ArrayList<String>();
						for(int n=0;n<length2;n++){
							temp2=child.getJSONObject(n);
							id2=temp2.getString("id");
							name2=temp2.getString("name");	
							Log.i("bbb", name2+"");						
							children.add(name2);
						}
						childData.add(children);
						adapter.notifyDataSetChanged();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};		
		Map<String, String> params = new HashMap<String, String>();
		handler_.SetRequest(new RequestType("ZP",Type.getJobCate),params);							
	}
	/*
	 * 自定义适配器
	 */
	//关键代码是这个可扩展的listView适配器  
    class ExAdapter extends BaseExpandableListAdapter {  
        Context context;  
  
        public ExAdapter(Context context) {  
            super();  
            this.context = context;  
        }  
        //1.得到大组成员的view  
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {  
            View view = convertView;  
            if (view == null) {  
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
                view = inflater.inflate(R.layout.list_item_work1, null);  
            }  
  
            TextView title = (TextView) view.findViewById(R.id.groupName);  
            title.setText(getGroup(groupPosition).toString());//设置大组成员名称  
  
            ImageView image = (ImageView) view.findViewById(R.id.ivPic);//是否展开大组的箭头图标  
//            if (isExpanded)//大组展开时  
//                image.setBackgroundResource(R.drawable.my_item_go2);  
//            else//大组合并时  
//                image.setBackgroundResource(R.drawable.my_item_go);  
  
            return view;  
        }  
        //得到大组成员的id  
        public long getGroupId(int groupPosition) {  
            return groupPosition;  
        }  
        //得到大组成员名称  
        public Object getGroup(int groupPosition) {  
            return groupData.get(groupPosition);  
        }  
        //得到大组成员总数  
        public int getGroupCount() {  
            return groupData.size();   
        }  
  
        // 2.得到小组成员的view  
        public View getChildView(int groupPosition, int childPosition,  
                boolean isLastChild, View convertView, ViewGroup parent) {  
            View view = convertView;  
            if (view == null) {  
                LayoutInflater inflater = LayoutInflater.from(context);  
                view = inflater.inflate(R.layout.list_item_work2, null);  
            }  
            final TextView title = (TextView) view.findViewById(R.id.childName);  
            title.setText(childData.get(groupPosition).get(childPosition).toString());//设置大组成员名称 
  
            return view;  
        }  
        //得到小组成员id  
        public long getChildId(int groupPosition, int childPosition) {  
            return childPosition;  
        }  
        //得到小组成员  
        public Object getChild(int groupPosition, int childPosition) {  
            return childData.get(groupPosition).get(childPosition);
        }  
        //得到小组成员的数量  
        public int getChildrenCount(int groupPosition) {  
            return childData.get(groupPosition).size();  
        }  
        /** 
         * Indicates whether the child and group IDs are stable across changes to the 
         * underlying data. 
         * 表明大組和小组id是否稳定的更改底层数据。 
         * @return whether or not the same ID always refers to the same object 
         * @see Adapter#hasStableIds() 
         */  
        public boolean hasStableIds() {  
            return false;  
        }  
        //得到小组成员是否被选择  
        public boolean isChildSelectable(int groupPosition, int childPosition) {        	
            return true;  
        }                 
    }  
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}