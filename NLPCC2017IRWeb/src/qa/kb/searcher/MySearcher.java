package qa.kb.searcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import qa.bean.KnowledgeBean;
import qa.bean.Question;
import qa.utils.DataUtils;
import custom.config.BasicConfig;

public class MySearcher {
	public static final String indexPath=BasicConfig.indexPath;
	public static final int Num=3000;
	IndexSearcher is;
	
	public static void main(String[] args) throws Exception {
		String text="对刘亦菲的爱称有哪些？	刘亦菲";
		String[] pair=text.split("\t");
		//EncodedData.initTestEncodedData();
		System.out.println(new File(indexPath).getAbsolutePath());
		
		//问题解析
		long t1=System.currentTimeMillis();
		
		Question question=new Question();
		question.setOriginal(pair[0]);
		question.setTopicEntity(pair[1]);
		System.out.println(question.getnGrams());
		
		long t3=System.currentTimeMillis();
		System.out.println("问题分析时间"+(t3-t1));
		//信息检索
//	    Query q=new MyQueryBuilder(question).getQuery();
	    Query q=new MyQueryBuilder(question).getTopicEntityQuery();
		IndexSearcher is=IndexSearcherBuilder.getMMapIndexSearcher(indexPath);
		List<KnowledgeBean> docs=new MySearcher(is).search(q,Num);
		for(KnowledgeBean kb:docs){
			System.out.println(kb.toString());
		}
		
//		System.out.println(docs.size());
//		System.out.println("抽取答案....");
//		WordSimilarity ws=new WordSimilarity();
//		long t4=System.currentTimeMillis();
//		System.out.println("检索时间"+(t4-t3));
//		// 答案抽取
//		RPCClient rpc=new RPCClient();
//		ExtractorConfig config=new ExtractorConfig(question, ws, rpc);
//		//Extractor et = ExtractorFactory.createExtractor(question.getCatogery(),config);
//		Extractor et =new Extractor(config);
//		if (et == null) {
//			et=new OTHERExtractor(config);
//			System.err.println("Error:创建Extractor失败！！");
//		}
//		System.out.println("答案过滤....");
//		List<KnowledgeBean> filters=et.filterAnswer(docs,false);
//		long t5=System.currentTimeMillis();
//		System.out.println("过滤时间"+(t5-t4));
//		System.out.println("答案排序...");
//		List<Candidate> res=et.sortedAnswer(filters);
//		long t6=System.currentTimeMillis();
//		System.out.println("排序时间"+(t6-t5));
//		System.out.println("耗时"+(t6-t1));
//		System.out.println("结果数："+res.size());
//		int i=0;
//		for(Candidate a:res){
//			if(i++>200) break;
//			System.out.println(a.getKb().getSubject()+"\t"+a.getKb().getPredicate()+
//					"\t"+a.getKb().getObject()+"\tScore:"+a.getScore());
//		}
		
	}
	
	public MySearcher(IndexSearcher is) throws Exception {
		this.is=is;
	}
	/**
	 * 检索，返回KnowledgeBean集合
	 * @param query
	 * @param sort
	 * @param indexPath
	 * @param topNum
	 * @return
	 */
	public List<KnowledgeBean> search(Query query,int topNum){
		try {
			TopDocs hits=is.search(query,topNum);
			//System.out.println("相关结果总数："+hits.totalHits);
			List<KnowledgeBean> list=new ArrayList<KnowledgeBean>();
			ScoreDoc[] docs=hits.scoreDocs;
			for(ScoreDoc sd:docs){
				KnowledgeBean kb=new KnowledgeBean();
				Document d=is.doc(sd.doc);
				kb.setId(sd.doc);
				kb.setSubject(d.get("subject"));
				kb.setPredicate(d.get("predicate"));
				kb.setObject(d.get("object"));
				String s=d.get("subject");
				kb.setPrimaryName(DataUtils.getPrimeName(s));
				kb.setScore((double) sd.score);
				list.add(kb);
			}
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
