package packagesatu;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Image;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	Connection connection = null;
	
	public Login() {
		initialize();
		connection = Database.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 153, 204));
		frame.setBounds(100, 100, 700, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 102, 153));
		panel.setBounds(0, 0, 333, 411);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("TAKASHIMURA");
		lblNewLabel.setForeground(new Color(0, 153, 204));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblNewLabel.setBounds(37, 102, 268, 53);
		panel.add(lblNewLabel);
		
		JLabel label = new JLabel("your solution");
		label.setForeground(new Color(0, 153, 204));
		label.setFont(new Font("Sitka Subheading", Font.ITALIC, 20));
		label.setBounds(180, 152, 125, 20);
		panel.add(label);
		
		JLabel lblAdmin = new JLabel("ADMIN");
		lblAdmin.setBackground(new Color(255, 69, 0));
		lblAdmin.setForeground(new Color(51, 102, 153));
		lblAdmin.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblAdmin.setBounds(445, 38, 132, 43);
		frame.getContentPane().add(lblAdmin);
		
		JLabel lblUsername = new JLabel("USERNAME");
		lblUsername.setForeground(new Color(51, 102, 153));
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblUsername.setBounds(408, 116, 103, 32);
		frame.getContentPane().add(lblUsername);
		
		textField = new JTextField();
		textField.setBackground(new Color(240, 255, 240));
		textField.setBounds(370, 159, 273, 32);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setForeground(new Color(51, 102, 153));
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblPassword.setBounds(408, 202, 103, 31);
		frame.getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBackground(new Color(240, 255, 240));
		passwordField.setBounds(370, 244, 273, 32);
		frame.getContentPane().add(passwordField);
		
		JButton btnNewButton = new JButton("LOGIN");
		btnNewButton.setBackground(new Color(204, 204, 204));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query="select * from admin where username=? and password=?";
					PreparedStatement pst=connection.prepareStatement(query);
					pst.setString(1, textField.getText());
					pst.setString(2, passwordField.getText());
					
					ResultSet rs = pst.executeQuery();
					int count=0;
					while(rs.next()) {
						count=count+1;
						
					}if(count==1) {
						frame.dispose();
						Admin mm = new Admin();
						mm.setVisible(true);
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
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnNewButton.setForeground(new Color(51, 102, 153));
		btnNewButton.setBounds(370, 302, 273, 32);
		frame.getContentPane().add(btnNewButton);
		
		JLabel label1 = new JLabel("");
		Image img1 = new ImageIcon(this.getClass().getResource("/username.png")).getImage();
		label1.setIcon(new ImageIcon(img1));
		label1.setBounds(370, 116, 46, 35);
		frame.getContentPane().add(label1);
		
		JLabel label2 = new JLabel("");
		Image img2 = new ImageIcon(this.getClass().getResource("/password.png")).getImage();
		label2.setIcon(new ImageIcon(img2));
		label2.setBounds(370, 202, 46, 35);
		frame.getContentPane().add(label2);
		
		JLabel lblNotAdmin = new JLabel("Not an admin?");
		lblNotAdmin.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNotAdmin.setForeground(new Color(51, 102, 153));
		lblNotAdmin.setBounds(418, 355, 103, 24);
		frame.getContentPane().add(lblNotAdmin);
		
		JLabel lblClickHere = new JLabel("Click Here");
		lblClickHere.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				SignUp su = new SignUp();
				su.setVisible(true);
			}
		});
		lblClickHere.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblClickHere.setForeground(new Color(51, 102, 153));
		lblClickHere.setBounds(525, 355, 80, 24);
		frame.getContentPane().add(lblClickHere);
	}
}
