#coding:utf-8
import re

def read_table(path,sep="\t"):
    f=open(path,encoding="utf-8")
    datas=[]
    for line in f:
        data=line.strip().split(sep)
        datas.append(data)
    return datas
    
def read_words(path,pattern="\s+"):
    f=open(path,encoding="utf-8")
    datas=[]
    for line in f:
        data=re.split(pattern,line)
        datas.append(data)
    return datas
    
def read_lines(path):
    f=open(path,encoding="utf-8")
    return f.readlines()
    
def build_args(args):
    '''sys.args命令行里的数据转换成dict
    '''
    assert len(args)%2==1
    arg_dict={}
    for i in range(1,len(args),2):
        if not args[i].startswith("-"):
            print("Usage:python main.py -cmd1 value1 -cmd2 value2")
            return
        key=re.sub("[-\s+]","",args[i])
        value=re.sub("\s+","",args[i+1])
        arg_dict[key]=value
    return arg_dict