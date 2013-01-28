/**@author Hugo Valdovinos Hernández <hugo.emec@gmail.com>*/
package com.wissen.telemedia.gui.views;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import com.wissen.telemedia.gui.MainWindow;
import com.wissen.telemedia.gui.ParagraphLabel;
import com.wissen.telemedia.gui.UIViewListener;
/**@brief vista de alerta al usuario por inactividad
 * @param timer proceso para restar 1 a secs_remaining coda segundo
 * @param secs_remaining  tiempo en segundos para cerrar la sesión
 * @param text texto al usuario
 * @see MainWindow*/
@SuppressWarnings("serial")
public class IdleScreen extends UIView {
	public IdleScreen(UIViewListener listener) {
		super(listener);
	}

	protected Timer timer;
	
	private int secs_remaining;

	protected JLabel text;
	/**@brief inicia los objetos en la vista y los timer
	 * @see getText()*/
	protected void init() {
		secs_remaining = MainWindow.IDLE_WAIT / 1000;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.CENTER;
		
		addHeading("¿Desea continuar?");

		text = new ParagraphLabel(getText(), SwingConstants.CENTER);

		JButton continueB = new JButton("Continuar");
		JButton cancelB = new JButton("Cancelar");

		continueB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				timer.stop();
				((UIView) IdleScreen.this).listener.wakeup();
			}

		});

		cancelB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				timer.stop();
				((UIView) IdleScreen.this).listener.endSession();
			}

		});

		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				secs_remaining--;
				text.setText(getText());
			}
		});

		timer.start();

		cancelB.setPreferredSize(new Dimension(135, 40));
		
		//cancelB.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		
		continueB.setPreferredSize(new Dimension(155, 40));
		
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;

		add(text, c);

		listener.setStandby(false);
		
		c.gridy++;
		c.gridwidth = 1;
		c.gridheight = 1;

		Component glue = javax.swing.Box.createGlue();
		
		glue.setPreferredSize(new Dimension(100, 20));
		
		add(glue, c);
		
		c.gridx = 1;
		c.anchor = GridBagConstraints.CENTER;
		
		c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.CENTER;

		add(cancelB, c);

		c.gridx = 2;
		c.anchor = GridBagConstraints.CENTER;

		add(continueB, c);
	}
	/**@brief genera texto de advertencia al usuario
	 * @return cadena con texto al usuario */
	private String getText() {
		return "Si no selecciona \"Continuar\", la medición "
				+ "se cancelará en " + secs_remaining + " segundos.";
	}

}
