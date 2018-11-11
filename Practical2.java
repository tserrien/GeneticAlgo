import java.util.*;

public class Practical2 {

	static final String TARGET = "HELLO WORLD";
	static char[] alphabet = new char[27];

	/**
	 * @param args
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
		
		if(mutationChanceReciprocal < 1){
			mutationChanceReciprocal = rand.nextInt(500)+50;
			System.out.println("Invalid mutation factor. Factor reset to: " + mutationChanceReciprocal);
		}
		
		for (char c = 'A'; c <= 'Z'; c++) {
			alphabet[c - 'A'] = c;
		}
		alphabet[26] =' ';
		
		for (int i = 0; i < popSize; i++){
			population[i] = new Individual();
		}
		
		// we initialize the population with random characters
		for (int i = 0; i < popSize; i++){	/*
			for (int j = 0; j < TARGET.length(); j++){
				tempChromosome[j] = alphabet[generator.nextInt(alphabet.length)]; //choose a random letter in the alphabet
			}
			*/
			if(debug && popSize > 3){
				if( i == popSize-2){
					population[i].StringIndividual("HELLO ASDFG");
				}
				if( i == popSize-1){
					population[i].StringIndividual(TARGET);
					//tempChromosome=new char[]{'H','E','L','L','O',' ','W','O','R','L','D'};
				}
			}
			
			population[i].setIndividual(TARGET.length());
			//population[i] = new Individual(tempChromosome);
		}
		
		// What does your population look like?
		
		for (int i = 0; i < population.length; i++) {
			population[i].setFitness(population[i].genoToPhenotype(), TARGET, debug);		//fitness: euchlidean distance
			population[i].setFitness2(population[i].genoToPhenotype(), TARGET, debug);		//fitness2: % of match
			if(debug)System.out.println(population[i].genoToPhenotype() + " " + population[i].getFitness() + " " + population[i].getFitness2());
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
		
		System.out.println("Goal: " + goal);
		
		System.out.println(CrO.Mutator(CrO.ChromosomeToBits(population[0]), mutationChanceReciprocal));
		
		//crossover
		for(int i = 0; i < population.length; i++){
			
		}
		

		// do your own cool GA here
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
	}

	public static int fact(int n){
		if (n==0)
			return 1;
		else
			return n*fact(n-1);
	}
}

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