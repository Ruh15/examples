
package com.rh.example.ws.client.oms;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.rh.example.ws.client.oms package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetINVSkuData_QNAME = new QName("http://data.ws.datahub/", "getINVSkuData");
    private final static QName _WmsSecurityInfo_QNAME = new QName("http://data.ws.datahub/", "wmsSecurityInfo");
    private final static QName _GetINVSkuDataResponse_QNAME = new QName("http://data.ws.datahub/", "getINVSkuDataResponse");
    private final static QName _WmsResultInfo_QNAME = new QName("http://data.ws.datahub/", "wmsResultInfo");
    private final static QName _WmsINVHeader_QNAME = new QName("http://data.ws.datahub/", "wmsINVHeader");
    private final static QName _WmsINVInfo_QNAME = new QName("http://data.ws.datahub/", "wmsINVInfo");
    private final static QName _WmsParamInfo_QNAME = new QName("http://data.ws.datahub/", "wmsParamInfo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.rh.example.ws.client.oms
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WmsINVHeader }
     * 
     */
    public WmsINVHeader createWmsINVHeader() {
        return new WmsINVHeader();
    }

    /**
     * Create an instance of {@link GetINVSkuDataResponse }
     * 
     */
    public GetINVSkuDataResponse createGetINVSkuDataResponse() {
        return new GetINVSkuDataResponse();
    }

    /**
     * Create an instance of {@link WmsResultInfo }
     * 
     */
    public WmsResultInfo createWmsResultInfo() {
        return new WmsResultInfo();
    }

    /**
     * Create an instance of {@link GetINVSkuData }
     * 
     */
    public GetINVSkuData createGetINVSkuData() {
        return new GetINVSkuData();
    }

    /**
     * Create an instance of {@link WmsSecurityInfo }
     * 
     */
    public WmsSecurityInfo createWmsSecurityInfo() {
        return new WmsSecurityInfo();
    }

    /**
     * Create an instance of {@link WmsParamInfo }
     * 
     */
    public WmsParamInfo createWmsParamInfo() {
        return new WmsParamInfo();
    }

    /**
     * Create an instance of {@link WmsINVInfo }
     * 
     */
    public WmsINVInfo createWmsINVInfo() {
        return new WmsINVInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetINVSkuData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://data.ws.datahub/", name = "getINVSkuData")
    public JAXBElement<GetINVSkuData> createGetINVSkuData(GetINVSkuData value) {
        return new JAXBElement<GetINVSkuData>(_GetINVSkuData_QNAME, GetINVSkuData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WmsSecurityInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://data.ws.datahub/", name = "wmsSecurityInfo")
    public JAXBElement<WmsSecurityInfo> createWmsSecurityInfo(WmsSecurityInfo value) {
        return new JAXBElement<WmsSecurityInfo>(_WmsSecurityInfo_QNAME, WmsSecurityInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetINVSkuDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://data.ws.datahub/", name = "getINVSkuDataResponse")
    public JAXBElement<GetINVSkuDataResponse> createGetINVSkuDataResponse(GetINVSkuDataResponse value) {
        return new JAXBElement<GetINVSkuDataResponse>(_GetINVSkuDataResponse_QNAME, GetINVSkuDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WmsResultInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://data.ws.datahub/", name = "wmsResultInfo")
    public JAXBElement<WmsResultInfo> createWmsResultInfo(WmsResultInfo value) {
        return new JAXBElement<WmsResultInfo>(_WmsResultInfo_QNAME, WmsResultInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WmsINVHeader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://data.ws.datahub/", name = "wmsINVHeader")
    public JAXBElement<WmsINVHeader> createWmsINVHeader(WmsINVHeader value) {
        return new JAXBElement<WmsINVHeader>(_WmsINVHeader_QNAME, WmsINVHeader.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WmsINVInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://data.ws.datahub/", name = "wmsINVInfo")
    public JAXBElement<WmsINVInfo> createWmsINVInfo(WmsINVInfo value) {
        return new JAXBElement<WmsINVInfo>(_WmsINVInfo_QNAME, WmsINVInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WmsParamInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://data.ws.datahub/", name = "wmsParamInfo")
    public JAXBElement<WmsParamInfo> createWmsParamInfo(WmsParamInfo value) {
        return new JAXBElement<WmsParamInfo>(_WmsParamInfo_QNAME, WmsParamInfo.class, null, value);
    }

}
