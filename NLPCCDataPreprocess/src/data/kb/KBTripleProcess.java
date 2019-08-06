package data.kb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import custom.config.BasicConfig;
import custom.config.MySegment;
import qa.utils.DataUtils;
import qa.utils.MyFileUtils;
import qa.utils.SortedMap;

public class KBTripleProcess {
	static String lowcaseKnowledgePath=BasicConfig.lowcaseKnowledgePath;
	static String segmentedKnowledgePath=BasicConfig.segmentedKnowledgePath;
	static String allPredicatePath=BasicConfig.allPredicatePath;
	static String allSubjectPath=BasicConfig.allSubjectPath;
	static String allSubjectPredicatePath=BasicConfig.allSubjectPredicatePath;
	static String allSegPredicatePath=BasicConfig.allSegPredicatePath;
	static String allSegSubjectPath=BasicConfig.allSegSubjectPath;
	
	public static void main(String[] args) throws Exception {
		KBTripleProcess ktp=new KBTripleProcess();
//		ktp.readPredicates();
//		ktp.readAllSubject();
		//subject和predicate分词
//		MyFileUtils.segmentFile(allPredicatePath, allSegPredicatePath);
//		MyFileUtils.segmentFile(allSubjectPath, allSegSubjectPath);
//		//知识库分词
//		segmentKB(lowcaseKnowledgePath, segmentedKnowledgePath);
	}
	
	public static void segmentKB(String kbPath,String outpath) throws Exception{
		BufferedReader br=null;
		FileWriter fw=null;
		String line=null;
		br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(kbPath)),"utf-8"));
		fw=new FileWriter(new File(outpath));
		while((line=br.readLine())!=null){
			String[] triples=line.split("\\|{3}");
			String subject=MySegment.segment(triples[0]);
			String predicate=MySegment.segment(triples[1]);
			String object=MySegment.segment(triples[2]);
			fw.write(subject+"\t"+predicate+"\t"+object+"\n");
		}
		fw.close();
		br.close();
	}
	
	/**
	 * 读取知识库中所有的Subject
	 */
	public void readAllSubject(){
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(lowcaseKnowledgePath))));
			FileWriter fw=new FileWriter(new File(allSubjectPath));
			String line=null;
			Map<String,Integer> map=new HashMap<String,Integer>();
			while((line=br.readLine())!=null){
				String[] s=line.split("\\|{3}");
				String subject=DataUtils.getPrimeName(s[0]);
				map.put(subject,map.getOrDefault(subject, 0)+1);
				if(map.size()%10000==0)
					System.out.println(map.size());
			}
			map=SortedMap.sort(map);
			Iterator<Entry<String, Integer>> it=map.entrySet().iterator();
			while(it.hasNext()){
				Entry<String,Integer> e=it.next();
//				if(e.getValue()<2) continue;
				fw.write(e.getKey()+"\n");
			}
			br.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 统计predicate关系词
	 * @throws Exception 
	 */
	public void readPredicates() throws Exception{
		System.out.println("提取predicate");
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(lowcaseKnowledgePath)),"utf-8"));
		String line=null;
		Map<String,Integer> map=new HashMap<String,Integer>();
		while((line=br.readLine())!=null){
			String[] s=line.split("\\|{3}");
			String pre=s[1].trim();
			if(pre.length()>30) 
				continue;
			System.out.println(pre);
			if(!map.containsKey(pre))
				map.put(pre, 1);
			else 
				map.put(pre,map.get(pre)+1);
			
		}
		br.close();
		FileWriter fw=new FileWriter(new File(allPredicatePath));
		map=SortedMap.sort(map);
		Iterator<Entry<String, Integer>> it=map.entrySet().iterator();
		while(it.hasNext()){
			Entry<String,Integer> e=it.next();
			fw.write(e.getKey()+"\n");
		}
		fw.close();
	}
}
