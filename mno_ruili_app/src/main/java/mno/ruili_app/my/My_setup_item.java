package mno.ruili_app.my;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.umeng.analytics.MobclickAgent;

import mno.down.util.Clean_Cache_item;
import mno.ruili_app.PassMgr;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.RequestType.Type;
import mno_ruili_app.net.webhandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class My_setup_item extends Activity {
	int i=1;int h=1;
	webhandler handler_;
	ImageView iv_back;
	CheckBox my_xw_but,my_zb_but;
	TextView tvCache,tv_xgmm;
	LinearLayout my_gywm_item,my_bz_item,my_yjfk_item,my_gwpf_item,my_xgmm_item,my_llhc_item;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_setup_item);
		init();
		//显示清理之前的缓存大小
		try {
			tvCache.setText(Clean_Cache_item.getTotalCacheSize(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("My_setup_item");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("My_setup_item");
		MobclickAgent.onPause(this);
	}
	private void init() {		
		tv_xgmm=(TextView) findViewById(R.id.tv_xgmm);
		if(PassMgr.Isplatform()==false){
			tv_xgmm.setTextColor(Color.GRAY);
		}
		//
		my_xw_but= (CheckBox)this.findViewById(R.id.my_xw_but);
		my_xw_but.setChecked(PassMgr.Isxw());	
		my_xw_but.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				PassMgr.Isxw(arg1);
				if(arg1)
					i=1;
				else
					i=2;
				change("newsPush",i);
				}});
		//
		my_zb_but = (CheckBox)this.findViewById(R.id.my_zb_but);
		my_zb_but.setChecked(PassMgr.Iszb());
		my_zb_but.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				//MessageBox.Show(getApplicationContext(), arg1+"");
				PassMgr.Iszb(arg1);
				if(arg1)
					h=1;
				else
					h=2;
				change("coursePush",h);
			}});
			handler_ = new webhandler(){

				@Override
				public void OnResponse(JSONObject response) {
					
					
				}

		};
		
		iv_back=(ImageView) findViewById(R.id.iv_back);
		tvCache=(TextView) findViewById(R.id.tvCache);
		
		my_gywm_item=(LinearLayout)findViewById(R.id.my_gywm_item);
		my_gwpf_item=(LinearLayout)findViewById(R.id.my_gwpf_item);
		my_bz_item=(LinearLayout)findViewById(R.id.my_bz_item);
		my_yjfk_item=(LinearLayout)findViewById(R.id.my_yjfk_item);
		my_xgmm_item=(LinearLayout)findViewById(R.id.my_xgmm_item);
		my_llhc_item=(LinearLayout)findViewById(R.id.my_llhc_item);
		
		iv_back.setOnClickListener(new MyListener());
		my_gywm_item.setOnClickListener(new MyListener()); 
		my_bz_item.setOnClickListener(new MyListener()); 
		my_yjfk_item.setOnClickListener(new MyListener()); 
		my_xgmm_item.setOnClickListener(new MyListener()); 
		my_llhc_item.setOnClickListener(new MyListener());
		my_gwpf_item.setOnClickListener(new MyListener());
	}
	//
	protected void change(String string,int a) {
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap<String, String>();
		params.put(string,a+"");
		
		handler_.SetRequest(new RequestType("4",Type.editInfo),params);
	}
	//
	 private class MyListener implements OnClickListener{

			@Override
			public void onClick(View v) {
				//1.修改密码（只有app注册的才能修改密码）
				if(v.getId()==R.id.my_xgmm_item)
	        	{	        	
	        		/*Intent intent = new Intent(My_setup_item.this, Reset_pwd_item.class);   
	        		startActivity(intent);*/
					if(PassMgr.Isplatform()){
						appuser.getInstance().LoginToAct(My_setup_item.this,  Reset_pwd_item.class);
					}else{
						//my_xgmm_item.setBackgroundColor(Color.GRAY);
						//my_xgmm_item.setBackgroundResource(R.drawable.radius_hui);
						//tv_xgmm.setTextColor(Color.GRAY);
					}
					
	        	}
				//返回
				else if(v.getId()==R.id.iv_back)
	        	{
	        	//	appuser.getInstance().LoginToAct(MyFragment.this.getActivity(),  my_gyll_item.class);
	        		
	        		My_setup_item.this.finish();
	        	}
				//2.缓存清理
				else if(v.getId()==R.id.my_llhc_item)
	        	{
	        	//	appuser.getInstance().LoginToAct(MyFragment.this.getActivity(),  my_gyll_item.class);
	        		AlertDialog.Builder builder=new AlertDialog.Builder(My_setup_item.this);
	        		builder.setMessage("缓存文件可以用来帮你节省流量，但是较大时会占用较多磁盘空间。确定开始清理吗？");
	        		builder.setTitle("提示");
	        		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							//清理所有缓存
							Clean_Cache_item.clearAllCache(My_setup_item.this);
							//显示清理之后的缓存大小
							try {
								tvCache.setText(Clean_Cache_item.getTotalCacheSize(My_setup_item.this));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
	        		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							
						}						
					});
	        		builder.create().show();
	        	}
				//3.关于我们
				else if(v.getId()==R.id.my_gywm_item)
	        	{
	        	//	appuser.getInstance().LoginToAct(MyFragment.this.getActivity(),  my_gyll_item.class);
	        		Intent intent = new Intent(My_setup_item.this, my_gyll_item.class);   
	        		startActivity(intent);
	        	}
				//4.帮助
	        	else if(v.getId()==R.id.my_bz_item)
	        	{
	        		//appuser.getInstance().LoginToAct(MyFragment.this.getActivity(),  my_bz_item.class);
	        		Intent intent = new Intent(My_setup_item.this, my_bz_item.class);   
	        		startActivity(intent);
	        	}
				//5.意见反馈
	        	else if(v.getId()==R.id.my_yjfk_item)
	        	{
	        		appuser.getInstance().LoginToAct(My_setup_item.this,  my_yjfk_item.class);
	        		/*Intent intent = new Intent(MyFragment.this.getActivity(), my_yjfk_item.class);   
	        		startActivity(intent);*/
	        	}
				//6.给我评分
	        	else if(v.getId()==R.id.my_gwpf_item)
	        	{
	        		Uri uri = Uri.parse("market://details?id="+getPackageName());  
	        		Intent intent = new Intent(Intent.ACTION_VIEW,uri);  
	        		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
	        		startActivity(intent); 
	        	}
			}
			 
		 }
}
