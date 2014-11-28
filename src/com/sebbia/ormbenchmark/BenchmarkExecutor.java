package com.sebbia.ormbenchmark;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.os.AsyncTask;

import com.sebbia.ormbenchmark.activeandroid.ActiveAndroidBenchmark;
import com.sebbia.ormbenchmark.activeandroid.sebbia.ActiveAndroidSebbiaBenchmark;
import com.sebbia.ormbenchmark.utils.TimeMeasure;

public class BenchmarkExecutor {
	public static Benchmark<?>[] BENCHMARKS = {
//		new OrmLiteBenchmark(), 
		new ActiveAndroidBenchmark(),
//		new SugarOrmBenchmark(),
		new ActiveAndroidSebbiaBenchmark()
	};

	public interface BenchmarkExecutorListener {
		public void onBenchmarkStarted(String name);

		public void onBenchmarkFinished(String name, List<TimeMeasure> results);
	}

	private static final int ENTITIES_COUNT = 1000;


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
					benchmark.init(BenchmarkApp.getInstance());
					
					List<TimeMeasure> results = new ArrayList<TimeMeasure>();
					List entities = benchmark.generateEntities(ENTITIES_COUNT);

					TimeMeasure saveEntities = new TimeMeasure(R.string.save_entities);
					benchmark.saveEntities(entities);
					results.add(saveEntities.end());

					TimeMeasure saveEntitiesInTransaction = new TimeMeasure(R.string.save_entities_in_transaction);
					benchmark.saveEntitiesInTransaction(entities);
					results.add(saveEntitiesInTransaction.end());

					benchmark.clearCache();
					TimeMeasure loadEntities = new TimeMeasure(R.string.load_entities);
					benchmark.loadEntities();
					results.add(loadEntities.end());
					
					
//					TimeMeasure loadEntityWithId = new TimeMeasure(R.string.load_entity_by_id);
//					loadEntityWithId.end();

					benchmark.dispose(BenchmarkApp.getInstance());
					BenchmarkExecutor.this.results.put(benchmark.getName(), results);
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

	public void setBenchmarkExecutorListener(BenchmarkExecutorListener listener) {
		this.listener = new WeakReference<BenchmarkExecutorListener>(listener);
	}
	
	public Map<String, List<TimeMeasure>> getResults() {
		return results;
	}

}
