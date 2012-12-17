package com.wissen.telemedia.gui.views;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.wissen.telemedia.gui.ParagraphLabel;
import com.wissen.telemedia.gui.UIViewListener;

public class WelcomeScreen extends UIView {
	JPanel panel = new JPanel();
	public WelcomeScreen(UIViewListener listener) {
		super(listener);
		
	}

	@Override
	protected void init() {
	
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 4;
		c.gridheight = 1;
		
		addHeading("Bienvenido, " + listener.getSession().firstname);

		JLabel text = new ParagraphLabel("¿Está listo para iniciar el proceso de medición de signos vitales?");
		
		c.gridy++;
		add(text, c);
		
		JButton continueB = new JButton("Continuar");
		JButton cancelB = new JButton("Cancelar");

		continueB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((UIView) WelcomeScreen.this).listener.nextScreen();
			}

		});

		cancelB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((UIView) WelcomeScreen.this).listener.endSession();
			}

		});
		
		listener.setStandby(false);
		
		c.gridy++;
		c.gridwidth = 1;
		c.gridheight = 1;

		Component glue = javax.swing.Box.createGlue();
		
		glue.setPreferredSize(new Dimension(100, 20));
		
		add(glue, c);
		
		cancelB.setPreferredSize(new Dimension(135, 40));
		continueB.setPreferredSize(new Dimension(155, 40));
		
		c.gridx = 1;
		
		
		c.anchor = GridBagConstraints.CENTER;

		add(cancelB, c);

		c.gridx = 2;
		c.anchor = GridBagConstraints.CENTER;
		//c.anchor = GridBagConstraints.NORTH;

		add(continueB, c);
	}

	
}
