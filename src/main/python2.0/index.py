from gensim import corpora, models, similarities


# similarities.Similarity
corpus = corpora.MmCorpus('data/deerwester.mm')
lsi = models.LsiModel.load('data/model.lsi')
index = similarities.MatrixSimilarity(lsi[corpus])# transform corpus to LSI space and index it

index.save('data/deerwester.index')
#index = similarities.MatrixSimilarity.load('/tmp/deerwester.index')