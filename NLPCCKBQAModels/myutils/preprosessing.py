#coding:utf-8
from keras.preprocessing import sequence

def padding(seq,maxlen,value=0,truncating='pre'):
    '''把数据padding成长度一致的'''
    return sequence.pad_sequences(sequences=seq,maxlen=maxlen,dtype='int32',value=value,truncating=truncating,padding='post')