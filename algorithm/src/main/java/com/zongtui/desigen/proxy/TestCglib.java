/**
 * 
 */
package com.zongtui.desigen.proxy;

/**
 * @author Administrator
 *
 */
public class TestCglib {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BookFacadeCglib cglib = new BookFacadeCglib();
		BookFacadeImpl1 bookCglib = (BookFacadeImpl1) cglib
				.getInstance(new BookFacadeImpl1());
		bookCglib.addBook();
	}

}
