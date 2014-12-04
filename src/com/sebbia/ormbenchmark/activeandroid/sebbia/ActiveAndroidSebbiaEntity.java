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
	@Column(name = "blob")
	Blob blob;
	@Column(name = "date")
	Date date;
	
	public ActiveAndroidSebbiaEntity() {

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
