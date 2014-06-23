package com.ff.dao.data;

import java.io.Serializable;

public final class DataLabel implements Serializable  {

	private static final long serialVersionUID = -1466849792463831473L;

	private int key = 0;
	private String value = "";

	public DataLabel(final int key, final String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * @return the key
	 */
	public int getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(int key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "LabelData: " + key + " - " + value;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DataLabel){
			return this.key == ((DataLabel)obj).key;
		}
		return false;
	}

}
