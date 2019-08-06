#coding:utf-8
'''
Mean Reciprocal Rank: 平均排名倒数
'''
import sys
sys.path.append("..")
import config
from myutils.io import read_table,read_lines

train_gold_path=config.getPathConfig("data","train_data")
train_pre_path=config.train_score_path
test_gold_path=config.getPathConfig("data","test_data")
test_pre_path=config.test_score_path

delta=2

def is_right(gold):
    if float(gold.strip())>delta:
        return True
    else:
        return False

class Metrics(object):
    def __init__(self,gold_path,pre_path):
        self.gold_path=gold_path
        self.pre_path=pre_path
        self.gold=None
        self.pre=None
        self.questions=dict() #每个问题对应的标签和预测的得分
        self.read_datas()
        
    def read_datas(self):
        self.gold=read_table(self.gold_path)
        self.pre=read_lines(self.pre_path)
        assert len(self.gold)==len(self.pre)
        for g,p in zip(self.gold,self.pre):
            question=g[0]
            if not question in self.questions:
                self.questions[question]=[]
            self.questions[question].append([g[2],float(p.strip())])
        
        #按预测答案排序
        for k,v in self.questions.items():
            self.questions[k]=sorted(v,key=lambda x:x[1],reverse=True)
    def getMRR(self):
        num=len(list(self.questions.keys()))
        mrr=0.0
        for k,v in self.questions.items():
            rank=0
            for gold,pre in v:
                rank+=1
                if is_right(gold):
                    mrr+=1/rank
                    break
        mrr/=num
        return mrr
    def getACC_N(self,n=1):
        num=len(list(self.questions.keys()))
        acc=0
        for k,v in self.questions.items():
            i=0
            for gold,pre in v:
                i+=1
                if i>n:
                    break
                if is_right(gold):
                    acc+=1
                    break
        return acc/num
        
    def getMAP(self):
        num=len(list(self.questions.keys()))
        MAP=0.0
        for k,v in self.questions.items():
            avg_p=0
            rank=0
            right_num=0
            for gold,pre in v:
                rank+=1
                if is_right(gold):
                    right_num+=1
                    avg_p+=right_num/rank
            if right_num==0:
                avg_p=0
            else:
                avg_p/=min(right_num,rank)
            MAP+=avg_p
        return MAP/num
        
def evaluate(train_pre_path=train_pre_path,test_pre_path=test_pre_path):
    m=Metrics(test_gold_path,test_pre_path)
    mrr=m.getMRR()
    acc1=m.getACC_N(1)
    MAP=m.getMAP()
    print("Test:==: MAP: %s, MRR: %s, ACC@1: %s" %(MAP,mrr,acc1))
    
    m=Metrics(train_gold_path,train_pre_path)
    mrr=m.getMRR()
    acc1=m.getACC_N(1)
    MAP=m.getMAP()
    print("Train:==: MAP: %s, MRR: %s, ACC@1: %s" %(MAP,mrr,acc1))
if __name__=="__main__":
    '''
    m=Metrics(test_gold_path,test_pre_path)
    mrr=m.getMRR()
    acc1=m.getACC_N(1)
    MAP=m.getMAP()
    print("Test:==: MAP: %s, MRR: %s, ACC@1: %s" %(MAP,mrr,acc1))
    '''
    
    m=Metrics(train_gold_path,train_pre_path)
    mrr=m.getMRR()
    acc1=m.getACC_N(1)
    MAP=m.getMAP()
    print("Train:==: MAP: %s, MRR: %s, ACC@1: %s" %(MAP,mrr,acc1))
