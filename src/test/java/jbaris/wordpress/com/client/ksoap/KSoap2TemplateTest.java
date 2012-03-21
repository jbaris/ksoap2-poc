package jbaris.wordpress.com.client.ksoap;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import jbaris.wordpress.com.common.entities.Car;
import jbaris.wordpress.com.common.entities.Manufacturer;
import jbaris.wordpress.com.common.entities.Owner;
import junit.framework.TestCase;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.ksoap2.serialization.SoapObject;

/**
 * @author Juan Ignacio Barisich
 */
public class KSoap2TemplateTest extends TestCase {

	private Server server;
	private KSoap2Template template;

	final ResponseMapper<Owner> ownerResponseMapper = new AbstractResponseMapper<Owner>() {

		@Override
		public Owner mapResponse(Object response) {
			final Owner result = new Owner();
			result.setId(getProperty((SoapObject) response, "id", Long.class));
			result.setName(getProperty((SoapObject) response, "name"));
			return result;
		}

	};

	final ResponseMapper<Manufacturer> manufacturerResponseMapper = new AbstractResponseMapper<Manufacturer>() {

		@Override
		public Manufacturer mapResponse(Object response) {
			final Manufacturer result = new Manufacturer();
			result.setCountry(getProperty((SoapObject) response, "country"));
			result.setId(getProperty((SoapObject) response, "id", Long.class));
			result.setName(getProperty((SoapObject) response, "name"));
			return result;
		}

	};

	final ResponseMapper<Car> carResponseMapper = new AbstractResponseMapper<Car>() {

		@Override
		public Car mapResponse(Object response) {
			Car result = null;
			if (response != null) {
				result = new Car();
				result.setId(getProperty((SoapObject) response, "id",
						Long.class));
				result.setManufacturer(getProperty((SoapObject) response,
						"manufacturer", manufacturerResponseMapper));
				result.setName(getProperty((SoapObject) response, "name"));
				result.setOwners(getList((SoapObject) response, "owners",
						ownerResponseMapper));
			}
			return result;
		}

	};

	ResponseMapper<List<Car>> carListResponseMapper = new AbstractResponseMapper<List<Car>>() {
		@Override
		public List<Car> mapResponse(Object response) {
			return getList(response, carResponseMapper);
		}
	};

	ResponseMapper<Car[]> carArrayResponseMapper = new AbstractResponseMapper<Car[]>() {
		@SuppressWarnings("unchecked")
		@Override
		public Car[] mapResponse(Object response) {
			return getArray((Collection<Object>) response, carResponseMapper,
					Car.class);
		}
	};

	private KSoap2Template getTemplate() {
		return template;
	}

	private void noParams_noResult_Call() {
		getTemplate().call("reset",
				"http://impl.services.com.wordpress.jbaris/reset");
	}

	private void objectArrayParam_Call() {
		getTemplate().call("addCarArray",
				"http://impl.services.com.wordpress.jbaris/addCarArray",
				new RequestPropertiesSetter() {

					@Override
					public void setValues(SoapObject request) {
						final SoapObject ownerSO = new SoapObject(null,
								"owners");
						ownerSO.addProperty("id", 1L);
						ownerSO.addProperty("name", "John");

						final SoapObject manufacturerSO = new SoapObject(null,
								"manufacturer");
						manufacturerSO.addProperty("id", 1L);
						manufacturerSO.addProperty("name", "Chevrolet");
						manufacturerSO.addProperty("country", "USA");

						final SoapObject carSO = new SoapObject(null, "cars");
						carSO.addProperty("id", 4L);
						carSO.addProperty("name", "Corsa");
						carSO.addProperty("manufacturer", manufacturerSO);
						carSO.addSoapObject(ownerSO);

						final SoapObject owner2SO = new SoapObject(null,
								"owners");
						owner2SO.addProperty("id", 2L);
						owner2SO.addProperty("name", "Mary");

						final SoapObject car2SO = new SoapObject(null, "cars");
						car2SO.addProperty("id", 5L);
						car2SO.addProperty("name", "Zafira");
						car2SO.addProperty("manufacturer", manufacturerSO);
						car2SO.addSoapObject(owner2SO);

						request.addSoapObject(carSO);
						request.addSoapObject(car2SO);
					}
				});
	}

	private void objectArrayResult_Call() {
		final Car[] cars = getTemplate().call("getCarsArray",
				"http://impl.services.com.wordpress.jbaris/getCarsArray",
				carArrayResponseMapper);
		assertEquals(5, cars.length);
		final List<Car> carsList = Arrays.asList(cars);
		assertTrue(carsList.contains(new Car(1L)));
		assertTrue(carsList.contains(new Car(2L)));
		assertTrue(carsList.contains(new Car(3L)));
		assertTrue(carsList.contains(new Car(4L)));
		assertTrue(carsList.contains(new Car(5L)));
	}

	private void objectListParam_Call() {
		getTemplate().call("addCarList",
				"http://impl.services.com.wordpress.jbaris/addCarList",
				new RequestPropertiesSetter() {

					@Override
					public void setValues(SoapObject request) {
						final SoapObject ownerSO = new SoapObject(null,
								"owners");
						ownerSO.addProperty("id", 1L);
						ownerSO.addProperty("name", "John");

						final SoapObject manufacturerSO = new SoapObject(null,
								"manufacturer");
						manufacturerSO.addProperty("id", 1L);
						manufacturerSO.addProperty("name", "Chevrolet");
						manufacturerSO.addProperty("country", "USA");

						final SoapObject carSO = new SoapObject(null, "cars");
						carSO.addProperty("id", 2L);
						carSO.addProperty("name", "Astra");
						carSO.addProperty("manufacturer", manufacturerSO);
						carSO.addSoapObject(ownerSO);

						final SoapObject owner2SO = new SoapObject(null,
								"owners");
						owner2SO.addProperty("id", 2L);
						owner2SO.addProperty("name", "Mary");

						final SoapObject car2SO = new SoapObject(null, "cars");
						car2SO.addProperty("id", 3L);
						car2SO.addProperty("name", "Vectra");
						car2SO.addProperty("manufacturer", manufacturerSO);
						car2SO.addSoapObject(owner2SO);

						request.addSoapObject(carSO);
						request.addSoapObject(car2SO);
					}
				});
	}

	private void objectListResut_Call() {
		final List<Car> cars = getTemplate().call("getCarsList",
				"http://impl.services.com.wordpress.jbaris/getCarsList",
				carListResponseMapper);
		assertEquals(5, cars.size());
		assertTrue(cars.contains(new Car(1L)));
		assertTrue(cars.contains(new Car(2L)));
		assertTrue(cars.contains(new Car(3L)));
		assertTrue(cars.contains(new Car(4L)));
		assertTrue(cars.contains(new Car(5L)));
	}

	private void objectParam_Call() {
		getTemplate().call("addCar",
				"http://impl.services.com.wordpress.jbaris/addCar",
				new RequestPropertiesSetter() {

					@Override
					public void setValues(SoapObject request) {
						final SoapObject ownerSO = new SoapObject(null,
								"owners");
						ownerSO.addProperty("id", 1L);
						ownerSO.addProperty("name", "John");

						final SoapObject owner2SO = new SoapObject(null,
								"owners");
						owner2SO.addProperty("id", 2L);
						owner2SO.addProperty("name", "Mary");

						final SoapObject manufacturerSO = new SoapObject(null,
								"manufacturer");
						manufacturerSO.addProperty("id", 1L);
						manufacturerSO.addProperty("name", "Chevrolet");
						manufacturerSO.addProperty("country", "USA");

						final SoapObject carSO = new SoapObject(null, "car");
						carSO.addProperty("id", 1L);
						carSO.addProperty("name", "Aveo");
						carSO.addProperty("manufacturer", manufacturerSO);
						carSO.addSoapObject(ownerSO);
						carSO.addSoapObject(owner2SO);

						request.addProperty("car", carSO);
					}
				});
	}

	private void objectResult_Call() {
		primitiveParam_Call();
	}

	private void primitiveArrayParam_Call() {
		final List<Car> cars = getTemplate().call("getCarsByNames",
				"http://impl.services.com.wordpress.jbaris/getCarsByNames",
				new RequestPropertiesSetter() {

					@Override
					public void setValues(SoapObject request) {
						request.addProperty("names", "Vectra");
						request.addProperty("names", "Astra");
					}
				}, carListResponseMapper);
		assertEquals(2, cars.size());
		assertTrue(cars.contains(new Car(2L)));
		assertTrue(cars.contains(new Car(3L)));
	}

	private void primitiveArrayResult_Call() {
		final String[] carNames = getTemplate().call("getCarNamesArray",
				"http://impl.services.com.wordpress.jbaris/getCarNamesArray",
				new AbstractResponseMapper<String[]>() {
					@Override
					public String[] mapResponse(Object response) {
						return getArray(response);
					}
				});
		final List<String> carNamesList = Arrays.asList(carNames);
		assertTrue(carNamesList.contains("Aveo"));
		assertTrue(carNamesList.contains("Astra"));
		assertTrue(carNamesList.contains("Vectra"));
	}

	private void primitiveListParam_Call() {
		final List<Car> cars = getTemplate().call("getCarsByIds",
				"http://impl.services.com.wordpress.jbaris/getCarsByIds",
				new RequestPropertiesSetter() {

					@Override
					public void setValues(SoapObject request) {
						request.addProperty("ids", 2L);
						request.addProperty("ids", 3L);
					}
				}, carListResponseMapper);
		assertEquals(2, cars.size());
		assertTrue(cars.contains(new Car(2L)));
		assertTrue(cars.contains(new Car(3L)));
	}

	private void primitiveListResult_Call() {
		final List<String> carNames = getTemplate().call("getCarNamesList",
				"http://impl.services.com.wordpress.jbaris/getCarNamesList",
				new AbstractResponseMapper<List<String>>() {
					@Override
					public List<String> mapResponse(Object response) {
						return getList(response);
					}
				});
		assertTrue(carNames.contains("Aveo"));
		assertTrue(carNames.contains("Astra"));
		assertTrue(carNames.contains("Vectra"));
	}

	private void primitiveParam_Call() {
		final Car car = getTemplate().call("getCarById", "getCarById",
				new RequestPropertiesSetter() {

					@Override
					public void setValues(SoapObject request) {
						request.addProperty("id", 2L);
					}
				}, carResponseMapper);
		assertNotNull(car);
		assertEquals((Long) 2L, car.getId());
		assertEquals("Astra", car.getName());
		assertEquals(1, car.getOwners().size());
		assertEquals("John", car.getOwners().get(0).getName());
		assertEquals("Chevrolet", car.getManufacturer().getName());
	}

	private void primitiveResult_Call() {
		final String carName = getTemplate().call("getCarName",
				"http://impl.services.com.wordpress.jbaris/getCarName",
				new RequestPropertiesSetter() {

					@Override
					public void setValues(SoapObject request) {
						request.addProperty("id", 3L);
					}
				}, new AbstractResponseMapper<String>() {
					@Override
					public String mapResponse(Object response) {
						return response.toString();
					}
				});
		assertEquals("Vectra", carName);
	}

	@Override
	protected void setUp() throws Exception {
		final int port = 8282;
		server = new Server(port);
		final WebAppContext context = new WebAppContext();
		context.setDescriptor("/WEB-INF/web.xml");
		context.setBaseResource(Resource.newResource(ClassLoader
				.getSystemResource("webapp")));
		context.setContextPath("/");
		context.setParentLoaderPriority(true);
		server.setHandler(context);
		server.start();
		template = new KSoap2Template("http://localhost:" + port
				+ "/ws/CarService", "http://com.wordpress.jbaris/");
	}

	@Override
	protected void tearDown() throws Exception {
		if (server != null) {
			server.stop();
			server = null;
		}
		template = null;
	}

	public void testCall() {
		noParams_noResult_Call();
		// Test params
		objectParam_Call();
		objectListParam_Call();
		objectArrayParam_Call();
		primitiveParam_Call();
		primitiveListParam_Call();
		primitiveArrayParam_Call();
		// Test results
		objectResult_Call();
		objectListResut_Call();
		objectArrayResult_Call();
		primitiveResult_Call();
		primitiveListResult_Call();
		primitiveArrayResult_Call();
	}

}