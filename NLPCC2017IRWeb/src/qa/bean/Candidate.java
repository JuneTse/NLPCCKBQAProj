package qa.bean;



public class Candidate implements Comparable<Candidate>{
	private KnowledgeBean kb;
	private Double score;
	private Double BM25Score;  //BM得分
	private Double SemanticScore;  //语义得分
	private Double OverlapScore;  //predicate字符overlap得分
	private Double SubjectScore;  //subject得分
	private Double IRScore;  //lucene检索得分
	
	public KnowledgeBean getKb() {
		return kb;
	}
	public void setKb(KnowledgeBean kb) {
		this.kb = kb;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public int compareTo(Candidate o) {
		return o.getScore().compareTo(getScore());
	}
	
	@Override
	public String toString() {
		return kb.getSubject()+"|||"+kb.getPredicate()+"|||"+kb.getObject()+"|||"+score;
	}
	public String getScoredString(){
		return kb.getSubject()+"|||"+kb.getPredicate()+"|||"+kb.getObject()+"|||"
					+BM25Score+" "+SemanticScore+" "+OverlapScore+" "+SubjectScore+" "+IRScore;
	}
	public String getScores(){
		return "1:"+BM25Score+" 2:"+SemanticScore+" 3:"+OverlapScore+" 4:"+SubjectScore+" 5:"+IRScore;
	}
	public String getAnswer(){
		return kb.getObject();
	}
	
	/**
	 * 字符串转换成Candidate
	 * @param k
	 * @return
	 */
	public void buildCandidate(String k){
		//System.out.println(k);
		KnowledgeBean kb=new KnowledgeBean();
		kb.buildKB(k);
//		String[] s=k.split("\\|{3,4}");
//		assert s.length==4;
//		kb.setSubject(s[0]);
//		kb.setPredicate(s[1]);
//		kb.setObject(s[2]);
//		Double sc=Double.parseDouble(s[3]);
//		kb.setScore(sc);
		this.setKb(kb);
		this.setScore(kb.getScore());
	}
	public Double getBM25Score() {
		return BM25Score;
	}
	public void setBM25Score(Double bM25Score) {
		BM25Score = bM25Score;
	}
	public Double getSemanticScore() {
		return SemanticScore;
	}
	public void setSemanticScore(Double semanticScore) {
		SemanticScore = semanticScore;
	}
	public Double getOverlapScore() {
		return OverlapScore;
	}
	public void setOverlapScore(Double overlapScore) {
		OverlapScore = overlapScore;
	}
	public Double getSubjectScore() {
		return SubjectScore;
	}
	public void setSubjectScore(Double subjectScore) {
		SubjectScore = subjectScore;
	}
	public Double getIRScore() {
		return IRScore;
	}
	public void setIRScore(Double iRScore) {
		IRScore = iRScore;
	}
}
