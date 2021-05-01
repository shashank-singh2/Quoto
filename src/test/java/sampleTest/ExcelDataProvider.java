package sampleTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class ExcelDataProvider {

	/**
	 * 
	 * to get Class name use "this.getClass().getName()" and to get method name
	 * pass Method as an argument in method parameter then use method.getName()
	 * such as Below code:- public void method1(Method method){ String
	 * methodName=method.getName();}
	 * 
	 * @param sheetName
	 *            as ClassName
	 * @param testCaseName
	 *            as Method name
	 * @return Map<String,String> having data value for particular TC
	 * @throws IOException
	 */
//String sheetName, String testCaseName
//	@DataProvider(name="data")
	public static String[][] getDataUsingColumnName() throws IOException {

		Map<String, String> tcDataM = new LinkedHashMap<String, String>();
		List<String> headerList = new ArrayList<String>();
		int k = 0;
		int tcColumn = 0;
		String filepath = ".\\src\\files\\TestData.xlsx";
		int a = 0;
		String arr[][] = null;

		FileInputStream fis = new FileInputStream(new File(filepath));
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		int totalSheets = wb.getNumberOfSheets();
		System.out.println("Total Number of sheet is : " + totalSheets);
		for (int l = 0; l < totalSheets; l++) {
			if (wb.getSheetName(l).equalsIgnoreCase("basics")) {

				XSSFSheet sh = wb.getSheetAt(l);
				int totalrows = sh.getLastRowNum();
				
				Iterator<Row> rows = sh.rowIterator();
				Row firstRow = rows.next();

				Iterator<Cell> fCl = firstRow.cellIterator();
//Getting Headers
				while (fCl.hasNext() == true) {
					Cell cl = fCl.next();
					String hdrvalue = cl.getStringCellValue();
					headerList.add(hdrvalue);

					if (hdrvalue.equalsIgnoreCase("testcase")) {
						tcColumn = k;
					}//if ends
					k++;
				}//while ends
				System.out.println(headerList);
				List<Integer> rowNum = new ArrayList<Integer>();
//Getting row number for testcase name
				while (rows.hasNext() == true) {

					Row r = rows.next();
					if (r.getCell(tcColumn).getStringCellValue().equalsIgnoreCase("method1")) {
						
								rowNum.add(r.getRowNum());}//if ends
					}//while ends
				
				arr = new String[rowNum.size()][k];
//Fetching data from Sheet using header name and fetched row number with matched method
						
						for (int ro:rowNum){	
						for (int i = 0; i < k; i++) {
//							if (i != tcColumn) {
								String dataValue = "";
								String headerData = headerList.get(i);
								
								Cell cell = sh.getRow(ro).getCell(i);

								if (cell == null) {
									dataValue = "";
								}

								else if (cell.getCellType() == CellType.STRING) {

									dataValue = cell.getStringCellValue();
									arr[a][i] = cell.getStringCellValue();
								} else if (cell.getCellType() == CellType.NUMERIC) {
									dataValue = NumberToTextConverter.toText(cell.getNumericCellValue());
								} else {
									dataValue = null;
								}

								tcDataM.put(headerData, dataValue);
//								a++;
//								}
//							continue;
								}//i ends

						a++;}//ro ends
					
				}//if worksheet match ends
			}// for ends for all worksheets

			if (fis != null) {
				fis.close();
			}
			if (wb != null) {
				wb.close();
			}
	
		// return tcDataM;
			System.out.println(arr);
		return arr;

	}//Method getDataUsingColumnName ends here
	

	
	
}
