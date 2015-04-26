/**
 * 
 */
package com.zongtui.desigen.proxy;

/**
 * @author Administrator
 *
 */
public class CountImpl implements Count {

	@Override
	public void queryCount() {
		System.out.println("查看账户方法...");

	}

	@Override
	public void updateCount() {
		System.out.println("修改账户方法...");

	}
}
