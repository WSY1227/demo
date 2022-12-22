package JSoup;

import Util.HttpClientUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
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
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ahsgsPost2 {
    public static void main(String[] args) throws IOException {
        String url = "http://www.ahsgh.com/ahghjtweb/web/list";
        Map<String, Object> params = new HashMap<>();
        params.put("strColId", "20782f569264489f87995ad0773ff626");
        params.put("strWebSiteId", "4c5fcf57602b48a0acde5a4ef3ede48d");
        try {
            String content = HttpClientUtils.sendGet(url, params);

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
                getTitTimes(j);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getTitTimes(int page) throws IOException {
        String url = "http://www.ahsgh.com/ahghjtweb/web/list";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        //伪装浏览器
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        Map<String, Object> data = new HashMap<>();
        data.put("listPage", "list");
        data.put("intCurPage", page);
        data.put("intPageSize", "10");
        data.put("strColId", "20782f569264489f87995ad0773ff626");
        data.put("strWebSiteId", "4c5fcf57602b48a0acde5a4ef3ede48d");
        data.put("nowPage", page);
        JSONObject json = new JSONObject(data);
        try {
            String content = HttpClientUtils.sendPost(url, headers, json);

            Document document = Jsoup.parse(content);
            Elements lis = document.select("ul.tab01open>li");
            for (Element li : lis) {
                Element titTime = li.select(".tit-time").first();
                String fl = titTime.getElementsByClass("fl").eq(0).text();
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

                String strId = null, strColId = null, strWebSiteId = null;
                for (int i = 0; i < 3; i++) {
                    matcher.find();
                    String temp = matcher.group(1);
                    switch (i) {
                        case 0:
                            strId = temp;
                            break;
                        case 1:
                            strColId = temp;
                            break;
                        case 2:
                            strWebSiteId = temp;
                            break;
                    }
                }
                String hrefUrl = "http://www.ahsgh.com/ahghjtweb/web/view?strId=" + strId + "&strColId=" + strColId + "&strWebSiteId=" + strWebSiteId;
                System.out.println("链接：" + hrefUrl);
                getUrlAndContext(hrefUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void getUrlAndContext(String url) throws IOException {
        try {
            String content = HttpClientUtils.sendGet(url);

            Document document = Jsoup.parse(content);
            String context = document.getElementsByClass("article-conca clearfix").get(0).html();
            int startIndex = context.indexOf("<!-- 正文内容 -->");
            int endIndex = context.indexOf("<!-- 文后 -->");
            if (startIndex != -1 && endIndex != -1) {
                String contentHtml = context.substring(startIndex + "<!-- 正文内容 -->".length(), endIndex);
                System.out.println(contentHtml);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
