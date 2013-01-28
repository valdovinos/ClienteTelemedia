/**@author Hugo Valdovinos Hernández <hugo.emec@gmail.com>*/
package com.wissen.telemedia.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.wissen.telemedia.dao.DAOParameters;
import com.wissen.telemedia.dao.DAOUser;
import java.awt.SystemColor;
/**@brief */
@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	private JPasswordField txtPassword;
	private JButton btnLogin;
	private JTextField txtUsername;
	private JTextArea areaLog;
	private JTextField txtWeight;
	private JTextField txtDiapress;
	private JTextField txtSyspress;
	private JTextField txtHeight;
	private JTextField txtTemperature;
	private JTextField txtOxygen;
	private JTextField txtHeartbeat;
	private JLabel lblOpenmrsPersonId;
	private JTextField txtPersonId;
	private JLabel lblResponsesFromServlet;

	private int personId = 0;
	
	public MainWindow() {
		super("Test connection to OpenMRS and TSAAK Application");
		getContentPane().setForeground(new Color(0, 128, 0));
		getContentPane().setBackground(UIManager.getColor("Viewport.background"));
		setSize(652, 441);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(3);
		getContentPane().setLayout(null);
		
		JLabel lblA = new JLabel("User name:");
		lblA.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblA.setHorizontalAlignment(SwingConstants.RIGHT);
		lblA.setBounds(48, 22, 82, 24);
		getContentPane().add(lblA);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPassword.setBounds(48, 55, 82, 24);
		getContentPane().add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					txtPassword.requestFocus();
			}
		});
		txtUsername.setForeground(Color.BLUE);
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtUsername.setHorizontalAlignment(SwingConstants.CENTER);
		txtUsername.setBounds(148, 24, 150, 20);
		getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setForeground(Color.BLUE);
		txtPassword.setHorizontalAlignment(SwingConstants.CENTER);
		txtPassword.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					btnLogin.requestFocus();
			}
		});
		txtPassword.setBounds(148, 58, 150, 20);
		getContentPane().add(txtPassword);
		
		btnLogin = new JButton("Log in to OpenMRS");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				String result = DAOUser.login(txtUsername.getText(), new String(txtPassword.getPassword()));
				if(result.startsWith("successful")) {
					String[] resultArray = result.split(";");
					personId = Integer.parseInt(resultArray[1]);
					txtPersonId.setText("" + personId);
				}
				areaLog.append(result + "\n");
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnLogin.setBounds(148, 89, 150, 23);
		getContentPane().add(btnLogin);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(348, 110, 262, 226);
		getContentPane().add(scrollPane);
		
		areaLog = new JTextArea();
		areaLog.setFont(new Font("Consolas", Font.PLAIN, 13));
		scrollPane.setViewportView(areaLog);
		
		JLabel lblWeight = new JLabel("Weight:");
		lblWeight.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWeight.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblWeight.setBounds(48, 122, 82, 24);
		getContentPane().add(lblWeight);
		
		txtWeight = new JTextField();
		txtWeight.setHorizontalAlignment(SwingConstants.CENTER);
		txtWeight.setForeground(new Color(0, 128, 0));
		txtWeight.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtWeight.setColumns(10);
		txtWeight.setBounds(148, 124, 150, 20);
		getContentPane().add(txtWeight);
		
		JLabel lblDiastolicPressure = new JLabel("Diastolic pressure:");
		lblDiastolicPressure.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDiastolicPressure.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDiastolicPressure.setBounds(21, 157, 109, 24);
		getContentPane().add(lblDiastolicPressure);
		
		txtDiapress = new JTextField();
		txtDiapress.setHorizontalAlignment(SwingConstants.CENTER);
		txtDiapress.setForeground(new Color(0, 128, 0));
		txtDiapress.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtDiapress.setColumns(10);
		txtDiapress.setBounds(148, 159, 150, 20);
		getContentPane().add(txtDiapress);
		
		JLabel label = new JLabel("Systolic pressure:");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label.setBounds(21, 192, 109, 24);
		getContentPane().add(label);
		
		txtSyspress = new JTextField();
		txtSyspress.setHorizontalAlignment(SwingConstants.CENTER);
		txtSyspress.setForeground(new Color(0, 128, 0));
		txtSyspress.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtSyspress.setColumns(10);
		txtSyspress.setBounds(148, 194, 150, 20);
		getContentPane().add(txtSyspress);
		
		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHeight.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblHeight.setBounds(21, 227, 109, 24);
		getContentPane().add(lblHeight);
		
		txtHeight = new JTextField();
		txtHeight.setHorizontalAlignment(SwingConstants.CENTER);
		txtHeight.setForeground(new Color(0, 128, 0));
		txtHeight.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtHeight.setColumns(10);
		txtHeight.setBounds(148, 229, 150, 20);
		getContentPane().add(txtHeight);
		
		JLabel lblTemperature = new JLabel("Temperature:");
		lblTemperature.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTemperature.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTemperature.setBounds(21, 262, 109, 24);
		getContentPane().add(lblTemperature);
		
		txtTemperature = new JTextField();
		txtTemperature.setHorizontalAlignment(SwingConstants.CENTER);
		txtTemperature.setForeground(new Color(0, 128, 0));
		txtTemperature.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtTemperature.setColumns(10);
		txtTemperature.setBounds(148, 264, 150, 20);
		getContentPane().add(txtTemperature);
		
		JLabel lblOxygen = new JLabel("Oxygen:");
		lblOxygen.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOxygen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblOxygen.setBounds(21, 297, 109, 24);
		getContentPane().add(lblOxygen);
		
		txtOxygen = new JTextField();
		txtOxygen.setHorizontalAlignment(SwingConstants.CENTER);
		txtOxygen.setForeground(new Color(0, 128, 0));
		txtOxygen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtOxygen.setColumns(10);
		txtOxygen.setBounds(148, 299, 150, 20);
		getContentPane().add(txtOxygen);
		
		JLabel lblHeartBeatsPer = new JLabel("Beats per minute:");
		lblHeartBeatsPer.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHeartBeatsPer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblHeartBeatsPer.setBounds(21, 332, 109, 24);
		getContentPane().add(lblHeartBeatsPer);
		
		txtHeartbeat = new JTextField();
		txtHeartbeat.setHorizontalAlignment(SwingConstants.CENTER);
		txtHeartbeat.setForeground(new Color(0, 128, 0));
		txtHeartbeat.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtHeartbeat.setColumns(10);
		txtHeartbeat.setBounds(148, 334, 150, 20);
		getContentPane().add(txtHeartbeat);
		
		JButton btnSaveParameters = new JButton("Save Parameters");
		btnSaveParameters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(personId <= 0) {
					JOptionPane.showMessageDialog(null, "Error: no se ha especificado ningningún usuario");
					return;
				}
				try {
					saveParameters();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error: Algún parámetro no está bien escrito");
					e.printStackTrace();
				}
			}
		});
		btnSaveParameters.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSaveParameters.setBounds(148, 368, 150, 23);
		getContentPane().add(btnSaveParameters);
		
		lblOpenmrsPersonId = new JLabel("OpenMRS Person Id:");
		lblOpenmrsPersonId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOpenmrsPersonId.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblOpenmrsPersonId.setBounds(340, 22, 132, 24);
		getContentPane().add(lblOpenmrsPersonId);
		
		txtPersonId = new JTextField();
		txtPersonId.setText("0");
		txtPersonId.setBackground(SystemColor.inactiveCaptionBorder);
		txtPersonId.setEditable(false);
		txtPersonId.setHorizontalAlignment(SwingConstants.CENTER);
		txtPersonId.setForeground(UIManager.getColor("ToolBar.dockingForeground"));
		txtPersonId.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtPersonId.setColumns(10);
		txtPersonId.setBounds(490, 24, 118, 20);
		getContentPane().add(txtPersonId);
		
		lblResponsesFromServlet = new JLabel("Responses from servlet:");
		lblResponsesFromServlet.setHorizontalAlignment(SwingConstants.CENTER);
		lblResponsesFromServlet.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblResponsesFromServlet.setBounds(348, 75, 262, 24);
		getContentPane().add(lblResponsesFromServlet);
		setVisible(true);
	}
	
	void saveParameters() throws Exception {
		double[] parameters = new double[7];
		parameters[0] = Double.parseDouble(txtWeight.getText());
		parameters[1] = Double.parseDouble(txtDiapress.getText());
		parameters[2] = Double.parseDouble(txtSyspress.getText());
		parameters[3] = Double.parseDouble(txtHeight.getText());
		parameters[4] = Double.parseDouble(txtTemperature.getText());
		parameters[5] = Double.parseDouble(txtOxygen.getText());
		parameters[6] = Double.parseDouble(txtHeartbeat.getText());
		String result = DAOParameters.saveParameters(personId, parameters);
		areaLog.append(result + "\n");
	}
	
	public static void main(String[] args) throws Exception {
		//new MainWindow();
	}
}
