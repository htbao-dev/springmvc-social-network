package bean;

import java.util.ArrayList;
import java.util.Date;

public class PostBEAN {
	protected long postID;
	protected String content;
	protected long userID;
	protected Date postTime;
	protected ArrayList<String> postImg;
	
	public PostBEAN(long postID, long userID, String content, Date postTime, ArrayList<String> postImg) {
		super();
		this.userID = userID;
		this.postID = postID;
		this.content = content;
		this.postTime = postTime;
		this.postImg = postImg;
	}
	
	protected PostBEAN() {
		
	}
	
	public long getPostID() {
		return postID;
	}
	public void setPostID(long postID) {
		this.postID = postID;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ArrayList<String> getPostImg() {
		return postImg;
	}
	public void setPostImg(ArrayList<String> postImg) {
		this.postImg = postImg;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
	
}
