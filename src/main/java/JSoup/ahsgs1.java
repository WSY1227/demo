package JSoup;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ahsgs1 {
    public static void main(String[] args) throws IOException {
        //创建Httpclient请求
        CloseableHttpClient httpClient = HttpClients.createDefault();
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
                Document document = Jsoup.parse(content);

                Elements titTimes = document.getElementsByClass("tit-time");
                //获取ul下所有的内容
                for (Element titTime : titTimes) {
                    String fl = titTime.getElementsByClass("fl").eq(0).text();
                    String fr = titTime.getElementsByClass("fr").eq(0).text();
                    System.out.println("标题：" + fl);
                    System.out.println("时间：" + fr);
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
