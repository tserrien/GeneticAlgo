import java.util.Random;

public class Individual {
	
	private char[] alphabet = new char[27];
	private int generation = 0;
	private String parent1 = "";
	private String parent2 = "";
	private boolean isAlive = true;

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
	
	public Individual stringIndividual(String manual){
		char[] painrelief = new char[manual.length()];
		for(int i = 0; i < manual.length(); i++){
			painrelief[i] = manual.charAt(i);
		}
		return new Individual(painrelief);
	}
	
	public void setFitness(Individual specimen) {
		String DNA = specimen.genoToPhenotype();
		for(int i = 0; i<DNA.length(); i++){
			byte a, b, space;
			a = (byte)DNA.charAt(i);
			b = (byte)Config.TARGET.charAt(i);
			space = (byte)' ';
			if( (b!=' ' && a!=' ') || (a==' ' && b==' ')){
			//if(Config.debug)System.out.println(DNA.charAt(i) + " " + a + " " + target.charAt(i) + " " + b);
			int distance = Math.abs(a - b);
			//if(Config.debug)System.out.println(distance);
			fitness += distance;
			}else{
			//if(Config.debug)System.out.println(DNA.charAt(i) + " " + a + " " + Config.TARGET.charAt(i) + " " + b);
			int distance = Math.abs(a - b) - space;
			//if(Config.debug)System.out.println(distance);
			fitness += distance;	
			}
		}
		fitness = 1 - fitness/(Config.TARGET.length()*maxDistance);
		this.fitness = fitness;
	}

	public void setFitness2(Individual specimen) {
		String DNA = specimen.genoToPhenotype();
		boolean equals;
		
		for(int i = 0; i<DNA.length(); i++){
			byte a, b;
			a = (byte)DNA.charAt(i);
			b = (byte)Config.TARGET.charAt(i);
			equals = a==b;
			//if(Config.debug)System.out.println(DNA.charAt(i) + " " + equals + " " + Config.TARGET.charAt(i));
			if(equals) fitness2++;
		}
		fitness2 = fitness2/(Config.TARGET.length());
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

	public void setParent1(Individual parent1){
		this.parent1 = parent1.genoToPhenotype();
	}

	public void setParent2(Individual parent2){
		this.parent1 = parent2.genoToPhenotype();
	}

	public String getParent1(){
		return parent1;
	}

	public String getParent2(){
		return parent2;
	}

	public boolean getAlive(){
	    return isAlive;
    }

    public void setAlive(boolean alive){
	    this.isAlive = alive;
    }
}
