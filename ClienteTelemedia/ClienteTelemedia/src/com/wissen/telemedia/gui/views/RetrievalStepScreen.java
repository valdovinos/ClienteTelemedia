/**@author Hugo Valdovinos Hernández <hugo.emec@gmail.com>*/
package com.wissen.telemedia.gui.views;

import imagene.panel.JPanelConFondo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
/**@brief gestiona la llamadas a los pasos de toma de mediciones
 * @param startButton
 * @param continueButton
 * @param centerContainer panel contenedor en el centro de la vista 
 * @param center panel con la opción de poner una imagen con fondo 
 * @param progressBar 
 * @param progressIndicator
 * @param nextStepTitle título en la pantalla
 * @param stepIndicator
 * @param instructions
 * @param next 
 * @param state control de estado de la medición 
 * @param color color blanco  
 * @param imageSet contenedor de las imagenes centrales
 * @param msg cadena con el mesaje a utilizar en la vista ErrorSensorView
 * @see ParagraphLabel*/
@SuppressWarnings("serial")
public abstract class RetrievalStepScreen extends UIView {
	protected JButton startButton, continueButton;
	protected JPanel centerContainer;
	protected JPanelConFondo center;
	protected JProgressBar progressBar;
	protected JLabel progressIndicator;

	protected String nextStepTitle;

	protected JLabel stepIndicator, instructions, next;
	
	protected int state = 0;
	protected String msg = "Por el momento no podemos atenderle, por favor intentelo mas tarde";
	Color color = new Color(255, 255, 255);
	protected JPanel imageSet;
	ImageIcon icon = new ImageIcon("assets/banner.png");	
	/*cantenedor de la imagenes centrales */
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
			///cambia el color del panel contenedor de las imagenes
			Color color = new Color(255, 255, 255);
			this.setBackground(color);
			
						
		}
		/**@brief label con la opción de colocar una imagen de fondo
		 * @param path ruta a la imagen
		 * @return JLabel con la imagen contenida en el */
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

	/**@brief intancia el objeto UIViewListener en la super clase*/
	public RetrievalStepScreen(UIViewListener listener) {
		super(listener);
	}
	/**@brief instancia los objetos a utiliazar y los agrega a la clase
	 * @param font objeto fuente BOLD para el texto
	 * @param progressIndicator indicador del progreso label con metodos sobrecargados  
	 * @param instructions instrucciones al usuario deacuerdo al paso donde se encuentra
	 * @param newLabelFont fuente personalizado en los textos*/
	protected void init() {
		Font font= new Font("newFon", Font.BOLD,
				(int) (12));
		progressIndicator = new ParagraphLabel();
		stepIndicator = new ParagraphLabel("Paso X de Y");
		/*agrega el titulo al panel principal*/
		addHeading(getTitle());

		Font newLabelFont = stepIndicator.getFont().deriveFont(
				heading.getFont().getSize() * 0.75f);
		stepIndicator.setFont(newLabelFont);
		/*agrega instruciones al usuario*/
		instructions = new ParagraphLabel(getInstructionText(),
				SwingConstants.CENTER);
		
		
		startButton = new JButton("Iniciar medición");
		/*panel con imagen de fondo */
		center = new JPanelConFondo();
		center.setPreferredSize(new Dimension(800, 400));
		center.setBackground(color);
		center.setImagen("assets/cuadro_blanco.png");
		centerContainer = new JPanel(new GridBagLayout());
		
		startButton.setPreferredSize(new Dimension(180, 40));
		//contenedor central de imagenes de medicion
		centerContainer.setPreferredSize(new Dimension(700, 360));		
		centerContainer.setBackground(color);
		/**agrega las imagenes centrales*/
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
		next.setFont(font);
		add(next, c);

		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				initRetrieval();

			}

		});
	}

	/**@brief cambia las instruciones en el JLabel stepIndicator*/
	public void setStepIndex(int index, int total) {
		/*agrega la imagen del banner y cambia las fuentes*/
		stepIndicator.setIcon(icon);
		stepIndicator.setVerticalTextPosition(JLabel.CENTER);
		stepIndicator.setHorizontalTextPosition(JLabel.CENTER);
		/*color del label instructions
		 *fondo blanco 
		 */		
		stepIndicator.setForeground(color);
		Font newLabelFont = new Font(heading.getFont().getName(), Font.BOLD,
				(int) (heading.getFont().getSize() * 1.20));
		stepIndicator.setFont(newLabelFont);		
		stepIndicator.setText(String.format("Paso %d de %d", index, total));
	}
	/**@brief agrega la barra de progreso al contenedor centerContainer */
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
		
	}
	/**@brief remueve los elementos startButton, imageSet, limpia los JLabels instructions, agrega la barra de progreso, repinta la interfaz y detiene el timer
	 * @see startRetrieval()*/
	private void initRetrieval() {
		if(imageSet != null)
			centerContainer.remove(imageSet);
		instructions.setText("");
		state = 1;
		remove(startButton);
		startButton.setEnabled(false);
		addProgressBar();
		repaint();
		/**detiene el idleTimer*/
		listener.setStandby(true);
		startRetrieval();
		
	}
	/**@brief asigna el siguiente titulo a mostrar*/
	public void setNextStepTitle(String title) {
		nextStepTitle = title;
		
	}
	/**@brief actualiza la interfaz indicando al usuario que la medición esta terminada*/
	protected final void finishedRetrieval() {
		/**reinicia el timer*/
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
	/**@brief inico del thread encargado de gestionar la vista de medición*/
	public void startRetrieval() {
		new Thread() {
			public void run() {
				doRetrieval();
				finishedRetrieval();
			}
		}.start();
	}
	/**@brief lectura de los dispositivos*/
	synchronized protected void doRetrieval() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**@brief actualiza la vista*/
	public void nextState() {
		if(state == 0) {
			repaint();
		}
		if(state == 2) {
			listener.nextScreen();
		}
	}

}
