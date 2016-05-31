package mno.down.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mno.down.modal.DownloadRecord;
import mno.down.util.DownloadHelper;
import mno.down.util.Mission;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MissionListenerForAdapter extends BaseAdapter implements Mission.MissionListener<Mission>{

	protected Context mContext;
	protected DownloadHelper downloadHelper; 
	protected List<Mission> mStartMissions = new ArrayList<Mission>();
    protected List<Mission> onGoingMissions = new ArrayList<Mission>();
    protected List<DownloadRecord> mCompletedMissions = new ArrayList<DownloadRecord>();
    
    public MissionListenerForAdapter(Context context){
    	this.mContext = context;
    	downloadHelper = new DownloadHelper(context);
        new Thread(){
            @Override
            public void run() {
                super.run();
                getPreGoingMissions();
                getGoingMissins();
                mCompletedMissions.addAll(DownloadRecord.getAllDownloaded());
                //updateUI();
            }
        }.start();
    }
    
    private void getPreGoingMissions() {
    	List<DownloadRecord> goingList =  DownloadRecord.getPreDownloading();
    	for(DownloadRecord record :goingList)
    		downloadHelper.AddDownload(record);
	}
    
    /*
     * 获取准备下载的任务
     */
    private void getGoingMissins() {
    	List<DownloadRecord> goingList =  DownloadRecord.getAllDownloading();
    		downloadHelper.addDownloadMission(goingList);
	}
    
    @Override
    public int getCount() {
        return mCompletedMissions.size() + onGoingMissions.size() + mStartMissions.size();
    }

    @Override
    public Object getItem(int position) {
        if(position < onGoingMissions.size()){
            return onGoingMissions.get(position);
        }else if(position < onGoingMissions.size() + mStartMissions.size()){
            return mStartMissions.get(position - onGoingMissions.size());
        }else{
        	return mCompletedMissions.get(position - onGoingMissions.size() - mStartMissions.size());
        }
    }

    public void removelist(int position) {
        if(position < onGoingMissions.size()){
          // onGoingMissions.remove(position);
        	onGoingMissions.get(position).cancel();
        	
            onGoingMissions.remove(onGoingMissions.get(position));

            
        }else if(position < onGoingMissions.size() + mStartMissions.size()){
        	 mStartMissions.get(position - onGoingMissions.size()).cancel();
             mStartMissions.remove(mStartMissions.get(position - onGoingMissions.size()));
   
        }else{
        	 
        	 mCompletedMissions.remove(position - onGoingMissions.size() - mStartMissions.size());
  
        }
        updateUI();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public abstract View getView(int position, View convertView, ViewGroup parent);


    @Override
	public void onAddMission(Mission mission) {
    	if(!isExistStartMission(mission.getUri())){
    		mStartMissions.add(mission);
    	}
    	updateUI();
	}

    @Override
    public synchronized void onStart(Mission mission) {
    	if(isExistStartMission(mission.getUri())){
    		mStartMissions.remove(mission);
    	}
    	if(!isExistGoingMission(mission.getUri()))
    		onGoingMissions.add(mission);
    
        List<DownloadRecord> records = new ArrayList<DownloadRecord>();
        records.addAll(DownloadRecord.getAllDownloaded());
        mCompletedMissions = records;
        updateUI();
    }

    @Override
    public void onMetaDataPrepared(Mission mission) {}

    @Override
    public void onPercentageChange(Mission mission) {
        updateUI();
    }

    @Override
    public void onSpeedChange(Mission mission) {}

    @Override
    public void onError(Mission mission, Exception e) {}

    @Override
    public synchronized void onSuccess(Mission mission) {
    	onGoingMissions.remove(mission);
        reloadData();
    }

    @Override
    public  void onFinish(Mission mission) {
        updateUI();
    }

    @Override
    public void onPause(Mission mission) {}

    @Override
    public void onResume(Mission mission) {}

    @Override
    public synchronized void onCancel(Mission mission) {
    	updateUI();
    }

    public void updateUI(){
        windTalker.sendEmptyMessage(0);
    }

    public void reloadData(){
        mCompletedMissions = DownloadRecord.getAllDownloaded();
        updateUI();
    }

    private Handler windTalker = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            notifyDataSetChanged();
        }
    };
    
    public boolean isExistStartMission(String url){
    	for(Mission mission : mStartMissions){
            if(mission.getUri().equals(url)){
                return true;
            }
        }
        return false;
    }
    
    public boolean isExistGoingMission(String url){
    	for(Mission mission : onGoingMissions){
            if(mission.getUri().equals(url)){
                return true;
            }
        }
        return false;
    }

    public boolean isDownloadingRightNow(String url){
        for(Mission mission : onGoingMissions){
            if(mission.getUri().equals(url)){
                return true;
            }
        }
        return false;
    }
}
