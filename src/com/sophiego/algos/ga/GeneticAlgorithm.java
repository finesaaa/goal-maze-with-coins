package com.sophiego.algos.ga;


import java.util.Random;

public class GeneticAlgorithm {

    public Population population = new Population(100);
    Individual fittest;
    Individual secondFittest;
    public int generationCount = 0;

    // Selection
    void selection() {
        System.out.println("Selection");

        // Select the most fittest individual
        fittest = population.getFittest();

        // Select the second most fittest individual
        secondFittest = population.getSecondFittest();

        fittest.printGenes();
        System.out.println("");
        fittest.printGenes();
        System.out.println("");
    }

    // Mutation
    public void mutation() {
        Random rn = new Random();

        for (int i = 0; i < population.individuals[0].geneLength; i++) {
            int mutationPoint1 = rn.nextInt(population.individuals[0].geneLength - 2) + 1;
            int mutationPoint2 = rn.nextInt(population.individuals[0].geneLength - 2) + 1;
            
            if (mutationPoint1 != mutationPoint2) {
                int temp = population.individuals[i].genes[mutationPoint1];
                population.individuals[i].genes[mutationPoint1] = population.individuals[i].genes[mutationPoint2];
                population.individuals[i].genes[mutationPoint2] = temp;
            }	
        }
    }

    // Get fittest offspring
    Individual getFittestOffspring() {
        return population.getFittest();
    }


    // Replace least fittest individual from most fittest offspring
    public void addFittestOffspring() {
        // Get index of least fit individual
        int mostFittestIndex = population.getMostFittestIndex();

        // Replace least fittest individual from most fittest offspring
        population.individuals[mostFittestIndex] = getFittestOffspring();
    }
}