package com.acuver.oms.api;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.acuver.oms.util.ACCommonUtil;
import com.acuver.oms.util.AcuverXMLUtil;

import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfs.japi.YFSEnvironment;

public class GetOrderList {

	private static final YFCLogCategory logger = YFCLogCategory.instance(GetOrderList.class);
	private static final String GET_ORDER_LIST_TEMPLATE = "<OrderList><Order OrderHeaderKey=\"\" OrderNo=\"\" EnterpriseCode=\"\" DocumentType=\"\">"
			+ "</Order></OrderList>";

	public Document getOrderList(YFSEnvironment env, Document inDoc) {

		Document getOrderListOutDoc = null;
		
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document docGetOrderListInput = docBuilder.newDocument();
			Element rootElement = docGetOrderListInput.createElement("Order");
			docGetOrderListInput.appendChild(rootElement);

			rootElement.setAttribute("OrderNo", "123456");
			rootElement.setAttribute("EnterpriseCode", "ACUVER");

			logger.verbose("Input xml to getOrderList Api " + AcuverXMLUtil.getXMLString(docGetOrderListInput));
			Document getOrderListTemplate = AcuverXMLUtil.getDocument(GET_ORDER_LIST_TEMPLATE);
			getOrderListOutDoc = ACCommonUtil.invokeAPI(env, getOrderListTemplate, "getOrderList",
					docGetOrderListInput);
			logger.verbose("Output of getOrderList Api " + AcuverXMLUtil.getXMLString(getOrderListOutDoc));
			
		} catch(Exception e) {
			
			logger.error("Exception in GetOrderList : " + e);
		}

		return getOrderListOutDoc;
	}

}
