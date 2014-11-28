package com.sebbia.ormbenchmark.ormlite;

import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.sebbia.ormbenchmark.BenchmarkEntity;
import com.sebbia.ormbenchmark.Blob;

@DatabaseTable(tableName = "entity")
public class OrmLiteEntity implements BenchmarkEntity {
	@DatabaseField(columnName = "id", generatedId = true)
	private long id;
	@DatabaseField(columnName = "field1")
	private String field1;
	@DatabaseField(columnName = "field2")
	private String field2;
	@DatabaseField(columnName = "date", dataType = DataType.DATE)
	private Date date;
	@DatabaseField(columnName = "blob", dataType = DataType.SERIALIZABLE)
	private Blob blob;

	public OrmLiteEntity() {

	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setBlob(Blob blob) {
		this.blob = blob;
	}

}
