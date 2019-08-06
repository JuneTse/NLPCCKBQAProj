#coding:utf-8
"""分词器
"""

def ngram_segment(sent,pad="#",n=3):
    '''n-gram分词
    '''
    sent=sent.replace(" ","")
    chars=list(sent)
    pad_num=n//2
    chars=[pad]*pad_num+chars+[pad]*pad_num
    words=[]
    for i in range(len(chars)-n):
        words.append("".join(chars[i:i+n]))
    return words
        
        
if __name__=="__main__":
    words=ngram_segment("我们都是好孩子！")
    print(words)