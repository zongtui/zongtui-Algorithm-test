/**
 * Project Name:algorithm
 * File Name:DecisionTreeNode.java
 * Package Name:com.zongtui.algorithm.decisiontree.c45.decision
 * Date:2015-3-30下午5:34:56
 * Copyright (c) 2015, 众推项目组版权所有.
 *
 */

package com.zongtui.algorithm.decisiontree.c45.decision;

/**
 * ClassName: DecisionTreeNode <br/>
 * Function: 树结点属性 <br/>
 * date: 2015-3-30 下午5:34:56 <br/>
 *
 * @author feng
 * @version 
 * @since JDK 1.7
 */
public class DecisionTreeNode {
	/**
	 * 父结点
	 */
	DecisionTreeNode parentNode;
	/**
	 * 父结点属性值
	 */
	String parentArrtibute;
	/**
	 * 当前结点名
	 */

	String nodeName;
	/**
	 * 当前结点属性集
	 */

	String[] arrtibutesArray;
	/**
	 * 孩子结点集
	 */
	DecisionTreeNode[] childNodesArray;
}

