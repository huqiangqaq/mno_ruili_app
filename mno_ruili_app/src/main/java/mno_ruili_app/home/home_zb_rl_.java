package mno_ruili_app.home;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.myapplication;
import mno.ruili_app.ct.CalendarView;
import mno.ruili_app.ct.LunarCalendar;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.ct.SpecialCalendar;
import mno_ruili_app.adapter.ImageListAdapter;
import mno_ruili_app.adapter.ImagezbrlListAdapter;
import mno_ruili_app.adapter.tv;
import mno_ruili_app.adapter.zb_tv;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.titlebar.AbTitleBar;
import com.umeng.analytics.MobclickAgent;

public class home_zb_rl_ extends Activity{
	     private SpecialCalendar sc = null;
	     private boolean isLeapyear = false;  //是否为闰年
	     
		 webhandler handler_2;
		 String id="",isEnd="";
		 ListView mListView;
		 ArrayList<zb_tv>   mTvList = new ArrayList<zb_tv>();
		 ImagezbrlListAdapter mylistViewAdapter;
		 int lastlength=0,timelength=0;
		 myapplication application;
		 @Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.home_zb_rl2);
		    init();
		    application = (myapplication) this.getApplication();
		    handler_2 = new webhandler(){
 			@Override
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
				String ms=mess;
				ms=ms+"";
				MessageBox.Show(home_zb_rl_.this,mess);
			}	
			@Override
 			public void OnResponse(JSONObject response) {
 				// TODO Auto-generated method stub
				try {					
					mTvList.clear();										
					JSONArray lastdata = response.getJSONObject("data").getJSONArray("last");					
					JSONArray noedata = response.getJSONObject("data").getJSONArray("now");										
					{
				    lastlength=lastdata.length();
				    for(int i=0;i< lastlength; i++)
				    {
				    JSONObject type_json =   lastdata.getJSONObject(i);			   
				    Iterator<?> it = type_json.keys();  
		            String a = "";  		           
		            while(it.hasNext()){//遍历JSONObject  
		                a = (String) it.next().toString();  
		                try {
			                JSONArray maindata = (JSONArray) type_json.getJSONArray(a);
							 //当期日期
							String year = a.split("-")[0];
							String month = year+"年"+a.split("-")[1]+"月";
							String day = a.split("-")[2];
							String week="星期"+getWeek(a);
							zb_tv mtv = new zb_tv(maindata,day, week, month ,"1");
							if(i==0)
							{
								 mtv = new zb_tv(maindata,day, week, month ,"0");
							}
					    	mTvList.add(mtv);
					    	String forwhat=application.getforwhat();
					    	if(forwhat.trim().contains(a.trim()))
		                	timelength=mTvList.size();
					    	mylistViewAdapter.notifyDataSetChanged();						
		                } catch (Exception e) {
							// TODO Auto-generated catch block
		                	String year = a.split("-")[0];
							String month = year+"年"+a.split("-")[1]+"月";
							String day = a.split("-")[2];
							String week="星期"+getWeek(a);
							JSONArray array=new JSONArray("");
		                	final zb_tv mtv = new zb_tv(  array, day, week, month,"1");
					    	mTvList.add(mtv);					    	
					    	mylistViewAdapter.notifyDataSetChanged();
						}
		            }  				    
				    }		
				    }
					/*11*/					
					{
						int length=noedata.length();
					    for(int i=0;i< length; i++)
					    {
					    JSONObject type_json =   noedata.getJSONObject(i);				   
					    Iterator<?> it = type_json.keys();  
			            String a = "";  			            
			            while(it.hasNext()){//遍历JSONObject  
			                a = (String) it.next().toString();  
			                try {			                	
			                	 	JSONArray maindata = (JSONArray) type_json.getJSONArray(a);				
									String year = a.split("-")[0];
									String month = year+"年"+a.split("-")[1]+"月";
									String day = a.split("-")[2];
									String week="星期"+getWeek(a);
									zb_tv mtv = new zb_tv(maindata,day, week, month ,"1");
									if(i==0)
									{
										 mtv = new zb_tv(maindata,day, week, month ,"0");
									}
							    	mTvList.add(mtv);
							    	if(application.getforwhat().trim().contains(a.trim()))
				                		 timelength=mTvList.size();
							    	mylistViewAdapter.notifyDataSetChanged();							
			                } catch (Exception e) {
								// TODO Auto-generated catch block
			                	String year = a.split("-")[0];
								String month = year+"年"+a.split("-")[1]+"月";
								String day = a.split("-")[2];
								String week="星期"+getWeek(a);
								JSONArray array=new JSONArray("");
								final zb_tv mtv = new zb_tv(  array, day, week, month,"1");
						    	mTvList.add(mtv);
						    	mylistViewAdapter.notifyDataSetChanged();
							}
			            }  					    
					    }			
					    }					
					/*222*/					
					{
						JSONArray nextdata = response.getJSONObject("data").getJSONArray("next");						
						int length=nextdata.length();
					    for(int i=0;i< length; i++)
					    {
					    JSONObject type_json =   nextdata.getJSONObject(i);				   
					    Iterator<?> it = type_json.keys();  
			            String a = "";  			           
			            while(it.hasNext()){//遍历JSONObject  
			                a = (String) it.next().toString();  
			                try {
			                	 JSONArray maindata = (JSONArray) type_json.getJSONArray(a);			                	 
									String year = a.split("-")[0];
									String month = year+"年"+a.split("-")[1]+"月";
									String day = a.split("-")[2];
									String week="星期"+getWeek(a);
									final zb_tv mtv = new zb_tv(maindata,day, week, month ,"1");
							    	mTvList.add(mtv);
							    	if(application.getforwhat().trim().contains(a.trim()))
				                		 timelength=mTvList.size();
							    	mylistViewAdapter.notifyDataSetChanged();							
			                } catch (Exception e) {
								// TODO Auto-generated catch block
			                	String year = a.split("-")[0];
								String month = year+"年"+a.split("-")[1]+"月";
								String day = a.split("-")[2];
								String week="星期"+getWeek(a);
								JSONArray array=new JSONArray("");
								final zb_tv mtv = new zb_tv(  array, day, week, month,"1");
						    	mTvList.add(mtv);
						    	mylistViewAdapter.notifyDataSetChanged();
							}							
			            }  					    
					    }			
					    }
					if(timelength>0)
					{
						mListView.setSelection(timelength-1);
						application.setforwhat("");
					}else
					mListView.setSelection(lastlength);
					//MessageBox.Show(getApplicationContext(), maindata.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					if(timelength>0)
					{
						mListView.setSelection(timelength-1);
						application.setforwhat("");
					}else
					mListView.setSelection(lastlength);
				}
 			} 			
 			}; 			
 			 handler_2.SetProgressDialog(home_zb_rl_.this); 			
		}
		 @Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			MobclickAgent.onPageStart("home_zb_rl_");
			MobclickAgent.onResume(this);
		}
		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			MobclickAgent.onPageEnd("home_zb_rl_");
			MobclickAgent.onPause(this);
		}
		private void init() {
			// TODO Auto-generated method stub
		//	LinearLayout mLinearLayout = (LinearLayout)this.findViewById(R.id.layout01);
			Date date = new Date();
			sysDate = sdf.format(date);  //当期日期
			sys_year = sysDate.split("-")[0];
			sys_month = sysDate.split("-")[1];
			sys_day = sysDate.split("-")[2];
			sc = new SpecialCalendar();						
			mListView = (ListView)this.findViewById(R.id.mListView);
			mylistViewAdapter = new ImagezbrlListAdapter(this, mTvList);	      
	        mListView.setAdapter(mylistViewAdapter);
	        mListView.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                    int position, long id) {
	            	/*if(mTvList.get(position).getName().length()>0)
	            	{
	            		
	            		 int time=getGapCount(mTvList.get(position).getdata().trim());
						 if(time==0)
						 {
							 if(get(mTvList.get(position).getdata().trim())>0)
							 {
								 if(mTvList.get(position).getisEnd().equals("1"))
								 {
									 MessageBox.Show(getApplicationContext(), "直播已结束");
									 return;
								 }
								    Intent intent = new Intent(home_zb_rl_.this,home_video.class);
									
									Constant.itemid=mTvList.get(position).getid();
									home_zb_rl_.this.startActivity(intent);
							 }
							 else{
								 MessageBox.Show(getApplicationContext(), "直播开始时间为\n"+mTvList.get(position).getdata());
							 }
								
						 }
	            	}*/
	            }
	            	//MessageBox.Show(getApplicationContext(), "直播还没开始\n点击右侧收藏");	            
	        });	        	        
		}
		private LunarCalendar lc = null; 
		private String[] dayNumber = new String[42];
		private int daysOfMonth = 0;      //某月的天数
		private int dayOfWeek = 0;        //具体某一天是星期几
		private int lastDaysOfMonth = 0;  //上一个月的总天数
		//得到某年的某月的天数且这月的第一天是星期几
		private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		//系统当前时间
		private String sysDate = "";  
		private String sys_year = "";
		private String sys_month = "";
		private String sys_day = "";				
		public void getCalendar(int year, int month){
			isLeapyear = sc.isLeapYear(year);              //是否为闰年
			daysOfMonth = sc.getDaysOfMonth(isLeapyear, month);  //某月的总天数
			dayOfWeek = sc.getWeekdayOfMonth(year, month);      //某月第一天为星期几
			lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month-1);  //上一个月的总天数
			//Log.d("DAY", isLeapyear+" ======  "+daysOfMonth+"  ============  "+dayOfWeek+"  =========   "+lastDaysOfMonth);
			getweek(year,month);
		}
		
		//将一个月中的每一天的值添加入数组dayNuber中
		private void getweek(int year, int month) {
			int j = 1;
			int flag = 0;
			String lunarDay = "";
			
			//得到当前月的所有日程日期(这些日期需要标记)

			for (int i = 0; i < dayNumber.length; i++) {
				// 周一
//				if(i<7){
//					dayNumber[i]=week[i]+"."+" ";
//				}
				 if(i < dayOfWeek){  //前一个月
					int temp = lastDaysOfMonth - dayOfWeek+1;
					lunarDay = lc.getLunarDate(year, month-1, temp+i,false);
					dayNumber[i] = (temp + i)+"."+lunarDay;
					
				}else if(i < daysOfMonth + dayOfWeek){   //本月
					String day = String.valueOf(i-dayOfWeek+1);   //得到的日期
					lunarDay = lc.getLunarDate(year, month, i-dayOfWeek+1,false);
					dayNumber[i] = i-dayOfWeek+1+"."+lunarDay;
					//对于当前月才去标记当前日期
					if(sys_year.equals(String.valueOf(year)) && sys_month.equals(String.valueOf(month)) && sys_day.equals(day)){
						//标记当前日期
						//currentFlag = i;
					}	
				/*	setShowYear(String.valueOf(year));
					setShowMonth(String.valueOf(month));
					setAnimalsYear(lc.animalsYear(year));
					setLeapMonth(lc.leapMonth == 0?"":String.valueOf(lc.leapMonth));
					setCyclical(lc.cyclical(year));*/
				}else{   //下一个月
					lunarDay = lc.getLunarDate(year, month+1, j,false);
					dayNumber[i] = j+"."+lunarDay;
					j++;
				}
			}
	        
	        String abc = "";
	        for(int i = 0; i < dayNumber.length; i++){
	        	 abc = abc+dayNumber[i]+":";
	        }
	        Log.d("DAYNUMBER",abc);
		}		
		private String getWeek(String pTime) {			  
			  String Week = "";
			  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			  Calendar c = Calendar.getInstance();
			  try {
			   c.setTime(format.parse(pTime));

			  } catch (ParseException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
			  }
			  if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			   Week += "天";
			  }
			  if (c.get(Calendar.DAY_OF_WEEK) == 2) {
			   Week += "一";
			  }
			  if (c.get(Calendar.DAY_OF_WEEK) == 3) {
			   Week += "二";
			  }
			  if (c.get(Calendar.DAY_OF_WEEK) == 4) {
			   Week += "三";
			  }
			  if (c.get(Calendar.DAY_OF_WEEK) == 5) {
			   Week += "四";
			  }
			  if (c.get(Calendar.DAY_OF_WEEK) == 6) {
			   Week += "五";
			  }
			  if (c.get(Calendar.DAY_OF_WEEK) == 7) {
			   Week += "六";
			  }			 
			  return Week;
			 }
		public void onclick(View v) {			
			 if(v.getId()==R.id.zbrl_tz)
	    	{				
	    	}
			 else if(v.getId()==R.id.zb_rl)
			 {
				    Intent intent = new Intent(home_zb_rl_.this,home_zb_rl.class);
				    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
					home_zb_rl_.this.startActivity(intent);
			 }
		}
		@Override
		protected void onRestart() {
			// TODO Auto-generated method stub
			super.onRestart();
			 Map<String, String> params = new HashMap<String, String>();	 						  
			 handler_2.SetRequest(new RequestType("2",Type.getLiveCourse),params);
		}

		@Override
		protected void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			 Map<String, String> params = new HashMap<String, String>();	 						  
			 handler_2.SetRequest(new RequestType("2",Type.getLiveCourse),params);
		}
		 private Long get(String time) {
				// TODO Auto-generated method stub
				//定义时间格式
				 
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				 
				//取的两个时间
				Date dt=new Date();
				 
				//透过SimpleDateFormat的format方法将Date转为字符串
				 
				String dts=sdf.format(dt);
				Date dt1 = null;
				Date dt2 = null;
				try {
					dt1 = sdf.parse(time);
					dt2 =sdf.parse(dts);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//取得两个时间的Unix时间
				 
				Long ut1=dt1.getTime();
				Long ut2=dt2.getTime();
				Long timeP=ut2-ut1;
			
				return timeP;
	
		   }

		public static int getGapCount( String endDate) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date dt=new Date();
			Date dt1 = null;
			Date dt2 = null;
			String dts=sdf.format(dt);
	        Calendar fromCalendar = Calendar.getInstance();  
	        
	        Calendar toCalendar = Calendar.getInstance();  
	        //dt1 = sdf.parse(endDate);
	        try {
				fromCalendar.setTime(sdf.parse(dts));
				
				    fromCalendar.set(Calendar.HOUR_OF_DAY, 0);  
			        fromCalendar.set(Calendar.MINUTE, 0);  
			        fromCalendar.set(Calendar.SECOND, 0);  
			        fromCalendar.set(Calendar.MILLISECOND, 0);  
			  
			        toCalendar.setTime( sdf.parse(endDate));  
			        toCalendar.set(Calendar.HOUR_OF_DAY, 0);  
			        toCalendar.set(Calendar.MINUTE, 0);  
			        toCalendar.set(Calendar.SECOND, 0);  
			        toCalendar.set(Calendar.MILLISECOND, 0);  
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	     
	  
	        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
	}

}
