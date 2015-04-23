/**
 * Project Name:algorithm
 * File Name:PriorProbability.java
 * Package Name:com.zongtui.algorithm.classifier.bayes
 * Date:2015-4-14上午10:53:59
 * Copyright (c) 2015, 众推项目组版权所有.
 *
 */

package com.zongtui.algorithm.classifier.bayes;

/**
 * ClassName: PriorProbability <br/>
 * Function: 先验概率计算. <br/>
 * 先验概率计算
 * 
 * <h3>先验概率计算</h3>
 * 
 * P(c<sub>j</sub>)=N(C=c<sub>j</sub>)<b>/</b>N <br>
 * 
 * 其中，N(C=c<sub>j</sub>)表示类别c<sub>j</sub>中的训练文本数量；
 * 
 * N表示训练文本集总数量。 date: 2015-4-14 上午10:53:59 <br/>
 * 
 * @author feng
 * @version
 * @since JDK 1.7
 */

public class PriorProbability {

	private static TrainingDataManager tdm = new TrainingDataManager();

	/**
	 * 
	 * 先验概率
	 * 
	 * @param c
	 *            给定的分类
	 * 
	 * @return 给定条件下的先验概率
	 */

	public static float calculatePc(String c) {
		float ret = 0F;
		float Nc = tdm.getTrainingFileCountOfClassification(c);
		float N = tdm.getTrainingFileCount();
		ret = Nc / N;
		return ret;
	}

}