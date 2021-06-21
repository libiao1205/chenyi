package com.cn.sundeinfo.sys.core.export;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @创建人 libiao
 * @创建时间 2021/4/23
 */
@Component
public class ExportZip {
    static final int BUFFER = 8192;

    /**
     * 遍历需要压缩文件集合
     * @param pathName
     * @param zipFile
     * @throws IOException
     */
    public void compress(File zipFile,String... pathName) throws IOException {
        ZipOutputStream out =null;
        FileOutputStream fileOutputStream=null;
        CheckedOutputStream cos=null;
        try {
            fileOutputStream = new FileOutputStream(zipFile);
            cos = new CheckedOutputStream(fileOutputStream,new CRC32());
            out = new ZipOutputStream(cos);
            String basedir = "";
            for (int i=0;i<pathName.length;i++){
                compress(new File(pathName[i]), out, basedir);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if(out!=null){
                out.close();
            }
            if(fileOutputStream!=null){
                fileOutputStream.close();
            }
            if(cos!=null){
                cos.close();
            }
        }
    }

    /**
     * 下载文件
     * @param file
     * @param response
     * @return
     */
    public HttpServletResponse downFile(File file, HttpServletResponse response) {
        InputStream fis = null;
        OutputStream toClient = null;
        try {
            // 以流的形式下载文件。
            fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            // 清空response
            response.reset();
            toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            //如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            toClient.write(buffer);
            toClient.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally{
            try {
                File f = new File(file.getPath());
                f.delete();
                if(fis!=null){
                    fis.close();
                }
                if(toClient!=null){
                    toClient.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    /**
     * 压缩
     * @param file
     * @param out
     * @param basedir
     */
    private void compress(File file, ZipOutputStream out, String basedir) throws IOException {
        // 判断是目录还是文件
        if (file.isDirectory()) {
            this.compressDirectory(file, out, basedir);
        } else {
            this.compressFile(file, out, basedir);
        }
    }
    /**
     * 压缩一个目录
     * */
    private void compressDirectory(File dir, ZipOutputStream out, String basedir) throws IOException {
        if (!dir.exists()){
            return;
        }
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 递归
            compress(files[i], out, basedir + dir.getName() + "/");
        }
    }
    /**
     * 压缩一个文件
     *
     * */
    private void compressFile(File file, ZipOutputStream out, String basedir) throws IOException {
        if (!file.exists()) {
            return;
        }
        BufferedInputStream bis =null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry entry = new ZipEntry(basedir + file.getName());
            out.putNextEntry(entry);
            int count;
            byte[] data = new byte[BUFFER];
            while ((count = bis.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if(bis!=null){
                bis.close();
            }
        }
    }

}
