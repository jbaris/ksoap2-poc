package jbaris.wordpress.com.server.services.impl;

import java.util.ArrayList;
import java.util.List;

import jbaris.wordpress.com.common.entities.Car;
import jbaris.wordpress.com.common.services.CarService;

import org.apache.log4j.Logger;

/**
 * @author Juan Ignacio Barisich
 */
public class CarServiceImpl implements CarService {

	private static Logger LOGGER = Logger.getLogger(CarServiceImpl.class);

	private final List<Car> cars = new ArrayList<Car>();

	@Override
	public void addAllCars(List<Car> cars) {
		LOGGER.info("addAllCars = " + cars);
		this.cars.addAll(cars);
	}

	@Override
	public void addCar(Car car) {
		LOGGER.info("addCar = " + car);
		cars.add(car);
	}

	@Override
	public List<String> getAllCarNames() {
		final List<String> result = new ArrayList<String>();
		for (final Car car : cars) {
			result.add(car.getName());
		}
		LOGGER.info("getAllCarNames = " + result);
		return result;
	}

	@Override
	public List<Car> getAllCars() {
		LOGGER.info("getAllCars = " + cars);
		return new ArrayList<Car>(cars);
	}

	@Override
	public Car getCarById(Long id) {
		LOGGER.info("getCarById = " + id);
		for (final Car car : cars) {
			if (car.getId().equals(id)) {
				return car;
			}
		}
		return null;
	}

	@Override
	public String getCarName(Long id) {
		final String result = getCarById(id).getName();
		LOGGER.info("getCarName = " + result);
		return result;
	}

	@Override
	public List<Car> getCarsByIds(List<Long> ids) {
		final List<Car> result = new ArrayList<Car>();
		for (final Long id : ids) {
			final Car car = getCarById(id);
			if (car != null) {
				result.add(car);
			}
		}
		LOGGER.info("getCarsById = " + result);
		return result;
	}

	@Override
	public void reset() {
		LOGGER.info("reset");
		cars.clear();
	}

}
