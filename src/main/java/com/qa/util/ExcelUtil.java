package com.qa.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtil {

    public static Workbook book;
    public static Sheet sheet;
    public static FileInputStream fi;
    public static FileOutputStream fo;
    public static XSSFWorkbook wb;
    public static XSSFSheet ws;
    public static XSSFRow row;
    public static XSSFCell cell;

    public static String TEST_DATA_SHEET_PATH = "./src"
            + "/main/java/com/qa/hubspot/testdata/HubSpotTestData.xlsx";

    public static Object[][] getTestData(String sheetName) {

        FileInputStream file = null;

        try {
            file = new FileInputStream(TEST_DATA_SHEET_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            book = WorkbookFactory.create(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sheet = book.getSheet(sheetName);

        Object data[][] = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
                data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
            }
        }
        return data;
    }

    public static int getRowCount(String xlfile,String xlsheet) throws IOException
    {
        fi=new FileInputStream(xlfile);
        wb=new XSSFWorkbook(fi);
        ws=wb.getSheet(xlsheet);
        int rowcount=ws.getLastRowNum();
        wb.close();
        fi.close();
        return rowcount;
    }


    public static int getCellCount(String xlfile,String xlsheet,int rownum) throws IOException
    {
        fi=new FileInputStream(xlfile);
        wb=new XSSFWorkbook(fi);
        ws=wb.getSheet(xlsheet);
        row=ws.getRow(rownum);
        int cellcount=row.getLastCellNum();
        wb.close();
        fi.close();
        return cellcount;
    }


    public static String getCellData(String xlfile,String xlsheet,int rownum,int colnum) throws IOException
    {
        fi=new FileInputStream(xlfile);
        wb=new XSSFWorkbook(fi);
        ws=wb.getSheet(xlsheet);
        row=ws.getRow(rownum);
        cell=row.getCell(colnum);
        String data;
        try
        {
            DataFormatter formatter = new DataFormatter();
            String cellData = formatter.formatCellValue(cell);
            return cellData;
        }
        catch (Exception e)
        {
            data="";
        }
        wb.close();
        fi.close();
        return data;
    }

    public static void setCellData(String xlfile,String xlsheet,int rownum,int colnum,String data) throws IOException
    {
        fi=new FileInputStream(xlfile);
        wb=new XSSFWorkbook(fi);
        ws=wb.getSheet(xlsheet);
        row=ws.getRow(rownum);
        cell=row.createCell(colnum);
        cell.setCellValue(data);
        fo=new FileOutputStream(xlfile);
        wb.write(fo);
        wb.close();
        fi.close();
        fo.close();
    }

}