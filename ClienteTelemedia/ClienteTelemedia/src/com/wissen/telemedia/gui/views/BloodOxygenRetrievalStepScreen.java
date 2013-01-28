/**@author Hugo Valdovinos Hernández <hugo.emec@gmail.com>*/

package com.wissen.telemedia.gui.views;

import com.wissen.telemedia.gui.UIViewListener;
import com.wissen.telemedia.tsaak.SensorsReader;

/** @brief valores para el paso medición de sangre y oxígeno */
@SuppressWarnings("serial")
public class BloodOxygenRetrievalStepScreen extends RetrievalStepScreen {

	public BloodOxygenRetrievalStepScreen(UIViewListener listener) {
		super(listener);
	}

	@Override
	public String getTitle() {
		return "Oxigenación en la sangre";
	}

	@Override
	public String getInstructionText() {
		return "Colocar el brazo como  muestra la imagen";
	}

	@Override
	public String getCorrectInstructiveImage() {
		return "assets/bloodoxygen_correct.png";
	}

	@Override
	public String getIncorrectInstructiveImage() {
		return "assets/bloodoxygen_incorrect.png";
	}

	/** @brief obtiene y almacena los valores */
	synchronized protected void doRetrieval() {
		double[] data = SensorsReader.readOximeter();

		double bloodOxygen = data[0];
		double heartRate = data[1];
		/*guarda el valor obtenido en el ArrayList de la clase Session */
		listener.getSession().addMetric("bloodoxygen", bloodOxygen);
		listener.getSession().addMetric("heartrate", heartRate);
	}
}
