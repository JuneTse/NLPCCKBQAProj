package qa.sys.main;




import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import qa.bean.Candidate;
import qa.bean.Question;
import qa.utils.DataUtils;
import custom.config.BasicConfig;
import custom.config.SkipWords;
import data.questions.QAPairReader;
import data.semantic.EncodedData;

public class QATest {
	List<String> question_entitys;//所有的问题
	List<String> questions;//所有的问题
	List<String> question_ids;
	List<String> questionsWithoutSpace;//所有的问题
	//List<String> q_entity;
	QASystem qa;
	static int answer_num=0;
	public boolean isFilter=true;
	public String resultPath=BasicConfig.resultPath;
	public String resultTmp=BasicConfig.resultTmp;
	public String topKResult=BasicConfig.topKResultPath;

	public static String testDataPath=BasicConfig.testData2017Clean;//BasicConfig.testData2017;
	public static String testQuestionEntityPath=BasicConfig.testQuestionEntity2017;//BasicConfig.testQuestionEntity2017
	
	public void init() throws Exception{
		//questions=QAPairReader.readQuestions();//所有的问题
		//q_entity=QAPairReader.readQuestionEntity();
		qa=new QASystem();
		EncodedData.initEncodedData();
	}
	public void testDataInit() throws Exception{
		questions=QAPairReader.readTestQuestions(testDataPath);//所有的测试问题
		question_ids=QAPairReader.readQuestionsIds(testDataPath);
		questionsWithoutSpace=new ArrayList<String>();
		for(String q:questions){
			q=DataUtils.getQuestionClean(q.replaceAll(" ", "").toLowerCase());
			questionsWithoutSpace.add(q);
		}
		
		question_entitys=QAPairReader.readTestQuestionAndEntity(testQuestionEntityPath);
		System.out.println("questions:"+question_entitys.size());
		System.out.println("questionEntitys:"+question_entitys.size());
		qa=new QASystem();
		EncodedData.initTestEncodedData(); //加载测试的编码数据
	}
	
	public static void main(String[] args) throws Exception {
		QATest qatest=new QATest();
		//qatest.init();
		qatest.testDataInit();
		 
//		qatest.multiThreadtestQA(0,50000);
//		System.out.println("answered Num:"+answer_num);
//		qatest.combineResults(0,63);
		qatest.results(2,true,0.3);
	}
	/**
	 * 多线程
	 * @throws Exception 
	 */
	public void multiThreadtestQA(int start,int end) throws Exception{
		String outpath=resultTmp+"qa_res";
		int samples=800;
		int candidate_num=50;
		List<Thread> ts=new ArrayList<Thread>();
		for(int i=start;i<end;i=i+samples){
			Thread t=new Thread(new QATestThread(i, outpath+(i/samples+1), samples,candidate_num));
			ts.add(t);
			t.start();
		}
		//主线程等待其他线程执行完
		for(Thread t:ts){
			t.join();
		}
	}
	/**
	 * 合并多线程的结果
	 * @throws Exception 
	 */
	public void combineResults(int start,int end) throws Exception{
		String path=resultTmp+"qa_res";
		FileWriter fw=new FileWriter(new File(resultPath));
		int id=0;
		for(int i=start;i<end;i++){
			BufferedReader br=new BufferedReader(new FileReader(new File(path+(i+1))));
			String line=null;
			while((line=br.readLine())!=null){
				if(line.startsWith("<question"))
					id++;
				if(!line.trim().equals(""))
					fw.write(line+"\n");
			}
			br.close();
			fw.flush();
		}
		System.out.println("combined:"+id);
		fw.close();
	}
	
	
	/**
	 * 取前n个结果
	 * @return
	 * @throws Exception
	 */
	public void results(int topN,boolean toLowCase,double threshold) throws Exception{
		File out=new File(topKResult);
		BufferedWriter bw=new BufferedWriter(new FileWriter(out));
		String fname=resultPath;
		BufferedReader br=new BufferedReader(new FileReader(new File(fname)));
		String line=null;
		int count=0;
		int count2=0;
		List<Integer> list=new ArrayList<Integer>();
		while((line=br.readLine())!=null){
			String[] triple=line.split("\t");
			String question=triple[1].trim();
			int idx=Integer.valueOf(triple[0].replaceAll("[<question id=>]", ""))-1;
			String qid=question_ids.get(idx);
			
			List<String> answers=new ArrayList<String>();
			int i=0;
			Double maxScore=0.0;
			while((line=br.readLine())!=null&&!line.endsWith("=============")){
				String[] s=line.split("\\|{3,}");
				Double score=Double.parseDouble(s[s.length-1]);
				if(maxScore<score)
					maxScore=score;
				if(i<topN&&maxScore-score<=0.001&&answers.indexOf(s[2])==-1){
					answers.add(s[2]);
					if(i==topN-1)count++; //统计多个答案问题的个数
					if(i>=7) count2++;
				}else if(i<topN&&maxScore-score<=threshold&&answers.indexOf(s[2])==-1){//取topN并且分数差小于阈值的答案
					answers.add(s[2]);
					if(i==topN-1)count++; //统计多个答案问题的个数
				}
				i++;
			}
			int id=questions.indexOf(question)+1;
//			int id=questionsWithoutSpace.indexOf(question)+1;
			if(id==0) {
				System.err.println("question id is 0!!!!!\t"+question);
				continue;
			}
			//如果该问题已经回答就跳过
//			if(list.indexOf(id)!=-1) continue;
			
			String answerStr="";
			for(String a:answers){
				answerStr+=a+"\t";
			}
			list.add(id);
			if(toLowCase)
				answerStr=answerStr.toLowerCase();
			String q=questions.get(idx);
			if(!q.equals(question)){
				System.err.println("question not matching...\t"+q+"\t"+question);
			}
			bw.write("<question id="+qid+">"+"\t"+question+"\n");
			bw.write("<answer id="+qid+">\t"+answerStr);
			bw.write("\n===================================\n");
			bw.flush();
		}
		System.out.println(list.size());
		System.out.println("topN+多个答案问题个数："+count);
		System.out.println("topN大于10个答案问题个数："+count2);
		bw.close();
		br.close();
	}
	
	class QATestThread implements Runnable{
		private int start;
		private int num;//处理的个数
		BufferedWriter bw;
		int k; //结果数
		public QATestThread(int start,String outPath,int num,int results_num) throws Exception {
			this.start=start;
			this.num=num;
			this.k=results_num;
			bw=new BufferedWriter(new FileWriter(new File(outPath)));
		}

		@Override
		public void run() {
			for(int id=start;id<start+num;id++){
				if(id>=questions.size())
					break;
				String text=questions.get(id);
				//question core
				//System.out.println(text);
				String qeStr=question_entitys.get(id);
				String[] q_e=qeStr.split("\t");
				if(!DataUtils.getQuestionClean(text.replaceAll(" ", "").toLowerCase()).equals(q_e[0])){
					System.err.println("问题匹配错误！！！"+qeStr+":\t"+text);
				}
				Question q=new Question();
				q.setOriginal(text);
				
				String e_text="";
				if(q_e.length>=2){
					e_text=q_e[1];
					q.setTopicEntity(e_text.replaceAll(SkipWords.subjectSkipWords, ""));
				}
				try {
					writeCandidateTo(text+"\t"+e_text, id+1, q);
					answer_num++;
					if(answer_num%100==0)
						System.out.println("answered questions:"+answer_num);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		private void writeCandidateTo(String text, int id, Question q)
				throws Exception, IOException {
			//QA生成答案
			List<Candidate> res=qa.getAnswerCandidates(q,k, isFilter);
			
			bw.write("<question id="+id+">"+"\t"+text+"\n");
			for(Candidate c:res){
				bw.write(c.toString()+"\n");
			}
			bw.write("===================================\n");
			bw.flush();
		}
		
	}
	
}
