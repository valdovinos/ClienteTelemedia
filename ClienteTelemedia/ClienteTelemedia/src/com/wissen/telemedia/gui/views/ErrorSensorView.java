/**
 * @author Hugo Valdovinos Hernández <hugo.emec@gmail.com>
 * 
 */
package com.wissen.telemedia.gui.views;

import java.awt.Dimension;
import java.awt.Font;
import imagene.panel.JPanelConFondo;
import com.wissen.telemedia.gui.ParagraphLabel;
import com.wissen.telemedia.gui.UIViewListener;
/**
 * @brief vista de alerta al usuario, indicando un error en el sensor*/
@SuppressWarnings("serial")
public class ErrorSensorView extends UIView {
	
	public ErrorSensorView(UIViewListener listener, String recivedMsg) {
		super(listener);
		// TODO Auto-generated method stub
				JPanelConFondo center;
				center = new JPanelConFondo();
				center.setPreferredSize(new Dimension(800, 400));
				center.setImagen("assets/cuadro_blanco.png");				
				ParagraphLabel mgsAlert = new ParagraphLabel(recivedMsg);
				mgsAlert.setFont(new Font("newFon", Font.BOLD,
				(int) (18)));
				center.add(mgsAlert);
				add(center);
	}
	
	
	@Override
	protected void init() {
				
	}
}