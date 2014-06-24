package com.ff.dao.util;

import java.util.List;

import com.ff.dao.data.Job;
import com.ff.dao.data.Job.STATUS;
import com.ff.dao.exception.FFCrawlSqlException;
import com.ff.dao.sql.SqlJob;

public class UtilJob {
	private final static long auth = 61686052;

	public static Job getById(final int jobId) {
		if(jobId <= 0 ){
			throw new FFCrawlSqlException("No jobId");
		}
		return SqlJob.get(auth, jobId);
	}
	
	
	public static int create(final List<String> urls){
		if(urls == null){
			throw new FFCrawlSqlException("No url");
		}else if(urls.size() <=0){
			throw new FFCrawlSqlException("No url");
		}
		return SqlJob.create(auth, urls);
	}
	
	public static STATUS getStatus(final int jobId) throws FFCrawlSqlException{
		if(jobId <= 0 ){
			throw new FFCrawlSqlException("No jobId");
		}
		return SqlJob.getStatus(auth, jobId);
	}
	
	public static int updateStatus(final int jobId, final STATUS status){
		if(jobId <= 0 || status == null){
			throw new FFCrawlSqlException("No jobId");
		}
		return SqlJob.updateStatus(auth, jobId, status);
	}

}
