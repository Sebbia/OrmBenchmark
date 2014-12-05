package com.sebbia.ormbenchmark.utils;

import java.util.concurrent.TimeUnit;

import com.sebbia.ormbenchmark.BenchmarkApp;

import android.support.v4.util.TimeUtils;


public class TimeMeasure {
	private long start;
	private long stop;
	private long msec;
	private int actionRes;
	private String result;
	
	public TimeMeasure(int actionRes) {
		this.start = System.nanoTime();
		this.actionRes = actionRes;
	}
	
	public TimeMeasure end() {
		this.stop = System.nanoTime();
		this.msec = TimeUnit.MILLISECONDS.convert(getNanoseconds(), TimeUnit.NANOSECONDS);
		
		StringBuilder stringBuilder = new StringBuilder();
		TimeUtils.formatDuration(msec, stringBuilder);
		this.result = stringBuilder.toString();
		
		Log.i(BenchmarkApp.getInstance().getString(actionRes) + " took " + result + " " + getNanoseconds() + " nanoseconds.");
		return this;
	}
	
	public int getActionRes() {
		return actionRes;
	}
	
	public String getResult() {
		return result;
	}
	
	public long getNanoseconds() {
		return stop - start;
	}
	
	public long getMsec() {
		return msec;
	}
	
	public void setMsec(long msec) {
		this.msec = msec;
	}
}
