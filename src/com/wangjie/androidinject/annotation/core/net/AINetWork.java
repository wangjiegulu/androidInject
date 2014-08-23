package com.wangjie.androidinject.annotation.core.net;

import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.utils.ABIOUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.*;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

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
                                                  Map<String, String> map) throws IOException {
        return postStringFromUrl(httpClient, 20000, 20000, baseUrl, map);
    }

    public static StringBuilder postStringFromUrl(HttpClient httpClient, int connTimeout, int soTimeout, String baseUrl,
                               Map<String, String> map) throws IOException {
        if(null == httpClient){
            httpClient = baseUrl.startsWith("https") ? getSSLHttpClient(connTimeout, soTimeout) : getDefaultHttpClient(connTimeout, soTimeout);
        }

        HttpPost httpPost = new HttpPost(baseUrl);
        httpPost.addHeader("Accept-Encoding", "gzip");
        // 保持同一session
//		if(!"".equals(Variables.appCookie)){
//			httpPost.setHeader("Cookie", "JSESSIONID=" + Variables.appCookie);
//		}

        List<BasicNameValuePair> postData = new ArrayList<BasicNameValuePair>();
        if(null != map){
            Set<Map.Entry<String, String>> entries = map.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                postData.add(new BasicNameValuePair(entry.getKey(), entry
                        .getValue()));
            }
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
        return obtainJsonStringFromGZIP(resp);
//        return obtainStringFromInputStream(resp.getEntity().getContent());
    }


    /**
     * 使用get来请求url并返回StringBuilder对象
     * @param baseUrl
     * @return
     * @throws Exception
     */

    public static StringBuilder getStringFromUrl(HttpClient httpClient, String baseUrl) throws IOException{
        return getStringFromUrl(httpClient, 20000, 20000, baseUrl);
    }
    public static StringBuilder getStringFromUrl(HttpClient httpClient, int connTimeout, int soTimeout, String baseUrl) throws IOException {
        if(null == httpClient){
            httpClient = baseUrl.startsWith("https") ? getSSLHttpClient(connTimeout, soTimeout) : getDefaultHttpClient(connTimeout, soTimeout);
        }

        HttpGet httpGet = new HttpGet(baseUrl);
        httpGet.addHeader("Accept-Encoding", "gzip");
        HttpResponse httpResponse;
        HttpEntity httpEntity;
        //生成一个http客户端对象

        //使用Http客户端发送请求对象，得到服务器发回的响应httpResponse
        httpResponse = httpClient.execute(httpGet);
//        //httpEntity中有服务器发回的响应的内容
//        httpEntity = httpResponse.getEntity();
//        return obtainStringFromInputStream(httpEntity.getContent());
        return obtainJsonStringFromGZIP(httpResponse);

    }


    public static StringBuilder deleteStringFromUrl(HttpClient httpClient, String baseUrl) throws IOException{
        return deleteStringFromUrl(httpClient, 20000, 20000, baseUrl);
    }

    public static StringBuilder deleteStringFromUrl(HttpClient httpClient, int connTimeout, int soTimeout, String baseUrl) throws IOException {
        if(null == httpClient){
            httpClient = baseUrl.startsWith("https") ? getSSLHttpClient(connTimeout, soTimeout) : getDefaultHttpClient(connTimeout, soTimeout);
        }
        HttpDelete httpDelete = new HttpDelete(baseUrl);
        httpDelete.addHeader("Accept-Encoding", "gzip");
        HttpResponse httpResponse;
        HttpEntity httpEntity;
        //生成一个http客户端对象

        //使用Http客户端发送请求对象，得到服务器发回的响应httpResponse
        httpResponse = httpClient.execute(httpDelete);
//        //httpEntity中有服务器发回的响应的内容
//        httpEntity = httpResponse.getEntity();
//        return obtainStringFromInputStream(httpEntity.getContent());
        return obtainJsonStringFromGZIP(httpResponse);

    }


    public static HttpClient getDefaultHttpClient(int connTimeout, int soTimeout){
        HttpClient httpClient = new DefaultHttpClient();
        HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, connTimeout);
        HttpConnectionParams.setSoTimeout(params, soTimeout);
        return httpClient;
    }

    public static HttpClient getSSLHttpClient(int connTimeout, int soTimeout) {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(params, connTimeout);
            HttpConnectionParams.setSoTimeout(params, soTimeout);

            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);


            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));
            registry.register(new Scheme("https", sf, 9094));
            registry.register(new Scheme("https", sf, 9000));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            Logger.e(TAG, e);
            return new DefaultHttpClient();
        }
    }

    private static StringBuilder obtainJsonStringFromGZIP(HttpResponse response) {
        StringBuilder resultSb = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        InputStreamReader reader = null;
        try {
            is = response.getEntity().getContent();
            bis = new BufferedInputStream(is);
            bis.mark(2);
            // 取前两个字节
            byte[] header = new byte[2];
            int result = bis.read(header);
            // reset输入流到开始位置
            bis.reset();
            // 判断是否是GZIP格式
            int headerData = getShort(header);
            // Gzip 流 的前两个字节是 0x1f8b
            if (result != -1 && headerData == 0x1f8b) {
                is = new GZIPInputStream(bis);
            } else {
                is = bis;
            }
            reader = new InputStreamReader(is, "utf-8");
            char[] data = new char[100];
            int readSize;
            resultSb = new StringBuilder();
            while ((readSize = reader.read(data)) > 0) {
                resultSb.append(data, 0, readSize);
            }
        } catch (Exception e) {
            Logger.e(TAG, e);
        } finally {
            ABIOUtil.closeIO(is, bis, reader);
        }
        return resultSb;
    }
    private static int getShort(byte[] data) {
        return (int) ((data[0] << 8) | data[1] & 0xFF);
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
