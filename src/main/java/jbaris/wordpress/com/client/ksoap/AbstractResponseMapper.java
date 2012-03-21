package jbaris.wordpress.com.client.ksoap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

/**
 * @author Juan Ignacio Barisich
 */
public abstract class AbstractResponseMapper<T> implements ResponseMapper<T> {

	protected char[] getCharArray(Object response) {
		char[] result = new char[0];
		if (response instanceof Collection) {
			@SuppressWarnings("unchecked")
			Collection<Object> collection = (Collection<Object>) response;
			result = new char[collection.size()];
			Iterator<Object> iterator = collection.iterator();
			for (int i = 0; i < result.length; i++) {
				String string = iterator.next().toString();
				result[i] = Character.valueOf((char) Integer.parseInt(string));
			}
		} else if (response != null) {
			result = new char[1];
			result[0] = Character.valueOf((char) Integer.parseInt(response
					.toString()));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected String[] getArray(Object response) {
		String[] result = new String[0];
		if (response instanceof Collection) {
			Collection<Object> collection = (Collection<Object>) response;
			result = new String[collection.size()];
			Iterator<Object> iterator = collection.iterator();
			for (int i = 0; i < result.length; i++) {
				result[i] = iterator.next().toString();
			}
		} else if (response != null) {
			result = new String[1];
			result[0] = response.toString();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected <U> U[] getArray(Collection<Object> collection,
			ResponseMapper<U> itemMapper, Class<U> resultType) {
		U[] result = (U[]) Array.newInstance(resultType, collection.size());
		Iterator<Object> iterator = collection.iterator();
		for (int i = 0; i < result.length; i++) {
			result[i] = itemMapper.mapResponse(iterator.next());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected <U> List<U> getList(Object response, ResponseMapper<U> itemMapper) {
		final List<U> result = new ArrayList<U>();
		if (response instanceof Collection) {
			Collection<Object> responseCollection = (Collection<Object>) response;
			for (final Object soapPrimitive : responseCollection) {
				result.add(itemMapper.mapResponse(soapPrimitive));
			}
		} else if (response != null) {
			result.add(itemMapper.mapResponse(response));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List<String> getList(Object response) {
		final List<String> result = new ArrayList<String>();
		if (response instanceof Collection) {
			Collection<Object> collection = (Collection<Object>) response;
			for (final Object item : collection) {
				result.add(item.toString());
			}
		} else if (response != null) {
			result.add(response.toString());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected <U> List<U> getList(Object response, Class<U> resultType) {
		final List<U> result = new ArrayList<U>();
		if (response instanceof Collection) {
			Collection<Object> collection = (Collection<Object>) response;
			for (final Object item : collection) {
				result.add(valueOf(resultType, item.toString()));
			}
		} else if (response != null) {
			result.add(valueOf(resultType, response.toString()));
		}
		return result;
	}

	protected <U> List<U> getList(SoapObject response, String key,
			ResponseMapper<U> itemMapper) {
		final List<U> result = new ArrayList<U>();
		final int propCount = response.getPropertyCount();
		for (int i = 0; i < propCount; i++) {
			final PropertyInfo propInfo = new PropertyInfo();
			response.getPropertyInfo(i, propInfo);
			if (propInfo.getName().equals(key)) {
				result.add(itemMapper.mapResponse(propInfo.getValue()));
			}
		}
		return result;
	}

	protected String getProperty(SoapObject soapObject, String key) {
		return soapObject.getProperty(key).toString();
	}

	protected <U> U getProperty(SoapObject soapObject, String key,
			Class<U> resultType) {
		return valueOf(resultType, getProperty(soapObject, key));
	}

	protected <U> U getProperty(SoapObject soapObject, String key,
			ResponseMapper<U> propertyMapper) {
		return propertyMapper.mapResponse(soapObject.getProperty(key));
	}

	protected <U> U valueOf(Class<U> resultType, Object object) {
		U result;
		if (object == null) {
			result = null;
		} else {
			result = valueOf(resultType, object.toString());
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private <U> U valueOf(Class<U> resultType, String valueAsString) {
		try {
			return (U) resultType.getMethod("valueOf",
					new Class[] { String.class }).invoke(resultType,
					new Object[] { valueAsString });
		} catch (final Exception e) {
			throw new IllegalStateException(e);
		}
	}

}
