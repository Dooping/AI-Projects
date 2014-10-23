package circuit;

/**
 * 	Classe abstracta para representar um indiv�duo da popula��o.
 */
public abstract class Individual implements Comparable<Individual>{
	/**
	 * 	fitness: representa a "abilidade"/adequabilidade do indiv�duo para "resolver" o problema, 
	 * isto �, o custo que se pretende o menor poss�vel
	 * @return fitness
	 */
	public abstract double fitness();
	/**
	 * m�todo abstracto que cruza dois indiv�duos e gera um array de indiv�duos.
	 * @return Individual[], array de indiv�duos
	 */
	public abstract Individual[] crossover(Individual other);
	/**
	 * 	M�todo abstracto que opera uma muta��o num indiv�duo.
	 */
	public abstract void mutate();
	
	public abstract Object clone();
	
}
