package data.semantic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import custom.config.BasicConfig;
import custom.config.MyConfig;
import qa.utils.CosSimilarity;


public class EncodedData {
	public static String BasePath=BasicConfig.BasePath+"/datasets/predict/";
	public static Map<String,double[]> encoded_questions_cnn1;
	public static Map<String,double[]> encoded_predicates_cnn1;
	public static Map<String,double[]> encoded_questions_cnn2;
	public static Map<String,double[]> encoded_predicates_cnn2;
	public static Map<String,double[]> encoded_questions_cnn3;
	public static Map<String,double[]> encoded_predicates_cnn3;
	
	
	
	public static final String train_question_all=BasicConfig.allTrainQuestionPath;
//	public static final String question_all=BasicConfig.allTestQuestionPath;
	public static final String question_all=BasicConfig.allTestQuestion2017Path;
	
	public static final String predicates_all=BasicConfig.allPredicatePath;
	
//	public static final String cnn_encoded_questions1=BasePath+"encoded_data/GatedBiGRUCNNDSSMTEEMJoint2017_encoded_questions.txt";
	public static final String cnn_encoded_questions1=BasePath+"encoded_data/GatedBiGRUCNNDSSMTEEMJoint2017_encoded_questions2017.txt";
	public static final String cnn_encoded_predicates1=BasePath+"encoded_data/GatedBiGRUCNNDSSMTEEMJoint2017_encoded_predicates.txt";
	
	public static final String cnn_encoded_questions2=BasePath+"encoded_data/2GatedBiGRUCNNDSSMTEEMJoint2017_encoded_questions2017.txt";
	public static final String cnn_encoded_predicates2=BasePath+"encoded_data/2GatedBiGRUCNNDSSMTEEMJoint2017_encoded_predicates.txt";
	
	public static final String cnn_encoded_questions3=BasePath+"encoded_data/3GatedBiGRUCNNDSSMTEEMJoint2017_encoded_questions2017.txt";
	public static final String cnn_encoded_predicates3=BasePath+"encoded_data/3GatedBiGRUCNNDSSMTEEMJoint2017_encoded_predicates.txt";
	
	
	
	public static void main(String[] args) throws Exception {
//		initEncodedData();
		initTestEncodedData();
		String question="威风堂堂的作曲是谁";
		String word1="地区";
		String word2="编曲";
		double[]  v1=encoded_questions_cnn1.get(question);
		double score=CosSimilarity.cos(v1,encoded_predicates_cnn1.get(word1));
		double score2=CosSimilarity.cos(encoded_questions_cnn1.get(question),encoded_predicates_cnn1.get(word2));
		System.out.println(score);
		System.out.println(score2);
	}
	
	
	public static void initEncodedData() throws Exception {
		System.out.println("initEncodedData...");
		encoded_questions_cnn1=readEncodedData(cnn_encoded_questions1,train_question_all,128);
		encoded_predicates_cnn1=readEncodedData(cnn_encoded_predicates1,predicates_all,128);
		System.out.println("predicates size:"+encoded_predicates_cnn1.size());
	}
	public static void initTestEncodedData() throws Exception {
		System.out.println("initTestEncodedData.....");
		
		encoded_questions_cnn1=readEncodedData(cnn_encoded_questions1,question_all,128);
		encoded_predicates_cnn1=readEncodedData(cnn_encoded_predicates1,predicates_all,128);
		
//		encoded_questions_cnn2=readEncodedData(cnn_encoded_questions2,question_all,128);
//		encoded_predicates_cnn2=readEncodedData(cnn_encoded_predicates2,predicates_all,128);
//		
//		encoded_questions_cnn3=readEncodedData(cnn_encoded_questions3,question_all,128);
//		encoded_predicates_cnn3=readEncodedData(cnn_encoded_predicates3,predicates_all,128);
		
		System.out.println("predicates size:"+encoded_predicates_cnn1.size());
		System.out.println("questions size:"+encoded_questions_cnn1.size());
	}
	
	/**
	 * 读取编码后的数据
	 * BasePath+"/QA/question_predicate/encoded_predicates.txt"
	 * BasePath+"/QA/question_predicate/encoded_questions.txt"
	 * @return
	 * @throws Exception
	 */
	public static Map<String,double[]> readEncodedData(String encode_path,String data_path,int size) throws Exception{
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(encode_path)),"utf-8"));
		BufferedReader data_br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(data_path)),"utf-8"));
		String line=br.readLine();
		String word=data_br.readLine();
		Map<String,double[]> map=new HashMap<String,double[]>();
		int j=0;
		while(line!=null&&word!=null){
			double[] vec=new double[size];
			String[] s=line.trim().split(" ");
			for(int i=0;i<s.length;i++){
				vec[i]=Double.parseDouble(s[i]);
			}
//			word=word.replaceAll(" ", ""); //不能去掉空格，否则可能找不到问题
//			if(map.containsKey(word))
//				System.out.println("allready contain the question:"+word.trim());
			map.put(word.trim(), vec);
			line=br.readLine();
			word=data_br.readLine();
			j++;
		}
		System.out.println("lines num:"+j);
		br.close();
		data_br.close();
		return map;
	}

}
