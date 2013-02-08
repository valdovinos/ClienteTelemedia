/**@file clase main del programa 
*@author Hugo Valdovinos Hernández <hugo.emec@gmail.com>
 */
package com.wissen.telemedia.gui;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.wissen.telemedia.gui.views.BloodOxygenRetrievalStepScreen;
import com.wissen.telemedia.gui.views.BloodPressureRetrievalStepScreen;
import com.wissen.telemedia.gui.views.HeightWeightRetrievalStepScreen;
import com.wissen.telemedia.gui.views.IdleScreen;
import com.wissen.telemedia.gui.views.LoginView;
import com.wissen.telemedia.gui.views.ReportView;
import com.wissen.telemedia.gui.views.RetrievalStepScreen;
import com.wissen.telemedia.gui.views.SessionEndedView;
import com.wissen.telemedia.gui.views.TemperatureRetrievalStepScreen;
import com.wissen.telemedia.gui.views.UIView;

/**
 * @class MainWindow
 * @brief inicio del programa
 * @param W
 *            ancho predeterminado de la pantalla
 * @param H
 *            alto predeterminado de la pantalla
 * @param IDLE_WAIT
 *            20 segundos
 * @param REPORT_WAIT
 *            30 segundos
 * @param currentScreenIndex
 *            control para el cambio de pasos en la vista RetrievalStepScreen
 * @param currentScreen
 *            paso actual dentro de la vista RetrievalStepScreen
 * @param idleScreen
 *            vista definida como MainWindow en idleTimer
 * @param currentView
 *            vista actual en el ciclo del programa
 * 
 */
@SuppressWarnings("serial")
public class MainWindow extends JFrame implements UIViewListener {
//	private static final int W = 800;
//	private static final int H = 520;

	public static final int IDLE_WAIT = 20000;
	public static final int REPORT_WAIT = 30000;

	private int currentScreenIndex = -1;
	public UIView currentScreen, idleScreen;

	private UIView currentView;

	@SuppressWarnings("unused")
	private boolean standby;

	private RetrievalStepScreen[] stepScreens;

	private int idleState = 0;
	private Timer idleTimer, reportTimer;

	private Session session;

	/**
	 * @brief constructor de la clase instancia los objetos a usar en UIManager,
	 *        configura el timer del programa e inicia el objeto sesión
	 * @see configureTimer(), reset()
	 * @param tk
	 *            instancia el framework AWT (ontener la resolucion de pantalla)
	 * @param oldLabelFont
	 *            font para el aspecto de los objetos
	 * */
	public MainWindow() {
		super();
		Toolkit tk = Toolkit.getDefaultToolkit();
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		setUndecorated(true);
		/*asigna el tamaño inicial*/
		setSize(800, 600);

		Font oldLabelFont = UIManager.getFont("Label.font");
		UIManager.put("Label.font", oldLabelFont.deriveFont(16f));

		Font oldButtonFont = UIManager.getFont("Button.font");
		UIManager.put("Button.font", oldButtonFont.deriveFont(16f));
		UIManager.put("TextField.font", oldButtonFont.deriveFont(16f));
		UIManager.put("RadioButton.font", oldButtonFont.deriveFont(16f));
		UIManager.put("PasswordField.font", oldButtonFont.deriveFont(16f));

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		/*redimenciona la pantalla*/
		setBounds(0, 0, tk.getScreenSize().width, tk.getScreenSize().height);
		/*asigna el tamaño final*/
		setSize(tk.getScreenSize());
		setResizable(false);

		configureTimer();
		reset();

	}

	/**
	 * @brief crea el objeto session asigna la vistas en stepScreens y coloca la
	 *        primera vista en pantalla
	 * @see changeViewTo()
	 * */
	public void reset() {
		session = new Session();
		/**detiene el timer*/
		setStandby(true);
		stepScreens = new RetrievalStepScreen[] {
				new HeightWeightRetrievalStepScreen((UIViewListener) this),
				new TemperatureRetrievalStepScreen((UIViewListener) this),
				new BloodOxygenRetrievalStepScreen((UIViewListener) this),
				new BloodPressureRetrievalStepScreen((UIViewListener) this), };
		/**cambia la vista actual*/
		changeViewTo(new LoginView(this));
	}

	/**
	 * @brief gestión de pantalla en la vista RetrievalStepScreen asigna la
	 *        pantalla con respecto al index recibido, finalizado asigna la siguiente vista
	 * @code changeViewTo(new ReportView(this));
	 * @param index
	 *            del paso dentro de stepScreen
	 * @see changeViewTo(), setStandby()
	 * */
	public void changeScreen(int index) {
		if (index >= stepScreens.length) {
			setStandby(true);
			/*reinicia el timer */
			reportTimer.restart();
			changeViewTo(new ReportView(this));
		} else {
			currentScreenIndex = index;
			currentScreen = stepScreens[index];
			if (currentScreen instanceof RetrievalStepScreen) {
				/*reinicia el timer*/
				setStandby(false);

				if (index < stepScreens.length - 1) {
					((RetrievalStepScreen) currentScreen)
							.setNextStepTitle(stepScreens[index + 1].getTitle());
				}
				((RetrievalStepScreen) currentScreen).setStepIndex(index + 1,
						stepScreens.length);
			}
			changeViewTo(currentScreen);
		}
	}

	/**
	 * @brief remuev la pantalla idle asignado la vista actual reinicia el timer
	 *        idleTimer
	 * @see nextScreen(), changeViewTo()
	 */
	public void wakeup() {
		idleState = 0;
		/** reinicia el timer */
		idleTimer.restart();
		/*existe currentScreen y session */
		if (currentScreen == null && getSession().id != -1) {
			nextScreen();
			/** siguiente pantalla */
		} else {
			/*si no existe asigna currentScreen */
			changeViewTo(currentScreen);
			/** avanza currentScreen al siguiente estado */
			currentScreen.nextState();
		}
	}

	/**
	 * @brief main de la clase asigna LookAndFeel a la interfaz con respecto al
	 *        sistema operativo en el que se encuentre ejecutando la aplicación
	 */
	public static void main(String[] args) {
		try {
			String OS = System.getProperty("os.name").toLowerCase();
			if (OS.indexOf("win") >= 0)
				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			else if ((OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0))
				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Frame frame = new MainWindow();

		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice screen = graphicsEnvironment.getDefaultScreenDevice();
		screen.setFullScreenWindow(frame);

	}

	/**
	 * @brief instancia  idleTimer y reportTimer
	 * crea un ActionListener para el reportTimer y idleTimer
	 * crea una vista para presentarla como idleState en la condicion del listener en idleTimer
	 * termina la sesion en el listener del timer reportTimer
	 * @see enSession(), reset(), changeView()
	 * */
	private void configureTimer() {
		idleTimer = new Timer(IDLE_WAIT, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				switch (idleState) {
				case 0:
					idleScreen = new IdleScreen(MainWindow.this);
					changeViewTo(idleScreen);
					idleState++;
					break;
				case 1:
					reset();
					break;
				}
			}

		});

		reportTimer = new Timer(REPORT_WAIT, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				endSession();
			}

		});
		/*solo permite al timer enviar un evento en su listener*/
		reportTimer.setRepeats(false);
		/*detiene el timer*/
		reportTimer.stop();
	}

	/**
	 * @brief detiene o inicia idleTimer dependiendo de standbyState
	 * @param standbyState parametro boleano recibido
	 * */
	@Override
	public void setStandby(boolean standbyState) {
		standby = standbyState;		
		if (standbyState) {
			/*detiene el timer*/
			idleState = 0;
			idleTimer.stop();
		} else/*reinicia el timer*/
			idleTimer.restart();
	}
	/**@brief reinicia el timer*/
	public void notifyActivity() {
		idleTimer.restart();
	}
	/**@brief cambia la vista actual a la siguente
	 * @see changeScreen()*/
	@Override
	public void nextScreen() {
		idleState = 0;
		changeScreen(currentScreenIndex + 1);
	}
	/**@brief cambia la vista actual 
	 * @param newView parametro recibida*/
	public void changeViewTo(UIView newView) {
		if (currentView != null)
			/*remueve la vista actual*/
			getContentPane().remove(currentView);
		/*agrega la nueva vista al panel*/
		getContentPane().add(newView);
		/*actualiza la variable indicadora de la vista actual*/
		currentView = newView;
		setVisible(true);
	}

	/**@brief termina la sesión del usuario*/
	@Override
	public void endSession() {
		reportTimer.stop();

		setStandby(true);

		idleState = 1;
		idleTimer.restart();
		
		changeViewTo(new SessionEndedView(this));

		currentScreenIndex = -1;
	}
	/**@brief obtiene la variable session
	 * @return regresa el objeto session*/
	@Override
	public Session getSession() {
		return session;

	}

}
