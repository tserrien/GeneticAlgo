import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Collections;

public class Selection{

    /** Recursive, array based volatile implementation of elitist selection.
     *
     * @param previousGeneration
     * @param generationCount
     * @return Array of Individuals, unsorted
     */
    public static Individual[] elitistSelection(Individual[] previousGeneration, int generationCount) {
        long start = System.nanoTime();
        long end = 0;

        //if(Config.debug)
            System.out.println(Individual.toString(previousGeneration[0]) + ", Gen: " + generationCount + ", members: " + previousGeneration.length);

        //base case of recursion. may be moved elsewhere
        if (previousGeneration[0].getFitness() == 1) return previousGeneration;

        //if basecase fails, work begans
        double minFitness = previousGeneration[0].getFitness() * (1 - Config.elitePercent);

        int breederCount = 0;
        //TODO merge loops
        for(int i = 0; i < previousGeneration.length; i++) {
            //System.out.println(previousGeneration[i].chromo);
            if( previousGeneration[i].getFitness() > minFitness){
                for (int j = i + 1; j < previousGeneration.length; j++) {
                    //eliminate duplicates, keep first entry
                    if (previousGeneration[j] != null && previousGeneration[j].getFitness() > minFitness && previousGeneration[i].chromo.compareTo(previousGeneration[j].chromo) == 0) previousGeneration[j].setAlive(false);
                }
            }
        }

        for (int i = 0; i < previousGeneration.length; i++) {
            if ( previousGeneration[i].getAlive() && previousGeneration[i].getFitness() > minFitness && (generationCount - previousGeneration[i].getGeneration()) < Config.lifeTime) {
                breederCount++;
            }else {
                //kiss of death
                previousGeneration[i].setAlive(false);
            }
        }
        //*/

        if(breederCount < 2 ) {
            breederCount = 2;
            for (int i = breederCount; i < previousGeneration.length; i++) previousGeneration[i] = null;
        }else{
            for(int i = 0; i < previousGeneration.length; i++)
                if(!previousGeneration[i].getAlive()) previousGeneration[i] = null;
        }

        Individual[] notNullOld = new Individual[breederCount];
        int step = 0;
        for(int i = 0; i < previousGeneration.length; i++){
            if(previousGeneration[i] != null) {
                notNullOld[step] = previousGeneration[i];
                step++;
            }
        }

        if(Config.debug) {
            System.out.println(breederCount + " parent out of " + previousGeneration.length + " individuals " + minFitness);
            System.out.println("Not nulls: " + notNullOld.length);
            for(int i = 0; i < notNullOld.length; i++) System.out.println(Individual.toString(notNullOld[i]));
            System.out.println();
        }

        int childrenSerial = 0;
        int livingOffspring = 0;
        int stillborn = 0;
        int arraySize = 0;

        if (notNullOld.length > Config.partners) {
            arraySize = (int) ((notNullOld.length - Config.partners) * Config.partners + Config.partners * (Config.partners - 1) / 2) * Config.children;
        }else{
            arraySize = (int) ((notNullOld.length * (notNullOld.length - 1 )) / 2) * Config.children;
        }

        if(Config.debug) System.out.println( notNullOld.length + " " + arraySize);

        Individual[] offsprings = new Individual[arraySize];
        for (int i = 0; i < offsprings.length; i++) offsprings[i] = new Individual();

        for(int i = 0; i < notNullOld.length; i++) {
            int limit = 0;

            if (i + Config.partners < notNullOld.length) {
                limit = i + Config.partners + 1;
            } else {
                limit = notNullOld.length;
            }
            if(Config.debug)System.out.println("i: " + i + " limit: " + limit + " length: " + notNullOld.length);

            for (int j = i + 1; j < limit; j++){
                if(Config.debug)System.out.println("i: " + i + " j: " + j);
                for(int k = 0; k < Config.children; k++) {
                    offsprings[childrenSerial] = Crossover.Uniform(notNullOld[i].chromo, notNullOld[j].chromo);

                    if(offsprings[childrenSerial].getAlive()) offsprings[childrenSerial].setFitness();

                    if(offsprings[childrenSerial].getAlive() && offsprings[childrenSerial].getFitness() > minFitness){
                        livingOffspring++;

                        offsprings[childrenSerial].setGeneration(generationCount + 1);
                        if(Config.debug)
                            System.out.println(Individual.toString(offsprings[childrenSerial]) + ", cS: " + childrenSerial + " of " + offsprings.length);
                    }else{
                        stillborn++;
                        offsprings[childrenSerial].setAlive(false);
                        offsprings[childrenSerial] = null;
                    }
                    childrenSerial++;
                }
            }
        }

        if(Config.debug) {
            if (stillborn + livingOffspring == offsprings.length) {
                System.out.println("Looking good");
                System.exit(0);
            } else {
                System.out.println("\nMath doesn't check out");
                System.out.println(stillborn + " dead, " + livingOffspring + " alive, sum: " + (livingOffspring + stillborn) + ". expected: " + offsprings.length);
                System.exit(0);
            }
        }

        Individual[] currentGeneration = new Individual[notNullOld.length + livingOffspring];
        for(int i = 0; i < currentGeneration.length; i++)currentGeneration[i] = new Individual();

        for (int i = 0; i < notNullOld.length; i++) {
            currentGeneration[i] = notNullOld[i];
        }
        for(int i = 0; i < notNullOld.length; i++){
            if(currentGeneration[i] == null){
                System.out.println("Error. Null entries copied to current generation." + i);
                System.exit(0);
            }
        }

        step = 0; //reset
        for(int i = 0; i < offsprings.length; i++){
            if(offsprings[i] != null){
                currentGeneration[(notNullOld.length - 1) + step ] = offsprings[i];
                if(Config.debug) System.out.println("Step " + step + " of " + offsprings.length + " Position " + i + " of " + currentGeneration.length);
                step++;
            }
        }

        if(Config.debug){
            System.out.println("Current generation: " + currentGeneration.length);
            System.out.println((notNullOld.length));
            System.out.println(Individual.toString(notNullOld[notNullOld.length - 1 ]));
            System.out.println(Individual.toString(currentGeneration[currentGeneration.length - 1 ]));
            }
        for(int i = 0; i < currentGeneration.length; i++){
            if(currentGeneration[i] == null) {
                System.out.println("Error. Null entries copied to current generation." + i);
                System.exit(0);
            }
        }
        previousGeneration = null;
        notNullOld = null;

        //every 3 generations garbage collection is forced
        if(generationCount > 1 && generationCount % 3 == 0) {
            System.gc();
            System.gc();
            Config.setElitePercent(Config.elitePercent / 2);
        }

        if(generationCount > 1 && generationCount % 4 == 0) {
            Config.setElitePercent(Config.elitePercent / 2);
        }

        end = System.nanoTime();
        long runtime = end - start;
        String time = "ms";
        runtime = (long) (runtime / 1000000);
        if(runtime > 1000){
            runtime = (long)(runtime / 1000);
            time = "s";
        }
        System.out.println("Runtime: " + runtime + " " + time);
        return elitistSelection(currentGeneration, (generationCount + 1));
    }

    /** Arraylist based version of elitist selection.
     *
     * @param starterGeneration
     * @return first individual to succeed in reaching the target
     */

    public static Individual elitistSelection(Individual[] starterGeneration){
        Benchmark benchmark = new Benchmark();
        benchmark.Start();
        Individual offspring = new Individual();
        int generation = 0;

        ArrayList<Individual> population = new ArrayList<Individual>();

        for(int i = 0; i < starterGeneration.length; i++){
            population.add(starterGeneration[i]);
        }
        Collections.sort(population);

        while(population.get(0).getFitness() != 1){
            System.out.println(population.get(0).toString() + " Gen: " + generation );
            generation++;
            //Take out unfit individuals
            double minFitness = population.get(0).getFitness() * (1 - Config.elitePercent);
            for(int i = 0; i < population.size(); i++){
                if(population.get(i).getFitness() < minFitness || (population.get(i).getGeneration() + Config.lifeTime) < generation) {
                    population.remove(i);
                    i--;
                }
            }

            if(generation % 2 == 0)
            for(int i = 0; i < population.size(); i++){
                int j = i + 1;
                //matching genes have equal fitness, no need to go though all of the individuals
                while(j < population.size() && population.get(i).getFitness() == population.get(j).getFitness() && population.get(i).chromo.compareTo(population.get(j).chromo) == 0){
                    if (population.get(i).getGeneration() > population.get(j).getGeneration()) {
                        population.remove(j);
                    } else {
                        population.remove(i);
                    }
                    j++;
                }
            }

            int alive = population.size();

            //solution to break out of this giant loop found at https://stackoverflow.com/a/886979
            outerloop:
            for(int i = 0; i < alive; i++){
                int limit = 0;
                if((i + Config.partners) < alive){
                    limit = i + Config.partners + 1;
                }else{
                    limit = alive;
                }

                for(int j = i + 1; j < limit; j++ ){
                    //System.out.println("Parents " + i + " of " + alive + " and " + j + " of " + limit);
                    for(int k = 0; k < Config.children; k++){
                        offspring = Crossover.Uniform(population.get(i).chromo, population.get(j).chromo);
                        if(offspring.getAlive()) offspring.setFitness();
                        if(offspring.getFitness() > minFitness){
                            offspring.setGeneration(generation);
                            population.add(offspring);
                            if(offspring.getFitness() == 1){
                                //as only one successful offspring is sufficient, the loops are broken
                                break outerloop;
                            }
                        }
                    }
                }
            }

            Collections.sort(population);
            benchmark.End();
            System.out.println(benchmark.PrintResult());
            System.out.println("Gen " + generation + " Pop " + population.size());
            if(Config.increasingElitism && generation % Config.elitismRate == 0) {
                Config.setElitePercent(Config.elitePercent / 2);
            }

        }

        return population.get(0);
    }
}