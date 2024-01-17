package com.acuver.oms.condition;

import java.util.Map;

import org.w3c.dom.Document;

import com.acuver.oms.constants.ACApplicationConstants;
import com.acuver.oms.util.ACXMLUtil;
import com.yantra.ycp.japi.YCPDynamicConditionEx;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.dom.YFCNodeList;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCDoubleUtils;
import com.yantra.yfs.japi.YFSEnvironment;

public class IsValidInvoice implements YCPDynamicConditionEx, ACApplicationConstants{
	private static final YFCLogCategory logger = YFCLogCategory.instance(IsValidInvoice.class);

	@Override
	public boolean evaluateCondition(YFSEnvironment arg0, String arg1, Map arg2, Document inDoc) {
		logger.beginTimer("IsValidInvoice | evaluateCondition : BEGIN");
		
		boolean isValid = false;
		YFCElement eleInput = YFCDocument.getDocumentFor(inDoc).getDocumentElement();
		YFCNodeList<YFCElement> nlLineDetail = eleInput.getElementsByTagName(E_LINE_DETAIL);
		
		Double InvHeaderTotalAmount = Double.parseDouble(ACXMLUtil.extractAttribute(inDoc, "/InvoiceDetail/InvoiceHeader/@TotalAmount"));
		Double InvHeaderCharges = Double.parseDouble(ACXMLUtil.extractAttribute(inDoc, "/InvoiceDetail/InvoiceHeader/@HeaderCharges"));
		Double InvHeaderTax = Double.parseDouble(ACXMLUtil.extractAttribute(inDoc, "/InvoiceDetail/InvoiceHeader/@HeaderTax"));
		Double InvHeaderAmountCollected = Double.parseDouble(ACXMLUtil.extractAttribute(inDoc, "/InvoiceDetail/InvoiceHeader/@AmountCollected"));
		Double sumofLineTotal = Double.parseDouble(ACXMLUtil.extractAttribute(inDoc, String.format("sum(InvoiceDetail/InvoiceHeader/LineDetails/LineDetail/@LineTotal)")));
		Double sumofLineTotalAndHeaderCharges = sumofLineTotal + InvHeaderCharges + InvHeaderTax;
		Double fSumofLineTotalAndHeaderCharges = YFCDoubleUtils.roundOff(sumofLineTotalAndHeaderCharges, 2);
		
		for(YFCElement eachLineDetail : nlLineDetail ) {
			
			Double extendedPrice = Double.parseDouble(eachLineDetail.getAttribute(ATTR_EXTENDED_PRICE));
			Double charges = Double.parseDouble(eachLineDetail.getAttribute(ATTR_CHARGES));
			Double tax = Double.parseDouble(eachLineDetail.getAttribute(ATTR_TAX));
			Double lineTotal = Double.parseDouble(eachLineDetail.getAttribute(ATTR_LINE_TOTAL));
			
			Double totalamount = YFCDoubleUtils.roundOff((extendedPrice + charges + tax), 2);
			if(totalamount.equals(lineTotal)) {
				isValid = true;
			}else {
				isValid = false;
			}
		}
		
		if(fSumofLineTotalAndHeaderCharges.equals(InvHeaderTotalAmount) && InvHeaderTotalAmount.equals(InvHeaderAmountCollected)) {
			isValid = true;
		}else 
			isValid = false;
		
		logger.endTimer("IsValidInvoice | evaluateCondition : END");
		return isValid;
	}

	@Override
	public void setProperties(Map arg0) {
		// TODO Auto-generated method stub
		
	}

}
