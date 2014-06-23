package com.ff.dao.data;

import java.util.Calendar;

public class SiteImage {
	private int _id;
	private String _url;
	private Calendar _dateLoad;
	
	
	public int getId() {
		return _id;
	}
	public void setId(int id) {
		this._id = id;
	}
	public String getUrl() {
		return _url;
	}
	public void setUrl(String url) {
		this._url = url;
	}
	public Calendar getDateLoad() {
		return _dateLoad;
	}
	public void setDateLoad(Calendar dateLoad) {
		this._dateLoad = dateLoad;
	}
}
