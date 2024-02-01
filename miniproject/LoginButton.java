package miniproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class LoginButton {
	private Connection conn;
	
	public LoginButton() {
	}

	public void excute1(String id, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(
					"jdbc:mysql://192.168.111.200:3306/thisisjava",
					"java",
					"mysql"
					);
			
			String sql = "" +
					"select userid, userpassword , username " +
					"from users " +
					"where userid = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id); //1은 sql에 ?에 대한 인덱스
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				String dbPassword = rs.getString("userpassword");
			
				if(password.equals(dbPassword)) {
					System.out.println("-----로그인 성공-----");
					//메인창 띄우기
					MainWindow second = new MainWindow();
					second.setVisible(true);
					conn.close();
					
					//있던 현재창 없앰
					//dispose();
					
					//JOptionPane.showMessageDialog(Shopping.this,
						//"환영합니다.");
				} else {
					System.out.println("-----로그인 실패-----");
					//JOptionPane.showMessageDialog(Shopping.this,
						//"비밀번호가 일치하지 않습니다..");
				}
			} else {
				System.out.println("해당 사용자가 없습니다.");
				//JOptionPane.showMessageDialog(Shopping.this,
						//"해당 사용자가 없습니다.");
			}
			
			rs.close();
			pstmt.close();
			
		} catch(Exception e1) {
			e1.printStackTrace();
			try {
				conn.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}

}
