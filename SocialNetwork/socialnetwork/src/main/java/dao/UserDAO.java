package dao;

import java.awt.Taskbar.State;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.mindrot.jbcrypt.BCrypt;

import bean.UserBEAN;
import exception.NotFoundException;
public class UserDAO {
	private DungChung dungChung;
	public UserDAO() {
		dungChung = new DungChung();// TODO Auto-generated constructor stub
	}
	
	/**
	 * return: </br>
	 * 		0: OK.
	 * 		</br>
	 * 		 -1: exist username
	 */
	public int register(String username, String firstName, String lastName, String email, String avatar, String hash) 
			throws NoSuchAlgorithmException, ClassNotFoundException, SQLException {
		String sql = "{call dbo.Register(?, ?, ?, ?, ?, ?, ?)}";
		dungChung.connect();
		CallableStatement call = dungChung.connection.prepareCall(sql);
		call.setString(1, username);
		call.setString(2, hash);
		call.setString(3, firstName);
		call.setString(4, lastName);
		call.setString(5, email);
		call.setString(6, avatar);
		call.registerOutParameter(7, Types.INTEGER);
		call.execute();
		int status = call.getInt(7);
		dungChung.disconnect();
		return status;
	}

	
	private boolean checkPassword(String username, String password) 
			throws NotFoundException, ClassNotFoundException, SQLException {
		String sql = "SELECT Password FROM [User] WHERE Username = ?";
		dungChung.connect();
		PreparedStatement state = dungChung.connection.prepareStatement(sql);
		state.setString(1, username);
		ResultSet rs = state.executeQuery();
		String hashPassword = "";
		boolean valuate = false;
		if (rs.next()) {
			hashPassword = rs.getString(1);
			valuate = BCrypt.checkpw(password, hashPassword);
		}
		else {
			throw new NotFoundException("Khong tim thay username");
		}
		rs.close();
		dungChung.disconnect();
		return valuate;
	}
	
	public UserBEAN login(String username, String password) 
			throws NotFoundException, ClassNotFoundException, SQLException {
		UserBEAN userBEAN = null;
		if (checkPassword(username, password)) {
			String sql = "SELECT UserID, FirstName, LastName, Email, AvatarPath FROM [User] WHERE Username = ?";		
			dungChung.connect();
			PreparedStatement state = dungChung.connection.prepareStatement(sql);
			state.setString(1, username);
			ResultSet rs = state.executeQuery();
			rs.next();
			userBEAN = new UserBEAN(rs.getLong(1), username, rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			rs.close();
			dungChung.disconnect();
		}
		return userBEAN;
	}
	
	public UserBEAN getUser(long userID) throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM [User] WHERE UserID = ?";
		UserBEAN userInfo = null;
		dungChung.connect();
		PreparedStatement state = dungChung.connection.prepareStatement(sql);
		state.setLong(1, userID);
		ResultSet rs = state.executeQuery();
		if (rs.next()) {
			userInfo = new UserBEAN(rs.getLong("UserID"), 
					rs.getString("Username"), 
					rs.getString("FirstName"),
					rs.getString("LastName"), 
					rs.getString("Email"),
					rs.getString("AvatarPath"));
		}
		rs.close();
		dungChung.disconnect();
		return userInfo;
		
	}
	
	public void changeAvatar(long userID, String avatarPath) throws ClassNotFoundException, SQLException {
		String sql = "UPDATE [User] SET AvatarPath = ? WHERE UserID = ?";
		dungChung.connect();
		PreparedStatement state = dungChung.connection.prepareStatement(sql);
		state.setString(1, avatarPath);
		state.setLong(2, userID);
		state.executeUpdate();
		dungChung.disconnect();
	}
	
	public boolean checkForgetCode(long userID, String forgetCode) throws ClassNotFoundException, SQLException {
		String sql = "{? = call dbo.func_checkForgetCode(?, ?)}";
		dungChung.connect();
		CallableStatement call = dungChung.connection.prepareCall(sql);
		call.registerOutParameter(1, Types.INTEGER);
		call.setLong(2, userID);
		call.setString(3, forgetCode);
		
		call.execute();
		boolean result = (call.getInt(1) == 1)?true:false;
		dungChung.disconnect();
		return result;
	}
	
	public int setForgetCode(long userID, String forgetCode) throws ClassNotFoundException, SQLException {
		String sql = "{call proc_setForgetCode (?, ?, ?)}";
		int status = 0;
		dungChung.connect();
		CallableStatement call = dungChung.connection.prepareCall(sql);
		call.setLong(1, userID);
		call.setString(2, forgetCode);
		call.registerOutParameter(3, Types.INTEGER);
		call.execute();
		status = call.getInt(3);
		dungChung.disconnect();
		return status;
	}
	
	public int removeForgetCode (long userID, String forgetCode) throws ClassNotFoundException, SQLException {
		int status = 0;
		String sql = "{call proc_removeForgetCode( ?, ?, ?)}";
		dungChung.connect();
		CallableStatement call = dungChung.connection.prepareCall(sql);
		call.setLong(1, userID);
		call.setString(2, forgetCode);
		call.registerOutParameter(3, Types.INTEGER);
		call.execute();
		status = call.getInt(3);
		
		dungChung.disconnect();
		return status;
	}

	public void changePassword(long userID, String hashedPassword) throws ClassNotFoundException, SQLException {
		String sql = "exec proc_ChangePwdOnForgetPwd ?, ?";
		dungChung.connect();
		PreparedStatement state = dungChung.connection.prepareStatement(sql);
		state.setLong(1, userID);
		state.setString(2, hashedPassword);
		state.execute();
		dungChung.disconnect();
		// TODO Auto-generated method stub
		
	}

	public UserBEAN getUser(String username) throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM [User] WHERE Username = ?";
		UserBEAN userInfo = null;
		dungChung.connect();
		PreparedStatement state = dungChung.connection.prepareStatement(sql);
		state.setString(1, username);
		ResultSet rs = state.executeQuery();
		if (rs.next()) {
			userInfo = new UserBEAN(rs.getLong("UserID"), 
					rs.getString("Username"), 
					rs.getString("FirstName"),
					rs.getString("LastName"), 
					rs.getString("Email"),
					rs.getString("AvatarPath"));
		}
		rs.close();
		dungChung.disconnect();
		return userInfo;
	}

	public UserBEAN getUserByCode(String forgetCode) throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM [User] WHERE ForgetCode = ?";
		UserBEAN userInfo = null;
		dungChung.connect();
		PreparedStatement state = dungChung.connection.prepareStatement(sql);
		state.setString(1, forgetCode);
		ResultSet rs = state.executeQuery();
		if (rs.next()) {
			userInfo = new UserBEAN(rs.getLong("UserID"), 
					rs.getString("Username"), 
					rs.getString("FirstName"),
					rs.getString("LastName"), 
					rs.getString("Email"),
					rs.getString("AvatarPath"));
		}
		rs.close();
		dungChung.disconnect();
		return userInfo;
	}
}
