package com.sophiego.algos.ga;

public class Population {
	
    int populationSize;
    Individual[] individuals;
    int fittest = 0;
    
    public Population(int populationSize) {
    	this.populationSize = populationSize;
    	this.individuals = new Individual[populationSize];
    }

    // Initialize population
    public void initializeIndividual(int individualSize, int[][] path) {
        for (int i = 0; i < individuals.length; i++) {
            individuals[i] = new Individual(individualSize, path);
        }
    }

    // Get the fittest individual
    public Individual getFittest() {
        int minFit = Integer.MAX_VALUE;
        int minFitIndex = 0;
        
        for (int i = 0; i < individuals.length; i++) {
            if (minFit >= individuals[i].fitness) {
            	minFit = individuals[i].fitness;
            	minFitIndex = i;
            }
        }
        fittest = individuals[minFitIndex].fitness;
        
        try {
            return (Individual) individuals[minFitIndex].clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    // Get the second most fittest individual
    public Individual getSecondFittest() {
        int minFit1 = 0;
        int minFit2 = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (individuals[i].fitness < individuals[minFit1].fitness) {
            	minFit2 = minFit1;
                minFit1 = i;
            } else if (individuals[i].fitness < individuals[minFit2].fitness) {
            	minFit2 = i;
            }
        }
        
        try {
            return (Individual) individuals[minFit2].clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    // Get index of least fittest individual
    public int getMostFittestIndex() {
        int maxFitVal = Integer.MIN_VALUE;
        int maxFitIndex = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (maxFitVal <= individuals[i].fitness) {
            	maxFitVal = individuals[i].fitness;
            	maxFitIndex = i;
            }
        }
        return maxFitIndex;
    }

    // Calculate fitness of each individual
    public void calculateFitness() {
        for (int i = 0; i < individuals.length; i++) {
            individuals[i].calcFitness();
        }
        getFittest();
    }
}
