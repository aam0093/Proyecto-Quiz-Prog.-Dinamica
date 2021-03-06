package problemas;

import java.util.Random;

import pregunta.Semilla;

/**
 * Clase que define la logica con la que se generaran los problemas de
 * Multiplicaci�n de Matrices Encadenadas
 * 
 * @author: Asier Alonso Morante
 * @version: 20/01/2016/A
 */

public class MultiplicaMatrices implements Problema {

	/** Almacena la matriz con los valores de las operaciones */
	public static int[][] matrizResultado = null;

	/** Almacena la matriz con el n�mero de las operaciones */
	public static int[][] matrizOperaciones;

	/** Variable que almacena el n�mero de matrices que se van a multiplicar */
	public static int numMatrices;

	/**
	 * Matriz donde se almacenan las dimensiones de las matrices, contiene
	 * numMatrices-1 elementos ya que el segundo valor de cada matriz es el
	 * mismo que el primer valor de las dimensiones de la matriz que la sucede
	 */
	public int[] dimensiones;

	/** Almacena el tipo de Pregunta del problema */
	public int tipoPregunta;

	/** Indica el valor de la semilla del Problema */
	public long semilla = 0;

	int porcentaje = 0;
	
	int inf = 9999;

	/**
	 * Crea el problema el numero de matrices recibidas.
	 * 
	 * @param numMat
	 *            entero que indica el n�mero de matrices que se van a
	 *            multiplicar
	 * @throws Exception
	 */
	public MultiplicaMatrices(int numMat) {
		numMatrices = numMat;
		dimensiones = new int[numMat + 1];
		matrizResultado = new int[numMatrices][numMatrices + 1];
		Semilla seed = new Semilla(numMat, 0,0,0, "matrices");
		semilla = seed.getSeed();
		initMatrices();
	
	}

	public MultiplicaMatrices(int numMat, long sem) {
		numMatrices = numMat;
		dimensiones = new int[numMat + 1];
		semilla = sem;
		initMatrices();
	}

	/**
	 * inicializa los valores de las matrices a partir de una semilla y los
	 * almacena en la matriz dimensiones
	 */
	public void initMatrices() {
		Random rnd = new Random(semilla);
		matrizResultado = new int[numMatrices][numMatrices];
		matrizOperaciones = new int[numMatrices][numMatrices];
		for (int i = 0; i < dimensiones.length; i++) {
			dimensiones[i] = rnd.nextInt((100 - 1) + 1) + 1;
		}
	}

	/**
	 * Ejecuta el problema
	 */
	@Override
	public String execute() {
	
        for (int lenMinusOne = 1; lenMinusOne < numMatrices; lenMinusOne++) {
            for (int i = 0; i < numMatrices - lenMinusOne; i++) {
                int j = i + lenMinusOne;
                matrizResultado[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    int cost = matrizResultado[i][k] + matrizResultado[k+1][j] + dimensiones[i]*dimensiones[k+1]*dimensiones[j+1];
                    if (cost < matrizResultado[i][j]) {
                    	matrizResultado[i][j] = cost;
                    	matrizOperaciones[i][j] = k;
                    }
                }
            }
        }
    
		return "OK";
	}

	/**
	 * Calcula de forma recursiva los valores menores de la multiplicacion de
	 * varias matrices
	 * 
	 * @param i
	 *            el indice de la primera matriz que se va a multiplicar
	 * @param j
	 *            el indice de la ultima matriz que se va a multiplicar
	 */
	public int llenarMatriz(int i, int j) {
		if (i == j)
			return 0;
		matrizResultado[i][j] = inf;
		for (int k = i; k < j; k++) {
			int q = llenarMatriz(i, k) + llenarMatriz(k + 1, j) + dimensiones[i - 1] * dimensiones[k] * dimensiones[j];
			if (q < matrizResultado[i][j]) {
				matrizResultado[i][j] = q;
				matrizOperaciones[i][j] = k;
			}
		}
		return matrizResultado[i][j]; // and s;
	}

	/** Devuelve el numero de matrices del problema */
	public int getNumMatrices() {
		return numMatrices;
	}

	/** Devuelve la matriz que produce el problema */
	public int[][] getMatriz() {
		return matrizResultado;
	}

	/** Ejecuta el array con las dimensiones de las matrices */
	public int[] getDimensiones() {
		return dimensiones;
	}

	/** Devuelve el valor con el menor numero de operaciones */
	public int getResultado() {
		
		return matrizResultado[numMatrices-1][numMatrices-1];
	}

	/** Devuelve el tipo de problema */
	@Override
	public String getTipo() {
		return TIPO.MATRICES.toString();
	}

	/**
	 * Recibe una semilla y permite recuperar un problema de matrices con unos
	 * valores ya determinados por la semilla
	 */
	@Override
	public Problema recuperarProblema(String semilla) {
		MultiplicaMatrices matrices;
		int numMatrices = Integer.parseInt(semilla.substring(3, 6));
		matrices = new MultiplicaMatrices(numMatrices, Long.valueOf(semilla).longValue());
		return matrices;
	}


	public long getSemilla() {
		return semilla;
	}

	public void setPorcentaje(int pct) {
		porcentaje = pct;
	}

	public int getPorcentaje() {
		return porcentaje;
	}

}