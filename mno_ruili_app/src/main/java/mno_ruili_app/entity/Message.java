package mno_ruili_app.entity;
//我的消息
public class Message {
	private String id;
	private String title;
	private String content;	
	private String type_id;
	private String update_time;
	private String isView;
	
	public Message(String title, String content, String type_id,
			String update_time, String isView) {
		super();
		this.title = title;
		this.content = content;
		this.type_id = type_id;
		this.update_time = update_time;
		this.isView = isView;
	}
	
	
	public Message(String id, String title, String content, String type_id,
			String update_time, String isView) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.type_id = type_id;
		this.update_time = update_time;
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
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getIsView() {
		return isView;
	}
	public void setIsView(String isView) {
		this.isView = isView;
	}
	
}
