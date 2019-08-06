#coding:utf-8
import pickle
import sys
sys.path.append("..")
from myutils.preprosessing import padding
import config
import re
import numpy as np

vocab=config.vocab
char_vocab=pickle.load(open(config.char_vocab_path,'rb'))

#vocab_path=config.vocab_path+".teem"
#emb_path=config.emb_path+".teem"

#vocab=pickle.load(open(vocab_path,"rb"))

#pattern_path=config.train_pattern_triples
train_data_path=config.seg_train_path
test_data_path=config.seg_test_path

train_questions=config.seg_train_question_path
test_questions_path=config.seg_test_question_path
all_questions=config.all_seg_question_path


def readAllQuestions(path=train_questions):
    '''读取所有的问题
    '''
    questions=[]
    with open(path,encoding='utf-8') as f:
        for line in f:
            question=line.strip()
            questions.append(question)
    return questions
def readTrainData(path=config.seg_train_triples):
    '''读取triples
    '''
    questions=[]
    subjects=[]
    predicates=[]
    with open(path,encoding='utf-8') as f:
        for line in f:
            pair=line.split("\t")
            question=pair[0]
            subject=pair[1]
            predicate=pair[2].strip()
            
            subjectSkipWords="[《》]"
            subject=re.sub(subjectSkipWords,"",subject)
            questions.append(question)
            subjects.append(subject)
            predicates.append(predicate)
    return questions,subjects,predicates 

    
questions,subjects,predicates=readTrainData()
test_questions=readAllQuestions(path=test_questions_path)

def get_train_teem_data(question_len=20):
    questions,subjects,predicates=readTrainData()
    qs=[]
    labels=[]
    for q,sub in zip(questions,subjects):
        question=re.split("\\s+",q.lower())
        subject=re.split("\\s+",sub.lower())
        
        qtemp=q.lower().replace(" ","")
        stemp=sub.lower().replace(" ","")
        if not stemp in qtemp:
            print(qtemp,stemp)
            stemp=subject[0]
        i=qtemp.index(stemp)
        
        prefix=q.lower().replace(" ","")[:i]
        pre=""
        for s in range(len(question)):
            pre+=question[s]
            if prefix==pre:
                s+=1
                break
            if prefix in pre:
                break
            
        e=s+len(subject)
        label=np.zeros(question_len)
        label[s:e]=1
        qidx=[vocab.get(word,0) for word in question]
        
        qs.append(qidx)
        labels.append(label)
    return padding(qs,maxlen=question_len,value=1),padding(labels,maxlen=question_len,value=0)
def get_test_teem_data(questions,question_len=20):
    qs=[]
    for q in questions:
        question=re.split("\\s+",q.lower())
        qidx=[vocab.get(w,0) for w in question]
        qs.append(qidx)
    return padding(qs,maxlen=question_len,value=1)

        

if __name__=='__main__':
    test_questions=readAllQuestions(path=test_questions_path)
    
    qs,labels=get_train_teem_data()
    
