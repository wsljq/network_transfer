package com.antfact.twitter.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

public class HttpConnectionManager {
	private static HttpParams params;
	private static PoolingClientConnectionManager cm;

	private static HttpClient aHttpClient = null;

	/**
	 * 最大连接数
	 */
	public final static int MAX_TOTAL_CONNECTIONS = 500;//200;// 1200;// 400;// 1200;//
														// 2000;//1200;//800;
	/**
	 * 获取连接的最大等待时间
	 */
	public final static long CONN_MANAGER_TIMEOUT = 60000;
	/**
	 * 每个路由最大连接数
	 */
	public final static int MAX_ROUTE_CONNECTIONS = 500;//20;// 600;// 200;// 600;//
														// 1000;//400;//400;
	/**
	 * 连接超时时间
	 */
	public final static int CONNECT_TIMEOUT = 10 * 1000;// 10000;
	/**
	 * 读取超时时间
	 */
	public final static int READ_TIMEOUT = 10 * 1000;// 10000;

	static {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

		cm = new PoolingClientConnectionManager(schemeRegistry);
		cm.setMaxTotal(MAX_TOTAL_CONNECTIONS);
		cm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);

		params = new BasicHttpParams();
		params.setParameter(ClientPNames.CONN_MANAGER_TIMEOUT, CONN_MANAGER_TIMEOUT);

		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECT_TIMEOUT);
		params.setParameter(CoreConnectionPNames.SO_TIMEOUT, READ_TIMEOUT);

		// 在提交请求之前 测试连接是否可用
		params.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, true); // for test

		aHttpClient = new DefaultHttpClient(cm, params);
	}

	public static HttpClient getHttpClient() {
		return new DefaultHttpClient(cm, params);
	}

	public static HttpClient getAHttpClient() {
		return aHttpClient;
	}

}