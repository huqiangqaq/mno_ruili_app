package mno.down.util;

import java.io.File;
import java.util.List;

import mno.down.adapter.DownloadAdapter;
import mno.down.modal.DownloadDetail;
import mno.down.modal.DownloadRecord;
import mno.down.service.DownloadService;
import mno.down.service.DownloadService.DownloadServiceBinder;
import mno.ruili_app.R;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import com.activeandroid.query.Select;


public class DownloadHelper {

	private Context mContext;
	private DownloadServiceBinder mDownloadServiceBinder;
	private Boolean isConnected = false;
	private Object o = new Object();

	public DownloadHelper(Context context) {
		mContext = context;
	}

	private ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mDownloadServiceBinder = (DownloadService.DownloadServiceBinder) service;
			synchronized (o) {
				isConnected = true;
				o.notify();
			}
		}
		@Override
		public void onServiceDisconnected(ComponentName name) {
			isConnected = false;
		}
	};

	public void startDownload(final DownloadDetail detail) {
		final DownloadRecord record = new Select().from(DownloadRecord.class)
				.where("DownloadId = ? ", detail.VideoId).executeSingle();
		if (record != null) {
			if (record.Status == DownloadRecord.STATUS.SUCCESS) {
				new AlertDialog.Builder(mContext)
						.setTitle(R.string.tip)
						.setMessage(R.string.redownload_tips)
						.setPositiveButton(R.string.yes,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										File file = new File(record.SaveDir
												+ record.SaveFileName);
										if (file.exists() && file.isFile()) {
											file.delete();
										}
										DownloadRecord.deleteOne(detail);
										//unbindDownloadService();
										safeDownload(detail);
									}
								})
						.setNegativeButton(R.string.no,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}
								}).create().show();
			} else if (record.Status == DownloadRecord.STATUS.PAUSE) { // 继续上次下载
				safeDownload(record);
			} else {
				safeDownload(detail);
			}
		} else { // 新建下载
			safeDownload(detail);
		}
	}

	private void safeDownload(final DownloadRecord record) {
		if (NetworkUtils.isNetworkAvailable(mContext)) {
			if (NetworkUtils.isWifiConnected(mContext)) {
				download(record);
			} else {
				new AlertDialog.Builder(mContext)
						.setTitle(R.string.tip)
						.setMessage(R.string.no_wifi_download)
						.setPositiveButton(R.string.yes,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										download(record);
									}
								}).setNegativeButton(R.string.no, null)
						.create().show();
			}
		} else {
			Toast.makeText(mContext, R.string.no_network, Toast.LENGTH_LONG)
					.show();
		}
	}

	private void safeDownload(final DownloadDetail detail) {
		if (NetworkUtils.isNetworkAvailable(mContext)) {
			if (NetworkUtils.isWifiConnected(mContext)) {
				download(detail);
			} else {
				new AlertDialog.Builder(mContext)
						.setTitle(R.string.tip)
						.setMessage(R.string.no_wifi_download)
						.setPositiveButton(R.string.yes,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										download(detail);
									}
								}).setNegativeButton(R.string.no, null)
						.create().show();
			}
		} else {
			Toast.makeText(mContext, R.string.no_network, Toast.LENGTH_LONG)
					.show();
		}
	}

	public void download(final Mission mission) {
		new Thread() {
			@Override
			public void run() {
				super.run();
				if (!isDownloadServiceRunning()) {
					mContext.startService(new Intent(mContext,
							DownloadService.class));
				}
				if (mDownloadServiceBinder == null || isConnected == false) {
					mContext.bindService(new Intent(mContext,
							DownloadService.class), connection,
							Context.BIND_AUTO_CREATE);
					synchronized (o) {
						while (!isConnected) {
							try {
								o.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
				mDownloadServiceBinder.continueDownload(mission);
			}
		}.start();
	}

	public void download(final DownloadRecord record) {
		new Thread() {
			@Override
			public void run() {
				super.run();
				if (!isDownloadServiceRunning()) {
					mContext.startService(new Intent(mContext,
							DownloadService.class));
				}
				if (mDownloadServiceBinder == null || isConnected == false) {
					mContext.bindService(new Intent(mContext,
							DownloadService.class), connection,
							Context.BIND_AUTO_CREATE);
					synchronized (o) {
						while (!isConnected) {
							try {
								o.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
				File file = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
				Mission mission = new Mission(record.DownloadUrl,
						file.getAbsolutePath() + "/Download/", record.FileName
								+ "." + record.Extension, record.DownloadedSize,record.FileSize);
				mission.addExtraInformation(mission.getUri(),
						DownloadRecord.getDownloadDetail(record));
				mDownloadServiceBinder.startDownload(mission);
			}
		}.start();
	}

	public void AddDownload(final DownloadRecord record) {
		new Thread() {
			@Override
			public void run() {
				super.run();
				if (!isDownloadServiceRunning()) {
					mContext.startService(new Intent(mContext,
							DownloadService.class));
				}
				if (mDownloadServiceBinder == null || isConnected == false) {
					mContext.bindService(new Intent(mContext,
							DownloadService.class), connection,
							Context.BIND_AUTO_CREATE);
					synchronized (o) {
						while (!isConnected) {
							try {
								o.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
				File file = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
				Mission mission = new Mission(record.DownloadUrl,
						file.getAbsolutePath() + "/Download/", record.FileName
								+ "." + record.Extension, record.DownloadedSize,record.FileSize);
				mission.addExtraInformation(mission.getUri(),
						DownloadRecord.getDownloadDetail(record));
				mDownloadServiceBinder.startDownload(mission);
			}
		}.start();
	}
	
	public void addDownloadMission(final List<DownloadRecord> records) {
		new Thread() {
			@Override
			public void run() {
				super.run();
				if (!isDownloadServiceRunning()) {
					mContext.startService(new Intent(mContext,
							DownloadService.class));
				}
				if (mDownloadServiceBinder == null || isConnected == false) {
					mContext.bindService(new Intent(mContext,
							DownloadService.class), connection,
							Context.BIND_AUTO_CREATE);
					synchronized (o) {
						while (!isConnected) {
							try {
								o.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
				File file = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
				file.mkdirs();
				for (DownloadRecord record : records) {
					Mission mission = new Mission(record.DownloadUrl,
							file.getAbsolutePath() + "/Download/",
							record.FileName + "." + record.Extension,
							record.DownloadedSize,record.FileSize);
					mission.addExtraInformation(mission.getUri(),
							DownloadRecord.getDownloadDetail(record));
					mDownloadServiceBinder.AddDownload(mission);
				}
			}
		}.start();
	}

	public void addDownloadMission(final DownloadRecord record) {
		new Thread() {
			@Override
			public void run() {
				super.run();
				if (!isDownloadServiceRunning()) {
					mContext.startService(new Intent(mContext,
							DownloadService.class));
				}
				if (mDownloadServiceBinder == null || isConnected == false) {
					mContext.bindService(new Intent(mContext,
							DownloadService.class), connection,
							Context.BIND_AUTO_CREATE);
					synchronized (o) {
						while (!isConnected) {
							try {
								o.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
				File file = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
				file.mkdirs();
				Mission mission = new Mission(record.DownloadUrl,
						file.getAbsolutePath() + "/Download/", record.FileName
								+ "." + record.Extension, record.DownloadedSize,record.FileSize);
				mission.addExtraInformation(mission.getUri(),
						DownloadRecord.getDownloadDetail(record));
				mDownloadServiceBinder.AddDownload(mission);
			}
		}.start();
	}

	private void download(final DownloadDetail detail) {
		new Thread() {
			@Override
			public void run() {
				super.run();
				if (!isDownloadServiceRunning()) {
					mContext.startService(new Intent(mContext,
							DownloadService.class));
				}
				if (mDownloadServiceBinder == null || isConnected == false) {
					mContext.bindService(new Intent(mContext,
							DownloadService.class), connection,
							Context.BIND_AUTO_CREATE);
					synchronized (o) {
						while (!isConnected) {
							try {
								o.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
				File file = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
				file.mkdirs();
				Mission mission = new Mission(detail.DownloadUrl,
						file.getAbsolutePath() + "/Download/", detail.FileName
								+ "." + detail.Extension);
				mission.addExtraInformation(mission.getUri(), detail);
				mDownloadServiceBinder.startDownload(mission);
			}
		}.start();
	}

	/*
	 * 判断下载服务是否在运行
	 */
	private boolean isDownloadServiceRunning() {
		ActivityManager manager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (DownloadService.class.getName().equals(
					service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	public void unbindDownloadService() {
		if (isDownloadServiceRunning() && isConnected && connection != null) {
			mContext.unbindService(connection);
		}
	}

}
