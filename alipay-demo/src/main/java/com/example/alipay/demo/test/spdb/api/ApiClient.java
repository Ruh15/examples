package com.example.alipay.demo.test.spdb.api;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class ApiClient {

	private OkHttpClient getClient() {
		SSLContext sslContext = null;
		X509TrustManager trustMgr = null;
		try {
			sslContext = SSLContext.getInstance("TLS");
			trustMgr = new X509TrustManager() {

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}

				@Override
				public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}

				@Override
				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}
			};
			sslContext.init(null, new TrustManager[] { trustMgr }, new SecureRandom());
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			e.printStackTrace();
		}

		HostnameVerifier verifier = new HostnameVerifier() {

			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
		};

		return new OkHttpClient.Builder().sslSocketFactory(sslContext.getSocketFactory(), trustMgr)
				.hostnameVerifier(verifier).connectTimeout(5, TimeUnit.SECONDS).writeTimeout(1, TimeUnit.SECONDS)
				.readTimeout(20, TimeUnit.SECONDS).build();
	}

	public void sendFormData(String url, String reqParamsFile, String clientId, String sec) {
		System.out.println("------------------------------------------------");
		System.out.println("ApiTest \nURL:\n" + url);
		Properties props = new Properties();
		try (FileInputStream is = new FileInputStream(reqParamsFile)) {
			props.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		MediaType MEDIA_TYPE_JPG = null;
		MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

		for (Entry<Object, Object> obj : props.entrySet()) {
			String key = (String) obj.getKey();
			String value = (String) obj.getValue();
			if ("@mediaType".equals(key)) {
				MEDIA_TYPE_JPG = MediaType.parse(value);
			} else {
				if (value.contains("@@")) {
					String filePath = value.substring(2);
					String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
					builder.addFormDataPart(key, fileName, RequestBody.create(MEDIA_TYPE_JPG, new File(filePath)));
				} else {
					builder.addFormDataPart(key, value);
				}
			}
		}

		RequestBody requstBody = builder.build();

		byte[] contentBytes = null;
		try (ByteArrayOutputStream os = new ByteArrayOutputStream(); Buffer buffer = new Buffer();) {
			requstBody.writeTo(buffer);
			buffer.copyTo(os);
			contentBytes = os.toByteArray();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

//		String signature = SPDBSMSignature.sign(sec, contentBytes);
		String signature = null;
		Request request = new Request.Builder().url(url).post(requstBody)
				.addHeader("content-type", "multipart/form-data").addHeader("X-SPDB-Client-ID", clientId)
				.addHeader("X-SPDB-SIGNATURE", signature).addHeader("X-SPDB-SM", "true").build();

		try {
			OkHttpClient client = getClient();
			Response response = client.newCall(request).execute();
			System.out.println("Rsp:\n" + new String(response.body().bytes(), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void printErrMsg() {
		System.err.println("Usage : java -jar apitest.jar [method] [url] [clientId] [clientSecret] [reqFile]");
		System.err.println("           -method : 		jsonget|jsonpost|formdata");
		System.err.println("           -url : 		URL地址");
		System.err.println("           -clientId : 	 Client-ID");
		System.err.println("           -clientSecret : Client-Secret");
		System.err.println("           -reqFile : JsonPost请求文件|FormData请求文件");
		System.exit(1);
	}

	public static void main(String[] args) throws Exception {

		if (args.length < 3) {
			printErrMsg();
		}

		String method = args[0];
		String url = args[1];
		String clientId = args[2];

		ApiClient test = new ApiClient();
		if ("formdata".equals(method)) {
			if (args.length < 5) {
				printErrMsg();
			}
			String sec = args[3];
			String reqFile = args[4];
			test.sendFormData(url, reqFile, clientId, sec);
		} else {
			printErrMsg();
		}
	}

}
