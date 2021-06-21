package com.cn.sundeinfo.main.modular.controller;

//import net.handle.hdllib.*;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.PrivateKey;

public class Handle {

//    private static String prefix = "20.500.12316";
//    private static String path = "D:/admpriv.bin";
//
//    public static String getBinPath() throws IOException {
//        ClassPathResource classPathResource = new ClassPathResource("admpriv.bin");
//        // 获得File对象，当然也可以获取输入流对象
//        File file = classPathResource.getFile();
//        return file.getPath();
//    }
//
//    public static void main(String[] args) {
//        //该id是内部生成的id，比如a开头的民国书刊，b开头的是地方志...
////        String id = "a0000001";
////        String handle = prefix + "/" + id;
////        String url = "http://www.youku.com";
////        Handle.regist(handle, url);
//    }
//
//    static String regist(String id) throws IOException {
//
//        String url = "http://www.sundeinfo.cn";
//        String handle = prefix + "/" + id;
//        //判断是否注册
//        Boolean handleRegistered = isHandleRegistered(handle);
//        if (handleRegistered == null) return "";
//
//        //打印
//        System.out.println("Regist handle " + handle);
//
//        //注册
//        AbstractRequest req;
//        try {
//            //初始化
//            AdminRecord admin = new AdminRecord(
//                    getHandleAuthority().getBytes("UTF8"), 300,
//                    true, true, true, true, true, true,
//                    true, true, true, true, true, true
//            );
//            int timestamp = (int) (System.currentTimeMillis() / 1000);
//            HandleValue[] val = {
//                    new HandleValue(100, "HS_ADMIN".getBytes("UTF8"), Encoder.encodeAdminRecord(admin), HandleValue.TTL_TYPE_RELATIVE, 86400, timestamp, null, true, true, true, false),
//                    new HandleValue(1, "URL".getBytes("UTF8"), url.getBytes(), HandleValue.TTL_TYPE_RELATIVE, 86400, timestamp, null, true, true, true, false)
//            };
//
//            //获取密钥
//            PublicKeyAuthenticationInfo auth = getAuthInfo(prefix);
//
//            //更新
//            if (handleRegistered) req = new ModifyValueRequest(handle.getBytes("UTF8"), val, auth);
//
//            //创建
//            else req = new CreateHandleRequest(handle.getBytes("UTF8"), val, auth);
//
//            //响应
//            HandleResolver resolver = new HandleResolver();
//            resolver.traceMessages = true;
//            AbstractResponse response = resolver.processRequest(req);
//
//            //结果
//            if (response.responseCode == AbstractMessage.RC_SUCCESS) {
//                System.out.println("Success: " + response);
//            }
//            else System.out.println("Error: " + response);
//
//            return response.toString();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return "error";
//        }
//    }
//
//    private static Boolean isHandleRegistered(String handle) throws IOException {
//        boolean handleRegistered = false;
//        ResolutionRequest req = buildResolutionRequest(handle);
//        if (req == null) {
//            System.out.println("发送handle请求失败");
//            return null;
//        }
//        AbstractResponse response = null;
//        HandleResolver resolver = new HandleResolver();
//        try {
//            response = resolver.processRequest(req);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        if ((response != null && response.responseCode == AbstractMessage.RC_SUCCESS)) {
//            System.out.println("Handle " + handle + " registered");
//            handleRegistered = true;
//        }
//        return handleRegistered;
//    }
//
//    private static ResolutionRequest buildResolutionRequest(final String handle) throws IOException {
//        PublicKeyAuthenticationInfo auth = getAuthInfo(prefix);
//        ResolutionRequest req = new ResolutionRequest(Util.encodeString(handle), null, null, auth);
//        req.certify = false;
//        req.cacheCertify = true;
//        req.authoritative = false;
//        req.ignoreRestrictedValues = true;
//        return req;
//    }
//
//    private static PublicKeyAuthenticationInfo getAuthInfo(String prefix) throws IOException {
//        byte[] key = readKey(path);
//        PrivateKey privkey = readPrivKey(key, path);
//        String authHandle = getHandleAuthority();
//        return new PublicKeyAuthenticationInfo(Util.encodeString(authHandle), 300, privkey);
//    }
//
//    private static PrivateKey readPrivKey(byte[] key, final String file) {
//        try {
//            return Util.getPrivateKeyFromBytes(Util.decrypt(key, null), 0);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        }
//    }
//
//    private static byte[] readKey(String path) {
//        FileInputStream fis = null;
//        try {
//            File file = new File(path);
//            if (!file.exists()) {
//                System.out.println("私钥文件不存在");
//                return null;
//            }
//            fis = new FileInputStream(file);
//            byte[] key = new byte[(int) file.length()];
//            int n = 0;
//            while (n < key.length) {
//                key[n++] = (byte) fis.read();
//            }
//            return key;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return null;
//        } finally {
//            try {
//                if (fis != null) fis.close();
//            } catch (Exception ignored) {}
//        }
//    }
//
//    private static String getHandleAuthority() {
//        return "0.NA/" + prefix;
//    }
}
