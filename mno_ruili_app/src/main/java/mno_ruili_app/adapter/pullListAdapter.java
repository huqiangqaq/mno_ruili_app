package mno_ruili_app.adapter;




import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import mno.ruili_app.R;
import mno.ruili_app.ct.RoundImageView;
import mno.ruili_app.ct.RoundimgView;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webpost;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ab.image.AbImageLoader;
import com.ab.util.AbViewHolder;
import com.android.volley.toolbox.NetworkImageView;

/**
 * © 2012 amsoft.cn
 * 名称：MyListViewAdapter
 * 描述：在Adapter中释放Bitmap
 * @author 还如一梦中
 * @date 2011-12-10
 * @version
 */
public class pullListAdapter extends BaseAdapter{
	
	private static String TAG = "ImageListAdapter";
	//private static final boolean D = Constant.DEBUG;
  
	private Context mContext;
	//xml转View对象
    private LayoutInflater mInflater;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private List mData;
    //Map中的key
    private String[] mFrom;
    //view的id
    private int[] mTo;
    //图片下载器
    private AbImageLoader mAbImageLoader = null;
    JSONArray ad;
   /**
    * 构造方法
    * @param context
    * @param data 列表展现的数据
 * @param ad 
    * @param resource 单行的布局
    * @param from Map中的key
    * @param to view的id
    */
    public pullListAdapter(Context context, List<tv> data,
            JSONArray ad, int resource, String[] from, int[] to){
    	this.mContext = context;
    	this.mData = data;
    	this.mResource = resource;
    	this.mFrom = from;
    	this.mTo = to;
    	this.ad=ad;
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
    TextView itemsTitle,itemsText;
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
          if(convertView == null){
	           //使用自定义的list_items作为Layout
	           convertView = mInflater.inflate(mResource, parent, false);
          }
          if(position==0)
          {
        	  
          }
          final tv mtv = (tv)mData.get(position);
          String imageUrl =RequestType.getWebUrl_(mtv.getImg());
          //ImageView itemsIcon = AbViewHolder.get(convertView, mTo[0]);
          if(mTo.length>2)  
          {
           itemsTitle = AbViewHolder.get(convertView, mTo[1]);
           itemsText = AbViewHolder.get(convertView, mTo[2]);
           itemsTitle.setText(mtv.getName());
           itemsText.setText(mtv.getMs());
          }
          //mAbImageLoader.display(itemsIcon,imageUrl);
		  //获取该行的数据
         /* final Map<String, Object>  obj = (Map<String, Object>)mData.get(position);
          String imageUrl = (String)obj.get("itemsIcon");
          itemsTitle.setText((String)obj.get("itemsTitle"));
          itemsText.setText((String)obj.get("itemsText"));*/
        
          //设置加载中的View
          //String path="http://b264.photo.store.qq.com/psb?/V14av8GK1voYCO/BPkJ.Gv0rDTo0MGFlxDb7ze4vPxjxPztOkaZHqVgdYI!/b/dIBJXp0lFAAA&bo=*gFmAQAAAAABB7g!&rf=viewer_4";
          //itemsIcon.setImageUrl(path,webpost.getImageLoader());
         // mAbImageLoader.setLoadingView(convertView.findViewById(R.id.progressBar));
          //图片的下载
          
          
          return convertView;
    }
    
}
