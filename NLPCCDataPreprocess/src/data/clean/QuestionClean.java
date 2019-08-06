package data.clean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import custom.config.BasicConfig;

public class QuestionClean {
	static String trainData=BasicConfig.trainData;
	static String testData=BasicConfig.testData;
	static String testData2017=BasicConfig.testData2017;
	static String trainDataClean=BasicConfig.trainDataClean;
	static String testDataClean=BasicConfig.testDataClean;
	static String testData2017Clean=BasicConfig.testData2017Clean;
	public static void main(String[] args) throws Exception {
		String question="你知道请问谁和她接吻了是在什么时候更新的呢？";//"呃……谁知道嘿咻的拼音怎样写";//我，堂吉诃德的曲作者是谁？ // 知道 vlookup 函数 的 作用 不
		String q=getQuestionCore(question);
		System.out.println(q);
		
		cleanQuestions(trainData, trainDataClean);
		cleanQuestions(testData, testDataClean);
		cleanQuestions(testData2017, testData2017Clean);
	}
	
	public static String getQuestionCore(String question){
		String reg1="^(呃(……)|我，)?";
		String reg2="(啊|呀|呢|((你)?知道)?(吗|嘛))?(？|\\?)*$";
		String reg3="(来着|你知不知道|你知道么|知道么|你知道嘛|知道嘛)$";
		String reg4="^请问(一下|你知道)?";
		String reg5="^((我想(问|请教|了解)(一下|下))，?)";
		String reg6="^(那么|什么是|我想知道|我很好奇|有谁了解|问一下|请问你知道|"
				+ "谁能告诉我一下|你能告诉我|我想了解|可不可以告诉我|能不能告诉我|说一下|谁告诉我|介绍下|介绍一下|给我介绍一下"
				+ "|告诉我一下|讲一下|告诉我|	能不能告诉我|你晓得|给一下|可不可以说说|能给我说说|可以说说|说说"
				+ "|你能跟我说一下|请具体说一下|能说一下|你能简单给我说一下|请回答|可不可以说下|说下"
				+ "|帮我找一下|帮我找下|帮我查查|帮我查一下|你帮我查一下|帮我算下|帮我百度一下)";
		String reg7="^((有人|谁|你|你们|有谁|大家)(记得|知道|了解))";
		String reg8="^((谁|请|麻烦|能|可以)告诉我)";
		String reg9="^(好想知道|很想知道|真的很想知道|想知道|你知不知道|知不知道|我好好奇|知道|谈谈|有没有听过|听过|有算过)";
		
		String[] regs={reg1,reg2,reg3,reg4,reg5,reg6,reg7,reg8,reg9};
		for(String reg:regs){
			question=question.replaceAll(reg,"");
		}
		return question;
	}
	
	public static void cleanQuestions(String path,String outpath) throws Exception{
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
		BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outpath))));
		
		String line=br.readLine();
		while(line!=null){
			String s=".*<question.*>.*";
			if(line.matches(s)){
				String question=line.substring(line.indexOf(">")+1).trim();
				question=getQuestionCore(question);
				String id=line.substring(line.indexOf("id=")+3,line.indexOf(">")).trim();
				String answer=br.readLine();
				answer=answer.substring(answer.indexOf(">")+1).trim();
				String str=id+"|||"+question+"|||"+answer;
				bw.write(str.toLowerCase()+"\n");
			}
			line=br.readLine();
		}
		br.close();
		bw.close();
	}

}
