package com.mome.pinyin;


import java.util.Comparator;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * ��������
 * */
public class LanguageComparator_CN implements Comparator<String> {
	

	public int compare(String ostr1, String ostr2) {
		String py1 = getPingYin(ostr1);  
        String py2 =getPingYin(ostr2);  
        // 判断是否为空""  
        if (isEmpty(py1) && isEmpty(py2))  
            return 0;  
        if (isEmpty(py1))  
            return -1;  
        if (isEmpty(py2))  
            return 1;  
        String str1 = "";  
        String str2 = "";  
        try {  
            str1 = ((getPingYin(ostr1)).toUpperCase()).substring(0, 1);  
            str2 = ((getPingYin(ostr2)).toUpperCase()).substring(0, 1);  
        } catch (Exception e) {  
            // TODO: handle exception  
            System.out.println("某个str为\" \" 空");  
        }  
        return str1.compareTo(str2);  
  }

	
	private boolean isEmpty(String str) {  
        return "".equals(str.trim());  
    } 
	/** 
     *  
     * @param inputString 
     * @return 
     */  
     public static String getPingYin(String inputString) {  
         HanyuPinyinOutputFormat format = new  
         HanyuPinyinOutputFormat();  
         format.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
         format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
         format.setVCharType(HanyuPinyinVCharType.WITH_V);  

         char[] input = inputString.trim().toCharArray();  
         String output = "";  

         try {  
             for (int i = 0; i < input.length; i++) {  
                 if (java.lang.Character.toString(input[i]).  
                 matches("[\\u4E00-\\u9FA5]+")) {  
                     String[] temp = PinyinHelper.  
                     toHanyuPinyinStringArray(input[i],  
                     format);  
                     output += temp[0];  
                 } else  
                     output += java.lang.Character.toString(  
                     input[i]);  
             }  
         } catch (BadHanyuPinyinOutputFormatCombination e) {  
             e.printStackTrace();  
         }  
         return output;  
     }  

	// ��ú���ƴ�������ַ�
	private String pinyin(char c) {
		String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c);
		if (pinyins == null) {
			return null;
		}
		return pinyins[0];
	}

}
