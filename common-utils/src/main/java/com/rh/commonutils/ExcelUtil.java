package com.rh.commonutils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
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

    /**
     *
     * @Title: readXlsx
     * @Description: 处理Xlsx文件
     * @param @param path
     * @param @return
     * @param @throws Exception    设定文件
     * @return List<List<String>>    返回类型
     * @throws
     */
    public static List<List<String>> readXlsx(String path, String dirPath) throws Exception {
        InputStream is = new FileInputStream(path);

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        List<List<String>> result = new ArrayList<>();
        // 循环每一页，并处理当前循环页
        for (Sheet sheet : xssfWorkbook) {
            XSSFSheet xssfSheet = (XSSFSheet) sheet;
            if (xssfSheet == null) {
                continue;
            }
            // 处理当前页，循环读取每一行
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                int minColIx = xssfRow.getFirstCellNum();
                int maxColIx = xssfRow.getLastCellNum();
                List<String> rowList = new ArrayList<>();
                File file = null;
                for (int colIx = minColIx; colIx < maxColIx; colIx++) {
                    XSSFCell cell = xssfRow.getCell(colIx);
                    if (cell == null) {
                        continue;
                    }
                    if (colIx == 0) {
                        file = new File(dirPath + cell.getStringCellValue());
                        if (file.exists()) {
                            continue;
                        }

                    }
                    if (colIx == 1) {
                        XSSFCell cell2 = xssfRow.getCell(colIx + 1);
                        downloadFile(cell.getStringCellValue(), file.getAbsolutePath(), cell2.getStringCellValue());
                    }
                    rowList.add(cell.toString());
                }
                result.add(rowList);
            }
        }
        return result;
    }


    /**
     * 说明：根据指定URL将文件下载到指定目标位置
     *
     * @param urlPath
     *            下载路径
     * @param downloadDir
     *            文件存放目录
     * @return 返回下载文件
     */
    @SuppressWarnings("finally")
    public static File downloadFile(String urlPath, String downloadDir, String fileName) {
        File file = null;
        try {
            // 统一资源
            URL url = new URL(urlPath);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            //设置超时
            httpURLConnection.setConnectTimeout(1000*5);
            //设置请求方式，默认是GET
            // TODO
//            httpURLConnection.setRequestMethod("POST");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();
            // 文件大小
            int fileLength = httpURLConnection.getContentLength();

            // 控制台打印文件大小
            System.out.println("您要下载的文件大小为:" + fileLength / 1024 + "KB");
//            System.out.println("您要下载的文件大小为:" + fileLength / (1024 * 1024) + "MB");

            // 建立链接从请求中获取数据
//            URLConnection con = url.openConnection();
            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());
            // 指定文件名称(有需求可以自定义)
            // 指定存放位置(有需求可以自定义)
            String path = downloadDir + File.separatorChar + fileName + ".pdf";
            file = new File(path);
            // 校验文件夹目录是否存在，不存在就创建一个目录
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            OutputStream out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[2048];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
                // 控制台打印文件下载的百分比情况
//                System.out.println("下载了-------> " + len * 100 / fileLength + "%\n");
            }
            // 关闭资源
            bin.close();
            out.close();
            System.out.println("文件下载成功！");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("文件下载失败！");
        } finally {
            return file;
        }

    }

}
