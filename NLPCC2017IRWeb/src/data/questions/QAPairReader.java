package data.questions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import qa.utils.MyFileUtils;
import custom.config.BasicConfig;
import custom.config.MySegment;

public class QAPairReader {
	static String allTrainQuestionPath=BasicConfig.allTrainQuestionPath;
	static String allSegTrainQuestionPath=BasicConfig.allSegTrainQuestionPath;
	
	static String allTestQuestionPath=BasicConfig.allTestQuestionPath;
	static String allSegTestQuestionPath=BasicConfig.allSegTestQuestionPath;
	
	static String allTestQuestion2017Path=BasicConfig.allTestQuestion2017Path;
	static String allSegTestQuestion2017Path=BasicConfig.allSegTestQuestion2017Path;
	
	static String trainData=BasicConfig.trainData;
	static String testData=BasicConfig.testData;
	static String testData2017=BasicConfig.testData2017;
	
	static String trainQuestionEntity=BasicConfig.trainQuestionEntity;
	static String testQuestionEntity=BasicConfig.testQuestionEntity;
	static String testQuestion2017Entity=BasicConfig.testQuestionEntity2017;
	
	public static void main(String[] args) throws Exception {
		QAPairReader reader=new QAPairReader();
		
	}
	
	/**
	 * 读取所有测试问题
	 * @return
	 * @throws Exception
	 */
	public static Map<String,String> readTestQuestionEntity2Map(String inpath) throws Exception{
		BufferedReader br=new BufferedReader(new FileReader(new File(inpath)));
		Map<String,String> map=new HashMap<String,String>();
		String line=null;
		int i=0;
		while((line=br.readLine())!=null){
//			System.out.println(line);
			String[] qe=line.trim().split("\t");
			if(qe.length>=2)
				map.put(qe[0],qe[1]);
			i++;
		}
		br.close();
		return map;
	}
	
	public static List<String> readTestQuestionAndEntity(String questionEntityPath) throws Exception{
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(questionEntityPath))));
		List<String> questions=new ArrayList<String>();
		String line=null;
		while((line=br.readLine())!=null){
			questions.add(line);
		}
		br.close();
		return questions;
	}

	public static List<String> readTestQuestions(String testDataPath) throws Exception {
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(testDataPath))));
		List<String> questions=new ArrayList<String>();
		String line=null;
		while((line=br.readLine())!=null){
			String[] s=line.trim().split("\\|{3}");
			String id=s[0];
			String question=s[1].trim();
			questions.add(question);
		}
		br.close();
		return questions;
	}

	public static List<String> readQuestionsIds(String testDataPath) throws Exception {
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(testDataPath))));
		List<String> ids=new ArrayList<String>();
		String line=null;
		while((line=br.readLine())!=null){
			String[] s=line.trim().split("\\|{3}");
			String id=s[0];
			String question=s[1];
			ids.add(id);
		}
		br.close();
		return ids;
	}
}
