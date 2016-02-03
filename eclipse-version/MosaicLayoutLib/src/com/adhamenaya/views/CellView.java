package com.adhamenaya.views;

import android.content.Context;
import android.widget.LinearLayout;

public class CellView extends LinearLayout {

	public CellView(Context context) {
		super(context);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}
}