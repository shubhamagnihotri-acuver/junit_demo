package com.acuver.oms.test;

import com.acuver.oms.util.*;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfs.japi.YFSEnvironment;
import mockit.Injectable;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.List;

/**
 * This is a base class for any Unit test class. Provides the common
 * functionality which can be used in different test classes.
 *
 * @author <a href="mailto:srinivas.simhadri@ikea.com">Srinivas Simhadri</a>
 * @author IBM updated with re-usable code.
 */
@Test(groups = { "unittest" }, timeOut = 10000)
public abstract class AcuverBaseUnitTest {

    /**
     * Holds the logger
     */
    private YFCLogCategory logger = getLogger();

    /**
     * Returns the logger for the class
     *
     * @return
     */
    public YFCLogCategory getLogger() {
        if (logger == null) {
            logger = AcuverUnitTestLoggerUtil.getLogger(this);
        }
        return logger;
    }

    /**
     * Logs a debug message - pMessage for IKEABaseUnitTest
     *
     * @param pMessage
     */
    protected void logDebug(Object pMessage) {
        AcuverUnitTestLoggerUtil.logDebug(this, pMessage);
    }

    /**
     * Logs a error message - pMessage for IKEABaseUnitTest
     *
     * @param pMessage
     */
    protected void logError(Object pMessage) {
        AcuverUnitTestLoggerUtil.logError(this, pMessage);
    }

    public XMLUnitTester xmlUnitTester;

    @Injectable
    public YFSEnvironment env;

    @BeforeClass
    public void before(final Object[] args) {

        getLogger();
        xmlUnitTester = new XMLUnitTester();

    }
    @BeforeMethod
    public void beforeMethod() {
        new MockUp<AcuverCommonUtil>() {
            @Mock
            void $clinit() {
            }
        };
        new MockUp<AcuverResourceUtil>() {
            @Mock
            void $clinit() {
            }

            @Mock
            String get(String name) {
                return "Y";
            }
        };


        new MockUp<AcuverLoggerUtil>(){
            @Mock
            boolean isDebugEnabled() {
                return true;
            }

            @Mock
            boolean isVerboseEnabled(){
                return true;
            }

            @Mock
            boolean isErrorEnabled() {
                return true;
            }

            @Mock
            void debug(String msg, Node aNode){

            }

            @Mock
            void logDebug(String message, Node aNode){

            }

            @Mock
            void verbose(String msg) {

            }
            @Mock
            void error(String msg) {

            }
        };
    }


    protected void mockInvokeAPI(final String expectedApiName,
                                 final Document expectedDoc, final Document mockedDoc) {
        new MockUp<AcuverCommonUtil>() {
            @Mock(invocations=1)
            Document invokeAPI(Invocation inv, YFSEnvironment env,
                               String apiName, Document inDoc) throws Exception {

                if (expectedApiName != null)
                    Assert.assertEquals(apiName, expectedApiName);

                if (expectedDoc != null)
                    xmlUnitTester.testForEquality(inDoc, expectedDoc);

                return mockedDoc;
            }

        };
    }
    protected void mockInvokeAPI(final List<AcuverMockInvokeVO> list) {
        final int size = list.size();
        new MockUp<AcuverCommonUtil>() {
            @Mock(invocations=1)
            Document invokeAPI(Invocation inv, YFSEnvironment env,
                               String apiName, Document inDoc) throws Exception {
                if (inv.getInvocationCount() <= size) {
                    AcuverMockInvokeVO mockInvokeVo = list.get(inv
                            .getInvocationCount() - 1);
                    if (mockInvokeVo != null) {
                        if (mockInvokeVo.getApiName() != null)
                            Assert.assertEquals(apiName,
                                    mockInvokeVo.getApiName());

                        if (mockInvokeVo.getExpectedDoc() != null)
                            xmlUnitTester.testForEquality(inDoc,
                                    (mockInvokeVo.getExpectedDoc()));

                        return mockInvokeVo.getMockedDoc();
                    } else
                        return null;
                } else
                    return null;
            }

        };
    }

    protected void mockInvokeAPITemplate(final List<AcuverMockInvokeVO> list) {
        final int size = list.size();
        new MockUp<AcuverCommonUtil>() {
            @Mock(invocations=1)
            Document invokeAPI(Invocation inv, YFSEnvironment env,
                               String templateName, String apiName, Document inDoc)
                    throws Exception {
                if (inv.getInvocationCount() <= size) {
                    AcuverMockInvokeVO mockInvokeVo = list.get(inv
                            .getInvocationCount() - 1);
                    if (mockInvokeVo != null) {
                        if (mockInvokeVo.getTemplateName() != null)
                            Assert.assertEquals(templateName,
                                    mockInvokeVo.getTemplateName());
                        if (mockInvokeVo.getApiName() != null)
                            Assert.assertEquals(apiName,
                                    mockInvokeVo.getApiName());

                        if (mockInvokeVo.getExpectedDoc() != null)
                            xmlUnitTester.testForEquality(inDoc,
                                    mockInvokeVo.getExpectedDoc());

                        return mockInvokeVo.getMockedDoc();
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        };
    }

    protected void mockInvokeAPITemplateDoc(final List<AcuverMockInvokeVO> list) {

        final int size = list.size();
        new MockUp<AcuverCommonUtil>() {
            @Mock(invocations=1)
            Document invokeAPI(Invocation inv, YFSEnvironment env,
                               Document template, String apiName, Document inDoc)
                    throws Exception {
                if (inv.getInvocationCount() <= size) {
                    AcuverMockInvokeVO mockInvokeVo = list.get(inv
                            .getInvocationCount() - 1);
                    if (mockInvokeVo != null) {

                        if (mockInvokeVo.getTemplateDoc() != null)
                            xmlUnitTester.testForEquality(template,
                                    mockInvokeVo.getTemplateDoc());
                        if (mockInvokeVo.getApiName() != null)
                            Assert.assertEquals(apiName,
                                    mockInvokeVo.getApiName());

                        if (mockInvokeVo.getExpectedDoc() != null)
                            xmlUnitTester.testForEquality(inDoc,
                                    mockInvokeVo.getExpectedDoc());

                        return mockInvokeVo.getMockedDoc();
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        };
    }

    protected void mockInvokeService(final String expectedApiName,
                                     final Document expectedDoc, final Document mockedDoc,
                                     final String expectedTemplate) {
        new MockUp<AcuverCommonUtil>() {
            @Mock(invocations=1)
            Document invokeAPI(Invocation inv, YFSEnvironment env,
                               String templateName, String apiName, Document inDoc)
                    throws Exception {

                Assert.assertEquals(templateName, expectedTemplate);
                Assert.assertEquals(apiName, expectedApiName);

                xmlUnitTester.testForEquality(inDoc, expectedDoc);

                return mockedDoc;
            }

        };
    }

}
