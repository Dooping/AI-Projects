package circuit;

import java.util.Random;

import org.jfree.chart.*;
import org.jfree.data.xy.*;
/**
 * Classe que "implementa" o algoritmo gen�tico
 */
public class GeneticAlgorithm {

	private final static int GEN_CAP = 1000;
	private static final float DEFAULT_P_CROSSOVER = 0.6f;
	private static final float DEFAULT_P_MUTATE = 0.001f;
	private static final int ELITE = 20;
	
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
		//PrintWriter writer;
		//try {
			//writer = new PrintWriter("data.xml", "UTF-8");
		JFreeChart chart;
		XYSeriesCollection data = new XYSeriesCollection();
		XYSeries series = new XYSeries("asdjh");
		
			for(int i = 0;i<GEN_CAP;i++){
				//writer.println(i+"	"+pop.getBestFitness());
				series.add(i,pop.getBestFitness());
				newpop = pop.getElite(ELITE);
				while(newpop.getSize()<pop.getSize()){
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
			series.add(GEN_CAP,pop.getBestFitness());
			data.addSeries(series);
			chart = ChartFactory.createXYLineChart("chart", "Generation", "Fitness", data);
			ChartPanel chartPanel = new ChartPanel(chart);
	        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
	        //setContentPane(chartPanel);
			//data = = new XYSeriesCollection();
			//writer.println(GEN_CAP+"	"+pop.getBestFitness());
			
			//writer.close();

		//} catch (FileNotFoundException | UnsupportedEncodingException e) {
		//	e.printStackTrace();
		//}
		return pop.getBestIndividual();		
	}
}
