package com.ff.dao.data;

import java.util.Calendar;

import com.ff.tool.MyTime;

public class SiteImage {
	private int _id;
	private String _url;
	private Calendar _dateLoad;
	
	
	public SiteImage(int id, String url, Calendar dateLoad) {
		super();
		this._id = id;
		this._url = url;
		this._dateLoad = dateLoad;
	}
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
	
	@Override
	public String toString() {
		return "\t\tImg id:"+this._id+"/url:"+this._url+"/dateLoad:"+MyTime.getAffichageComplet(this._dateLoad);
	}
}
