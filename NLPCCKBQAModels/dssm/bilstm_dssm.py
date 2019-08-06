# -*- coding: utf-8 -*-
#DSSMModel
import  pickle
from keras.preprocessing import sequence
import keras.backend as K
from keras.layers import Input,merge,Conv1D,MaxPooling1D,LSTM,Dropout,Lambda,Flatten,Dense,Embedding
from keras.models import Model
import numpy as np
import base_dssm
import sys
sys.path.append("../../..")
import config
from myutils.train_util import get_weight_path
from data.data import Dataset



vocab=pickle.load(open(config.char_vocab_path,'rb'))
size=len(vocab)
embedding=np.load(open(config.char_embedding,'rb')).astype("float32")
embedding=embedding/np.sqrt((np.sum(np.square(embedding),axis=-1,keepdims=True)+1e-8)) #一定要进行标准化


class BiLSTMSM(base_dssm.BaseDSSM):
    '''DSSM模型'''
    def __init__(self,samples_num=1000):
        self.weight_path=get_weight_path(self,config.base_weight_path)
        self.emb_dim=config.emb_dim
        self.question_len=50
        self.predicate_len=20
        #问题rnn
        input_1=Input(shape=(50,),dtype='int32')
        
        emb=Embedding(input_dim=base_dssm.size,output_dim=self.emb_dim,weights=[embedding])
        dropout=Dropout(0.25)
        max_pool=Lambda(lambda x:K.max(x,axis=1,keepdims=False),output_shape=lambda x:(x[0],x[2]))
        sum_pool=Lambda(lambda x:K.sum(x,axis=1,keepdims=False),output_shape=lambda x:(x[0],x[2]))
        
        #maxpooling
        emb_1=emb(input_1)
        lstm_f_q=LSTM(128,return_sequences=True,dropout=0.2)(emb_1)
        lstm_b_q=LSTM(128,return_sequences=True,dropout=0.2,go_backwards=True)(emb_1)
        #lstm_q=merge([lstm_f_q,lstm_b_q],mode=lambda x:x[0]+x[1][::-1],output_shape=lambda x:x[0])
        question_pool_f=max_pool(lstm_f_q)
        question_pool_b=max_pool(lstm_b_q)
        question_drop_f=dropout(question_pool_f)
        question_drop_b=dropout(question_pool_b)
        question_drop=merge([question_drop_f,question_drop_b],mode="sum")
        question_out=Dense(128)(question_drop)
        

        #谓语属性Model
        input_2=Input(shape=(5,),dtype='int32')
        emb_2=emb(input_2)
        lstm_f_p=LSTM(128,return_sequences=True,dropout=0.2)(emb_2)
        lstm_b_p=LSTM(128,return_sequences=True,dropout=0.2,go_backwards=True)(emb_2)
        #lstm_p=merge([lstm_f_p,lstm_b_p],mode=lambda x:x[0]+x[1][::-1],output_shape=lambda x:x[0])
        predicate_pool_f=max_pool(lstm_f_p)
        predicate_pool_b=max_pool(lstm_b_p)
        predicate_drop_f=dropout(predicate_pool_f)
        predicate_drop_b=dropout(predicate_pool_b)
        predicate_drop=merge([predicate_drop_f,predicate_drop_b],mode="sum")
        predicate_out=Dense(128)(predicate_drop)
        
        
        
        sim=Lambda(lambda x:base_dssm.cosine(x[0],x[1]),output_shape=lambda x:(None,1))([question_out,predicate_out])
        sim_model=Model([input_1,input_2],sim)
        model_1=Model(input_1,question_out)
        model_1.compile(optimizer='adam',loss='mse')
        model_2=Model(input_2,predicate_out)
        model_2.compile(optimizer='adam',loss='mse')
        self.model_1=model_1
        self.model_2=model_2
        self.sim_model=sim_model
        self.build()

    

if __name__=="__main__":
    ds=Dataset(config.predicate_train_data,vocab=vocab,label_column=2,process=False)
    model=BiLSTMSM()
    #model.load_weights()
    
    model.train(ds,iter_num=100,nb_epoch=1)
    model.save_weights()
    
    base_dssm.encodeData2file(model)
    '''
    ds=Dataset(data_path=config.seg_train_triples+".chars",vocab=vocab,label_column=None,process=False)
    questions=ds.get_column_data(0,ispadding=True,max_len=50)
    predicates=ds.get_column_data(2,ispadding=True,max_len=20)
    encoded_predicates=model.encode_predicate(predicates)
    encoded_questions=model.encode_question(questions)
    '''