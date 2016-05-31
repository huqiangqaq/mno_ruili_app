package mno.ruili_app.ct;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class showWindow {
	private static PopupWindow popupWindow;
	static LinearLayout layout;
	public static void showWindow(Context context,View position,int resource) {

		layout = (LinearLayout) LayoutInflater.from(context).inflate(resource, null);
		
		
		popupWindow = new PopupWindow(position);
		// 设置弹框的宽度为布局文件的宽
		popupWindow.setWidth(LayoutParams.MATCH_PARENT);	
		//popupWindow.setHeight(LayoutParams.WRAP_CONTENT)spinnerlayout.getWidth();
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置一个透明的背景，不然无法实现点击弹框外，弹框消失
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击弹框外部，弹框消失
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setContentView(layout);
		// 设置弹框出现的位置，在v的正下方横轴偏移textview的宽度，为了对齐~纵轴不偏移
		popupWindow.showAsDropDown(position, 0, -300);
		//im.setBackgroundResource(R.drawable.set_item_back);
		popupWindow.setOnDismissListener(new OnDismissListener(){
			@Override
			public void onDismiss() {
				//spinnerlayout.setBackgroundResource(R.drawable.preference_single_item);
				//im.setBackgroundResource(R.drawable.set_item_go);
			}
			
		});
		/*if(resource!=R.layout.fragment_setting_about)
		{
		TextView but = (TextView) layout.findViewById(R.id.dialog_ok);
		but.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(value!=null)
				{
				textView.setText(value);
				
				if(value.equals("English"))
					
				updateActivity("en");
				else
				updateActivity("ch");  
				}
				popupWindow.dismiss();
				popupWindow = null;
			}});
		
		RadioGroup rb = (RadioGroup)layout.findViewById(R.id.rg_lg);
		if(resource==R.layout.dialog_layout)
		{
		RadioButton bt1=(RadioButton)layout.findViewById(R.id.lg_English); 
		
		RadioButton bt2=(RadioButton)layout.findViewById(R.id.lg_chinese); 
		if(lang.equals("ch"))
		{
			bt1.setChecked(false);
			
			bt2.setChecked(true);
		}
		else
		{bt1.setChecked(true);}
		
		}
		rb.setOnCheckedChangeListener(new OnCheckedChangeListener(){
 
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if(checkedId==R.id.lg_English){
                	
                	value="English";
                	
                	
                }
                if(checkedId==R.id.lg_chinese){
                	value="简体中文";
                	
                }
                
                
 
            }

		
        });*/
		// listView的item点击事件
		
		}
	}

