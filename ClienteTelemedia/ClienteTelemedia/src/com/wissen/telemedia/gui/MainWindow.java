package com.wissen.telemedia.gui;
//
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
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

public class MainWindow extends JFrame implements UIViewListener {
	private static final int W = 800;
	private static final int H = 520;

	public static final int IDLE_WAIT = 20000;
	public static final int REPORT_WAIT = 30000;

	private int currentScreenIndex = -1;
	private UIView currentScreen, idleScreen;

	private UIView currentView;

	private boolean standby;

	private RetrievalStepScreen[] stepScreens;

	private int idleState = 0;
	private Timer idleTimer, reportTimer;

	private Session session;

	/**
	 * Main Window Constructor
	 * 
	 * @todo Fullscreen
	 */
	public MainWindow() {
		super();
		
		
		
		Toolkit tk = Toolkit.getDefaultToolkit();  
        setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		setUndecorated(true);
		setSize(800, 600);
		
		Font oldLabelFont = UIManager.getFont("Label.font");
		UIManager.put("Label.font", oldLabelFont.deriveFont(16f));

		Font oldButtonFont = UIManager.getFont("Button.font");
		UIManager.put("Button.font",    oldButtonFont.deriveFont(16f));
		UIManager.put("TextField.font", oldButtonFont.deriveFont(16f));
		UIManager.put("RadioButton.font", oldButtonFont.deriveFont(16f));
		UIManager.put("PasswordField.font", oldButtonFont.deriveFont(16f));

		setDefaultCloseOperation(EXIT_ON_CLOSE);

	    setBounds(0,0,tk.getScreenSize().width, tk.getScreenSize().height);

		setSize(tk.getScreenSize());
		setResizable(false);

		configureTimer();

		reset();
		
	}

	public void reset() {
		session = new Session();
		setStandby(true);
		stepScreens = new RetrievalStepScreen[] {
				new HeightWeightRetrievalStepScreen((UIViewListener) this),
				new TemperatureRetrievalStepScreen((UIViewListener) this),
				new BloodOxygenRetrievalStepScreen((UIViewListener) this),
				new BloodPressureRetrievalStepScreen((UIViewListener) this),
				};
		changeViewTo(new LoginView(this));
	}

	public void changeScreen(int index) {
		if (index >= stepScreens.length) {
			setStandby(true);
			reportTimer.restart();
			changeViewTo(new ReportView(this));
		} else {
			currentScreenIndex = index;
			currentScreen = stepScreens[index];
			if (currentScreen instanceof RetrievalStepScreen) {
				setStandby(false);

				if (index < stepScreens.length - 1) {
					((RetrievalStepScreen) currentScreen)
							.setNextStepTitle(stepScreens[index + 1].getTitle());
				}
				((RetrievalStepScreen) currentScreen).setStepIndex(
						index + 1, stepScreens.length);
			}
			changeViewTo(currentScreen);
		}
	}

	public void wakeup() {
		idleState = 0;
		idleTimer.restart();

		if(currentScreen == null && getSession().id != -1) {
			nextScreen();
		} else {
			changeViewTo(currentScreen);
			
			currentScreen.nextState();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String OS = System.getProperty("os.name").toLowerCase();
			if(OS.indexOf("win") >= 0)
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			else if((OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0))
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
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
		
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice screen = graphicsEnvironment.getDefaultScreenDevice();
		      screen.setFullScreenWindow(frame);
		   

	}

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

		reportTimer.setRepeats(false);

		reportTimer.stop();
	}

	@Override
	public void setStandby(boolean standbyState) {
		standby = standbyState;
		if (standbyState) {
			idleState = 0;
			idleTimer.stop();
		} else
			idleTimer.restart();
	}
	
	public void notifyActivity() {
		idleTimer.restart();
	}

	@Override
	public void nextScreen() {
		idleState = 0;
		changeScreen(currentScreenIndex + 1);
	}

	public void changeViewTo(UIView newView) {
		if (currentView != null)
			getContentPane().remove(currentView);

		getContentPane().add(newView);

		currentView = newView;
		setVisible(true);
	}

	@Override
	public void endSession() {
		reportTimer.stop();
		
		setStandby(true);

		idleState = 1;
		idleTimer.restart();

		changeViewTo(new SessionEndedView(this));

		currentScreenIndex = -1;
	}

	@Override
	public Session getSession() {
		return session;
		
	}

}
