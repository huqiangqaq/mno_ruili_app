package mno_ruili_app.net;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import android.util.Log;

public class RequestType {
		
	private Map<String,Object> params_;
	protected  Type   type_;
	protected  String   api_; 
	//private static String root= "http://dsdsx.com:8080/";//测试
	private static String root= "http://dsdsx.com:/";//正式
	public static String uploadImg= root+"ApiZP/uploadCompInfo";//上传公司纸质图片
	// private String base_url_ = "http://192.168.1.128/Ajax/API/%s.html?signature=%s&timestamp=%s&nonce=%s";
	private String base_url= root+"Api%s/%s";
	// http://www.yuetingfengsong.com:8087/Api/register
	public enum Type{
		getCompStatus,//用户资料填写状态
		newCourse,//新版首页3.0版
		sendEmailToPerson,//发邮件到个人
		sendEmailToComp,//发邮件到公司
		deletePost,//删除岗位
		deleteStuExp,//删除学习经历
		deleteJobExp,//删除工作经验
		getUserNameAndPhoto,//获取用户的昵称和头像
		getPublishDate,//获取发布日期
		saveComp,//保存公司信息
		uploadCompInfo,//上传公司的资质图片
		getCompDetail,//自己的公司详情接口
		savePost,//保存公司的工作岗位
		createPost,//创建岗位并返回id
		getMyResume,//自己的简历接口
		createJobExp,//创建工作经验并返回id
		createStuExp,//创建学习经历并返回id
		saveJobExp,//保存简历的工作经验
		saveStuExp,//保存简历的学习经验
		getEducation,//获取学历
		getJobYear,//获取工作经验年份
		getSalary,//获取薪水
		getResumeIdByUid,//获取简历id
		saveResume,//保存个人简历
		getResumeDetail,//简历详情接口
		getPostDetail,//岗位详情接口
		getResumeList,//简历列表接口
		getPostList,//岗位列表接口
		getJobCate,//职位分类
		msgView,//清除消息的红点
		getMsgDetail,//获取系统消息详情
		getMsg,//获取消息
		updatecode,//修改密码
		register,
		resetcode,
		login,
		getcourse,
		getNewsList,
		getNewsDetail,
		getCourseDetail,
		getNewsReply,
		createQues,
		updateQues,
		getTags,
		getLivePage,
		feedback,
		getNewSign,//版本检查接口
		getUserBaseInt,
		checkPhone,
		sendSms,
		checkSms,
		getQuesList,
		getQuesDetail,
		getResonList,
		addAnswer,
		addReply,
		editInfo,
		logout,
		getMyQA,
		getPointRule,
		getProduct,
		delMyData,//用户删除数据(包括我发布的问题/收藏的数据)
		clearRedPoint,
		getSbReply,
		getCourseRecord,
		getMyColl,
		addLike,
		CourseRecord,
		getRedPoint,//获取红点
		getCourseByDate,
		getLiveCourse,
		reportQues,
		chooseAnswer,
		searchAtRegion,//社区搜索
		searchCourse,
		getSbQA,
		signIn,
		setOrder,
		getPayment						
	}		
	public Type getType(){
		return type_;
	}
	public String getUrl(){		
		String Url  = null;

		Url = GetAPIUrl(api_,type_.toString());
		//Log.e("url",Url);		
		return Url;
	}		
	//获得图片的URL
	public static String getWebUrl_(String url){
		return root +url;
	}
	public static String getimgpostUrl_(){
		return root+"Api3/onlyfileupload";
	}

	public RequestType(String i,Type type)	{
		api_=i;
		type_ = type;	   
	}	
	public RequestType(){
		
	}		
	private   String GetAPIUrl(String i,String url_key)
	{	      
		String Token = "930bb8937c42109377e73bea7b92f88c";
		//时间戳
		String timestamp=String.valueOf(System.currentTimeMillis());
		//随机数
		String nonce=String.valueOf(new Random().nextInt(1000));
		
		String[] arrs={Token,timestamp,nonce};
		
		Arrays.sort(arrs);
		
		//String signature= DigestUtils.shaHex(arrs[0]+arrs[1]+arrs[2]);
		
		return String.format(base_url,i,url_key);				 
	}
	public Map<String, Object> getParams(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}		
}
