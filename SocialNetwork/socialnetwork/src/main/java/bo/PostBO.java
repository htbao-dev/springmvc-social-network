package bo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import bean.CommentBEAN;
import bean.CreateNewPostResult;
import bean.PostAndUser;
import bean.PostBEAN;
import bean.PostReactionBEAN;
import bean.Result;
import bean.UserBEAN;
import dao.PostDAO;
import dao.UserDAO;

public class PostBO {
	private PostDAO postDAO;
	private UserDAO userDAO;
	
	public PostBO() {
		postDAO = new PostDAO();// TODO Auto-generated constructor stub
		userDAO = new UserDAO();
	}
	
	public final int DEFAULT_PAGE_SIZE_OF_COMMET = 5;
	public final int DEFAULT_QUANTILY_OF_POST = 10;
	/**
	 * 
	 * @param content
	 * @param fileDatas
	 * @param folderUploadPath
	 * @return <br>
	 *   0: OK <br>
	 *  -1: UserID not exists <br>
	 *  -2: content and file are empty <br>
	 *  
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public CreateNewPostResult createNewPost(String content, MultipartFile[] fileDatas, long userID) throws ClassNotFoundException, SQLException {
		CreateNewPostResult createNewPostStatus;
		if ((content == null ||content.isEmpty()) && (fileDatas == null || fileDatas.length == 0)) {
			createNewPostStatus = new CreateNewPostResult(-1, -1, null);
		}
		else {
			createNewPostStatus = postDAO.createNewPost(content, fileDatas, userID);
			
		} 
		return createNewPostStatus;
	}

//	public PostBEAN getPost(long postID) throws ClassNotFoundException, SQLException {
//		
//		return postDAO.getPost(postID);
//	}

	public PostAndUser getPostAndUser(long postID, long userID) throws ClassNotFoundException, SQLException {
		PostBEAN postBEAN = postDAO.getPost(postID);
		UserBEAN author = userDAO.getUser(postBEAN.getUserID());
		boolean isReaction = this.isReaction(postBEAN.getPostID(), userID);
		long countReaction = this.countReactionOfPost(postBEAN.getPostID());
		long countComment = this.countCommentOfPost(postBEAN.getPostID());
		ArrayList<CommentBEAN> listDemoComment = this.getListComment(postBEAN.getPostID(), 1);
		return new PostAndUser(postBEAN, author, isReaction, countReaction, countComment, listDemoComment);
	}
	
	public PostAndUser getPostAndUser(long postID) throws ClassNotFoundException, SQLException {
		PostBEAN postBEAN = postDAO.getPost(postID);
		UserBEAN author = userDAO.getUser(postBEAN.getUserID());
		boolean isReaction = false;
		long countReaction = this.countReactionOfPost(postBEAN.getPostID());
		long countComment = this.countCommentOfPost(postBEAN.getPostID());
		ArrayList<CommentBEAN> listDemoComment = this.getListComment(postBEAN.getPostID(), 1);
		return new PostAndUser(postBEAN, author, isReaction, countReaction, countComment, listDemoComment);
	}
	
	public ArrayList<PostAndUser> getListPostAndUser(long postIDNew, long postIDOld, long userID, String keySearch) throws ClassNotFoundException, SQLException {
		ArrayList<PostBEAN> listPost = postDAO.getListPost(postIDNew, postIDOld, DEFAULT_QUANTILY_OF_POST, keySearch);
		ArrayList<PostAndUser> listPostAndUser = new ArrayList<PostAndUser>();
		for (PostBEAN postBEAN : listPost) {
			UserBEAN author = userDAO.getUser(postBEAN.getUserID());
			boolean isReaction = this.isReaction(postBEAN.getPostID(), userID);
			long countReaction = this.countReactionOfPost(postBEAN.getPostID());
			long countComment = this.countCommentOfPost(postBEAN.getPostID());
			ArrayList<CommentBEAN> listDemoComment = this.getListComment(postBEAN.getPostID(), 1);
			PostAndUser tmp = new PostAndUser(postBEAN, author, isReaction, countReaction, countComment, listDemoComment);
			listPostAndUser.add(tmp);
		}
		return listPostAndUser;
		
	}
	
	public ArrayList<PostAndUser> getListPostAndUser(long postIDNew, long postIDOld) throws ClassNotFoundException, SQLException {
		ArrayList<PostBEAN> listPost = postDAO.getListPost(postIDNew, postIDOld, DEFAULT_QUANTILY_OF_POST, "");
		ArrayList<PostAndUser> listPostAndUser = new ArrayList<PostAndUser>();
		for (PostBEAN postBEAN : listPost) {
			UserBEAN author = userDAO.getUser(postBEAN.getUserID());
			boolean isReaction = false;
			long countReaction = this.countReactionOfPost(postBEAN.getPostID());
			long countComment = this.countCommentOfPost(postBEAN.getPostID());
			ArrayList<CommentBEAN> listDemoComment = this.getListComment(postBEAN.getPostID(), 1);
			PostAndUser tmp = new PostAndUser(postBEAN, author, isReaction, countReaction, countComment, listDemoComment);
			listPostAndUser.add(tmp);
		}
		return listPostAndUser;
		
	}
	
	public ArrayList<CommentBEAN> getListComment(long PostID, int pageNumber) throws ClassNotFoundException, SQLException{
		ArrayList<CommentBEAN> listComment = postDAO.getlistComment(PostID, pageNumber, DEFAULT_PAGE_SIZE_OF_COMMET);
		return listComment;
	}

	private boolean isReaction(long postID, long userID) throws ClassNotFoundException, SQLException {
		return postDAO.isReaction(postID, userID);
	}

	/**
	 * 
	 * @param postID
	 * @param cmtContent
	 * @param userID
	 * @return <br>
	 *  0: OK <br>
	 * -1: not exists userID <br>
	 * -2: not exusts postID <br>
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Result addNewComment(long postID, String cmtContent, long userID) throws ClassNotFoundException, SQLException {
		Result result;
		if (cmtContent.isEmpty()) {
			result = new Result(-1,-4);
		}
		else {
			result = postDAO.addNewComment(postID, cmtContent, userID);
		}
		
		return result;
		
	}
	
	public int addReaction(long postID, long userID, int reactionID) throws ClassNotFoundException, SQLException {
		return postDAO.addReaction(postID, userID, reactionID);
	}
	
	public int deleteReaction(long postID, long userID) throws ClassNotFoundException, SQLException {
		return postDAO.deleteReaction(postID, userID);
	}
	
	public ArrayList<PostReactionBEAN> getListReaction(long postID) throws ClassNotFoundException, SQLException{
		return postDAO.getListReaction(postID);
	}
	
	public long countCommentOfPost(long postID) throws ClassNotFoundException, SQLException {
		return postDAO.countCommentOfPost(postID);
	}
	
	public long countReactionOfPost(long postID)throws ClassNotFoundException, SQLException {
		return postDAO.countReactionOfPost(postID);
	}
	

	public CommentBEAN getComment(long postID) throws ClassNotFoundException, SQLException {
		return postDAO.getComment(postID);
	}

	public int deletePost(long postID) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return postDAO.deletePost(postID);
	}

	public int deleteComment(long postID, long commentID) throws ClassNotFoundException, SQLException {
		return postDAO.deleteComment(postID, commentID);
	}
}
