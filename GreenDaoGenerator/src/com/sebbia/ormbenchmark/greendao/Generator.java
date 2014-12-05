package com.sebbia.ormbenchmark.greendao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Generator {
	
	public static void main(String[] args) throws Exception {
		Schema schema = new Schema(1, "com.sebbia.ormbenchmark.greendao");
		schema.enableKeepSectionsByDefault();
		Entity entity = schema.addEntity("GreenDaoEntity");
		entity.implementsInterface("com.sebbia.ormbenchmark.BenchmarkEntity");
		entity.addIdProperty().autoincrement();
		entity.addStringProperty("field1");
		entity.addStringProperty("field2");
		entity.addByteArrayProperty("blobArray");
		entity.addDateProperty("date");

		new DaoGenerator().generateAll(schema, "../src-gen/");
	}
}
