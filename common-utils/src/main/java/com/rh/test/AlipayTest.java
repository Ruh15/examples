//package com.rh.test;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.alipay.api.AlipayApiException;
//import com.alipay.api.AlipayClient;
//import com.alipay.api.DefaultAlipayClient;
//import com.alipay.api.request.AlipayOpenPublicQrcodeCreateRequest;
//import com.alipay.api.response.AlipayOpenPublicQrcodeCreateResponse;
//import com.dingtalk.api.DefaultDingTalkClient;
//import com.dingtalk.api.DingTalkClient;
//import com.dingtalk.api.request.OapiGettokenRequest;
//import com.dingtalk.api.request.OapiProcessinstanceGetRequest;
//import com.dingtalk.api.request.OapiProcessinstanceListidsRequest;
//import com.dingtalk.api.response.OapiGettokenResponse;
//import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
//import com.dingtalk.api.response.OapiProcessinstanceListidsResponse;
//import com.taobao.api.ApiException;
//
///**
// * content:
// *   https://open-doc.dingtalk.com/microapp/serverapi2/tcwmha
// * @author Ruh
// * @time 2019/7/16
// **/
//public class AlipayTest {
//    private static final String APPKEY = "appKey";
//    private static final String APPSECRET = "appsecret";
//    private static final String GET = "GET";
//    private static final String POST = "POST";
//    private static final String PROCESSCODETEST = "PROC-xxx";
//    private static final String APPID = "appId";
//    private static final String PRIVATEKEY = "privateKey";
//    private static final String ALIPAYPUBLICKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr/5okc3HQg5lZI289Tv2qukoDc+/YUziEfk68QhDWk/OULHsegs8umhp+5mG5bEVt1lpi2NOoL4vOeABHCVV4r2kNbaKgA1jt2TU5iDLAWgk9cCALnGT+Ixho47Gl8phw6DSHUNfbzm/DnoRx+Q5UkpyoVBvJZIbnnRO3VJYDav7nu6iLmr+iMo+hCstAEce0m0S+3ysjVaJ0FqB2wvQJ1ecF2o4uxvtQhVK6S1UHgIsYjLIlIwI8Omyg6U68uArP3m2argjvSR6edNcAUZ8Mfqgo57K+V9waKlAwTwOeAaWAL6T0pw96QHjpPaWraW6cfr5oMyOe7SQOipRB5B3DQIDAQAB";
//    private static final String SERVERURL = "https://openapi.alipay.com/gateway.do";
//    private static final String FORMAT = "json";
//    private static final String CHARSET = "UTF-8";
//    private static final String SIGNTYPE = "RSA2";
//
//
//    public static void main(String[] args) {
//        openPublicQrcode();
////        getToken();
////        String accessToken = "9debd368cdb8320da7ea3f7435595511";
////        JSONObject response = processinstanceListids(accessToken, PROCESSCODETEST);
////        JSONObject result = response.getJSONObject("result");
////        JSONArray list = result.getJSONArray("list");
////        processinstanceDetail(accessToken, list.get(0).toString());
//    }
//
//    /**
//     * 获取 accessToken 正常情况下access_token有效期为7200秒，有效期内重复获取返回相同结果，并自动续期。
//     * 52558bf20b95310e83c86478d4f9ec98
//     */
//    private static void getToken() {
//        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
//        OapiGettokenRequest request = new OapiGettokenRequest();
//        request.setAppkey(APPKEY);
//        request.setAppsecret(APPSECRET);
//        request.setHttpMethod(GET);
//        try {
//            OapiGettokenResponse response = client.execute(request);
//            System.out.println(JSON.toJSONString(response));
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 企业使用此接口可获取某个审批的实例id列表。企业可以再根据审批实例id，调用获取审批实例详情接口，获取实例详情信息。
//     * @param accessToken
//     * @return
//     */
//    private static JSONObject processinstanceListids(String accessToken, String processCode){
//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/listids");
//        OapiProcessinstanceListidsRequest req = new OapiProcessinstanceListidsRequest();
//        req.setProcessCode(processCode);
//        req.setStartTime(1528214400000L);
//        req.setEndTime(1563292800000L);
//        req.setSize(10L);
//        req.setCursor(0L);
////        req.setUseridList("manager1,manager2");
//        OapiProcessinstanceListidsResponse response = null;
//        try {
//            response = client.execute(req, accessToken);
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//        System.out.println(JSON.toJSONString(response, true));
//        return (JSONObject) JSON.toJSON(response);
//    }
//
//    /**
//     * 根据审批实例id调用此接口获取审批实例详情，包括审批表单信息、操作记录列表、操作人、抄送人、审批任务列表等。
//     * @param accessToken
//     * @param processInstanceId
//     * @return
//     */
//    private static Object processinstanceDetail(String accessToken, String processInstanceId){
//
//        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/get");
//        OapiProcessinstanceGetRequest request = new OapiProcessinstanceGetRequest();
//        request.setProcessInstanceId(processInstanceId);
//        OapiProcessinstanceGetResponse response = null;
//        try {
//            response = client.execute(request,accessToken);
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//        System.out.println(JSON.toJSONString(response, true));
//        return response;
//    }
//
//    /**
//     * 带参推广二维码接口
//     */
//    public static void openPublicQrcode() {
//        AlipayClient alipayClient = new DefaultAlipayClient(SERVERURL,APPID,PRIVATEKEY,FORMAT,CHARSET,ALIPAYPUBLICKEY,SIGNTYPE);
//        AlipayOpenPublicQrcodeCreateRequest request = new AlipayOpenPublicQrcodeCreateRequest();
//        request.setBizContent("{\"code_info\":{\"scene\":{\"scene_id\":\"xxx\"},\"goto_url\":\"https://xxx/index.html\"},\"code_type\":\"PERM\",\"expire_second\":\"\",\"show_logo\":\"N\"}");
//        AlipayOpenPublicQrcodeCreateResponse response = null;
//        try {
//            response = alipayClient.execute(request);
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        System.out.println(JSON.toJSONString(response, true));
//        if(response.isSuccess()){
//            System.out.println("调用成功");
//        } else {
//            System.out.println("调用失败");
//        }
//    }
//}
