/**
 * Some very basic stuff to get you started. It shows basically how each
 * chromosome is built.
 *
 * @author Jo Stevens
 * @version 1.0, 14 Nov 2008
 *
 * @author Alard Roebroeck
 * @version 1.1, 12 Dec 2012
 *
 */

/**
 * Some general programming remarks and hints:
 * - A crucial point is to set each individual's fitness (by the setFitness() method) before sorting. When is an individual fit?
 * 	How do you encode that into a double (between 0 and 1)?
 * - Decide when to stop, that is: when the algorithm has converged. And make sure you  terminate your loop when it does.
 * - print the whole population after convergence and print the number of generations it took to converge.
 * - print lots of output (especially if things go wrong).
 * - work in an orderly and structured fashion (use tabs, use methods,..)
 * - DONT'T make everything private. This will only complicate things. Keep variables local if possible
 * - A common error are mistakes against pass-by-reference (this means that you pass the
 * 	address of an object, not a copy of the object to the method). There is a deepclone method included in the
 *  Individual class.Use it!
 * - You can compare your chromosome and your target string, using for eg. TARGET.charAt(i) == ...
 * - Check your integers and doubles (eg. don't use ints for double divisions).
 */
import java.util.Random;

public class Main {

	static final String TARGET = "HELLO WORLD";
	static char[] alphabet = new char[27];

	/**
	 * @param args Control parameters for the program.
	 */
	public static void main(String[] args) {
		
		int potent = 0;
		int popSize = 500;
		int mutationChanceReciprocal = 300000;
		
		double goal = 0;		
		
		char[] tempChromosome = new char[TARGET.length()];
		
		boolean debug = false;
		
		try{
			popSize = Integer.parseInt(args[0]);
			mutationChanceReciprocal = Integer.parseInt(args[1]);
			debug = Boolean.parseBoolean(args[2]);
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
		
		if(mutationChanceReciprocal < 1){
			mutationChanceReciprocal = rand.nextInt(500)+50;
			System.out.println("Invalid mutation factor. Factor reset to: " + mutationChanceReciprocal);
		}

		// we initialize the population with random characters
		for (int i = 0; i < popSize; i++){	
			population[i] = population[i].setIndividual(TARGET.length());
			population[i].setGeneration(0);
			
			if(debug && popSize > 3){
				if( i == popSize-2){
					population[i] = population[i].StringIndividual("HELLO ASDFG");
				}
				if( i == popSize-1){
					population[i] = population[i].StringIndividual(TARGET);
				}
			}
		}

		// What does your population look like?
		//TODO store generation and parent info
		
		for (int i = 0; i < population.length; i++) {
			population[i].setFitness(population[i].genoToPhenotype(), TARGET, debug);		//fitness: euchlidean distance
			population[i].setFitness2(population[i].genoToPhenotype(), TARGET, debug);		//fitness2: % of match compared to target
			//if(debug)
				System.out.println(population[i].genoToPhenotype() + " " + population[i].getFitness() + " " + population[i].getFitness2());
		}
		System.out.println("\n\n");
		
		//sorting
		HeapSort.sort(population);
		goal = population.length * 0.7; //only top 30% is selected for reproduction
		//preferred specimens have higher fitness2 regardless the original fitness
		
		for(int i = 0; i < population.length; i++){
			if(population[i].getFitness() > goal || population[i].getFitness2() > 0) potent++;
			if(debug)System.out.println(population[i].genoToPhenotype() + " " + population[i].getFitness() + " " + population[i].getFitness2());
		}
		
		if(debug)System.out.println("Goal: " + goal);
		
		//crossover
		/*
		System.out.println( "\nParent 1:\n" + population[0].genoToPhenotype() + "\nParent 2:\n" + population[popSize-1].genoToPhenotype() + "\nOffspring:\n" + CrO.crossover(population[0].genoToPhenotype(), population[popSize-1].genoToPhenotype()) );
		
		System.out.println( "\nParent 1:\n" + population[0].genoToPhenotype() + "\nParent 2:\n" + population[popSize-1].genoToPhenotype() + "\nOffspring:\n" + CrO.ChromosomeToBits( population[0] ) + "\n" + CrO.ChromosomeToBits( population[popSize-1] ) + "\n" +
		CrO.crossover( CrO.ChromosomeToBits( population[0] ), CrO.ChromosomeToBits( population[popSize-1]) ) );
		*/
		
		for(int i = 0; i < population.length; i++){
			
		}
	}
}