
package com.rh.example.ws.client.oms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getINVSkuData complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="getINVSkuData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="wmsSecurityInfo" type="{http://data.ws.datahub/}wmsSecurityInfo" minOccurs="0"/>
 *         &lt;element name="wmsParam" type="{http://data.ws.datahub/}wmsParamInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getINVSkuData", propOrder = {
    "wmsSecurityInfo",
    "wmsParam"
})
public class GetINVSkuData {

    protected WmsSecurityInfo wmsSecurityInfo;
    protected WmsParamInfo wmsParam;

    /**
     * 获取wmsSecurityInfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link WmsSecurityInfo }
     *     
     */
    public WmsSecurityInfo getWmsSecurityInfo() {
        return wmsSecurityInfo;
    }

    /**
     * 设置wmsSecurityInfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link WmsSecurityInfo }
     *     
     */
    public void setWmsSecurityInfo(WmsSecurityInfo value) {
        this.wmsSecurityInfo = value;
    }

    /**
     * 获取wmsParam属性的值。
     * 
     * @return
     *     possible object is
     *     {@link WmsParamInfo }
     *     
     */
    public WmsParamInfo getWmsParam() {
        return wmsParam;
    }

    /**
     * 设置wmsParam属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link WmsParamInfo }
     *     
     */
    public void setWmsParam(WmsParamInfo value) {
        this.wmsParam = value;
    }

}
