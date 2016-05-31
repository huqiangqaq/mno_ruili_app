package mno.ruili_app.main;

import java.lang.Thread.UncaughtExceptionHandler;

import mno.ruili_app.ct.MessageBox;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class CrashHandler implements UncaughtExceptionHandler {

    private static CrashHandler instance;  //单例引用，这里我们做成单例的，因为我们一个应用程序里面只需要一个UncaughtExceptionHandler实例
    //系统默认的UncaughtException处理类       
    private Thread.UncaughtExceptionHandler mDefaultHandler;     
    private CrashHandler(){}
    Context Context_;
    public synchronized static CrashHandler getInstance(){  //同步方法，以免单例多线程环境下出现异常
        if (instance == null){
            instance = new CrashHandler();
        }
        return instance;
    }
    
    public void init(Context ctx){  //初始化，把当前对象设置成UncaughtExceptionHandler处理器
    	Context_=ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();      
        //设置该CrashHandler为程序的默认处理器      
        Thread.setDefaultUncaughtExceptionHandler(this);      

       
    }
    
    /**   
     * 当UncaughtException发生时会转入该函数来处理   
     */      
    @Override      
    public void uncaughtException(Thread thread, Throwable ex) {      
        if (!handleException(ex) && mDefaultHandler != null) {      
            //如果用户没有处理则让系统默认的异常处理器来处理      
            mDefaultHandler.uncaughtException(thread, ex);      
        } else {      
            try {      
                Thread.sleep(3000);      
            } catch (InterruptedException e) {      
                Log.e("mno", "error : ", e);      
            }      
            //退出程序      
            android.os.Process.killProcess(android.os.Process.myPid());      
            System.exit(1);      
        }      
    }      
    
    /**   
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.   
     *    
     * @param ex   
     * @return true:如果处理了该异常信息;否则返回false.   
     */      
    private boolean handleException(Throwable ex) {      
        if (ex == null) {      
            return false;      
        }      
        //收集设备参数信息       
       // collectDeviceInfo(Context_);      
          
        //使用Toast来显示异常信息      
        new Thread() {      
            @Override      
            public void run() {      
                Looper.prepare();      
                Toast.makeText(Context_, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_SHORT).show();      
                Looper.loop();      
            }      
        }.start();      
        //保存日志文件       
        //saveCatchInfo2File(ex);    
        return true;      
    }      
}