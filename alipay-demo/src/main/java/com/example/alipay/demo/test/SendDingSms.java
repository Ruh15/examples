package com.example.alipay.demo.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiV2UserGetbymobileRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import com.taobao.api.ApiException;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author hui
 * @description 发送钉钉消息
 * @date 2020/12/22
 */
public class SendDingSms {

    private static final String appKey = "";
    private static final String appSecret = "";
    private static final long agentId = 0L;

    private static final String bm_appKey = "dingxmhxy1ja9m2ywbwt";
    private static final String bm_appSecret = "DNb5s38D9wBiVbkV-IJkgAbj17dhdyMX3fmt9JrHBvV1-HSPqEkZ9ZgXzUiD08Zq";
    private static final long bm_agentId = 1040334688L;

    public static void main(String[] args) throws ApiException {

        String accessToken = getAccessToken(bm_appKey, bm_appSecret);
        getByMobile("18086514426", accessToken);
//        sendDingText(bm_appKey, bm_appSecret, bm_agentId, "7590", "收到请回电", accessToken);

    }

    public static void getByMobile(String mobile, String accessToken) throws ApiException {
        if (StringUtils.isEmpty(accessToken)) {
            System.out.println("token 获取失败");
            return;
        }
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/getbymobile");
        OapiV2UserGetbymobileRequest req = new OapiV2UserGetbymobileRequest();
        req.setMobile(mobile);
        OapiV2UserGetbymobileResponse rsp = client.execute(req, accessToken);
        System.out.println(rsp.getBody());
    }

    private static void sendDingText(String appKey, String appSecret, long agentId, String userId, String message, String accessToken) throws ApiException {
        if (StringUtils.isEmpty(accessToken)) {
            System.out.println("token 获取失败");
            return;
        }
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setUseridList(userId);
        request.setAgentId(agentId);
        request.setToAllUser(false);
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("text");
        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
        msg.getText().setContent(message);
        request.setMsg(msg);

        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, accessToken);
        System.out.println(JSON.toJSONString(response, SerializerFeature.PrettyFormat));
    }

    private static String getAccessToken(String appKey, String appSecret) {
        String accessToken = "";
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setCorpid(appKey);
        request.setCorpsecret(appSecret);
        request.setHttpMethod("GET");
        OapiGettokenResponse response;
        try {
            response = client.execute(request);
            accessToken = response.getAccessToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }
}
