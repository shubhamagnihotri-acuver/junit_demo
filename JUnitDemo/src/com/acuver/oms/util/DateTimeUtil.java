package com.acuver.oms.util;

	import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
	
	public final class DateTimeUtil {

  /**
	     * Gets the current time in XML format "yyyy-MM-dd'T'HH:mm:ss"
	     *
	     * @return String Current time in XML format
	     */
	    public static String getXMLCurrentTime() {
	        return formatDate(Calendar.getInstance().getTime(),
	        "yyyy-MM-dd'T'HH:mm:ss");
	    }


	    /**
	     * Gets the current time in XML format "yyyy-MM-dd'T'HH:mm:ss:SSSS"
	     *
	     * @return String Current time in XML formats
	     */
	    public static String getXMLCurrentTimeMs() {
	        return formatDateGMT(Calendar.getInstance().getTime(),
	        "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
	    }

		// Added for OCD-1499
		 /**
	     * Gets the current time in XML format yyyy-MM-dd'T'HH:mm:ss'Z'"
	     *
	     * @return String Current time in GMT until seconds
	     */
	    public static String getXMLGMTCurrentTimeSec() {
	        return formatDateGMT(Calendar.getInstance().getTime(),
	                "yyyy-MM-dd'T'HH:mm:ss'Z'");
	    }
		
	    /**
	     * Gets the current time in XML format "yyyy-MM-dd'T'HH:mm:ss:SSS"
	     *
	     * @return String Current time in XML formats
	     */
	    public static String getXMLCurrentTimeMilliSec() {
	        return formatDate(Calendar.getInstance().getTime(),
	        "yyyy-MM-dd'T'HH:mm:ss.SSS");
	    }



	    /**
		     * Gets the current time in XML format "yyyy-MM-dd'T'HH:mm:ss:SSS'000'XXX"
		     *
		     * @return String Current time in XML formats
		     */
		    public static String getXMLCurrentTimeMsTz() {
		        return formatDateGMT(Calendar.getInstance().getTime(),
		        "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX");
		    }


	    /**
	     * Validate the input DATE format and
	     *
	     * @param inputDate
	     * @param outputFormat
	     * @return
	     * @throws IllegalArgumentException
	     */
	    public static String formatDate(final Date inputDate,
	        final String outputFormat) throws IllegalArgumentException {
	        // Validate input date value
	        if (inputDate == null) {
	            throw new IllegalArgumentException("Input date cannot be null in "
	                + "DateUtils.formatDate method");
	        }

	        // Validate output date format
	        if (outputFormat == null) {
	            throw new IllegalArgumentException(
	                "Output format cannot be null in "
	                + "DateUtils.formatDate method");
	        }

	        // Apply formatting
	        SimpleDateFormat formatter = new SimpleDateFormat(outputFormat);
	        //Removing DateTime formatting in GMT TimeZone
	        //formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
	        return formatter.format(inputDate);
	    }
	    /**
	     *
	     * @param inputDate
	     * @param outputFormat
	     * @return
	     * @throws IllegalArgumentException
	     */
	    public static String formatDateGMT(final Date inputDate,
	        final String outputFormat) throws IllegalArgumentException {
	        // Validate input date value
	        if (inputDate == null) {
	            throw new IllegalArgumentException("Input date cannot be null in "
	                + "DateUtils.formatDate method");
	        }

	        // Validate output date format
	        if (outputFormat == null) {
	            throw new IllegalArgumentException(
	                "Output format cannot be null in "
	                + "DateUtils.formatDate method");
	        }

	        // Apply formatting
	        SimpleDateFormat formatter = new SimpleDateFormat(outputFormat);
	        //Removing DateTime formatting in GMT TimeZone
	        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
	        return formatter.format(inputDate);
	    }
	    public static String formatSterlingDatetoString(final Date inputDate)
	    {
	        // Apply formatting
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	        return formatter.format(inputDate);
	    }
	    public static Date formatStringtoSterlingDate(final String inputDate) throws ParseException {

	        // Apply Date formatting
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	        return formatter.parse(inputDate);
	    }
	    public static Date formatStringtoSterlingDateInGMT(final String inputDate) throws ParseException {

	        // Apply Date formatting
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
	        return formatter.parse(inputDate);
	    }

	    public static String addDays(String strDate, String StringNoOfDays)
	    {
	      return addDays(strDate, Integer.parseInt(StringNoOfDays));
	    }

	    public static String addDays(String strDate, int iNoOfDays)
	    {
	      String strNewDate = "";
	      try {
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        Date startDate = df.parse(strDate);
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(startDate);
	        calendar.add(5, iNoOfDays);
	        strNewDate = df.format(calendar.getTime());
	      } catch (Exception e) {
	        e.printStackTrace();
	      }

	      return strNewDate;
	    }

	    public static String getXMLCurrentDate() {
			return formatDateGMT(Calendar.getInstance().getTime(),
			        "yyyy-MM-dd");
		}

	    // Added this method to caluclate current system date irrespective of time zone
	    public static String getCurrentSysDate() {
	        return formatDate(Calendar.getInstance().getTime(),
	                "yyyy-MM-dd");
	    }

	    // Calculate data time difference in milliseconds
		public static String calculateDifferenceInMilliSec(String endTimeStamp,
				String startTimeStamp) throws ParseException {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
			Date startDate = sdf.parse(startTimeStamp);
			Date endDate = sdf.parse(endTimeStamp);
			long diffInSeconds = endDate.getTime() - startDate.getTime();
			return Long.toString(diffInSeconds);
		}

	    // Add number of days passed  to current Date
	    public static String addDaysToXMLCurrentTime(String numberOfDays) {
	    	Calendar cal=Calendar.getInstance();
	    	cal.add(Calendar.DATE,Integer.parseInt(numberOfDays));
	    	return formatDate(cal.getTime(), "yyyy-MM-dd'T'HH:mm:ss");
	    }
	/**
	    * Gets the current time in XML format "yyyy-MM-dd'T'HH:mm:ss:SSSSSS"
	    *
	    * @return String Current time in XML formats
	    */
	   public static String getXMLCurrentTimeMicros() {
	       return formatDate(Calendar.getInstance().getTime(),
	       "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
	   }
	 /**
	    * Gets the earlier date in the format yyyy-MM-dd'T'HH:mm:ss
	    *
	    * @return String Current time in XML formats
	    */
	   public static String getEarlierDate(String strDate, int iNoOfDays) {
		   Calendar calnow = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		   try{
		 		Date startDate = sdf.parse(strDate);
		 		calnow.setTime(startDate);
		 		
		 		java.util.Date tsCurrentTime = sdf.parse(sdf.format(calnow.getTime()));	 		
		 		calnow.setTime(tsCurrentTime);
		 		calnow.add(Calendar.DAY_OF_MONTH, -iNoOfDays);
		 	  
	   } catch (Exception e) {
			e.printStackTrace();
		}
			return sdf.format(calnow.getTime());
	   }
	   
	  
		// Add number of hours passed  to current Date time  -Added for PRB0027221 fix
	    public static String addHoursToXMLCurrentTime(String numberOfHours) {
	    	 Calendar cal = Calendar.getInstance(); 
	    	    cal.setTime(new Date()); 
	    	    cal.add(Calendar.MINUTE, Integer.parseInt(numberOfHours)); 
	    	    cal.getTime();
	    	   return formatDate(cal.getTime(), "yyyy-MM-dd'T'HH:mm:ss");
	    }
		
		//Added for Auto pick fail for branch
		public static String getXMLCurrentDateInGMT() {
			return formatDateGMT(Calendar.getInstance().getTime(),
					"yyyy-MM-dd'T'HH:mm:ss");
		}
		
	

}
