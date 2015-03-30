/**
 * Project Name:algorithm
 * File Name:Vertex.java
 * Package Name:com.zongtui.algorithm.decisiontree.c45.pojo
 * Date:2015-3-30下午5:40:25
 * Copyright (c) 2015, 众推项目组版权所有.
 *
 */

package com.zongtui.algorithm.decisiontree.c45.pojo;

/**
 * ClassName: Vertex <br/>
 * Function: 顶点. <br/>
 * date: 2015-3-30 下午5:40:25 <br/>
 *
 * @author feng
 * @version 
 * @since JDK 1.7
 */
public class Vertex {
	private String value;
	private String parent;
	private int SubCount=0;
	public int getSubcount() {
		return SubCount;
	}
	public void setSubcount(int subcount) {
		this.SubCount = subcount;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	@Override
	public String toString() {
		return "Vertex [value=" + value + ", parent=" + parent
				+ ", SubCount=" + SubCount + "]";
	}
}

