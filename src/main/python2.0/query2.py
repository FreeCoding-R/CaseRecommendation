#!/usr/bin/python3
# -*- coding: utf-8 -*-

from gensim import corpora, models, similarities
import sys
from constant import RECOMMEND_NUM,PYTHON_PATH

def getCases(location):
    lsi = models.LsiModel.load(PYTHON_PATH+ 'data/model.lsi')
    index = similarities.MatrixSimilarity.load(PYTHON_PATH+ 'data/deerwester.index')

    corpus = corpora.MmCorpus(PYTHON_PATH+'data/deerwester.mm')

    vec_bow = corpus[int(location)]

    vec_lsi = lsi[vec_bow] # convert the query to LSI space
    sims = index[vec_lsi]

    sims = abs(sims)
    sims = sorted(enumerate(sims), key=lambda item: -item[1])
    #return sims[:6]
    return [index[0] for index in sims[1:RECOMMEND_NUM+1]]

print(getCases(sys.argv[1]))


