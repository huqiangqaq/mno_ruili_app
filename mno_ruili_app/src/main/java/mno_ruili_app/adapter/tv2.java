package mno_ruili_app.adapter;

public class tv2 {
	private String name;  
	private String ms;  
	private String img;  
	private String Type; 
	private String id; String isCollect;

	public tv2() {
		super();
	}
	
	public tv2(String name, String ms, String img, String id) {
		super();
		this.name = name;
		this.ms = ms;
		this.img = img;
		this.id = id;
	}

	public tv2(String name, String ms, String img, String type, String id,
			String isCollect) {
		super();
		this.name = name;
		this.ms = ms;
		this.img = img;
		Type = type;
		this.id = id;
		this.isCollect = isCollect;
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

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}
	
}
