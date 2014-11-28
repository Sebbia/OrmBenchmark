package com.sebbia.ormbenchmark.activeandroid;

import java.io.Serializable;

import com.activeandroid.serializer.TypeSerializer;
import com.sebbia.ormbenchmark.Blob;
import com.sebbia.ormbenchmark.utils.Utils;

public class BlobSerializer extends TypeSerializer {

	@Override
	public Class<?> getDeserializedType() {
		return Blob.class;
	}

	@Override
	public Class<?> getSerializedType() {
		return Byte[].class;
	}

	@Override
	public Object serialize(Object data) {
		return Utils.serialize((Serializable) data);
	}

	@Override
	public Object deserialize(Object data) {
		return Utils.deserialize((byte[]) data);
	}

}
