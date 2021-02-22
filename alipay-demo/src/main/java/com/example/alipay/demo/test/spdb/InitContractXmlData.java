package com.example.alipay.demo.test.spdb;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * @author hui
 * @description 初始化待签名合同 xml 数据
 * @date 2021/2/9
 */
@Data
@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
public class InitContractXmlData {

    @XmlElement(name = "ChnMess")
    private ChnMess chnMess;

    @XmlElement(name = "ContentInfo")
    private ContractInfoBO contentInfo;
}
