package com.sebbia.ormbenchmark;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;

import com.sebbia.ormbenchmark.utils.Utils;

public abstract class Benchmark<T extends BenchmarkEntity> {

	public abstract void saveEntities(List<T> entities);
	public abstract void saveEntitiesInTransaction(List<T> entities);
	public abstract int loadEntities();
	public abstract boolean findEntityWithId(long id);
	public abstract void clearCache();
	public abstract String getName();
	public abstract Class<? extends T> getEntityClass();
	
	public void init(Context context) {}
	public void dispose(Context context) {}
	
	List<T> generateEntities(int count) {
		try {
			List<T> entities = new ArrayList<T>();
			for (int i = 0; i < count; ++i) {
				T benchmarkEntity = getEntityClass().newInstance();
				benchmarkEntity.setField1(Utils.getRandomString(100));
				benchmarkEntity.setField2(Utils.getRandomString(100));
				benchmarkEntity.setDate(new Date(System.currentTimeMillis() + count * 100));
				benchmarkEntity.setBlob(new Blob());
				entities.add(benchmarkEntity);
			}
			return entities;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
