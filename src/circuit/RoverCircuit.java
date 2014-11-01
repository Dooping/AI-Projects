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
		circuit = new int[size];
		List<Integer> c = new ArrayList<Integer>();
		for (int i = 0; i<data.getSize(); i++)
			c.add(i);
		
		gen.setSeed(System.nanoTime());
		Collections.shuffle(c,gen);
		for(int i = 0;i<c.size();i++){
			circuit[i]=c.get(i);
			
		}
		//gerar uma lista de inteiros cegamente (0..n-1)
		//aplicar shuffle
		//copiar para circuit
	}
	
	public RoverCircuit(ObservationData data, int[] circuit){
		this.data = data;
		this.circuit = new int[circuit.length];
		for (int i:circuit)
			this.circuit[i] = circuit[i];
		size = circuit.length;
	}

	@Override
	public double fitness() {
		int time = data.getSpot(circuit[0]).firstTime();
		
		for(int i = 0; i<size;i++){
			time += data.getSpot(circuit[i]).durationObservation(time);
			if (i<size-1)
				time += data.getCost(circuit[i], circuit[i+1]);
			else
				time += data.getCost(circuit[i], circuit[0]);
		}
		fit = (double)time;
		return fit;
	}

	@Override
	public Individual[] crossover(Individual other) {
		//return OX2(other);
		return OX1(other);
	}
	
	private Individual[] OX2(Individual other){
		int[] ca = new int[size];
		int[] cb = new int[size];
		boolean[] check1 = new boolean[size];
		boolean[] check2 = new boolean[size];
		
		int cut1,cut2;
		int r1 = gen.nextInt(size-1);
		int r2 = gen.nextInt(size-2);
		if (r2 >= r1){
			cut1=r1+1;
			cut2=r2+2;
		}
		else{
			cut1=r2+1;
			cut2=r1+2;
		}
		
		for (int i=cut1;i<cut2;i++){
			ca[i] = this.getCircuit()[i];
			check1[ca[i]]=true;
			cb[i] = ((RoverCircuit)other).getCircuit()[i];
			check2[cb[i]]=true;
		}
		int n1 = 0;
		int n2 = 0;
		for(int i=0;i<size;i++){
			if (!check1[((RoverCircuit)other).getCircuit()[i]])
				ca[n1++]=((RoverCircuit)other).getCircuit()[i];
			if (!check2[this.getCircuit()[i]])
				cb[n2++]=this.getCircuit()[i];
			if (n1 == cut1)
				n1 = cut2;
			if (n2 == cut1)
				n2 = cut2;
		}
		
		children[0] = new RoverCircuit(data,ca);
		children[1] = new RoverCircuit(data,cb);
		
		return children;
	}
	
	private Individual[] OX1(Individual other){
		int[] ca = new int[size];
		int[] cb = new int[size];
		boolean[] check1 = new boolean[size];
		boolean[] check2 = new boolean[size];
		
		int cut1,cut2;
		int r1 = gen.nextInt(size-1);
		int r2 = gen.nextInt(size-2);
		if (r2 >= r1){
			cut1=r1+1;
			cut2=r2+2;
		}
		else{
			cut1=r2+1;
			cut2=r1+2;
		}
		
		for (int i=cut1;i<cut2;i++){
			ca[i] = this.getCircuit()[i];
			check1[ca[i]]=true;
			cb[i] = ((RoverCircuit)other).getCircuit()[i];
			check2[cb[i]]=true;
		}
		int n1 = cut2%size;
		int n2 = cut2%size;
		for(int i=cut2;i<size;i++){
			if (!check1[((RoverCircuit)other).getCircuit()[i]])
				ca[n1++]=((RoverCircuit)other).getCircuit()[i];
			if (!check2[this.getCircuit()[i]])
				cb[n2++]=this.getCircuit()[i];
			if (n1 == size)
				n1 = 0;
			if (n2 == size)
				n2 = 0;
		}
		
		for(int i=0;i<cut2;i++){
			if (!check1[((RoverCircuit)other).getCircuit()[i]])
				ca[n1++]=((RoverCircuit)other).getCircuit()[i];
			if (!check2[this.getCircuit()[i]])
				cb[n2++]=this.getCircuit()[i];
			if (n1 == size)
				n1 = 0;
			if (n2 == size)
				n2 = 0;
		}
		
		children[0] = new RoverCircuit(data,ca);
		children[1] = new RoverCircuit(data,cb);
		
		return children;
	}

	@Override
	public void mutate() {
		// operador de troca
		int n1 = gen.nextInt(size);
		int n2 = gen.nextInt(size-1);
		if (n1 == n2)
			n1++;
		int aux = circuit[n1];
		circuit[n1]=circuit[n2];
		circuit[n2]=aux;
	}

	@Override
	public Object clone() {
		return new RoverCircuit(data, circuit);
	}
	
	public String toString(){
		String s = "[";
		for (int i = 0;i<circuit.length-1;i++)
			s += circuit[i] + " ";
		s+=circuit[circuit.length-1]+"]";
		return s;		
	}
	
	public int[] getCircuit(){
		return circuit;
	}

	@Override
	public int compareTo(Individual other) {
		double i1 = this.fitness();
		double i2 = other.fitness();
		if( i1 >  i2)
			return 1;
		else if ( i1 < i2 )
			return -1;
		else
			return 0;
	}
	
}
