package com.sebbia.ormbenchmark;

import java.io.Serializable;

import com.sebbia.ormbenchmark.utils.Utils;

public class Blob implements Serializable {

	private static final long serialVersionUID = 782110244341183915L;
	
	String valueA;
	String valueB;
	byte[] data;
	
	public Blob() {
		valueA = Utils.getRandomString(100);
		valueB = Utils.getRandomString(50);
		data = Utils.getRandomArray(200);
	}
	
}
