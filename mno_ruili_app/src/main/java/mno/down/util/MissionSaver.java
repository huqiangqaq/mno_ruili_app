package mno.down.util;

import mno.down.modal.DownloadDetail;
import mno.down.modal.DownloadRecord;

public class MissionSaver implements Mission.MissionListener<Mission>{

    private DownloadDetail getDownloadDetail(Mission mission){
        Object obj = mission.getExtraInformation(mission.getUri());
        DownloadDetail detail = null;
        if(obj!=null)
        	detail = (DownloadDetail)obj;
        return detail;
    }

    private void save(Mission mission){
    	DownloadDetail detail = getDownloadDetail(mission);
        if(detail!=null){
            DownloadRecord.save(detail,mission);
        }
    }
    
    private void delete(Mission mission){
    	DownloadDetail detail = getDownloadDetail(mission);
        if(detail!=null){
            DownloadRecord.deleteOne(detail);
        }
    }

    @Override
    public void onStart(Mission mission) {
    	save(mission);
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
        save(mission);
    }

    @Override
    public void onSuccess(Mission mission) {
        save(mission);
    }

    @Override
    public void onFinish(Mission mission) {
    	
    }

    @Override
    public void onPause(Mission mission) {
    	
    }

    @Override
    public void onResume(Mission mission) {

    }

    /*
     * 此处的onCancel，目的是暂停下载任务退出当前线程，并不是取消下载，此时下载进度会被保存起来
     */
    @Override
    public void onCancel(Mission mission) {
    	save(mission);
    }

	@Override
	public void onAddMission(Mission mission) {
 
	}
}
