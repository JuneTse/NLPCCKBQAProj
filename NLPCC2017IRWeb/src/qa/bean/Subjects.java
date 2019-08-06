package qa.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import custom.config.BasicConfig;

public class Subjects {
	private static String subjectPath=BasicConfig.allSubjectPath;
	private static Set<String> subjects=null;

	public static Set<String> getSubjects() throws Exception {
		if(subjects!=null)
			return subjects;
		else{
			subjects=new HashSet<String>();
			BufferedReader br=new BufferedReader(new FileReader(new File(subjectPath)));
			String line=null;
			while((line=br.readLine())!=null){
				line=line.trim().replace(" ", "");
				subjects.add(line);
			}
			br.close();
			return subjects;
		}
	}
}
