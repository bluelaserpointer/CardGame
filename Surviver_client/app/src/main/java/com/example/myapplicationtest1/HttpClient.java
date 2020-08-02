package com.example.myapplicationtest1;

import android.os.StrictMode;

import com.example.myapplicationtest1.page.Page;
import com.example.myapplicationtest1.utils.Cache;
import com.example.myapplicationtest1.utils.Urls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClient {
    private static final int CONNECT_TIMEOUT = 3000;
    private static final int READ_TIMEOUT = 60000;
    public static final String TIMEOUT_SIGN = "CONNECTION TIMEOUT";
    public static String doGetShort(String url) {
        return doRequest(Urls.URLHead + url, null);
    }
    public static String doPostShort(String url, String param) {
        return doRequest(Urls.URLHead + url, param);
    }
    private static String doRequest(String httpUrl, String param) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        HttpURLConnection connection = null;
        try {
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection) new URL(httpUrl).openConnection();
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(READ_TIMEOUT);
            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            if(Cache.token != null)
                connection.setRequestProperty("Authorization", "Bearer " + Cache.token);
            if(param == null) { //GET
                // 设置连接请求方式
                connection.setRequestMethod("GET");
            } else { //POST
                // 设置连接请求方式
                connection.setRequestMethod("POST");
                // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
                connection.setDoOutput(true);
                // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
                connection.setRequestProperty("Content-Type", "application/json");
                // 通过连接对象获取一个输出流
                try (final OutputStream os = connection.getOutputStream()) {
                    // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
                    os.write(param.getBytes());
                }
            }
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                try (
                        final InputStream is = connection.getInputStream();
                        final BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                    final StringBuilder sbf = new StringBuilder();
                    String temp;
                    // 循环遍历一行一行读取数据
                    while ((temp = br.readLine()) != null) {
                        sbf.append(temp);
                        sbf.append("\r\n");
                    }
                    return sbf.toString();
                }
            }
        } catch (SocketTimeoutException e) {
            StartPage.backWithConnectionError(); //发生错误则返回登录页面
            return TIMEOUT_SIGN;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 断开与远程地址url的连接
            if(connection != null)
                connection.disconnect();
        }
        return null;
    }
}