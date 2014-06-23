package com.ff.tool;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MyLang {
	private final static String BUNDLENAME="com.ff.ressource.lang";
	private static MyLang INSTANCE_LANGUAGE =null;

	private ResourceBundle langResources = null;

	public static MyLang getInstance(){
		if(INSTANCE_LANGUAGE == null)
			INSTANCE_LANGUAGE = new MyLang();
		return INSTANCE_LANGUAGE;
	}

	public static MyLang getInstance(Locale locale){
		if(INSTANCE_LANGUAGE == null){
			INSTANCE_LANGUAGE = new MyLang(locale);
		}else{
			INSTANCE_LANGUAGE.setLocale(locale);
		}
		return INSTANCE_LANGUAGE;
	}

	public MyLang() {
		loadLang(Locale.getDefault());
	}

	public MyLang(Locale locale) {
		loadLang(locale);
	}

	public MyLang(String language) {
		loadLang(new Locale(language));
	}

	public MyLang(String language, String country) {
		loadLang(new Locale(language, country));
	}

	private void loadLang(Locale newLocale) {
		Locale locale = null;
		try {
			locale = newLocale;
			if (locale != null) {
				try {
					langResources = ResourceBundle
							.getBundle(BUNDLENAME, locale);
				} catch (MissingResourceException mre1) {
					langResources = ResourceBundle.getBundle(BUNDLENAME);
				}
			} else {
				langResources = ResourceBundle.getBundle(BUNDLENAME);
			}
		} catch (MissingResourceException mre) { }
		try { Locale.setDefault(langResources.getLocale()); } catch (Exception e) { }
	}

	public static List<Locale> getSupportedLocal(){
		final List<Locale> list = new ArrayList<Locale>();
		for(Locale locale :Locale.getAvailableLocales()){
			if(isSupported(locale))
					list.add(locale);
		}
		return list;
	}

	public static boolean isSupported(Locale locale){
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(BUNDLENAME, locale);
			if(locale.equals(bundle.getLocale()))
				return true;
		} catch (MissingResourceException mre1) {}
		return false;
	}

	public String getTranslationString(String originalText) {
		try {
			return langResources.getString(originalText);
		} catch (Exception n) {
			return originalText;
		}
	}

	public String getTranslationString(String originalText, Object... arguments){
		try{
			return MessageFormat.format(langResources.getString(originalText), arguments);
		} catch (Exception n) {
			return originalText;
		}
	}

	public void setLocale(Locale locale){
		loadLang(locale);
	}

	public final Locale getCurrentLocal(){
		return langResources.getLocale();
	}

	@Override
	protected void finalize() throws Throwable {
		INSTANCE_LANGUAGE = null;
		langResources = null;
	}

}
