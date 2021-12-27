package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.AdminBEAN;

public class AdminDAO {
	private DungChung dungChung;
	public AdminDAO() {
		dungChung = new DungChung();// TODO Auto-generated constructor stub
	}
	public AdminBEAN login(String username, String password) throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM ADMIN WHERE Username = ? And Password = ?";
		AdminBEAN adminInfo = null;
		dungChung.connect();
		PreparedStatement state = dungChung.connection.prepareStatement(sql);
		state.setString(1, username);
		state.setString(2, password);
		ResultSet rs = state.executeQuery();
		if (rs.next()) {
			adminInfo = new AdminBEAN(
					rs.getLong("AdminID"),
					rs.getString("Username"),
					rs.getString("Name"),
					rs.getString("AvatarPath"));
		}
		rs.close();
		dungChung.disconnect();
		return adminInfo;
	}
	
	
}
