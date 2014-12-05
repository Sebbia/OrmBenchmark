package com.sebbia.ormbenchmark.sugarorm;

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
		context.deleteDatabase("sugarorm.db");
		SugarContext.init(context);
	}
	
	@Override
	public void dispose(Context context) {
		super.dispose(context);
		SugarContext.terminate();
		context.deleteDatabase("sugarorm.db");
	}

	@Override
	public void saveEntitiesInTransaction(final List<SugarOrmEntity> entities) {
		SugarRecord.saveInTx(entities);
	}

	@Override
	public List<SugarOrmEntity> loadEntities() {
		List<SugarOrmEntity> entities = SugarRecord.listAll(SugarOrmEntity.class);
		return entities;
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
