package qa.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import custom.config.BasicConfig;


public class FileReaderUtil {
	public static void main(String[] args) throws Exception {
		FileReaderUtil reader=new FileReaderUtil();
		reader.reader();
	}
	
	/**
	 * 实体词库
	 * @throws Exception 
	 */
	@Test
	public void reader() throws Exception{
		File f=new File("F:\\knowledge\\nlpcc-iccpol-2016.kbqa.kb.mention2id");
		FileInputStream is=new FileInputStream(f);
		BufferedReader br=new BufferedReader(new InputStreamReader(is,"utf-8"));
		File out=new File("source\\entity.txt");
		FileWriter fw=new FileWriter(out);
		String line=br.readLine();
		while(line!=null){
			String[] s=line.split("\\|{3}");
			for(int i=1;i<s.length;i++){
				String[] words=s[i].split("\\|{3}|\\s");
				for(String w:words){
					w=w.trim();
					if(!w.equals("")){
						System.out.println(w);
						fw.write(w+" ne"+"\n");
					}
				}
			}
			line=br.readLine();
		}
		is.close();
		br.close();
		fw.close();
	}
	
	/**
	 * 读取疑问词
	 * @return
	 */
	public static List<String> getRYWords(){
		List<String> words=new ArrayList<String>();
		try {
			BufferedReader br=new BufferedReader(new FileReader(new File(BasicConfig.synWordPath)));
			String line=br.readLine();
			while(line!=null){
				words.add(line.trim());
				line=br.readLine();
			}
			return words;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<String> getStopWords(){
		List<String> words=new ArrayList<String>();
		try {
			File file=new File(BasicConfig.stopWordPath);
			FileInputStream fis=new FileInputStream(file);
			BufferedReader br=new BufferedReader(new InputStreamReader(fis,"utf-8"));
			String line=br.readLine();
			while(line!=null){
				words.add(line.trim());
				line=br.readLine();
			}
			br.close();
			return words;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
