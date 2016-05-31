package mno.ruili_app;

import mno.ruili_app.main.Main;
import mno.ruili_app.my.mylogin;
import mno_ruili_app.net.loginhandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.CheckBox;
import android.widget.Toast;

public  class PassMgr 
{
	
	static Context context_;

	private static  SharedPreferences sp; 
	static String pass_,id,idpass;
	static String user_name_;
	static boolean check_;//是否记住密码
	static boolean  Auto_;
	static boolean  isxw;//推送通知里的新闻
	static boolean  iszb,Islogin;//推送通知里的直播 , //是否自动登录
	static boolean  platform;
	static long download_id;
	
	public static  void init(Context context){
		context_ = context;
		
         sp = context.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);  
		  //1.
		  if(sp.getBoolean("check",false))
		  {
			    check_ = true;
				pass_ = sp.getString("pass","");
				user_name_ = sp.getString("user","");
		  }else{
			  check_ = false;
			  user_name_ = sp.getString("user","");
		  }
		  //2.		  		  
		  if(sp.getBoolean("auto", true))
		  {
			  Auto_ = true;
		  }else{
			  Auto_ = false;
		  }
		  //3.
		  if(sp.getBoolean("isxw", true))
		  {
			  isxw = true;
		  }else{
			  isxw = false;
		  }
		  //4.
		  if(sp.getBoolean("iszb", true))
		  {
			  iszb = true;
		  }else{
			  iszb = false;
		  }
		  //5.
		  if(sp.getBoolean("Islogin", false))
		  {
			  Islogin = true;
			try {
			    JSONObject response= new JSONObject(sp.getString("response","").toString());
			
				appuser.getInstance().OnLoginSuccess(response);
				Constant.uid=sp.getString("id","");
				Constant.acc=sp.getString("idpass","");
				id = sp.getString("id","");
				idpass = sp.getString("idpass","");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		  }else{
			  Islogin = false;
		  }
		  //6.
		  if(sp.getBoolean("platform", true))
		  {
			  platform = true;
		  }else{
			  platform= false;
		  }
	}
	//1.
	public static void login(){		
		  if(PassMgr.IsAuto() == true && PassMgr.IsSave() == true)
			{
			  loginhandler h = new loginhandler(){

					@Override
					public void OnLoginSuccess(JSONObject response) {
						// TODO Auto-generated method stub
						///Toast.makeText(context_, "欢迎回来。。", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						intent.setClass(context_, Main.class);
						context_.startActivity(intent);
						
					}

				};
					
					h.Login(context_, PassMgr.getName(),PassMgr.getPass());
			}
		  else
		  {
				if( !appuser.getInstance().IsLogin())
		    	{
					Intent intent = new Intent();
					intent.setClass(context_, mylogin.class);
					context_.startActivity(intent);
		    	
		    	}
		  }
	}
	//2.
	public static void Save(String name,String pass){
		
		 Editor editor = sp.edit();  
		 if(name !=null && pass !=null)
			{
				editor.putString("user",name);  
				editor.putString("pass",pass);  
				editor.putBoolean("check", true);
				editor.commit();  
				check_ = true;
			}
		 else
		 {
			    editor.putString("user","");  
				editor.putString("pass","");  
				editor.putBoolean("check",false);
				editor.commit();  
				
				check_ = false;
				pass_ = null;
				user_name_ = null;
		 }
	}
	//3.
	public static void Save(String name,String pass,boolean save){
		
		 Editor editor = sp.edit();  
		 if(name !=null && pass !=null&&save){
			editor.putString("user",name);  
			editor.putString("pass",pass);  
			editor.putBoolean("check", true);
			editor.commit();  
			check_ = true;
			pass_ = sp.getString("pass","");
			user_name_ = sp.getString("user","");
		}
		 else{
			    editor.putString("user",name);  
				editor.putString("pass","");  
				editor.putBoolean("check",false);
				editor.commit();  
				
				check_ = false;
				pass_ = null;
				user_name_ = name;
		 }
	}
	//4.
	public static  boolean IsSave(){
		return check_;
	}
	//5.	
	public static String getName()
	{
		return user_name_;
	}
	//6.
	public static String getPass()
	{
		return pass_;
	}	
	//7.
   public static void IsAuto(boolean is_auto)
   {
	   Editor editor = sp.edit();  
	   editor.putBoolean("auto", is_auto);
	   editor.commit();  
	   Auto_ = is_auto;
   }
   //8.
   public static void Isxw(boolean is_auto)
   {
	   Editor editor = sp.edit();  
	   editor.putBoolean("xw", is_auto);
	   editor.commit();  
	   isxw = is_auto;
   }
   //9.
   public static void Iszb(boolean is_auto)
   {
	   Editor editor = sp.edit();  
	   editor.putBoolean("zb", is_auto);
	   editor.commit();  
	   iszb = is_auto;
   }
   //10.
   public static void Isplatform(boolean is_auto)
   {
	   Editor editor = sp.edit();  
	   editor.putBoolean("platform", is_auto);
	   editor.commit();  
	   platform = is_auto;
   }
   //1.这里的response是用户登陆功能接口中解析"data"后的字符串
   public static void Islogin(boolean is_auto,String response,String name,String pass)
   {
	   Editor editor = sp.edit();  
	   editor.putString("id",name);  
	   editor.putString("idpass",pass);  
	   editor.putString("response",response);  
	   editor.putBoolean("Islogin", is_auto);
	   editor.commit();  
	   Islogin = is_auto;
	   id = sp.getString("id","");
	   idpass = sp.getString("idpass","");
   }
   public static boolean Iszb()
   {
	   return iszb;
   }
   public static boolean Isxw()
   {
	   return isxw;
   }
   //平台用户
   public static boolean Isplatform()
   {
	   return platform;
   }
   public static boolean IsAuto()
   {
	   return Auto_;
   }
   public static boolean Islogin()
   {
	   return Islogin;
   }
	public static void Save(long id) {
		// TODO Auto-generated method stub
		 Editor editor = sp.edit();  
		 {
			    editor.putLong("download_id",id);  
				
				editor.commit();  
			    download_id = id;
		 }
	}
	public static long getdownload_id()
	{
		return download_id;
	}

}
