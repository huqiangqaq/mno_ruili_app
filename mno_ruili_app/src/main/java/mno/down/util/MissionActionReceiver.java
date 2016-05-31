package mno.down.util;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MissionActionReceiver extends BroadcastReceiver{

    private static final String TAG  = "MissionActionReceiver";
    private static final String ID = "MISSION_ID";
    private static final String TYPE = "MISSION_TYPE";

    public enum MISSION_TYPE{
        PAUSE_MISSION,RESUME_MISSION,CANCEL_MISSION
    }

    public static PendingIntent buildReceiverPendingIntent(Context context,MISSION_TYPE type,int missionID){
        Intent intent = new Intent(context.getApplicationContext(),MissionActionReceiver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(ID,missionID);
        intent.putExtra(TYPE,type);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, FileUtil.randInt(1,1000000), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        MISSION_TYPE type =(MISSION_TYPE)intent.getExtras().getSerializable(TYPE);
        int missionID = intent.getExtras().getInt(ID);
        if(missionID == -1){
            return;
        }

        switch (type){
            case PAUSE_MISSION:
                DownloadManager.getInstance().pauseMission(missionID);
                break;
            case RESUME_MISSION:
            	DownloadManager.getInstance().resumeMission(missionID);
                break;
            case CANCEL_MISSION:
            	DownloadManager.getInstance().cancelMission(missionID);
                break;
        }
    }
}
