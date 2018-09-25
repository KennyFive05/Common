package Utility;

import org.dom4j.DocumentException;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class XMLUtilityTest {

    private final String xml = "<RqXMLData>	<Header>		<ClientDtTm>20171225092916</ClientDtTm>		<ClientID>10.112.185.12</ClientID>		<FrnIP>10.1.5.146</FrnIP>		<FrnName>NP</FrnName>		<FrnMsgID>077721670908</FrnMsgID>		<SvcType>NPFrnCDInqRq</SvcType>		<SvcCode>NONE</SvcCode>		<Encoding>Big5</Encoding>		<Language>zh_TW</Language>	</Header>	<AuthData>		<CustPermId>P222222229</CustPermId>		<CustLoginId>test11</CustLoginId>		<CustLoginType>4</CustLoginType>		<CustPswd>****</CustPswd>		<SignonRole>Agent</SignonRole>		<AuthenticationRule></AuthenticationRule>	</AuthData>	<ATMAuthData>		<BankNo/>		<CardNo/>		<TrCode/>		<FrnCode/>		<FrnChkCode/>		<TrDate/>		<CardMemo/>		<RCPTIdTSAC/>		<Auth/>	</ATMAuthData>	<OTPAuthData>		<OTPId/>		<OTPType/>		<OTPPswd/>	</OTPAuthData>	<Text/></RqXMLData>";

    //    @Test
    public void testFormat() throws DocumentException {
        System.out.println(XMLUtility.format(xml));
    }

    @Test
    public void getValue() throws DocumentException {
        List<String> list = XMLUtility.getValue(xml, "RqXMLData", "ClientDtTm");
        assertEquals("20171225092916", list.get(0));
    }

    @Test
    public void seachTag() throws DocumentException {
        List<String> list = XMLUtility.searchTag(xml, "RqXMLData", "Header", "ClientDtTm");
        assertEquals("<ClientDtTm>20171225092916</ClientDtTm>", list.get(0));
    }
}
