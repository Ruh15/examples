package com.rh.example.ws.client.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;
import org.w3c.dom.Document;

public class UuvServiceClient {
//	private static String namespace="http://uuv.webservice.api.xmrbi.com/";
//	private static String wsdlUrl="http://117.29.161.2:3358/services/UUVService/";
	//private static String wsdlUrl="http://localhost:3368/services/businessToDoService/";

	private static String namespace="http://data.ws.datahub/";
	private static String wsdlUrl="http://ip:port/datahubWeb/TMSSOAP/SY?wsdl";


	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public void setWsdlUrl(String wsdlUrl) {
		this.wsdlUrl = wsdlUrl;
	}

	public static void main(String[] args) {
		try {
			String json=null;
//				json=invoke("getUser", new String[]{"key","beginDate","endDate"},new String[]{"1","2018-03-02 00:00:00","2018-12-01 00:00:01"});
//			json=invoke("getUserByKeyNo", new String[]{"key","keyNo"},new String[]{"1","895ce248-b6c8-11e6-bef5-00ffaabbccdd"});
//						json=invoke("getUserAll", new String[]{"key","keyNo"},new String[]{"",""});
			// json=invoke("getUseunit", new String[]{"key","beginDate","endDate"},new String[]{"EXT_SYS_SUBWAY","2018-01-01 00:00:01","2018-11-01 00:00:01"});
//		    json=invoke("getUseunitByKeyNo", new String[]{"key","keyNo"},new String[]{"1","4ba9967f-7608-4df3-8554-3a917e95c8d7"});
//			json=invoke("getINVSkuData", new String[]{"key","keyNo"},new String[]{"",""});
			//json=invoke("validateUP", new String[]{"key","userName","userPswd"},new String[]{"EXT_SYS_SUBWAY","yuyq","a"});
			//json=invoke("updateBussinessStatus", new String[]{"todoid","systemId"},new String[]{"1","yuyq"});


			json=invoke("getINVSkuData", new String[]{"password","username", "from_time", "to_time"},
					new String[]{"402", "flux","2016-11-01 00:00:00","2016-11-02 00:00:00"});
			System.out.println("getUseunitAll: "+json);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SOAPException e) {
			e.printStackTrace();
		}
	}
	public static String invoke(String method,
			String[] parameterNames,String[] parameterValue) throws SOAPException, MalformedURLException {

		String obj = null;
        try {
        	String endpoint = wsdlUrl;
    	    String operationName = method;
        	String targetNamespace = namespace;
        	org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            Call call = (Call) service.createCall();
            call.setTimeout(new Integer(200000));
            call.setTargetEndpointAddress(endpoint);
            QName qn = new QName(targetNamespace, operationName);
          // 设置方法参数
            call.setOperationName(qn);
        	if (parameterNames != null && parameterNames.length > 0) {
    			for(int i=0;i<parameterNames.length;i++)
    			call.addParameter(parameterNames[i],XMLType.XSD_STRING,ParameterMode.IN);
    		}
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING); //要返回的数据类型（自定义类型）

            /*******************************************************/
            // 解决错误：服务器未能识别 HTTP 头 SOAPAction 的值
            call.setUseSOAPAction(true);
            call.setSOAPActionURI(targetNamespace + operationName);
            // 调用ws方法
            obj = (String) call.invoke(parameterValue);
        }catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
            e.printStackTrace();
        }
        return obj;
	}

}

