/**
 * Session.java*/
package com.wissen.telemedia.gui;

import java.util.ArrayList;

import com.wissen.telemedia.dao.DAOParameters;
import com.wissen.telemedia.dao.DAOUser;

public class Session {

	public String firstname, lastname1, lastname2;
	
	public int id = -1;
	
	protected ArrayList<String> metricKeys;
	protected ArrayList<Double> metricValues;
	
	public Session() {
		metricKeys = new ArrayList<String>();
		metricValues = new ArrayList<Double>();
	}

	public void addMetric(String key, double value) {
		metricKeys.add(key);
		metricValues.add(value);
	}
	
	public double getMetric(String key) {
		return metricValues.get(metricKeys.indexOf(key));
	}
	
	public String getKey(int index) {
		return metricKeys.get(index);
	}
	
	public int metricTotal() {
		return metricKeys.size();
	}
	
	/**
	 * * inicia la sesion con los parametros recibidos generardo una conexion y enviando el nombre de usuario y el password
	 * de ser exitosa almacena los datos en las variables correspondientes
	 * @param username  nombre de usuario 
	 * @param password  contrasena	  
	 * */
	public boolean attemptLogin(String username, String password) {
		String result = DAOUser.login(username, password);
		//el servidor responde successful seguido de la trama con los datos del usuario
		if(result.startsWith("successful")) {
			System.out.println(result);
			String[] resultArray = result.split(";");// formato de la trama successful;person_id;given_name;middle_name;lastName (lopez perez)
			int personId = Integer.parseInt(resultArray[1]);
			String firstName = resultArray[2];
			String[] lastname = resultArray[4].split(" ");
			
			id = personId;
			firstname = firstName;
			lastname1 = lastname[0];
			lastname2 = lastname[1];
			
			return true;
		}
		return false;
	}
	
	public void commit() {
		double[] parameters = new double[7];
		parameters[0] = getMetric("weight");
		parameters[1] = getMetric("diastolic");
		parameters[2] = getMetric("systolic");
		parameters[3] = getMetric("height");
		parameters[4] = getMetric("temperature");
		parameters[5] = getMetric("bloodoxygen");
		parameters[6] = getMetric("heartrate");
		String result = DAOParameters.saveParameters(id, parameters);
		System.out.println(result);
	}
}
