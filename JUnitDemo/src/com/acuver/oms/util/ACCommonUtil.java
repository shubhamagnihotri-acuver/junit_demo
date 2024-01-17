package com.acuver.oms.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

//import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.acuver.oms.constants.Constants;
import com.sterlingcommerce.baseutil.SCXmlUtil;
import com.yantra.interop.japi.YIFApi;
import com.yantra.interop.japi.YIFClientFactory;
import com.yantra.yfc.log.YFCLogCategory;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

public class ACCommonUtil implements Constants {

	private static YFCLogCategory logger = YFCLogCategory.instance(ACCommonUtil.class);

	public static final String GET_ORDER_LIST_INPUT = "<Order OrderHeaderKey='%s'/>";
	public static String commonCodePath =  "/CommonCodeList/CommonCode";
	public static YIFApi api;

	/*
	 * static { try { ACCommonUtil.api = YIFClientFactory.getInstance().getApi(); }
	 * catch (Exception e) { throw new YFSException(e.getMessage()); } }
	 */

	public static Document invokeAPI(YFSEnvironment env, Document template, String apiName, Document inDoc)
			throws YFSException, RemoteException {

		env.setApiTemplate(apiName, template);

		Document returnDoc = ACCommonUtil.api.invoke(env, apiName, inDoc);

		env.clearApiTemplate(apiName);
		return returnDoc;
	}

	/**
	 * Invokes a Sterling Commerce Service.
	 *
	 * @param env         Sterling Commerce Environment Context.
	 * @param serviceName Name of Service to invoke.
	 * @param inDoc       Input Document to be passed to the Service.
	 * @throws Exception Exception thrown by the Service.
	 * @return Output of the Service.
	 * @throws RemoteException
	 * @throws YFSException
	 */
	public static Document executeFlow(YFSEnvironment env,
            String strServiceName, Document docInXMLDoc) throws Exception {

        // logger.debug("=============== executeFlow()....docInXMLDoc: "+XmlUtils.getString(docInXMLDoc));
        YIFApi yifApi = YIFClientFactory.getInstance().getLocalApi();
        // logger.debug("=============== executeFlow()....docOutputXml: "+XmlUtils.getString(docOutputXml));

        return yifApi.executeFlow(env, strServiceName, docInXMLDoc);
    }

	/**
	 * Invokes a getCommonCodeList Api.
	 *
	 * @param env         Sterling Commerce Environment Context.
	 * @param serviceName Name of api to invoke.
	 * @param inDoc       Input Document to be passed to the api.
	 * @throws Exception Exception thrown by the api.
	 * @return Output of the Service.
	 * @throws RemoteException
	 * @throws YFSException
	 */

	public static Document getCommonCodeList(YFSEnvironment env, String strCommonCodeType, String strOrganizationCode) {
		Document docCommonCodeList = null;
		try {
			Document docInput = ACXMLUtil.createDocument(Constants.E_COMMON_CODE);
			Element eleInput = docInput.getDocumentElement();
			eleInput.setAttribute(Constants.ATTR_ORG_CODE, strOrganizationCode);
			eleInput.setAttribute(Constants.ATTR_CODE_TYPE, strCommonCodeType);
			logger.debug("ACCommonUtil getCommonCodeList api Input : " + SCXmlUtil.getString(docInput));
			docCommonCodeList = invokeAPI(env, null, Constants.API_GET_COMMON_CODE_LIST, docInput);
			logger.debug("ACCommonUtil getCommonCodeList api Output : " + SCXmlUtil.getString(docCommonCodeList));
		} catch (ParserConfigurationException | YFSException | RemoteException e) {
			throw new YFSException(e.getMessage());
		}
		return docCommonCodeList;
	}

	/**
	 * Invokes to generate access token for IV call.
	 *
	 * @param String tenant ID tenant id for IV.
	 * @param String client ID Client Id for IV
	 * @param String clientSecret client secret for IV
	 * @throws RemoteException
	 * @throws YFSException
	 */

	public static String getAccessToken(String tenantId, String clientId, String clientSecret) throws IOException {
		String strUrl = "https://edge-api.watsoncommerce.ibm.com/inventory/" + tenantId
				+ "/v1/configuration/oauth2/token";
		URL url = new URL(strUrl);
		String urlParameters = "grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret
				+ "";
		byte[] postData = urlParameters.getBytes();
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			try (DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {
				dos.write(postData);
			}

			StringBuilder content;
			try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
				String line;
				content = new StringBuilder();
				while ((line = br.readLine()) != null) {
					content.append(line);
				}
			}
			// Parse to JSON and read access-token
			String strContent=content.toString();
			logger.verbose("strContent is :"+strContent);
			//JSONObject jsobj = new JSONObject(strContent);
			//logger.verbose("access_token is :"+jsobj.getString("access_token"));
			return "";
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	
	
	/**
	 * @param env
	 * @param sOrgCode
	 * @param commonCodeType
	 * @return
	 * @throws YFSException
	 * @throws RemoteException
	 */
	public static List<String> getCommonCodeValueList(YFSEnvironment env, String sOrgCode, String commonCodeType) throws YFSException, RemoteException
	{
		Document docInCommonCodeXML = SCXmlUtil.createDocument(E_COMMON_CODE);
		Element rootElement = docInCommonCodeXML.getDocumentElement();
		rootElement.setAttribute(ATTR_CODE_TYPE, commonCodeType);
		rootElement.setAttribute(ATTR_ORGANIZATION_CODE, sOrgCode);

		Document commonCodeAPIDoc = ACCommonUtil.invokeAPI(env, null, API_GET_COMMON_CODE_LIST, docInCommonCodeXML);
		logger.verbose("ACCommonUtil :: getCommonCodeValueList : Common Code Op - "+SCXmlUtil.getString(commonCodeAPIDoc));

		NodeList nlCommonCode = commonCodeAPIDoc.getDocumentElement().getElementsByTagName(E_COMMON_CODE);
		List<String> commonCodeValueList = new ArrayList<String>();

		for (int i = 0; i < nlCommonCode.getLength(); i++) {
			Element eleCommonCode = (Element)nlCommonCode.item(i);
			commonCodeValueList.add(eleCommonCode.getAttribute(ATTR_CODE_VALUE));
		}
		logger.verbose("ACCommonUtil :: getCommonCodeValueList : commonCodeValueList - "+commonCodeValueList);

		return commonCodeValueList;
	}


	/**
	 * This method returns map with MapKey as CodeValue and MapValue as Code Short Desc
	 * @param env
	 * @param sOrgCode
	 * @param CommonCodeType
	 * @return
	 * @throws YFSException
	 * @throws RemoteException
	 */
	public static Map<String, String> getCommonCodeValueShortDesc(YFSEnvironment env, String sOrgCode, String CommonCodeType) throws YFSException, RemoteException
	{
		Document docInCommonCodeXML = SCXmlUtil.createDocument(E_COMMON_CODE);
		Element rootElement = docInCommonCodeXML.getDocumentElement();
		rootElement.setAttribute(ATTR_CODE_TYPE, CommonCodeType);
		rootElement.setAttribute(ATTR_ORGANIZATION_CODE, sOrgCode);

		Document commonCodeAPIDoc = ACCommonUtil.invokeAPI(env, null, API_GET_COMMON_CODE_LIST, docInCommonCodeXML);
		logger.verbose("ACCommonUtil :: getCommonCodeValueShortDesc : Common Code Op - "+SCXmlUtil.getString(commonCodeAPIDoc));

		NodeList nlCommonCode = commonCodeAPIDoc.getDocumentElement().getElementsByTagName(E_COMMON_CODE);
		Map<String, String> mapCodeValShortDesc = new HashMap<String, String>();

		for (int i = 0; i < nlCommonCode.getLength(); i++) {
			Element eleCommonCode = (Element)nlCommonCode.item(i);
			String codeShortDesc = eleCommonCode.getAttribute(ATTR_CODE_SHORT_DESC);
			String codeValue = eleCommonCode.getAttribute(ATTR_CODE_VALUE);
			mapCodeValShortDesc.put(codeValue,codeShortDesc);
		}
		logger.verbose("ACCommonUtil :: getCommonCodeValueShortDesc : mapCodeValShortDesc - "+mapCodeValShortDesc);

		return mapCodeValShortDesc;
	}

}
