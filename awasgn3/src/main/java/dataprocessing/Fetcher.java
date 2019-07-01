package dataprocessing;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.tartarus.snowball.ext.PorterStemmer;

//Fetched class is to fetch the recommendations from crawled data
public class Fetcher {
	public List<String> fetchData(String queryString) throws IOException, ParseException {
		System.out.println("Inside fetchData");
		queryString = queryString.replaceAll("[^A-Za-z\\s]", "");
		StandardAnalyzer analyzer = new StandardAnalyzer();
		
		int queryLength = queryString.split("\\s+").length;
		if(queryLength > 3) {
			PorterStemmer stemmer = new PorterStemmer();
			stemmer.setCurrent(queryString);
			stemmer.stem();
			queryString = stemmer.getCurrent();
		}

		ClassLoader cl = getClass().getClassLoader();
		URL path = cl.getResource("/indexedData");
		String configPath = URLDecoder.decode(path.getFile(), "UTF-8");
		File indexedDictionary = new File(configPath);
		
		Directory directory = FSDirectory.open(indexedDictionary.toPath());
		Query query = new QueryParser("contents", analyzer).parse(queryString);
		
		int resultsCount = 20;
		IndexReader reader = null;
		IndexSearcher searcher = null;
		TopScoreDocCollector collector = null;
		
		reader = DirectoryReader.open(directory);
		searcher = new IndexSearcher(reader);
		collector = TopScoreDocCollector.create(resultsCount);
		searcher.search(query, collector);
		
		ScoreDoc[] matches = collector.topDocs().scoreDocs;
		List<String> results = new ArrayList<String>();
			
		for (int i = 0; i < matches.length; ++i) {
			int docId = matches[i].doc;
			org.apache.lucene.document.Document doc = searcher.doc(docId);
			results.add(doc.get("contents"));
		}
		
		reader.close();
		return results;
	}
}
