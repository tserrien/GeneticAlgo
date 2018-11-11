import java.util.*;

public class ChromosomeOperations{
	
	public String ChromosomeToBits(Individual specimen){		//takes a specimen, and changes it's gene codes to bits
		String chromosome = specimen.genoToPhenotype();
		String[] bits = new String[chromosome.length()];			//array for the bits to be displayed
		
		for(int i = 0; i < chromosome.length(); i++){				//can't describe how much i hate java now
			//following line is courtesy of https://stackoverflow.com/a/12310078 modified to fit
			bits[i] = String.format("%8s", Integer.toBinaryString(chromosome.charAt(i) & 0xFF)).replace(' ', '0');
			System.out.println(chromosome.charAt(i) + " " + (byte)chromosome.charAt(i) + " " + bits[i]);
		}
		
		StringBuilder sb = new StringBuilder(chromosome.length()*9); //8 bits, +1 space
		
		for(int i = 0; i < bits.length; i++){
			chromosome = sb.append(bits[i] + " ").toString();
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
			char space = ' ';								//debugging lvl desperate
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
	
	/*
	public static Individual BitsToChromosome(String chromosome){
		Induvidual specimen = new Individual();
		
		return specimen;	
	}
	*/
}