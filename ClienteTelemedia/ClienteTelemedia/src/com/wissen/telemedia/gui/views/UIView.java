package com.wissen.telemedia.gui.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.wissen.telemedia.gui.UIViewListener;

abstract public class UIView extends JPanel {
	protected UIViewListener listener;

	protected GridBagConstraints c = new GridBagConstraints();
	
	protected JLabel heading;

	public UIView(UIViewListener listener) {
		super();
		
		this.listener = listener;
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		//c.insets = new Insets(20, 5, 0, 0);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 1;
		
		init();
	}
	
	protected void addHeading(String text) {
		heading = new JLabel(text);

		Font newLabelFont = new Font(heading.getFont().getName(), Font.BOLD,
				(int) (heading.getFont().getSize() * 1.25));

		//heading.setPreferredSize(new Dimension(500, 40));
		heading.setFont(newLabelFont);

		heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		//color rojo oscuro
		 Color color=new Color(111,1,0);  
		heading.setForeground(color);
		c.anchor = GridBagConstraints.CENTER;
		add(heading, c);
	}
	
	public void nextState() {
		
	}
	
	abstract protected void init();

}
