package problemas;

import java.util.ArrayList;
import java.util.List;

public interface Problema {


	public List<Problema> problemasGenerados = new ArrayList<Problema>();
//	final int semilla = 0;
	public enum TIPO { MOCHILA , SUBSECUENCIA, VIAJANTE, MATRICES}; 
	public String execute();
	public String getTipo();
	Problema recuperarProblema(String semilla);
	public int getTipoPregunta();
	public void setTipoPregunta(int tipoPregunta);

}