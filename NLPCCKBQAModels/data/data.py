#coding: utf-8
import pickle
import pandas as pd
import sys
sys.path.append("..")
from myutils.preprosessing import padding
import config
import random
import re
import numpy as np

question_len=config.question_len
answer_len=config.answer_len
vocab=config.vocab

def read_table(path,sep="\t"):
    f=open(path,encoding="utf-8")
    datas=[]
    for line in f:
        data=line.strip().split(sep)
        data=[d.strip() for d in data]
        datas.append(data)
    return datas
    
def str2index(texts,max_len=30):
    assert type(texts) is list
    index_datas=[]
    for text in texts:
        #row=text.strip().split(" ")
        row=re.split("\\s+",text.strip())
        ids=[]
        for word in row:
            ids.append(vocab.get(word,0))
        index_datas.append(ids)
    return padding(index_datas,max_len)
    
class Dataset(object):
    def __init__(self,data_path,label_column,int_list_columns=[],vocab=vocab,process=False):
        '''读取并处理数据
        params:
            data_path: 文本数据的路径
            process: boolean, True时对原始的tab分割文本进行处理，并保存处理结果。
                     False时直接加载保存的结果。
        '''
        self.datas=None
        self.questions=None
        self.column_num=0
        self.index_datas=None
        self.pos_ids=None
        self.pos_negs=None
        self.label_column=label_column
        self.int_list_columns=int_list_columns
        pickle_path=data_path+".pkl"
        if process:
            self.read_table(data_path)
            self.build_index_data(vocab)
            self.save(pickle_path)
        else:
            self.load(pickle_path)
        
        
    def read_table(self,path,sep='\t',header=None,col_names=None):
        '''从tab分割的文件中读取原始的文本数据
        '''
        print("readding table....")
        #self.datas=pd.read_table(path,sep=sep,header=None,names=col_names)
        self.datas=pd.DataFrame(read_table(path,sep))
        temp=self.datas.copy()
        self.questions=temp.groupby(by=0)
        self.column_num=self.datas.columns.size
        for i in range(self.column_num):
            a=self.datas[i] #第i列的数据
            if self.label_column is None or i!=self.label_column:
                    self.datas[i]=a.str.split(" ")
                    if i in self.int_list_columns:
                        self.datas[i]=self.datas[i].apply(lambda x:[int(v) for v in x])
            else:
                #self.datas[i]=self.datas[i].apply(lambda x:int(float(x)/2))
                self.datas[i]=self.datas[i].apply(lambda x:int(x))
                
    def build_index_data(self,vocab,default=0):
        '''单词转换成在词库中的索引
        '''
        print("building word index...")
        index_datas=[]
        for i,row in self.datas.iterrows():
            index_data=[]
            for c in row:
                if type(c) is not list or type(c[0]) is not str:
                    index_data.append(c)
                    continue
                ids=[]
                for word in c:
                    ids.append(vocab.get(word,default))
                index_data.append(ids)
            index_datas.append(index_data)
        self.index_datas=pd.DataFrame(index_datas)

    def save(self,path):
        pickle.dump([self.datas,self.index_datas,self.questions],open(path,"wb"))
        
    def load(self,path):
        self.datas,self.index_datas,self.questions=pickle.load(open(path,"rb"))
        self.column_num=self.datas.columns.size
        
    def get_column_data(self,column=0,ispadding=True,max_len=30,value=1):
        '''获取某一列的数据，0： 问题列， 1： 答案列， 2：标签列
        '''
        if type(column) is list:
            columns=self.index_datas[column].values
            datas=[]
            for c in columns:
                a=[]
                for e in c:
                    a.extend(e)
                datas.append(a)
        else:
            datas=self.index_datas[column].values
        if ispadding:
            return padding(datas,maxlen=max_len,value=value) #1是序列结束符号
        else:
            return datas
    def get_column_text(self,column=0):
        '''获取某一列的数据，0： 问题列， 1： 答案列， 2：标签列
        '''
        datas=self.datas[column]
        return datas.values
            
    def get_groupy_by_label(self,label_column=2):
        '''按照标签分组
        '''
        grouped=self.index_datas.groupby(by=label_column)
        return grouped.groups
    def get_pos_negs(self,label_column=2,min_pos_value=0.5):
        '''获取每个正确答案对应的负样本（错误答案）
        '''
        pos_answers={}
        for key,ids in self.questions.groups.items():
            group=self.questions.get_group(key)
            pos=group[group.iloc[:,label_column].apply(lambda x:float(x))>min_pos_value]
            pos_index=pos.index.values
            for v in pos_index:
                pos_answers[v]=ids
        return pos_answers
    def get_candidates(self):
        '''按问题分组，获取每个问题对应的候选答案
        '''
        candidates=self.questions.groups
        return candidates
    def random_neg_sample(self,pos_ids,pos_negs=None):
        '''随机产生正样本对应的负样本的index
        '''
        if pos_negs is None:
            pos_negs=self.get_pos_negs(label_column=self.label_column)
        neg=[]
        for idx in pos_ids:
            negs=pos_negs.get(idx,[idx])
            negs=list(negs)
            r=random.sample(negs,1)[0]
            neg.append(r)
        return neg
    def get_train_pairs(self,neg_num=1):
        '''获取训练样本的问题，正样本，负样本的index
        '''
        #按标签分组
        if self.pos_ids is None or self.pos_negs is None:
            grouped=self.get_groupy_by_label(label_column=self.label_column)
            self.pos_ids=grouped[1]
            self.pos_negs=self.get_pos_negs(label_column=self.label_column)
        negs=[]
        for i in range(neg_num):
            random_negs=self.random_neg_sample(self.pos_ids,self.pos_negs)   
            negs.append(random_negs)
        return self.pos_ids,negs

def get_train_data():
    path=config.train_pattern_triples
    ds=Dataset(path,config.vocab,process=False)
    questions=ds.get_column_data(1,ispadding=True,max_len=config.question_len)
    subjects=ds.get_column_data(2,ispadding=True,max_len=5)
    predicates=ds.get_column_data(3,ispadding=True,max_len=7)
    objects=ds.get_column_data(4,ispadding=True,max_len=8)
    answers=np.concatenate([predicates,objects],axis=-1)
    #answers=ds.get_column_data([3,4],ispadding=True,max_len=config.answer_len)
    return ds,questions,answers       
        
def example():
    '''例子
    '''
    path=config.getPathConfig("data","seg_train_data")
    
    print(path)
    ds=Dataset(path,process=True)
    q=ds.questions
    print(len(q))
    data=ds.datas
    ids=ds.index_datas
    print(len(ids))
    #data=pd.read_table(path,header=None)
    values=data.values
    print(values[0])
    questions=ds.get_column_data(0,ispadding=True,max_len=30)
    answers=ds.get_column_data(1,ispadding=True,max_len=100)
    labels=ds.get_column_data(2,ispadding=False)
    print(len(questions),len(answers),len(labels))
    #按标签分组
    grouped=ds.get_groupy_by_label()
    neg_ids=grouped[0]
    pos_ids=grouped[1]
    pos_negs=ds.get_pos_negs()
    random_negs=ds.random_neg_sample(pos_ids,pos_negs)    
    print(len(neg_ids),len(random_negs))    

def preprocess():
    '''数据预处理'''
    path=config.train_pattern_triples
    Dataset(path,label_column=None,vocab=vocab,process=True)
    #训练数据
    ds=Dataset(config.seg_train_path,label_column=4,vocab=vocab,process=True)
    #测试数据
    ds=Dataset(config.seg_test_path,label_column=None,vocab=vocab,process=True)
    

if __name__=='__main__':
    #path=config.train_pattern_triples
    #Dataset(path,label_column=None,vocab=vocab,process=True)
    #训练数据
    ds=Dataset(config.seg_train_path,label_column=4,vocab=vocab,process=True)
    #测试数据
    ds=Dataset(config.seg_test_path,label_column=None,vocab=vocab,process=True)
