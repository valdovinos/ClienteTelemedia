package com.wissen.telemedia.gui;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ParagraphLabel extends JLabel {

	public ParagraphLabel() {
		super("", SwingConstants.CENTER);
	}

	public ParagraphLabel(String text) {
		super(text, SwingConstants.CENTER);
		setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		// TODO Auto-generated constructor stub
	}

	public ParagraphLabel(Icon image) {
		super(image);
		setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		// TODO Auto-generated constructor stub
	}

	public ParagraphLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		// TODO Auto-generated constructor stub
	}

	public ParagraphLabel(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
		setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		// TODO Auto-generated constructor stub
	}

	public ParagraphLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
		setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		// TODO Auto-generated constructor stub
	}
	
	public void setText(String text) {
		
		super.setText("<html><body align='center'>"
				+ text + "</body></html>");
	}

}
