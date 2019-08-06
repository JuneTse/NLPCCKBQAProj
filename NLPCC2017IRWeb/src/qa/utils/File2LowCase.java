package qa.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class File2LowCase {
	public static void toLowcase(String inPath,String outPath) throws Exception{
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(inPath)),"utf-8"));
		FileWriter fw=new FileWriter(new File(outPath));
		String line=null;
		while((line=br.readLine())!=null){
			line=line.toLowerCase();
			fw.write(line+"\n");
		}
		br.close();
		fw.close();
	}

}
