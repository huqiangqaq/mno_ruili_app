package mno_ruili_app.home;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;

public class AlertUtil {
	public static AlertDialog showDialog(Context context, String msg,
			OnClickListener okL, OnClickListener cancelL) {
		Builder builder = new Builder(context).setMessage(msg);
		if (okL != null) {
			builder.setPositiveButton("确定", okL);
		}
		if (cancelL != null) {
			builder.setNegativeButton("取消", cancelL);
		}
		AlertDialog dialog = builder.create();
		dialog.show();
		return dialog;
	}

	public static void toast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}
