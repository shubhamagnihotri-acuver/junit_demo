package com.acuver.oms.test;
import java.util.Map;

public interface AcuverTestData {

    /**
     *
     * Adds a property to the test data object, if an value already exist with pPropertyName - the old pPropertyObject is overridden.
     * @param pPropertyName
     * @param pObject
     */
    void addProperty(String pPropertyName, Object pPropertyObject);

    /**
     * Removes the property with the pPropertyName from the test data object ,
     * if the pPropertyName doesn't exist no error is thrown.
     * @param pPropertyName
     */
    void removeProperty(String pPropertyName);
    /**
     * Returns the data which is saved/loaded with the key pPropertyName
     * Returns null if no data is found with  pPropertyName
     *
     * @param pPropertyName
     * @return
     */
    Object getProperty(String pPropertyName);

    /**
     *
     * Returns the String property with the key pPropertyName
     *
     * @param pPropertyName
     * @return
     */
    String getStringProperty(String pPropertyName);

    /**
     * Returns the Integer property stored with the key pPropertyName
     *
     * @param pPropertyName
     * @return
     */
    Integer getIntegerProperty(String pPropertyName);

    /**
     * Returns all the properties stored in the test data object.
     * The returned value is an instance of UnmodifiableMap and cannot be modified.
     *
     * @return
     */
    Map<String,Object> getAllProperties();


    /**
     * Returns true is the test data object is empty , else false
     * @return
     */
    boolean isEmpty();

    /**Returns the name of the Test Data object.
     *
     * The implementing classes can return a relevant identifier string as an name.
     *
     * @return
     */
    String getName();

    /**
     * Returns the error record count
     *
     * @return
     */
    int getErrorRecordsCount();
}
