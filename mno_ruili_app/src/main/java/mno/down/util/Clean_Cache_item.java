package mno.down.util;

import java.io.File;
import java.math.BigDecimal;

import android.content.Context;
import android.os.Environment;

public class Clean_Cache_item {
	//删除文件的方法
	private static void deleteFilesByDirectory(File directory){
		if(directory!=null&&directory.exists()&&directory.isDirectory()){
			for(File item:directory.listFiles()){
				item.delete();
			}
		}
	}
	//1.清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
	public static void cleanInternalCache(Context context){
		deleteFilesByDirectory(context.getCacheDir());
	}
	//2.清除本应用外部缓存(/mnt/sdcard/android/data/com.xxx.xxx/cache)
	public static void cleanExternalCache(Context context){
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			deleteFilesByDirectory(context.getExternalCacheDir());
		}
	}
	//3.清除本应用所有的数据库(/data/data/com.xxx.xxx/databases)
	public static void cleanDatabases(Context context){
		deleteFilesByDirectory(new File("/data/data"+context.getPackageName()+"/databases"));
	}
	//4.清理自定义文件夹的数据
	public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }
	
	//5.获取文件缓存大小
	public static long getFolderSize(File file){
		long size=0;
		try{
		File[] fileList=file.listFiles();
		for(int i=0;i<fileList.length;i++){
			if(fileList[i].isDirectory()){
				size=size+getFolderSize(fileList[i]);				
			}else{
				size=size+fileList[i].length();
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return size;
	}
	//获得总缓存大小
	 public static String getTotalCacheSize(Context context) throws Exception {
         long cacheSize = getFolderSize(context.getCacheDir());
         if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { 
             cacheSize += getFolderSize(context.getExternalCacheDir());
         } 
         return getFormatSize(cacheSize);
     }
	//6.清理所有缓存
	public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { 
            deleteDir(context.getExternalCacheDir());
        } 
    }
	private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()&&dir.exists()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
	//7.把获取缓存格式化单位
	public static String getFormatSize(double size){
		double kiloByte=size/1024;
		if(kiloByte<1){
			return  size+"B";
		}
		double megaByte = kiloByte / 1024;  
        if (megaByte < 1) {  
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));  
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";  
        }  
  
        double gigaByte = megaByte / 1024;  
        if (gigaByte < 1) {  
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));  
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";  
        }  
  
        double teraBytes = gigaByte / 1024;  
        if (teraBytes < 1) {  
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));  
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP) .toPlainString() + "GB";  
        }  
        BigDecimal result4 = new BigDecimal(teraBytes);  
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
	}
	//8.获取格式化后的缓存大小
	/*
	public static String getCacheSize(File file)  {  
		String cache=null;
		try {
			cache=getFormatSize(getFolderSize(file)); 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cache; 
    } 
    */ 
}
