package mno.down.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huqiang on 2016/3/1 17:02.
 */
public class SharePreTolls {

   private Context mcontext;

    public static void writeHistroy(Context context,String path ,int m){
        SharedPreferences Save = context.getSharedPreferences("history",Context.MODE_PRIVATE);
        int n = Save.getInt("point", 0);
        SharedPreferences.Editor editor = Save.edit();
        editor.putString("path"+n,path);
        editor.putInt("point", (n + 1) % m);
        editor.commit();

    }

    public static List<String> readHistory(Context context,int m){
        List<String> list = new ArrayList<String>();
        SharedPreferences Save = context.getSharedPreferences("history",Context.MODE_PRIVATE);
        int point = Save.getInt("point", 0);
        String path;
        for (int i=0,n=point;i<m;i++){
            path = Save.getString("path"+n,null);
            if (path!=null){            	
                list.add(path);
            }
            n=n>0?(--n):(--n+m)%m;
        }
        //去除list中的重复项
        for (int i = 0; i < list.size()-1; i++){  //外循环是循环的次数
            for (int j = list.size()-1 ; j > i; j--){  //内循环是 外循环一次比较的次数
                if (list.get(i) .equals(list.get(j))){
                    //list.remove(list.get(j));
                    list.remove(j);
                }
            }
        }
        
        return list;
    }
    public static void deleteHistory(Context context){
    	SharedPreferences Save = context.getSharedPreferences("history",Context.MODE_PRIVATE);
    	SharedPreferences.Editor editor = Save.edit();
    	editor.clear();
    	editor.commit();
    }
}
