package com.wordpress.jbaris.server.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.apache.log4j.Logger;

import com.wordpress.jbaris.common.entities.Car;
import com.wordpress.jbaris.common.services.CarService;

/**
 * @author Juan Ignacio Barisich
 */
public class CarServiceImpl implements CarService {

	private static Logger LOGGER = Logger.getLogger(CarServiceImpl.class);

	private final List<Car> cars = new ArrayList<Car>();

	@Override
	public void addCar(Car car) {
		LOGGER.info("addCar = " + car);
		cars.add(car);
	}

	@Override
	public void addCarArray(Car[] cars) {
		for (final Car car : cars) {
			this.cars.add(car);
		}
		LOGGER.info("addCarArray = " + Arrays.deepToString(cars));
	}

	@Override
	public void addCarList(List<Car> cars) {
		LOGGER.info("addCarList = " + cars);
		this.cars.addAll(cars);
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
	public String[] getCarNamesArray() {
		final String[] result = new String[cars.size()];
		int i = 0;
		for (final Car car : cars) {
			result[i++] = car.getName();
		}
		LOGGER.info("getCarNamesArray = " + Arrays.deepToString(result));
		return result;
	}

	@Override
	public List<String> getCarNamesList() {
		final List<String> result = new ArrayList<String>();
		for (final Car car : cars) {
			result.add(car.getName());
		}
		LOGGER.info("getCarNamesList = " + result);
		return result;
	}

	@Override
	public Car[] getCarsArray() {
		final Car[] result = new Car[cars.size()];
		int i = 0;
		for (final Car car : cars) {
			result[i++] = car;
		}
		LOGGER.info("getCarsArray = " + Arrays.deepToString(result));
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
	public List<Car> getCarsByNames(String[] names) {
		final List<Car> result = new ArrayList<Car>();
		for (final Car car : cars) {
			final String name = car.getName();
			for (final String string : names) {
				if (string.equals(name)) {
					result.add(car);
				}
			}
		}
		LOGGER.info("getCarsByNames = " + result);
		return result;
	}

	@Override
	public List<Car> getCarsList() {
		LOGGER.info("getCarsList = " + cars);
		return new ArrayList<Car>(cars);
	}

	@Override
	public void reset() {
		LOGGER.info("reset");
		cars.clear();
	}

}
