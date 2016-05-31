package mno_ruili_app.home;

import java.util.List;

import mno.ruili_app.R;
import mno.ruili_app.ct.MessageBox;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.ab.activity.AbActivity;
import com.gensee.common.ServiceType;
import com.gensee.common.PlayerEnv.IProxy;
import com.gensee.download.VodDownLoader;
import com.gensee.entity.ChatMsg;
import com.gensee.entity.DocInfo;
import com.gensee.entity.InitParam;
import com.gensee.entity.QAMsg;
import com.gensee.entity.VodObject;
import com.gensee.media.VODPlayer;
import com.gensee.media.VODPlayer.OnVodPlayListener;
import com.gensee.pdu.GSDocView;
import com.gensee.pdu.GSDocView.OnDocViewEventListener;
import com.gensee.taskret.OnTaskRet;
import com.gensee.utils.GenseeLog;
import com.gensee.utils.StringUtil;
import com.gensee.view.GSVideoView;
import com.gensee.vod.VodSite;
import com.gensee.vod.VodSite.OnVodListener;


public class home_vod extends AbActivity implements OnDocViewEventListener,
OnVodPlayListener, OnClickListener, OnSeekBarChangeListener,OnVodListener{
	
	private VODPlayer mGSOLPlayer;
	private GSDocView mDocView;
	private SeekBar mSeekBarPlayViedo;
	private TextView mNowTimeTextview;
	private TextView mAllTimeTextView;
	LinearLayout playcontrldb;
	private ImageView mPauseScreenplay;
	private boolean isTouch = false;

	private static final int DURITME = 2000;

	private int VIEDOPAUSEPALY = 0;

	String vodId;
	private VodSite vodSite;

	interface MSG {
		int MSG_ON_INIT = 1;
		int MSG_ON_STOP = 2;
		int MSG_ON_POSITION = 3;
		int MSG_ON_VIDEOSIZE = 4;
		int MSG_ON_PAGE = 5;
		int MSG_ON_SEEK = 6;
		int MSG_ON_AUDIOLEVEL = 7;
		int MSG_ON_ERROR = 8;
		int MSG_ON_PAUSE = 9;
		int MSG_ON_RESUME = 10;
	}

	protected Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG.MSG_ON_INIT:
				int max = (Integer) msg.obj;
				mSeekBarPlayViedo.setMax(max);
				max = max / 1000;
				mAllTimeTextView.setText(getTime(max));
				break;
			case MSG.MSG_ON_STOP:

				break;
			case MSG.MSG_ON_VIDEOSIZE:

				break;
			case MSG.MSG_ON_PAGE:

				break;
			case MSG.MSG_ON_PAUSE:
				VIEDOPAUSEPALY = 1;
				mPauseScreenplay.setImageResource(R.drawable.view_zt);
				break;
			case MSG.MSG_ON_RESUME:
				VIEDOPAUSEPALY = 0;
				mPauseScreenplay.setImageResource(R.drawable.view_ks);
				break;
			case MSG.MSG_ON_POSITION:
				if (isTouch) {
					return;
				}
			case MSG.MSG_ON_SEEK:
				isTouch = false;
				int anyPosition = (Integer) msg.obj;
				mSeekBarPlayViedo.setProgress(anyPosition);
				anyPosition = anyPosition / 1000;
				mNowTimeTextview.setText(getTime(anyPosition));
				break;
			case MSG.MSG_ON_AUDIOLEVEL:

				break;
			case MSG.MSG_ON_ERROR:
				int errorCode = (Integer) msg.obj;
				switch (errorCode) {
				case 2:
					Toast.makeText(getApplicationContext(), "暂停失败", DURITME)
							.show();
					break;
				case 1:
					Toast.makeText(getApplicationContext(), "播放失败", DURITME)
							.show();
					break;
				case 3:
					Toast.makeText(getApplicationContext(), "恢复失败", DURITME)
							.show();
					break;
				case 4:
					Toast.makeText(getApplicationContext(), "进度变化失败", DURITME)
							.show();
					break;
				case 5:
					Toast.makeText(getApplicationContext(), "停止失败", DURITME)
							.show();
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	private String getTime(int time) {
		return String.format("%02d", time / 3600) + ":"
				+ String.format("%02d", time % 3600 / 60) + ":"
				+ String.format("%02d", time % 3600 % 60);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vodplay);
		VodSite.setTcpProxy(new IProxy() {

			@Override
			public int getProxyPort(int port) {
				// 返回代理端口
				return port;
			}

			@Override
			public String getProxyIP(String ip) {
				// 返回代理ip地址
				return ip;
			}
		});
		VodSite.init(this, new OnTaskRet() {

			@Override
			public void onTaskRet(boolean arg0, int arg1, String arg2) {
				// TODO Auto-generated method stub

			}
		});
		
		mDocView = (GSDocView) findViewById(R.id.palydoc);
		mSeekBarPlayViedo = (SeekBar) findViewById(R.id.seekbarpalyviedo);
		//stopVeidoPlay = (Button) findViewById(R.id.stopveidoplay);
		mPauseScreenplay = (ImageView) findViewById(R.id.pauseresumeplay);
		playcontrldb = (LinearLayout) findViewById(R.id.playcontrldb);
		mNowTimeTextview = (TextView) findViewById(R.id.palynowtime);
		mAllTimeTextView = (TextView) findViewById(R.id.palyalltime);
		mSeekBarPlayViedo.setOnSeekBarChangeListener(this);
		mDocView.setOnDocViewClickedListener(this);
		//stopVeidoPlay.setOnClickListener(this);
		//replyVedioPlay.setOnClickListener(this);
		mPauseScreenplay.setOnClickListener(this);
        getid();
		//initPlayer();
	}
	private ServiceType serviceType = ServiceType.ST_CASTLINE;
	private void getid() {
		// TODO Auto-generated method stub
		// initParam的构造
					InitParam initParam = new InitParam();
					// domain 域名
					initParam.setDomain("wzect.gensee.com");
					// 点播编号 （不是点播id）
					//initParam.setNumber(strNumber);
					// 设置点播id，和点播编号对应，两者至少要有一个有效才能保证成功
					initParam.setLiveId("90ca9f5e4e164d4aac836d398534207d");
					// 站点认证帐号
					initParam.setLoginAccount("13588713118");
					// 站点认证密码
					initParam.setLoginPwd("123123");
					// 点播口令
					initParam.setVodPwd("ruilipeixun");
					// 昵称 用于统计和显示
					initParam.setNickName("ck");
					// 服务类型（站点类型）
					// webcast - ST_CASTLINE
					// training - ST_TRAINING
					// meeting - ST_MEETING
					initParam.setServiceType(serviceType);
					
					vodSite = new VodSite(this);
					vodSite.setVodListener(this);
					vodSite.getVodObject(initParam);
	}
	private void initPlayer() {
		
		String vodIdOrLocalPath = getVodIdOrLocalPath();
		if (vodIdOrLocalPath == null) {
			Toast.makeText(this, "路径不对", Toast.LENGTH_SHORT).show();
			return;
		}
		if (mGSOLPlayer == null) {
			mPauseScreenplay.setImageResource(R.drawable.view_ks);
			mGSOLPlayer = new VODPlayer();
			//mGSOLPlayer.setGSVideoView(mGSVideoView);
			mGSOLPlayer.setGSDocView(mDocView);
			mGSOLPlayer.play(vodIdOrLocalPath, this, "");
		}
	}
	
	private String getVodIdOrLocalPath(){
	
		return  vodId;
	}

	@Override
	public void onInit(int result, boolean haveVideo, int duration,
			List<DocInfo> docInfos) {
		myHandler.sendMessage(myHandler
				.obtainMessage(MSG.MSG_ON_INIT, duration));
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	public void onPlayStop() {
		myHandler.sendMessage(myHandler.obtainMessage(MSG.MSG_ON_STOP, 0));
	}

	@Override
	public void onPosition(int position) {
		//GenseeLog.d(TAG, "onPosition pos = " + position);
		myHandler.sendMessage(myHandler.obtainMessage(MSG.MSG_ON_POSITION,
				position));
	}

	@Override
	public void onVideoSize(int position, int videoWidth, int videoHeight) {
		myHandler.sendMessage(myHandler.obtainMessage(MSG.MSG_ON_VIDEOSIZE, 0));
	}

//	@Override
//	public void onPage(int position) {
//		myHandler.sendMessage(myHandler
//				.obtainMessage(MSG.MSG_ON_PAGE, position));
//	}

	@Override
	public void onSeek(int position) {
		myHandler.sendMessage(myHandler
				.obtainMessage(MSG.MSG_ON_SEEK, position));
	}

	@Override
	public void onAudioLevel(int level) {
		myHandler.sendMessage(myHandler.obtainMessage(MSG.MSG_ON_AUDIOLEVEL,
				level));
	}

	@Override
	public void onError(int errCode) {
		myHandler.sendMessage(myHandler
				.obtainMessage(MSG.MSG_ON_ERROR, errCode));
	}

	@Override
	public void onPlayPause() {
		myHandler.sendMessage(myHandler.obtainMessage(MSG.MSG_ON_PAUSE, 0));
	}

	@Override
	public void onPlayResume() {
		myHandler.sendMessage(myHandler.obtainMessage(MSG.MSG_ON_RESUME, 0));
	}


	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		isTouch = true;

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		if (null != mGSOLPlayer) {
			int pos = seekBar.getProgress();
			//GenseeLog.d(TAG, "onStopTrackingTouch pos = " + pos);
			mGSOLPlayer.seekTo(pos);

		}

	}
	public void onclick(View currenView) {
		if (currenView.getId() == R.id.palydoc) {
			
		}

	}
	@Override
	public void onClick(View currenView) {

		 if (currenView.getId() == R.id.pauseresumeplay) {

			 if(mGSOLPlayer==null)
			 {
				 initPlayer();	
				 return;
			 }
			if (VIEDOPAUSEPALY == 0) {

				mGSOLPlayer.pause();

			} else if (VIEDOPAUSEPALY == 1) {

				mGSOLPlayer.resume();

			}
		}
	}

	private void stopPlay() {
		if (mGSOLPlayer != null) {
			mGSOLPlayer.stop();
		}
	}

	private void release() {
		stopPlay();
		if (mGSOLPlayer != null) {
			mGSOLPlayer.release();
		}
	}

	@Override
	public void onBackPressed() {
		
		release();
		super.onBackPressed();
	}

	@Override
	public boolean onDoubleClicked(GSDocView arg0) {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
//	public boolean onEndHDirection(GSDocView arg0, int arg1) {
//		// TODO Auto-generated method stub
//		return false;
//	}

	@Override
	public boolean onSingleClicked(GSDocView arg0) {
		// TODO Auto-generated method stub
		playcontrldb.setVisibility(View.GONE);
		MessageBox.Show(getApplicationContext(), "ON");
		return false;
	}

	@Override
	public void onCaching(boolean isCatching) {
		// TODO Auto-generated method stub
		
	}
/*fdssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss*/
	
	
	private static final int DURTIME = 2000;

	public interface RESULT {
		int DOWNLOAD_ERROR = 2;
		int DOWNLOAD_STOP = 3;
		int DOWNLOADER_INIT = 4;
		int DOWNLOAD_START = 5;
		int ON_GET_VODOBJ = 100;
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			
			case RESULT.ON_GET_VODOBJ:

				vodId = (String) msg.obj;
				
				break;
			
			default:
				break;
			}
		}

	};

//	@Override
//	public void onChatHistory(String arg0, List<ChatMsg> arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//	@Override
//	public void onQaHistory(String arg0, List<QAMsg> arg1) {
//		// TODO Auto-generated method stub
//		
//	}
	@Override
	public void onVodDetail(VodObject vodObj) {
		// TODO Auto-generated method stub
		if (vodObj != null) {
			vodObj.getDuration();// 时长
			vodObj.getEndTime();// 录制结束时间 始于1970的utc时间毫秒数
			vodObj.getStartTime();// 录制开始时间 始于1970的utc时间毫秒数
			vodObj.getStorage();// 大小 单位为Byte
		}
	}
	@Override
	public void onVodErr(final int errCode) {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				String msg = getErrMsg(errCode);
				if (!"".equals(msg)) {
					Toast.makeText(home_vod.this , msg, Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}
	@Override
	public void onVodObject(String arg0) {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler
				.obtainMessage(RESULT.ON_GET_VODOBJ, arg0));
	}
	/**
	 * 错误码处理
	 * 
	 * @param errCode
	 *            错误码
	 * @return 错误码文字表示内容
	 */
	private String getErrMsg(int errCode) {
		String msg = "";
		switch (errCode) {
		case ERR_DOMAIN:
			msg = "domain 不正确";
			break;
		case ERR_TIME_OUT:
			msg = "超时";
			break;
		case ERR_SITE_UNUSED:
			msg = "站点不可用";
			break;
		case ERR_UN_NET:
			msg = "无网络请检查网络连接";
			break;
		case ERR_DATA_TIMEOUT:
			msg = "数据过期";
			break;
		case ERR_SERVICE:
			msg = "请检查填写的serviceType";
			break;
		case ERR_PARAM:
			msg = "请检查参数";
			break;
		case -201:
			msg = "请先调用getVodObject";
			break;
		case ERR_VOD_INTI_FAIL:
			msg = "调用getVodObject失败";
			break;
		case ERR_VOD_NUM_UNEXIST:
			msg = "点播编号不存在或点播不存在";
			break;
		case ERR_VOD_PWD_ERR:
			msg = "点播密码错误";
			break;
		case ERR_VOD_ACC_PWD_ERR:
			msg = "登录帐号或登录密码错误";
			break;
		case ERR_UNSURPORT_MOBILE:
			msg = "不支持移动设备";
			break;

		default:
			break;
		}
		return msg;
	}
	
	@Override
	public void onChat(List<ChatMsg> arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPageSize(int position, int arg1, int arg2) {
		myHandler.sendMessage(myHandler
				.obtainMessage(MSG.MSG_ON_PAGE, position));
		
	}
	@Override
	public void onVideoStart() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onChatHistory(String arg0, List<ChatMsg> arg1, int arg2,
			boolean arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onQaHistory(String arg0, List<QAMsg> arg1, int arg2,
			boolean arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onEndHDirection(GSDocView arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return true;
	}

}
