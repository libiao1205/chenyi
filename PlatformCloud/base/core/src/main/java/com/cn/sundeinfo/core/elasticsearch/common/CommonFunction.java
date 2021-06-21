package com.cn.sundeinfo.core.elasticsearch.common;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CommonFunction
{
    protected static final Properties PROPERTIES = new Properties(System.getProperties());
    protected static final String DateBaseFormat = "yyyy/MM/dd";
    protected static final String DateTimeBaseFormat = "yyyy/MM/dd HH:mm:ss";
    protected static final String Version = "1.0.0";

    public static String CreateUUIDString()
    {
        return CreateUUID().toString();
    }

    public static UUID CreateUUID()
    {
        return UUID.randomUUID();
    }

    public static String SysFileSeparator()
    {
        return PROPERTIES.getProperty("file.separator");
    }

    public static String SysEncoding()
    {
        return PROPERTIES.getProperty("file.encoding");
    }

    public static String replaceBlank(String str)
    {
        String dest = "";
        if (str != null)
        {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static Map<String, String> GetUrlParameter(String str)
    {
        Map<String, String> map = new Hashtable();
        if (null != str)
        {
            String[] p = str.split("&");
            for (int i = 0; i < p.length; i++)
            {
                String[] s = p[i].split("=");
                if (s.length > 1) {
                    map.put(s[0], s[1]);
                }
            }
        }
        return map;
    }

    public static String Null2Empty(String str)
    {
        return null == str ? "" : str;
    }

    public static String Empty2Null(String str)
    {
        return "".equalsIgnoreCase(str) ? null : str;
    }

    public static boolean IsNullOrEmpty(String str)
    {
        return (null == str) || (str.length() == 0);
    }

    public static boolean IsNullOrEmptyByObject(Object str)
    {
        return (null == str) || (str.toString().trim().length() == 0);
    }

    public static String CreateRandom(int len)
    {
        Random ran = new Random();
        String[] a = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++)
        {
            double b = ran.nextDouble();
            int c = (int)(b * a.length);
            sb.append(a[c]);
        }
        return sb.toString();
    }

    public static String CreateRandomNumer(int len)
    {
        Random ran = new Random();
        String[] a = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++)
        {
            double b = ran.nextDouble();
            int c = (int)(b * a.length);
            sb.append(a[c]);
        }
        return sb.toString();
    }

    public static String GetNowDateString()
    {
        Date dt = new Date();
        return GetLocalDateString(dt);
    }

    public static String GetNowDateString(String format)
    {
        Date dt = new Date();
        return GetLocalDateString(dt, format);
    }

    public static String GetLocalDateString(Date date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String GetLocalDateString(Date date, String format)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String GetDateByTime(Long time)
    {
        Date dt = new Date(time.longValue());
        return GetLocalDateString(dt);
    }

    public static Date AddDateTime(Date date, Integer time)
    {
        Long time_val = Long.valueOf(date.getTime() + time.intValue());
        return new Date(time_val.longValue());
    }

    public static Date AddDateTime(Integer time)
    {
        return AddDateTime(new Date(), time);
    }

    public static String ConvertToDateByExcel(String date)
    {
        return ConvertToDateByExcel(date, "yyyy/MM/dd");
    }

    public static String ConvertToDateByExcel(String date, String format)
    {
        if (null == date) {
            return null;
        }
        try
        {
            Integer month = Integer.valueOf(Integer.parseInt(date));
            Calendar c = new GregorianCalendar(1900, 0, -1);
            c.add(5, month.intValue());
            return GetLocalDateString(c.getTime());
        }
        catch (Exception localException)
        {
            try
            {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                Date dt = formatter.parse(date);
                return GetLocalDateString(dt);
            }
            catch (Exception localException1) {}
        }
        return null;
    }

    public static Date GetLocalDate(String date)
            throws ParseException
    {
        return GetLocalDate(date, "yyyy/MM/dd");
    }

    public static Date GetLocalDate(String date, String format)
            throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date dt = formatter.parse(date);
        return dt;
    }

    public static void ImageResize(String fileName, int w, int h)
            throws Exception
    {
        File file = new File(fileName);
        Image img = ImageIO.read(file);
        int width = img.getWidth(null);
        int height = img.getHeight(null);
        String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
        String name = fileName.substring(0, fileName.lastIndexOf('.'));
        String filePath = String.format("%s%sX%s.%s", new Object[] { name, Integer.valueOf(w), Integer.valueOf(h), ext });
        if (width > height) {
            h = height * w / width;
        } else {
            w = width * h / height;
        }
        BufferedImage image = new BufferedImage(w, h, 1);
        boolean ok = image.getGraphics().drawImage(img, 0, 0, w, h, null);
        if (ok)
        {
            File destFile = new File(filePath);
            ImageIO.write(image, ext, destFile);
        }
    }

    public static String ChangePath(String path, String separator)
    {
        String systemSeparator = SysFileSeparator();
        return path.replace(separator, systemSeparator);
    }

    public static String CreateDir(String dir)
    {
        String systemSeparator = SysFileSeparator();
        String[] sp = null;
        if (dir.indexOf("/") >= 0) {
            sp = dir.split("/");
        } else {
            sp = dir.split("\\\\");
        }
        String path = "";
        for (int i = 0; i < sp.length; i++) {
            if ((i == 0) || (sp[i].length() > 0))
            {
                path = path + sp[i];
                File pathStr = new File(path);
                if (!pathStr.isFile())
                {
                    path = path + systemSeparator;
                    if (!pathStr.exists()) {
                        pathStr.mkdir();
                    }
                }
            }
        }
        return path;
    }

    public static String CreateDirAndJoin(String... dir)
    {
        String path = "";
        for (int i = 0; i < dir.length; i++) {
            path = CreateDir(path + dir[i]);
        }
        return path;
    }

    public static void Unzip(String path, String outpath)
            throws Exception
    {
        Unzip(path, outpath, SysEncoding());
    }

    public static <T> T SetNullValue(Object obj, Object value)
    {
        Object rtn = null == obj ? value : obj;
        return (T)rtn;
    }

    public static void gzipFile(String path, String outpath)
            throws IOException
    {
        File file = new File(path);
        File outFile = new File(outpath);
        gzipFile(file, outFile);
    }

    public static void gzipFile(File path, File outpath)
            throws IOException
    {
        if (!outpath.exists()) {
            outpath.createNewFile();
        }
        GZIPOutputStream gzip = new GZIPOutputStream(new FileOutputStream(outpath));
        FileInputStream fileInputStream = new FileInputStream(path);
        try
        {
            int b;
            while ((b = fileInputStream.read()) >= 0) {
                gzip.write(b);
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            gzip.flush();
            gzip.close();
            fileInputStream.close();
        }
    }

    public static void Unzip(String path, String outpath, String charset)
            throws Exception
    {
        File file = new File(path);
        File outFile = null;
        ZipFile zipFile = new ZipFile(file, Charset.forName(charset));
        ZipInputStream zipInput = new ZipInputStream(new FileInputStream(file), Charset.forName(charset));
        ZipEntry entry = null;
        InputStream input = null;
        OutputStream output = null;
        while ((entry = zipInput.getNextEntry()) != null)
        {
            outFile = new File(outpath + File.separator + entry.getName());
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdir();
            }
            if ((!outFile.exists()) && (entry.isDirectory()))
            {
                outFile.mkdir();
            }
            else if (!outFile.exists())
            {
                outFile.createNewFile();
                input = zipFile.getInputStream(entry);
                output = new FileOutputStream(outFile);
                int temp = 0;
                while ((temp = input.read()) != -1) {
                    output.write(temp);
                }
                input.close();
                output.close();
            }
        }
    }

    public static String CreateMd5(String text)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            byte[] b = md.digest();

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++)
            {
                int i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String Encode(String str, String charset)
            throws UnsupportedEncodingException
    {
        return URLEncoder.encode(str, charset).replace("+", "%20");
    }

    public static String Encode(String str)
            throws UnsupportedEncodingException
    {
        return Encode(str, "utf-8");
    }

    public static String ConvertEncoding(String str, String coding)
            throws UnsupportedEncodingException
    {
        byte[] n = str.getBytes();
        return new String(n, coding);
    }

    public static boolean RegexString(String regex, String str)
    {
        return Pattern.matches(regex, str);
    }

    public static byte[] Encrypt(String content, String password)
            throws Exception
    {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(password.getBytes()));
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        byte[] byteContent = content.getBytes("utf-8");
        cipher.init(1, key);
        byte[] result = cipher.doFinal(byteContent);
        return result;
    }

    public static byte[] DesEncrypt(byte[] content, String password)
            throws Exception
    {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(password.getBytes()));
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, key);
        byte[] result = cipher.doFinal(content);
        return result;
    }

    public static String Encrypt(String data, String key, String iv)
            throws Exception
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength += blockSize - plaintextLength % blockSize;
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(1, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return new BASE64Encoder().encode(encrypted);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String DesEncrypt(String data, String key, String iv)
            throws Exception
    {
        try
        {
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(2, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            return new String(original);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
