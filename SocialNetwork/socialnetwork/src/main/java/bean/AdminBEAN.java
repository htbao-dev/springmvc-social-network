package bean;

public class AdminBEAN {
	private long adminID;
	private String username;
	private String name;
	private String avatarPath;
	public long getAdminID() {
		return adminID;
	}
	public void setAdminID(long adminID) {
		this.adminID = adminID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public AdminBEAN(long adminID, String username, String name, String avatarPath) {
		super();
		this.adminID = adminID;
		this.username = username;
		this.name = name;
		this.avatarPath = avatarPath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatarPath() {
		return avatarPath;
	}
	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}
	
	
}
