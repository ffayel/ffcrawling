package com.ff.dao.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.ff.tool.MyTime;

public class Job {
	public static enum STATUS {
		BEGIN, RUN, END, ERROR
	};

	private int _id;
	private Calendar _dateCreate;
	private List<Site> _sites;
	private STATUS _status;

	public Job() {
		this._id = -1;
		this._sites = new ArrayList<Site>();
		this._status = STATUS.BEGIN;
		this._dateCreate = MyTime.nowCalendar();
	}

	public Job(String... urls) {
		this();
		for (String url : urls) {
			this._sites.add(new Site(url));
		}
	}

	public Job(List<String> urls) {
		this();
		for (String url : urls) {
			this._sites.add(new Site(url));
		}
	}

	public Job(int id, int status, Calendar dateCreate) {
		this();
		this._id = id;
		this._dateCreate = dateCreate;
		for (STATUS s : STATUS.values()) {
			if (s.ordinal() == status) {
				this._status = s;
				break;
			}
		}

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

	public int getIntStatus() {
		return _status.ordinal();
	}

	public void setStatus(STATUS status) {
		this._status = status;
	}

	public Calendar get_dateCreate() {
		return _dateCreate;
	}

	public void set_dateCreate(Calendar _dateCreate) {
		this._dateCreate = _dateCreate;
	}

	public void addSite(final Site site) {
		this._sites.add(site);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Job id:"+this._id);
		builder.append("/status:"+this._status);
		builder.append("/date:"+MyTime.getAffichageComplet(this._dateCreate));
		builder.append("/ countSite:"+this._sites.size());
		builder.append(" sites\n");	
		for (Site s : this._sites) {
			builder.append(s);
		}
		return builder.toString();
	}

}
