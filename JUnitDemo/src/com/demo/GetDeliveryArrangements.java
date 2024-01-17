package com.demo;

import org.w3c.dom.Document;

import com.yantra.yfs.japi.YFSEnvironment;
import com.yantra.yfs.japi.YFSException;

//import com.yantra.yfc.core.YFCObject;
//import com.yantra.yfs.japi.YFSEnvironment;
//import com.yantra.yfs.japi.YFSException;

public class GetDeliveryArrangements {
	
//	public static void main(String args[]) {
//		
//		System.out.println("In main");
//	}
	
	
	public Document getDeliveryArrangements(YFSEnvironment env, Document inDocument) {
		
		try {
			
			System.out.println("In getDeliveryArrangements -- "+inDocument.toString());
			
			String isHappy = inDocument.getDocumentElement().getAttribute("Happy");
			GetDeliveryArrangements.getALife(isHappy);
			
			
		} catch (YFSException e) {
			
			e.printStackTrace();
		}
		
		return inDocument;
	}
	
	public static boolean getALife(String isHappy) {
		
		if("true".equalsIgnoreCase(isHappy))
			return true;
		return false;
		
	}
}

