package de.galan.commons.util;

/**
 * Simple key/value pair
 *
 * @author galan
 * @param <K> Type of key
 * @param <V> Type of value
 */
public class Pair<K, V> {

	K key;
	V value;


	public Pair() {
		//
	}


	public Pair(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}


	public K getKey() {
		return key;
	}


	public void setKey(K key) {
		this.key = key;
	}


	public Pair<K, V> k(K k) {
		setKey(k);
		return this;
	}


	public V getValue() {
		return value;
	}


	public void setValue(V value) {
		this.value = value;
	}


	public Pair<K, V> v(V v) {
		setValue(v);
		return this;
	}


	@Override
	public String toString() {
		return getKey() + "/" + getValue();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Pair<?, ?> other = (Pair<?, ?>)obj;
		if (key == null) {
			if (other.key != null) {
				return false;
			}
		}
		else if (!key.equals(other.key)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		}
		else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

}
