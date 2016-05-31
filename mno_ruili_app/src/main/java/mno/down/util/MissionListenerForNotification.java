package mno.down.util;

import java.util.Timer;
import java.util.TimerTask;

import mno.down.modal.DownloadDetail;
import mno.down.ui.DownloadActivity;


import mno.ruili_app.R;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;

public class MissionListenerForNotification implements Mission.MissionListener<Mission>{
    private Context context;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notifyBuilder;

    private PendingIntent pausePendingIntent,resumePendingIntent,cancelPendingIntent;
    private Intent contentIntent;
    private PendingIntent contentPendingIntent;

    public MissionListenerForNotification(Context context){
        this.context = context;
    }

    public final int SUCCESS = 0;
    public final int FAILED = 1;
    private Handler ShowMessageHandler;

    @Override
    public void onStart(Mission mission) {
        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        cancelPendingIntent = MissionActionReceiver.buildReceiverPendingIntent(context, MissionActionReceiver.MISSION_TYPE.CANCEL_MISSION, mission.getMissionID());
        pausePendingIntent = MissionActionReceiver.buildReceiverPendingIntent(context, MissionActionReceiver.MISSION_TYPE.PAUSE_MISSION, mission.getMissionID());
        resumePendingIntent = MissionActionReceiver.buildReceiverPendingIntent(context, MissionActionReceiver.MISSION_TYPE.RESUME_MISSION, mission.getMissionID());
        contentIntent = new Intent(context, DownloadActivity.class);
        contentPendingIntent = PendingIntent.getActivity(context,0,contentIntent,Intent.FLAG_ACTIVITY_NEW_TASK);
        notifyBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setContentTitle(mission.getShowName())
                .setContentText(context.getString(R.string.download_preparing))
                .setProgress(100,100,true)
                .setContentInfo("0%")
                //.addAction(R.drawable.ic_action_coffee,context.getString(R.string.download_pause), pausePendingIntent)
                //.addAction(R.drawable.ic_action_cancel,context.getString(R.string.download_cancel), cancelPendingIntent)
                .setContentIntent(contentPendingIntent)
                .setOngoing(true);
      //  notificationManager.notify(mission.getMissionID(),notifyBuilder.build());
        //MobclickAgent.onEvent(context,"download_start");
    }

    @Override
    public void onMetaDataPrepared(Mission mission) {
        notifyBuilder.setContentText(context.getString(R.string.download_start));
      //  notificationManager.notify(mission.getMissionID(),notifyBuilder.build());
    }


    @Override
    public void onPercentageChange(Mission mission) {
        notifyBuilder.setProgress(100, mission.getPercentage(), false);
        notifyBuilder.setContentInfo(mission.getPercentage()+"%");
     //   notificationManager.notify(mission.getMissionID(),notifyBuilder.build());
    }

    @Override
    public void onError(Mission mission, Exception e) {
        e.printStackTrace();
        //Animation animation = (Animation)mission.getExtraInformation(mission.getUri());
        notifyBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setContentTitle(mission.getShowName())
                .setContentText(context.getString(R.string.download_error))
                .setContentInfo(mission.getPercentage() + "%")
                .setContentIntent(contentPendingIntent)
                .setOngoing(false);
       // notificationManager.notify(mission.getMissionID(),notifyBuilder.build());
       // MobclickAgent.onEvent(context,"download_failed");
    }
    

    @Override
    public void onSuccess(Mission mission) {
        Intent intent = new Intent(context, DownloadActivity.class);
        DownloadDetail detail = (DownloadDetail)mission.getExtraInformation(mission.getUri());
        intent.putExtra("Detail", detail);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,Intent.FLAG_ACTIVITY_NEW_TASK);
        notifyBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_action_emo_wink)
                .setContentTitle(mission.getShowName())
                .setContentText(context.getString(R.string.download_success))
                .setProgress(100,mission.getPercentage(),false)
                .setContentIntent(pendingIntent)
                .setOngoing(false);
     //   notificationManager.notify(mission.getMissionID(), notifyBuilder.build());
       // MobclickAgent.onEvent(context,"download_success");
    }



    @Override
    public void onFinish(final Mission mission) {
        if(mission.getResultStatus() == Mission.RESULT_STATUS.SUCCESS){
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
           //         notificationManager.cancel(mission.getMissionID());
                }
            },6000);
        }
    }

    @Override
    public void onSpeedChange(Mission mission) {
        notifyBuilder.setProgress(100,mission.getPercentage(),false).setContentText(context.getString(R.string.download_speed,mission.getAccurateReadableSpeed()));
      //  notificationManager.notify(mission.getMissionID(),notifyBuilder.build());
    }

    @Override
    public void onPause(Mission mission) {
        notifyBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setContentTitle(mission.getShowName())
                .setContentText(context.getString(R.string.download_pause))
                .setProgress(100,mission.getPercentage(),false)
                .setContentInfo(mission.getPercentage() + "%")
                //.addAction(R.drawable.ic_action_rocket,context.getString(R.string.download_resume), resumePendingIntent)
                //.addAction(R.drawable.ic_action_cancel,context.getString(R.string.download_cancel), cancelPendingIntent)
                .setContentIntent(contentPendingIntent)
                .setOngoing(true);
      //  notificationManager.notify(mission.getMissionID(),notifyBuilder.build());
        
    }

    @Override
    public void onResume(Mission mission) {
        notifyBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setContentTitle(mission.getShowName())
                .setContentText(context.getString(R.string.download_downloading))
                .setProgress(100,mission.getPercentage(),false)
                .setContentInfo(mission.getPercentage() + "%")
                //.addAction(R.drawable.ic_action_coffee,context.getString(R.string.download_pause), pausePendingIntent)
                //.addAction(R.drawable.ic_action_cancel,context.getString(R.string.download_cancel), cancelPendingIntent)
                .setContentIntent(contentPendingIntent)
                .setOngoing(true);
    //    notificationManager.notify(mission.getMissionID(),notifyBuilder.build());
    }

    @Override
    public void onCancel(Mission mission) {
        notificationManager.cancel(mission.getMissionID());
    }

	@Override
	public void onAddMission(Mission mission) {
		
	}


}

