package com.ff.dao.data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.ff.tool.MyTime;

public class MarvelApiKey {
	private String ts;
	private String apiKey;
	private String hash;

	public MarvelApiKey(final String publicKey, final String privateKey) {
		this.ts = MyTime.getNowTmpFileName();
		this.apiKey = publicKey;
		this.hash = encode(ts + privateKey + apiKey);
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
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
