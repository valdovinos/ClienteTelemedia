package com.wissen.telemedia.gui.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import com.wissen.telemedia.gui.UIViewListener;

public class ReportView extends UIView {
	static Color blanco = new Color(255, 255, 255);
	static class Row extends JPanel {
		public Row(String key, Double val) {
			this(key, Double.toString(val));
			
		}

		public Row(String key, String val) {
			
			setBorder(new MatteBorder(0, 1, 1, 1, new Color(185, 190, 16)));

			((FlowLayout) getLayout()).setHgap(0);

			JLabel keyL = new JLabel(key);
			JLabel valL = new JLabel(val);

			keyL.setPreferredSize(new Dimension(240, 32));
			valL.setPreferredSize(new Dimension(320, 32));
			
			setBackground(blanco);
			keyL.setFont(keyL.getFont().deriveFont(Font.BOLD));
			
			keyL.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0)); 

			add(keyL);
			add(valL);
			
		}
	}

	public ReportView(UIViewListener listener) {
		super(listener);		
	}

	@Override
	protected void init() {
		
		addHeading("Reporte");
		JButton done = new JButton("Terminado");

		done.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((UIView) ReportView.this).listener.endSession();
			}

		});

		c.insets = new Insets(0, 0, 0, 0);

		c.gridy++;
		
		JPanel row = new Row("Nombre", listener.getSession().firstname);
		
		row.setBorder(new MatteBorder(1, 1, 1, 1, new Color(185, 190, 16)));

		add(row, c);

		c.gridy++;		
	//cambiar el fondo 
		
		//row.setFont(font);
	//
		add(new Row("Apellido Paterno", listener.getSession().lastname1), c);
		
		c.gridy++;
		
		add(new Row("Apellido Materno", listener.getSession().lastname2), c);

		c.gridy++;

		for (int i = 0; i < listener.getSession().metricTotal(); i++) {
			String key = listener.getSession().getKey(i);
			String unit = "";

			Double value = listener.getSession().getMetric(key);

			if (key.equals("weight")) {
				key = "Peso";
				unit = "kg";
			} else if (key.equals("height")) {
				key = "Altura";
				unit = "m";
			} else if (key.equals("temperature")) {
				key = "Temperatura";
				unit = "ºC";
			} else if (key.equals("systolic")) {
				key = "Presión sistólica";
				unit = "ºC";
			} else if (key.equals("diastolic")) {
				key = "Presión diastólica";
				unit = "";
			} else if (key.equals("bloodoxygen")) {
				key = "Oxigenación en la sangre";
				unit = "";
			} else if (key.equals("heartrate")) {
				key = "Frecuencia cardiaca";
				unit = "";
			}

			c.gridy++;

			row = new Row(key, value + " " + unit);

			add(row, c);
		}

		c.gridy++;

		add(done, c);
		
		
		new Thread() {
			public void run() {
				listener.getSession().commit();
			}
		}.start();

	}

}
