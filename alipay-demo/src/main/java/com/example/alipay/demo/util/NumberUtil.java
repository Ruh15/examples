package com.example.alipay.demo.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 流水号生成工具类
 */
public class NumberUtil {

    private static final String QUERY_LOANPLAN_NUM_PREFIX = "LP";

    private static final String PAYMENTAPPLY_NUM_PREFIX = "PA";

    private static final String CREATE_ORDER_NUM_PREFIX = "CO";

    private static final String REQUEST_FADADA_NUM_PREFIX = "RF";

    private static final String CAPITAL_ROUTING_NUM_PREFIX = "CP";

    private static final String DRAWDOWN_APPLY_NUM_PREFIX = "DA";

    private static  final String CREATE_SEQ_NUM_PREFIX="CS";

    private static  final String PREPAYMENT_APPLY="AP";

    private static final String DATEFORMAT_PATTERN = "yyyyMMddHHmmssSSS" ;

    private static final String REQUEST_ID_DATEFORMAT_PATTERN = "yyyyMMdd" ;

    /* 浦发银行对接：授权书编号时间格式化 */
    private static final String SPDB_AHR_NUM_DATEFORMAT_PATTERN = "yyMMddHHmmss";

    private static final String SPDB_AHR_NUM_PREFIX_B = "12B";
    private static final String SPDB_AHR_NUM_PREFIX_A = "12A";

    /**
     * 1. 生成查询贷款⽅案的流水号
     * 获取当前时间,格式: yyyyMMddHHmmssSSS
     */
    public static synchronized String generateNumForQueryLoanPlan(){
        return QUERY_LOANPLAN_NUM_PREFIX + DateFormatUtils.format(new Date(), DATEFORMAT_PATTERN) + RandomNumberUtil.newRandomNumber();
    }

    /**
     * 2. 生成: 代收付申请流水号
     * 获取当前时间,格式: yyyyMMddHHmmssSSS
     */
    public static synchronized String generateNumForPaymentApply(){
        return PAYMENTAPPLY_NUM_PREFIX + DateFormatUtils.format(new Date(), DATEFORMAT_PATTERN)+ RandomNumberUtil.newRandomNumber();
    }

    /**
     * 3. 生成: 订单编号流水号
     * 获取当前时间,格式: yyyyMMddHHmmssSSS
     */
    public static synchronized String generateNumForCreateOrder(){
        return CREATE_ORDER_NUM_PREFIX + DateFormatUtils.format(new Date(), DATEFORMAT_PATTERN)+ RandomNumberUtil.newRandomNumber();
    }

    /**
     * 4. 生成: 请求电子签章流水号
     * 获取当前时间,格式: yyyyMMddHHmmssSSS
     */
    public static synchronized String generateNumForRequestFadada(){
        return REQUEST_FADADA_NUM_PREFIX + DateFormatUtils.format(new Date(), DATEFORMAT_PATTERN)+ RandomNumberUtil.newRandomNumber();
    }

    /**
     * 5. 生成: 获取资方路由流水号
     * 获取当前时间,格式: yyyyMMddHHmmssSSS
     */
    public static synchronized String generateNumForCapitalRouting(){
        return CAPITAL_ROUTING_NUM_PREFIX + DateFormatUtils.format(new Date(), DATEFORMAT_PATTERN)+ RandomNumberUtil.newRandomNumber();
    }

    /**
     * 6. 生成: 获取放款审核申请的流水号
     * 获取当前时间,格式: yyyyMMddHHmmssSSS
     */
    public static synchronized String generateNumForDrawdownApply(){
        return DRAWDOWN_APPLY_NUM_PREFIX + DateFormatUtils.format(new Date(), DATEFORMAT_PATTERN)+ RandomNumberUtil.newRandomNumber();
    }

    /**
     * 7. 生成: 获取放款结果通知的请求号
     * 获取当前时间,格式: iproleId+YYYYMMDD+35位流水号，倒数2，3位必须是数字
     */
    public static synchronized String generateRequestIdForDrawdownConfirm(String ipRoleId){
        StringBuilder stringBuilder = new StringBuilder(ipRoleId);
        stringBuilder.append(DateFormatUtils.format(new Date(), REQUEST_ID_DATEFORMAT_PATTERN))
                .append(UUIDUtil.getUUID32())
                .append(RandomNumberUtil.getRandomNumber2())
                .append((char)(Math.random()*26+97));
        return stringBuilder.toString();
    }

    /**
     * 8. 生成: 征信查询授权书编号 生成规则：2位渠道编号+A+YYMMDDHHMMSS+4位随机
     * 获取当前时间,格式: yyyyMMddHHmmssSSS
     */
    public static synchronized String generateNumForSPDBAHRA(){
        return SPDB_AHR_NUM_PREFIX_A + DateFormatUtils.format(new Date(), SPDB_AHR_NUM_DATEFORMAT_PATTERN)+ RandomNumberUtil.newRandomNumber();
    }

    /**
     * 10. 生成: 生成规则：2位渠道编号+B+YYMMDDHHMMSS+4位随机
     * 获取当前时间,格式: yyyyMMddHHmmssSSS
     */
    public static synchronized String generateNumForSPDBAHRB(){
        return SPDB_AHR_NUM_PREFIX_B + DateFormatUtils.format(new Date(), SPDB_AHR_NUM_DATEFORMAT_PATTERN)+ RandomNumberUtil.newRandomNumber();
    }
    /**
     * 9.生成唯一提前结清外部请求流水号
     *
     * @return
     */
    public static  synchronized String generateNumForPrepayment(){
        return PREPAYMENT_APPLY +DateFormatUtils.format(new Date(), DATEFORMAT_PATTERN)+ RandomNumberUtil.newRandomNumber();
    }

}
