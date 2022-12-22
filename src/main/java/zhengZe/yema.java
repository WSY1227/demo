package zhengZe;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class yema {
    public static void main(String[] args) throws IOException {
        //设置超时时间
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setStaleConnectionCheckEnabled(true)
                .build();
        //创建Httpclient请求
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(defaultRequestConfig)
                .build();
        //创建http GET请求
        String url = "http://www.ahsgh.com/ahghjtweb/web/list?strColId=20782f569264489f87995ad0773ff626&strWebSiteId=4c5fcf57602b48a0acde5a4ef3ede48d";
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {

            //执行请求
            response = httpClient.execute(httpGet);
            //判断返回状态码是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                //请求具体能容
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                Pattern pattern = Pattern.compile("<span class=\"pagenub\">共\\s*<i>(.*?)</i>\\s*条</span>");
                Matcher matcher = pattern.matcher(content);
                if (matcher.find()) {
                    String contents = matcher.group(1);
                    System.out.println("Content: " + contents);
                }

            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        }
    }
}
