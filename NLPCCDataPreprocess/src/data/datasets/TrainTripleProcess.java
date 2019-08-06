package data.datasets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import custom.config.BasicConfig;
import custom.config.MySegment;

public class TrainTripleProcess {
	public static String trainTriplePath=BasicConfig.trainTriplePath;
	public static String testTriplePath=BasicConfig.testTriplePath;
	public static String testTriplePath2017=BasicConfig.testTriplePath2017;
	
	public static String segtrainTriplePath=BasicConfig.segTrainTriplePath;
	public static String segtestTriplePath=BasicConfig.segTestTriplePath;
//	public static String segtestTriplePath2017=BasicConfig.segTestTriplePath2017;
	
	public static void main(String[] args) throws Exception {
		segmentTriple(trainTriplePath,segtrainTriplePath);
		segmentTriple(testTriplePath,segtestTriplePath);
		segmentTriple(testTriplePath2017,segtestTriplePath.replace(".txt", "2017.txt"));
	}
	
	private static void segmentTriple(String path,String outpath) throws Exception {
		BufferedReader br=new BufferedReader(new FileReader(new File(path)));
		FileWriter fw=new FileWriter(new File(outpath));
		String line=null;
		while((line=br.readLine())!=null){
			String[] strs=line.split("\t");
			assert(strs.length==4);
			String question=MySegment.segment(strs[0]);
			String subject=MySegment.segment(strs[1]);
			String predicate=MySegment.segment(strs[2]);
			String object=MySegment.segment(strs[3]);
			fw.write(question+"\t"+subject+"\t"+predicate+"\t"+object+"\n");
		}
		fw.close();
	}

}
