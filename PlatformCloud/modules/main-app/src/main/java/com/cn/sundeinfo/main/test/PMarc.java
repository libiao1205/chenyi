package com.cn.sundeinfo.main.test;

/**
 * @创建人 libiao
 * @创建时间 2021/4/27
 */
public class PMarc {
    String marc1 = null;//第一段标识
    String marc2 = null;//第二段目次
    String marc3 = null;//第三段记录
    String marc =null;//marc数据
    char _RecordSplitChar = '\u001D';  // 区间分隔符
    char _FieldSplitChar = '\u001E';  // 字段分割符
    char _SubFieldSplitChar = '\u001F';  // 子字段分隔符

    public String pjMarc() {
        //记录区
        String s1="  "+_SubFieldSplitChar+"a"+_SubFieldSplitChar+"d";    //010  isbn+价格
        String s2=_FieldSplitChar+"1 "+_SubFieldSplitChar+"a"+_SubFieldSplitChar+"f";   //200 题名+作者
        String s3=_FieldSplitChar+"  "+_SubFieldSplitChar+"c";     //210 出版社
        String s4=_FieldSplitChar+"  "+_SubFieldSplitChar+"a" ;    //690 中图分类号
        marc3=s1+s2+s3+s4;

        marc2="010"+String.format("%04d", s1.length())+String.format("%05d", 0)
                +"200"+String.format("%04d", s2.length())+String.format("%05d", 0+s1.length())
                +"210"+String.format("%04d", s3.length())+String.format("%05d", 0+s1.length()+s2.length())
                +"690"+String.format("%04d", s4.length())+String.format("%05d", 0+s1.length()+s2.length()+s3.length())
        ;

        marc1=String.format("%05d", marc3.length()+marc2.length()+1)
                +"pam0"+" 22"+String.format("%05d",marc2.length()+1+24)+"   "+"450 "
        ;

        marc=marc1+marc2+_FieldSplitChar+marc3+_RecordSplitChar;

        return marc;
    }
}
