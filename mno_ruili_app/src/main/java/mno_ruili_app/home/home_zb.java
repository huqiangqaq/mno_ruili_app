package mno_ruili_app.home;

import mno.ruili_app.R;
import mno.ruili_app.ct.MessageBox;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.gensee.common.ServiceType;
import com.gensee.entity.InitParam;
import com.gensee.net.RtComp;
import com.gensee.net.RtComp.Callback;
import com.gensee.room.RtSimpleImpl;
import com.gensee.routine.IRTEvent.IRoomEvent.LeaveReason;
import com.gensee.routine.State;
import com.gensee.routine.UserInfo;
import com.gensee.routine.IRTEvent.IRoomEvent.JoinResult;
import com.gensee.view.GSVideoView;
import com.gensee.view.GSVideoView.RenderMode;

public class home_zb extends Activity implements Callback{

	private Button btnLeave;

	private GSVideoView videoView;

	private RtSimpleImpl simpleImpl;
	
	// 站点类型ServiceType.ST_CASTLINE 直播webcast，
		// ServiceType.ST_MEETING 会议meeting，
		// ServiceType.ST_TRAINING 培训 training
	private ServiceType serviceType = ServiceType.ST_CASTLINE;
	private UserInfo self;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_zb);
	
		/**
		 * 初始化一个RTSDK简单实现，重新定义一个类继承RtSimpleImpl也是可以的，
		 * 并实现其3个抽象函数，其中onGetContext必须要返回app context，音视频最佳选择。
		 * 注：这里是在onCreate中创建的simpleImpl，如果有屏幕变化引起simpleImpl 重新被创建需要自行处理；回调线程为非UI线程，更新ui请在UI线程中处理。
		 */
		simpleImpl = new RtSimpleImpl() {
			
			
			
			@Override
			public Context onGetContext() {
				return getBaseContext();
			}
			
			@Override
			protected void onVideoStart() {
			
				
			}
			
			@Override
			protected void onVideoEnd() {
				
				
			}
			
			/**
			 * result 0 表示加入房间（直播间、会议室、课堂）成功  其他代表加入失败  失败后最好以对话框通知用户本次操作失败了
			 */
			@Override
			public void onRoomJoin(final int result, UserInfo self) {
				super.onRoomJoin(result, self);
				home_zb.this.self = self;
				
				runOnUiThread(new Runnable() {
					public void run() {
						
						String resultDesc = null;
						switch (result) {
						//加入成功  除了成功其他均需要正常提示给用户
						case JoinResult.JR_OK:
							AlertUtil.toast(home_zb.this, "您已加入成功");
							break;
							//加入错误
						case JoinResult.JR_ERROR:
							resultDesc = "加入失败，重试或联系管理员";
							finish();
							break;
							//课堂被锁定
						case JoinResult.JR_ERROR_LOCKED:
							resultDesc = "直播间已被锁定";
							
							break;
							//老师（组织者已经加入）
						case JoinResult.JR_ERROR_HOST:
							resultDesc = "老师已经加入，请以其他身份加入";
							break;
							//加入人数已满
						case JoinResult.JR_ERROR_LICENSE:
							resultDesc = "人数已满，联系管理员";
							
							break;
							//音视频编码不匹配
						case JoinResult.JR_ERROR_CODEC:
							resultDesc = "编码不匹配";
							break;
							//超时
						case JoinResult.JR_ERROR_TIMESUP:
							resultDesc = "已经超过直播结束时间";
							break;
							
						default:
							resultDesc = "其他结果码：" + result + "联系管理员";
							break;
						}
						if(resultDesc != null){
							Toast.makeText(home_zb.this,resultDesc,Toast.LENGTH_SHORT).show();
						}
					}
				});
				
			}
			
			/**
			 * 直播状态 s.getValue()   0 默认直播未开始 1、直播中， 2、直播停止，3、直播暂停
			 */
			@Override
			public void onRoomPublish(State s) {
				super.onRoomPublish(s);
				
				//TODO 此逻辑是控制视频要在直播开始后才准许看的逻辑
				/*	byte castState = s.getValue();
				    RtSdk rtSdk = getRtSdk();
				    
					switch (castState) {
					case 1:
						setVideoView(videoView);
				        rtSdk.audioOpenSpeaker(null);
						break;
					case 0:
					case 2:
					case 3:
						setVideoView(null);
				        rtSdk.audioCloseSpeaker(null);
					default:
						break;
					}*/
				
			}

			@Override
			public void onJoin(boolean result) {
				// TODO Auto-generated method stub
				
			}
			
			//退出完成 关闭界面
			@Override
			protected void onRelease(final int reason) {
				//reason 退出原因
				runOnUiThread(new Runnable() {
					
					@SuppressLint("NewApi")
					@Override
					public void run() {
						String msg = "已退出";
						switch (reason) {
						//用户自行退出  正常退出
						case LeaveReason.LR_NORMAL:
							msg = "您已经成功退出";
							break;
					    //LR_EJECTED = LR_NORMAL + 1; //被踢出
						case LeaveReason.LR_EJECTED:
							msg = "您已被踢出"; 
							break;
						//LR_TIMESUP = LR_NORMAL + 2; //时间到
						case LeaveReason.LR_TIMESUP:
							msg = "时间已过"; 
							break;
						//LR_CLOSED = LR_NORMAL + 3; //直播（课堂）已经结束（被组织者结束）
						case LeaveReason.LR_CLOSED:
							msg = "直播间已经被关闭"; 
							break;

						default:
							break;
						}
						
				
								finish();
				
						
						
					}
				});
			}
		};
		initView();
	}

	private void initView() {
	
		
		
		
		videoView = (GSVideoView) findViewById(R.id.surface_casting_cus);


		findViewById(R.id.init).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				InitParam p = new InitParam();
				//domain
				p.setDomain("wzect.gensee.com");
				//编号（直播间号）,如果没有编号却有直播id的情况请使用setLiveId("此处直播id或课堂id");
				//p.setNumber(number);
				p.setLiveId("fb2fe17543ab47ff89add7345ce6de0d");
				//站点认证帐号，根据情况可以填""
				p.setLoginAccount("");
				//站点认证密码，根据情况可以填""
				p.setLoginPwd("");
				//昵称，供显示用
				p.setNickName("123");
				//加入口令，没有则填""
				p.setJoinPwd("951325");
				//站点类型ServiceType.ST_CASTLINE 直播webcast，
				//ServiceType.ST_MEETING 会议meeting，
				//ServiceType.ST_TRAINING 培训
				p.setServiceType(serviceType);
				
				RtComp comp = new RtComp(getApplicationContext(),
						home_zb.this);
				comp.setbAttendeeOnly(true);
				comp.initWithGensee(p);
			}
		});

		btnLeave = (Button) findViewById(R.id.btnExit);
		btnLeave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				leaveCast();
			}
		});

		/**
		 * 设置视频View
		 */
		simpleImpl.setVideoView(videoView);
		findViewById(R.id.btnVideo).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RenderMode  mode = videoView.getRenderMode();
				switch (mode) {
				//适应窗口
				case RM_ADPT_XY:
					videoView.setRenderMode(RenderMode.RM_FILL_XY);
					break;
					//完全充满
				case RM_FILL_XY:
					videoView.setRenderMode(RenderMode.RM_CENTER);
					break;
					//中间显示  原始大小比窗口小的时候
				case RM_CENTER:
					videoView.setRenderMode(RenderMode.RM_ADPT_XY);
					break;

				default:
					break;
				}
			}
		});
		
		// 这里可以获得Rtsdk的实例，因此可以注册其他回调进行其他功能"问答、聊天、投票"的开发
		/*
		 * RtSdk rtSdk = simpleImpl.getRtSdk(); 
		 * rtSdk.setQACallback(qaCallback); //问答功能
		 * rtSdk.setVoteCallback(voteCallBack);//投票
		 * rtSdk.setChatCallback(chatCallBack);//聊天
		 */
		
		/*
		 * 设置文档View
		 */
        //simpleImpl.setDocView(docView);
	}

	@Override
	public void onInited(String rtParam) {
		
		simpleImpl.joinWithParam("", rtParam);
	}

	@Override
	public void onErr(final int errCode) {
	
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
			/*	AlertUtil.showDialog(home_zb.this, "初始化错误:错误码" + errCode
						+ "，请参考文档中的错误码说明",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						}, null);*/
				Toast.makeText(home_zb.this,"初始化错误:错误码" + errCode
						+ "，请参考文档中的错误码说明",Toast.LENGTH_SHORT).show();
			}
		});
	}

	
	/**
	 * 退出的时候请调用
	 */
	private void leaveCast(){
		//TODO 显示进度框
		simpleImpl.leave(false);
		finish();
	}

	
	@Override
	public void onBackPressed() {
		if (self == null) {
			super.onBackPressed();
			return;
		}
		leaveCast();
	}


}