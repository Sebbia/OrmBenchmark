package com.sebbia.ormbenchmark.activeandroid.sebbia;

import java.io.Serializable;

import com.activeandroid.sebbia.serializer.TypeSerializer;
import com.sebbia.ormbenchmark.Blob;
import com.sebbia.ormbenchmark.utils.Utils;

public class BlobTypeSerializer extends TypeSerializer {

	public BlobTypeSerializer() {
	}

	@Override
	public Object deserialize(Object byteArray) {
		return Utils.deserialize((byte[]) byteArray);
	}

	@Override
	public Class<?> getDeserializedType() {
		return Blob.class;
	}

	@Override
	public Class<?> getSerializedType() {
		return byte[].class;
	}

	@Override
	public Object serialize(Object blob) {
		return Utils.serialize((Serializable) blob);
	}

}
