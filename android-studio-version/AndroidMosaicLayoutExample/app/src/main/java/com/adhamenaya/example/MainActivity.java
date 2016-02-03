package com.adhamenaya.example;

import java.util.List;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.adhamenaya.androidmosaiclayout.listeners.OnItemClickListener;
import com.adhamenaya.androidmosaiclayout.views.BlockPattern.BLOCK_PATTERN;
import com.adhamenaya.androidmosaiclayout.views.MosaicLayout;

import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.Call;
import retrofit.Callback;

public class MainActivity extends Activity implements Callback<ResponseData>{

	private MosaicLayout mMosaicLayout;
	private MyAdapter mAdapater;

	BLOCK_PATTERN pattern1[] = { BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.SMALL, BLOCK_PATTERN.SMALL,
			BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.HORIZONTAL, BLOCK_PATTERN.HORIZONTAL };

	BLOCK_PATTERN pattern2[] = { BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG,
			BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG, BLOCK_PATTERN.BIG };

	private WebServiceProxy mWebServiceProxy;

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

		mWebServiceProxy = new Retrofit.Builder().baseUrl(WebServiceProxy.ENDPOINT)
				.addConverterFactory(GsonConverterFactory.create())
				.build().create(WebServiceProxy.class);


		Call<ResponseData> call = mWebServiceProxy.getImages();

		call.enqueue(this);
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

	@Override
	public void onResponse(Response<ResponseData> response, Retrofit retrofit) {

		if(null == response.body()) return;

		List<Image> result = response.body().getHits();

		if (null != response) {
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
		}
	}

	@Override
	public void onFailure(Throwable t) {
		Toast.makeText(getApplicationContext(),"Error!!",Toast.LENGTH_LONG).show();
	}
}
