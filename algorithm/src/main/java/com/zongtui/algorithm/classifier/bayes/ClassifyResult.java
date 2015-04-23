/**
 * Project Name:algorithm
 * File Name:ClassifyResult.java
 * Package Name:com.zongtui.algorithm.classifier.bayes
 * Date:2015-4-14上午10:55:59
 * Copyright (c) 2015, 众推项目组版权所有.
 *
 */

package com.zongtui.algorithm.classifier.bayes;

/**
 * ClassName: ClassifyResult <br/>
 * Function: 分类结果. <br/>
 * date: 2015-4-14 上午10:55:59 <br/>
 * 
 * @author feng
 * @version
 * @since JDK 1.7
 */

public class ClassifyResult
{
	public double probility;// 分类的概率
	public String classification;// 分类

	public ClassifyResult() {
		this.probility = 0;
		this.classification = null;
	}

}