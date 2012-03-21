package jbaris.wordpress.com.common.services;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import jbaris.wordpress.com.common.entities.Car;

/**
 * @author Juan Ignacio Barisich
 */
@WebService(targetNamespace = "http://com.wordpress.jbaris/")
public interface CarService {

	void addCarList(@WebParam(name = "cars") List<Car> cars);
	void addCarArray(@WebParam(name = "cars") Car[] cars);

	void addCar(@WebParam(name = "car") Car car);

	List<String> getCarNamesList();
	String[] getCarNamesArray();

	List<Car> getCarsList();
	Car[] getCarsArray();

	Car getCarById(@WebParam(name = "id") Long id);

	String getCarName(@WebParam(name = "id") Long id);

	List<Car> getCarsByIds(@WebParam(name = "ids") List<Long> ids);
	List<Car> getCarsByNames(@WebParam(name = "names") String[] ids);

	void reset();

}
