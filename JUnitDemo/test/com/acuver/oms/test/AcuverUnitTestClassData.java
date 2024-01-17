package com.acuver.oms.test;

import com.acuver.oms.util.AcuverUnitTestContants;
import com.acuver.oms.util.AcuverUnitTestLoggerUtil;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acuver.oms.util.AcuverUnitTestCommonUtils;

public class AcuverUnitTestClassData extends AcuverAbstractTestDataImpl{

    /**
     * Constructs the AcuverUnitTestClassData with the class name pClassName.
     *
     */
    public AcuverUnitTestClassData(String pClassName) {
        if (AcuverUnitTestCommonUtils.isNullOrEmpty(pClassName)) {
            throw new IllegalArgumentException("pClassName :: " + pClassName
                    + " cannot be null or empty");
        }
        addProperty(AcuverUnitTestContants.STR_UNIT_TEST_CLASS_NAME, pClassName);
    }

    /**
     * Constructs the AcuverUnitTestClassData with pTestMethodData and with the
     * class name pClassName.
     *
     */
    public AcuverUnitTestClassData(String pClassName,
                                 Map<String, List<AcuverTestMethodData>> pTestMethodData) {
        if (AcuverUnitTestCommonUtils.isNullOrEmpty(pClassName)) {
            throw new IllegalArgumentException("pClassName :: " + pClassName
                    + " cannot be null or empty");
        }
        addProperty(AcuverUnitTestContants.STR_UNIT_TEST_CLASS_NAME, pClassName);
        addProperty(AcuverUnitTestContants.STR_TEST_METHOD_DATA, pTestMethodData);
    }

    /**
     * Constructs the AcuverUnitTestClassData
     *
     */
    public AcuverUnitTestClassData() {
    };

    /**
     * Adds the test method data for the respective class. Use the
     * AcuverTestMethodData.getTestMethodName() to identify the test method name ,
     * the test data belongs to. pSeqNumber is set to the pTestMethodDataObj
     * before saving.
     *
     * @param pSeqNumber
     * @param pTestMethodDataObj
     */
    public void addTestMethodData(int pSeqNumber,
                                  AcuverTestMethodData pTestMethodDataObj) {
        // getting the test method data
        Map<String, List<AcuverTestMethodData>> testMethodData = getAllTestMethodData();
        List<AcuverTestMethodData> testMethodList = null;

        // initializing the required objects if the testMethodData is null
        if (testMethodData == null) {
            testMethodData = new HashMap<String, List<AcuverTestMethodData>>();
            testMethodList = new ArrayList<AcuverTestMethodData>();
        } else {
            testMethodList = testMethodData.get(pTestMethodDataObj
                    .getTestMethodName());
            if (testMethodList == null) {
                testMethodList = new ArrayList<AcuverTestMethodData>();
            }
        }
        pTestMethodDataObj.setTestDataSequenceNumber(pSeqNumber);
        testMethodList.add(pTestMethodDataObj);
        // saving the data
        testMethodData.put(pTestMethodDataObj.getTestMethodName(),
                testMethodList);
        addProperty(AcuverUnitTestContants.STR_TEST_METHOD_DATA, testMethodData);
    }

    /**
     * Combines all the test data for the Unit test class and returns a list of
     * AcuverTestMethodData. Returns null if there is no test data found for the
     * class.
     *
     * @return
     */
    public List<AcuverTestMethodData> getAllTestDataList() {
        // getting the test method data
        Map<String, List<AcuverTestMethodData>> testMethodData = getAllTestMethodData();
        List<AcuverTestMethodData> testMethodList = null;

        if (AcuverUnitTestCommonUtils.isNotEmpty(testMethodData)) {
            for (List<AcuverTestMethodData> dataList : testMethodData.values()) {
                if (testMethodList == null) {
                    testMethodList = new ArrayList<AcuverTestMethodData>();
                }
                if (AcuverUnitTestCommonUtils.isNotEmpty(dataList)) {
                    testMethodList.addAll(dataList);
                }
            }
        }
        if (AcuverUnitTestCommonUtils.isEmpty(testMethodList)) {
            AcuverUnitTestLoggerUtil.logDebug(this,
                    "No test method data found for Unit test Class :: "
                            + getUnitTestClassName());
        } else {
            AcuverUnitTestLoggerUtil.logDebug(this,
                    "Found :: " + testMethodList.size()
                            + " test data sets for Unit test Class "
                            + getUnitTestClassName());
        }
        return testMethodList;
    }

    /**
     * Returns the Map of test method , with key as the test method name and
     * value - List<AcuverTestMethodData>. Returns null if no data is found for
     * the test methods
     *
     * @return
     */
    public Map<String, List<AcuverTestMethodData>> getAllTestMethodData() {
        return (Map<String, List<AcuverTestMethodData>>) getProperty(AcuverUnitTestContants.STR_TEST_METHOD_DATA);
    }

    /**
     * Returns the test class name for which the AcuverUnitTestClassData is
     * created
     *
     * @return
     */
    public String getUnitTestClassName() {
        return getStringProperty(AcuverUnitTestContants.STR_UNIT_TEST_CLASS_NAME);
    }

    /**
     * Returns the test class name for which the AcuverUnitTestClassData is
     * created
     *
     * @return
     */
    public void setUnitTestClassName(String pUnitTestClassName) {
        addProperty(AcuverUnitTestContants.STR_UNIT_TEST_CLASS_NAME,
                pUnitTestClassName);
    }

    /**
     * Returns the complete path of the <i>"TestData.csv"</i> for the respective
     * Unit test class, from which the unit test data is extracted.
     *
     *
     * @return
     */
    public String getTestDataFilePath() {
        return getStringProperty(AcuverUnitTestContants.STR_TEST_DATA_CSV_DATA_PATH);
    }

    /**
     * sets the <i>"TestData.csv"</i> path from which the unit test data is
     * loaded.
     *
     * @param pTestDataPath
     */
    public void setTestDataFilePath(String pTestDataPath) {
        addProperty(AcuverUnitTestContants.STR_TEST_DATA_CSV_DATA_PATH,
                pTestDataPath);
    }

    /**
     * Returns the list of AcuverTestMethodData objects for the test method.
     *
     * @param pMethodName
     */
    public List<AcuverTestMethodData> getTestDataForMethod(String pMethodName) {
        List<AcuverTestMethodData> testMethodData = null;
        Map<String, List<AcuverTestMethodData>> testMethodDataMap = getAllTestMethodData();
        if (AcuverUnitTestCommonUtils.isNotNullOrEmpty(pMethodName)
                && AcuverUnitTestCommonUtils.isNotEmpty(testMethodDataMap)) {
            testMethodData = testMethodDataMap.get(pMethodName);
            if (testMethodData == null || testMethodData.size() == 0){
                Assert.fail("Test Data for the method is not configured");
            }
        }
        AcuverUnitTestLoggerUtil.logDebug(this, "Found :: "
                + (testMethodData == null ? AcuverUnitTestContants.INT_ZERO
                : testMethodData.size())
                + " test data entries for the test method " + pMethodName
                + ", of Class :: " + getUnitTestClassName());
        ;
        return testMethodData;
    }

    /**
     * Returns the AcuverTestMethodData for the test method with name -
     * pMethodName and with sequence number - pTestDataSeqNumber.
     *
     * @param pMethodName
     * @param pTestDataSeqNumber
     */
    public AcuverTestMethodData getTestDataForMethod(String pMethodName,
                                                   int pTestDataSeqNumber) {
        AcuverTestMethodData testMethodData = null;
        List<AcuverTestMethodData> testMethodDataList = getTestDataForMethod(pMethodName);
        Integer seqNum = null;
        if (AcuverUnitTestCommonUtils.isNotEmpty(testMethodDataList)) {
            for (AcuverTestMethodData AcuverTestMethodData : testMethodDataList) {
                seqNum = AcuverTestMethodData.getTestDataSequenceNumber();
                if (seqNum != null
                        && seqNum.equals(Integer.valueOf(pTestDataSeqNumber))) {
                    testMethodData = AcuverTestMethodData;
                    break;
                }
            }
        }
        if (testMethodData == null) {
            AcuverUnitTestLoggerUtil.logDebug(this,
                    "Found no test Data for the method :: " + pMethodName
                            + " of Test data Sequence No :: "
                            + pTestDataSeqNumber + " for test class ::"
                            + getUnitTestClassName());
        }
        return testMethodData;
    }

    /**
     * Returns the count of test data loaded for the test class
     *
     * @return
     */
    public int getLoadedRecordCount() {
        return getAllTestDataList() != null ? getAllTestDataList().size()
                : AcuverUnitTestContants.INT_ZERO;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ikea.ibm.som.test.vo.IKEATestData#getName()
     */
    @Override
    public String getName() {
        return getUnitTestClassName();
    }
}
