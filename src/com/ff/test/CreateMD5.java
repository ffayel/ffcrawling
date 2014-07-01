package com.ff.test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.ff.tool.MyProperties;
import com.ff.tool.MyTime;

public class CreateMD5 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String yourString = "1abcd1234";

		System.out.println("fonction encode :" + encode(yourString));
		String ts = MyTime.getNowTmpFileName();
		String apiKey = MyProperties.getString("marvel.publickey");
		String privateKey = MyProperties.getString("marvel.privatekey");
		String hash = encode(ts + privateKey + apiKey);
		String url = "http://gateway.marvel.com/v1/comics/?ts=" + ts + "&apikey=" + apiKey + "&hash=" + hash;
		System.out.println(url);

	}

	private static String encode(String password) {
		byte[] uniqueKey = password.getBytes();
		byte[] hash = null;

		try {
			hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
		} catch (NoSuchAlgorithmException e) {
			throw new Error("No MD5 support in this VM.");
		}

		StringBuilder hashString = new StringBuilder();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(hash[i]);
			if (hex.length() == 1) {
				hashString.append('0');
				hashString.append(hex.charAt(hex.length() - 1));
			} else
				hashString.append(hex.substring(hex.length() - 2));
		}
		return hashString.toString();
	}

}
