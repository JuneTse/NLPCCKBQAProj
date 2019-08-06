package custom.config;

import java.util.ArrayList;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

public class MySegment {
	/**
	 * 使用HanLP分词器
	 * @param s
	 * @return
	 */
	public static List<String> segment2List(String s){
		List<Term> terms=HanLP.segment(s);
		List<String> list=new ArrayList<String>();
		for(Term t:terms){
			list.add(t.word);
		}
		return list;
	}
	/**
	 * 使用HanLP分词器
	 * @param s
	 * @return
	 */
	public static String segment(String s){
		List<Term> terms=HanLP.segment(s);
		String res="";
		for(Term t:terms){
			res+=" "+t.word;
		}
		return res.trim();
	}

}
