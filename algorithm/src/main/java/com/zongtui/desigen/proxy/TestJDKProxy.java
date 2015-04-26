/**
 * 
 */
package com.zongtui.desigen.proxy;

/**
 * @author Administrator
 *
 */
public class TestJDKProxy {
	public static void main(String[] args) {
		BookFacadeProxy proxy = new BookFacadeProxy();
		BookFacade bookProxy = (BookFacade) proxy.bind(new BookFacadeImpl());
		bookProxy.addBook();
	}
}
