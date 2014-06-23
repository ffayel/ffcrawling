package com.ff.dao.data;

public final class DataLabelString {

	private String key;
	private String value;

	public DataLabelString(final String key, final String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * @return the key
	 */
	public final String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public final void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the value
	 */
	public final String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public final void setValue(String value) {
		this.value = value;
	}

}
