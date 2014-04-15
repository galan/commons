package de.galan.commons.util;

/**
 * daniel should have written a comment here.
 * 
 * @author daniel
 * @param <K> ..
 * @param <V> ..
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


	public V getValue() {
		return value;
	}


	public void setValue(V value) {
		this.value = value;
	}

}
