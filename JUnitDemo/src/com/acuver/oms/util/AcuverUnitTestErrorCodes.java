package com.acuver.oms.util;

public enum AcuverUnitTestErrorCodes {


    // List of Errors
    INVALID_RECORD_ID("UT_0001", "Invalid record ID :"), MISSING_HEADER(
            "UT_0002", "Header record not found for the method"),

    // Order Promising related Errors - DO and TW - START
    KEY_VALUE_MISMATCH("UT_0003", "Key Value Size Mismatch"), FOLDER_NAME_MISSING(
            "UT_0006", "FolderName not mentioned"), INVALID_FOLDER_NAME(
			"UT_0007", "Invalid folderName mentioned for the key"), HEADER_MISMATCH(
            "UT_0008",
            "The headers of the same method are not matching,skipping the data"), TESTMETHOD_DATA_NOT_FOUND(
			"UT_0010", "No TestMethodData is available for the method ");

    /**
     * Declaration of variable error code.
     */

    private String errorCode;

    /**
     *
     * Declaration of variable errorDescription.
     */

    private String errorDescription;

    /**
     *
     * @param errorCode
     *            OMS custom error code.
     *
     * @param errorDescription
     *            OMS custom error Description.
     */

    private AcuverUnitTestErrorCodes(String errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    /**
     *
     * @return String Type errorCode.
     */

    public String getErrorCode() {
        return this.errorCode;

    }

    /**
     *
     * @return String Type errorDescription.
     */

    public String getErrorDescription() {
        return this.errorDescription;

    }
}
