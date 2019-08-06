package qa.sys.answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import qa.bean.Candidate;
import qa.bean.KnowledgeBean;
import qa.bean.Question;
import qa.utils.CosSimilarity;
import qa.utils.DataUtils;

public  class Extractor {
	protected Question question;
	protected boolean isFilter;
	
	public Extractor(Question question) throws Exception {
		this.question=question;
		isFilter=true;
	}
	
	public int isAccept(KnowledgeBean kb){
		String subject=kb.getPrimaryName();
		String text=question.getOriginal();
		if(text.contains(subject.trim().toLowerCase())){
			return 1;
		}
		return 0;
	}
	
	/**
	 * 获取过滤后的候选知识三元组
	 * @param irResults
	 * @param isFilter 是否执行过滤
	 * @return
	 */
	public List<KnowledgeBean> filterAnswer(List<KnowledgeBean> irResults,boolean isFilter){
		if(!isFilter)
			return irResults;
		List<KnowledgeBean> list=new ArrayList<KnowledgeBean>();
		for(KnowledgeBean kb:irResults){
			if(isAccept(kb)!=0)
				list.add(kb);
		}
		return list;
	}
	
	/**
	 * 返回排序的候选知识三元组
	 * @return
	 * @throws Exception 
	 */
	public List<Candidate> sortedAnswer(List<KnowledgeBean> cadidate) throws Exception{
		List<Candidate> list=new ArrayList<Candidate>();
		for(KnowledgeBean kb:cadidate){
			Candidate an=new Candidate();
			an.setKb(kb);
			an.setScore(score(kb));
			list.add(an);
		}
		Collections.sort(list);
		return list;
	}
	
	/**
	 * 为每个知识三元组评分
	 * @return
	 * @throws Exception 
	 */
	public double score(KnowledgeBean kb) throws Exception {
		Double score=0.0;
		//主题实体匹配
		if(question.getTopicEntity()!=null)
			score+=AnswerScore.topicEntityMatch(question,kb);
		else {
			score+=AnswerScore.subjectMatch(question,kb);
		}
		//包含predicate
//		score+=containPredicate(kb)*5;   
		//lucene检索评分
//		score+=kb.getScore();
		//问题与predicate的DSSM语义相似度
		score+=AnswerScore.dssmSim(question, kb)*5; 
		
		//BM25计算相似度
//		score+=bm25Sim.getBM25Sim(question.getOriginal(), kb.getPredicate());
		if(score.equals(Double.NaN)){
			System.out.println(score);
			score=0.0;
		}
		return score;
	}
	/**
	 * 抽取答案三元组
	 * @param k
	 * @return 每个答案，及其分数
	 * @throws Exception 
	 */
	public List<Candidate> extractAnswers(List<KnowledgeBean> irResults,int k,boolean isFilter) throws Exception{
		List<Candidate> answers=sortedAnswer(filterAnswer(irResults,isFilter)); //filter设为false
//		//如果过滤设为true找不到答案，就设为false重新排序
//		if(isFilter==true&&answers.size()==0)
//			answers=sortedAnswer(filterAnswer(irResults,false));
//		System.out.println("candidate Num:"+answers.size());
		List<Candidate> candidates=new ArrayList<Candidate>();
		for(int i=0;i<k&&i<answers.size();i++){
			candidates.add(answers.get(i));
		}
		return candidates;
	}
	/**
	 * 抽取答案
	 * @param kb
	 * @return
	 */
	public String extractAnswer(KnowledgeBean kb){
		return kb.getObject();
	}
	
	protected double containPredicate(KnowledgeBean kb) {
		double res=0;
		String q=question.getOriginal().toLowerCase().trim().replaceAll(" ",""); //去掉空格
		q=DataUtils.getQuestionClean(q);
		String predicate=kb.getPredicate().toLowerCase().trim().replaceAll(" ", "");
		if(q.contains(predicate))
			res+=0.2;
		res+=CosSimilarity.getStrCosSimilar(q, predicate);
		int i=0;
		for(char c:predicate.toCharArray()){
			if(q.contains(c+"")){
				i++;
			}
		}
		res=i*1.0/predicate.length();
		
		return res;
	}
	protected double containSubject(KnowledgeBean kb) {
		double score=0.;
		String s=question.getTopicEntity();//question.getOriginal().toLowerCase().trim().replaceAll(" ",""); //去掉空格
		s=DataUtils.getQuestionClean(s);
		String subject=kb.getPrimaryName().toLowerCase().trim().replaceAll(" ", "");
		int i=0;
		for(char c:subject.toCharArray()){
			if(s.contains(c+"")){
				i++;
			}
		}
		score=i*1.0/s.length()*10;
		String entity=question.getTopicEntity();
		if(question.getOriginal().toLowerCase().contains(kb.getPrimaryName().toLowerCase().trim()))
			score+=1;
//		if(kb.getPrimaryName().equalsIgnoreCase(entity.trim())||kb.getSubject().equalsIgnoreCase(entity.trim()))
//			return score+10;
//		else if(kb.getPrimaryName().contains(entity.trim().toLowerCase())){
//			//return 5;
//			return score+10; //+8
//		}
		return score;
	}
}
