package com.example.alipay.demo.test.work;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.alipay.demo.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hui
 * @description 业务测试
 * @date 2021/1/28
 */
public class Test {

    private static String getAutoPauseDataUrl = "https://uat-k8s.keking.cn/risk-engine/api/risk/leasing/getDealerAndAffiliatedCompanyIndex";

    public static void main(String[] args) {

        try {
//            handlerPostLoanChannels("DEALER", 100, "经销商触发暂停规则");
            handlerPostLoanChannels("AFFILIATED_COMPANY", 50, "挂靠公司触发暂停规则");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理渠道数据
     *
     * @param nature
     * @param balance
     * @param reason
     * @return
     */
    private static List<String> handlerPostLoanChannels(String nature, int balance, String reason) {
        List<String> dealerDataList = new ArrayList<>();
        // 获取 渠道性质为 经销商的数据
        List<String> res = getHandlerData(nature, balance, 20, 0, 1, 100);
        if (!CollectionUtils.isEmpty(res)) {
            dealerDataList.addAll(res);
        }
        res = getHandlerData(nature, balance, 0, 15, 1, 100);

        if (!CollectionUtils.isEmpty(res)) {
            dealerDataList.addAll(res);
        }
        if (!CollectionUtils.isEmpty(dealerDataList)) {
            System.out.println(dealerDataList.size());
//            dealerBusinessService.channelAutoPause(dealerDataList, reason);
        }
        return dealerDataList;
    }

    /**
     * 获取需要 处置的渠道信息
     *
     * @param nature
     * @param balance
     * @param m1
     * @param m2
     * @param page
     * @param size
     * @return
     */
    private static List<String> getHandlerData(String nature, int balance, int m1, int m2, int page, int size) {
        System.err.println("获取参数" + nature + ":" + balance + ":page==" + page + ":" + size);
        JSONObject param = new JSONObject();
        param.put("nature", nature);
        param.put("balance", balance);
        if (m1 > 0) {
            param.put("m1", m1);
        }
        if (m2 > 0) {
            param.put("m2", m2);
        }
        String url = getAutoPauseDataUrl + "?page=" + page + "&size=" + size;
        String res = HttpClientUtil.doPost(url, param.toJSONString());
        if (StringUtils.isEmpty(res)) {
            System.out.println("数据不存在,参数为" + param);
        } else {
            try {
                JSONObject resObject = JSON.parseObject(res);
                if (!"0".equals(resObject.getString("code"))) {
                    return null;
                }
                JSONObject data = resObject.getJSONObject("content");
                int totalPages = data.getIntValue("pages");
                int total = data.getIntValue("total");
                JSONArray records = data.getJSONArray("records");
                if (CollectionUtils.isEmpty(records)) {
                    return null;
                }
                List<String> channelList = new ArrayList<>(total);
                for (Object record : records) {
                    JSONObject channelInfo = JSON.parseObject(record.toString());
                    channelList.add(channelInfo.getString("name"));
                }
                if (page < totalPages) {
                    List<String> handlerData = getHandlerData(nature, balance, m1, m2, page + 1, size);
                    if (handlerData != null) {
                        channelList.addAll(handlerData);
                    }
                } else {
                    System.err.println("获取完成" + nature + total);
                }
                return channelList;
            } catch (Exception e) {
                System.out.println("结果解析失败" + e.getMessage());
            }
        }
        return null;
    }
}
