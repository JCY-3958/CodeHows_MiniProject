package miniproject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class MainWindow extends JFrame {
	private Connection conn;
	Shopping backToMain = null;
	
	private JButton logOutButton, purchase;
	private JTable jTable;
	private JPanel topButton;
	private JComboBox category;
	private String[] columnNames = {"아이디", "비밀번호", "이름"};
	//private DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
	private DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
		public boolean isCellEditable(int rowIndex, int mColIndex) {
			return false;
		}
	};
	
	
	public MainWindow() {
		this.setTitle("쇼핑몰 메인창");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(new JScrollPane(getJTable()),
				BorderLayout.CENTER);
		this.getContentPane().add(getLogOutButton(), BorderLayout.SOUTH);
		this.getContentPane().add(getTopButton(), BorderLayout.NORTH);
		backToMain = new Shopping();
	}
	
	public JButton getLogOutButton() {
		if(logOutButton == null) {
			logOutButton = new JButton("로그아웃");
			logOutButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					backToMain.setVisible(true);
					setVisible(false);
				}
			});
		}
		return logOutButton;
	}
	
	public JTable getJTable() {
		if(jTable == null) {
			jTable = new JTable(tableModel);
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				conn = DriverManager.getConnection(
						"jdbc:mysql://192.168.111.200:3306/thisisjava",
						"java",
						"mysql"
						);
				
				String sql = "" +
						"select userid, userpassword , username from users";
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
					tableModel.addRow(new Object[] {rs.getString("userid"),
						rs.getString("userpassword"),
						rs.getString("username")});
					System.out.println(rs.getString("userid") +
							"\t" + rs.getString("userpassword") +
							"\t" + rs.getString("username"));
				}
				rs.close();
				pstmt.close();
				conn.close();
				
			} catch(Exception e) {
				e.printStackTrace();
				try {
					conn.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				
			}
		}
		return jTable;
	}
	
	public JPanel getTopButton() {
		if(topButton == null) {
			topButton = new JPanel();
			topButton.setLayout(new GridLayout(1, 5));
			topButton.add(getCategory());
			topButton.add(new JLabel(""));
			topButton.add(new JLabel(""));
			topButton.add(new JLabel(""));
			topButton.add(getPurchase());
		}
		return topButton;
	}
	
	public JButton getPurchase() {
		if(purchase == null) {
			purchase = new JButton("결제");
		}
		return purchase;
	}
	
	public JComboBox getCategory() {
		if(category == null) {
			String[] arrString = {
					"의류", "가전", "취미", "잡화", "미용"
			};
			category = new JComboBox(arrString);
		}
		return category;
	}
	
	

}
