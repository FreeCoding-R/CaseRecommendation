from gensim import corpora, models, similarities
from pprint import pprint
import xml.etree.ElementTree as ET


# 遍历所有的节点
def walkData(root_node,  result_list):
    item = root_node.items()
    if len(item) >= 2:
        if len(item[1][1]) < 15:
            result_list.add(root_node.tag+','+item[1][1])

    # 遍历每个子节点
    children_node = root_node.getchildren()
    if len(children_node) == 0:
        return
    for child in children_node:
        walkData(child, result_list)
    return


def getXmlData(file_name):
    result_list = set([])
    root = ET.parse(file_name).getroot()
    walkData(root, result_list)
    return result_list


dictionary = corpora.Dictionary.load('data/deerwester.dict')
lsi = models.LsiModel.load('data/model.lsi')
index = similarities.MatrixSimilarity.load('data/deerwester.index')

file = '/Users/loick/Desktop/test.xml'
vec_bow = dictionary.doc2bow(getXmlData(file))

vec_lsi = lsi[vec_bow] # convert the query to LSI space
sims = index[vec_lsi]

sims = abs(sims)
sims = sorted(enumerate(sims), key=lambda item: -item[1])
pprint(sims)

print('done')