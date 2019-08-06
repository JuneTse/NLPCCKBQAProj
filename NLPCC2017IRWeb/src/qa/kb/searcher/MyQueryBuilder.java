package qa.kb.searcher;

import java.util.List;

import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.Query;

import qa.bean.Question;
import qa.utils.DataUtils;
import custom.config.MySegment;

public class MyQueryBuilder {
	private Question question;
	public MyQueryBuilder(Question question){
		this.question=question;
	}
	
	public MyQueryBuilder() {
	}
	/**
	 * 使用语义解析结果进行查询
	 * @return
	 */
	public Query getQuestionParserQuery() {
		BooleanQuery.Builder builder=new BooleanQuery.Builder();
		BooleanQuery.setMaxClauseCount(5120);
//		builder.add(getCombineQuery(),Occur.SHOULD);
		builder.add(getTopicEntityQuery(),Occur.SHOULD);
		builder.add(getNGramQuery(),Occur.SHOULD);
		builder.add(getPredicateQuery(),Occur.SHOULD);
		return builder.build();
	}
	public Query getQuery(){
		BooleanQuery.Builder builder=new BooleanQuery.Builder();
		BooleanQuery.setMaxClauseCount(5120);
		builder.add(getCombineQuery(),Occur.SHOULD);
		if(question.getTopicEntity()!=null)
			builder.add(getTopicEntityQuery2(),Occur.SHOULD);
		builder.add(getNGramQuery(),Occur.SHOULD);
		return builder.build();
	}
	/**
	 * 按predicate查询
	 * @return
	 */
	public Query getPredicateQuery(){
		BooleanQuery.Builder builder=new BooleanQuery.Builder();
		for(String predicate:question.getPredicate()){
			builder.add(QueryBase.getBoostQuery("predicate", predicate, 10.0f),Occur.SHOULD);
			builder.add(new BoostQuery(QueryBase.getMixQuery("relation",predicate.replaceAll(" ", "")),10.0f),Occur.SHOULD);
			List<String> terms=MySegment.segment2List(predicate);
			for(String word:terms){
				builder.add(QueryBase.getBoostQuery("predicate", word, 6),Occur.SHOULD);
			}
		}
		return builder.build();
	}	
	/**
	 * 在subject+predicate+object域中查询
	 * @return
	 */
	public Query getCombineQuery(){
		BooleanQuery.Builder builder=new BooleanQuery.Builder();
		List<String> ts=MySegment.segment2List(question.getOriginal());
		for(String t:ts){
			builder.add(QueryBase.getBoostQuery("all", t, 1.0f),Occur.SHOULD);
		}
		return builder.build();
	}
	
	
	/**
	 * 主题实体查询
	 * @return
	 */
	public Query getTopicEntityQuery(){
		BooleanQuery.Builder builder=new BooleanQuery.Builder();
		BooleanQuery.setMaxClauseCount(20480);
		String s=question.getTopicEntity();
		String entity=s.replaceAll(" ", "");
		builder.add(new BoostQuery(QueryBase.getEntityQuery(entity), 20),Occur.SHOULD);
		
		builder.add(QueryBase.getEntityBoostQuery("subject", s, 10),Occur.SHOULD);
		builder.add(QueryBase.getEntityBoostQuery("primaryName", entity, 10),Occur.SHOULD);
		return builder.build();
	}
	/**
	 * 如果使用getTopicEnityQuery没有找到，就使用getTopicEnityQuery2和Ngram来检索
	 * @return
	 */
	public Query getTopicEntityQuery2(){
		BooleanQuery.Builder builder=new BooleanQuery.Builder();
		BooleanQuery.setMaxClauseCount(20480);
		String s=question.getTopicEntity();
		String entity=s.replaceAll(" ", "");
		builder.add(new BoostQuery(QueryBase.getEntityQuery(entity), 20),Occur.SHOULD);
		
		List<String> terms=MySegment.segment2List(s);
		builder.add(QueryBase.getEntityBoostQuery("subject", s, 10),Occur.SHOULD);
		builder.add(QueryBase.getEntityBoostQuery("primaryName", entity, 10),Occur.SHOULD);
		for(String word:terms){
			builder.add(QueryBase.getBoostQuery("subject", word, 6),Occur.SHOULD);
			builder.add(QueryBase.getBoostQuery("primaryName", word.replaceAll(" ", ""), 6),Occur.SHOULD);
		}
		return builder.build();
	}
	public Query getNGramQuery(){
		BooleanQuery.Builder builder=new BooleanQuery.Builder();
		BooleanQuery.setMaxClauseCount(20480);
		for(String s:question.getnGrams()){
			String entity=s.replaceAll(" ", "");
//			builder.add(new BoostQuery(QueryBase.getEntityQuery(entity), 20),Occur.SHOULD);
			builder.add(QueryBase.getEntityBoostQuery("subject", s, 8),Occur.SHOULD);
//			builder.add(QueryBase.getEntityBoostQuery("primaryName", s.replaceAll(" ", ""), 10),Occur.SHOULD);
			builder.add(QueryBase.getEntityBoostQuery("primaryName", DataUtils.getPrimeName(entity), 10),Occur.SHOULD);
		}
		return builder.build();
	}
	
	
	public Query getTermsQuery(List<String> words,String field,float score){
		BooleanQuery.Builder builder=new BooleanQuery.Builder();
		for(String s:words){
			builder.add(QueryBase.getTermQuery(field, s), Occur.SHOULD);
			builder.add(QueryBase.getBoostQuery(field, s, score), Occur.SHOULD);
		}
		return builder.build();
	}
	
	public Query getStringQuery(String str,String field){
		List<String> ts=MySegment.segment2List(str);
		BooleanQuery.Builder builder=new BooleanQuery.Builder();
		for(String t:ts){
			builder.add(QueryBase.getTermQuery(field, t), Occur.SHOULD);
		}
		return builder.build();
	}
	

	/**
	 * 根据问答对从知识库中检索出相应的三元组
	 * @param answer
	 * @return
	 */
	public Query getQAPairQuery(String answer) {
		BooleanQuery.Builder builder=new BooleanQuery.Builder();
		List<String> list=MySegment.segment2List(answer);
		//builder.add(getKeyWordQuery(),Occur.SHOULD);
		//builder.add(getPredicateQuery(),Occur.SHOULD);
		if(question.getTopicEntity()!=null)
			builder.add(getTopicEntityQuery(),Occur.SHOULD);
		if(question.getnGrams()!=null)
			builder.add(getNGramQuery(),Occur.SHOULD);
		for(String t:list){
			builder.add(QueryBase.getBoostQuery("object", t, 1.0f),Occur.SHOULD);
		}
		
		return builder.build();
	}


}
