package qa.kb.indexer;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexOptions;

import qa.bean.KnowledgeBean;
import custom.config.MySegment;

public class Bean2Doc {

	public static Document bean2Doc(KnowledgeBean kb) {
		Document doc=new Document();
		FieldType ft=new FieldType();
		ft.setStored(true);
		ft.setTokenized(true);
		ft.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
		//三元组(subject,predicate,object)
		String s=kb.getSubject();
		String p=kb.getPredicate();
		String o=kb.getObject();
		//添加document中
		doc.add(new Field("subject",s,ft));
		doc.add(new Field("predicate",p,ft));
		doc.add(new Field("object",o,ft));
		
		
		FieldType ft1=new FieldType(ft);
		ft1.setStored(false);
		ft1.setTokenized(true);
		doc.add(new Field("all",s+"\n"+p+"\n"+o,ft1));
		
		FieldType ft2=new FieldType();
		ft2.setStored(false);
		ft2.setTokenized(false);
		ft2.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
		doc.add(new Field("primaryName",kb.getPrimaryName().replaceAll(" ", ""),ft2));
		doc.add(new Field("relation",kb.getPredicate().replace(" ", ""),ft2));
		doc.add(new Field("objectName",kb.getObject().replace(" ", ""),ft2));
		return doc;
	}
	public static Document subject2Doc(String subject) {
		Document doc=new Document();
		FieldType ft=new FieldType();
		ft.setStored(true);
		ft.setTokenized(true);
		ft.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
		doc.add(new Field("word",subject,ft));
		doc.add(new StringField("entity",subject,Store.YES));
		return doc;
	}

}
