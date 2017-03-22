package com.baiguo.framework.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 抓取网页工具类
 * @author Administrator
 *
 */
public class CatchDataUtils {
	public static int num = 229;
	public static String itname = "6559";
	public static final String dirName = "D://catch_data//"+itname;
	public static String catch_url2 = "http://www.selie123.com/shaonvmanhua/"+itname+".html";
	public static String catch_url = "http://www.selie123.com/shaonvmanhua/"+itname+"_%s.html";
	
	public static List<String> getNutchUrl(List<String> urls) throws IOException {
		List<String> list = new ArrayList<String>();
		for (String url : urls) {
			Document doc = Jsoup.connect(url).get(); 
			Elements els = doc.getElementsByTag("img");
			Element el = els.get(0);
			String s = el.attr("src");
			System.out.println("获取到的图片url："+s);
			list.add(s);
		}
		return list;
	}
	
	public static void nutchImg() {
		// 从 URL 直接加载 HTML 文档
		List<String> list = new ArrayList<String>();
		String url = null;
		for(int i = 1; i <= num; i++) {
			if(i == 1) {
				url = catch_url2;
			} else {
				url = String.format(catch_url, String.valueOf(i));
			}
			list.add(url);
		}
		List<String> nutchs = null;
		try {
			nutchs = getNutchUrl(list);
			for(int i = 1; i < nutchs.size(); i++) {
				HttpClientUtils.downloadFileToDir(nutchs.get(i), i+".jpg", dirName);
				System.out.println("文件保存成功："+dirName+"/"+i+".jpg");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param nowUrl 第一页URL
	 * @param nextString 经过格式化后的URL
	 * @param num 该URL下的图片数量
	 * @param dirName 保存的文件夹名称
	 * @throws IOException
	 */
	public static void nutchImg2(String nowUrl, String nextString, int num, String dirName) throws IOException {
		// 从 URL 直接加载 HTML 文档
		List<String> list = new ArrayList<String>();
		String url = null;
		for(int i = 1; i <= num; i++) {
			if(i == 1) {
				url = nowUrl;
			} else {
				url = String.format(nextString, String.valueOf(i));
			}
			list.add(url);
		}
		List<String> nutchs = getNutchUrl(list);
		for(int i = 1; i < nutchs.size(); i++) {
			HttpClientUtils.downloadFileToDir(nutchs.get(i), i+".jpg", dirName);
			System.out.println("文件保存成功："+dirName+"/"+i+".jpg");
		}
	}
	
	public static void main(String[] args) {
		catchMore();
//		nutchImg();
	}
	
	public static String list_url = "http://www.selie123.com/shaonvmanhua/list_4_%s.html";
	public static int list_num = 65;
	public static ExecutorService fixedThreadPool = null;
	
	static {
		if(fixedThreadPool == null) {
			fixedThreadPool = Executors.newFixedThreadPool(100); //初始化线程池
		}
	}
	
	public static void catchMore() {
		for(int i = 1; i < list_num; i++) {
			String listUrl = String.format(list_url, String.valueOf(i));
			fixedThreadPool.execute(new GetListImg(listUrl, "D://catch_data//"));
		}
	}
	
	public static String matcherHTML(String url) {
		Pattern pattern = Pattern.compile(".html");
		Matcher matcher = pattern.matcher(url);
		return matcher.replaceAll("_%s.html");
	}
}
