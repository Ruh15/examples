package com.example.alipay.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class HttpClientUtil {
	public static final String SUCC = "success";
	public static final String FAIL = "fail";

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	public static CookieStore cookieStore = null;

	/*******************************************************
	 * 打印返回结果,并返回给调用者
	 */
	private static String printResponse(HttpResponse httpResponse) throws Exception {
		String rtnStr = "error";
		logger.debug("---------printResponse---------");
		// 获取响应消息实体
		HttpEntity entity = httpResponse.getEntity();
		// 响应状态
//		logger.debug("status: {} ", httpResponse.getStatusLine());
//		logger.debug("headers:");
		HeaderIterator iterator = httpResponse.headerIterator();
//		while (iterator.hasNext()) {
//			logger.debug("\t" + iterator.next());
//		}
		Header[] headers = httpResponse.getHeaders("Cookie");

		for (Header header : headers) {
			logger.debug("\t {} : {} ", header.getName(), header.getValue());
		}
		// 判断响应实体是否为空
		if (httpResponse.getStatusLine().getStatusCode() == 200 && entity != null) {
			String responseString = EntityUtils.toString(entity, "UTF-8");
//			logger.debug("response length: {}" , responseString.length());
//			logger.debug("response content: {}" , responseString.replace("\r\n", ""));
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
					public boolean isTrusted(X509Certificate[] arg0, String arg1)
							throws CertificateException {
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
			if (StringUtils.isNotBlank(cookieValue)) {
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
		return ip;
	}

	/**
	 * 获取请求body体
	 * @param request
	 * @return
	 */
	public static String getBodyString(ServletRequest request) {
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = null;
		BufferedReader reader = null;
		try {
			inputStream = request.getInputStream();
			reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	/*  ----------------------------------------------------分界线---------------------------------------------------------------*/


	private static final OkHttpClient client= new OkHttpClient.Builder()
			.connectionPool(new ConnectionPool(500, 5, TimeUnit.MINUTES))
			.connectTimeout(10000, TimeUnit.MILLISECONDS)
			.readTimeout(60000, TimeUnit.MILLISECONDS)
			.build();
	private static final OkHttpClient clientHttps = new OkHttpClient.Builder() .sslSocketFactory(createSSLSocketFactory())
			.hostnameVerifier(new TrustAllHostnameVerifier())
			.connectionPool(new ConnectionPool(500, 5, TimeUnit.MINUTES))
			.connectTimeout(10000, TimeUnit.MILLISECONDS)
			.readTimeout(60000, TimeUnit.MILLISECONDS)
			.build();
	public static String postWithSoapMsg(String soapXML,String url) throws Exception{
		String result="";
		MediaType mediaType = MediaType.parse("application/xml;charset=utf-8");
		RequestBody body = RequestBody.create(mediaType,soapXML);
		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.addHeader("content-type", "application/xml;charset=utf-8")
				.build();
		try(Response response = client.newCall(request).execute()){
			result=new String(response.body().bytes());
		}catch(Exception e){
			throw e;
		}
		return result;
	}

	public static String doGetUseOkHttp(String url, String clientId, String clientSecret, boolean useHttps) throws IOException {

		String param = url.substring(url.indexOf("?") + 1);

		Request request = new Request.Builder().url(url).get()
				.addHeader("Content-Type", "application/json;charset=utf-8") //Content-Type
				.addHeader("X-SPDB-Client-ID", clientId)         //Client-ID
				.addHeader("X-SPDB-SIGNATURE", CryptoEncrypt.signBytes(param.getBytes(), clientSecret.getBytes()))        //签名结果
//				.addHeader("X-SPDB-SM", "true")            //使用国密
//				.addHeader("X-SPDB-Encryption", "true")    //使用全报文加密
				.build();
		Response response;
		if (useHttps){
			response=clientHttps.newCall(request).execute();
		}else {
			response = client.newCall(request).execute();
		}
		String resBody = new String(response.body().bytes(), "UTF-8");
		return resBody;
	}

	public static JSONObject postWithFile(Map<String, Object> param, String url, String clientId, String clientSecret, String fileMd5){

		JSONObject result = new JSONObject();

		MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");
		MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

		for (Map.Entry<String, Object> obj : param.entrySet()) {
			String key = obj.getKey();
			if (key.startsWith("content")) {
				builder.addFormDataPart(key, key + ".jpg", RequestBody.create(MEDIA_TYPE_JPG, (byte[])obj.getValue()));
			} else {
				builder.addFormDataPart(key, (String) obj.getValue());
			}
		}
		RequestBody body = builder.build();

		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.addHeader("content-type", "multipart/form-data")
				.addHeader("X-SPDB-Client-ID", clientId)
				.addHeader("X-SPDB-FilesMD5", fileMd5)
				.addHeader("X-SPDB-SIGNATURE", CryptoEncrypt.signBytes(fileMd5.getBytes(), clientSecret.getBytes())).build();// TODO: 2019/7/30 是否包含文件的签名不同？
		Response response=null;
		try{
			if (url.indexOf("https") > -1){
				response=clientHttps.newCall(request).execute();
			}else {
				response = client.newCall(request).execute();
			}
			if (response.code()== 200){
				String responseBody=new String(response.body().bytes(), "UTF-8");
				result.put("success", responseBody);
			}else if (response.code() == 502){
				String responseBody=new String(response.body().bytes(), "UTF-8");
				result.put("502", responseBody);
			}else {
				response=response;
				System.out.println(response);
				if (response.body()!=null){
					String errorBody=new String(response.body().bytes(), "UTF-8");
					result.put("error",errorBody);
				} else {
					result.put("error",response.message());
				}
			}
			return result;
		}catch(IOException e){
			e.printStackTrace();
			result.put("error", e.getMessage());
			return result;
		}
	}

	public static JSONObject postWithJsonMsg(String content, String url, String clientId, String clientSecret){

		JSONObject result = new JSONObject();
		MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
		RequestBody body = RequestBody.create(mediaType, content);
		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.addHeader("content-type", "application/json;charset=utf-8")
				.addHeader("X-SPDB-Client-Id", clientId)
				.addHeader("X-SPDB-SIGNATURE", CryptoEncrypt.signBytes(content.getBytes(), clientSecret.getBytes())).build();// TODO: 2019/7/30 是否包含文件的签名不同？
		Response response=null;
		try{
			if (url.indexOf("https") > -1){
				response=clientHttps.newCall(request).execute();
			}else {
				response = client.newCall(request).execute();
			}
			if (response.code()== 200){
				String responseBody=new String(response.body().bytes(), "UTF-8");
				result.put("success", responseBody);
			}else if (response.code() == 502){
				String responseBody=new String(response.body().bytes(), "UTF-8");
				result.put("502", responseBody);
			}else {
				response=response;
				System.out.println(response);
				if (response.body()!=null){
					String errorBody=new String(response.body().bytes(), "UTF-8");
					result.put("error",errorBody);
				} else {
					result.put("error",response.message());
				}
			}
			return result;
		}catch(IOException e){
			e.printStackTrace();
			result.put("error", e.getMessage());
			return result;
		}
	}

	private static SSLSocketFactory createSSLSocketFactory() {
		SSLSocketFactory ssfFactory = null;
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null,  new TrustManager[] {
					new TrustAllCerts()
			}, new SecureRandom());
			ssfFactory = sc.getSocketFactory();
		} catch (Exception e) {

		}
		return ssfFactory;
	}

	private static class TrustAllCerts implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

		}
		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}
	}
	private static class TrustAllHostnameVerifier implements HostnameVerifier {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}


	/**
	 * 获取请求真实ip地址
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ipAddress = null;
		try {
			ipAddress = request.getHeader("x-forwarded-for");
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				if (ipAddress.equals("127.0.0.1")) {
					// 根据网卡取本机配置的IP
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					ipAddress = inet.getHostAddress();
				}
			}
			// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
			if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
				// = 15
				if (ipAddress.indexOf(",") > 0) {
					ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ipAddress="";
		}
		return ipAddress;
	}
}
