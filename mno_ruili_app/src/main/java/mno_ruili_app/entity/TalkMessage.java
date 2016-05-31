package mno_ruili_app.entity;

public class TalkMessage {
	private String jobImagePath;//照片
	private String nickName;//用户名
	private String updateTime;//发布时间
	private String message;//消息
	public TalkMessage(String jobImagePath, String nickName, String updateTime,
			String message) {
		super();
		this.jobImagePath = jobImagePath;
		this.nickName = nickName;
		this.updateTime = updateTime;
		this.message = message;
	}
	public String getJobImagePath() {
		return jobImagePath;
	}
	public void setJobImagePath(String jobImagePath) {
		this.jobImagePath = jobImagePath;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
