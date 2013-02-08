/** @author Hugo Valdovinos Hernández <hugo.emec@gmail.com>
 */
package com.wissen.telemedia.gui;

import java.util.ArrayList;

import com.wissen.telemedia.dao.DAOParameters;
import com.wissen.telemedia.dao.DAOUser;
/**@class Session
 * @brief sesión de usuario dentro del ciclo de vida del programa
 * @param firstname primer nombre
 * @param lastname1 segundo nombre
 * @param lastname2 apellidos
 * @param id id único de usuario
 * @param metricKeys nombre de la variable en el array
 * @param metricValues valor de la variable en el array*/
public class Session {

	public String firstname, lastname1, lastname2;
	
	public int id = -1;
	
	protected ArrayList<String> metricKeys;
	protected ArrayList<Double> metricValues;
	/**@brief contructor de la clase inicializa los array*/
	public Session() {
		metricKeys = new ArrayList<String>();
		metricValues = new ArrayList<Double>();
	}
	/**@brief agregar el nombre y el valor de la variable en los array*/
	public void addMetric(String key, double value) {
		metricKeys.add(key);
		metricValues.add(value);
	}
	/**@brief obtiene el nombre y el valor de la variable en los array
	 * @param key index del valor en el array
	 * @return valor  en el index recibido*/
	public double getMetric(String key) {
		return metricValues.get(metricKeys.indexOf(key));
	}
	/**@brief obtiene el nombre del valor
	 * @param index posicón en el array
	 * @return nombre del valor en el index recibido*/
	public String getKey(int index) {
		return metricKeys.get(index);
	}
	/**@brief obtiene el tamaño de la variable metricKeys
	 * @return tamaño de array*/
	public int metricTotal() {
		return metricKeys.size();
	}
	
	/**
	 *@brief inicia la sesión con los parametros recibidos generardo una conexión y enviando el nombre de usuario y el password
	 * de ser exitosa almacena los datos en las variables correspondientes
	 * @param username  nombre de usuario 
	 * @param password  contrasena	
	 * @return verdadero
	 * @retval falso si la cadena regresada por el servidor no empieza por successful  
	 * */
	public boolean attemptLogin(String username, String password) {
		String result = DAOUser.login(username, password);
		//el servidor responde successful seguido de la trama con los datos del usuario
		if(result.startsWith("successful")) {
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
	/**@brief almacena los valores del array metricValues y los envia a DAOParameters
	 * @param parameters contiene los valores devueltos por getMetric()
	 * @see DAOParameterssaveParameters(), getMetric()*/
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
