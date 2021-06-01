package com.sophiego.algos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.sophiego.shopie.Player;

public class ShortestPath {

	int[][][] dist;
    int[][] dp, maze;
    int src_x, src_y, dst_x, dst_y;
    int row, col;
    List<int[]> coins;
    int allOnes, numCoins;
    int MAXDIST = 1000 * 1000;

    
	public ShortestPath(int[][] maze, Player player) {
		this.maze = maze;
	
		this.src_x = player.getStartRow();
		this.src_y = player.getStartCol();
		
		this.dst_x = player.getEndRow();
		this.dst_y = player.getEndCol();
		
		this.row = maze.length;
		this.col = maze[0].length;
	}
	
	boolean isInRange(int r, int c) {
        return r >= 0 && r < row && c >= 0 && c < col;
    }
	
	public int minMoves() {
        int[] startPoint = {src_x, src_y};

        coins = new ArrayList<>();
        coins.add(startPoint);
        
        extractCoins();
        numCoins = coins.size();
        
        allOnes = (1 << numCoins) - 1;
        
//        System.out.println("allOnes = " + allOnes);

        // maximum no. of coins = 10, max number of 'trees' = 2**10
        int dpR = numCoins;
        int dpC = allOnes + 1;
        dp = new int[dpR][dpC];
        
        for (int i = 0; i < dpR; i++) {
            for (int j = 0; j < dpC; j++) 
            	dp[i][j] = -1;
        }

        dist = new int[this.row][this.col][numCoins];
        for (int i = 0; i < numCoins; i++) { 
        	setDistances(i);
        	
        	int[] coin = this.coins.get(i);
        	System.out.println("Path untuk coin ke-" + i + " pada titik (" + coin[0] + ", " + coin[1] + ")");
    		for(int row = 0; row < this.row; row++) {
    			for(int col = 0; col < this.col; col++) {
    				if (this.maze[row][col] == 1) {
        				System.out.print("*");
    				} else {
    					System.out.print(this.dist[row][col][i]);
    				}
    			}
    			System.out.println("");
    		}
        }

        // solve recursively!
        int ans = getMinDist(0, 1);
        return ans >= MAXDIST ? -1 : ans;
    }
	
	void extractCoins() {
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (this.maze[r][c] == 3) { //coins
                    int[] point = {r, c};
                    coins.add(point);
                }
            }
        }
    }
	
	void setDistances(int coin) {

        for (int r = 0; r < this.row; r++) {
            for (int c = 0; c < this.col; c++) 
            	dist[r][c][coin] = MAXDIST;
        }

        boolean[][] visited = new boolean[this.row][this.col];
        Queue<int[]> q = new LinkedList<>();
        
        int[] startPoint = coins.get(coin);
        q.add(startPoint);
        
        visited[startPoint[0]][startPoint[1]] = true;
        dist[startPoint[0]][startPoint[1]][coin] = 0;

        int[] dr = {0, -1, 0, 1};
        int[] dc = {-1, 0, 1, 0};

        while (!q.isEmpty()) {
        	
            int[] point = q.poll();
            int oldR = point[0];
            int oldC = point[1];
         
            for (int k = 0; k < 4; k++) {
                int newR = oldR + dr[k];
                int newC = oldC + dc[k];
                
                if (isInRange(newR, newC) && !visited[newR][newC] && this.maze[newR][newC] != 1) {
                    int[] newPoint = {newR, newC};
                    visited[newR][newC] = true;
                    dist[newR][newC][coin] = dist[oldR][oldC][coin] + 1;
                    q.add(newPoint);
                }
            }
        }
    }

    int getMinDist(int coin, int seq) {
        // seq - keeps track of the nodes/coins included in the "spanning tree"
        // 0 means that coin has not been considered, and isn't in the spanning tree
        // 1 means it has been considered

    	int Ra = this.dst_x;
    	int Ca = this.dst_y;
    	
        if (seq == allOnes) return dist[Ra][Ca][coin];
        if (dp[coin][seq] != -1) return dp[coin][seq];

        int res = Integer.MAX_VALUE;
        for (int i = 0; i < numCoins; i++) {
            // (seq & (1 << i)) == 0 means i hasn't been included in the current "spanning tree"
            if ((seq & (1 << i)) == 0) {
                int newSeq = seq | (1 << i);
                int[] pos = coins.get(i);
                res = Math.min(res, getMinDist(i, newSeq) + dist[pos[0]][pos[1]][coin]);
            }
        }

        dp[coin][seq] = res;
//        System.out.println("coin = " + coin + " seq = " + " res = " + res);
        return res;
    }
}
