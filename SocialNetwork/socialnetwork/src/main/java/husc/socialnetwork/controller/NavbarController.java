package husc.socialnetwork.controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import bean.CreateNewPostResult;
import bean.PostAndUser;
import bean.PostBEAN;
import bean.UserBEAN;
import bo.PostBO;
import bo.UserBO;

@Controller
public class NavbarController {
	
	private PostBO postBO;
	private UserBO userBO;
	public NavbarController() {
		postBO = new PostBO();// TODO Auto-generated constructor stub
		userBO = new UserBO();
	}
	
	@RequestMapping(path = "/logout")
	public ModelAndView logout(Model model, HttpSession session) {
		session.removeAttribute("userInfo");
		return new ModelAndView("redirect:/login");
	}
	
	@RequestMapping(path = "/changeAvatar", method = RequestMethod.POST)
	public ModelAndView changeAvatar(
			HttpServletRequest request,
			HttpSession session,
			@RequestParam(name = "file") MultipartFile file){

		UserBEAN userInfo = (UserBEAN)session.getAttribute("userInfo");
		
		String content = userInfo.getFirstName() + " " + userInfo.getLastName() + " đã thay đổi ảnh đại diện.";
		try {
			String avatarPath = userBO.changeAvatar(userInfo.getUserID(), content, file);
			userInfo.setAvatarPath(avatarPath);
			session.setAttribute("userInfo", userInfo);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/home");
	}
	
}
