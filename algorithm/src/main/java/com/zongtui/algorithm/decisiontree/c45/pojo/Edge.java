/**
 * Project Name:algorithm
 * File Name:Edge.java
 * Package Name:com.zongtui.algorithm.decisiontree.c45.pojo
 * Date:2015-3-30下午5:36:50
 * Copyright (c) 2015, 众推项目组版权所有.
 *
 */

package com.zongtui.algorithm.decisiontree.c45.pojo;

/**
 * ClassName: Edge <br/>
 * Function: 数据源。 <br/>
 * date: 2015-3-30 下午5:36:50 <br/>
 *
 * @author feng
 * @version 
 * @since JDK 1.7
 */
public class Edge {
	private String value;
	private String source;
	private String target;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	@Override
	public String toString() {
		return "Edge [value=" + value + ", source=" + source + ", target="
				+ target + "]";
	}
}

