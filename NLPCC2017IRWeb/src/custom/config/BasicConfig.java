package custom.config;

public class BasicConfig {
	
	public static MyConfig config=new MyConfig();
	public static String BasePath=config.BasePath;
	//KnowledgeBase
	public static String knowledgePath=config.getPathConifg("knowledgePath");
	public static String lowcaseKnowledgePath=config.getPathConifg("lowcaseKnowledgePath");
	public static String segmentedKnowledgePath=config.getPathConifg("segmentedKnowledgePath");
	public static String segmentedCharsKnowledgePath=config.getPathConifg("segmentedCharsKnowledgePath");
	
	//Triples
	public static String allPredicatePath=config.getPathConifg("allPredicatePath");
	public static String allSubjectPath=config.getPathConifg("allSubjectPath");
	public static String allSegPredicatePath=config.getPathConifg("allSegPredicatePath");
	public static String allSegSubjectPath=config.getPathConifg("allSegSubjectPath");
	public static String allSubjectPredicatePath=config.getPathConifg("allSubjectPredicatePath");
	
	//questions
	public static String allQuestionPath=config.getPathConifg("allQuestionPath");
	public static String allSegQuestionPath=config.getPathConifg("allSegQuestionPath");
	public static String allTrainQuestionPath=config.getPathConifg("allTrainQuestionPath");
	public static String allSegTrainQuestionPath=config.getPathConifg("allSegTrainQuestionPath");
	public static String allTestQuestionPath=config.getPathConifg("allTestQuestionPath");
	public static String allSegTestQuestionPath=config.getPathConifg("allSegTestQuestionPath");
	public static String allTestQuestion2017Path=config.getPathConifg("allTestQuestion2017Path");
	public static String allSegTestQuestion2017Path=config.getPathConifg("allSegTestQuestion2017Path");
	
	
	//dataset
	public static String trainData=config.getPathConifg("trainData");
	public static String testData=config.getPathConifg("testData");
	public static String testData2017=config.getPathConifg("testData2017");
	
	public static String trainDataClean=config.getPathConifg("trainDataClean");
	public static String testDataClean=config.getPathConifg("testDataClean");
	public static String testData2017Clean=config.getPathConifg("testData2017Clean");
	
	//TopicEntity
	public static String trainQuestionEntity=config.getPathConifg("trainQuestionEntityPath");
	public static String testQuestionEntity=config.getPathConifg("testQuestionEntityPath");
	public static String testQuestionEntity2017=config.getPathConifg("testQuestionEntity2017Path");
	
	//Ngrams
	public static String trainQuestionNgrams=config.getPathConifg("trainQuestionNgramsPath");
	public static String testQuestionNgrams=config.getPathConifg("testQuestionNgramsPath");
	
	//Question Parser
	public static String trainQuestionParser=config.getPathConifg("trainQuestionParserResults");
	public static String testQuestionParser=config.getPathConifg("testQuestionParserResults");
	
	//index
	public static String indexPath=config.getPathConifg("indexPath");
	public static String entityIndexPath=config.getPathConifg("entityIndexPath");
	
	//词典
	public static String synWordPath=config.getPathConifg("synWordPath");
	public static String stopWordPath=config.getPathConifg("stopWordPath");

	//results
	public static String resultPath=config.getPathConifg("resultPath");
	public static String resultTmp=config.getPathConifg("resultTmp");
	public static String topKResultPath=config.getPathConifg("topKResultPath");
	
	//TrainData
	public static String trainTriplePath=config.getPathConifg("trainTriplePath");
	public static String testTriplePath=config.getPathConifg("testTriplePath");
	public static String testTriplePath2017=config.getPathConifg("testTriplePath2017");
	public static String wrongTrainTriplePath=config.getPathConifg("wrongTrainTriplePath");
	public static String segTrainTriplePath=config.getPathConifg("segTrainTriplePath");
	public static String segTestTriplePath=config.getPathConifg("segTestTriplePath");
	
	//Candidates
	public static String trainCandidatePath=config.getPathConifg("trainCandidatePath");
	public static String segTrainCandidatePath=config.getPathConifg("segTrainCandidatePath");
	public static String testCandidatePath=config.getPathConifg("testCandidatePath");
	public static String segTestCandidatePath=config.getPathConifg("segTestCandidatePath");
	public static String testCandidate2017Path=config.getPathConifg("testCandidate2017Path");
	public static String segTestCandidate2017Path=config.getPathConifg("segTestCandidate2017Path");

}
