package mno_ruili_app.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import mno.ruili_app.my.AddEmpActivity;
import mno.ruili_app.my.AddWorkExp;
import mno_ruili_app.entity.Worker;
import mno_ruili_app.net.RequestType;
import mno_ruili_app.net.webhandler;
import mno_ruili_app.net.RequestType.Type;

import com.ab.util.AbViewHolder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AddEmpAdapter extends  BaseAdapter{
	//private static final boolean D = Constant.DEBUG;
  
	private Context mContext;
	//xml转View对象
    private LayoutInflater mInflater;
    //单行的布局
    private int mResource;
    //列表展现的数据
    private List<Worker> mData;
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
    public AddEmpAdapter(Context context, List<Worker> data,
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
    public View getView(final int position, View convertView, ViewGroup parent){
	      convertView = mInflater.inflate(mResource, parent, false);
                           
          TextView tv_jobName = AbViewHolder.get(convertView, mTo[0]);          
          TextView tv_salary= AbViewHolder.get(convertView, mTo[1]);         
          TextView tv_workTime = AbViewHolder.get(convertView, mTo[2]);
          TextView tv_workPlace = AbViewHolder.get(convertView, mTo[3]);
          TextView tv_edit = AbViewHolder.get(convertView, mTo[4]);
          TextView tv_delete = AbViewHolder.get(convertView, mTo[5]);
		  //获取该行的数据        
          final Worker workExp= (Worker)mData.get(position);
                   
  		  final String jobName=workExp.getWorkerId();
  		  tv_jobName.setText(jobName);
  		  
  		  final String salary=workExp.getJobName();
  		  tv_salary.setText(salary);
  		  
          final String workTime=workExp.getWorkTime();      
          tv_workTime.setText( workTime);
          
          final String workPlace=workExp.getEducation();     
          tv_workPlace.setText(workPlace);
          
          final String post_id=workExp.getSalary();
          
          final String eduLevel=workExp.getWorkPlace();     
          
          final String jobDuty=workExp.getWorkImagePath();     
          
          final String jobAsk=workExp.getNickName();     
          
          tv_edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent in=new Intent(mContext,AddEmpActivity.class);
				in.putExtra("from", "f");
				in.putExtra("jobName", jobName);
				in.putExtra("salary",salary);
				in.putExtra("workTime", workTime);
				in.putExtra("workPlace", workPlace);
				in.putExtra("eduLevel", eduLevel);
				in.putExtra("jobDuty", jobDuty);
				in.putExtra("jobAsk", jobAsk);
				mContext.startActivity(in);
			}
		});
          tv_delete.setOnClickListener(new OnClickListener() {
  			
  			@Override
  			public void onClick(View arg0) {
  				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setMessage("确定要删除当前选择的岗位吗？");
				builder.setTitle("提示");
				builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int which) {
						dialog.dismiss();
						webhandler handler1=new webhandler(){
		  					@Override
		  					public void OnResponse(JSONObject response) {
		  						// TODO Auto-generated method stub
		  						super.OnResponse(response);
		  					}
		  				};
		  				Map<String, String> params1 = new HashMap<String, String>();
		  				params1.put("post_id", post_id);
		  				handler1.SetRequest(new RequestType("ZP",Type.deletePost),params1);
		  				mData.remove(position);
		  				notifyDataSetChanged();
					}
				});
				builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int which) {
						dialog.dismiss();
					}
				});
				builder.create().show(); 				 				
  			}
  		});
           
          return convertView;
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
		if(min-60<0)
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
    
}


