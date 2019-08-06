package qa.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import custom.config.MySegment;

public class MyFileUtils {
	/**
	 * 读文件到list中
	 * @param path
	 * @return
	 */
	public static List<String> readFile2List(String path){
		List<String> list=null;
		try {
			list=new ArrayList<String>();
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),"utf-8"));
			String line=null;
			while((line=br.readLine())!=null){
				list.add(line.trim());
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 读文件到map中,文件有两列，第一列是key，第二列是value
	 * @param path
	 * @return
	 */
	public static Map<String,String> readFile2Map(String path,String sep){
		Map<String,String> map=null;
		try {
			map=new HashMap<String,String>();
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),"utf-8"));
			String line=null;
			while((line=br.readLine())!=null){
				String[] columns=line.split(sep);
				map.put(columns[0], columns[1]);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 对文件中的文本分词
	 * @param inp
	 * @param oup
	 */
	public static void segmentFile(String inp,String outp){
		BufferedReader br=null;
		FileWriter fw=null;
		String line=null;
		try{
			br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(inp)),"utf-8"));
			fw=new FileWriter(new File(outp));
			while((line=br.readLine())!=null){
				String segLine=MySegment.segment(line);
				fw.write(segLine+"\n");
			}
			fw.close();
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
