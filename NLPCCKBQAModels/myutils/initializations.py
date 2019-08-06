#coding:utf-8
import tensorflow as tf

def init_weights(shape,l2=None,name=None):
    w=tf.Variable(tf.random_normal(shape=shape,mean=0.0,stddev=1.0),name=name)
    if not l2 is None:
        tf.add_to_collection("losses",tf.nn.l2_loss(w)*l2)
    return w
    
def init_bias(shape,name=None):
    b=tf.Variable(tf.zeros(shape),name=None)
    return b