package com.sebbia.ormbenchmark;

import java.io.Serializable;

import com.sebbia.ormbenchmark.utils.Utils;

public class Blob	implements
					Serializable {

	private static final long serialVersionUID = 782110244341183915L;

	private String field;
	private byte[] backingArray;

	public Blob() {
		field = Utils.getRandomString(100);
		backingArray = Utils.getRandomArray(100);
	}

	public String getField() {
		return field;
	}

	public byte[] getBackingArray() {
		return backingArray;
	}
}
