package husc.socialnetwork.cms.controller;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import bean.AdminBEAN;
import bean.CommentBEAN;
import bean.PostAndUser;
import bean.PostUploadForm;
import bean.UserBEAN;
import bo.AdminBO;
import bo.PostBO;
import exception.NotFoundException;

@Controller
public class CmsController {
	private AdminBO adminBO;
	private PostBO postBO;
	
	public CmsController() {
		adminBO = new AdminBO();// TODO Auto-generated constructor stub
		postBO = new PostBO();
	}
	
	 @RequestMapping("/cms/login")
	    public ModelAndView Login(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
	        try {
	        	request.setCharacterEncoding("utf-8");
	        	response.setCharacterEncoding("utf-8");
	        	String username = request.getParameter("inputUsername");
	        	String passString = request.getParameter("inputPassword");
	        	int status = 0;
	        	/*
	        	 *  0: normal
	        	 * -1: invalid password
	        	 * -2: invalid username*/
	        	if (!(username == null || username.equals(""))) {
	        		try {
						AdminBEAN adminInfo = adminBO.login(username, passString);
						if (adminInfo == null) {
							status = -1;
						}
						else {
							session.setAttribute("adminInfo", adminInfo);
							return new ModelAndView("redirect:/cms/home");
						}
					}catch (SQLException | ClassNotFoundException e) {
						status = -3;
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		request.setAttribute("status", status);
	        		request.setAttribute("username", username);
	        	}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
	        return new ModelAndView("cmsLogin");
	 }
	 
	 @RequestMapping(path= "/cms/home")
	 public ModelAndView home(
			 HttpSession session,
			 Model model) {
		if (session.getAttribute("adminInfo") == null) {
			return new ModelAndView("redirect:/cms/login");
		}
		return new ModelAndView("cmsHome");
	 }
	 
	@RequestMapping(path = "/cms/logout")
	public ModelAndView logout(Model model, HttpSession session) {
		session.removeAttribute("adminInfo");
		return new ModelAndView("redirect:/cms/login");
	}
	
	@RequestMapping(path = "/cms/loadMorePost", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public @ResponseBody String loadMorePost(
			HttpServletRequest request,
			HttpSession sesion,
			@RequestParam(name = "postTimeNew")long postIDNew,
			@RequestParam(name = "postTimeOld")long postIDOld) {
		String response = "";
		try {
			AdminBEAN adminInfo = (AdminBEAN)sesion.getAttribute("adminInfo");

			ArrayList<PostAndUser> listPostAndUser = postBO.getListPostAndUser(postIDNew, postIDOld);
			JSONArray json = new JSONArray(listPostAndUser);
			response = json.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(path = "/cms/getPostDetail", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public @ResponseBody String getPostDetail(
			@RequestParam(name = "postID")long postID) {
		String response = "";
		try {
			PostAndUser post = postBO.getPostAndUser(postID);
			JSONObject json=  new JSONObject(post);
			response = json.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(path = "/cms/deletePost", method = RequestMethod.POST)
	public @ResponseBody String deletePost(
			@RequestParam(name = "postID")long postID) {
		String response = "";
		int status = 0;
		try {
			status = postBO.deletePost( postID);
			JSONObject json = new JSONObject();
			json.append("status", status);
			response = json.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(path = "/cms/loadMoreComment", method = RequestMethod.GET)
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
	
	@RequestMapping(path = "/cms/deleteComment", method = RequestMethod.POST)
	public @ResponseBody String deleteComment(
			@RequestParam(name = "postID")long postID,
			@RequestParam(name = "commentID")long commentID) {
		String response = "";
		try {
			int status = postBO.deleteComment(postID, commentID);
			JSONObject json = new JSONObject();
			json.append("status", status);
			response = json.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
}
