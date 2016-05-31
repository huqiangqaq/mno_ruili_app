package mno.down.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mno.down.modal.DownloadDetail;
import mno.down.modal.DownloadRecord;
import mno.down.util.Mission;
import mno.ruili_app.R;
import mno.ruili_app.ct.MessageBox;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webpost;

import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;











import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownloadAdapter extends MissionListenerForAdapter{
	
	Context context_;
	//protected List<DownloadRecord> mData = new ArrayList<DownloadRecord>();
	private boolean isCheck, check;
    public boolean getCheck() {
		return check;
	}
	
	public void setCheck(boolean check) {
		this.check = check;
	}
	public void setBoolean(boolean isCheck) {
		this.isCheck = isCheck;
	}
	/**
	 * CheckBox 是否选择的存储集合,key 是 id , value 是该position是否选中
	 */
	private Map<String, Boolean> isCheckMap = new HashMap<String, Boolean>();
	
	
	/**
	 * 首先,默认情况下,所有项目都是没有选中的.这里进行初始化
	 * @return 
	 */
	public DownloadDetail getmission(int i)
	{
		DownloadDetail detail = null;
		Object mission = getItem(i);
		
		if(mission instanceof DownloadRecord){
			DownloadRecord record = (DownloadRecord)mission;
        	detail = DownloadRecord.getDownloadDetail(record);
        	return detail;
		}
		else 
		{
			Mission m = (Mission)mission;
			//m.cancel();
			Object extra =m.getExtraInformation(m.getUri());
            detail = (DownloadDetail)extra;
            return detail;
		}
	}
	public void configCheckMap(boolean bool) {
		isCheckMap.clear();
		for (int i = 0; i <  getCount(); i++) {
			DownloadDetail detail = null;
			Object mission = getItem(i);
			if(mission instanceof DownloadRecord){
	        	DownloadRecord record = (DownloadRecord)mission;
	        	detail = DownloadRecord.getDownloadDetail(record);
			    isCheckMap.put(detail.VideoId+"", bool);}
			else
			{
				Mission m = (Mission)mission;
				m.cancel();
	            Object extra =m.getExtraInformation(m.getUri());
	            detail = (DownloadDetail)extra;
	            isCheckMap.put(detail.VideoId+"", bool);
			}
		}

	}
	
	
	public Map<String, Boolean> getCheckMap() {
		return this.isCheckMap;
	}

	// 移除一个项目的时候
	public void remove(String id) {
		//this.mData.remove(position);
		
		removelist(Integer.parseInt(id));
		
	}
	
	    private LayoutInflater mLayoutInflater;
	    public DownloadAdapter(Context context) {
	        super(context);
	        context_=context;
	        //mData.addAll(DownloadRecord.getAllDownloaded());
	        mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }

	    @Override
	    public View getView(final int position, View convertView, ViewGroup parent) {
	    	NetworkImageView thumb;
			final ImageView downloadImage;
	        TextView title,downSpeed,downingDetail;
	        ProgressBar speedBar;
	        ViewHolder holder;
	        CheckBox cbCheckBox;
	        if(convertView == null){
	            convertView = mLayoutInflater.inflate(R.layout.download_item,null);
	            thumb = (NetworkImageView)convertView.findViewById(R.id.thumbImage);
	            title = (TextView)convertView.findViewById(R.id.title);
	            downloadImage = (ImageView)convertView.findViewById(R.id.downloadImage);
	            downSpeed = (TextView)convertView.findViewById(R.id.downSpeed);
	            downingDetail = (TextView)convertView.findViewById(R.id.downingDetail);
	            speedBar = (ProgressBar)convertView.findViewById(R.id.speedBar);
	            cbCheckBox = (CheckBox)convertView.findViewById(R.id.cbCheckBox);
	            holder = new ViewHolder(title,downSpeed,downingDetail,thumb,speedBar,downloadImage,cbCheckBox);
	            convertView.setTag(holder);
	        }else{
	            holder = (ViewHolder)convertView.getTag();
	            title = holder.title;
	            downloadImage = holder.downloadImage;
	            downSpeed = holder.downSpeed;
	            downingDetail = holder.downingDetail;
	            speedBar = holder.speedBar;
	            cbCheckBox= holder.cbCheckBox;
	            thumb = holder.thumb;
	            
	        }
	       
	        if (isCheck) {
	        	  cbCheckBox.setVisibility(View.VISIBLE);
	  		} else {
	  			  cbCheckBox.setVisibility(View.GONE);
	  		}
	        
	        if(position > getCount() - 1){
	            return null;
	        }
	        final Object mission = getItem(position);
	        
	        DownloadDetail detail = null;
	        
	        if(mission instanceof DownloadRecord){
	        	DownloadRecord record = (DownloadRecord)mission;
	        	detail = DownloadRecord.getDownloadDetail(record);
	        	speedBar.setVisibility(View.INVISIBLE);
	        	downingDetail.setText(record.getFileSize());
	        	downloadImage.setVisibility(View.GONE);
	        	downSpeed.setText("");
	        }else if(mission instanceof Mission){
	            Mission m = (Mission)mission;
	            Object extra =m.getExtraInformation(m.getUri());
	            detail = (DownloadDetail)extra;
	            if(m.isDone()){
	            	if(m.isSuccess()){
	            		downloadImage.setVisibility(View.INVISIBLE);
	            		speedBar.setVisibility(View.INVISIBLE);
	            		downSpeed.setText(R.string.download_success);
	            		//downingDetail.setVisibility(View.INVISIBLE);
	            		downingDetail.setText(m.getReadableSize());
	            	}
	            	else{ //下载错误
	            		downloadImage.setVisibility(View.VISIBLE);
	            		speedBar.setProgress(m.getPercentage());
	            		speedBar.setVisibility(View.VISIBLE);
	            		downSpeed.setText(R.string.download_error);
	            		downingDetail.setText(m.getAccurateReadableSize()+"/" + m.getReadableSize());
	            		
	            		if(m.isDownloading()){
	    	            	downloadImage.setVisibility(View.VISIBLE);
	    	            	downloadImage.setImageResource(R.drawable.view_ks);
	    	            	downSpeed.setText(m.getAccurateReadableSpeed());
	    	            	downingDetail.setText(m.getAccurateReadableSize()+"/" + m.getReadableSize());
	    	            	speedBar.setProgress(m.getPercentage());
	    	            	speedBar.setVisibility(View.VISIBLE);}
	            		else if(m.isCanceled()){
	    	            	downloadImage.setVisibility(View.VISIBLE);
	    	            	downloadImage.setImageResource(R.drawable.view_zt);
	    	            	downSpeed.setText(R.string.download_pause);
	    	            	downingDetail.setText(m.getAccurateReadableSize()+"/" + m.getReadableSize());
	    	            	speedBar.setProgress(m.getPercentage());
	    	            }
	            		if(m.isSuccess()){
		            		downloadImage.setVisibility(View.INVISIBLE);
		            		speedBar.setVisibility(View.INVISIBLE);
		            		downSpeed.setText(R.string.download_success);
		            		//downingDetail.setVisibility(View.INVISIBLE);
		            		downingDetail.setText(m.getReadableSize());
		            	}
	    	           
	            	}
	            }else if(m.isCanceled()){
	            	downloadImage.setVisibility(View.VISIBLE);
	            	downloadImage.setImageResource(R.drawable.view_zt);
	            	downSpeed.setText(R.string.download_pause);
	            	downingDetail.setText(m.getAccurateReadableSize()+"/" + m.getReadableSize());
	            	speedBar.setProgress(m.getPercentage());
	            }else if(m.isDownloading()){
	            	downloadImage.setVisibility(View.VISIBLE);
	            	downloadImage.setImageResource(R.drawable.view_ks);
	            	downSpeed.setText(m.getAccurateReadableSpeed());
	            	downingDetail.setText(m.getAccurateReadableSize()+"/" + m.getReadableSize());
	            	speedBar.setProgress(m.getPercentage());
	            	speedBar.setVisibility(View.VISIBLE);
	            }else { //等待下载
	            	downloadImage.setVisibility(View.VISIBLE);
	            	downloadImage.setImageResource(R.drawable.view_zt);
	            	downSpeed.setText(R.string.download_wait);
	            	downingDetail.setText(m.getAccurateReadableSize()+"/" + m.getReadableSize());
	            	speedBar.setProgress(m.getPercentage());
	            	speedBar.setVisibility(View.VISIBLE);
	            }
	        }
	       /*Picasso.with(mContext).load(detail.HomeIcon)
            .error(R.drawable.image_error).into(thumb);*/
	        thumb.setImageUrl(detail.HomeIcon,webpost.getImageLoader());
	       
	        if (isCheckMap.containsKey(detail.VideoId+"") && isCheckMap.get(detail.VideoId+"")) {
	  			cbCheckBox.setChecked(true);
	  		} else {
	  			cbCheckBox.setChecked(false);
	  		}
	  		final String id=detail.VideoId+"";
	  		final DownloadDetail detail_= detail;
	  		cbCheckBox .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
	  			@Override
	  			public void onCheckedChanged(CompoundButton buttonView,
	  					boolean isChecked) {
	  				if (isChecked) {
	  					isCheckMap.put(id+"", true);
	  					
	  					//MessageBox.Show(context_, id+detail_.Title);
					} else {
						isCheckMap.put(id+"", false);
					}
	  			}
	  		});

	        title.setText(detail.Title);
	        downloadImage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					 if (isCheck)
						 return;
					if(mission instanceof Mission){
						Mission sion = (Mission)mission;
						 if(sion.isCanceled()){
							 downloadImage.setImageResource(R.drawable.view_ks);
							 downloadHelper.download(sion);  
						 }
						 else if
						 (sion.isDownloading()){
							 
							 downloadImage.setImageResource(R.drawable.view_zt);
							 sion.cancel();
							
						 }
						 
						 else{
								 downloadImage.setImageResource(R.drawable.view_ks);
								 sion.cancel();
								// sion.setCanceled(false);
								 //sion.setDownloading(true);
								 downloadHelper.download(sion);  
								 
						 }
					}
				}
			});
	        return convertView;
	    }

	    public static class ViewHolder{
	        public TextView title,progress,downSpeed,downingDetail;
	        public ImageView downloadImage;
	        NetworkImageView thumb;
	        public ProgressBar speedBar;
	        public CheckBox cbCheckBox;
	        private ViewHolder(TextView title, TextView downSpeed,TextView downingDetail, NetworkImageView thumb,ProgressBar speedBar,ImageView downloadImage,CheckBox cbCheckBox) {
	            this.title = title;
	            this.downSpeed = downSpeed;
	            this.downingDetail = downingDetail;
	            this.thumb = thumb;
	            this.speedBar = speedBar;
	            this.downloadImage = downloadImage;
	            this.cbCheckBox = cbCheckBox;
	        }
	    }
	}
