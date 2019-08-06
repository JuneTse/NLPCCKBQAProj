package qa.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import custom.config.MyConfig;

public class WordSimilarity {
	String synWordPath;
	MyConfig config=new MyConfig();
	public static void main(String[] args) {
		WordSimilarity ws=new WordSimilarity();
		double score=ws.similarity("播放", "放映");
		System.out.println(score);
	}
	
	Map<String,List<String>> word_groupID=new HashMap<String,List<String>>();
	
	public WordSimilarity() {
		synWordPath=config.getPathConifg("synWordPath");
		init();
	}
	public void init(){
		try {
			FileInputStream fis=new FileInputStream(new File(synWordPath));
			BufferedReader br=new BufferedReader(new InputStreamReader(fis, "GBK"));
			String line=br.readLine();
			while(line!=null){
				String[] words=line.split("\\s");
				String id=words[0].trim();
				for(int i=1;i<words.length;i++){
					String w=words[i].trim();
					if(!word_groupID.containsKey(w)){
						List<String> list=new ArrayList<String>();
						list.add(id);
						word_groupID.put(w,list);
					}else{
						word_groupID.get(w).add(id);
					}
				}
				line=br.readLine();
			}
			fis.close();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public double similarity(String w1,String w2){
		if(word_groupID.containsKey(w1)&&word_groupID.containsKey(w2)){
			List<String> group1=word_groupID.get(w1);
			List<String> group2=word_groupID.get(w2);
			double score=0.0;
			for(String g1:group1){
				String[] gs1=getWordClass(g1);
				for(String g2:group2){
					int i=0;
					String[] gs2=getWordClass(g2);
					for(i=0;i<gs2.length;i++){
						if(!gs1[i].equals(gs2[i]))
							break;
					}
					if(score<i) score=i;
				}
			}
			return score*score/36; //平方，越相似，分数越高
		}
		return 0;
	}
	/**
	 * 分为5级类别
	 * @param g1
	 * @return
	 */
	private String[] getWordClass(String g1) {
		String[] s={g1.substring(0,1),g1.substring(1,2),g1.substring(2,4),
				g1.substring(4,5),g1.substring(5,7),g1.substring(7,8)};
		return s;
	}

}
