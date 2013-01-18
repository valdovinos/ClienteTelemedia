package com.wissen.telemedia.dao;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLConnection;

public class DAOUser {

	static final String ENCRYPTION_KEY = "TELEMEDIA";
	
	public static String login(String username, String password) {
		String result;
		try {
			URLConnection conn = DAOConnection.getConnection();			
			conn.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());	
//			String parameters = "Action=Login&Username=" + username + "&Password=" + password;
//			PC1Encrypter pc1 = new PC1Encrypter(ENCRYPTION_KEY);
//			byte[] encryptedParameters = pc1.encrypt(parameters.getBytes());
//			pc1.endEncryption();
//			out.write(new String(encryptedParameters));
			out.write("Action=Login&Username=" + username + "&Password=" + password);
			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"ISO-8859-15"));
	        result = in.readLine();
	        in.close();
		} catch (Exception e) {
			System.out.println(e);
			result = "Connection error";
		}
		return result;
	}
	
	
	// Birthdate in format 'YYYY/MM/DD'
	public static String add(String name, String lastName1, String lastName2, String birthdate, String gender, String username, String password) {
		String result;
		try {
			URLConnection conn = DAOConnection.getConnection();
			conn.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			out.write("Action=New_User&Name=" + name + "&LastName1=" + lastName1 + "&LastName2=" + lastName2 +
					  "&Birthdate=" + birthdate + "&Gender=" + gender + "&Username=" + username + "&Password=" + password);
			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        result = in.readLine();
	        System.out.println(result);
	        in.close();
		} catch (Exception e) {
			result = "Connection error";
		}
		return result;
		
	}
	
}
