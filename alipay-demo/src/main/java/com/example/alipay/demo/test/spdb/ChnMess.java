package com.example.alipay.demo.test.spdb;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * @author hui
 * @description 存储影像平台节点
 * @date 2021/2/9
 */
@Data
@XmlRootElement(name = "ChnMess")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChnMess {
    /**
     * 唯一编号 可以是回单号或者流水号
     */
    @XmlElement(name = "ReceiptNo")
    private String receiptNo;

}
