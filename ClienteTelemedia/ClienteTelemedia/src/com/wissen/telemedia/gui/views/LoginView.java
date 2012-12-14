package com.wissen.telemedia.gui.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.wissen.telemedia.gui.MainWindow;
import com.wissen.telemedia.gui.UIViewListener;

public class LoginView extends UIView {

	public LoginView(UIViewListener listener) {
		super(listener);
	}
	
	JTextField username, password;
	
	JButton loginButton;

	JLabel errors;
	
	@Override
	protected void init() {
		addHeading("Entrar");
		
		heading.setBorder(BorderFactory.createEmptyBorder());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		errors = new JLabel("");
		errors.setForeground(new Color(200, 60, 60));
		errors.setPreferredSize(new Dimension(400, 40));
		c.gridy++;
		
		add(errors, c);
		
		c.gridy++;
		
		JLabel usernameL =  new JLabel("Usuario");
		usernameL.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
		
		add(usernameL, c);
		
		c.gridy++;
		username = new JTextField();
		username.setPreferredSize(new Dimension(400, 40));
		
		
		add(username, c);
		
		c.gridy++;
		
		JLabel passwordL = new JLabel("Contraseña");
		passwordL.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
		
		add(passwordL, c);
		
		c.gridy++;
		password = new JPasswordField();
		
		password.setPreferredSize(new Dimension(400, 40));
		
		password.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(loginButton.isEnabled() && e.getKeyCode() == KeyEvent.VK_ENTER)
					tryLogin();
			}
		});
		username.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(loginButton.isEnabled() && e.getKeyCode() == KeyEvent.VK_ENTER)
					tryLogin();
			}
		});
		
		
		
		
		add(password, c);
		
		c.fill = GridBagConstraints.NONE;
		c.gridy++;

		loginButton = new JButton("Entrar");
		
		loginButton.setPreferredSize(new Dimension(200, 40));
		
		add(loginButton, c);
		
		JButton newUserButton = new JButton("Nuevo usuario");
		
		newUserButton.setPreferredSize(new Dimension(200, 40));
		
		c.gridy++;
		
		add(newUserButton, c);
		newUserButton.setVisible(false);
		newUserButton.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				((MainWindow) listener).changeViewTo(new SignupView(listener));
			}
			
		});
		
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tryLogin();
				
			}
			
		});
	}
	
	protected synchronized void tryLogin() {
		loginButton.setEnabled(false);
		new Thread() {
			public void run() {
				if(listener.getSession().attemptLogin(username.getText(), password.getText())) {
					((MainWindow) listener).changeViewTo(new WelcomeScreen(listener));
				} else {
					errors.setText("Contraseña o usuario incorrecto	");
					password.setText("");
					loginButton.setEnabled(true);
				}
			}
		}.start();
	}

}
