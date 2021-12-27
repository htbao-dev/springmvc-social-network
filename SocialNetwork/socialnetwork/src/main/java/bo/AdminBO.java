package bo;

import java.sql.SQLException;
import java.util.ArrayList;

import bean.AdminBEAN;
import bean.PostAndUser;
import dao.AdminDAO;
import dao.PostDAO;

public class AdminBO {
	private AdminDAO adminDAO;
	private PostDAO postDAO;
	public AdminBO() {
		adminDAO = new AdminDAO();// TODO Auto-generated constructor stub
		postDAO = new PostDAO();
	}	
	
	public AdminBEAN login(String username, String password) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return adminDAO.login(username, password);
	}
	

}
