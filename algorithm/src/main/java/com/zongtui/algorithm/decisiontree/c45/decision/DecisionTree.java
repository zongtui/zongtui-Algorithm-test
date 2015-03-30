/**
 * Project Name:algorithm
 * File Name:DecisionTree.java
 * Package Name:com.zongtui.algorithm.decisiontree.c45.decision
 * Date:2015-3-30下午5:33:46
 * Copyright (c) 2015, 众推项目组版权所有.
 *
 */

package com.zongtui.algorithm.decisiontree.c45.decision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import com.zongtui.algorithm.decisiontree.c45.pojo.Edge;
import com.zongtui.algorithm.decisiontree.c45.pojo.Vertex;

/**
 * ClassName: DecisionTree <br/>
 * Function: 决策树生成. <br/>
 * date: 2015-3-30 下午5:33:46 <br/>
 *
 * @author feng
 * @version 
 * @since JDK 1.7
 */
public class DecisionTree {

	/**
	 * 根结点
	 */
	private DecisionTreeNode root;

	/**
	 * visableArray
	 */
	private boolean[] visable;

	private static final int NOT_FOUND = -1;

	/**
	 * 初始输入实际数据中，从DATA_START_LINE开始的才是数据，之前的是属性列名
	 */
	private static final int DATA_START_LINE = 1;

	/**
	 * 训练集
	 */
	private Object[] trainingArray;
	/**
	 * 属性列名数组
	 */
	private String[] columnHeaderArray;
	/**
	 * 当前生成的决策树的结点个数
	 */
	private int countNode=0;
	/**
	 * 要预测的属性列的索引值
	 */
	private int nodeIndex;
	/**
	 * 标记当前个数不为0的属性值
	 */
	private String trueattr; 
	/**
	 * 结点信息
	 */
	private ArrayList<Vertex> vertexs = new ArrayList<Vertex>();
	/**
	 * @return 结点信息
	 */
	public ArrayList<Vertex> getVertexs() {
		return vertexs;
	}
	/**
	 * 边信息
	 */
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	/**
	 * @return 边信息
	 */
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
	/**
	 * 处理操作步骤输出流
	 */
	private StringBuffer process_buffer = new StringBuffer();

	/**
	 * 
	 * @return process_buffer 处理操作步骤输出流
	 */
	public StringBuffer getProcess_buffer() {
		return process_buffer;
	}

	/**
	 * 处理结果输出流
	 */
	private StringBuffer result_buffer = new StringBuffer();

	/**
	 * 
	 * @return process_buffer 处理结果输出流
	 */
	public StringBuffer getResult_buffer() {
		return result_buffer;
	}

	/**
	 * 初始化C4.5决策树数据，划分训练集
	 * 
	 * @param array
	 *            属性列名和数据 数组
	 * @param index
	 *            预测值的索引值（列号）从0开始计数，即第一列即记为index=0
	 */
	public void create(Object[] array, int index) {
		this.trainingArray = Arrays.copyOfRange(array, DATA_START_LINE,
				array.length);// 将当前数据划定为训练集，注意这时的数据和属性名要区分开来，属性名在第一行
		init(array, index);
		createDecisionTree(this.trainingArray);// 注意，这里的trainArray只是数据部分，已经将属性列名数据分离
		printDecisionTree(root);
	}

	/**
	 * 初始化预测属性列索引值和属性列名集合，且预测属性列为visable状态，用于在计算MaxGain（A）时，忽略计算该属性列
	 * 
	 * @param dataArray
	 *            数据数组
	 * @param index
	 *            预测值所在列
	 */
	public void init(Object[] dataArray, int index) {
		this.nodeIndex = index;
		this.columnHeaderArray = (String[]) dataArray[0];
		visable = new boolean[((String[]) dataArray[0]).length];
		/**
		 * 设置预测属性列为visable状态
		 */
		for (int i = 0; i < visable.length; i++) {
			if (i == index) {
				visable[i] = true;
			} else {
				visable[i] = false;
			}
		}
	}

	/**
	 * @param array
	 */
	public void createDecisionTree(Object[] array) {
		Object[] maxgain = getMaxGainRatio(array,"ALL");
		if (root == null) {
			root = new DecisionTreeNode();
			/*
			 * 这里一定要显示的说明根节点的父节点为null，因为在输出结点的时候要使用是否为null值来判断是否为根节点
			 */
			root.parentNode = null;
			root.parentArrtibute = null;
			root.arrtibutesArray = getArrtibutesArray(((Integer) maxgain[1])
					.intValue());
			root.nodeName = getColumnHeaderNameByIndex(((Integer) maxgain[1])
					.intValue());
			root.childNodesArray = new DecisionTreeNode[root.arrtibutesArray.length];
			insertDecisionTree(array, root);
		}
	}

	/**
	 * @param array
	 *            训练集
	 * @return Object[] 返回当前的MaxGain(S,A)
	 */
	public Object[] getMaxGainRatio(Object[] array,String processingData) {
		Object[] result = new Object[2];
		double gain = 0;//初始值为0
		int index = -1;

		for (int i = 0; i < visable.length; i++) {
			if (!visable[i]) {// 处理非预测属性列
				// TODO 这里是将ID3算法转换为 C4.5算法的关键步骤，计算信息增益率gainRatio
				double value = gainRatio(array, i, this.nodeIndex,processingData);
				if (gain < value) {
					gain = value;
					index = i;
				}
			}
		}
		result[0] = gain;
		result[1] = index;
		if (index >= 0) {
			process_buffer.append("计算得到MaxGainRatio:" + gain + ",生成第"+(++countNode)+"个结点"+ this.columnHeaderArray[index]+"\n");
			process_buffer.append("*************************************************结点分割线***************************************\n");
		}else{
			process_buffer.append("计算得到MaxGainRatio:" + gain + ",生成以"+processingData+"为分支的第"+(++countNode)+"个结点"+this.columnHeaderArray[nodeIndex]+":"+trueattr+"\n");
			process_buffer.append("*************************************************结点分割线***************************************\n");
		}
		
		// TODO 抛弃不能进行预测的情况，记所以的GainRatio均小于0
		if (index != -1) {
			visable[index] = true;
		}
		return result;
	}

	/**
	 * @param array
	 * @param parentNode
	 */
	public void insertDecisionTree(Object[] array, DecisionTreeNode parentNode) {
		String[] arrtibutes = parentNode.arrtibutesArray;
		for (int i = 0; i < arrtibutes.length; i++) {
			Object[] pickArray = pickUpAndCreateSubArray(array, arrtibutes[i],
					getColumnHeaderIndexByName(parentNode.nodeName));
			//注意现在处理的Data不再是整一张表，而是通过上一次产生的结点划分为 不同属性 后的新表
			Object[] info = getMaxGainRatio(pickArray,arrtibutes[i]);
			double gain = ((Double) info[0]).doubleValue();
			if (gain != 0) {
				int index = ((Integer) info[1]).intValue();
				DecisionTreeNode currentNode = new DecisionTreeNode();
				currentNode.parentNode = parentNode;
				currentNode.parentArrtibute = arrtibutes[i];
				currentNode.arrtibutesArray = getArrtibutesArray(index);
				currentNode.nodeName = getColumnHeaderNameByIndex(index);
				currentNode.childNodesArray = new DecisionTreeNode[currentNode.arrtibutesArray.length];
				parentNode.childNodesArray[i] = currentNode;
				insertDecisionTree(pickArray, currentNode);
			} else {
				DecisionTreeNode leafNode = new DecisionTreeNode();
				leafNode.parentNode = parentNode;
				leafNode.parentArrtibute = arrtibutes[i];
				leafNode.arrtibutesArray = new String[0];
				leafNode.nodeName = getLeafNodeName(pickArray, this.nodeIndex);
				leafNode.childNodesArray = new DecisionTreeNode[0];
				parentNode.childNodesArray[i] = leafNode;
			}
		}
	}
	private Vertex findVertexBySameParent(String parent){
		for(int i=0;i<vertexs.size();i++)//得到source和target
		{
			Vertex temp=vertexs.get(i);
			if(temp.getParent()!=null&&temp.getParent().equals(parent)){
				return temp;
			}
		}
		return null;
	}
	/**
	 * @param node
	 */
	public void printDecisionTree(DecisionTreeNode node) {
		if (node.parentNode != null) {
			result_buffer.append("新生结点：" + node.nodeName + "[当前结点的父结点为："
					+ node.parentNode.nodeName + "]\n");
			Vertex v = new Vertex();
			v.setValue(node.nodeName);
			Vertex sameParentV = findVertexBySameParent(node.parentNode.nodeName);
			v.setParent(node.parentNode.nodeName);
			vertexs.add(vertexs.indexOf(sameParentV)+1,v);
		} else {
			result_buffer.append("根结点：" + node.nodeName + "\n");
			Vertex v = new Vertex();
			v.setValue(node.nodeName);
			vertexs.add(v);
		}
		DecisionTreeNode[] childs = node.childNodesArray;
		for (int i = 0; i < childs.length; i++) {
			if (childs[i] != null) {
				result_buffer.append(node.nodeName + "的分支"
						+ childs[i].parentArrtibute + "\n");
				printDecisionTree(childs[i]);
				Edge e = new Edge();
				e.setValue(childs[i].parentArrtibute);
				e.setSource(node.nodeName);
				e.setTarget(childs[i].nodeName);
				edges.add(e);
			}
		}
	}

	/**
	 * @param array
	 * @param arrtibute
	 * @param index
	 * @return Object[]
	 */
	public Object[] pickUpAndCreateSubArray(Object[] array, String arrtibute,
			int index) {
		List<String[]> list = new ArrayList<String[]>();
		for (int i = 0; i < array.length; i++) {
			String[] strs = (String[]) array[i];
			if (strs[index].equals(arrtibute)) {
				list.add(strs);
			}
		}
		return list.toArray();
	}

	/**
	 * gain(A)
	 * 
	 * @param array
	 *            训练集
	 * @param index
	 *            当前处理属性列
	 * @return double 信息增益 Gain(A)
	 */
	public double gain(Object[] array, int index, int nodeIndex,String processingData) {
		int[] counts = CountSameValueArrays(array, nodeIndex);
		String[] attr = getArrtibutesArray(nodeIndex);
		process_buffer.append("训练集S("+processingData+")的预测属性列不同值的个数：\n");
		for (int i = 0; i < counts.length; i++) {
			process_buffer.append(attr[i] + ":" + counts[i] + "\t");
			if(counts[i]>0){
				trueattr=attr[i];
			}
		}
		process_buffer.append("\n");
		process_buffer.append("计算训练集个数S("+processingData+")=sum(Si)=" + array.length+"\n");
		process_buffer.append("计算pi=Si/S\n");
		for (int i = 0; i < counts.length; i++) {
			process_buffer.append("p" + i + "=" + counts[i] + "/" + array.length
					+ "\t");
		}
		process_buffer.append("\n");
		String[] arrtibutes = getArrtibutesArray(index);
		double gainValue = 0;
		double infoD = entropy_S(array, counts, true,processingData);
		double infoaD = entropyA_S(array, index, nodeIndex, arrtibutes);
		gainValue = infoD - infoaD;
		process_buffer.append("计算信息增益值Gain(A)=Gain("
				+ this.columnHeaderArray[index] + ")=Entropy(S)-Entropy(S,"
				+ this.columnHeaderArray[index] + ")=" + infoD + "-" + infoaD
				+ "=" + gainValue+"\n");
		process_buffer.append("================================================分割线===============================================\n");
		return gainValue;
	}

	/**
	 * @param array
	 *            训练集
	 * @param nodeIndex
	 *            预测分析属性列
	 * @return 属性值相同的预测值个数集合
	 */
	public int[] CountSameValueArrays(Object[] array, int nodeIndex) {
		String[] arrti = getArrtibutesArray(nodeIndex);
		int[] counts = new int[arrti.length];
		for (int i = 0; i < counts.length; i++) {
			counts[i] = 0;
		}
		for (int i = 0; i < array.length; i++) {
			String[] strs = (String[]) array[i];
			for (int j = 0; j < arrti.length; j++) {
				if (strs[nodeIndex].equals(arrti[j])) {
					counts[j]++;
				}
			}
		}
		return counts;
	}

	/**
	 * 信息增益率 gainRatio = Gain(A)/splitE(A)
	 * 
	 * @param array
	 *            训练集
	 * @param index
	 *            当前处理属性列号
	 * @param nodeIndex
	 *            预测属性列
	 * @return
	 */
	public double gainRatio(Object[] array, int index, int nodeIndex,String processData) {
		// 计算当前列的信息增益Gain(A)
		double gain = gain(array, index, nodeIndex,processData);
		int[] counts = CountSameValueArrays(array, index);
		double splitInfo = splitE_A(array, counts);
		double gainRatio = 0;
		if (splitInfo != 0) {
			gainRatio = gain / splitInfo;
			process_buffer.append("计算" + this.columnHeaderArray[index]
					+ "属性列的信息增益率gainRatio=Gain(A)/SplitE(A)=" + gain + " / "
					+ splitInfo + "=" + gainRatio+"\n");
			process_buffer.append("================================================分割线===============================================\n");
			return gainRatio;
		}
		return 0;
	}

	/**
	 * infoD = -E(pi*log2 pi)
	 * 
	 * @param array
	 * @param counts
	 * @return
	 */
	public double entropy_S(Object[] array, int[] counts, boolean entropyS,String processingData) {
		double infod = 0;
		double infoD = 0;
		String infoD_str = "";
		for (int i = 0; i < counts.length; i++) {
			infod = info(counts[i], array.length);// 当前计算的分裂信息
			process_buffer.append("计算 p" + i + "*log2 p" + i + "= (" + counts[i]
					+ "/" + array.length + ")*log2 (" + counts[i] + "/"
					+ array.length + ")=" + infod+"\n");
			infoD += infod;
			if (infoD_str.equals("")) {// 处理第一个
				infoD_str += "(" + infod + ")";
			} else {
				infoD_str += "+(" + infod + ")";
			}
		}
		if (entropyS) {
			process_buffer.append("计算训练集S("+processingData+")的信息熵：Entropy(S)=Entropy(p1,p2...,pm)=-sum(pi*log2 pi)=-("
							+ infoD_str + ")=" + (-infoD)+"\n");
		} else {
			process_buffer.append("计算分裂信息：SplitE(A)=-sum(pi*log2 pi)=-("
					+ infoD_str + ")=" + (-infoD)+"\n");
		}
		process_buffer.append("================================================分割线===============================================\n");
		return (-infoD);
	}

	/**
	 * entropyA_S = sum(|Dj| / |D|) * info(Dj)
	 * 
	 * @param array
	 * @param index
	 * @param arrtibutes
	 * @return
	 */
	public double entropyA_S(Object[] array, int index, int nodeIndex,
			String[] arrtibutes) {
		double sv_total = 0;
		String infoDjStr = "";
		double infoDj = 0;
		for (int i = 0; i < arrtibutes.length; i++) {
			infoDj = infoDj(array, index, nodeIndex, arrtibutes[i],
					array.length);
			sv_total += infoDj;
			if (infoDjStr.equals("")) {// 处理第一个
				infoDjStr += "(" + infoDj + ")";
			} else {
				infoDjStr += "+(" + infoDj + ")";
			}
		}
		process_buffer.append("按照属性" + this.columnHeaderArray[index]
				+ "划分S后样本子集的信息熵：Entropy(S," + this.columnHeaderArray[index]
				+ ")=sum((Si/S)*Entropy(Si))=" + infoDjStr + "=" + sv_total+"\n");
		process_buffer.append("================================================分割线===============================================\n");
		return sv_total;
	}

	/**
	 * splitE_A = -sum（i=1~k） |Di|/|D|*log2(|Di|/|D|) E（i=1~k）表示求和运算 i从1到k
	 * 
	 * @param array
	 * @param counts
	 * @return
	 */
	public double splitE_A(Object[] array, int[] counts) {
		return entropy_S(array, counts, false,"ALL");
	}

	/**
	 * ((|Dj| / |D|) * Info(Dj))
	 * 
	 * @param array
	 * @param index
	 * @param arrtibute
	 * @param allTotal
	 * @return double
	 */
	public double infoDj(Object[] array, int index, int nodeIndex,
			String arrtibute, int allTotal) {
		String[] arrtibutes = getArrtibutesArray(nodeIndex);
		int[] counts = new int[arrtibutes.length];
		for (int i = 0; i < counts.length; i++) {
			counts[i] = 0;
		}

		for (int i = 0; i < array.length; i++) {
			String[] strs = (String[]) array[i];
			if (strs[index].equals(arrtibute)) {
				for (int k = 0; k < arrtibutes.length; k++) {
					if (strs[nodeIndex].equals(arrtibutes[k])) {
						counts[k]++;
					}
				}
			}
		}
		for (int k = 0; k < arrtibutes.length; k++) {
			process_buffer.append("计算属性列" + this.columnHeaderArray[index] + "属性值"
					+ arrtibute + "为[" + arrtibutes[k] + "]的个数为：" + counts[k]+"\n");
		}
		int total = 0;
		double infoDj = 0;
		double infoDjt = 0;
		String infoDjStr = "";
		process_buffer.append("计算属性列" + this.columnHeaderArray[index] + "属性值为"
				+ arrtibute + "的总数：");
		for (int i = 0; i < counts.length; i++) {
			if (i == 0) {
				process_buffer.append(counts[i] + "");
			} else {
				process_buffer.append("+" + counts[i]);
			}
			total += counts[i];
		}
		process_buffer.append("=" + total+"\n");

		for (int i = 0; i < counts.length; i++) {
			infoDjt = info(counts[i], total);
			if (i == 0) {
				infoDjStr += "(" + infoDjt + ")";
			} else {
				infoDjStr += "+(" + infoDjt + ")";
			}
			infoDj += infoDjt;
		}
		process_buffer.append("计算Entropy(Si)=Entropy(" + arrtibute + ")=-(");
		for (int i = 0; i < counts.length; i++) {
			if (i == 0) {
				process_buffer.append("(log2 " + counts[i] + "/" + total + ")");
			} else {
				process_buffer.append("+(log2 " + counts[i] + "/" + total + ")");
			}
		}
		process_buffer.append(")=-" + infoDjStr + "=" + (-infoDj)+"\n");
		double entropyA_S = DecisionTree.getPi(total, allTotal) * (-infoDj);
		process_buffer.append("计算(Si/S)*Entropy(Si)=(" + total + "/" + allTotal
				+ ")*(" + (-infoDj) + ")=" + entropyA_S+"\n");
		process_buffer.append("================================================分割线===============================================\n");
		return entropyA_S;
	}

	/**
	 * @param index
	 *            指定 的列号
	 * @return String[] 返回指定列号的属性值数组
	 */
	public String[] getArrtibutesArray(int index) {
		TreeSet<String> set = new TreeSet<String>(new Comparator<Object>() {
			public int compare(Object obj1, Object obj2) {
				String str1 = (String) obj1;
				String str2 = (String) obj2;
				return str1.compareTo(str2);
			}

		});
		for (int i = 0; i < trainingArray.length; i++) {
			String[] strs = (String[]) trainingArray[i];
			set.add(strs[index]);
		}
		String[] result = new String[set.size()];
		return set.toArray(result);
	}

	/**
	 * 
	 * @param index
	 *            指定的属性列号
	 * @return String 返回指定列的属性名
	 */
	public String getColumnHeaderNameByIndex(int index) {
		for (int i = 0; i < columnHeaderArray.length; i++) {
			if (i == index) {
				return columnHeaderArray[i];
			}
		}
		return null;
	}

	/**
	 * @param array
	 * @param nodeIndex
	 *            指定的结点号
	 * @return String 返回叶子节点名
	 */
	public String getLeafNodeName(Object[] array, int nodeIndex) {
		if (array != null && array.length > 0) {
			String[] strs = (String[]) array[0];
			return strs[nodeIndex];
		}
		return null;
	}

	/**
	 * @param name
	 *            指定的属性列名
	 * @return int 返回指定属性列名的索引号（列号）
	 */
	public int getColumnHeaderIndexByName(String name) {
		for (int i = 0; i < columnHeaderArray.length; i++) {
			if (name.equals(columnHeaderArray[i])) {
				return i;
			}
		}
		return NOT_FOUND;
	}

	/**
	 * 信息熵 entropy: info(T)=(i=1...k)pi*log（2）pi
	 * 
	 * @param x
	 *            当Si前值
	 * @param total
	 *            S总值
	 * @return double 返回当前值的 信息分裂 当前属性列的集合的信息分裂的和即为
	 */
	public double info(int x, int total) {
		double info = 0;
		if (x == 0) {
			process_buffer.append(0 + "/" + total + "=0.0\n");
			return 0;
		}
		double x_pi = getPi(x, total);
		process_buffer.append(x + "/" + total + "=" + x_pi+"\n");
		info = (x_pi * logYBase2(x_pi));
		return info;
	}

	/**
	 * log2y
	 * 
	 * @param y
	 * @return double
	 */
	public static double logYBase2(double y) {
		double log2Y = Math.log(y) / Math.log(2);
		return log2Y;
	}

	/**
	 * pi=|C(i,d)|/|D|
	 * 
	 * @param x
	 *            当Si前值
	 * @param total
	 *            S总值
	 * @return double 返回当前值所占总数的比例
	 */
	public static double getPi(int x, int total) {
		double pi = x / (double) total;
		return pi; // 这里必须强制转换，否则计算结果为 整数
	}
}

