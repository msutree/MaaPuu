package com.maapuu.mereca.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jia on 2018/1/8 0008.
 */
public class DateUtil {
	public static String FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";
	public static String FORMAT_DATE = "yyyy-MM-dd";
	public static String FORMAT_ALL = "yyyy-MM-dd HH:mm:ss SSS";
	public static String FORMAT_YEAR_MONTH = "yyyy-MM";
	/**
	 * 获取当前时间并转换格式
	 * dateformat值一般为： "yyyy-MM-dd HH:mm:ss"
	 */
	public static String getNowDate(String dateFormat) {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(dt);
	}

	/**
	 * 获取当前时间并转换格式
	 * 输出格式为： "yyyy-MM-dd"
	 */
	public static String getNowDate() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		return sdf.format(dt);
	}

	/**
	 * 获取当前时间并转换格式
	 * 输出格式为： "HH:mm"
	 */
	public static String getNowTime() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(dt);
	}

	/**
	 * 获取当前时间并转换格式
	 * 输出格式为： "yyyy-MM-dd HH:mm:ss SSS"
	 */
	public static String getTimeMillisecond() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_ALL);
		return sdf.format(dt);
	}

	/**
	 *Date 转化Calendar
	 */
	public static Calendar date2Calendar(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 *Calendar转化Date
	 */
	public static Date calendar2Date(Calendar calendar){
		return calendar.getTime();
	}

	/**
	 * Calendar 转化 String
	 */
	public static String calendar2String(Calendar calendar){
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		return sdf.format(calendar.getTime());
	}

	public static String calendar2String(Calendar calendar, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(calendar.getTime());
	}

	/**
	 * String 转化Calendar
	 * eg. date="2010-5-27";
	 */
	public static Calendar String2Calendar(String date){
		SimpleDateFormat sdf= new SimpleDateFormat(FORMAT_DATE);
		Date dt = null;
		try {
			dt = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);

		return calendar;
	}

	/**
	 * Date 转化String
	 */
	public static String date2String(Date date){
		SimpleDateFormat sdf= new SimpleDateFormat(FORMAT_DATE);
		return sdf.format(new Date());
	}

	public static String date2String(Date date, String format){
		SimpleDateFormat sdf= new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	/**
	 * String 转化Date
	 * eg. str="2010-5-27";
	 */
	public static Date string2Date(String dateStr){
		SimpleDateFormat sdf= new SimpleDateFormat(FORMAT_DATE);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date string2Date(String dateStr, String format){
		SimpleDateFormat sdf= new SimpleDateFormat(format);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
