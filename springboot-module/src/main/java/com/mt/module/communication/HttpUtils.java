package com.mt.module.communication;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * http工具类
 * 
 * @author mt 2016-08-25
 *
 */
public final class HttpUtils {
	private Logger logger = Logger.getLogger(HttpUtils.class);

	private String DEFAULT_ENCODE = "utf-8";
	private String IP_ADDRESS;
	private int PORT;
	private String REALM = "Contacts Realm via Digest Authentication";
	private String USERNAME;
	private String PASSWORD;
	private static final int SOCKET_TIMEOUT = 2000;
	private static final int CONNECT_TIMEOUT = 12000;

	public HttpUtils(String ip, int port, String realm, String uername, String password) {
		IP_ADDRESS = ip;
		PORT = port;
		REALM = realm;
		USERNAME = uername;
		PASSWORD = password;
	}

	public HttpUtils() {

	}

	/**
	 * 发送邮件
	 * 
	 * @param url
	 * @param entity
	 * @param reqHeader
	 * @return
	 */
	public String httpPostEMail(String url, MultipartEntityBuilder entity, Map<String, String> reqHeader) {
		HttpPost httpPost = sethttpPostHeader(url, reqHeader);
		httpPost.setEntity(entity.build());
		return result(httpPost);
	}

	/**
	 * 发送json
	 * 
	 * @param url
	 * @param json
	 * @param reqHeader
	 * @return
	 */
	public String httpPostJson(String url, String json, Map<String, String> reqHeader) {
		HttpPost httpPost = sethttpPostHeader(url, reqHeader);
		if (json != null) {
			StringEntity myEntity = new StringEntity(json, DEFAULT_ENCODE);
			myEntity.setContentType("application/json");
			httpPost.setEntity(myEntity);
		}
		return result(httpPost);
	}

	/**
	 * 发送xml
	 * 
	 * @param url
	 * @param xml
	 * @param reqHeader
	 * @return
	 */
	public String httpPostXML(String url, String xml, Map<String, String> reqHeader) {
		HttpPost httpPost = sethttpPostHeader(url, reqHeader);
		if (xml != null) {
			StringEntity myEntity = new StringEntity(xml, DEFAULT_ENCODE);
			myEntity.setContentType("text/xml");
			httpPost.setEntity(myEntity);
		}
		return result(httpPost);
	}

	
	/**
	 * 发送普通params
	 * 
	 * @param url
	 * @param params
	 * @param reqHeader
	 * @return
	 */
	public String httpPostMap(String url, Map<String, String> params, Map<String, String> reqHeader) {
		HttpPost httpPost = sethttpPostHeader(url, reqHeader);
		if (params != null) {
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			for (String name : params.keySet()) {
				parameters.add(new BasicNameValuePair(name, params.get(name)));
			}
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(parameters, DEFAULT_ENCODE));
			} catch (UnsupportedEncodingException e) {
				logger.error("UrlEncodedFormEntity Error,encode=" + DEFAULT_ENCODE + ",parameters=" + parameters, e);
			}
		}
		return result(httpPost);
	}

	private HttpPost sethttpPostHeader(String url, Map<String, String> reqHeader) {
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT)
				.setConnectTimeout(CONNECT_TIMEOUT).build();// 设置请求和传输超时时间
		httpPost.setConfig(requestConfig);
		if (reqHeader != null) {// 设置请求头信息
			for (String name : reqHeader.keySet()) {
				httpPost.addHeader(name, reqHeader.get(name));
			}
		}
		return httpPost;
	}

	private String result(HttpUriRequest httpPost) {
		String result = "";
		try {
			result = executeHttpRequest(httpPost);// 发送请求
		} catch (Exception e) {
			e.printStackTrace();
			result = "";
		}
		return result;

	}

	private String executeHttpRequest(HttpUriRequest httpUriRequest) throws Exception {
		CloseableHttpClient client = null;
		String result = null;
		// 创建HttpClient对象
		client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		if (null != IP_ADDRESS) {
			// 用户密码认证
			HttpHost targetHost = new HttpHost(IP_ADDRESS, PORT, "http");
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(new AuthScope(IP_ADDRESS, PORT, REALM),
					new UsernamePasswordCredentials(USERNAME, PASSWORD));
			// Create AuthCache instance
			AuthCache authCache = new BasicAuthCache();
			// Generate BASIC scheme object and add it to the local auth cache
			BasicScheme basicAuth = new BasicScheme();
			authCache.put(targetHost, basicAuth);
			// Add AuthCache to the execution context
			HttpClientContext context = HttpClientContext.create();
			context.setCredentialsProvider(credsProvider);
			context.setAuthCache(authCache);
			response = client.execute(httpUriRequest, context);// 获得返回结果
		} else {
			response = client.execute(httpUriRequest);// 获得返回结果
		}
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {// 如果成功
			result = EntityUtils.toString(response.getEntity(), DEFAULT_ENCODE);
		} else {// 如果失败
			StringBuffer errorMsg = new StringBuffer();
			errorMsg.append("httpStatus:");
			errorMsg.append(response.getStatusLine().getStatusCode());
			errorMsg.append(response.getStatusLine().getReasonPhrase());
			errorMsg.append(", Header: ");
			Header[] headers = response.getAllHeaders();
			for (Header header : headers) {
				errorMsg.append(header.getName());
				errorMsg.append(":");
				errorMsg.append(header.getValue());
			}
			logger.error("HttpResonse Error:" + errorMsg);
		}
		return result;
	}

}
