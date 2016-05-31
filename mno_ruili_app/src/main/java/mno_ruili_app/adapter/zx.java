package mno_ruili_app.adapter;

public class zx  {
	private String name;  
	private String who;  
	private String id;  
	private String img;  
	private String content;  
	private String replyTotal;  
	public zx( String Name,String Content, String Id,String Img){
		  
	    this.name=Name;
	    this.id=Id;
	    this.img= Img;
	    this.content= Content;
	}
	
	public zx( String Name,String Content, String Id,String Img,String replyTotal){
		this.replyTotal= replyTotal;
	    this.name=Name;
	    this.id=Id;
	    this.img= Img;
	    this.content= Content;
	}
	public zx( String Name,String Content, String Id,String Img,String replyTotal,String who){
		this.who=who;
		this.replyTotal= replyTotal;
	    this.name=Name;
	    this.id=Id;
	    this.img= Img;
	    this.content= Content;
	}
	public void setwho(String who) {  
	    this.who = who;  
	}
	
	public String getwho() {  
	    return who;  
	} 
	public void setreplyTotal(String replyTotal) {  
	    this.replyTotal = replyTotal;  
	}
	
	public String getreplyTotal() {  
	    return replyTotal;  
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
	public String getid() {  
	    return id;  
	} 

	public void setid(String id) {  
	    this.id = id;  
	}
	public String getImg() {  
	    return img;  
	} 

	public void setImg(String img) {  
	    this.img = img;  
	}
}



