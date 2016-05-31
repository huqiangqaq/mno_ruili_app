package mno_ruili_app.entity;
//系统消息
public class Message2 {
	private String id;
	private String title;
	private String content;
	private String image;
	private String isView;
	
	public Message2(String id, String title, String content, String image) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.image = image;
	}
	public Message2(String id, String title, String content, String image,
			String isView) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.image = image;
		this.isView = isView;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getIsView() {
		return isView;
	}
	public void setIsView(String isView) {
		this.isView = isView;
	}
	
}
