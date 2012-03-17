package jbaris.wordpress.com.client.ksoap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

/**
 * @author Juan Ignacio Barisich
 */
public abstract class AbstractResponseMapper<T> implements ResponseMapper<T> {

	protected <U> List<U> getList(Collection<SoapObject> collection,
			ResponseMapper<U> itemMapper) {
		final List<U> result = new ArrayList<U>();
		for (final SoapObject soapPrimitive : collection) {
			result.add(itemMapper.mapResponse(soapPrimitive));
		}
		return result;
	}

	protected List<String> getList(Collection<SoapPrimitive> collection) {
		final List<String> result = new ArrayList<String>();
		for (final SoapPrimitive soapPrimitive : collection) {
			result.add(soapPrimitive.toString());
		}
		return result;
	}

	protected <U> List<U> getList(Collection<SoapPrimitive> collection,
			Class<U> resultType) {
		final List<U> result = new ArrayList<U>();
		for (final SoapPrimitive soapPrimitive : collection) {
			result.add(valueOf(resultType, soapPrimitive.toString()));
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
