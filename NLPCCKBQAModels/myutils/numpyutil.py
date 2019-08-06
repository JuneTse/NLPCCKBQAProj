#coding:utf-8
import numpy as np

def cos(a,b):
    '''a,b: 2D tensor,shape相同
    '''
    up=batch_dot(a,b)
    bottom=np.sqrt(batch_dot(a,a)*batch_dot(b,b))+1e-8
    return up/bottom    
    

def batch_dot(a,b):
    '''a,b: 2D tensor
    '''
    return np.sum(a*b,axis=-1,keepdims=False)