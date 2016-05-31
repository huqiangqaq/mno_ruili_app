package mno.ruili_app.my;

import java.util.ArrayList;
import java.util.List;

import com.ab.activity.AbActivity;
import com.ab.download.DownFile;



import com.ab.download.DownFileDao;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno_ruili_app.adapter.ImageListAdapter_myht;
import mno_ruili_app.adapter.ImageListAdapter_myxz;
import mno_ruili_app.adapter.ht;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class my_wdxa_item extends  AbActivity {
	 List<DownFile>   mList = new ArrayList<DownFile>();
	 private DownFileDao mDownFileDao = null;
	 private ListView mListView = null;
	 TextView nei_zx,nei_wd,bz_zx,bz_wd,my_but_bj,tv_all;
	  LinearLayout ll_bottom;
	  String deleteid="",deleteid2="";
	  ImageListAdapter_myxz mylistViewAdapter;
	@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	//setContentView(R.layout.my_wdxa_item);
	 setAbContentView(R.layout.my_wdxa_item);
	init();
}
private void init() {
	// TODO Auto-generated method stub
	 ll_bottom=(LinearLayout)this.findViewById(R.id.ll_bottom);
	 tv_all=(TextView)this.findViewById(R.id.tv_all);
	 mDownFileDao = DownFileDao.getInstance(this);
	 
	 
	 mListView = (ListView)this.findViewById(R.id.mListView);
	 //id, myReply, ReplyTime, title, Rcontent,  replyTotal, publish_at
	 mylistViewAdapter = new ImageListAdapter_myxz(this, mList,
				R.layout.item_ht, new String[] { "itemsIcon" },
				new int[] { R.id.myReply ,R.id.title,R.id.replyTotal,R.id.ReplyTime});
	 mListView.setAdapter(mylistViewAdapter);
	 
	 List<DownFile> mDownFileList = new ArrayList<DownFile>();
		
		DownFile mDownFile1 = new DownFile();
		mDownFile1.setName("视频1");
		mDownFile1.setDescription("乐趣");
		mDownFile1.setPakageName("");
		mDownFile1.setState(Constant.undownLoad);
		mDownFile1.setIcon(String.valueOf(R.drawable.image_empty));
		mDownFile1.setDownUrl("http://www.yuetingfengsong.com:8087/media/files/test.mp4 ");
		mDownFile1.setSuffix(".mp4");
		mDownFileList.add(mDownFile1); 
		mylistViewAdapter.notifyDataSetChanged();
		
		for(DownFile mDownFile:mDownFileList){
			  //本地数据
			  DownFile mDownFileT = mDownFileDao.getDownFile(mDownFile.getDownUrl());
	          if(mDownFileT != null){
	        	  mDownFile = mDownFileT;
	        	  if(mDownFile.getDownLength() == mDownFile.getTotalLength() && mDownFile.getTotalLength()!=0){
	    	    	  mDownFile.setState(Constant.downloadComplete);
	    	    	  
	    	    	  mList.add(mDownFile);
	    	    	  mylistViewAdapter.notifyDataSetChanged();
				  }else{
					  //显示为暂停状态
		        	  mDownFile.setState(Constant.downLoadPause);
		        	  mList.add(mDownFile);
		        	  mylistViewAdapter.notifyDataSetChanged();
	        	     
				  }
	          }else{
	        	    mDownFile.setState(Constant.undownLoad);
	        	    mList.add(mDownFile);
	        	    mylistViewAdapter.notifyDataSetChanged();
	          }
	    }
}
public void onclick(View v) {
	 if(v.getId()==R.id.my_but_login)
	 {
		 
	 }
	 else if(v.getId()==R.id.my_but_registered)
	 {
		 
	 }
}
@Override
public void finish() {
	super.finish();
	
	//释放所有的下载线程
	mylistViewAdapter.releaseThread();

	
}
}
