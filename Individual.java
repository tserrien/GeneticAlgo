import java.util.Random;
import java.text.DecimalFormat;

public class Individual {

	private char[] alphabet = new char[27];
	private int generation = 0;
	private String parent1 = "";
	private String parent2 = "";
	private boolean isAlive = true;
	public String chromo;

	private char[] chromosome;
	private double fitness;
	private double fitness2;
	private int maxDistance = 25;

	//empty constructor
	public Individual(){
	}

	//original constructor
	public Individual(char[] chromosome) {
		StringBuilder sb = new StringBuilder();
		this.chromosome = chromosome;
		this.chromo = sb.append(chromosome).toString();
		this.fitness = 0;
	}
/*
	//Original gTPt. Technically a toString method.
	//Decided not to rename, as I plan to create a different toString
	public String genoToPhenotype() {
		StringBuilder builder = new StringBuilder();
		builder.append(chromosome);
		return builder.toString();
	}*/

	//Mostly matches original
	public Individual setIndividual(int len){

		char[] tempChromosome = new char[len];

		Random generator = new Random();				//the system time binded version caused uniform individuals for some weird reason

		for (char c = 'A'; c <= 'Z'; c++) {
			alphabet[c - 'A'] = c;
		}
		alphabet[26] = ' ';

		for (int j = 0; j < len; j++){
			tempChromosome[j] = alphabet[generator.nextInt(alphabet.length)]; //choose a random letter in the alphabet
		}

		StringBuilder sb = new StringBuilder(len);
		sb.append(tempChromosome);
		this.chromo = sb.toString();

		return new Individual(tempChromosome);
	}

	public Individual stringIndividual(String manual){
		this.chromo = manual;
		char[] painrelief = new char[manual.length()];
		for(int i = 0; i < manual.length(); i++){
			painrelief[i] = manual.charAt(i);
		}
		return new Individual(painrelief);
	}

	public void setFitness() {
		for(int i = 0; i<chromo.length(); i++){
			byte a, b, space;
			a = (byte)chromo.charAt(i);
			b = (byte)Config.TARGET.charAt(i);
			space = (byte)' ';
			if( (b!=' ' && a!=' ') || (a==' ' && b==' ')){

				int distance = Math.abs(a - b);

				fitness += distance;
			}else{

				int distance = Math.abs(a - b) - space;

				fitness += distance;
			}
		}
		fitness = 1 - fitness/(Config.TARGET.length()*maxDistance);
		this.fitness = fitness;
	}

	public void setFitness2() {
		boolean equals;

		for(int i = 0; i<chromo.length(); i++){
			byte a, b;
			a = (byte)chromo.charAt(i);
			b = (byte)Config.TARGET.charAt(i);
			equals = a==b;
			//if(Config.debug)System.out.println(chromo.charAt(i) + " " + equals + " " + Config.TARGET.charAt(i));
			if(equals) fitness2++;
		}
		fitness2 = fitness2/(Config.TARGET.length());
		this.fitness2 = fitness2;
	}

	public static String toString(Individual specimen){
		if(specimen == null) return "null";

		StringBuilder sb = new StringBuilder();
		DecimalFormat df = new DecimalFormat("#.####");

		sb.append(specimen.chromo);
		sb.append(", f: ");
		sb.append(df.format(specimen.getFitness()));
		sb.append(", Generation: ");
		sb.append(specimen.generation);
		sb.append(", Parent1: ");
		sb.append(specimen.parent1);
		sb.append(", Parent2: ");
		sb.append(specimen.parent2);

		return sb.toString();
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

	public void setParents(String parent1, String parent2){
		this.parent1 = parent1;
		this.parent2 = parent2;
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
