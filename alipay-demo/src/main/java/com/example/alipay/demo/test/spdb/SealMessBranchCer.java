package com.example.alipay.demo.test.spdb;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author hui
 * @description 加签流程顺序
 * @date 2021/2/20
 */
@Data
@XmlRootElement(name = "SealMess_BranchCer")
@XmlAccessorType(XmlAccessType.FIELD)
public class SealMessBranchCer {
    /**
     * 分行号
     */
    @XmlElement(name = "BranchCerFileName")
    private String branchCerFileName = "8200";
}
