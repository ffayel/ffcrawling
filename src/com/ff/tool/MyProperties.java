package com.ff.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import com.ff.conf.AppConf;
import com.ff.dao.data.DataLabelString;
import com.ff.dao.data.DataProperty;

public final class MyProperties {

	private static boolean OPEN = false;
	private static final Properties _config = new Properties();
	private final static String PATH = System.getProperty("user.home");

	public final static void forceReLoad() {
		_config.clear();
		OPEN = false;
	}

	public final static void save(){
		if(OPEN){
			final File maConf = new File(PATH + File.separator + ".ffcrawling" + File.separator + "crawling.cfg");
			if (maConf.exists()) {
				store(maConf);
			}
		}
	}

	private final static void load() {
		try { new File(PATH + File.separator + ".ffcrawling" + File.separator).mkdirs(); } catch (Exception e) {
			System.err.println("MyProperties cant create file : "+PATH + File.separator + ".ffcrawling" + File.separator);
		}
		final File maConf = new File(PATH + File.separator + ".ffcrawling" + File.separator + "crawling.cfg");
		if (!maConf.exists()) {
			try {
				maConf.createNewFile();
				loadDefaultValues();
				store(maConf);
				OPEN = true;
			} catch (IOException e) {
				System.err.println("MyProperties cant create file : "+maConf.getPath());
				e.printStackTrace(); }
		} else {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(maConf.getAbsolutePath());
				_config.load(fis);
				OPEN = true;
				checkUpdate();
				store(maConf);
			} catch (Exception e) { e.printStackTrace(); }
			finally { try { fis.close(); } catch (Exception e) { } }
		}
		if (!OPEN) {
			loadDefaultValues();
			OPEN = true;
		}
	}

	public final static String getConfigFile() {
		return PATH + File.separator + ".ffcrawling" + File.separator + "crawling.cfg";
	}

	private static final void loadDefaultValues(){
		final List<DataLabelString> defaultConfig = AppConf.getDefaultConfig();
		for (final DataLabelString param : defaultConfig)
			_config.setProperty(param.getKey(), param.getValue());
	}

	private static final void checkUpdate(){
		final List<DataLabelString> defaultConfig = AppConf.getDefaultConfig();
		for (final DataLabelString param : defaultConfig) {
			if (getString(param.getKey(), null) == null) {
				_config.setProperty(param.getKey(), param.getValue());
			}
		}
	}

	public static final void set(final String key, final String value){
		if (!OPEN) load();
		_config.setProperty(key, value);
	}

	public static final String getString(final String key){ return getString(key, ""); }
	public static final String getString(final String key, final String defaultValue){
		if (!OPEN) load();
		return _config.getProperty(key, defaultValue);
	}

	public static final int getInt(final String key){ return getInt(key, -1); }
	public static final int getInt(final String key, final int defaultValue){
		if (!OPEN) load();
		try {
			return Integer.parseInt(_config.getProperty(key, ""+defaultValue));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static final double getDouble(final String key){ return getDouble(key, -1); }
	public static final double getDouble(final String key, final double defaultValue){
		if (!OPEN) load();
		try {
			return Double.parseDouble(_config.getProperty(key, ""+defaultValue));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static final long getLong(final String key){ return getLong(key, -1L); }
	public static final long getLong(final String key, final long defaultValue){
		if (!OPEN) load();
		try {
			return Long.parseLong(_config.getProperty(key, ""+defaultValue));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static final boolean getBoolean(final String key){ return getBoolean(key, false); }
	public static final boolean getBoolean(final String key, final boolean defaultValue){
		if (!OPEN) load();
		try {
			final String txt = _config.getProperty(key, "false");
			for (final String trueText : MyStatics._TRUE_TEXTS) {
				if (txt.equals(trueText))
					return true;
			}
			return false;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	private static void store(final File maConf) {
		try {
			if (!maConf.getParentFile().exists()) { maConf.getParentFile().mkdirs(); }
			FileOutputStream fos = new FileOutputStream(maConf.getAbsolutePath());
			_config.store(fos, "");
			fos.flush();
			fos.close();
		} catch (IOException e) { e.printStackTrace(); }
	}

	public static List<DataProperty> getAll() {
		if (!OPEN) load();
		final List<DataProperty> params = new ArrayList<DataProperty>();
		try {
			Iterator<Object> it = _config.keySet().iterator();
			while (it.hasNext()) {
			    String propertyName = (String) it.next();
			    String propertyValue = _config.getProperty(propertyName);
			    String propertyDefaultValue = AppConf.getDefaultValue(propertyName);
			    params.add(new DataProperty(propertyName, propertyValue, propertyDefaultValue));
			}
		} catch (Exception e) { }
		Collections.sort(params);
		return params;
	}

	public static List<String> getListString(String key, String separator) {
		final List<String> result = new ArrayList<String>();
		try {
			final String value = getString(key, "");
			final Scanner scan = new Scanner(value);
			scan.useDelimiter(separator);
			while (scan.hasNext()) {
				final String tmp = scan.next().trim();
				if (!"".equals(tmp))
					result.add(tmp);
			}
		} catch (Exception e) { }
		return result;
	}

}
