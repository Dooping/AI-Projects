package circuit;

import java.util.Random;
/**
 * Classe que "implementa" o algoritmo gen�tico
 */
public class GeneticAlgorithm {

	private final static int CAP = 100;
	private static final float DEFAULT_P_CROSSOVER = 0.6f;
	private static final float DEFAULT_P_MUTATE = 0.001f;
	
	private static Random gen = new Random();
	private float pcrossover;
	private float pmutate;
	private Population pop;

	/**
	 * Construtor
	 * @param pop uma popula��o
	 */
	GeneticAlgorithm(Population pop) {
		gen.setSeed(System.currentTimeMillis());
		pcrossover = DEFAULT_P_CROSSOVER;
		pmutate = DEFAULT_P_MUTATE;
		this.pop = pop;
	}
	/**
	 * Construtor
	 * @param pop uma popula��o
	 * @param pcrossover a probabilidade de crossover
	 * @param pmutate a probabilidade de muta��o
	 */
	GeneticAlgorithm(Population pop, float pcrossover, float pmutate) {
		gen.setSeed(System.currentTimeMillis());
		this.pop = pop;
		this.pcrossover = pcrossover;
		this.pmutate = pmutate;
	}
	
	/**
	 * 	M�todo que pesquisa e devolve o melhor indiv�duo encontrado
	 * @return pop.getBestIndividual(), o melhor indiv�duo
	 */
	public Individual search() {
		Population newpop;
		Individual x,y;
		Individual[] children = new Individual[2];
		for(int i = 0;i<CAP;i++){
			newpop = new Population();
			for(int j = 0; j<pop.getSize()/2;j++){
				x=pop.selectIndividual();
				y=pop.selectIndividual();
				if (gen.nextFloat()<=pcrossover)
					children = x.crossover(y);
				else{
					children[0] = x;
					children[1] = y;
				}
				if(gen.nextFloat()<=pmutate)
					children[0].mutate();
				if(gen.nextFloat()<=pmutate)
					children[1].mutate();
				newpop.addIndividual(children[0]);
				newpop.addIndividual(children[1]);
			}
			pop=newpop;
		}
		return pop.getBestIndividual();		
	}
}
