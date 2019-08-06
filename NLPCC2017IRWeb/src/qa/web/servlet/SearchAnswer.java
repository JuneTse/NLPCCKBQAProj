package qa.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import custom.config.BasicConfig;
import qa.bean.Candidate;
import qa.bean.Question;
import qa.sys.main.QASystem;
import data.questions.QAPairReader;
import data.semantic.EncodedData;

/**
 * Servlet implementation class SearchAnswer
 */
@WebServlet("/SearchAnswer")
public class SearchAnswer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private QASystem qa;
    private Map<String,String> question_entity;
    public static String testQuestionEntityPath=BasicConfig.testQuestionEntity2017;
    public SearchAnswer() throws Exception {
        super();
        if(EncodedData.encoded_questions_cnn1==null)
        	EncodedData.initTestEncodedData();
        question_entity=QAPairReader.readTestQuestionEntity2Map(testQuestionEntityPath);
		qa=new QASystem();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取请求数据
		String text=request.getParameter("question");
        //获取请求数据----防止get请求乱码
  	    text=new String(text.getBytes("iso-8859-1"),"utf-8");
  	  long t1=System.currentTimeMillis();
		Question question=new Question();
		question.setOriginal(text);
		String entity=question_entity.get(text);
		System.out.println("TopicEntity:"+entity);
		if(entity!=null) question.setTopicEntity(entity);
		//QA
		try {
			List<Candidate> candidates=qa.getAnswerCandidates(question, 20, false);
			String cand="";
			for(Candidate c: candidates){
				//System.out.println(c);
				cand+=c+"<br>";
			}
			request.setAttribute("list",candidates);
			if(candidates!=null&&candidates.size()>0){
				request.setAttribute("answer",candidates.get(0).getAnswer());
				request.setAttribute("candidates",cand);
				}
			else
				request.setAttribute("answer","No Answer!");
			request.setAttribute("question", text);
			request.setAttribute("time0",t1);
			
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//防止乱码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		//获取请求数据
		String text=request.getParameter("question");

  	    System.out.println(text);
		long t1=System.currentTimeMillis();
		Question question=new Question();
		question.setOriginal(text);
		String entity=question_entity.get(text);
		System.out.println("TopicEntity:"+entity);
		if(entity!=null) question.setTopicEntity(entity);
		//QA
		try {
			List<Candidate> candidates=qa.getAnswerCandidates(question, 20, false);
			String cand="";
			for(Candidate c: candidates){
				//System.out.println(c);
				cand+=c+"<br>";
			}
			request.setAttribute("list",candidates);
			if(candidates!=null&&candidates.size()>0){
				request.setAttribute("answer",candidates.get(0).getAnswer());
				request.setAttribute("candidates",cand);
				}
			else
				request.setAttribute("answer","No Answer!");
			request.setAttribute("question", text);
			request.setAttribute("time0",t1);
			
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
