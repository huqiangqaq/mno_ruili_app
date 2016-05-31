package mno_ruili_app.adapter;

import java.util.List;

import mno.ruili_app.Constant;
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


public class ImageGridAdapter2 extends BaseAdapter{
	  
		private Context mContext;
		//xml转View对象
	    private LayoutInflater mInflater;
	    //单行的布局
	  
	    private List<tv> mData;

	    
	   /**
	    * 构造方法
	    * @param context
	    * @param data 列表展现的数据
	    * @param resource 单行的布局
	    * @param from Map中的key
	    * @param to view的id
	    */
	    public ImageGridAdapter2(Context context ,List<tv> data
	          ){
	    	this.mContext = context;
	    	
	    	this.mData = data;

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
		           convertView = mInflater.inflate(R.layout.item_grid2, parent, false);
				   holder = new ViewHolder();
				   holder.itemsIcon = ((ImageView) convertView.findViewById(R.id.itemsIcon)) ;
				  
				   holder.itemsImage=((RelativeLayout) convertView.findViewById(R.id.all)) ;
				   holder.itemsText2 =((TextView) convertView.findViewById(R.id.itemstext2)) ;
				   holder.itemsText =((TextView) convertView.findViewById(R.id.itemstext)) ;
				   convertView.setTag(holder);
	          }else{
	        	   holder = (ViewHolder) convertView.getTag();
	          }
	          
			  //获取该行的数据
	          final tv mtv = (tv)mData.get(position);
	          LayoutParams lp;        
	          lp=holder.itemsImage.getLayoutParams();
	          
	          lp.height=Constant.displayWidth/3*10/16;        
	          holder.itemsImage.setLayoutParams(lp); 
	         String name=mtv.getName().substring(0, mtv.getName().indexOf("."));
	         String ms=mtv.getMs().substring(0, mtv.getMs().indexOf("."));
	          if(mtv.getImg().equals("1"))
	          {//my_sex.setImageDrawable(getResources().getDrawable(R.drawable.my_sex_man));
	        	  holder.itemsText.setText(name+"元/月");
		          holder.itemsText2.setText(ms+"元/月");
	        	  holder.itemsIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.buy_a));
	          }
	          if(mtv.getImg().equals("6"))
	          {
	        	  holder.itemsText.setText(name+"元/半年");
		          holder.itemsText2.setText(ms+"元/半年");
	        	  holder.itemsIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.buy_b));
	          }
	          if(mtv.getImg().equals("12"))
	          {
	        	  holder.itemsText.setText(name+"元/年");
		          holder.itemsText2.setText(ms+"元/年");
	        	  holder.itemsIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.buy_c));
	          }
	          return convertView;
	    }
	    
	    /**
		 * View元素
		 */
		static class ViewHolder {
			ImageView itemsIcon;
			RelativeLayout itemsImage;
			TextView itemsText2;
			TextView itemsText;
		}
	    
	}
