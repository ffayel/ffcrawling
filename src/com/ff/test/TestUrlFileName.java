package com.ff.test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class TestUrlFileName {

	public static void main(String[] args) {
		try {
			URL url = new URL("http://i2.cdn.turner.com/cnn/dam/assets/140624185139-suarez-tease-video-tease.jpg");
			System.out.println(url.getFile());
			File f = new File("http://i2.cdn.turner.com/cnn/dam/assets/140624185139-suarez-tease-video-tease.jpg");//url.getFile());
			System.out.println(f.getName());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
