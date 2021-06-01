package com.sophiego.algos.ga;


import java.util.Random;

import com.sophiego.shopie.Player;
import com.sophiego.algos.ShortestPath;

public class GeneticAlgorithm extends ShortestPath {

    public Population population = new Population(100);
    Individual fittest;
    Individual secondFittest;
    public int generationCount = 0;

	public GeneticAlgorithm(int[][] maze, Player player) {
		super(maze, player);
	}

	public int minMoves() {
		int superResult = super.minMoves();
		if (superResult != 0) {
			return superResult;
		}
		
        Random rn = new Random();
        this.population.initializeIndividual(paths.length, paths);
        this.population.calculateFitness();

        while (this.generationCount < 100) {
            ++this.generationCount;

            if (rn.nextInt() % 7 < 5) {
                this.mutation();
            }

            this.addFittestOffspring();

            this.population.calculateFitness();
        }

        System.out.println("\nSolution found in generation " + this.generationCount);
        System.out.println("Fitness: "+this.population.getFittest().fitness);
        System.out.print("Genes: ");
        
        this.population.getFittest().printGenes();

        System.out.println("");
        
        return this.population.getFittest().fitness;
	}

    void selection() {
        System.out.println("Selection");

        fittest = population.getFittest();

        secondFittest = population.getSecondFittest();

        fittest.printGenes();
        System.out.println("");
        fittest.printGenes();
        System.out.println("");
    }

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

    Individual getFittestOffspring() {
        return population.getFittest();
    }

    public void addFittestOffspring() {
        int mostFittestIndex = population.getMostFittestIndex();
        population.individuals[mostFittestIndex] = getFittestOffspring();
    }
}