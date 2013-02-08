/**@author Hugo Valdovinos Hernández <hugo.emec@gmail.com>*/
package com.wissen.telemedia.gui.views;

import com.wissen.telemedia.gui.MainWindow;
import com.wissen.telemedia.gui.UIViewListener;
import com.wissen.telemedia.tsaak.SensorsReader;
/**
 * @brief valores para el paso temperatura*/
@SuppressWarnings("serial")
public class TemperatureRetrievalStepScreen extends RetrievalStepScreen {

	public TemperatureRetrievalStepScreen(UIViewListener listener) {
		super(listener);
	}

	@Override
	public String getTitle() {
		return "Temperatura";
	}

	@Override
	public String getInstructionText() {
		return "Pararse en forma recta mirando hacia el frente, en dirección hacia el termómetro";
	}

	@Override
	public String getCorrectInstructiveImage() {
		return "assets/temperature_correct.png";
	}

	@Override
	public String getIncorrectInstructiveImage() {
		return "assets/temperature_incorrect.png";
	}
	/**@brief obtiene la temperatura y la almacena*/
	synchronized protected void doRetrieval() {
		double temperature = SensorsReader.readTemperature();
		if (temperature == -1.0) {
			try {
				temperature = SensorsReader.readTemperature();
				if (temperature == -1.0) {
					((MainWindow) listener).changeViewTo(new ErrorSensorView(
							listener, msg));
					Thread.sleep(4000);
					((MainWindow) listener)
							.changeViewTo(((MainWindow) listener).currentScreen);
					listener.endSession();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*guarda el valor obtenido en el ArrayList de la clase Session*/ 
		listener.getSession().addMetric("temperature", temperature);
	}

}
