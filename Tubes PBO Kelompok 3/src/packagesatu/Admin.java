package packagesatu;

import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;
import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Admin extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin frame = new Admin();
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
	private JTextField textFieldNama;
	private JTextField textFieldHarga;
	private JTextField textFieldKode2;
	private JTextField textFieldNama2;
	private JTextField textFieldHarga2;
	private JTextField textFieldKode3;
	
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
	
	public Admin() {
		connection = Database.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 102, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 153, 204));
		panel.setBounds(276, 0, 408, 411);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 26, 355, 334);
		panel.add(scrollPane);
		
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
						textFieldKode2.setText(rs.getString("kode"));
						textFieldNama2.setText(rs.getString("nama"));
						textFieldHarga2.setText(rs.getString("harga"));
					}
					pst.close();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnOrderList = new JButton("ORDER LIST");
		btnOrderList.setBackground(new Color(204, 204, 204));
		btnOrderList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query="select n.username as 'Username', m.kode as 'Kode', m.nama as 'Menu', m.harga as 'Harga', n.qty as 'Qty' from menu m inner join nota n on m.kode=n.kode";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnOrderList.setForeground(new Color(51, 102, 153));
		btnOrderList.setFont(new Font("Segoe UI", Font.BOLD, 11));
		btnOrderList.setBounds(165, 371, 99, 23);
		panel.add(btnOrderList);
		
		JButton btnMenuList = new JButton("MENU LIST");
		btnMenuList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshTable();
			}
		});
		btnMenuList.setForeground(new Color(51, 102, 153));
		btnMenuList.setFont(new Font("Segoe UI", Font.BOLD, 11));
		btnMenuList.setBackground(new Color(204, 204, 204));
		btnMenuList.setBounds(37, 371, 106, 23);
		panel.add(btnMenuList);
		
		JButton btnUserList = new JButton("USER LIST");
		btnUserList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query="select * from user";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnUserList.setForeground(new Color(51, 102, 153));
		btnUserList.setFont(new Font("Segoe UI", Font.BOLD, 11));
		btnUserList.setBackground(new Color(204, 204, 204));
		btnUserList.setBounds(283, 371, 99, 23);
		panel.add(btnUserList);
		
		JButton btnInsert = new JButton("INSERT");
		btnInsert.setBackground(new Color(204, 204, 204));
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query="insert into menu (kode, nama, harga) values (?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textFieldKode.getText());
					pst.setString(2, textFieldNama.getText());
					pst.setString(3, textFieldHarga.getText());
					
					pst.execute();
					JOptionPane.showMessageDialog(null, "Data Saved");
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				refreshTable();
			}
		});
		btnInsert.setForeground(new Color(51, 102, 153));
		btnInsert.setFont(new Font("Segoe UI", Font.BOLD, 11));
		btnInsert.setBounds(97, 171, 118, 23);
		contentPane.add(btnInsert);
		
		JLabel lblKode = new JLabel("KODE");
		lblKode.setForeground(new Color(0, 153, 204));
		lblKode.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblKode.setBounds(21, 79, 61, 19);
		contentPane.add(lblKode);
		
		JLabel lblNama = new JLabel("NAMA");
		lblNama.setForeground(new Color(0, 153, 204));
		lblNama.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNama.setBounds(21, 109, 61, 20);
		contentPane.add(lblNama);
		
		JLabel lblHarga = new JLabel("HARGA");
		lblHarga.setForeground(new Color(0, 153, 204));
		lblHarga.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblHarga.setBounds(21, 141, 61, 19);
		contentPane.add(lblHarga);
		
		textFieldKode = new JTextField();
		textFieldKode.setBackground(new Color(240, 255, 240));
		textFieldKode.setBounds(97, 78, 148, 20);
		contentPane.add(textFieldKode);
		textFieldKode.setColumns(10);
		
		textFieldNama = new JTextField();
		textFieldNama.setBackground(new Color(240, 255, 240));
		textFieldNama.setBounds(97, 109, 148, 20);
		contentPane.add(textFieldNama);
		textFieldNama.setColumns(10);
		
		textFieldHarga = new JTextField();
		textFieldHarga.setBackground(new Color(240, 255, 240));
		textFieldHarga.setBounds(97, 140, 148, 20);
		contentPane.add(textFieldHarga);
		textFieldHarga.setColumns(10);
		
		JLabel lblAdmin = new JLabel("ADMIN");
		lblAdmin.setForeground(new Color(0, 153, 204));
		lblAdmin.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblAdmin.setBounds(80, 22, 118, 31);
		contentPane.add(lblAdmin);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.setBackground(new Color(204, 204, 204));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query="update menu set kode='"+textFieldKode2.getText()+"', nama='"+textFieldNama2.getText()+"', harga='"+textFieldHarga2.getText()+"' where kode='"+textFieldKode2.getText()+"' ";
					PreparedStatement pst = connection.prepareStatement(query);
				
					pst.execute();
					
					JOptionPane.showMessageDialog(null, "Data Updated");
					
					pst.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				refreshTable();
			}
		});
		btnUpdate.setForeground(new Color(51, 102, 153));
		btnUpdate.setFont(new Font("Segoe UI", Font.BOLD, 11));
		btnUpdate.setBounds(97, 298, 118, 23);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.setBackground(new Color(204, 204, 204));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query="delete from menu where kode='"+textFieldKode3.getText()+"' ";
					PreparedStatement pst = connection.prepareStatement(query);
				
					pst.execute();
					
					JOptionPane.showMessageDialog(null, "Data Deleted");
					
					pst.close();
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				refreshTable();
			}
		});
		btnDelete.setForeground(new Color(51, 102, 153));
		btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 11));
		btnDelete.setBounds(97, 365, 118, 23);
		contentPane.add(btnDelete);
		

		
		JLabel lblKode2 = new JLabel("KODE");
		lblKode2.setForeground(new Color(0, 153, 204));
		lblKode2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblKode2.setBounds(21, 206, 61, 19);
		contentPane.add(lblKode2);
		
		textFieldKode2 = new JTextField();
		textFieldKode2.setBackground(new Color(240, 255, 240));
		textFieldKode2.setColumns(10);
		textFieldKode2.setBounds(97, 205, 148, 20);
		contentPane.add(textFieldKode2);
		
		JLabel lblNama2 = new JLabel("NAMA");
		lblNama2.setForeground(new Color(0, 153, 204));
		lblNama2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNama2.setBounds(21, 236, 61, 20);
		contentPane.add(lblNama2);
		
		textFieldNama2 = new JTextField();
		textFieldNama2.setBackground(new Color(240, 255, 240));
		textFieldNama2.setColumns(10);
		textFieldNama2.setBounds(97, 236, 148, 20);
		contentPane.add(textFieldNama2);
		
		JLabel lblHarga2 = new JLabel("HARGA");
		lblHarga2.setForeground(new Color(0, 153, 204));
		lblHarga2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblHarga2.setBounds(21, 268, 61, 19);
		contentPane.add(lblHarga2);
		
		textFieldHarga2 = new JTextField();
		textFieldHarga2.setBackground(new Color(240, 255, 240));
		textFieldHarga2.setColumns(10);
		textFieldHarga2.setBounds(97, 267, 148, 20);
		contentPane.add(textFieldHarga2);
		
		JLabel lblKode3 = new JLabel("KODE");
		lblKode3.setForeground(new Color(0, 153, 204));
		lblKode3.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblKode3.setBounds(21, 335, 61, 19);
		contentPane.add(lblKode3);
		
		textFieldKode3 = new JTextField();
		textFieldKode3.setBackground(new Color(240, 255, 240));
		textFieldKode3.setColumns(10);
		textFieldKode3.setBounds(97, 334, 148, 20);
		contentPane.add(textFieldKode3);
		
		refreshTable();
	}
}
