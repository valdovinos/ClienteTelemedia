/**
 * @author Hugo Valdovinos
 * */
package com.wissen.telemedia.dao;

import java.net.URL;
import java.net.URLConnection;
/**@brief gestión de las conexiones al servidor web
 * @param SERVLET_LOCATION direccion web del servidor
 */
public class DAOConnection {

	//private static final String CONFIG_FILENAME = "config.dat";
	//private static String HOST = "localhost", PORT = "8081";
	//private static String SERVLET_LOCATION = "http://" + HOST + ":" + PORT + "/openmrs-standalone/Telemedia/OmrsServlet";
	///cambio para correr en un servidor php
	private static String SERVLET_LOCATION = "http://www.e-saludjalisco.org/telemedicina/includes/EXT/bridge.php";
	
	//private static String SERVLET_LOCATION = "http://192.168.1.187/telemedicina/includes/EXT/bridge.php";
	//private static String SERVLET_LOCATION = "http://localhost/Telemedia/bridge.php";
	/*static {
		readConfigFile();
		//SERVLET_LOCATION = "http://" + HOST + ":" + PORT + "/openmrs-standalone/Telemedia/OmrsServlet";
		//System.out.println(SERVLET_LOCATION);
	}*/
	/*
	 * lee el archivo de configuracion config.dat el cual contiene las configuaraciones de la conexion
	 * @param HOST direccion del servidor
	 * @param PORT puerto para acceder al servidor
	 */
	/*static void readConfigFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(CONFIG_FILENAME));
			String line = br.readLine();
			if(line.toUpperCase().startsWith("HOST")) HOST = line.split("=")[1];
			else if(line.toUpperCase().startsWith("PORT")) PORT = line.split("=")[1];
			line = br.readLine();
			if(line.toUpperCase().startsWith("HOST")) HOST = line.split("=")[1];
			else if(line.toUpperCase().startsWith("PORT")) PORT = line.split("=")[1];
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	/**
	 *@brief asigna la dirección web a la variable URL
	 *@return url del servidor
	 **/
	static URLConnection getConnection() throws Exception {
		URL url = new URL(SERVLET_LOCATION);
		URLConnection connection = url.openConnection();
		return connection;
	}
	
}
