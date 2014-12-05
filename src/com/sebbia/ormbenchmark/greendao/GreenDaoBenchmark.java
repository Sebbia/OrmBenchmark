package com.sebbia.ormbenchmark.greendao;

import java.util.List;

import android.content.Context;

import com.sebbia.ormbenchmark.Benchmark;
import com.sebbia.ormbenchmark.greendao.DaoMaster.DevOpenHelper;

public class GreenDaoBenchmark extends Benchmark<GreenDaoEntity> {
	
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private GreenDaoEntityDao dao;
	
	@Override
	public void init(Context context) {
		super.init(context);
		context.deleteDatabase("greendao");
		DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "greendao", null);
		daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
		daoSession = daoMaster.newSession();
		dao = daoSession.getGreenDaoEntityDao();
	}
	
	@Override
	public void dispose(Context context) {
		super.dispose(context);
		context.deleteDatabase("greendao");
		daoSession.clear();
	}

	@Override
	public void saveEntitiesInTransaction(final List<GreenDaoEntity> entities) {
		dao.insertInTx(entities);;
	}

	@Override
	public List<GreenDaoEntity> loadEntities() {
		return dao.loadAll();
	}

	@Override
	public void clearCache() {
		daoSession.clear();
	}

	@Override
	public String getName() {
		return "Green Dao";
	}

	@Override
	public Class<? extends GreenDaoEntity> getEntityClass() {
		return GreenDaoEntity.class;
	}

}
