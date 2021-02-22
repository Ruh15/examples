package com.example.alipay.demo.test.spdb;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author hui
 * @description 场景证书节点
 * @date 2021/2/20
 */
@Data
@XmlRootElement(name = "SealMess_SceneCer")
@XmlAccessorType(XmlAccessType.FIELD)
public class SealMessSceneCer {

    /**
     * 用户姓名
     */
    @XmlElement(name = "UserName")
    private String userName;

    /**
     * 证件类型
     */
    @XmlElement(name = "IdentType")
    private Integer identType = 1;

    /**
     * 证件号码
     */
    @XmlElement(name = "IdentNo")
    private String identNo;

    /**
     * 证据文件列表 证据文件名称列表，文件名之间用“;”分割  -- 同 {@link SignContractXmlData.attachment}
     */
    @XmlElement(name = "Evidence")
    private String evidence;

    /**
     * 场景证书字符串证据  固定写法
     * BorrowNumber: 借据号；
     * 例：“BorrowNumber: 00001；” ，冒号和分号都得带上。
     */
    @XmlElement(name = "StringElement")
    private String stringElement;

}
