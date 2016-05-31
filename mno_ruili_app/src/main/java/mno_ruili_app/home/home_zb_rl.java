package mno_ruili_app.home;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.myapplication;
import mno.ruili_app.ct.CalendarView;
import mno.ruili_app.ct.MessageBox;
import mno_ruili_app.adapter.CalendarAdapter;
import mno_ruili_app.adapter.Imagezbrl2ListAdapter;
import mno_ruili_app.adapter.ImagezbrlListAdapter;
import mno_ruili_app.adapter.tv;
import mno_ruili_app.adapter.zb_tv;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
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

public class home_zb_rl extends Activity implements OnGestureListener{

	private GestureDetector gestureDetector = null;
	private CalendarAdapter calV = null;
	private GridView gridView = null;
	private TextView topText = null;
	private static int jumpMonth = 0;      //每次滑动，增加或减去一个月,默认为0（即显示当前月）
	private static int jumpYear = 0;       //滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;
	private String currentDate = "";
	int[]mark;
	String datatime="",isEnd="";
	String isCollect="";
	ListView mListView;
	ArrayList<zb_tv>   mTvList = new ArrayList<zb_tv>();
	Imagezbrl2ListAdapter mylistViewAdapter;
	private TextView monthText = null;
	webhandler handler_2,handler_,handler_3;
	String id="";		 
	public home_zb_rl() {	
		Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
    	currentDate = sdf.format(date);  //当期日期
    	year_c = Integer.parseInt(currentDate.split("-")[0]);
    	month_c = Integer.parseInt(currentDate.split("-")[1]);
    	day_c = Integer.parseInt(currentDate.split("-")[2]);	    		    	
	}
	 myapplication application;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.home_zb_rl);
			application = (myapplication) this.getApplication();			
		    init();
		    handler_ = new webhandler(){
	    		@Override
	  			public void OnError(int code, String mess) {
	  				// TODO Auto-generated method stub
	  				super.OnError(code, mess);
	  				String ms=mess;
	  				ms=ms+"";
	  				MessageBox.Show(home_zb_rl.this,mess);
	  			} 	
	  			@Override
				public void OnResponse(JSONObject response) {
					try {  					
						String mess = response.getString("message"); 					
						if(mess.equals("null") == false && mess.length() > 1)
						{
							Toast.makeText(home_zb_rl.this, "已预约成功", Toast.LENGTH_SHORT).show();
						}  					  					
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			   }    			
    	 };
		 handler_2 = new webhandler(){
 			@Override
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
				String ms=mess;
				ms=ms+"";
				MessageBox.Show(home_zb_rl.this,mess);
			}	
			@Override
 			public void OnResponse(JSONObject response) {
 				// TODO Auto-generated method stub
 				// TODO Auto-generated method stub
				try {
					mTvList.clear();
					id="";
					JSONArray maindata = (JSONArray) response.getJSONArray("data");
					int length=maindata.length();
					if(length==0)
					{
						String title="今日无直播";
						final zb_tv mtv = new zb_tv("", title,"","", "","","");
						mTvList.add(mtv);
						mylistViewAdapter.notifyDataSetChanged();
						return;
					}
					 for(int i=0;i<= length-1; i++){
				    	JSONObject type_json;
						type_json = maindata.getJSONObject(i);
						String begin="",end="";
						String title=type_json.getString("title");
						try {
							datatime=type_json.getString("beginTime");
						    begin=type_json.getString("beginTime").substring(11, 16);
						} catch (Exception e) {
							 begin="00:00";
						}
						String isMem=type_json.getString("isMembers");
						isEnd=type_json.getString("isEnd");
						String zj=type_json.getString("teacher");
						id=type_json.getString("id");
						isCollect=type_json.getString("isCollect");;
						
						final zb_tv mtv = new zb_tv(id, title,zj,datatime, isCollect,isEnd,isMem);
						mTvList.add(mtv);
						mylistViewAdapter.notifyDataSetChanged();		
				    }				
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					String title="今日无直播";
					final zb_tv mtv = new zb_tv("", title,"","", "","","");
					mTvList.add(mtv);
					mylistViewAdapter.notifyDataSetChanged();
				}
 			}
 			
 			};
 			handler_3 = new webhandler(){
 				@Override
 				public void OnError(int code, String mess) {
 					// TODO Auto-generated method stub
 					super.OnError(code, mess);
 					String ms=mess;
 					ms=ms+"";
 					MessageBox.Show(home_zb_rl.this,mess);
 				}
				@Override
				public void OnResponse(JSONObject response) {					
					Toast.makeText(home_zb_rl.this, "已取消预约", Toast.LENGTH_SHORT).show();				
				}
			};	           			
 			handler_2.SetProgressDialog(home_zb_rl.this);
 			Map<String, String> params = new HashMap<String, String>();			
 	        int mYear = year_c; //获取当前年份
 	        int mMonth = month_c;//获取当前月份
 	        int mDay = day_c;//获取当前月份的日期号码
 	        if(mMonth<10)
 	        {
 	        	if(mDay<10)
 	        	{
 	        		  params.put("date",mYear+"-0"+mMonth+"-0"+mDay+"");
 	        	}
 	        	else
 	        	{
 	        		 params.put("date",mYear+"-0"+mMonth+"-"+mDay+"");
 	        	}
 	        }	        	
 	        else{
 	       	if(mDay<10)
	        	{
	        		  params.put("date",mYear+"-"+mMonth+"-0"+mDay+"");
	        	}
	        	else
	        	{
	        		 params.put("date",mYear+"-"+mMonth+"-"+mDay+"");
	        	}	         
 	        }
 	       handler_2.SetRequest(new RequestType("2",Type.getCourseByDate),params); 	        	       
		}
		private void init() {
			// TODO Auto-generated method stub
			mark= new int[1] ;  
			mark[0]=-1;	    	
			gestureDetector = new GestureDetector(this);			
	        calV = new CalendarAdapter(this,getResources(),jumpMonth,jumpYear,year_c,month_c,day_c,mark);
	        addGridView();
	        gridView.setAdapter(calV);	        
			topText = (TextView) findViewById(R.id.tv_month);
			addTextToTopTextView(topText);			 
			mListView = (ListView)this.findViewById(R.id.mListView);
			mylistViewAdapter = new Imagezbrl2ListAdapter(this, mTvList);	      
	        mListView.setAdapter(mylistViewAdapter);
	        mListView.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                    int position, long id) {
	            	     String ti=mTvList.get(position).gettime().trim();
	            	     if(ti.length()<=0)
	            	    	 return;
	            		 int time=getGapCount(ti);
						 if(time==0)
						 {
							 if(get(ti)>0)
							 {
								 if(mTvList.get(position).getisEnd().equals("1"))
								 {
									 MessageBox.Show(getApplicationContext(), "直播已结束");
									 return;
								 }
								    Intent intent = new Intent(home_zb_rl.this,home_video.class);
									
									Constant.itemid=mTvList.get(position).getid();
									home_zb_rl.this.startActivity(intent);
							 }
							 else{
								 MessageBox.Show(getApplicationContext(), "直播开始时间为\n"+mTvList.get(position).gettime());
							 }
								
						 
	            	}
	            }
	          
	            	//MessageBox.Show(getApplicationContext(), "直播还没开始\n点击右侧收藏");
	            
	        });

			
			
		}
		//添加头部的年份 闰哪月等信息
		public void addTextToTopTextView(TextView view){
			StringBuffer textDate = new StringBuffer();
			textDate.append(calV.getShowYear()).append("年").append(
					calV.getShowMonth()).append("月").append("\t");
			view.setText(textDate);
			view.setTextColor((android.graphics.Color.parseColor("#4b4b4b")));
			view.setTypeface(Typeface.DEFAULT_BOLD);
		}
		
		//添加gridview
		private void addGridView() {
			
			gridView =(GridView)findViewById(R.id.gridview);

			gridView.setOnTouchListener(new OnTouchListener() {
	            //将gridview中的触摸事件回传给gestureDetector
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					return home_zb_rl.this.gestureDetector.onTouchEvent(event);
				}
			});           			
			gridView.setOnItemClickListener(new OnItemClickListener() {
	            //gridView中的每一个item的点击事件				
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
					  //点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
					  int startPosition = calV.getStartPositon();
					  int endPosition = calV.getEndPosition();
					  if(startPosition <= position+7  && position <= endPosition-7){
						  String scheduleDay = calV.getDateByClickItem(position).split("\\.")[0];  //这一天的阳历
						  //String scheduleLunarDay = calV.getDateByClickItem(position).split("\\.")[1];  //这一天的阴历
		                  String scheduleYear = calV.getShowYear();
		                  String scheduleMonth = calV.getShowMonth();
		                  TextView textView = (TextView) arg1.findViewById(R.id.tvtext);
		          		  TextView textView2 = (TextView) arg1.findViewById(R.id.tvtext2);
		                  ImageView imageView1= (ImageView) arg1.findViewById(R.id.imageView1);
		                  imageView1.setVisibility(View.VISIBLE);
		                  textView.setTextColor(Color.WHITE);
		      			  textView2.setTextColor(Color.WHITE);
		      			  Map<String, String> params = new HashMap<String, String>();
		      			  if(Integer.valueOf(scheduleMonth)<10){
		       	        	if(Integer.valueOf(scheduleDay)<10){
		       	        		  params.put("date",scheduleYear+"-0"+scheduleMonth+"-0"+scheduleDay+"");
		       	        	}else{
		       	        		 params.put("date",scheduleYear+"-0"+scheduleMonth+"-"+scheduleDay+"");
		       	            }
		       	          }else{
		       	        	 if(Integer.valueOf(scheduleDay)<10){
		       	        		  params.put("date",scheduleYear+"-"+scheduleMonth+"-0"+scheduleDay+"");
		       	        	 }else{
		       	        		 params.put("date",scheduleYear+"-"+scheduleMonth+"-"+scheduleDay+"");
		       	        	 }
		       	         //params.put("date",scheduleYear+scheduleMonth+scheduleDay+"");
		       	        }		      			 		      			 		    		  
		    			handler_2.SetRequest(new RequestType("2",Type.getCourseByDate),params);		      			  
		                //Toast.makeText(home_zb_rl.this, scheduleYear+"-"+scheduleMonth+"-"+scheduleDay, 2000).show();
		                mark[0]=position;		                 
		                calV.notifyDataSetChanged();		                 
		            }
				}				
			});
		}		
		public void onclick(View v) {						
			  if(v.getId()==R.id.zb_rl){
				 Intent intent = new Intent(home_zb_rl.this,home_zb_rl_.class);
				 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				 intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
				 home_zb_rl.this.startActivity(intent);
			 }
			 else if(v.getId()==R.id.tv_month)
			 {
				    jumpMonth = 0;
		        	jumpYear = 0;
		        	addGridView();   //添加一个gridView
		        	year_c = Integer.parseInt(currentDate.split("-")[0]);
		        	month_c = Integer.parseInt(currentDate.split("-")[1]);
		        	day_c = Integer.parseInt(currentDate.split("-")[2]);
		        	calV = new CalendarAdapter(this,getResources(),jumpMonth,jumpYear,year_c,month_c,day_c,mark);
			        gridView.setAdapter(calV);
			        addTextToTopTextView(topText);

			 }
			 
		}				
		@Override
		public boolean onDown(MotionEvent arg0) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			int gvFlag = 0;         //每次添加gridview到viewflipper中时给的标记
			if (e1.getX() - e2.getX() > 250) {
	            //像左滑动
				addGridView();   //添加一个gridView
				jumpMonth++;     //下一个月
				mark[0]=-1;
	                 
	                 
				calV = new CalendarAdapter(this,getResources(),jumpMonth,jumpYear,year_c,month_c,day_c,mark);
		        gridView.setAdapter(calV);
		        addTextToTopTextView(topText);
		        gvFlag++;
		
				return true;
			} else if (e1.getX() - e2.getX() < -250) {
	            //向右滑动
				addGridView();   //添加一个gridView
				jumpMonth--;     //上一个月
				mark[0]=-1;
				calV = new CalendarAdapter(this,getResources(),jumpMonth,jumpYear,year_c,month_c,day_c,mark);
		        gridView.setAdapter(calV);
		        gvFlag++;
		        addTextToTopTextView(topText);

				return true;
			}
			return false;
		}
		@Override
		public void onLongPress(MotionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
				float arg3) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onShowPress(MotionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onSingleTapUp(MotionEvent arg0) {
			// TODO Auto-generated method stub
			return false;
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

		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			MobclickAgent.onPageStart("home_zb_rl");
			MobclickAgent.onResume(this);
		    jumpMonth = 0;
        	jumpYear = 0;
        	addGridView();   //添加一个gridView
        	year_c = Integer.parseInt(currentDate.split("-")[0]);
        	month_c = Integer.parseInt(currentDate.split("-")[1]);
        	day_c = Integer.parseInt(currentDate.split("-")[2]);
        	calV = new CalendarAdapter(this,getResources(),jumpMonth,jumpYear,year_c,month_c,day_c,mark);
	        gridView.setAdapter(calV);
	        addTextToTopTextView(topText);
		}
		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			MobclickAgent.onPageEnd("home_zb_rl");
			MobclickAgent.onPause(this);
		}
}
