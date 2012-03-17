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

	void addAllCars(@WebParam(name = "cars") List<Car> cars);

	void addCar(@WebParam(name = "car") Car car);

	List<String> getAllCarNames();

	List<Car> getAllCars();

	Car getCarById(@WebParam(name = "id") Long id);

	String getCarName(@WebParam(name = "id") Long id);

	List<Car> getCarsByIds(@WebParam(name = "ids") List<Long> ids);

	void reset();

}
