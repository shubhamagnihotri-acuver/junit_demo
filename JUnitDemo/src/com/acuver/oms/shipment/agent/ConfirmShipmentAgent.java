package com.acuver.oms.shipment.agent;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.acuver.oms.constants.Constants;
import com.acuver.oms.util.ACCommonUtil;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.ycp.japi.util.YCPBaseAgent;
import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.dom.YFCNodeList;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfc.util.YFCCommon;
import com.yantra.yfc.util.YFCException;
import com.yantra.yfc.util.YFCLocale;
import com.yantra.yfs.core.YFSSystem;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;


public class ConfirmShipmentAgent extends YCPBaseAgent implements Constants {

	private static final YFCLogCategory logger = YFCLogCategory.instance(ConfirmShipmentAgent.class);
	private static final String GET_SHIPMENT_TEMPLATE = "<Shipments><Shipment ShipmentKey=\"\"><Containers><Container TrackingNo=\"\" ServiceType=\"\" /></Containers></Shipment></Shipments>";
	
	
	public List<Document> getJobs(YFSEnvironment env, Document inDoc, Document docLastMessage) throws Exception {
		logger.beginTimer("ConfirmShipmentAgent : getJobs : Begin");

		List<Document> ShipmentList = new ArrayList<>();
		Element eleRootinDoc = inDoc.getDocumentElement();
		String maximumRecords = eleRootinDoc.getAttribute(NUM_RECORDS_TO_BUFFER);

		// Input to getShipmentlist api
		YFCDocument docgetShipmentListIn = YFCDocument.createDocument(ELE_SHIPMENT);
		YFCElement eleShipment = docgetShipmentListIn.getDocumentElement();
		eleShipment.setAttribute(ATTR_STATUS, PACKED_STATUS);
		YFCElement getShipmentListInEle = docgetShipmentListIn.getDocumentElement();
		getShipmentListInEle.setAttribute(ATTR_MAXIMUM_RECORDS, maximumRecords);
		if (!YFCCommon.isVoid(docLastMessage)) {
			Element eleLastMessage = docLastMessage.getDocumentElement();
			getShipmentListInEle.setAttribute(ATTR_SHIPMENT_KEY, eleLastMessage.getAttribute(ATTR_SHIPMENT_KEY));
			getShipmentListInEle.setAttribute("ShipmentKeyQryType", "GT");
		}

		logger.verbose("Input xml to getShipmentList Api " + SCXmlUtil.getString(docgetShipmentListIn.getDocument()));
		Document getShipmentListTemplateDoc = YFCDocument.getDocumentFor(GET_SHIPMENT_TEMPLATE).getDocument();
		Document getShipmentListOutDoc = ACCommonUtil.invokeAPI(env, getShipmentListTemplateDoc, API_GET_SHIPMENT_LIST,
				docgetShipmentListIn.getDocument());
		logger.verbose("Output of getShipmentList Api " + SCXmlUtil.getString(getShipmentListOutDoc));
		Element getShipmentListOutEle = getShipmentListOutDoc.getDocumentElement();
		NodeList shipmentList = getShipmentListOutEle.getElementsByTagName(ELE_SHIPMENT);
		int shipmentListLength = shipmentList.getLength();
		for (int i = 0; i < shipmentListLength; i++) {
			Element shipmentEle = (Element) shipmentList.item(i);
			ShipmentList.add(SCXmlUtil.createFromString(SCXmlUtil.getString(shipmentEle)));
		}
		logger.endTimer("ConfirmShipmentAgent : getJobs : End");
		return ShipmentList;
	}
	
	@Override
	public void executeJob(YFSEnvironment env, Document inDoc) throws Exception {
		logger.beginTimer("ConfirmShipmentAgent.executeJob : Begin");
		logger.verbose("ConfirmShipmentAgent.executeJob input: " + SCXmlUtil.getString(inDoc));
		ArrayList<Boolean> StatusFlagList = new ArrayList<Boolean>();
		YFCElement ShipmentEle = YFCDocument.getDocumentFor(inDoc).getDocumentElement();
		YFCNodeList<YFCElement> containerNl = ShipmentEle.getElementsByTagName(E_CONTAINER);
		String serviceType = "";		
		String trackingNo = "";
		for(YFCElement eachContainer : containerNl) {
		        serviceType = eachContainer.getAttribute(ATTR_SERVICE_TYPE);
			trackingNo = eachContainer.getAttribute(ATTR_TRACKING_NO);
			boolean possessionStatusFlag =  prepareConfirmShipmentRequest(env, trackingNo);
			StatusFlagList.add(possessionStatusFlag);
		}
		boolean isAllTrue = !StatusFlagList.contains(false);
		if(isAllTrue) {
			invokeConfirmShipment(env, ShipmentEle);
		}
		logger.endTimer("ConfirmShipmentAgent : executeJob : End");
	}
	
	/**
	 * @param env
	 * @param shipmentEle
	 * @throws YFSException
	 * @throws RemoteException
	 */
	public void invokeConfirmShipment(YFSEnvironment env, YFCElement shipmentEle) throws YFSException, RemoteException {
		logger.beginTimer("ConfirmShipmentAgent : invokeConfirmShipment - Begin");
		
		YFCDocument confirmShipmentInDoc= YFCDocument.createDocument(ELE_SHIPMENT);
		YFCElement eleShipment = confirmShipmentInDoc.getDocumentElement();
		eleShipment.setAttribute(ATTR_SHIPMENT_KEY, shipmentEle.getAttribute(ATTR_SHIPMENT_KEY));
		
		logger.verbose("confirmShipment input"+SCXmlUtil.getString(confirmShipmentInDoc.getDocument()));
		ACCommonUtil.invokeAPI(env, null,API_CONFIRM_SHIPMENT,
				confirmShipmentInDoc.getDocument());
		logger.endTimer("ConfirmShipmentAgent : invokeConfirmShipment : End");
		
	}

	/**
	 * @param env
	 * @param trackingNumber
	 * @return
	 * @throws JSONException
	 * @throws Exception
	 */
	public Boolean prepareConfirmShipmentRequest(YFSEnvironment env, String trackingNumber) throws JSONException, Exception {
		logger.beginTimer("ConfirmShipmentAgent : prepareConfirmShipmentRequest : Begin");
		logger.verbose("ConfirmShipmentAgent: trackingNumber: " + trackingNumber);
		
		URL url = new URL(YFSSystem.getProperty(ATTR_FEDEX_TRACKINGNO_URL));
		boolean possessionStatus = false;

		JSONObject inputjsobj = new JSONObject();
		inputjsobj.put("includeDetailedScans", true);
		
		JSONArray trackingInfoArr = new JSONArray();
		JSONObject trackingInfoObj = new JSONObject();
		JSONObject trackingNumberInfoObj = new JSONObject();
		trackingNumberInfoObj.put("trackingNumber", trackingNumber);
		
		trackingInfoObj.put("trackingNumberInfo", trackingNumberInfoObj);
		trackingInfoArr.put(trackingInfoObj);
		inputjsobj.put("trackingInfo", trackingInfoArr);
		
		String accessToken = "";
		String fedexResponse = "";
		
		JSONObject jsonObj = new JSONObject(fedexResponse); 
		JSONObject trackResultsOjb = jsonObj.getJSONObject("output").getJSONArray("completeTrackResults")
				.getJSONObject(0).getJSONArray("trackResults").getJSONObject(0);
		if (trackResultsOjb.has("shipmentDetails")) {
			JSONObject shipmentDetailsObj = trackResultsOjb.getJSONObject("shipmentDetails");
			possessionStatus = shipmentDetailsObj.getBoolean("possessionStatus");
		} else {
			Exception exp = new Exception();
			throw new YFCException(exp, "ERROR01", "Tracking number cannot be found", "", YFCLocale.getDefaultLocale());
		}
		logger.verbose("PossessionStatus: " + possessionStatus);
		logger.endTimer("ConfirmShipmentAgent.executeJob : End");
		return possessionStatus;
	}
	
	 public static String removePrefix(String tokenNo, String prefix)
	    {
	        if (tokenNo != null && tokenNo.startsWith(prefix)) {
	            return tokenNo.split(prefix, 2)[1];
	        }
	        return tokenNo;
	    }

}
