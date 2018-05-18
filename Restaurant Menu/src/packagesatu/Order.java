package packagesatu;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.border.EmptyBorder;
import java.sql.*;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.awt.Color;

import java.awt.Font;

public class Order extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Order frame = new Order();
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
	private JTable table;
	
	public void refreshTable() {
		try {
			String query="select m.kode, m.nama, m.harga, n.qty from menu m inner join nota n on m.kode=n.kode";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Order() {
		connection = Database.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 153, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 102, 153));
		panel.setBounds(0, 0, 203, 411);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(51, 102, 153));
		panel_1.setBounds(202, 0, 482, 95);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblOrder = new JLabel("MY ORDER");
		lblOrder.setForeground(new Color(240, 255, 240));
		lblOrder.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblOrder.setBounds(66, 26, 164, 39);
		panel_1.add(lblOrder);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(213, 106, 234, 294);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		refreshTable();
	}
}
