#coding:utf-8
import logging
import numpy as np
from gensim.models import Word2Vec
from gensim.models.word2vec import KeyedVectors,LineSentence
import itertools
import sys
sys.path.append('..')
import config
import pickle
import os
MAX_WORDS_IN_BATCH=1000


logger=logging.Logger(name="word2vec",level=logging.INFO)
logging.basicConfig(format='%(asctime)s:%(levelname)s:%(message)s')
logging.root.setLevel(level=logging.INFO)

question_path=config.getPathConfig("question","all_seg_question")

model_path=config.getPathConfig("word2vec","word2vec")+".teem"
emb_dim=config.getIntConfig("word2vec","emb_dim")

vocab_path=config.vocab_path+".teem"
emb_path=config.emb_path+".teem"

                
def train_word2vec():
    '''训练词项向量
    '''
    model=Word2Vec(sentences=LineSentence(question_path),size=emb_dim,window=5,min_count=5,iter=10)
    model.wv.save_word2vec_format(model_path,binary=True)
    return model
    
def embed():
    model=KeyedVectors.load_word2vec_format(model_path,binary=True)
    vocab=model.vocab
    size=len(vocab)+2
    embedding=np.zeros(shape=[size,emb_dim])
    w2idx={'UNK':0,"<END>":1}
    index=2
    for word in vocab.keys():
        if word in vocab:
            embedding[index]=model[word]
        w2idx[word]=index
        index+=1
    np.save(open(emb_path,"wb"),embedding)
    pickle.dump(w2idx,open(vocab_path,'wb'))
    return w2idx,embedding
    
if __name__=="__main__":
    #train_word2vec()
    vocab,emb=embed()
