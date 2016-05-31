package mno_ruili_app.adapter;

import java.util.List;

import mno.ruili_app.R;
import mno_ruili_app.nei.nei_bj_tag;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class mySimpleAdapter  extends BaseAdapter{
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
    
    public mySimpleAdapter(Context context ,List<tv> data,
            int resource, String[] from, int[] to){
    	this.mContext = context;
    	this.mData = data;
    	this.mResource = resource;
    	this.mFrom = from;
    	this.mTo = to;
        //用于将xml转为View
        this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
			   holder.itemsIcon = ((TextView) convertView.findViewById(mTo[0])) ;
			  // holder.itemsImage=((RelativeLayout) convertView.findViewById(mTo[1])) ;
			   convertView.setTag(holder);
          }else{
        	   holder = (ViewHolder) convertView.getTag();
          }
          
		  //获取该行的数据
          final tv mtv = (tv)mData.get(position);
          
          if(mtv.getMs().equals("1"))
          {
        	  Drawable drawable =mContext.getResources().getDrawable(R.drawable.radius_blue);
				//but_0.setBackgroundDrawable(drawable);
        	  holder.itemsIcon.setBackgroundDrawable(drawable);
        	  holder.itemsIcon.setTextColor(android.graphics.Color.parseColor("#ffffff"));;
          }
          //设置加载中的View
          holder.itemsIcon.setText(mtv.getName());
          return convertView;
    }
    
    /**
	 * View元素
	 */
	static class ViewHolder {
		TextView itemsIcon;
		//RelativeLayout itemsImage;
	}
}
