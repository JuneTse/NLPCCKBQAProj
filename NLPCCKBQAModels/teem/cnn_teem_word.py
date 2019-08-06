# -*- coding: utf-8 -*-
#TEEM: Topic Entity Extraction
from keras.layers import Conv1D,Input,Embedding,Flatten,Dropout,concatenate
from keras.models import Model
from keras.optimizers import Adam
from keras.objectives import categorical_crossentropy,mse
from keras.regularizers import l2
from pprint import pprint
import sys
sys.path.append("../")
import config
from myutils.train_util import get_weight_path
from myutils.preprosessing import padding
from data.question_data_teem_word import get_train_teem_data,readAllQuestions,readTrainData,get_test_teem_data
import pickle
import numpy as np
import keras.backend as K
from myutils.regularizations import EarlyStopping
from sklearn.model_selection import train_test_split
import re

vocab_path=config.vocab_path+".teem"
emb_path=config.emb_path+".teem"

vocab=pickle.load(open(vocab_path,"rb"))
idx2w=dict([(i,w) for w,i in vocab.items()])
embedding=np.load(open(emb_path,"rb"))
embedding=embedding/np.sqrt((np.sum(np.square(embedding),axis=-1,keepdims=True)+1e-8))

def seq_binary_entropy_loss(y_true, y_pred):
    y_pred=K.clip(y_pred,1e-6,1-1e-6)
    return -K.sum(y_true*K.log(y_pred)+(1-y_true)*K.log(1-y_pred),axis=-1)
    
class CNNTEEMWord(object):
    def __init__(self,l2_scale=0.001):
        self.emb_dim=config.emb_dim
        self.weight_path=get_weight_path(self,config.base_weight_path)
        self.question_len=20
        
        #构建模型
        question_in=Input((self.question_len,))
        #embedding层
        emb=Embedding(input_dim=len(embedding),output_dim=self.emb_dim,weights=[embedding])(question_in)
        emb=Dropout(0.8)(emb)
        #卷积层
        cnn=Conv1D(64,5,padding='same',activation="relu",kernel_regularizer=l2(l2_scale))(emb)
        cnn=Dropout(0.6)(cnn)
        #卷积层
        cnn=Conv1D(128,5,padding='same',activation="relu",kernel_regularizer=l2(l2_scale))(cnn)
        dropout=Dropout(0.5)(cnn)
        #输出层
        cnn=Conv1D(1,5,padding='same',activation="sigmoid",kernel_regularizer=l2(l2_scale))(dropout)
        out=Flatten()(cnn)
        model=Model(question_in,out)
        model.compile(optimizer=Adam(0.001),loss=seq_binary_entropy_loss,metrics=['accuracy'])
        
        self.model=model
    def train(self,x,y,nb_epoch=10,batch_size=128,val_split=0.3):
        X_train,X_valid,y_train,y_valid=train_test_split(x,y,test_size=val_split,random_state=42)
        es=EarlyStopping(patient=5,delta=0.01,best=-np.inf,monitor=np.greater)
        for i in range(nb_epoch):
            self.model.fit(X_train,y_train,batch_size=batch_size,epochs=1,shuffle=True)
            pre=self.predict(X_train)
            acc=self.accuracy(y_train,pre)
            valid_pre=self.predict(X_valid)
            valid_acc=self.accuracy(y_valid,valid_pre)
            print("iter_num: %s, train acc: %s, valid_acc:%s" %(i,acc,valid_acc))
            if es.is_early_stop(valid_acc):
                print("Early Stopping....: best:%s,current:%s"%(es.best,valid_acc))
                break
        self.model.fit(x,y,batch_size=batch_size,epochs=10,shuffle=True)
    def predict(self,X_test):
        '''预测结果'''
        pre=self.model.predict(X_test)
        return pre 
    def load_weights(self):
        self.model.load_weights(self.weight_path)
    def save_weigths(self):
        self.model.save_weights(self.weight_path,overwrite=True)
    def accuracy(self,true_y,pre_y,delta=0.8):
        '''计算准确率
        '''
        all_num=len(true_y)
        right_num=0
        pre_y=np.greater(pre_y,np.min(delta,np.max(pre_y,axis=-1))).astype(np.int32)
        
        for y1,y2 in zip(true_y,pre_y):
            if np.sum(y1!=y2)==0:
                right_num+=1
        return right_num/all_num
    def show_results(self,questions,pre,delta=0.8):
        '''显示结果'''
        results=[]
        for p,q in zip(pre,questions):
            question=re.split("\\s+",q)
            topicEntity="".join([question[i] for i in range(len(p)) if p[i]>=min(delta,max(p)) and i<len(question)])
            results.append(["".join(question),topicEntity])
        return results
    def write_topicEntity(self,results,outpath=config.test_subject_result):
        with open(outpath,"w",encoding="utf-8") as f:
            for q,e in results:
                f.write(q+"\t"+e+"\n")
                
def write_results(model,inpath=config.seg_test_question_path,outpath=config.test_subject_result,question_len=50):
    questions=readAllQuestions(inpath)
    qidx=get_test_teem_data(questions,question_len=question_len)
    pre=model.predict(qidx)
    results=model.show_results(questions,pre)
    model.write_topicEntity(results,outpath=outpath)
    return pre,results
if __name__=="__main__":
    args=sys.argv
    nb_epoch=10
    l2_scale=0.001
    if len(args)==2:
        nb_epoch=int(args[1].strip())
    if len(args)==3:
        nb_epoch=int(args[1].strip())
        l2_scale=float(args[2].strip())
        
    qs,labels=get_train_teem_data(question_len=20) 
    model=CNNTEEMWord(l2_scale=l2_scale)
    '''
    model.train(qs,labels,nb_epoch=100,val_split=0.3)
    model.save_weigths()
    '''
    model.load_weights()
    
    pre,results=write_results(model,inpath=config.seg_test_question_path,outpath=config.test_subject_result,question_len=20)
    tpre,tresults=write_results(model,inpath=config.seg_train_question_path,outpath=config.train_subject_result,question_len=20)
