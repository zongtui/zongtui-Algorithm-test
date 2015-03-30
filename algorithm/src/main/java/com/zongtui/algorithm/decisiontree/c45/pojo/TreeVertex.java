/**
 * Project Name:algorithm
 * File Name:TreeVertex.java
 * Package Name:com.zongtui.algorithm.decisiontree.c45.pojo
 * Date:2015-3-30下午5:39:08
 * Copyright (c) 2015, 众推项目组版权所有.
 *
 */

package com.zongtui.algorithm.decisiontree.c45.pojo;

/**
 * ClassName: TreeVertex <br/>
 * Function: 根定义。 <br/>
 * date: 2015-3-30 下午5:39:08 <br/>
 *
 * @author feng
 * @version 
 * @since JDK 1.7
 */
public class TreeVertex {

	private String value;
	private Object Vertex;
	/**
	 * @param value
	 * @param vertex
	 */
	public TreeVertex(String value, Object vertex) {
		super();
		this.value = value;
		Vertex = vertex;
	}

	public Object getVertex() {
		return Vertex;
	}

	public void setVertex(Object vertex) {
		Vertex = vertex;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}

