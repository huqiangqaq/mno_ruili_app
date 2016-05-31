package mno.down.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;

public class DownloadManager {
	private final String TAG = "DownLoadManager";
    private static int MAX_MISSION_COUNT = 2; //设置线程池最大线程个数
    private static DownloadManager Instance;
    protected ThreadPoolExecutor mExecutorService;
    protected HashMap<Integer,Mission> mMissionBook;

    public static synchronized DownloadManager getInstance(){
        if(Instance == null || Instance.mExecutorService.isShutdown()){
            Instance = new DownloadManager();
        }
        return Instance;
    }

    private DownloadManager(){
        mMissionBook = new HashMap<Integer, Mission>();
        mExecutorService = new ThreadPoolExecutor(MAX_MISSION_COUNT,MAX_MISSION_COUNT,15,TimeUnit.SECONDS,
        		new LinkedBlockingDeque<Runnable>());
    }
    
    public boolean containMission(int missionID){
    	return mMissionBook.containsKey(missionID);
    }

    public void addMission(Mission mission){
        mMissionBook.put(mission.getMissionID(), mission);
        mission.notifyAdd();
    }
    
    public void executeMission(Mission mission){
        if(mission.isCanceled())
        	mission.setCanceled(false);
        mExecutorService.execute(mission);

    }
    public void executeMission(int missionID){
    	try 
    	{
    		
    	if(mMissionBook.containsKey(missionID)){
    		Mission mission=mMissionBook.get(missionID);
    		if(!mission.isSuccess())
    		{
    			if(mission.isCanceled())
    			 mission.setCanceled(false);
    			 mExecutorService.execute(mission);
    		}
    		
        }
    /*	if(mMissionBook.containsKey(1)){
    		Mission mission=mMissionBook.get(1);
    		if(!mission.isSuccess())
    		{
    		mission.setCanceled(false);
   		    mExecutorService.execute(mission);
    		}
        }*/
    	} catch (Exception e) {
    		
    	}
       

    }
    public void pauseMission(int missionID){
        if(mMissionBook.containsKey(missionID)){
            mMissionBook.get(missionID).pause();
        }
    }

    public void resumeMission(int missionID){
        if(mMissionBook.containsKey(missionID)){
            mMissionBook.get(missionID).resume();
        }
    }

    public void cancelMission(int missionID){
        if(mMissionBook.containsKey(missionID)){
            mMissionBook.get(missionID).cancel();
        }
    }
    
    /*
     * 停止所有任务
     */
    public void surrenderMissions(){
        for(Map.Entry<Integer, Mission> mission : mMissionBook.entrySet()){
        	mission.getValue().cancel();
            mMissionBook.get(mission).cancel();
        }
    }
    DownloadHelper downloadHelper;
    public void StatMissions(){
        for(Map.Entry<Integer, Mission> mission : mMissionBook.entrySet()){
        	//mission.getValue().cancel();
           // mMissionBook.get(mission).cancel();
        	downloadHelper .download(mission.getValue());
        	downloadHelper .download(mMissionBook.get(mission));
        }
    }

    public static void setMaxMissionCount(int count){
        if(Instance == null && count > 0)
            MAX_MISSION_COUNT = count;
        else
            throw new IllegalStateException("Can not change max mission count after getInstance been called");
    }


}
