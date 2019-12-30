package com.rh.example.ws.client.util;

import com.rh.example.ws.client.oms.WmsParamInfo;
import com.rh.example.ws.client.oms.WmsSecurityInfo;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;

import javax.xml.namespace.QName;
import java.util.Arrays;

/**
 * content:
 *
 * @author Ruh
 * @time 2019/12/24
 **/
public class WSCallUtil {

    public static String INVOICE_WS_URL = "http://ip:port/datahubWeb/TMSSOAP/SY?wsdl";
    private static String namespace="http://data.ws.datahub/";

    public static void main(String[] args) {
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

        System.out.println(call(INVOICE_WS_URL, namespace, "getINVSkuData", new Object[]{wmsSecurityInfo, paramInfo}));

    }


    public static String call(String url, String nameSpace, String methodName,Object[] param){
        return webserviceAxis2(url, nameSpace, methodName, param);
    }

    /**
     * 应用rpc的方式调用 这种方式就等于远程调用，
     * 即通过url定位告诉远程服务器，告知方法名称，参数等， 调用远程服务，得到结果。
     * 使用 org.apache.axis2.rpc.client.RPCServiceClient类调用WebService
     *
     【注】：
     如果被调用的WebService方法有返回值 应使用 invokeBlocking 方法 该方法有三个参数
     第一个参数的类型是QName对象，表示要调用的方法名；
     第二个参数表示要调用的WebService方法的参数值，参数类型为Object[]；
     当方法没有参数时，invokeBlocking方法的第二个参数值不能是null，而要使用new Object[]{}。
     第三个参数表示WebService方法的 返回值类型的Class对象，参数类型为Class[]。
     如果被调用的WebService方法没有返回值 应使用 invokeRobust 方法
     该方法只有两个参数，它们的含义与invokeBlocking方法的前两个参数的含义相同。
     在创建QName对象时，QName类的构造方法的第一个参数表示WSDL文件的命名空间名，
     也就是 <wsdl:definitions>元素的targetNamespace属性值。
     *
     */
    public static String webserviceAxis2(String url, String nameSpace, String methodName,Object[] param) {
        String result = "";
        // 使用RPC方式调用WebService
        RPCServiceClient serviceClient = null;
        try {
            // axis2 服务端地址
            String htWsUrl = url;
            serviceClient = new RPCServiceClient();
            // 指定调用WebService的URL
            EndpointReference targetEPR = new EndpointReference(htWsUrl);
            Options options = serviceClient.getOptions();
            //确定目标服务地址
            options.setTo(targetEPR);
            //确定调用方法
            options.setAction("urn:"+methodName);
            options.setProperty(HTTPConstants.CHUNKED, "false");// 把chunk关掉后，会自动加上Content-Length
            //解决高并发链接超时问题
            options.setManageSession(true);
            options.setProperty(HTTPConstants.REUSE_HTTP_CLIENT,true);
            //设置响应超时，默认5s
            options.setProperty(HTTPConstants.SO_TIMEOUT, 5000);
            //设置连接超时，默认5s
            options.setProperty(HTTPConstants.CONNECTION_TIMEOUT, 5000);
            //options.setTimeOutInMilliSeconds(TIMEOUT_MILLISECONDS_AXIS2);
            /**
             * 指定要调用的getPrice方法及WSDL文件的命名空间
             * 如果 webservice 服务端由axis2编写
             * 命名空间 不一致导致的问题
             * org.apache.axis2.AxisFault: java.lang.RuntimeException: Unexpected subelement arg0
             */
            QName qname = new QName(nameSpace, methodName);
            // 指定传递参数值
            Object[] parameters = param;
            // 指定方法返回值的数据类型的Class对象
            Class[] returnTypes = new Class[] { String.class };
            // 调用方法并输出该方法的返回值
            Object[] response = serviceClient.invokeBlocking(qname, parameters, returnTypes);
            result = Arrays.toString(response);
        } catch (org.apache.axis2.AxisFault e) {
            if(e.getCause()!=null&&e.getCause().toString().toLowerCase().contains("timeout")){
                System.out.println(e.getDetail());
            }
        } finally {
            try {
                if(serviceClient != null){
                    serviceClient.cleanupTransport();
                }
            } catch (org.apache.axis2.AxisFault e) {
                if(e.getCause()!=null&&e.getCause().toString().contains("SocketTimeoutException")){
                    System.out.println(e.getDetail());
                }
            }
        }
        return result;
    }
}
