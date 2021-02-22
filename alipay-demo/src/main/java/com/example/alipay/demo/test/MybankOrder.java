package com.example.alipay.demo.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipayLogger;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.example.alipay.demo.util.ChangeToPinYinJPUtil;
import com.example.alipay.demo.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.Map;

/**
 * @author hui
 * @desc
 * @date 2020/6/5
 */
public class MybankOrder {


    private static final String charset = "GBK";
    private static final String format = "json";
    private static final String signPartnerId = "2012020052500000100000078007";
    private static final String signProductCode = "PD_MYBANK_CMD";

    private static final String orgCode = "KEKING-PSBCSH";
//    private static final String orgCode = "KEKING-PSBCSHFDB";
    private static final String salesProductCode = "01023200002000003818";
    private static final String productCode = "01021000002000056237";

    private static final String solutionCode = "20201225000126851890049";
    private static final String first_solutionCode = "20200629000813311890041";
    private static final String second_solutionCode = "20201225000126851890049";
    private static final String second_solutionCode_new = "20210121000191071890042";

    private static DefaultAlipayClient client = new DefaultAlipayClient(serverUrl, appId, privateKey,
            format, charset, publicKey, signType);

    public static void main(String[] args) throws AlipayApiException {
//        testHttpCall();
//        billQuery("20201217830440418183S", "2088722281224660");
        String str = "￼ LZGJLG846KX113501";
    }

    public static void test() {
        //        String orderNo = "CO202101201333522995070";
//        signOrderQuery(orderNo, second_solutionCode);

//        queryLpr("0.162");

//        String userId = "41080219761226351X";
//        String orderNo = "CO202101181437246816090";
//        String site = "PERSON"; // ALIPAY
//        queryOrderStatus(userId, orderNo, site);

//        System.out.println(ChangeToPinYinJPUtil.changeToTonePinYin("拉"));
//        String s = ChangeToPinYinJPUtil.changeToTonePinYin("额登乌拉");

//        String orderNo = "CO202101051416171699370";
//        String alipayOrderNo = "20201224680072939268S";
//        String loanAmt = "12834900";
//        String custName = "毕青莺";
//        String loanPeriod = "30";
//        String firstpayamt = "2265000";
//
//        String monthpayamt = "497332";
//        String lastpayamt = "0";
//        String interestrate = "0.120006";
//        querySignUrl(orderNo, custName, alipayOrderNo, loanAmt, loanPeriod, firstpayamt, monthpayamt, lastpayamt, interestrate);

//        signErrorQueryByOrderNo(orderNo);
    }
    public static void testHttpCall() {
        JSONObject param = new JSONObject();
        param.put("nature", "AFFILIATED_COMPANY");
        param.put("balance", "50");
        param.put("m1", "20");
        param.put("m2", "15");
        String res = HttpClientUtil.doPost("https://uat-k8s.keking.cn/risk-engine/api/risk/leasing/getDealerAndAffiliatedCompanyIndex?page=3&size=10", param.toJSONString());
        System.out.println(res);
    }

    public static void testThreadMXBean() {
        // 获取 Java 线程管理 MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的 monitor 和 synchronizer 信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        // 遍历线程信息，仅打印线程 ID 和线程名称信息
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
        }
    }

    /**
     * 签约单查询
     * @param orderNo
     * @param solutionCode
     * @throws AlipayApiException
     */
    public static AlipayFinancialnetAuthEcsignSignorderQueryResponse signOrderQuery(String orderNo, String solutionCode) throws AlipayApiException {
        AlipayFinancialnetAuthEcsignSignorderQueryModel model = new AlipayFinancialnetAuthEcsignSignorderQueryModel();
        model.setOutOrderNo(orderNo);
        model.setPartnerId(signPartnerId);
        model.setSignProductId(signProductCode);
        model.setSolutionCode(solutionCode);
        AlipayFinancialnetAuthEcsignSignorderQueryRequest request = new AlipayFinancialnetAuthEcsignSignorderQueryRequest();
        request.setBizModel(model);
        AlipayFinancialnetAuthEcsignSignorderQueryResponse res = client.execute(request);
        System.out.println(JSON.toJSONString(res, SerializerFeature.PrettyFormat));
        return res;
    }

    /**
     * LPR 查询
     * @param loanRate
     * @throws AlipayApiException
     */
    public static MybankCreditSceneprodLprQueryResponse queryLpr(String loanRate) throws AlipayApiException {
        MybankCreditSceneprodLprQueryModel model = new MybankCreditSceneprodLprQueryModel();
        model.setLoanRate(loanRate);
        MybankCreditSceneprodLprQueryRequest request = new MybankCreditSceneprodLprQueryRequest();
        request.setBizModel(model);
        MybankCreditSceneprodLprQueryResponse res = client.execute(request);
        System.out.println(JSON.toJSONString(res, SerializerFeature.PrettyFormat));
        return res;
    }

    /**
     * 推送网商数据采集
     * @throws AlipayApiException
     */
    public static void dataUpload(String alipayNo) throws AlipayApiException {
        MybankCreditSceneprodDataUploadModel model = new MybankCreditSceneprodDataUploadModel();
        model.setAppSeqno(alipayNo);
        model.setDataContent("");
        model.setOrgCode(orgCode);
        model.setProductCode(productCode);

        MybankCreditSceneprodDataUploadRequest request = new MybankCreditSceneprodDataUploadRequest();
        request.setBizModel(model);
        System.out.println("数据采集参数:" + JSON.toJSONString(model, SerializerFeature.PrettyFormat));
        MybankCreditSceneprodDataUploadResponse response = client.execute(request);
        System.out.println(JSON.toJSONString(response, SerializerFeature.PrettyFormat));
    }

    /**
     * 查询签约失败原因
     * @param orderNo
     * @throws AlipayApiException
     */
    public static AlipayFinancialnetAuthEcsignErrorQueryResponse signErrorQueryByOrderNo(String orderNo) throws AlipayApiException {
        AlipayFinancialnetAuthEcsignErrorQueryModel model = new AlipayFinancialnetAuthEcsignErrorQueryModel();
        model.setOutOrderNo(orderNo);
        model.setPartnerId(signPartnerId);
        model.setSignProductId(signProductCode);

        AlipayFinancialnetAuthEcsignErrorQueryRequest request = new AlipayFinancialnetAuthEcsignErrorQueryRequest();
        request.setBizModel(model);
        AlipayFinancialnetAuthEcsignErrorQueryResponse response = client.execute(request);
        System.out.println(JSON.toJSONString(response, SerializerFeature.PrettyFormat));
        return response;
    }

    /**
     * 查询签约结果
     * @param orderNo
     * @throws AlipayApiException
     */
    public static void querySignRes(String orderNo) throws AlipayApiException {
        AlipayFinancialnetAuthEcsignSignorderQueryModel model = new AlipayFinancialnetAuthEcsignSignorderQueryModel();
        model.setOutOrderNo(orderNo);
        model.setPartnerId(signPartnerId);
        model.setSignProductId(signProductCode);

        AlipayFinancialnetAuthEcsignSignorderQueryRequest request = new AlipayFinancialnetAuthEcsignSignorderQueryRequest();
        request.setBizModel(model);
        System.out.println(JSON.toJSONString(model, SerializerFeature.PrettyFormat));
        AlipayFinancialnetAuthEcsignSignorderQueryResponse response = client.execute(request);
        System.out.println(JSON.toJSONString(response, SerializerFeature.PrettyFormat));
    }

    /**
     * 生成签约链接
     * @param orderNo
     * @throws AlipayApiException
     */
    public static void queryFirstSignUrl(String orderNo, String customerName) throws AlipayApiException {
        AlipayFinancialnetAuthEcsignDataprepareCreateModel model = new AlipayFinancialnetAuthEcsignDataprepareCreateModel();
        model.setPartnerId(signPartnerId);
        model.setSignProductId(signProductCode);
        model.setJumpType("NORMAL");

        JSONObject extInfo = new JSONObject();
        JSONObject extData = new JSONObject();
        extData.put("org_code", orgCode);
        extData.put("product_code", productCode);
        extData.put("sales_product_code", salesProductCode);
        extData.put("customer_name", customerName);
        JSONObject extParam = new JSONObject();
        extParam.put("carType", "01");
        extParam.put("tradeType", "01");
        extParam.put("TRADE_TYPE", "01");
        extData.put("ext_param", extParam);
        extInfo.put("ext_data", extData);
        model.setExtInfo(extInfo.toJSONString());
        model.setOutOrderNo(orderNo);

        model.setPartnerId(signPartnerId);
        model.setSignProductId(signProductCode);
        AlipayFinancialnetAuthEcsignDataprepareCreateRequest request = new AlipayFinancialnetAuthEcsignDataprepareCreateRequest();
        request.setBizModel(model);
        System.out.println("model: " + JSON.toJSONString(model, SerializerFeature.PrettyFormat));
        AlipayFinancialnetAuthEcsignDataprepareCreateResponse execute = client.execute(request);
        System.out.println(JSON.toJSONString(execute, SerializerFeature.PrettyFormat));
    }

    /**
     * 生成签约链接
     * @param orderNo
     * @throws AlipayApiException
     */
    public static void querySignUrl(String orderNo, String customerName, String alipayOrderNo,
                                    String loanAmt, String loanPeriod, String firstpayamt,
                                    String monthpayamt, String lastpayamt, String interestrate
                                    ) throws AlipayApiException {
        AlipayFinancialnetAuthEcsignDataprepareCreateModel model = new AlipayFinancialnetAuthEcsignDataprepareCreateModel();
        model.setPartnerId(signPartnerId);
        model.setSignProductId(signProductCode);
        model.setJumpType("NORMAL");

        JSONObject extInfo = new JSONObject();
        JSONObject extData = new JSONObject();
        extData.put("org_code", orgCode);
        extData.put("product_code", productCode);
        extData.put("sales_product_code", salesProductCode);
        extData.put("solution_code", solutionCode);
        extData.put("customer_name", customerName);
        if (alipayOrderNo != null && !"".equals(alipayOrderNo)) {
            extData.put("app_seq_no", alipayOrderNo);
            JSONObject outParam = new JSONObject();
            outParam.put("loanAmt", new BigDecimal(loanAmt).toString());
            outParam.put("loanPeriod", loanPeriod);
            outParam.put("firstpayamt", new BigDecimal(firstpayamt).toString());
            outParam.put("monthpayamt", new BigDecimal(monthpayamt).toString());
            outParam.put("lastpayamt", new BigDecimal(lastpayamt).toString());

            BigDecimal rate = new BigDecimal(interestrate)
                    .multiply(new BigDecimal(100))
                    .setScale(4, RoundingMode.HALF_UP);
            // 查询 LPR
            MybankCreditSceneprodLprQueryResponse response = queryLpr(rate.toString());
            // LPR 浮动值
            outParam.put("floatRate", response.getFloatRate());
            //LPR 日期
            outParam.put("lprDateStr", response.getLprDateStr());
            //  LPR 期数
            outParam.put("loanTenor", response.getLoanTenor());
            // 浮动方向
            outParam.put("loanDir", response.getLoanDir());

            outParam.put("address", "上海市");
//            outParam.put("zip", );
            outParam.put("signed_city", "上海市");

            outParam.put("interestrate", rate.toString() + "%");
            extData.put("out_param", outParam);

            extInfo.put("leaseBack", JSON.parseObject(leaseBack));
            extInfo.put("tradeDetails", JSON.parseObject(tradeDetails));
            extInfo.put("mortgage", JSON.parseObject(mortgage));

        } else {
            JSONObject extParam = new JSONObject();
            extParam.put("carType", "01");
            extParam.put("tradeType", "01");
            extParam.put("TRADE_TYPE", "01");
            extData.put("ext_param", extParam);
        }
        extInfo.put("ext_data", extData);
        model.setExtInfo(extInfo.toJSONString());
        model.setOutOrderNo(orderNo);

        model.setPartnerId(signPartnerId);
        model.setSignProductId(signProductCode);
        AlipayFinancialnetAuthEcsignDataprepareCreateRequest request = new AlipayFinancialnetAuthEcsignDataprepareCreateRequest();
        request.setBizModel(model);
        System.out.println("model: " + JSON.toJSONString(model, SerializerFeature.PrettyFormat));
        AlipayFinancialnetAuthEcsignDataprepareCreateResponse execute = client.execute(request);
        System.out.println(JSON.toJSONString(execute, SerializerFeature.PrettyFormat));
    }

    /**
     * 查询订单状态
     * @param userId
     * @param orderNo
     * @throws AlipayApiException
     */
    public static void queryOrderStatus(String userId, String orderNo, String site) throws AlipayApiException {
        MybankCreditSceneprodLoanQueryModel model = new MybankCreditSceneprodLoanQueryModel();
        model.setSiteUserId(userId);
        model.setOutOrderNo(orderNo);
        model.setSite(site);
        model.setOrgCode(orgCode);
        model.setProductCode(productCode);
        MybankCreditSceneprodLoanQueryRequest request = new MybankCreditSceneprodLoanQueryRequest();
        request.setBizModel(model);
        MybankCreditSceneprodLoanQueryResponse res = client.execute(request);
        System.out.println(JSON.toJSONString(res, SerializerFeature.PrettyFormat));
    }
    /**
     * 关闭订单
     * @param userId
     * @param orderNo
     * @throws AlipayApiException
     */
    public static void loanCancel(String userId, String orderNo) throws AlipayApiException {
        MybankCreditSceneprodLoanCancelModel model = new MybankCreditSceneprodLoanCancelModel();
        //非必填  1.外部站点
        model.setSite("ALIPAY");
        //必填 3.机构编码
        model.setOrgCode(orgCode);
        //必填  4.产品编码
        model.setProductCode(productCode);

        model.setSiteUserId(userId);
        model.setOutOrderNo(orderNo);
        MybankCreditSceneprodLoanCancelRequest request = new MybankCreditSceneprodLoanCancelRequest();
        request.setBizModel(model);
        MybankCreditSceneprodLoanCancelResponse response = client.execute(request);
        System.out.println(JSON.toJSONString(response, SerializerFeature.PrettyFormat));
    }

    /**
     * 账单查询
     * @return
     */
    public static MybankCreditSceneprodBillQueryResponse billQuery(String alipayNo, String uId) throws AlipayApiException {
        MybankCreditSceneprodBillQueryModel model = new MybankCreditSceneprodBillQueryModel();
        model.setAppSeqno(alipayNo);
        model.setSite("ALIPAY");
        model.setSiteUserId(uId);
        MybankCreditSceneprodBillQueryRequest request = new MybankCreditSceneprodBillQueryRequest();
        request.setBizModel(model);
        MybankCreditSceneprodBillQueryResponse res = client.execute(request);
        System.out.println(JSON.toJSONString(res));
        return res;
    }
}
