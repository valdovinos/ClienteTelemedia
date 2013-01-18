package com.wissen.telemedia.gui.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import imagene.panel.JKeyboardPane;


import com.wissen.telemedia.gui.MainWindow;
import com.wissen.telemedia.gui.UIViewListener;

public class LoginView extends UIView {

	public LoginView(UIViewListener listener) {
		super(listener);
	}
	
	JTextField username, password;
	
	JButton loginButton;
	JLabel errors;
	JKeyboardPane teclado;
	JPopupMenu pop;
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
		
		/**@brief listener para el evento focus el cual lanza el teclado virtual asignadolo al JTextField
		 * @param pop panel flotante donde se alojara el teclado
		 * @param teclado objeto que regresa la clase JKeyboardPane al ser inicializado*/
		username.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				
				pop = new JPopupMenu();
				teclado=new JKeyboardPane(username);
				pop.add(teclado);
				pop.setVisible(true);
				pop.setLocation(username.getLocationOnScreen().x-190, username.getLocationOnScreen().y+200);
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				pop.setVisible(false);
			}
			
		});
		/**@brief listener para el evento focus el cual lanza el teclado virtual asignadolo al JPasswordField
		 * @param pop panel flotante donde se alojara el teclado
		 * @param teclado objeto que regresa la clase JKeyboardPane al ser inicializado*/
		password.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				
				pop = new JPopupMenu();
				teclado=new JKeyboardPane(password);
				pop.add(teclado);
				pop.setVisible(true);
				pop.setLocation(password.getLocationOnScreen().x-190, password.getLocationOnScreen().y+100);
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				pop.setVisible(false);
			}
			
		});
		c.gridy++;
	}
	
	protected synchronized void tryLogin() {
		loginButton.setEnabled(false);
		new Thread() {
			public void run() {
				if(listener.getSession().attemptLogin(username.getText(), password.getText())) {
					pop.removeAll();
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
