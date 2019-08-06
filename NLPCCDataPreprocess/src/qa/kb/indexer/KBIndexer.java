package qa.kb.indexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

import com.hankcs.lucene.HanLPAnalyzer;

import qa.bean.KnowledgeBean;
import qa.utils.DataUtils;
import custom.config.BasicConfig;

public class KBIndexer {
	static String lowcaseKnowledgePath=BasicConfig.lowcaseKnowledgePath;
	static File file=new File(lowcaseKnowledgePath);
	static Analyzer analyzer=new HanLPAnalyzer();//new SimpleAnalyzer();
	static String indexPath=BasicConfig.indexPath;
	
	public static void main(String[] args) {
		KBIndexer indexer=new KBIndexer();
		indexer.createIndex(analyzer, indexPath, file);
	}
	public void createIndex(Analyzer analyzer,String indexPath,File source){
		//创建IndexWriter
		IndexWriter iw=null;
		try {
			iw=getIndexWriter(analyzer, indexPath);
			//写入索引中
			indexAll(iw,source);
			if(iw!=null){
				iw.commit();
				iw.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * file中的所有知识都添加到索引中
	 * @param iw
	 * @param file
	 * @throws Exception 
	 */
	private void indexAll(IndexWriter iw, File file) throws Exception {
		FileInputStream is=new FileInputStream(file);
		BufferedReader br=new BufferedReader(new InputStreamReader(is));
		String line=br.readLine();
		int id=0;
		
		while(line!=null){
			KnowledgeBean b=new KnowledgeBean();
		    String[] s=line.split("\\|{3}");
		    //System.out.println(line);
		    b.setId(id++);
		    b.setSubject(s[0].trim());
		    b.setPredicate(s[1].trim());
		    b.setObject(s[2].trim());
		    String primaryName=DataUtils.getPrimeName(s[0]);
		    b.setPrimaryName(primaryName);
		    addDoc(iw,b);
			line=br.readLine();
			if(id%100000==0){
				iw.flush();
				iw.commit();
				System.out.println("100000....");
			}
		}
	}
	/**
	 * 添加一条文档索引
	 * @param iw
	 * @param kb
	 * @throws IOException
	 */
	public void addDoc(IndexWriter iw,KnowledgeBean kb) throws IOException{
		Document doc=Bean2Doc.bean2Doc(kb);
		iw.addDocument(doc);
	}
	/**
	 * 创建IndexWriter
	 * @param analyzer
	 * @param indexPath
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private IndexWriter getIndexWriter(Analyzer analyzer,String indexPath) throws Exception{
			Path path=new File(indexPath).toPath();
			FSDirectory dir=FSDirectory.open(path);
			//创建分词器
			IndexWriterConfig conf=new IndexWriterConfig( analyzer);
			IndexWriter iw=new IndexWriter(dir, conf);
			return iw;
	}

}
