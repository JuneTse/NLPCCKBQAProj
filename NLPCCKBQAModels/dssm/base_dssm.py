# -*- coding: utf-8 -*-
#DSSMModel
import pickle
from keras.preprocessing import sequence
import keras.backend as K
from keras.layers import Input,merge,Conv1D,MaxPooling1D,LSTM,Dropout,Lambda,Flatten,Dense,Embedding,add
from keras.models import Model
import numpy as np
import sys
sys.path.append("../")
import config
from data.data import Dataset
from myutils.train_util import get_weight_path
import os
from myutils.preprosessing import padding
import random
from keras.callbacks import EarlyStopping

vocab=pickle.load(open(config.char_vocab_path,'rb'))
id2w=dict([(i,w) for w,i in vocab.items()])
size=len(vocab)
embedding=np.load(open(config.char_embedding,'rb')).astype("float32")
#embedding=embedding/np.sqrt((np.sum(np.square(embedding),axis=-1,keepdims=True)+1e-8))

#cos函数
def cosine(x1,x2):
    return K.sum(x1*x2,axis=-1)/(K.sqrt(K.sum(x1*x1,axis=-1)*K.sum(x2*x2,axis=-1))+0.0000001) #cos
    
#定义cos目标函数
def cosine_error(x):  #x=[x1,x2,x3,x4] ,xi.shape=(batch_size,input_dim)
    cos1=cosine(x[0],x[1]) #cos shape=(batch_size,)
    cos2=cosine(x[0],x[2])
    cos3=cosine(x[0],x[3])
    cos4=cosine(x[0],x[4])
    cos5=cosine(x[0],x[5])
    cos6=cosine(x[0],x[6])
    delta=5 
    p=K.exp(cos1*delta)/(K.exp(cos1*delta)+K.exp(cos2*delta)+K.exp(cos3*delta)+K.exp(cos4*delta)+K.exp(cos5*delta)+K.exp(cos6*delta)) #softmax
    f=-K.log(p) #objective function：-log  #f.shape=(batch_size,)
    return K.reshape(f,(K.shape(p)[0],1))  #return.sahpe=(batch_size,1)

def neg_log_loss(x):
    cos1=x[0]
    cos2=x[1]
    cos3=x[2]
    cos4=x[3]
    #cos5=x[4]
    #cos6=x[5]
    delta=5 
    p=K.exp(cos1*delta)/(K.exp(cos1*delta)+K.exp(cos2*delta)+K.exp(cos3*delta)+K.exp(cos4*delta))#+K.exp(cos5*delta)+K.exp(cos6*delta)) #softmax
    f=-K.log(p) #objective function：-log  #f.shape=(batch_size,)
    return K.reshape(f,(K.shape(p)[0],1))  #return.sahpe=(batch_size,1)
#def hinge_loss(x,margin=0.5):
#    pos=x[0]
#    negs=[x[1],x[2],x[3]]
#    losses=[K.maximum(0.0,neg-pos+margin) for neg in negs]
#    loss=losses[0]+losses[1]+losses[2]
#    return loss
    
class BaseDSSM(object):
    '''DSSM模型'''
    def __init__(self,samples_num=1000):
        self.weight_path=get_weight_path(self,config.base_weight_path)
        self.emb_dim=config.emb_dim
        self.question_len=50
        self.predicate_len=20
        
        #问题rnn
        input_1=Input(shape=(self.question_len,),dtype='int32')
        emb=Embedding(input_dim=size,output_dim=self.emb_dim,weights=[embedding])
        dropout=Dropout(0.25)
        sum_pool=Lambda(lambda x:K.sum(x,axis=1,keepdims=False),output_shape=lambda x:(x[0],x[2]))
        
        #maxpooling
        emb_1=emb(input_1)
        question_cnn=Conv1D(128,5,padding='same',activation="relu")(emb_1)
        question_pool=MaxPooling1D(5)(question_cnn)
        question_cnn=Conv1D(128,3,padding='same',activation='tanh')(question_pool)
        question_flat=sum_pool(question_cnn)
        question_drop=dropout(question_flat)
        question_out=Dense(128)(question_drop)
        
        #谓语属性Model
        input_2=Input(shape=(self.predicate_len,),dtype='int32')
        emb_2=emb(input_2)
        predicate_cnn=Conv1D(128,5,padding='same',activation="tanh")(emb_2)
        predicate_pool=MaxPooling1D(2)(predicate_cnn)
        predicate_cnn=Conv1D(128,3,padding='same',activation="tanh")(predicate_pool)
        predicate_flat=sum_pool(predicate_cnn)
        predicate_drop=dropout(predicate_flat)
        predicate_out=Dense(128)(predicate_drop)
        
        
        #sim=merge(inputs=[question_out,predicate_out],mode=lambda x:cosine(x[0],x[1]),output_shape=lambda x:(None,1))   
        
        sim=Lambda(lambda x:cosine(x[0],x[1]),output_shape=lambda x:(None,1))([question_out,predicate_out])
        sim_model=Model([input_1,input_2],sim)
        self.sim_model=sim_model
        
        model_1=Model(input_1,question_out)
        model_1.compile(optimizer='adam',loss='mse')
        model_2=Model(input_2,predicate_out)
        model_2.compile(optimizer='adam',loss='mse')
        self.model_1=model_1
        self.model_2=model_2
        
        self.build()
    def build(self):
         #输入正样本和负样本
        question_in=Input(shape=(50,),dtype='int32')
        predicate_pos=Input(shape=(20,),dtype='int32')
        predicate_neg_1=Input(shape=(20,),dtype='int32')
        predicate_neg_2=Input(shape=(20,),dtype='int32')
        predicate_neg_3=Input(shape=(20,),dtype='int32')
        sim1=self.sim_model([question_in,predicate_pos])
        sim2=self.sim_model([question_in,predicate_neg_1])
        sim3=self.sim_model([question_in,predicate_neg_2])
        sim4=self.sim_model([question_in,predicate_neg_3])
        #计算损失
        loss=Lambda(lambda x:neg_log_loss(x),output_shape=lambda x:(x[0],1))([sim1,sim2,sim3,sim4])

        #构造模型    
        self.model=Model([question_in,predicate_pos,predicate_neg_1,predicate_neg_2,predicate_neg_3],outputs=loss)
        self.model.compile(optimizer="adam",loss=lambda y_true,y_pred:y_pred,metrics=['accuracy'])
        
    def split_data(self,sample_num,val_split=0.1):
        shuffle_ids=random.sample(list(range(sample_num)),sample_num)
        val_num=int(sample_num*val_split)
        train_ids,val_ids=shuffle_ids[:sample_num-val_num],shuffle_ids[-val_num:]
        return train_ids,val_ids
    def build_train_data(self,all_questions,all_answers,pos_ids,neg_ids1,neg_ids2,neg_ids3):
        questions=all_questions[pos_ids]
        answers=all_answers[pos_ids]
        bad_answers1=all_answers[neg_ids1]
        bad_answers2=all_answers[neg_ids2]
        bad_answers3=all_answers[neg_ids3]
        return questions,answers,bad_answers1,bad_answers2,bad_answers3
    def train(self,ds,iter_num=15,nb_epoch=1,batch_size=128,val_split=0.2):
        all_questions=ds.get_column_data(0,ispadding=True,max_len=self.question_len)
        all_answers=ds.get_column_data(1,ispadding=True,max_len=self.predicate_len)
        #all_answers=ds.get_column_data(2,ispadding=True,max_len=20)
        #按标签分组
        grouped=ds.get_groupy_by_label(label_column=2)
        neg_ids=np.array(grouped[0])
        pos_ids=np.array(grouped[1])
        
        samples_num=len(pos_ids)
        labels=np.array([[0]]*samples_num)
        #把训练数据分成训练集和验证集
        train_ids,valid_ids=self.split_data(sample_num=samples_num,val_split=val_split)
        
        for it in range(iter_num):
            print('iter:',it)

            pos_ids,[neg_ids1,neg_ids2,neg_ids3]=ds.get_train_pairs(neg_num=3)
            questions,predicates,predicates_b,predicates_c,predicates_d=self.build_train_data(all_questions,all_answers,pos_ids,neg_ids1,neg_ids2,neg_ids3)
            self.model.fit([questions,predicates,predicates_b,predicates_c,predicates_d],labels,epochs=nb_epoch,batch_size=batch_size)
            if (iter_num+1)%10==0:
                self.save_weights()
            sim=self.sim_model.predict([questions,predicates])
            print(sim.shape,sim[:10])
                        
    def encode_question(self,question): #question.shape=(samples,4000)
        return self.model_1.predict(question) #return.shape=(samples,128)
    
    def encode_predicate(self,predicate): #question.shape=(samples,1000)
        return self.model_2.predict(predicate) #return.shape=(samples,128)
        
        #保存权值
    def save_weights(self):
        self.sim_model.save_weights(self.weight_path,overwrite=True)
        #加载权值   
    def load_weights(self):
        self.sim_model.load_weights(self.weight_path)
        #self.model.compile(optimizer="adam",loss='mse',metrics=['accuracy'])
        
def write_encoded_data(encoded_data,path):
    f=open(path,'w')
    for data in encoded_data:
        for e in data:
            f.write(str(e)+" ")
        f.write('\n')
    f.close()
    
def load_questions(vocab=vocab,path=config.seg_test_question_path):
    '''加载问题'''
    f=open(path,encoding="utf-8")
    questions=[]
    for line in f:
        question=list(line.strip().replace(" ",""))
        q_seq=[]
        for q in question:
            q_seq.append(vocab.get(q,0))
        questions.append(q_seq)
    return padding(questions,50,value=1)
    
def load_predicates(vocab=vocab,path=config.all_predicate_path):
    '''加载predicate'''
    f=open(path,encoding="utf-8")
    predicates=[]
    for line in f:
        predicate=list(line.strip().replace(" ",""))
        p_seq=[]
        for w in predicate:
            i=vocab.get(w,0)
            p_seq.append(i)
        predicates.append(p_seq)
    return padding(predicates,maxlen=20,value=1)#padding(datas,maxlen=max_len,value=1)
    
def encodeData2file(model):
    print("encodeData2file....")
    base_path=os.path.join(config.base_encoded_path,model.__class__.__name__)
    questions=load_questions()
    questions2017=load_questions(vocab=vocab,path=config.seg_test_question2017_path)
    predicates=load_predicates()
    encoded_predicates=model.encode_predicate(predicates)
    write_encoded_data(encoded_predicates,base_path+"_encoded_predicates.txt")
    encoded_questions=model.encode_question(questions)
    write_encoded_data(encoded_questions,base_path+"_encoded_questions.txt")
    encoded_questions2017=model.encode_question(questions2017)
    write_encoded_data(encoded_questions2017,base_path+"_encoded_questions2017.txt")
    print("encoded...")
    
def cos_sim(x,y):
    return np.sum(x*y,axis=-1)/np.sqrt(np.sum(x*x,axis=-1)*np.sum(y*y,axis=-1)+0.0000001)    
if __name__=="__main__":
    ds=Dataset(config.predicate_train_data,vocab=vocab,label_column=2,process=False)
    model=BaseDSSM(ds)
    #model.load_weights()
    
    model.train(ds,iter_num=30)
    model.save_weights()
    encodeData2file(model)
    
    ds=Dataset(data_path=config.seg_train_triples+".chars",vocab=vocab,label_column=None,process=True)
    questions=ds.get_column_data(0,ispadding=True,max_len=50)
    predicates=ds.get_column_data(2,ispadding=True,max_len=20)
    encoded_predicates=model.encode_predicate(predicates)
    encoded_questions=model.encode_question(questions)
    sims=cos_sim(encoded_predicates[0:10],encoded_questions[0:10])
    #encodeData2file(model)
    
