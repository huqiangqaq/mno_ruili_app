package mno.ruili_app.my;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mno.ruili_app.Constant;
import mno.ruili_app.R;

import mno_ruili_app.entity.Worker;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectJobActivity extends Activity  {
	webhandler handler_; 
	ExAdapter adapter;  
    ExpandableListView exList;//可扩展的ListView  
	private List<String> groupData=new ArrayList<String>(); 
	private List<List<Worker>> childData = new ArrayList<List<Worker>>();//小组成员	
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
				String jobResult=childData.get(groupPosition).get(childPosition).getJobName().toString();
				String jobId=childData.get(groupPosition).get(childPosition).getWorkerId().toString();
				Intent intent=new Intent();
				intent.putExtra("jobId", jobId);
				intent.putExtra("jobResult", jobResult);
				SelectJobActivity.this.setResult(RESULT_OK, intent);
				SelectJobActivity.this.finish();
				return true;
			}
		});
	}
//    @Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//	super.onResume();
//	MobclickAgent.onPageStart("SelectJobActivity");
//	MobclickAgent.onResume(this); 
//	}
//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		MobclickAgent.onPageEnd("SelectJobActivity");
//		MobclickAgent.onPause(this);
//	}
	
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
						groupData.add(name1);
						adapter.notifyDataSetChanged();
						//2.获得工作小纲
						child=temp.getJSONArray("child");						
						int length2=child.length();
						List<Worker> children = new ArrayList<Worker>();
						for(int n=0;n<length2;n++){
							temp2=child.getJSONObject(n);
							id2=temp2.getString("id");
							name2=temp2.getString("name");
							Worker worker=new Worker(id2, name2);
							children.add(worker);
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
            title.setText(childData.get(groupPosition).get(childPosition).getJobName().toString());//设置小组成员名称 
  
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("SelectJobActivity");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("SelectJobActivity");
		MobclickAgent.onPause(this);
	}
}