package com.sebbia.ormbenchmark.activeandroid.sebbia;

import java.util.List;

import android.content.Context;

import com.activeandroid.sebbia.ActiveAndroid;
import com.activeandroid.sebbia.Cache;
import com.activeandroid.sebbia.Configuration;
import com.activeandroid.sebbia.Model;
import com.activeandroid.sebbia.query.Select;
import com.sebbia.ormbenchmark.Benchmark;

public class ActiveAndroidSebbiaBenchmark extends Benchmark<ActiveAndroidSebbiaEntity> {
	
	@Override
	public void init(Context context) {
		context.deleteDatabase("activeandroid_sebbia");
		Configuration configuration = new Configuration.Builder(context)
		.setDatabaseName("activeandroid_sebbia")
		.addTypeSerializer(BlobTypeSerializer.class)
		.create();
		ActiveAndroid.initialize(configuration);
	}
	
	@Override
	public void dispose(Context context) {
		super.dispose(context);
		ActiveAndroid.dispose();
		context.deleteDatabase("activeandroid_sebbia");
	}

	@Override
	public void saveEntities(List<ActiveAndroidSebbiaEntity> entities) {
		for (ActiveAndroidSebbiaEntity entity : entities) {
			entity.save();
		}
	}

	@Override
	public void saveEntitiesInTransaction(List<ActiveAndroidSebbiaEntity> entities) {
		try {
			ActiveAndroid.getDatabase().beginTransaction();
			Model.saveMultiple(entities);
			ActiveAndroid.getDatabase().setTransactionSuccessful();
		} finally {
			ActiveAndroid.getDatabase().endTransaction();
		}
	}

	@Override
	public List<ActiveAndroidSebbiaEntity> loadEntities() {
		return new Select().from(ActiveAndroidSebbiaEntity.class).execute();
	}

	@Override
	public boolean findEntityWithId(long id) {
		return Model.load(ActiveAndroidSebbiaEntity.class, id) != null;
	}

	@Override
	public void clearCache() {
		Cache.clear();
	}

	@Override
	public String getName() {
		return "ActiveAndroid.Sebbia";
	}

	@Override
	public Class<? extends ActiveAndroidSebbiaEntity> getEntityClass() {
		return ActiveAndroidSebbiaEntity.class;
	}

	

}
