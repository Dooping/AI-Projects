package circuit;

import java.util.Random;

import javax.swing.JFrame;

import org.jfree.chart.*;
import org.jfree.data.xy.*;
/**
 * Classe que "implementa" o algoritmo gen�tico
 */
public class GeneticAlgorithm {

	public final static int GEN_CAP = 2000;
	public static final float DEFAULT_P_CROSSOVER = 0.6f;
	public static final float DEFAULT_P_MUTATE = 0.01f;
	public static final int ELITE = 50;
	
	private static Random gen = new Random();
	private float pcrossover;
	private float pmutate;
	private int elite;
	private int gen_cap;
	private Population pop;

	/**
	 * Construtor
	 * @param pop uma popula��o
	 */
	GeneticAlgorithm(Population pop) {
		gen.setSeed(System.nanoTime());
		pcrossover = DEFAULT_P_CROSSOVER;
		pmutate = DEFAULT_P_MUTATE;
		elite = ELITE;
		gen_cap = GEN_CAP;
		this.pop = pop;
	}
	/**
	 * Construtor
	 * @param pop uma popula��o
	 * @param pcrossover a probabilidade de crossover
	 * @param pmutate a probabilidade de muta��o
	 */
	GeneticAlgorithm(Population pop, float pcrossover, float pmutate, int gen_cap, int elite) {
		gen.setSeed(System.nanoTime());
		this.pop = pop;
		this.pcrossover = pcrossover;
		this.pmutate = pmutate;
		this.elite = elite;
		this.gen_cap = gen_cap;
	}
	
	/**
	 * 	M�todo que pesquisa e devolve o melhor indiv�duo encontrado
	 * @return pop.getBestIndividual(), o melhor indiv�duo
	 */
	public Individual search() {
		Population newpop;
		Individual x,y;
		Individual[] children = new Individual[2];
		JFreeChart chart;
		XYSeriesCollection data = new XYSeriesCollection();
		XYSeries best = new XYSeries("Best fitness");
		XYSeries worst = new XYSeries("Worst fitness");
		XYSeries average = new XYSeries("Average fitness");
		
			for(int i = 0;i<gen_cap;i++){
				//writer.println(i+"	"+pop.getBestFitness());
				best.add(i,pop.getBestFitness());
				worst.add(i,pop.getWorstFitness());
				average.add(i,pop.getAvgFitness());
				newpop = pop.getElite(elite);
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
				pop.hillClimbing();
			}
			best.add(gen_cap,pop.getBestFitness());
			worst.add(gen_cap,pop.getWorstFitness());
			average.add(gen_cap,pop.getAvgFitness());
			data.addSeries(best);
			data.addSeries(worst);
			data.addSeries(average);
			chart = ChartFactory.createXYLineChart("p.cross: "+pcrossover*100+"% p.mutate: "+pmutate*100+"% elite: "+elite, "Generation", "Fitness", data);
			ChartPanel chartPanel = new ChartPanel(chart);
	        chartPanel.setPreferredSize(new java.awt.Dimension(600, 270));
	        chartPanel.setVisible(true);
	        JFrame frame = new JFrame("GeneticAlgorithm");
	        frame.add(chartPanel);
	        frame.pack();
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        frame.setVisible(true);
		return pop.getBestIndividual();		
	}
}
