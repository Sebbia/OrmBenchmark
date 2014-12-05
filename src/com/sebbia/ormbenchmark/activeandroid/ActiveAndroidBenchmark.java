package com.sebbia.ormbenchmark.activeandroid;

import java.util.List;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.Configuration;
import com.activeandroid.query.Select;
import com.sebbia.ormbenchmark.Benchmark;

public class ActiveAndroidBenchmark extends Benchmark<ActiveAndroidEntity> {
	
	@Override
	public void init(Context context) {
		Configuration configuration = new Configuration.Builder(context)
		.setDatabaseName("activeandroid")
		.create();
		
		ActiveAndroid.initialize(configuration);
	}
	
	@Override
	public void dispose(Context context) {
		super.dispose(context);
		ActiveAndroid.dispose();
		context.deleteDatabase("activeandroid");
	}

	@Override
	public void saveEntitiesInTransaction(List<ActiveAndroidEntity> entities) {
		try {
			ActiveAndroid.getDatabase().beginTransaction();
			for (ActiveAndroidEntity entity : entities) {
				entity.save();
			}
			ActiveAndroid.getDatabase().setTransactionSuccessful();
		} finally {
			ActiveAndroid.getDatabase().endTransaction();
		}
	}

	@Override
	public List<ActiveAndroidEntity> loadEntities() {
		return new Select().from(ActiveAndroidEntity.class).execute();
	}

	@Override
	public void clearCache() {
		Cache.clear();
	}

	@Override
	public String getName() {
		return "ActiveAndroid";
	}

	@Override
	public Class<? extends ActiveAndroidEntity> getEntityClass() {
		return ActiveAndroidEntity.class;
	}

	

}
