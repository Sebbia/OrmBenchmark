package com.sebbia.ormbenchmark;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sebbia.ormbenchmark.BenchmarkExecutor.BenchmarkExecutorListener;
import com.sebbia.ormbenchmark.utils.TimeMeasure;

public class BenchmarkActivity extends Activity implements BenchmarkExecutorListener {
	
	private TextView statusTextView;
	private TextView resultsTextView;
	private ListView resultsListView;
	private Button startButton;
	private BenchmarkExecutor executor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.benchmark_activity);
		
		statusTextView = (TextView) findViewById(R.id.statusTextView);
		resultsListView = (ListView) findViewById(R.id.resultsListView);
		resultsTextView = (TextView) findViewById(R.id.resultsTextView);
		startButton = (Button) findViewById(R.id.startButton);
		
		startButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				start();
			}
		});
	}
	
	private void start() {
		executor = new BenchmarkExecutor();
		executor.setBenchmarkExecutorListener(this);
		executor.execute();
	}

	@Override
	public void onBenchmarkStarted(String name) {
		startButton.setEnabled(false);
		statusTextView.setText(getString(R.string.running, name));
	}

	@Override
	public void onBenchmarkFinished(String name, List<TimeMeasure> results) {
		updateResultsList();
		
		if (executor.getResults().size() == BenchmarkExecutor.BENCHMARKS.length) {
			statusTextView.setText(R.string.idle);
			startButton.setEnabled(true);
		}
	}
	
	private void updateResultsList() {
		List<String> listItems = new ArrayList<String>();
		for (String executorName : executor.getResults().keySet()) {
			StringBuilder itemBuilder = new StringBuilder();
			List<TimeMeasure> executorResults = executor.getResults().get(executorName);
			itemBuilder.append(executorName).append(":\n");
			for (TimeMeasure timeMeasure : executorResults)
				itemBuilder.append(getString(timeMeasure.getActionRes()) + " - " + timeMeasure.getResult()).append("\n");
			listItems.add(itemBuilder.toString());
		}
		
		resultsListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems));
		resultsTextView.setVisibility(listItems.size() > 0 ? View.VISIBLE : View.GONE);
	}
	
}
