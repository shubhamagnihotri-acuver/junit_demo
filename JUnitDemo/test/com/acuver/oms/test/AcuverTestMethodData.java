package com.acuver.oms.test;

import java.util.List;
import java.util.Map;

import com.acuver.oms.util.AcuverUnitTestContants;
import com.acuver.oms.util.AcuverUnitTestLoggerUtil;
import org.testng.Assert;
import org.w3c.dom.Document;
import com.acuver.oms.util.AcuverUnitTestCommonUtils;


public class AcuverTestMethodData extends AcuverAbstractTestDataImpl{


    /**
     * Constructs the Test data Object with the test method name as - pTestMethodName.
     * The sequence number is defaulted to 1
     *
     * @param pTestMethodName
     */
    public AcuverTestMethodData(String pTestMethodName){
        if (AcuverUnitTestCommonUtils.isNullOrEmpty(pTestMethodName)) {
            throw new IllegalArgumentException("pTestMethodName :: " + pTestMethodName + " cannot be null or empty");
        }
        addProperty(AcuverUnitTestContants.STR_TEST_METHOD_NAME, pTestMethodName);
    }

    /**
     * Constructs the Test data Object with the provided test method name and sequence number.
     * @param pTestMethodName
     *
     */
    public AcuverTestMethodData(String pTestMethodName,Integer pTestDataSeqNumber){
        if (AcuverUnitTestCommonUtils.isNullOrEmpty(pTestMethodName)) {
            throw new IllegalArgumentException("pTestMethodName :: " + pTestMethodName + " cannot be null or empty");
        }
        addProperty(AcuverUnitTestContants.STR_TEST_METHOD_NAME, pTestMethodName);
        addProperty(AcuverUnitTestContants.STR_SEQUENCE_NUMBER, pTestDataSeqNumber);
    }

    /**
     * Constructs the Test data Object with the provided test method name and sequence number.
     * @param pTestMethodName
     *
     */
    public AcuverTestMethodData(AcuverUnitTestClassData pClassData,String pTestMethodName,Integer pTestDataSeqNumber){
        if (AcuverUnitTestCommonUtils.isNullOrEmpty(pTestMethodName)) {
            throw new IllegalArgumentException("pTestMethodName :: " + pTestMethodName + " cannot be null or empty");
        }

        addProperty(AcuverUnitTestContants.STR_TEST_METHOD_NAME, pTestMethodName);
        addProperty(AcuverUnitTestContants.STR_SEQUENCE_NUMBER, pTestDataSeqNumber);
        setClassData(pClassData);
    }


    /**
     * Sets the Unit test class reference
     *
     * @param pClassData
     */
    public void setClassData(AcuverUnitTestClassData pClassData) {
        addProperty(AcuverUnitTestContants.STR_UNIT_TEST_CLASS_DATA, pClassData);
    }


    /**
     * Sets the Unit test class reference
     *
     */
    public AcuverUnitTestCommonUtils getClassData() {
        return (AcuverUnitTestCommonUtils)getProperty(AcuverUnitTestContants.STR_UNIT_TEST_CLASS_DATA);
    }

    /**
     * Returns the boolean property value for the property key.
     *
     * Returns true if the value in set to "True" or "true" in the CSV , the value is case insensitive.
     * Returns false for any other value.
     *
     *
     * @param pPropertyKey
     * @return
     */
    public boolean getBooleanProperty(String pPropertyKey){
        boolean booleanVal = Boolean.FALSE;
        String strBoolValue = getStringProperty(pPropertyKey);
        if(AcuverUnitTestCommonUtils.isNotNullOrEmpty(pPropertyKey)){
            booleanVal = Boolean.parseBoolean(strBoolValue.toLowerCase());
        }
        AcuverUnitTestLoggerUtil.logDebug(this, "Returning boolean value :: " + booleanVal + " for property key :: " + pPropertyKey);
        return booleanVal;
    }

    /**
     * Returns the IKEATestXMLDocument object for the pDocumentKey
     *
     * @param pDocumentKey
     * @return
     */
    public Document getXMLDocument(String pDocumentKey) {
        Object objVal = getProperty(pDocumentKey);
        Document testXMLDoc = null;
        if (objVal != null) {
            if ((objVal instanceof Document)) {
                testXMLDoc = (Document) objVal;
                AcuverUnitTestLoggerUtil.logDebug(this, " Returning XML document :: " + testXMLDoc.getDocumentURI() + " for DocumentKey :: " + pDocumentKey);
            } else {
                AcuverUnitTestLoggerUtil.logDebug(this,"Unable to fetch any XML document for document Key :: " + pDocumentKey);
            }
        }
        return testXMLDoc;
    }

    /**
     * Returns the Input XML document for the test method
     *
     * @return
     */
    public Document getInputXMLDocument(){
        return getXMLDocument(AcuverUnitTestContants.STR_INPUT_XML);
    }

    /**
     * Returns the Output XML document for the test method
     *
     * @return
     */
    public Document getOutputXMLDocument(){
        return getXMLDocument(AcuverUnitTestContants.STR_OUTPUT_XML);
    }

    /**
     * Returns the Mocked XML document for the test method
     *
     * @return
     */
    public Document getMockedXMLDocument(){
        return getXMLDocument(AcuverUnitTestContants.STR_MOCKED_XML);
    }

    /**
     * Returns the test data sequence number
     * @return
     */
    public int getTestDataSequenceNumber(){
        return getIntegerProperty(AcuverUnitTestContants.STR_SEQUENCE_NUMBER);
    }

    /**
     * Returns the test method name
     *
     * @return
     */
    public String getTestMethodName(){
        return getStringProperty(AcuverUnitTestContants.STR_TEST_METHOD_NAME);
    }


    /**
     * Returns the test data sequence number
     * @return
     */
    public void setTestDataSequenceNumber(Integer pSeqNumber){
        addProperty(AcuverUnitTestContants.STR_SEQUENCE_NUMBER, pSeqNumber);
    }

    /**
     * Returns the test method name
     *
     * @return
     */
    public void setTestMethodName(String pTestMethodName){
        Assert.assertTrue(AcuverUnitTestCommonUtils.isNotNullOrEmpty(pTestMethodName));
        addProperty(AcuverUnitTestContants.STR_TEST_METHOD_NAME, pTestMethodName);
    }

    /* (non-Javadoc)
     * @see com.ikea.ibm.som.test.vo.IKEATestData#getName()
     */
    @Override
    public String getName() {
        return getTestMethodName();
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        Map<String, Object> dataMap = this.getDataMap();
        for(Map.Entry<String, Object> entry:dataMap.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            sb.append(key+':'+value+';');
        }
        return sb.toString();

    }

    /* (non-Javadoc)
     * @see com.ikea.ibm.som.test.vo.IKEATestData#getProperty(java.lang.String)
     */
    public Object getProperty(String pPropertyName) {
        Object valObj = null;
        if((! AcuverUnitTestContants.STR_ERRORS.equals(pPropertyName) )&& (! AcuverUnitTestContants.STR_TEST_METHOD_NAME.equals(pPropertyName))){
            if(getErrorRecordsCount() > 0){
                List<Exception> exceptionList = getErrors();
                if(exceptionList != null && exceptionList.size() >= 1){
                    String errorMsg = exceptionList.get(0).getMessage();
                    Assert.fail( errorMsg);
                }
            }
        }
        if (AcuverUnitTestCommonUtils.isNotNullOrEmpty(pPropertyName) && (!isEmpty())) {
            valObj = getDataMap().get(pPropertyName);
            if((! AcuverUnitTestContants.STR_ERRORS.equals(pPropertyName) )&& (! AcuverUnitTestContants.STR_TEST_METHOD_NAME.equals(pPropertyName))){
                if(valObj==null){
                    AcuverUnitTestLoggerUtil.logDebug(this, " No value found with pPropertyName :: " + pPropertyName);
                    Assert.fail("Key: " + pPropertyName + "is either not configured in the CSV file or if its a filename, the file was not loaded : " + getDataMap().keySet());
                }
            }
        }else{
            AcuverUnitTestLoggerUtil.logDebug(this, " Unable to fetch the value for pPropertyName :: " + pPropertyName +" is empty or null");
        }
        AcuverUnitTestLoggerUtil.logDebug(this, "Returning value :: " + valObj +
                " for pPropertyName :: " + pPropertyName);
        return valObj;
    }
}
