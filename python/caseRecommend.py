from math import inf
import os
from numpy import *
import random
import jieba
import numpy as np


stopWordsFile = "stop_words.txt"
numRecommend = 6;


def read_from_file(file_name):
    with open(file_name, "r", encoding='utf8', errors='ignore') as fp:
        words = fp.read()
    return words


def stop_words(stop_word_file):
    words = read_from_file(stop_word_file)
    result = jieba.cut(words)
    new_words = []
    for r in result:
        new_words.append(r)
    return set(new_words)


def del_stop_words(words, stop_words_set):
    result = jieba.cut(words)
    new_words = []
    for r in result:
        if r not in stop_words_set:
            new_words.append(r)
    return new_words


def get_all_vector(file_path, stop_words_set):
    names = [ os.path.join(file_path,f) for f in os.listdir(file_path) ]
    posts = []
    for name in names:
      if(name.endswith(".xml")):
        case = read_from_file(name)
        posts.append(case)
    docs = []
    word_set = set()
    for post in posts:
        doc = del_stop_words(post, stop_words_set)
        docs.append(doc)
        word_set |= set(doc)

    word_set = list(word_set)
    docs_vsm = []
    for doc in docs:
        temp_vector = []
        for word in word_set:
            temp_vector.append(doc.count(word) * 1.0)
        docs_vsm.append(temp_vector)

    docs_matrix = np.array(docs_vsm)
    return docs_matrix, word_set


def gen_sim(A,B):
    num = float(np.dot(A,B.T))
    denum = np.linalg.norm(A) * np.linalg.norm(B)
    if denum == 0:
        denum = 1
    cosn = num / denum
    sim = 0.5 + 0.5 * cosn
    return sim


def randCent(dataSet, k):
    n = shape(dataSet)[1]
    centroids = mat(zeros((k, n)))
    for j in range(n):
        minJ = min(dataSet[:,j])
        rangeJ = float(max(dataSet[:,j]) - minJ)
        centroids[:, j] = mat(minJ + rangeJ * random.rand(k, 1))
    return centroids


def kMeans(dataSet, k, distMeas=gen_sim, createCent=randCent):
    m = shape(dataSet)[0]
    clusterAssment = mat(zeros((m, 2)))

    centroids = createCent(dataSet, k)
    clusterChanged = True
    counter = 0
    while counter <= 50:
        counter += 1
        clusterChanged = False
        for i in range(m):
            minDist = inf;
            minIndex = -1
            for j in range(k):
                distJI = distMeas(centroids[j,:],dataSet[i,:])
                if distJI < minDist:
                    minDist = distJI;
                    minIndex = j
            if clusterAssment[i,0] != minIndex:
                clusterChanged = True
            clusterAssment[i, :] = minIndex, minDist**2
        for cent in range(k):
            ptsInClust = dataSet[nonzero(clusterAssment[:,0].A==cent)[0]]
            centroids[cent, :] = mean(ptsInClust, axis=0)
    return centroids, clusterAssment


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



