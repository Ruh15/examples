package com.rh.commonutils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kl on 2017/4/17.
 * Content :excel封装poi接口服务
 */
@Service
public class ExcelUtil {

    public static HSSFWorkbook exportEmailData(List<Map> data){
        List<Map> excelData = new ArrayList<>();
        Map mapVal;
        for(Map map: data){
            mapVal = new LinkedHashMap();
            mapVal.put("A", map.get("a")); // 此处 key 根据自己需要变更
            mapVal.put("B", map.get("b"));
            mapVal.put("C", map.get("c"));
            excelData.add(mapVal);
        }
        return assembleHSSFWorkBookData(excelData,"sheetName", "title");
    }

    /**
     * 组装excel数据
     * @param data 数据源
     * @param sheetName 工作区名字
     * @param title 首行合并单元格 标题
     * @return
     */
    public static HSSFWorkbook assembleHSSFWorkBookData(List<Map> data, String sheetName, String title) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式

        int colTitlesSize =data.get(0).size();//共多少列
        HSSFRow firstRow = sheet.createRow(0);
        HSSFCell firstCell = firstRow.createCell(0, CellType.STRING);
        firstCell.setCellValue(title);//设置文件大标题
        firstCell.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, colTitlesSize-1));
        // 写入实体数据
        for (Map mapData : data) {
            int lastRowNum = sheet.getLastRowNum();
            HSSFRow row = null;

            // 第四步，创建单元格，并设置值
            int initCellNum = 0;
            if(lastRowNum==0){
                row= sheet.createRow(lastRowNum + 1);
                for (Object key : mapData.keySet()) {
                    HSSFCell hssfCell= row.createCell(initCellNum);
                    hssfCell.setCellValue(key.toString());
                    hssfCell.setCellStyle(style);
                    initCellNum++;
                }
            }
            initCellNum = 0;
            row= sheet.createRow(sheet.getLastRowNum() + 1);
            for (Object key : mapData.keySet()) {
                String val = mapData.get(key) == null ? "" : mapData.get(key).toString();
                HSSFCell hssfCell= row.createCell(initCellNum);
                hssfCell.setCellValue(val);
                hssfCell.setCellStyle(style);
                initCellNum++;
            }
            for (int i=0;i<colTitlesSize;i++){
                sheet.setColumnWidth(i,4000);
            }
        }
        return workbook;
    }

    public static void ecportExcelFile(HSSFWorkbook wb, HttpServletResponse response, String fileName) {
        response.setContentType("application/vnd.ms-excel");
        String filename = fileName + ".xls";
        OutputStream stream = null;
        try {
            String agent = response.getHeader("USER-AGENT");
            if (null != agent && -1 != agent.indexOf("MSIE")) {
                filename = URLEncoder.encode(filename, "utf-8");
            } else {
                filename = new String(filename.getBytes("utf-8"), "iso8859-1");
            }
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            stream = response.getOutputStream();
            wb.write(stream);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.flush();
                    stream.close();
                    response.flushBuffer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
