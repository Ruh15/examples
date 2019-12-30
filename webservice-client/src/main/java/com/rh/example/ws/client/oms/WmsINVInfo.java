
package com.rh.example.ws.client.oms;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;


/**
 * <p>wmsINVInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="wmsINVInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tmsViewHeaders" type="{http://data.ws.datahub/}wmsINVHeader" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="wmsResultInfo" type="{http://data.ws.datahub/}wmsResultInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wmsINVInfo", propOrder = {
    "tmsViewHeaders",
    "wmsResultInfo"
})
@XmlRootElement(name = "WmsINVInfo")
public class WmsINVInfo {

    @XmlElement(nillable = true)
    protected List<WmsINVHeader> tmsViewHeaders;
    protected WmsResultInfo wmsResultInfo;

    /**
     * Gets the value of the tmsViewHeaders property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tmsViewHeaders property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTmsViewHeaders().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WmsINVHeader }
     * 
     * 
     */
    public List<WmsINVHeader> getTmsViewHeaders() {
        if (tmsViewHeaders == null) {
            tmsViewHeaders = new ArrayList<WmsINVHeader>();
        }
        return this.tmsViewHeaders;
    }

    /**
     * 获取wmsResultInfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link WmsResultInfo }
     *     
     */
    public WmsResultInfo getWmsResultInfo() {
        return wmsResultInfo;
    }

    /**
     * 设置wmsResultInfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link WmsResultInfo }
     *     
     */
    public void setWmsResultInfo(WmsResultInfo value) {
        this.wmsResultInfo = value;
    }

}
