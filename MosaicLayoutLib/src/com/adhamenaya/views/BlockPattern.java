package com.adhamenaya.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlockPattern {

	public enum BLOCK_PATTERN {
		SMALL, BIG, VERTICAL, HORIZONTAL, EMPTY
	};

	BLOCK_PATTERN board[] = new BLOCK_PATTERN[8];

	ArrayList<ArrayList<ArrayList<Integer>>> boxesAll = new ArrayList<ArrayList<ArrayList<Integer>>>();
	int N = 0;

	static String result = "";

	public BlockPattern() {

		// Initialize the board pattern
		for (int i = 0; i < 8; i++)
			board[i] = BLOCK_PATTERN.EMPTY;
	}

	public ArrayList<ArrayList<Integer>> getPatternRandomly(int remainingCount) {

		ArrayList<ArrayList<Integer>> list = null;

		run(0, board);

		if (remainingCount == 1 || remainingCount == 2) {
			list = getSingleDoubleItemPattern();

		} else if (remainingCount < 8) {
			for (;;) {
				list = getRandomOrder();
				if (list.size() == remainingCount) {
					break;
				}
			}
		} else {
			list = getRandomOrder();
		}
		return list;

	}

	public ArrayList<ArrayList<Integer>> getPattern(List<BLOCK_PATTERN[]> board, boolean isRandom, int lastSelectIndex) {
		if (board.size() == 1)
			// Get the only exits pattern
			return parse(board.get(0));
		else if (board.size() > 0) {

			// Randomly select a pattern from a specified list of patterns
			if (isRandom) {
				Random random = new Random();
				int randomPattenIndex = random.nextInt(board.size());
				return parse(board.get(randomPattenIndex));
			}
			// Orders select of a pattern from a specified list of patterns
			else {
				return parse(board.get(lastSelectIndex));
			}
		} else
			return null;
	}

	private boolean isPossible(BLOCK_PATTERN pattern, int spot, BLOCK_PATTERN[] board) {

		switch (pattern) {
		case SMALL:
			return board[spot].equals(BLOCK_PATTERN.EMPTY);
		case BIG:
			return spot < 3 && board[spot].equals(BLOCK_PATTERN.EMPTY) && board[spot + 1].equals(BLOCK_PATTERN.EMPTY)
					&& board[spot + 4].equals(BLOCK_PATTERN.EMPTY) && board[spot + 5].equals(BLOCK_PATTERN.EMPTY);
		case VERTICAL:
			return spot < 4 && board[spot].equals(BLOCK_PATTERN.EMPTY) && board[spot + 4].equals(BLOCK_PATTERN.EMPTY);
		case HORIZONTAL:
			return spot % 4 < 3 && board[spot].equals(BLOCK_PATTERN.EMPTY) && board[spot + 1].equals(BLOCK_PATTERN.EMPTY);
		default:
			return false;

		}
	}

	private ArrayList<ArrayList<Integer>> getSingleDoubleItemPattern() {
		BLOCK_PATTERN[] pattern12 = { BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG,
				BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG };
		return parse(pattern12);
	}

	private BLOCK_PATTERN[] insert(BLOCK_PATTERN pattern, int spot, BLOCK_PATTERN[] board2) {

		board = Arrays.copyOf(board2, board2.length);

		switch (pattern) {
		case SMALL:
			board[spot] = BLOCK_PATTERN.SMALL;
			break;
		case BIG:
			board[spot] = BLOCK_PATTERN.BIG;
			board[spot + 1] = BLOCK_PATTERN.BIG;
			board[spot + 4] = BLOCK_PATTERN.BIG;
			board[spot + 5] = BLOCK_PATTERN.BIG;
			break;
		case VERTICAL:
			board[spot] = board[spot + 4] = BLOCK_PATTERN.VERTICAL;
			break;
		case HORIZONTAL:
			board[spot] = board[spot + 1] = BLOCK_PATTERN.HORIZONTAL;
			break;
		case EMPTY:
		}
		return board;
	}

	public boolean isValid(BLOCK_PATTERN[] borad) {
		for (int i = 0; i < board.length; ++i) {
			if (board[i].equals(BLOCK_PATTERN.EMPTY)) {
				return false;
			}
		}
		return true;
	}

	public void run(int spot, BLOCK_PATTERN[] board) {
		if (spot == 8) {
			if (isValid(board)) {
				N++;
				boxesAll.add(parse(board));
			}
			return;
		}

		for (int i = 0; i < BLOCK_PATTERN.values().length; ++i) {
			BLOCK_PATTERN pattern = BLOCK_PATTERN.values().clone()[i];

			if (isPossible(pattern, spot, board)) {
				run(spot + 1, insert(pattern, spot, board));
			}
		}

		if (!board[spot].equals(BLOCK_PATTERN.EMPTY)) {
			run(spot + 1, board);
		}
	}

	public String join(ArrayList<Integer> list) {

		String str = "";
		for (int i : list) {
			str += i;
		}

		return str;
	}

	private ArrayList<ArrayList<Integer>> getRandomOrder() {

		int n = (int) (Math.random() * 89);
		return boxesAll.get(n);
	}

	private ArrayList<ArrayList<Integer>> parse(BLOCK_PATTERN[] board) {

		boolean[] occupy = { false, false, false, false, false, false, false, false };

		ArrayList<ArrayList<Integer>> boxes = new ArrayList<ArrayList<Integer>>();

		ArrayList<Integer> cell;

		for (int i = 0; i < board.length; i++) {
			if (!occupy[i]) {
				switch (board[i]) {
				case SMALL:
					cell = new ArrayList<Integer>();
					cell.add(i);
					boxes.add(cell);
					occupy[i] = true;
					break;
				case BIG:
					cell = new ArrayList<Integer>();
					occupy[i] = true;
					occupy[i + 1] = true;
					occupy[i + 4] = true;
					occupy[i + 5] = true;
					cell.add(i);
					cell.add(i + 1);
					cell.add(i + 4);
					cell.add(i + 5);
					boxes.add(cell);
					break;
				case VERTICAL:
					cell = new ArrayList<Integer>();
					occupy[i] = true;
					occupy[i + 4] = true;
					cell.add(i);
					cell.add(i + 4);
					boxes.add(cell);
					break;
				case HORIZONTAL:
					cell = new ArrayList<Integer>();
					occupy[i] = true;
					occupy[i + 1] = true;
					cell.add(i);
					cell.add(i + 1);
					boxes.add(cell);
					break;
				case EMPTY:
					break;
				}
			}
		}
		return boxes;

	}

	public Block getBlock(ArrayList<Cell> cells) {

		double maxX = 0;
		double maxY = 0;
		double minX = Integer.MAX_VALUE;
		double minY = Integer.MAX_VALUE;

		for (int i = 0; i < cells.size(); i++) {
			if (cells.get(i).x1 < minX)
				minX = cells.get(i).x1;

			if (cells.get(i).y1 < minY)
				minY = cells.get(i).y1;

			if (cells.get(i).x2 > maxX)
				maxX = cells.get(i).x2;

			if (cells.get(i).y2 > maxY)
				maxY = cells.get(i).y2;
		}

		Block block = new Block();
		block.x1 = minX;
		block.y1 = minY;
		block.x2 = maxX;
		block.y2 = maxY;

		block.width = block.x2 - block.x1;
		block.height = block.y2 - block.y1;

		return block;

	}
}
