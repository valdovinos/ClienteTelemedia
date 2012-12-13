package com.wissen.telemedia.gui.views;

import com.wissen.telemedia.gui.UIViewListener;
import com.wissen.telemedia.tsaak.SensorsReader;

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
	
	synchronized protected void doRetrieval() {
		double temperature = SensorsReader.readTemperature();
		
		listener.getSession().addMetric("temperature", temperature);
	}

}
