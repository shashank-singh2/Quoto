package mainUtilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @author ssing155
 *
 */
public class ExcelUtilities {
	static final String filePath = Global_VARS.TestData_Quoto_xlsx;

	/**
	 * This method is to read data from Test data sheet based on Sheet number, row
	 * number and Cell number
	 * 
	 * @param sheetName
	 * @param rowNum
	 * @param cellNum
	 * @return @
	 */
	public static String readData(String sheetName, int rowNum, int cellNum) {
		String value = "";

		Workbook wb;
		try {
			wb = WorkbookFactory.create(new FileInputStream(new File(filePath)));

			value = wb.getSheet(sheetName).getRow(rowNum).getCell(cellNum).getStringCellValue();
			wb.close();
		} catch (EncryptedDocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return value;

	}

	public static void writeData(String filepath, String sheetName, int rowNum, int cellNum, String value) {

		try {
			FileOutputStream fos1 = new FileOutputStream(new File(filepath));
			XSSFWorkbook wb1 = new XSSFWorkbook();
			XSSFSheet sh = wb1.createSheet(sheetName);
			sh.createRow(rowNum).createCell(cellNum).setCellValue(value);
			wb1.write(fos1);
			wb1.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This will create new sheet in excel file and write the data
	 * 
	 * @param filepath
	 * @param sheetName
	 * @param rowNum
	 * @param cellNum
	 * @param arr
	 */
	public static void write2DArrayDataInExcel(String filepath, String sheetName, int rowNum, int cellNum,
			String[][] arr) {
		FileOutputStream fos1 = null;
		Workbook wb1 = null;
		try {
			fos1 = new FileOutputStream(new File(filepath));
			wb1 = new HSSFWorkbook();
			Sheet sh = wb1.createSheet(sheetName);

			for (int r = 0; r < rowNum; r++) {
				Row row = sh.createRow(r);
				for (int c = 0; c < cellNum; c++) {
					row.createCell(c).setCellValue(arr[r][c]);
				}
			}

			wb1.write(fos1);
			wb1.close();
			fos1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This will append the data at the last row of the existing sheet
	 * 
	 * @param filepath
	 * @param sheetName
	 * @param rowNum
	 * @param cellNum
	 * @param arr
	 */
	public static void write2DArrayInExistingExcelSheet(String filepath, String sheetName, int rowNum, int cellNum,
			String[][] arr) {
		try {
			FileInputStream fis = new FileInputStream(new File(filepath));
			Workbook wb = WorkbookFactory.create(fis);
			Sheet sh = wb.getSheet(sheetName);
			int lastrow = sh.getLastRowNum();
			for (int i = 0; i < rowNum; i++) {
				Row rw = sh.createRow(lastrow + i + 1);
				for (int j = 0; j < cellNum; j++) {
					rw.createCell(j).setCellValue(arr[i][j]);
				}
			}
			fis.close();
			FileOutputStream fos = new FileOutputStream(new File(filepath));
			wb.write(fos);
			wb.close();
			fos.close();

		} catch (IOException | EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * this will be used for data provider class
	 * 
	 * @param sheetName
	 * @param filepath
	 * @return String[][]
	 */
	public static String[][] dataProvider(String sheetName, String filepath) {

		FileInputStream fis = null;
		XSSFWorkbook wb = null;

		try {
			fis = new FileInputStream(new File(filepath));

			wb = new XSSFWorkbook(fis);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XSSFSheet sh = wb.getSheet(sheetName);
		int totalRows = sh.getLastRowNum();
		short totalCells = sh.getRow(0).getLastCellNum();
		System.out.println(totalRows + "  : " + totalCells);

		String arr[][] = new String[totalRows][totalCells];
		for (int dc = 0; dc < totalCells; dc++) {

			for (int rc = 1; rc <= totalRows; rc++) {
				Cell cell = sh.getRow(rc).getCell(dc);

				int r = rc - 1;
				if (cell == null) {
//					arr[r][dc] = "";
				} else if (cell.getCellType() == CellType.STRING) {

					arr[r][dc] = cell.getStringCellValue();

				} else if (cell.getCellType() == CellType.NUMERIC) {
					arr[r][dc] = NumberToTextConverter.toText(cell.getNumericCellValue());
				}

			}

		}
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (wb != null) {
			try {
				wb.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return arr;
	}

	/**
	 * 
	 * to get Class name use "this.getClass().getName()" and to get method name pass
	 * Method as an argument in method parameter then use method.getName() such as
	 * Below code:- public void method1(Method method){ String
	 * methodName=method.getName();}
	 * 
	 * @param sheetName    as ClassName
	 * @param testCaseName as Method name
	 * @return Map<String,String> having data value for particular TC
	 * @throws IOException
	 */

	public static Map<String, String> getDataUsingColumnName(String sheetName, String testCaseName) {

		Map<String, String> tcDataM = new LinkedHashMap<String, String>();
		List<String> headerList = new ArrayList<String>();
		int k = 0;
		int tcColumn = 0;

		FileInputStream fis = null;
		XSSFWorkbook wb = null;
		try {
			fis = new FileInputStream(new File(filePath));

			wb = new XSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int totalSheets = wb.getNumberOfSheets();
		System.out.println("Total Number of sheet is : " + totalSheets);
		for (int l = 0; l < totalSheets; l++) {
			if (wb.getSheetName(l).equalsIgnoreCase(sheetName)) {

				XSSFSheet sh = wb.getSheetAt(l);
				Iterator<Row> rows = sh.rowIterator();
				Row firstRow = rows.next();

				Iterator<Cell> fCl = firstRow.cellIterator();

				while (fCl.hasNext() == true) {
					Cell cl = fCl.next();
					String hdrvalue = cl.getStringCellValue();
					headerList.add(hdrvalue);

					if (hdrvalue.equalsIgnoreCase("testcase")) {
						tcColumn = k;
					}
					k++;
				}
				System.out.println("List of columns present in Excel data sheet : " + headerList);

				while (rows.hasNext() == true) {
					Row r = rows.next();
					System.out.println(testCaseName +" : this is TC name");
					if (r.getCell(tcColumn).getStringCellValue().equalsIgnoreCase(testCaseName)) {
						System.out.println("inside if");
						System.out.println(" k is : "+ k);
						for (int i = 0; i < k; i++) {
							if (i != tcColumn) {
								String dataValue = "";
								String headerData = headerList.get(i);
								Cell cell = r.getCell(i);

								if (cell == null) {
									dataValue = null;
									System.out.println("value for "+ headerData + " is Null");
								}

								else if (cell.getCellType() == CellType.STRING) {

									dataValue = cell.getStringCellValue();
								} else { 
//									(cell.getCellType() == CellType.NUMERIC) {
									dataValue = NumberToTextConverter.toText(cell.getNumericCellValue());
								} 

								tcDataM.put(headerData, dataValue);
							}
							continue;
						}

					break;}
				}
			}

			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return tcDataM;

	}

	/**
	 * This will write data to a particular cell of existing row w.r.t Testcase and
	 * Column name passed
	 * 
	 * @param sheetName
	 * @param testCaseName
	 * @param columnName
	 * @param columnValue
	 */
	public static void writeDatausingColumnName(String sheetName, String testCaseName, String columnName,
			String columnValue) {
		FileInputStream fis = null;
		XSSFWorkbook wb = null;
		int wci = 0;
		int tci = 0;
		try {
			fis = new FileInputStream(new File(filePath));
			wb = new XSSFWorkbook(fis);
			XSSFSheet sh = wb.getSheet(sheetName);
			if (sh != null) {
				XSSFRow head = sh.getRow(0);
				// For Loop to get the column index for headers
				for (int i = 0; i < head.getLastCellNum(); i++) {
					String headCol = head.getCell(i).getStringCellValue();
					if (headCol.equalsIgnoreCase(columnName)) {
						wci = i;

					} else if (headCol.equalsIgnoreCase("testcase")) {
						tci = i;
					}
				}
				// for loop to get the testcase matching row
				for (int j = 1; j <= sh.getLastRowNum()-1; j++) {
					System.out.println(sh.getLastRowNum() +" : this is last row num");
					String tcname = sh.getRow(j).getCell(tci).getStringCellValue();
					if (tcname.equalsIgnoreCase(testCaseName)) {
						sh.getRow(j).createCell(wci).setCellValue(columnValue);

					}
				}

			}
			fis.close();
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			wb.write(fos);
			fos.close();
			wb.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * #### Design Under PROGRESS ####
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String[][] getDataForDataProvider(String className, String methodName) throws IOException {

		int k = 0;
		int tcColumn = 0;
		String filepath = Global_VARS.TestData_Quoto_xlsx;
		int a = 0;

		String tempArr[][] = null;

		FileInputStream fis = new FileInputStream(new File(filepath));
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		int totalSheets = wb.getNumberOfSheets();
		System.out.println("Total Number of sheet is : " + totalSheets);
		for (int l = 0; l < totalSheets; l++) {
			if (wb.getSheetName(l).equalsIgnoreCase(className)) {

				XSSFSheet sh = wb.getSheetAt(l);
//				int totalrows = sh.getLastRowNum();

				Iterator<Row> rows = sh.rowIterator();
				Row firstRow = rows.next();

				Iterator<Cell> fCl = firstRow.cellIterator();
				// Getting Headers
				while (fCl.hasNext() == true) {
					Cell cl = fCl.next();
					String hdrvalue = cl.getStringCellValue();

					if (hdrvalue.equalsIgnoreCase("TestCase")) {
						tcColumn = k;
					} // if ends
					k++;
				} // while ends

				List<Integer> rowNum = new ArrayList<Integer>();
				// Getting row number for testcase name
				while (rows.hasNext() == true) {

					Row r = rows.next();
					if (r.getCell(tcColumn).getStringCellValue().equalsIgnoreCase(methodName)) {

						rowNum.add(r.getRowNum());
					} // if ends
				} // while ends

				// Fetching data from Sheet using header name and fetched row number with
				// matched method
				System.out.println("Row num : " + rowNum + " its size is : " + rowNum.size());
				int c;
				String val;
				for (int ro : rowNum) {
					int b = 1;
					for (int i = 0; i < k; i++) {

						tempArr = new String[rowNum.size()][b];
						Cell cell = sh.getRow(ro).getCell(i);
						if (i != tcColumn && cell != null) {

							if (cell.getCellType() == CellType.STRING) {

								c = b - 1;
								val = cell.getStringCellValue();
								System.out.println(val);
								tempArr[a][c] = val;
								b++;

							} else if (cell.getCellType() == CellType.NUMERIC) {

								c = b - 1;
								val = NumberToTextConverter.toText(cell.getNumericCellValue());
								System.out.println(val);
								tempArr[a][c] = val;
								b++;

							} else {

							}
							if (i == k - 1) {

								a++;
							}

						}
					} // i ends

				} // ro ends

				System.out.println("Sixe of array dp is : " + tempArr.length);

			} // if worksheet match ends
		} // for ends for all worksheets

		if (fis != null) {
			fis.close();
		}
		if (wb != null) {
			wb.close();
		}

		System.out.println(tempArr.length + " Printing this array ");

		for (int i = 0; i < tempArr.length; i++) {
			for (int j = 0; j < tempArr[i].length; j++)
				System.out.print(tempArr[i][j] + " ");

		}

		return tempArr;

	}

}
