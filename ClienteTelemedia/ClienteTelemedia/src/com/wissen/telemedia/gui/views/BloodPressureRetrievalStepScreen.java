/**@author Hugo Valdovinos Hernández <hugo.emec@gmail.com>*/
package com.wissen.telemedia.gui.views;

import com.wissen.telemedia.gui.UIViewListener;
import com.wissen.telemedia.tsaak.SensorsReader;
/**@brief valores para la medición de presión en la sangre*/
@SuppressWarnings("serial")
public class BloodPressureRetrievalStepScreen extends RetrievalStepScreen {

	public BloodPressureRetrievalStepScreen(UIViewListener listener) {
		super(listener);
	}

	@Override
	public String getTitle() {
		return "Presión";
	}

	@Override
	public String getInstructionText() {
		return "Colocar el brazo como  muestra la imagen";
	}

	@Override
	public String getCorrectInstructiveImage() {
		return "assets/bloodpressure_correct.png";
	}

	@Override
	public String getIncorrectInstructiveImage() {
		return "assets/bloodpressure_incorrect.png";
	}
	/**@brief obtiene y almacena los valores */
	synchronized protected void doRetrieval() {
		double[] data = SensorsReader.readPressure();
		
		double systolicPressure  = data[0];
		double diastolicPressure = data[1];
		/*guarda el valor obtenido en el ArrayList de la clase Session*/ 
		listener.getSession().addMetric("systolic",  systolicPressure);
		listener.getSession().addMetric("diastolic", diastolicPressure);
	}
}
