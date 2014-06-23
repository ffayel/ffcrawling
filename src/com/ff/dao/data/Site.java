package com.ff.dao.data;

import java.util.ArrayList;
import java.util.List;

public class Site {
	private int _id;
	private String _url;
	private List<SiteImage> _images;
	
	public Site(final String url) {
		this._id = 0;
		this._url = url;
		this._images = new ArrayList<SiteImage>();
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
	public List<SiteImage> getImages() {
		return _images;
	}
	public void setImages(List<SiteImage> images) {
		this._images = images;
	}
}
