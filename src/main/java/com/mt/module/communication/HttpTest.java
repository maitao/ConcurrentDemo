package com.mt.module.communication;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpTest {

	// 使用JDK实现http通信
	public void get() throws Exception {

		URL url = new URL("http://127.0.0.1/http/demo.do?name=Jack&age=10");
		URLConnection urlConnection = url.openConnection(); // 打开连接
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8")); // 获取输入流
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}

		System.out.println(sb.toString());
	}

	public void post() throws IOException {

		URL url = new URL("http://127.0.0.1/http/demo.do");
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true); // 设置该连接是可以输出的
		httpURLConnection.setRequestMethod("POST"); // 设置请求方式
		httpURLConnection.setRequestProperty("charset", "utf-8");

		PrintWriter pw = new PrintWriter(new BufferedOutputStream(httpURLConnection.getOutputStream()));
		pw.write("name=welcome"); // 向连接中输出数据（相当于发送数据给服务器）
		pw.write("&age=14");
		pw.flush();
		pw.close();

		BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) { // 读取数据
			sb.append(line + "\n");
		}

		System.out.println(sb.toString());
	}

	// 使用httpclient进行http通信
	public void httpclientGet() throws Exception {

		// 创建HttpClient对象
		HttpClient client = HttpClients.createDefault();

		// 创建GET请求（在构造器中传入URL字符串即可）
		HttpGet get = new HttpGet("http://127.0.0.1/http/demo.do?name=admin&age=40");

		// 调用HttpClient对象的execute方法获得响应
		HttpResponse response = client.execute(get);

		// 调用HttpResponse对象的getEntity方法得到响应实体
		HttpEntity httpEntity = response.getEntity();

		// 使用EntityUtils工具类得到响应的字符串表示
		String result = EntityUtils.toString(httpEntity, "utf-8");
		System.out.println(result);
	}

	public void httpclientPost() throws Exception {

		// 创建HttpClient对象
		HttpClient client = HttpClients.createDefault();

		// 创建POST请求
		HttpPost post = new HttpPost("http://127.0.0.1/http/demo.do");

		// 创建一个List容器，用于存放基本键值对（基本键值对即：参数名-参数值）
		List<BasicNameValuePair> parameters = new ArrayList();
		parameters.add(new BasicNameValuePair("name", "张三"));
		parameters.add(new BasicNameValuePair("age", "25"));

		// 向POST请求中添加消息实体
		post.setEntity(new UrlEncodedFormEntity(parameters, "utf-8"));

		// 得到响应并转化成字符串
		HttpResponse response = client.execute(post);
		HttpEntity httpEntity = response.getEntity();
		String result = EntityUtils.toString(httpEntity, "utf-8");
		System.out.println(result);
	}

	public String httpsRequest(String requestUrl, String requestMethod, String output)
			throws NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException, MalformedURLException,
			IOException, ProtocolException, UnsupportedEncodingException {
		URL url = new URL(requestUrl);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod(requestMethod);
		if (null != output) {
			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(output.getBytes("UTF-8"));
			outputStream.close();
		}
		// 从输入流读取返回内容
		InputStream inputStream = connection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String str = null;
		StringBuffer buffer = new StringBuffer();
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
		}
		bufferedReader.close();
		inputStreamReader.close();
		inputStream.close();
		inputStream = null;
		connection.disconnect();
		return buffer.toString();
	}

	public static void main(String[] args) throws Exception {
		// 创建HttpClient对象
		HttpClient client = HttpClients.createDefault();

		// 创建POST请求
		HttpPost post = new HttpPost("http://localhost:9080/hd/joke/contribute.mt");

		// 创建一个List容器，用于存放基本键值对（基本键值对即：参数名-参数值）
		List<BasicNameValuePair> parameters = new ArrayList();
		parameters.add(new BasicNameValuePair("sentence", "1"));
		parameters.add(new BasicNameValuePair("imgpath", "25"));

		// 向POST请求中添加消息实体
		post.setEntity(new UrlEncodedFormEntity(parameters, "utf-8"));

		// 得到响应并转化成字符串
		HttpResponse response = client.execute(post);
		HttpEntity httpEntity = response.getEntity();
		String result = EntityUtils.toString(httpEntity, "utf-8");
		System.out.println(result);

	}

}
