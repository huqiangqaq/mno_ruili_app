package mno_ruili_app.adapter;

import java.util.ArrayList;
import java.util.List;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.ct.HorizontalListView;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.ct.RoundImageView;
import mno.ruili_app.main.NeighborFragment;
import mno.ruili_app.my.my_grzy_item;
import mno_ruili_app.adapter.ImageGridAdapter.ViewHolder;
import mno_ruili_app.nei.nei_find;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webpost;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.ab.image.AbImageLoader;
import com.ab.util.AbViewHolder;

public class ImageListAdapter_wd extends BaseAdapter{
	
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
   int where=0;
    
   /**
    * 构造方法
    * @param context
    * @param data 列表展现的数据
    * @param resource 单行的布局
    * @param from Map中的key
    * @param to view的id
    */
    public ImageListAdapter_wd(Context context, List<wd> data,
            int resource, String[] from, int[] to,int i){
    	this.mContext = context;
    	this.mData = data;
    	this.mResource = resource;
    	this.mFrom = from;
    	this.mTo = to;
        //用于将xml转为View
        this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //图片下载器
        where=i;
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
    /**
	 * View元素
	 */
	static class ViewHolder {
		RoundImageView my_login_image;
		TextView wdname;
		HorizontalListView tag;
		TextView statusName;
		TextView wdtime;
		TextView wdcontent;
		TextView wdbestname;
		TextView wdbestcon;
		TextView wdhd;
		TextView wdfs;
		TextView wdrs;
		TextView wdbeshd;
		LinearLayout best;
	}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
    	final ViewHolder holder;
    	 if(convertView == null){
	           convertView = mInflater.inflate(mResource, parent, false);
			   holder = new ViewHolder();
			   holder.my_login_image = ((RoundImageView) convertView.findViewById(mTo[0])) ;
			   holder.wdname = ((TextView) convertView.findViewById(mTo[1])) ;
			   holder.tag=((HorizontalListView) convertView.findViewById(mTo[2])) ;
			   holder.statusName =((TextView) convertView.findViewById(mTo[3])) ;
			   holder.wdtime =((TextView) convertView.findViewById(mTo[4])) ;
			   
			   holder.wdcontent =((TextView) convertView.findViewById(mTo[5])) ;
			   holder.wdbestname =((TextView) convertView.findViewById(mTo[6])) ;
			   holder.wdbestcon =((TextView) convertView.findViewById(mTo[7])) ;
			   holder.wdfs =((TextView) convertView.findViewById(mTo[8])) ;
			   holder.wdhd =((TextView) convertView.findViewById(mTo[9])) ;
			   holder.wdbeshd =((TextView) convertView.findViewById(mTo[10])) ;
			   holder.wdrs =((TextView) convertView.findViewById(mTo[11])) ;
			   holder.best=((LinearLayout) convertView.findViewById( R.id.best)) ;
			   
			   convertView.setTag(holder);
        }else{
      	   holder = (ViewHolder) convertView.getTag();
        }
          
       
    	 holder.best.setVisibility(View.VISIBLE);
          final wd mzx= (wd)mData.get(position);
          holder.wdname.setText(mzx.getName());
          holder.wdtime.setText(mzx.gettime());
          
          /*     if(mzx.getbestAnwUname().length()>0)
               { 
             	 }
               else
               {
             	 
             	  }*/
               holder.wdfs.setText(mzx.getfs());
               holder. wdrs.setText(mzx.getanswerTotal());
              /* if(mzx.getImg().length()>5)
               {*/
               String imageUrl =RequestType.getWebUrl_(mzx.getImg());
               holder.my_login_image.setVisibility(View.VISIBLE);
               holder. my_login_image.setImageUrl(imageUrl,webpost.getImageLoader());
               holder. my_login_image.setErrorImageResId(R.drawable.my_up);
              /* }
               else
               {
            	   holder.my_login_image.setVisibility(View.GONE);
               }*/
          
          if(mzx.getName().length()>0)
        	  holder.wdhd.setText("提问： ");
          String content =mzx.getContent();
          if(content.length()>50)
          {
        	  content=content.substring(0, 49)+"...";
          }
          content= Html.fromHtml(content).toString();
          holder.wdcontent.setText("               "+content);
          
          mySimpleAdapter saImageItems;
          final ArrayList<tv>   mchoseList = new ArrayList<tv>();
          String[] pic;
          pic  = mzx.getTag().split(",");
		
          for (int i = 0; i <pic.length; i++) { 
              
              final tv mtv = new tv(pic[i], "0","");
              mchoseList.add(mtv);
            } 
          saImageItems = new mySimpleAdapter(mContext, //没什么解释  
          		mchoseList,//数据来源   
                    R.layout.item_tag_wd,//night_item的XML实现  
                      
                    //动态数组与ImageItem对应的子项          
                    new String[] {"textItem"},   
                      
                    //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
                    new int[] {R.id.but_tag});  
          //holder.tag.setHorizontalSpacing(10);
          //holder.tag.setPadding(0, 0, 0, 0);
          
       /*
          if(where==1)  
              holder.tag.setNumColumns(5);
              else
           	   holder.tag.setNumColumns(4);
	      holder.tag.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);*/
          holder.tag.setAdapter(saImageItems);
          //tag.setText(mzx.getTag().replaceAll(",", "   "));
          holder.tag.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if(mchoseList.get(position).getName().toString().trim().length()<=0)
						return;
					MessageBox.Show(mContext, mchoseList.get(position).getName());
					Intent intent0 = new Intent(mContext, nei_find.class); 
					intent0.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					intent0.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
	        		Constant.wd_find=mchoseList.get(position).getName();
	        		Constant.fromwd="1";
	        		mContext.startActivity(intent0);
					
				}
				});
          holder.my_login_image.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
			       Intent intent = new Intent(mContext,my_grzy_item.class);
			       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				   intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
			        intent.putExtra("img",mzx.getImg());  
					intent.putExtra("ID",mzx.getuid());  
					intent.putExtra("name",mzx.getName());  
					 mContext.startActivity(intent);
					// TODO Auto-generated method stub
				}});
          
          if(mzx.getzt().equals("已解决"))
          {
        	 
        	  
        	  
        	  holder.best.setVisibility(View.VISIBLE);
        	  holder.wdbestcon.setVisibility(View.VISIBLE);
        	  holder.wdbeshd.setVisibility(View.VISIBLE);
        	  holder.statusName.setVisibility(View.VISIBLE);
        	  
        	  holder. wdbestname.setText(mzx.getbestAnwUname());
        	  holder. wdbeshd.setText("回答 ：");
        	  holder. wdbestcon.setText(mzx.getbestAnwCon());
        	  if(mzx.getbestAnwCon().equals("null"))
        	  {
/*        		  holder. wdbestname.setText("用户已删除该评论");
        		  holder. wdbeshd.setText("");
        		  holder. wdbestcon.setText("");*/
        		  holder.best.setVisibility(View.GONE);
            	  holder.statusName.setVisibility(View.GONE);
            	  
            	  holder.wdbestcon.setVisibility(View.INVISIBLE);
            	  holder.wdbeshd.setVisibility(View.INVISIBLE);
        	  }
        	  if(mzx.getbestAnwCon().trim().length()<=0)
                  holder.best.setVisibility(View.GONE);
         	  else
         		  holder.best.setVisibility(View.VISIBLE);
          }
          else
          {
        	 
        		  
        	  holder.best.setVisibility(View.GONE);
        	  holder.statusName.setVisibility(View.GONE);
        	  
        	  holder.wdbestcon.setVisibility(View.INVISIBLE);
        	  holder.wdbeshd.setVisibility(View.INVISIBLE);
          }
          //statusName.setText(mzx.getzt());
         
          return convertView;
    }
    
}
