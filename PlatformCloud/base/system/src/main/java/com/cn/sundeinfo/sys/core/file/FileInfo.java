package com.cn.sundeinfo.sys.core.file;

import cn.hutool.core.bean.BeanUtil;
import com.cn.sundeinfo.core.enums.CommonStatusEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

/**
 * @创建人 libiao
 * @创建时间 2021/4/26
 */
@Component
public class FileInfo {

    public boolean saveFile(String filePath,String newFileName,MultipartFile multipartFile){
        boolean flag = true;
        OutputStream os = null;
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            flag = false;
            e.printStackTrace();
        }

        try {
            File file = new File(filePath);
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            if (!file.exists()) {
                file.mkdirs();
            }
            os = new FileOutputStream(file.getPath() + File.separator + newFileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            os.close();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

}
