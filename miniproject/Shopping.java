package miniproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Shopping extends JFrame{
	private JTextField txtId;
	private JPasswordField txtPassword;
	private JButton loginButton, exit;
	private Connection conn;
	
	public Shopping() {
		this.setTitle("JMenuExample");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new GridLayout(3, 2));
		this.getContentPane().add(new JLabel("아이디", JLabel.CENTER));
		this.getContentPane().add(getTxtId());
		this.getContentPane().add(new JLabel("패스워드", JLabel.CENTER));
		this.getContentPane().add(getTxtPassword());
		this.getContentPane().add(getLoginButton());
		this.getContentPane().add(getExit());
		this.setSize(300, 200);
	} 
	
	public JTextField getTxtId() {
		if(txtId == null) {
			txtId = new JTextField();
		}
		return txtId;
	}
	
	public JTextField getTxtPassword() {
		if(txtPassword == null) {
			txtPassword = new JPasswordField();
		}
		return txtPassword;
	}
	
	//로그인 버튼
	public JButton getLoginButton() {
		if(loginButton == null) {
			loginButton = new JButton("로그인");
			loginButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String inputId = new String(txtId.getText());
					String inputPassword = new String(txtPassword.getPassword());
					String id = inputId;
					String password = inputPassword;
					//JOptionPane.showMessageDialog(Shopping.this,
							//"입력한 id: " + id + "\n" + "입력한 password: " + password);
					txtId.setText("");
					txtPassword.setText("");
					
					//Connection conn = null;
					
					//LoginButton logic = new LoginButton();
					//logic.excute1(id, password);
					
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
								MainWindow mainWindow = new MainWindow();
								mainWindow.setVisible(true);
								setVisible(false);
								conn.close();
								
								//있던 현재창 없앰
								//dispose();
								
								//JOptionPane.showMessageDialog(Shopping.this,
										//"환영합니다.");
							} else {
								System.out.println("-----로그인 실패-----");
								JOptionPane.showMessageDialog(Shopping.this,
										"비밀번호가 일치하지 않습니다..");
							}
						} else {
							System.out.println("해당 사용자가 없습니다.");
							JOptionPane.showMessageDialog(Shopping.this,
									"해당 사용자가 없습니다.");
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
			});
		}
		return loginButton;
	}
	
	//로그인 창의 종료 버튼의 exit
	public JButton getExit() {
		if(exit == null) {
			exit = new JButton("종료");
			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exit;
	}

	public static void main(String[] args) {	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Shopping jFrame = new Shopping();
				jFrame.setVisible(true);
			}
		});
	}
}