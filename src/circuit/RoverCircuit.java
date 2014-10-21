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
	private Double fit = 0.0;
	
	//2 construtores
	public RoverCircuit(ObservationData data){
		this.data = data;
		List<Integer> c = new ArrayList<Integer>();
		for (int i = 0; i<data.getSize(); i++)
			c.add(i);
		
		gen.setSeed(System.currentTimeMillis());
		Collections.shuffle(c,gen);
		for(int i:c)
			circuit[i]=c.get(i);
		
		for(int i:circuit)
			if (i>0)
				fit += data.getCost(circuit[i-1], circuit[i]);
			else
				fit += data.getCost(circuit[circuit.length-1], circuit[i]);
		
		//gerar uma lista de inteiros cegamente (0..n-1)
		//aplicar shuffle
		//copiar para Circuit
	}

	@Override
	public double fitness() {
		return fit;
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
