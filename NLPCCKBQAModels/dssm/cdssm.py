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
sys.path.append("../")
import config
from myutils.train_util import get_weight_path
from data.data import Dataset



vocab=pickle.load(open(config.char_vocab_path,'rb'))
size=len(vocab)
embedding=np.load(open(config.char_embedding,'rb')).astype("float32")
embedding=embedding/np.sqrt((np.sum(np.square(embedding),axis=-1,keepdims=True)+1e-8))

class CDSSM(base_dssm.BaseDSSM):
    '''DSSM模型'''
    def __init__(self,samples_num=1000):
        self.weight_path=get_weight_path(self,config.base_weight_path)
        self.emb_dim=config.emb_dim
        self.question_len=50
        self.predicate_len=20
        #问题rnn
        emb=Embedding(input_dim=base_dssm.size,output_dim=self.emb_dim,weights=[embedding])
        dropout=Dropout(0.25)
        sum_pool=Lambda(lambda x:K.sum(x,axis=1,keepdims=False),output_shape=lambda x:(x[0],x[2]))
        
        #question
        question_in=Input(shape=(self.question_len,),dtype='int32')
        question_emb=emb(question_in)
        question_cnn=Conv1D(128,5,padding='same',activation="tanh")(question_emb)
        question_pool=MaxPooling1D(5)(question_cnn)
        question_cnn=Conv1D(128,3,padding='same',activation='tanh')(question_pool)
        question_flat=sum_pool(question_cnn)
        question_drop=dropout(question_flat)
        question_out=Dense(128)(question_drop)
        
        #predicate
        predicate_in=Input(shape=(self.predicate_len,),dtype='int32')
        predicate_emb=emb(predicate_in)
        predicate_cnn=Conv1D(128,5,padding='same',activation="tanh")(predicate_emb)
        predicate_pool=MaxPooling1D(2)(predicate_cnn)
        predicate_cnn=Conv1D(128,3,padding='same',activation="tanh")(predicate_pool)
        predicate_flat=sum_pool(predicate_cnn)
        predicate_drop=dropout(predicate_flat)
        predicate_out=Dense(128)(predicate_drop)
        
        #sim=merge(inputs=[question_out,predicate_out],mode=lambda x:base_dssm.cosine(x[0],x[1]),output_shape=lambda x:(None,1))
        sim=Lambda(lambda x:base_dssm.cosine(x[0],x[1]),output_shape=lambda x:(None,1))([question_out,predicate_out])
        sim_model=Model([question_in,predicate_in],sim)
        model_1=Model(question_in,question_out)
        model_1.compile(optimizer='adam',loss='mse')
        model_2=Model(predicate_in,predicate_out)
        model_2.compile(optimizer='adam',loss='mse')
        self.model_1=model_1
        self.model_2=model_2
        self.sim_model=sim_model
        self.build()

    

if __name__=="__main__":
    ds=Dataset(config.predicate_train_data,vocab=vocab,label_column=2,process=False)
    model=CDSSM()
    #model.load_weights()
    
    model.train(ds,iter_num=20,nb_epoch=1)
    model.save_weights()
    
    base_dssm.encodeData2file(model)
    
    ds=Dataset(data_path=config.seg_train_triples+".chars",vocab=vocab,label_column=None,process=True)
    questions=ds.get_column_data(0,ispadding=True,max_len=50)
    predicates=ds.get_column_data(2,ispadding=True,max_len=20)
    encoded_predicates=model.encode_predicate(predicates)
    encoded_questions=model.encode_question(questions)
    sims=base_dssm.cos_sim(encoded_predicates[0:10],encoded_questions[0:10])
    
