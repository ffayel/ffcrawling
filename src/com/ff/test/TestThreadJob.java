package com.ff.test;

import com.ff.dao.data.Job;
import com.ff.thread.JobThread;

public class TestThreadJob {

	public static void main(String[] args) {
		Job job = new Job("http://www.cnn.com/", "http://hebus.com");
		JobThread jobThread = new JobThread(job);
		jobThread.setSaveBDD(false);
		Thread thread = new Thread(jobThread);
		thread.run();
	}

}
