package packagesatu;

import java.sql.*;
import javax.swing.*;

public class Database {
	Connection conn = null;
	public static Connection dbConnector() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jresto","farhanalfa","farhanalfa");
			return conn;
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
