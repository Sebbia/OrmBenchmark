package com.sebbia.ormbenchmark;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import junit.framework.TestCase;
import android.os.AsyncTask;

import com.activeandroid.util.Log;
import com.sebbia.ormbenchmark.activeandroid.sebbia.ActiveAndroidSebbiaBenchmark;
import com.sebbia.ormbenchmark.activeandroid.ActiveAndroidBenchmark;
import com.sebbia.ormbenchmark.greendao.GreenDaoBenchmark;
import com.sebbia.ormbenchmark.noorm.NoOrmBenchmark;
import com.sebbia.ormbenchmark.noorm.NoOrmBenchmarkMultipleInsertStatement;
import com.sebbia.ormbenchmark.ormlite.OrmLiteBenchmark;
import com.sebbia.ormbenchmark.sugarorm.SugarOrmBenchmark;
import com.sebbia.ormbenchmark.utils.TimeMeasure;

public class BenchmarkExecutor {

	public static Benchmark<?>[] BENCHMARKS = {
			new NoOrmBenchmark(),
			new NoOrmBenchmarkMultipleInsertStatement(),
			new GreenDaoBenchmark(),
			new OrmLiteBenchmark(),
			new ActiveAndroidBenchmark(),
			new SugarOrmBenchmark(),
			new ActiveAndroidSebbiaBenchmark()
	};

	public interface BenchmarkExecutorListener {
		public void onBenchmarkStarted(String name);

		public void onBenchmarkFinished(String name, List<TimeMeasure> results);
	}

	private static final String[] CSV_HEADER = { "Name", "Save in transaction", "Load" };
	private static final int ENTITIES_COUNT = 1000;
	private static final int PASSES = 10;

	private Map<String, List<TimeMeasure>> results;
	private WeakReference<BenchmarkExecutorListener> listener;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void execute() {
		results = new ConcurrentHashMap<String, List<TimeMeasure>>();
		new AsyncTask<Void, String, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				for (Benchmark<? extends BenchmarkEntity> benchmark : BENCHMARKS) {
					publishProgress(benchmark.getName());
					List<TimeMeasure> results = new ArrayList<TimeMeasure>();
					for (int i = 0; i < PASSES; ++i) {
						benchmark.init(BenchmarkApp.getInstance());
	
						List entities = benchmark.generateEntities(ENTITIES_COUNT);
	
						TimeMeasure saveEntitiesInTransaction = new TimeMeasure(R.string.save_entities_in_transaction);
						benchmark.saveEntitiesInTransaction(entities);
						results.add(saveEntitiesInTransaction.end());
	
						benchmark.clearCache();
						TimeMeasure loadEntities = new TimeMeasure(R.string.load_entities);
						entities = benchmark.loadEntities();
						results.add(loadEntities.end());
	
						TestCase.assertEquals(ENTITIES_COUNT, entities.size());
						for (Object entity : entities) {
							BenchmarkEntity benchmarkEntity = ((BenchmarkEntity) entity);
							TestCase.assertEquals(100, benchmarkEntity.getField1().length());
							TestCase.assertEquals(100, benchmarkEntity.getField2().length());
							TestCase.assertNotNull(benchmarkEntity.getDate());
							TestCase.assertEquals(100, benchmarkEntity.getBlob().getBackingArray().length);
							TestCase.assertEquals(100, benchmarkEntity.getBlob().getField().length());
						}
	
						benchmark.dispose(BenchmarkApp.getInstance());
					}
					List<TimeMeasure> medianResults = new ArrayList<TimeMeasure>();
					medianResults.add(new TimeMeasure(R.string.save_entities_in_transaction, results));
					medianResults.add(new TimeMeasure(R.string.load_entities, results));
					
					BenchmarkExecutor.this.results.put(benchmark.getName(), medianResults);
					publishProgress(benchmark.getName());
				}
				return null;
			}

			@Override
			protected void onProgressUpdate(String... values) {
				super.onProgressUpdate(values);
				if (listener.get() != null) {
					if (results.containsKey(values[0]))
						listener.get().onBenchmarkFinished(values[0], results.get(values[0]));
					else
						listener.get().onBenchmarkStarted(values[0]);
				}
			}

		}.execute();
	}

	public void generateCSV(File file) throws IOException {
		if (file.exists())
			file.delete();

		if (file.getParentFile() != null)
			file.getParentFile().mkdirs();

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < CSV_HEADER.length; ++i)
			stringBuilder.append(CSV_HEADER[i]).append(i == CSV_HEADER.length - 1 ? "\n" : ",");

		for (String key : results.keySet()) {
			List<TimeMeasure> measures = results.get(key);
			stringBuilder.append(key).append(",");
			for (int i = 0; i < measures.size(); ++i)
				stringBuilder.append(measures.get(i).getMsec()).append(i == measures.size() - 1 ? "\n" : ",");
		}

		Log.i(stringBuilder.toString());

		FileOutputStream outputStream = new FileOutputStream(file);
		outputStream.write(stringBuilder.toString().getBytes());
		outputStream.flush();
		outputStream.close();
	}

	public void setBenchmarkExecutorListener(BenchmarkExecutorListener listener) {
		this.listener = new WeakReference<BenchmarkExecutorListener>(listener);
	}

	public Map<String, List<TimeMeasure>> getResults() {
		return results;
	}

}
