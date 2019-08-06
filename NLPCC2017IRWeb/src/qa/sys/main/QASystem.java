package qa.sys.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.Query;

import qa.bean.Candidate;
import qa.bean.KnowledgeBean;
import qa.bean.Question;
import qa.kb.searcher.IndexSearcherBuilder;
import qa.kb.searcher.MyQueryBuilder;
import qa.kb.searcher.MySearcher;
import qa.sys.answer.Extractor;
import custom.config.BasicConfig;
import data.questions.QAPairReader;
import data.semantic.EncodedData;

public class QASystem {
	int searchNum=3000;
	private MySearcher mySearcher;
	
	public static String testDataPath=BasicConfig.testData2017;
	public static String testQuestionEntityPath=BasicConfig.testQuestionEntity2017;
	
	public static void main(String[] args) throws Exception {
		EncodedData.initTestEncodedData();
		Map<String,String> question_entity=QAPairReader.readTestQuestionEntity2Map(testQuestionEntityPath);
//		System.out.println(question_entity);
		QASystem qa=new QASystem();
		String text="秦始皇多少岁逝世的";
		long t1=System.currentTimeMillis();
		Question question=new Question();
		question.setOriginal(text);
		String entity=question_entity.get(text);
		System.out.println("TopicEntity:"+entity);
		if(entity!=null) question.setTopicEntity(entity);
		
		//QA Answer
		List<Candidate> candidates=qa.getAnswerCandidates(question, 20, false);
		for(Candidate c: candidates){
			System.out.println(c);
		}
		
		
		//QA Candidate
//		List<KnowledgeBean> docs=qa.getCandidates(question, true);
//		System.out.println(docs.size());
//		for(KnowledgeBean kb:docs){
//			System.out.println(kb);
//		}
		long t2=System.currentTimeMillis();
		System.out.println("耗时：==="+(t2-t1));
	}

	
	public QASystem() throws Exception {
		mySearcher=new MySearcher(IndexSearcherBuilder.getIndexSearcher(MySearcher.indexPath));
	}
	
	public List<String> answerQuestion(List<Candidate> answers) throws Exception{
		List<String> res=new ArrayList<String>();
		for(Candidate c:answers){
			String a=c.getKb().getObject();
			if(!res.contains(a))
				res.add(a);
		}
		return res;
	}
	/**
	 * isFilter 设为 False效果比True好，可以增加召回率, 可以解决 subject 与问题中的topic entity不匹配的问题，
	 * 比如，question: 请问中国男篮职业联赛什么时候比赛？
	 * 	   topic entity: 中国男篮职业联赛
	 * 	   subject： 中国男子篮球职业联赛
	 * @param quest
	 * @param Num
	 * @param isFilter
	 * @return
	 * @throws Exception
	 */
	public List<Candidate> getAnswerCandidates(Question question,int Num,boolean isFilter) throws Exception{
		//问题解析
		//信息检索
		List<KnowledgeBean> docs=null;
		Query q=null;
		if(question.getTopicEntity()!=null){
			q=new MyQueryBuilder(question).getTopicEntityQuery();
			docs=mySearcher.search(q,searchNum);
		}
		if(docs==null||docs.size()==0){
			System.out.println("NGram Searcher..."+question.getTopicEntity()+"\t"+question.getOriginal());
			q=new MyQueryBuilder(question).getQuery();
			docs=mySearcher.search(q,searchNum);
		}
		if(docs.size()==0)
			System.out.println(question.getOriginal());
		String text=question.getOriginal().toLowerCase();
		//答案抽取
		Extractor et=new Extractor(question);
		//List<Candidate> rs=et.extractAnswers(docs, Num, isFilter);
		List<Candidate> rs=et.extractAnswers(docs, Num, false);
		return rs;
	}
}
