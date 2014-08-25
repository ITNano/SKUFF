package se.matzlarsson.skuff.database.data.sag;

import java.util.Date;

import se.matzlarsson.skuff.database.DatabaseFactory;
import se.matzlarsson.skuff.database.DatabaseHelper;
import se.matzlarsson.skuff.database.DateUtil;
import se.matzlarsson.skuff.database.data.Saveable;
import se.matzlarsson.skuff.database.data.StringUtil;
import android.database.Cursor;

public class SAG implements Saveable{
	
	private int id;
	private String name;
	private String post;
	private Date birth;
	private String imageName;
	private int displayOrder;

	public SAG(){
		setId(-1);
		setName("");
		setPost("");
		setBirth("2014-01-01");
		setImageName("");
		setDisplayOrder(100);
	}
	
	public SAG(Cursor cursor){
		setId(cursor.getInt(cursor.getColumnIndex("_id")));
		setName(cursor.getString(cursor.getColumnIndex("name")));
		setPost(cursor.getString(cursor.getColumnIndex("post")));
		setBirth(cursor.getString(cursor.getColumnIndex("birth")));
		setImageName(cursor.getString(cursor.getColumnIndex("image")));
		setDisplayOrder(cursor.getInt(cursor.getColumnIndex("displayOrder")));
	}
	
	public SAG(int id, String name, String post, Date birth, String imageName, int displayOrder) {
		setId(id);
		setName(name);
		setPost(post);
		setBirth(birth);
		setImageName(imageName);
		setDisplayOrder(displayOrder);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Date getBirth() {
		return birth;
	}
	
	public String getBirthString(){
		return DateUtil.dateToString(this.birth);
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	public void setBirth(String birth){
		this.birth = DateUtil.stringToDate(birth);
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Override
	public void saveToDb(DatabaseHelper db){
		db.insertOrUpdateQuery(DatabaseFactory.getTable(DatabaseFactory.TABLE_SAG), new String[]{getId()+"", getName(), getPost(), getBirthString(), getImageName(), getDisplayOrder()+""});
	}

	@Override
	public String toString(){
		String s = "NEWS:[ ";
		s += "ID="+getId()+", ";
		s += "name="+getName()+", ";
		s += "post='"+getPost()+"', ";
		s += "birth='"+getBirthString()+"', ";
		s += "imageName='"+getImageName()+"', ";
		s += "displayOrder="+getDisplayOrder()+" ]";
		
		return s;
	}
}
