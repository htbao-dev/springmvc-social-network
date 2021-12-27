package bo;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.multipart.MultipartFile;

import bean.CreateNewPostResult;
import bean.UserBEAN;
import dao.PostDAO;
import dao.UserDAO;
import exception.NotFoundException;

public class UserBO {
	private final String REGEX_SYMBOL = ".*[!$%^&*()+|~\\-=`{}\\[\\]:\";<>?,.\\/#@].*";
	private final String EMAIL = "18T1021011@husc.edu.vn";
	private final String PASSWORD_EMAIL = "18T1021011Bao";
	private final int LOG_ROUNDS = 12;
	private UserDAO userDAO;
	private PostDAO postDAO;
	public UserBO() {
		userDAO = new UserDAO();// TODO Auto-generated constructor stub
		postDAO = new PostDAO();
	}
	
	public UserBEAN login(String username, String password) 
			throws ClassNotFoundException, NotFoundException, SQLException {
		return userDAO.login(username, password);
	}
	
	public UserBEAN getUser(long userID) throws ClassNotFoundException, SQLException {
		return userDAO.getUser(userID);
	}
	
	/**
	 * 
	 * @param registerInfo
	 * @param password
	 * @param confirmPassword
	 * @return 
	 * <br>  0: ok
	 * <br> -1: invalid name
	 * <br> -2: invalid username
	 * <br> -3: invalid password
	 * <br> -4: invalid confirm password
	 * <br> -5: exists username
	 * <br> -99: unkown error
	 * @throws NoSuchAlgorithmException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int register(String username, String firstName, String lastName, String email, String password, String confirmPassword) 
			throws NoSuchAlgorithmException, ClassNotFoundException, SQLException {
		
		int registerStatus = -99;
		if (!isValidName(firstName, lastName)) {
			registerStatus = -1;
		}else if(!isValidUsername(username)) {
			registerStatus = -2;
		}else if(!isValidPassword(password)) {
			registerStatus = -3;
		}else if(!isValidConfirmPassword(password, confirmPassword)) {
			registerStatus = -4;
		}else {
			String avatar = ("img/default-avatar.png");
			password = hashPassword(password);
			int tmp = userDAO.register(username, firstName, lastName, email, avatar, password);
			if (tmp == -1) {
				registerStatus = -5;
			}else {
				registerStatus = 0;
			}
		}
		return registerStatus;
	}
	
	public String changeAvatar(long userID, String content, MultipartFile file) throws ClassNotFoundException, SQLException {
		MultipartFile[] datas = {file};
		CreateNewPostResult rs = postDAO.createNewPost(content, datas, userID);
		String avatarPath = postDAO.getPost(rs.getPostID()).getPostImg().get(0);
		userDAO.changeAvatar(userID, avatarPath);
		return avatarPath;
		
	}
	
	private boolean isValidUsername(String username) {
		return (username != null 
				&& !username.equals("")
				&& !username.matches(REGEX_SYMBOL)
				&& !username.contains(" ")
				&& username.length() >= 6);
	}
	
	private boolean isValidPassword(String password) {
		return (password != null 
				&& !password.equals("")
				&& !password.matches(REGEX_SYMBOL)
				&& !password.contains(" ")
				&& password.length() >= 6);
	}
	
	private boolean isValidConfirmPassword(String password, String confirmPassword) {
		return password.equals(confirmPassword);
	}
	
	private boolean isValidName(String firstName, String lastName) {
		boolean isValid = (firstName != null && !firstName.equals("") && !firstName.matches(".*\\d.*") && !firstName.matches(REGEX_SYMBOL));
		isValid &= (lastName != null && !lastName.equals("") && !lastName.matches(".*\\d.*") && !lastName.matches(REGEX_SYMBOL));
		return isValid;
	}
	
	private String createCodeForgetPassword() {
		 
	    int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();
	    StringBuilder buffer = new StringBuilder(targetStringLength);
	    for (int i = 0; i < targetStringLength; i++) {
	        int randomLimitedInt = leftLimit + (int) 
	          (random.nextFloat() * (rightLimit - leftLimit + 1));
	        buffer.append((char) randomLimitedInt);
	    }
	    String generatedString = buffer.toString();

	    System.out.println(generatedString);
		return generatedString;
	}
	
	public String createForgetPassword(UserBEAN user) throws Exception {
		String forgetCode = this.createCodeForgetPassword();
		userDAO.setForgetCode(user.getUserID(), forgetCode);
		
		sendMail(user, forgetCode);
		
		Thread forgetPasswordController = new ForgetPasswordController(user.getUserID(), forgetCode);
		forgetPasswordController.start();
		
		return forgetCode;
	}
	
	public int changePassword(long userID, String password, String confirmPassword) throws ClassNotFoundException, SQLException {
		int status = 0;
		if (!isValidPassword(password)) {
			status = -1;
		}
		else if (!isValidConfirmPassword(password, confirmPassword)) {
			status = -2;
		}
		else {
			String hashedPassword = hashPassword(password);
			userDAO.changePassword(userID, hashedPassword);
			status = 0;
			
		}
		return status;
	}
	
	public int removeForgetCode(long userID, String forgetCode) throws ClassNotFoundException, SQLException {
		int status = 0;
		status = userDAO.removeForgetCode(userID, forgetCode);
		return status;
	}
	
	public boolean checkForgetCode(long userID, String forgetCode) throws ClassNotFoundException, SQLException {
		return userDAO.checkForgetCode(userID, forgetCode);
	}
	
	private class ForgetPasswordController extends Thread{
		private String forgetCode;
		private long userID;
		
		public ForgetPasswordController(long userID,String forgetCode) {
			this.userID = userID;
			this.forgetCode = forgetCode;
		}
		
		public void run() {
			try {
				sleep(5* 60 *1000);
//				sleep(5* 1000);
				removeForgetCode(userID, forgetCode);
				System.out.println("da xoa");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void sendMail(UserBEAN user, String forgetCode) throws Exception {
		 	final String url1 = "http://localhost:8080/socialnetwork/changePassword?code=" + forgetCode;
		 	final String url2 = "http://"+getLocalIp()+":8080/socialnetwork/changePassword?code=" + forgetCode;
			final String fromEmail = EMAIL;
	        // Mat khai email cua ban
	        final String password = PASSWORD_EMAIL;
	        // dia chi email nguoi nhan
	        final String toEmail = user.getEmail();
	        System.out.println("to email: " + toEmail);
	        final String subject = "Đổi mật khẩu";
	        String body = "click vào link sau để đổi mật khẩu: " + url1;
	        body += "\n hoặc: " + url2;
	        
	        Properties props = new Properties();
	        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
	        props.put("mail.smtp.port", "587"); //TLS Port
	        props.put("mail.smtp.auth", "true"); //enable authentication
	        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
	        Authenticator auth = new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(fromEmail, password);
	            }
	        };
	        Session session = Session.getInstance(props, auth);
	        MimeMessage msg = new MimeMessage(session);
	        //set message headers
	        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");

				msg.addHeader("format", "flowed");

	        msg.addHeader("Content-Transfer-Encoding", "8bit");
	        msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));
	        msg.setReplyTo(InternetAddress.parse(fromEmail, false));
	        msg.setSubject(subject, "UTF-8");
	        msg.setText(body, "UTF-8");
	        msg.setSentDate(new Date());
	        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
	        Transport.send(msg);
	        System.out.println("Gui mail thanh cong");
	}
	
	public String getLocalIp() throws SocketException {
		String ip = "";
		for (
			    final Enumeration< NetworkInterface > interfaces =
			        NetworkInterface.getNetworkInterfaces( );
			    interfaces.hasMoreElements( );
			)
			{
			    final NetworkInterface cur = interfaces.nextElement( );

			    if ( cur.isLoopback( ) )
			    {
			        continue;
			    }

			    for ( final InterfaceAddress addr : cur.getInterfaceAddresses( ) )
			    {
			        final InetAddress inet_addr = addr.getAddress( );

			        if ( !( inet_addr instanceof Inet4Address ) )
			        {
			            continue;
			        }

			        if (inet_addr.getHostAddress().contains("192.168.1.")) {
			        	ip = inet_addr.getHostAddress();
			        	return ip;
			        }
			    }
			}
		return ip;

	}
	
	private String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(LOG_ROUNDS));
	}
	
	public UserBEAN getUser(String username) throws ClassNotFoundException, SQLException {
		UserBEAN user = userDAO.getUser(username);
		return user;
	}
	
	public UserBEAN getUserByCode(String forgetCode) throws ClassNotFoundException, SQLException {
		return userDAO.getUserByCode(forgetCode);
	}
}
