/**
 * Project Name:algorithm
 * File Name:TreePainter.java
 * Package Name:com.zongtui.algorithm.decisiontree.c45
 * Date:2015-3-30下午6:07:35
 * Copyright (c) 2015, 众推项目组版权所有.
 *
 */

package com.zongtui.algorithm.decisiontree.c45;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import com.zongtui.algorithm.decisiontree.c45.pojo.Edge;
import com.zongtui.algorithm.decisiontree.c45.pojo.TreeVertex;
import com.zongtui.algorithm.decisiontree.c45.pojo.Vertex;

/**
 * ClassName: TreePainter <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2015-3-30 下午6:07:35 <br/>
 *
 * @author feng
 * @version 
 * @since JDK 1.7
 */
public class TreePainter extends JFrame
{

	private static final long serialVersionUID = -2707712944901661771L;
	private static ArrayList<TreeVertex> treevertexs = new ArrayList<TreeVertex>();
	public TreePainter(ArrayList<Vertex> vertexs, ArrayList<Edge> edges) {
		super("C4.5决策树");
		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();
		Collections.reverse(vertexs);
		graph.getModel().beginUpdate();
		for(int i=0;i<vertexs.size();i++){//設置每個结点的儿子个数
			Vertex v= vertexs.get(i);
			v.setSubcount(countSubBySameParentName(vertexs,v.getValue()));
			System.out.println(v);
		}
		try
		{
			for(int i=0;i<vertexs.size();i++){
				Vertex v= vertexs.get(i);
				Object ov;
				if(v.getParent()==null){//当前结点是根节点
					ov = graph.insertVertex(parent, null,v.getValue(),250 , 50, 80,30);//直接画在顶部中央位置
				}
				else{
					Vertex parentV=findParentByValue(vertexs,v.getParent());//不是根节点就要找到结点的父亲，以得到该行有多少个结点
					int currentRowCount = parentV.getSubcount();
//					if(currentRowCount>=0){//说明是同一行的结点
						ov = graph.insertVertex(parent, null,v.getValue(),currentRowCount*100 , 50*(i+currentRowCount+1), 80,30);
//					}
//					else{
//						ov = graph.insertVertex(parent, null,v.getValue(),currentRowCount*100 , 50*((i+3)%5), 80,30);
//					}
					parentV.setSubcount(--currentRowCount);//更新父结点未处理的儿子个数
				}
				TreeVertex tv = new TreeVertex(v.getValue(),ov);
				treevertexs.add(tv);
			}
			for(int i=0;i<edges.size();i++){
				Edge edge = edges.get(i);
				graph.insertEdge(parent, null, edge.getValue(), findVertexByValue(edge.getSource()),findVertexByValue(edge.getTarget()));
				
			}
		}
		finally
		{
			graph.getModel().endUpdate();
		}
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		getContentPane().add(graphComponent);
	}
	/**
	 * 检索结点
	 * @param value
	 * @return
	 */
	private Object findVertexByValue(String value){
		for(int i=0;i<treevertexs.size();i++)//得到source和target
		{
			String temp=treevertexs.get(i).getValue();
			if(temp.equals(value)){
				Object v=treevertexs.get(i).getVertex();
				if(countVertexByValue(temp)>1)//数据里面有相同名称的结点
				{
					treevertexs.remove(i);//去除该结点
				}
				return v;
			}
		}
		return null;
	}
	/**
	 * 查找父亲
	 * @param value
	 * @return
	 */
	private Vertex findParentByValue(ArrayList<Vertex> vertexs,String parent){
		for(int i=0;i<vertexs.size();i++)
		{
			Vertex temp=vertexs.get(i);
			if(temp.getValue().equals(parent)){
				return temp;
			}
		}
		return null;
	}
	/**
	 * 计算指定名称的树结点个数
	 * @param value
	 * @return count
	 */
	private int countVertexByValue(String value){
		int count =0;
		for(int i=0;i<treevertexs.size();i++)
		{
			if(treevertexs.get(i).getValue().equals(value)){
				count++;
			}
		}
		return count;
	}
	/**
	 * 计算相同父親的结点个数
	 * @param parent
	 * @return count
	 */
	private int countSubBySameParentName(ArrayList<Vertex> vertexs,String parent){
		int count =0;
		for(int i=0;i<vertexs.size();i++)
		{
			Vertex temp=vertexs.get(i);
			if(temp.getParent()!=null&&temp.getParent().equals(parent)){
				count++;
			}
		}
		return count;
	}
}

