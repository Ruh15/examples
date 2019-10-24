package com.rh.commonutils.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * pinyin4j汉字转拼音工具类
 *
 * @author zhiheng
 */
public class ChangeToPinYin {

    //pinyin4j格式类
    private static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
    //拼音字符串数组
    private static String[] pinyin;

    static {
        // 设置不带声调标识
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        // 设置字符大写
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
    }

    /**
     * 对单个字进行转换
     *
     * @param pinYinStr 需转换的汉字字符串
     * @return 拼音字符串数组
     */
    public static String getCharPinYin(char pinYinStr) {
//        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        try {
            //执行转换
            pinyin = PinyinHelper.toHanyuPinyinStringArray(pinYinStr, format);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //pinyin4j规则，当转换的符串不是汉字，就返回null
        if (pinyin == null) {
            return null;
        }

        //多音字会返回一个多音字拼音的数组，pinyiin4j并不能有效判断该字的读音
        return pinyin[0];
    }

    /**
     * 对单个字进行转换
     *
     * @param pinYinStr
     * @return
     */
    public static String getStringPinYin(String pinYinStr) {
        StringBuffer sb = new StringBuffer();
        String tempStr = null;
        //循环字符串
        for (int i = 0; i < pinYinStr.length(); i++) {

            tempStr = getCharPinYin(pinYinStr.charAt(i));
            if (tempStr == null) {
                //非汉字直接拼接
                sb.append(pinYinStr.charAt(i));
            } else {
                sb.append(tempStr);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // 多音字无法正确处理，会返回词组，只能取其中一个
        String str = "参差";
        System.out.println(getStringPinYin(str));
        char c = '你';
        System.out.println(getCharPinYin(c));
    }

}