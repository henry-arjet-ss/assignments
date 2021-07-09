package com.ss.jb.five.date;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
* Date-Time assignments for JavaBasics 5 
* Includes both text answers and examples
* Henry Arjet 
* July 2021 Cloud Engineering
*/

public class DateTime {

	 /*
	 
1.	 Which class would you use to store your birthday in years, months, days, seconds, and nanoseconds?
		 LocalDateTime

2.	 Given a random date, how would you find the date of the previous Thursday?
	 	 loop changing the date via minusDays(1) then checking that date via getDayOfWeek
	 	 
3.	 What is the difference between a ZoneId and a ZoneOffset?
		 ZoneID includes offset and region-specific formatting information whereas ZoneOffset only includes the offset from GMT
		  
4.	 How would you convert an Instant to a ZonedDateTime? How would you convert a ZonedDateTime to an Instant?
     a)	 ZonedDateTime.ofInstant(instant, zone) or instant.atZone(zone); 
     b)	 .toInstant()
    	 
5.	 Write an example that, for a given year, reports the length of each month within that year.
*/
	public List<Integer> findMonthsLength(int year){
		List<Integer> ret = new ArrayList<Integer>();//return value  
		for (int i = 1; i <= 12; i++) { //go through the months
			String yearString = String.valueOf(year) + '-' + (i<10?("0" + i):i) + "-01"; //if i is less than 10 in needs a leading 0
			ret.add(LocalDate.parse(yearString).lengthOfMonth());//push length of month for each month
		}
		return ret;
	} 
	
/*

6.	 Write an example that, for a given month of the current year, lists all of the Mondays in that month.

7.	 Write an example that tests whether a given date occurs on Friday the 13th.

	 */
	
	public static void main(String[] args) {
		DateTime dateTime = new DateTime();
		System.out.println(dateTime.findMonthsLength(2021));
		
	}

}
