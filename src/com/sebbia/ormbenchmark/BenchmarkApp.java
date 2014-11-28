package com.sebbia.ormbenchmark;

import android.app.Application;

public class BenchmarkApp extends Application {
	
	private static BenchmarkApp instance;
	
	public static BenchmarkApp getInstance() {
		return instance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}
}
