package junit.test;

import java.util.Calendar;
import java.util.Date;

public class FirstDateOfWeek {
	public static void main(String[] args) {
		int week = 24;
		int year = 2014;

		// Get calendar, clear it and set week number and year.
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_WEEK, 3);

		// Now get the first day of week.
		Date date = calendar.getTime();
		
		
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(date);
//		int day = cal.get(Calendar.DAY_OF_MONTH);
//		int mon = cal.get(Calendar.MONTH)+1;//Calendar里取出来的month比实际的月份少1，所以要加上
//		
//		System.out.println(mon +"haha"+ day);
		System.out.println(date);
	}
}
