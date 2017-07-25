import random
import os
import xml.etree.ElementTree as ET
from pprint import pprint


path = "/Users/loick/Desktop/3组/samples/"


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


def getXmlData(result_list, file_name):
    root = ET.parse(file_name).getroot()
    walkData(root, result_list)
    return result_list


names = [os.path.join(path, f) for f in os.listdir(path) if f.endswith('.xml')]

stoplist = set([])
getXmlData(stoplist, names[0])
pprint(stoplist)

