import java.util.*;

public class ChromosomeOperations{
	
	public String ChromosomeToBits(Individual specimen){		//takes a specimen, and changes it's gene codes to bits
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
	
	public String Mutator(String chromosome, int reciprocal){
		Random rand = new Random();
		StringBuilder mutant = new StringBuilder(chromosome.length());
		//System.out.println("Unmodified:\n" + chromosome);
		
		for(int i = 0; i < chromosome.length(); i++){
			int chance = rand.nextInt(reciprocal);
			char current = chromosome.charAt(i);
			char space = ' ';								//debugging lvl desperate... but works :D
			mutant = mutant.append(chromosome.charAt(i));
			if( (chance == 0) && (current != space) ){		//hate++ https://imgflip.com/i/2m8lhr
				char modified;
				if(current == '1'){							//forcecasting didn't work :(
					modified = '0';							//ugly but works
				}else{
					modified = '1';
				}				
				mutant.setCharAt(i, modified);
			}
		}		
		chromosome = mutant.toString();		
		return chromosome;
	}
	
	public String crossover(String parent1, String parent2){			//crossover method works both on bit, byte and char level
		
		int len = parent1.length();										//for convinience and more readable code
		int strength;													//determines which parent's gene/nucleotid is stronger for the inheritance
		String offspring = new String();												//chromosome coded in genes or nucleotids (depending on what was passed to the method)
		
		Random rand = new Random();
		StringBuilder offspringChromosome = new StringBuilder( len );
		
		for( int i = 0; i < len; i++){
			strength = rand.nextInt(len);
			if(strength > (len/2) ){									//if the random based on the chromosomelenght of the parents is higher than the half, first parent passes down genes
				offspringChromosome.append(parent1.charAt(i));			//needs manual recoding to give priority to a parent, may do it later for fun
			}else{
				offspringChromosome.append(parent2.charAt(i));
			}
		}
		
		offspring = offspringChromosome.toString();
		
		return offspring;
		//returns string, as it can easily be processed by mutator method
	}
	
	/*
	public static Individual BitsToChromosome(String chromosome){
		Induvidual specimen = new Individual();
		
		return specimen;	
	}
	*/
}