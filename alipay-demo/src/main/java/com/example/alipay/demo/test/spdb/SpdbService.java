package com.example.alipay.demo.test.spdb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import com.example.alipay.demo.util.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;


/**
 * @author hui
 * @description
 * @date 2020/12/8
 */
public class SpdbService {


    // 合作平台
    public static final String coopPltfrmtp = "3026";
    // 贷款属性
    public static final String loanProperty = "02";
    // 产品类别
    public static final String productType = "300001";
    // 产品代码
    public static final String productCode = "3026300001";
    public static final String TIMEFORMAT = "yyyyMMddHHmmss";
    public static final String DATEFORMAT = "yyyyMMdd";
    // 消费平台编号  kjzjd01:凯京租金贷
    public static final String BUSSPLTFRMID = "kjzjd01";
    // 还款方式
    public static final String REPAYMODE = "1";
    public static final String REPAY_MODE_NAME = "等额本息";
    // 贷款金额
    public static final String LOANAMT = "150000.00";
    // 贷款期限
    public static final String LOANTERM = "020000";
    public static final Integer LOAN_TERM_NUM = 24;
    // 签约类型
    public static final String SIGNTYPE = "4";
    // 凯京融资总额
    public static final String TOTALAMT = "160000.00";
    public static final BigDecimal DIVISOR = new BigDecimal("0.71");// 凯京融资总额/0.71 = 车辆成交价

    public static final String ADDRESS = "上海市";


    public static void main(String[] args) {
        SpdbService spdbService = new SpdbService();
//        apply();

        String dateStr = DateUtil.formatDateToStr(new Date(), "yyyyMMdd");
        // 4. 查询结果
        spdbService.queryApplyList(dateStr, dateStr, "000", SpdbService.IDNUM);

        // 审批作废
//        cancelOrder(IDNUM, "DBSQ202000000395");

//        网贷审批作废参数：{"coopPltTp":"3026","cardId":"111111198101010505","loanAcctAttr":"02","pdType":"300001","aplSeqNo":"DBSQ202000000395","changeFlg":"4","idTpCd":"1","productNo":"3026300001"}
//        {
//            "success":"{\"transNo\":\"04972012090171142421147013\",\"ntwLnTranSeqNo\":\"AK631209114241668f60\",\"ntwLnSysDt\":\"20201209\",\"cnclSeqNo\":\"ZFSQ202000000022\",\"changeFlg\":\"4\",\"crAplSeqNo\":\"\",\"aplSeqNo\":\"DBSQ202000000395\"}"
//        }
    }

    // ------------------------------****************************进件相关操作***************************************-------------------------
    // ------------------------------****************************下单相关操作***************************************-------------------------

    /**
     * 1. 根据客户信息获取客户校验结果。在互联网贷款场景中，对用户信息进行准入校验。
     * 根据客户有效证件信息包括客户姓名、证件类型及证件号码及业务场景（比如授信申请）获取客户校验结果
     */
    public void access() {
        String url = PREFIXURL + "o2oloan/accessRulesCheck";
        JSONObject param = new JSONObject();
        param.put("coopPltfrmTp", coopPltfrmtp);// 合作平台
        param.put("loanProperty", loanProperty);// 贷款属性
        param.put("productType", productType);// 产品类别
        param.put("productNo", productCode);// 产品代码
        param.put("idType", IDTYPE);// 证件类型
        param.put("idNum", IDNUM);// 证件号码
        param.put("clientName", CUSTNAME);// 客户姓名
        param.put("bizScene", "03");// 业务场景
        JSONObject res = HttpClientUtil.postWithJsonMsg(param.toString(), url, CLIENTID, CLIENTSECRET);
        System.out.println(JSON.toJSONString(res, true));
    }

    /**
     * 2. 下单推数
     */
    public String pushOrder(Date date) throws Exception {
        String url = PREFIXURL + "o2oloan/kJRentOrdInfoPsh";
        JSONObject param = new JSONObject();
        param.put("coopPltfrmTp", coopPltfrmtp);// 合作平台
        param.put("loanProperty", loanProperty);// 贷款属性
        param.put("productType", productType);// 产品类别
        param.put("productCode", productCode);// 产品代码

        String orderNo = NumberUtil.generateNumForCreateOrder();
        System.err.println("订单编号: " + orderNo);
        param.put("ordrNo", orderNo);// 订单编号
//        param.put("ordrNo", "CO201908071802050772432");// 订单编号
//        param.put("ordrNo", "CO201908071807286512724");// 订单编号
        param.put("ordrDate", DateUtil.formatDateToStr(date, DATEFORMAT));// 订单日期
        param.put("ordrTime", DateUtil.formatDateToStr(date, TIMEFORMAT));// 订单时间
        param.put("bussPltfrmId", BUSSPLTFRMID);// 消费平台编号  kjzjd01:凯京租金贷
        param.put("loanTerm", LOANTERM);// 贷款期限

        param.put("loanAmt", LOANAMT);// 贷款金额
        param.put("repayMode", REPAYMODE);// 还款方式
        param.put("ctfType", IDTYPE);// 证件类型
        param.put("ctfId", IDNUM);// 证件号码
        param.put("clientName", CUSTNAME);// 客户姓名
        param.put("prpsGender", "M");// 申请人性别
        param.put("prpsBrthDt", BIRTHDAY);// 申请人出生日期
        param.put("bussType", "1");// 业务类型

        param.put("contactMode", MOBILE);// 联系方式
        param.put("incmSts", "1");// 收入状态
        param.put("othrIncmSrc", "2");// 收入来源
        param.put("annualIncome", "1000000.00");// 年收入
        param.put("dwllAdr", ADDRESS);// 居住地址
        param.put("domicileAdr", ADDRESS);// 户籍所在地
        param.put("prpsMrtlStCd", "10");// 婚姻状况

        param.put("crspNm", "黄伟大");// 联系人1姓名
        param.put("crspRltnp", "9");// 联系人1关系
        param.put("crspMbl", "18823567896");// 联系人1手机号
        param.put("vhclLoAdr", "上海市");// 车辆所在地
        param.put("vhclType", "1");// 车辆类型
        param.put("vhclBargainPrc", new BigDecimal(TOTALAMT).divide(DIVISOR, 2));// 车辆成交价
        param.put("othrFee", "0.00");// 购车相关其他费用
        param.put("totalAmt", TOTALAMT); // 融资租赁总金额
        System.out.println("推数参数：" + param.toJSONString());
        JSONObject res = HttpClientUtil.postWithJsonMsg(param.toJSONString(), url, CLIENTID, CLIENTSECRET);
        System.out.println(JSON.toJSONString(res, true));
        if (null != res.get("502")) {
            throw new Exception(res.getJSONObject("502").toJSONString());
        }
        return orderNo;
    }

    /**
     * 3. 网贷单笔贷款申请
     */
    public String apply(String orderNo) throws Exception {
        String url = PREFIXURL + "o2oloan/ntwSnglLnAplExn";
        JSONObject param = new JSONObject();
        param.put("coopPltTp", coopPltfrmtp);// 合作平台
        param.put("loanType", loanProperty);// 贷款属性
        param.put("productType", productType);// 产品类别
        param.put("productCode", productCode);// 产品代码

//        param.put("thdPtyObjId", "");// 第三方对象ID
        param.put("ctfType", IDTYPE);// 证件类型
        param.put("ctfNo", IDNUM);// 证件号码
        param.put("clientName", CUSTNAME);// 客户姓名

        param.put("spellName", ChangeToPinYinJPUtil.changeToTonePinYin(CUSTNAME).toUpperCase());// 姓名拼音
        param.put("ctfIssuInstNm", "上海市");// 发证机关
        param.put("clientType", "1");// 客户类型
        param.put("contactAdr", "上海市浦东新区杨高南路");// 联系地址
        param.put("mailCode1", "000000");// 联系地址邮政编码
        param.put("ctcEmail", EMAIL);// 常用邮箱
        param.put("cellPhoneNum", MOBILE);// 手机号码
        param.put("edcDgree", "0");// 教育水平   1 高中及以下 3 大专 4 大学 6 硕士及以上 0 其他

        param.put("dwllState", "9");// 居住状态   0房改/继承 1公司提供 2按揭自置 3无按揭自置 4与父母同住 5租借 9其他
        param.put("dwllAdr", "上海市浦东新区");// 居住地址
        param.put("dwllAdrMailCd", "000000");// 居住地址邮政编码
        param.put("prpsMrtlStCd", "10");// 婚姻状况  00 未知 10 未婚 20 已婚 30 丧偶 40 离婚 默认 00未知
//        param.put("spouseName", "");// 配偶姓名  如果“婚姻状况”为20已婚时必须输入，否则非必输。
//        param.put("spouseCtfType1", "1");// 配偶证件类型  如果“婚姻状况”为20已婚时必须输入，否则非必输。
//        param.put("spsCtfType1", "1");// 配偶证件号码  如果“婚姻状况”为20已婚时必须输入，否则非必输。spsCtfType1
        param.put("loanUsgType", "b");// 贷款用途
        param.put("lndOpnAcctBrNo", "310290000013");// 放款账户开户行行号
//        param.put("lndCardId", "");// 放款卡号
        param.put("repyOpnAcctBrNo", "310290000013");// 还款账户开户行行号
        param.put("repyCardId", REPYCARDID);// 还款卡号
        param.put("ourBankPrvyFlg", "N");// 是否我行关系人
        String numForSPDBAHRA = NumberUtil.generateNumForSPDBAHRA();
        System.err.println("征信授权书编号：" + numForSPDBAHRA);
        param.put("crQryAhrBkNum", numForSPDBAHRA);// 征信查询授权书编号
        param.put("crQryAhrTm", DateUtil.formatDateToStr(new Date(), TIMEFORMAT));// 征信查询授权时间  按照交易调用的时间
        param.put("crAnsrRsltCd", "YYY");//征信答题结果 总共20位，每一位的值对应题库征信问题的客户回答答案 Y标识问题答案为是
        String numForSPDBAHRB = NumberUtil.generateNumForSPDBAHRB();
        System.err.println("贷款用途声明书编号：" + numForSPDBAHRB);
        param.put("lnUsgStmtId", numForSPDBAHRB);// 贷款用途声明书编号
        param.put("lnUsgStmtTm", DateUtil.formatDateToStr(new Date(), TIMEFORMAT));// 贷款用途声明时间

        param.put("useAmount1", LOANAMT);// 支用金额
        param.put("useStndTrm", LOANTERM);// 支用标准期限
        param.put("repayMode", REPAYMODE); // 还款方式

        System.out.println("下单参数: " + param);
        JSONObject res = HttpClientUtil.postWithJsonMsg(param.toJSONString(), url, CLIENTID, CLIENTSECRET);
        System.out.println(JSON.toJSONString(res, true));
        if (null != res.get("502")) {
            throw new Exception(res.getJSONObject("502").toJSONString());
        }
        return res.getJSONObject("success").getString("aplSeqId");
    }

    /**
     * 4. 互联网贷款对网贷申请列表信息进行查询。
     * 在互联网贷款场景中，根据客户有效信息包括证件类型、处理状态、申请日期等，对网贷申请列表进行查询，并获取列表查询结果。
     */
    public void queryApplyList(String startDate, String endDate, String status, String idCard) {
        String url = PREFIXURL + "o2oloan/aplListQuery";
        JSONObject param = new JSONObject();
        param.put("coopPltfrmTp", coopPltfrmtp);// 合作平台
        param.put("loanProperty", loanProperty);// 贷款属性
        param.put("productType", productType);// 产品类别
        param.put("productCode", productCode);// 产品代码
        param.put("idType", IDTYPE);// 证件类型
        param.put("idNum", idCard);// 证件号码
        /*
        A01 审批中 A02 审批失败 A03 审批通过  A04 审批作废 B01 额度生效  B02 额度失效 B03 额度暂停
        C01 已签订 C02 贷款作废 C03 已放款 C04 已结清 C05 逾期
        000 查询全部
         */
        param.put("processState", status);// 处理状态
        param.put("aplStartDate", startDate);// 申请日期（从）YYYYMMDD
        param.put("aplEndtDate", endDate);// 申请日期（到）
        param.put("startNum", "1");// 起始记录号
        param.put("queryNum", "10");// 查询记录数  -- 所有查询交易单次最多可以查10条记录
        System.out.println("网贷申请列表信息查询参数：" + param);
        JSONObject res = HttpClientUtil.postWithJsonMsg(param.toJSONString(), url, CLIENTID, CLIENTSECRET);

        System.out.println(JSON.toJSONString(res, true));
    }

    /**
     * 5. 网贷审批作废
     */
    public void cancelOrder(String idCard, String aplId) {
        String url = PREFIXURL + "o2oloans/approvals/termination";
        JSONObject param = new JSONObject();
        param.put("coopPltTp", coopPltfrmtp);// 合作平台
        param.put("loanAcctAttr", loanProperty);// 贷款属性
        param.put("pdType", productType);// 产品类别
        param.put("productNo", productCode);// 产品代码
        param.put("idTpCd", IDTYPE);// 证件类型
        param.put("cardId", idCard);// 证件号码
        param.put("aplSeqNo", aplId);// 申请流水号
        param.put("changeFlg", "4");// 变更场景
        System.out.println("网贷审批作废参数：" + param);
        JSONObject res = HttpClientUtil.postWithJsonMsg(param.toJSONString(), url, CLIENTID, CLIENTSECRET);
        System.out.println(JSON.toJSONString(res, true));
    }

    /**
     * 5. 网贷申请明细查询， 查询额度
     */
    public void queryDetail(String idCard, String aplId) {
        String url = PREFIXURL + "o2oloan/applyDetailQry";
        JSONObject param = new JSONObject();
        param.put("coopPltfrmTp", coopPltfrmtp);// 合作平台
        param.put("loanProperty", loanProperty);// 贷款属性
        param.put("productType", productType);// 产品类别
        param.put("productCode", productCode);// 产品代码
        param.put("idType", IDTYPE);// 证件类型
        param.put("idNum", idCard);// 证件号码
        param.put("aplSerialNo", aplId);// 申请流水号
        param.put("aplType", "2");// 申请类型
        System.out.println("贷款申请明细查询参数：" + param);
        JSONObject res = HttpClientUtil.postWithJsonMsg(param.toJSONString(), url, CLIENTID, CLIENTSECRET);
        System.out.println(JSON.toJSONString(res, true));
        JSONObject success = res.getJSONObject("success");
        System.out.println(ApprovalEnums.valueOf(success.getString("processState")).getDesc());
    }

    // ------------------------------****************************放款相关操作***************************************-------------------------
    // ------------------------------****************************放款相关操作***************************************-------------------------

    /**
     * 6. 授信合同生成
     * 对用户信息进行授信/支用合同生成。在互联网贷款场景中，根据客户有效贷款信息包括签约类型、申请流水号等生成授信/支用合同。
     */
    public String generateContract(String aplId) {
        String url = PREFIXURL + "o2oloan/assPactPrd";
        JSONObject param = new JSONObject();
        param.put("coopPltfrmTp", coopPltfrmtp);// 合作平台
        param.put("loanProperty", loanProperty);// 贷款属性
        param.put("productType", productType);// 产品类别
        param.put("productCode", productCode);// 产品代码
        param.put("signType", SIGNTYPE);// 签约类型
        param.put("signAmount", LOANAMT);// 签订额度  精确到小数位后两位 Eg:100.00
        param.put("loanGiveOutAmt", LOANAMT);// 贷款发放金额
        param.put("useStnDeadline", LOANTERM);// 支用标准期限
        param.put("loanUseType", "b");// 贷款用途
        param.put("repaymentMode", REPAYMODE);// 还款方式  1 等额本息
        param.put("aplNo", aplId);// 浦发申请流水号   需要跟系统订单表订单关联
        System.out.println("授信合同参数：" + param);
        JSONObject res = HttpClientUtil.postWithJsonMsg(param.toJSONString(), url, CLIENTID, CLIENTSECRET);
        System.out.println(JSON.toJSONString(res, true));
        return res.getJSONObject("success").getString("debtOnBondNum");
    }

    /**
     * 7.网贷授信支用合同签订
     */
    public void signContract(String debtOnBondNum) {
        String url = PREFIXURL + "o2oloan/assPactSign";
        JSONObject param = new JSONObject();
        param.put("coopPltfrmTp", coopPltfrmtp);// 合作平台
        param.put("loanProperty", loanProperty);// 贷款属性
        param.put("productType", productType);// 产品类别
        param.put("productCode", productCode);// 产品代码
        param.put("signType", SIGNTYPE);// 签约类型
//        param.put("assPactNum", "");// 授信合同编号
        param.put("debtOnBondNum", debtOnBondNum);// 借据编号  使用合同生成接口返回的借据号 上个接口返回
        System.out.println("网贷授信支用合同签订参数：" + param);
        JSONObject res = HttpClientUtil.postWithJsonMsg(param.toJSONString(), url, CLIENTID, CLIENTSECRET);
        System.out.println(JSON.toJSONString(res, true));
    }

    //  8 调用第四步 testAplListQuery 查看放款是否成功

    /**
     * 9. 网贷订单放款信息查询
     * 订单放款信息查询。在互联网贷款场景中，可对已签订的授信支用合同进行订单放款信息查询。
     */
    public void queryLoanInfo(String orderNo, String orderDate, String idCard) {
        String url = PREFIXURL + "o2oloan/ntwOrdrLoanMsgQry";
        JSONObject param = new JSONObject();
        param.put("coopPlatform", coopPltfrmtp);// 合作平台
        param.put("loanProperty", loanProperty);// 贷款属性
        param.put("productType", productType);// 产品类别
        param.put("productCode", productCode);// 产品代码
        param.put("ordrNum1", orderNo);// 订单编号
        param.put("ordrDate", orderDate);// 订单日期
        param.put("idNumber", idCard);// 证件号码
        System.out.println("网贷订单放款信息查询：" + param);
        JSONObject res = HttpClientUtil.postWithJsonMsg(param.toJSONString(), url, CLIENTID, CLIENTSECRET);
        System.out.println(JSON.toJSONString(res, true));
    }

    /**
     * 10、网贷订单放款信息列表查询
     */
    public void queryLoanList(String idCard) {
        String url = PREFIXURL + "o2oloan/orderLnInfoLstQry";
        JSONObject param = new JSONObject();
        param.put("coopPltfrmTp", coopPltfrmtp);// 合作平台
        param.put("loanProperty", loanProperty);// 贷款属性
        param.put("productType", productType);// 产品类别
        param.put("productCode", productCode);// 产品代码
        param.put("idType", IDTYPE);// 证件类型
        param.put("idNum", idCard);// 证件号码
        param.put("startRecordNo", "1");// 起始记录号
        param.put("queryRecordNum", "10");// 查询记录数
        System.out.println("网贷订单放款信息列表查询：" + param);
        JSONObject res = HttpClientUtil.postWithJsonMsg(param.toJSONString(), url, CLIENTID, CLIENTSECRET);
        System.out.println(JSON.toJSONString(res, true));
    }

    /**
     * 11、网络贷提前还款试算
     */
    public void advanceRepayTrial(String debtOnBondNum) {
        String url = PREFIXURL + "o2oloan/ntwAdvRepyTrial";
        JSONObject param = new JSONObject();
        param.put("productCode", productCode);// 产品代码
        param.put("iouNo", debtOnBondNum);// 借据号
        param.put("repaymentDate", "20190822");// 本次还款日期
        param.put("advRepyMode", "1");// 提前还款方式  1-部分  2-全部
        param.put("formulaMode", "1");// 测算方式  当提前还款方式是“1－部分”时必须输入 1-本息 2-本金
        param.put("transactionAmt", LOANAMT);// 交易金额  当提前还款方式是“1－部分”时必须输入
        param.put("serviceCharge", "0.00");// 服务费金额
        System.out.println("网络贷提前还款试算:" + param);
        JSONObject res = HttpClientUtil.postWithJsonMsg(param.toJSONString(), url, CLIENTID, CLIENTSECRET);
        System.out.println(JSON.toJSONString(res, true));
    }

    /**
     * 12、用户进行提前还款
     */
    public void advanceRepay(String debtOnBondNum) {
        String url = PREFIXURL + "o2oloan/lnAdvRepymtApl";
        JSONObject param = new JSONObject();
        param.put("coopPltfrmTp", coopPltfrmtp);// 合作平台
        param.put("loanProperty", loanProperty);// 贷款属性
        param.put("productType", productType);// 产品类别
        param.put("productCode", productCode);// 产品代码
        param.put("iouNum", debtOnBondNum);// 借据号
        param.put("advRepyMd", "1");// 提前还款模式
        param.put("advRepyPrncpl", "1000.00");// 提前还款本金
        System.out.println("用户进行提前还款:" + param);
        JSONObject res = HttpClientUtil.postWithJsonMsg(param.toJSONString(), url, CLIENTID, CLIENTSECRET);
        System.out.println(JSON.toJSONString(res, true));
    }

    /**
     * 13、网贷贷款还款计划查询   放款当天无法查到 还款计划
     */
    public void repayPlanQuery(String debtOnBondNum, String startDate, String endDate) {
        String url = PROD_PREFIXURL + "o2oloan/lnRepymtPlanQry";
        JSONObject param = new JSONObject();
        param.put("iouNum", debtOnBondNum);// 借据号
        param.put("startDate", startDate);// 起始日期
        param.put("endDate", endDate);// 终止日期   借款日期加上 借款总时间
        param.put("startNum1", "1");// 起始笔数
        param.put("queryNum1", "100");// 查询笔数
        System.out.println("网贷贷款还款计划查询：" + param);
        JSONObject res = HttpClientUtil.postWithJsonMsg(param.toJSONString(), url, PROD_CLIENTID, PROD_CLIENTSECRET);
        System.out.println(JSON.toJSONString(res, true));
        System.out.println(res.getJSONObject("success").getJSONArray("repymtInfoArray"));
    }

    /**
     * 网贷还款账户变更
     */
    public void repayAccChanges(String debtOnBondNum) {
        String url = PREFIXURL + "o2oloan/repyAccChanges";
        JSONObject param = new JSONObject();
        param.put("coopPltfrmTp", coopPltfrmtp);// 合作平台
        param.put("loanProperty", loanProperty);// 贷款属性
        param.put("productType", productType);// 产品类别
        param.put("productCode", productCode);// 产品代码
        param.put("alterMode", "1");// 变更方式  1 - 按借据号变更
        param.put("iouNo", debtOnBondNum);// 借据号
//        param.put("assurePactNo", "1");// 授信合同编号
        param.put("bankAccountSign", "0");// 行内账户标志 0-本行帐户
        param.put("repyOpnAcctBrId", "310290000013");// 还款账户开户行行号

        param.put("repyCardNum", "310290000013");// 还款卡号
        param.put("repyAccountType", "1");// 还款账户类型
//        param.put("settleAcctNum", "");// 还款结算账户
//        param.put("noClrgDublSign", "Y");// 同步修改已发放未结清借据标志 N-不变更 Y-同步更新
        param.put("agreementNum", "1");// 还款账户变更协议书编号
        param.put("agrmChangeDtTm", "1");// 还款账户变更协议时间
        System.out.println("网贷还款账户变更:" + param);
        JSONObject res = HttpClientUtil.postWithJsonMsg(param.toJSONString(), url, CLIENTID, CLIENTSECRET);
        System.out.println(JSON.toJSONString(res, true));
    }

    /**
     * 网贷支用列表查询
     */
    public void loanListQuery(String idCard, String startDate, String endDate) {
        String url = PREFIXURL + "o2oloan/ntwUseLstQuery";
        JSONObject param = new JSONObject();
        param.put("coopPltfrmTp", coopPltfrmtp);// 合作平台
        param.put("lnProperty", loanProperty);// 贷款属性
        param.put("productTp", productType);// 产品类别
        param.put("productCd", productCode);// 产品代码

        param.put("ctfType", IDTYPE);// 证件类型
        param.put("ctfId", idCard);// 证件号码
        /*
         * 000 全部
         * 105 已放款
         * 106 已结清
         * 107 逾期
         * 默认000
         */
        param.put("lnRepyState", "000");// 贷款状态
        param.put("startDate", startDate);// 起始日期
        param.put("endDate", endDate);// 结束日期
        param.put("startNum1", "1");// 起始记录号
        param.put("queryNum1", "10");// 查询记录数 所有查询交易单次最多可以查10条记录
        System.out.println("网贷支用列表查询:" + param);
        JSONObject res = HttpClientUtil.postWithJsonMsg(param.toJSONString(), url, CLIENTID, CLIENTSECRET);
        System.out.println(JSON.toJSONString(res, true));
    }


    /*  ----------------电子合同相关-------------------- */
    // 1. 网贷待签名电子合同生成交易
    // 2. 网贷待签名电子合同查询交易
    // 3. 网贷合同文本查询
    // 4. 网贷电子合同生成签名申请
    // 5. 网贷电子合同生成签名申请状态查询

    // 1. 网贷待签名电子合同生成交易
    /**
     * 网贷待签名电子合同生成交易
     */
    public void initContract(InitContractXmlData initContractXmlData) {
        String url = PREFIXURL + "o2oloans/contracts/initiation";
        JSONObject param = new JSONObject();
        param.put("templateNo", "APIM20002");
        //场景编号， 4位，0表示否，1表示是。4位分别代表合成、加签、存储、发送。
        param.put("sroNo", "1010");
        param.put("tranTimep", DateUtil.formatDateToStr(new Date(), "yyyyMMddHHmmss"));

        param.put("dateCntnt", SpdbDataUtil.getXmlData(initContractXmlData));
        System.out.println("网贷待签名电子合同生成交易:" + param);
        JSONObject res = HttpClientUtil.postWithJsonMsg(param.toJSONString(), url, CLIENTID, CLIENTSECRET);
        System.out.println(JSON.toJSONString(res, true));
    }

    // 2. 网贷待签名电子合同查询交易
    /**
     * 网贷待签名电子合同生成交易结果查询
     */
    public void initContractRes(String formNO) {
        String param = "formNO=" + formNO;
        String url = PREFIXURL + "o2oloans/contracts/infos?" + param;

        String res = null;
        try {
            res = HttpClientUtil.doGetUseOkHttp(url,  CLIENTID, CLIENTSECRET, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (res != null) {
            String fileContent = res.substring(res.indexOf("<FileContent>") + 13, res.indexOf("</FileContent>"));

            //将base64的编码转成PDF格式文件,，保存到XXX
            PDFBinaryUtil.base64StringToPDF(fileContent, "E:\\test4.pdf");
//            try (FileWriter writer = new FileWriter(new File("E:\\test.pdf"))) {
//                Base64.Decoder decoder = Base64.getDecoder();
//                String s = Arrays.toString(decoder.decod(fileContent));
//                writer.write(fileContent);
//                writer.flush();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }

        System.out.println(res);
    }
    // 3. 网贷合同文本查询
    // 4. 网贷电子合同生成签名申请
    /**
     *
     * @param signContractXmlData
     */
    public void signContractApply(SignContractXmlData signContractXmlData) {
        String url = PREFIXURL + "o2oloans/eContracts/applications";
        Map<String, Object> param = new HashMap<>();
        param.put("templateNo", "APIM20002");
        //场景编号， 4位，0表示否，1表示是。4位分别代表合成、加签、存储、发送。
        param.put("sroNo", "0110");
        param.put("tranTimep", DateUtil.formatDateToStr(new Date(), "yyyyMMddHHmmss"));

        param.put("dateCntnt", SpdbDataUtil.getXmlData(signContractXmlData));

        byte[] bytes = new byte[0];
        try {
            bytes = FileUtils.readFileToByteArray(new File("F:\\test.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
//        String encode = base64.encodeAsString(bytes);
        param.put("content1", bytes);
        param.put("content2", bytes);
        param.put("content3", bytes);
        System.out.println("网贷电子合同生成签名申请:" + param);
        String fileMD5 = DigestUtils.md5Hex(bytes);
        String filesMD5 = fileMD5 + "," + fileMD5 + "," + fileMD5;
        JSONObject res = HttpClientUtil.postWithFile(param, url, CLIENTID, CLIENTSECRET, filesMD5);
        System.out.println(JSON.toJSONString(res, true));
    }

    // 5. 网贷电子合同生成签名申请状态查询

    public void querySignContractRes(String formNo) {
        String param = "fRMDntfrVlm=" + formNo;
        String url = PREFIXURL + "o2oloans/eContracts/applicationStatus?" + param;

        String res = null;
        try {
            res = HttpClientUtil.doGetUseOkHttp(url,  CLIENTID, CLIENTSECRET, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res);
    }

    /*  ------------------------------------ */

    /**
     * 网贷合同文本查询
     */
    public void contractInfoQuery(String debtOnBondNum, String idCard) {
        String url = PROD_PREFIXURL + "o2oloan/ntwCtrTxQry";
        JSONObject param = new JSONObject();
        param.put("coopPltfrmTp", coopPltfrmtp);// 合作平台
        param.put("loanProperty", loanProperty);// 贷款属性
        param.put("productType", productType);// 产品类别
        param.put("productCode", productCode);// 产品代码

        param.put("queryType", "2");// 查询类型 1-按授信编号 2- 按借据号
//        param.put("crCtrId", "");// 查询类型为 1-按授信编号  必输
        param.put("iouNo", debtOnBondNum);// 借据编号 查询类型为 2-按借据号  必输

        param.put("idType", IDTYPE);// 证件类型
        param.put("idNum", idCard);// 证件号码
        System.out.println("网贷合同文本查询:" + param);
        JSONObject res = HttpClientUtil.postWithJsonMsg(param.toJSONString(), url, PROD_CLIENTID, PROD_CLIENTSECRET);
        System.out.println(JSON.toJSONString(res, true));
    }

    /**
     * 网贷还款历史查询
     */
    public void repayHisQuery(String debtOnBondNum, String startDate, String endDate) {
        String url = PREFIXURL + "o2oloan/loanRepyHistQry";
        JSONObject param = new JSONObject();
        param.put("iouNo", debtOnBondNum);// 借据编号 查询类型为 2-按借据号  必输

        param.put("startDate", startDate);// 起始日期
        param.put("endDate", endDate);// 终止日期
        param.put("startNum", "1");// 起始笔数
        param.put("queryNum", "20");// 查询笔数
        System.out.println("网贷还款历史查询:" + param);
        JSONObject res = HttpClientUtil.postWithJsonMsg(param.toJSONString(), url, CLIENTID, CLIENTSECRET);
        System.out.println(JSON.toJSONString(res, true));
    }


    /**
     * 网贷计价利率查询基准基础利率
     * @param queryDate
     */
    public JSONObject intRateQuery(String queryDate) {
        String url = PREFIXURL + "o2oloan/vltIntRateQryBaseIntRate";
        JSONObject param = new JSONObject();
        param.put("intTerm", "001");
        param.put("queryDate", queryDate);
        System.out.println("网贷计价利率查询基准基础利率:" + param);
        JSONObject res = HttpClientUtil.postWithJsonMsg(param.toJSONString(), url, CLIENTID, CLIENTSECRET);
        System.out.println(JSON.toJSONString(res, true));
        return res.getJSONObject("success");
    }

}
