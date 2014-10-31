package circuit;

import java.util.Random;

import javax.swing.JFrame;

import org.jfree.chart.*;
import org.jfree.data.xy.*;
/**
 * Classe que "implementa" o algoritmo gen�tico
 */
public class GeneticAlgorithm {

	private final static int GEN_CAP = 1000;
	private static final float DEFAULT_P_CROSSOVER = 0.6f;
	private static final float DEFAULT_P_MUTATE = 0.0001f;
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
		JFreeChart chart;
		XYSeriesCollection data = new XYSeriesCollection();
		XYSeries series = new XYSeries("");
		
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
				pop.hillClimbing();
			}
			series.add(GEN_CAP,pop.getBestFitness());
			data.addSeries(series);
			chart = ChartFactory.createXYLineChart("Evolution", "Generation", "Fitness", data);
			ChartPanel chartPanel = new ChartPanel(chart);
	        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
	        chartPanel.setVisible(true);
	        JFrame frame = new JFrame("GeneticAlgorithm");
	        frame.add(chartPanel);
	        frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setVisible(true);
		return pop.getBestIndividual();		
	}
}
