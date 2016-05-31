package mno_ruili_app.adapter;

import java.util.List;

import mno.ruili_app.R;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webpost;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.ab.util.AbViewHolder;
import com.android.volley.toolbox.NetworkImageView;


public class ImageGridAdapter extends BaseAdapter{
	  
		private Context mContext;
		//xml转View对象
	    private LayoutInflater mInflater;
	    //单行的布局
	    private int mResource;
	    //列表展现的数据
	    private List<tv> mData;
	    //Map中的key
	    private String[] mFrom;
	    //view的id
	    private int[] mTo;
	    private int mwith;
	   
	    
	   /**
	    * 构造方法
	    * @param context
	    * @param data 列表展现的数据
	    * @param resource 单行的布局
	    * @param from Map中的key
	    * @param to view的id
	    */
	    public ImageGridAdapter(Context context, int with ,List<tv> data,
	            int resource, String[] from, int[] to){
	    	this.mContext = context;
	    	this.mwith=with;
	    	this.mData = data;
	    	this.mResource = resource;
	    	this.mFrom = from;
	    	this.mTo = to;
	        //用于将xml转为View
	        this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        //图片下载器
	      
	    } 
	    
	    @Override
	    public int getCount() {
	        return mData.size();
	    }
	    
	    @Override
	    public Object getItem(int position) {
	        return mData.get(position);
	    }

	    @Override
	    public long getItemId(int position){
	      return position;
	    }
	    
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent){
	    	  final ViewHolder holder;
	          if(convertView == null){
		           convertView = mInflater.inflate(mResource, parent, false);
				   holder = new ViewHolder();
				   holder.itemsIcon = ((NetworkImageView) convertView.findViewById(mTo[0])) ;
				   holder.itemsIcon.setDefaultImageResId(R.drawable.image_empty);
				   holder.itemsIcon.setErrorImageResId(R.drawable.image_error);
				   holder.itemsImage=((RelativeLayout) convertView.findViewById(mTo[1])) ;
				   holder.itemsTitle =((TextView) convertView.findViewById(mTo[2])) ;
				   holder.itemsText =((TextView) convertView.findViewById(mTo[3])) ;
				   convertView.setTag(holder);
	          }else{
	        	   holder = (ViewHolder) convertView.getTag();
	          }
	          
			  //获取该行的数据
	          final tv mtv = (tv)mData.get(position);
	          String imageUrl =RequestType.getWebUrl_(mtv.getImg());
	          //设置加载中的View
	          holder.itemsIcon.setImageUrl(imageUrl,webpost.getImageLoader());
	          //图片的下载
	          holder.itemsIcon.setDefaultImageResId(R.drawable.image_empty);
	          holder.itemsIcon.setErrorImageResId(R.drawable.image_error);	
	          LayoutParams lp;        
	          lp=holder.itemsImage.getLayoutParams();
	          
	          lp.height=mwith/3;        
	          holder.itemsImage.setLayoutParams(lp); 
	          String name=mtv.getName();
	          if(name.length()>10)
	          {
	          name=name.substring(0, 9)+"...";
	          }
               // 使设置好的布局参数应用到控件myGrid   
	           holder.itemsTitle.setText(name);
	           holder.itemsText.setText(name);
	          //设置加载中的View); holder.itemsTitle.setText(mtv.getName());
	          return convertView;
	    }
	    
	    /**
		 * View元素
		 */
		static class ViewHolder {
			NetworkImageView itemsIcon;
			RelativeLayout itemsImage;
			TextView itemsTitle;
			TextView itemsText;
		}
	    
	}
