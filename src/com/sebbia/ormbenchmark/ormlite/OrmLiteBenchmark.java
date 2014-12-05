package com.sebbia.ormbenchmark.ormlite;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.sebbia.ormbenchmark.Benchmark;

public class OrmLiteBenchmark extends Benchmark<OrmLiteEntity> {
	
	@Override
	public void init(Context context) {
		super.init(context);
		context.deleteDatabase("ormlite");
		DatabaseHelper.getInstance();
	}
	
	@Override
	public void dispose(Context context) {
		super.dispose(context);
		DatabaseHelper.dispose();
		context.deleteDatabase("ormlite");
	}

	@Override
	public void saveEntitiesInTransaction(final List<OrmLiteEntity> entities) {
		final Dao<OrmLiteEntity, Long> dao = DatabaseHelper.getInstance().getDao(OrmLiteEntity.class);
		
		try {
			TransactionManager.callInTransaction(DatabaseHelper.getInstance().getConnectionSource(), new Callable<Void>() {
	
				@Override
				public Void call() throws Exception {
					for (OrmLiteEntity entity : entities) {
						dao.createIfNotExists(entity);
					}
					return null;
				}
				
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<OrmLiteEntity> loadEntities() {
		Dao<OrmLiteEntity, Long> dao = DatabaseHelper.getInstance().getDao(OrmLiteEntity.class);
		try {
			List<OrmLiteEntity> entities = dao.queryForAll();
			return entities;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void clearCache() {
		Dao<OrmLiteEntity, Long> dao = DatabaseHelper.getInstance().getDao(OrmLiteEntity.class);
		dao.clearObjectCache();
	}

	@Override
	public String getName() {
		return "ORMLite";
	}

	@Override
	public Class<? extends OrmLiteEntity> getEntityClass() {
		return OrmLiteEntity.class;
	}
	
}
