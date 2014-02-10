package com.wangjie.androidinject.annotation.core.net;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wangjie
 * @version 创建时间：2013-3-9 上午9:29:40
 */
public class AINetWork {
    private static final String TAG = AINetWork.class.getSimpleName();
	/**
	 * 使用post来请求url并返回StringBuilder对象
	 * 
	 * @author wangjie
	 * @param map
	 *            post携带的参数
	 * @return 返回请求结果StringBuilder对象
	 * @throws Exception
	 *             如果请求出错
	 */


    public static StringBuilder postStringFromUrl(HttpClient httpClient, String baseUrl,
                               Map<String, String> map) throws Exception {
        HttpPost httpPost = new HttpPost(baseUrl);
        // 保持同一session
//		if(!"".equals(Variables.appCookie)){
//			httpPost.setHeader("Cookie", "JSESSIONID=" + Variables.appCookie);
//		}

        List<BasicNameValuePair> postData = new ArrayList<BasicNameValuePair>();
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            postData.add(new BasicNameValuePair(entry.getKey(), entry
                    .getValue()));
        }

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postData,
                HTTP.UTF_8);
        httpPost.setEntity(entity);
        HttpResponse resp = httpClient.execute(httpPost);

//		CookieStore mCookieStore = httpClient.getCookieStore();
//		// 更新session
//		List<Cookie> cookies = mCookieStore.getCookies();
//		for (int i = 0; i < cookies.size(); i++) {
//			// 这里是读取Cookie['jsessionid']的值存在静态变量中，保证每次都是同一个值
//			if ("jsessionid".equalsIgnoreCase(cookies.get(i).getName())) {
//				Variables.appCookie = cookies.get(i).getValue();
//				break;
//			}
//		}

        return obtainStringFromInputStream(resp.getEntity().getContent());
    }

    /**
     * 使用get来请求url并返回StringBuilder对象
     * @param baseUrl
     * @return
     * @throws Exception
     */
    public static StringBuilder getStringFromUrl(HttpClient httpClient, String baseUrl) throws Exception{
        HttpGet httpGet = new HttpGet(baseUrl);
        HttpResponse httpResponse;
        HttpEntity httpEntity;
        //生成一个http客户端对象

        //使用Http客户端发送请求对象，得到服务器发回的响应httpResponse
        httpResponse = httpClient.execute(httpGet);
        //httpEntity中有服务器发回的响应的内容
        httpEntity = httpResponse.getEntity();
        return obtainStringFromInputStream(httpEntity.getContent());

    }

    public static HttpClient getDefaultHttpClient(int connTimeout, int soTimeout){
        HttpClient httpClient = new DefaultHttpClient();
        HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, connTimeout);
        HttpConnectionParams.setSoTimeout(params, soTimeout);
        System.out.println("params timeout, connTimeout: " + connTimeout + ", soTimeout: " + soTimeout);
        return httpClient;
    }



	/**
	 * 通过InputStream获得字符串。
	 * 
	 * @author wangjie
	 * @param is
	 *            需要进行读取的InputStream对象
	 * @return 返回读取后的字符串信息
	 */
	public static StringBuilder obtainStringFromInputStream(InputStream is) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		try {
			while (null != (line = br.readLine())) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb;
	}




}
