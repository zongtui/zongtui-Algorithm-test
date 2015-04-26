/**
 * 
 */
package com.zongtui.desigen.proxy;

/**
 * @author Administrator
 *
 */
public class TestCount {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	        CountImpl countImpl = new CountImpl();  
	        CountProxy countProxy = new CountProxy(countImpl);  
	        countProxy.updateCount();  
	        countProxy.queryCount();  
	}
}
