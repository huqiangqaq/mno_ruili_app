package mno.ruili_app.my;

import java.io.File;

import com.ab.image.AbImageLoader;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.ct.validator;
import mno_ruili_app.home.home_video;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class my_viedio extends Activity{
	RelativeLayout layout_view,layout_view2;
	int lastvalue=0;
	int start=0;
	String path;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_view);
		path=Constant.itemid;
		init();
		setListener();
		start=1;
		myp.setVisibility(view.VISIBLE);
		//postSize=10;
	/*	new PlayMovie(0).start();   //表明是第一次开始播放
		play.setEnabled(false);
		play.setBackgroundResource(R.drawable.view_ks);*/
	}
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
			if(newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
			  {
			   //TODO 某些操作 
			  }
		super.onConfigurationChanged(newConfig);
	}
	MediaPlayer mediaPlayer;
	SeekBar seekbar;
	SurfaceView pView;
	LinearLayout playcontrl;
	ImageView backButton,play;
	TextView view_bj,vtime;
	ProgressBar myp;
	private View view;  
	private int postSize;  
	private boolean flag = true;   //用于判断视频是否在播放中
	private boolean display;   //用于是否显示其他按钮
	private upDateSeekBar update;   //更新进度条用
	private void init() {
		// TODO Auto-generated method stub
		    layout_view= (RelativeLayout)this.findViewById(R.id.layout_view);
			layout_view2= (RelativeLayout)this.findViewById(R.id.layout_view2);
			mediaPlayer = new MediaPlayer();   //创建一个播放器对象
			update = new upDateSeekBar();  //创建更新进度条对象
			play= (ImageView) findViewById(R.id.play); 
			backButton = (ImageView) findViewById(R.id.view_back);  //返回按钮
			seekbar = (SeekBar)this. findViewById(R.id.seekbar);  //进度条
			vtime=(TextView) this.findViewById(R.id.vtime); 
			view_bj = (TextView) findViewById(R.id.view_bj); 
			myp = (ProgressBar) findViewById(R.id.myp); 
			pView = (SurfaceView) findViewById(R.id.mSurfaceView);
			pView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);   //不缓冲
			pView.getHolder().setKeepScreenOn(true);   //保持屏幕高亮
			pView.getHolder().addCallback(new surFaceView());   //设置监听事件
			playcontrl = (LinearLayout) findViewById(R.id.playcontrl);
			playcontrl.setVisibility(View.GONE);
			pView.setVisibility(view.VISIBLE);
			view = findViewById(R.id.pb);  
			//play.setEnabled(false);  
			seekbar.setEnabled(false);
			
	    	
	       
	}
	private boolean fullscreen = false;//全屏/窗口播放切换标志
	public void onclick(View v) {
		
		 if(v.getId()==R.id.butqp){
			 if(!fullscreen){
   			
   			  getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
   			  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
   				LayoutParams lp;       
   		        lp=layout_view.getLayoutParams();
   		        lp.width= LayoutParams.FILL_PARENT;;
   		        lp.height= LayoutParams.FILL_PARENT;;        
   		        layout_view.setLayoutParams(lp); 
   		       
   		            fullscreen = true;//改变全屏/窗口的标记
   		        }else{//设置RelativeLayout的窗口模式
   		        	/*validator.setScreenBrightness(this,right);*/
   		        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
   		        	getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
   		        	LayoutParams lp;       
   		            lp=layout_view.getLayoutParams();
   		            //lp.width=100;
   		            lp.height=Constant.displayWidth/16*9;        
   		            layout_view.setLayoutParams(lp); 
   		            fullscreen = false;//改变全屏/窗口的标记
   		        }    
		 }
		 else if(v.getId()==R.id.view_back)
	    	{
	    		/*if (mediaPlayer.isPlaying()) {
					mediaPlayer.stop();
					mediaPlayer.release();
				}
				mediaPlayer = null;*/
				finish();
	    	}
   	}
	private void setListener() {
		mediaPlayer
				.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
					@Override
					public void onBufferingUpdate(MediaPlayer mp, int percent) {
					}
				});

		mediaPlayer
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { //视频播放完成
					@Override
					public void onCompletion(MediaPlayer mp) {
						flag = false;
						play.setBackgroundResource(R.drawable.view_zt);
					}
				});
		
		mediaPlayer.setOnErrorListener(new OnErrorListener() { 
			  
			@Override 
			public boolean onError(MediaPlayer mp, int what, int extra) { 
			// 发生错误重新播放 
				play.setBackgroundResource(R.drawable.view_zt);
				mediaPlayer.reset();
				/*if (mediaPlayer.isPlaying()) {
					mediaPlayer.stop();
					mediaPlayer.release();
				}
				mediaPlayer = null;
				mediaPlayer = new MediaPlayer(); */
				new PlayMovie(0).start(); 
				
				/*
				postSize = 0;*/
			//isPlaying = false; 
			return false; 
			} 
			}); 

		mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				
    		
			}
			
		});
		play.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*if(start==0)
				{
					start=1;
		    		myp.setVisibility(view.VISIBLE);
		    		new PlayMovie(0).start();   //表明是第一次开始播放
		    		play.setEnabled(false);
		    		play.setBackgroundResource(R.drawable.view_ks);
				}*/
				 if (mediaPlayer.isPlaying()) {    
					play.setBackgroundResource(R.drawable.view_zt);
					mediaPlayer.pause();
					postSize = mediaPlayer.getCurrentPosition();
					flag=false;
				} else {
					if (flag == false) {
						flag = true;
						
					}
					new Thread(update).start();
					mediaPlayer.start();
					play.setBackgroundResource(R.drawable.view_ks);

				}
			}
		});
		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

				int value = seekbar.getProgress() * mediaPlayer.getDuration() 
						/ seekbar.getMax();
				//validator.getShowTime(position * sMax / mMax)
				vtime.setText(validator.getShowTime(value)+" / "+validator.getShowTime(mediaPlayer.getDuration()));
				mediaPlayer.seekTo(value);

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

			}
		});
		/**
		 * 点击屏幕，切换控件的显示，显示则应藏，隐藏，则显示
		 */
				pView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (display) {
							
							playcontrl.setVisibility(View.GONE);
							display = false;
						} else {
							
							playcontrl.setVisibility(View.VISIBLE);
							pView.setVisibility(View.VISIBLE);
							/**
							 * 设置播放为全屏
							 */
						/*	ViewGroup.LayoutParams lp = pView.getLayoutParams();
							lp.height = LayoutParams.FILL_PARENT;
							lp.width = LayoutParams.FILL_PARENT;
							pView.setLayoutParams(lp);*/
							display = true;
						}

					}
				});
		}
	class PlayMovie extends Thread {   //播放视频的线程

		int post = 0;
		
		public PlayMovie(int post) {
			this.post = post;

		}
		/*public PlayMovie(String moveieurl) {
			this.moveieurl = moveieurl;

		}*/

		@Override
		public void run() {
			Message message = Message.obtain();
			try {
				
				mediaPlayer.reset();    //回复播放器默认
				

					File file = new File(path);
					//判断文件夹是否存在,如果不存在则创建文件夹
					if (!file.exists()) 
					{
						MessageBox.Show(getApplicationContext(), "文件不存在");
						
					}
					else
					{
						if(file.isFile())
							mediaPlayer.setDataSource(path); 
						else
						{
							MessageBox.Show(getApplicationContext(), "文件错误\n无法播放");
						}
						
					}
				
				  //设置播放路径
				mediaPlayer.setDisplay(pView.getHolder());  //把视频显示在SurfaceView上
				mediaPlayer.setOnPreparedListener(new Ok(post));  //设置监听事件
				mediaPlayer.prepare();  //准备播放
			} catch (Exception e) {
				message.what = 2;
				Log.e("hck", e.toString());
			}

			super.run();
		}

	}
	class Ok implements OnPreparedListener {
		int postSize;

		public Ok(int postSize) {
			this.postSize = postSize;
		}

		@Override
		public void onPrepared(MediaPlayer mp) {
			int videoWidth = mp.getVideoWidth();
			int videoHeight = mp.getVideoHeight();
			int mWidth = layout_view.getWidth();
			int mHeight = layout_view.getHeight();
			
			if (videoWidth > 0 && videoHeight > 0) {
	            if ( videoWidth * mHeight  > mWidth * videoHeight ) {
	                //Log.i("@@@", "image too tall, correcting");
	            	mHeight = mWidth * videoHeight / videoWidth;
	            } else if ( videoWidth * mHeight  < mWidth * videoHeight ) {
	                //Log.i("@@@", "image too wide, correcting");
	            	mWidth = mHeight * videoWidth / videoHeight;
	            } else {
	                
	            }
	        }
			LayoutParams lp;       
	        lp=pView.getLayoutParams();
	        lp.width=mWidth;
	        lp.height=mHeight;    
	        pView.setLayoutParams(lp); 
			
			Log.i("hck", "play");
			Log.i("hck", "post " + postSize);
			view.setVisibility(View.GONE);  //准备完成后，隐藏控件
			pView.setVisibility(view.VISIBLE);
			myp.setVisibility(view.GONE);
			layout_view2.setBackgroundColor(Color.TRANSPARENT );
		
			//vtime.setText("00:00:00"+"/"+mp.getDuration());
			playcontrl.setVisibility(View.GONE);
			view_bj.setVisibility(View.GONE);
			play.setEnabled(true);  
			seekbar.setEnabled(true);
			display = false;
			if (mediaPlayer != null) { 
				if(flag)
				{
				mediaPlayer.start();  //开始播放视频
				if (postSize > 0) {  //说明中途停止过（activity调用过pase方法，不是用户点击停止按钮），跳到停止时候位置开始播放
					Log.i("hck", "seekTo ");
					mediaPlayer.seekTo(postSize);
					mediaPlayer.pause();//跳到postSize大小位置处进行播放
					play.setBackgroundResource(R.drawable.view_zt);
					playcontrl.setVisibility(View.VISIBLE);
					flag=true;
					
				}
				else
				
				new Thread(update).start();   //启动线程，更新进度条
				}
			} else {
				return;
			}
			
		}
	}
	/**
	 * 更新进度条
	 */
	int first=0;
		Handler mHandler = new Handler() {
			public void handleMessage(Message msg) {
				if (mediaPlayer == null) {
					flag = false;
				} else if (mediaPlayer.isPlaying()) {
					
					flag = true;
					int position = mediaPlayer.getCurrentPosition();
					int mMax = mediaPlayer.getDuration();
					int sMax = seekbar.getMax();
					String time =validator.getShowTime(mMax);
					vtime.setText(validator.getShowTime(position)+" / "+time);
					seekbar.setProgress(position * sMax / mMax);
				
					int videoWidth = mediaPlayer.getVideoWidth();
					int videoHeight = mediaPlayer.getVideoHeight();
					int mWidth = layout_view.getWidth();
					int mHeight = layout_view.getHeight();
					
					if (videoWidth > 0 && videoHeight > 0) {
			            if ( videoWidth * mHeight  > mWidth * videoHeight ) {
			                //Log.i("@@@", "image too tall, correcting");
			            	mHeight = mWidth * videoHeight / videoWidth;
			            } else if ( videoWidth * mHeight  < mWidth * videoHeight ) {
			                //Log.i("@@@", "image too wide, correcting");
			            	mWidth = mHeight * videoWidth / videoHeight;
			            } else {
			                
			            }
			        }
					
					LayoutParams lp;       
			        lp=pView.getLayoutParams();
			        lp.width=mWidth;
			        lp.height=mHeight;    
			        pView.setLayoutParams(lp); 
				} else {
					return;
				}
			};
		};
	class upDateSeekBar implements Runnable {

		@Override
		public void run() {
			mHandler.sendMessage(Message.obtain());
			if (flag) {
				mHandler.postDelayed(update, 1000);
			}
		}
	}
	private class surFaceView implements Callback {     //上面绑定的监听的事件

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {   //创建完成后调用
			if (postSize > 0 && path!= null) { 
				//说明，停止过activity调用过pase方法，跳到停止位置播放
				if(flag==true)
					return;
				new PlayMovie(postSize).start();
				int a=postSize;
				a=a+1;
				int c=mediaPlayer.getDuration();
				c=c+1;
				flag = true;
				int sMax = seekbar.getMax();
				int mMax = mediaPlayer.getDuration();
				seekbar.setProgress(postSize * sMax / mMax);
				postSize = 0;
				view.setVisibility(View.GONE);
			}
			else if(start==1){
				new PlayMovie(0).start();
				int b =104858;
				play.setBackgroundResource(R.drawable.view_ks);//表明是第一次开始播放
			}
			
		}
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) { //activity调用过pase方法，保存当前播放位置
			if (mediaPlayer != null && mediaPlayer.isPlaying()) {
				postSize = mediaPlayer.getCurrentPosition();
				mediaPlayer.stop();
				flag = false;
				view.setVisibility(View.VISIBLE);
			}
		}
	}
	//play.setBackgroundResource(R.drawable.view_ks);

	@Override
	protected void onDestroy() {   //activity销毁后，释放资源
		super.onDestroy();
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
			
			
		}
		System.gc();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		
		 if (mediaPlayer.isPlaying()) {    
				play.setBackgroundResource(R.drawable.view_zt);
				mediaPlayer.pause();
				postSize = mediaPlayer.getCurrentPosition();
				flag=false;
		 }
		
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		 
		super.onResume();
	}
}
