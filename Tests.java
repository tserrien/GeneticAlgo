/**
 * Test method to keep Main clutterfree
 */

import java.util.Random;

public class Tests{

    public static void main(String args[]){

        Config.setDebug(true);

        int popSize = 500;

        try{
            popSize = Integer.parseInt(args[0]);
            Config.setMutationChance(Integer.parseInt(args[1]));
            Config.setDebug(Boolean.parseBoolean(args[2]));
        }catch(Exception e){
            System.out.println("Input error. Default values used for test.");
        }

        Individual[] population = new Individual[popSize];
        ChromosomeOperations CrO = new ChromosomeOperations();
        Random generator = new Random(System.currentTimeMillis());

        Individual test1 = new Individual();
        Individual test2 = new Individual();
        Individual offspring = new Individual();

        test1 = test1.stringIndividual(Config.TARGET);
        test2 = test2.stringIndividual("ALIVEINDEED");

        offspring = CrO.uniformCrossover(test1, test2);
        System.out.println("Uniform crossover offspring: " + offspring.genoToPhenotype() + " " + offspring.getAlive());

        offspring = CrO.averageCrossover(test1, test2);
        System.out.println("Average crossover offspring: " + offspring.genoToPhenotype() + " " + offspring.getAlive());

        test1 = test1.setIndividual(Config.TARGET.length());
        test2 = test2.setIndividual(Config.TARGET.length());

        offspring = CrO.uniformCrossover(test1, test2);
        System.out.println("Uniform crossover offspring: " + offspring.genoToPhenotype() + " " + offspring.getAlive());

        offspring = CrO.averageCrossover(test1, test2);
        System.out.println("Average crossover offspring: " + offspring.genoToPhenotype() + " " + offspring.getAlive());

        Individual test = new Individual();
        test = test.stringIndividual(Config.TARGET);
        for(int i = 0; i < 15; i++) {
           test = CrO.mutation(test);
           System.out.println(i + " " +test.genoToPhenotype() + " " + test.getAlive());
        }
    }
}