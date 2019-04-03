package com.mt.module.communication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class ConcurrentTest {
	final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	static Logger logger = Logger.getLogger(ConcurrentTest.class);

	public static void main(String[] args) {
		// 模拟10000人并发请求，用户钱包
		CountDownLatch latch = new CountDownLatch(1);
		// 模拟10000个用户
		for (int i = 0; i < 1000; i++) {
			AnalogUser analogUser = new AnalogUser("user" + i, "58899dcd-46b0-4b16-82df-bdfd0d953bfb" + i, "1",
					"20.024", latch);
			analogUser.start();
		}
		// 计数器減一 所有线程释放 并发访问。
		latch.countDown();
		System.out.println("所有模拟请求结束  at " + sdf.format(new Date()));

	}

	static class AnalogUser extends Thread {
		// 模拟用户姓名
		String workerName;
		String openId;
		String openType;
		String amount;
		CountDownLatch latch;

		public AnalogUser(String workerName, String openId, String openType, String amount, CountDownLatch latch) {
			super();
			this.workerName = workerName;
			this.openId = openId;
			this.openType = openType;
			this.amount = amount;
			this.latch = latch;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				latch.await(); // 一直阻塞当前线程，直到计时器的值为0
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			post(workerName);// 发送post 请求

		}

		public void post(String workerName) {
			String result = "";
			System.out.println("模拟用户： " + workerName + " 开始发送模拟请求  at " + sdf.format(new Date()));
			try {

				// 创建HttpClient对象
				HttpClient client = HttpClients.createDefault();

				// 创建GET请求（在构造器中传入URL字符串即可）
				HttpGet get = new HttpGet("http://localhost/insertValues?userId=" + workerName);
				
				RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(8000).setConnectTimeout(8000).build();//设置请求和传输超时时间
				get.setConfig(requestConfig);
				
				// 调用HttpClient对象的execute方法获得响应
				HttpResponse response = client.execute(get);

				// 调用HttpResponse对象的getEntity方法得到响应实体
				HttpEntity httpEntity = response.getEntity();

				// 使用EntityUtils工具类得到响应的字符串表示
				result = EntityUtils.toString(httpEntity, "utf-8");

			} catch (Exception e) {
				logger.error(workerName + "::" + e.getMessage());
			}
			// sendPost("http://localhost:8080/Settlement/wallet/walleroptimisticlock.action",
			// "openId="+openId+"&openType="+openType+"&amount="+amount);
			System.out.println("操作结果：" + result);
			System.out.println("模拟用户： " + workerName + " 模拟请求结束  at " + sdf.format(new Date()));

		}
	}
}
