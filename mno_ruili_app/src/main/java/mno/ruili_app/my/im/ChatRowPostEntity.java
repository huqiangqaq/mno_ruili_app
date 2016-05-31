package mno.ruili_app.my.im;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatRowPostEntity {
	private String jobName;//工作职位名称	
	private String workTime;	//工作经验
	private String education;//学历
	private String salary;	//薪水
	private String workPlace;//期望城市
	//private String[] workerMsg;//亮点名字
	
	
	
	public ChatRowPostEntity(String jobName, String workTime, String education,
			String salary, String workPlace) {
		super();
		this.jobName = jobName;
		this.workTime = workTime;
		this.education = education;
		this.salary = salary;
		this.workPlace = workPlace;
		//this.workerMsg = workerMsg;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getWorkPlace() {
		return workPlace;
	}
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
//	public String[] getWorkerMsg() {
//		return workerMsg;
//	}
//	public void setWorkerMsg(String[] workerMsg) {
//		this.workerMsg = workerMsg;
//	}
	
	public JSONObject getJSONObject() {
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonMsgType = new JSONObject();
		try {
			jsonObject.put("jobName", this.jobName);
			jsonObject.put("workTime", this.workTime);
			jsonObject.put("education", this.education);
			jsonObject.put("salary", this.salary);
			jsonObject.put("workPlace", this.workPlace);
			//jsonObject.put("workerMsg", this.workerMsg);
			jsonMsgType.put("track", jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonMsgType;
	}
	public static ChatRowPostEntity getEntityFromJSONObject(JSONObject jsonMsgType){
		try {
			JSONObject jsonOrder = jsonMsgType.getJSONObject("track");
			String jobName = jsonOrder.getString("jobName");
			String workTime = jsonOrder.getString("workTime");
			String education = jsonOrder.getString("education");
			String salary = jsonOrder.getString("salary");
			String workPlace = jsonOrder.getString("workPlace");
			//String[] workerMsg = jsonOrder.getJSONArray("workerMsg");
			ChatRowPostEntity entity = new ChatRowPostEntity(jobName, workTime, education, salary, workPlace);
			return entity;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
