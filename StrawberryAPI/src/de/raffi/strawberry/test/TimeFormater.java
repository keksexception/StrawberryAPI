package de.raffi.strawberry.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormater {
	
	private long millis;
	private String date;
	private String time;
	private String seconds;
	private String hours;
	private String min;
	private String month;
	private String day;
	public TimeFormater(long millis) {
		this.millis = millis;
		Date date = new Date(millis);
		String mm_dd = new SimpleDateFormat("dd.MM.YYYY").format(date);
		String hour_min = new SimpleDateFormat("HH:mm:ss").format(date);	
		this.date = mm_dd;
		time = hour_min;	
		seconds = hour_min.split(":")[2];
		min = hour_min.split(":")[1];
		hours = hour_min.split(":")[0];
		month = mm_dd.split("\\.")[1];
		day = mm_dd.split("\\.")[0];
	}
	public long getMillis() {
		return millis;
	}
	public String getDate() {
		return date;
	}
	public int getMonth() {
		return Integer.parseInt(month);
	}
	public int getDay() {
		return Integer.parseInt(day);
	}
	/**
	 * 
	 * @return hour_min
	 */
	public String getTime() {
		return time;
	}
	public int getSeconds() {
		return Integer.parseInt(seconds);
	}
	public int getMinutes() {
		return Integer.parseInt(min);
	}
	public int getHours() {
		return Integer.parseInt(hours);
	}
	/**
	 * 
	 * @return dd.MM.YYYY HH:mm:ss
	 */
	public String getComplete() {
		return getDate() + " " + getTime();
	}
}
