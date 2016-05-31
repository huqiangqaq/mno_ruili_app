package mno.down.service;

import mno.down.adapter.DownloadAdapter;
import mno.down.util.DownloadManager;
import mno.down.util.Mission;
import mno.down.util.MissionListenerForNotification;
import mno.down.util.MissionSaver;
import mno.ruili_app.R;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.BaseAdapter;
import android.widget.Toast;


public class DownloadService extends Service  implements Mission.MissionListener<Mission>{
	private DownloadManager manager = DownloadManager.getInstance();
    private DownloadAdapter missionAdapter;

    private final int MSG_REPEAT = 0;
    private final int MSG_START = 1;
    private Handler downloadHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case MSG_REPEAT:
                
                	Intent intent = new Intent();  
                    intent.setAction("mno.xz");  
                    intent.putExtra("name", "正在下载");  
                    DownloadService.this.sendBroadcast(intent);  
                   // Toast.makeText(getApplicationContext(), "发送广播成功", Toast.LENGTH_SHORT).show();  
                
                   // Toast.makeText(DownloadService.this, R.string.downloading, Toast.LENGTH_SHORT).show();
                 
                    break;
                case MSG_START:
                
                	Intent intent2 = new Intent();  
                    intent2.setAction("mno.xz");  
                    intent2.putExtra("name", "开始下载");  
                    DownloadService.this.sendBroadcast(intent2);  
                   // Toast.makeText(getApplicationContext(), "发送广播成功", Toast.LENGTH_SHORT).show();  
                
                   // Toast.makeText(DownloadService.this, R.string.downloading, Toast.LENGTH_SHORT).show();
                 
                break;
            }

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        missionAdapter = new DownloadAdapter(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        manager.surrenderMissions(); //此处当我强制关闭App的时候，App暂停信息根本来不及保存
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new DownloadServiceBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class DownloadServiceBinder extends Binder {

        public void startDownload(Mission mission) {
            if(missionAdapter.isDownloadingRightNow(mission.getUri())){
                downloadHandler.sendEmptyMessage(MSG_REPEAT);
            }else{
                mission.addMissionListener(new MissionListenerForNotification(DownloadService.this));
                mission.addMissionListener(new MissionSaver());
                mission.addMissionListener(missionAdapter);
                mission.addMissionListener(DownloadService.this);
                manager.addMission(mission);
                manager.executeMission(mission);
                downloadHandler.sendEmptyMessage(MSG_START);
            }
        }
        
        public void continueDownload(Mission mission){
        	if(manager.containMission(mission.getMissionID()))
        		manager.executeMission(mission);
        }
        
        public void AddDownload(Mission mission) {
            if(missionAdapter.isDownloadingRightNow(mission.getUri())){
                
            }else{
                mission.addMissionListener(new MissionListenerForNotification(DownloadService.this));
                mission.addMissionListener(new MissionSaver());
                mission.addMissionListener(missionAdapter);
                mission.addMissionListener(DownloadService.this);
                manager.addMission(mission);
            }
        }

        public BaseAdapter getMissionAdapter(){
            return missionAdapter;
        }

        public void stopMission(int MissionID){
        	manager.cancelMission(MissionID);
        }
    }

    private int count = 0;

    @Override
    public void onStart(Mission mission) {
        count++;
    }

    @Override
    public void onMetaDataPrepared(Mission mission) {

    }

    @Override
    public void onPercentageChange(Mission mission) {

    }

    @Override
    public void onSpeedChange(Mission mission) {

    }

    @Override
    public void onError(Mission mission, Exception e) {

    }

    @Override
    public void onSuccess(Mission mission) {

    }

    @Override
    public void onFinish(Mission mission) {
        count--;
        if(count == 0){
            stopSelf();
        }
    }

    @Override
    public void onPause(Mission mission) {

    }

    @Override
    public void onResume(Mission mission) {

    }

    @Override
    public void onCancel(Mission mission) {

    }

	@Override
	public void onAddMission(Mission mission) {
		
		
	}

}
