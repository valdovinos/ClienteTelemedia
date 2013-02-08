/**@author Hugo Valdovinos Hernández <hugo.emec@gmail.com>*/
package com.wissen.telemedia.gui.views;

import com.wissen.telemedia.gui.MainWindow;
import com.wissen.telemedia.gui.UIViewListener;
import com.wissen.telemedia.tsaak.SensorsReader;

/** @brief valores para la medición de peso y altura */
@SuppressWarnings("serial")
public class HeightWeightRetrievalStepScreen extends RetrievalStepScreen {

	public HeightWeightRetrievalStepScreen(UIViewListener listener) {
		super(listener);
	}

	@Override
	public String getTitle() {
		return "Peso y Altura";
	}

	@Override
	public String getInstructionText() {
		return "Pararse en posición recta sobre la báscula que se "
				+ "encuentra en la parte superficial de la cabina.";
	}

	@Override
	public String getCorrectInstructiveImage() {
		return "assets/height_weight_correct.png";
	}

	@Override
	public String getIncorrectInstructiveImage() {
		return "assets/height_weight_incorrect.png";
	}
	/**
	 * @brief obtiene los valores de peso y altura y almacena la información. De
	 *        no estar disponible el dispositivo informa al usuario y cierra la
	 *        sesión
	 */
	synchronized protected void doRetrieval() {
		/*lectura del peso*/
		double weight = SensorsReader.readWeight();		
		if (weight == -1.0) {
			try {
				/*espera 3 segundos e intenta leer el dispositivo*/
				Thread.sleep(3000);
				weight = SensorsReader.readWeight();
				/*si el segundo intento  de lectura no es posible inicia la vista ErrorSensorView y cierra la sesión*/
				if (weight == -1.0) {
					String msg = "Por el momento no podemos atenderle por favor intentelo luego";
					((MainWindow) listener).changeViewTo(new ErrorSensorView(
							listener, msg));
					Thread.sleep(7000);
					((MainWindow) listener)
							.changeViewTo(((MainWindow) listener).currentScreen);
					listener.endSession();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*lectura de la altura*/
		double height = SensorsReader.readHeight();
		if (height == -1.0) {
			try {
				height = SensorsReader.readHeight();
				if (height == -1.0) {
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
		listener.getSession().addMetric("weight", weight);
		listener.getSession().addMetric("height", height);
	}

}
