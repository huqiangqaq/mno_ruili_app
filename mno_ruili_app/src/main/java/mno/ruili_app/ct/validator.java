package mno.ruili_app.ct;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.ContentResolver;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.WindowManager;

public class validator {
	
    /**
     * 转换播放时间
     * 
     * @param milliseconds 传入毫秒值
     * @return 返回 hh:mm:ss或mm:ss格式的数据
     */
	public static int getScreenBrightness(Activity activity) {
	    int value = 0;
	    ContentResolver cr = activity.getContentResolver();
	    try {
	        value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
	    } catch (SettingNotFoundException e) {
	        
	    }
	    return value;
	}
	
	public static void setScreenBrightness(Activity activity, int value) {
	    WindowManager.LayoutParams params = activity.getWindow().getAttributes();
	    params.screenBrightness = value / 255f;
	    activity.getWindow().setAttributes(params);
	}
    public static String getShowTime(long milliseconds) {
        // 获取日历函数
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        SimpleDateFormat dateFormat = null;
        // 判断是否大于60分钟，如果大于就显示小时。设置日期格式
        if (milliseconds / 60000 > 60) {
            dateFormat = new SimpleDateFormat("hh:mm:ss");
        } else {
            dateFormat = new SimpleDateFormat("mm:ss");
        }
        return dateFormat.format(calendar.getTime());
    }
	/**
	 * 验证身份证号码
	 * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkIdCard(String idCard) {
		String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
		return Pattern.matches(regex,idCard);
	}
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
		.compile("^((13[0-9])|(177)|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);

		return m.matches();
		}
	public static boolean isnameNO(String name) {
		Pattern p = Pattern
				
		.compile("^([\u4e00-\u9fa5]){2,7}$");
		Matcher m = p.matcher(name);

		return m.matches();
		}
    //判断email格式是否正确
   public static boolean isEmail(String email) {
String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
Pattern p = Pattern.compile(str);
Matcher m = p.matcher(email);

return m.matches();
}
    //判断是否全是数字
   public static boolean isNumeric(String str) {
Pattern pattern = Pattern.compile("[0-9]*");
Matcher isNum = pattern.matcher(str);
if (!isNum.matches()) {
return false;
}
return true;
}
}
