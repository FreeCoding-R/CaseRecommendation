#!/usr/bin/python3
# -*- coding: utf-8 -*-

from gensim import corpora, models, similarities
from six import iteritems
import os
import jieba
import xml.etree.ElementTree as ET
from constant import FILE_PATH
from constant import PYTHON_PATH, STOP_WORDS_FILE


file_path = FILE_PATH

stop_words_file = STOP_WORDS_FILE


def get_stop_words(stop_file):
    result = set([])
    with open(stop_file, 'r', encoding='utf8') as file:
        templist = jieba.cut(file.read())
        # for word in templist:
        #     result.add(word)
        return set(templist)


def delete_stop_words(words):
    result = []
    stoplist = get_stop_words(stop_words_file)
    tempset = jieba.cut(words)
    for word in tempset:
        if word not in stoplist:
            result.append(word)
    return result


# 遍历所有的节点
def walkData(root_node,  result_list):
    item = root_node.items()
    if len(item) >= 2:
        if root_node.tag == 'QW':
            templist = delete_stop_words(item[1][1])
            result_list += templist
        if len(item[1][1]) < 15:
            result_list.append(root_node.tag+','+item[1][1])

    # 遍历每个子节点
    children_node = root_node.getchildren()
    if len(children_node) == 0:
        return
    for child in children_node:
        walkData(child, result_list)
    return


def getXmlData(file_name):
    result_list = []
    root = ET.parse(file_name).getroot()
    walkData(root, result_list)
    return result_list


names = [os.path.join(file_path, f) for f in os.listdir(file_path) if f.endswith('.xml')]
num_topics = len(names)//120

# collect statistics about all tokens
dictionary = corpora.Dictionary(getXmlData(name) for name in names)

once_ids = [tokenid for tokenid, docfreq in iteritems(dictionary.dfs) if docfreq == 1]
dictionary.filter_tokens(once_ids)  # remove stop words and words that appear only once
dictionary.compactify()  # remove gaps in id sequence after words that were removed

dictionary.save(PYTHON_PATH+ 'data/deerwester.dict')  # store the dictionary, for future reference


class MyCorpus(object):
    def __iter__(self):
        for name in names:
            # assume there's one document per line, tokens separated by whitespace
            yield dictionary.doc2bow(getXmlData(name))


corpus_memory_friendly = MyCorpus()  # doesn't load the corpus into memory!

corpora.MmCorpus.serialize(PYTHON_PATH+ 'data/deerwester.mm', corpus_memory_friendly)


tfidf = models.TfidfModel(corpus_memory_friendly) # step 1 -- initialize a model

corpus_tfidf = tfidf[corpus_memory_friendly]

lsi = models.LsiModel(corpus_tfidf, id2word=dictionary, num_topics=num_topics)# initialize an LSI transformation


lsi.save(PYTHON_PATH+ 'data/model.lsi') # same for tfidf, lda, ...


