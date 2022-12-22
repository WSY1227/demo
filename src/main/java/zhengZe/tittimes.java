package zhengZe;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class tittimes {
    public static void main(String[] args) throws IOException {
        System.out.println("k");
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
        int page = 1;
        String url = "http://www.ahsgh.com/ahghjtweb/web/list";
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        ArrayList<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
        valuePairs.add(new BasicHeader("listPage", "list"));
        valuePairs.add(new BasicHeader("intCurPage", page + ""));
        valuePairs.add(new BasicHeader("intPageSize", "10"));
        valuePairs.add(new BasicHeader("strColId", "20782f569264489f87995ad0773ff626"));
        valuePairs.add(new BasicHeader("strWebSiteId", "4c5fcf57602b48a0acde5a4ef3ede48d"));
        valuePairs.add(new BasicHeader("nowPage", page + ""));
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
                System.out.println(1);
//              <ul class="tab01open tab01tca"> <li>1</li>  <li>2</li> </ul>
                /*Pattern.DOTALL 标志，它会让 . 能够匹配所有字符，包括换行符。*/
                Pattern ulPattern = Pattern.compile("<ul class=\"tab01open tab01tca\">(.*?)</ul>", Pattern.DOTALL);
                Matcher ulMatcher = ulPattern.matcher(content);
                ulMatcher.find();
                String ul = ulMatcher.group(1);
                Pattern liPattern = Pattern.compile("<li>(.*?)</li>", Pattern.DOTALL);
                Matcher liMatcher = liPattern.matcher(ul);
                System.out.println(2);
                while (liMatcher.find()) {
                    String liContent = liMatcher.group(1);
                    Pattern h2Pattern = Pattern.compile("<h2 class=\"f1\">(.*?)</h2>", Pattern.DOTALL);
                    Matcher h2Matcher = h2Pattern.matcher(liContent);
                    h2Matcher.find();
                    String h2 = h2Matcher.group(1);
                    System.out.println("标题: " + h2);
                    Pattern iPattern = Pattern.compile("<i class=\"fr\">(.*?)</i>", Pattern.DOTALL);
                    Matcher iMatcher = iPattern.matcher(liContent);
                    h2Matcher.find();
                    String fr = iMatcher.group(1);
                    System.out.println("时间: " + fr);

                }

            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}
