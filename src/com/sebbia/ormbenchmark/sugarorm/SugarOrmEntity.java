package com.sebbia.ormbenchmark.sugarorm;

import java.util.Date;

import com.orm.dsl.Table;
import com.sebbia.ormbenchmark.BenchmarkEntity;
import com.sebbia.ormbenchmark.Blob;
import com.sebbia.ormbenchmark.utils.Utils;

@Table(name = "entity")
public class SugarOrmEntity implements BenchmarkEntity {
	@SuppressWarnings("unused")
	private Long id;
	private String field1;
	private String field2;
	private Date date;
	private byte[] blob;

	public SugarOrmEntity() {

	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setBlob(Blob blob) {
		this.blob = Utils.serialize(blob);
	}
	
	public Blob getBlob() {
		return Utils.deserialize(blob);
	}
}
