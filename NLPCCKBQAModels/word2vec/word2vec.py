#coding:utf-8
import logging
import numpy as np
from gensim.models import Word2Vec
from gensim.models.word2vec import KeyedVectors,LineSentence
import itertools
import sys
sys.path.append('..')
import config
MAX_WORDS_IN_BATCH=1000


logger=logging.Logger(name="word2vec",level=logging.INFO)
logging.basicConfig(format='%(asctime)s:%(levelname)s:%(message)s')
logging.root.setLevel(level=logging.INFO)

kb_path=config.getPathConfig("kb","seg_kb")
question_path=config.getPathConfig("question","all_seg_question")

model_path=config.getPathConfig("word2vec","word2vec")
emb_dim=config.getIntConfig("word2vec","emb_dim")

def any2unicode(text, encoding='utf8', errors='strict'):
    """Convert a string (bytestring in `encoding` or unicode), to unicode."""
    if isinstance(text, str):
        return text
    return str(text, encoding, errors=errors)
to_unicode = any2unicode
class MyLineSentence(object):
    """
    Simple format: one sentence = one line; words already preprocessed and separated by whitespace.
    """

    def __init__(self, sources, max_sentence_length=MAX_WORDS_IN_BATCH, limit=None):
        """
        `source` can be either a string or a file object. Clip the file to the first
        `limit` lines (or no clipped if limit is None, the default).
        """
        self.sources = sources
        self.max_sentence_length = max_sentence_length
        self.limit = limit

    def __iter__(self):
        """Iterate through the lines in the source."""
        for source in self.sources:
            try:
                # Assume it is a file-like object and try treating it as such
                # Things that don't have seek will trigger an exception
                source.seek(0)
                for line in itertools.islice(source, self.limit):
                    line = to_unicode(line).split()
                    i = 0
                    while i < len(line):
                        yield line[i : i + self.max_sentence_length]
                        i += self.max_sentence_length
            except AttributeError:
                # If it didn't work like a file, use it as a string filename
                with open(source,encoding="utf-8") as fin:
                    for line in itertools.islice(fin, self.limit):
                        line = to_unicode(line).split()
                        i = 0
                        while i < len(line):
                            yield line[i : i + self.max_sentence_length]
                            i += self.max_sentence_length
                
def train_word2vec():
    '''训练词项向量
    '''
    model=Word2Vec(sentences=MyLineSentence([kb_path,question_path]),size=emb_dim,window=5,min_count=5,iter=5)
    model.wv.save_word2vec_format(model_path,binary=True)
    return model
    
if __name__=="__main__":
    train_word2vec()
