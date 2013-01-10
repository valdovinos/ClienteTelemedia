package com.wissen.telemedia.tsaak;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SensorsReader {

	// Devuelve peso en kilogramos
	public static double readWeight() {
		double weight = -1;
		try {
			Process p = Runtime.getRuntime().exec("./tsaak SAAL");
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			BufferedReader br = new BufferedReader(ir);
			String line = br.readLine(); // BEGIN:SAAL
			line = br.readLine();
			
			if(!line.equals("END:ERROR")) 
				// RESULT1:0000 en hectogramos
				weight = Double.parseDouble(line.split(":")[1].trim()) / 10.0;

			br.close();
			ir.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return weight;
	}
	
	// Devuelve { Presion sistlica, presion diastlica }
	public static double[] readPressure() { 
		double[] pressure = {-1, -1};
		try {
			Process p = Runtime.getRuntime().exec("./tsaak KIL");
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			BufferedReader br = new BufferedReader(ir);
			String line = br.readLine(); // BEGIN:SAAL
			line = br.readLine();
			
			if(!line.equals("END:ERROR")) {
				// RESULT1:000 Presin sistlica
				pressure[0] = Double.parseDouble(line.split(":")[1].trim()) / 10.0;
				line = br.readLine();
				// RESULT2:000 Presin sistlica
				pressure[1] = Double.parseDouble(line.split(":")[1].trim()) / 10.0;
			}
			
			br.close();
			ir.close();
		} catch (Exception e) {
			pressure[0] = -1;
			pressure[1] = -1;
			e.printStackTrace();
		}
		
		return pressure;
	}
	
	// Devuelve estatura en metros
	public static double readHeight() { 
		double height = -1;
		try {
			Process p = Runtime.getRuntime().exec("tsaakIWM NAAB");
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			BufferedReader br = new BufferedReader(ir);
			String line = br.readLine(); // BEGIN:SAAL
			line = br.readLine();
			
			if(!line.equals("END:ERROR")) 
				// RESULT1:0000 en centmetros
				height = Double.parseDouble(line.split(":")[1].trim()) / 100.0;

			br.close();
			ir.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return height;
	}
	
	// Devuelve temperature en grados Celsius
	public static double readTemperature() { 
		double temperature = -1;
		try {
			Process p = Runtime.getRuntime().exec("./tsaak OOXOL");
            p.waitFor();		
			
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			
			BufferedReader br = new BufferedReader(ir);
		
			//String line1 = br.readLine(); // BEGIN:SAAL
			String line = br.readLine(); 
			
			line = br.readLine();
			System.out.println("temperatura "+line);
			if(!line.equals("END:ERROR")) 
				// RESULT1:0000 en dcimas de grados
				temperature = Double.parseDouble(line.split(":")[1].trim()) / 10.0;

			br.close();
			ir.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return temperature;
	}
	
	// Devuelve { Porcentaje de oxigeno, latidos por minuto}
	public static double[] readOximeter() { 
		double[] oximeter = {-1, -1};
		try {
			Process p = Runtime.getRuntime().exec("./tsaak IIK");
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			BufferedReader br = new BufferedReader(ir);
			String line = br.readLine(); // BEGIN:SAAL
			line = br.readLine();
			
			if(!line.equals("END:ERROR")) {
				// RESULT1:000 Porcentaje de oxigeno
				oximeter[0] = Double.parseDouble(line.split(":")[1].trim());
				line = br.readLine();
				// RESULT2:000 Latidos por minuto
				oximeter[1] = Double.parseDouble(line.split(":")[1].trim());
			}
			
			br.close();
			ir.close();
		} catch (Exception e) {
			oximeter[0] = -1;
			oximeter[1] = -1;
			e.printStackTrace();
		}
		
		return oximeter;
	}
	
}
