package husc.socialnetwork.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bean.CommentBEAN;
import bean.CreateNewPostResult;
import bean.PostAndUser;
import bean.PostBEAN;
import bean.PostUploadForm;
import bean.Result;
import bean.UserBEAN;
import bo.PostBO;
import bo.UserBO;

@Controller
public class HomeController {
	private PostBO postBO;
	private UserBO userBO;
	public HomeController() {
		postBO = new PostBO();// TODO Auto-generated constructor stub
		userBO = new UserBO();
	}
	
   @InitBinder
   public void initBinder(WebDataBinder dataBinder) {
       Object target = dataBinder.getTarget();
       if (target == null) {
           return;
       }

       if (target.getClass() == PostUploadForm.class) {
 
           // Đăng ký để chuyển đổi giữa các đối tượng multipart thành byte[]
           dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
       }
   }
	
	@RequestMapping(path = "/home")
	public ModelAndView home(Model model, HttpSession sesion) {
		if (sesion.getAttribute("userInfo") == null) {
			return new ModelAndView("redirect:/login");
		}
		UserBEAN userInfo = (UserBEAN)sesion.getAttribute("userInfo");
		PostUploadForm postUploadForm = new PostUploadForm();
		model.addAttribute("postUploadForm", postUploadForm);
		return new ModelAndView("home");
	}
	
	@RequestMapping(value = "/newPost", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public @ResponseBody String uploadFile(HttpServletRequest request, //
			Model model, //
			HttpSession session,
			@RequestParam(name = "content", required = false) String content,
			@RequestParam(name = "images", required = false) MultipartFile[] images) {
		
		UserBEAN userInfo = (UserBEAN)session.getAttribute("userInfo");
		
		String response = "";
		try {
			CreateNewPostResult createNewPostStatus = postBO.createNewPost(content, images, userInfo.getUserID());
			long postID = createNewPostStatus.getPostID();
			PostAndUser data = postBO.getPostAndUser(postID, userInfo.getUserID());
			JSONObject json = new JSONObject(data);
			response = json.toString();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return response;
   }
	
	@RequestMapping(path = "/loadMorePost", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public @ResponseBody String loadMorePost(
			HttpServletRequest request,
			HttpSession sesion,
			@RequestParam(name = "postTimeNew")long postIDNew,
			@RequestParam(name = "postTimeOld")long postIDOld,
			@RequestParam(name = "keySearch")String keySearch) {
		String response = "";
		try {
			UserBEAN userInfo = (UserBEAN)sesion.getAttribute("userInfo");

			ArrayList<PostAndUser> listPostAndUser = postBO.getListPostAndUser(postIDNew, postIDOld ,userInfo.getUserID(),keySearch);
			JSONArray json = new JSONArray(listPostAndUser);
			response = json.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
}
