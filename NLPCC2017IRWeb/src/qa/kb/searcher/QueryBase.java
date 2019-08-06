package qa.kb.searcher;

import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RegexpQuery;
import org.apache.lucene.search.TermQuery;

public class QueryBase {
	
	public static Query getEntityBoostQuery(String field,String word,float weight){
		return new BoostQuery( getMixQuery(field,word),weight);
	}
	public static Query getBoostQuery(String field,String word,float weight){
		return new BoostQuery(new TermQuery(new Term(field,word)),weight);
	}
	public static Query getTermQuery(String field,String word){
		return new TermQuery(new Term(field,word));
	}
	public static Query getPhraseQuery(String field,List<String> words,int slop){
		PhraseQuery.Builder builder=new PhraseQuery.Builder();
		builder.setSlop(slop);
		for(String s:words){
			builder.add(new Term(field,s));
		}
		return builder.build();
	}
	public static Query getEntityQuery(String entity){
		BooleanQuery.Builder builder=new BooleanQuery.Builder();
		TermQuery tQ1=new TermQuery(new Term("primaryName",entity));
		FuzzyQuery tQ2=new FuzzyQuery(new Term("primaryName",entity),1);
//		if(entity.length()>5)
//			tQ2=new FuzzyQuery(new Term("primaryName",entity),2);
		PrefixQuery tQ3=new PrefixQuery(new Term("primaryName",entity));
//		RegexpQuery tQ4=new RegexpQuery(new Term("primaryName",".*"+entity));
		builder.add(tQ1,Occur.SHOULD);
		builder.add(tQ2,Occur.SHOULD);
		builder.add(tQ3,Occur.SHOULD);
//		builder.add(tQ4,Occur.SHOULD);
		return builder.build();
	}
	public static Query getMixQuery(String field,String entity){
		BooleanQuery.Builder builder=new BooleanQuery.Builder();
		TermQuery tQ1=new TermQuery(new Term(field,entity));
		FuzzyQuery tQ2=new FuzzyQuery(new Term(field,entity),1);
		PrefixQuery tQ3=new PrefixQuery(new Term(field,entity));
//		RegexpQuery tQ4=new RegexpQuery(new Term(field,".*"+entity));
		builder.add(tQ1,Occur.SHOULD);
		builder.add(tQ2,Occur.SHOULD);
		builder.add(tQ3,Occur.SHOULD);
//		builder.add(tQ4,Occur.SHOULD);
		return builder.build();
	}
	
	
	

}
