package mno_ruili_app.net;

import java.util.HashMap;
import java.util.Map;

import mno.ruili_app.appuser;
import mno_ruili_app.net.RequestType.Type;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public abstract class getlisthandler extends webhandler{
	String pagesize_;
	String page_;
	
	public void getlist(Context context ,String pagesize ,String page,String way,Type typ)
	{
		//String code =accessCode(account);
		context_ = context;
		Map<String, String> params = new HashMap<String, String>();
		
		pagesize_ = pagesize;
		page_ = page;
		
        params.put("pagesize",pagesize_);
        params.put("page",page_);		
		this.SetRequest(new RequestType(way,typ),params);
		
	}
	public void getlist(Context context ,String label,String typeId,String pagesize ,String page,String way,Type typ){
		//String code =accessCode(account);
		context_ = context;
		Map<String, String> params = new HashMap<String, String>();
		
		pagesize_ = pagesize;
		page_ = page;
		params.put("label",label);
		params.put("typeId",typeId);
	    params.put("pagesize",pagesize_);
	    params.put("page",page_);
			
		this.SetRequest(new RequestType(way,typ),params);
		
	}

	@Override
	public void OnResponse(JSONObject response) {
		// TODO Auto-generated method stub
		super.OnResponse(response);		
		try {
						
			JSONObject data = response.getJSONObject("data");
			JSONObject json = null;						
			OnSuccess(data);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			this.OnError(-1, e.getMessage());
		}
			
	}
	public abstract void  OnSuccess(JSONObject response);
}
