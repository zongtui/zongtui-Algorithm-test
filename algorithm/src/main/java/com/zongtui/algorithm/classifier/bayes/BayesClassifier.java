/**
 * Project Name:algorithm
 * File Name:BayesClassifier.java
 * Package Name:com.zongtui.algorithm.classifier.bayes
 * Date:2015-4-14上午10:57:18
 * Copyright (c) 2015, 众推项目组版权所有.
 *
 */

package com.zongtui.algorithm.classifier.bayes;

/**
 * ClassName: BayesClassifier <br/>
 * Function: 朴素贝叶斯分类器. <br/>
 * date: 2015-4-14 上午10:57:18 <br/>
 * 
 * C000007	汽车
 C000008	财经
 C000010	IT
 C000013	健康
 C000014	体育
 C000016	旅游
 C000020	教育
 C000022	招聘
 C000023	文化
 C000024	军事
 *
 * @author feng
 * @version 
 * @since JDK 1.7
 */

import java.util.ArrayList;

import java.util.Comparator;

import java.util.List;

import java.util.Vector;

public class BayesClassifier {
	private TrainingDataManager tdm;// 训练集管理器
	private String trainnigDataPath;// 训练集路径
	private static double zoomFactor = 10.0f;

	/**
	 * 
	 * 默认的构造器，初始化训练集
	 */
	public BayesClassifier() {
		tdm = new TrainingDataManager();
	}

	/**
	 * 
	 * 计算给定的文本属性向量X在给定的分类Cj中的类条件概率
	 * 
	 * <code>ClassConditionalProbability</code>连乘值
	 * 
	 * @param X
	 *            给定的文本属性向量
	 * 
	 * @param Cj
	 *            给定的类别
	 * 
	 * @return 分类条件概率连乘值，即<br>
	 */
	float calcProd(String[] X, String Cj) {
		float ret = 1.0F;
		// 类条件概率连乘
		for (int i = 0; i < X.length; i++) {
			String Xi = X[i];
			// 因为结果过小，因此在连乘之前放大10倍，这对最终结果并无影响，因为我们只是比较概率大小而已
			ret *= ClassConditionalProbability.calculatePxc(Xi, Cj)
					* zoomFactor;
		}
		// 再乘以先验概率
		ret *= PriorProbability.calculatePc(Cj);
		return ret;
	}

	/**
	 * 
	 * 去掉停用词
	 * 
	 * @param text
	 *            给定的文本
	 * 
	 * @return 去停用词后结果
	 */
	public String[] DropStopWords(String[] oldWords) {
		Vector<String> v1 = new Vector<String>();
		for (int i = 0; i < oldWords.length; ++i) {
			if (StopWordsHandler.IsStopWord(oldWords[i]) == false) {// 不是停用词
				v1.add(oldWords[i]);
			}
		}
		String[] newWords = new String[v1.size()];
		v1.toArray(newWords);
		return newWords;
	}

	/**
	 * 
	 * 对给定的文本进行分类
	 * 
	 * @param text
	 *            给定的文本
	 * 
	 * @return 分类结果
	 */

	@SuppressWarnings("unchecked")
	public String classify(String text) {
		String[] terms = null;
		terms = ChineseSpliter.split(text, " ").split(" ");// 中文分词处理(分词后结果可能还包含有停用词）
		terms = DropStopWords(terms);// 去掉停用词，以免影响分类
		String[] Classes = tdm.getTraningClassifications();// 分类
		float probility = 0.0F;
		List<ClassifyResult> crs = new ArrayList<ClassifyResult>();// 分类结果
		for (int i = 0; i < Classes.length; i++) {
			String Ci = Classes[i];// 第i个分类
			System.out.println(" 分类数据  "+Ci);
			probility = calcProd(terms, Ci);// 计算给定的文本属性向量terms在给定的分类Ci中的分类条件概率
			// 保存分类结果
			ClassifyResult cr = new ClassifyResult();
			cr.classification = Ci;// 分类
			cr.probility = probility;// 关键字在分类的条件概率
			System.out.println("In process.");
			System.out.println(Ci + "：" + probility);
			crs.add(cr);
		}
		// 对最后概率结果进行排序
		java.util.Collections.sort(crs, new Comparator() {
			public int compare(final Object o1, final Object o2) {
				final ClassifyResult m1 = (ClassifyResult) o1;
				final ClassifyResult m2 = (ClassifyResult) o2;
				final double ret = m1.probility - m2.probility;
				if (ret < 0) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		// 返回概率最大的分类
		return crs.get(0).classification;
	}

	public static void main(String[] args) {
		String text = "此役，马刺这边4人得分上双，邓肯得到28分11篮板，生涯季后赛总得分达到5027分，他也成为了继乔丹、贾巴尔、科比和奥尼尔之后，NBA历史上第五位生涯季后赛总分5000+的球星。另外，今天的比赛，也是石佛第100次在季后赛中打出得分20+、篮板球10+的表现。帕克送出5次助攻，生涯季后赛助攻总数达到1040次，追平了科比排名历史榜单第7位。但整场球，法国跑车的发挥并不好，他6投0中，仅得到1分，还在比赛末节，因为右脚脚踝、跟腱和大腿的多处伤势复发，遗憾退赛。吉诺比利在今天得到9分，比赛末节，他6次犯规离场，这是他职业生涯第四次在季后赛中遭遇驱逐，前三场，马刺都输了球，但今天这一历史却被改写了。马刺替补迪奥拿到了12分9篮板6助攻，他也成为了自从1985-86赛季以来，马刺队史上第二位在季后赛中替补出场，却至少拿下12分9个篮板6个助攻的球员。2007年5月19日，吉诺比利在对阵太阳的比赛中，也做到过这一点（33分11篮板6助攻）。另外，米尔斯替补出场发挥出色，砍下16分；伦纳德斩下23分9篮板。";
//		String text = "怎么买迷藥订货Q:285829120怎么买迷藥,不货比怕货，就怕不识货，质量第一,验货付款,担保交易，货到付款，保密配送,安心取货,欢迎前来咨询,医药专卖。";
		BayesClassifier classifier = new BayesClassifier();// 构造Bayes分类器
		String result = classifier.classify(text);// 进行分类
		System.out.println("此项属于[" + result + "]");
	}

}