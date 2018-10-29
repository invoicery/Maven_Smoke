package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	public static String[][] getdata(String filename,String sheetname) throws IOException
	{
	
	File file=new File(filename);
	FileInputStream ips=new FileInputStream(file);
	Workbook wb=new XSSFWorkbook(ips);
	Sheet sh=wb.getSheet(sheetname);
	int rownum=sh.getLastRowNum()+1;
	int colnum=sh.getRow(0).getLastCellNum();
	String[][] data=new String[rownum][colnum];
	for(int i=0;i<rownum;i++) {
		Row row=sh.getRow(i);
		for(int j=0;j<colnum;j++)
		{
			Cell cell=row.getCell(j);
			String value=new DataFormatter().formatCellValue(cell);
			data[i][j]=value;
		}
	}
	return data;
	}





}
