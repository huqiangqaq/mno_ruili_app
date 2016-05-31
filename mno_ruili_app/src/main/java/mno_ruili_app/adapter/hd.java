package mno_ruili_app.adapter;

public class hd {
	private String name;  
	private String id;  
	private String uid;  
	private String img;  
	private String content;  
	private String time;
	private String islike;
	private String likeTotal;
	private String hot;
	public hd( String Name,String Content, String Id,String Img,String time,String islike){
		  
	    this.name=Name;
	    this.id=Id;
	    this.img= Img;
	    this.content= Content;
	    this.time=time;
	    this.islike=islike;
	}
	public hd( String Name,String Content, String Id,String Img,String time,String islike,String uid){
		  
	    this.name=Name;
	    this.id=Id;
	    this.img= Img;
	    this.content= Content;
	    this.time=time;
	    this.islike=islike;
	    this.uid=uid;
	}
	public hd( String Name,String Content, String Id,String time,String Img,String islike,String likeTotal,String hot,String uid){
		  
	    this.name=Name;
	    this.id=Id;
	    this.img= Img;
	    this.content= Content;
	    this.time=time;
	    this.islike=islike;
	    this.likeTotal=likeTotal;
	    this.hot=hot;
	    this.uid=uid;
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
	public String gettime() {  
	    return time;  
	} 

	public void settime(String time) {  
	    this.time = time;  
	}
	public String getId() {  
	    return id;  
	} 

	public void setId(String id) {  
	    this.id = id;  
	}
	public String getImg() {  
	    return img;  
	} 

	public void setImg(String img) {  
	    this.img = img;  
	}
	public String getislike() {  
	    return islike;  
	} 

	public void setislike(String islike) {  
	    this.islike = islike;  
	}
	public String getlikeTotal() {  
	    return likeTotal;  
	} 

	public void setlikeTotal(String likeTotal) {  
	    this.likeTotal = likeTotal;  
	}
	public String gethot() {  
	    return hot;  
	} 

	public void sethot(String hot) {  
	    this.hot = hot;  
	}
	public String getuid() {  
	    return uid;  
	} 

	public void setuid(String uid) {  
	    this.uid = uid;  
	}
}
