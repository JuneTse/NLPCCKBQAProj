#coding:utf-8
import tensorflow as tf
'''
一些常用的数学计算,tensorflow
'''

def cos(a,b):
    '''a,b: 2D tensor,shape相同
    '''
    up=batch_dot(a,b)
    bottom=tf.sqrt(batch_dot(a,a)*batch_dot(b,b))+1e-8
    #bottom=tf.clip_by_value(bottom,clip_value_min=1e-8,clip_value_max=1-1e-8)
    return up/bottom    
    

def batch_dot(a,b):
    '''a,b: 2D tensor
    '''
    return tf.reduce_sum(a*b,axis=-1,keep_dims=False)
    
    
def cal_cos(x1,x2):
    return tf.reduce_sum(x1*x2,axis=-1,keep_dims=False)/(tf.sqrt(tf.reduce_sum(x1*x1,axis=-1,keep_dims=False))*tf.sqrt(tf.reduce_sum(x2*x2,keep_dims=False))+0.00000001)