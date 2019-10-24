package com.rh.example.ws.client;

import com.rh.example.ws.client.stub.MobileAddressImpl;
import com.rh.example.ws.client.stub.MobileAddressImplService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ruhui on 2019/8/25.
 * content:
 */
public class JaxwsClient2 {
    public static void main(String[] args) throws MalformedURLException {
        // 下面的参数都可以在 生成的 标注 @WebServiceClient 的类中看到
        Service service = Service.create(
                new URL("http://127.0.0.1:9999/mobile?wsdl"),
                new QName("http://impl.server.ws.example.rh.com/", "MobileAddressImplService")
        );

        MobileAddressImpl ma = service.getPort(MobileAddressImpl.class);

        System.out.println(ma.getAddressByMobile("13822225555"));
    }
}
