package qa.utils;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class MyAnaylzer {
	public static void main(String[] args) throws IOException {
		String seperator="|";
		String text="基于 java 语言 开发 的轻量 级的中文分词工具包entity.primaryName";
		Analyzer analyzer=new SimpleAnalyzer();
		StringBuffer sb=new StringBuffer();
	    //分词
		TokenStream ts=analyzer.tokenStream("ss", new StringReader(text));
		ts.reset();
		CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);

		while(ts.incrementToken()){
			sb.append(term.toString()+seperator);
		}
		System.out.println(sb.toString());
		ts.close();
		analyzer.close();
	}
}
