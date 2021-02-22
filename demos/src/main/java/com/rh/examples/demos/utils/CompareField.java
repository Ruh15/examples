package com.rh.examples.demos.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hui
 * @description 比较字段
 * @date 2020/9/30
 */
public class CompareField {
    public static void main(String[] args) {
        String str1 = "{\"carInfo\":{\"brand\":\"欧曼牌\",\"carPrice\":\"40200000\",\"carType2\":\"02\",\"driveType\":\"6x4\",\"evaluatePrice\":\"40200000\",\"horsePower\":\"560\",\"isTrailored\":\"02\",\"mortgagee\":\"凯京\",\"orgEvaluatePrice\":\"40200000\",\"truckLength\":\"7.1\",\"truckModel\":\"BJ4259SNFKB-CA\",\"truckType\":\"车头\",\"vin\":\"LRDS6PEBXLR041333\"},\"customerInfo\":{\"address\":\"河南省沈丘县刘庄店镇梁唐庄行政村南唐庄067号\",\"carShopCity\":\"310000\",\"carShopProvince\":\"310000\",\"certNo\":\"41272819810413453X\",\"certType\":\"100\",\"certValid\":\"2029-08-04\",\"customerName\":\"徐建军\",\"districtCode\":\"310000\",\"driverLicenseType\":\"B1\",\"gurantorCertNo\":\"913101130862476360\",\"gurantorName\":\"上海创远汽车销售有限公司\",\"isCorpOwner\":\"01\",\"isGuaranteed\":\"是\",\"licenseIssueDate\":\"2099-12-31\",\"marriedStatus\":\"01\",\"mobile\":\"18601673382\",\"salary\":\"21554\",\"saler\":\"王宁\",\"salerCertNo\":\"340621199511207569\",\"spouseCertNo\":\"341221198109021568\",\"spouseMobile\":\"15618686492\",\"spouseName\":\"马雪云\",\"transportType\":\"蔬菜运输\"},\"dealerInfo\":{\"affiliatedCorpName\":\"上海起格物流有限公司\",\"affiliatedLicense\":\"91310120324394229J\",\"affiliatedLpName\":\"唐市委\",\"dealerLicense\":\"913101130862476360\",\"dealerLpCertNo\":\"34220119780616643X\",\"dealerLpName\":\"张桂刚\",\"dealerName\":\"上海创远汽车销售有限公司\",\"dealerRate\":\"NEW\"},\"loanInfo\":{\"deposit\":\"1206000\",\"downPayment\":\"16080000\",\"drawdownAmount\":\"24316655\",\"interestRate\":\"0.039003\",\"loanAmount\":\"24316655\",\"loanPeriod\":\"24\",\"monthPayAmount\":\"1077729\",\"orgCreditAmt\":\"24316655\"},\"supplyDataUse\":\"LOAN\"}";
        String str2 = "{\"carInfo\":{\"brand\":\"欧曼牌\",\"carPrice\":\"25900000\",\"carType2\":\"01\",\"driveType\":\"6*2\",\"evaluatePrice\":\"25900000\",\"horsePower\":\"280\",\"isTrailored\":\"02\",\"mortgagee\":\"凯京\",\"orgEvaluatePrice\":\"25900000\",\"truckLength\":\"9.6\",\"truckModel\":\"BJ5259CCY-AA\",\"truckType\":\"仓栅式载货车\",\"vin\":\"LRDV6PEC7LT080082\"},\"customerInfo\":{\"address\":\"杨家板桥镇邦道窝村新南街9号\",\"carShopCity\":\"130200\",\"carShopProvince\":\"130000\",\"certNo\":\"130229199101126424\",\"certType\":\"100\",\"certValid\":\"2038-07-04\",\"customerName\":\"张丽红\",\"districtCode\":\"310000\",\"driverLicenseType\":\"B1\",\"gurantorCertNo\":\"91130202MA0A44HH6B\",\"gurantorName\":\"唐山市诚远汽车销售有限公司\",\"isCorpOwner\":\"01\",\"isGuaranteed\":\"是\",\"licenseIssueDate\":\"2099-12-31\",\"marriedStatus\":\"01\",\"mobile\":\"18731551136\",\"salary\":\"12828\",\"saler\":\"李东良\",\"salerCertNo\":\"130281198412020011\",\"spouseCertNo\":\"130229199008206219\",\"spouseMobile\":\"15027538123\",\"spouseName\":\"张传照\",\"transportType\":\"\"},\"dealerInfo\":{\"affiliatedCorpName\":\"张丽红\",\"affiliatedLicense\":\"130229199101126424\",\"affiliatedLpName\":\"张丽红\",\"dealerLicense\":\"91130202MA0A44HH6B\",\"dealerLpCertNo\":\"130223198208090318\",\"dealerLpName\":\"宣进财\",\"dealerName\":\"唐山市诚远汽车销售有限公司\",\"dealerRate\":\"A\"},\"loanInfo\":{\"accountFipName\":\"中国建设银行股份有限公司河北省唐山市遵化支行\",\"accountNo\":\"13050162783609966666\",\"cardHolderName\":\"唐山市诚远汽车销售有限公司\",\"deposit\":\"1143450\",\"downPayment\":\"7623000\",\"drawdownAmount\":\"22028900\",\"interestRate\":\"0.070496\",\"loanAmount\":\"22869000\",\"loanPeriod\":\"24\",\"monthPayAmount\":\"986786\",\"orgCreditAmt\":\"22869000\"}}";

        JSONObject obj1 = JSON.parseObject(str1);
        JSONObject obj2 = JSON.parseObject(str2);

        JSONObject dealerInfo = obj1.getJSONObject("dealerInfo");
        JSONObject loanInfo = obj1.getJSONObject("loanInfo");
        JSONObject customerInfo = obj1.getJSONObject("customerInfo");
        JSONObject carInfo = obj1.getJSONObject("carInfo");

        List<String> allKey1 = new ArrayList<>();
        List<String> allKey2 = new ArrayList<>();

        allKey1.addAll(dealerInfo.keySet());

        allKey1.addAll(loanInfo.keySet());

        allKey1.addAll(customerInfo.keySet());

        allKey1.addAll(carInfo.keySet());


        JSONObject dealerInfo2 = obj2.getJSONObject("dealerInfo");
        JSONObject loanInfo2 = obj2.getJSONObject("loanInfo");
        JSONObject customerInfo2 = obj2.getJSONObject("customerInfo");
        JSONObject carInfo2 = obj2.getJSONObject("carInfo");
        allKey2.addAll(dealerInfo2.keySet());

        allKey2.addAll(loanInfo2.keySet());

        allKey2.addAll(customerInfo2.keySet());

        allKey2.addAll(carInfo2.keySet());

        System.out.println(JSON.toJSONString(allKey1));
        System.out.println(JSON.toJSONString(allKey2));
        System.out.println("------------------------");
        allKey2.removeAll(allKey1);
        System.out.println(JSON.toJSONString(allKey2));
    }
}
