#!/usr/bin/python3
# -*- coding: utf-8 -*-

from gensim import corpora, models, similarities
import xml.etree.ElementTree as ET
import sys
import jieba
from constant import RECOMMEND_NUM,PYTHON_PATH,STOP_WORDS_FILE

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


def getCases(file):
    dictionary = corpora.Dictionary.load(PYTHON_PATH+ 'data/deerwester.dict')
    lsi = models.LsiModel.load(PYTHON_PATH+ 'data/model.lsi')
    index = similarities.MatrixSimilarity.load(PYTHON_PATH+ 'data/deerwester.index')

    vec_bow = dictionary.doc2bow(getXmlData(file))

    vec_lsi = lsi[vec_bow] # convert the query to LSI space
    sims = index[vec_lsi]

    sims = abs(sims)
    sims = sorted(enumerate(sims), key=lambda item: -item[1])
    return [index[0] for index in sims[:RECOMMEND_NUM] if index[1]>0.8]

print(getCases(sys.argv[1]))