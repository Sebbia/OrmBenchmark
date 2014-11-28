package com.sebbia.ormbenchmark.sugarorm;

import java.util.Iterator;
import java.util.List;

import android.content.Context;

import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.util.SugarConfig;
import com.sebbia.ormbenchmark.Benchmark;

public class SugarOrmBenchmark extends Benchmark<SugarOrmEntity> {
	
	@Override
	public void init(Context context) {
		super.init(context);
		SugarContext.init(context);
	}
	
	@Override
	public void dispose(Context context) {
		super.dispose(context);
		SugarContext.terminate();
		context.deleteDatabase("sugarorm.db");
	}

	@Override
	public void saveEntities(List<SugarOrmEntity> entities) {
		for (SugarOrmEntity entity : entities)
			SugarRecord.save(entity);
	}

	@Override
	public void saveEntitiesInTransaction(final List<SugarOrmEntity> entities) {
		SugarRecord.saveInTx(entities);
	}

	@Override
	public int loadEntities() {
		Iterator<SugarOrmEntity> iterator = SugarRecord.findAll(SugarOrmEntity.class);
		int count = 0;
		while (iterator.hasNext()) {
			iterator.next().getBlob();
			count++;
		}
		return count;
	}

	@Override
	public boolean findEntityWithId(long id) {
		SugarOrmEntity entity = SugarRecord.findById(SugarOrmEntity.class, id);
		return entity != null;
	}

	@Override
	public void clearCache() {
		SugarConfig.clearCache();
	}

	@Override
	public String getName() {
		return "Sugar ORM";
	}

	@Override
	public Class<? extends SugarOrmEntity> getEntityClass() {
		return SugarOrmEntity.class;
	}

}
