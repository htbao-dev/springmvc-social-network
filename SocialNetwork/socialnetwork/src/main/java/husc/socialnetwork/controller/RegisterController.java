package husc.socialnetwork.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bean.UserBEAN;
import bo.UserBO;

@Controller
public class RegisterController {
	private UserBO userBO;// = new UserBO();
	
	public RegisterController() {
		userBO = new UserBO();// TODO Auto-generated constructor stub
	}

	@RequestMapping(path = "/register", method = RequestMethod.GET)
	public ModelAndView register(Model model, HttpServletRequest request, 
			@RequestParam(name = "firstName", defaultValue = "") String firstName,
			@RequestParam(name = "lastName", defaultValue = "") String lastName,
			@RequestParam(name = "username", defaultValue = "") String username,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "registerStatus", defaultValue = "1") int registerStatus) {
		try {
			firstName = new String(firstName.getBytes(StandardCharsets.ISO_8859_1), "UTF-8");
			lastName = new String(lastName.getBytes(StandardCharsets.ISO_8859_1), "UTF-8");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("registerStatus", registerStatus);
		request.setAttribute("firstName", firstName);
		request.setAttribute("lastName", lastName);
		request.setAttribute("username", username);
		request.setAttribute("email", email);
		return new ModelAndView("register");
	}
	
	@RequestMapping(path = "/handleResigter", method = RequestMethod.POST)
	public String handleRegister(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam(name = "firstName") String firstName,
			@RequestParam(name = "lastName") String lastName,
			@RequestParam(name = "username") String username,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password,
			@RequestParam(name = "confirmPassword") String confirmPassword) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			int registerStatus = userBO.register(username, firstName, lastName, email, password, confirmPassword);
			redirectAttributes.addAttribute("firstName", firstName);
			redirectAttributes.addAttribute("lastName", lastName);
			redirectAttributes.addAttribute("username", username);
			redirectAttributes.addAttribute("email", email);
			redirectAttributes.addAttribute("registerStatus", registerStatus);
		}catch (NoSuchAlgorithmException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/register";
	}
}
