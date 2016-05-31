package mno.ruili_app;

public class userinfo {
	public String uid;
	public String nickname;
	public String work;//职业
	public String danwei;//工作单位
	public String phone;//手机号
	public String email;
	public String platform;//来自哪个平台的用户
	public String sex;
	public String provice;
	public String city;
	public String collTotal;
	public String replyTotal;
	public String quesTotal;
	public String invCode;
	//{"message":"登陆成功","data":{"uid":"1","phone":"13588713117","sex":"女","collTotal":0,"signText":"艾玛哦哦","replyTotal":2,"provice":null,"street":null,"smallImg":"media\/small\/20150714144035_small.jpg","city":null,"quesTotal":1,"invCode":"16752788pynb","username":"1234567","pointTotal":"0","email":"","bigImg":"media\/root\/20150714144035291.jpg"},"code":"0"}
	public String signText;
	public String pointTotal;//总学分
	public String bigImg;//用户头像
	public String smallImg;
	public String indentify;//是否为会员
	public String coursePush;
	public String newsPush;
	public String signinPoint;//签到学分
}
