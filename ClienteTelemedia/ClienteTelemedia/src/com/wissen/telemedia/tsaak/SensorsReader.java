/**
 * @author Hugo Valdovinos Hernández <hugo.emec@gmail.com>
 */
package com.wissen.telemedia.tsaak;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @class SensorsReader
 * @brief interactua con la consola del sistema y esta a su vez con los
 *        dispositivos de medición de signos vitales
 */

public class SensorsReader {

	/**
	 * @brief acceso al sensor para medir el peso
	 * @note Devuelve peso en gramos
	 * @param p
	 *            ejecuta un comando en consola
	 * @param ir
	 *            se almacena lo devuelto por la llamada a consola
	 * @param line
	 *            cadena para almacenar lo obtenido de la variable ir
	 * @param weight
	 *            variable almacena el valor devuelto por la variable br
	 * @note trama obtenida del buffer donde esta almacenado el valor BEGIN:SAAL
	 *       "primera linea" RESULT1:0000 "segunda linea" en hectogramos
	 * @return peso en hectogramos
	 * @retval objeto con la expción del error */
	 
	public static double readWeight() {
		double weight = -1;
		try {
			Process p = Runtime.getRuntime().exec("tsaak SAAL");
			p.waitFor();
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			BufferedReader br = new BufferedReader(ir);
			String line = br.readLine();
			/* < valor devuelto BEGIN:SAAL */
			line = br.readLine();
			/* < valor devuelto RESULT1:0000 */

			if (!line.equals("END:ERROR"))

				weight = Double.parseDouble(line.split(":")[1].trim()) / 10.0;

			br.close();
			ir.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return weight;
	}

	/**
	 * @brief acceso al sensor para medir Presión sistlica, presion diastlica
	 * @note Devuelve Presion sistlica, presión diastlica
	 * @param p
	 *            ejecuta un comando en consola
	 * @param ir
	 *            se almacena lo devuelto por la llamada a consola
	 * @param line
	 *            cadena para almacenar lo obtenido de la variable ir
	 * @param weight
	 *            variable almacena el valor devuelto por la variable br
	 * @note trama obtenida del buffer donde esta almacenado el valor BEGIN:KIL
	 *       "primera linea" RESULT1:0000 "segunda linea" en sistlica
	 *       RESULT2:0000 "tercera linea" en diastlica
	 * @return arreglo con los dos valores de la medición
	 * @retval objeto con la expción del error
	 */
	public static double[] readPressure() {
		double[] pressure = { -1, -1 };
		try {
			Process p = Runtime.getRuntime().exec("tsaak KIL");
			p.waitFor();
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			BufferedReader br = new BufferedReader(ir);
			String line = br.readLine();
			/* < valor devuelto BEGIN:KIL */
			line = br.readLine();
			/* < valor devuelto RESULT1:0000 */

			if (!line.equals("END:ERROR")) {
				pressure[0] = Double.parseDouble(line.split(":")[1].trim()) / 10.0;
				line = br.readLine();
				/* < RESULT2:0000 */
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

	/**
	 * @brief acceso al sensor para medir estatura
	 * @note Devuelve estatura en metros
	 * @param p
	 *            ejecuta un comando en consola
	 * @param ir
	 *            se almacena lo devuelto por la llamada a consola
	 * @param line
	 *            cadena para almacenar lo obtenido de la variable ir
	 * @param weight
	 *            variable almacena el valor devuelto por la variable br
	 * @note trama obtenida del buffer donde esta almacenado el valor BEGIN:NAAB
	 *       "primera linea" RESULT1:0000 "segunda linea" en centimetros
	 * @return estatura en metros
	 * @retval objeto con la expción del error
	 */
	public static double readHeight() {
		double height = -1;
		try {
			Process p = Runtime.getRuntime().exec("tsaak NAAB");
			p.waitFor();
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			BufferedReader br = new BufferedReader(ir);
			String line = br.readLine();
			line = br.readLine();

			if (!line.equals("END:ERROR"))
				height = Double.parseDouble(line.split(":")[1].trim()) / 100.0;

			br.close();
			ir.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return height;
	}

	/**
	 * @brief acceso al sensor para medir temperatura
	 * @note Devuelve temperatura en grados Celsius
	 * @param p
	 *            ejecuta un comando en consola
	 * @param ir
	 *            se almacena lo devuelto por la llamada a consola
	 * @param line
	 *            cadena para almacenar lo obtenido de la variable ir
	 * @param weight
	 *            variable almacena el valor devuelto por la variable br *
	 * @note trama obtenida del buffer donde esta almacenado el valor BEGIN:OOXOL
	 *       "primera linea" RESULT1:0000 "segunda linea" en decimas de grados
	 * @return valor de la temperatura
	 */
	public static double readTemperature() {
		double temperature = -1;
		try {
			Process p = Runtime.getRuntime().exec("tsaak OOXOL");
			p.waitFor();

			InputStreamReader ir = new InputStreamReader(p.getInputStream());

			BufferedReader br = new BufferedReader(ir);

			String line = br.readLine();

			line = br.readLine();
			if (!line.equals("END:ERROR"))
				temperature = Double.parseDouble(line.split(":")[1].trim()) / 10.0;

			br.close();
			ir.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return temperature;
	}

	/**
	 * @brief acceso al sensor para medir oxigenación en la sangre
	 * @note Devuelve Porcentaje de oxigeno, latidos por minuto
	 * @param p
	 *            ejecuta un comando en consola
	 * @param ir
	 *            se almacena lo devuelto por la llamada a consola
	 * @param line
	 *            cadena para almacenar lo obtenido de la variable ir
	 * @param weight
	 *            variable almacena el valor devuelto por la variable br
	 * @note trama obtenida del buffer donde esta almacenado el valor BEGIN:IIK
	 *       "primera linea" RESULT1:0000 "segunda linea" Porcentaje de oxigeno
	 *       RESULT2:0000 "tercera linea" Latidos por minuto
	 * @return arreglo con los dos valores de la medición
	 * 
	 */
	public static double[] readOximeter() {
		double[] oximeter = { -1, -1 };
		try {
			Process p = Runtime.getRuntime().exec("tsaak IIK");
			p.waitFor();
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			BufferedReader br = new BufferedReader(ir);
			String line = br.readLine();
			line = br.readLine();

			if (!line.equals("END:ERROR")) {
				oximeter[0] = Double.parseDouble(line.split(":")[1].trim());
				line = br.readLine();
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
