#coding:utf-8
import tensorflow as tf

def Adam(loss,var_list=None,learning_rate=0.001):
    '''
    优化器:
        AdamOptimizer学习速率不宜设置过大，否则容易过拟合，0.001效果比较好
        AdamOptimizer学习速率不宜太小，否则收敛很慢，
        SDG的学习速率不应太大，否则可能不收敛，0.01比较好
    '''
    opt=tf.train.AdamOptimizer(learning_rate=learning_rate,beta1=0.9,beta2=0.99,epsilon=1e-8)
    train_opt=optimize(opt,loss,var_list)
    return train_opt    
    
def Adadelta(loss,var_list=None,learning_rate=0.001):
    opt=tf.train.AdadeltaOptimizer(learning_rate=learning_rate,rho=0.95,epsilon=1e-8)
    train_opt=optimize(opt,loss,var_list)
    return train_opt    
    
def AdaGrad(loss,var_list=None,learning_rate=0.001):
    opt=tf.train.AdagradDAOptimizer(learning_rate=learning_rate,beta1=0.9,beta2=0.99,epsilon=1e-8)
    train_opt=optimize(opt,loss,var_list)
    return train_opt       

def optimize(opt,loss,var_list):
    if var_list is None:
        grad_vars=opt.compute_gradients(loss)
    else:
        #计算梯度
        grad_vars=opt.compute_gradients(loss,var_list=var_list)
    caped_grads=clip_grad(grad_vars)
    #应用梯度
    train_opt=opt.apply_gradients(caped_grads)   
    return train_opt
    
def clip_grad(grad_vars,low=-2.0,high=2.0):
    #对梯度进行约束
    caped_grads=[(tf.clip_by_value(g,-1.,1.),v) for g,v in grad_vars if g is not None]
    return caped_grads
     