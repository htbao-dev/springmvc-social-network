package husc.socialnetwork.controller;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bean.CommentBEAN;
import bean.PostReactionBEAN;
import bean.Result;
import bean.UserBEAN;
import bo.PostBO;

@Controller
public class PostController {
	private PostBO postBO;
	
	public PostController() {
		postBO = new PostBO();
	}
	
	
	@RequestMapping(value = "/addNewComment", method = RequestMethod.POST)
	public @ResponseBody String addNewComment(
			HttpServletRequest request,
			HttpSession session,
			@RequestParam(name = "postID") long postID,
			@RequestParam(name = "cmtContent")String cmtContent) {
		
		String response = "";
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			UserBEAN userInfo = (UserBEAN)session.getAttribute("userInfo");
			Result result = postBO.addNewComment(postID, cmtContent, userInfo.getUserID());
			CommentBEAN commentBEAN = postBO.getComment(result.getID());
			long count = postBO.countCommentOfPost(postID);
			JSONObject a = new JSONObject();
			a.append("status", result.getStatus());
			a.append("firstName", userInfo.getFirstName());
			a.append("lastName", userInfo.getLastName());
			a.append("avatarPath", userInfo.getAvatarPath());
			a.append("time", commentBEAN.getCommentTime());
			a.append("countComment", count);
			response = a.toString();
			System.out.println(response);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value = "/addReaction", method = RequestMethod.POST)
	public @ResponseBody String addNewReaction(
			HttpServletRequest request,
			HttpSession session,
			@RequestParam(name = "postID") long postID,
			@RequestParam(name = "reactionID") int reactionID){
		String response = "";
		try {
			UserBEAN userInfo = (UserBEAN)session.getAttribute("userInfo");
			int status = postBO.addReaction(postID, userInfo.getUserID(), reactionID);
			long count = postBO.countReactionOfPost(postID);
			JSONObject json = new JSONObject();
			json.append("status", status);
			json.append("countReaction", count);
			response = json.toString();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
		
	}
	
	@RequestMapping(value = "/deleteReaction", method = RequestMethod.POST)
	public @ResponseBody String deleteReaction(
			HttpServletRequest request,
			HttpSession session,
			@RequestParam(name = "postID") long postID){
		String response = "";
		try {
			UserBEAN userInfo = (UserBEAN)session.getAttribute("userInfo");
			int status = postBO.deleteReaction(postID, userInfo.getUserID());
			long count = postBO.countReactionOfPost(postID);
			JSONObject json = new JSONObject();
			json.append("status", status);
			json.append("countReaction", count);
			response = json.toString();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
		
	}
	
	@RequestMapping(path = "/loadComment", method = RequestMethod.GET)
	public @ResponseBody String loadComment(
			HttpServletRequest request,
			@RequestParam(name ="postID") long postID,
			@RequestParam(name = "pageNumber") int pageNumber) {
		
		String response = "";
		JSONObject json = new JSONObject();
		try {
			ArrayList<CommentBEAN> listCommnet = postBO.getListComment(postID, pageNumber);
			boolean canGet = listCommnet.size() >= postBO.DEFAULT_PAGE_SIZE_OF_COMMET;
			json.append("canGet", canGet);
			json.append("listComment", listCommnet);
			response = json.toString();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
}
