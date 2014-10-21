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
		size = data.getSize();
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
		//copiar para circuit
	}
	
	public RoverCircuit(ObservationData data, int[] circuit){
		this.data = data;
		for (int i:circuit)
			this.circuit[i] = circuit[i];
		size = circuit.length;
	}

	@Override
	public double fitness() {
		return fit;
	}

	@Override
	public Individual[] crossover(Individual other) {
		RoverCircuit a =(RoverCircuit)this.clone();
		RoverCircuit b =(RoverCircuit)other.clone();
		int[] ca = a.getCircuit();
		int[] cb = b.getCircuit();
		int c2 = 1 + gen.nextInt(size - 3);
		int c1 = 0 + gen.nextInt(c2 - 1);
		int n = c2;
		for (int i = c2;i<size;i++){
			if (alreadyInArray(cb,c1,n,this.getCircuit()[i]))
				cb[n] = 0;
		}
		
		return null;
	}
	
	private boolean alreadyInArray(int[] circuit,int c1, int c2, int elem){
		//for ()
		return false;
	}

	@Override
	public void mutate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object clone() {
		return new RoverCircuit(data, circuit);
	}
	
	public String toString(){
		return null;		
	}
	
	public int[] getCircuit(){
		return circuit;
	}
	
}
