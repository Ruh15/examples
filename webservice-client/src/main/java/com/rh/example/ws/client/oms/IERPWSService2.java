
package com.rh.example.ws.client.oms;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 *
 */
@WebService(name = "IERPWSService2", targetNamespace = "http://data.ws.datahub/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IERPWSService2 {


    /**
     *
     * @param wmsParam
     * @param wmsSecurityInfo
     * @return
     *     returns com.rh.example.ws.client.oms.WmsINVInfo
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getINVSkuData", targetNamespace = "http://data.ws.datahub/", className = "com.rh.example.ws.client.oms.GetINVSkuData")
    @ResponseWrapper(localName = "getINVSkuDataResponse", targetNamespace = "http://data.ws.datahub/", className = "com.rh.example.ws.client.oms.GetINVSkuDataResponse")
    public WmsINVInfo getINVSkuData(
            @WebParam(name = "wmsSecurityInfo", targetNamespace = "")
                    WmsSecurityInfo wmsSecurityInfo,
            @WebParam(name = "wmsParam", targetNamespace = "")
                    WmsParamInfo wmsParam);

}
