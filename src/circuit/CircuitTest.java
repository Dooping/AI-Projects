package circuit;

import java.io.*;

import static java.lang.Math.*;

public class CircuitTest {

	public static final int DEFAULT_POP = 200;
	
	private int pop_size;
	/**
	 * @param args
	 * @throws IOException, ClassNotFoundException 
	 */
	//public static void main(String[] args) throws IOException, ClassNotFoundException {
	public CircuitTest(int pop_size,float pcrossover, float pmutate, int gen_cap, int elite) throws IOException, ClassNotFoundException{
		System.out.println(pop_size+":"+pcrossover+":"+pmutate+":"+gen_cap+":"+elite);
		if (pop_size > 0)
			this.pop_size = pop_size;
		else
			this.pop_size = DEFAULT_POP;
		
		String datastr = "";
		BufferedReader reader = new BufferedReader( new FileReader("/home/doping/Downloads/TrintaReal.txt"));
		
		String line = reader.readLine();
		while( line != null) {
			datastr += line + "\n";
			line = reader.readLine();
		}
		
		reader.close();
		
		
		ObservationData r = new ObservationData(datastr);
		
		int maxdist=0;
		int mindist=Integer.MAX_VALUE;
		
		for(int i=0; i < r.getSize(); i++ )
			for(int j=0; j < r.getSize(); j++ ) {
				maxdist = max(maxdist,r.getCost(i, j));
				if( i != j ) mindist = min(mindist,r.getCost(i, j));
			}
		
		System.out.println(mindist+" "+maxdist);
			
		/*
		Random gen = new Random();
		int size = 20;
		
		ObservationData.ObservationInterval interval = r.new ObservationInterval(0,ObservationData.SOL-1);
		SortedSet<ObservationData.ObservationInterval> is = new TreeSet<ObservationData.ObservationInterval>();
		is.add(interval);
		
		ObservationData.ObservationSpot[] spot = new ObservationData.ObservationSpot[size];
		int[][] distances = new int[size][size];
		for( int s =0 ; s < size ; s++) {
			spot[s] = r.new ObservationSpot("P"+s,gen.nextInt(ObservationData.SOL/2),is);
		}
		for( int i=0; i < size; i++)
			for( int j=0; j < size; j++)
				if( i != j )
					distances[i][j] = gen.nextInt(6*ObservationData.SOL);
		
		r = new ObservationData(size,spot,distances);
		*/	
		RoverCircuit p1 = new RoverCircuit(r);
		RoverCircuit p2 = new RoverCircuit(r);
		
		System.out.println(p1);
		System.out.println(p1.fitness());
		System.out.println(p2);
		System.out.println(p2.fitness());
		
		Individual[] offspring = p1.crossover(p2);
		System.out.println(offspring[0]);
		System.out.println(offspring[0].fitness());
		System.out.println(offspring[1]);
		System.out.println(offspring[1].fitness());
		
		Population p = new Population();
		for(int i=0; i < this.pop_size; i++) {
			Individual ind = new RoverCircuit(r);
			//System.out.println(ind);
			//System.out.println(ind.fitness());
			p.addIndividual(ind);
		}
		System.out.println(p.getBestFitness() + " "+ p.getAvgFitness() + " " +p.getWorstFitness());		
		System.out.println(p.getBestIndividual());

		//System.out.println(p.selectIndividual());
		//System.out.println(p.selectIndividual());
		
		GeneticAlgorithm ga = new GeneticAlgorithm(p,pcrossover,pmutate,gen_cap,elite);
		
		System.out.println("--------------------");
		Individual bestind = ga.search();
		System.out.println("Best individual found: "+ bestind);
		System.out.println("Best individual fitness: "+ bestind.fitness());
		
		      
	}

}
