package com.acuver.oms.util;

import java.util.StringTokenizer;

public class AcuverStringUtil {



    private static AcuverLoggerUtil log = AcuverLoggerUtil.getLogger(AcuverStringUtil.class.getName());

    /**
     * Pad string with space to specified length. If the string length >= the
     * length parameter, no change is made.
     *
     * @param strPad
     *            the string to pad.
     * @param length
     *            the full string length after padding.
     * @return String type
     */
    public static String padSpaces(String strPad, int length) {
        /*This is the code fix for the Defect 72241,
        where we have implemented using StringBuilder instead of String concatenation*/
        /* Defect 72241 Start */
        int origLength = strPad.length();
        StringBuilder sbModified = new StringBuilder();

        if (origLength == length) {
            sbModified.append(strPad);
        } else {
            sbModified.append(strPad.trim());
            origLength = sbModified.length();
            if (origLength > length) {
                return sbModified.toString();
            } else {
                int spaceLength = length - origLength;
                for (int i = 0; i < spaceLength; i++) {
                    sbModified.append(' ');
                }
            }
        }

        return sbModified.toString();
        /* Defect 72241 End */
    }

    /**
     * Replace (globally) occurrence of substring.
     *
     * @param strInput
     *            the String to go through.
     * @param delim
     *            the substring to be replaced.
     * @param strReplace
     *            replacement.
     * @return replaced String
     */
    public static String escapeChar(String strInput, String delim,
                                    String strReplace) {

        StringTokenizer strToken = new StringTokenizer(strInput, delim, true);
        String strTemp;

        /*This is the code fix for the Defect 72241,
        where we have implemented using StringBuilder instead of String concatenation*/
        /* Defect 72241 Start */
        StringBuilder sbReturn = new StringBuilder();

        if (strToken.countTokens() > 0) {
            while (strToken.hasMoreTokens()) {
                strTemp = strToken.nextToken();
                if (strTemp.equals(delim)) {
                    sbReturn.append(strReplace);
                } else {
                    sbReturn.append(strTemp);
                }
            }
        } else {
            sbReturn.append(strInput);
        }

        return sbReturn.toString();

        /* Defect 72241 End */
    }

    /**
     * Splits the string, strInput based on the delimiter, delim and returns the
     * string at position, index
     *
     * @param strInput
     * @param delim
     * @param index
     * @return
     */
    public static String splitString(String strInput, String delim, String index)
            throws NumberFormatException {
        String strReturn = strInput;
        if (!("".equals(strInput))) {
            String[] strTemp = strInput.split(delim);
            try {
                int ind = Integer.parseInt(index);
                strReturn = strTemp[ind];
            } catch (NumberFormatException nfe) {
                log.error("IKEAStringUtil :: splitString :: NumberFormatException is"+nfe);
            }
        }
        return strReturn;

    }

    /**
     * @param value
     *            value
     * @param suffix
     *            suffix
     * @return Return the subclass without specified suffix
     */
    public static String stripSuffix(String value, String suffix) {
        if (value != null && !value.equals("")) {
            int suffixStartingIndex = value.lastIndexOf(suffix);
            if (suffixStartingIndex != -1) {
                return value.substring(0, suffixStartingIndex);
            }
        }
        return value;
    }

    /**
     * Convert null String to empty String
     *
     * @param value
     *            value
     * @return String type
     */
    public static String nonNull(String value) {
        String retval = value;
        if (value == null) {
            retval = "";
        }
        return retval;
    }

    /**
     * Append '/' or '\' to the end of input, based on the input already has '/'
     * or '\'. If the input does not have '/' or '\', the System property
     * "file.separator"
     *
     * @param dirName
     *            dirName
     * @return String type
     */
    public static String formatDirectoryName(String dirName) {
        /*This is the code fix for the Defect 72241,
        where we have implemented using indexOf(char) instead of indexOf(String)
        and using StringBuilder over += for concatenating strings*/
        /* Defect 72241 Start */
        StringBuilder sbRetval = new StringBuilder();

        if (dirName.charAt(dirName.length() - 1) == '\\'
                || dirName.charAt(dirName.length() - 1) == '/') {
            sbRetval.append(dirName);
        } else if (dirName.indexOf('/') > -1) {
            sbRetval.append(dirName).append('/');
        } else if (dirName.indexOf('\\') > -1) {
            sbRetval.append(dirName).append('\\');
        } else {
            sbRetval.append(dirName).append(System.getProperty("file.separator"));
        }
        return sbRetval.toString();
        /* Defect 72241 End */
    }

    /**
     * Prepad string with '0'. If the length of the String is >= the input size,
     * no change is made.
     *
     * @param value
     *            the string to pad
     * @param size
     *            the full String length after the pad.
     * @return String type
     */
    public static String prepadStringWithZeros(String value, int size) {
        String retval = value;
        if (value != null) {
            int length = value.length();
            if (length > size) {
                retval = value;
            } else {
                StringBuffer buffer = new StringBuffer();
                for (int count = 0; count < size - length; count++) {
                    buffer.append("0");
                }
                buffer.append(value);
                retval = buffer.toString();
            }
        }
        return retval;
    }

    /**
     * Check if a String is null or can be trimmed to empty.
     *
     * @param str
     *            string to be checked
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * Check if a String is not null and cannot be trimmed to empty.
     *
     * @param str
     *            string to be checked
     * @return boolean
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Convert an empty String (null or "") to a space String.
     *
     * @param val
     *            value to be padded with
     * @return String padded string
     */
    public static String padNullEmpty(String val) {
        if (val == null || val.equals("")) {
            return " ";
        }
        return val;
    }

    /**Commented this method in order to not to override the Object.equals().Fix for the Critical Sonar Violation.
     * compares two string values after empty checks
     *
     * @param s1
     *            value to be compared
     * @param s2
     *            value to be compared
     * @return boolean
     */
	/*public static boolean equals(String s1, String s2) {
		boolean flag = false;

		if (isNotEmpty(s1)) {
			if (s1.equalsIgnoreCase(s2)) {
				flag = true;
			}
		} else if (isEmpty(s2)) {
			flag = true;
		}

		return flag;
	}*/

    /**
     * compares two Sterling Statuses and returns MaxStatus irrespective of the
     * number of dots in the statuses Sample Status : 2130.0050.020
     *
     * @param firstStatus
     *            value to be compared
     * @param secondStatus
     *            value to be compared
     * @return String
     */
    public static String getMaximumStatus(String firstStatus,
                                          String secondStatus) {
        String[] splitFirstStatus = firstStatus.split("\\.");
        String[] splitSecondStatus = secondStatus.split("\\.");
        int splitFirStatusLength = splitFirstStatus.length;
        int splitSecStatusLength = splitSecondStatus.length;
        Integer intSplitFirStatus = null;
        Integer intSplitSecStatus = null;
        int minSplitLength;
        if (splitFirStatusLength <= splitSecStatusLength) {
            minSplitLength = splitFirStatusLength;
        } else {
            minSplitLength = splitSecStatusLength;
        }
        for (int i = 0; i < minSplitLength; i++) {
            intSplitFirStatus = Integer.parseInt(splitFirstStatus[i]);
            intSplitSecStatus = Integer.parseInt(splitSecondStatus[i]);

            if (intSplitFirStatus > intSplitSecStatus) {
                return firstStatus;
            } else if (intSplitFirStatus < intSplitSecStatus) {
                return secondStatus;
            } else {
                continue;
            }
        }
        if (minSplitLength == splitFirStatusLength) {
            return secondStatus;
        } else {
            return firstStatus;
        }
    }

    /**
     * compares two Sterling Statuses and returns true if (firstStatus >=
     * secondStatus) firstStatus(first Parameter)is >= secondStatus(second
     * Parameter), else false,irrespective of the number of dots in the
     * statuses. Sample Status : 2130.0050.020
     *
     * @param firstStatus
     *            value to be compared
     * @param secondStatus
     *            value to be compared
     * @return boolean
     */
    public static boolean isFirstStatusMax(String firstStatus,
                                           String secondStatus) {
        String[] splitFirstStatus = firstStatus.split("\\.");
        String[] splitSecondStatus = secondStatus.split("\\.");
        int splitFirStatusLength = splitFirstStatus.length;
        int splitSecStatusLength = splitSecondStatus.length;
        Integer intSplitFirStatus = null;
        Integer intSplitSecStatus = null;
        int minSplitLength;
        if (splitFirStatusLength <= splitSecStatusLength) {
            minSplitLength = splitFirStatusLength;
        } else {
            minSplitLength = splitSecStatusLength;
        }
        for (int i = 0; i < minSplitLength; i++) {
            intSplitFirStatus = Integer.parseInt(splitFirstStatus[i]);
            intSplitSecStatus = Integer.parseInt(splitSecondStatus[i]);

            if (intSplitFirStatus > intSplitSecStatus) {
                return true;
            } else if (intSplitFirStatus < intSplitSecStatus) {
                return false;
            } else {
                continue;
            }
        }
        //Updated the below if condition "firstStatus.equals(secondStatus)" check for fixing Sonar Blocker Violation.Task ID - 192183.
        if (firstStatus.equals(secondStatus)) {
            return true;
        } else if (minSplitLength == splitFirStatusLength) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * compares two Sterling Statuses and returns true if (firstStatus >=
     * secondStatus) firstStatus(first Parameter)is >= secondStatus(second
     * Parameter), else false,irrespective of the number of dots in the
     * statuses. Sample Status : 2130.0050.020
     *
     * @param firstStatus
     *            value to be compared
     * @param secondStatus
     *            value to be compared
     * @return boolean
     */
    public static boolean isFirstStatusGreater(String firstStatus,
                                               String secondStatus) {
        String[] splitFirstStatus = firstStatus.split("\\.");
        String[] splitSecondStatus = secondStatus.split("\\.");
        int splitFirStatusLength = splitFirstStatus.length;
        int splitSecStatusLength = splitSecondStatus.length;
        Integer intSplitFirStatus = null;
        Integer intSplitSecStatus = null;
        int minSplitLength;
        if (splitFirStatusLength <= splitSecStatusLength) {
            minSplitLength = splitFirStatusLength;
        } else {
            minSplitLength = splitSecStatusLength;
        }
        for (int i = 0; i < minSplitLength; i++) {
            intSplitFirStatus = Integer.parseInt(splitFirstStatus[i]);
            intSplitSecStatus = Integer.parseInt(splitSecondStatus[i]);

            if (intSplitFirStatus > intSplitSecStatus) {
                return true;
            } else if (intSplitFirStatus < intSplitSecStatus) {
                return false;
            }
        }
        return false;
    }

}
