package com.cn.sundeinfo.sys.core.export;

import cn.hutool.core.util.ObjectUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Component
public class ExportExcel {

    /*
    * 一个sheet页
    * */
    public void excel(String fileName,String sheetName,String[] titles,List<String[]> contents,HttpServletResponse response) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
        this.setTitles(wb,sheet,titles);
        this.setContent(wb,sheet,contents);
        this.downLoadExcel(wb,response,fileName);
    }

    public HSSFWorkbook createWordBook(){
        return new HSSFWorkbook();
    }

    /*
    * 添加多个sheet页
    * */
    public void  addSheet(HSSFWorkbook wb,String sheetName,String[] titles,List<String[]> contents) throws IOException {
        HSSFSheet sheet = wb.createSheet(sheetName);
        this.setTitles(wb,sheet,titles);
        this.setContent(wb,sheet,contents);
    }

    /*
    * 设置标题
    * */
    private void setTitles(HSSFWorkbook wb,HSSFSheet sheet,String[] titles) throws IOException {
        if(ObjectUtil.isNull(titles)){
            return;
        }
        //样式
        HSSFCellStyle headCss = wb.createCellStyle();
        headCss.setBorderBottom((short)1);
        headCss.setBorderTop((short)1);
        headCss.setBorderLeft((short)1);
        headCss.setBorderRight((short)1);
        headCss.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        headCss.setFillForegroundColor(HSSFColor.ROSE.index);
        headCss.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //拿到palette颜色板
        HSSFPalette palette = wb.getCustomPalette();
        //这个是重点，具体的就是把之前的颜色 HSSFColor.ROSE.index
        //替换为  RGB(248, 203, 173)
        palette.setColorAtIndex(HSSFColor.ROSE.index, (byte) 248, (byte) 203, (byte) 173);

        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("仿宋_GB2312");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        headCss.setFont(font);
        //创建首行
        HSSFRow row = sheet.createRow(0);
        for(int i = 0; i < titles.length; i++){
            sheet.setColumnWidth(i, 20 * 256);
            CellRangeAddress column = new CellRangeAddress(0,0,i,i);
            sheet.addMergedRegion(column);
            //内容
            HSSFCell title1Cell = row.createCell(i);
            title1Cell.setCellValue(new HSSFRichTextString(titles[i]));
            title1Cell.setCellStyle(headCss);
        }
    }

    /*
    * 循环将数据存入excel
    * */
    private void setContent(HSSFWorkbook wb,HSSFSheet sheet,List<String[]> list){
        if(ObjectUtil.isNull(list)){
            return;
        }
        HSSFCellStyle css = wb.createCellStyle();
        css.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        css.setBorderBottom((short)1);
        css.setBorderTop((short)1);
        css.setBorderLeft((short)1);
        css.setBorderRight((short)1);

        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Times New Roman");
        css.setFont(font);
        css.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
        for (int rowIndex = 0; rowIndex < list.size(); rowIndex++) {
            String[] rowContent = list.get(rowIndex);
            HSSFRow row = sheet.createRow(rowIndex + 1);
            for(int columnIndex = 0; columnIndex < rowContent.length; columnIndex++){
                HSSFCell cell1 = row.createCell(columnIndex);
                cell1.setCellValue(new HSSFRichTextString(rowContent[columnIndex]));
                cell1.setCellStyle(css);
            }
        }
    }

    /*
    * 下载excel
    * */
    public void downLoadExcel(HSSFWorkbook wb, HttpServletResponse response, String fileName) {
        // logger.info("************************开始下载excel************************");
        try {
            response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName+".xls", "utf-8"));
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            wb.write(response.getOutputStream());
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // logger.info("************************下载excel完成************************");
    }

}
