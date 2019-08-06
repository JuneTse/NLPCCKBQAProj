#coding:utf-8
import tensorflow as tf

def mean_square_error(x,y):
    return tf.reduce_mean(tf.square(x-y))
    
def hinge(small,big,margin):
    '''合页损失函数
    '''
    loss=tf.maximum(0,small-big+margin)
    return tf.reduce_mean(loss)
    
def binary_cross_entroy(y_true,y_pre):
    y_pre=tf.clip_by_value(y_pre,clip_value_min=1e-6,clip_value_max=1-1e-6)
    loss=-tf.reduce_mean(y_true*tf.log(y_pre)+(1-y_true)* tf.log(1-y_pre))
    return loss
    
mse=mean_square_error