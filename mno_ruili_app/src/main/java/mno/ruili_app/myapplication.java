package mno.ruili_app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gensee.common.GenseeConfig;

import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.ct.phoneunit;
import mno.ruili_app.my.im.AppHelper;
import mno_ruili_app.net.webpost;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.support.multidex.MultiDex;

public class myapplication extends com.activeandroid.app.Application  {
    private String name;
    private String content;
	private String titler;
	private String id;
	private String type;
	private String token,forwhat="";
	PendingIntent restartIntent;    
	private Context appContext = null;//
//	private static myapplication instance;
//	public static myapplication getInstance() {
//		return instance;
//	}
    @Override
    public void onCreate() {
    	MultiDex.install(this);
        super.onCreate();
        GenseeConfig.isDocDataPng= true;
        appContext = getApplicationContext();
        //初始化AppHelper/初始化EaseUI
        AppHelper.getInstance().init(appContext);                                    
        //ActiveAndroid.initialize(this);
        webpost.init(this);
        PassMgr.init(this);
        setName(NAME); //初始化全局变量
        // 以下用来捕获程序崩溃异常    
        collectDeviceInfo(this);
        Intent intent = new Intent();    
        // 参数1：包名，参数2：程序入口的activity    
        intent.setClassName("mno.ruili_app", "mno.ruili_app.main.LauncherActivity");    
        restartIntent = PendingIntent.getActivity(getApplicationContext(), 0,    
                intent, Intent.FLAG_ACTIVITY_NEW_TASK);    
        Thread.setDefaultUncaughtExceptionHandler(restartHandler); // 程序崩溃时触发线程    
        String a=phoneunit.getCpuType();
        setName(a);
    }
    @Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
			MultiDex.install(this);
	}
    public UncaughtExceptionHandler restartHandler = new UncaughtExceptionHandler() {    
        public void uncaughtException(Thread thread, Throwable ex) {    
        	
        	saveCatchInfo2File(ex);
            AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);    
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,    
                    restartIntent); // 1秒钟后重启应用    
            
            new ActivityContrl().finishProgram(); // 自定义方法，关闭当前打开的所有avtivity    
        }    
    };    
    private static List<Activity> activityList = new ArrayList<Activity>();    
      
    public static class ActivityContrl {                                      
         public void remove(Activity activity) {    
             activityList.remove(activity);    
         }             
         public static void add(Activity activity) {    
             activityList.add(activity);    
         }             
        public void finishProgram() {            	
             for (Activity activity : activityList) {    
                 if (null != activity) {    
                     activity.finish();    
                 }    
             }                
             android.os.Process.killProcess(android.os.Process.myPid());  
             System.exit(0);
         }    
     } 
    public String getName() {
            return name;
    }
    public void setName(String name) {
            this.name = name;
    }
    public String gettoken() {  
	    return token;  
	} 

	public void settoken(String token) {  
	    this.token = token;  
	}
    
    public String gettype() {  
	    return type;  
	} 

	public void settype(String type) {  
	    this.type = type;  
	}
	public String getcontent() {  
	    return content;  
	} 
	public void setcontent(String content) {  
	    this.content = content;  
	}
	
	public String gettitler() {  
	    return titler;  
	} 
	public void titler(String titler) {  
	    this.titler = titler;  
	}
	public String getid() {  
	    return id;  
	} 
	public void setid(String id) {  
	    this.id = id;  
	}
	 public String getforwhat() {  
		    return forwhat;  
	} 
	public void setforwhat(String forwhat) {  
		    this.forwhat = forwhat;  
	}
	public static Map<String, Long> map;
    private static final String NAME = "MyApplication";             
    /*异常！！！！！！！！！*/    
  //用来存储设备信息和异常信息      
    private Map<String, String> infos = new HashMap<String, String>(); 
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");    
    /**   
     * 收集设备参数信息   
     * @param ctx   
     */      
    public void collectDeviceInfo(Context ctx) {      
        try {      
            PackageManager pm = ctx.getPackageManager();      
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);      
            if (pi != null) {      
                String versionName = pi.versionName == null ? "null" : pi.versionName;      
                String versionCode = pi.versionCode + "";      
                infos.put("versionName", versionName);      
                infos.put("versionCode", versionCode);      
            }      
        } catch (NameNotFoundException e) {      
            Log.e("mno", "an error occured when collect package info", e);      
        }      
        Field[] fields = Build.class.getDeclaredFields();      
        for (Field field : fields) {      
            try {      
                field.setAccessible(true);      
                infos.put(field.getName(), field.get(null).toString());      
                Log.d("mno", field.getName() + " : " + field.get(null));      
            } catch (Exception e) {      
                Log.e("mno", "an error occured when collect crash info", e);      
            }      
        }      
    }      
      
    /**   
     * 保存错误信息到文件中   
     *    
     * @param ex   
     * @return  返回文件名称,便于将文件传送到服务器   
     */      
    private String saveCatchInfo2File(Throwable ex) {   
    	new Thread() {      
            @Override      
            public void run() {      
                Looper.prepare();      
                MessageBox.Show(myapplication.this, "正在生成错误日志。。");
                Looper.loop();      
            }      
        }.start();      
    	
        StringBuffer sb = new StringBuffer();    
        sb.append("cpu------------------------------------");
        sb.append(phoneunit.getCpuString());
        sb.append("cpu------------------------------------");
        for (Map.Entry<String, String> entry : infos.entrySet()) {      
            String key = entry.getKey();      
            String value = entry.getValue();      
            sb.append(key + "=" + value + "\n");      
        }      
              
        Writer writer = new StringWriter();      
        PrintWriter printWriter = new PrintWriter(writer);      
        ex.printStackTrace(printWriter);      
        Throwable cause = ex.getCause();      
        while (cause != null) {      
            cause.printStackTrace(printWriter);      
            cause = cause.getCause();      
        }      
        printWriter.close();      
        String result = writer.toString();      
        sb.append(result);      
        try {      
            long timestamp = System.currentTimeMillis();      
            String time = formatter.format(new Date());      
            String fileName = "crash-" + time + "-" + timestamp + ".log";      
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {      
                String path = Environment.getExternalStorageDirectory()+"/crash/";      
                File dir = new File(path);      
                if (!dir.exists()) {      
                    dir.mkdirs();      
                }      
                FileOutputStream fos = new FileOutputStream(path + fileName);      
                fos.write(sb.toString().getBytes());    
                //发送给开发人员  
                //sendCrashLog2PM(path+fileName);  
                fos.close();      
            }      
            return fileName;      
        } catch (Exception e) {      
            Log.e("mno", "an error occured while writing file...", e);      
        }      
        return null;      
    }
	
}