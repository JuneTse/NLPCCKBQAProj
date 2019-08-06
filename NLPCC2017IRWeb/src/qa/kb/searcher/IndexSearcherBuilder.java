package qa.kb.searcher;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.MMapDirectory;

public class IndexSearcherBuilder {
	/**
	 * 创建IndexSearcher
	 * @param indexPath
	 * @return
	 */
	public static IndexSearcher getIndexSearcher(String indexPath) {
		try {
			Directory dir=FSDirectory.open(new File(indexPath).toPath());
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
