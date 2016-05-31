package mno.ruili_app;

import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.my.mylogin;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

public class appuser {
	
	private static appuser AppUser_;
	private userinfo user_info_;
	private  boolean IsLogin_;
	
	String accessCode = "";
	public static appuser getInstance()
	{
		if( AppUser_ == null)
		{
			AppUser_ = new appuser();
		}
		
		return AppUser_;
	}
	private appuser()
	{
		IsLogin_ = false;		
	}
	public void OnLoginSuccess(JSONObject response) throws JSONException
	{
		String str = "ruili"+response.getString("uid");
		// 在这里使用的是encode方式，返回的是byte类型加密数据，可使用new String转为String类型
		String strBase64 = new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
		Log.i("Test", "encode >>>" + strBase64);

		// 这里 encodeToString 则直接将返回String类型的加密数据
		String enToStr = Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
		Log.i("Test", "encodeToString >>> " + enToStr);

		// 对base64加密后的数据进行解密
		///Log.i("Test", "decode >>>" + new String(Base64.decode(strBase64.getBytes(), Base64.DEFAULT)));
			
		accessCode=enToStr;
		IsLogin_ = true;
		if(user_info_ == null)
			user_info_ = new userinfo();
		
		//JSONObject info =  response.getJSONObject("data");
		user_info_.uid = response.getString("uid");
		Constant.uid=user_info_.uid;
		Constant.acc=accessCode;
		//把登录成功后解析得到的"data"字符串存入sp（userInfo）中
		PassMgr.Islogin(true,response.toString(), user_info_.uid, accessCode);
		user_info_.nickname = response.getString("username");
		//
		user_info_.work=response.getString("profession");
		user_info_.danwei=response.getString("shop_name");
		user_info_.platform=response.getString("platform");
		
		user_info_.phone = response.getString("phone");
		user_info_.email = response.getString("email");
		user_info_.sex=response.getString("sex");
		user_info_.signText=response.getString("signText");
		user_info_.pointTotal=response.getString("pointTotal");
		user_info_.bigImg=response.getString("bigImg");
		user_info_.smallImg=response.getString("smallImg");
		user_info_.provice=response.getString("provice");
		user_info_.city=response.getString("city");
		user_info_.collTotal=response.getString("collTotal");
		user_info_.pointTotal=response.getString("pointTotal");
		user_info_.replyTotal=response.getString("replyTotal");
		user_info_.quesTotal=response.getString("quesTotal");
		user_info_.coursePush=response.getString("coursePush");
		user_info_.newsPush=response.getString("newsPush");
		try{
			user_info_.signinPoint=response.getString("signinPoint");
		} catch (Exception e) {
       
		}
		//
		if(user_info_.coursePush.equals("1"))
			PassMgr.Iszb(true);
		else
			PassMgr.Iszb(false);
		if(user_info_.newsPush.equals("1"))
			PassMgr.Isxw(true);
		else
			PassMgr.Isxw(false);
		//判断登录的平台
		if(user_info_.platform.equals("1")){
			PassMgr.Isplatform(true);		
		}else{
			PassMgr.Isplatform(false);					
		}		
	}
	//
   public userinfo getUserinfo()
   {
	   return user_info_;
   }
   
   
   public String getaccessCode()
   {
	   return accessCode;
   }


	
	public void LogOut()
	{
		IsLogin_ = false;
		accessCode = null;
		user_info_ = null;
	}
	public void islogin()
	{
		IsLogin_ = true;
	}
	
	public   void Login(Context context)
	{
		   Intent intent = new Intent(context, mylogin.class); 		    
		   context.startActivity(intent);
	}
	
	
	public void LoginToAct(Context context,Class<? extends android.app.Activity> StartClass){		
		if(!appuser.getInstance().IsLogin()){
			MessageBox.Show(context, "该功能需要登录以后才可以使用\n请登录");
	    }
		Intent intent = new Intent(context, mylogin.class); 		
		Bundle bundle = new Bundle();  		
		bundle.putSerializable("class", StartClass);  
		intent.putExtras(bundle);		
		context.startActivity(intent);		
	}	
	
	public boolean IsLogin(){
		return IsLogin_;
	}
}
