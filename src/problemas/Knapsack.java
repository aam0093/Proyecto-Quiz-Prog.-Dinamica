/**
 * Clase Knapsack
 *
 * Esta clase implementa la interface Problema y genera problemas de programaci�n din�mica
 * del tipo de Mochila o Knapsack
 * 
 * @author Asier Alonso Morante 
 * @version 1.2 23/11/2015
 */
package problemas;

import java.util.Random;
import pregunta.Semilla;

/**
 * Clase que define la logica con la que se generaran los problemas del tipo
 * Mochila o Knapsack
 * 
 * @author: Asier Alonso Morante
 * @version: 20/01/2016/A
 */

public class Knapsack implements Problema {

	/** Indica el valor de la semilla del Problema */
	long semilla = 0;

	/** Indica el tipo de pregunta al que se exportar� el problema. */
	int tipoPregunta = 0;

	/** Indica la Capacidad que tendr� la Mochila */
	int capacidad;

	/** N�mero de elementos que tendremos disponibles */
	int numElements;

	/**
	 * Array con pesos de cada elemento, posicion 0 peso del elemento 1; pos
	 * 1,elemento 2..
	 */
	int[] weight;

	/**
	 * Array con valor de cada elemento, posicion 0 peso del elemento 1; pos
	 * 1,elemento 2..
	 */
	int[] values;

	/** Matriz de la mochila */
	int[][] matriz;

	/**
	 * Crea el problema con la capacidad y numero de elementos introducidos
	 * 
	 * @param capacidad
	 *            entero que indica la capacidad deseada
	 * @param elementos
	 *            entero que indica el numero de elementos del problema.
	 * @throws Exception
	 */
	public Knapsack(int capacidad, int elementos) {
		this.capacidad = capacidad;
		numElements = elementos;
		Semilla seed = new Semilla(capacidad, elementos, "mochila");
		semilla = seed.getSeed();
		System.out.println("Semilla creada auto: " + seed);
		weight = new int[numElements];
		values = new int[numElements];
		matriz = new int[numElements + 1][capacidad + 1];
	}// Constructor()

	/**
	 * Contructor que recibe una semilla que se utilizar� a la hora de recuperar
	 * un problema
	 * 
	 * @param capacidad
	 *            entero que indica la capacidad del problema.
	 * @param elementos
	 *            entero que indica el numero de elementos del problema.
	 * @param seed
	 *            variable tipo long que indica el valor de la semilla del
	 *            problema que se quiere recuperar.
	 * @throws Exception
	 */
	public Knapsack(int capacidad, int elementos, long seed) {
		this.capacidad = capacidad;
		numElements = elementos;
		semilla = seed;
		System.out.println("Semilla recibida: " + capacidad);
		weight = new int[numElements];
		values = new int[numElements];
		matriz = new int[numElements + 1][capacidad + 1];
	}

	/**
	 * Crea el problema con las longitudes introducidas como parametros
	 * 
	 * @param pesos
	 *            cadena con los pesos deseados para cada elemento del problema
	 * @param valores
	 *            cadena con los valores deseados para cada elemento del
	 *            problema
	 * @throws Exception
	 */
	public void initializeWeights(int[] pesos, int[] valores) {
		this.weight = pesos;
		this.values = valores;
	}

	/**
	 * Inicializa los pesos y valores con valores aleatorios.
	 * 
	 * @throws Exception
	 */
	public void initializeWeights() {
		Random rnd = new Random(semilla);
		for (int i = 0; i < numElements; i++) {
			weight[i] = (int) Math.round(rnd.nextDouble() * 30 + 1);
			values[i] = (int) Math.round(rnd.nextDouble() * 30 + 1);
		}
	}

	/**
	 * Rellena la matriz con los datos del problema
	 * 
	 * @throws Exception
	 */
	public void fillKnaspackMatrix() {
		// Rellenamos la 1� fila de ceros
		for (int i = 0; i <= capacidad; i++)
			matriz[0][i] = 0;

		// Rellenamos la 1� columna de ceros
		for (int i = 0; i <= weight.length; i++)
			matriz[i][0] = 0;

		for (int j = 1; j <= weight.length; j++) {
			for (int c = 1; c <= capacidad; c++) {
				if (c < weight[j - 1]) {
					matriz[j][c] = matriz[j - 1][c];
				} else {
					if (matriz[j - 1][c] > matriz[j - 1][(int) (c - weight[j - 1])] + values[j - 1]) {
						matriz[j][c] = matriz[j - 1][c];
					} else {
						matriz[j][c] = matriz[j - 1][(int) (c - weight[j - 1])] + values[j - 1];
					}
				}
			}
		}
	}

	/**
	 * Devuelve el valor de la semilla del problema
	 * 
	 * @throws Exception
	 */
	public long getSemilla() {
		return semilla;
	}

	/** Obtiene la matriz del problema */
	public int[][] getMatrix() {
		return matriz;
	}

	/** Obtiene el maximo beneficio obtenido */
	public double getMaxProfit() {
		return matriz[capacidad + 1][numElements + 1];
	}

	/** Ejecuta el problema */
	@Override
	public String execute() {
		initializeWeights();
		fillKnaspackMatrix();
		return "";
	}

	/** Obtiene el tipo de problema */
	@Override
	public String getTipo() {
		return TIPO.MOCHILA.toString();
	}

	/** Obtiene la capacidad de la mochila */
	public int getCapacity() {
		return capacidad;
	}

	/** Obtiene el numero de elementos del problema */
	public int getNumElements() {
		return numElements;
	}

	/** Obtiene los pesos de los elementos del problema */
	public int[] getWeights() {
		return weight;
	}

	/** Obtiene los valores de los elementos del problema */
	public int[] getValues() {
		return values;
	}

	/**
	 * M�todo que recibe una semilla y crea un problema con los datos que se
	 * generaran a partir de esa semilla
	 * 
	 * @param semilla
	 *            de tipo String
	 */
	@Override
	public Problema recuperarProblema(String semilla) {
		Knapsack mochila;
		int cantidad = Integer.parseInt(semilla.substring(3, 6));
		int numElem = Integer.parseInt(semilla.substring(6, 9));
		mochila = new Knapsack(cantidad, numElem, Long.valueOf(semilla).longValue());
		return mochila;
	}

	/** Obtiene el valor final de la Mochila */
	public int getResultValue() {
		return matriz[capacidad][numElements];
	}

	/** Obtiene un entero con el tipo de Pregunta del problema */
	@Override
	public int getTipoPregunta() {
		return tipoPregunta;
	}

	/** Establece cual ser� el tipo de pregunta del problema */
	@Override
	public void setTipoPregunta(int tipo) {
		tipoPregunta = tipo;
	}

}// Clase Knapsack
