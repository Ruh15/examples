package com.example.alipay.demo.test.spdb;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author hui
 * @description 合同生成签名 xml 数据
 * @date 2021/2/9
 */
@Data
@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
public class SignContractXmlData {

    @XmlElement(name = "SealProcessSteps")
    private Integer sealProcessSteps = 32;

    @XmlElement(name = "SealMess_BranchCer")
    private SealMessBranchCer sealMessBranchCer;

    /**
     * 需要在 pdf 中添加的文件名称列表  APIM20002_1.jpg;APIM20002_2.jpg
     */
    @XmlElement(name = "Attachment")
    private String attachment;

    @XmlElement(name = "SealMess_SceneCer")
    private SealMessSceneCer sealMessSceneCer;

    @XmlElement(name = "ChnMess")
    private ChnMess chnMess;

    @XmlElement(name = "ContentInfo")
    private ContractInfoBO contentInfo;
}
