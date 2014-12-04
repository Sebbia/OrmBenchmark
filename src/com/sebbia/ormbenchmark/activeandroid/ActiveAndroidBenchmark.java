package com.sebbia.ormbenchmark.activeandroid;

import java.util.List;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.Configuration;
import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.sebbia.ormbenchmark.Benchmark;
import com.sebbia.ormbenchmark.sugarorm.SugarOrmEntity;

public class ActiveAndroidBenchmark extends Benchmark<ActiveAndroidEntity> {
	
	@Override
	public void init(Context context) {
		Configuration configuration = new Configuration.Builder(context)
		.setDatabaseName("activeandroid")
		.addTypeSerializer(BlobTypeSerializer.class)
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
	public void saveEntities(List<ActiveAndroidEntity> entities) {
		for (ActiveAndroidEntity entity : entities) {
			entity.save();
		}
	}

	@Override
	public void saveEntitiesInTransaction(List<ActiveAndroidEntity> entities) {
		try {
			ActiveAndroid.getDatabase().beginTransaction();
			saveEntities(entities);
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
	public boolean findEntityWithId(long id) {
		return Model.load(ActiveAndroidEntity.class, id) != null;
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
