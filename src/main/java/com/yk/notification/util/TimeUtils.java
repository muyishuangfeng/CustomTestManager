package com.yk.notification.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class TimeUtils {

	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static final SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat(
			"HH:mm");

	private TimeUtils() {
		throw new AssertionError();
	}

	/**
	 * long time to string
	 * 
	 * @param timeInMillis
	 * @param dateFormat
	 * @return
	 */
	public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
		return dateFormat.format(new Date(timeInMillis));
	}

	/**
	 * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
	 * 
	 * @param timeInMillis
	 * @return
	 */
	public static String getTime(long timeInMillis) {
		return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
	}
	/**
	 * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
	 *
	 * @param timeInMillis
	 * @return
	 */
	public static String getCurrentTime(long timeInMillis) {
		return getTime(timeInMillis, DATE_FORMAT_TIME);
	}

	/**
	 * get current time in milliseconds
	 * 
	 * @return
	 */
	public static long getCurrentTimeInLong() {
		return System.currentTimeMillis();
	}

	/**
	 * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
	 * 
	 * @return
	 */
	public static String getCurrentTimeInString() {
		return getTime(getCurrentTimeInLong());
	}

	/**
	 * get current time in milliseconds
	 * 
	 * @return
	 */
	public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
		return getTime(getCurrentTimeInLong(), dateFormat);
	}

	/**
	 * UTC转换为本地时间
	 * 
	 * @param dateTime
	 * @return
	 */
	public static String utc2Local(String dateTime) {
		dateTime = DEFAULT_DATE_FORMAT.format(new Date(
				(Long.valueOf(dateTime)) * 1000));
		return dateTime;
	}
}
