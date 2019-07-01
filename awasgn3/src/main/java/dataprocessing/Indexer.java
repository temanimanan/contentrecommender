package dataprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

//Indexer class used to indexed the crawled data
public class Indexer {
	public void indexing() throws IOException, ParseException {
		System.out.println("Inside indexing");
		
		ClassLoader cl = getClass().getClassLoader();
		URL path = cl.getResource("/data");
		String configPath = URLDecoder.decode(path.getFile(), "UTF-8");
		File dict = new File(configPath);
		//Verify whether the crawled data is available?
		if (!dict.exists() || !dict.isDirectory()) {
			throw new IOException("Dictionary Not Found!!!");
		}
		
		//Store the indexed data in new folder
		path = cl.getResource("/indexedData");
		configPath = URLDecoder.decode(path.getFile(), "UTF-8");
		File indexDict = new File(configPath);

		Directory directory = FSDirectory.open(indexDict.toPath());
		StandardAnalyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(directory, config);
		directoryIndexing(writer, dict);
		writer.close();
	}
	
	//Function used to loop through all the files in the directory
	public void directoryIndexing(IndexWriter writer, File directory) throws IOException {
		System.out.println("Inside directoryIndexing");
		File[] fileList = directory.listFiles();
		for(File file:fileList) {
			if (file.isDirectory()) {
				directoryIndexing(writer, file);
			} else if (file.getName().endsWith(".txt")) {
				fileIndexing(writer, file);
			}
		}
	}
	
	//Function used to index data files
	public void fileIndexing(IndexWriter writer, File file) throws IOException {
		System.out.println("Inside fileIndexing");
		org.apache.lucene.document.Document document = new org.apache.lucene.document.Document();
		document.add(new TextField("filename", file.getName(), TextField.Store.YES));
		
		try {
			FileInputStream inputStream = new FileInputStream(file);
			BufferedReader readBuffer = new BufferedReader(new InputStreamReader(inputStream));
			StringBuffer stringBuffer = new StringBuffer();
			String line = null;
			
			while((line = readBuffer.readLine()) != null) {
			  stringBuffer.append(line).append("\n");
			}
			
			readBuffer.close();
			document.add(new TextField("contents", stringBuffer.toString(), TextField.Store.YES));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		writer.addDocument(document);
	}
}
