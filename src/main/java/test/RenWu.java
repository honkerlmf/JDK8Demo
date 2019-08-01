package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class RenWu {
    // 搜索页数
    private static int N = 6;
    // 搜索关键词
    private static String keyWord = "爬虫";
    // 第一页搜索结果
    private static HtmlPage firstBaiduPage;
    // Baidu对应每个搜索结果的第一页第二页第三页等等其中包含“&pn=1”,“&pn=2”,“&pn=3”等等，
    // 提取该链接并处理可以获取到一个模板，用于定位某页搜索结果
    private static String template = "";

    public static void main(String[] args) {
        goSearch(N, keyWord);
    }

    private static void goSearch(final int n, final String keyWord) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 页数
                int x = n;
                System.out.println("爬取百度关于关键字“" + keyWord + "”搜索结果的前" + x + "页");
                FileUtil.toFile("爬取百度关于关键字“" + keyWord + "”搜索结果的前" + x + "页\n");
                
                //1.获取并输出第一页百度查询内容
                Elements firstElementsLink = null;
                try {
                    firstElementsLink = getFirstPage(keyWord);
                } catch (Exception e) {
                    e.printStackTrace();
                } 
                for (Element link : firstElementsLink) {
                    // 链接url
                    String linkHref = link.attr("href");
                    // 链接标题
                    String linkText = link.text();
                    if(linkHref.length() > 13 & linkText.length() > 4) {
                        String content = "链接url: " + linkHref + "\n\t链接标题: " + linkText + "\n";
                        System.out.println(content);
                        FileUtil.toFile(content);
                    }
                }
                
                //2.读取第二页及之后页面预处理
                // 以firstBaiduPage作为参数，定义template，即网页格式。
                nextHref(firstBaiduPage);
                
                //3.获取百度第一页之后的搜索结果
                for(int i = 1; i< x; i++) {
                    System.out.println("\n---------百度搜索关键字“" + keyWord + "”第" + (i + 1) + "页结果------");
                    FileUtil.toFile("\n---------百度搜索关键字“" + keyWord + "”第" + (i + 1) + "页结果------" + "\n");
                    // 根据已知格式修改生成新的一页的链接
                    String tempURL = template.replaceAll("&pn=1", "&pn=" + i + "");
                    // 显示该搜索模板
                    System.out.println("\t该页地址为：" + tempURL);
                    RenWu renWu = new RenWu();
                    // 实现摘取网页源码
                    String htmls = renWu.getPageSource(tempURL, "utf-8");
                    // 网页信息转换为jsoup可识别的doc模式
                    Document doc = Jsoup.parse(htmls);
                    // 摘取该页搜索链接
                    Elements links = doc.select("a[data-click]");
                    // 该处同上getFirstPage的相关实现
                    for (Element link : links) {
                        // 链接url
                        String linkHref = link.attr("href");
                        // 链接标题
                        String linkText = link.text();
                        if(linkHref.length() > 13 & linkText.length() > 4) {
                            String content = "链接url: " + linkHref + "\n\t链接标题: " + linkText + "\n";
                            System.out.println(content);    
                            FileUtil.toFile(content);
                        }
                    }
                }
            }
        });
        thread.start();
    }
    
    public String getPageSource(String pageURL, String encoding) {
        // 输入：url链接&编码格式
        // 输出：该网页内容
        StringBuffer sb = new StringBuffer();
        try {
            // 构建一URL对象
            URL url = new URL(pageURL);
            // 使用openStream得到一输入流并由此构造一个BufferedReader对象
            InputStream in = url.openStream();
            InputStreamReader ir = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(ir);
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /*
     * 获取百度搜索第一页内容
     */
    public static Elements getFirstPage(String keyWord) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        //设置浏览器的User-Agent
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45);
        // HtmlUnit对JavaScript的支持不好，关闭之
        webClient.getOptions().setJavaScriptEnabled(false);
        // HtmlUnit对CSS的支持不好，关闭之
        webClient.getOptions().setCssEnabled(false);
        // 百度搜索首页页面
        HtmlPage htmlPage = webClient.getPage("http://www.baidu.com/");
        // 获取搜索输入框并提交搜索内容（查看源码获取元素名称）
        HtmlInput input = htmlPage.getHtmlElementById("kw");
        // 将搜索词模拟填进百度输入框（元素ID如上）
        input.setValueAttribute(keyWord);
        // 获取搜索按钮并点击
        HtmlInput btn = htmlPage.getHtmlElementById("su");
        // 模拟搜索按钮事件,获取第一页的html内容
        firstBaiduPage = btn.click();
        // 将获取到的百度搜索的第一页信息输出
        // 通过page.asXml()来获取百度首页的源代码，
        // 通过page.asTest()来获取页面的文字
        String content = firstBaiduPage.asXml().toString();
        // 转换为Jsoup识别的doc格式
        Document doc = Jsoup.parse(content);
        System.out.println("---------百度搜索关键字“" + keyWord + "”第1页结果--------");
        FileUtil.toFile("---------百度搜索关键字“" + keyWord + "”第1页结果--------" + "\n");
        // 返回包含类似<a......data-click=" "......>等的元素
        Elements firstElementsLink = doc.select("a[data-click]");
        // 返回此类链接，即第一页的百度搜素链接
        return firstElementsLink;
    }
    
    /*
     * 获取下一页地址
     */
    public static void nextHref(HtmlPage firstBaiduPage) {
        
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        // 获取到百度第一页搜索的底端的页码的html代码
        String morelinks = firstBaiduPage.getElementById("page").asXml();
        // 转换为Jsoup识别的doc格式
        Document doc = Jsoup.parse(morelinks);
        // 提取这个html中的包含<a href=""....>的部分
        Elements links = doc.select("a[href]");
        // 设置只取一次每页链接的模板格式
        boolean getTemplate = true;
        for (Element e : links) {
            // 将提取出来的<a>标签中的链接取出
            String linkHref = e.attr("href");
            if(getTemplate) {
                // 补全模板格式
                template = "http://www.baidu.com" + linkHref;
                getTemplate = false;
            }
        }
    }
}