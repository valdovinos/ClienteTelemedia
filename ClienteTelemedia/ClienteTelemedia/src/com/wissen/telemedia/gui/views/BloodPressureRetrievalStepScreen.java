/**@author Hugo Valdovinos Hernández <hugo.emec@gmail.com>*/
package com.wissen.telemedia.gui.views;

import com.wissen.telemedia.gui.MainWindow;
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
		if (data[0] == -1.0){
				try {
					data = SensorsReader.readPressure();
					if (data[0] == -1.0) {						
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
		double systolicPressure  = data[0];
		double diastolicPressure = data[1];
		/*guarda el valor obtenido en el ArrayList de la clase Session*/ 
		listener.getSession().addMetric("systolic",  systolicPressure);
		listener.getSession().addMetric("diastolic", diastolicPressure);
	}
}
