package com.sebbia.ormbenchmark.noorm;

import java.util.List;

import android.content.Context;

import com.sebbia.ormbenchmark.Benchmark;

public class NoOrmBenchmark extends Benchmark<NoOrmEntity> {
	
	private NoOrmSQLiteHelper sqLiteHelper;
	
	@Override
	public void init(Context context) {
		super.init(context);
		context.deleteDatabase("no_orm");
		sqLiteHelper = new NoOrmSQLiteHelper(context);
	}
	
	@Override
	public void dispose(Context context) {
		super.dispose(context);
		sqLiteHelper.close();
		context.deleteDatabase("no_orm");
	}

	@Override
	public void saveEntities(List<NoOrmEntity> entities) {
		for (NoOrmEntity entity : entities)
			sqLiteHelper.insert(entity);
	}

	@Override
	public void saveEntitiesInTransaction(List<NoOrmEntity> entities) {
		sqLiteHelper.insertInTransaction(entities);
	}

	@Override
	public List<NoOrmEntity> loadEntities() {
		return sqLiteHelper.getAllEntities();
	}

	@Override
	public boolean findEntityWithId(long id) {
		return sqLiteHelper.findEntity(id) == null;
	}

	@Override
	public void clearCache() {
		
	}

	@Override
	public String getName() {
		return "SQLiteOpenHelper";
	}

	@Override
	public Class<? extends NoOrmEntity> getEntityClass() {
		return NoOrmEntity.class;
	}

}
