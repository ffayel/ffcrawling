package com.ff.conf;


import java.util.ArrayList;
import java.util.List;

import com.ff.dao.data.DataLabelString;

public final class AppConf {

	public static final List<DataLabelString> getDefaultConfig() {
		final List<DataLabelString> config = new ArrayList<DataLabelString>();

		/* Web */
		config.add(new DataLabelString("web.lock", "false"));

		/* Database */
		config.add(new DataLabelString("db.class", "com.mysql.jdbc.Driver"));
		config.add(new DataLabelString("db.path", "jdbc:mysql://127.0.0.1:3306/crawling?useUnicode=true&characterEncoding=UTF-8"));
		config.add(new DataLabelString("db.user", "root"));
		config.add(new DataLabelString("db.pass", ""));

		/* Path */
		config.add(new DataLabelString("path.image", "/var/ImageDB/"));
		
		/* Debug */
		config.add(new DataLabelString("debug.sql", "false"));
		config.add(new DataLabelString("debug.navigation", "false"));
		
		/* MARVEL*/
		config.add(new DataLabelString("marvel.publickey", ""));
		config.add(new DataLabelString("marvel.privatekey", ""));
		

		return config;
	}

	public static String getDefaultValue(String propertyName) {
		String dft ="";
		for (DataLabelString dataLabelString : getDefaultConfig()) {
			if(propertyName.equals(dataLabelString.getKey())){
				dft = dataLabelString.getValue();
				break;
			}
		}
		return dft;
	}

}
