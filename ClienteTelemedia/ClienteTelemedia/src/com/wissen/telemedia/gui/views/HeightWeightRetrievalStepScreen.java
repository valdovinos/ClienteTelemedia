/**@author Hugo Valdovinos Hernández <hugo.emec@gmail.com>*/
package com.wissen.telemedia.gui.views;

import com.wissen.telemedia.gui.UIViewListener;
import com.wissen.telemedia.tsaak.SensorsReader;
/**@brief valores para la medición de peso y altura*/
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
		return "Pararse en posición recta sobre la báscula que se "+
	           "encuentra en la parte superficial de la cabina.";
	}

	@Override
	public String getCorrectInstructiveImage() {
		return "assets/height_weight_correct.png";
	}

	@Override
	public String getIncorrectInstructiveImage() {
		return "assets/height_weight_incorrect.png";
	}
	/**@brief obtiene los valores de peso y altura y almacena la información*/
	synchronized protected void doRetrieval() {
		double weight = SensorsReader.readWeight();
		double height = SensorsReader.readHeight();
		
		listener.getSession().addMetric("weight", weight);
		listener.getSession().addMetric("height", height);
	}

}
