Trie树详解及其应用
一、知识简介 
最近在看字符串算法了，其中字典树、AC自动机和后缀树的应用是最广泛的了，下面将会重点介绍下这几个算法的应用。
字典树（Trie）可以保存一些字符串->值的对应关系。基本上，它跟 Java 的 HashMap 功能相同，都是 key-value 映射，只不过 Trie 的 key 只能是字符串。
　　Trie 的强大之处就在于它的时间复杂度。它的插入和查询时间复杂度都为 O(k) ，其中 k 为 key 的长度，与 Trie 中保存了多少个元素无关。Hash 表号称是 O(1) 的，但在计算 hash 的时候就肯定会是 O(k) ，而且还有碰撞之类的问题；Trie 的缺点是空间消耗很高。
　　至于Trie树的实现，可以用数组，也可以用指针动态分配，我做题时为了方便就用了数组，静态分配空间。
Trie树，又称单词查找树或键树，是一种树形结构，是一种哈希树的变种。典型应用是用于统计和排序大量的字符串（但不仅限于字符串），所以经常被搜索引擎系统用于文本词频统计。它的优点是：最大限度地减少无谓的字符串比较，查询效率比哈希表高。
Trie的核心思想是空间换时间。利用字符串的公共前缀来降低查询时间的开销以达到提高效率的目的。
Trie树的基本性质可以归纳为：
（1）根节点不包含字符，除根节点意外每个节点只包含一个字符。
（2）从根节点到某一个节点，路径上经过的字符连接起来，为该节点对应的字符串。
（3）每个节点的所有子节点包含的字符串不相同。
Trie树有一些特性：
1）根节点不包含字符，除根节点外每一个节点都只包含一个字符。
2）从根节点到某一节点，路径上经过的字符连接起来，为该节点对应的字符串。
3）每个节点的所有子节点包含的字符都不相同。
4）如果字符的种数为n，则每个结点的出度为n，这也是空间换时间的体现，浪费了很多的空间。
5）插入查找的复杂度为O(n)，n为字符串长度。
基本思想（以字母树为例）：
1、插入过程
对于一个单词，从根开始，沿着单词的各个字母所对应的树中的节点分支向下走，直到单词遍历完，将最后的节点标记为红色，表示该单词已插入Trie树。
2、查询过程
同样的，从根开始按照单词的字母顺序向下遍历trie树，一旦发现某个节点标记不存在或者单词遍历完成而最后的节点未标记为红色，则表示该单词不存在，若最后的节点标记为红色，表示该单词存在。
二、字典树的数据结构：
利用串构建一个字典树，这个字典树保存了串的公共前缀信息，因此可以降低查询操作的复杂度。
下面以英文单词构建的字典树为例，这棵Trie树中每个结点包括26个孩子结点，因为总共有26个英文字母(假设单词都是小写字母组成)。
则可声明包含Trie树的结点信息的结构体:
typedef struct Trie_node  
{  
    int count;                    // 统计单词前缀出现的次数  
    struct Trie_node* next[26];   // 指向各个子树的指针  
    bool exist;                   // 标记该结点处是否构成单词    
}TrieNode , *Trie;
其中next是一个指针数组，存放着指向各个孩子结点的指针。
如给出字符串”abc”,”ab”,”bd”,”dda”，根据该字符串序列构建一棵Trie树。则构建的树如下:
1347267156_9178
Trie树的根结点不包含任何信息，第一个字符串为”abc”，第一个字母为’a'，因此根结点中数组next下标为’a'-97的值不为NULL，其他同理，构建的Trie树如图所示，红色结点表示在该处可以构成一个单词。很显然，如果要查找单词”abc”是否存在，查找长度则为O(len)，len为要查找的字符串的长度。而若采用一般的逐个匹配查找，则查找长度为O(len*n)，n为字符串的个数。显然基于Trie树的查找效率要高很多。
如上图中：Trie树中存在的就是abc、ab、bd、dda四个单词。在实际的问题中可以将标记颜色的标志位改为数量count等其他符合题目要求的变量。
已知n个由小写字母构成的平均长度为10的单词，判断其中是否存在某个串为另一个串的前缀子串。下面对比3种方法：
1、 最容易想到的：即从字符串集中从头往后搜，看每个字符串是否为字符串集中某个字符串的前缀，复杂度为O(n^2)。
2、 使用hash：我们用hash存下所有字符串的所有的前缀子串。建立存有子串hash的复杂度为O(n*len)。查询的复杂度为O(n)* O(1)= O(n)。
3、 使用Trie：因为当查询如字符串abc是否为某个字符串的前缀时，显然以b、c、d….等不是以a开头的字符串就不用查找了，这样迅速缩小查找的范围和提高查找的针对性。所以建立Trie的复杂度为O(n*len)，而建立+查询在trie中是可以同时执行的，建立的过程也就可以成为查询的过程，hash就不能实现这个功能。所以总的复杂度为O(n*len)，实际查询的复杂度只是O(len)。
三、Trie树的操作
在Trie树中主要有3个操作，插入、查找和删除。一般情况下Trie树中很少存在删除单独某个结点的情况，因此只考虑删除整棵树。
1、插入
假设存在字符串str，Trie树的根结点为root。i=0，p=root。
1)取str[i]，判断p->next[str[i]-97]是否为空，若为空，则建立结点temp，并将p->next[str[i]-97]指向temp，然后p指向temp；
若不为空，则p=p->next[str[i]-97]；
2)i++，继续取str[i]，循环1)中的操作，直到遇到结束符’\0′，此时将当前结点p中的 exist置为true。
2、查找
假设要查找的字符串为str，Trie树的根结点为root，i=0，p=root
1)取str[i]，判断判断p->next[str[i]-97]是否为空，若为空，则返回false；若不为空，则p=p->next[str[i]-97]，继续取字符。
2)重复1)中的操作直到遇到结束符’\0′，若当前结点p不为空并且 exist 为true，则返回true，否则返回false。
3、删除
删除可以以递归的形式进行删除。
前缀查询的典型应用：
http://acm.hdu.edu.cn/showproblem.php?pid=1251
#include<iostream>  
#include<cstring>  
using namespace std;  
 
typedef struct Trie_node  
{  
    int count;                    // 统计单词前缀出现的次数  
    struct Trie_node* next[26];   // 指向各个子树的指针  
    bool exist;                   // 标记该结点处是否构成单词    
}TrieNode , *Trie;  
 
TrieNode* createTrieNode()  
{  
    TrieNode* node = (TrieNode *)malloc(sizeof(TrieNode));  
    node->count = 0;  
    node->exist = false;  
    memset(node->next , 0 , sizeof(node->next));    // 初始化为空指针  
    return node;  
}  
 
void Trie_insert(Trie root, char* word)  
{  
    Trie node = root;  
    char *p = word;  
    int id;  
    while( *p )  
    {  
        id = *p - 'a';  
        if(node->next[id] == NULL)  
        {  
            node->next[id] = createTrieNode();  
        }  
        node = node->next[id];  // 每插入一步，相当于有一个新串经过，指针向下移动  
        ++p;  
        node->count += 1;      // 这行代码用于统计每个单词前缀出现的次数（也包括统计每个单词出现的次数）  
    }  
    node->exist = true;        // 单词结束的地方标记此处可以构成一个单词  
}  
 
int Trie_search(Trie root, char* word)  
{  
    Trie node = root;  
    char *p = word;  
    int id;  
    while( *p )  
    {  
        id = *p - 'a';  
        node = node->next[id];  
        ++p;  
        if(node == NULL)  
            return 0;  
    }  
    return node->count;  
}  
 
int main(void)  
{  
    Trie root = createTrieNode();     // 初始化字典树的根节点  
    char str[12] ;  
    bool flag = false;  
    while(gets(str))  
    {  
        if(flag)  
            printf("%d\n",Trie_search(root , str));  
        else  
        {  
            if(strlen(str) != 0)  
            {  
                Trie_insert(root , str);  
            }  
            else  
                flag = true;  
        }  
    }  
 
    return 0;  
}
字典树的查找
http://acm.hdu.edu.cn/showproblem.php?pid=1075

#include<iostream>  
#include<cstring>  
using namespace std;  
 
typedef struct Trie_node  
{  
    int count;                    // 统计单词前缀出现的次数  
    struct Trie_node* next[26];   // 指向各个子树的指针  
    bool exist;                   // 标记该结点处是否构成单词    
    char trans[11];               // 翻译  
}TrieNode , *Trie;  
 
TrieNode* createTrieNode()  
{  
    TrieNode* node = (TrieNode *)malloc(sizeof(TrieNode));  
    node->count = 0;  
    node->exist = false;  
    memset(node->next , 0 , sizeof(node->next));    // 初始化为空指针  
    return node;  
}  
 
void Trie_insert(Trie root, char* word , char* trans)  
{  
    Trie node = root;  
    char *p = word;  
    int id;  
    while( *p )  
    {  
        id = *p - 'a';  
        if(node->next[id] == NULL)  
        {  
            node->next[id] = createTrieNode();  
        }  
        node = node->next[id];  // 每插入一步，相当于有一个新串经过，指针向下移动  
        ++p;  
        node->count += 1;      // 这行代码用于统计每个单词前缀出现的次数（也包括统计每个单词出现的次数）  
    }  
    node->exist = true;        // 单词结束的地方标记此处可以构成一个单词  
    strcpy(node->trans , trans);  
}  
 
char* Trie_search(Trie root, char* word)  
{  
    Trie node = root;  
    char *p = word;  
    int id;  
    while( *p )  
    {  
        id = *p - 'a';  
        node = node->next[id];  
        ++p;  
        if(node == NULL)  
            return 0;  
    }  
    if(node->exist)          // 查找成功  
        return node->trans;  
    else                     // 查找失败  
        return NULL;  
}  
 
int main(void)  
{  
    Trie root = createTrieNode();     // 初始化字典树的根节点  
    char str1[3003] , str2[3003] , str[3003] , *p;  
    int i , k;  
 
    scanf("%s",str1);  
    while(scanf("%s",str1) && strcmp(str1 , "END") != 0)  
    {  
        scanf("%s",str2);  
        Trie_insert(root , str2 , str1);  
    }  
 
    getchar();  
    gets(str1);  
    k = 0;  
    while(gets(str1))  
    {  
        if(strcmp(str1 , "END") == 0)  
            break;  
        for(i = 0 ; str1[i] != '\0' ; ++i)  
        {  
            if(str1[i] >= 'a' && str1[i] <= 'z')  
            {  
                str[k++] = str1[i];  
            }  
            else  
            {  
                str[k] = '\0';  
                p = Trie_search(root , str);  
                if(p)  
                    printf("%s", p);  
                else  
                    printf("%s", str);  
                k = 0;  
                printf("%c", str1[i]);  
            }  
        }  
        printf("\n");  
    }  
 
    return 0;  
}