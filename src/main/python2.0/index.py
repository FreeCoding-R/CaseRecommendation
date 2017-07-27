#!/usr/bin/python3
# -*- coding: utf-8 -*-

from gensim import corpora, models, similarities
from constant import PYTHON_PATH


# similarities.Similarity
corpus = corpora.MmCorpus(PYTHON_PATH+'data/deerwester.mm')
lsi = models.LsiModel.load(PYTHON_PATH+'data/model.lsi')
index = similarities.MatrixSimilarity(lsi[corpus])# transform corpus to LSI space and index it

index.save(PYTHON_PATH+'data/deerwester.index')
#index = similarities.MatrixSimilarity.load('/tmp/deerwester.index')
