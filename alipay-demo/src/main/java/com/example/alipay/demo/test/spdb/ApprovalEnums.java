package com.example.alipay.demo.test.spdb;

import lombok.Getter;

/**
 * @author hui
 * @description 浦发审批结果枚举
 * @date 2021/2/19
 */
@Getter
public enum ApprovalEnums {
    /**
     *  A01 审批中 A02 审批失败 A03 审批通过  A04 审批作废 B01 额度生效  B02 额度失效 B03 额度暂停
     *         C01 已签订 C02 贷款作废 C03 已放款 C04 已结清 C05 逾期
     *         000 查询全部
     */
    A01("A01", "审批中"),
    A02("A02", "审批失败"),
    A03("A03", "审批通过"),
    A04("A04", "审批作废"),
    B01("B01", "审批作废"),
    B02("B02", "额度失效"),
    B03("B03", "额度暂停"),
    C01("C01", "已签订"),
    C02("C02", "贷款作废"),
    C03("C03", "已放款"),
    C04("C04", "已结清"),
    C05("C05", "逾期"),
    ;

    private final String code;
    private final String desc;
    ApprovalEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
