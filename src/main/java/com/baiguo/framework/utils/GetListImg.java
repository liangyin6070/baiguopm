package com.baiguo.framework.utils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @description 抓取图片线程实现类
 * @author ldw
 * @datetime 2017年3月22日 上午11:57:13
 */
public class GetListImg implements Runnable {

	private String listUrl;
	private String dirName;

	private static String dsn = "http://www.selie123.com";

	public GetListImg(String listUrl, String dirName) {
		this.listUrl = listUrl;
		this.dirName = dirName;
	}

	@Override
	public void run() {
		// TODO 正式开启爬取图片
		Document doc = null;
		try {
			doc = Jsoup.connect(listUrl).get();
			Element ul = doc.select("ul.piclist").first();
			Elements eles = ul.select("a.pic");
			for (int i = 0; i < eles.size(); i++) {
				Element ele = eles.get(i);
				String topicUrl = dsn + ele.attr("href");// 格式化前的URL
				System.out.println("需要抓取的URL:" + topicUrl);
				String topicUrl2 = CatchDataUtils.matcherHTML(topicUrl);// 格式化后的URL
				String[] s = topicUrl.split("/");
				String tur = s[s.length - 1];
				String[] fileName = tur.split(".html");
				String dirName1 = fileName[0];
				int num = returnImgsNum(topicUrl);
				CatchDataUtils.nutchImg2(topicUrl, topicUrl2, num, dirName+dirName1);
				//Thread.sleep(10000);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.notify();
		}
	}

	public int returnImgsNum(String topicUrl) {
		int num = 0;
		try {
			Document doc = Jsoup.connect(topicUrl).get();
			Element ul = doc.select("ul.pagelist").first();
			Element a = ul.getElementsByTag("a").first();
			String text = a.text();
			Pattern p = Pattern.compile("(\\d+)");
			Matcher m = p.matcher(text);
			if (m.find()) {
				num = Integer.parseInt(m.group(1));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return num;
	}

	public String getListUrl() {
		return listUrl;
	}

	public void setListUrl(String listUrl) {
		this.listUrl = listUrl;
	}
	
	public static void main(String[] args) {
		String text = "共186页:";
		Pattern p = Pattern.compile("(\\d+)");
		Matcher m = p.matcher(text);
		if (m.find()) {
			System.out.println(m.group(1));
		}
	}
}
