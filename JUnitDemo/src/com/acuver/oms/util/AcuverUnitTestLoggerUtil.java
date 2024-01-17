package com.acuver.oms.util;

import com.yantra.yfc.log.YFCLogCategory;

public class AcuverUnitTestLoggerUtil {



    //YFCLogCategory Logger = YFCLogCategory.instance(IKEAUnitTestLoggerUtil.class.getName());

    /*
     * static{
     * PropertyConfigurator.configure(PropertiesUtil.getProperty("LoggerProperties")
     * ); //alternative for the above line -Dlog4j.configuration=log4j-ut.properties
     * in the java args }
     */

    /**
     * Returns the Logger for the pClassObj  - Object's class
     * @param pClassObj
     * @return
     */
    public static YFCLogCategory getLogger(Object pClassObj){
        return YFCLogCategory.instance(pClassObj.getClass());
    }

    /**
     * Returns the Logger for the pClass -  Class
     *
     * @param pClass
     * @return
     */
    public static YFCLogCategory getLogger(Class<? extends Object> pClass){
        return YFCLogCategory.instance(pClass);
    }


    /**
     * Logs the pMessage at Debug Level of the pClass - Class
     * @param pClass
     * @param pMessage
     */
    public static void logDebug(Class<? extends Object> pClass,Object pMessage){
        YFCLogCategory logger = YFCLogCategory.instance(pClass);
        if(logger.isDebugEnabled()){
            logger.debug(pMessage);
        }
    }

    /**
     * Logs the pMessage at ERROR Level of the pClass - Class
     * @param pClass
     * @param pMessage
     */
    public static void logError(Class<? extends Object> pClass,Object pMessage){
        YFCLogCategory logger = YFCLogCategory.instance(pClass);
        logger.error(pMessage);
    }

    /**
     * Logs the pMessage at DEBUG Level of the pClassObj's Class
     * @param pMessage
     */
    public static void logDebug(Object pClassObj,Object pMessage){
        YFCLogCategory logger = YFCLogCategory.instance(pClassObj.getClass());
        if(logger.isDebugEnabled()){
            logger.debug(pMessage);
        }
    }

    /**
     * Logs the pMessage at ERROR Level of the pClassObj's Class
     * @param pMessage
     */
    public static void logError(Object pClassObj,Object pMessage){
        YFCLogCategory logger = YFCLogCategory.instance(pClassObj.getClass());
        logger.error(pMessage);
    }


    public static void logError(YFCLogCategory log, String className, String methodName, AcuverUnitTestErrorCodes errorCode, String message){
        StringBuffer sb = new StringBuffer();
        sb.append(className);
        sb.append(": ");
        sb.append(methodName);
        sb.append(": ");
        sb.append(errorCode.getErrorCode());
        sb.append(": ");
        sb.append(errorCode.getErrorDescription());
        if(message != null && ! AcuverUnitTestCommonUtils.isNullOrEmpty(message)){
            sb.append(message);
        }
        log.error(sb.toString());
    }


    public static void logError(YFCLogCategory log, String className, String methodName, String testDataNum, AcuverUnitTestErrorCodes errorCode, String message){
        StringBuffer sb = new StringBuffer();
        sb.append(className);
        sb.append(": ");
        sb.append(methodName);
        sb.append(": ");
        sb.append("TestDataNum " + testDataNum);
        sb.append(errorCode.getErrorCode());
        sb.append(": ");
        sb.append(errorCode.getErrorDescription());
        if(message != null && ! AcuverUnitTestCommonUtils.isNullOrEmpty(message)){
            sb.append(message);
        }
        log.error(sb.toString());
    }
}
