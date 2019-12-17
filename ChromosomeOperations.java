import java.util.Random;

public class ChromosomeOperations{
	/** Mutation method with full chromosome to nucleotid, nucleotid to chromosome rebuilding.
	 *
	 * @param specimen to perform mutation check on
	 * @return mutated specimen
	 */
	public static Individual mutation(Individual specimen){
		String chromosomeBits = chromosomeToBits(specimen.chromo);
		boolean mutated = false;
		Random rand = new Random();
		StringBuilder mutant = new StringBuilder(chromosomeBits.length());

		if(Config.debug)System.out.println("Unmodified: " + specimen.chromo);

		for(int i = 0; i < chromosomeBits.length(); i++){
			int chance = rand.nextInt(Config.mutationChance);
			char current = chromosomeBits.charAt(i);

			mutant = mutant.append(current);

			if (chance == 0){
				mutated = true;
				if(current == '1'){
					mutant.setCharAt(i, '0');
				}else{
					mutant.setCharAt(i, '1');
				}
			}
		}
		chromosomeBits = mutant.toString();
		if(Config.debug && mutated)System.out.println("Mutated: " + chromosomeBits);
		specimen = bitsToChromosome(chromosomeBits);
		return specimen;
	}

	/**
	 * Uniform crossover method for two parent setup. Yields in a great amount of "stillborn" or invalid offsprings in
	 * bit level crossover, the "nucleotid" level gives valid offsring only. The algorithm was selected based on this paper is flagged under.
	 * Choice further explained in accompanying report.
	 *
	 *
	 * @param parent1 string of chromosome
	 * @param parent2 string of chromosome
	 * @return offspring, Individual type
	 *
	 * @see <a href="https://www.researchgate.net/publication/288749263_CROSSOVER_OPERATORS_IN_GENETIC_ALGORITHMS_A_REVIEW">Source</a>
	 */

	public static Individual uniformCrossover(String parent1, String parent2){

		int len = parent1.length();
		int strength;

		Individual offspring = new Individual();

		Random rand = new Random();
		StringBuilder offspringChromosome = new StringBuilder( len );

		for( int i = 0; i < len; i++){
			//could have been a simple coin toss as well as this is just an overcomplicated 50/50 decision
			strength = rand.nextInt(len);
			if(strength > (len/2) ){
				offspringChromosome.append(parent1.charAt(i));
			}else{
				offspringChromosome.append(parent2.charAt(i));
			}
		}

		offspring = mutation(offspring.stringIndividual(offspringChromosome.toString()));
		offspring.setParents(parent1, parent2);
		return (offspring);
	}

	/** Average crossover method. Was added to compare runtime to uniform crossover.
	 *
	 *
	 * @param parent1 Individual type
	 * @param parent2 Individual type
	 * @return Individual offspring
	 *
	 * @see <a href="https://www.researchgate.net/publication/288749263_CROSSOVER_OPERATORS_IN_GENETIC_ALGORITHMS_A_REVIEW">Source</a>
	 */
	public static Individual averageCrossover(String parent1, String parent2){
		Individual offspring = new Individual();

		byte[] averageOfParents = new byte[parent1.length()];
		byte[] parent1Byte = parent1.getBytes();
		byte[] parent2Byte = parent2.getBytes();

		for(int i = 0; i < averageOfParents.length; i++){
			averageOfParents[i] = (byte) Math.round( (parent1Byte[i]+parent2Byte[i])/2	);
		}
		if(Config.debug)System.out.println(new String(averageOfParents));
		offspring = mutation(offspring.stringIndividual(new String(averageOfParents)));
		offspring.setParents(parent1, parent2);
		return offspring;
	}

	/**
	 * Takes a specimen, and changes it's gene codes to bits
	 * @param chromosome string of chromosome
	 * @return string of nucleotids
	 */
	private static String chromosomeToBits(String chromosome){

		String[] nucleotides = new String[chromosome.length()];			//array for the bits to be displayed

		for(int i = 0; i < chromosome.length(); i++){
			//following line is courtesy of https://stackoverflow.com/a/12310078 modified to fit
			nucleotides[i] = String.format("%8s", Integer.toBinaryString(chromosome.charAt(i) & 0xFF)).replace(' ', '0');
		}

		StringBuilder sb = new StringBuilder(chromosome.length()*8); //8 bits

		for(int i = 0; i < nucleotides.length; i++){
			chromosome = sb.append(nucleotides[i]).toString();
		}

		return chromosome;
	}

	/**
	 * Method to rebuild the chromosome from nucleotids given in bit representation
	 * @param chromosome string of bits
	 * @return chromosome of individual
	 */
	private static Individual bitsToChromosome(String chromosome){
		byte[] bitToByte = new byte[(int)(chromosome.length()/8)];
		boolean valid = true;

		Individual specimen = new Individual();

		for(int i = 0; i < chromosome.length(); i+=8) {
			StringBuilder sb = new StringBuilder(8); //to store only one byte in bit representation
			int step = (int) (i / 8);

			for (int j = 0; j < 8; j++) {
				sb.append(chromosome.charAt(i + j));
				//there must be a less hassle-free solution for this, but i'm not comfortable with bit operations
				if (j != 0) bitToByte[step] += (byte)(Character.getNumericValue(chromosome.charAt(i + j )) * (int)Math.pow(2 , 8 - ( j + 1)));
			}

			if(!((bitToByte[step] >= 65 && bitToByte[step] <= 90) || bitToByte[step] == 32)){
				valid = false;
				break;
			}
		}

		if(Config.debug)System.out.println(new String(bitToByte));

		if(valid){
			specimen = specimen.stringIndividual(new String(bitToByte));
		}else{
			specimen = specimen.stringIndividual("Fatal mutation");
		}

		specimen.setAlive(valid);

		return specimen;
	}
}