package com.sebbia.ormbenchmark.activeandroid.sebbia;

import java.util.Date;

import com.activeandroid.sebbia.Model;
import com.activeandroid.sebbia.annotation.Column;
import com.activeandroid.sebbia.annotation.Table;
import com.sebbia.ormbenchmark.BenchmarkEntity;
import com.sebbia.ormbenchmark.Blob;

@Table(name = "entity")
public class ActiveAndroidSebbiaEntity extends Model implements BenchmarkEntity {
	@Column(name = "field1")
	String field1;
	@Column(name = "field2")
	String field2;
	@Column(name = "date")
	Date date;
	@Column(name = "blob")
	Blob blob;

	public ActiveAndroidSebbiaEntity() {

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
