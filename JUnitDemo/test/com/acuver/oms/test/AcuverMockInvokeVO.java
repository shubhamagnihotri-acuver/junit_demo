package com.acuver.oms.test;
import org.w3c.dom.Document;
public class AcuverMockInvokeVO {


    private String apiName;
    private String templateName;
    private Document expectedDoc;
    private Document mockedDoc;
    private Document templateDoc;

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Document getExpectedDoc() {
        return expectedDoc;
    }

    public void setExpectedDoc(Document expectedDoc) {
        this.expectedDoc = expectedDoc;
    }

    public Document getMockedDoc() {
        return mockedDoc;
    }

    public void setMockedDoc(Document mockedDoc) {
        this.mockedDoc = mockedDoc;
    }

    public Document getTemplateDoc() {
        return templateDoc;
    }

    public void setTemplateDoc(Document templateDoc) {
        this.templateDoc = templateDoc;
    }

}
