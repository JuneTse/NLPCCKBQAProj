#coding:utf-8
import logging
import numpy as np
import pickle
from gensim.models import Word2Vec
from gensim.models.word2vec import KeyedVectors,LineSentence
import sys
sys.path.append('..')
import config
import nltk
import itertools
import re
import os

#stop_words='!"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~\'，、；“”：。【】（）《》'
stop_words=''

kb_path=config.kb_path
question_path=config.all_seg_question_path

model_path=config.getPathConfig("word2vec","word2vec")
emb_dim=config.emb_dim

vocab_path=config.vocab_path
emb_path=config.emb_path

def embed():
    model=KeyedVectors.load_word2vec_format(model_path,binary=True)
    if os.path.exists(vocab_path):
        vocab=pickle.load(open(vocab_path,"rb"))
    else:
        iw,vocab,_=build_index()
    size=len(list(vocab.keys()))
    emb=np.zeros(shape=[size,emb_dim])
    for word,index in vocab.items():
        if index in [0,1,2]:
            continue
        emb[index]=model[word]
    np.save(open(emb_path,"wb"),emb)
    return vocab,emb
def wordReader(path):
    '''读取知识库，每次生成一个词
    '''
    f=open(path,encoding="utf-8")
    for line in f:
        if line!="":
            line=line.strip()
            words=re.split("\\s+",line)
            #去掉无用的符号
            words=[a for a in words if a not in stop_words]
            for w in words:
                yield w
    f.close()
def build_index(vocab_size):
    '''统计词频
    '''
    kb_freqDist=nltk.FreqDist(wordReader(kb_path))
    q_freqDist=nltk.FreqDist(wordReader(question_path))
    vocab=kb_freqDist.most_common(vocab_size)
    min_count=vocab[-1][1]
    print(min_count)
    vocab=dict(vocab)
    q_vocab=q_freqDist.most_common(10000)
    for w,c in q_vocab:
        if w in vocab:
            vocab[w]+=c
        else:
            vocab[w]=c
    vocab=sorted(vocab.items(),key=lambda x:x[1],reverse=True)
    vocab=vocab[:vocab_size]
    index2word=['unk','<end>','<subject_placeholder>']+[x[0] for x in vocab]
    word2index=dict([(w,i) for i,w in enumerate(index2word)])
    pickle.dump(word2index,open(vocab_path,"wb"))
    return index2word,word2index
    
if __name__=="__main__":
    id2w,w2id=build_index(300000)
    vocab,emb=embed()
