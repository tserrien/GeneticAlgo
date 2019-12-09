import java.util.Random;

public class Individual {
	
	private char[] alphabet = new char[27];
	private int generation;
	char[] chromosome;
	double fitness;
	double fitness2;
	int maxDistance = 25;
	
	public Individual(){								//empty constructor
	}
	
	public Individual(char[] chromosome) {				//original constructor
		this.chromosome = chromosome;
		this.fitness = 0;
	}
	
	public String genoToPhenotype() {					//original gTP
		StringBuilder builder = new StringBuilder();
		builder.append(chromosome);
		return builder.toString();
	}
	
	public Individual clone() {							//original Clone
		char[] chromClone = new char[chromosome.length];
		for(int i = 0; i < chromClone.length; i++) {
			chromClone[i] = chromosome[i];
		}
		return new Individual(chromClone);
	}
	
	public Individual setIndividual(int len){			//took it out for a cleaner code, mostly matches the original
	
		char[] tempChromosome = new char[len];
		
		Random generator = new Random();				//the system time binded version caused uniform individuals for some weird reason
		
		for (char c = 'A'; c <= 'Z'; c++) {
			alphabet[c - 'A'] = c;
		}
		alphabet[26] = ' ';
		
		for (int j = 0; j < len; j++){
			tempChromosome[j] = alphabet[generator.nextInt(alphabet.length)]; //choose a random letter in the alphabet
		}
		return new Individual(tempChromosome);
	}
	
	public Individual StringIndividual(String manual){
		char[] painrelief = new char[manual.length()];
		for(int i = 0; i < manual.length(); i++){
			painrelief[i] = manual.charAt(i);
		}
		return (new Individual(painrelief));
	}
	
	public void setFitness(String DNA, String target, boolean debug) {
		
		for(int i = 0; i<DNA.length(); i++){
			byte a, b, space;
			a = (byte)DNA.charAt(i);
			b = (byte)target.charAt(i);
			space = (byte)' ';
			if( (b!=' ' && a!=' ') || (a==' ' && b==' ')){
			//if(debug)System.out.println(DNA.charAt(i) + " " + a + " " + target.charAt(i) + " " + b);
			int distance = Math.abs(a - b);
			//if(debug)System.out.println(distance);
			fitness += distance;
			}else{
			//if(debug)System.out.println(DNA.charAt(i) + " " + a + " " + target.charAt(i) + " " + b);
			int distance = Math.abs(a - b) - space;
			//if(debug)System.out.println(distance);
			fitness += distance;	
			}
		}
		fitness = 1 - fitness/(target.length()*maxDistance);
		this.fitness = fitness;
	}

	//so far only a testing feature, though this seems to be more useful
	public void setFitness2(String DNA, String target, boolean debug) {
		boolean e;
		
		for(int i = 0; i<DNA.length(); i++){
			byte a, b;
			a = (byte)DNA.charAt(i);
			b = (byte)target.charAt(i);
			e = a==b;
			//if(debug)System.out.println(DNA.charAt(i) + " " + e + " " + target.charAt(i));
			if(e) fitness2++;
		}
		fitness2 = fitness2/(target.length());
		this.fitness2 = fitness2;
	}
	
	public char[] getChromosome() {
		return chromosome;
	}

	public void setChromosome(char[] chromosome) {
		this.chromosome = chromosome;
	}

	public double getFitness() {
		return fitness;
	}
	
	public double getFitness2() {
		return fitness2;
	}

	public void setGeneration(int generation){
		this.generation = generation;
	}

	public int getGeneration(){
		return generation;
	}
}
