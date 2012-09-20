package com.wordpress.jbaris.common.services;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.wordpress.jbaris.common.entities.Car;


/**
 * @author Juan Ignacio Barisich
 */
@WebService(targetNamespace = "http://com.wordpress.jbaris/")
public interface CarService {

	void addCar(@WebParam(name = "car") Car car);

	void addCarArray(@WebParam(name = "cars") Car[] cars);

	void addCarList(@WebParam(name = "cars") List<Car> cars);

	Car getCarById(@WebParam(name = "id") Long id);

	String getCarName(@WebParam(name = "id") Long id);

	String[] getCarNamesArray();

	List<String> getCarNamesList();

	Car[] getCarsArray();

	List<Car> getCarsByIds(@WebParam(name = "ids") List<Long> ids);

	List<Car> getCarsByNames(@WebParam(name = "names") String[] ids);

	List<Car> getCarsList();

	void reset();

}
