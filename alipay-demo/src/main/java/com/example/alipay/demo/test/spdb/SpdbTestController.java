package com.example.alipay.demo.test.spdb;

import com.alibaba.fastjson.JSONObject;
import com.example.alipay.demo.util.AmountUtil;
import com.example.alipay.demo.util.DateUtil;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * @author hui
 * @description
 * @date 2021/2/9
 */
public class SpdbTestController {


    public static void main(String[] args) throws Exception {
        SpdbService spdbService = new SpdbService();
//        spdbService.repayPlanQuery("82012020a1119901", "20200401", "20250401");
//        business();
//        // 合同文本查询
        spdbService.contractInfoQuery("82012021a0190301", "522224197910160036");
    }

    private static void business() throws Exception {
        SpdbService spdbService = new SpdbService();
        // 1. 申请
        int month = 6;
        Calendar calendar = Calendar.getInstance();
        calendar.set(2035, Calendar.DECEMBER, 7);
//        apply(spdbService, month, calendar.getTime());

//        spdbService.intRateQuery(DateUtil.formatDateToStr(calendar.getTime(), "yyyyMMdd"));

        String aplSeqId = "DBSQ203500002610";
        // 2. 查询详情
        spdbService.queryDetail(SpdbService.IDNUM, aplSeqId);
        // 3. 生成合同
        String debtOnBondNum = spdbService.generateContract(aplSeqId);
        // 4. 生成签名
//        String debtOnBondNum = "82012035a0024201";
        initContract(spdbService, aplSeqId, debtOnBondNum, calendar.getTime());
        // 5. 电子合同查询交易
        spdbService.initContractRes("20210219000009325669");

        // 6. 合同签名 申请
//        signContract(spdbService, aplSeqId, debtOnBondNum, calendar.getTime());

        // 7. 查询合同签名申请结果 20210220000009329474
        // {"transNo":"04972102200171505060980876","isScss":"000000000000","scssFlgDsc":"交易成功","retRsltVlm":"20210220000009329467|0497|DBSQ203500002610|0110|0000"}
//        spdbService.querySignContractRes("20210220000009329467");
    }

    /**
     * 电子合同生成交易
     * @param spdbService
     * @param aplSeqId
     * @param debtOnBondNum
     * @param date
     */
    private static void initContract(SpdbService spdbService, String aplSeqId, String debtOnBondNum, Date date) {
        InitContractXmlData initContractXmlData = new InitContractXmlData();
        ChnMess chnMess = assembleChnMess(aplSeqId);
        ContractInfoBO contractInfoBO = assembleContractInfo(spdbService, debtOnBondNum, date);

        initContractXmlData.setChnMess(chnMess);
        initContractXmlData.setContentInfo(contractInfoBO);
        spdbService.initContract(initContractXmlData);
    }

    /**
     * 电子合同签名生成交易
     * @param spdbService
     * @param aplSeqId
     * @param debtOnBondNum
     * @param date
     */
    private static void signContract(SpdbService spdbService, String aplSeqId, String debtOnBondNum, Date date) {
        SignContractXmlData signContractXmlData = new SignContractXmlData();

        String fileName = "content.jpg;content1.jpg;content2.jpg;";
        signContractXmlData.setAttachment(fileName);
        SealMessSceneCer sealMessSceneCer = new SealMessSceneCer();
        sealMessSceneCer.setUserName(SpdbService.CUSTNAME);
        sealMessSceneCer.setEvidence(fileName);
        sealMessSceneCer.setIdentNo(SpdbService.IDNUM);
        sealMessSceneCer.setStringElement("BorrowNumber: " + debtOnBondNum + "；");

        signContractXmlData.setSealMessSceneCer(sealMessSceneCer);


        ChnMess chnMess = assembleChnMess(aplSeqId);
        ContractInfoBO contractInfoBO = assembleContractInfo(spdbService, debtOnBondNum, date);

        signContractXmlData.setChnMess(chnMess);
        signContractXmlData.setContentInfo(contractInfoBO);
        spdbService.signContractApply(signContractXmlData);
    }

    /**
     * 组装 存储影像平台节点 参数
     * @param aplSeqId
     * @return
     */
    private static ChnMess assembleChnMess(String aplSeqId) {
        ChnMess chnMess = new ChnMess();
        chnMess.setReceiptNo(aplSeqId);
        return chnMess;
    }

    /**
     * 组装合同信息参数
     * @param spdbService
     * @param debtOnBondNum
     * @param date
     * @return
     */
    private static ContractInfoBO assembleContractInfo(SpdbService spdbService, String debtOnBondNum, Date date) {
        ContractInfoBO contractInfoBO = new ContractInfoBO();
        contractInfoBO.setEvidenceOfDebtNo(debtOnBondNum);
        contractInfoBO.setName_Q(SpdbService.CUSTNAME);
        contractInfoBO.setName_H(SpdbService.CUSTNAME);
        contractInfoBO.setAddress_Q(SpdbService.ADDRESS);
        contractInfoBO.setAddress_H(SpdbService.ADDRESS);
        contractInfoBO.setPhone_H(SpdbService.MOBILE);
        contractInfoBO.setPhone_Q(SpdbService.MOBILE);
        contractInfoBO.setCerType(SpdbService.CERT_TYPE);
        contractInfoBO.setCardNumber(SpdbService.IDNUM);
        contractInfoBO.setLoanMoney(SpdbService.LOANAMT);
        contractInfoBO.setAmountInWords(AmountUtil.toUppercase(SpdbService.LOANAMT));

        Calendar calendar = Calendar.getInstance();
        // 设置浦发签署合同日期为最新订单更新日期
        calendar.setTime(date);

        contractInfoBO.setYear(calendar.get(Calendar.YEAR));
        contractInfoBO.setMonth(calendar.get(Calendar.MONTH) + 1);
        contractInfoBO.setDay(calendar.get(Calendar.DATE));

        // 计算结束日期
        calendar.add(Calendar.MONTH, SpdbService.LOAN_TERM_NUM);

        contractInfoBO.setYear2(calendar.get(Calendar.YEAR));
        contractInfoBO.setMonth2(calendar.get(Calendar.MONTH) + 1);
        contractInfoBO.setDay2(calendar.get(Calendar.DATE));

        contractInfoBO.setDay3("20");

        contractInfoBO.setYearLimit(calendar.get(Calendar.YEAR));
        contractInfoBO.setMouthLimit(calendar.get(Calendar.MONTH) + 1);

        JSONObject content = spdbService.intRateQuery(DateUtil.formatDateToStr(date, "yyyyMMdd"));

        //  计算上浮点数  6.5 - 按照LPR查询的结果   1个bp是万分之一
        BigDecimal rate = new BigDecimal("6.5");
        BigDecimal subRes = rate.subtract(content.getBigDecimal("intRate")).setScale(4, BigDecimal.ROUND_HALF_UP);
        BigDecimal floatingPointNumber = subRes.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
        contractInfoBO.setFloatValue(floatingPointNumber.toString());
        contractInfoBO.setExecRate(rate.toString());
        contractInfoBO.setPayAmount(SpdbService.LOANAMT);
        contractInfoBO.setRemitType(SpdbService.REPAY_MODE_NAME);
        contractInfoBO.setPayerBankName("浦发银行");
        contractInfoBO.setPayerNo(SpdbService.REPYCARDID);
        contractInfoBO.setPayerName(SpdbService.CUSTNAME);
        contractInfoBO.setSealContent("1");
        contractInfoBO.setSceneCerSealContent("1");
        return contractInfoBO;
    }

    /**
     * 下单申请操作
     */
    public static void apply(SpdbService spdbService, int month, Date date) throws Exception {
        // 1.校验准入
        spdbService.access();

        // 2.下单推数
        String orderNo = spdbService.pushOrder(date);

        // CO202012091138137867617
        // 3. 贷款申请
        String aplSeqId = spdbService.apply(orderNo);
        System.err.println("浦发流水编号:" + aplSeqId);// DBSQ202000000395
//        征信授权书编号：12A2012091138139572
//        贷款用途声明书编号：12B2012091138135945

        queryOrder(spdbService, month, date);

        // 审批作废
//        cancelOrder(IDNUM, aplSeqId);
        // 申请明细查询
//        spdbService.queryDetail(SpdbService.IDNUM, aplSeqId);
    }

    /**
     * 查询订单是否存在
     * @param spdbService
     */
    private static void queryOrder(SpdbService spdbService, int month, Date date) {
        String dateStr = DateUtil.formatDateToStr(new Date(), "yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -month);
        Date startDate = calendar.getTime();
        // 4. 查询结果
        spdbService.queryApplyList(DateUtil.formatDateToStr(startDate, "yyyyMMdd"), dateStr, "000", SpdbService.IDNUM);
    }

}
