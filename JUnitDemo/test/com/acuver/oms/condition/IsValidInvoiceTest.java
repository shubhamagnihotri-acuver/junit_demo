package com.acuver.oms.condition;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.acuver.oms.test.AcuverBaseUnitTest;
import com.acuver.oms.util.AcuverXMLUtil;
import com.yantra.yfs.japi.YFSEnvironment;

import mockit.Injectable;

public class IsValidInvoiceTest {

	@Injectable
	public YFSEnvironment env;

	String invoiceIn = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
			"<InvoiceDetail>\r\n" + 
			"	<InvoiceHeader ActualFreightCharge=\"0.00\" AmountCollected=\"53.07\" ChargedActualFreight=\"N\" CollectedExternally=\"\" CollectedThroughAR=\"0.00\" DateInvoiced=\"2023-02-14T11:49:58+00:00\" HeaderCharges=\"0.27\" HeaderDiscount=\"0.00\" HeaderTax=\"0.00\" InvoiceNo=\"5642\" InvoiceType=\"SHIPMENT\" LineSubTotal=\"41.88\" MasterInvoiceNo=\"\" OrderInvoiceKey=\"202302141103495171738360\" Reference1=\"\" ShipNode=\"891\" TotalAmount=\"53.07\" TotalCharges=\"9.22\" TotalDiscount=\"0.00\" TotalHeaderCharges=\"0.27\" TotalTax=\"1.97\">\r\n" + 
			"		<Order CustomerRewardsNo=\"8042503333\" DeliveryCode=\"\" DocumentType=\"0001\" EnteredBy=\"ML_41692545\" EnterpriseCode=\"WM\" EntryType=\"ECOM\" OrderDate=\"2023-02-13T20:20:13+00:00\" OrderHeaderKey=\"20230213202020171408406\" OrderNo=\"81004977965\" SellerOrganizationCode=\"WM\" TermsCode=\"\">\r\n" + 
			"			<PriceInfo Currency=\"USD\"/>\r\n" + 
			"			<Extn ExtnAssociateId=\"\" ExtnIsBOPISOrder=\"N\" ExtnIsMixedOrder=\"N\" ExtnIsRefundOrder=\"\" ExtnIsSTHOrder=\"Y\" ExtnRefundParentOrderNo=\"\" ExtnSalesOrderNo=\"\" ExtnStoreNumber=\"\"/>\r\n" + 
			"			<PersonInfoBillTo AddressLine1=\"2205 Pine St\" AddressLine2=\"\" AddressLine3=\"\" AddressLine4=\"\" AddressLine5=\"\" AddressLine6=\"\" AlternateEmailID=\"\" Beeper=\"\" City=\"Boulder\" Company=\"\" Country=\"US\" DayFaxNo=\"\" DayPhone=\"\" Department=\"\" EMailID=\"denise.kelly@worldmarket.com\" EveningFaxNo=\"\" EveningPhone=\"\" FirstName=\"Tax\" JobTitle=\"\" LastName=\"Tester\" MiddleName=\"\" MobilePhone=\"\" OtherPhone=\"\" PersonID=\"\" PersonInfoKey=\"20230210204505170035540\" State=\"CO\" Suffix=\"\" Title=\"\" ZipCode=\"80302\"/>\r\n" + 
			"			<AdditionalAddresses NumberOfAdditionalAddresses=\"0\"/>\r\n" + 
			"			<References/>\r\n" + 
			"		</Order>\r\n" + 
			"		<Shipment CarrierServiceCode=\"Standard_Ground\" SCAC=\"FEDX\" ShipDate=\"2022-11-22T04:08:32+00:00\" ShipNode=\"891\" ShipmentKey=\"20230214064808171621677\">\r\n" + 
			"			<Extn ExtnActualShippingLocation=\"\" ExtnPartnerOrder=\"\" ExtnTaxRetryCount=\"0\"/>\r\n" + 
			"			<ToAddress AddressLine1=\"2205 Pine St\" AddressLine2=\"\" AddressLine3=\"\" AddressLine4=\"\" AddressLine5=\"\" AddressLine6=\"\" AlternateEmailID=\"\" Beeper=\"\" City=\"Boulder\" Company=\"\" Country=\"US\" DayFaxNo=\"\" DayPhone=\"8049712190\" Department=\"\" EMailID=\"denise.kelly@worldmarket.com\" EveningFaxNo=\"\" EveningPhone=\"\" FirstName=\"Tax\" HttpUrl=\"\" JobTitle=\"\" LastName=\"Tester\" MiddleName=\"\" MobilePhone=\"\" OtherPhone=\"\" PersonID=\"\" State=\"CO\" Suffix=\"\" Title=\"\" ZipCode=\"80302\"/>\r\n" + 
			"			<ShipNode NodeType=\"DC\" ShipnodeKey=\"891\">\r\n" + 
			"				<ShipNodePersonInfo AddressLine1=\"1735 Zephyr St\" AddressLine2=\"\" AddressLine3=\"\" AddressLine4=\"\" AddressLine5=\"\" AddressLine6=\"\" AlternateEmailID=\"\" Beeper=\"\" City=\"Stockton\" Company=\"\" Country=\"US\" DayFaxNo=\"\" DayPhone=\"\" Department=\"\" EMailID=\"\" EveningFaxNo=\"\" EveningPhone=\"\" FirstName=\"\" HttpUrl=\"\" JobTitle=\"\" LastName=\"\" MiddleName=\"\" MobilePhone=\"\" OtherPhone=\"\" PersonID=\"\" State=\"\" Suffix=\"\" Title=\"\" ZipCode=\"95206\"/>\r\n" + 
			"			</ShipNode>\r\n" + 
			"			<Containers>\r\n" + 
			"				<Container ActualWeight=\"0.00\" ActualWeightUOM=\"\" CarrierServiceCode=\"Standard_Ground\" ContainerGrossWeight=\"0.00\" ContainerGrossWeightUOM=\"LBS\" ContainerGroup=\"SHIPMENT\" ContainerHeight=\"0.00\" ContainerHeightUOM=\"IN\" ContainerLength=\"0.00\" ContainerLengthUOM=\"IN\" ContainerNetWeight=\"0.00\" ContainerNetWeightUOM=\"LBS\" ContainerNo=\"100009356\" ContainerScm=\"\" ContainerType=\"\" ContainerWidth=\"0.00\" ContainerWidthUOM=\"IN\" ExternalReference1=\"https://worldmarket.narvar.com/worldmarket/tracking/fedex?tracking_numbers=81004977965_1&amp;service=FG&amp;dzip=80302&amp;order_number=81004977965\" SCAC=\"FEDX\" ServiceType=\"\" ShipDate=\"2023-02-14\" ShipMode=\"PARCEL\" ShipmentContainerKey=\"20230214064821171621678\" ShipmentKey=\"20230214064808171621677\" TrackingNo=\"81004977965_1\">\r\n" + 
			"					<ContainerDetails>\r\n" + 
			"						<ContainerDetail ContainerDetailsKey=\"20230214064821171621679\" ItemID=\"340072\" OrderHeaderKey=\"20230213202020171408406\" OrderLineKey=\"20230213202027171408495\" OrderReleaseKey=\"20230213214506171437755\" ProductClass=\"\" Quantity=\"12.00\" QuantityPlaced=\"0.00\" ShipmentContainerKey=\"20230214064821171621678\" ShipmentKey=\"20230214064808171621677\" ShipmentLineKey=\"20230214064808171621676\" UnitOfMeasure=\"EACH\">\r\n" + 
			"							<ShipmentLine BackroomPickedQuantity=\"\" ItemDesc=\"Heinz Baked Beans\" ItemID=\"340072\" OrderHeaderKey=\"20230213202020171408406\" OrderLineKey=\"20230213202027171408495\" OrderNo=\"81004977965\" OrderReleaseKey=\"20230213214506171437755\" OriginalQuantity=\"12.00\" PrimeLineNo=\"1\" ProductClass=\"\" Quantity=\"12.00\" ReleaseNo=\"1\" ShipmentKey=\"20230214064808171621677\" ShipmentLineKey=\"20230214064808171621676\" ShipmentLineNo=\"1\" ShortageQty=\"0.00\" SubLineNo=\"101\" UnitOfMeasure=\"EACH\"/>\r\n" + 
			"						</ContainerDetail>\r\n" + 
			"					</ContainerDetails>\r\n" + 
			"				</Container>\r\n" + 
			"			</Containers>\r\n" + 
			"		</Shipment>\r\n" + 
			"		<LineDetails TotalLines=\"1\">\r\n" + 
			"			<LineDetail Charges=\"8.95\" ExtendedPrice=\"41.88\" LineTotal=\"52.80\" OrderInvoiceDetailKey=\"202302141180495171738362\" OrderLineKey=\"20230213202027171408495\" ShippedQty=\"12.00\" Tax=\"1.97\" UnitPrice=\"3.49\">\r\n" + 
			"				<OrderLine DeliveryMethod=\"SHP\" LineType=\"REGULAR\" OrderLineKey=\"20230213202027171408495\" PrimeLineNo=\"1\" ReturnReason=\"\" SubLineNo=\"101\">\r\n" + 
			"					<Item ItemID=\"340072\" ProductClass=\"NEW\" UnitOfMeasure=\"EACH\">\r\n" + 
			"						<LanguageDescriptionList/>\r\n" + 
			"					</Item>\r\n" + 
			"					<LinePriceInfo DiscountPercentage=\"0.00\" DiscountReference=\"\" DiscountType=\"\" ListPrice=\"3.49\" RetailPrice=\"0.00\" UnitPrice=\"3.49\"/>\r\n" + 
			"					<Extn ExtnIsReship=\"\" ExtnQtyConvFactor=\"12\" ExtnTOReferenceNo=\"\">\r\n" + 
			"						<OrderLineGCList/>\r\n" + 
			"					</Extn>\r\n" + 
			"					<PersonInfoShipTo AddressLine1=\"2205 Pine St\" AddressLine2=\"\" AddressLine3=\"\" AddressLine4=\"\" AddressLine5=\"\" AddressLine6=\"\" AlternateEmailID=\"\" Beeper=\"\" City=\"Boulder\" Company=\"\" Country=\"US\" DayFaxNo=\"\" DayPhone=\"8049712190\" Department=\"\" EMailID=\"denise.kelly@worldmarket.com\" EveningFaxNo=\"\" EveningPhone=\"\" FirstName=\"Tax\" HttpUrl=\"\" JobTitle=\"\" LastName=\"Tester\" MiddleName=\"\" MobilePhone=\"\" OtherPhone=\"\" PersonID=\"\" State=\"CO\" Suffix=\"\" Title=\"\" ZipCode=\"80302\"/>\r\n" + 
			"					<References/>\r\n" + 
			"					<ItemDetails CanUseAsServiceTool=\"N\" Createprogid=\"SterlingHttpTester\" Createts=\"2022-02-10T00:07:23+00:00\" Createuserid=\"admin\" DisplayItemId=\"340072\" GlobalItemID=\"\" InheritAttributesFromClassification=\"Y\" IsShippingCntr=\"N\" ItemGroupCode=\"PROD\" ItemID=\"340072\" ItemKey=\"2022021000072317273005\" Lockid=\"52\" MaxModifyTS=\"2023-02-08T17:24:38+00:00\" Modifyprogid=\"WMItemFeedIntServer\" Modifyts=\"2023-02-08T17:24:38+00:00\" Modifyuserid=\"WMItemFeedIntServer\" OrganizationCode=\"WM\" UnitOfMeasure=\"EACH\">\r\n" + 
			"						<PrimaryInformation ItemType=\"OWNED\"/>\r\n" + 
			"					</ItemDetails>\r\n" + 
			"					<OrderStatuses>\r\n" + 
			"						<OrderStatus ShipNode=\"891\"/>\r\n" + 
			"					</OrderStatuses>\r\n" + 
			"				</OrderLine>\r\n" + 
			"				<LineCharges>\r\n" + 
			"					<LineCharge AmountFromAddnlLinePrices=\"0.00\" ChargeAmount=\"8.95\" ChargeCategory=\"SHIPPING\" ChargeName=\"Shipping\" ChargeNameKey=\"202205310639163521557\" ChargePerLine=\"8.95\" ChargePerUnit=\"0.00\" IsBillable=\"Y\" IsDiscount=\"N\" IsManual=\"Y\" IsShippingCharge=\"N\" OriginalChargePerLine=\"0.00\" OriginalChargePerUnit=\"0.00\" Reference=\"\">\r\n" + 
			"						<Extn ExtnChargeInfo=\"\"/>\r\n" + 
			"					</LineCharge>\r\n" + 
			"				</LineCharges>\r\n" + 
			"				<LineTaxes>\r\n" + 
			"					<LineTax ChargeCategory=\"SHIPPING\" ChargeName=\"\" ChargeNameKey=\"\" Reference_1=\"\" Reference_2=\"\" Reference_3=\"\" Tax=\"0.00\" TaxName=\"08_CO STATE TAX\" TaxPercentage=\"2.90\"/>\r\n" + 
			"					<LineTax ChargeCategory=\"SHIPPING\" ChargeName=\"\" ChargeNameKey=\"\" Reference_1=\"\" Reference_2=\"\" Reference_3=\"\" Tax=\"0.00\" TaxName=\"013_CO COUNTY TAX\" TaxPercentage=\"1.185\"/>\r\n" + 
			"					<LineTax ChargeCategory=\"SHIPPING\" ChargeName=\"\" ChargeNameKey=\"\" Reference_1=\"\" Reference_2=\"\" Reference_3=\"\" Tax=\"0.35\" TaxName=\"07850_CO CITY TAX\" TaxPercentage=\"3.86\"/>\r\n" + 
			"					<LineTax ChargeCategory=\"SHIPPING\" ChargeName=\"\" ChargeNameKey=\"\" Reference_1=\"\" Reference_2=\"\" Reference_3=\"\" Tax=\"0.00\" TaxName=\"1300008001_CO SPECIAL TAX\" TaxPercentage=\"0.10\"/>\r\n" + 
			"					<LineTax ChargeCategory=\"SHIPPING\" ChargeName=\"\" ChargeNameKey=\"\" Reference_1=\"\" Reference_2=\"\" Reference_3=\"\" Tax=\"0.00\" TaxName=\"AKFG_CO SPECIAL TAX\" TaxPercentage=\"1.00\"/>\r\n" + 
			"					<LineTax ChargeCategory=\"Price\" ChargeName=\"\" ChargeNameKey=\"\" Reference_1=\"\" Reference_2=\"\" Reference_3=\"\" Tax=\"1.62\" TaxName=\"07850_CO CITY TAX\" TaxPercentage=\"3.86\"/>\r\n" + 
			"					<TaxSummary>\r\n" + 
			"						<TaxSummaryDetail Tax=\"0.00\" TaxName=\"08_CO STATE TAX\"/>\r\n" + 
			"						<TaxSummaryDetail Tax=\"0.00\" TaxName=\"013_CO COUNTY TAX\"/>\r\n" + 
			"						<TaxSummaryDetail Tax=\"1.97\" TaxName=\"07850_CO CITY TAX\"/>\r\n" + 
			"						<TaxSummaryDetail Tax=\"0.00\" TaxName=\"1300008001_CO SPECIAL TAX\"/>\r\n" + 
			"						<TaxSummaryDetail Tax=\"0.00\" TaxName=\"AKFG_CO SPECIAL TAX\"/>\r\n" + 
			"					</TaxSummary>\r\n" + 
			"				</LineTaxes>\r\n" + 
			"			</LineDetail>\r\n" + 
			"		</LineDetails>\r\n" + 
			"		<TotalSummary>\r\n" + 
			"			<ChargeSummary>\r\n" + 
			"				<ChargeSummaryDetail ChargeAmount=\"0.27\" ChargeCategory=\"CO_DELVY_FEE\" ChargeName=\"ColoradoDeliveryFee\" ChargeNameKey=\"20230116075514149858464\" IsBillable=\"Y\" IsDiscount=\"N\" IsShippingCharge=\"N\" OriginalChargeAmount=\"0.00\" Reference=\"\">\r\n" + 
			"					<Extn/>\r\n" + 
			"				</ChargeSummaryDetail>\r\n" + 
			"				<ChargeSummaryDetail ChargeAmount=\"8.95\" ChargeCategory=\"SHIPPING\" ChargeName=\"Shipping\" ChargeNameKey=\"202205310639163521557\" IsBillable=\"Y\" IsDiscount=\"N\" IsShippingCharge=\"N\" OriginalChargeAmount=\"0.00\" Reference=\"\">\r\n" + 
			"					<Extn/>\r\n" + 
			"				</ChargeSummaryDetail>\r\n" + 
			"			</ChargeSummary>\r\n" + 
			"			<TaxSummary>\r\n" + 
			"				<TaxSummaryDetail Tax=\"0.00\" TaxName=\"08_CO STATE TAX\"/>\r\n" + 
			"				<TaxSummaryDetail Tax=\"0.00\" TaxName=\"013_CO COUNTY TAX\"/>\r\n" + 
			"				<TaxSummaryDetail Tax=\"1.97\" TaxName=\"07850_CO CITY TAX\"/>\r\n" + 
			"				<TaxSummaryDetail Tax=\"0.00\" TaxName=\"1300008001_CO SPECIAL TAX\"/>\r\n" + 
			"				<TaxSummaryDetail Tax=\"0.00\" TaxName=\"AKFG_CO SPECIAL TAX\"/>\r\n" + 
			"			</TaxSummary>\r\n" + 
			"		</TotalSummary>\r\n" + 
			"		<HeaderCharges>\r\n" + 
			"			<HeaderCharge ChargeAmount=\"0.27\" ChargeCategory=\"CO_DELVY_FEE\" ChargeName=\"ColoradoDeliveryFee\" ChargeNameKey=\"20230116075514149858464\" IsBillable=\"Y\" IsDiscount=\"N\" OriginalChargeAmount=\"0.00\" Reference=\"\">\r\n" + 
			"				<Extn ExtnChargeInfo=\"\"/>\r\n" + 
			"			</HeaderCharge>\r\n" + 
			"		</HeaderCharges>\r\n" + 
			"		<HeaderTaxes/>\r\n" + 
			"		<CollectionDetails TotalLines=\"1\">\r\n" + 
			"			<CollectionDetail AmountCollected=\"53.07\" AuditTransactionID=\"\" AuthorizationExpirationDate=\"2023-02-20T12:20:13+00:00\" AuthorizationID=\"OK7793\" BookAmount=\"0.00\" ChargeTransactionKey=\"20230214130443171775203\" ChargeType=\"CHARGE\" CollectionDate=\"2023-02-14T13:04:43+00:00\" CreditAmount=\"652.75\" DebitAmount=\"0.00\" DistributedAmount=\"-652.75\" HoldAgainstBook=\"N\" OpenAuthorizedAmount=\"-652.75\" OrderHeaderKey=\"20230213202020171408406\" OrderInvoiceKey=\"\" PaymentKey=\"202302132074202171408499\" RequestAmount=\"652.75\" SettledAmount=\"0.00\" Status=\"CHECKED\" TransactionDate=\"2023-02-14T14:32:11+00:00\" UserExitStatus=\"\">\r\n" + 
			"				<CreditCardTransactions>\r\n" + 
			"					<CreditCardTransaction AuthAmount=\"652.75\" AuthAvs=\"\" AuthCode=\"OK7793\" AuthReturnCode=\"APPROVED\" AuthReturnFlag=\"T\" AuthReturnMessage=\"\" AuthTime=\"2023-02-14T14:31:26+00:00\" ChargeTransactionKey=\"20230214130443171775203\" CreditCardTransactionKey=\"202302141447312171823819\" InternalReturnCode=\"\" InternalReturnFlag=\"\" InternalReturnMessage=\"\" ParentKey=\"\" Reference1=\"\" Reference2=\"\" RequestId=\"\" TranAmount=\"652.75\" TranRequestTime=\"2023-02-14T14:31:26+00:00\" TranReturnCode=\"\" TranReturnFlag=\"T\" TranReturnMessage=\"\" TranType=\"\"/>\r\n" + 
			"				</CreditCardTransactions>\r\n" + 
			"				<PaymentMethod AwaitingAuthInterfaceAmount=\"0.00\" AwaitingChargeInterfaceAmount=\"0.00\" ChargeSequence=\"0\" CheckNo=\"\" CheckReference=\"\" CreditCardExpDate=\"02-2024\" CreditCardName=\"\" CreditCardNo=\"0083\" CreditCardType=\"VI\" CustomerPONo=\"\" DisplayCreditCardNo=\"0083\" DisplaySvcNo=\"\" IncompletePaymentType=\"\" MaxChargeLimit=\"0.00\" PaymentKey=\"202302132074202171408499\" PaymentReference1=\"84619628662\" PaymentReference2=\"1462177637270083\" PaymentReference3=\"\" PaymentReference8=\"81004977965_1\" PaymentType=\"CC\" RequestedAuthAmount=\"0.00\" RequestedChargeAmount=\"0.00\" SuspendAnyMoreCharges=\"N\" TotalAuthorized=\"0.00\" TotalCharged=\"653.03\" TotalRefundedAmount=\"0.00\" UnlimitedCharges=\"Y\"/>\r\n" + 
			"			</CollectionDetail>\r\n" + 
			"		</CollectionDetails>\r\n" + 
			"	</InvoiceHeader>\r\n" + 
			"</InvoiceDetail>\r\n" + 
			"";

	@Test	
	public void IsValidInvoicePositiveCondition() throws TransformerException, ParserConfigurationException, SAXException, IOException {
		IsValidInvoice isValidInvoice = new IsValidInvoice();
		org.junit.Assert.assertTrue("", isValidInvoice.evaluateCondition(env, "", 
				new HashMap<String,String>(), AcuverXMLUtil.getDocument(invoiceIn)));
	}

	@Test	
	public void IsValidInvoiceNegitiveCondition1() throws TransformerException, ParserConfigurationException, SAXException, IOException {
		IsValidInvoice isValidInvoice = new IsValidInvoice();
		org.junit.Assert.assertFalse("", isValidInvoice.evaluateCondition(env, "", 
				new HashMap<String,String>(), AcuverXMLUtil.getDocument("")));
	}

	@Test	
	public void IsValidInvoiceNegitiveCondition2() throws TransformerException {
		IsValidInvoice isValidInvoice = new IsValidInvoice();
		org.junit.Assert.assertFalse("", isValidInvoice.evaluateCondition(env, "", 
				new HashMap<String,String>(), null));
	}




}
