package mno.ruili_app.main;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class mysever extends Service{

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this,"MusicService onBind()",Toast.LENGTH_SHORT).show();   
        Log.i("tag", "MusicService onBind()"); 
		return null;
	}
	 public boolean onUnbind(Intent intent){   
         Toast.makeText(this, "MusicService onUnbind()", Toast.LENGTH_SHORT).show();   
        
         timer.cancel();  
         return false;   
 }   
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		 timer.schedule(task,1000,1000);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		 Toast.makeText(this, "MusicService onDestroy()", Toast.LENGTH_SHORT).show();   
		 if (timer != null) {
			  timer.cancel( );
			  timer = null;
			  }
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
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

	};
	int i=100;
	private void update() {
		// TODO Auto-generated method stub
		 i++;
		 Toast.makeText(this, i+"", Toast.LENGTH_SHORT).show();   
	}


}
