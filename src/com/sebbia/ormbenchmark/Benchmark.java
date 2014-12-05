package com.sebbia.ormbenchmark;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;

import com.sebbia.ormbenchmark.utils.Utils;

public abstract class Benchmark<T extends BenchmarkEntity> {

	public abstract void saveEntitiesInTransaction(List<T> entities);
	public abstract List<T> loadEntities();
	public abstract void clearCache();
	public abstract String getName();
	public abstract Class<?  extends T> getEntityClass();
	
	public void init(Context context) {}
	public void dispose(Context context) {}
	
	List<T> generateEntities(int count) {
		try {
			List<T> entities = new ArrayList<T>();
			Date date = new Date();
			Blob blob = new Blob();
			String field1 = Utils.getRandomString(100);
			String field2 = Utils.getRandomString(100);
			for (int i = 0; i < count; ++i) {
				T benchmarkEntity = getEntityClass().newInstance();
				benchmarkEntity.setField1(field1);
				benchmarkEntity.setField2(field2);
				benchmarkEntity.setBlob(blob);
				benchmarkEntity.setDate(date);
				entities.add(benchmarkEntity);
			}
			return entities;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
