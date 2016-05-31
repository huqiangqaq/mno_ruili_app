package mno.ruili_app.ct;

import android.content.Context;
import android.widget.Toast;

public class MessageBox 
{
	public static void Show(Context context,String text,int i)
	{
		Toast.makeText(context,text,i).show();
	}
	public static void Show(Context context,String text)
	{
		Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
	}
	
	public static void ShowLong(Context context,String text)
	{
		Toast.makeText(context,text,Toast.LENGTH_LONG).show();
	}

}
