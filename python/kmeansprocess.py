#!/usr/bin/python3
# -*- coding: utf-8 -*-
import random
import jieba
import sys
import os
from numpy import *
import numpy as np
import functions as func


def runKmeans(files):
    stopWordsFile = "stop_words.txt"
    stop_words_set = func.stop_words(stopWordsFile)

    all_vector = func.get_all_vector(files, stop_words_set)
    docs_matrix = all_vector[0]
    word_set = all_vector[1]

    column_sum = [float(len(np.nonzero(docs_matrix[:, i])[0])) for i in range(docs_matrix.shape[1])]
    column_sum = np.array(column_sum)
    column_sum = docs_matrix.shape[0] / column_sum
    idf =  np.log(column_sum)
    idf =  np.diag(idf)

    for doc_v in docs_matrix:
        if doc_v.sum() == 0:
            doc_v = doc_v / 1
        else:
            doc_v = doc_v / (doc_v.sum())
        tfidf = np.dot(docs_matrix, idf)

    result = func.kMeans(tfidf, 2)

    data = [result, idf, word_set]

    np.save("kmeansData", data)


runKmeans(sys.argv[1])

print('success')


