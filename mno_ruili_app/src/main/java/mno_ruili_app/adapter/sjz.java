package mno_ruili_app.adapter;

import org.json.JSONArray;

public class sjz{
private String month;  

private String day;  
private JSONArray data;  
private String zt;  
private String replyTotal;
private String publish_at;
private String  ReplyTime;
public sjz(String month, String day,JSONArray data,String zt){
	  
	
    this.month=month;
    this.day=day;
    this.data= data;
    this.zt= zt;
   
}

public String getmonth() {  
    return month;  
} 

public void setmonth(String month) {  
    this.month = month;  
}

public String getday() {  
    return day;  
} 

public void setday(String day) {  
    this.day = day;  
}
public JSONArray getdata() {  
    return data;  
} 

public void setdata(JSONArray data) {  
    this.data = data;  
}
public String getzt() {  
    return zt;  
} 

public void setzt(String zt) {  
    this.zt = zt;  
}

}