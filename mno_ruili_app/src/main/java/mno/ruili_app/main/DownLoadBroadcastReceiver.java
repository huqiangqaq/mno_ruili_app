package mno.ruili_app.main;

import mno.ruili_app.PassMgr;
import mno.ruili_app.ct.MessageBox;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

public class DownLoadBroadcastReceiver extends BroadcastReceiver{

    public void onReceive(Context context, Intent intent) {
    	try
    	{
        long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

      

        long refernece = PassMgr.getdownload_id();

        if (refernece == myDwonloadID) {

            String serviceString = Context.DOWNLOAD_SERVICE;

            DownloadManager dManager = (DownloadManager) context.getSystemService(serviceString);

            Intent install = new Intent(Intent.ACTION_VIEW);

            Uri downloadFileUri = dManager.getUriForDownloadedFile(myDwonloadID);

            install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");

            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(install);        }
    	} catch (Exception e) {
	        MessageBox.Show(context, "更新失败");
	    }
    }

}
