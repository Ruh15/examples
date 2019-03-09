package com.rh.commonutils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.SocketTimeoutException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.util.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * content:
 *
 * @author Ruh
 * @time 2019/3/4
 **/
public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    public static CookieStore cookieStore = null;

    public static final String SUCC = "success";

    public static final String FAIL = "fail";

    /*******************************************************
     * 打印返回结果,并返回给调用者
     */
    private static String printResponse(HttpResponse httpResponse) throws Exception {
        String rtnStr = "error";
        logger.debug("---------printResponse---------");
        // 获取响应消息实体
        HttpEntity entity = httpResponse.getEntity();
        // 响应状态
        logger.debug("status: {} ", httpResponse.getStatusLine());
        logger.debug("headers:");
        HeaderIterator iterator = httpResponse.headerIterator();
        while (iterator.hasNext()) {
            logger.debug("\t" + iterator.next());
        }
        Header[] headers = httpResponse.getHeaders("Cookie");

        for (Header header : headers) {
            logger.debug("\t {} : {} ", header.getName(), header.getValue());
        }
        // 判断响应实体是否为空
        if (httpResponse.getStatusLine().getStatusCode() == 200 && entity != null) {
            String responseString = EntityUtils.toString(entity, "UTF-8");
            logger.debug("response length: {}" , responseString.length());
            logger.debug("response content: {}" , responseString.replace("\r\n", ""));
            rtnStr = responseString.replace("\r\n", "");
        }
        EntityUtils.consumeQuietly(entity);
        return rtnStr;
    }

    /*******************************************************
     * 设置一个请求如 : 超时等
     */
    public static RequestConfig setRequestConfig(int socketTimeout, int connectTimeOut, int connectionRequestTimeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeOut)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setExpectContinueEnabled(false).build();
        return requestConfig;
    }

    private static RequestConfig getRequestConfig() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(60000)
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .setExpectContinueEnabled(false).build();
        return requestConfig;
    }

    /*******************************************************
     * 获得一个httpClient
     */
    private static CloseableHttpClient getHttpClient(boolean useHttps, CookieStore cookie) {
        CloseableHttpClient client = null;
        try {
            HttpClientBuilder builder = HttpClients.custom();
            if (cookie != null) {
                builder = builder.setDefaultCookieStore(cookie);
            }
            if (useHttps) {
                SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {// 信任所有
                    @Override
                    public boolean isTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                            throws java.security.cert.CertificateException {
                        return true;
                    }
                }).build();
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
                builder = builder.setSSLSocketFactory(sslsf);
            }
            client = builder.build();
        } catch (Exception e) {
            logger.info(e.getMessage(),e);
        }
        return client;
    }

    /*******************************************************
     * 构建表单请求参数
     */
    private static List<NameValuePair> getParam(Map parameterMap) {
        List<NameValuePair> param = new ArrayList<NameValuePair>();
        Iterator it = parameterMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry parmEntry = (Map.Entry) it.next();
            param.add(new BasicNameValuePair((String) parmEntry.getKey(), parmEntry.getValue().toString()));
        }
        return param;
    }

    /*******************************************************
     * 创建cookieStore
     *
     * @param httpResponse
     */
    public static CookieStore setCookieStore(String domain, String path, String minutes, HttpResponse httpResponse) {
        try {
            logger.debug("---------setCookieStore---------");
            cookieStore = new BasicCookieStore();
            // JSESSIONID
            String setCookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
            String JSESSIONID = setCookie.substring("JSESSIONID=".length(), setCookie.indexOf(";"));
            logger.debug("JSESSIONID:" + JSESSIONID);
            // 新建一个Cookie
            BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", JSESSIONID);
            cookie.setVersion(0);
            cookie.setDomain(domain);
            cookie.setPath(path);
            Calendar dar = Calendar.getInstance();
            dar.add(Calendar.MINUTE, Integer.parseInt(minutes));
            cookie.setExpiryDate(dar.getTime());
            cookieStore.addCookie(cookie);

        } catch (Exception e) {
            logger.info(e.getMessage(),e);
        }
        return cookieStore;
    }

    /*******************************************************
     * ajax POST请求
     */
    public static Map<String,Object> doPostResCookie(String url,String json){
        return doPostResCookie(url,json,false,null,null);
    }
    public static Map<String,Object> doPostResCookie(String url,String json,boolean useHttps, CookieStore cookie, RequestConfig requestConfig) {
        CloseableHttpClient client = null;
        Map<String,Object> map = Maps.newHashMap();
        String strReturn = "";
        String ce = null;
        try {
            logger.debug(url);
            if(useHttps == false) useHttps = StringUtils.startsWithIgnoreCase(url, "https") ? true:false;
            client = getHttpClient(useHttps, cookie);
            HttpPost httpPost = new HttpPost(StringUtils.trim(url));
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setConfig((null==requestConfig) ? getRequestConfig() : requestConfig);
            StringEntity postEntity = new StringEntity(json, "UTF-8");
            httpPost.setEntity(postEntity);
            HttpResponse res = client.execute(httpPost);
            ce = res.getFirstHeader("Set-Cookie").getValue();
            strReturn = printResponse(res);
        } catch (SocketTimeoutException e) {
            logger.info(e.getMessage(),e);
            strReturn = "Read timed out";
        }  catch (Exception e) {
            logger.info(e.getMessage(),e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                logger.info(e.getMessage(),e);
            }
        }

        map.put("result",strReturn);
        map.put("cookie",ce);
        return map;
    }
    public static String doPost(String url,String json,String cookieValue){
        return doPost(url,json,cookieValue,false,null,null);
    }

    public static String doPost(String url, String json, boolean useHttps, Map<String, String> headers) {
        CloseableHttpClient client = null;
        String strReturn = "";
        try {
            logger.debug(url);
            if(useHttps == false) useHttps =StringUtils.startsWithIgnoreCase(url, "https") ? true:false;
            client = getHttpClient(useHttps, null);
            HttpPost httpPost = new HttpPost(StringUtils.trim(url));
            httpPost.setHeader("Content-type", "application/json");
            // 设置自定义请求头(token。。。)
            if (headers != null) {
                for (String key : headers.keySet()) {
                    httpPost.setHeader(key, headers.get(key).toString());
                }
            }
            StringEntity postEntity = new StringEntity(json, "UTF-8");
            httpPost.setEntity(postEntity);
            HttpResponse res = client.execute(httpPost);
            strReturn = printResponse(res);
        } catch (SocketTimeoutException e) {
            logger.info(e.getMessage(),e);
            strReturn = "Read timed out";
        }  catch (Exception e) {
            logger.info(e.getMessage(),e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                logger.info(e.getMessage(),e);
            }
        }
        return strReturn;
    }
    public static String doPost(String url,String json,String cookieValue,boolean useHttps,CookieStore cookie,RequestConfig requestConfig) {
        CloseableHttpClient client = null;
        String strReturn = "";
        try {
            logger.debug(url);
            if(useHttps == false) useHttps =StringUtils.startsWithIgnoreCase(url, "https") ? true:false;
            client = getHttpClient(useHttps, cookie);
            HttpPost httpPost = new HttpPost(StringUtils.trim(url));
            httpPost.setHeader("Content-type", "application/json");
            if (org.apache.commons.lang3.StringUtils.isNotBlank(cookieValue)) {
                httpPost.setHeader("Cookie",cookieValue);
            }
            httpPost.setConfig((null==requestConfig) ? getRequestConfig() : requestConfig);
            StringEntity postEntity = new StringEntity(json, "UTF-8");
            httpPost.setEntity(postEntity);
            HttpResponse res = client.execute(httpPost);
            strReturn = printResponse(res);
        } catch (SocketTimeoutException e) {
            logger.info(e.getMessage(),e);
            strReturn = "Read timed out";
        }  catch (Exception e) {
            logger.info(e.getMessage(),e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                logger.info(e.getMessage(),e);
            }
        }
        return strReturn;
    }

    public static String doPost(String url, String json, boolean useHttps, CookieStore cookie, RequestConfig requestConfig) {
        return doPost(url,json,null,useHttps,cookie,requestConfig);
    }

    public static String doPost(String url, String json, CookieStore cookie) {
        return doPost(url, json, false, cookie, null);
    }

    public static String doPost(String url, String json) {
        return doPost(url, json, false, null, null);
    }

    public static String doPost(String url, String json, boolean useHttps) {
        return doPost(url, json, useHttps, null, null);
    }

    public static String doPost(String url, String json, RequestConfig config) {
        return doPost(url, json, false, null, config);
    }


    /*******************************************************
     * 表单 POST请求
     */

    public static String doPost(String url, Map<String, Object> params, boolean useHttps, CookieStore cookie, RequestConfig config) {
        String strReturn = "";
        CloseableHttpClient client = null;
        try {
            logger.debug(url);
            if(useHttps == false) useHttps =StringUtils.startsWithIgnoreCase(url, "https") ? true:false;
            client = getHttpClient(useHttps, cookie);
            HttpPost httpPost = new HttpPost(StringUtils.trim(url));
            httpPost.setConfig( (null == config) ? getRequestConfig() : config );
            UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(getParam(params), "UTF-8");
            httpPost.setEntity(postEntity);
            HttpResponse httpResponse1 = client.execute(httpPost);
            strReturn = printResponse(httpResponse1);
        } catch (SocketTimeoutException e) {
            logger.info(e.getMessage(),e);
            strReturn = "Read timed out";
        } catch (Exception e) {
            logger.info(e.getMessage(),e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                logger.info(e.getMessage(),e);
            }
        }
        return strReturn;
    }

    public static String doPost(String url, Map<String, Object> params, boolean useHttps, CookieStore cookie) {
        return doPost(url, params, useHttps, cookie, null);
    }

    public static String doPost(String url, Map<String, Object> params) {
        return doPost(url, params, false, null);
    }

    public static String doPost(String url, Map<String, Object> params, boolean useHttps) {
        return doPost(url, params, useHttps, null);
    }
    public static String doPost(String url, Map<String, Object> params, RequestConfig config) {
        return doPost(url, params, false, null, config);
    }

    /*****************************************************
     * Get请求
     */
    public static String doGet(String url, boolean useHttps, CookieStore cookie ,Map<String, Object> headers, RequestConfig config) {
        String strResult = "";
        CloseableHttpClient client = null;
        try {
            logger.debug(url);
            if(useHttps == false) useHttps =StringUtils.startsWithIgnoreCase(url, "https") ? true:false;
            client = getHttpClient(useHttps, cookie);
            HttpGet get = new HttpGet(StringUtils.trim(url));
            get.setConfig((null == config) ? getRequestConfig() : config);
            if (headers != null) {
                for (String key : headers.keySet()) {
                    get.setHeader(key, headers.get(key).toString());
                }
            }
            HttpResponse httpResponse = client.execute(get);

            strResult = printResponse(httpResponse);
        } catch (SocketTimeoutException e) {
            logger.info(e.getMessage(),e);
            strResult = "Read timed out";
        }  catch (Exception e) {
            logger.info(e.getMessage(),e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                logger.info(e.getMessage(),e);
            }
        }
        return strResult;
    }

    public static String doGet(String url, boolean useHttps, CookieStore cookie ,Map<String, Object> headers) {
        return doGet(url, useHttps,cookie,headers, null);
    }

    public static String doGet(String url, boolean useHttps, CookieStore cookie) {
        return doGet(url, useHttps, cookie, null);
    }

    /**
     * get请求，map自动转换请求参数,url后缀不加”？“
     * @param requestParams
     * @param baseUrl
     * @return
     */
    public static String doGet(Map<String,Object> requestParams,String baseUrl){
        StringBuilder url=new StringBuilder();
        for (Map.Entry<String,Object> entry : requestParams.entrySet()) {
            url.append(entry.getKey() + "=" + entry.getValue()).append("&");
        }
        return doGet(baseUrl.concat("?")+StringUtils.substringBeforeLast(url.toString(),"&"));
    }
    public static String doGet(String url) {
        return doGet(url, false, null);
    }

    public static String doGet(String url, boolean useHttps) {
        return doGet(url, useHttps, null);
    }

    public static String doGet(String url, Map<String, Object> headers) {
        return doGet(url, false, null, headers);
    }

    public static String doGet(String url, RequestConfig config) {
        return doGet(url, false, null, null, config);
    }

    /*****************************************************
     * upload请求
     */
    public static String doUpload(String url, File[] files, String jsonParam, boolean useHttps, CookieStore cookie) {
        String strResult = "";
        CloseableHttpClient client = null;
        try {
            logger.debug(url);
            if(useHttps == false) useHttps =StringUtils.startsWithIgnoreCase(url, "https") ? true:false;
            client = getHttpClient(useHttps, cookie);
            HttpPost httppost = new HttpPost(StringUtils.trim(url));
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            for (File file : files) {
                // 把文件转换成流对象FileBody
                FileBody fileBody = new FileBody(file);
                builder = builder.addPart("file" + file.getName(), fileBody);
            }
            StringBody stringBody = new StringBody(jsonParam, ContentType.TEXT_PLAIN);
            builder = builder.addPart("fileDesc", stringBody);//上传文件的描述参数,如加密的KEY,合作方编号等信息
            builder = builder.setCharset(CharsetUtils.get("UTF-8"));
            HttpEntity reqEntity = builder.build();
            httppost.setEntity(reqEntity);
            // 发起请求 并返回请求的响应
            HttpResponse response = client.execute(httppost);
            strResult = printResponse(response);
        } catch (SocketTimeoutException e) {
            logger.info(e.getMessage(),e);
            strResult = "Read timed out";
        }  catch (Exception e) {
            logger.info(e.getMessage(),e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                logger.info(e.getMessage(),e);
            }
        }
        return strResult;
    }

    /**
     * 上传附件
     * @param url
     *      请求地址
     * @param files
     *      文件信息
     *      {
     *          fileField1: File,
     *          fileField2: File
     *      }
     * @param jsonParam
     *      {
     *          normalField1: "",
     *          normalField2: ""
     *      }
     * @param useHttps
     *      是否使用https
     * @return
     */
    public static String doUpload(String url, Map<String,File> files, JSONObject jsonParam, boolean useHttps) {
        String strResult = "";
        CloseableHttpClient client = null;
        try {
            logger.debug(url);
            if(useHttps == false) useHttps =StringUtils.startsWithIgnoreCase(url, "https") ? true:false;
            client = getHttpClient(useHttps, null);
            HttpPost httppost = new HttpPost(StringUtils.trim(url));
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            Set<Map.Entry<String,File>> entries = files.entrySet();
            for (Map.Entry<String,File> entry : entries) {
                // 把文件转换成流对象FileBody
                File file = entry.getValue();
                String fieldName = entry.getKey();
                FileBody fileBody = new FileBody(file);
                builder = builder.addPart(fieldName, fileBody);
            }
            if (null != jsonParam && jsonParam.size() > 0) {
                // 非文件字段
                Set<Map.Entry<String,Object>> paramEntries = jsonParam.entrySet();
                for (Map.Entry<String, Object> entry : paramEntries) {
                    String fieldName = entry.getKey();
                    Object body = entry.getValue();
                    StringBody stringBody = new StringBody(String.valueOf(null == body ? "" : body),
                            ContentType.TEXT_PLAIN);
                    builder = builder.addPart(fieldName, stringBody);
                }
            }
            builder = builder.setCharset(CharsetUtils.get("UTF-8"));
            HttpEntity reqEntity = builder.build();
            httppost.setEntity(reqEntity);
            // 发起请求 并返回请求的响应
            HttpResponse response = client.execute(httppost);
            strResult = printResponse(response);
        } catch (SocketTimeoutException e) {
            logger.info(e.getMessage(),e);
            strResult = "Read timed out";
        }  catch (Exception e) {
            logger.info(e.getMessage(),e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                logger.info(e.getMessage(),e);
            }
        }
        return strResult;
    }

    /**
     * post文件流及参数
     * @param url
     * @param multipartRequest
     * @param param
     * @param useHttps
     * @param cookie
     * @return
     */
    public static String doUpload(String url, MultipartHttpServletRequest multipartRequest, Map<String,String> param, boolean useHttps, CookieStore cookie) {
        String strResult = "";
        CloseableHttpClient client = null;
        try {
            logger.debug(url);
            if(useHttps == false) useHttps =StringUtils.startsWithIgnoreCase(url, "https") ? true:false;
            client = getHttpClient(useHttps, cookie);
            HttpPost httppost = new HttpPost(StringUtils.trim(url));
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            for(Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();){
                String key = (String) it.next();
                MultipartFile mulFile = multipartRequest.getFile(key);
                InputStreamBody body = new InputStreamBody(mulFile.getInputStream(),mulFile.getOriginalFilename());
                builder.addPart(mulFile.getName(),body);
            }

            for(String key:param.keySet()){
                StringBody stringBody = new StringBody(param.get(key), ContentType.TEXT_PLAIN);
                builder = builder.addPart(key, stringBody);
            }
            builder = builder.setCharset(CharsetUtils.get("UTF-8"));
            HttpEntity reqEntity = builder.build();
            httppost.setEntity(reqEntity);
            // 发起请求 并返回请求的响应
            HttpResponse response = client.execute(httppost);
            strResult = printResponse(response);
        } catch (SocketTimeoutException e) {
            logger.info(e.getMessage(),e);
            strResult = "Read timed out";
        }  catch (Exception e) {
            logger.info(e.getMessage(),e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                logger.info(e.getMessage(),e);
            }
        }
        return strResult;
    }


    public static String doUpload(String url, File[] files, String jsonParam, boolean useHttps){
        return doUpload(url, files, jsonParam, useHttps, null);
    }

    public static String doUpload(String url, File[] files, boolean useHttps){
        return doUpload(url, files, null, useHttps, null);
    }

    public static String doUpload(String url, File[] files){
        return doUpload(url, files, null, false, null);
    }

    /*******************************************************************
     * 下载文件
     */
    public static void doDownload(String url, String destPath, boolean useHttps, CookieStore cookie) {
        // 生成一个httpclient对象
        CloseableHttpClient client = null;
        try {
            if(useHttps == false) useHttps = StringUtils.startsWithIgnoreCase(url, "https") ? true:false;
            client = getHttpClient(useHttps, cookie);

            HttpGet httpget = new HttpGet(StringUtils.trim(url));
            HttpResponse response = client.execute(httpget);
            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();

            String fileDir = StringUtils.substringAfterLast(url, "/");
            FileUtils.forceMkdir(new File(fileDir));

            File outFile = new File(destPath);
            OutputStream output = new FileOutputStream(outFile);

            IOUtils.copy(in, output);

            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(in);

        } catch (Exception e) {
            logger.info(e.getMessage(),e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                logger.info(e.getMessage(),e);
            }
        }
    }

    public static void doDownload(String url, String destPath, boolean useHttps){
        doDownload(url, destPath,useHttps,null);
    }

    public static void doDownload(String url, String destPath){
        doDownload(url, destPath, false, null);
    }



    /**
     * 获得用户IP地址
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        return ip;
    }



    /**
     * 发送http请求，url加密，亲测GET也可以调
     * @param url
     * @param paramList
     * @return
     */
    public static JSONObject request(String url, List<NameValuePair> paramList) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramList, "utf-8");
            httpPost.setEntity(formEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "UTF-8");
                return JSON.parseObject(result);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                    logger.info("httpClient释放失败");
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    logger.info("response释放失败");
                }
            }
        }
        return null;
    }
}

