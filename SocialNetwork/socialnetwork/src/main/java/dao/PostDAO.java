package dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import bean.CommentBEAN;
import bean.CreateNewPostResult;
import bean.PostBEAN;
import bean.PostReactionBEAN;
import bean.Result;
import bean.UserBEAN;

public class PostDAO {
	private DungChung dungChung;
	public PostDAO() {
		dungChung = new DungChung();// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param content
	 * @param fileDatas
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public CreateNewPostResult createNewPost(String content, MultipartFile[] fileDatas, long userID ) throws ClassNotFoundException, SQLException {
		List<File> uploadedFiles = savePostImgToServer(fileDatas);
		int status = 0;
		long postID;
		String sql = "{call dbo.CreateNewPost(?, ?, ?, ?)}";
		dungChung.connect();
		CallableStatement call = dungChung.connection.prepareCall(sql);
		call.setLong(1, userID);
		call.setString(2, content);
		call.registerOutParameter(3, Types.INTEGER);
		call.registerOutParameter(4, Types.BIGINT);
		
		call.execute();
		status = call.getInt(3);
		postID = call.getLong(4);
		dungChung.disconnect();
		
		for (File file : uploadedFiles) {
			savePostImgToDatabase(postID, "img/" +file.getName());
		}
		
		return new CreateNewPostResult(status, postID, uploadedFiles);
	}
	
	private List<File> savePostImgToServer(MultipartFile[] fileDatas){
		Random rd = new Random(System.currentTimeMillis());
		List<File> uploadedFiles = new ArrayList<File>();
		if (fileDatas != null && fileDatas.length != 0) {
			File uploadRootDir = new File(getImgFolderPath());
			if (!uploadRootDir.exists()) {
				uploadRootDir.mkdirs();
	       	}
	       for (MultipartFile fileData : fileDatas) {
	           String name = fileData.getOriginalFilename();
	           if (name != null && name.length() > 0) {
	               try {
	            	   int index = name.lastIndexOf('.');
	                   String extension = name.substring(index);
	                   String serverFileName = Long.toString(Math.abs(rd.nextLong())) + extension;
	                   // Tạo file tại Server.
	                   File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + serverFileName);

	                   // Luồng ghi dữ liệu vào file trên Server.
	                   BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
	                   stream.write(fileData.getBytes());
	                   stream.close();
	                   //
	                   uploadedFiles.add(serverFile);
	                   System.out.println("Write file: " + serverFile);
	               } catch (Exception e) {
	                   System.out.println("Error Write file: " + name);
	               }
	           }
	       }
		}
       return uploadedFiles;
	}
	
	private int savePostImgToDatabase(long postID, String imageName) throws SQLException, ClassNotFoundException {
		String sql = "{call dbo.SavePostImage(?, ?, ?)}";
		dungChung.connect();
		CallableStatement call = dungChung.connection.prepareCall(sql);
		call.setLong(1, postID);
		call.setString(2, imageName);
		call.registerOutParameter(3, Types.INTEGER);
		
		call.execute();
		
		int status = call.getInt(3);
		dungChung.disconnect();
		return status;
	}
	
	private String getImgFolderPath() {
		return System.getProperty("user.dir") + "\\src\\main\\webapp\\img";
	}

	public PostBEAN getPost(long postID) throws ClassNotFoundException, SQLException {
		ArrayList<String> listImg = getListImagePost(postID);
		PostBEAN postInfo = null;
		String sql = "SELECT * FROM Post WHERE PostID = ?";
		dungChung.connect();
		PreparedStatement state = dungChung.connection.prepareStatement(sql);
		state.setLong(1, postID);
		ResultSet rs = state.executeQuery();
		
		if (rs.next()) {
			long userID = rs.getLong("UserID");
			String content = rs.getString("Content");
			Date postTime = rs.getDate("PostTime");
			
			postInfo = new PostBEAN(postID, userID, content, postTime, listImg);
			
		}
		rs.close();
		dungChung.disconnect();
		
		return postInfo;
	}
	
	private ArrayList<String> getListImagePost(long postID) throws ClassNotFoundException, SQLException {
		ArrayList<String> listImg = new ArrayList<String>();
		String sql = "SELECT * FROM PostImage WHERE PostID = ?";
		dungChung.connect();
		PreparedStatement state = dungChung.connection.prepareStatement(sql);
		state.setLong(1, postID);
		ResultSet rs = state.executeQuery();
		while(rs.next()) {
			listImg.add(rs.getString("ImageName"));
		}
		rs.close();
		dungChung.disconnect();
		return listImg;
	}

	public ArrayList<PostBEAN> getListPost(long postIDNew, long postIDOld, int quantily, String keySearch) throws ClassNotFoundException, SQLException {
		ArrayList<PostBEAN> listPost = new ArrayList<PostBEAN>();
		String sql = "exec proc_LoadMorePost ?, ?, ?, ?";
		dungChung.connect();
		PreparedStatement state = dungChung.connection.prepareStatement(sql);
		state.setLong(1, postIDNew);
		state.setLong(2, postIDOld);
		state.setInt(3, quantily);
		state.setString(4, keySearch);
		ResultSet rs = state.executeQuery();
		
		while (rs.next()) {
			long postID = rs.getLong("PostID");
			long userID = rs.getLong("UserID");
			String content = rs.getString("Content");
			Date postTime = rs.getDate("PostTime");
			
			listPost.add(new PostBEAN(postID, userID, content, postTime, null));
		}
		rs.close();
		dungChung.disconnect();
		
		for (PostBEAN postBEAN : listPost) {
			ArrayList<String> listImg = getListImagePost(postBEAN.getPostID());
			postBEAN.setPostImg(listImg);
		}
		
		return listPost;
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
		int status = 0;
		String sql = "{call dbo.proc_AddNewComment(?, ?, ?, ?, ?)}";
		dungChung.connect();
		CallableStatement call = dungChung.connection.prepareCall(sql);
		call.setLong(1, postID);
		call.setString(2, cmtContent);
		call.setLong(3, userID);
		call.registerOutParameter(4, Types.INTEGER);
		call.registerOutParameter(5, Types.BIGINT);
		
		call.execute();
		status = call.getInt(4);
		long commentID = call.getLong(5);
		dungChung.disconnect();
		return new Result(commentID, status);
	}
	
	/*
		 0: OK
		-1: not exists userID
		-2: not exists postID
		-3: not exists reactionID
		-4: exists reaction
	 */
	public int addReaction(long postID, long userID, int reactionID) throws ClassNotFoundException, SQLException {
		int status = 0;
		String sql = "{call dbo.proc_AddReaction(?, ?, ?, ?)}";
		dungChung.connect();
		CallableStatement call = dungChung.connection.prepareCall(sql);
		call.setLong(1, postID);
		call.setLong(2, userID);
		call.setInt(3, reactionID);
		call.registerOutParameter(4, Types.INTEGER);
		
		call.execute();
		status = call.getInt(4);
		dungChung.disconnect();
		return status;
	}
	/*
		 0: OK
		-1: not exists userID
		-2: not exists postID
		-3: not exists reaction
	 */
	public int deleteReaction(long postID, long userID) throws ClassNotFoundException, SQLException {
		int status = 0;
		String sql = "{call dbo.proc_DeleteReaction(?, ?, ?)}";
		dungChung.connect();
		CallableStatement call = dungChung.connection.prepareCall(sql);
		call.setLong(1, postID);
		call.setLong(2, userID);
		call.registerOutParameter(3, Types.INTEGER);
		
		call.execute();
		status = call.getInt(3);
		dungChung.disconnect();
		
		return status;
	}
	
	public ArrayList<PostReactionBEAN> getListReaction(long postID) throws ClassNotFoundException, SQLException{
		ArrayList<PostReactionBEAN> listReaction = new ArrayList<PostReactionBEAN>();
		String sql = "SELECT * FROM PostReaction WHERE PostID = ?";
		dungChung.connect();
		PreparedStatement state = dungChung.connection.prepareStatement(sql);
		state.setLong(1, postID);
		ResultSet rs = state.executeQuery();
		while(rs.next()) {
			listReaction.add(new PostReactionBEAN(rs.getLong("PostReactionID"), postID, rs.getLong("UserID"), rs.getInt("ReactionID")));
		}
		rs.close();
		dungChung.disconnect();
		return listReaction;
	}
	
	public ArrayList<CommentBEAN> getlistComment(long postID, int pageNumber, int pageSize) throws SQLException, ClassNotFoundException{
		ArrayList<CommentBEAN> listComment = new ArrayList<CommentBEAN>();
		String sql = "exec proc_PagingComment ?, ?, ?";
		dungChung.connect();
		PreparedStatement state = dungChung.connection.prepareStatement(sql);
		state.setLong(1, postID);
		state.setInt(2	, pageNumber);
		state.setInt(3, pageSize);
		ResultSet rs = state.executeQuery();
		while(rs.next()) {
			long postCommentID = rs.getLong("PostCommentID");
			String content = rs.getString("Content");
			Date date = rs.getDate("CommentTime");
			UserBEAN author = new UserBEAN(rs.getLong("UserID"), 
					rs.getString("Username"), 
					rs.getString("FirstName"), 
					rs.getString("LastName"), 
					rs.getString("Email"),
					rs.getString("AvatarPath"));
			listComment.add(new CommentBEAN(postCommentID, postID, author, content, date));
		}
		rs.close();
		dungChung.disconnect();
		return listComment;
	}
	
	public long countCommentOfPost(long postID) throws ClassNotFoundException, SQLException {
		String sql = "{? = call dbo.func_CountCommentOfPost(?) }";
		long count = 0;
		dungChung.connect();
		CallableStatement call = dungChung.connection.prepareCall(sql);
		call.registerOutParameter(1, Types.BIGINT);
		call.setLong(2, postID);
		call.execute();
		count = call.getLong(1);
		dungChung.disconnect();
		return count;
	}
	
	public long countReactionOfPost(long postID) throws ClassNotFoundException, SQLException {
		String sql = "{? = call dbo.func_CountReactionOfPost(?) }";
		long count = 0;
		dungChung.connect();
		CallableStatement call = dungChung.connection.prepareCall(sql);
		call.registerOutParameter(1, Types.BIGINT);
		call.setLong(2, postID);
		call.execute();
		count = call.getLong(1);
		dungChung.disconnect();
		return count;
	}

	public boolean isReaction(long postID, long userID) throws ClassNotFoundException, SQLException {
		String sql = "{? = call dbo.func_IsReaction(?, ?)}";
		boolean isReaction = false;
		dungChung.connect();
		CallableStatement call = dungChung.connection.prepareCall(sql);
		call.registerOutParameter(1, Types.BIT);
		call.setLong(2, postID);
		call.setLong(3, userID);
		call.execute();
		isReaction = call.getBoolean(1);
		dungChung.disconnect();
		return isReaction;
	}
	
	public CommentBEAN getComment(long commentID) throws ClassNotFoundException, SQLException {
		String sql = "exec proc_GetComment ?";
		dungChung.connect();
		PreparedStatement state = dungChung.connection.prepareStatement(sql);
		state.setLong(1, commentID);
		
		ResultSet rs = state.executeQuery();
		CommentBEAN commentBEAN = null;
		if (rs.next()) {
			commentBEAN = new CommentBEAN(rs.getLong("PostCommentID"), 
					rs.getLong("PostID"), 
					new UserBEAN(rs.getLong("UserID"), 
							rs.getString("Username"), 
							rs.getString("FirstName"), 
							rs.getString("LastName"), 
							rs.getString("Email"),
							rs.getString("AvatarPath")),
					rs.getString("Content"),
					rs.getDate("CommentTime"));
		}
		rs.close();
		dungChung.disconnect();
		return commentBEAN;
	}

	public int deletePost(long postID) throws SQLException, ClassNotFoundException {
		String sql = "DELETE Post WHERE PostID = ?";
		int status = 1;
		dungChung.connect();
		PreparedStatement state = dungChung.connection.prepareStatement(sql);
		state.setLong(1, postID);
		if (state.execute()) {
			status = 1;
		}
		else status = 0;
		dungChung.disconnect();
		return status;
	}

	public int deleteComment(long postID, long commentID) throws ClassNotFoundException, SQLException {
		String sql = "DELETE PostComment WHERE PostID = ? AND PostCommentID = ?";
		int status = 1;
		dungChung.connect();
		PreparedStatement state = dungChung.connection.prepareStatement(sql);
		state.setLong(1, postID);
		state.setLong(2, commentID);
		if (state.execute()) {
			status = 1;
		}
		else status = 0;
		dungChung.disconnect();
		return status;
	}
}
