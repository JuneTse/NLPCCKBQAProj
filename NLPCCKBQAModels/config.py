#coding:utf-8
import configparser
import os
import pickle
import numpy as np
import sys

#当前目录
directory=os.path.split(os.path.realpath(__file__))[0]
def add_paths(root):
    '''把子目录都添加到path中
    '''
    if not os.path.isdir(root):
        return
    for p in os.listdir(root):
        cur=os.path.join(root,p)
        if os.path.isdir(cur):
            sys.path.append(cur)
        add_paths(cur)
        
add_paths(directory)

'''
读取配置文件
'''
directory=os.path.split(os.path.realpath(__file__))[0]
path=os.path.join(directory,'config.cfg')

#生成config对象
config=configparser.ConfigParser()
config.read(path)

def getPathConfig(section,key):
    '''从配置文件中读取配置的路径
    '''
    return os.path.join(directory,config.get(section,key))
    
def getConfig(section,key):
    return config.get(section,key)
    
def getIntConfig(section,key):
    return config.getint(section,key)
    
def getFloatConfig(section,key):
    return config.getfloat(section,key)
    

#获取配置的参数
#questions
question_len=getIntConfig("question","len")
seg_test_question_path=getPathConfig("question","all_seg_test_question")
seg_test_question2017_path=getPathConfig("question","all_seg_test_question2017")
seg_train_question_path=getPathConfig("question","all_seg_train_question")
seg_train_question_path2017=getPathConfig("question","all_seg_train_question2017")
all_seg_question_path=getPathConfig("question","all_seg_question")


#kb 
kb_chars_path=getPathConfig("kb","seg_ckb")
kb_path=getPathConfig("kb","seg_kb")
sub_kb=getPathConfig("kb","sub_kb")
subject_predicate=getPathConfig("kb","subject_predicate")

all_seg_subject_path=getPathConfig("kb","all_seg_subjects")
all_seg_predicate_path=getPathConfig("kb","all_seg_predicates")
all_subject_path=getPathConfig("kb","all_subjects")
all_predicate_path=getPathConfig("kb","all_predicates")

#train data
train_data_path=getPathConfig("data","train_data")
test_data_path=getPathConfig("data","test_data")
seg_train_path=getPathConfig("data","seg_train_data")
seg_test_path=getPathConfig("data","seg_test_data")

seg_train_triples=getPathConfig("data","seg_train_triples")
seg_train_triples2017=getPathConfig("data","seg_train_triples2017")
seg_train_triples2016=getPathConfig("data","seg_train_triples2016")
seg_test_triples2016=getPathConfig("data","seg_test_triples2016")

train_triples_path=getPathConfig("data","train_triples")
train_triples_path2017=getPathConfig("data","train_triples2017")
train_triples_path2016=getPathConfig("data","train_triples2016")
test_triples_path2016=getPathConfig("data","test_triples2016")

all_candidates_path=getPathConfig("data","seg_all_candidates")

#train predicate matching
predicate_train_data=getPathConfig("predicate","predicate_train_data")
predicate_train_data2017=getPathConfig("predicate","predicate_train_data2017")

#word2vec
emb_dim=getIntConfig("word2vec","emb_dim")
vocab_path=getPathConfig("word2vec","vocab")
emb_path=getPathConfig("word2vec","embedding")   
countInfo_path=getPathConfig("word2vec","countInfo")

char_vocab_path=getPathConfig("word2vec","char_vocab")
char_embedding=getPathConfig("word2vec","char_embedding")

#weight and results
base_weight_path=getPathConfig("weights","base_path")
base_result_path=getPathConfig("results",'base_path')
test_score_path=getPathConfig("results",'test_score')
train_score_path=getPathConfig("results","train_score")

#subject model
question_char_len=getIntConfig("subject","question_len")
subject_char_len=getIntConfig("subject","subject_len")

subject_hop_vector_path=getPathConfig("subject","hop_vector")

subject_train_path=getPathConfig("subject","train_data")
subject_unsupervise_train_path=getPathConfig("subject","unsupervise_train_data")
subject_test_path=getPathConfig("subject","test_data")

test_subject_result=getPathConfig("subject","test_subject_result")
test_subject_result2017=getPathConfig("subject","test_subject_result2017")
train_subject_result=getPathConfig("subject","train_subject_result")

test_predicate_result=getPathConfig("subject","test_predicate_result")
#test_predicate_result2017=getPathConfig("subject","test_predicate_result2017")
subject_stopwords=getPathConfig("subject","subject_stopwords")

#encoded data
base_encoded_path=getPathConfig("encoded_data","base_path")
try:
    #加载词库和embedding
    vocab=pickle.load(open(vocab_path,'rb'))   
    embedding=np.load(open(emb_path,'rb'))
    vocab_size=len(list(vocab.keys()))
except:
    print("vocab not find....")
if __name__=="__main__":
    path=getConfig('data','train_data')
    print(path)