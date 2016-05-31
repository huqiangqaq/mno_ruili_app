package mno_ruili_app.adapter;

public class mywd {
	private String content;
	private String titler;
	private String redPoint;
	private String bestAnwId;
	private String qid;  
	private String id;  
	private String statusName;  
	private String create_at;  
	private String answerTotal;  
	public mywd(String titler, String content,String id,String statusName,String create_at,String answerTotal){
		this.titler=titler;
	    this.content=content;
	    this.id=id;
	    this.statusName= statusName;
	    this.create_at=create_at;
	    this.answerTotal=answerTotal;
	}
	public mywd(String titler, String content,String qid,String aid,String statusName,String create_at,String answerTotal){
		this.titler=titler;
	    this.content=content;
	    this.id=aid;
	    this.qid=qid;
	    this.statusName= statusName;
	    this.create_at=create_at;
	    this.answerTotal=answerTotal;
	}
	public mywd(String bestAnwId,String redPoint,String titler, String content,String qid,String aid,String statusName,String create_at,String answerTotal){
		this.titler=titler;
		this.bestAnwId=bestAnwId;
		this.redPoint=redPoint;
	    this.content=content;
	    this.id=aid;
	    this.qid=qid;
	    this.statusName= statusName;
	    this.create_at=create_at;
	    this.answerTotal=answerTotal;
	}
	 public String getbestAnwId() {  
		    return bestAnwId;  
		} 

		public void setbestAnwId(String bestAnwId) {  
		    this.bestAnwId = bestAnwId;  
		}
	    
	 public String getredPoint() {  
		    return redPoint;  
		} 

		public void setredPoint(String redPoint) {  
		    this.redPoint = redPoint;  
		}
	    
	    public String gettitler() {  
		    return titler;  
		} 

		public void settitler(String titler) {  
		    this.titler = titler;  
		}
		
	
	public String getcontent() {  
	    return content;  
	} 

	public void setcontent(String content) {  
	    this.content = content;  
	}
	
	public String getqid() {  
	    return qid;  
	} 

	public void setqid(String qid) {  
	    this.qid = qid;  
	}
	///
	public String getid() {  
	    return id;  
	} 

	public void setid(String id) {  
	    this.id = id;  
	}
	///
	public String getstatusName() {  
	    return statusName;  
	} 

	public void setstatusName(String statusName) {  
	    this.statusName = statusName;  
	}
	///
	public String create_at() {  
	    return create_at;  
	} 
	public void setcreate_at(String create_at) {  
	    this.create_at = create_at;  
	}
	///
	public String getanswerTotal() {  
	    return answerTotal;  
	} 

	public void setanswerTotal(String answerTotal) {  
	    this.answerTotal = answerTotal;  
	}
	
}
