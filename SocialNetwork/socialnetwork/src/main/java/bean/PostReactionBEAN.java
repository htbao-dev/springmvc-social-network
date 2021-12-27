package bean;

public class PostReactionBEAN {
	private long postReactionID;
	private long postID;
	private long userID;
	private int reactionID;
	public PostReactionBEAN(long postReactionID, long postID, long userID, int reactionID) {
		super();
		this.postReactionID = postReactionID;
		this.postID = postID;
		this.userID = userID;
		this.reactionID = reactionID;
	}
	public long getPostReactionID() {
		return postReactionID;
	}
	public void setPostReactionID(long postReactionID) {
		this.postReactionID = postReactionID;
	}
	public long getPostID() {
		return postID;
	}
	public void setPostID(long postID) {
		this.postID = postID;
	}
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public int getReactionID() {
		return reactionID;
	}
	public void setReactionID(int reactionID) {
		this.reactionID = reactionID;
	}
	
	
}
