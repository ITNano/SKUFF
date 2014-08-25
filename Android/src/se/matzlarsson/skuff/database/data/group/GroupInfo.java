package se.matzlarsson.skuff.database.data.group;

public class GroupInfo {

	private Group group;
	GroupValue[] values;
	
	public GroupInfo(Group group, GroupValue[] values) {
		this.group = group;
		this.values = values;
	}

	public Group getGroup() {
		return group;
	}

	public GroupValue[] getValues() {
		return values;
	}

}
