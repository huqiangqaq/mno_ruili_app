package mno_ruili_app.adapter;




import java.util.List;
import java.util.Map;

import mno.ruili_app.R;
import mno.ruili_app.ct.RoundImageView;
import mno.ruili_app.ct.RoundimgView;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webpost;
import android.content.Context;
import android.graphics.drawable.Drawable;
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
public class ImageListAdapter_myxf extends BaseAdapter{
	
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
    
   /**
    * 构造方法
    * @param context
    * @param data 列表展现的数据
    * @param resource 单行的布局
    * @param from Map中的key
    * @param to view的id
    */
    public ImageListAdapter_myxf(Context context, List<tv> data,
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
    TextView itemstext3,itemsText,itemsText2;
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
          if(convertView == null){
	           //使用自定义的list_items作为Layout
	           convertView = mInflater.inflate(mResource, parent, false);
          }
          final tv mtv = (tv)mData.get(position);
          
         
         
          itemsText = AbViewHolder.get(convertView, mTo[0]);
          itemsText2 = AbViewHolder.get(convertView, mTo[1]);
          itemstext3 = AbViewHolder.get(convertView, mTo[2]);
          String name=mtv.getName();
          String zf=mtv.getImg();
          if(zf.equals("获得"))
          {
        	  itemsText2.setText("+"+mtv.getMs());
        	  itemsText2.setTextColor(android.graphics.Color.parseColor("#fe0100"));
        	  itemstext3.setTextColor(android.graphics.Color.parseColor("#fe0100"));
        	  itemstext3.setText("+"+mtv.getid());
          }else{
        	  itemsText2.setText("-"+mtv.getMs());
        	  itemsText2.setTextColor(android.graphics.Color.parseColor("#3ab527"));
        	  itemstext3.setTextColor(android.graphics.Color.parseColor("#3ab527"));
        	  itemstext3.setText("-"+mtv.getid());
          }
        	 
          if(name.equals("登陆"))//没有登陆的情况
          {
	          Drawable pic= mContext.getResources().getDrawable( R.drawable.xf_zc); 
	          itemsText.setText(name);
	          itemsText.setCompoundDrawablesWithIntrinsicBounds(pic, null,null,null);
          }
          else if(name.equals("回帖"))//2.
          {
              Drawable pic= mContext.getResources().getDrawable( R.drawable.xf_ht); 
              itemsText.setText(name);
              itemsText.setCompoundDrawablesWithIntrinsicBounds(pic,null, null,null);
           }
          else if(name.equals("收藏"))//4.
          {
              Drawable pic= mContext.getResources().getDrawable( R.drawable.xf_sc); 
              itemsText.setText(name);
              itemsText.setCompoundDrawablesWithIntrinsicBounds(pic,null, null,null);
          }
          else if(name.equals("点赞"))//3.
          {
              Drawable pic= mContext.getResources().getDrawable( R.drawable.xf_dz); 
              itemsText.setText(name);
              itemsText.setCompoundDrawablesWithIntrinsicBounds(pic,null, null,null);
          }
          else if(name.equals("分享"))//5.
          {
              Drawable pic= mContext.getResources().getDrawable( R.drawable.xf_fx); 
              itemsText.setText(name);
              itemsText.setCompoundDrawablesWithIntrinsicBounds(pic,null, null,null);
          }
          else if(name.equals("悬赏"))//8.
          {
              Drawable pic= mContext.getResources().getDrawable( R.drawable.xf_xs); 
              itemsText.setText(name);
              itemsText.setCompoundDrawablesWithIntrinsicBounds(pic,null, null,null);
              }
          else if(name.equals("邀请码"))//9.
          {
              Drawable pic= mContext.getResources().getDrawable( R.drawable.xf_yq); 
              itemsText.setText(name);
              itemsText.setCompoundDrawablesWithIntrinsicBounds(pic,null, null,null);
          }
          else if(name.equals("签到"))//1.
          {
              Drawable pic= mContext.getResources().getDrawable( R.drawable.xf_qd); 
              itemsText.setText(name);
              itemsText.setCompoundDrawablesWithIntrinsicBounds(pic,null, null,null);
          }
          else if(name.equals("提问"))//6.
          {
              Drawable pic= mContext.getResources().getDrawable( R.drawable.xf_tw); 
              itemsText.setText(name);
              itemsText.setCompoundDrawablesWithIntrinsicBounds(pic,null, null,null);
          }
          else if(name.equals("回答"))//7.
          {
              Drawable pic= mContext.getResources().getDrawable( R.drawable.xf_ht); 
              itemsText.setText(name);
              itemsText.setCompoundDrawablesWithIntrinsicBounds(pic,null, null,null);
          }
          else if(name.equals("注册"))//10.
          {
              Drawable pic= mContext.getResources().getDrawable( R.drawable.xf_zc); 
              itemsText.setText(name);
              itemsText.setCompoundDrawablesWithIntrinsicBounds(pic,null, null,null);
          }
          else{
        	  itemsText.setText(name);
          }        	                         
          return convertView;
    }
    
}
