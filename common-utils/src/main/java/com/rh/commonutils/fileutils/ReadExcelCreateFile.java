package com.rh.commonutils.fileutils;

import com.alibaba.fastjson.JSON;
import com.rh.commonutils.ExcelUtil;

import java.util.List;

/**
 * content:
 *
 * @author Ruh
 * @time 2019/10/28
 **/
public class ReadExcelCreateFile {
    public static void main(String[] args) throws Exception {
        String path = "E:\\ruhui\\working\\文档\\需求\\浦发对接\\11月最新处理\\";
        List<List<String>> lists = ExcelUtil.readXlsx(path.concat("浦发订单合同下载地址.xlsx"), path);

//        String path = "E:\\ruhui\\working\\文档\\需求\\浦发对接\\文件拷贝\\错误文件详情\\";
//        List<List<String>> lists = ExcelUtil.readXlsx("E:\\ruhui\\working\\文档\\需求\\浦发对接\\文件拷贝\\错误文件详情\\浦发订单错误合同下载地址.xlsx", path);
        System.out.println(JSON.toJSONString(lists));
    }
}
