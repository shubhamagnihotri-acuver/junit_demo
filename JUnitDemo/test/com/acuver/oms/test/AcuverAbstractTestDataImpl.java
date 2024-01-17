package com.acuver.oms.test;

import com.acuver.oms.util.AcuverUnitTestCommonUtils;
import com.acuver.oms.util.AcuverUnitTestContants;
import com.acuver.oms.util.AcuverUnitTestLoggerUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class AcuverAbstractTestDataImpl implements AcuverTestData{
    /**
     * This property holds the data being saved into the test data Object
     */
    private Map<String, Object> mDataMap = new ConcurrentHashMap<String, Object>();


    /* (non-Javadoc)
     * @see com.ikea.ibm.som.test.vo.IKEATestData#addProperty(java.lang.String, java.lang.Object)
     */
    public void addProperty(String pPropertyName, Object pValueObj) {
        if ((this.mDataMap != null) && AcuverUnitTestCommonUtils.isNotNullOrEmpty(pPropertyName) && (pValueObj!=null)) {
            mDataMap.put(pPropertyName, pValueObj);
        }else{
            AcuverUnitTestLoggerUtil.logDebug(this, " Unable to save data as pPropertyName :: " + pPropertyName +
                    " or pValueObj :: "+ ((pValueObj==null) ? null :pValueObj.toString()) + " is null or empty,  ");
        }
    }

    /**
     * Adds errors the the test data object
     *
     * @param pException
     */
    @SuppressWarnings("unchecked")
    public void addError(Exception pException){
        List<Exception> errors = (List<Exception>) getProperty(AcuverUnitTestContants.STR_ERRORS);
        if(AcuverUnitTestCommonUtils.isEmpty(errors)){
            errors = new ArrayList<Exception>();
        }
        AcuverUnitTestLoggerUtil.logDebug(this, "Adding exception with  message :: " + pException.toString() + " for :: " + getName());
        errors.add(pException);
        addProperty(AcuverUnitTestContants.STR_ERRORS, errors);
    }

    /**
     * Gets all the errors added to the test data object
     *
     * @return
     */
    public List<Exception> getErrors(){
        return (List<Exception>) getProperty(AcuverUnitTestContants.STR_ERRORS);
    }



    /* (non-Javadoc)
     * @see com.ikea.ibm.som.test.vo.IKEATestData#getProperty(java.lang.String)
     */
    public Object getProperty(String pPropertyName) {
        Object valObj = null;
        if (AcuverUnitTestCommonUtils.isNotNullOrEmpty(pPropertyName) && (!isEmpty())) {
            valObj = mDataMap.get(pPropertyName);
            if(valObj==null){
                AcuverUnitTestLoggerUtil.logDebug(this, " No value found with pPropertyName :: " + pPropertyName);
            }
        }else{
            AcuverUnitTestLoggerUtil.logDebug(this, " Unable to fetch the value for pPropertyName :: " + pPropertyName +" is empty or null");
        }
        AcuverUnitTestLoggerUtil.logDebug(this, "Returning value :: " + valObj +
                " for pPropertyName :: " + pPropertyName);
        return valObj;
    }

    /* (non-Javadoc)
     * @see com.ikea.ibm.som.test.vo.IKEATestData#getStringProperty(java.lang.String)
     */
    public String getStringProperty(String pPropertyName) {
        String strValue = null;
        Object strObj = getProperty(pPropertyName);
        if (strObj instanceof String) {
            strValue = (String) strObj;
        }else{
            AcuverUnitTestLoggerUtil.logDebug(this, "No String value found with the pPropertyName :: "
                    + pPropertyName + " Value found :: " + strObj);
        }
        return strValue;
    }

    /* (non-Javadoc)
     * @see com.ikea.ibm.som.test.vo.IKEATestData#getIntegerProperty(java.lang.String)
     */
    public Integer getIntegerProperty(String pPropertyName) {
        Integer intValue = null;
        Object objectVal = getProperty(pPropertyName);
        if (objectVal instanceof Integer) {
            intValue = (Integer) objectVal;
        } else {
            // as the value is not stored as integer attempting to parse the
            // String property to integer
            String str = getStringProperty(pPropertyName);
            if (AcuverUnitTestCommonUtils.isNotNullOrEmpty(str)) {
                intValue = Integer.parseInt(str);
            }
        }
        AcuverUnitTestLoggerUtil.logDebug(this,
                "Returning value :: " + intValue + " for pPropertyName :: " + pPropertyName);
        return intValue;
    }

    /* (non-Javadoc)
     * @see com.ikea.ibm.som.test.vo.IKEATestData#getAllProperties()
     */
    public Map<String, Object> getAllProperties() {
        Map<String,Object> dataMap = null;
        if(!isEmpty()){
            dataMap = Collections.unmodifiableMap(mDataMap);
        }
        return dataMap;
    }


    /**
     * Checks is the data map is empty or null, returns true if it is else, false
     * @return
     */
    public boolean isEmpty() {
        boolean isEmpty = AcuverUnitTestCommonUtils.isEmpty(mDataMap);
        if(isEmpty){
            AcuverUnitTestLoggerUtil.logDebug(this, " DataMap is empty");
        }
        return isEmpty;
    }

    @Override
    public String getName() {
        return null;
    }


    /**
     * Sets the data map to the test data object.
     * The pData map should be an instance of the ConcurrentHashMap.
     * @param pDataMap
     */
    public void setDataMap(ConcurrentHashMap<String, Object> pDataMap) {
        mDataMap = pDataMap;
    }

    protected Map<String, Object> getDataMap(){
        return mDataMap;

    }
    /* (non-Javadoc)
     * @see com.ikea.ibm.som.test.vo.IKEATestData#removeProperty(java.lang.String)
     */
    @Override
    public void removeProperty(String pPropertyName) {
        if(AcuverUnitTestCommonUtils.isNullOrEmpty(pPropertyName)){
            if(!isEmpty()){
                mDataMap.remove(pPropertyName);
            }
        }else{
            AcuverUnitTestLoggerUtil.logDebug(this, "Cannot remove the Property, pPropertyName :: "+
                    pPropertyName + " is null or empty");
        }
    }


    /* (non-Javadoc)
     * @see com.ikea.ibm.som.test.vo.IKEATestData#getErrorRecordsCount()
     */
    @Override
    public int getErrorRecordsCount() {
        return getErrors()!=null ? getErrors().size(): AcuverUnitTestContants.INT_ZERO;
    }
}
