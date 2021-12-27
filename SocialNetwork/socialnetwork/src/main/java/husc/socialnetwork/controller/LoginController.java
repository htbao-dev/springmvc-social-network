package husc.socialnetwork.controller;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import bean.UserBEAN;
import bo.UserBO;
import exception.NotFoundException;

@Controller
public class LoginController {
	private UserBO userBO;
	
	public LoginController() {
		userBO = new UserBO();// TODO Auto-generated constructor stub
	}

    @RequestMapping("/login")
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
					UserBEAN userInfo = userBO.login(username, passString);
					if (userInfo == null) {
						status = -1;
					}
					else {
						session.setAttribute("userInfo", userInfo);
						return new ModelAndView("redirect:/home");
					}
				} catch (NotFoundException e) {
					status = -2;
					e.printStackTrace();
				} catch (SQLException | ClassNotFoundException e) {
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
        return new ModelAndView("login");
    }
    
	@RequestMapping(path = "/forgetPassword")
	public  String forgetPassword(Model model) {
		return "forgetPassword";
	}
	
	@RequestMapping(path = "/handleForgetPassword", method = RequestMethod.POST)
	public String handleForgetPassword(
			@RequestParam(name = "username", required = true) String username,
			Model model) {
		try {
			UserBEAN user = userBO.getUser(username);
			if (user == null) {
				model.addAttribute("status", -1);
			}
			else {
				model.addAttribute("status", 1);
				model.addAttribute("email", user.getEmail());
				System.out.println(userBO.getLocalIp());
				String code = userBO.createForgetPassword(user);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "forgetPassword";
		
	}
	
	@RequestMapping(path = "/changePassword", method = RequestMethod.GET)
	public String changePassword(
			@RequestParam(name = "code", required = true)String code,
			Model model) {
		String page = "changePassword";
		try {
			UserBEAN user = userBO.getUserByCode(code);
			if (user == null) {
				page = "errorPage";
			}
			else{
				System.out.println(user.getFirstName());
				model.addAttribute("userInfo", user);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}
	
	@RequestMapping(path = "/handleChangePassword", method = RequestMethod.POST)
	public @ResponseBody String handleChangePassword(
			@RequestParam(name ="password")String password,
			@RequestParam(name ="confirmPassword")String confirmPassword,
			@RequestParam(name = "userID") long userID) {
		int status;
		String response = "";
		try {
			status = userBO.changePassword(userID, password, confirmPassword);
			JSONObject json = new JSONObject();
			json.append("status", status);
			response = json.toString();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

}
