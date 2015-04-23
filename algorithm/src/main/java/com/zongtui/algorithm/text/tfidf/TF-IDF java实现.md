http://www.chepoo.com/tf-idf-java-implementation.html

在文本分类中要得到特征词，首先要获取tf-idf模型，搜索中也会用到tf-idf，有必要深入学习。首先来简单回顾一下tf-idf.
词频(TF)=某个词在文章中出现的次数
词频(TF)=某个词在文章中出现的次数/文章的总词数
或者：
词频(TF)=某个词在文章中出现的次数/该文出现次数最多的词的出现次数
逆文档率：
idf
TF-IDF：
TF-IDF=词频(TF)*逆文档率(IDF)
TF-IDF与一个词在文档中的出现次数成正比，与该词在整个语言中的出现次数成反比。