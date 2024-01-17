package com.demo;

import static org.junit.Assert.*;

import java.sql.Connection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

//import com.ikea.ibm.som.util.IKEACommonUtil;
import com.yantra.ycp.core.YCPContext;
import com.yantra.yfs.japi.YFSEnvironment;

import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

public class GetDeliveryArrangementsTest {

	@Injectable
	public YFSEnvironment env;
	
	@Mocked
	Connection conn;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("In setUpBeforeClass");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("In tearDownAfterClass");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("In setUp");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("In tearDown");
	}

	@Test
	public void firstTestCase() throws ParserConfigurationException {
		//fail("Not yet implemented");
		
		System.out.println("In firstTestCase");
		//mockEnvironmentVariable();
		
		mockAMethod();
		
		new MockUp<GetDeliveryArrangements>() {
	        @Mock
	        public boolean getALife(String value) {
	            return false;
	        }
	    };
		
		GetDeliveryArrangements GDA = new GetDeliveryArrangements();
		
		//GDA1 GDA = new GDA1();
		
		//String strDoc = "<Input />";
		
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		 
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();
        document.createElement("Input");
        
		GDA.getDeliveryArrangements(env, document);
	}
 
	private void mockAMethod() {
		// TODO Auto-generated method stub
		
	}

	private void mockEnvironmentVariable() {
		
		//YFSEnvironment ycp = null;
		try {
			
			System.out.println("Hello");
			new MockUp<YCPContext>() {
				@Mock
				void $clinit() {
				}

				@Mock
				void $init(String a, String b) {
				}

				@Mock()
				Connection getDBConnection() {
					return conn;
				}
			};
			
			//ycp = new YCPContext("Promise", "ycp");
			env = new YCPContext("Promise", "ycp");

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
