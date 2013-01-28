/**@author Hugo Valdovinos Hernández <hugo.emec@gmail.com>*/
package com.wissen.telemedia.gui.views;

import imagene.panel.JPanelConFondo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.wissen.telemedia.gui.MainWindow;
import com.wissen.telemedia.gui.ParagraphLabel;
import com.wissen.telemedia.gui.UIViewListener;

/**@brief bienvenida al usuario
 *se despliega un menu preguntando la siguiente actividad a realizar 
 *@param icon imagen del banner superior en la vista
 *@param panel contenedor de los objetos*/
@SuppressWarnings("serial")
public class WelcomeScreen extends UIView {
	JPanel panel = new JPanel();
	
	ImageIcon icon = new ImageIcon("assets/banner.png");	
	public WelcomeScreen(UIViewListener listener) {
		super(listener);
		
	}
	/**@brief inicia los objetos en la vista y asigna los listener a los botones 
	 *@param heading contedor con imagen de fondo*/
	@Override
	protected void init() {
	
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 4;
		c.gridheight = 1;
		
		//addHeading("Bienvenido, " + listener.getSession().firstname);
		JPanelConFondo heading = new JPanelConFondo();			
		JLabel title = new JLabel();		
		Font font= new Font("newFon", Font.BOLD,
				(int) (15));
		Color color = new Color(255, 255, 255);
		title.setForeground(color);
		title.setFont(font);
		title.setPreferredSize(new Dimension(500, 54));
		title.setText("Bienvenido, " + listener.getSession().firstname);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setHorizontalAlignment(JLabel.CENTER);
		heading.setImagen("assets/banner.png");
		heading.setPreferredSize(new Dimension(500, 54));
		heading.add(title);
		c.gridy++;
		add(heading, c);
		JPanelConFondo content = new JPanelConFondo();	
		content.setImagen("assets/cuadro_blanco.png");
		content.setPreferredSize(new Dimension(500, 200));
		JLabel text = new ParagraphLabel("¿Está listo para iniciar el proceso de medición de signos vitales?");
		
		//c.gridy++;
		c.gridy=0;
		content.add(text,c);
		//add(text, c);
		//add(content,c);
		JButton continueB = new JButton("Continuar");
		JButton cancelB = new JButton("Cancelar");
		
		/*codigo agregado para ir a la vista de video conferencia*/		
		JButton conferencia = new JButton("Videoconferencia");
		conferencia.setPreferredSize(new Dimension(200, 40));
		conferencia.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goConferencia();
				
			}
			
		});		
		continueB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//((UIView) WelcomeScreen.this).listener.nextScreen();
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

		c.gridy=2;
		content.add(cancelB, c);
		c.gridx=1;
		content.add(continueB, c);
		c.gridy++;
		content.add(conferencia);
		c.gridx=0;
		c.gridy=2;
		add(content,c);
		/*JPanelConFondo imageMono = new JPanelConFondo();	
		imageMono.setPreferredSize(new Dimension(100,250));
		imageMono.setImagen("assets/mono.png");
		c.gridx=4;
		c.gridy=0;
		add(imageMono, c);
		//add(cancelB, c);
*/
		c.gridx = 2;
		c.anchor = GridBagConstraints.CENTER;
		//c.anchor = GridBagConstraints.NORTH;

		//add(continueB, c);
	}
/**@brief thread para cambiar la vista a la video conferencia*/
	protected synchronized void goConferencia() {
		
		new Thread() {
			public void run() {				
					((MainWindow) listener).changeViewTo(new videoconferencingView(listener));				
			}
		}.start();
	}
}
