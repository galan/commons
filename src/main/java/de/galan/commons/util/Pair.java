package de.galan.commons.util;

/**
 * Simple key/value pair
 *
 * @author daniel
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

}
