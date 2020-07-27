package com.example.myapplicationtest1;

import android.os.StrictMode;

import com.example.myapplicationtest1.page.Page;
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
    //Edward: 192.168.175.1
    //Jun: 192.168.254.1
    public static final String URLHead = "http://192.168.254.1:8080/";
    private static final int CONNECT_TIMEOUT = 3000;
    private static final int READ_TIMEOUT = 60000;
    public static final String TIMEOUT_SIGN = "CONNECTION TIMEOUT";
    public static String doGetShort(String url) {
        return doGet(URLHead + url);
    }
    public static String doGet(String httpurl) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
        try {
            // 创建远程url连接对象
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) new URL(httpurl).openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            if (Urls.token != null)
                connection.setRequestProperty("Authorization", "Bearer " + Urls.token);
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(READ_TIMEOUT);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                // 存放数据
                final StringBuilder sbf = new StringBuilder();
                String temp;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (SocketTimeoutException e) {
            StartPage.backWithConnectionError();
            return TIMEOUT_SIGN;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null)
                connection.disconnect();// 关闭远程连接
        }
        return result;
    }

    public static String doPostShort(String url, String param) {
        return doPost(URLHead + url, param);
    }
    public static String doPost(String httpUrl, String param) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接请求方式
            connection.setRequestMethod("POST");
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(READ_TIMEOUT);

            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Content-Type", "application/json");
            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            if(Urls.token != null)
                connection.setRequestProperty("Authorization", "Bearer " + Urls.token);
            // 通过连接对象获取一个输出流
            os = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            os.write(param.getBytes());
            // 通过连接对象获取一个输入流，向远程读取
            if (connection.getResponseCode() == 200) {

                is = connection.getInputStream();
                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

                final StringBuilder sbf = new StringBuilder();
                String temp;
                // 循环遍历一行一行读取数据
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (SocketTimeoutException e) {
            StartPage.backWithConnectionError();
            return TIMEOUT_SIGN;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 断开与远程地址url的连接
            if(connection != null)
                connection.disconnect();
        }
        return result;
    }
}