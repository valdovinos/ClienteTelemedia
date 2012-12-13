package com.wissen.telemedia.gui.views;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;

import com.wissen.telemedia.gui.ParagraphLabel;
import com.wissen.telemedia.gui.UIViewListener;

public class SessionEndedView extends UIView {

	public SessionEndedView(UIViewListener listener) {
		super(listener);
	}

	@Override
	protected void init() {
		addHeading("Gracias por preferir nuestros servicios");
		
		JLabel text = new ParagraphLabel("Vuelva pronto");
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.CENTER;

		add(text, c);
	}

}
