package qa.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

public class CosSimilarity {
	public static void main(String[] args) {
		String s="校训是广大师生共同遵守的基本行为准则与道德规范，它既是一个学校办学理念、治校精神的反映，也是校园文化建设的重要内容，是一所学校教风、学风、校风的集中表现，体现大学文化精神的核心内容。文内介绍了中外学校校训，校训的比较，校训的创作，校训的作用。";
		String o="学院可以作为独立的学校，同时很多大学也拥有自己下属的二级学院（与系相同）或普通高等学校与国家机构以外的社会组织或者个人合作设置的独立学院。";
		double sim=CosSimilarity.similarity(s, o);
		double[] v1={1,1,1};
		double[] v2={1,1,1};
		System.out.println(cos(v1, v2));
	}

	/**
	 * 分词统计词频
	 * @param s
	 * @return
	 */
	public static Map<String,Double> tokenize(String s){
		List<Term> sTerms=HanLP.segment(s);
		Map<String,Double> map=new HashMap<String,Double>();
		int w_num=0;
		for(Term t:sTerms){
				w_num++;
				if(!map.containsKey(t.word))
					map.put(t.word, 1.0);
				else
					map.put(t.word,map.get(t.word)+1);
		}
		//词频归一化
		for(Entry<String,Double> e:map.entrySet()){
			map.put(e.getKey(), e.getValue()/(w_num+0.00001));
		}
		return map;
	}
	public static double similarity(String s,String o){
		if(s.trim().equals("")||o.trim().equals(""))
			return 0;
		s=s.toLowerCase();
		o=o.toLowerCase();
		Map<String,Double> map=tokenize(s+o);
		Map<String,Double> map_s=tokenize(s);
		Map<String,Double> map_o=tokenize(o);
		if(map.size()==0) return 0;
		double[] sA=new double[map.size()];
		double[] oA=new double[map.size()];
		int i=0;
		for(Entry<String,Double> e:map.entrySet()){
			if(map_s.containsKey(e.getKey()))
				sA[i]=map_s.get(e.getKey());
			else
				sA[i]=0;
			if(map_o.containsKey(e.getKey()))
				oA[i]=map_o.get(e.getKey());
			else
				oA[i]=0;
			i++;
		}
		return cos(sA,oA);
	}

	public static double cos(double[] sA, double[] oA) {
		if(sA==null||oA==null||sA.length==0||oA.length==0){
//			System.out.println("cos inputs vector is null!!!");
			return 0.0;
		}
		double v=dot(sA,oA)/(Math.sqrt(dot(sA,sA))*Math.sqrt(dot(oA,oA))+0.0000001);
		
		return v;
	}

	//点乘
	private static double dot(double[] sA, double[] oA) {
		double v=0;
		for(int i=0;i<sA.length;i++){
			v+=sA[i]*oA[i];
		}
		return v;
	}
	
	public static double getStrCosSimilar(String s,String o){
		char[] sA=s.toCharArray();
		char[] oA=o.toCharArray();
		Set<Character> voc=new HashSet<Character>();
		for(char c: sA)
			voc.add(c);
		for(char c:oA)
			voc.add(c);
		List<Character> voc_list=new ArrayList<Character>(voc);
		double[] sVector=new double[voc_list.size()];
		double[] oVector=new double[voc_list.size()];
		for(char c:sA){
			int i=voc_list.indexOf(c);
			if(c!=-1)
				sVector[i]=1;
		}
		for(char c:oA){
			int i=voc_list.indexOf(c);
			if(c!=-1)
				oVector[i]=1;
		}
		return cos(sVector, oVector);
	}
}
