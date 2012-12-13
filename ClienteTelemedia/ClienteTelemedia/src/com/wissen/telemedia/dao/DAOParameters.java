package com.wissen.telemedia.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLConnection;

public class DAOParameters {

	public static final String[] PARAMETER_NAMES = {"Weight", "Diapress", "Syspress",
		                                            "Height", "Temperature", "Oxygen",
		                                            "Heartbeat"
		                                           };
	
	public static String saveParameters(int personId, double[] parameters) {
		String result;
		try {
			URLConnection conn = DAOConnection.getConnection();
			conn.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			out.write("Action=Parameters&PersonId=" + personId);
			for(int i = 0; i < parameters.length; i ++) out.write("&" + PARAMETER_NAMES[i] + "=" + parameters[i]);
			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        result = in.readLine();
	        in.close();
		} catch (Exception e) {
			result = "Connection error";
		}
		return result;
	}
	
}
