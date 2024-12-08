package Simulador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimuladorMP {

	public static void main(String[] args) {
		int tipoProteina = Integer.parseInt(args[0]);
		int ordenProteina = Integer.parseInt(args[1]); // Recibimos el orden de la proteína

		double resultado = simulation(tipoProteina);

		crearArchivo(tipoProteina, ordenProteina, resultado);
	}
	/**
	 * Simula un cálculo basado en el tipo de estructura proteica.
	 * El tiempo de simulación se incrementa exponencialmente según el tipo.
	 * 
	 * @param tipo el tipo de estructura proteica (1, 2, 3 o 4).
	 * @return un valor de cálculo simulado, basado en cálculos matemáticos.
	 */
	public static double simulation(int tipo) {
		double calc = 0.0;
		double simulationTime = Math.pow(5, tipo);
		double startTime = System.currentTimeMillis();
		double endTime = startTime + simulationTime;

		while (System.currentTimeMillis() < endTime) {
			calc = Math.sin(Math.pow(Math.random(), 2));
		}

		return calc;
	}
	/**
	 * Crea un archivo con los resultados de la simulación.
	 * El archivo se guarda con un nombre basado en el tipo de proteína, 
	 * el orden de la proteína y la fecha y hora exacta.
	 * 
	 * @param tipoProteina el tipo de proteína que se ha simulado.
	 * @param ordenProteina el orden de la proteína dentro de su tipo.
	 * @param resultado el resultado final de la simulación.
	 */
	public static void crearArchivo(int tipoProteina, int ordenProteina, double resultado) {
		try {
			// Formatear la fecha y hora correctamente
			String fechaHora = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());

			// Generar el nombre del archivo
			String fileName = String.format("PROT_MP_%d_n%d_%s.sim", tipoProteina, ordenProteina, fechaHora);

			File file = new File(fileName);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			// Hora de inicio
			writer.write(fechaHora);
			writer.newLine();

			// Hora de finalización
			String fechaFinalizacion = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
			writer.write(fechaFinalizacion);
			writer.newLine();

			// Calcular el tiempo de simulación con los tiempos en milisegundos
			double startTime = System.currentTimeMillis();
			double simulationTime = Math.pow(5, tipoProteina);
			double endTime = startTime + simulationTime;
			double tiempoSimulacion = (endTime - startTime) / 1000.0;
			writer.write(String.format("%.2f", tiempoSimulacion));
			writer.newLine();

			// Resultado de la simulación
			writer.write(String.format("%.8f", resultado));
			writer.newLine();

			writer.close();

		} catch (IOException e) {
			System.err.println("Error al crear el archivo: " + e.getMessage());
		}
	}
}
