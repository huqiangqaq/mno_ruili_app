package mno_ruili_app.net;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mno.ruili_app.Constant;
import mno.ruili_app.appuser;
import mno.ruili_app.my.mylogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


public abstract class webpost {
	static Context context_;
	public static ImageLoader imageLoader;
	static RequestQueue requestQueue;
	
	public static void init(Context context){
		context_=context;
		if(requestQueue == null)
		{
			requestQueue = Volley.newRequestQueue(context); 
			requestQueue.start();
			
		}
		if(imageLoader == null)
		{
			imageLoader = new ImageLoader(requestQueue, new BitmapCache()); 
		}
	/*	
		requestQueue= Volley.newRequestQueue(context_);
		requestQueue.start();*/
		/*if(Queue_ == null)
		{
			Queue_ = Volley.newRequestQueue(context); 
			Queue_.start();
			
		}
		
		if(imageLoader == null)
		{
			imageLoader = new ImageLoader(Queue_, new BitmapCache()); 
		}*/
	}
	public static ImageLoader getImageLoader()
	{
		return imageLoader;
	}
	//1.
	public void SetRequest(RequestType Request,Map<String, String> param)
	{
	//1.1
	String httpurl  = Request.getUrl();
	if( Request.getType() == RequestType.Type.login)//注册不能传access_token
	{
		
	}
	if( Request.getType() == RequestType.Type.register)//注册不能传access_token
	{
		
	}
	else if( Request.getType() == RequestType.Type.checkPhone)
	{
		
	}

	else if( Request.getType() == RequestType.Type.getUserBaseInt)
	{
		
	}
	else if( Request.getType() == RequestType.Type.getSbQA)
	{
		
	}
	else if( Request.getType() == RequestType.Type.getMyQA)
	{
		
	}
	
	else if( Request.getType() == RequestType.Type.getSbReply)
	{
		
	}
	else if( Request.getType() == RequestType.Type.getCourseRecord)
	{
		
	}
	else if( Request.getType() == RequestType.Type.getNewsReply)
	{
		
	}
	else if( Request.getType() == RequestType.Type.getCourseDetail)
	{
		
	}
	else if( Request.getType() == RequestType.Type.getResonList)
	{
		
	}
	else if( Request.getType() == RequestType.Type.getQuesDetail)
	{
		
	}
	else if( Request.getType() == RequestType.Type.resetcode)
	{
		
	}
	//新版首页3.0版
	else if( Request.getType() == RequestType.Type.newCourse)
	{
		//param.put("accessCode",  Constant.acc);
	}
	//获取消息
	else if( Request.getType() == RequestType.Type.getMsg)
	{
		param.put("uid",  Constant.uid);
	}
	//获取系统消息详情
	else if( Request.getType() == RequestType.Type.getMsgDetail)
	{
		
	}
	//获取职位分类接口
	else if( Request.getType() == RequestType.Type.getJobCate)
	{
		
	}
	//清除消息提示的红点
	else if( Request.getType() == RequestType.Type.msgView)
	{
		
	}
	//检测版本更新
	else if( Request.getType() == RequestType.Type.getNewSign)
	{
		param.put("accessCode",  Constant.acc);
	}
	//
	else if( Request.getType() == RequestType.Type.clearRedPoint)
	{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);
	}
	//修改密码
	else if( Request.getType() == RequestType.Type.updatecode)
	{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);
	}
	//岗位列表接口
	else if( Request.getType() == RequestType.Type.getPostList)
	{
		
	}
	//岗位详情接口
	else if( Request.getType() == RequestType.Type.getPostDetail)
	{
		
	}
	//简历列表接口
	else if( Request.getType() == RequestType.Type.getResumeList)
	{
		
	}
	//简历详情接口
	else if( Request.getType() == RequestType.Type.getResumeDetail)
	{
		
	}
	//获取简历id
	else if( Request.getType() == RequestType.Type.getResumeIdByUid)
	{
		
	}
	//保存个人简历
	else if( Request.getType() == RequestType.Type.saveResume)
	{
		
	}
	//获取薪水
	else if( Request.getType() == RequestType.Type.getSalary)
	{
		
	}
	//获取工作经验年份
	else if( Request.getType() == RequestType.Type.getJobYear)
	{
		
	}
	//获取工作经验年份
	else if( Request.getType() == RequestType.Type.getEducation)
	{
		
	}
	//获取发布时间
	else if( Request.getType() == RequestType.Type.getPublishDate)
	{
		
	}
	//创建工作经验并返回id
	else if( Request.getType() == RequestType.Type.createJobExp)
	{
		
	}
	//创建学习经验并返回id
	else if( Request.getType() == RequestType.Type.createStuExp)
	{
		
	}
	//保存简历的工作经验
	else if( Request.getType() == RequestType.Type.saveJobExp)
	{
		
	}
	//保存简历的学习经验
	else if( Request.getType() == RequestType.Type.saveStuExp)
	{
		
	}
	//自己的简历接口
	else if( Request.getType() == RequestType.Type.getMyResume)
	{
		
	}
	//获取用户的昵称和头像
	else if( Request.getType() == RequestType.Type.getUserNameAndPhoto)
	{
		
	}
	//创建岗位并返回id
	else if( Request.getType() == RequestType.Type.createPost)
	{
		
	}
	//保存公司的工作岗位
	else if( Request.getType() == RequestType.Type.savePost)
	{
		
	}
	//自己的公司详情接口
	else if( Request.getType() == RequestType.Type.getCompDetail)
	{
		
	}
	//上传公司的资质图片
	else if( Request.getType() == RequestType.Type.uploadCompInfo)
	{
		
	}
	//保存公司信息
	else if( Request.getType() == RequestType.Type.saveComp)
	{
		
	}
	//删除工作经验
	else if( Request.getType() == RequestType.Type.deleteJobExp)
	{
		
	}
	//删除学习经历
	else if( Request.getType() == RequestType.Type.deleteStuExp)
	{
		
	}
	//删除岗位
	else if( Request.getType() == RequestType.Type.deletePost)
	{
		
	}
	//发邮件到公司
	else if( Request.getType() == RequestType.Type.sendEmailToComp)
	{
		
	}
	//发邮件到个人
	else if( Request.getType() == RequestType.Type.sendEmailToPerson)
	{
		
	}
	//用户资料填写状态
	else if( Request.getType() == RequestType.Type.getCompStatus)
	{
		
	}
	else if( Request.getType() == RequestType.Type.setOrder)
	{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);
	}
	else if( Request.getType() == RequestType.Type.getPayment)
	{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);
	}
	//获取红点
	else if( Request.getType() == RequestType.Type.getRedPoint)
	{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);
	}
	else if( Request.getType() == RequestType.Type.chooseAnswer)
	{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);
	}
	else if( Request.getType() == RequestType.Type.CourseRecord)
	{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);
	}
	else if( Request.getType() == RequestType.Type.getProduct)
	{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);
	}
	else if( Request.getType() == RequestType.Type.signIn)
	{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);
	}
	else if( Request.getType() == RequestType.Type.feedback)
	{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);
	}
	else if( Request.getType() == RequestType.Type.getPointRule)
	{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);
	}
	else if( Request.getType() == RequestType.Type.delMyData)
	{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);
	}
	else if( Request.getType() == RequestType.Type.getMyColl)
	{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);
	}
	//用户修改资料
	else if( Request.getType() == RequestType.Type.editInfo)
	{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);
	}
	else if( Request.getType() == RequestType.Type.addReply)
	{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);
	}
	else if( Request.getType() == RequestType.Type.addAnswer)
	{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);
	}
	else if( Request.getType() == RequestType.Type.addLike)
	{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);
	}
	else if( Request.getType() == RequestType.Type.updateQues)
	{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);
	}
	else if( Request.getType() == RequestType.Type.createQues)
	{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);
	}
	else if( Request.getType() == RequestType.Type.getLiveCourse)
	{
		if(appuser.getInstance().IsLogin())
		{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);}
		else
			param.put("accessCode",  Constant.accessCode);
	}
	else if( Request.getType() == RequestType.Type.getLivePage)
	{
		if(appuser.getInstance().IsLogin())
		{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);}
		else
			param.put("accessCode",  Constant.accessCode);
	}
	else if( Request.getType() == RequestType.Type.getCourseByDate)
	{
		if(appuser.getInstance().IsLogin())
		{
		param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.acc);}
		else
			param.put("accessCode",  Constant.accessCode);
	}
	
	
	else
	{
		//param.put("uid",  Constant.uid);
		param.put("accessCode",  Constant.accessCode);
	}
	
	//1.2使用volley框架进行网络请求
	Request<JSONObject> request = new NormalPostRequest(httpurl,new Response.Listener<JSONObject>() {
	        @Override
	        public void onResponse(JSONObject response) {	        	
	        	  Log.d("web", "response -> " + response.toString());		            
				try {
					int ret_code;
					String mess = null;
					ret_code = response.getInt("code");
					mess= response.getString("message");
					if(ret_code == 0){
						webpost.this.OnResponse(response);//此时调用534行的方法
						//mess = "请登陆";
						webpost.this.OnFinish();//此时调用538行的方法
						//AppUser.getInstance().LogOut();
						 return;
					}else{
						if(mess.equals("登陆失败")){							
						   Intent intent = new Intent(context_, mylogin.class); 
						   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
							intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
						   context_.startActivity(intent);						   
						}
						 webpost.this.OnError(ret_code,mess);//此时调用536行的方法
					}									
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				webpost.this.OnFinish();//此时调用538行的方法	            
	        }	        	        
	    }, new Response.ErrorListener() {
	        @Override
	        public void onErrorResponse(VolleyError error) {
	        	if( error.networkResponse == null){
					webpost.this.OnError(-1,"无法连接网络");//此时调用536行的方法
				}
				else{
					try{
						webpost.this.OnError(-1,error.getMessage().toString());//此时调用536行的方法							
					} catch (Exception e) {
					}	
				}								
	        	webpost.this.OnFinish();//此时调用538行的方法
	        }
	    }, param);	
	requestQueue.add(request);
	this.OnStart();//此时调用532行的方法
	}
	//2.自定义继承自volley框架下的Request<JSONObject>
	private class NormalPostRequest extends Request<JSONObject> {
	    private Map<String, String> mMap;
	    private Listener<JSONObject> mListener;
	    //2.1
	    public NormalPostRequest(String url, Listener<JSONObject> listener,ErrorListener errorListener, Map<String, String> map) {
	        super(Request.Method.POST, url, errorListener);
	             
	        mListener = listener;
	        mMap = map;
	    }
	     
	    //2.2.mMap是已经按照前面的方式,设置了参数的实例
	    @Override
	    protected Map<String, String> getParams() throws AuthFailureError {
	        return mMap;
	    }
	     
	    //2.3.此处因为response返回值需要json数据,和JsonObjectRequest类一样即可
	    @Override
	    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
	        try {
	            String jsonString = new String(response.data,HttpHeaderParser.parseCharset(response.headers));
	                 
	            return Response.success(new JSONObject(jsonString),HttpHeaderParser.parseCacheHeaders(response));
	        } catch (UnsupportedEncodingException e) {
	            return Response.error(new ParseError(e));
	        } catch (JSONException je) {
	            return Response.error(new ParseError(je));
	        }
	    }
	    //2.4
	    @Override
	    protected void deliverResponse(JSONObject response) {
	        mListener.onResponse(response);
	    }
	}
	//3.
	public   abstract  void OnStart();

	public   abstract  void OnResponse(JSONObject response);

	public abstract void OnError(int code,String mess);

	public abstract void OnFinish();
	//4.
	public  static Map<String, Object> getMap(JSONObject jsonObject)   
	 {    
	     try  
	     {   
	      Iterator<String> keyIter = jsonObject.keys();   
	      String key;   
	      Object value;   
	      Map<String, Object> valueMap = new HashMap<String, Object>();   
	      while (keyIter.hasNext())   
	      {   
	       key = (String) keyIter.next();   
	       value = jsonObject.get(key);   
	        valueMap.put(key, value);   
	      }   
	      return valueMap;   
	     }   
	      catch (JSONException e)   
	      {   
	          
	      }   
	     
	     return null;   
	 }   
	
	//5.
	public static  List<Map<String, Object>> getList(JSONArray jsonArray)   
	    {   
	      List<Map<String, Object>> list = null;   
	      try  
	      {      
	    	  JSONObject jsonObject;   
	       	  list = new ArrayList<Map<String, Object>>();   
	          for (int i = 0; i < jsonArray.length(); i++)   
	       	 {   
              jsonObject = jsonArray.getJSONObject(i);   
	          list.add(webpost.getMap(jsonObject));
	          }   
	     }   
	      catch (Exception e)   
	      {   
	         
	      }   
	      return list;   
   }   
}
