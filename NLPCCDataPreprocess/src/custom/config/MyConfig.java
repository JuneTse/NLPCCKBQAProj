package custom.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class MyConfig {
	private Properties pro;
	private FileInputStream in;
	public String BasePath;
	private String configPath="./config.properties";
	
	public static void main(String[] args) {
		MyConfig config=new MyConfig();
		String basePath=config.getPathConifg("BasePath");
		System.out.println(basePath);
	}
	
	public MyConfig(){
		pro=new Properties();
		try {
			in=new FileInputStream(this.configPath);
			pro.load(in);
			BasePath=pro.getProperty("BasePath");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getPathConifg(String key){
		String path=pro.getProperty(key);
		if(path==null){
			System.err.println("Path:"+key+"is null!!!");
		}
		return BasePath+"/"+path;
	}
	
//	public void setConfig(String key,String value){
//		pro.setProperty(key, value);
//		try{
//			FileOutputStream out=new FileOutputStream(configPath,false);
//			pro.store(out, key);
//			out.close();
//		}catch(IOException e){
//			e.printStackTrace();
//		}
//	}
	
}
