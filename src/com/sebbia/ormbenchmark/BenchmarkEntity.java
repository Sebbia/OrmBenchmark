package com.sebbia.ormbenchmark;

import java.util.Date;

public interface BenchmarkEntity {
	public void setField1(String field1);
	public void setField2(String field2);
	public void setDate(Date date);
	public void setBlob(Blob blob);
}
