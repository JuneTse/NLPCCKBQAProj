package qa.bean;

import java.util.ArrayList;
import java.util.List;

import custom.config.MySegment;
import custom.config.SkipWords;

public class Question {
	private String original;
	private String topicEntity; //主题实体
	private List<String> question;
	private List<String> predicate;
	private List<String> nGrams;
	private String answer;
	private String id;
	
	@Override
	public String toString() {
		String s="分词结果："+question+"\n"+"\n可能的属性关系词："+predicate+
				"\n主题实体："+topicEntity;
		return s;
	}
	public String getOriginal() {
		return original;
	}
	public void setOriginal(String original) {
		this.original = original;
	}
	public String getTopicEntity() {
		return topicEntity;
	}
	public void setTopicEntity(String topicEntity) {
		this.topicEntity = topicEntity;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public List<String> getnGrams() {
		if(nGrams!=null)
			return nGrams;
		else{
			nGrams=new ArrayList<String>();
			List<String> words=MySegment.segment2List(original);
			int n=words.size();
			for(int i=1;i<=n;i++){
				nGrams.addAll(getNGrams(words, i));
			}
			return nGrams;
		}
	}
	public void setnGrams(List<String> nGrams) {
		this.nGrams = nGrams;
	}
	public List<String> getQuestion() {
		return question;
	}
	public void setQuestion(List<String> question) {
		this.question = question;
	}
	public List<String> getPredicate() {
		return predicate;
	}
	public void setPredicate(List<String> predicate) {
		this.predicate = predicate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public  List<String> getNGrams(List<String> words,int n){
		if(n>words.size())
			return null;
		List<String> n_gram=new ArrayList<String>();
		for(int i=0;i<words.size()-n+1;i++){
			String s="";
			for(int j=0;j<n;j++)
				s+=words.get(i+j);
			try {
				if(Subjects.getSubjects().contains(s.trim())&&!SkipWords.getStopWords().contains(s.trim()))
					n_gram.add(s.trim());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return n_gram;
	}

}
