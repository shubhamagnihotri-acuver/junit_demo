package com.acuver.oms.util;

import com.yantra.yfc.log.YFCLogCategory;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.*;

public class AcuverResourceUtil {


    private static Properties resources = new Properties();

    @SuppressWarnings("rawtypes")
    private static ArrayList msgResBundles = new ArrayList();

    @SuppressWarnings("rawtypes")
    private static ArrayList msgResBundleNames = new ArrayList();

    private static int numMsgResBundlesLoaded;

    private final static String DESC_NOT_FOUND = "Error Description Not Found";
    private static YFCLogCategory logger = YFCLogCategory.instance(AcuverResourceUtil.class);



    // Currently, we see the need for only PROD and DEV to be two modes
    // in which IBM Sterling would be run. Therefore, the flag is a boolean.
    // If mode modes develop in the future (like TEST etc), then the key would
    // have to be redefined.


    public final static String YANTRA_RUNTIME_MODE = "yantra.implementation.runtime.mode";


    public static boolean isProductionMode = true;

    static {
        AcuverResourceUtil.loadDefaultResources();
    }


    public static void loadDefaultResources() {
        AcuverResourceUtil.msgResBundleNames.clear();
        AcuverResourceUtil.msgResBundles.clear();
        AcuverResourceUtil.resources.clear();

        // server level configuration files
        AcuverResourceUtil.loadResourceFile("/yfs.properties");
        AcuverResourceUtil.loadResourceFile("/resources/yifclient.properties");

        // Check if running in dev mode, if so reset the flag.
        //	try {
        if ("false".equalsIgnoreCase(AcuverResourceUtil.get(
                AcuverResourceUtil.YANTRA_RUNTIME_MODE, "true"))) {
            AcuverResourceUtil.isProductionMode = false;
        } else {
            AcuverResourceUtil.isProductionMode = true;
        }
        //Removed generic exception to resolve PMD error.
        //} catch ( Exception e) {
        //Catching Generic Exception here because if any exception occurs during property loading, the execution to continue
        //AcuverResourceUtil.logger
        //		.info("Error fetching Property value for YANTRA_RUNTIME_MODE");
        // Ignore exception. We'll assume production mode.
        //}
    }

    /**
     * Loading resources.
     *
     * @param filename
     *            the resource filename, must be available on CLASSPATH.
     */
    public static void loadResourceFile(String filename) {
        InputStream is = null;
        try {
            /*
             * YRCPlatformUI.trace("Loading Properties from: " + filename + ": "
             * + ResourceUtil.class.getResource(filename));
             */
            is = AcuverResourceUtil.class.getResourceAsStream(filename);
            AcuverResourceUtil.resources.load(is);
        } catch (IOException e) {
            if (logger.isDebugEnabled()) {
                /* Catching Generic Exception here because if any exception occurs during
                resource/property loading, the execution to continue */
                AcuverResourceUtil.logger.debug("Error loading resource from file [" + filename + "]: " + e.getMessage());
            }
            AcuverResourceUtil.logger.error("AcuverResourceUtil :: loadResourceFile() :: IOException is " + e);
        } finally {
            /* This is the code fix for the Defect 72241,
             * where we have fixed Sonar critical violation
             * Method may fail to clean up stream or resource on checked exception
             * in public static void loadResourceFile(String filename) */
            /* Defect 72241 Start */
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("AcuverResourceUtil::loadResourceFile()::IOException when closing the input stream" + e);
                }
            }
        }
        /* Defect 72241 End */
    }

    /**
     * Get resource by name
     *
     * @param name
     *            the resource name
     * @return String type
     */
    public static String get(String name) {

        String retVal = AcuverResourceUtil.resources.getProperty(name);
        if (retVal != null) {
            retVal = retVal.trim();
        }
        return retVal;
    }

    /**
     * Get resource or the default value
     *
     * @param name
     *            the resource name.
     * @param defaultValue
     *            the default value if the resource does not exist.
     * @return String type
     */
    public static String get(String name, String defaultValue) {
        String retval = AcuverStringUtil.nonNull(AcuverResourceUtil.resources
                .getProperty(name));
        if (retval.equals("")) {
            retval = defaultValue;
        }
        return retval.trim();
    }

    /**
     *
     * @param key
     *            resource name
     * @param def
     *            default value
     * @return true if the value is 'Y' or 'true' (case incensitive) false
     *         otherwise
     */
    public boolean getAsBoolean(String key, boolean def) {
        String val = (String) AcuverResourceUtil.resources.get(key);
        if (null == val) {
            return def;
        }
        /*
         * This is the code fix for the Defect 72241, where we avoid declaring a
         * variable if it is unreferenced before a possible exit point.
         */
        /* Defect 72241 Start */
        boolean retValue = false;
        /* Defect 72241 End */
        if (val.equalsIgnoreCase("Y") || val.equalsIgnoreCase("true")) {
            retValue = true;
        }

        return retValue;
    }

    /**
     *
     * @param key
     *            resource key
     * @param def
     *            key definition
     * @return double value of the resource. def in case of
     *         NumberFormatException
     */
    public double getAsDouble(String key, double def) {
        String val = (String) AcuverResourceUtil.resources.get(key);
        if (null == val) {
            return def;
        }
        double ret = 0.0;
        try {
            ret = Double.parseDouble(val);
            // return ret;
        } catch (NumberFormatException e) {
            // YRCPlatformUI.trace("Unable to convert value to double:" + val);
            // return def;
            ret = def;
        }
        return ret;
    }

    /**
     *
     * @param key
     *            resource key
     * @param def
     *            key definition
     * @return int value of the key as defined in the properies file.
     */
    public int getAsInt(String key, int def) {
        String val = (String) AcuverResourceUtil.resources.get(key);
        if (null == val) {
            return def;
        }
        int ret = 0;
        try {
            ret = Integer.parseInt(val);
            // return ret;
        } catch (NumberFormatException e) {
            ret = def;
        }
        return ret;
    }

    /**
     *
     @param key
      *            resource key
      * @param def
     *            key definition
     * @return int value of the key as defined in the properies file.
     */
    public long getAsLong(String key, long def) {
        String val = (String) AcuverResourceUtil.resources.get(key);
        if (null == val) {
            return def;
        }
        long ret = 0L;
        try {
            ret = Long.parseLong(val);
            // return ret;
        } catch (NumberFormatException e) {
            // //YRCPlatformUI.trace("Unable to convert value to long:" + val);
            ret = def;
        }
        return ret;
    }

    /**
     *
     */
    @SuppressWarnings("rawtypes")
    public static void list() {
        // //YRCPlatformUI.trace("Managing Following Properties");
        Enumeration keys = AcuverResourceUtil.resources.keys();

        /*
         * This is the code fix for the Defect 72241, where we use a
         * StringBuilder instead of StringBuffer.
         */
        /* Defect 72241 Start */
        StringBuilder strBuilder = new StringBuilder();
        /* Defect 72241 End */

        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            strBuilder.append(key).append("=")
                    .append(AcuverResourceUtil.resources.getProperty(key))
                    .append("\n");
        }
        // //YRCPlatformUI.trace(sb.toString());
    }

    /**
     *
     * @return the list of resources defined
     */
    public static Properties getAllResources() {
        return AcuverResourceUtil.resources;
    }

    /**
     *
     * @param componentName
     *            name of the resourcebundle component
     * @return ResourceBundle
     */

    public static ResourceBundle loadMsgCodes(String componentName) {
        return AcuverResourceUtil.loadMsgCodes(componentName, null);
    }

    /**
     * Use this method to load messages. Message from
     * /resources/extn/messagecodes.properties will be loaded by default
     *
     * @param componentName
     *            name of resourcebundle component
     * @param locale
     *            locale
     * @return ResourceBundle
     */
    @SuppressWarnings("unchecked")
    public static ResourceBundle loadMsgCodes(String componentName,
                                              Locale locale) {
        ResourceBundle rb = null;

        /*
         * This is the code fix for the Defect 72241, where we use
         * StringBuilder instead of += as Sonar suggests replacing StringBuffer
         * with StringBuilder.
         */

        /* Defect 72241 Start */

        StringBuilder sbKey = new StringBuilder(componentName);

        if (locale != null) {
            sbKey.append('_').append(locale.getDisplayName());
        }

        if (!AcuverResourceUtil.msgResBundleNames.contains(sbKey)) {
            try {
                if (locale != null) {
                    rb = ResourceBundle.getBundle(componentName, locale);
                } else {
                    rb = ResourceBundle.getBundle(componentName);
                }

                AcuverResourceUtil.msgResBundleNames.add(componentName);
                AcuverResourceUtil.msgResBundles.add(rb);
                AcuverResourceUtil.numMsgResBundlesLoaded++;
            } catch (MissingResourceException mre) {
                if (logger.isDebugEnabled()) {
                    AcuverResourceUtil.logger.debug("Unable to load error codes from Resource Bundle: "
                            + sbKey);
                }
                /* Defect 72241 End */
                AcuverResourceUtil.logger.error("AcuverResourceUtil :: loadMsgCodes() :: MissingResourceException is " +mre);
            }
        }
        return rb;
    }

    /**
     * This method returns the error description for the given errorCode as
     * specified in the message Bundle file. If a matching entry is not found
     * then it returns "Error Description Not Found"
     *
     * @param errorCode
     *            error codes
     * @return String type
     */

    public static String resolveMsgCode(String errorCode) {
        return AcuverResourceUtil.resolveMsgCode(errorCode, null);
    }

    /**
     * This method returns the error description for the given errorCode as
     * specified in the message Bundle file. If a matching entry is not found
     * then it returns "Error Description Not Found".
     * <p>
     * Use errorArgs to parameterize error description.
     *
     * @param errorCode
     *            error code
     * @param errorArgs
     *            error argumenst
     * @return String type
     */
    public static String resolveMsgCode(String errorCode, Object[] errorArgs) {
        String desc = null;
        int resBundleIndex = -1;

        while (desc == null
                && ++resBundleIndex < AcuverResourceUtil.numMsgResBundlesLoaded) {
            ResourceBundle rb = (ResourceBundle) AcuverResourceUtil.msgResBundles
                    .get(resBundleIndex);
            try {
                desc = rb.getString(errorCode);
                desc = MessageFormat.format(desc, errorArgs);
            } catch (MissingResourceException mre) {
                // Ignore as we'd set it to erro desc not found
                desc = AcuverResourceUtil.DESC_NOT_FOUND;
                AcuverResourceUtil.logger.error("AcuverResourceUtil :: resolveMsgCode() :: MissingResourceException raised is " +mre);
            } catch (IllegalArgumentException iae) {
                // Ignore. If any error had occured, it'll be evident
                // from the raw text that is present as error description
                desc = AcuverResourceUtil.DESC_NOT_FOUND;
                AcuverResourceUtil.logger.error("AcuverResourceUtil :: resolveMsgCode() :: IllegalArgumentException raised is " +iae);
            }
        }
        return desc;
    }

}
