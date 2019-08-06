package data.datasets;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import lucene.search.QueryBase;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import qa.bean.Candidate;
import qa.bean.KnowledgeBean;
import qa.bean.Question;
import qa.kb.searcher.KBSearcher;
import qa.utils.DataUtils;
import qa.utils.EditDistance;
import custom.config.BasicConfig;
import custom.config.MySegment;

public class TripleExtractor {
	public static String testDataPath=BasicConfig.testDataClean;
	public static String testDataPath2017=BasicConfig.testData2017Clean;
	public static String trainDataPath=BasicConfig.trainDataClean;
	
	public static String trainTriplePath=BasicConfig.trainTriplePath;
	public static String testTriplePath=BasicConfig.testTriplePath;
	public static String testTriplePath2017=BasicConfig.testTriplePath2017;
	public String wrongTrainTriplePath=BasicConfig.wrongTrainTriplePath;
	
	
	public static void main(String[] args) throws Exception {
		TripleExtractor ex=new TripleExtractor();
		ex.extractTriple(trainDataPath,trainTriplePath);
		ex.extractTriple(testDataPath,testTriplePath);
		ex.extractTriple(testDataPath2017, testTriplePath2017);
	}
	/**
	 * 根据问答对从知识库中检索出相应的三元组
	 * @throws Exception
	 */
	public void extractTriple(String dataPath,String outpath) throws Exception{
		File file=new File(outpath);
		FileWriter fw=new FileWriter(file);
		FileWriter fw2=new FileWriter(new File(outpath+"_no_triple.txt"));
		FileWriter fw3=new FileWriter(new File(outpath+"_multi_triple.txt"));
		KBSearcher searcher=new KBSearcher();
		List<Question> qs=readQAPairs(dataPath);
		System.out.println(qs.size());
		
		for(Question question:qs){
			//构造查询语句
			Query query=getQAPairQuery(question);
			//检索
			List<Candidate> list = search(searcher, query,question);
			System.out.println(question.getOriginal()+"\t"+question.getAnswer()+"\t"+list.size());
			List<String>  triples=new ArrayList<String>();
			for(Candidate c:list){
				KnowledgeBean a=c.getKb(); 
				String triple=question.getOriginal()+"\t"+a.getPrimaryName()+"\t"+a.getPredicate()+"\t"+a.getObject();
				if(!triples.contains(triple))
					triples.add(triple);
				System.out.println(c);
			}
			if(triples.size()!=0){
				if(triples.size()==1){
				fw.write(triples.get(0)+"\n");
				fw.flush();}
				
				//如果大于1个匹配的三元组，写到文件3
				if(triples.size()>1){
					for(String c:triples){
						fw3.write(c+"\n");
						fw3.flush();
					}
				}
			}else{
				fw2.write("<question id="+question.getId()+">"+question.getOriginal()+"\t"+question.getAnswer()+"\n");
				fw2.flush();
			}
		}
		fw.close();
		fw2.close();
		fw3.close();
		
	}
	private List<Question> readQAPairs(String dataPath) throws Exception {
		BufferedReader br=new BufferedReader(new FileReader(new File(dataPath)));
		String line=null;
		List<Question> qs=new ArrayList<Question>();
		while((line=br.readLine())!=null){
			System.out.println(line);
			String[] qa=line.trim().split("\\|{3}");
			if(qa.length<3)
				continue;
			String id=qa[0];
			String question=qa[1];
			String answer=qa[2];
			Question q=new Question();
			q.setOriginal(question);
			q.setAnswer(answer);
			q.setId(id);
			qs.add(q);
		}
		return qs;
	}
	private Query getQAPairQuery(Question question) {
		BooleanQuery.Builder builder=new BooleanQuery.Builder();
		List<String> list=MySegment.segment2List(question.getAnswer());
		builder.add(new TermQuery(new Term("objectName",question.getAnswer().replace(" ", ""))), Occur.MUST);
		for(String t:list){
			builder.add(new BoostQuery(new TermQuery(new Term("object", t)), 1.0f),Occur.SHOULD);
		}
		for(String s:question.getnGrams()){
			builder.add(new BoostQuery(new TermQuery(new Term("subject", s)),0.5f),Occur.SHOULD);
			builder.add(new BoostQuery(new TermQuery(new Term("primaryName", s)),0.8f),Occur.SHOULD);
		}
		return builder.build();
	}
	private List<Candidate> search(KBSearcher searcher, Query query,Question question) throws Exception {
		List<KnowledgeBean> list=searcher.search(query, 50000);
		List<KnowledgeBean> triples=new ArrayList<KnowledgeBean>();
		String q=question.getOriginal();
		String a=question.getAnswer().toLowerCase().trim();
		for(KnowledgeBean kb:list){
			String s=DataUtils.getPrimeName(kb.getSubject().toLowerCase());
			String o=kb.getObject().toLowerCase().trim();
			if(!s.equals("")&&q.toLowerCase().contains(s.toLowerCase())&&o.equalsIgnoreCase(a)){
				triples.add(kb);
			}
		}
		//重新排序
		List<Candidate> rs=reSort(triples, 10, question);
		return rs;
	}

	public List<Candidate> reSort(List<KnowledgeBean> triples, int i, Question question) throws Exception {
		List<Candidate> list=new ArrayList<Candidate>();
		for(KnowledgeBean kb:triples){
			Candidate cand=new Candidate();
			cand.setScore(score(kb,question));
			cand.setKb(kb);
			list.add(cand);
		}
		//排序
		Collections.sort(list,new Comparator<Candidate>() {
			@Override
			public int compare(Candidate o1, Candidate o2) {
				return -o1.getScore().compareTo(o2.getScore());
			}
		});
		return list;
	}
	
	/**
	 * 为每个知识三元组评分
	 * @return
	 * @throws Exception 
	 */
	public double score(KnowledgeBean kb,Question question) throws Exception {
		double score=0.0;
		String q=question.getOriginal().replace(" ", "");
		//主题实体匹配
		if(q.contains(kb.getPrimaryName())){
			score+=1;
			score+=EditDistance.getEditSim(kb.getPrimaryName()+q.replace(kb.getPrimaryName(),""), kb.getPrimaryName()+kb.getPredicate());
		}
		//包含predicate
		if(q.contains(kb.getPredicate().replace(" ", ""))){
			score+=1;
		}
		
		//lucene检索评分
		score+=kb.getScore();
		return score;
	}

}
