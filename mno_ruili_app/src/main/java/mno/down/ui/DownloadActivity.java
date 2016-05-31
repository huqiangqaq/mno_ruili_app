package mno.down.ui;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.activeandroid.query.Delete;
import com.umeng.analytics.MobclickAgent;

import mno.down.adapter.DownloadAdapter;
import mno.down.modal.DownloadRecord;
import mno.down.service.DownloadService;


import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.main.HomeFragment;
import mno.ruili_app.my.my_viedio;
import mno.ruili_app.my.my_wd;
import mno_ruili_app.home.home_video;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class DownloadActivity extends AbActivity {
	private ListView downloadList;
	private boolean isConnected = false;
	private DownloadService.DownloadServiceBinder mBinder;
	private DownloadAdapter downloadAdapter;
	 TextView nei_zx,nei_wd,bz_zx,bz_wd,my_but_bj,tv_all;
	 LinearLayout ll_bottom;
	 String deleteid="",deleteid2="";
	 int begin;
	// protected List<DownloadRecord> mList = new ArrayList<DownloadRecord>();
	private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        	
        	
            mBinder = (DownloadService.DownloadServiceBinder)service;
            isConnected = true;
            downloadList = (ListView)findViewById(R.id.download_list);
            downloadAdapter = (DownloadAdapter)mBinder.getMissionAdapter();
            downloadAdapter.setBoolean(false);
            downloadList.setAdapter(downloadAdapter);
            downloadList.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					 try{
						 TextView title = (TextView)view.findViewById(R.id.title);
							
						 Constant.itemid="/sdcard/Movies/Download/"+title.getText().toString()+".mp4";
						 File file = new File( Constant.itemid);
							//判断文件夹是否存在,如果不存在则创建文件夹
							if (!file.exists()) 
							{
								MessageBox.Show(getApplicationContext(), "下载文件不存在");

							}
							else
							{
								Intent intent = new Intent(DownloadActivity.this,my_viedio.class);
								
								startActivity(intent);
							}
					
						
					 }catch(Exception e){
							
							
						}
					
					//AbToastUtil.showToast(HomeFragment.this.getActivity(),""+mTvList.get(position).getImg());
				}
	    		
	    	});
    		
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnected = false;
        }
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		initDatas();
		init();				
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onPageStart("DownloadActivity");
		MobclickAgent.onResume(this); 
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("DownloadActivity");
		MobclickAgent.onPause(this);
	}

	private void init() {
		// TODO Auto-generated method stub
		 ll_bottom=(LinearLayout)this.findViewById(R.id.ll_bottom);
		 my_but_bj=(TextView)this.findViewById(R.id.my_but_bj);
		 tv_all=(TextView)this.findViewById(R.id.tv_all);
		 nei_zx= (TextView)this.findViewById(R.id.my_butwen);
		 nei_wd= (TextView)this.findViewById(R.id.my_butda);
		 bz_zx= (TextView)this.findViewById(R.id.bz_nei_zx);
		 bz_wd= (TextView)this.findViewById(R.id.bz_nei_wd);
	}
	
	private void digshow2() {
		// TODO Auto-generated method stub
		View mView = null;
		mView = mInflater.inflate(R.layout.dialog_myconfig,null);
		AbDialogUtil.showDialog(mView,R.animator.fragment_top_enter,R.animator.fragment_top_exit,R.animator.fragment_pop_top_enter,R.animator.fragment_pop_top_exit);
		Button leftBtn1 = (Button)mView.findViewById(R.id.left_btn);
		Button rightBtn1 = (Button)mView.findViewById(R.id.right_btn);
		TextView title_choices = (TextView)mView.findViewById(R.id.title_choices);
		TextView choice_one_text= (TextView)mView.findViewById(R.id.choice_one_text);
		//title_choices.setText("");
		choice_one_text.setText("文件不存在或已被删除,是否删除此记录");
		leftBtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(DownloadActivity.this);
			}
			
		});
		
		rightBtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				 Map<String, Boolean> map = downloadAdapter.getCheckMap();
					
				 int size=downloadAdapter.getCount();
				 try{
				 for (int i = 0; i <  size; i++) {
					 int a=downloadAdapter.getmission(i).VideoId;
					 String path="/sdcard/Movies/Download/"+downloadAdapter.getmission(i).FileName+".mp4";
						if (map.containsKey(a+"")
								&& map.get(a+"")) {
							 try{
								 File file = new File(path);
									//判断文件夹是否存在,如果不存在则创建文件夹
									if (!file.exists()) 
									{

									}
									else
									{
										file.delete();
									}
								downloadAdapter.remove(i+"");
								//mList.remove(i);
								DownloadRecord.deleteOne(a+"");
								
							 }catch(Exception e){
									
									break;
								}
							size--;
							 i--;
						}

					}
				 }catch(Exception e){
						
						
					}
			
					downloadAdapter.configCheckMap(false);
					downloadAdapter.notifyDataSetChanged();
			    AbDialogUtil.removeDialog(DownloadActivity.this);
				}
			
		});
	}
	private void digshow() {
		// TODO Auto-generated method stub
		View mView = null;
		mView = mInflater.inflate(R.layout.dialog_myconfig,null);
		AbDialogUtil.showDialog(mView,R.animator.fragment_top_enter,R.animator.fragment_top_exit,R.animator.fragment_pop_top_enter,R.animator.fragment_pop_top_exit);
		Button leftBtn1 = (Button)mView.findViewById(R.id.left_btn);
		Button rightBtn1 = (Button)mView.findViewById(R.id.right_btn);
		TextView title_choices = (TextView)mView.findViewById(R.id.title_choices);
		TextView choice_one_text= (TextView)mView.findViewById(R.id.choice_one_text);
		//title_choices.setText("");
		choice_one_text.setText("是否删除所选数据?");
		leftBtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(DownloadActivity.this);
			}
			
		});
		
		rightBtn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				 Map<String, Boolean> map = downloadAdapter.getCheckMap();
					
				 int size=downloadAdapter.getCount();
				 try{
				 for (int i = 0; i <  size; i++) {
					 int a=downloadAdapter.getmission(i).VideoId;
					 String path="/sdcard/Movies/Download/"+downloadAdapter.getmission(i).FileName+".mp4";
						if (map.containsKey(a+"")
								&& map.get(a+"")) {
							 try{
								 File file = new File(path);
									//判断文件夹是否存在,如果不存在则创建文件夹
									if (!file.exists()) 
									{

									}
									else
									{
										file.delete();
									}
								downloadAdapter.remove(i+"");
								//mList.remove(i);
								DownloadRecord.deleteOne(a+"");
								
							 }catch(Exception e){
									
									break;
								}
							size--;
							 i--;
						}

					}
				 }catch(Exception e){
						
						
					}
			
					downloadAdapter.configCheckMap(false);
					downloadAdapter.notifyDataSetChanged();
			    AbDialogUtil.removeDialog(DownloadActivity.this);
				}
			
		});
	}
	 public void onclick(View v) {
		  if(v.getId()==R.id.tv_all)
		 {
			 if (tv_all.getText().toString().trim().equals("全选")) {
				 downloadAdapter.configCheckMap(true);
				 downloadAdapter.notifyDataSetChanged();
			
			 tv_all.setText("全不选");
			 }
			 else{
				 downloadAdapter.configCheckMap(false);
				 downloadAdapter.notifyDataSetChanged();
				 
				 tv_all.setText("全选");
			 }
				 
			// all.setText("全选");
		 }
		 else if(v.getId()==R.id.tv_delete)
		 {
			 boolean size = downloadAdapter.getCheckMap().containsValue(true);
			 if(size)
			 digshow() ;
			
					
			//	delData("question");
			 
		 }
		 else if(v.getId()==R.id.my_but_bj)
		 {
			 if(!downloadAdapter.getCheck()||begin==0)
			 {
				 begin=1;
				 downloadAdapter.setBoolean(true);
				 downloadAdapter.setCheck(true);
				 ll_bottom.setVisibility(View.VISIBLE);
				 my_but_bj.setText("完成");
			 }
			
			 else
			 {
				 downloadAdapter.setBoolean(false);
				 downloadAdapter.setCheck(false);
				 ll_bottom.setVisibility(View.GONE);
				 my_but_bj.setText("编辑");
			 }
			 tv_all.setText("全选");
			 downloadAdapter.configCheckMap(false);
			 downloadAdapter.notifyDataSetChanged();
			
		 }
	 

	 }
	private void initDatas() {
		 Intent intent = new Intent(this,DownloadService.class);
	     bindService(intent,connection,BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		begin=0;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		 if(isConnected){
	         unbindService(connection);
	     }
	}
}
