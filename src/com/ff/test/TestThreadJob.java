package com.ff.test;

import com.ff.dao.data.Job;
import com.ff.thread.JobThread;

public class TestThreadJob {

	public static void main(String[] args) {
		Job job = new Job(1, "http://www.cnn.com/", "http://hebus.com");
		Thread thread = new Thread(new JobThread(job));
		thread.run();
	}

}
