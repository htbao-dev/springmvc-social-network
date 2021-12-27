package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DungChung {
	final private String URL="jdbc:sqlserver://HUYNHBAO\\SQLEXPRESS:1433;databaseName=SocialNetworkDB;user=sa; password=18T1021011";
	final private String CLASS_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	public Connection connection;
	public void connect() throws SQLException, ClassNotFoundException{
		//b1: Xac dinh HQTCSDL
    	Class.forName(CLASS_NAME);
    	connection= DriverManager.getConnection(URL);
	}
	
	public void disconnect() throws SQLException {
		connection.close();
	}
}
