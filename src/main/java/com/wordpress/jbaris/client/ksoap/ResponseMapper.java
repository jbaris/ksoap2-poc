package com.wordpress.jbaris.client.ksoap;

/**
 * @author Juan Ignacio Barisich
 */
public interface ResponseMapper<T> {

	T mapResponse(Object response);

}
