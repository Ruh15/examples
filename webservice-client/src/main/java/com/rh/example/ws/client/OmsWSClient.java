package com.rh.example.ws.client;

import com.alibaba.fastjson.JSON;
import com.rh.example.ws.client.oms.*;
import com.rh.example.ws.client.stub.MobileAddressImpl;
import com.rh.example.ws.client.util.XMLUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.*;

/**
 * Created by ruhui on 2019/8/25.
 * content:
 */
public class OmsWSClient {
    public static void main(String[] args) {

    }

    public static void test2() {
        // 1. 实例化服务类
        CERPWSService2Service mas = new CERPWSService2Service();

        // 2. 通过服务类得到本地代理类
        IERPWSService2 service2Port = mas.getCERPWSService2Port();

    }

    public static void test1() {
        // 1. 实例化服务类
        CERPWSService2Service mas = new CERPWSService2Service();

        // 2. 通过服务类得到本地代理类
        IERPWSService2 service2Port = mas.getCERPWSService2Port();

        //3. 调用本地代理对象的方法，得到数据
        String username = "flux";
        String password = "402";
        String warehouseid = "WH01";    //仓库ID , 固定值 " WH01"
        String customerid = "SY";    //客户ID ， 固定值 "SY"
        String messageid = "SY_ORDQ";        //接口标识, 固定值 " SY_ORDQ"
        String from_time = "2019-11-25 11:30:00";        //开始时间
        String to_time = "2019-11-26 11:30:00";        //结束时间
        String stdno = "TMSQ";            //接口类别, 固定值 "TMSQ "

        WmsSecurityInfo wmsSecurityInfo = new WmsSecurityInfo();
        wmsSecurityInfo.setUsername(username);
        wmsSecurityInfo.setPassword(password);
        WmsParamInfo paramInfo = new WmsParamInfo();
        paramInfo.setCustomerid(customerid);
        paramInfo.setWarehouseid(warehouseid);
        paramInfo.setMessageid(messageid);
        paramInfo.setStdno(stdno);
        paramInfo.setFromTime(from_time);
        paramInfo.setToTime(to_time);
        paramInfo.getParam().add("1111111"); //承运商代码
        System.out.println(JSON.toJSONString(wmsSecurityInfo));
        System.out.println(JSON.toJSONString(paramInfo));

        String xml = XMLUtil.convertToXml(wmsSecurityInfo);
        System.out.println(xml);
        System.out.println(XMLUtil.convertToXml(paramInfo));
        WmsINVInfo invSkuData = service2Port.getINVSkuData(wmsSecurityInfo, paramInfo);
        System.out.println(JSON.toJSONString(invSkuData));

//        String res = "<{http://data.ws.datahub/}WmsINVInfo><resultInfo>0</resultInfo><returnCode>0000</returnCode><returnDesc/><returnFlag>1</returnFlag></{http://data.ws.datahub/}WmsINVInfo>";
//        WmsINVInfo wmsINVInfo = (WmsINVInfo) XMLUtil.convertXmlStrToObject(WmsINVInfo.class, res);
//        System.out.println(JSON.toJSONString(wmsINVInfo));
//
//
//        Service service = null;
//        try {
//            service = Service.create(
//                    new URL("http://ip:port/datahubWeb/TMSSOAP/SY?wsdl"),
//                    new QName("http://data.ws.datahub/", "CERPWSService2Service")
//            );
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }

//        IERPWSService2 ma = service.getPort(IERPWSService2.class);
//
//        System.out.println(ma.getINVSkuData(wmsSecurityInfo, paramInfo));

//        try {
//            testWS();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void testWS() throws IOException {
        String wsdl = "http://ip:port/datahubWeb/TMSSOAP/SY?wsdl";
        int timeout = 10000;
        StringBuffer sb = new StringBuffer("");
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<soap:Envelope "
                + "xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' "
                + "xmlns:xsd='http://www.w3.org/2001/XMLSchema' "
                + "xmlns:data='http://data.ws.datahub/'"
                + "xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>");
        sb.append("<soapenv:Header/>");
        sb.append("<soapenv:Body>");
        sb.append("<data:getINVSkuData>");
        sb.append("<wmsSecurityInfo>");
        sb.append("<password>password</password>");
        sb.append("<username>username</username>");
        sb.append("</wmsSecurityInfo>");
        sb.append("<wmsParam>");
        sb.append("<customerid>SY</customerid>");
        sb.append("<messageid>SY_ORDQ</messageid>");
        sb.append("<from_time>2016-11-01 00:00:00</from_time>");
        sb.append("<to_time>2016-11-02 00:00:00</to_time>");
        sb.append("<param>1111111</param>");
        sb.append("<stdno>TMSQ</stdno>");
        sb.append("<warehouseid>WH01</warehouseid>");
        sb.append("</wmsParam>");
        sb.append("</data:getINVSkuData>");
        sb.append("</soapenv:Body>");
        sb.append("</soapenv:Envelope>");

        // HttpClient发送SOAP请求
        System.out.println("HttpClient 发送 SOAP 请求");
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(wsdl);
        // 设置连接超时
        client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
        // 设置读取时间超时
        client.getHttpConnectionManager().getParams().setSoTimeout(timeout);
        // 然后把Soap请求数据添加到PostMethod中
        RequestEntity requestEntity = new StringRequestEntity(sb.toString(), "text/xml", "UTF-8");
        //设置请求头部，否则可能会报 “no SOAPAction header” 的错误
        postMethod.setRequestHeader("SOAPAction", "");
        // 设置请求体
        postMethod.setRequestEntity(requestEntity);
        int status = client.executeMethod(postMethod);
        // 打印请求状态码
        System.out.println("status:" + status);
        // 获取响应体输入流
        InputStream is = postMethod.getResponseBodyAsStream();
        // 获取请求结果字符串
        String result = IOUtils.toString(is);
        System.out.println("result: " + result);

        // HttpURLConnection 发送SOAP请求
        System.out.println("HttpURLConnection 发送 SOAP 请求");
        URL url = new URL(wsdl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        conn.setRequestMethod("POST");
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);

        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
        dos.write(sb.toString().getBytes("utf-8"));
        dos.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        String line = null;
        StringBuffer strBuf = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            strBuf.append(line);
        }
        dos.close();
        reader.close();

        System.out.println(strBuf.toString());
    }
}
