package mno_ruili_app.adapter;

public class ht{
private String id;  
private String mianid;  
private String myReply;  
private String title;  
private String Rcontent;  
private String replyTotal;
private String publish_at;
private String  ReplyTime;
private String  where;
private String  coverImg;
public ht(String where,String mianid, String id,String myReply,String ReplyTime, String title,String Rcontent, String replyTotal,String publish_at){
	this.where=where;
	this.mianid=mianid;
    this.id=id;
    this.myReply=myReply;
    this. ReplyTime=ReplyTime;
    this.title= title;
    this.Rcontent= Rcontent;
    this.replyTotal= replyTotal;
    this.publish_at= publish_at;
  
}
public ht(String where,String mianid, String id,String myReply,String ReplyTime, String title,String Rcontent, String replyTotal,String publish_at,String coverImg){
	this.where=where;
	this.mianid=mianid;
    this.id=id;
    this.myReply=myReply;
    this. ReplyTime=ReplyTime;
    this.title= title;
    this.Rcontent= Rcontent;
    this.replyTotal= replyTotal;
    this.publish_at= publish_at;
    this.coverImg= coverImg;
}
public String getcoverImg() {  
    return coverImg;  
} 

public void setcoverImg(String coverImg) {  
    this.coverImg = coverImg;  
}
public String getwhere() {  
    return where;  
} 

public void setwhere(String where) {  
    this.where = where;  
}
public String getmianid() {  
    return mianid;  
} 

public void setmianid(String mianid) {  
    this.mianid = mianid;  
}
public String getid() {  
    return id;  
} 

public void setid(String id) {  
    this.id = id;  
}
public String getReplyTime() {  
    return ReplyTime;  
} 

public void setReplyTime(String ReplyTime) {  
    this.ReplyTime = ReplyTime;  
}
public String getmyReply() {  
    return myReply;  
} 

public void setmyReply(String myReply) {  
    this.myReply = myReply;  
}
public String gettitle() {  
    return title;  
} 

public void settitle(String title) {  
    this.title = title;  
}
public String getRcontent() {  
    return Rcontent;  
} 

public void setRcontent(String Rcontent) {  
    this.Rcontent = Rcontent;  
}
public String getreplyTotal() {  
    return replyTotal;  
} 

public void setreplyTotal(String replyTotal) {  
    this.replyTotal = replyTotal;  
}
public String getpublish_at() {  
    return publish_at;  
} 

public void setpublish_at(String publish_at) {  
    this.publish_at = publish_at;  
}
}