
package com.rh.example.ws.client.oms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>wmsINVHeader complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="wmsINVHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="message_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bl_order_no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="customer_order_no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bl_load_no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cltrafficname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="temp_class" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="departname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destinationname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="eta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destcustomer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destaddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contact" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="memo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="is_pod_return" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="qty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="weight" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cubic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="itemsize" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="udf01" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="udf02" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="udf03" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="udf04" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="udf05" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="udf06" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="udf07" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="udf08" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="udf09" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="udf10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "wmsINVHeader", propOrder = {
    "messageId",
    "blOrderNo",
    "customerOrderNo",
    "blLoadNo",
    "cltrafficname",
    "tempClass",
    "departname",
    "destinationname",
    "eta",
    "destcustomer",
    "destaddress",
    "tel",
    "contact",
    "memo",
    "isPodReturn",
    "qty",
    "weight",
    "cubic",
    "itemsize",
    "udf01",
    "udf02",
    "udf03",
    "udf04",
    "udf05",
    "udf06",
    "udf07",
    "udf08",
    "udf09",
    "udf10"
})
public class WmsINVHeader {

    @XmlElement(name = "message_id")
    protected String messageId;
    @XmlElement(name = "bl_order_no")
    protected String blOrderNo;
    @XmlElement(name = "customer_order_no")
    protected String customerOrderNo;
    @XmlElement(name = "bl_load_no")
    protected String blLoadNo;
    protected String cltrafficname;
    @XmlElement(name = "temp_class")
    protected String tempClass;
    protected String departname;
    protected String destinationname;
    protected String eta;
    protected String destcustomer;
    protected String destaddress;
    protected String tel;
    protected String contact;
    protected String memo;
    @XmlElement(name = "is_pod_return")
    protected String isPodReturn;
    protected String qty;
    protected String weight;
    protected String cubic;
    protected String itemsize;
    protected String udf01;
    protected String udf02;
    protected String udf03;
    protected String udf04;
    protected String udf05;
    protected String udf06;
    protected String udf07;
    protected String udf08;
    protected String udf09;
    protected String udf10;

    /**
     * 获取messageId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * 设置messageId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageId(String value) {
        this.messageId = value;
    }

    /**
     * 获取blOrderNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlOrderNo() {
        return blOrderNo;
    }

    /**
     * 设置blOrderNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlOrderNo(String value) {
        this.blOrderNo = value;
    }

    /**
     * 获取customerOrderNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerOrderNo() {
        return customerOrderNo;
    }

    /**
     * 设置customerOrderNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerOrderNo(String value) {
        this.customerOrderNo = value;
    }

    /**
     * 获取blLoadNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlLoadNo() {
        return blLoadNo;
    }

    /**
     * 设置blLoadNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlLoadNo(String value) {
        this.blLoadNo = value;
    }

    /**
     * 获取cltrafficname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCltrafficname() {
        return cltrafficname;
    }

    /**
     * 设置cltrafficname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCltrafficname(String value) {
        this.cltrafficname = value;
    }

    /**
     * 获取tempClass属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTempClass() {
        return tempClass;
    }

    /**
     * 设置tempClass属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTempClass(String value) {
        this.tempClass = value;
    }

    /**
     * 获取departname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartname() {
        return departname;
    }

    /**
     * 设置departname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartname(String value) {
        this.departname = value;
    }

    /**
     * 获取destinationname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationname() {
        return destinationname;
    }

    /**
     * 设置destinationname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationname(String value) {
        this.destinationname = value;
    }

    /**
     * 获取eta属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEta() {
        return eta;
    }

    /**
     * 设置eta属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEta(String value) {
        this.eta = value;
    }

    /**
     * 获取destcustomer属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestcustomer() {
        return destcustomer;
    }

    /**
     * 设置destcustomer属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestcustomer(String value) {
        this.destcustomer = value;
    }

    /**
     * 获取destaddress属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestaddress() {
        return destaddress;
    }

    /**
     * 设置destaddress属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestaddress(String value) {
        this.destaddress = value;
    }

    /**
     * 获取tel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置tel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTel(String value) {
        this.tel = value;
    }

    /**
     * 获取contact属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContact() {
        return contact;
    }

    /**
     * 设置contact属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContact(String value) {
        this.contact = value;
    }

    /**
     * 获取memo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 设置memo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMemo(String value) {
        this.memo = value;
    }

    /**
     * 获取isPodReturn属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsPodReturn() {
        return isPodReturn;
    }

    /**
     * 设置isPodReturn属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsPodReturn(String value) {
        this.isPodReturn = value;
    }

    /**
     * 获取qty属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQty() {
        return qty;
    }

    /**
     * 设置qty属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQty(String value) {
        this.qty = value;
    }

    /**
     * 获取weight属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeight() {
        return weight;
    }

    /**
     * 设置weight属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeight(String value) {
        this.weight = value;
    }

    /**
     * 获取cubic属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCubic() {
        return cubic;
    }

    /**
     * 设置cubic属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCubic(String value) {
        this.cubic = value;
    }

    /**
     * 获取itemsize属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemsize() {
        return itemsize;
    }

    /**
     * 设置itemsize属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemsize(String value) {
        this.itemsize = value;
    }

    /**
     * 获取udf01属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUdf01() {
        return udf01;
    }

    /**
     * 设置udf01属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUdf01(String value) {
        this.udf01 = value;
    }

    /**
     * 获取udf02属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUdf02() {
        return udf02;
    }

    /**
     * 设置udf02属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUdf02(String value) {
        this.udf02 = value;
    }

    /**
     * 获取udf03属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUdf03() {
        return udf03;
    }

    /**
     * 设置udf03属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUdf03(String value) {
        this.udf03 = value;
    }

    /**
     * 获取udf04属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUdf04() {
        return udf04;
    }

    /**
     * 设置udf04属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUdf04(String value) {
        this.udf04 = value;
    }

    /**
     * 获取udf05属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUdf05() {
        return udf05;
    }

    /**
     * 设置udf05属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUdf05(String value) {
        this.udf05 = value;
    }

    /**
     * 获取udf06属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUdf06() {
        return udf06;
    }

    /**
     * 设置udf06属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUdf06(String value) {
        this.udf06 = value;
    }

    /**
     * 获取udf07属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUdf07() {
        return udf07;
    }

    /**
     * 设置udf07属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUdf07(String value) {
        this.udf07 = value;
    }

    /**
     * 获取udf08属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUdf08() {
        return udf08;
    }

    /**
     * 设置udf08属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUdf08(String value) {
        this.udf08 = value;
    }

    /**
     * 获取udf09属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUdf09() {
        return udf09;
    }

    /**
     * 设置udf09属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUdf09(String value) {
        this.udf09 = value;
    }

    /**
     * 获取udf10属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUdf10() {
        return udf10;
    }

    /**
     * 设置udf10属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUdf10(String value) {
        this.udf10 = value;
    }

}
