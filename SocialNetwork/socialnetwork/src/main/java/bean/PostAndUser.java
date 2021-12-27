package bean;

import java.util.ArrayList;

public class PostAndUser {
	private PostBEAN postBEAN;
	private UserBEAN author;
	private boolean reaction;
	private long countReaction;
	private long countComment;	
	private ArrayList<CommentBEAN> listDemoComment;
	
	public PostAndUser(PostBEAN postBEAN, UserBEAN author, boolean isReaction, long countReaction, long countComment, ArrayList<CommentBEAN> listDemoComment) {
		super();
		this.postBEAN = postBEAN;
		this.author = author;
		this.reaction = isReaction;
		this.countReaction = countReaction;
		this.countComment = countComment;
		this.listDemoComment = listDemoComment;
	}
	public PostBEAN getPostBEAN() {
		return postBEAN;
	}
	public void setPostBEAN(PostBEAN postBEAN) {
		this.postBEAN = postBEAN;
	}
	public UserBEAN getAuthor() {
		return author;
	}
	public void setAuthor(UserBEAN author) {
		this.author = author;
	}

	public long getCountReaction() {
		return countReaction;
	}
	public void setCountReaction(long countReaction) {
		this.countReaction = countReaction;
	}
	public long getCountComment() {
		return countComment;
	}
	public void setCountComment(long countComment) {
		this.countComment = countComment;
	}
	public void setReaction(boolean reaction) {
		this.reaction = reaction;
	}
	public boolean isReaction() {
		return reaction;
	}
	public ArrayList<CommentBEAN> getListDemoComment() {
		return listDemoComment;
	}
	public void setListDemoComment(ArrayList<CommentBEAN> listDemoComment) {
		this.listDemoComment = listDemoComment;
	}
	
	
	
}
