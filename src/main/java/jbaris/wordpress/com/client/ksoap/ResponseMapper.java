package jbaris.wordpress.com.client.ksoap;

/**
 * @author Juan Ignacio Barisich
 */
public interface ResponseMapper<T> {

	T mapResponse(Object response);

}
