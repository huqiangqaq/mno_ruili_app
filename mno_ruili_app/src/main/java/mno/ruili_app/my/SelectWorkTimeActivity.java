package mno.ruili_app.my;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno_ruili_app.entity.Worker;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SelectWorkTimeActivity extends Activity{
	ListView mListView;
	MyAdapter adapter;
	List<Worker>   list = new ArrayList<Worker>();
	int preposition;
    boolean isShow = false;
    Map<Integer,Boolean> map=new HashMap<Integer,Boolean>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_work_time);	
		init();		
		adapter=new MyAdapter();
		mListView.setAdapter(adapter);
	}
	

	private void init() {		
		webhandler handler=new webhandler(){
			public void OnResponse(org.json.JSONObject response) {
				try {
					JSONArray data=response.getJSONArray("data");
					for(int i=0;i<data.length();i++){
						JSONObject salary=data.getJSONObject(i);
						String salaryId=salary.getString("id");
						String salaryResult=salary.getString("name");
						Worker worker=new Worker(salaryId, salaryResult);
						list.add(worker);
						map.put(i,isShow);
					}
					adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		Map<String, String> params = new HashMap<String, String>();
		handler.SetRequest(new RequestType("ZP",Type.getJobYear),params);	
		
		mListView=(ListView) findViewById(R.id.mListView);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				map.put(position,true);
                map.put(preposition, false);
                adapter.notifyDataSetChanged();
                if (position!=preposition){
                    preposition = position;
                }else {
                    map.put(preposition,true);
                }
				Intent intent=new Intent();
				intent.putExtra("workTimeId", list.get(position).getWorkerId());
				intent.putExtra("workTimeResult", list.get(position).getJobName());
				SelectWorkTimeActivity.this.setResult(RESULT_OK, intent);
				SelectWorkTimeActivity.this.finish();				
			}
		}); 
	}
	private class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		class ViewHolder{
            TextView tv_salary;
            ImageView iv_salary;
        }
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView==null){
				holder=new ViewHolder();
				convertView = LayoutInflater.from(SelectWorkTimeActivity.this).inflate(R.layout.list_item_selectsalary,parent,false);
				holder.tv_salary=(TextView)convertView.findViewById(R.id.tv_salary);
				holder.iv_salary=(ImageView)convertView.findViewById(R.id.iv_salary);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_salary.setText(list.get(position).getJobName());
			if (map.get(position)){ //显示
				holder.iv_salary.setVisibility(View.VISIBLE);
            }else {//不显示
            	holder.iv_salary.setVisibility(View.INVISIBLE);
            }
			return convertView;
		}		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("SelectWorkTimeActivity");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("SelectWorkTimeActivity");
		MobclickAgent.onPause(this);
	}
}

