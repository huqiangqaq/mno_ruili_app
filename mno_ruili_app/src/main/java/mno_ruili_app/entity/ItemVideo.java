package mno_ruili_app.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemVideo implements Parcelable{
	private String id;
	private String coverImg;
	private String title;
	private String teacherName;
	private String point;
	
	
	public ItemVideo() {
		super();
	}

	public ItemVideo(String id, String coverImg, String title,
			String teacherName, String point) {
		super();
		this.id = id;
		this.coverImg = coverImg;
		this.title = title;
		this.teacherName = teacherName;
		this.point = point;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	//该方法将类的数据写入外部提供的Parcel中。
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(coverImg);
		dest.writeString(title);
		dest.writeString(teacherName);
		dest.writeString(point);
	}
	Parcelable.Creator<ItemVideo> creator=new Creator<ItemVideo>() {

		@Override
		public ItemVideo createFromParcel(Parcel source) {
			//实现从source中创建出类的实例的功能
			ItemVideo itemVideo=new ItemVideo();
			itemVideo.id=source.readString();
			itemVideo.coverImg=source.readString();
			itemVideo.title=source.readString();
			itemVideo.teacherName=source.readString();
			itemVideo.point=source.readString();
			return itemVideo;
		}
		//创建一个类型为T，长度为size的数组
		@Override
		public ItemVideo[] newArray(int size) {
			// TODO Auto-generated method stub
			return new ItemVideo[size];
		}
		
	};
}
