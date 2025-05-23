import java.util.*;

public class genetic {
    private static int populationSize;
    private static int chromosomeLength;
    private static double mutationRate;
    private static int maxGenerations;
    private static double selectionPercentage;
    private static ArrayList<int[]> population;
    private static ArrayList<Double> fitnessScores;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter population size: ");
        populationSize = sc.nextInt();
        System.out.print("Enter chromosome length: ");
        chromosomeLength = sc.nextInt();
        System.out.print("Enter mutation rate (0.0 to 1.0): ");
        mutationRate = sc.nextDouble();
        System.out.print("Enter maximum generations: ");
        maxGenerations = sc.nextInt();
        System.out.print("Enter selection percentage (0.0 to 1.0): ");
        selectionPercentage = sc.nextDouble();
        sc.close();

        initializePopulation();
        runGeneticAlgorithm();
    }

    private static void initializePopulation() {
        population = new ArrayList<>();
        Random rand = new Random();

        // Create random chromosomes
        for (int i = 0; i < populationSize; i++) {
            int[] chromosome = new int[chromosomeLength];
            for (int j = 0; j < chromosomeLength; j++) {
                chromosome[j] = rand.nextInt(2); // Binary genes (0 or 1)
            }
            population.add(chromosome);
        }
    }

    private static void runGeneticAlgorithm() {
        int generation = 0;
        while (generation < maxGenerations) {
            calculateFitness();
            displayBestSolution(generation);
            if (generation == maxGenerations - 1) {
                break;
            }
            createNextGeneration();
            generation++;
        }
    }

    private static void calculateFitness() {
        fitnessScores = new ArrayList<>();
        for (int[] chromosome : population) {
            double fitness = 0;
            for (int gene : chromosome) {
                fitness += gene;
            }
            fitnessScores.add(fitness);
        }
    }

    private static void displayBestSolution(int generation) {
        int bestIndex = 0;
        double bestFitness = fitnessScores.get(0);

        for (int i = 1; i < fitnessScores.size(); i++) {
            if (fitnessScores.get(i) > bestFitness) {
                bestFitness = fitnessScores.get(i);
                bestIndex = i;
            }
        }

        System.out.println("Generation " + (generation + 1) + ":");
        System.out.print("Best chromosome: ");
        for (int gene : population.get(bestIndex)) {
            System.out.print(gene);
        }
        System.out.println(" (Fitness: " + bestFitness + ")");
    }

    private static void createNextGeneration() {
        ArrayList<int[]> newPopulation = new ArrayList<>();
        ArrayList<int[]> selectedChromosomes = selection();
        while (newPopulation.size() < populationSize) {
            int[] parent1 = selectedChromosomes.get(new Random().nextInt(selectedChromosomes.size()));
            int[] parent2 = selectedChromosomes.get(new Random().nextInt(selectedChromosomes.size()));

            int[] child = crossover(parent1, parent2);
            mutate(child);
            newPopulation.add(child);
        }
        // Replace old population with new population
        population = newPopulation;
    }

    private static ArrayList<int[]> selection() {
        ArrayList<int[]> selectedChromosomes = new ArrayList<>();

        // Create a copy of population and fitnessScores to sort
        ArrayList<int[]> sortedPopulation = new ArrayList<>(population);
        ArrayList<Double> sortedFitness = new ArrayList<>(fitnessScores);

        // Bubble sort both arrays based on fitness (in descending order)
        for (int i = 0; i < populationSize - 1; i++) {
            for (int j = 0; j < populationSize - i - 1; j++) {
                if (sortedFitness.get(j) < sortedFitness.get(j + 1)) {
                    // Swap fitness scores
                    Double tempFitness = sortedFitness.get(j);
                    sortedFitness.set(j, sortedFitness.get(j + 1));
                    sortedFitness.set(j + 1, tempFitness);

                    // Swap chromosomes
                    int[] tempChromosome = sortedPopulation.get(j);
                    sortedPopulation.set(j, sortedPopulation.get(j + 1));
                    sortedPopulation.set(j + 1, tempChromosome);
                }
            }
        }

        // Select top percentage
        int selectCount = (int) (populationSize * selectionPercentage);
        for (int i = 0; i < selectCount; i++) {
            selectedChromosomes.add(sortedPopulation.get(i));
        }

        return selectedChromosomes;
    }

    private static int[] crossover(int[] parent1, int[] parent2) {
        int[] child = new int[chromosomeLength];
        int crossoverPoint = new Random().nextInt(chromosomeLength);

        for (int i = 0; i < chromosomeLength; i++) {
            if (i < crossoverPoint) {
                child[i] = parent1[i];
            } else {
                child[i] = parent2[i];
            }
        }
        return child;
    }

    private static void mutate(int[] chromosome) {
        Random rand = new Random();
        for (int i = 0; i < chromosomeLength; i++) {
            if (rand.nextDouble() < mutationRate) {
                chromosome[i] = 1 - chromosome[i];
            }
        }
    }
}