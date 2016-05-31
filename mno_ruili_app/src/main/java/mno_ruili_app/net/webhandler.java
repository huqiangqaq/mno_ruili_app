package mno_ruili_app.net;

import mno.ruili_app.ct.LoadingDialog;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.my.my_findpwd;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;


public class webhandler extends webpost {
	
	Dialog dialog_;
	 
	Context context_;
	 public void SetProgressDialog(Context context)
	 {
		 context_ = context;
		 dialog_ = LoadingDialog.createLoadingDialog(context, "正在加载中...");
	 }

	@Override
	public void OnStart() {
		// TODO Auto-generated method stub
		if(dialog_ != null)
    	{
    		dialog_.show();  
    	}
	}

	@Override
	public void OnResponse(JSONObject response) {
		// TODO Auto-generated method stub
		 if(context_ == null)
			 return;
		
		try {
			String mess = response.getString("message");
			
			if(context_ == null)
				return;
			
			if(mess.equals("null") == false && mess.length() > 1)
			{
				Toast.makeText(context_, mess, Toast.LENGTH_SHORT).show();
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	@Override
	public void OnError(int code, String mess) {
		// TODO Auto-generated method stub
		
		 if(context_ == null)
			 return;
		
		if(context_.getClass()==my_findpwd.class)
		{
			if(mess.equals("用户已存在"))
				return;
				MessageBox.Show(context_,mess);
		}
		else
			MessageBox.Show(context_,mess);
			
	}

	@Override
	public void OnFinish() {
		// TODO Auto-generated method stub
		if(dialog_ != null)
    	{
    		try {
    			
				dialog_.cancel();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
	}

}
