package data.clean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import qa.utils.DataUtils;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import custom.config.BasicConfig;
import custom.config.MyConfig;
import custom.config.MySegment;

public class KBClean {
	public String knowledgePath=BasicConfig.knowledgePath;
	String lowcaseKnowledgePath=BasicConfig.lowcaseKnowledgePath;
	String segmentedKnowledgePath=BasicConfig.segmentedKnowledgePath;
	String segmentedCharsKnowledgePath=BasicConfig.segmentedCharsKnowledgePath;
	MyConfig config;
	public static void main(String[] args) {
		KBClean prep=new KBClean();
		System.out.println(prep.knowledgePath);
		prep.kb2lowcase();
	}
	
	public String getCleanedPredicate(String predicate){
		String regex="[`、◆\\s•【】（）\\(\\),. ，。：:?？！!#@$·“”\"《》-l]+";
		String reg2="\\[.*?\\]";
		String reg3="[\\[\\]]";
		String reg4="任期.*";
		String reg5="就任日期.*";
		predicate=predicate.replaceAll(regex, "").replaceAll(reg2,"").replaceAll(reg3, "");
		predicate=predicate.replaceAll(reg4, "任期");
		predicate=predicate.replaceAll(reg5, "就任日期");
		return predicate;
	}
	public String getCleanedSubject(String subject){
		String reg="[《》【】]";
		return subject.replaceAll(reg,"");
	}
	
	/**
	 * kb转换成小写
	 */
	public void kb2lowcase(){
		BufferedReader br=null;
		FileWriter fw=null;
		try{
			br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(knowledgePath)),"utf-8"));
			fw=new FileWriter(new File(lowcaseKnowledgePath));
			String line=null;
			while((line=br.readLine())!=null){
				line=line.toLowerCase();
				line=line.replaceAll("\\|{4,}", "|||");
				String[] triple=line.split("\\|{3}");
				
				String subject=getCleanedSubject(triple[0]);
				String predicate=getCleanedPredicate(triple[1]);
				String object=triple[2];
				
				String res=subject+"|||"+predicate+"|||"+object;
				fw.write(res+"\n");
				fw.flush();
			}
			br.close();
			fw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
