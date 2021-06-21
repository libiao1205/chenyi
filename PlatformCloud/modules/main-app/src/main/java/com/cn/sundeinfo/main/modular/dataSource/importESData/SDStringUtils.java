package com.cn.sundeinfo.main.modular.dataSource.importESData;

import com.hankcs.hanlp.HanLP;

public class SDStringUtils {

    static public String convertZHCNForCharacter(String data) {
        if (data == null) return null;
        String tmp = HanLP.convertToSimplifiedChinese(data);
        return tmp;
    }

    static public String convertTWCNForCharacter(String data) {
        if (data == null) return null;
        String tmp = HanLP.convertToTraditionalChinese(data);
        return tmp;
    }
}
