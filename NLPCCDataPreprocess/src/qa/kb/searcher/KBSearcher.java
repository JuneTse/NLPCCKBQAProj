package qa.kb.searcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;

import qa.bean.KnowledgeBean;
import qa.utils.DataUtils;
import custom.config.BasicConfig;

public class KBSearcher {
	static String indexPath=BasicConfig.indexPath;
	IndexSearcher is=null;
	public static void main(String[] args) {
		KBSearcher ks=new KBSearcher();
		Query query=new TermQuery(new Term("object","1938"));
		List<KnowledgeBean> list=ks.search(query, 10);
		System.out.println(list);
	}
	
	public KBSearcher() {
		is=getMMapIndexSearcher(indexPath);
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
	
	/**
	 * 创建IndexSearcher
	 * @param indexPath
	 * @return
	 */
	public static IndexSearcher getMMapIndexSearcher(String indexPath) {
		try {
			Directory dir=MMapDirectory.open(new File(indexPath).toPath());
			if(DirectoryReader.indexExists(dir)){
				//获取IndexReader对象的方法
				IndexReader ir=DirectoryReader.open(dir);
				//IndexReader ir=IndexReader.open(dir);
				IndexSearcher is=new IndexSearcher(ir);
				return is;
			}else{
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
