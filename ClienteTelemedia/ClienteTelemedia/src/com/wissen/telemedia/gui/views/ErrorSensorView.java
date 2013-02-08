/**
 * @author Hugo Valdovinos Hernández <hugo.emec@gmail.com>
 * 
 */
package com.wissen.telemedia.gui.views;

import imagene.panel.JPanelConFondo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import com.wissen.telemedia.gui.ParagraphLabel;
import com.wissen.telemedia.gui.UIViewListener;

/**
 * @brief vista de alerta al usuario, indicando un error en el sensor
 */
@SuppressWarnings("serial")
public class ErrorSensorView extends UIView {

	public ErrorSensorView(UIViewListener listener, String recivedMsg) {
		super(listener);
		// TODO Auto-generated method stub
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 3;
		c.anchor = GridBagConstraints.CENTER;		
		ParagraphLabel mgsAlert = new ParagraphLabel(recivedMsg);
		mgsAlert.setSize(300, 60);
		mgsAlert.setFont(new Font("newFon", Font.BOLD, (int) (18)));
		mgsAlert.setForeground( new Color(100, 37, 83));
		JPanelConFondo imageIndicator = new JPanelConFondo();
		imageIndicator.setPreferredSize(new Dimension(300, 300));
		imageIndicator.setImagen("assets/pagina-no-encontrada.jpg");		
		add(mgsAlert, c);
		c.gridx =3;
		c.gridy = 3;
		c.anchor= GridBagConstraints.FIRST_LINE_END;
		add (imageIndicator, c);
	}

	@Override
	protected void init() {

	}
}