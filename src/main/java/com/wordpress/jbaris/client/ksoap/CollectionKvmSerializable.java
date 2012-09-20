package com.wordpress.jbaris.client.ksoap;

import java.util.Hashtable;
import java.util.Vector;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * @author Juan Ignacio Barisich
 */
public class CollectionKvmSerializable<T> extends Vector<T> implements
		KvmSerializable {

	private static final long serialVersionUID = -2043977785996851154L;

	private final String parentName;
	private final String childrenName;
	private final Class<?> childrenType;

	public CollectionKvmSerializable(String parentName, String childrenName,
			Class<?> childrenType) {
		this.parentName = parentName;
		this.childrenName = childrenName;
		this.childrenType = childrenType;
	}

	public PropertyInfo buildPropertyInfo() {
		final PropertyInfo documentIdsPropertyInfo = new PropertyInfo();
		documentIdsPropertyInfo.setName(this.parentName);
		documentIdsPropertyInfo.setValue(this);
		documentIdsPropertyInfo.setType(this.getClass());
		return documentIdsPropertyInfo;
	}

	@Override
	public Object getProperty(int arg0) {
		return get(arg0);
	}

	@Override
	public int getPropertyCount() {
		return size();
	}

	@Override
	public void getPropertyInfo(int arg0,
			@SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo arg2) {
		arg2.name = childrenName;
		arg2.type = childrenType;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setProperty(int arg0, Object arg1) {
		this.add((T) arg1);
	}

}
