/**
 * Project Name:algorithm
 * File Name:StopWordsHandler.java
 * Package Name:com.zongtui.algorithm.classifier.bayes
 * Date:2015-4-14上午10:34:05
 * Copyright (c) 2015, 众推项目组版权所有.
 *
 */

package com.zongtui.algorithm.classifier.bayes;

/**
 * ClassName: StopWordsHandler <br/>
 * Function: 停用词处理器. <br/>
 * date: 2015-4-14 上午10:34:05 <br/>
 * 
 * @author feng
 * @version
 * @since JDK 1.7
 */

public class StopWordsHandler {
	private static String stopWordsList[] = { "的", "我们", "要", "自己", "之", "将",
			"“", "”", "，", "（", "）", "后", "应", "到", "某", "后", "个", "是", "位",
			"新", "一", "两", "在", "中", "或", "有", "更", "好", "" };// 常用停用词

	public static boolean IsStopWord(String word) {
		for (int i = 0; i < stopWordsList.length; ++i) {
			if (word.equalsIgnoreCase(stopWordsList[i]))
				return true;
		}
		return false;
	}

}
