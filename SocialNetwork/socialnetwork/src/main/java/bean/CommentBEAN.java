package bean;

import java.util.Date;

public class CommentBEAN {
	private long commentID;
	private long postID;
	private UserBEAN author;
	private String content;
	private Date commentTime;
	
	public long getCommentID() {
		return commentID;
	}
	public void setCommentID(long commentID) {
		this.commentID = commentID;
	}
	public long getPostID() {
		return postID;
	}
	public void setPostID(long postID) {
		this.postID = postID;
	}
	public UserBEAN getAuthor() {
		return author;
	}
	public void setAuthor(UserBEAN author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}
	
	public CommentBEAN(long commentID, long postID, UserBEAN author, String content, Date commentTime) {
		super();
		this.commentID = commentID;
		this.postID = postID;
		this.author = author;
		this.content = content;
		this.commentTime = commentTime;
	}
	
	
}
