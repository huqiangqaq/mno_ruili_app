package mno_ruili_app.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import mno.ruili_app.Constant;
import mno.ruili_app.R;
import mno.ruili_app.appuser;
import mno.ruili_app.ct.MessageBox;
import mno.ruili_app.my.my_grzy_item;
import mno_ruili_app.nei.nei_wdxq;
import mno_ruili_app.nei.wdfragment;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;

import com.ab.image.AbImageLoader;
import com.ab.util.AbViewHolder;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ImageListAdapter_pl2 extends  BaseAdapter{
	
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
    String id,id2;
   /**
    * 构造方法
    * @param context
    * @param data 列表展现的数据
    * @param resource 单行的布局
    * @param from Map中的key
    * @param to view的id
    */
    public ImageListAdapter_pl2(Context context, List<hd> data,
            int resource, String[] from, int[] to,String id){
    	this.mContext = context;
    	this.mData = data;
    	this.mResource = resource;
    	this.mFrom = from;
    	this.mTo = to;
    	this.id=id;
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
    
    boolean ok=true,chose=false;
    AlertDialog alertDialog_;
    //ImageView   nei_wd_cn;
    webhandler handler_;
    int mposition=0;
    @Override
    
    public View getView(final int position, View convertView, ViewGroup parent){
          if(convertView == null){
	           //使用自定义的list_items作为Layout
        	   
	           convertView = mInflater.inflate(mResource, parent, false);
          }
          
          if (Constant.itemzt.equals("已解决"))
        	  ok=false;
          else
        	  ok=true;
          if(appuser.getInstance().IsLogin())
	    	 {
          if (Constant.itemuid.equals(appuser.getInstance().getUserinfo().uid))
             {
        	  ok=true;
        	  if (Constant.itemzt.equals("已解决"))
            	  ok=false;
              else
            	  ok=true;
	    	 }
          else
        	  ok=false;
	    	 }
          else
        	  ok=false;
        
          init_Dialog();
          inithander();
          ImageView itemsIcon = AbViewHolder.get(convertView, mTo[0]);
          TextView username = AbViewHolder.get(convertView, mTo[1]);
          TextView create_at = AbViewHolder.get(convertView, mTo[2]);
          TextView content= AbViewHolder.get(convertView, mTo[3]);
          TextView floor = AbViewHolder.get(convertView, R.id.pl_floor);
          Button nei_lzcn= AbViewHolder.get(convertView, R.id.nei_lzcn);
          TextView nei_wd_cn= AbViewHolder.get(convertView, R.id.nei_wd_cn);
          nei_wd_cn.setVisibility(View.GONE);
          nei_lzcn.setVisibility(View.INVISIBLE);
		  //获取该行的数据
          int floornum=mData.size()-position;
          floor.setText("第"+floornum+"楼");
          //final tv mtv = (tv)mData.get(position);
          //String a= mData.get(position).get(mFrom[0]).toString();
          if(ok)
          {
        	  nei_lzcn.setVisibility(View.VISIBLE);
          }
          else
        	  nei_lzcn.setVisibility(View.INVISIBLE);
          
          final hd mzx= (hd)mData.get(position);
        
          //评论被采纳了
          if(mzx.getislike().equals("1"))
          {
        	  //nei_lzcn.setVisibility(View.INVISIBLE);
        	  nei_wd_cn.setVisibility(View.VISIBLE);
        	  
          }

		  if(chose)
		  {
			  nei_lzcn.setVisibility(View.INVISIBLE);
		  }
			 
		  
		  itemsIcon.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					 Intent intent = new Intent(mContext,my_grzy_item.class);
				        intent.putExtra("img",mzx.getImg());  
						intent.putExtra("ID",mzx.getuid());  
						intent.putExtra("name",mzx.getName());  
						 mContext.startActivity(intent);
					//MessageBox.Show(mContext, mzx.getContent());
				}
        });
         
          String name=mzx.getName();
          if(name.length()>0)
          username.setText(mzx.getName());
          else
          username.setText("该用户无昵称");
          content.setText(mzx.getContent());
          create_at.setText(get(mzx.gettime()));

          String imageUrl =RequestType.getWebUrl_(mzx.getImg());
          //设置加载中的View
          //String path="http://b264.photo.store.qq.com/psb?/V14av8GK1voYCO/BPkJ.Gv0rDTo0MGFlxDb7ze4vPxjxPztOkaZHqVgdYI!/b/dIBJXp0lFAAA&bo=*gFmAQAAAAABB7g!&rf=viewer_4";
          //itemsIcon.setImageUrl(path,webpost.getImageLoader());
         // mAbImageLoader.setLoadingView(convertView.findViewById(R.id.progressBar));
          //图片的下载
          mAbImageLoader.display(itemsIcon,imageUrl);
          nei_lzcn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					alertDialog_.show();
					mposition=position;
					//MessageBox.Show(mContext, mzx.getContent());
				}
          });
          return convertView;
    }
    public void init_Dialog()
	{
		Builder builder = new Builder(mContext);
		//builder.setTitle("		是否联系此用户？");
		builder.setMessage("是否将该回答采纳为最佳答案 ？");
		//builder.setIcon(R.drawable.cat);

		DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				if (arg1 == DialogInterface.BUTTON_POSITIVE) 
				{
					arg0.cancel();
					
				} else if (arg1 == DialogInterface.BUTTON_NEGATIVE) {
					final hd mzx= (hd)mData.get(mposition);
					 id2=mzx.getId();
					 Map<String, String> params = new HashMap<String, String>();
					 params.put("questionId",id );
				     params.put("answerId",id2);
				 
			        handler_.SetRequest(new RequestType("3",Type.chooseAnswer),params);
			        chose=true;
					
			        Constant.itemzt="已解决";
					mzx.setislike("1");
					notifyDataSetChanged();
					
				}
			}
		};
		builder.setPositiveButton("取消", dialog);
		builder.setNegativeButton("确定", dialog);
		alertDialog_ = builder.create();
	}
    private String get(String time) {
		// TODO Auto-generated method stub
		//定义时间格式
		 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
		//取的两个时间
		Date dt=new Date();
		 
		//透过SimpleDateFormat的format方法将Date转为字符串
		 
		String dts=sdf.format(dt);
		Date dt1 = null;
		Date dt2 = null;
		try {
			dt1 = sdf.parse(time);
			dt2 =sdf.parse(dts);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//取得两个时间的Unix时间
		 
		Long ut1=dt1.getTime();
		Long ut2=dt2.getTime();
		//相减获得两个时间差距的毫秒 
		Long timeP=ut2-ut1;//毫秒差 
		Long sec=timeP/1000;//秒差
		Long min=timeP/1000/60;//分差
		Long hr=timeP/1000/60/60;//时差
		Long day=timeP/1000/60/60/24;//日差
		Long month =day/30;
		Long year =day/30/12;
		String age = null;
		if(min-1<0)
			age="刚刚";
		else if(min-59<0)
			age=min.toString()+"分钟前";
		else if(hr-24<0)
			age=hr.toString()+"小时前";
		else if(day-30<0)
			age=day.toString()+"天前";
		else if(month-12<0)
			age =month.toString()+"月前";
		else 
			age =year.toString()+"年前";
		return age;
	}
    private void inithander() {
		// TODO Auto-generated method stub
		handler_= new webhandler(){

			@Override
			public void OnResponse(JSONObject response) {
				// TODO Auto-generated method stub
				super.OnResponse(response);
				String mess;
				try {
					mess = response.getString("message");
					
					if(mess.equals("null") == false && mess.length() > 1)
					{
						Toast.makeText(mContext, mess, Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void OnError(int code, String mess) {
				// TODO Auto-generated method stub
				super.OnError(code, mess);
				String ms=mess;
				ms=ms+"";
				if(ms.trim().equals("无法连接网络"))
				{
					
				}
				else
				MessageBox.Show(mContext,mess);
			}};
			
	}
    
}
