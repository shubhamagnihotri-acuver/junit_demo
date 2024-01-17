package com.acuver.oms.api;

import static org.junit.Assert.*;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.acuver.oms.util.ACCommonUtil;
import com.acuver.oms.util.AcuverXMLUtil;
import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Tested;

public class GetOrderListTest {

	@Tested
	GetOrderList getOrderList;
	
	@Injectable
	public YFSEnvironment env;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		//MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		try {
			GetOrderList getOrderList = new GetOrderList();
			
			final Document docEmpty = null;

			new MockUp<ACCommonUtil>() {
				@Mock
				Document invokeAPI(YFSEnvironment env, Document temp, String apiName, Document inDoc) throws YFSException, RemoteException{
					switch(apiName) {
					case "getOrderList":
						String strGetOrderListOutput = "<OrderList><Order OrderHeaderKey=\"\" OrderNo=\"\" EnterpriseCode=\"\" DocumentType=\"\">"
								+ "</Order></OrderList>";

						try {
							return AcuverXMLUtil.getDocument(strGetOrderListOutput);
						} catch (ParserConfigurationException | SAXException | IOException e) {
							e.printStackTrace();
						}
					default:
						return docEmpty;
					}
				}
			};
			
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			document.createElement("Input");

			getOrderList.getOrderList(env, document);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	

}
