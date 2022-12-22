package JSoup;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ahsgsPost1 {
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
                Document document = Jsoup.parse(content);

                Elements pagenub = document.getElementsByClass("pagenub");
                Element element = pagenub.get(0);
                String i = element.getElementsByTag("i").get(0).text();
                Integer integer = Integer.valueOf(i);
                int totalPage = totalPage = integer / 10;
                if (integer % 10 != 0) {
                    ++totalPage;
                }
                for (int j = 1; j <= totalPage; j++) {
                    getTitTimes(httpClient, j);
                }
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        }
    }

    public static void getTitTimes(CloseableHttpClient httpClient, int page) throws IOException {
//        //创建Httpclient请求
//        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建http Post请求
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
                Document document = Jsoup.parse(content);
//                Element uls = document.select("tab01open tab01tca").first();
                Elements lis = document.select("ul.tab01open>li");
                for (Element li : lis) {
                    Element titTime = li.select(".tit-time").first();
                    String fl = titTime.getElementsByClass("fl").eq(0).text();
//                    String fr = titTime.getElementsByClass("fr").eq(0).text();
                    System.out.println("标题：" + fl);
                    String text = titTime.html();
                    Pattern frPattern = Pattern.compile("<i class=\"fr\">(.*?)</i>");
                    Matcher frMatcher = frPattern.matcher(text);
                    String fr = null;
                    while (frMatcher.find()) {
                        fr = frMatcher.group(1);

                    }
                    System.out.println("时间：" + fr);
                    String href = li.getElementsByTag("a").attr("href");
                    Pattern pattern = Pattern.compile("'(.+?)'");
                    Matcher matcher = pattern.matcher(href);
//                    String strId, strColId, strWebSiteId;
                    String[] hrefs = new String[3];
                    int i = 0;
                    while (matcher.find()) {
                        hrefs[i++] = matcher.group(1);
                    }
                    getUrlAndContext(httpClient, hrefs);
                }


            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    public static void getUrlAndContext(CloseableHttpClient httpClient, String[] hrefs) throws IOException {
        String strId = hrefs[0];
        String strColId = hrefs[1];
        String strWebSiteId = hrefs[2];
        String url = "http://www.ahsgh.com/ahghjtweb/web/view?strId=" + strId + "&strColId=" + strColId + "&strWebSiteId=" + strWebSiteId;
        System.out.println("链接：" + url);
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {

            //执行请求
            response = httpClient.execute(httpGet);
            //判断返回状态码是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                Document document = Jsoup.parse(content);
                String context = document.getElementsByClass("article-conca clearfix").get(0).html();
                int startIndex = context.indexOf("<!-- 正文内容 -->");
                int endIndex = context.indexOf("<!-- 文后 -->");
                if (startIndex != -1 && endIndex != -1) {
                    String contentHtml = context.substring(startIndex + "<!-- 正文内容 -->".length(), endIndex);
                    System.out.println(contentHtml);
                }
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}
