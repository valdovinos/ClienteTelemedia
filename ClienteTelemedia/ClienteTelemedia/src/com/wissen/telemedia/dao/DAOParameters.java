/**@author Hugo Valdovinos Hernández <hugo.emec@gmail.com>*/
package com.wissen.telemedia.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLConnection;

/**
 * @class DAOParameters
 * @brief envió de paramaetros mediante una conexión URLConnection
 * @param PARAMETER_NAMES
 *            nombre de los valores a enviar
 */
public class DAOParameters {

	public static final String[] PARAMETER_NAMES = { "Weight", "Diapress",
			"Syspress", "Height", "Temperature", "Oxygen", "Heartbeat" };

	/**
	 * @brief envia los valores por la conexión
	 * @param con
	 *            recibe el objeto Connection
	 * @param out
	 *            recive los parametros a enviar
	 * @param in
	 *            almacena la respuesta del servidor
	 * @return cadena con la respuesta del servidor
	 * @retval e objeto con la expción del error
	 * @see DAOConnection
	 */
	public static String saveParameters(int personId, double[] parameters) {
		String result;
		try {
			URLConnection conn = DAOConnection.getConnection();
			conn.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(
					conn.getOutputStream());
			out.write("Action=Parameters&PersonId=" + personId);
			/*concatena los valores con sus respectivos nombres */
			for (int i = 0; i < parameters.length; i++)
				out.write("&" + PARAMETER_NAMES[i] + "=" + parameters[i]);
			out.close();
			/*almacena la respuesta del servidor en buffer */
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			result = in.readLine();
			in.close();
		} catch (Exception e) {
			result = "Connection error";
		}
		return result;
	}

}
