package com.adhamenaya;

import java.util.List;

import retrofit.RestAdapter;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.adhamenaya.listeners.OnItemClickListener;
import com.adhamenaya.views.BlockPattern.BLOCK_PATTERN;
import com.adhamenaya.views.MosaicLayout;

public class MainActivity extends Activity {

	private MosaicLayout mMosaicLayout;
	private MyAdapter mAdapater;

	BLOCK_PATTERN pattern1[] = { BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL,
			BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.HORIZONTAL, BLOCK_PATTERN.HORIZONTAL };

	BLOCK_PATTERN pattern2[] = { BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG,
			BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG };

	private WebSerivceProxy mWebSerivceProxy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mMosaicLayout = (MosaicLayout) findViewById(R.id.mosaic_layout);

		// Choose a pattern randomly from the set of pattern specified
		mMosaicLayout.chooseRandomPattern(false);

		mMosaicLayout.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onClick(int position) {
				Toast.makeText(getApplicationContext(), "pos " + position, Toast.LENGTH_LONG).show();
			}
		});
		randomSelectedPatterns();
		// Download images for
		downloadImages();

	}

	private void randomAllPatters() {
		mMosaicLayout.chooseRandomPattern(true);

	}

	private void randomSelectedPatterns() {
		mMosaicLayout.addPattern(pattern1);
		mMosaicLayout.addPattern(pattern2);
		mMosaicLayout.chooseRandomPattern(true);

	}

	private void orderedSelectedPatterns() {
		mMosaicLayout.addPattern(pattern1);
		mMosaicLayout.addPattern(pattern2);
		mMosaicLayout.chooseRandomPattern(false);

	}

	private void downloadImages() {
		mWebSerivceProxy = new RestAdapter.Builder().setEndpoint(WebSerivceProxy.ENDPOINT).build().create(WebSerivceProxy.class);

		new AsyncTask<Void, Void, List<Image>>() {

			@Override
			protected List<Image> doInBackground(Void... params) {

				return mWebSerivceProxy.getImages().getResponseData().getResults();
			}

			protected void onPostExecute(java.util.List<Image> result) {
				mMosaicLayout.reset();
				// Set the data to the adapter
				mAdapater = new MyAdapter(getApplicationContext());
				// add the results 2 times to increase the size of the results
				result.addAll(result);
				result.addAll(result);
				result.addAll(result);

				mAdapater.setData(result);

				// Set the adapter to the layout
				mMosaicLayout.setAdapter(mAdapater);
			};

		}.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action1:
			randomAllPatters();
		case R.id.action2:
			randomSelectedPatterns();
		case R.id.action3:
			orderedSelectedPatterns();
		default:
			randomAllPatters();
		}
		downloadImages();
		return true;
	}
}
