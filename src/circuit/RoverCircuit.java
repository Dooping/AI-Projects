package circuit;

import java.util.*;

/**
 * 	Classe que instancia a classe abstracta Individual
 */
public class RoverCircuit extends Individual {
	
	private static Random gen = new Random();
	private static Individual []children = new Individual[2];
	private int size;
	private int []circuit;
	private ObservationData data;
	private Double fit;
	
	//2 construtores
	public RoverCircuit(ObservationData data){
		this.data = data;
		//gerar uma lista de inteiros cegamente (0..n-1)
		//aplicar shuffle
		//copiar para circuit
	}

	@Override
	public double fitness() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Individual[] crossover(Individual other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mutate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString(){
		return null;		
	}
	
}
