package com.sophiego.algos.ga;

import java.util.Random;

public class Individual  implements Cloneable {
	
    public int fitness;
    int[] genes;
    int startNode;
    int endNode;
    int geneLength;
    int[][] nodesCost;

    public Individual(int size, int[][] path) {
        Random rn = new Random();
        
        this.startNode = 1;
        this.endNode = size;
        this.geneLength = size;
        this.genes = new int[size];
        this.fitness = 0;
        this.nodesCost = new int[size][size];
        
        //Set genes randomly for each individual
        this.genes[0] = this.startNode;
        for (int i = 1; i < this.genes.length - 1; i++) {
        	int number = rn.nextInt(size - 1) + this.startNode;
        	if (verifyGene(i, number)) {
        		this.genes[i] = number;
        	} else {
        		i--;
        	}
        }
        this.genes[size - 1] = this.endNode;
        
        this.nodesCost = path;
    }
    
    private boolean verifyGene(int size, int node) {
    	for (int i = 0; i < size; i++) {
    		if (this.genes[i] == node) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public void printGenes() {
        for (int i = 0; i < genes.length; i++) {
            System.out.print(genes[i]);
        }
    }

    //Calculate fitness
    public void calcFitness() {
        this.fitness = 0;
        for (int i = 0; i < this.genes.length - 1; i++) {
        	this.fitness += this.nodesCost[this.genes[i] - 1][this.genes[i + 1] - 1];
        }
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        Individual individual = (Individual) super.clone();
        individual.genes = new int[this.geneLength];
        for(int i = 0; i < individual.genes.length; i++){
            individual.genes[i] = this.genes[i];
        }
        return individual;
    }
}
