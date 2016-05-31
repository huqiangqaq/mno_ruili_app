package mno_ruili_app.adapter;

public class tv {
	private String name;  
	private String ms;  
	private String img;  
	private String id;
	
	public tv( String id, String Name, String Ms,String Img){
		this.id=id;
	    this.name=Name;
	    this.ms=Ms;
	    this.img= Img;
	}
	public tv(  String Name, String Ms,String Img){
		
	    this.name=Name;
	    this.ms=Ms;
	    this.img= Img;
	}
	public String getid() {  
	    return id;  
	} 

	public void setid(String id) {  
	    this.id = id;  
	}
	public String getName() {  
	    return name;  
	} 

	public void setName(String name) {  
	    this.name = name;  
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

	
	
}



