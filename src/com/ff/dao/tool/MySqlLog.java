package com.ff.dao.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import com.ff.tool.MyProperties;



public final class MySqlLog {

	private final static DateFormat dateFileFormat = new SimpleDateFormat("dd_MM_yyyy");
	private final static DateFormat dateLogFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public final static void log(String msg) {
		if (MyProperties.getBoolean("debug.sql")) {
			System.out.println(msg);
		}else{
			final File file = new File(System.getProperty("user.home")+ File.separator + ".ffcrawling" + File.separator + "libsql" + File.separator + dateFileFormat.format(new GregorianCalendar().getTime()) + ".log");
			if (!file.exists())
				file.getParentFile().mkdirs();
			try{
				FileOutputStream flog = new FileOutputStream(file, true);
				msg = "\n" + dateLogFormat.format(new GregorianCalendar().getTime()) + " " + msg;
				flog.write(msg.getBytes("UTF-8"));
				flog.flush();
				try {
					flog.close();
					flog = null;
				} catch (Exception e) { }
			} catch (IOException t) { }
		}
	}
	public final static void log(Exception ex) {
		if (MyProperties.getBoolean("debug.sql")) {
			ex.printStackTrace();
		}else{
			log(ex.getMessage());
		}
	}

}