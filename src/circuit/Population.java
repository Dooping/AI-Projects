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
			return pop.get(pos);
		else
			return pop.get(-(pos+1));
		
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
		return null;
	}

	public int getAvgFitness() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getWorstFitness() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getBestFitness() {
		// TODO Auto-generated method stub
		return null;
	}

	public char[] getBestIndividual() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
