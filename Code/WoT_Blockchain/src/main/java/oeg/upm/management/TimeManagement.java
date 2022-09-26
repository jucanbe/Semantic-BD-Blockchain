package oeg.upm.management;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeManagement {
	
	public static DateFormat dateFormat;
	public static Calendar cal;
	
	public TimeManagement() {
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		cal = Calendar.getInstance();
		cal.set(2022, 11, 1, 20, 0, 0);
	}
	
	public void addTime(){
		cal.add(Calendar.HOUR, 1);
	}
	
	public long timestamp() {
		return cal.getTimeInMillis();
	}
	
	public Calendar getCalendarFromTimestamp(long timestamp) {
		Calendar calendar = null;
		calendar.setTimeInMillis(timestamp);
		return calendar;
	}

}
