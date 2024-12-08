package Simulador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimulacioMT implements Runnable {

	private final int tipo;
	private final int ordenProteina;
	
	/**
	 * Constructor de la clase SimulacioMT.
	 * 
	 * @param tipo el tipo de proteína a simular.
	 * @param ordenProteina el número de orden de la proteína dentro de su tipo.
	 */
	public SimulacioMT(int tipo, int ordenProteina) {
		this.tipo = tipo;
		this.ordenProteina = ordenProteina;
	}

	/**
	 * Método que se ejecuta en un hilo separado para realizar la simulación y generar el archivo de resultados.
	 */
	@Override
	public void run() {
		try {
			double resultado = simulation(tipo);
			crearArchivo(tipo, ordenProteina, resultado);
		} catch (Exception e) {
			System.err.printf("Error en la simulación del tipo %d: %s%n", tipo, e.getMessage());
		}
	}
	
	/**
	 * Realiza la simulación para un tipo de proteína dado.
	 * El tiempo de simulación se calcula como una potencia de 5 según el tipo.
	 * 
	 * @param tipo el tipo de proteína para el cual se realiza la simulación.
	 * @return un valor de tipo double como resultado de los cálculos de la simulación.
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
	 * El archivo incluye la hora de inicio, la hora de finalización, el tiempo de simulación y el resultado.
	 * 
	 * @param tipoProteina el tipo de proteína que se ha simulado.
	 * @param ordenProteina el número de orden de la proteína dentro de su tipo.
	 * @param resultado el valor calculado durante la simulación.
	 */
	public static void crearArchivo(int tipoProteina, int ordenProteina, double resultado) {
		try {
			String fechaHora = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());

			String fileName = String.format("PROT_MT_%d_n%d_%s.sim", tipoProteina, ordenProteina, fechaHora);

			File file = new File(fileName);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			writer.write(fechaHora);
			writer.newLine();

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

			writer.write(String.format("%.8f", resultado));
			writer.newLine();

			writer.close();

		} catch (IOException e) {
			System.err.println("Error al crear el archivo: " + e.getMessage());
		}
	}
}
