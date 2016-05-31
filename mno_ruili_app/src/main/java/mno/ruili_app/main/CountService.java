package mno.ruili_app.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mno.down.modal.DownloadDetail;
import mno.down.modal.DownloadRecord;
import mno.down.util.DownloadHelper;
import mno.down.util.DownloadManager;
import mno.down.util.Mission;
import mno.down.util.NetworkUtils;
import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.my.mycheckcode;
import mno.ruili_app.my.mylogin;
import mno_ruili_app.home.home_video;
import mno_ruili_app.nei.nei_zxxq;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;

import org.json.JSONException;
import org.json.JSONObject;





import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CountService extends Service {
	/** 创建参数 */
    boolean threadDisable;
    int count;
    webhandler handler_,handler_2;
    List<DownloadRecord> goingList;
    public IBinder onBind(Intent intent) {
        return null;
    }
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			 String name = intent.getExtras().getString("name");  
			 String name2 = intent.getExtras().getString("name2");  
			 String id = intent.getExtras().getString("id");  
			 String time=name2;
			 if(appuser.getInstance().IsLogin())
	    	 {
				 
			 Map<String, String> params = new HashMap<String, String>();
			 params.put("viewTime",time);
			 params.put("viewAt",name);
			 params.put("cid",id);
			 handler_.SetRequest(new RequestType("2",Type.CourseRecord),params);
	    	 }
		}

	};
    public void onCreate() {
        super.onCreate();
        downloadHelper = new DownloadHelper(this);
        IntentFilter filter = new IntentFilter();
		filter.addAction("mno.cd");
		filter.setPriority(Integer.MAX_VALUE);
		registerReceiver(myReceiver, filter);
        /** 创建一个线程，每秒计数器加一，并在控制台进行Log输出 */
        new Thread(new Runnable() {
            public void run() {
                while (!threadDisable) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {

                    }
                    count++;
                  Message message = new Message( );
              	  message.what = 1;
              	  handler.sendMessage(message);
              	// goingList.clear();
              	/* if(appuser.getInstance().IsLogin())
    	    	 {
              	 goingList=DownloadRecord.getAllFailures();
              	 
              	if(goingList.size()>0)
              	{
              		  Message message2 = new Message( );
                	  message2.what = 2;
                	  //handler.sendMessage(message2);
                }
                }*/
                }
            }
        }).start();
        handler_2= new webhandler(){

  			@Override
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
				String ms=mess;
				ms=ms+"";
				//MessageBox.Show(CountService.this,mess);
			}

	

			@Override
  			public void OnResponse(JSONObject response) {
  				// TODO Auto-generated method stub
  				// TODO Auto-generated method stub
				try {
					String data=response.getString("data");
					/*if(Integer.parseInt(data)>0)
					{*/
						Intent intent = new Intent();  
	                    intent.setAction("mno.xx");  
	                    intent.putExtra("name", data);  
	                    CountService.this.sendBroadcast(intent);  
	                  
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
  			}
  			
  			};
        handler_ = new webhandler(){

  			@Override
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
				String ms=mess;
				ms=ms+"";
				firstts=0; 
				//MessageBox.Show(CountService.this,ms);
			}

	

			@Override
  			public void OnResponse(JSONObject response) {
  				// TODO Auto-generated method stub
  				// TODO Auto-generated method stub
				try {
					
					String mess = response.getString("message");
					
					if(mess.equals("null") == false && mess.length() > 1)
					{
						Toast.makeText(CountService.this, mess, Toast.LENGTH_SHORT).show();
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
  			}
  			
  			};
    }
    DownloadHelper downloadHelper;
    DownloadDetail detail = null;
    final Handler handler = new Handler( ) {
  	  public void handleMessage(Message msg) {
  	  switch (msg.what) {
  	  case 1:
  		 
  	  update( );
  	  break;
  	  case 2:
  		 a=NetworkUtils.getConnectivityStatus(CountService.this);
  		 if(a==2)
  		 {
  			if(firstts==0)
  			{
  			if(DownloadManager.getInstance().containMission(0))
  		     showDialog();
  		     
  			 firstts=1;
  			}
  			
  		 }
  		 else if(a==1)
  		 {
  			if(firstts==0)
  			{
  			 down();
  			 firstts=1;
  			}
  		 }
  		 else
  			firstts=0; 
  		break;
  	  }
  	  super.handleMessage(msg);
  	  }

	
    };
    private void update() {
		// TODO Auto-generated method stub
    	//Toast.makeText(this, count+"", Toast.LENGTH_SHORT).show();  
    	//getRedPoint
    	if(appuser.getInstance().IsLogin())
   	 {
    	 Map<String, String> params = new HashMap<String, String>(); 
    	 handler_2.SetRequest(new RequestType("",Type.getRedPoint),params);
    	 
    	 webhandler handler_ = new webhandler(){
    		 public void OnError(int code, String mess) {
 				// TODO Auto-generated method stub
 				super.OnError(code, mess);
    		 }
    		 
				@Override
				public void OnResponse(JSONObject response) {
					// TODO Auto-generated method stub
					// TODO Auto-generated method stub
					try {
						 if(appuser.getInstance().IsLogin())
		    	    	 {
						Constant.indentify=response.getJSONObject("data").getString("indentify");
						appuser.getInstance().getUserinfo().signText=response.getJSONObject("data").getString("signText");
						appuser.getInstance().getUserinfo().city=response.getJSONObject("data").getString("city");
						appuser.getInstance().getUserinfo().provice=response.getJSONObject("data").getString("provice");
						if(response.getJSONObject("data").getString("provice").trim().toLowerCase().equals("null"))
						{
							appuser.getInstance().getUserinfo().provice="";
						}
						
						
						appuser.getInstance().getUserinfo().sex=response.getJSONObject("data").getString("sex");
						try
						{
							appuser.getInstance().getUserinfo().signinPoint=response.getJSONObject("data").getString("signinPoint");
						} catch (Exception e) {
				       
						}
						appuser.getInstance().getUserinfo().pointTotal=response.getJSONObject("data").getString("pointTotal");
						appuser.getInstance().getUserinfo().collTotal=response.getJSONObject("data").getString("collTotal");
						appuser.getInstance().getUserinfo().quesTotal=response.getJSONObject("data").getString("quesTotal");
						appuser.getInstance().getUserinfo().replyTotal=response.getJSONObject("data").getString("replyTotal");
						//appuser.getInstance().getUserinfo().invCode=response.getJSONObject("data").getString("invCode");
						//MessageBox.Show(getActivity(), ""+appuser.getInstance().getUserinfo().invCode);
					
		    	    	 }} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
			
					
				}
				
				};
				//handler_.SetProgressDialog(getActivity());
				Map<String, String> params2 = new HashMap<String, String>();
				params2.put("uid",   Constant.uid);
				String str = "ruili"+ Constant.uid;
				String strBase64 = new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
				Log.i("Test", "encode >>>" + strBase64);
				String enToStr = Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
				params2.put("accessCode",  enToStr);
				handler_.SetRequest(new RequestType("",Type.getUserBaseInt),params);
   	 }
	}
    public void onDestroy() {
        super.onDestroy();
        /** 服务停止时，终止计数进程 */
        this.threadDisable = true;
    }

    public int getConunt() {
        return count;
    }
    int firstts =-1;
    int a=-1;
    private void showDialog() {
    	
		final AlertDialog dialog = new AlertDialog.Builder(CountService.this)
				.create();
		dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dialog.show();
		Window window = dialog.getWindow();
		// 设置布局
		window.setContentView(R.layout.dialog_myconfig2);
		// 设置宽高
		
		window.setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		// 设置弹出的动画效果
		//window.setWindowAnimations(R.style.AnimBottom);
		// 设置监听
		
		Button leftBtn1 = (Button)window.findViewById(R.id.left_btn);
		Button rightBtn1 = (Button)window.findViewById(R.id.right_btn);
		TextView title_choices = (TextView)window.findViewById(R.id.title_choices);
		TextView choice_one_text= (TextView)window.findViewById(R.id.choice_one_text);
		//title_choices.setText("");
		
		String b="";
		if(a==1)
			b=""+"是否继续下载?";
		
			else if(a==2)
				b="正在使用数据流量"+"是否继续下载?";
				else if(a==0)
					b="当前无网络连接,下载失败";
				
			
		choice_one_text.setText(b);
		leftBtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				dialog.cancel();

			}
			
		});
		
		rightBtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(a==0)
				{}
				else
				 down();
				
				dialog.cancel();
			}
			
		});
	
	}
    private void down() {
		// TODO Auto-generated method stub
    	 int size=goingList.size();
  		
  		//DownloadManager.getInstance().StatMissions();
  		 for(int i=0;i<size;i++)
  		 {
  		Object mission =goingList.get(i);
  	  if(mission instanceof DownloadRecord){
  		DownloadRecord record = (DownloadRecord)mission;
  		//if(record.Status==1)
  	//	DownloadRecord.deleteOne(record.DownloadId+"");
  		//downloadHelper.safeDownload(record);
  	//	DownloadManager.getInstance().cancelMission(record.DownloadId);
  		//downloadHelper .download(record);
  		DownloadManager.getInstance().executeMission(i);
  	  }
  		if(mission instanceof Mission){
			Mission sion = (Mission)mission;
			downloadHelper .download(sion);}
  		}
  	  	 


	}
//此方法是为了可以在Acitity中获得服务的实例   
class ServiceBinder extends Binder {
        public CountService getService() {
            return CountService.this;
        }
    }
}