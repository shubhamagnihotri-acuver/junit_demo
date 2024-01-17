package com.acuver.oms.util;


import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
public class AcuverUnitTestCommonUtils {



    /**
     * Returns the string format of the document object. Returns null if the
     * pDocument is null.
     *
     * @param pDocument
     * @return
     */
    public static String documentToString(Document pDocument) {
        String docStr = null;
        // if oDocument is null returning null String
        if (pDocument == null) {
            return docStr;
        }
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer
                    .setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.transform(new DOMSource(pDocument),
                    new StreamResult(sw));
            docStr = sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
        return docStr;
    }

    public static String getCurrentTestExecutionName() {
        return "";
    }

    public static boolean isNotEmpty(Map pMap) {
        return !isEmpty(pMap);
    }

    public static boolean isEmpty(Map pMap) {
        boolean isEmpty = Boolean.FALSE;
        if (pMap == null || pMap.isEmpty()) {
            isEmpty = Boolean.TRUE;
        }
        return isEmpty;
    }

    public static boolean isNotEmpty(List pList) {
        return !isEmpty(pList);
    }

    public static boolean isEmpty(List pList) {
        boolean isEmpty = Boolean.FALSE;
        if (pList == null || pList.isEmpty()) {
            isEmpty = Boolean.TRUE;
        }
        return isEmpty;
    }

    public static boolean isNotNullOrEmpty(String pString) {
        return !(pString == null || pString.trim().length() == 0);
    }

    public static boolean isNullOrEmpty(String pString) {
        return pString == null || pString.trim().length() == 0;
    }

    /**
     *
     * @param rootDir
     * @param extn
     * @return list of files recursively found in the rootDir with extn
     */
    public static Collection<File> getFileExtension(File rootDir, String[] extn) {

        //true searches for the file recursively
        return null;
    }
}
