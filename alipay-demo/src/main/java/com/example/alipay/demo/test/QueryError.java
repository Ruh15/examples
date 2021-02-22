package com.example.alipay.demo.test;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFinancialnetAuthEcsignErrorQueryModel;
import com.alipay.api.request.AlipayFinancialnetAuthEcsignErrorQueryRequest;
import com.alipay.api.response.AlipayFinancialnetAuthEcsignErrorQueryResponse;

/**
 * @author chengxin.xcx
 * @version $Id: SceneQueryServiceTestBankAlb4Alipay.java, v 0.1 2020��04��02�� 13:02 chengxin.xcx Exp $
 */

public class QueryError {

    private static String format = "json";

    // SIT 网关地址
    private static String url = "http://openapi.sit.dl.alipaydev.com/gateway.do";

    // SIT 应用ID
    private static String appId = "2014060600164699";
    // 开发者RSA私钥
    private static String rsaPrivateKey
            = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDOB+7d4u08HvfvzynZGZ1FMfKi/4kravQbBd+6O38Yn/b8AfOy"
            + "+vHHm6CRFOwpAGT503SliRJgSBs6vCwrdAXZctYRzKkU7l4gCFMG8rL0kX1KWzYi3sQlsBylPay7cXVcjZiBNrxI752753L1C"
            + "+JFP6yfbOSV2xn3xB4KlMRtLDcDYWHa2ffpS9nuFzY9YF5zt+63yxUhhQxe9ISeS1K+1YGrQCemjZG8908e5kuq/ywcL/lhHB4BJ8xEp0u9OuyUTCnJy84Y"
            + "/ifTsxog8BXuDRPyzEChplssNUjH1RgHjpeCOPImypGuvbXq47KhEEPUW2qho5zIX0"
            + "+UcdsZJ0XRAgMBAAECggEAXZLCzSnUf1q9VsArDHwSrquZvKf8X6jKxz8qtoVxGvkEDr7ANQi"
            + "+KN8o1NvAynpwYfrE3q3bl7kIDOwLz4x5X6JFUX43SNdeDoRZWS1"
            +
            "/U46EbfHxK3MreMZ8rBvPyK4mFGwG2KDIcQPLCt16m4rTMIpT13B4fQsuxxXeYwXgFIiQpSbVdWadnoZvZRwDeJF3p2kMOx9THnaMNnVakBuSKgpRd18tq4MnVRLreNk5rdtEvUtRtdINUgIAjCkFISw3XgeodRYCYG04EJtO7CEQNAzirZTUXaRjfFa1WXu33xrYyYAJdtYP/Q0fxY60xL8hcAAc2Zhaee6IsJPnuYPcBQKBgQDq549qQ/DzZV7FoIv51VIhGoJOwAI1XJtTvKBtzfjX4oKDmmjOzU4tVgCryxxU53IPwd8oXmvNKJkXVH4q8h4ILwBLBiaSEPtuIjcVSbo7Z99xY2Vt2z/17h1pFLYdJSdP+yDs7iDQKZe7BmdduOys9bRr+OCrVwDerEP/oEziewKBgQDgiJJtsT60z7cM5Jyc7VvF6VnsRZuQQesicJ7AbJ8k7zCjCeCv4/LLr6VUsWs2yW0KzXG5A2nJ70uBNBe+W08vOD7jNFPvyEQEh4+39IfBWVDzq13lcW1X//OU3zUoftXiqgxxtABVB6Mly6skex4KUN1uDt4I5P8oNnvCiPc9IwKBgQDQAgaz8b++uAgI9lac/3H/kErNUydhe0SsDL7/HMH64T/zK1sdrR1J9fsYJP5MjLorC+EBDUNmY0nVJ+OlQcqoMn6O8L5c357VcoTWW/gGPL/W105szhZAPv9aGpX9DvZV06nfRCpYSkxqt4v2qRcjPVvrtHG2J4/EnkSEar1KWwKBgQCxeuKbuD3DuGiN1WsCFBC1uMUuoLrdZW2SVIj3uyR0kmjUhutGvRze6iD6eB8yODdsEYax4sPNLcx1/ZJDEnPd9EypVWR/pcI1/l2Y374rFAmMAkn/IhB3PcbxRxoCv3cbaqTZf5m/nIDWUE4gUP0m1FKjOzdAupoB1EcxNwiPFwKBgQCBYKck0H/Yl3Z7RHAjpxsoV5gZnljxU0WDa7/QW0xr/c2SBcFY1tr7sV3wiz3bEo35Zou7xd++PCetCmd+5TaQ0nNwye1L75q6x9spzb64WHMtuXfZdCB9nWcKtVx1iFNpaqY6EM8poJ2/5kPTyu+qGm7XTEJNg4gpXEtGjhLWOw==";

    //支付宝网关公钥
    private static String publicKey
            =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnorkuHxKjCNC1A/gIcUsg+P3srv/k5KWFqkveHvGpDRkcpcKCMdl/5K"
                    + "/Wr5Vhf6Cx9ZlSMx73yBA74pRN0hio4L9ZXdeUTt5tupkvXMIi"
                    + "/zKy4U1GbYhz0cHAxh01Z4tCzWN1hlJYRUDKZCV6BtUkzXlk7bzYLqiKNX4TTzyGVU7CxxS1FodbHLbjxo+qOlE3C5Cjv"
                    + "+hwyyNiySjxSZNk0WxEVtVjPSJzt/tHm9oPoCJSwbtgi6AmrQBLH/4Op0Jny9uaF2C27yJQgynfF+xjD2EglbfsZIFU"
                    + "/ViD1B1fG7OC86K7ozZMy9CwriDu46o7dhnpPqd1W2sHsugqrzSgQIDAQAB";


    public static void main(String[] args) throws Exception {

        getErrorLog();//TESTED

    }

    private static String getErrorLog() throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(url, appId, rsaPrivateKey, format,
                "GBK", publicKey, "RSA2");

        AlipayFinancialnetAuthEcsignErrorQueryRequest request = new AlipayFinancialnetAuthEcsignErrorQueryRequest();
        AlipayFinancialnetAuthEcsignErrorQueryModel model = new AlipayFinancialnetAuthEcsignErrorQueryModel();

        model.setLogonId("15684748629");
        model.setPartnerId("2012020051500000100000066024");
        model.setSignProductId("PD_STANDARD_CMD");

        request.setBizModel(model);
//        request.putOtherTextParam("ws_service_url", "11.158.43.91:12200");
//        request.putOtherTextParam("new_error_code", "true");

        AlipayFinancialnetAuthEcsignErrorQueryResponse response = alipayClient.execute(request);
        System.out.println(JSON.toJSONString(response.getParams()));

        if (response.isSuccess()) {
            System.out.println(response.getBody() + ":" + JSON.toJSONString(response));
        } else {
            System.out.println(response.getSubCode() + ":" + response.getSubMsg() + ":" + JSON.toJSONString(response));
        }

        return null;
    }

}
