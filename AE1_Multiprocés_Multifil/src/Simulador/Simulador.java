package Simulador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Simulador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Simulador frame = new Simulador();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public Simulador() {
		// Cambiar dimensiones de la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500); // Tamaño más grande
		setTitle("Simulador de Proteínas");

		// Cambiar colores y diseño principal
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setBackground(new Color(173, 216, 230)); // Azul claro
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Título principal
		JLabel TituloLabel = new JLabel("SIMULADOR DE PROTEÍNAS");
		TituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
		TituloLabel.setForeground(new Color(0, 51, 102)); // Azul oscuro
		TituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TituloLabel.setBounds(150, 20, 400, 40); // Ajustar posición y tamaño
		contentPane.add(TituloLabel);

		// Primera estructura
		JLabel PrimeraEstructuraLabel = new JLabel("Primera Estructura (Tipo 1):");
		PrimeraEstructuraLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		PrimeraEstructuraLabel.setBounds(180, 100, 250, 20);
		contentPane.add(PrimeraEstructuraLabel);

		JSpinner PrimeraEstructuraSpinner = new JSpinner();
		PrimeraEstructuraSpinner.setBounds(450, 100, 60, 25);
		contentPane.add(PrimeraEstructuraSpinner);

		// Segunda estructura
		JLabel SegundaEstructuraLabel = new JLabel("Segunda Estructura (Tipo 2):");
		SegundaEstructuraLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		SegundaEstructuraLabel.setBounds(180, 150, 250, 20);
		contentPane.add(SegundaEstructuraLabel);

		JSpinner SegundaEstructuraSpinner = new JSpinner();
		SegundaEstructuraSpinner.setBounds(450, 150, 60, 25);
		contentPane.add(SegundaEstructuraSpinner);

		// Tercera estructura
		JLabel TerceraEstructuraLabel = new JLabel("Tercera Estructura (Tipo 3):");
		TerceraEstructuraLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		TerceraEstructuraLabel.setBounds(180, 200, 250, 20);
		contentPane.add(TerceraEstructuraLabel);

		JSpinner TerceraEstructuraSpinner = new JSpinner();
		TerceraEstructuraSpinner.setBounds(450, 200, 60, 25);
		contentPane.add(TerceraEstructuraSpinner);

		// Cuarta estructura
		JLabel CuartaEstructuraLabel = new JLabel("Cuarta Estructura (Tipo 4):");
		CuartaEstructuraLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		CuartaEstructuraLabel.setBounds(180, 250, 250, 20);
		contentPane.add(CuartaEstructuraLabel);

		JSpinner CuartaEstructuraSpinner = new JSpinner();
		CuartaEstructuraSpinner.setBounds(450, 250, 60, 25);
		contentPane.add(CuartaEstructuraSpinner);

		// Botón de simulación
		JButton botonSimular = new JButton("SIMULAR");
		botonSimular.setFont(new Font("Arial", Font.BOLD, 16));
		botonSimular.setBackground(new Color(0, 102, 204)); // Azul intenso
		botonSimular.setForeground(Color.WHITE); // Texto blanco
		botonSimular.setFocusPainted(false);
		botonSimular.setBounds(300, 330, 140, 40); // Ajustar tamaño y posición
		contentPane.add(botonSimular);

		// Multiproceso
		JLabel MultiprocesoLabel = new JLabel("Multiproceso:");
		MultiprocesoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		MultiprocesoLabel.setBounds(180, 400, 120, 20);
		contentPane.add(MultiprocesoLabel);

		JLabel SegundosMultiprocesoLabel = new JLabel("0.00 s");
		SegundosMultiprocesoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		SegundosMultiprocesoLabel.setForeground(new Color(0, 51, 102));
		SegundosMultiprocesoLabel.setBounds(310, 400, 100, 20);
		contentPane.add(SegundosMultiprocesoLabel);

		// Multihilo
		JLabel MultihiloLabel = new JLabel("Multihilo:");
		MultihiloLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		MultihiloLabel.setBounds(420, 400, 100, 20);
		contentPane.add(MultihiloLabel);

		JLabel SegundosMultihiloLabel = new JLabel("0.00 s");
		SegundosMultihiloLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		SegundosMultihiloLabel.setForeground(new Color(0, 51, 102));
		SegundosMultihiloLabel.setBounds(510, 400, 100, 20);
		contentPane.add(SegundosMultihiloLabel);

		botonSimular.addActionListener(e -> {
			int t1 = (int) PrimeraEstructuraSpinner.getValue();
			int t2 = (int) SegundaEstructuraSpinner.getValue();
			int t3 = (int) TerceraEstructuraSpinner.getValue();
			int t4 = (int) CuartaEstructuraSpinner.getValue();

			try {
				// Multiproceso
				double tiempoMultiproceso = ejecutarMultiproceso(t1, t2, t3, t4);
				SegundosMultiprocesoLabel.setText(String.format("%.2f s", tiempoMultiproceso));

				// Multihilo
				double tiempoMultihilo = ejecutarMultihilo(t1, t2, t3, t4);
				SegundosMultihiloLabel.setText(String.format("%.2f s", tiempoMultihilo));

				// Validación: Si alguno de los tiempos es 0.00, mostrar error
				if (tiempoMultiproceso == 0.00 || tiempoMultihilo == 0.00) {
					JOptionPane.showMessageDialog(this,
							"La simulación no se ha realizado con éxito. Por favor, revise los datos ingresados o inténtelo de nuevo.",
							"Error en la Simulación", JOptionPane.ERROR_MESSAGE);
				} else {
					// Si todo funciona correctamente, mostrar mensaje de éxito
					JOptionPane.showMessageDialog(this,
							"Simulación completada con éxito.\n" + "Tiempo multiproceso: "
									+ String.format("%.2f s", tiempoMultiproceso) + "\n" + "Tiempo multihilo: "
									+ String.format("%.2f s", tiempoMultihilo),
							"Éxito", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception ex) {
				// Manejo de errores generales
				JOptionPane.showMessageDialog(this, "Error durante la simulación. Por favor, inténtelo de nuevo.\n"
						+ "Detalles: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		});

	}

	/**
	 * Ejecuta la simulación utilizando múltiples procesos externos.
	 * Para cada tipo de estructura proteica (t1, t2, t3, t4), se crea un número 
	 * correspondiente de procesos que simulan cada estructura.
	 * 
	 * @param t1 número de simulaciones para el tipo 1 de estructura.
	 * @param t2 número de simulaciones para el tipo 2 de estructura.
	 * @param t3 número de simulaciones para el tipo 3 de estructura.
	 * @param t4 número de simulaciones para el tipo 4 de estructura.
	 * @return el tiempo total de ejecución de la simulación en segundos.
	 */

	private double ejecutarMultiproceso(int t1, int t2, int t3, int t4) {
		long start = System.currentTimeMillis();
		List<Process> processes = new ArrayList<>();
		int ordenProteina = 1;

		try {
			for (int i = 1; i <= 4; i++) {
				int cantidad = switch (i) {
				case 1 -> t1;
				case 2 -> t2;
				case 3 -> t3;
				case 4 -> t4;
				default -> 0;
				};

				for (int j = 0; j < cantidad; j++) {

					ProcessBuilder pb = new ProcessBuilder("java", "-cp", "bin", "Simulador.SimuladorMP",
							String.valueOf(i), String.valueOf(ordenProteina));

					pb.redirectErrorStream(true);
					processes.add(pb.start());

					ordenProteina++;
				}
			}

			for (Process process : processes) {
				process.waitFor();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (System.currentTimeMillis() - start) / 1000.0;
	}

	/**
	 * Ejecuta la simulación utilizando multithreading (hilos concurrentes).
	 * Cada hilo representa una simulación independiente de una estructura proteica.
	 * 
	 * @param t1 número de simulaciones para el tipo 1 de estructura.
	 * @param t2 número de simulaciones para el tipo 2 de estructura.
	 * @param t3 número de simulaciones para el tipo 3 de estructura.
	 * @param t4 número de simulaciones para el tipo 4 de estructura.
	 * @return el tiempo total de ejecución de la simulación en segundos.
	 */
	private double ejecutarMultihilo(int t1, int t2, int t3, int t4) {
		long start = System.currentTimeMillis();

		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		List<Runnable> tareas = new ArrayList<>();

		int contador = 1;

		for (int i = 0; i < t1; i++) {
			tareas.add(new SimulacioMT(1, contador++));
		}
		for (int i = 0; i < t2; i++) {
			tareas.add(new SimulacioMT(2, contador++));
		}
		for (int i = 0; i < t3; i++) {
			tareas.add(new SimulacioMT(3, contador++));
		}
		for (int i = 0; i < t4; i++) {
			tareas.add(new SimulacioMT(4, contador++));
		}

		for (Runnable tarea : tareas) {
			executor.submit(tarea);
		}

		executor.shutdown();
		try {
			if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
				executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			executor.shutdownNow();
		}
		return (System.currentTimeMillis() - start) / 1000.0;
	}

	/**
	 * Simula un cálculo basado en el tipo de estructura proteica.
	 * El tiempo de simulación se incrementa exponencialmente según el tipo.
	 * 
	 * @param type el tipo de estructura proteica (1, 2, 3 o 4).
	 * @return un valor de cálculo simulado, basado en cálculos matemáticos.
	 */
	public static double simulation(int type) {
		double calc = 0.0;
		double simulationTime = Math.pow(5, type);
		double startTime = System.currentTimeMillis();
		double endTime = startTime + simulationTime;
		while (System.currentTimeMillis() < endTime) {
			calc = Math.sin(Math.pow(Math.random(), 2));
		}
		return calc;
	}
}
