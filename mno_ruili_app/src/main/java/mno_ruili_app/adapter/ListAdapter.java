package mno_ruili_app.adapter;

import java.util.List;
import java.util.Map;

import mno.ruili_app.R;
import mno_ruili_app.net.RequestType;

import com.ab.image.AbImageLoader;
import com.ab.util.AbViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends  BaseAdapter{
	
	private static String TAG = "ImageListAdapter";
	//private static final boolean D = Constant.DEBUG;
  
	private Context mContext;
	//xml转View对象
    private LayoutInflater mInflater;
    //单行的布局
    private int mResource;
    //列表展现的数据
    List<? extends Map<String, ?>> mData;
    //Map中的key
    private String[] mFrom;
    //view的id
    private int[] mTo;
    //图片下载器
    private AbImageLoader mAbImageLoader = null;
    
   /**
    * 构造方法
    * @param context
    * @param data 列表展现的数据
    * @param resource 单行的布局
    * @param from Map中的key
    * @param to view的id
    */
    public ListAdapter(Context context, List<? extends Map<String, ?>> data,
            int resource, String[] from, int[] to){
    	this.mContext = context;
    	this.mData = data;
    	this.mResource = resource;
    	this.mFrom = from;
    	this.mTo = to;
        //用于将xml转为View
        this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //图片下载器
        mAbImageLoader = AbImageLoader.newInstance(mContext);
        mAbImageLoader.setMaxWidth(0);
        mAbImageLoader.setMaxHeight(0);
        mAbImageLoader.setLoadingImage(R.drawable.image_loading);
        mAbImageLoader.setErrorImage(R.drawable.image_error);
        mAbImageLoader.setEmptyImage(R.drawable.image_empty);
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
          if(convertView == null){
	           //使用自定义的list_items作为Layout
	           convertView = mInflater.inflate(mResource, parent, false);
          }
          
          ImageView itemsIcon = AbViewHolder.get(convertView, mTo[0]);
          TextView username = AbViewHolder.get(convertView, mTo[1]);
          TextView create_at = AbViewHolder.get(convertView, mTo[2]);
          View content= convertView.findViewById(mTo[3]);
		  //获取该行的数据
          String class_name = content.getClass().getSimpleName();
          
          //final tv mtv = (tv)mData.get(position);
          //String a= mData.get(position).get(mFrom[0]).toString();
          Object value_o =   mData.get(position).get(mFrom[3]);
          String imageUrl = RequestType.getWebUrl_(mData.get(position).get(mFrom[0]).toString());
          username.setText(mData.get(position).get(mFrom[1]).toString());
          create_at.setText(mData.get(position).get(mFrom[2]).toString());
          ((TextView)content).setText(value_o.toString()); 
          //设置加载中的View
          //String path="http://b264.photo.store.qq.com/psb?/V14av8GK1voYCO/BPkJ.Gv0rDTo0MGFlxDb7ze4vPxjxPztOkaZHqVgdYI!/b/dIBJXp0lFAAA&bo=*gFmAQAAAAABB7g!&rf=viewer_4";
          //itemsIcon.setImageUrl(path,webpost.getImageLoader());
       
          mAbImageLoader.setLoadingView(convertView.findViewById(R.id.progressBar));
          //图片的下载
          mAbImageLoader.display(itemsIcon,imageUrl);
          
          return convertView;
    }
    
}
