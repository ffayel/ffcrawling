package com.ff.tool;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public final class MyTime {

	private final static Locale local_fr = Locale.FRANCE;
	private final static DateFormat w3dtf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	private final static DateFormat affichageComplet =  new SimpleDateFormat("EEEE dd MMMM yyyy HH:mm", local_fr);
	private final static DateFormat affichageTextMoisAnnee =  new SimpleDateFormat("MMMM yyyy", local_fr);
	private final static DateFormat affichageCompletDate =  new SimpleDateFormat("EEEE dd MMMM yyyy", local_fr);
	private final static DateFormat affichageCompletDateWithoutDayName =  new SimpleDateFormat("dd MMMM yyyy", local_fr);
	private final static DateFormat inputDate =  new SimpleDateFormat("dd/MM/yyyy", local_fr);
	private final static DateFormat inputDateWithoutYear =  new SimpleDateFormat("dd/MM", local_fr);
	private final static DateFormat inputDateHeure =  new SimpleDateFormat("dd/MM/yyyy HH:mm", local_fr);
	private final static DateFormat inputDateHeureSeconds =  new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", local_fr);
	private final static DateFormat inputDateHeureWithoutYear =  new SimpleDateFormat("dd/MM HH:mm", local_fr);
	private final static DateFormat inputHeure =  new SimpleDateFormat("HH:mm", local_fr);
	private final static DateFormat tmpFileName =  new SimpleDateFormat("ddMMyyyyHHmmssSSS", local_fr);

	private final static DateFormat stringMonth =  new SimpleDateFormat("MM", local_fr);
	private final static DateFormat stringYear =  new SimpleDateFormat("yyyy", local_fr);

	final static DateFormat facturepdf_df_year = new SimpleDateFormat("yyyy");
	final static DateFormat facturepdf_df_inputDate =  new SimpleDateFormat("dd/MM/yyyy");
	final static DateFormat facturepdf_df_month = new SimpleDateFormat("MM");
	final static DateFormat facturepdf_df_monthName = new SimpleDateFormat("MMMM", Locale.FRANCE);

	private final static DateFormat ticketing_reference =  new SimpleDateFormat("yyyyMMdd", local_fr);
	private final static DateFormat ticketing_date =  new SimpleDateFormat("EEEE dd MMMM HH:mm", local_fr);
	private final static DateFormat ticketing_dateListe =  new SimpleDateFormat("dd/MM HH:mm", local_fr);

	private final static DateFormat statsday_format = new SimpleDateFormat("dd/MM/yyyy (EEEE)", local_fr);
	private final static DateFormat sql_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", local_fr);
	private final static DateFormat yearmonth_format = new SimpleDateFormat("yyyy MM", local_fr);


	public final static String getLastMonthStringMonth() {
		final Calendar cal = new GregorianCalendar();
		cal.add(Calendar.MONTH, -1);
		return stringMonth.format(cal.getTime());
	}
	public final static String getLastMonthStringYear() {
		final Calendar cal = new GregorianCalendar();
		cal.add(Calendar.MONTH, -1);
		return stringYear.format(cal.getTime());
	}
	public final static String getStringMonth() {
		final Calendar cal = new GregorianCalendar();
		return stringMonth.format(cal.getTime());
	}
	public final static String getStringYear() {
		final Calendar cal = new GregorianCalendar();
		return stringYear.format(cal.getTime());
	}

	public final static String getNowW3dtf (){
		return w3dtf.format(new GregorianCalendar().getTime());
	}
	
	public final static String getNowTmpFileName (){
		return tmpFileName.format(new GregorianCalendar().getTime());
	}

	public static Calendar getTmpFileCalendar(final String input) {
		try {
			final Calendar result = new GregorianCalendar();
			result.setTime(tmpFileName.parse(input));
			return result;
		} catch (ParseException e) {
			return nowCalendar();
		}
	}

	public final static Timestamp now() {
		return new Timestamp(new Date().getTime());
	}


	public final static Timestamp nowWithoutTime() {
		final Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return getTimestamp(cal);
	}



	public final static int nowMonth() {
		return new GregorianCalendar().get(Calendar.MONTH) + 1;
	}
	public final static String nowMonthString() {
		int month =  new GregorianCalendar().get(Calendar.MONTH) + 1;
		return month < 10 ? ("0"+month) : (""+month);
	}

	public final static int nowYear() {
		return new GregorianCalendar().get(Calendar.YEAR);
	}
	public final static String nowYearString() {
		return ""+new GregorianCalendar().get(Calendar.YEAR);
	}

	public final static String getAffichageComplet(final Timestamp date){
		return affichageComplet.format(date);
	}
	public final static String getAffichageComplet(final Calendar date) {
		return affichageComplet.format(date.getTime());
	}
	public final static String getRefTicketing(final Calendar date) {
		return ticketing_reference.format(date.getTime());
	}
	public final static String getDateTicketing(final Calendar date) {
		return ticketing_date.format(date.getTime());
	}
	public final static String getDateTicketingListe(final Calendar date) {
		return ticketing_dateListe.format(date.getTime());
	}
	public final static String getAffichageCompletDate(final Calendar date) {
		return affichageCompletDate.format(date.getTime());
	}
	public final static String getAffichageCompletDateWithoutDayName(final Calendar date) {
		return affichageCompletDateWithoutDayName.format(date.getTime());
	}


	public static String getAffichageInputDate(final Calendar date) {
		return inputDate.format(date.getTime());
	}
	public static Calendar getInputCalendarDate(final String input, final Calendar defaultValue) {
		try {
			final Calendar result = new GregorianCalendar();
			result.setTime(inputDate.parse(input));
			return result;
		} catch (ParseException e) {
			return defaultValue;
		}
	}
	public static String getAffichageInputDateHeure(final Calendar date) {
		return inputDateHeure.format(date.getTime());
	}
	public static String getAffichageInputDateHeureSeconds(final Calendar date) {
		return inputDateHeureSeconds.format(date.getTime());
	}


	public static String getAffichageInputDateHeureForAddMatch(final Calendar date) {
		date.set(Calendar.HOUR_OF_DAY, 21);
		date.set(Calendar.MINUTE, 0);
		return inputDateHeure.format(date.getTime());
	}
	public static String getAffichageInputDateHeureForAddEvent(final Calendar date) {
		date.set(Calendar.HOUR_OF_DAY, 10);
		date.set(Calendar.MINUTE, 0);
		return inputDateHeure.format(date.getTime());
	}

	public static String getAffichageInputDateHeureWithoutYear(final Calendar date) {
		return inputDateHeureWithoutYear.format(date.getTime());
	}
	public static String getAffichageInputDateWithoutYear(final Calendar date) {
		return inputDateWithoutYear.format(date.getTime());
	}
	public static Calendar getInputCalendarDateHeure(final String input, final Calendar defaultValue) {
		try {
			final Calendar result = new GregorianCalendar();
			result.setTime(inputDateHeure.parse(input));
			return result;
		} catch (ParseException e) {
			return defaultValue;
		}
	}
	public static String getAffichageInputHeure(final Calendar date) {
		return inputHeure.format(date.getTime());
	}
	public static Calendar getInputCalendarHeure(final String input, final Calendar defaultValue) {
		try {
			final Calendar result = new GregorianCalendar();
			result.setTime(inputHeure.parse(input));
			return result;
		} catch (ParseException e) {
			return defaultValue;
		}
	}

	public final static Timestamp getDateAnniv(final int year, final int month, final int day) {
		final Calendar date = new GregorianCalendar();
		date.set(Calendar.YEAR, year);
		date.set(Calendar.MONTH, month-1);
		date.set(Calendar.DAY_OF_MONTH, day);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		return new Timestamp(date.getTime().getTime());
	}

	public static final Calendar getCalendar(final Timestamp timestamp) {
		try {
			final Calendar date = new GregorianCalendar();
			date.setTime(new Date(timestamp.getTime()));
			return date;
		} catch (Exception e) {
			return new GregorianCalendar();
		}
	}
	public static final Calendar getCalendarWithHour(final Timestamp timestamp) {
		try {
			final Calendar date = new GregorianCalendar();
			date.setTime(new Date(timestamp.getTime()));
			date.set(Calendar.HOUR_OF_DAY, 0);
			date.set(Calendar.MINUTE, 0);
			date.set(Calendar.SECOND, 0);
			date.set(Calendar.MILLISECOND, 0);
			return date;
		} catch (Exception e) {
			return new GregorianCalendar();
		}
	}

	public final static Timestamp getTimestamp(final Calendar date) {
		try {
			return new Timestamp(date.getTime().getTime());
		} catch (Exception e) {
			return now();
		}
	}

	public final static Calendar getCalendar(final int month, final int year) {
		final Calendar date = new GregorianCalendar();
		date.set(Calendar.YEAR, year);
		date.set(Calendar.MONTH, month);
		date.set(Calendar.DAY_OF_MONTH, 0);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		return date;
	}

	public final static Calendar addMonth(final Calendar date) {
		date.add(Calendar.MONTH, 1); return date;
	}

	public final static Calendar removeMonth(final Calendar date) {
		date.add(Calendar.MONTH, -1); return date;
	}

	public final static Calendar addDay(final Calendar date, final int nbDay){
		date.add(Calendar.DATE, nbDay); return date;
	}

	public final static int getNbJoursDansLeMois(final Calendar cal) {
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 *  1 => dimanche
	 *  2 => lundi
	 *  ...
	 *  7 => samedi
	 */
	public final static int getValueOfFisrtDayInWeek(final Calendar cal) {
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DAY_OF_MONTH));
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public final static Calendar getDayOfCalendar(final Calendar cal, int day) {
		final Calendar result = cal;
		result.set(Calendar.DAY_OF_MONTH, day);
		return result;
	}

	public final static boolean isSameDayMonthYear(final Calendar cal1, final Calendar cal2) {
		return ((cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) && (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) && (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))) ? true : false;
	}
	public final static boolean isSameDayMonth(final Calendar cal1, final Calendar cal2) {
		return ((cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) && (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH))) ? true : false;
	}

	public static long getTime() {
		return new Date().getTime();
	}
	public static long getTimeAddMinute(final int nbMinute) {
		final Calendar cal = new GregorianCalendar();
		cal.add(Calendar.MINUTE, nbMinute);
		return cal.getTime().getTime();
	}

	public static Calendar nowCalendar() {
		return new GregorianCalendar();
	}

	public static Timestamp getTimestampsForOnlineUser(final int time) {
		final Calendar date = new GregorianCalendar();
		date.add(Calendar.MINUTE, (time * -1));
		return new Timestamp(date.getTime().getTime());
	}

	public static int getAge(Calendar cal) {
		final int nbJours = getNbJours(cal, nowCalendar());
		return new Double(nbJours/364.25).intValue();
	}

	public static int getNbJours(Calendar aCal, Calendar bCal) {
		final long MILLISECONDS_PER_DAY = 1000 * 60 * 60 * 24;
		aCal.set(Calendar.HOUR_OF_DAY, 0);
		aCal.set(Calendar.MINUTE, 0);
		aCal.set(Calendar.SECOND, 0);
		aCal.set(Calendar.MILLISECOND, 0);
		bCal.set(Calendar.HOUR_OF_DAY, 0);
		bCal.set(Calendar.MINUTE, 0);
		bCal.set(Calendar.SECOND, 0);
		bCal.set(Calendar.MILLISECOND, 0);
		long aFromOffset = aCal.get(Calendar.DST_OFFSET);
		long aToOffset = bCal.get(Calendar.DST_OFFSET);
		long aDayDiffInMili = (bCal.getTime().getTime() + aToOffset) - (aCal.getTime().getTime() + aFromOffset);
		return new Long(aDayDiffInMili / MILLISECONDS_PER_DAY).intValue();
	}

	public static int getNbMin(Calendar aCal, Calendar bCal) {
		final long MILLISECONDS_PER_MIN = 1000 * 60;
		aCal.set(Calendar.MILLISECOND, 0);
		bCal.set(Calendar.MILLISECOND, 0);
		long aFromOffset = aCal.get(Calendar.DST_OFFSET);
		long aToOffset = bCal.get(Calendar.DST_OFFSET);
		long aDayDiffInMili = (bCal.getTime().getTime() + aToOffset) - (aCal.getTime().getTime() + aFromOffset);
		return new Long(aDayDiffInMili / MILLISECONDS_PER_MIN).intValue();
	}
	public static int getNbSec(Calendar aCal, Calendar bCal) {
		aCal.set(Calendar.MILLISECOND, 0);
		bCal.set(Calendar.MILLISECOND, 0);
		long aFromOffset = aCal.get(Calendar.DST_OFFSET);
		long aToOffset = bCal.get(Calendar.DST_OFFSET);
		long aDayDiffInMili = (bCal.getTime().getTime() + aToOffset) - (aCal.getTime().getTime() + aFromOffset);
		return new Long(aDayDiffInMili / 1000).intValue();
	}

	public static int getNbJoursCompareToNow(Timestamp date) {
		final Calendar test = getCalendar(date);
		return getNbJours(nowCalendar(), test);
	}

	public static int getNbJoursCompareToNow(Calendar test) {
		return getNbJours(nowCalendar(), test);
	}

	public static String getAffichageText(int month, int year) {
		final Calendar date = getCalendar(month, year);
		return affichageTextMoisAnnee.format(date.getTime());
	}
	public static String getAffichageTextMoisAnnee(Calendar cal) {
		return affichageTextMoisAnnee.format(cal.getTime());
	}

	public static boolean isGreaterThan(Calendar value, Calendar compareValue) {
		return (value.getTimeInMillis() > compareValue.getTimeInMillis());
	}
	public static boolean isLessThan(Calendar value, Calendar compareValue) {
		return (value.getTimeInMillis() < compareValue.getTimeInMillis());
	}
	public static boolean isGreaterThanOrEqualTo(Calendar value, Calendar compareValue) {
		return (value.getTimeInMillis() >= compareValue.getTimeInMillis());
	}
	public static boolean isLessThanOrEqualTo(Calendar value, Calendar compareValue) {
		return (value.getTimeInMillis() <= compareValue.getTimeInMillis());
	}
	public static Calendar nowCalendarWithoutTime() {
		final Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static final String getMonth(final Calendar cal) {
		return facturepdf_df_month.format(cal.getTime());
	}
	public static String getMonth(Timestamp time) {
		return getMonth(getCalendar(time));
	}
	public static String getMonthName(Calendar cal) {
		return facturepdf_df_monthName.format(cal.getTime());
	}
	public static final String getYear(final Calendar cal) {
		return facturepdf_df_year.format(cal.getTime());
	}
	public static String getYear(Timestamp time) {
		return getYear(getCalendar(time));
	}
	public static final String getInputDate(final Calendar cal) {
		return facturepdf_df_inputDate.format(cal.getTime());
	}

	public static final Calendar getCalendar(int day, int month, int year) {
		final Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static Timestamp getTimestamp(int day, int month, int year) {
		final Calendar cal = getCalendar(day, month, year);
		return new Timestamp(cal.getTime().getTime());
	}

	public static int getNbDayInMonth(int month, int year) {
		final Calendar cal = getCalendar(1, month, year);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	public static Calendar getCalendar(String input) {
		final Calendar result = new GregorianCalendar();
		try { result.setTime(facturepdf_df_inputDate.parse(input)); } catch (ParseException e) { }
		return result;
	}

	public static String getStats(Calendar date) {
		return statsday_format.format(date.getTime());
	}
	public static String getMonth(int month) {
		return (month > 9) ? (""+month) : ("0"+month);
	}
	public static String getSqlFormat(Calendar cal) {
		return sql_format.format(cal.getTime());
	}
	public static String getYearMonth(Calendar cal) {
		return yearmonth_format.format(cal.getTime());
	}

	public static boolean isSameMonth(Calendar cal1, Calendar cal2) {
		final int month1 = cal1.get(Calendar.MONTH);
		final int year1 = cal1.get(Calendar.YEAR);
		final int month2 = cal2.get(Calendar.MONTH);
		final int year2 = cal2.get(Calendar.YEAR);
		return (month1 == month2 && year1 == year2);
	}

	public static String nowInputDate() {
		final Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 10);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return getAffichageInputDate(cal);
	}
	public static int getMonthInt(Calendar cal) {
		return cal.get(Calendar.MONTH) + 1;
	}

}
