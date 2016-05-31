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
import mno.ruili_app.appuser;
import mno.ruili_app.ct.CustomEditText;
import mno.ruili_app.ct.CustomListview;
import mno_ruili_app.adapter.AddEduExpAdapter;
import mno_ruili_app.adapter.AddWorkExpAdapter;
import mno_ruili_app.entity.Worker;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Resume extends Activity implements OnClickListener{
	LinearLayout ll_hopeWork,ll_hopeSalary,ll_hopeCity,ll_workTime,ll_highEdu;
	TextView tv_addWorkExp,tv_addEduExp,tv_ok,tv_hopeWork,tv_hopeSalary,tv_hopeCity,tv_workTime,tv_highEdu;
	GridView gv_workerMsg;
	MyAdapter adapter=new MyAdapter();
	List<Worker>   mList1 = new ArrayList<Worker>();
	List<Worker>   mList2 = new ArrayList<Worker>();
	private CustomListview mListView1;
	private CustomListview mListView2;
	AddWorkExpAdapter adapter1;
	AddEduExpAdapter adapter2;
	CustomEditText cet_phone,cet_email;
	//String[] datas = new String[8];
	LayoutInflater mInflater;
	String jobId="",salaryId="",cityId="",workTimeId="",highEduId="",
			workExpId,eduExpId,resumeId,uId,job_exp_id,stu_exp_id;
	webhandler handler,handler1,handler2;
	String[] workerMsg=new String[2];//
	String jobName,salary ,workTime ,education,workPlace,phone,email,workerImagePath,
		nickName,sex,companyName,position,joinWork,leaveWork,workDescribe,
		school,major,eduLevel,joinSchool,leaveSchool,schoolDescribe;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resume);	
		mInflater = LayoutInflater.from(this);
		handler1=new webhandler(){
			@Override
			public void OnResponse(JSONObject response) {
				// TODO Auto-generated method stub
				super.OnResponse(response);
				try {
					resumeId=response.getString("data");
					loadResumeData();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		uId=appuser.getInstance().getUserinfo().uid.toString();
		Map<String, String> params1 = new HashMap<String, String>();
		params1.put("uid", uId);
		handler1.SetRequest(new RequestType("ZP",Type.getResumeIdByUid),params1);		
		init();
		gv_workerMsg.setAdapter(adapter);//第一次进入填写简历界面时亮点空格的初始化		
	}
	//此页数据的加载
	private void loadResumeData(){		
		handler = new webhandler() {
			public void OnResponse(org.json.JSONObject response) {
				try {
					JSONObject type_json = response.getJSONObject("data");

					jobName = type_json.getString("job_name");
					if(jobName.equals("null")){
						jobName="";
					}
					jobId = type_json.getString("job_name_id");
					salary = type_json.getString("salary");
					if(salary.equals("null")){
						salary="";
					}
					salaryId= type_json.getString("sal_id");
					workTime = type_json.getString("job_year");
					if(workTime.equals("null")){
						workTime="";
					}
					workTimeId=type_json.getString("job_year_id");
					education = type_json.getString("education");
					if(education.equals("null")){
						education="";
					}
					highEduId=type_json.getString("edu_id");
					workPlace = type_json.getString("job_addr");
					if(workPlace.equals("null")){
						workPlace="";
					}
					cityId=type_json.getString("job_addr_id");
					phone=type_json.getString("contact_phone");
					if(phone.equals("null")){
						phone="";
					}
					email=type_json.getString("job_email");
					if(email.equals("null")){
						email="";
					}
					// 亮点名字
					JSONArray points = type_json.getJSONArray("points");					
					if(points.length()>1){
						workerMsg = new String[points.length()+1];						
					}else{
						workerMsg = new String[2];
					}
					for (int j = 0; j < points.length(); j++) {
						JSONObject item = points.getJSONObject(j);
						String point_name = item.getString("point_name");
						workerMsg[j] = point_name;
					}						
					//工作经历
					JSONArray job_exp=type_json.getJSONArray("job_exp");
					for(int i=0;i<job_exp.length();i++){
						JSONObject item=job_exp.getJSONObject(i);
						job_exp_id=item.getString("id");
						companyName=item.getString("comp_name");
						position=item.getString("job_name");
						joinWork=item.getString("join_time");
						leaveWork=item.getString("leave_time");
						workDescribe=item.getString("description");
						Worker work1=new Worker(position,companyName,  joinWork, leaveWork, workDescribe,job_exp_id);
						mList1.add(work1);
					}					
					//教育经历
					JSONArray stu_exp=type_json.getJSONArray("stu_exp");
					for(int i=0;i<stu_exp.length();i++){
						JSONObject item=stu_exp.getJSONObject(i);
						stu_exp_id=item.getString("id");
						school=item.getString("school");
						major=item.getString("pro");
						eduLevel=item.getString("education");
						joinSchool=item.getString("join_time");
						leaveSchool=item.getString("leave_time");
						schoolDescribe=item.getString("description");
						Worker work2=new Worker(school, eduLevel, joinSchool, leaveSchool, major,schoolDescribe,stu_exp_id);
						mList2.add(work2);
					}					
					// 数据填充到视图
					tv_hopeWork.setText(jobName);
					tv_hopeSalary.setText(salary);
					tv_workTime.setText(workTime);
					tv_highEdu.setText(education);
					tv_hopeCity.setText(workPlace);
					cet_phone.setText(phone);
					cet_email.setText(email);
					// 1.亮点
					//adapter = new MyAdapter();
					gv_workerMsg.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					//2.工作经历
					adapter1=new AddWorkExpAdapter(Resume.this, mList1, R.layout.add_work_exp, 
							new String[] { "itemsIcon" }, new int[] { R.id.tv_position ,R.id.tv_companyName,
							R.id.tv_joinWork,R.id.tv_leaveWork,R.id.tv_workDescribe,R.id.tv_edit,R.id.tv_delete});
					mListView1.setAdapter(adapter1);
					setListViewHeight(mListView1);
					adapter1.notifyDataSetChanged();					
					//3.教育经历
					adapter2 = new AddEduExpAdapter(Resume.this, mList2,R.layout.add_edu_exp, 
							new String[] { "itemsIcon" },new int[] { R.id.tv_school ,R.id.tv_eduLevel,
							R.id.tv_joinSchool,R.id.tv_leaveSchool,R.id.tv_major,R.id.tv_schoolDescribe,R.id.tv_edit,R.id.tv_delete});	
					mListView2.setAdapter(adapter2);
					setListViewHeight(mListView2);
					adapter2.notifyDataSetChanged();													        
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}			
			
		};
		Map<String, String> params = new HashMap<String, String>();
		params.put("resume_id", resumeId);
		RequestType requestType = new RequestType("ZP", Type.getMyResume);		
		handler.SetRequest(requestType, params);		
	}
	private void init() {
		tv_addWorkExp=(TextView) findViewById(R.id.tv_addWorkExp);
		tv_addEduExp=(TextView) findViewById(R.id.tv_addEduExp);
		tv_ok=(TextView) findViewById(R.id.tv_ok);
		tv_hopeWork=(TextView) findViewById(R.id.tv_hopeWork);
		tv_hopeSalary=(TextView) findViewById(R.id.tv_hopeSalary);
		tv_hopeCity=(TextView) findViewById(R.id.tv_hopeCity);
		tv_workTime=(TextView) findViewById(R.id.tv_workTime);
		tv_highEdu=(TextView) findViewById(R.id.tv_highEdu);
		mListView1=(CustomListview) findViewById(R.id.mListView1);
		mListView2=(CustomListview) findViewById(R.id.mListView2);
		
		ll_hopeWork=(LinearLayout) findViewById(R.id.ll_hopeWork);
		ll_hopeSalary=(LinearLayout) findViewById(R.id.ll_hopeSalary);
		ll_hopeCity=(LinearLayout) findViewById(R.id.ll_hopeCity);
		ll_workTime=(LinearLayout) findViewById(R.id.ll_workTime);
		ll_highEdu=(LinearLayout) findViewById(R.id.ll_highEdu);
		cet_phone=(CustomEditText) findViewById(R.id.cet_phone);
		cet_email=(CustomEditText) findViewById(R.id.cet_email);
		gv_workerMsg=(GridView) findViewById(R.id.gv_workerMsg);
				
		tv_addWorkExp.setOnClickListener(this);
		tv_addEduExp.setOnClickListener(this);
		tv_ok.setOnClickListener(this);
		ll_hopeWork.setOnClickListener(this);
		ll_hopeSalary.setOnClickListener(this);
		ll_hopeCity.setOnClickListener(this);
		ll_workTime.setOnClickListener(this);
		ll_highEdu.setOnClickListener(this);
	}
	/**  
     * 重新计算ListView的高度，解决ScrollView和ListView两个View都有滚动的效果，
     * 在嵌套使用时起冲突的问题  
     * @param listView  
     */  
    public void setListViewHeight(ListView listView) {    
            
        // 获取ListView对应的Adapter    
        
        ListAdapter listAdapter = listView.getAdapter();    
        
        if (listAdapter == null) {    
            return;    
        }    
        int totalHeight = 0;    
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目    
            View listItem = listAdapter.getView(i, null, listView);    
            listItem.measure(0, 0); // 计算子项View 的宽高    
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度    
        }    
        
        ViewGroup.LayoutParams params = listView.getLayoutParams();    
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));    
        listView.setLayoutParams(params);    
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_addWorkExp:
			Intent intent1=new Intent(Resume.this,AddWorkExp.class);
			intent1.putExtra("resumeId", resumeId);
			intent1.putExtra("from", "a");
			startActivityForResult(intent1, 6);
			break;
		case R.id.tv_addEduExp:
			Intent intent2=new Intent(Resume.this,AddEduExp.class);
			intent2.putExtra("resumeId", resumeId);
			intent2.putExtra("from", "c");
			//startActivity(intent2);
			startActivityForResult(intent2, 7);
			break;	
		case R.id.tv_ok:
			//上传亮点
			StringBuffer sb = new StringBuffer();
			sb.append("[");
		    for(int i=0;i<workerMsg.length-1;i++){
		    	EditText editText = (android.widget.EditText) gv_workerMsg.getChildAt(i);			    	
		    	if(editText.getText()!=null&&!"".equals(editText.getText().toString())){
		    		String text = editText.getText().toString();
		    		sb.append("\"");
			    	sb.append(text);
			    	sb.append("\"");			    	
		    		sb.append(",");			    	
		    	}
		    }
		    String str=sb.substring(0,sb.length()-1);
		    str=str+"]";
		    //提交简历的条件
			if(tv_hopeWork.getText().toString().length()>0&&
					tv_hopeSalary.getText().toString().length()>0&&
					tv_hopeCity.getText().toString().length()>0&&
					tv_workTime.getText().toString().length()>0&&
					tv_highEdu.getText().toString().length()>0&&
					cet_phone.getText().toString().length()>0&&
					cet_email.getText().toString().length()>0&&
					mList1.size()>0&&mList2.size()>0&&sb.toString().length()>2){
				handler2=new webhandler(){
					@Override
					public void OnResponse(JSONObject response) {
						// TODO Auto-generated method stub
						super.OnResponse(response);
						try {
							String msg=response.getString("message");
							Toast.makeText(Resume.this, msg, 1000).show();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					@Override
					public void OnError(int code, String mess) {
						// TODO Auto-generated method stub
						super.OnError(code, mess);
						String msg=mess;
						Toast.makeText(Resume.this, msg, 1000).show();
					}
				};
				Map<String, String> params2= new HashMap<String, String>();
				params2.put("job_name", jobId);
				params2.put("salary", salaryId);
				params2.put("job_addr", cityId);
				params2.put("job_year", workTimeId);
				params2.put("education", highEduId);
				params2.put("contact_phone", cet_phone.getText().toString());
				params2.put("job_email", cet_email.getText().toString());				
				params2.put("points", str);
				params2.put("resume_id", resumeId);
				handler2.SetRequest(new RequestType("ZP",Type.saveResume),params2);
				finish();
			}else{
				Toast.makeText(this, "请填写完必填项", 1000).show();
			}			
			break;	
		case R.id.ll_hopeWork:
			Intent intent3=new Intent(Resume.this,SelectJobActivity.class);
			startActivityForResult(intent3, 1);
			break;	
		case R.id.ll_hopeSalary:
			Intent intent4=new Intent(Resume.this,SelectSalaryActivity.class);
			startActivityForResult(intent4, 2);
			break;	
		case R.id.ll_hopeCity:
			Intent intent5=new Intent(Resume.this,SelectPlaceActivity.class);
			startActivityForResult(intent5, 3);
			break;	
		case R.id.ll_workTime:
			Intent intent6=new Intent(Resume.this,SelectWorkTimeActivity.class);
			startActivityForResult(intent6, 4);			
			break;	
		case R.id.ll_highEdu:
			Intent intent7=new Intent(Resume.this,SelectHighEduActivity.class);
			startActivityForResult(intent7, 5);
			break;	
		default:
			break;
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		try{
			switch (requestCode) {
			case 1:
				jobId = data.getExtras().getString("jobId");
				String jobResult = data.getExtras().getString("jobResult");
				tv_hopeWork.setText(jobResult);
				break;
			case 2:
				salaryId= data.getExtras().getString("salaryId");
				String salaryResult = data.getExtras().getString("salaryResult");
				tv_hopeSalary.setText(salaryResult);
				break;	
			case 3:
				cityId= data.getExtras().getString("placeId");
				String cityResult = data.getExtras().getString("placeResult");
				tv_hopeCity.setText(cityResult);
				break;
			case 4:
				workTimeId= data.getExtras().getString("workTimeId");
				String workTimeResult = data.getExtras().getString("workTimeResult");
				tv_workTime.setText(workTimeResult);
				break;	
			case 5:
				highEduId= data.getExtras().getString("highEduId");
				String highEduResult = data.getExtras().getString("highEduResult");
				tv_highEdu.setText(highEduResult);
				break;	
			case 6:
				companyName=data.getExtras().getString("comp_name");
				position=data.getExtras().getString("job_name");
				joinWork=data.getExtras().getString("join_time");
				leaveWork=data.getExtras().getString("leave_time");
				workDescribe=data.getExtras().getString("description");
				Worker work1=new Worker(position,companyName,  joinWork, leaveWork, workDescribe,job_exp_id);
				mList1.add(work1);
				adapter1=new AddWorkExpAdapter(Resume.this, mList1, R.layout.add_work_exp, 
						new String[] { "itemsIcon" }, new int[] { R.id.tv_position ,R.id.tv_companyName,
						R.id.tv_joinWork,R.id.tv_leaveWork,R.id.tv_workDescribe,R.id.tv_edit,R.id.tv_delete});
				mListView1.setAdapter(adapter1);
				setListViewHeight(mListView1);
				adapter1.notifyDataSetChanged();	
				break;	
			case 7:							
				major=data.getExtras().getString("pro");
				school=data.getExtras().getString("school");
				eduLevel=data.getExtras().getString("education");
				joinSchool=data.getExtras().getString("join_time");
				leaveSchool=data.getExtras().getString("leave_time");
				schoolDescribe=data.getExtras().getString("description");				
				Worker work2=new Worker(school, eduLevel, joinSchool, leaveSchool, major,schoolDescribe,stu_exp_id);
				mList2.add(work2);												
				adapter2 = new AddEduExpAdapter(Resume.this, mList2,R.layout.add_edu_exp, 
						new String[] { "itemsIcon" },new int[] { R.id.tv_school ,R.id.tv_eduLevel,
						R.id.tv_joinSchool,R.id.tv_leaveSchool,R.id.tv_major,R.id.tv_schoolDescribe,R.id.tv_edit,R.id.tv_delete});	
				mListView2.setAdapter(adapter2);
				setListViewHeight(mListView2);
				adapter2.notifyDataSetChanged();
				break;	
			default:
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	//自定义adapter
	private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return workerMsg.length;
        }
        @Override
        public Object getItem(int position) {
            return workerMsg[position];
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView( final int position, View convertView,  final ViewGroup parent) {
            final viewHolder holder;
            if (convertView==null){
                holder = new viewHolder();
                convertView = mInflater.inflate(R.layout.list_item_addtext,parent,false);
                holder.etcontent = (EditText) convertView.findViewById(R.id.et_addtext);
                //输入框的文本监听事件		
        		TextWatcher tw=new TextWatcher() {
        			private CharSequence temp;
        			@Override
        			public void onTextChanged(CharSequence s, int start, int before, int count) {
        				temp = s;
        			}			
        			@Override
        			public void beforeTextChanged(CharSequence s, int start,int count,int after) {
        				temp = s;        				
        			}			
        			@Override
        			public void afterTextChanged(Editable s) {
        				if(temp.toString().length()==6){
        					Toast.makeText(Resume.this, "最多可输入6个字", 1000).show();
        				}
        			}
        		};
        		holder.etcontent.addTextChangedListener(tw);
                convertView.setTag(holder);
            }else {
                holder = (viewHolder) convertView.getTag();
            }
            if (position==workerMsg.length-1){
            	final View finalConvertView = convertView;
            	holder.etcontent = (EditText) adapter.getView(position-1,finalConvertView,parent);
	            holder.etcontent.clearFocus();
	            holder.etcontent.setFocusable(false);
	            holder.etcontent.setFocusableInTouchMode(false);
	            holder.etcontent.setHint("");
	            holder.etcontent.setBackgroundResource(R.drawable.add_edittext);
                holder.etcontent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    	workerMsg= new String[workerMsg.length+1];                            
                        holder.etcontent.setFocusable(true);
                        holder.etcontent.setFocusableInTouchMode(true);
                        holder.etcontent.setHint("请输入内容");
                        holder.etcontent.setBackgroundResource(R.drawable.tv_bg);
                    }
                });
            }           
            final int len=workerMsg.length;
            //for(int i=0;i<len;i++){
            	if(position<len){
            		//((EditText) adapter.getView(i,convertView,parent)).setText(workerMsg[i]);
                	holder.etcontent.setText(workerMsg[position]);           	
                //}
            }            
            return convertView;
        }
        class viewHolder{
            EditText etcontent;
        }
    }
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("Resume");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("Resume");
		MobclickAgent.onPause(this);
	}
}
