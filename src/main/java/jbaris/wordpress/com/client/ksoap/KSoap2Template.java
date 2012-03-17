package jbaris.wordpress.com.client.ksoap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * @author Juan Ignacio Barisich
 */
public class KSoap2Template {

	private static final ResponseMapper<Object> NULL_RESPONSE_MAPPER = new ResponseMapper<Object>() {

		@Override
		public Object mapResponse(Object response) {
			return response;
		}
	};

	private static final RequestPropertiesSetter NULL_REQUEST_PROPERTIES_SETTER = new RequestPropertiesSetter() {

		@Override
		public void setValues(SoapObject request) {
			// do nothing
		}
	};

	private final String namespace;
	private final String url;

	public KSoap2Template(String url, String namespace) {
		this.url = url;
		this.namespace = namespace;
	}

	public void call(String methodName, String soapAction)
			throws ServiceAccessException {
		call(methodName, soapAction, NULL_REQUEST_PROPERTIES_SETTER);
	}

	public void call(String methodName, String soapAction,
			RequestPropertiesSetter requestPropertiesSetter)
			throws ServiceAccessException {
		call(methodName, soapAction, requestPropertiesSetter,
				NULL_RESPONSE_MAPPER);
	}

	public <T> T call(String methodName, String soapAction,
			RequestPropertiesSetter requestPropertiesSetter,
			ResponseMapper<T> responseMapper) throws ServiceAccessException {
		try {
			final SoapObject request = new SoapObject(getNamespace(),
					methodName);
			final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			final HttpTransportSE transport = new HttpTransportSE(getUrl());
			requestPropertiesSetter.setValues(request);
			transport.call(soapAction, envelope);
			return responseMapper.mapResponse(envelope.getResponse());
		} catch (final Exception e) {
			throw new ServiceAccessException(e);
		}
	}

	public <T> T call(String methodName, String soapAction,
			ResponseMapper<T> responseMapper) throws ServiceAccessException {
		return call(methodName, soapAction, NULL_REQUEST_PROPERTIES_SETTER,
				responseMapper);
	}

	private String getNamespace() {
		return namespace;
	}

	private String getUrl() {
		return url;
	}

}
