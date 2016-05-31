package mno.down.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Mission implements Runnable {

	public static int ID = 0;

	private final int MissionID;
	private String Uri;
	private String SaveDir;
	private String SaveName;

	protected long mDownloaded;
	protected long mFileSize;

	private String mOriginFilename;
	private String mExtension;
	private String mShowName;
	private long mPreviousNotifyTime;

	private int mProgressNotifyInterval = 1;
	private TimeUnit mTimeUnit = TimeUnit.SECONDS;

	private long mLastSecondDownloadTime = 0;
	private long mLastSecondDownload = 0;

	private int mPreviousPercentage = -1;

	protected long mStartTime;
	protected long mEndTime;

	public enum RESULT_STATUS {
		STILL_DOWNLOADING, SUCCESS, FAILED
	}

	private RESULT_STATUS mResultStatus = RESULT_STATUS.STILL_DOWNLOADING;

	private boolean isContinuing = false;
	private boolean isDone = false;
	private boolean isPaused = false;
	private boolean isSuccess = false;
	private boolean isCanceled = false;
	private final Object o = new Object();

	private List<MissionListener<Mission>> missionListeners;
	private HashMap<String, Object> extras;

	/**
	 * 
	 * @param uri
	 *            the file url you want to download
	 * @param saveDir
	 *            save to which directory
	 */
	public Mission(String uri, String saveDir,long downloadedNum,long fileSize) {
		MissionID = ID++;
		Uri = uri;
		SaveDir = saveDir;
        this.mFileSize = fileSize;
		mStartTime = System.currentTimeMillis();
		mPreviousNotifyTime = mStartTime;
		setOriginFileName(FileUtil.getFileName(uri));
		setExtension(FileUtil.getFileExtension(uri));
		SaveName = getOriginFileName() + "." + getExtension();
		missionListeners = new ArrayList<MissionListener<Mission>>();
		extras = new HashMap<String, Object>();
		mDownloaded = downloadedNum;
	}

	/**
	 * 
	 * @param uri
	 *            the file url you want to download
	 * @param saveDir
	 *            save to which directory
	 * @param saveFilename
	 *            the file name you want to save as
	 */
	public Mission(String uri, String saveDir, String saveFileName) {
		this(uri, saveDir,0,0);
		SaveName = getSafeFilename(saveFileName);
	}
	
	public Mission(String uri,String saveDir,String saveFileName,long downloadedNum,long fileSize){
		this(uri,saveDir,downloadedNum,fileSize);
		SaveName = getSafeFilename(saveFileName);
	}

	public Mission(String uri, String saveDir, String saveFilename,
			String showName) {
		this(uri, saveDir, saveFilename);
		this.mShowName = showName;
	}
    
	//注册回调接口
	public void addMissionListener(MissionListener<Mission> listener) {
		missionListeners.add(listener);
	}

	public void removeMissionListener(MissionListener<Mission> listener) {
		missionListeners.remove(listener);
	}

	public void setProgressNotificateInterval(TimeUnit unit, int interval) {
		mTimeUnit = unit;
		mProgressNotifyInterval = interval;
	}

	@Override
	public void run() {
		notifyStart();
		InputStream in = null;
		RandomAccessFile fos = null;
		HttpURLConnection httpURLConnection = null;
		try {
			httpURLConnection = (HttpURLConnection) new URL(Uri)
					.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setRequestProperty("Range", "bytes=" + mDownloaded+"-");
			//当文件没有开始下载，记录文件大小
			if(mFileSize <= 0)
				setFileSize(httpURLConnection.getContentLength());
			in = httpURLConnection.getInputStream();
			File accessFile = getSafeFile(getSaveDir(), getSaveName());
			fos = new RandomAccessFile(accessFile, "rw");
			fos.seek(mDownloaded);
			byte data[] = new byte[1024];
			int count;
			notifyMetaDataReady();
			while (!isCanceled() && (count = in.read(data, 0, 1024)) != -1) {
					fos.write(data, 0, count);
					mDownloaded += count;
					notifyPercentageChange();
					notifySpeedChange();
					checkPaused();
			}
			if (isCanceled == false) {
				notifyPercentageChange();
				notifySuccess();
			} else {
				notifyCancel();
			}
		} catch (Exception e) {
			notifyError(e);
		} finally {
			try {
				if (in != null)
					in.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				notifyError(e);
			}
			notifyFinish();
		}
	}

	protected FileOutputStream getSafeOutputStream(String directory,
			String filename) {
		String filepath;
		if (directory.lastIndexOf(File.separator) != directory.length() - 1) {
			directory += File.separator;
		}
		File dir = new File(directory);
		dir.mkdirs();
		filepath = directory + filename;
		File file = new File(filepath);
		try {
			file.createNewFile();
			return new FileOutputStream(file.getCanonicalFile().toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error("Can not get an valid output stream");
		}
	}

	protected File getSafeFile(String directory, String filename) {
		String filepath;
		if (directory.lastIndexOf(File.separator) != directory.length() - 1) {
			directory += File.separator;
		}
		File dir = new File(directory);
		dir.mkdirs();
		filepath = directory + filename;
		File file = new File(filepath);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Error("Can not Create A New File");
		}
		return file;
	}

	protected String getSafeFilename(String name) {
		return name.replaceAll("[\\\\|\\/|\\:|\\*|\\?|\\\"|\\<|\\>|\\|]", "-");
	}

	protected void checkPaused() {
		synchronized (o) {
			while (isPaused) {
				try {
					notifyPause();
					o.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	public void pause() {
		if (isPaused() || isDone())
			return;
		isPaused = true;
	}

	public void resume() {
		if (!isPaused() || isDone()) {
			return;
		}
		synchronized (o) {
			isPaused = false;
			o.notifyAll();
		}
		notifyResume();
	}

	public void cancel() {
		isCanceled = true;
		if (isPaused()) {
			resume();
		}
	}
    
	//通知进度改变
	protected final void notifyPercentageChange() {
		if (missionListeners != null && missionListeners.size() != 0) {
			int currentPercentage = getPercentage();
			if (mPreviousPercentage != currentPercentage) {
				for (MissionListener<Mission> l : missionListeners) {
					l.onPercentageChange(this);
				}
				mPreviousPercentage = currentPercentage;
			}
		}
	}

	//通知下载速度更新
	protected final void notifySpeedChange() {
		if (missionListeners != null && missionListeners.size() != 0) {
			long currentNotifyTime = System.currentTimeMillis();
			long notifyDuration = currentNotifyTime - mPreviousNotifyTime;
			long spend = mTimeUnit.convert(notifyDuration,
					TimeUnit.MILLISECONDS);
			if (spend >= mProgressNotifyInterval) {
				mPreviousNotifyTime = currentNotifyTime;
				for (MissionListener<Mission> l : missionListeners) {
					l.onSpeedChange(this);
				}
			}
			long speedRecordDuration = currentNotifyTime
					- mLastSecondDownloadTime;
			if (TimeUnit.MILLISECONDS.toSeconds(speedRecordDuration) >= 1) {
				mLastSecondDownloadTime = currentNotifyTime;
				mLastSecondDownload = getDownloaded();
			}
		}
	}

	protected final void notifyAdd() {
		if (missionListeners != null && missionListeners.size() != 0) {
			for (MissionListener<Mission> l : missionListeners) {
				l.onAddMission(this);
			}
		}
	}

	protected final void notifyStart() {
		isContinuing = true;
		if (missionListeners != null && missionListeners.size() != 0) {
			for (MissionListener<Mission> l : missionListeners) {
				l.onStart(this);
			}
		}
	}

	protected final void notifyPause() {
		if (missionListeners != null && missionListeners.size() != 0) {
			for (MissionListener<Mission> l : missionListeners) {
				l.onPause(this);
			}
		}
	}

	protected final void notifyResume() {
		if (missionListeners != null && missionListeners.size() != 0) {
			for (MissionListener<Mission> l : missionListeners) {
				l.onResume(this);
			}
		}
	}

	protected final void notifyCancel() {
		isContinuing = false; //取消被通知到的时候，isContinuing为fasle
		if (missionListeners != null && missionListeners.size() != 0) {
			for (MissionListener<Mission> l : missionListeners) {
				l.onCancel(this);
			}
		}
	}

	protected final void notifyMetaDataReady() {
		if (missionListeners != null && missionListeners.size() != 0) {
			for (MissionListener<Mission> l : missionListeners) {
				l.onMetaDataPrepared(this);
			}
		}
	}

	protected final void notifySuccess() {
		mResultStatus = RESULT_STATUS.SUCCESS;
		isDone = true;
		isSuccess = true;
		if (missionListeners != null && missionListeners.size() != 0) {
			for (MissionListener<Mission> l : missionListeners) {
				l.onSuccess(this);
			}
		}
	}

	protected final void notifyError(Exception e) {
		mResultStatus = RESULT_STATUS.FAILED;
		isDone = true;
		isSuccess = false;
		if (missionListeners != null && missionListeners.size() != 0) {
			for (MissionListener<Mission> l : missionListeners) {
				l.onError(this, e);
				String i=e.getMessage().toString();
				i=i+"";
			}
		}
	}

	protected final void notifyFinish() {
		isContinuing = false;
		mEndTime = System.currentTimeMillis();
		if (missionListeners != null && missionListeners.size() != 0) {
			for (MissionListener<Mission> l : missionListeners) {
				l.onFinish(this);
			}
		}
	}

	public String getUri() {
		return Uri;
	}

	public String getSaveDir() {
		return SaveDir;
	}

	public String getSaveName() {
		return SaveName;
	}

	public long getDownloaded() {
		return mDownloaded;
	}

	public long getFilesize() {
		return mFileSize;
	}

	protected void setFileSize(int size) {
		mFileSize = size;
	}

	public long getTimeSpend() {
		if (mEndTime != 0) {
			return mEndTime - mStartTime;
		} else {
			return System.currentTimeMillis() - mStartTime;
		}
	}

	public String getAverageReadableSpeed() {
		return FileUtil.getReadableSpeed(getDownloaded(), getTimeSpend(),
				TimeUnit.MILLISECONDS);
	}

	public String getAccurateReadableSpeed() {
		return FileUtil.getReadableSize(getDownloaded() - mLastSecondDownload,
				false) + "/s";
	}

	public int getPercentage() {
		if (mFileSize == 0) {
			return 0;
		} else {
			return (int) (mDownloaded * 100.0f / mFileSize);
		}
	}

	public String getAccurateReadableSize() {
		return FileUtil.getReadableSize(getDownloaded());
	}

	public String getReadableSize() {
		return FileUtil.getReadableSize(getFilesize());
	}

	public String getReadablePercentage() {
		StringBuilder builder = new StringBuilder();
		builder.append(getPercentage());
		builder.append("%");
		return builder.toString();
	}

	public void setOriginFileName(String name) {
		mOriginFilename = name;
	}

	public String getOriginFileName() {
		return mOriginFilename;
	}

	public void setExtension(String extension) {
		mExtension = extension;
	}

	public String getExtension() {
		return mExtension;
	}

	public String getShowName() {
		if (mShowName == null || mShowName.length() == 0) {
			return getSaveName();
		} else {
			return mShowName;
		}
	}

	public int getMissionID() {
		return MissionID;
	}

	public boolean isDownloading() {
		return isContinuing;
	}

	public boolean isDone() {
		return isDone;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public boolean isPaused() {
		return isPaused;
	}
	/*ck*/
	public void setDownloading(boolean isDownloading){
		this.isContinuing = isDownloading;
	}
	public void setCanceled(boolean isCanceled){
		this.isCanceled = isCanceled;
	}

	public boolean isCanceled() {
		return isCanceled;
	}

	public RESULT_STATUS getResultStatus() {
		return mResultStatus;
	}

	public void addExtraInformation(String key, Object value) {
		extras.put(key, value);
	}

	public void removeExtraInformation(String key) {
		extras.remove(key);
	}

	public Object getExtraInformation(String key) {
		return extras.get(key);
	}

	public interface MissionListener<T extends Mission> {

		public void onAddMission(T mission);

		public void onStart(T mission);

		public void onMetaDataPrepared(T mission);

		public void onPercentageChange(T mission);

		public void onSpeedChange(T mission);

		public void onError(T mission, Exception e);

		public void onSuccess(T mission);

		public void onFinish(T mission);

		public void onPause(T mission);

		public void onResume(T mission);

		public void onCancel(T mission);
	}

}
