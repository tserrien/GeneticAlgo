import java.util.Random;
import java.math.BigInteger;

public class Selection{
    public static Individual[] elitistSelection(Individual[] previousGeneration, int generationCount) {

        HeapSort.sort(previousGeneration);
        //if(Config.debug)
        System.out.println(Individual.toString(previousGeneration[0]));
        //base case of recursion. may be moved elsewhere
        if (previousGeneration[0].getFitness() == 1) return previousGeneration;

        //if basecase fails
        double minFitness = previousGeneration[0].getFitness() * Config.elitePercent;

        int breederCount = 0;
        for (int i = 0; i < previousGeneration.length; i++) {
            if (previousGeneration[i].getFitness() > minFitness) {
                breederCount++;
            } else {
                previousGeneration[i] = null;
            }

        }

        int childrenSerial = 0;
        int livingOffspring = 0;
        Individual[] offsprings = new Individual[breederCount * (breederCount - 1) * Config.children];

        for (int i = 0; i < offsprings.length; i++) offsprings[i] = new Individual();

        for (int i = 0; i < breederCount; i++) {
            for (int j = i + 1; j < breederCount; j++) {
                for (int k = 0; k < Config.children; k++) {
                    offsprings[childrenSerial] = ChromosomeOperations.uniformCrossover(previousGeneration[i], previousGeneration[j]);

                    if (offsprings[childrenSerial].getAlive()) {
                        offsprings[childrenSerial].setFitness(offsprings[childrenSerial]);
                        offsprings[childrenSerial].setParents(previousGeneration[i], previousGeneration[j]);
                        offsprings[childrenSerial].setGeneration(generationCount + 1);
                        livingOffspring++;

                        //if(Config.debug)
                        System.out.println(Individual.toString(offsprings[childrenSerial]) + " cS:" + childrenSerial);
                        childrenSerial++;
                    } else {
                        offsprings[childrenSerial] = null;
                    }
                }
            }
        }

        HeapSort.sort(offsprings);

        Individual[] currentGeneration = new Individual[previousGeneration.length + livingOffspring];

        for (int i = 0; i < previousGeneration.length; i++) {
            currentGeneration[i] = previousGeneration[i];
        }
        for (int i = 0; i < livingOffspring; i++) {
            currentGeneration[previousGeneration.length + i] = offsprings[i];
        }
        previousGeneration = null;
        System.gc();
        System.gc();

        return elitistSelection(currentGeneration, (generationCount + 1));
    }
}
