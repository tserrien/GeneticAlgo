/**
 * Original intro are still available in the Practical2_code folder to cut down file length.
 *
 *
 * @author Jo Stevens
 * @version 1.0, 14 Nov 2008
 *
 * @author Alard Roebroeck
 * @version 1.1, 12 Dec 2012
 *
 * @author Andor Lindtner
 * @version 2.0 10 Dec 2019
 *
 */

import java.util.Random;

public class Main {

	static char[] alphabet = new char[27];

	/**
	 * @param args Control parameters for the program.
	 */
	public static void main(String[] args) {

		int popSize = 500;
		
		double goal = 0;		
		
		char[] tempChromosome = new char[Config.TARGET.length()];
		
		try{
			popSize = Integer.parseInt(args[0]);
			Config.setMutationChance(Integer.parseInt(args[1]));
			Config.setDebug(Boolean.parseBoolean(args[2]));
		}catch(Exception e){
			System.out.println("Input error. Default values used for simulation.");
		}

		Random rand = new Random();

		Individual[] population = new Individual[popSize];
		ChromosomeOperations CrO = new ChromosomeOperations();
		Random generator = new Random(System.currentTimeMillis());
		
		for (int i = 0; i < popSize; i++){
			population[i] = new Individual();
		}
		
		if(Config.mutationChance < 1){
			Config.setMutationChance(rand.nextInt(500)+50);
			System.out.println("Invalid mutation factor. Factor reset to: " + Config.mutationChance);
		}

		// we initialize the population with random characters
		for (int i = 0; i < popSize; i++){	
			population[i] = population[i].setIndividual(Config.TARGET.length());
			
			if(Config.debug && popSize > 3){
				if( i == popSize-2){
					population[i] = population[i].stringIndividual("HELLO ASDFG");
				}
				if( i == popSize-1){
					population[i] = population[i].stringIndividual(Config.TARGET);
				}
			}
		}
		
		for (int i = 0; i < population.length; i++) {
			population[i].setFitness(population[i]);		//fitness: euchlidean distance
			population[i].setFitness2(population[i]);		//fitness2: % of match compared to target
			if(Config.debug)System.out.println(population[i].genoToPhenotype() + " f1: " + population[i].getFitness() + " f2: " + population[i].getFitness2());
		}
		
		//sorting
		HeapSort.sort(population);
		goal = population.length * 0.7; //only top 30% is selected for reproduction. elitist approach

		if(Config.debug)System.out.println("Goal: " + goal);
		
		for(int i = 0; i < population.length; i++){
			//if(population[i].getFitness() > goal || population[i].getFitness2() > 0) potent++;  //faulty line
			if(Config.debug)System.out.println(population[i].genoToPhenotype() + " " + population[i].getFitness() + " " + population[i].getFitness2());
		}

		//crossover

	}
}