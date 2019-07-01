package dataprocessing;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.DefaultExtractor;

//Crawler class fetch the data from the Wiki and Oracle links
public class Crawler {
	//Crawling function defined to first crawl Wiki and then Oracle page
	public void crawling() throws IOException, ParseException {
		System.out.println("Inside crawling");
		
		ClassLoader cl = getClass().getClassLoader();
		URL path = cl.getResource("/data");
		String configPath = URLDecoder.decode(path.getFile(), "UTF-8");
		File dict = new File(configPath);
		
		crawlWiki(dict);
		crawlOracle(dict);
	}
	
	//Function defined to crawl Wiki page
	public void crawlWiki(File dict) throws IOException {
		System.out.println("Inside crawlWiki");
		Document document1 = Jsoup.connect("http://en.wikibooks.org/wiki/Java_Programming").get();
		Elements links1 = document1.select("div#mw-content-text>ul>li>a[href]");
		String[] keywords1 = new String[links1.size()];
		
		//Fetching the topics/headings to crawl upto level 1
		int i = 0;
		for(Element link:links1) {
			if(link.equals("https:\\en.wikibooks.org\\wiki\\Java_Programming\\Print_version")) 
				continue;
			
			if(link.attr("href").contains("Print_version"))
				continue;
			
			if (link.attr("abs:href").contains("wiki/Java_Programming")) {
				String[] topic = link.attr("abs:href").split("/");
				String edit_topic = topic[topic.length - 1];
				keywords1[i] = edit_topic.replace("_", " ");
			}
			String[] topic = link.attr("abs:href").split("/");
			String edit_topic = topic[topic.length - 1];
			keywords1[i] = edit_topic.replace("_", " ");
			i++;
		}
		
		//Fetching the level 1 data from individual topics/headings
		int j = 0;
		for(Element link:links1) {
			Document doc = Jsoup.connect(link.attr("abs:href")).get();
			doc.select("div#mw-content-text>table.wikitable.noprint").remove();
			doc.select("div#mw-content-text>table.wikitable").remove();
			doc.select("h2>span.mw-editsection").remove();
			Elements data = doc.select("div#mw-content-text>*");
			
			List<String> list = Arrays.asList(data.toString().replaceAll("^.*?<h[0-3]>", "").split("<h[0-9]>.*?h[0-9]>|<p><br></p>"));
			for(int k = 0; k < list.size() - 1; k++) {
				if(list.get(k).split("\\s+").length > 300) {
					//Extracting the required article using Boiler Pipe
					String getArticle = null;
					try {
						getArticle = DefaultExtractor.INSTANCE.getText(list.get(k));
					} catch (BoilerpipeProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//Storing the extracted article to the file based on the topic/heading name
					File file = new File(dict + "//" + keywords1[j] + "_" + k + "_wiki.txt");
					PrintWriter writeToFile = new PrintWriter(file);
					writeToFile.println(getArticle);
					writeToFile.close();
				}
			}
			j++;
		}
	}
	
	//Function defined to crawl Oracle page
	public void crawlOracle(File dict) throws IOException {
		System.out.println("Inside crawlOracle");
		Document document2 = Jsoup.connect("https://docs.oracle.com/javase/tutorial/java/TOC.html").get();
		Elements links2 = document2.select("div#PageContent>ul>li>ul>li>a[href],div#PageContent>ul>li>ul>li>ul>li>a[href]");
		String[] keywords2 = new String[links2.size()];
		
		//Fetching the topics/headings to crawl upto level 1
		int i = 0;
		for(Element link:links2) {
			String[] topic = link.attr("abs:href").split("/");
			String edit_topic = topic[topic.length - 1];
			keywords2[i] = edit_topic.replace(".html", "");
			i++;
		}
		
		//Fetching the level 1 data from individual topics/headings
		int j = 0;
		for(Element link:links2) {
			Document doc = Jsoup.connect(link.attr("abs:href")).get();
			doc.select("div#MainFlow>div.PrintHeaders,div#MainFlow>div.BreadCrumbs,div#MainFlow>div.NavBit,div#MainFlow>div.PageTitle").remove();
			Elements data = doc.select("div#MainFlow>*");
			
			List<String> list = Arrays.asList(data.toString().replaceAll("../../", "https://docs.oracle.com/javase/tutorial/").split("<h[0-9]>.*?h[0-9]>|<p><br></p>"));
			for(int k = 0; k < list.size() - 1; k++) {
				if(list.get(k).split("\\s+").length > 300) {
					//Extracting the required article using Boiler Pipe
					list.get(k).replaceAll("../../", "https://docs.oracle.com/javase/tutorial/");
					String getArticle = null;
					try {
						getArticle = DefaultExtractor.INSTANCE.getText(list.get(k));
					} catch (BoilerpipeProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//Storing the extracted article to the file based on the topic/heading name
					File file = new File(dict + "//" + keywords2[j] + "_" + k + "_oracle.txt");
					PrintWriter writeToFile = new PrintWriter(file);
					writeToFile.println(getArticle);
					writeToFile.close();
				}
			}
			j++;
		}
	}
}
