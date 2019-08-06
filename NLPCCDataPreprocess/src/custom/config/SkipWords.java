package custom.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class SkipWords {
	public static String predicateSkipWords="[-\\s•【】（）\\(\\),. ，。：:]+";
	public static String subjectSkipWords="[《》]";
	public static Set<String> stopWords=null;
	public static String stopWordPath=BasicConfig.stopWordPath;
	
	public static void main(String[] args) {
		String predicate="人口（1999）";
		predicate=predicate.replaceAll(predicateSkipWords, "");
		System.out.println(predicate);
	}
	
	public static Set<String> getStopWords() throws Exception {
		if(stopWords!=null)
			return stopWords;
		else{
			stopWords=new HashSet<String>();
			BufferedReader br=new BufferedReader(new FileReader(new File(stopWordPath)));
			String line=null;
			while((line=br.readLine())!=null){
				line=line.trim().replace(" ", "");
				stopWords.add(line);
			}
			br.close();
			return stopWords;
		}
	}
}
