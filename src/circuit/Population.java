package circuit;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
/**
 * 	Classe usada para a representa��o de uma popula��o.
 */
public class Population {
	
	private final static int CAP = 100;
	private static Random gen = new Random();
	private double sumOfFitness;
	private int size;
	private boolean currupt;
	private Individual bestInd, worstInd;
	private double bestFit, worstFit;
	private ArrayList<Individual> pop;
	private ArrayList<Double> acum;
	
	/**
	 * 	Construtor relativo � classe Population
	 */
	public Population(){
		this.size = 0;
		this.pop = new ArrayList <Individual>(CAP);
		this.acum = new ArrayList <Double>(CAP);
		this.sumOfFitness = 0.0;
		this.currupt = true;
		this.bestInd = null;
		this.worstInd = null;
		this.bestFit = Double.POSITIVE_INFINITY;
		this.worstFit = Double.NEGATIVE_INFINITY;
		gen.setSeed(System.nanoTime());
	}
	
	/**
	 * 	Construtor onde se especifica a popola��o
	 * @param p um array de indiv�duos
	 */
	public Population(Individual[] pop){
		this();
		for(Individual i:pop)
			addIndividual(i);
	}
	
	/**
	 * Selecciona e devolve um indiv�duo da popula��o, tendo em conta a sua fitness
	 * @return um array de indiv�duos
	 */
	public Individual selectIndividual() {
		//está a implementar o método da roleta
		// Verifica se necessita de calcular os valores de probabilidade de selec��o de cada indiv�duo
		if( currupt ) {
			acum.clear();
			double total=0.0;
			for(int i=0; i < pop.size(); i++) {
				total += 1/pop.get(i).fitness();
				acum.add(total/sumOfFitness);
			}
			currupt = false;
		}
		
		double r = gen.nextDouble();
		int pos = Collections.binarySearch(acum, r);
		
		if( pos >= 0)
			return pop.get(pos%size);
		else
			return pop.get(-(pos+1)%size);
		
	}
	/**
	 * Adiciona um indiv�duo � popula��o
	 * @param ind, um indiv�duo
	 */
	public void addIndividual(Individual ind) {
		size++;
		pop.add(ind);
		double f = ind.fitness();
		sumOfFitness += 1/f; 
		if( f > worstFit ) {
			worstFit = f;
			worstInd = ind;
		}
		if( f < bestFit ) {
			bestFit = f;
			bestInd = ind;
		}
	}
	
	public Population getElite(int n){
		Population newpop = new Population();
		Collections.sort(pop);
		if (n>size)
			n=size;
		for (int i=0;i<n;i++)
			newpop.addIndividual((Individual)pop.get(i).clone());
		return newpop;
	}

	public double getAvgFitness() {
		double avg = 0.0;
		for (Individual i:pop)
			avg += i.fitness();
			
		return avg/size;
	}

	public double getWorstFitness() {
		return worstFit;
	}

	public double getBestFitness() {
		return bestFit;
	}

	public Individual getBestIndividual() {
		return bestInd;
	}
	
	public int getSize(){
		return size;
	}
	
	public void hillClimbing(){
		Collections.sort(pop);
		boolean changed = true;
		Individual []children;
		while(changed){
			children = bestInd.crossover(this.selectIndividual());
			changed = false;
			for (int i=0;i<children.length;i++)
				if (children[i].fitness()<bestInd.fitness()){
					bestInd = children[i];
					pop.set(0, bestInd);
					changed = true;
					currupt = true;	
				}
		}
	}
	

}
