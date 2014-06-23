package com.ff.dao.data;


public final class DataProperty implements Comparable<DataProperty> {

	@Override
	public String toString() {
		return mainKey + " - " + key + " - " + value;
	}

	private String mainKey = "";
	private String key;
	private String value;
	private String defaultValue;

	public DataProperty(final String key, final String value, final String defaultValue) {
		try { this.mainKey = key.substring(0, key.indexOf(".")); } catch (Exception e) { }
		this.key = key;
		this.value = value;
		this.defaultValue = defaultValue;
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
		try { this.mainKey = key.substring(0, key.indexOf(".")); } catch (Exception e) { }
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

	public int compareTo(DataProperty o) {
		return this.getKey().compareToIgnoreCase(o.getKey());
	}

	public String getMainKey() {
		return mainKey;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
