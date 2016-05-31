package mno_ruili_app.entity;

public class Worker {
	private String workerId;//简历id
	private String jobName;//工作职位名称	
	private String workTime;	//工作经验
	private String education;//学历
	private String salary;	//薪水
	private String workPlace;//期望城市
	private String[] workerMsg;//亮点名字
	private String workImagePath;//照片
	private String nickName;//用户名
	private String updateTime;//发布时间
	
	//发送简历到公司后在聊天界面上显示的构造方法
	public Worker(String jobName, String workTime, String education,
			String salary, String workPlace, String[] workerMsg) {
		super();
		this.jobName = jobName;
		this.workTime = workTime;
		this.education = education;
		this.salary = salary;
		this.workPlace = workPlace;
		this.workerMsg = workerMsg;
	}
	
	//薪资选择用到的构造函数
	public Worker(String workerId, String jobName) {
		super();
		this.workerId = workerId;
		this.jobName = jobName;
	}
	//填写招聘信息中的-添加招聘职位要用到的构造函数
	public Worker(String workerId, String jobName, String workTime,
			String education, String salary, String workPlace,
			String workImagePath, String nickName) {
		super();
		this.workerId = workerId;
		this.jobName = jobName;
		this.workTime = workTime;
		this.education = education;
		this.salary = salary;
		this.workPlace = workPlace;
		this.workImagePath = workImagePath;
		this.nickName = nickName;
	}
	//和个人简历中的-添加工作经历要用的构造函数
	public Worker(String workerId, String jobName, String workTime,
			String education, String salary,String workPlace) {
		super();
		this.workerId = workerId;//职位
		this.jobName = jobName;//公司名字
		this.workTime = workTime;//入职时间
		this.education = education;//离职时间
		this.salary = salary;//描述
		this.workPlace = workPlace;//工作经历id
	}	

	public Worker(String workerId, String jobName, String workTime,
			String education, String salary) {
		super();
		this.workerId = workerId;//职位
		this.jobName = jobName;//公司名字
		this.workTime = workTime;//入职时间
		this.education = education;//离职时间
		this.salary = salary;//描述
	}
	//个人简历中的-添加教育经历要用的构造函数	
	public Worker(String workerId, String jobName, String workTime,
			String education, String salary, String workPlace,String nickName) {
		super();
		this.workerId = workerId;//学校名字
		this.jobName = jobName;//本科
		this.workTime = workTime;//入校时间
		this.education = education;//离校时间
		this.salary = salary;//专业
		this.workPlace = workPlace;//描述
		this.nickName = nickName;//教育经历id
	}
	public Worker(String workerId, String jobName, String workTime,
			String education, String salary, String workPlace,
			String[] workerMsg, String workImagePath, String nickName,
			String updateTime) {
		super();
		this.workerId = workerId;
		this.jobName = jobName;
		this.workTime = workTime;
		this.education = education;
		this.salary = salary;
		this.workPlace = workPlace;
		this.workerMsg = workerMsg;
		this.workImagePath = workImagePath;
		this.nickName = nickName;
		this.updateTime = updateTime;
	}

	
	public String getWorkerId() {
		return workerId;
	}

	public void setWorkerId(String workerId) {
		this.workerId = workerId;
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
	
	public String[] getWorkerMsg() {
		return workerMsg;
	}

	public void setWorkerMsg(String[] workerMsg) {
		this.workerMsg = workerMsg;
	}

	public String getWorkImagePath() {
		return workImagePath;
	}
	public void setWorkImagePath(String workImagePath) {
		this.workImagePath = workImagePath;
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
	
}
