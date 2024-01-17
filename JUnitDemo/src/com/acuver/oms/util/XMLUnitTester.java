package com.acuver.oms.util;

import com.sterlingcommerce.baseutil.SCXmlUtil;
import org.custommonkey.xmlunit.*;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author patrick.blake
 *
 * XMLUnitTeser Diff provides more detailed explanation when an error occurs.
 *
 * XMLUnit Documentation - http://xmlunit.sourceforge.net/api/org/custommonkey/xmlunit/package-summary.html
 */
public class XMLUnitTester extends XMLTestCase {


    /**
     * Test to validate the XML markup are matching
     * @param testXML
     * @param expectedXML
     * @throws Exception
     */
    @Test
    public void testForEquality(String testXML, String expectedXML) throws Exception {
        assertXMLEqual("comparing test xml to expected xml", testXML, expectedXML);
        assertXMLNotEqual("test xml not similar to expected xml", testXML, expectedXML);
    }

    @Test
    public  void testForEquality(Document testDocument, Document expectedDocument) throws Exception {
        assertXMLEqual("comparing test xml to expected xml", SCXmlUtil.getString(testDocument), SCXmlUtil.getString(expectedDocument));

    }

    /**
     * checks the XML are identical - noted to be too strict asertXMLEqual can be sufficient
     * @param testXML
     * @param expectedXML
     * @throws Exception
     */
    @Test
    public void testIdentical(String testXML, String expectedXML) throws Exception {
        Diff myDiff = new Diff(testXML, expectedXML);
        AssertJUnit.assertTrue("testIdentical.similar? " + myDiff, myDiff.similar());
        AssertJUnit.assertTrue("testIdentical.identical? " + myDiff, myDiff.identical());
    }

    /**
     * checks for differences between XML Files and asserts this is 0 in size
     * @param testXML
     * @param expectedXML
     * @throws Exception
     */
    @Test
    public void testAllDifferences(String testXML, String expectedXML) throws Exception {
        DetailedDiff myDiff = new DetailedDiff(compareXML(expectedXML, testXML));
        List<DetailedDiff> allDifferences = myDiff.getAllDifferences();
        AssertJUnit.assertEquals(myDiff.toString(), 0, allDifferences.size());
    }

    /**
     *
     *checks to compare the XML Skeleton of two XML files ignoring attribute values
     * @param testXML
     * @param expectedXML
     * @throws Exception
     */
    @Test
    public void testCompareToSkeletonXML(String testXML, String expectedXML) throws Exception {
        DifferenceListener myDifferenceListener = new IgnoreTextAndAttributeValuesDifferenceListener();
        Diff myDiff = new Diff(expectedXML, testXML);
        myDiff.overrideDifferenceListener(myDifferenceListener);
        AssertJUnit.assertTrue("test XML matches control skeleton XML " + myDiff, myDiff.similar());
    }

    /**
     *
     * @param testXML
     * @param expectedXML
     * @throws Exception
     */
    @Test
    public void testRepeatedChildElements(String testXML, String expectedXML) throws Exception {
        assertXMLNotEqual("Repeated child elements in different sequence order are not equal by default",
                expectedXML, testXML);

        Diff myDiff = new Diff(expectedXML, testXML);
        myDiff.overrideElementQualifier(new ElementNameAndTextQualifier());
        assertXMLEqual("But they are equal when an ElementQualifier controls which test element is compared with each control element",
                myDiff, true);
    }

    /**
     * Checks XPaths are valid in an XML file
     * @param testXML
     * @param xPaths
     * @throws Exception
     */
    @Test
    public void testXPaths(String testXML, ArrayList<String> xPaths) throws Exception {
        for (String xPath : xPaths) {
            assertXpathExists(xPath, testXML);
            assertXpathNotExists(xPath, testXML);
        }
    }

    /**
     * Checks an Attribute value is equal to input
     * @param testXML
     * @param value
     * @param xpath
     * @throws Exception
     */
    @Test
    public void testXPathValuesEquals(String testXML, String value, String xpath) throws Exception {
        assertXpathEvaluatesTo(value, xpath, testXML);
    }

    /**
     *  Checks an Attribute value is not equal to input
     * @param testXML
     * @param value
     * @param xpath
     * @throws Exception
     */
    @Test
    public void testXPathValuesNotEquals(String testXML, String value, String xpath) throws Exception {
        assertXpathValuesNotEqual(value, xpath, testXML);
    }
}
