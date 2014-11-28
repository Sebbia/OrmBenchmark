package com.sebbia.ormbenchmark.ormlite;

import java.sql.SQLException;

import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sebbia.ormbenchmark.BenchmarkApp;
import com.sebbia.ormbenchmark.utils.Log;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static DatabaseHelper instance;

	public static DatabaseHelper getInstance() {
		if (instance == null)
			instance = new DatabaseHelper();
		return instance;
	}

	private DatabaseHelper() {
		super(BenchmarkApp.getInstance(), "ormlite", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, OrmLiteEntity.class);
		} catch (SQLException e) {
			Log.e("Failed to create ormlite database", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, OrmLiteEntity.class, true);
			onCreate(db);
		} catch (SQLException e) {
			Log.e("Failed to create ormlite database", e);
		}
	}

	@Override
	public <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) {
		try {
			D dao = super.getDao(clazz);
//			dao.setObjectCache(true);
			return dao;
		} catch (SQLException e) {
			Log.e("Failed to get dao", e);
			return null;
		}
	}

}
