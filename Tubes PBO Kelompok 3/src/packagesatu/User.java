package packagesatu;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.sql.*;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableModel;


public class User extends JFrame {

	private JFrame us;
	private JPanel contentPane;
	private JTable table;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User frame = new User();
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
	private JTextField textFieldKode;
	private JTextField textFieldQty;
	private JTextField textFieldSearch;
	private JTextField textFieldUsername;
	
	public void refreshTable() {
		try {
			String query="select kode as 'Kode', nama as 'Nama Menu', harga as 'Harga (Rp)' from menu";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public User() {
		connection = Database.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 153, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		us = new JFrame();
		JPanel headPanel = new JPanel();
		headPanel.setBackground(new Color(51, 102, 153));
		headPanel.setBounds(0, 0, 684, 80);
		contentPane.add(headPanel);
		headPanel.setLayout(null);
		
		JLabel lblTakashimura = new JLabel("TAKASHIMURA");
		lblTakashimura.setForeground(new Color(240, 248, 255));
		lblTakashimura.setBounds(219, 11, 230, 45);
		lblTakashimura.setFont(new Font("Tahoma", Font.BOLD, 30));
		headPanel.add(lblTakashimura);
		
		JButton btnListMenu = new JButton("LIST MENU");
		btnListMenu.setBounds(29, 57, 110, 23);
		headPanel.add(btnListMenu);
		btnListMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				refreshTable();
			}
		});
		btnListMenu.setForeground(new Color(51, 102, 153));
		btnListMenu.setFont(new Font("Segoe UI", Font.BOLD, 11));
		btnListMenu.setBackground(new Color(192, 192, 192));
		
		textFieldSearch = new JTextField();
		textFieldSearch.setBackground(new Color(240, 255, 240));
		textFieldSearch.setBounds(540, 11, 110, 20);
		headPanel.add(textFieldSearch);
		textFieldSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try {
					String query = "select * from menu where nama=? ";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textFieldSearch.getText() );
					ResultSet rs = pst.executeQuery();
					
					table.setModel(DbUtils.resultSetToTableModel(rs));
					pst.close();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		textFieldSearch.setColumns(10);
		
		JLabel lblSearch = new JLabel("");
		Image img3 = new ImageIcon(this.getClass().getResource("/search16.png")).getImage();
		lblSearch.setIcon(new ImageIcon(img3));
		lblSearch.setBounds(658, 7, 16, 24);
		headPanel.add(lblSearch);
		
		JLabel lblBack = new JLabel("BACK");
		lblBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SignUp sg = new SignUp();
				sg.setVisible(true);
				dispose();
			}
		});
		lblBack.setForeground(new Color(240, 255, 240));
		lblBack.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblBack.setBounds(10, 11, 46, 14);
		headPanel.add(lblBack);
		
		JLabel lblYourSolution = new JLabel("your solution");
		lblYourSolution.setForeground(new Color(240, 248, 255));
		lblYourSolution.setFont(new Font("Sitka Subheading", Font.ITALIC, 12));
		lblYourSolution.setBounds(380, 49, 69, 20);
		headPanel.add(lblYourSolution);
		
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(new Color(51, 102, 153));
		leftPanel.setBounds(0, 80, 169, 331);
		contentPane.add(leftPanel);
		leftPanel.setLayout(null);
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(0, 153, 204));
		panel1.setBounds(24, 11, 119, 108);
		leftPanel.add(panel1);
		panel1.setLayout(null);
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(new Color(0, 153, 204));
		panel2.setBounds(24, 138, 119, 108);
		leftPanel.add(panel2);
		panel2.setLayout(null);
		
		JButton btnMyOrder = new JButton("MY ORDER");
		btnMyOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String selection=(String)textFieldUsername.getText();
					String query="select m.kode as 'Kode', m.nama as 'Nama Menu', m.harga as 'Harga (Rp)', n.qty as 'Qty' from menu m inner join nota n on m.kode=n.kode where username='"+selection+"' ";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnMyOrder.setBackground(new Color(204, 204, 204));
		btnMyOrder.setForeground(new Color(51, 102, 153));
		btnMyOrder.setFont(new Font("Segoe UI", Font.BOLD, 10));
		btnMyOrder.setBounds(10, 74, 99, 23);
		panel1.add(btnMyOrder);
				
		JButton btnCallWaiter = new JButton("CALL WAITER");
		btnCallWaiter.setBackground(new Color(204, 204, 204));
		btnCallWaiter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Mohon Menunggu, \nPelayan kami akan segera menghampiri anda");
			}
		});
		btnCallWaiter.setForeground(new Color(51, 102, 153));
		btnCallWaiter.setFont(new Font("Segoe UI", Font.BOLD, 10));
		btnCallWaiter.setBounds(10, 74, 99, 23);
		panel2.add(btnCallWaiter);
		
		JLabel lblLabelOrder = new JLabel("");
		Image img1 = new ImageIcon(this.getClass().getResource("/order.png")).getImage();
		lblLabelOrder.setIcon(new ImageIcon(img1));
		lblLabelOrder.setBounds(32, 11, 58, 52);
		panel1.add(lblLabelOrder);
		
		JLabel lblLabelWaiter = new JLabel("");
		Image img2 = new ImageIcon(this.getClass().getResource("/callwaiter.png")).getImage();
		lblLabelWaiter.setIcon(new ImageIcon(img2));
		lblLabelWaiter.setBounds(33, 11, 59, 52);
		panel2.add(lblLabelWaiter);
		
		JButton btnHelp = new JButton("HELP");
		btnHelp.setBounds(24, 257, 119, 23);
		leftPanel.add(btnHelp);
		btnHelp.setBackground(new Color(204, 204, 204));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Silahkan Hubungi Karyawan kami");
			}
		});
		btnHelp.setFont(new Font("Segoe UI", Font.BOLD, 11));
		btnHelp.setForeground(new Color(51, 102, 153));
		
		JButton btnAbout = new JButton("ABOUT");
		btnAbout.setBounds(24, 285, 119, 23);
		leftPanel.add(btnAbout);
		btnAbout.setBackground(new Color(204, 204, 204));
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Takashimura v0.1 'Aplikasi Pesan Makanan Khas Jepang' ");
			}
		});
		btnAbout.setFont(new Font("Segoe UI", Font.BOLD, 11));
		btnAbout.setForeground(new Color(51, 102, 153));
				
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(179, 91, 320, 309);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					int row = table.getSelectedRow();
					String kode=(table.getModel().getValueAt(row, 0)).toString();
					
					String query = "select * from menu where kode='"+kode+"' ";
					PreparedStatement pst=connection.prepareStatement(query);
	
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()) {
						textFieldKode.setText(rs.getString("kode"));
					}
					pst.close();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblOrder = new JLabel("FORM ORDER");
		lblOrder.setBackground(new Color(51, 102, 153));
		lblOrder.setForeground(new Color(240, 255, 240));
		lblOrder.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblOrder.setBounds(533, 104, 110, 24);
		contentPane.add(lblOrder);
		
		JLabel lblKode = new JLabel("KODE");
		lblKode.setBackground(new Color(51, 102, 153));
		lblKode.setForeground(new Color(240, 255, 240));
		lblKode.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblKode.setBounds(520, 211, 73, 20);
		contentPane.add(lblKode);
		
		JLabel lblQty = new JLabel("QTY");
		lblQty.setBackground(new Color(51, 102, 153));
		lblQty.setForeground(new Color(240, 255, 240));
		lblQty.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblQty.setBounds(520, 273, 55, 20);
		contentPane.add(lblQty);
		
		textFieldKode = new JTextField();
		textFieldKode.setBackground(new Color(240, 255, 240));
		textFieldKode.setBounds(520, 242, 133, 20);
		contentPane.add(textFieldKode);
		textFieldKode.setColumns(10);
		
		textFieldQty = new JTextField();
		textFieldQty.setBackground(new Color(240, 255, 240));
		textFieldQty.setColumns(10);
		textFieldQty.setBounds(520, 304, 133, 20);
		contentPane.add(textFieldQty);
		
		JButton btnOrder = new JButton("ORDER");
		btnOrder.setBackground(new Color(204, 204, 204));
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query="insert into nota (username, kode, qty) values (?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textFieldUsername.getText());
					pst.setString(2, textFieldKode.getText());
					pst.setString(3, textFieldQty.getText());
					
					pst.execute();
					JOptionPane.showMessageDialog(null, "Order Saved");
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnOrder.setForeground(new Color(51, 102, 153));
		btnOrder.setFont(new Font("Segoe UI", Font.BOLD, 11));
		btnOrder.setBounds(520, 354, 133, 23);
		contentPane.add(btnOrder);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setBackground(new Color(240, 255, 240));
		textFieldUsername.setBounds(520, 180, 133, 20);
		contentPane.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("USERNAME");
		lblUsername.setForeground(new Color(240, 255, 240));
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUsername.setBackground(new Color(51, 102, 153));
		lblUsername.setBounds(520, 149, 73, 20);
		contentPane.add(lblUsername);		
		
		refreshTable();
	}
}
