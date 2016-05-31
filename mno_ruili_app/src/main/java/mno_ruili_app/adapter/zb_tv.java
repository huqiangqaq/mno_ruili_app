package mno_ruili_app.adapter;

import org.json.JSONArray;

public class zb_tv {
	private String name;  
	private String ms;  
	private String img;  
	private String Type; //视频类型：直播与点播
	private String id; String isCollect,isMembers;
	private String zj; String day;String week; String month;String time;String data;String isEnd;
	JSONArray daydata;
	public String gettime() {  
	    return time;  
	} 

	public void settime(String time) {  
	    this.time = time;  
	}
	public String getisEnd() {  
	    return isEnd;  
	} 

	public void setisEnd(String isEnd) {  
	    this.isEnd = isEnd;  
	}
	public String getmonth() {  
	    return month;  
	} 

	public void setmonth(String month) {  
	    this.month = month;  
	}
	public String getweek() {  
	    return week;  
	} 

	public void setweek(String week) {  
	    this.week = week;  
	}
	public String getday() {  
	    return day;  
	} 

	public void setday(String day) {  
	    this.day = day;  
	}
	public String getzj() {  
	    return zj;  
	} 

	public void setzj(String zj) {  
	    this.zj = zj;  
	}	
	public String getIsMembers() {
		return isMembers;
	}
	public void setIsMembers(String isMembers) {
		this.isMembers = isMembers;
	}
	public zb_tv( String Name, String Ms,String Img,String Type,String id ,String isCollect,String time,String isEnd,String isMembers){
		  
	    this.name=Name;
	    this.ms=Ms;
	    this.img= Img;
	    this.Type= Type;
	    this.id= id;
	    this.isCollect=isCollect;
	    this.time=time;
	    this.isEnd=isEnd;
	    this.isMembers=isMembers;
	}
	/*public zb_tv( String Name, String zj,String time,String day,String week,String month,String id ,String isCollect,String Type,String data,String isEnd){
		  
	    this.name=Name;
	    this.zj=zj;
	    this.time=time;
	    this.day= day;
	    this.week= week;
	    this.month=month;
	    this.Type= Type;
	    this.id= id;
	    this.Type= Type;
	    this.isCollect=isCollect;
	    this.data=data;
	    this.isEnd=isEnd;
	}*/
	public zb_tv( String id,String Name, String zj,String time,String isCollect,String isEnd){		  	    
	    this.zj=zj;
	    this.name=Name;
	    this.time=time;
	    this.id= id;
	    this.isCollect=isCollect;
	    this.isEnd=isEnd;
	}
	public zb_tv( String id,String Name, String zj,String time,String isCollect,String isEnd,String isMembers){		  	    
	    this.zj=zj;
	    this.name=Name;
	    this.time=time;
	    this.id= id;
	    this.isCollect=isCollect;
	    this.isEnd=isEnd;
	    this.isMembers=isMembers;
	}
	public zb_tv(JSONArray maindata, String day, String week, String month,
			String Type) {
		// TODO Auto-generated constructor stub
			this.daydata=maindata;
		    this.day= day;
		    this.week= week;
		    this.month=month;
		    this.Type= Type;
	}
	public JSONArray getdaydata() {  
	    return daydata;  
	} 

	public void setdaydata(JSONArray daydata) {  
	    this.daydata = daydata;  
	}
	
	public String getdata() {  
	    return data;  
	} 

	public void setdata(String data) {  
	    this.data = data;  
	}
	
	public String getName() {  
	    return name;  
	} 

	public void setName(String name) {  
	    this.name = name;  
	}
	
	public void setid(String id) {  
	    this.id = id;  
	}
	
	public String getisCollect() {  
	    return isCollect;  
	} 
	public void setisCollect(String isCollect) {  
	    this.isCollect = isCollect;  
	}
	
	public String getid() {  
	    return id;  
	} 
	public String getMs() {  
	    return ms;  
	} 

	public void setMs(String ms) {  
	    this.ms = ms;  
	}
	public String getImg() {  
	    return img;  
	} 

	public void setImg(String img) {  
	    this.img = img;  
	}
	public String getType() {  
	    return Type;  
	} 

	public void setType(String type) {  
	    this.Type = type;  
	}
}



