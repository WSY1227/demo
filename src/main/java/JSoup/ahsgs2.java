package JSoup;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ahsgs2 {
    public static void main(String[] args) throws IOException {
        //创建Httpclient请求
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建http Post请求
        String url = "http://www.ahsgh.com/ahghjtweb/web/list";
        HttpPost httpPost = new HttpPost(url);
        ArrayList<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
        valuePairs.add(new BasicHeader("listPage", "list"));
        valuePairs.add(new BasicHeader("intCurPage", "2"));
        valuePairs.add(new BasicHeader("intPageSize", "10"));
        valuePairs.add(new BasicHeader("strColId", "20782f569264489f87995ad0773ff626"));
        valuePairs.add(new BasicHeader("strWebSiteId", "4c5fcf57602b48a0acde5a4ef3ede48d"));
        valuePairs.add(new BasicHeader("nowPage", "1"));
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(valuePairs);
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);
        //伪装浏览器
        httpPost.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        CloseableHttpResponse response = null;
        try {
            //执行请求
            response = httpClient.execute(httpPost);
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
