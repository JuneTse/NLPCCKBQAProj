# -*- coding: utf-8 -*-
#TEEM: Topic Entity Extraction
from keras.layers import Conv1D,Input,Embedding,Flatten,Dropout
from keras.models import Model
from keras.optimizers import Adam
from keras.objectives import categorical_crossentropy,mse
from keras.regularizers import l2
from base_teem import BaseTEEM
from pprint import pprint
import sys
sys.path.append("../")
import config
from data.data import Dataset
from myutils.train_util import get_weight_path
import os
from myutils.preprosessing import padding
from data.question_data_teem import get_train_teem_data,readAllQuestions,readTrainData,get_test_teem_data
import pickle
import numpy as np
import keras.backend as K

vocab=pickle.load(open(config.char_vocab_path,'rb'))
idx2w=dict([(i,w) for w,i in vocab.items()])
embedding=np.load(open(config.char_embedding,'rb')).astype("float32")
embedding=embedding/np.sqrt((np.sum(np.square(embedding),axis=-1,keepdims=True)+1e-8))
question_len=50

def seq_binary_entropy_loss(y_true, y_pred):
    y_pred=K.clip(y_pred,1e-6,1-1e-6)
    return -K.sum(y_true*K.log(y_pred)+(1-y_true)*K.log(1-y_pred),axis=-1)
    
class CNNTEEM(BaseTEEM):
    def __init__(self,l2_scale=0.001):
        self.emb_dim=config.emb_dim
        self.weight_path=get_weight_path(self,config.base_weight_path)
        #构建模型
        question_in=Input((question_len,))
        #embedding层
        emb=Embedding(input_dim=len(vocab),output_dim=self.emb_dim,weights=[embedding],mask_zero=False)(question_in)
        #emb=Dropout(0.5)(emb)
        #卷积层
        cnn=Conv1D(128,5,padding='same',activation="tanh",kernel_regularizer=l2(l2_scale))(emb)
        dropout=Dropout(0.3)(cnn)
        cnn=Conv1D(1,5,padding='same',activation="sigmoid",kernel_regularizer=l2(l2_scale))(dropout)
        out=Flatten()(cnn)
        model=Model(question_in,out)
        model.compile(optimizer=Adam(0.001),loss=seq_binary_entropy_loss,metrics=['accuracy'])
        self.model=model
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
        
    qs,labels=get_train_teem_data(question_len=50)
    
    model=CNNTEEM(l2_scale=l2_scale)
    model.train(qs,labels,nb_epoch=100,val_split=0.3)
    model.save_weigths()
    model.load_weights()
    
    pre,results=write_results(model,inpath=config.seg_test_question_path,outpath=config.test_subject_result,question_len=50)

    #model.train(qs,labels)
    #model.save_weigths()
