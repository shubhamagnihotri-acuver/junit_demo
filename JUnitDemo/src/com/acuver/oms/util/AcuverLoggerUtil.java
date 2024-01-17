package com.acuver.oms.util;

import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.log.YFCLogLevel;
import com.yantra.yfc.log.YFCLogManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Iterator;
import java.util.Map;

public class AcuverLoggerUtil {


    YFCLogCategory logger;

    public static final String NL = "\r\n";

    /**
     * Construct a default LoggerUtil
     */
    public AcuverLoggerUtil() {
        this.logger = YFCLogCategory.instance("com.ikea");
    }

    @SuppressWarnings("rawtypes")
    public AcuverLoggerUtil(Class clazz) {
        this.logger = YFCLogCategory.instance(clazz.getName());
    }

    /**
     * Construct a LoggerUtil with specified name
     *
     * @param name
     *            LoggerUtil name
     */
    public AcuverLoggerUtil(String name) {
        this.logger = YFCLogCategory.instance(name);
    }

    @SuppressWarnings("rawtypes")
    public static AcuverLoggerUtil getInstance(Class clazz) {
        return new AcuverLoggerUtil(clazz.getName());
    }

    /**
     * Error message
     *
     * @param code
     *            error code
     */
    public void error(String code) {
        if (this.isErrorEnabled()) {
            this.logger.error(AcuverResourceUtil.resolveMsgCode(code));
        }
    }

    /**
     * Error message
     *
     * @param code
     *            error code
     * @param t
     *            Throw-able object
     */
    public void error(String code, Throwable t) {
        if (this.isErrorEnabled()) {
            this.logger.error(AcuverResourceUtil.resolveMsgCode(code), t);
        }
    }

    /**
     * Error message
     *
     * @param code
     *            error code
     * @param args
     *            Object array
     */
    public void error(String code, Object[] args) {
        if (this.isErrorEnabled()) {
            this.logger.error(AcuverResourceUtil.resolveMsgCode(code, args));
        }
    }

    /**
     * Error message
     *
     * @param code
     *            error code
     * @param args
     *            Object array
     * @param t
     *            Throwable object
     */
    public void error(String code, Object[] args, Throwable t) {
        if (this.isErrorEnabled()) {
            this.logger.error(AcuverResourceUtil.resolveMsgCode(code, args), t);
        }
    }

    /**
     * Info message
     *
     * @param code
     *            Info code
     */
    public void info(String code) {
        if (this.isInfoEnabled()) {
            this.logger.info(AcuverResourceUtil.resolveMsgCode(code));
        }
    }

    /**
     * Info message
     *
     * @param code
     *            Info code
     * @param args
     *            Object array
     */
    public void info(String code, Object[] args) {
        if (this.isInfoEnabled()) {
            this.logger.info(AcuverResourceUtil.resolveMsgCode(code, args));
        }
    }

    /**
     * Prints info log statements without checking any log enabled conditions
     * @param msg Message to be printed
     */
    public void infoLogMessage(String msg) {
        this.logger.info(msg);
    }


    /**
     * Log Debug message
     *
     * @param msg
     *            Message object
     */
    public void debug(Object msg) {
        if (isDebugEnabled())
            this.logger.debug(objectToString(msg));
    }

    /**
     * Log verbose message
     *
     * @param msg
     *            Message string
     */
    public void verbose(String msg) {
        this.logger.verbose(msg);
    }

    /**
     * Check the enabled Log Level
     *
     * @return True if Verbose enabled
     */
    public boolean isVerboseEnabled() {
        return YFCLogManager.isLevelEnabled(YFCLogLevel.VERBOSE_INT);
    }

    /**
     * Check the enabled Log Level
     *
     * @return True if Debug enabled
     */
    public boolean isDebugEnabled() {
        return YFCLogManager.isLevelEnabled(YFCLogLevel.DEBUG_INT);
    }

    /**
     * Check the enabled Log Level
     *
     * @return True if Info enabled
     */
    public boolean isInfoEnabled() {
        return YFCLogManager.isLevelEnabled(YFCLogLevel.INFO_INT);
    }

    /**
     * Check the enabled Log Level
     *
     * @return True if Error enabled
     */
    public boolean isErrorEnabled() {
        return YFCLogManager.isLevelEnabled(YFCLogLevel.ERROR_INT);
    }

    /**
     * @param methodName
     *            Name of the method to be monitored
     */

    public void beginTimer(String methodName) {
        this.logger.beginTimer(methodName);
    }

    /**
     * @param methodName
     *            Name of the method to be monitored
     */

    public void endTimer(String methodName) {
        this.logger.endTimer(methodName);
    }

    /**
     * Get default package level LoggerUtil.
     *
     * @return the default LoggerUtil
     */
    public static AcuverLoggerUtil getLogger() {
        return new AcuverLoggerUtil();
    }

    /**
     * Get LoggerUtil by name.
     *
     * @param name
     *            Class name
     * @return the LoggerUtil with the name.
     */
    public static AcuverLoggerUtil getLogger(String name) {
        return new AcuverLoggerUtil(name);
    }

    /**
     * @param message
     * @param doc
     */
    public void error(String message, Node aNode) {
        logger.error(message + NL + AcuverXMLUtil.serialize(aNode));
    }

    /**
     * @param message
     * @param t
     */
    public void logError(String message, Throwable t) {
        logger.error(message, t);

    }

    public void debug(String msg, Node aNode) {
        this.logDebug(msg, aNode);
    }

    /**
     * @param message
     * @param doc
     * @param ex
     */
    public void logDebug(String message, Node aNode, Throwable ex) {
        if (isDebugEnabled())
            logger.debug(message + NL + AcuverXMLUtil.serialize(aNode), ex);
    }

    /**
     * @param message
     * @param aNode
     */
    public void logDebug(String message, Node aNode) {
        if (isDebugEnabled())
            logger.debug(message + NL + AcuverXMLUtil.serialize(aNode));
    }



    @SuppressWarnings({ "rawtypes", "unused" })
    public static String mapToString(Map mapObj) {
        /*This is the code fix for the Defect 72241,
        where we have implemented using StringBuilder in place of StringBuffer
        and using entrySet iterator in place of keySet iterator
        and fixed the critical violaton possible null pointer dereference of mapObj */
        /* Defect 72241 Start */
        StringBuilder sbBuilder = new StringBuilder();

        sbBuilder.append('{');

        if (mapObj != null) {
            Iterator entries = mapObj.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                if (entry != null) {
                    sbBuilder.append(objectToString(entry.getKey()));
                    sbBuilder.append('=');
                    sbBuilder.append(objectToString(entry.getValue()));
                    sbBuilder.append(';');
                }
            }
        }

        sbBuilder.append('}');
        return sbBuilder.toString();
        /* Defect 72241 End */
    }

    public static String objectToString(Object obj) {

        String str = "";

        if (obj != null) {
            str = obj.toString();
        }

        return str;
    }

    //////////////////////////////////////////////////////////////////////////
    public static String documentToString(Document obj) {

        return SCXmlUtil.getString(obj);
    }

    /**
     * Log Debug message
     *
     * @param msg
     *            Message object
     */
    public void debug(String msg, Document inDoc) {
        if (isDebugEnabled())
            this.logger.debug(msg + documentToString(inDoc));
    }
    //added for code optimization

    public void debug(String msg ,Object obj) {
        if (isDebugEnabled())
            this.logger.debug(msg + objectToString(obj));
    }

    // End Code Optimization
    public static String documentToString(Element obj) {

        return SCXmlUtil.getString(obj);
    }

    /**
     * Log Debug message
     *
     * @param msg
     *            Message object
     */
    public void debug(String msg, Element inDoc) {
        if (isDebugEnabled())
            this.logger.debug(msg + documentToString(inDoc));
    }
    /**
     * Log Verbose message
     *
     * @param msg
     *            Message object
     */
    public void verbose(String msg, Document inDoc) {
        if (isVerboseEnabled())
            this.logger.verbose(msg + documentToString(inDoc));

    }
    /**
     * Log Verbose message
     *
     * @param msg
     *            Message object
     */
    public void verbose(String msg, Element inDoc) {
        if (isVerboseEnabled())
            this.logger.verbose(msg + documentToString(inDoc));
    }
    /**
     * Log Verbose message
     *
     * @param msg
     *            Message object
     */
    public void verbose(String msg, String inDoc) {
        if (isVerboseEnabled())
            this.logger.verbose(msg + inDoc);

    }
    /**
     * Log Verbose message
     *
     * @param msg
     *            Message object
     */
    public void debug(String msg, String inDoc) {
        if (isDebugEnabled())
            this.logger.debug(msg + inDoc);
    }


}
