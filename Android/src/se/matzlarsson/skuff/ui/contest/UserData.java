package se.matzlarsson.skuff.ui.contest;

public class UserData {

	private String name;
	private String phone;
	
	public UserData(){
		setName("");
		setPhone("");
	}
	
	public UserData(String name, String phone){
		setName(name);
		setPhone(phone);
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
}
