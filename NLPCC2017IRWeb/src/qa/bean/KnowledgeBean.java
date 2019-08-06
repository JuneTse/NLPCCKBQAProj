package qa.bean;

import qa.utils.CosSimilarity;
import qa.utils.DataUtils;


public class KnowledgeBean {
	private int id;
	private String primaryName;
	private String subject;
	private String predicate;
	private String object;
	private Double score=0.0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPrimaryName() {
		return primaryName;
	}
	public void setPrimaryName(String primaryName) {
		this.primaryName = primaryName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPredicate() {
		return predicate;
	}
	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	@Override
	public String toString() {
		String s=subject+"|||"+predicate+"|||"+object;
		return s;
	}
	public void buildKB(String k){
		String[] s=k.split("\\|{3}");
		assert s.length<=4&&s.length>=3;
		setSubject(s[0]);
		setPredicate(s[1]);
		setObject(s[2]);
		primaryName=DataUtils.getPrimeName(s[0]);
		if(s.length==4){
			Double sc=Double.parseDouble(s[3]);
			setScore(sc);
		}
	}
	/**
	 * 计算两个KnowledgeBean的相似度
	 * @param kb
	 * @return
	 */
	public Double getSim(KnowledgeBean kb) {
		Double score=0.0;
		score+=CosSimilarity.similarity(primaryName, kb.getPrimaryName());
		score+=CosSimilarity.similarity(predicate, kb.getPredicate());
		score+=CosSimilarity.similarity(object,kb.getObject());
		return ((int)(score*1000))/1000.0;
	}

}
