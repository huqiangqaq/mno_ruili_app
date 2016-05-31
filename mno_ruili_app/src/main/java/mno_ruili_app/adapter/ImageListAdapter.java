package mno_ruili_app.adapter;




import java.util.List;
import java.util.Map;

import mno.ruili_app.R;
import mno.ruili_app.ct.RoundImageView;
import mno.ruili_app.ct.RoundimgView;
import mno_ruili_app.adapter.ImageListAdapter_zx.ViewHolder;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webpost;
import android.content.Context;
import android.text.Html;
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
public class ImageListAdapter extends BaseAdapter{
	
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
  
    
   /**
    * 构造方法
    * @param context
    * @param data 列表展现的数据
    * @param resource 单行的布局
    * @param from Map中的key
    * @param to view的id
    */
    public ImageListAdapter(Context context, List<tv> data,
            int resource, String[] from, int[] to){
    	this.mContext = context;
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
    static class ViewHolder {
   		NetworkImageView itemsIcon;
   		TextView itemstext;
   		TextView itemstext2;
   		//TextView itemstext3;
   		
   	}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
    	final ViewHolder holder;  
    	if(convertView == null){
      	  
	           //使用自定义的list_items作为Layout
	           convertView = mInflater.inflate(mResource, parent, false);
	           holder = new ViewHolder();
	           try
	           {
	           holder.itemsIcon = ((NetworkImageView) convertView.findViewById(mTo[0])) ;
	           holder.itemsIcon.setDefaultImageResId(R.drawable.image_empty);
			   holder.itemsIcon.setErrorImageResId(R.drawable.image_error);
	           }
	           catch(Exception e)
	           {}
			 
			   holder.itemstext =((TextView) convertView.findViewById(mTo[1])) ;
			   holder.itemstext2 =((TextView) convertView.findViewById(mTo[2])) ;
			   convertView.setTag(holder);
    		}
    	else{
    		holder = (ViewHolder) convertView.getTag();
 			}
          final tv mtv = (tv)mData.get(position);
          
          String imageUrl =RequestType.getWebUrl_(mtv.getImg());

          if(mTo.length>2)  
          {
        	  String name=mtv.getName();
              String content=mtv.getMs();
              if(name.length()>15)
              {
              name=name.substring(0, 13)+"...";
              }
              if(content.length()>20)
              {
            	 content=content.substring(0, 18)+"...";
              }
              
        	  holder.itemstext.setText(name);
        	  holder.itemstext2.setText(Html.fromHtml(content).toString());
          }
          try
          {
          //图片的下载
          if(!(imageUrl.length()<3))
          	 holder.itemsIcon.setImageUrl(imageUrl,webpost.getImageLoader());
          }catch(Exception e)
          {}
         
          
          return convertView;
    }
    
}
