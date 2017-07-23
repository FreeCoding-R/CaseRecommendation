#!/usr/bin/python3
# -*- coding: utf-8 -*-
import os
from numpy import *
import random
import numpy as np
import functions as func
import sys

stopWordsFile = "stop_words.txt"
numRecommend = 6;


def kmeansCases(xmlfile):
    casefile = open(xmlfile, "r", encoding='utf8')
    doc = casefile.read()
    casefile.close()

    # prepare for kmeans data
    data = np.load("kmeansData.npy")
    centroids = data[0][0]
    clusters = data[0][1]
    idf = data[1]
    word_set = data[2]

    # calculate doc's tfidf
    stop_words_set = func.stop_words(stopWordsFile)
    doc = func.del_stop_words(doc, stop_words_set)

    temp_vector = []
    for word in word_set:
        temp_vector.append(doc.count(word) * 1.0)

    doc = temp_vector
    tfidf = doc*idf

    # find the nearest center point
    minindex = 0;
    mindis = inf
    for i in range(len(centroids)):
        dist = func.gen_sim(centroids[i], tfidf[i, :])
        if mindis > dist:
            mindis = dist
            minindex = i

    # get recommend cases
    cases = []
    clusters = np.array(clusters)
    for i in range(len(clusters)):
        if(clusters[i][0] == minindex):
            tempcases = [clusters[i], i]
            cases.append(tempcases)
    errorMargin = mindis**2

    for case in cases:
        case[0][1] = case[0][1] - errorMargin

    cases = sorted(cases, key=lambda index: index[0][1])
    cases = [case[1] for case in cases]
    if len(cases) >= numRecommend:
        return cases[0:5]
    else:
        return cases

print(kmeansCases(sys.argv[1]))



