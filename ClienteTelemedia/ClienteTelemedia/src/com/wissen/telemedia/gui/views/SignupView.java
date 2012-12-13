package com.wissen.telemedia.gui.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;

import com.wissen.telemedia.dao.DAOUser;
import com.wissen.telemedia.gui.MainWindow;
import com.wissen.telemedia.gui.UIViewListener;

public class SignupView extends UIView {
	
	public JLabel errors;
	public Form form;
	public JButton done;
	
	class Form extends JPanel {
		class JLabel extends javax.swing.JLabel {
			public JLabel(String text) {
				super(text);
				setPreferredSize(new Dimension(200, 28));
			}
		}
		
		class JTextField extends javax.swing.JTextField {
			public JTextField() {
				setPreferredSize(new Dimension(200, 28));
				
				addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent e) {
						listener.notifyActivity();
					}
				});
			}
		}
		
		public JLabel nameL, lastname1L, lastname2L, birthDateL, genderL, usernameL, passwordL;
		public javax.swing.JTextField name, lastname1, lastname2, username, password; 
		public JComboBox year, month, day, newDay;
		
		String[] years, months;
		
	
		ButtonGroup radioButtonGroup;
		
		JRadioButton genderM, genderF;
		
		public Form() {
			Calendar cal = Calendar.getInstance();
			
			setLayout(new FlowLayout(FlowLayout.LEFT));
			
			nameL = new JLabel("Nombre");
			lastname1L = new JLabel("Apellido paterno");
			lastname2L = new JLabel("Apellido materno");
			birthDateL = new JLabel("Fecha de nacimiento");
			genderL = new JLabel("Género");
			usernameL = new JLabel("Usuario");
			passwordL = new JLabel("Contraseña");
			
			name = new JTextField();
			lastname1 = new JTextField();
			lastname2 = new JTextField();
			username = new JTextField();
			password = new JPasswordField();
			
			
			
			password.setPreferredSize(new Dimension(200, 28));
			
			password.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					listener.notifyActivity();
				}
			});
			
			int curYear = cal.get(Calendar.YEAR);
			
			radioButtonGroup=new ButtonGroup();

	        genderM =new JRadioButton("Masculino",true);
	        genderF =new JRadioButton("Femenino",false);


	        radioButtonGroup.add(genderM);
	        radioButtonGroup.add(genderF);
			
			years = new String[80];
			months = new String[]{
				"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
				"Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre",
			};
			
			for(int i = 0, y = curYear - 10; i < 80; i++, y--) {
				years[i] = Integer.toString(y);
			}
				
			
			year = new JComboBox(years);
			month = new JComboBox(months);
			
			updateDays(31);
			
			add(nameL);
			add(name);
			add(lastname1L);
			add(lastname1);
			add(lastname2L);
			add(lastname2);
			add(genderL);
			add(genderM);
			add(genderF);
			add(birthDateL);
			add(year);
			add(month);
			add(day);
			add(usernameL);
			add(username);
			add(passwordL);
			add(password);
		
			
			year.addActionListener (new ActionListener () {
			    public void actionPerformed(ActionEvent e) {
			    	findDays();
			    }
			});
			
			month.addActionListener (new ActionListener () {
			    public void actionPerformed(ActionEvent e) {
			    	findDays();
			    }
			});
		}
		
		protected void findDays() {
			Calendar calendar = Calendar.getInstance();
			
			calendar.set(
					  Integer.parseInt(years[year.getSelectedIndex()]),
					  month.getSelectedIndex(), 1);
			updateDays(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			updateContent();
		}
		
		protected void updateDays(int n) {
			String[] days = new String[n];
			for(int i = 0; i < n; i++)
				days[i] = Integer.toString(i + 1);
		
			
			newDay = new JComboBox(days);
				
			if(day == null) {
				day = newDay; 
			}
		}
		
		public String getGender() {
			return genderM.isSelected() ? "M" : "F";
		}
		
		protected void updateContent() {
			remove(nameL);
			remove(name);
			remove(lastname1L);
			remove(lastname1);
			remove(lastname2L);
			remove(lastname2);
			remove(genderL);
			remove(genderM);
			remove(genderF);
			remove(birthDateL);
			remove(year);
			remove(month);
			remove(day);
			remove(usernameL);
			remove(username);
			remove(passwordL);
			remove(passwordL);
			
			add(nameL);
			add(name);
			add(lastname1L);
			add(lastname1);
			add(lastname2L);
			add(lastname2);
			add(genderL);
			add(genderM);
			add(genderF);
			add(birthDateL);
			add(year);
			add(month);
			add(newDay);
			add(usernameL);
			add(username);
			add(passwordL);
			add(password);
			
			day = newDay;
			newDay = null;
			
			validate();
		}
		
		public String getBirthDate() {
			return years[year.getSelectedIndex()] + "/" + (month.getSelectedIndex() + 1) + "/" + (day.getSelectedIndex() + 1);
		}
	}
	
	public SignupView(UIViewListener listener) {
		super(listener);
	}

	@Override
	protected void init() {
		errors = new JLabel("");
		errors.setPreferredSize(new Dimension(600, 32));
		errors.setForeground(new Color(200, 60, 60));
		
		form = new Form();
		form.setPreferredSize(new Dimension(600, 260));
		
		addHeading("Nuevo usuario");
		c.anchor = GridBagConstraints.CENTER;
		c.gridy++;
		add(errors, c);
		c.gridy++;
		add(form, c);
		done = new JButton("Registrarse");
		c.gridy++;
		add(done, c);
		
		done.setPreferredSize(new Dimension(200, 40));
		
		done.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(form.name.getText().equals("") || form.lastname1.getText().equals("") || form.username.getText().equals("") || form.password.getText().equals("")) {
					errors.setText("<html><body align='center'>Por favor llene todos los campos</body></html>");
				} else {
					done.setEnabled(false);
					new Thread() {
						public void run() {
							String result = DAOUser.add(
									form.name.getText(), 
									form.lastname1.getText(),
									form.lastname2.getText(),
									form.getBirthDate(),
									form.getGender(), form.username.getText(), form.password.getText());
							if(result.startsWith("successful")) {
								/*
								listener.getSession().id = 0;
								listener.getSession().firstname = form.name.getText();
								listener.getSession().lastname1 = form.lastname1.getText();
								listener.getSession().lastname1 = form.lastname2.getText();*/
								listener.getSession().attemptLogin(form.username.getText(), form.password.getText());
								((MainWindow) listener).changeViewTo(new WelcomeScreen(listener));
							} else {
								done.setEnabled(true);
							}
						}
					}.start();
				}
			}
			
		});
		listener.setStandby(false);
	}
}
