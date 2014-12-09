package com.sebbia.ormbenchmark.noorm;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;



public class NoOrmSQLiteHelperMultipleInsertStatement extends SQLiteOpenHelper {

	static final class EntityTable {
		static final String TABLE = "entity";
		static final String ID = "id";
		static final String FIELD_1 = "field1";
		static final String FIELD_2 = "field2";
		static final String BLOB = "blob";
		static final String DATE = "date";
	}

	private static final int DATABASE_VERSION = 1;

	private SQLiteStatement insertStatement;
	private SQLiteStatement multipleInsertStatement;
	
	private int entitiesInMultipleInsert;


	public NoOrmSQLiteHelperMultipleInsertStatement(Context context, int entitiesInMultipleInsert) {
		super(context, "no_orm_multiple", null, DATABASE_VERSION);
		createInsertStatement(entitiesInMultipleInsert);
		this.entitiesInMultipleInsert = entitiesInMultipleInsert;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ EntityTable.TABLE
				+ " (" + EntityTable.ID + " INTEGER PRIMARY KEY AUTOINCREMENT"
				+ ", " + EntityTable.FIELD_1 + " TEXT"
				+ ", " + EntityTable.FIELD_2 + " TEXT"
				+ ", " + EntityTable.BLOB + " BLOB"
				+ ", " + EntityTable.DATE + " INTEGER"
				+ ")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + EntityTable.TABLE);
		onCreate(db);
	}

	public void insertInTransaction(List<NoOrmEntity> entities) {
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.beginTransaction();
			int i=0;
			while (i<entities.size()) {
				multipleInsertStatement.clearBindings();
				for (int j=0; j< entitiesInMultipleInsert; j++) {
					entities.get(i+j).bindToMultipleStatement(multipleInsertStatement,j);
				}
				i += entitiesInMultipleInsert;
				multipleInsertStatement.executeInsert();
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	
	public void insert(NoOrmEntity entity) {
		insertStatement.clearBindings();
		entity.bindToStatement(insertStatement);
		insertStatement.executeInsert();
	}

	public List<NoOrmEntity> getAllEntities() {
		List<NoOrmEntity> result = new ArrayList<NoOrmEntity>();
		Cursor cursor = null;
		try {
			cursor = getReadableDatabase().query(EntityTable.TABLE, null, null, null, null, null, null);
			while (cursor.moveToNext()) {
				result.add(NoOrmEntity.fromCursor(cursor));
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return result;
	}
	
	public NoOrmEntity findEntity(long id) {
		NoOrmEntity entity = null;
		Cursor cursor = null;
		try {
			cursor = getReadableDatabase().query(EntityTable.TABLE, null, EntityTable.ID + " =?", new String[]{ String.valueOf(id) }, null, null, null);
			if (cursor.moveToNext())
				entity = NoOrmEntity.fromCursor(cursor);
		} finally {
			cursor.close();
		}
		return entity;
	}

	private void createInsertStatement(int entitiesInMultipleInsert) {
		insertStatement = getWritableDatabase().compileStatement(
				"INSERT INTO " + EntityTable.TABLE
						+ " (" + EntityTable.FIELD_1
						+ "," + EntityTable.FIELD_2
						+ "," + EntityTable.BLOB
						+ "," + EntityTable.DATE + ")"
						+ " VALUES (?, ?, ?, ?)");
		String multipleInsertSQL = "INSERT INTO " + EntityTable.TABLE
				+ " (" + EntityTable.FIELD_1
				+ "," + EntityTable.FIELD_2
				+ "," + EntityTable.BLOB
				+ "," + EntityTable.DATE + ")"
				+ " VALUES (?, ?, ?, ?)";
		for (int i=1; i<entitiesInMultipleInsert; i++) {
			multipleInsertSQL += ", (?, ?, ?, ?)";
		}
		multipleInsertStatement = getWritableDatabase().compileStatement(multipleInsertSQL);
		
	}
}