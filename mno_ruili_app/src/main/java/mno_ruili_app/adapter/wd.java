package mno_ruili_app.adapter;

public class wd {
	private String name;  
	private String id;  
	private String uid;
	private String img;  
	private String content;  
	private String tag;  
	private String zt;  
	private String time;  
	private String bestAnwCon;  
	private String bestAnwUname;  
	private String fs;  
	private String answerTotal;
	public wd( String Name,String Content, String Id,String uid,String Img,String tag,String zt,String time,String bestAnwCon,String bestAnwUname,String fs,String answerTotal){
		  
	    this.name=Name;
	    this.id=Id;
	    this.uid=uid;
	    this.img= Img;
	    this.content= Content;
	    this.tag=tag;
	    this.zt=zt;
	    this.time=time;
	    this.bestAnwCon=bestAnwCon;
	    this.bestAnwUname=bestAnwUname;
	    this.fs=fs;
	    this.answerTotal=answerTotal;
	}
	public String getName() {  
	    return name;  
	} 

	public void setName(String name) {  
	    this.name = name;  
	}
	public String getContent() {  
	    return content;  
	} 

	public void setContent(String content) {  
	    this.content = content;  
	}
	public String getId() {  
	    return id;  
	} 

	public void setId(String id) {  
	    this.id = id;  
	}
	public String getuid() {  
	    return uid;  
	} 

	public void setuid(String uid) {  
	    this.id = uid;  
	}
	public String getImg() {  
	    return img;  
	} 

	public void setImg(String img) {  
	    this.img = img;  
	}
	//////
	
	public String getTag() {  
	    return tag;  
	} 

	public void setTag(String tag) {  
	    this.tag = tag;  
	}
	public String getzt() {  
	    return zt;  
	} 

	public void setzt(String zt) {  
	    this.zt = zt;  
	}
	public String gettime() {  
	    return time;  
	} 

	public void settime(String time) {  
	    this.time = time;  
	}
	////
	public String getbestAnwCon() {  
	    return bestAnwCon;  
	} 

	public void setbestAnwCon(String bestAnwCon) {  
	    this.bestAnwCon = bestAnwCon;  
	}
	public String getbestAnwUname() {  
	    return bestAnwUname;  
	} 

	public void setbestAnwUname(String bestAnwUname) {  
	    this.bestAnwUname = bestAnwUname;  
	}
public String getfs() {  
    return fs;  
} 

public void setfs(String fs) {  
    this.fs = fs;  
}
public String getanswerTotal() {  
    return answerTotal;  
} 

public void setanswerTotal(String answerTotal) {  
    this.answerTotal = answerTotal;  
}


}
