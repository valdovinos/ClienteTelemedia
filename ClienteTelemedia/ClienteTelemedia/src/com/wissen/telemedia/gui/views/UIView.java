package com.wissen.telemedia.gui.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.wissen.telemedia.gui.UIViewListener;

abstract public class UIView extends JPanel {
	protected UIViewListener listener;

	protected GridBagConstraints c = new GridBagConstraints();
	private Image imagen;
	protected JLabel heading;

	// // imagen en el jlabel Heading

	public UIView(UIViewListener listener) {
		super();

		this.listener = listener;
		this.setBackground(new java.awt.Color(255, 255, 255));
		this.setImagen("assets/background.png");
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);

		// c.insets = new Insets(20, 5, 0, 0);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 1;

		init();
	}

	/**
	 * funcion de prueba para imagen de fondo
	 * */
	public void setImagen(String nombreImagen) {
		if (nombreImagen != null) {
			imagen = new ImageIcon(nombreImagen).getImage();
		} else {
			imagen = null;
		}

		repaint();
	}

	public void setImagen(Image nuevaImagen) {
		imagen = nuevaImagen;

		repaint();
	}

	@Override
	public void paint(Graphics g) {
		if (imagen != null) {
			g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);

			setOpaque(false);
		} else {
			setOpaque(true);
		}

		super.paint(g);
	}

	// //////////////////////////////////////////////////////////
	protected void addHeading(String text) {
		
		if (text == "Reporte") {
			Color blanco = new Color(255, 255, 255);
			ImageIcon icon = new ImageIcon("assets/bannerReport.png");
			JLabel title = new JLabel();
			title.setIcon(icon);
			title.setText("reporte");
			Font font= new Font("newFon", Font.BOLD,
					(int) (20));
			title.setForeground(blanco);
			title.setFont(font);
			title.setVerticalTextPosition(JLabel.CENTER);
			title.setHorizontalTextPosition(JLabel.CENTER);
			c.anchor = GridBagConstraints.CENTER;
			add(title, c);
		} else {
			heading = new JLabel();
			Font newLabelFont = new Font(heading.getFont().getName(),
					Font.BOLD, (int) (heading.getFont().getSize() * 1.50));

			// heading.setPreferredSize(new Dimension(400, 40));
			heading.setFont(newLabelFont);

			heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
			// color morado oscuro
			Color color = new Color(100, 37, 83);
			// se agrega la imagen al label
			heading.setText(text);
			heading.setForeground(color);
			c.anchor = GridBagConstraints.CENTER;
			// forza al texto en el centro
			heading.setVerticalTextPosition(JLabel.CENTER);
			heading.setHorizontalTextPosition(JLabel.CENTER);
			add(heading, c);
		}
	}

	public void nextState() {

	}

	abstract protected void init();

}
