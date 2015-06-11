package de.galan.commons.collection;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Stores it's value-entries as SoftReferences
 *
 * @author galan
 * @param <T> Type of value objects
 */
public class SoftReferenceCache<T> {

	private Map<String, SoftReference<T>> cache = null;


	public SoftReferenceCache() {
		cache = Collections.synchronizedMap(new HashMap<String, SoftReference<T>>());
	}


	public void put(String key, T obj) {
		cache.put(key, new SoftReference<T>(obj));
	}


	public T get(String key) {
		SoftReference<T> ref = cache.get(key);
		if (ref == null) {
			return null;
		}
		T result = ref.get();
		if (result == null) {
			cache.remove(key);
		}
		return result;
	}

}
