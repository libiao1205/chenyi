package com.cn.sundeinfo.sys.core.importExcel;

import cn.hutool.core.util.ObjectUtil;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ImportExcelService {

    /*
    * 获取表格所有数据
    * */
    public List<List<String []>> getExcelData(MultipartFile multipartFile) throws IOException {

        // MultipartFile 转 File
        File file = new File(multipartFile.getOriginalFilename());
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);

        //解析excel
        FileInputStream is = new FileInputStream(file);
        //获取整个excel
        HSSFWorkbook wb = new HSSFWorkbook(is);

        List<List<String []>> resource = new ArrayList<>();
        int sheetNum = wb.getNumberOfSheets();

        // 循环sheet
        for(int i = 0; i < sheetNum; i++){
            List<String []> sheetList = new ArrayList<>();
            HSSFSheet sheet = wb.getSheetAt(i);
            int rowNum = sheet.getPhysicalNumberOfRows();
            if(rowNum <= 1){
                resource.add(sheetList);
                continue;
            }

            // sheetList的第一行存放sheetName
            String [] sheetName = {sheet.getSheetName()};
            sheetList.add(sheetName);

            // 按照首行的列数读取数据
            int cellNum = sheet.getRow(0).getPhysicalNumberOfCells();
            // 循环行,不获取首行
            for(int j = 1; j < rowNum; j++){
                Row row = sheet.getRow(j);
                String [] cells = new String[cellNum];

                // 循环列
                for(int k = 0; k < cellNum; k++){
                    cells[k] = row.getCell(k) == null ? "" : row.getCell(k).toString();
                }
                sheetList.add(cells);
            }
            resource.add(sheetList);
        }
        is.close();
        wb.close();
        // 会在本地产生临时文件，用完后需要删除
        if (file.exists()) {
            file.deleteOnExit();
        }
        return resource;
    }

    /*
     * 根据sheet、行号、列号获取表格数据
     * */
    public List<Map<String,Object>> getExcelDataByXls(File file,String sheetName,int rowIndex,List<Map<String,String>> columnList) throws IOException {

        List<Map<String,Object>> resource = new ArrayList<>();

        //解析excel
        FileInputStream is = new FileInputStream(file);
        //获取整个excel
        HSSFWorkbook wb = new HSSFWorkbook(is);

        // sheet下标从0开始
        HSSFSheet sheet = wb.getSheet(sheetName);
        int rowNum = sheet.getPhysicalNumberOfRows();
        if(rowNum <= 1){
            is.close();
            wb.close();
            // 会在本地产生临时文件，用完后需要删除
            if (file.exists()) {
                file.deleteOnExit();
            }
            return resource;
        }

        // 循环行，下标从0开始
        for(int j = rowIndex - 1; j < rowNum; j++){
            Row row = sheet.getRow(j);
            Map<String,Object> cells = new HashMap<>();

            // 循环列,读取指定列
            for(int i = 0; i < columnList.size(); i++){
                int cellIndex = CellReference.convertColStringToIndex(columnList.get(i).get("index"));
                cells.put(columnList.get(i).get("key"),row.getCell(cellIndex) == null ? "" : row.getCell(cellIndex).toString());
            }
            resource.add(cells);
        }
        is.close();
        wb.close();
        // 会在本地产生临时文件，用完后需要删除
        if (file.exists()) {
            file.deleteOnExit();
        }
        return resource;
    }

    /*
     * 根据sheet、行号、列号获取表格数据
     * */
    public List<Map<String,Object>> getExcelDataByXlsx(File file,String sheetName,int rowIndex,List<Map<String,String>> columnList) throws IOException {

        //解析excel
        FileInputStream is = new FileInputStream(file);

        //获取整个excel
        XSSFWorkbook wb = new XSSFWorkbook(is);

        List<Map<String,Object>> resource = new ArrayList<>();

        // sheet下标从0开始
        XSSFSheet sheet = wb.getSheet(sheetName);
        int rowNum = sheet.getPhysicalNumberOfRows();
        if(rowNum <= 1){
            is.close();
            wb.close();
            // 会在本地产生临时文件，用完后需要删除
            if (file.exists()) {
                file.deleteOnExit();
            }
            return resource;
        }

        // 循环行，下标从0开始
        for(int j = rowIndex - 1; j < rowNum; j++){
            Row row = sheet.getRow(j);
            if(ObjectUtil.isNull(row)){
                continue;
            }
            Map<String,Object> cells = new HashMap<>();

            // 循环列,读取指定列
            for(int i = 0; i < columnList.size(); i++){
                int cellIndex = CellReference.convertColStringToIndex(columnList.get(i).get("index"));
                cells.put(columnList.get(i).get("key"),row.getCell(cellIndex) == null ? "" : row.getCell(cellIndex).toString());
            }
            resource.add(cells);
        }
        is.close();
        wb.close();
        // 会在本地产生临时文件，用完后需要删除
        if (file.exists()) {
            file.deleteOnExit();
        }
        return resource;
    }
}
