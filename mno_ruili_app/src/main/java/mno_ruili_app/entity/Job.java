package mno_ruili_app.entity;

public class Job {
	private String jobId;//岗位id
	private String jobName;//职位名称
	private String salary;//薪水
	private String workTime;	//工作经验
	private String education;//学历要求
	private String workPlace;//所在城市
	private String[] jobMsg;//亮点名字
	private String jobImagePath;//照片
	private String nickName;//用户名
	private String workLevel;//职业
	private String companyName;//公司名字
	private String updateTime;//发布时间
	
	//用于查看发布的所有职位		
	public Job(String jobId, String jobName, String salary, String workTime,
			String education, String workPlace, String[] jobMsg,
			String updateTime) {
		super();
		this.jobId = jobId;
		this.jobName = jobName;
		this.salary = salary;
		this.workTime = workTime;
		this.education = education;
		this.workPlace = workPlace;
		this.jobMsg = jobMsg;
		this.updateTime = updateTime;
	}

	public Job(String jobId, String jobName, String salary, String workTime,
			String education, String workPlace, String[] jobMsg,
			String jobImagePath, String nickName, String workLevel,
			String companyName, String updateTime) {
		super();
		this.jobId = jobId;
		this.jobName = jobName;
		this.salary = salary;
		this.workTime = workTime;
		this.education = education;
		this.workPlace = workPlace;
		this.jobMsg = jobMsg;
		this.jobImagePath = jobImagePath;
		this.nickName = nickName;
		this.workLevel = workLevel;
		this.companyName = companyName;
		this.updateTime = updateTime;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
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
	public String getWorkPlace() {
		return workPlace;
	}
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
	
	public String[] getJobMsg() {
		return jobMsg;
	}

	public void setJobMsg(String[] jobMsg) {
		this.jobMsg = jobMsg;
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
	public String getWorkLevel() {
		return workLevel;
	}
	public void setWorkLevel(String workLevel) {
		this.workLevel = workLevel;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
