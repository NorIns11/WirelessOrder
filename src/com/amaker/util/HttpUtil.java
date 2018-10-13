package com.amaker.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	// 声明Base URL常量
	public static final String Base_URL="http://192.168.1.101:8888/WirelessOrder_Server/";
	
	//通过URL获得HttpGet对象
	public static HttpGet getHttpGet (String url){
		HttpGet request = new HttpGet(url);
		return request;
	}
	
	//通过URL获得HttpPost对象
	public static HttpPost getHttpPost (String url){
		HttpPost request = new HttpPost(url);
		return request;
	}
		
	//通过HttpGet获得HttpResponse对象
	public static HttpResponse getHttpResponse (HttpGet request) throws ClientProtocolException,IOException{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}
	
	//通过HttpPost获得HttpResponse对象
	public static HttpResponse getHttpResponse (HttpPost request) throws ClientProtocolException,IOException{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}
	
	// 通过url发送post请求，返回请求结果
	public static String queryStringForPost (String url) {
		
		// 获得HttpPost实例
		HttpPost request = HttpUtil.getHttpPost(url);
		String result = null;
		try {
			// 获得HttpResponse实例
			HttpResponse response = HttpUtil.getHttpResponse(request);
			// 判断请求是否成功
			if (response.getStatusLine().getStatusCode() == 200){
				// 返回获得结果
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常";
			return result;
		}
		return null;
	}
	
	// 通过HttpPost发送get请求，返回请求结果
	public static String queryStringForGet (String url) {
		HttpGet request = HttpUtil.getHttpGet(url);
		String result = null;
		try {
			HttpResponse response = HttpUtil.getHttpResponse(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity()); // EntityUtils对象是org.apache.http.util下的一个工具类，是为HttpEntity对象提供的静态帮助类
				return result;																// response.getEntity()表示从相应中获取消息实体
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常";
			return result;
		}
		return null;
	}
	
	// 通过HttpPost发送post请求，返回请求结果
	public static String queryStringForPost (HttpPost request) {
		String result = null;
		try {
			HttpResponse response = HttpUtil.getHttpResponse(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常";
			return result;
		}
		return null;
	}
}
