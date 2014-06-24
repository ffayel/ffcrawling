package com.ff.dao.data;

import java.util.ArrayList;
import java.util.List;

public class Site {
	private int _id;
	private String _url;
	private List<SiteImage> _images;
	
	public Site() {
		this._id = -1;
		this._url = "";
		this._images = new ArrayList<SiteImage>();
	}
	
	public Site(final int id, final String url) {
		this._id = id;
		this._url = url;
		this._images = new ArrayList<SiteImage>();
	}
	public Site(final String url) {
		this._id = -1;
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

	public void addImage(SiteImage img) {
		this._images.add(img);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Job id:"+this._id);
		builder.append("/url:"+this._url);
		builder.append(" SiteImage:\n");	
		for (SiteImage sImg : this._images) {
			builder.append(sImg);
		}
		return builder.toString();
	}

	public int countImage() {
		return this._images.size();
	}
}
