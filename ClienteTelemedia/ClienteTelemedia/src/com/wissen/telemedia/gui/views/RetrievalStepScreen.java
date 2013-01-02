package com.wissen.telemedia.gui.views;

import imagene.panel.JPanelConFondo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import com.wissen.telemedia.gui.ParagraphLabel;
import com.wissen.telemedia.gui.UIViewListener;

public abstract class RetrievalStepScreen extends UIView {
	protected JButton startButton, continueButton;
	protected JPanel centerContainer;
	protected JPanelConFondo center;
	protected JProgressBar progressBar;
	protected JLabel progressIndicator;

	protected String nextStepTitle;

	protected JLabel stepIndicator, instructions, next;
	
	protected int state = 0;
	
	Color color = new Color(255, 255, 255);
	protected JPanel imageSet;
	ImageIcon icon = new ImageIcon("assets/banner.png");	
	static class ImageSet extends JPanel {
		public ImageSet(String correct, String incorrect) {
			super(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			add(labelFromImage("assets/correct_sign.png"), c);
			c.gridx++;
			add(labelFromImage(correct), c);
			c.gridx++;
			add(labelFromImage("assets/incorrect_sign.png"), c);
			c.gridx++;
			add(labelFromImage(incorrect), c);
			//cambia el color del panel contenedor de las imagenes
			Color color = new Color(255, 255, 255);
			this.setBackground(color);
			
						
		}
		
		public JLabel labelFromImage(String path) {
			BufferedImage i = null;
			try {
				 i = ImageIO.read(new File(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new JLabel(new ImageIcon(i));
		}
	}


	public RetrievalStepScreen(UIViewListener listener) {
		super(listener);
	}

	protected void init() {
		Font font= new Font("newFon", Font.BOLD,
				(int) (12));
		progressIndicator = new ParagraphLabel();
		stepIndicator = new ParagraphLabel("Paso X de Y");
		
		addHeading(getTitle());

		Font newLabelFont = stepIndicator.getFont().deriveFont(
				heading.getFont().getSize() * 0.75f);
		stepIndicator.setFont(newLabelFont);
	
		instructions = new ParagraphLabel(getInstructionText(),
				SwingConstants.CENTER);
		
		
		startButton = new JButton("Iniciar medición");
		//panel con imagen de fondo para las mediciones
		center = new JPanelConFondo();
		center.setPreferredSize(new Dimension(800, 400));
		center.setBackground(color);
		center.setImagen("assets/cuadro_blanco.png");
		centerContainer = new JPanel(new GridBagLayout());
		
		startButton.setPreferredSize(new Dimension(180, 40));
		//contenedor central de imagenes de medicion
		centerContainer.setPreferredSize(new Dimension(700, 360));		
		centerContainer.setBackground(color);
	
		try {
			imageSet = new ImageSet(getCorrectInstructiveImage(), getIncorrectInstructiveImage());
			centerContainer.add(imageSet);
		} catch(NullPointerException e) {
			
		}

		instructions.setVerticalAlignment(SwingConstants.TOP);
		instructions.setPreferredSize(new Dimension(700, 40));		
		instructions.setFont(font);
		instructions.setVerticalTextPosition(JLabel.CENTER);
		instructions.setHorizontalTextPosition(JLabel.CENTER);
		c.anchor = GridBagConstraints.CENTER;

		c.gridy++;
		add(stepIndicator, c);

		c.gridx = 0;
		c.gridwidth = 3;
		c.gridy++;
		add(instructions, c);

		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 3;
		
		//add(centerContainer, c);
		center.add(centerContainer);
		add(center, c);
		c.gridy++;
		c.gridwidth = 1;
		c.gridheight = 1;

		Component glue = javax.swing.Box.createGlue();
		
		glue.setPreferredSize(new Dimension(165, 40));
		
		add(glue, c);
		
		c.gridx = 1;
		
		add(startButton, c);
		
		c.gridy++;
		next = new ParagraphLabel();
		next.setPreferredSize(new Dimension(380, 20));
		//Color color = new Color;
		next.setFont(font);
		add(next, c);

		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				initRetrieval();

			}

		});
	}

	public void setStepIndex(int index, int total) {
		//agrega la imagen del banner y cambia las fuentes
		stepIndicator.setIcon(icon);
		stepIndicator.setVerticalTextPosition(JLabel.CENTER);
		stepIndicator.setHorizontalTextPosition(JLabel.CENTER);
		/**color del label instructions
		 * cambiar paraponer un fonde blanco detras de las letras 
		 */
		 
		//instructions.setIcon(icon);
		stepIndicator.setForeground(color);
		Font newLabelFont = new Font(heading.getFont().getName(), Font.BOLD,
				(int) (heading.getFont().getSize() * 1.20));
		stepIndicator.setFont(newLabelFont);
		///////////////////////////////////
		stepIndicator.setText(String.format("Paso %d de %d", index, total));
	}

	private void addProgressBar() {
		GridBagConstraints c = new GridBagConstraints();

		progressBar = new JProgressBar(0, 100);
		progressBar.setIndeterminate(true);
		progressBar.setVisible(true);

		progressIndicator.setText("Tomando medición...");
		progressIndicator.setVerticalAlignment(SwingConstants.CENTER);
		//progressIndicator.setBorder(BorderFactory.createEmptyBorder(20, 0, 20,
		//				0));

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		progressBar.setPreferredSize(new Dimension(300, 20));
		centerContainer.add(progressBar, c);

		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy++;
		progressIndicator.setPreferredSize(new Dimension(300, 60));
		centerContainer.add(progressIndicator, c);
		
		
		//centerContainer.validate();
		
		
		
	}

	private void initRetrieval() {
		if(imageSet != null)
			centerContainer.remove(imageSet);
		instructions.setText("");
		state = 1;
		remove(startButton);
		startButton.setEnabled(false);
		addProgressBar();
		repaint();
		listener.setStandby(true);
		startRetrieval();
		
	}
	
	public void setNextStepTitle(String title) {
		nextStepTitle = title;
		
	}

	protected final void finishedRetrieval() {
		listener.setStandby(false);

		progressBar.setValue(progressBar.getMaximum());
		progressBar.setIndeterminate(false);
		
		continueButton = new JButton("Continuar");
		continueButton.setPreferredSize(new Dimension(140, 40));
		continueButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.nextScreen();

			}

		});
		
		c.gridy--;
		add(continueButton, c);

		remove(startButton);
		
		if (nextStepTitle != null) {
			next.setText("Siguiente medición: " + nextStepTitle);
		}
		progressIndicator.setText("¡Listo!");
		
		state = 2;
		
		repaint();
	}

	abstract public String getTitle();

	abstract public String getInstructionText();

	abstract public String getCorrectInstructiveImage();

	abstract public String getIncorrectInstructiveImage();

	public void startRetrieval() {
		new Thread() {
			public void run() {
				doRetrieval();
				finishedRetrieval();
			}
		}.start();
	}
	
	synchronized protected void doRetrieval() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void nextState() {
		if(state == 0) {
			repaint();
		}
		if(state == 2) {
			listener.nextScreen();
		}
	}

}
