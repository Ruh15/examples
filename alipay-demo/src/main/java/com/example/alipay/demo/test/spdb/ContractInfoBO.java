package com.example.alipay.demo.test.spdb;

import com.example.alipay.demo.util.JaxbXmlUtil;
import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * @author hui
 * @description 合同信息参数
 * @date 2021/2/8
 */
@Data
//xml顶级元素标签
@XmlRootElement(name = "ContentInfo")
//类中的每个非静态、非瞬态字段将会自动绑定到 XML，除非由 XmlTransient 注释
@XmlAccessorType(XmlAccessType.FIELD)
public class ContractInfoBO {

    /**
     * 借款合同编号
     */
    @XmlElement(name = "EvidenceOfDebtNo")
    private String evidenceOfDebtNo;

    @XmlElement(name = "Name_Q")
    private String name_Q ;
    @XmlElement(name = "Address_Q")
    private String address_Q;
    @XmlElement(name = "Phone_Q")
    private String phone_Q;
    @XmlElement(name = "Name_H")
    private String name_H;
    @XmlElement(name = "CerType")
    private String cerType;
    @XmlElement(name = "CardNumber")
    private String cardNumber;
    @XmlElement(name = "Address_H")
    private String address_H;
    @XmlElement(name = "Phone_H")
    private String phone_H;
    @XmlElement(name = "LoanMoney")
    private String loanMoney;
    @XmlElement(name = "AmountInWords")
    private String amountInWords;
    @XmlElement(name = "YearLimit")
    private Integer yearLimit;
    @XmlElement(name = "MouthLimit")
    private Integer mouthLimit;
    @XmlElement(name = "Year")
    private Integer year;
    @XmlElement(name = "Month")
    private Integer month;
    @XmlElement(name = "Day")
    private Integer day;

    @XmlElement(name = "Year2")
    private Integer year2;
    @XmlElement(name = "Month2")
    private Integer month2;
    @XmlElement(name = "Day2")
    private Integer day2;

    @XmlElement(name = "CheckBox1")
    private String checkBox1 = "√";
    @XmlElement(name = "CheckBox2")
    private String checkBox2 = "ⅹ";
    // 非必填
    @XmlElement(name = "FloatValue")
    private String floatValue;
    // 非必填
    @XmlElement(name = "ExecRate")
    private String execRate;
    // 非必填
    @XmlElement(name = "PayAmount")
    private String payAmount;

    @XmlElement(name = "RemitType")
    private String remitType;
    @XmlElement(name = "Day3")
    private String day3;
    @XmlElement(name = "PayerBankName")
    private String payerBankName;
    @XmlElement(name = "PayerNo")
    private String payerNo;
    @XmlElement(name = "PayerName")
    private String payerName;

    @XmlElement(name = "SealContent")
    private String sealContent = "1";
    @XmlElement(name = "SceneCerSealContent")
    private String sceneCerSealContent = "1";

}
