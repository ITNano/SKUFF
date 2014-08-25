package se.matzlarsson.skuff.database.data.contact;

import se.matzlarsson.skuff.database.DatabaseFactory;
import se.matzlarsson.skuff.database.DatabaseHelper;
import se.matzlarsson.skuff.database.data.Saveable;
import android.database.Cursor;

public class Contact implements Saveable{

	private int id;
	private String name;
	private String phone;
	private String mail;
	private String imageName;
	
	public Contact(){
		setId(-1);
		setName("");
		setPhone("");
		setMail("");
		setImageName("");
	}
	
	public Contact(Cursor c){
		setId(c.getInt(c.getColumnIndex("_id")));
		setName(c.getString(c.getColumnIndex("name")));
		setPhone(c.getString(c.getColumnIndex("phone")));
		setMail(c.getString(c.getColumnIndex("mail")));
		setImageName(c.getString(c.getColumnIndex("image")));
	}
	
	public Contact(int id, String name, String phone, String mail, String imageName){
		setId(id);
		setName(name);
		setPhone(phone);
		setMail(mail);
		setImageName(imageName);
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Override
	public void saveToDb(DatabaseHelper db) {
		db.insertOrUpdateQuery(DatabaseFactory.getTable(DatabaseFactory.TABLE_CONTACTS), new String[]{getId()+"", getName(), getPhone(), getMail(), getImageName()});
	}

	public String toString(){
		String s = "CONTACT: [";
		s += "\tID:"+getId()+"\n";
		s += "\tName:"+getName()+"\n";
		s += "\tPhone:"+getPhone()+"\n";
		s += "\tMail:"+getMail()+"\n";
		s += "\tImageName:"+getImageName()+" ]";
		
		return s;
	}
}
