package com.sophiego.shopie;

import java.awt.Image;

import com.sophiego.entities.Level;
import com.sophiego.gfx.Assets;

public class Player {

	private int startRow, startCol, endRow, endCol;
	private int row, col;
	private Image texture;

	public Player(int plaStartRow, int plaStartCol) {
		this.startRow = plaStartRow;
		this.startCol = plaStartCol;
		this.row = plaStartRow;
		this.col = plaStartCol;
		this.texture = Assets.PlayerFront;
	}

	public void resetTexture() {
		this.texture = Assets.PlayerFront;
	}
	
	public void resetPosition() {
		this.row = this.startRow;
		this.col = this.startCol;
	}
	
	public void updatePosition(int row, int col) {
		this.row += row;
		this.col += col;
	}
	public void goUp() {
		this.texture = Assets.playerBack;
	}
	
	public void goLeft() {
		this.texture = Assets.playerLeft;
	}
	
	public void goDown() {
		this.texture = Assets.PlayerFront;
	}
	
	public void goRight() {
		this.texture = Assets.playerRight;
	}
	
	public void dead() {
		this.texture = Assets.playerDead;
	}
	
	public Image getTexture() {
		return texture;
	}

	public void setTexture(Image texture) {
		this.texture = texture;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getStartCol() {
		return startCol;
	}

	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getEndCol() {
		return endCol;
	}

	public void setEndCol(int endCol) {
		this.endCol = endCol;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
}
