package com.ff.dao.data;

import java.util.ArrayList;
import java.util.List;

public class Job {
	public static enum STATUS{BEGIN, RUN, END, ERROR};

	private int _id;
	private List<Site> _sites;
	private STATUS _status;

	
	public Job (int id, String... urls){
		this._id = id;
		this._sites = new ArrayList<Site>();
		for (String url : urls) {
			this._sites.add(new Site(url));
		}
		this._status = STATUS.BEGIN;
	}
	
	
	public int getId() {
		return _id;
	}
	public void setId(int id) {
		this._id = id;
	}
	public List<Site> getSites() {
		return _sites;
	}
	public void setSites(List<Site> sites) {
		this._sites = sites;
	}
	public STATUS getStatus() {
		return _status;
	}
	public void setStatus(STATUS status) {
		this._status = status;
	}
	
}
