package com.adhamenaya.views;

import java.util.ArrayList;

public class Block {

	// Identifier to recognize the block
	int id;
	// The left edge of the block
	public double x1;
	// The top edge of the block
	public double y1;
	// The right edge of the block
	public double x2;
	// The bottom edge of the block
	public double y2;
	// The width of the block in pixels
	public double width;
	// The height of the block in pixels
	public double height;
	// The type of the block
	public int type;
	// The set of cell units that represent the block
	public ArrayList<Cell> cells = new ArrayList<Cell>();

	/**
	 * Get how much the block should be shifted below depending on the previous
	 * records.
	 */
	public double getHeightShift(double maxYs[]) {
		double maxHeight = 0.0;

		for (int i = 0; i < cells.size(); i++) {
			if (maxYs[cells.get(i).coorX] > maxHeight) {
				maxHeight = maxYs[cells.get(i).coorX];
			}
		}
		return maxHeight;
	}
}
