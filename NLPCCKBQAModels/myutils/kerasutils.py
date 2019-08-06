#coding:utf-8
import keras.backend as K

def cosine(x1,x2):
    return K.sum(x1*x2,axis=-1)/(K.sqrt(K.sum(x1*x1,axis=-1)*K.sum(x2*x2,axis=-1))+1e-6) #cos
    
