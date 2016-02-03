package com.adhamenaya.androidmosaiclayout.views;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import com.adhamenaya.androidmosaiclayout.listeners.OnItemClickListener;
import com.adhamenaya.androidmosaiclayout.listeners.OnItemLongClickListener;
import com.adhamenaya.androidmosaiclayout.views.BlockPattern.BLOCK_PATTERN;

public class MosaicLayout extends NestedScrollView {

	private ArrayList<Cell> cells = new ArrayList<Cell>();

	// The number of cells in each row
	private static final int NO_CESLLS = 4;
	private double d = 0.0;
	private int counter = 0, coorX = 0, coorY = 0;

	// Pattern in which the blocks should be displayed
	private BlockPattern mBlockPattern = new BlockPattern();
	// The position on the block to be displayed
	private int postion = 0;
	// The adapter the binds the data list in the layout
	private ArrayAdapter<Object> mAdapter;
	// Numerical presentation of the pattern
	private ArrayList<ArrayList<Integer>> pattern;
	// The context that the layout works in
	private Context mContext;
	// Block on click event listener
	private OnItemClickListener mOnItemClickListener;
	// Block on long click event listener
	private OnItemLongClickListener mOnItemLongClickListener;
	// The presentation of the pattern
	private List<BLOCK_PATTERN[]> patternsList = new ArrayList<BlockPattern.BLOCK_PATTERN[]>();
	// The max height for each column
	private double maxHeights[] = { 0, 0, 0, 0 };
	// Sequential select for the pattern
	private int mSeqPatternId = 0;
	// Is random pattern
	private boolean isRandomPattern = false;
	// Container layout for the view
	private FrameLayout frameLayout;

	/**
	 * @param context: the context where the layout works in
	 * 
	 * @description Constructor that pass an object of the context
	 */
	public MosaicLayout(Context context) {
		super(context);
		this.mContext = context;
		if (frameLayout == null)

			frameLayout = new FrameLayout(mContext);
		Log.d("MosaicLayout", "MosaicLayout(Context context)");
	}

	public MosaicLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		if (frameLayout == null)
			frameLayout = new FrameLayout(mContext);
		Log.d("MosaicLayout", "MosaicLayout(Context context, AttributeSet attrs)");

	}

	public MosaicLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		if (frameLayout == null)

			frameLayout = new FrameLayout(mContext);
		Log.d("MosaicLayout", "MosaicLayout(Context context, AttributeSet attrs, int defStyle)");

	}

	/**
	 * @param adapter: the adapter the binds the data to the layout
	 * 
	 * @description this function set the adapter for the layout and start
	 *              showing data according to it.
	 * */
	public void setAdapter(ArrayAdapter<Object> adapter) {
		this.mAdapter = adapter;
		postion = 0;
		show(mContext);
	}

	/**
	 * @param x: the x coordinate on the screen
	 * @param y: the y coordinate on the screen
	 * 
	 * @return the cell object
	 * 
	 * @description this function takes the x and y coordinates and find the
	 *              cell where these coordinate exists
	 * */
	/*
	 * private Cell getCell(double x, double y) { for (int i = 0; i <
	 * cells.size(); i++) { if (x > cells.get(i).x1 && y > cells.get(i).y1 && x
	 * < cells.get(i).x2 && y < cells.get(i).y2) { return cells.get(i); } }
	 * return null; }
	 */

	/**
	 * @return the size of the data
	 * 
	 * @description this function returns the size of the data
	 * */
	public int getCount() {
		return pattern.size();
	}

	/**
	 * @param x1: the x coordinate of the first point
	 * @param y1: the y coordinate of the first point
	 * @param x2: the x coordinate of the second point
	 * @param y2: the y coordinate of the second point
	 * 
	 * @return the distance
	 * 
	 * @description the distance value between two points
	 * */
	/*
	 * private double getDistance(double x1, double y1, double x2, double y2) {
	 * return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)); }
	 */

	/**
	 * @param context: the context where the blocks are shown
	 * 
	 * @description this function binds the data in already defined block
	 *              pattern,
	 * */
	private void show(Context context) {
		this.removeAllViews();
		do {
			setPadding(1, 1, 1, 0);
			display(context);
		} while (postion < mAdapter.getCount());

		this.addView(frameLayout);
	}

	/**
	 * This function creates the wire frame for the blocks, where divide the
	 * screen into cells (smallest unit), where each block can occupy one or
	 * more cell.
	 * 
	 * @param context
	 *            context where the layout binds
	 * 
	 * */
	private void calculateAllCells(Context context) {

		DisplayMetrics displaymetrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int width = displaymetrics.widthPixels;
		d = width / NO_CESLLS;

		for (int y = 0; y < d * 2; y += d) { // height
			coorX = 0;

			for (int x = 0; x <= width - d; x += d) { // width

				Cell cell = new Cell();
				cell.x1 = x;
				cell.y1 = y;

				cell.x2 = x + d;
				cell.y2 = y + d;

				cell.id = counter;
				cell.coorX = coorX;
				cell.coorY = coorY;

				cells.add(cell);
				counter++;
				coorX++;
			}
			coorY++;
		}
	}

	public void reset() {
		postion = 0;
		resetPatterns();
		// Clear the adapter
		if (mAdapter != null)
			mAdapter.clear();
	}

	public void chooseRandomPattern(boolean isRandom) {
		this.isRandomPattern = isRandom;
	}

	public void setOnItemClickListener(OnItemClickListener onClickListener) {
		this.mOnItemClickListener = onClickListener;
	}

	public void resetPatterns() {
		this.patternsList.clear();
	}

	public void addPattern(BLOCK_PATTERN patternsList[]) {
		this.patternsList.add(patternsList);
	}

	private void display(Context cx) {
		ArrayList<Block> blocks = new ArrayList<Block>();

		counter = coorX = coorY = 0;
		calculateAllCells(cx);
		pattern = getDisplayPattern();

		for (int i = 0; i < pattern.size(); i++) {

			ArrayList<Cell> boxCells = new ArrayList<Cell>();

			for (int j = 0; j < pattern.get(i).size(); j++) {
				Cell c = cells.get(pattern.get(i).get(j));
				boxCells.add(c);
			}

			Block block = mBlockPattern.getBlock(boxCells);
			block.cells = boxCells;

			blocks.add(block);
		}

		double maxHeightsLocal[] = { 0, 0, 0, 0 };

		for (int i = 0; i < blocks.size(); i++) {

			CellView cv = new CellView(cx);
			View view = mAdapter.getView(postion, null, frameLayout);
			cv.addView(view);
			
			LayoutParams params = new LayoutParams((int) blocks.get(i).width, (int) blocks.get(i).height);
			params.setMargins((int) blocks.get(i).x1, (int) blocks.get(i).y1 + (int) blocks.get(i).getHeightShift(maxHeights), 0, 0);

			frameLayout.addView(cv, params);
			cells.get(i).setCellView(cv);
			postion++;
			view.setTag(postion);
			setViewListeners(view);

			if (postion >= mAdapter.getCount())
				return;

			// Get the max height that the cells of the box occupy
			for (int j = 0; j < blocks.get(i).cells.size(); j++) {
				maxHeightsLocal[blocks.get(i).cells.get(j).coorX] += blocks.get(i).cells.get(j).getHeight();
			}

		}
		for (int i = 0; i < 4; i++)
			maxHeights[i] += maxHeightsLocal[i];

		cells.clear();
	}

	private int getRemainingCount() {
		return mAdapter.getCount() - postion;
	}

	private ArrayList<ArrayList<Integer>> getDisplayPattern() {
		if (patternsList.size() == 0)
			pattern = mBlockPattern.getPatternRandomly(getRemainingCount());
		else {
			if (isRandomPattern)
				pattern = mBlockPattern.getPattern(patternsList, true, -1);
			else {
				pattern = mBlockPattern.getPattern(patternsList, false, mSeqPatternId);
				mSeqPatternId++;
				if (mSeqPatternId == patternsList.size())
					mSeqPatternId = 0;
			}
		}
		return pattern;
	}

	private void setViewListeners(View view) {
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mOnItemClickListener != null)
					mOnItemClickListener.onClick(Integer.parseInt(v.getTag().toString()) - 1);
			}
		});
		view.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				if (mOnItemLongClickListener != null)
					mOnItemLongClickListener.onLongClick(Integer.parseInt(v.getTag().toString()) - 1);
				return true;
			}
		});
	}
}
