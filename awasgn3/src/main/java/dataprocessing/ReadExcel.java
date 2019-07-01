package dataprocessing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//ReadExcel class is used to read the excel file provided and parse it to get the query
public class ReadExcel {
	public String parseExcel(int ptr) {
		System.out.println("Inside parseExcel");
		
		try {
			ClassLoader cl = getClass().getClassLoader();
			URL path = cl.getResource("/data_aw17f.xlsx");
			String configPath = URLDecoder.decode(path.getFile(), "UTF-8");
			FileInputStream file = new FileInputStream(new File(configPath));
			
			XSSFWorkbook wb = new XSSFWorkbook(file);
			XSSFSheet sheet = wb.getSheetAt(0);
			Iterator<Row> itr = sheet.iterator();
			
			while(itr.hasNext()) {
				Row row = itr.next();
				if (row.getRowNum() == ptr) {
					Cell url = row.getCell(1);
					String value = url.toString();
					return value;
				}
			}
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "Incorrect Position";
	}
}
