package com.sebbia.ormbenchmark.noorm;

import java.util.Date;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.orm.dsl.Table;
import com.sebbia.ormbenchmark.BenchmarkEntity;
import com.sebbia.ormbenchmark.Blob;
import com.sebbia.ormbenchmark.utils.Utils;

@Table(name = "entity")
public class NoOrmEntity implements BenchmarkEntity {
	
	public static NoOrmEntity fromCursor(Cursor cursor) {
		NoOrmEntity noOrmEntity = new NoOrmEntity();
		noOrmEntity.field1 = cursor.getString(1);
		noOrmEntity.field2 = cursor.getString(2);
		noOrmEntity.blobArray = cursor.getBlob(3);
		noOrmEntity.date = new Date(cursor.getLong(4));
		return noOrmEntity;
	}
	
	private String field1;
	private String field2;
	private byte[] blobArray;
	private Blob blob;
	private Date date;

	public NoOrmEntity() {

	}

	@Override
	public void setField1(String field1) {
		this.field1 = field1;
	}

	@Override
	public void setField2(String field2) {
		this.field2 = field2;
	}

	@Override
	public void setBlob(Blob blob) {
		this.blob = blob;
		blobArray = Utils.serialize(blob);
	}

	@Override
	public String getField1() {
		return field1;
	}

	@Override
	public String getField2() {
		return field2;
	}

	@Override
	public Blob getBlob() {
		if (blob == null)
			blob = Utils.deserialize(blobArray);
		return blob;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public Date getDate() {
		return date;
	}
	
	public void bindToStatement(SQLiteStatement statement) {
		statement.bindString(1, field1);
		statement.bindString(2, field2);
		statement.bindBlob(3, blobArray);
		statement.bindLong(4, date.getTime());
	}

}
