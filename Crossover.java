import java.lang.StringBuilder;
import java.util.Random;

public class Crossover{
    /** Average crossover method.
     *
     *
     * @param parent1 Individual type
     * @param parent2 Individual type
     * @return Individual offspring
     *
     * @see <a href="https://www.researchgate.net/publication/288749263_CROSSOVER_OPERATORS_IN_GENETIC_ALGORITHMS_A_REVIEW">Source</a>
     */
    public static Individual Average(String parent1, String parent2){
        Individual offspring = new Individual();

        byte[] averageOfParents = new byte[parent1.length()];
        byte[] parent1Byte = parent1.getBytes();
        byte[] parent2Byte = parent2.getBytes();

        for(int i = 0; i < averageOfParents.length; i++){
            averageOfParents[i] = (byte) Math.round( (parent1Byte[i]+parent2Byte[i])/2	);
        }
        if(Config.debug)System.out.println(new String(averageOfParents));
        offspring = ChromosomeOperations.mutation(offspring.stringIndividual(new String(averageOfParents)));
        offspring.setParents(parent1, parent2);
        return offspring;
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

    public static Individual Uniform(String parent1, String parent2){

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

        offspring = ChromosomeOperations.mutation(offspring.stringIndividual(offspringChromosome.toString()));
        offspring.setParents(parent1, parent2);
        return offspring;
    }

    /**One point crossover method.
     *
     * @param parent1
     * @param parent2
     * @return Individual type offspring
     *
     * @see <a href="https://www.researchgate.net/publication/288749263_CROSSOVER_OPERATORS_IN_GENETIC_ALGORITHMS_A_REVIEW">Source</a>
     */
    public static OnePoint(String parent1, String parent2){
        StringBuilder offspringChromosome = new StringBuilder(parent1.length());
        Individual offpsring = new Individual();
        Random rand = new Random();

        int cutPoint = rand.nextInt(parent1.length());
        for(int i = 0; i < cutPoint; i++){
            offspringChromosome.append(parent1.charAt(i));
        }
        for(int i = cutPoint; i < parent1.length(); i++){
            offspringChromosome.append(parent2.charAt(i));
        }
        offspring = ChromosomeOperations.mutation(offspring.stringIndividual(new String(averageOfParents)));
        offspring.setParents(parent1, parent2);
        return offspring;
    }

    /**Shuffle crossover method. Shuffles the genes of both parents, performs one point crossover and deshuffles
     *
     * @param parent1
     * @param parent2
     * @return Individual type offspring
     *
     * @see <a href="https://www.researchgate.net/publication/288749263_CROSSOVER_OPERATORS_IN_GENETIC_ALGORITHMS_A_REVIEW">Source</a>
    */

    public static Shuffle(String parent1, String parent2){
        Individual offpsring = new Individual();
        Individual tempOffpsring = new Individual();
        Random rand = new Random();

        int[][] shuffleArray = new int[parent1.length()][2];

        for(int i = 0; i < parent1.length(); i++){
            shuffleArray[i][0] = i;
            shuffleArray[i][1] = rand.nextInt(parent1.length());
        }

        tempOffspring = OnePoint(shuffleHelper(parent1, shuffleArray), shuffleHelper(parent2, shuffleArray));


        return offspring;
    }

    private static String shuffleHelper(String parent, int[][] shuffleArray){
        StringBuilder sb = new StringBuilder;

        for(int i = 0; i < parent.length(); i++) {
            sb.append(parent.charAt(shuffleArray[i][1]));
        }
        return sb.toString();
    }

    //TODO finish
    private static deShuffle(String chromosome, int[][] shuffleArray){
        StringBuilder sb = new StringBuilder;
        char temp = '';

        for(int i = chromosome.length(); i >= 0 ; i--){
            temp = chromosome.charAt(i);

            sb.setCharAt();
        }

        return sb.toString();
    }

}