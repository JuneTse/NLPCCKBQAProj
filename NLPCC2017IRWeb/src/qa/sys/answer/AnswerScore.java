package qa.sys.answer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import qa.bean.KnowledgeBean;
import qa.bean.Question;
import qa.utils.CosSimilarity;
import qa.utils.EditDistance;
import qa.utils.FileReaderUtil;
import qa.utils.WordSimilarity;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import custom.config.MySegment;
import data.semantic.EncodedData;

public class AnswerScore {
	public static List<String> stopwords=FileReaderUtil.getStopWords();
	/**
	 * 问题与predicate的DSSM语义相似度
	 * @param question
	 * @param kb
	 * @return
	 */
	public static double dssmSim(Question question,KnowledgeBean kb){
		double score=0.0;
		String predicate=kb.getPredicate();
		String questionString=question.getOriginal();
		double[] v1;
		double[] v2;
		v1=EncodedData.encoded_questions_cnn1.get(questionString);
		if(v1==null){
			v1=EncodedData.encoded_questions_cnn1.get(questionString.replaceAll(" ", ""));
		}
		v2=EncodedData.encoded_predicates_cnn1.get(predicate);
		if(v1==null||v1.length==0){
			System.err.println("can not find this question:"+questionString);
			System.err.println(questionString+"\t"+predicate);
		}
		score+=CosSimilarity.cos(v1, v2);
		
//		v1=EncodedData.encoded_questions_cnn2.get(questionString);
//		v2=EncodedData.encoded_predicates_cnn2.get(predicate);
//		score+=CosSimilarity.cos(v1, v2);
//		
//		v1=EncodedData.encoded_questions_cnn3.get(questionString);
//		v2=EncodedData.encoded_predicates_cnn3.get(predicate);
//		score+=CosSimilarity.cos(v1, v2);
		
		return score;
	}
	/**
	 * 范围：大于1
	 * @param question
	 * @param kb
	 * @return
	 */
	public static double maxLengthMatch(Question question,KnowledgeBean kb){
		int max=0;
		//最长匹配分数最高
		if(question.getOriginal().toLowerCase().contains(kb.getPrimaryName().toLowerCase()))
			return kb.getPrimaryName().length()*1.5; //整体完全匹配分数*1.5,分词之后匹配分数低点，减少噪音干扰
		else{
			List<Term> sub=HanLP.segment(kb.getPrimaryName());
			for(Term t:sub){
				if(question.getOriginal().contains(t.word)&&max<t.word.length())
					max=t.word.length();
			}
		}
		return max;
	}
	/**
	 * 范围大于等于1
	 * @param question
	 * @param kb
	 * @return
	 */
	public static double maxNumMatch(Question question,KnowledgeBean kb){
		String focus=kb.getSubject();
		List<Term> sub=HanLP.segment(focus); 
		//不计算重复串，Set
		Set<String> set=new HashSet<String>();
		for(Term t:sub){
			set.add(t.word);
		}
		//最多单词匹配分数最高
		String s=question.getOriginal();
		int match=0;
		for(String t:set){
			if(s.contains(t)&&!stopwords.contains(t)){
				match+=1;
			}
		}
		return match;
	}
	/**
	 * 范围0~1
	 * @param question
	 * @param kb
	 * @param ws
	 * @return
	 */
	public static double semanticMatch(Question question,KnowledgeBean kb,WordSimilarity ws){
		List<String> terms=MySegment.segment2List(kb.getPredicate().toLowerCase());
		List<String> ques=question.getPredicate();
		// predicate与问句中词的语义相似度
		double max = 0.0;
		for (String t:ques) {
			String w=t.toLowerCase();
			for (String p : terms) {
				double sim = ws.similarity(w, p);
				sim+=CosSimilarity.similarity(w, p)*0.2;
				if (max < sim){
					max = sim;
				}
			}
		}
		return max;
	}
	/**
	 * 范围0~1
	 * Object分句后与问题的余弦相似度
	 * @param question
	 * @param kb
	 * @return
	 */
	public static double bestMatchSentence(Question question,KnowledgeBean kb){
		//Object分句后与问题的余弦相似度
		double maxCos = 0.0;
		Segment seg=HanLP.newSegment().enableAllNamedEntityRecognize(true);
		List<List<Term>> objects = seg.seg2sentence(kb.getObject());
		for (List<Term> lt : objects) {
			String obj = "";
			for (Term t : lt) {
				obj += t.word;
			}
			double sim = CosSimilarity.similarity(obj, question.getOriginal());
			if (maxCos < sim)
				maxCos = sim;
		}
		return maxCos;
	}
	/**
	 * 范围0~1
	 * 知识与问题的余弦相似度
	 * @param question
	 * @param kb
	 * @return
	 */
	public static double bestMatchKnowledge(Question question,KnowledgeBean kb){
		Segment seg=HanLP.newSegment().enableAllNamedEntityRecognize(true);
		List<Term> terms=seg.seg((kb.getPrimaryName()+kb.getPredicate()+kb.getObject()).toLowerCase());
		String obj="";
		for (Term t : terms) {
			if(!stopwords.contains(t.word)) obj+=t.word;
		}
		double sim = CosSimilarity.similarity(obj, question.getOriginal().toLowerCase());
		return sim;
	}
	/**
	 * 主题实体匹配
	 * @param question
	 * @param kb
	 * @return
	 */
	public static double topicEntityMatch(Question question, KnowledgeBean kb) {
		String entity=question.getTopicEntity();
		String qtext=question.getOriginal();
		double score=0;
		if(question.getOriginal().toLowerCase().contains(kb.getPrimaryName().toLowerCase().trim())){
			score+=30;	
			qtext=kb.getPrimaryName()+qtext.replace(kb.getPrimaryName(), "");
		}
		score+=EditDistance.getEditSim(kb.getPrimaryName()+kb.getPredicate(),qtext.toLowerCase())*5;
		score+=EditDistance.getEditSim(kb.getPrimaryName(), entity);
		score+=EditDistance.getEditSim(kb.getObject(), qtext.toLowerCase())*0.1;

		return score;
	}
	public static double subjectMatch(Question question, KnowledgeBean kb) {
		double score=0.0;
		if(question.getOriginal().toLowerCase().contains(kb.getPrimaryName().toLowerCase().trim()))
			score+=10;
		return score;
	}
	public static double descriptionMatch(Question question, KnowledgeBean kb) {
		//subject括号里的描述
		String sp=kb.getSubject().substring(kb.getPrimaryName().length());
		return 0;
	}

}
