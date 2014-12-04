package com.sebbia.ormbenchmark.sugarorm;

import java.util.Date;

import com.orm.dsl.Ignore;
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
	private byte[] blobArray;
	@Ignore
	private Blob blob;
	private Date date;

	public SugarOrmEntity() {

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
}
