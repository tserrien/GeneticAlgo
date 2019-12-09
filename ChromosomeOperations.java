import java.util.Random;

public class ChromosomeOperations{
	/**
	 * Takes a specimen, and changes it's gene codes to bits
	 * @param specimen
	 * @return string of chromosome
	 */
	public static String ChromosomeToBits(Individual specimen){
		String chromosome = specimen.genoToPhenotype();
		String[] nucleotides = new String[chromosome.length()];			//array for the bits to be displayed
		
		for(int i = 0; i < chromosome.length(); i++){				//can't describe how much i hate java now
			//following line is courtesy of https://stackoverflow.com/a/12310078 modified to fit
			nucleotides[i] = String.format("%8s", Integer.toBinaryString(chromosome.charAt(i) & 0xFF)).replace(' ', '0');
			//System.out.println(chromosome.charAt(i) + " " + (byte)chromosome.charAt(i) + " " + bits[i]);
		}
		
		StringBuilder sb = new StringBuilder(chromosome.length()*9); //8 bits, +1 space
		
		for(int i = 0; i < nucleotides.length; i++){
			chromosome = sb.append(nucleotides[i] + " ").toString();
		}
		
		return chromosome;
	}

	/**
	 *
	 * @param chromosome
	 * @param reciprocal chance of mutation
	 * @return
	 */

	public static String Mutator(String chromosome, int reciprocal){
		Random rand = new Random();
		StringBuilder mutant = new StringBuilder(chromosome.length());
		//System.out.println("Unmodified:\n" + chromosome);
		
		for(int i = 0; i < chromosome.length(); i++){
			int chance = rand.nextInt(reciprocal);
			char current = chromosome.charAt(i);
			char space = ' ';
			mutant = mutant.append(chromosome.charAt(i));
			if( (chance == 0) && (current != space) ){
				char modified;
				if(current == '1'){
					modified = '0';
				}else{
					modified = '1';
				}				
				mutant.setCharAt(i, modified);
			}
		}		
		chromosome = mutant.toString();		
		return chromosome;
	}

	/**
	 * Method to create the genetic code for the offspring of two individuals
	 *
	 * @param parent1
	 * @param parent2
	 * @return offsring of two parent specimens. (String)
	 */
	
	public static String crossover(String parent1, String parent2){			//crossover method works both on bit, byte and char level
		//TODO full revision, this is a mess
		int len = parent1.length();
		int strength;
		String offspring = new String();	//chromosome coded in genes or nucleotids (depending on what was passed to the method)
		
		Random rand = new Random();
		StringBuilder offspringChromosome = new StringBuilder( len );
		
		for( int i = 0; i < len; i++){
			strength = rand.nextInt(len);
			if(strength > (len/2) ){
				offspringChromosome.append(parent1.charAt(i));
			}else{
				offspringChromosome.append(parent2.charAt(i));
			}
		}
		
		offspring = offspringChromosome.toString();
		
		return offspring;
	}

	public static Individual BitsToChromosome(String chromosome){
		Individual specimen = new Individual();
		
		return specimen;	
	}

	public void ChromosomeToBitsSomething(Individual specimen){ //messy code, function unclear atm
		String gene = specimen.genoToPhenotype();
		String[] bits = new String[gene.length()];
	/*
	byte tested = (byte) Integer.parseInt(args[0]);
	String s1 = String.format("%8s", Integer.toBinaryString(tested & 0xFF)).replace(' ', '0');
	System.out.println(s1);
	}
	*/
	}
}