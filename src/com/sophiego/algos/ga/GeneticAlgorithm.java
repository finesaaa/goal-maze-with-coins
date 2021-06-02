package com.sophiego.algos.ga;


import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import com.sophiego.shopie.Player;

import com.sophiego.algos.ShortestPath;

public class GeneticAlgorithm extends ShortestPath {

    public Population population = new Population(100);
    public int maxGeneration = 10000;
    public int fittestRate = 20;

	public GeneticAlgorithm(int[][] maze, Player player) {
		super(maze, player);
	}
	
	public void setParameter(int populationSize, int generationMax, int fittestRate) {
		this.population = new Population(populationSize);
		this.maxGeneration = generationMax;
		this.fittestRate = fittestRate;
		
	}

	public int minMoves() {
		int superResult = super.minMoves();
		if (superResult != 0) {
			return superResult;
		}
		
        Random rn = new Random();
        int fittest = 0;
        int fittestCount = 0;
        int generationCount = 0;
        
        this.population.initializeIndividual(paths.length, paths);
        this.population.calculateFitness();

        while (generationCount < maxGeneration && fittestCount < fittestRate) {
            ++generationCount;
            
            this.selection();

            if (rn.nextInt() % 7 < 5) {
                this.mutation();
            }

            this.population.calculateFitness();
            
            if (this.population.fittest != fittest) {
            	fittest = this.population.fittest;
            	fittestCount = 0;
            } else {
            	fittestCount++;
            }
        }

        System.out.println("\nSolution found in generation " + generationCount);
        System.out.println("Fitness: "+ this.population.getFittest().fitness);
        System.out.print("Genes: ");
        
        this.population.getFittest().printGenes();

        System.out.println("");
        
        return this.population.getFittest().fitness;
	}

    void selection() {
		Arrays.sort(this.population.individuals, new Comparator<Individual>() {
			@Override
			public int compare(Individual i1, Individual i2) {
				if (i1.fitness > i2.fitness) {
					return -1;
				} else if (i1.fitness < i2.fitness) {
					return 1;
				}
				return 0;
			}
		});
    }

    public void mutation() {
        Random rn = new Random();

        for (int i = 2; i < population.individuals[0].geneLength; i++) {
            int mutationPoint1 = rn.nextInt(population.individuals[0].geneLength - 2) + 1;
            int mutationPoint2 = rn.nextInt(population.individuals[0].geneLength - 2) + 1;
            
            if (mutationPoint1 != mutationPoint2) {
                int temp = population.individuals[i].genes[mutationPoint1];
                population.individuals[i].genes[mutationPoint1] = population.individuals[i].genes[mutationPoint2];
                population.individuals[i].genes[mutationPoint2] = temp;
            }
        }
    }
}