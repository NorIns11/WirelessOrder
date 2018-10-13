package com.amaker.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	// ����Base URL����
	public static final String Base_URL="http://192.168.1.101:8888/WirelessOrder_Server/";
	
	//ͨ��URL���HttpGet����
	public static HttpGet getHttpGet (String url){
		HttpGet request = new HttpGet(url);
		return request;
	}
	
	//ͨ��URL���HttpPost����
	public static HttpPost getHttpPost (String url){
		HttpPost request = new HttpPost(url);
		return request;
	}
		
	//ͨ��HttpGet���HttpResponse����
	public static HttpResponse getHttpResponse (HttpGet request) throws ClientProtocolException,IOException{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}
	
	//ͨ��HttpPost���HttpResponse����
	public static HttpResponse getHttpResponse (HttpPost request) throws ClientProtocolException,IOException{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}
	
	// ͨ��url����post���󣬷���������
	public static String queryStringForPost (String url) {
		
		// ���HttpPostʵ��
		HttpPost request = HttpUtil.getHttpPost(url);
		String result = null;
		try {
			// ���HttpResponseʵ��
			HttpResponse response = HttpUtil.getHttpResponse(request);
			// �ж������Ƿ�ɹ�
			if (response.getStatusLine().getStatusCode() == 200){
				// ���ػ�ý��
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "�����쳣";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "�����쳣";
			return result;
		}
		return null;
	}
	
	// ͨ��HttpPost����get���󣬷���������
	public static String queryStringForGet (String url) {
		HttpGet request = HttpUtil.getHttpGet(url);
		String result = null;
		try {
			HttpResponse response = HttpUtil.getHttpResponse(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity()); // EntityUtils������org.apache.http.util�µ�һ�������࣬��ΪHttpEntity�����ṩ�ľ�̬������
				return result;																// response.getEntity()��ʾ����Ӧ�л�ȡ��Ϣʵ��
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "�����쳣";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "�����쳣";
			return result;
		}
		return null;
	}
	
	// ͨ��HttpPost����post���󣬷���������
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
			result = "�����쳣";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "�����쳣";
			return result;
		}
		return null;
	}
}
