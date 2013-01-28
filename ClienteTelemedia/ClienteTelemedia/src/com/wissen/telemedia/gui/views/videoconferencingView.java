/**
 * @author Hugo Valdovinos Hernández <hugo.emec@gmail.com>
 * 
 */
package com.wissen.telemedia.gui.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import imagene.panel.JPanelConFondo;
import com.wissen.telemedia.gui.MainWindow;
import com.wissen.telemedia.gui.UIViewListener;
/**@author Hugo Valdovinos Hernández <hugo.emec@gmail.com>*/

/**@brief vista para la videoconferencia
 * @param container panel donde estara contenido los elementos de la vista
 * @param imageDoctor panel con la imagen de fondo del doctor
 * @param color objeto color rgb blanco*/
@SuppressWarnings("serial")
public class videoconferencingView extends UIView {
	protected JPanel container;
	protected JPanelConFondo imageDoctor;
	static Color blanco = new Color(255, 255, 255);

	public videoconferencingView(UIViewListener listener) {
		super(listener);
		// TODO Auto-generated constructor stub
	}

	/**@brief agrega los abjetos a la vista principal y detiene el timer general de inactividad
	 * @param done JButton para cerrar la vista
	 * @param heading JPanel contiene la imagen de fondo del titulo
	 * @param title JLabel texto en el titilo de la vista 	
	 * @see((MainWindow) listener).setStandby(); 
	 */
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		((MainWindow) listener).setStandby(true);//detiene el thread de inactividad en la sesion
		GridBagConstraints cAux = c;
		cAux.gridx = 0;
		cAux.gridy = 0;
		cAux.gridwidth = 4;
		cAux.gridheight = 3;
		cAux.gridx = 0;
		cAux.gridy = 0;
		cAux.gridwidth = 4;
		cAux.gridheight = 3;
		JPanelConFondo heading = new JPanelConFondo();			
		JLabel title = new JLabel();		
		Font font= new Font("newFon", Font.BOLD,
				(int) (15));
		Color color = new Color(255, 255, 255);
		title.setForeground(color);
		title.setFont(font);
		title.setPreferredSize(new Dimension(450, 54));
		title.setText("Videoconferencia");
		title.setVerticalAlignment(JLabel.CENTER);
		title.setHorizontalAlignment(JLabel.CENTER);
		heading.setImagen("assets/banner.png");
		heading.setPreferredSize(new Dimension(450, 54));
		heading.add(title);
		c.gridy++;
		add(heading, c);
		
		
		container = new JPanel();
		container.setPreferredSize(new Dimension(450, 400));
		container.setBackground(blanco);
		
		imageDoctor = new JPanelConFondo();
		imageDoctor.setPreferredSize(new Dimension(400, 350));
		imageDoctor.setImagen("assets/doctor.jpg");
		JButton done = new JButton("Terminar");
		done.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((MainWindow) listener).setStandby(false);//vuelve a iniciar el el conteo de inactividad en la sesion 
				((MainWindow) listener).changeViewTo(new WelcomeScreen(listener));								
			}
		});
		
		container.add(imageDoctor,cAux);
		cAux.gridy=2;
		container.add(done, cAux);
		c.gridy=2;
		add(container, c);

	}

}