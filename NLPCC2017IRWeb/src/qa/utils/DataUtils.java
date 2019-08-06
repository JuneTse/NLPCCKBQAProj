package qa.utils;

import java.util.List;

import com.hankcs.hanlp.seg.common.Term;
public class DataUtils {
	public static void main(String[] args) {
		String s="中国（盛泽）纺织电子交易中心（哈哈）";
		System.out.println(getPrimeName(s));
		String s1="《兄弟》(你好的)";
		System.out.println(getPrimeName(s1));
	}
	
	/**
	 * 去掉subject括号里的描述信息
	 * @param subject
	 * @return
	 */
	public static String getPrimeName(String subject){
//		String primaryName=subject.trim().replaceAll("[(（][^\\(\\)（）]+[\\)）]$", "");
		String primaryName=subject.trim().replaceAll("[(（][^\\(\\)（）]+[\\)）]", "").replaceAll("[(（][^\\(\\)（）]+[\\)）]", "");
//		primaryName=primaryName.replaceAll(" ","");
		return primaryName;
	}
	/*
	 * 去掉问题中括号里的内容
	 */
	public static String getQuestionClean(String question){
		return question.replaceAll("[(（][^\\(\\)（）]+[\\)）]", "").replaceAll("[(（][^\\(\\)（）]+[\\)）]", "");
	}
	public static String terms2Str(List<Term> terms){
		String s="";
		for(Term t:terms){
			s+=" "+t.word;
		}
		return s;
	}

}
