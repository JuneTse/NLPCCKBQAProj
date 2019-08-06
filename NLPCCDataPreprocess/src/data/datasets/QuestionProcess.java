package data.datasets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import custom.config.BasicConfig;
import custom.config.MySegment;

public class QuestionProcess {
	static String trainDataClean=BasicConfig.trainDataClean;
	static String testDataClean=BasicConfig.testDataClean;
	static String testData2017Clean=BasicConfig.testData2017Clean;
	
	static String segTrainQuestion=BasicConfig.allSegQuestionPath;
	static String segTestQuestion=BasicConfig.allSegTestQuestionPath;
	static String segTestQuestion2017=BasicConfig.allSegTestQuestion2017Path;
	
	static String allTrainQuestion=BasicConfig.allQuestionPath;
	static String allTestQuestion=BasicConfig.allTestQuestionPath;
	static String allTestQuestion2017=BasicConfig.allTestQuestion2017Path;
	
	public static void main(String[] args) throws Exception {
		segmentQuestion(trainDataClean, segTrainQuestion);
		segmentQuestion(testDataClean, segTestQuestion);
		segmentQuestion(testData2017Clean, segTestQuestion2017);
		
		writeQuestion(trainDataClean, allTrainQuestion);
		writeQuestion(testDataClean, allTestQuestion);
		writeQuestion(testData2017Clean, allTestQuestion2017);
	}
	/**
	 * 问题分词
	 * @param inp
	 * @param outp
	 * @throws Exception
	 */
	private static void segmentQuestion(String inp,String outp) throws Exception {
		BufferedReader br=new BufferedReader(new FileReader(new File(inp)));
		FileWriter fw=new FileWriter(new File(outp));
		String line=null;
		while((line=br.readLine())!=null){
			String[] s=line.split("\\|{3}");
			String id=s[0];
			String question=s[1];
			fw.write(MySegment.segment(question)+"\n");
			fw.flush();
		}
		fw.close();
		br.close();
	}
	/**
	 * 从问答对中提取问题
	 * @param inp
	 * @param outp
	 * @throws Exception
	 */
	private static void writeQuestion(String inp,String outp) throws Exception {
		BufferedReader br=new BufferedReader(new FileReader(new File(inp)));
		FileWriter fw=new FileWriter(new File(outp));
		String line=null;
		while((line=br.readLine())!=null){
			String[] s=line.split("\\|{3}");
			String id=s[0];
			String question=s[1];
			fw.write(question+"\n");
			fw.flush();
		}
		fw.close();
		br.close();
	}

}
