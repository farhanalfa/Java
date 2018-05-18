package packagesatu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.sql.*;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignUp extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldUsernameL;
	private JPasswordField passwordFieldL;
	private JTextField textFieldFullnameL;
	private JLabel label;
	private JButton btnSubmit;
	private JTextField textFieldUserR;
	private JPasswordField passwordFieldR;
	private JButton btnLogin;
	private JLabel lblUsernameL;
	private JLabel lblFullNameL;
	private JLabel lblPasswordL;
	private JLabel lblUserR;
	private JLabel lblPassR;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	Connection connection = null;
	
	public SignUp() {
		connection = Database.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 153, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUser = new JLabel("SIGN IN");
		lblUser.setBackground(new Color(0, 153, 204));
		lblUser.setForeground(new Color(51, 102, 153));
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblUser.setBounds(449, 52, 145, 44);
		contentPane.add(lblUser);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(51, 102, 153));
		panel.setBounds(0, 0, 341, 411);
		contentPane.add(panel);
		
		label = new JLabel("SIGN UP");
		label.setForeground(new Color(0, 153, 204));
		label.setFont(new Font("Tahoma", Font.BOLD, 30));
		label.setBackground(new Color(0, 153, 204));
		label.setBounds(96, 53, 145, 44);
		panel.add(label);
		
		textFieldUsernameL = new JTextField();
		textFieldUsernameL.setBackground(new Color(240, 255, 240));
		textFieldUsernameL.setBounds(51, 152, 237, 27);
		panel.add(textFieldUsernameL);
		textFieldUsernameL.setColumns(10);
		
		textFieldFullnameL = new JTextField();
		textFieldFullnameL.setBackground(new Color(240, 255, 240));
		textFieldFullnameL.setBounds(51, 219, 237, 27);
		panel.add(textFieldFullnameL);
		textFieldFullnameL.setColumns(10);
		
		passwordFieldL = new JPasswordField();
		passwordFieldL.setBackground(new Color(240, 255, 240));
		passwordFieldL.setBounds(51, 289, 237, 27);
		panel.add(passwordFieldL);
		
		btnSubmit = new JButton("SUBMIT");
		btnSubmit.setBackground(new Color(204, 204, 204));
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query="insert into user (username, fullname, password) values (?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textFieldUsernameL.getText());
					pst.setString(2, textFieldFullnameL.getText());
					pst.setString(3, passwordFieldL.getText());
					pst.execute();
					JOptionPane.showMessageDialog(null, "Data Saved");
					pst.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnSubmit.setForeground(new Color(51, 102, 153));
		btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnSubmit.setBounds(51, 352, 237, 32);
		panel.add(btnSubmit);
		
		lblUsernameL = new JLabel("USERNAME");
		lblUsernameL.setForeground(new Color(0, 153, 204));
		lblUsernameL.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblUsernameL.setBounds(51, 120, 108, 21);
		panel.add(lblUsernameL);
		
		lblFullNameL = new JLabel("FULL NAME");
		lblFullNameL.setForeground(new Color(0, 153, 204));
		lblFullNameL.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblFullNameL.setBounds(51, 190, 98, 18);
		panel.add(lblFullNameL);
		
		lblPasswordL = new JLabel("PASSWORD");
		lblPasswordL.setForeground(new Color(0, 153, 204));
		lblPasswordL.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblPasswordL.setBounds(51, 257, 108, 21);
		panel.add(lblPasswordL);
		
		textFieldUserR = new JTextField();
		textFieldUserR.setBackground(new Color(240, 255, 240));
		textFieldUserR.setColumns(10);
		textFieldUserR.setBounds(400, 184, 237, 27);
		contentPane.add(textFieldUserR);
		
		passwordFieldR = new JPasswordField();
		passwordFieldR.setBackground(new Color(240, 255, 240));
		passwordFieldR.setBounds(400, 268, 237, 27);
		contentPane.add(passwordFieldR);
		
		btnLogin = new JButton("LOGIN");
		btnLogin.setBackground(new Color(204, 204, 204));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query="select * from user where username=? and password=?";
					PreparedStatement pst=connection.prepareStatement(query);
					pst.setString(1, textFieldUserR.getText());
					pst.setString(2, passwordFieldR.getText());
					
					ResultSet rs = pst.executeQuery();
					int count=0;
					while(rs.next()) {
						count=count+1;
						
					}if(count==1) {
						User us = new User();
						us.setVisible(true);
						dispose();
					}else if(count>1) {
						JOptionPane.showMessageDialog(null, "Duplicate Username and Password");
					}else {
						JOptionPane.showMessageDialog(null, "Username and Password isn't correct");
					}
					rs.close();
					pst.close();
				}catch(Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnLogin.setForeground(new Color(51, 102, 153));
		btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnLogin.setBounds(400, 351, 237, 32);
		contentPane.add(btnLogin);
		
		lblUserR = new JLabel("USERNAME");
		lblUserR.setForeground(new Color(51, 102, 153));
		lblUserR.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblUserR.setBounds(449, 138, 145, 35);
		contentPane.add(lblUserR);
		
		lblPassR = new JLabel("PASSWORD");
		lblPassR.setForeground(new Color(51, 102, 153));
		lblPassR.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblPassR.setBounds(449, 222, 145, 35);
		contentPane.add(lblPassR);
		
		JLabel lblUserPng = new JLabel("");
		Image img1 = new ImageIcon(this.getClass().getResource("/username.png")).getImage();
		lblUserPng.setIcon(new ImageIcon(img1));
		lblUserPng.setBounds(400, 138, 46, 35);
		contentPane.add(lblUserPng);
		
		JLabel lblPassPng = new JLabel("");
		Image img2 = new ImageIcon(this.getClass().getResource("/password.png")).getImage();
		lblPassPng.setIcon(new ImageIcon(img2));
		lblPassPng.setBounds(400, 222, 46, 35);
		contentPane.add(lblPassPng);
	}
}
